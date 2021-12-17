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
public class BusinessPartner {

    public static List<String> businessPartner(String lang) throws IOException {
        List<String> businessRates =new ArrayList<>();
        SSLFix.execute();
        Document document = Jsoup.connect("https://www.anorbank.uz/" + lang + "/business/partner-program/").get();

        Elements image = document.getElementsByAttributeValue("class", "slider-banner__img");
        String img = image.attr("src");
        businessRates.add("https://www.anorbank.uz/" + img.substring(img.indexOf("/"), img.lastIndexOf(".")) + ".png"); //image

        Elements cardInfo = document.getElementsByAttributeValue("class", "col-12 col-md-12 col-lg-6 slider-banner__teaser ");
        String h3 =cardInfo.get(0).childNodes().get(23).toString();
        businessRates.add(h3.substring(3, h3.lastIndexOf("<")).substring(1)); //<h3>

//
        Elements creditInfoButton = document.getElementsByAttributeValue("class", "col-12 col-md-12 col-lg-6 slider-banner__teaser ");
        String p = creditInfoButton.get(0).childNodes().get(25).toString();
        businessRates.add(p.substring(2, p.lastIndexOf("<")).substring(1));
        Elements depInfoButton = document.getElementsByAttributeValue("class", "slider-banner__btn");
        String button = depInfoButton.get(0).childNodes().get(1).toString();
        businessRates.add(button.substring(button.indexOf(">"), button.lastIndexOf("<")).substring(1));
        businessRates.add("https://www.anorbank.uz/" + (lang.equals("") ? "" : lang + "/") + "business/partner-program"+(lang.equals("") ? "" : "-" + lang)+"/");

        return businessRates;
    }
}
