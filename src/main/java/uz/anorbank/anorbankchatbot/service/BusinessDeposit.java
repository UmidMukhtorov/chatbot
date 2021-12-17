package uz.anorbank.anorbankchatbot.service;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import uz.anorbank.anorbankchatbot.config.SSLFix;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@Slf4j
@Service
public class BusinessDeposit {

    public static List<String> getOverNightDeposit(String lang) throws IOException {
        List<String> businessRates =new ArrayList<>();
        SSLFix.execute();
        Document document = Jsoup.connect("https://www.anorbank.uz/" + lang + "/business/deposits/").get();

        Elements image = document.getElementsByAttributeValue("class", "biznes-cards__img");
        String img = image.get(0).childNodes().get(1).attr("src");
        businessRates.add("https://www.anorbank.uz/" + img.substring(img.indexOf("/"), img.lastIndexOf(".")) + ".png"); //image

        Elements cardInfo = document.getElementsByAttributeValue("class", "biznes-cards__teaser");
        String p = cardInfo.get(0).childNodes().get(3).toString();
        businessRates.add(p.substring(3, p.lastIndexOf("<"))); //<p>


        Elements creditInfoButton = document.getElementsByAttributeValue("class", "biznes-cards__btns");
        String button = creditInfoButton.get(0).childNodes().get(1).toString();

        businessRates.add(button.substring(button.indexOf(">"), button.lastIndexOf("<")).substring(1));
        businessRates.add("https://www.anorbank.uz/" + (lang.equals("") ? "" : lang + "/") + "business/deposits/overnight"+(lang.equals("") ? "" : "-" + lang)+"/");

        return businessRates;
    }

    public static List<String> getFixedTermDeposit(String lang) throws IOException {
        List<String> businessRates =new ArrayList<>();
        SSLFix.execute();
        Document document = Jsoup.connect("https://www.anorbank.uz/" + lang + "/business/deposits/").get();

        Elements image = document.getElementsByAttributeValue("class", "biznes-cards__img");
        String img = image.get(1).childNodes().get(1).attr("src");
        businessRates.add("https://www.anorbank.uz/" + img.substring(img.indexOf("/"), img.lastIndexOf(".")) + ".png"); //image

        Elements cardInfo = document.getElementsByAttributeValue("class", "biznes-cards__teaser");
        String p = cardInfo.get(1).childNodes().get(3).toString();
        businessRates.add(p.substring(3, p.lastIndexOf("<"))); //<p>


        Elements creditInfoButton = document.getElementsByAttributeValue("class", "biznes-cards__btns");
        String button = creditInfoButton.get(1).childNodes().get(1).toString();

        businessRates.add(button.substring(button.indexOf(">"), button.lastIndexOf("<")).substring(1));
        businessRates.add("https://www.anorbank.uz/" + (lang.equals("") ? "" : lang + "/") + "business/deposits/term-deposit"+(lang.equals("") ? "" : "-" + lang)+"/");

        return businessRates;
    }

    public static List<String> getDocuments(String lang) throws IOException {
        List<String> businessRates =new ArrayList<>();
        SSLFix.execute();
        businessRates.add("https://www.anorbank.uz/upload/medialibrary/31b/31b62a6a646d88be68547dcafcec9a3b.jpg"); //image
        Document document = Jsoup.connect("https://www.anorbank.uz/" + lang + "/about/documents/").get(); //h3
        businessRates.add("⠀ ");
        Elements creditInfoButton = document.getElementsByAttributeValue("class", "col-12 col-md-9 pages__col pages__col--content");
        String button = creditInfoButton.get(0).childNodes().get(1).toString(); //button text
        businessRates.add(button.substring(button.indexOf(">"), button.lastIndexOf("<")).substring(1));
        businessRates.add("https://www.anorbank.uz/" + (lang.equals("") ? "" : lang + "/") + "about/documents/");



        return businessRates;
}
}
