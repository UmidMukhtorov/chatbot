package uz.anorbank.anorbankchatbot.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import uz.anorbank.anorbankchatbot.config.SSLFix;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClientHelper {
    public static List<String> getLinkBot() throws IOException {   //ANOR muddatli to’lov kartasi
        List<String> getLink = new ArrayList<>();
        SSLFix.execute();
        getLink.add("Не получил ответ на вопрос");
        getLink.add("https://www.t.me/anorbank_chat_bot");


        return getLink;
    }
}
