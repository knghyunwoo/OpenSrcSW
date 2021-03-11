package com.company;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileOutputStream;

public class Main {
    public static void main (String[] args) {

        try {
            // XML 파일 만들기
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            Document doc = docBuilder.newDocument();
            doc.setXmlStandalone(true); //standalone="no" 를 없애준다.

            Element docs = doc.createElement("docs");
            doc.appendChild(docs);

            // HTML 파일 가져오기
            File directoryPath = new File("C:\\\\Users\\\\s_knghyunwoo\\\\Desktop\\\\21년 1학기\\\\오픈소스\\\\2주차\\\\SimpleIR\\\\html");
            File filesList[] = directoryPath.listFiles();
            int id = 0;

            // HTML Parsing 시작
            for (File file : filesList) {
                org.jsoup.nodes.Document document = Jsoup.parse(file, "UTF-8");

                Elements titles = document.select("title");
                Elements bodys = document.select("p");

                String idToString = Integer.toString(id);

                Element code = doc.createElement("doc");
                docs.appendChild(code);

                code.setAttribute("id", idToString);

                Element title = doc.createElement("title");
                title.appendChild(doc.createTextNode(titles.html()));
                code.appendChild(title);

                Element body = doc.createElement("body");
                body.appendChild(doc.createTextNode(bodys.html()));
                code.appendChild(body);

                // XML 파일로 쓰기
                TransformerFactory transformerFactory = TransformerFactory.newInstance();

                Transformer transformer = transformerFactory.newTransformer();
                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4"); //정렬 스페이스4칸
                transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
                transformer.setOutputProperty(OutputKeys.INDENT, "yes"); //들여쓰기
                transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, "yes"); //doc.setXmlStandalone(true); 했을때 붙어서 출력되는부분 개행

                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(new FileOutputStream(new File("C:\\Users\\s_knghyunwoo\\Desktop\\21년 1학기\\오픈소스\\2주차\\SimpleIR\\xml\\food.xml")));

                transformer.transform(source, result);
                id++;
//                System.out.println("=========END=========");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}




