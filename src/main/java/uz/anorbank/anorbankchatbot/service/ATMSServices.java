package uz.anorbank.anorbankchatbot.service;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import uz.anorbank.anorbankchatbot.config.SSLFix;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ATMSServices{

    public static List<String> getATMS(String lang) throws IOException {
        List<String> aboutVacancies =new ArrayList<>();
        SSLFix.execute();
        Document document = Jsoup.connect("https://www.anorbank.uz/" + lang + "/atms/").get();
        aboutVacancies.add("https://www.anorbank.uz/upload/medialibrary/31b/31b62a6a646d88be68547dcafcec9a3b.jpg"); //image

        Elements anorBankVacancies = document.getElementsByAttributeValue("class", "maps-bankomats__tabs");
        String p = anorBankVacancies.get(0).childNodes().get(1).childNodes().get(3).childNodes().get(1).childNodes().get(0).toString();
        aboutVacancies.add(p); //<h3>

        if (lang.equals("uz")){
            aboutVacancies.add("Batafsil");
        }else if (lang.equals("")){
            aboutVacancies.add("Подробнее");
        }else if (lang.equals("en")){
            aboutVacancies.add("More");//buttonText
        }


        aboutVacancies.add("https://www.anorbank.uz/" + (lang.equals("") ? "" : lang + "/") + "atms"+"/");//buttonURL
        return aboutVacancies;
    }



}
