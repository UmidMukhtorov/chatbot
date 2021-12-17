package uz.anorbank.anorbankchatbot.service;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public class SendGroupMessage {
    protected static void doInBackground(String message) {
        String urlString = "https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s";

        //Add Telegram token (given Token is fake)
        String apiToken = "5012038491:AAFFganXTtSR9rEUqxqLE9sZP2wdsLLbbbc";

        //Add chatId (given chatId is fake)
        String chatId = "https://t.me/+_lf0_cfDz5U3ZjE6";

        //https://api.telegram.org/bot664321744:AAGimqEuidlzO84qMoY1-_C-1OsNWRQ8FyM/sendMessage?chat_id=-1001349137188&amp&text=Hello+World
        urlString = String.format(urlString, apiToken, chatId, message);

        try {
            URL url = new URL(urlString);
            URLConnection conn = url.openConnection();
            InputStream is = new BufferedInputStream(conn.getInputStream());

            //getting text, we can set it to any TextView
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String inputLine = "";
            StringBuilder sb = new StringBuilder();
            while ((inputLine = br.readLine()) != null) {
                sb.append(inputLine);
            }
            //You can set this String to any TextView
            String response = sb.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
