import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.xml.sax.SAXException;
import org.snu.ids.kkma.index.Keyword;
import org.snu.ids.kkma.index.KeywordExtractor;
import org.snu.ids.kkma.index.KeywordList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


public class searcher {

    public static void search(String fileName, String query) throws IOException, ParserConfigurationException, SAXException, ClassNotFoundException {

        int fileNum = 0;
        ArrayList<String> docTitle = new ArrayList<>();
        HashMap<Integer, Double> similarFile = new HashMap<>();
        HashMap<Integer, Double> cosineSimilarFile = new HashMap<>();
        HashMap<String, Integer> queryMap = new HashMap<>();

        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document collection = docBuilder.parse("./collection.xml");
        collection.getDocumentElement().normalize();
        NodeList nList = collection.getElementsByTagName("doc");

        fileNum = nList.getLength();
        if (nList.getLength() > 0) {
            for (int i = 0; i < nList.getLength(); i++) {
                Element nNode = (Element) nList.item(i);
                docTitle.add(nNode.getElementsByTagName("title").item(0).getChildNodes().item(0).getNodeValue());
            }
        }

        //index.post의 HashMap 만들기
        FileInputStream fileStream = new FileInputStream(fileName);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileStream);
        Object object = objectInputStream.readObject();
        objectInputStream.close();
        HashMap<String, String> indexPostMap = (HashMap) object;

        //문장 꼬꼬마 분석
        KeywordExtractor kkma = new KeywordExtractor();
        KeywordList keywordList = kkma.extractKeyword(query, true);
        for (Keyword keyword : keywordList) {
            queryMap.put(keyword.getString(), 1);
        }

        //문서 별로 유사도 계산
        for (int i = 0; i < fileNum; i++) {
            similarFile.put(i, InnerProduct(i, queryMap, indexPostMap));  // InnerProduct 계산해서 넣기
            cosineSimilarFile.put(i, calcSim(i, queryMap, indexPostMap)); // calcSim (Cosine Similarity) 계산해서 넣기
        }

        // InnerProduct 정렬
        List<Integer> keySetList = new ArrayList<>(similarFile.keySet());
        Collections.sort(keySetList, (a1, a2) -> (similarFile.get(a2).compareTo(similarFile.get(a1))));
        System.out.println("InnerProduct Result");
        for (Integer key : keySetList) {
            System.out.println("key: " + key + ", value: " + String.format("%.2f", similarFile.get(key)));
        }
        System.out.println();
        for (int i = 0; i < 3; i++) {
            System.out.println(i + 1 + "번째 문서: " + docTitle.get(keySetList.get(i)));
        }

        // Cosine Similarity 정렬
        List<Integer> keySetList2 = new ArrayList<>(cosineSimilarFile.keySet());
        Collections.sort(keySetList2, (a1, a2) -> (cosineSimilarFile.get(a2).compareTo(cosineSimilarFile.get(a1))));
        System.out.println("Cosine Similarity Result");
        for (Integer key : keySetList2) {
            System.out.println("key: " + key + ", value: " + String.format("%.2f", cosineSimilarFile.get(key)));
        }
        System.out.println();
        for (int i = 0; i < 3; i++) {
            System.out.println(i + 1 + "번째 문서: " + docTitle.get(keySetList2.get(i)));
        }
    }


    private static double InnerProduct(int i, HashMap<String, Integer> queryMap, HashMap<String, String> indexPostMap) {
        double similarity = 0;
        Iterator<String> keys = queryMap.keySet().iterator();
        queryMap.keySet();
        while (keys.hasNext()) {
            String key = keys.next();
            if (indexPostMap.containsKey(key)) {
                String s = String.valueOf(indexPostMap.get(key));
                s = s.replace("[", "");
                s = s.replace("]", "");
                s = s.replace(",", "");
                String[] split = s.split(" ");

                for (int index = 0; index < split.length; index += 2) {
                    if (Integer.parseInt(split[index]) == i) {
                        similarity += queryMap.get(key) * Double.parseDouble(split[index + 1]);
                        break;
                    }
                }
            }
        }
        return similarity;
    }

    private static double calcSim(int i, HashMap<String, Integer> queryMap, HashMap<String, String> indexPostMap) {
        double cosineSimilarity = 0;
        double innerProduct = InnerProduct(i, queryMap, indexPostMap);
        double dividor1 = 0, dividor2 =0;
        Iterator<String> keys = queryMap.keySet().iterator();
        queryMap.keySet();
        while (keys.hasNext()) {
            String key = keys.next();
            if (indexPostMap.containsKey(key)) {
                String s = String.valueOf(indexPostMap.get(key));
                s = s.replace("[", "");
                s = s.replace("]", "");
                s = s.replace(",", "");
                String[] split = s.split(" ");

                for (int index = 0; index < split.length; index += 2) {
                    if (Integer.parseInt(split[index]) == i) {
                        dividor1 += Math.pow(queryMap.get(key),2);
                        dividor2 += Math.pow(Double.parseDouble(split[index + 1]),2);
                        break;
                    }
                }
            }
        }
        // 결과값이 0일 경우 예외처리
        if (dividor1 == 0 || dividor2 == 0){
            dividor1 = 1;
            dividor2 = 2;
            innerProduct = 0;
        }
        dividor1 = Math.sqrt(dividor1);
        dividor2 = Math.sqrt(dividor2);
        cosineSimilarity += innerProduct / (dividor1 * dividor2);

        return cosineSimilarity;
    }

}