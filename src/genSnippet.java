import org.jsoup.Jsoup;
import org.snu.ids.kkma.index.Keyword;
import org.snu.ids.kkma.index.KeywordExtractor;
import org.snu.ids.kkma.index.KeywordList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.List;


// 제가 자바가 미숙하여 매번 알아보며 실습을 했는데 문법이 많이 틀려 실행이 안됩니다 의사코드 느낌으로 작성을 해보겠습니다.
public class genSnippet {
    public static void genSnippet(String inputPath, String query) throws IOException, ParserConfigurationException, SAXException, ClassNotFoundException{
        System.out.println(inputPath);
        System.out.println(query);
        try {
            FileInputStream fileStream = new FileInputStream(inputPath);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileStream);
            Object nodeList = objectInputStream.readObject();

            for(int i = 0; i < nodeList.getLength(); i++){
                Node node = nodeList.item(i);
                Element element = (Element)node;
                String data = element.getElementsByTagName("body").item(0).getTextContent();
                data = data.replace("," , "");
                String count_data = new String();
                for (int j=0; j< node.size(); j++){
                    Keyword kwrd = node.get(j);
                    String temp = kwrd.getString() + ":" + kwrd.getCnt()+ "#";
                    if (kwrd == j){
                        temp += 1
                    }
                    count_data = count_data.concat(temp);
                }
                // 다 못짰습니다 죄송합니다
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
