package GoogleTranslate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class GoogleTranslate {

    public static final String ENGLISH_LANG = "en";
    public static final String VIETNAM_LANG = "vi";

    public static String translate(String langFrom, String langTo, String text) throws IOException {
        try {
            String urlStr = "https://script.google.com/macros/s/AKfycbzSfX-GI-K87YHdwlrJy-xTgx63TBBCfLq57VdiZKzfY8qdFAGVRgwsAw6dHICLSSlo7g/exec" +
                    "?q=" + URLEncoder.encode(text, "UTF-8") +
                    "&target=" + langTo +
                    "&source=" + langFrom;
            URL url = new URL(urlStr);

            //  String urlStr = "https://script.google.com/macros/s/AKfycbyh9n_7WPZVUUrW0ozGGlmjpvcDWpxHxjBGlvhfJBy_C4eK-qzQo6x-AAIwp2GT4BJuXQ/exec" +
            //         "?q=" + URLEncoder.encode(text, "UTF-8") +
            //         "&target=" + langTo +
            //         "&source=" + langFrom;
            // URL url = new URL(urlStr);
            
            StringBuilder response = new StringBuilder();
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("User-Agent", "Mozilla/5.0");

            // // Kiểm tra Content-Type
            // Map<String, List<String>> headers = con.getHeaderFields();
            // String contentType = headers.get("Content-Type").get(0);
            // System.out.println("Content-Type: " + contentType);

            try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
            }

            return response.toString();
        } catch (IOException e) {
            e.printStackTrace(); // Xử lý ngoại lệ một cách thích hợp hoặc ném lại
            return "Translation Error";
        }
    }
}