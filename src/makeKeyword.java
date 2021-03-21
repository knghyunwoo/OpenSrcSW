import java.io.*;
import org.snu.ids.kkma.index.Keyword;
import org.snu.ids.kkma.index.KeywordExtractor;
import org.snu.ids.kkma.index.KeywordList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class makeKeyword {
//    public static void main(String[] args) {
    public static void makeKeyword (String collectionXmlPath) {
//    String collectionXmlPath = args[0];
        try {
            //collection.xml을 복사하여 index.xml 생성
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(collectionXmlPath), "UTF-8"));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("./index.xml"), "UTF-8"));
            String line = null;
            while ((line = reader.readLine()) != null)
            {
                writer.write(line);
                writer.write(" ");
            }
            reader.close();
            writer.close();

            // 복사해서 만든 index.xml 열기
            DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
            DocumentBuilder parser = f.newDocumentBuilder();
            String url = "./index.xml";
            Document doc = parser.parse(url);
            Element root = doc.getDocumentElement();
            NodeList nodeList = root.getElementsByTagName("doc");

            // 꼬꼬마 분석기로 분석후 데이터 바꾸기
            for(int i = 0; i < nodeList.getLength(); i++){
                Node node = nodeList.item(i);
                Element element = (Element)node;
                String data = element.getElementsByTagName("body").item(0).getTextContent();
                KeywordExtractor ke = new KeywordExtractor();
                KeywordList kl = ke.extractKeyword(data, true);
                String count_data = new String();
                for (int j=0; j< kl.size(); j++){
                    Keyword kwrd = kl.get(j);
                    String temp = kwrd.getString() + ":" + kwrd.getCnt()+ "#";
                    count_data = count_data.concat(temp);
                }
                element.getElementsByTagName("body").item(0).setTextContent(count_data);
            }

            // 바꾼부분을 xml로 재생성
            Transformer xformer = TransformerFactory.newInstance().newTransformer();
            String outputFile = "./index.xml";
            xformer.transform(new DOMSource(doc), new StreamResult(new File(outputFile)));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}