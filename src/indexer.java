import java.io.*;
import java.util.*;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class indexer {
    public static double log(int a, double n1Length) {
        return n1Length/Double.parseDouble(String.valueOf(a));
    }
    public static double freqBody(double log, String splitJ) {
        return Double.parseDouble(splitJ)*Math.log(log);
    }

    @SuppressWarnings({"rawtypes", "unchecked", "nls"})
    public static void indexer(String indexXmlPath) {
        try {
            File xml = new File(indexXmlPath);
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(xml);
            doc.getDocumentElement().normalize();

            FileOutputStream fileStream = new FileOutputStream("./index.post");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileStream);
            HashMap hash = new HashMap();
            HashMap tfMap = new HashMap();
            NodeList n1 = doc.getElementsByTagName("body");

            List DocArr = new ArrayList();

            // index.xml에서 각각의 보디내용 다 받아옴
            for (int i = 0; i < n1.getLength(); i++) {
                Node temp = n1.item(i);
                DocArr.add(temp.getTextContent());
//                System.out.println(DocArr.get(i));
            }
            String[] splitArray = null;
            // DocArr를 돌며 tfMap hashmap에 단어랑 tf 저장
            for (int i = 0; i < DocArr.size(); i++) {
                splitArray = Filter(DocArr.get(i).toString());
                for (int j = 0; j < splitArray.length; j += 2) { //한글만 가져오려고 2씩 추가
                    if (tfMap.containsKey(splitArray[j])) {
                        int value = (int) tfMap.get(splitArray[j]) + 1; // 단어에 해당하는 tf 값 가져오기
                        tfMap.put(splitArray[j], value);
                    } else {
                        tfMap.put(splitArray[j], 1);
                    }

                    // index.post에 넣을 hashmap 에 넣을 단어와 가중치계산
                    double log = log((int) tfMap.get(splitArray[j]), n1.getLength());
                    double freqBody = freqBody(log, splitArray[j+1]);
                    String roundBody = String.format("%.2f", freqBody);

                    List<String> idList = new ArrayList<String>(); //몇번쨰 문서인지 알려주는 리스트
                    idList.add(Integer.toString(i));
                    idList.add(roundBody);

                    if (hash.containsKey(splitArray[j])) {
                        List<String> dfList = (List<String>) hash.get(splitArray[j]);
                        dfList.addAll(idList);
                        hash.put(splitArray[j], dfList);
                    } else {
                        hash.put(splitArray[j], idList);
                    }
                }
            }
            objectOutputStream.writeObject(hash);
            objectOutputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String[] Filter(String tempDoc) {

        String changeDoc = new String(tempDoc);
        while (changeDoc.indexOf("#") > 0) {
            changeDoc = changeDoc.replace(":", ",");
            changeDoc = changeDoc.replace("#", ",");
//            System.out.println("changeDoc = " + changeDoc);
        }
        String stringArray = changeDoc.toString();
        String[] splitArray = stringArray.split(",");

        return splitArray;
    }

    public static void PostOpen() throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream("index.post");

        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

        Object object = objectInputStream.readObject();
        objectInputStream.close();

        HashMap hashMap = (HashMap) object;
        Iterator<String> it = hashMap.keySet().iterator();

        while (it.hasNext()) {
            String key = it.next();
            String value = hashMap.get(key).toString();
            System.out.println(key + "->" + value);
        }
    }
}
