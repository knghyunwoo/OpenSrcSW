import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class MovieAPI {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        String clientId = "Oq8EwJ40lhvkF90tWYpA";
        String clientSecret = "joh7bcsAGO";
        String text = null;
        try {
            Scanner scan = new Scanner(System.in);
            String query;
            System.out.print("검색어를 입력하세요 : ");
            query = scan.nextLine();
            text = URLEncoder.encode(query, "UTF-8");
            scan.close();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("검색어 인코딩 실패",e);
        }

        String apiURL = "https://openapi.naver.com/v1/search/movie.json?query=" + text;

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", clientSecret);
        String responseBody = get(apiURL,requestHeaders);

        try {
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject;
            jsonObject = (JSONObject) jsonParser.parse(responseBody);
            JSONArray infoArray = (JSONArray) jsonObject.get("items");

            for(int i = 0; i < infoArray.size(); i++) {
                System.out.println("=item_" + i + "============================");
                JSONObject itemObject = (JSONObject) infoArray.get(i);
                System.out.println("title:\t" + itemObject.get("title"));
                System.out.println("subtitle:\t" + itemObject.get("subtitle"));
                System.out.println("director:\t" + itemObject.get("director"));
                System.out.println("actor:\t" + itemObject.get("actor"));
                System.out.println("userRating:\t" + itemObject.get("userRating") + "\n");
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    private static String get(String apiUrl, Map<String, String> requestHeaders){
        HttpURLConnection con = connect(apiUrl);
        try {
            con.setRequestMethod("GET");
            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
                return readBody(con.getInputStream());
            } else { // 에러 발생
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }

    private static HttpURLConnection connect(String apiUrl){
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }

    private static String readBody(InputStream body){
        InputStreamReader streamReader = new InputStreamReader(body);

        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();

            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }

            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
        }
    }

}