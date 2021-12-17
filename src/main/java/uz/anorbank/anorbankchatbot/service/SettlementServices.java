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
public class SettlementServices  {



    public static List<String> getBusinessRates(String lang) throws IOException {
        List<String> businessRates = new ArrayList<>();
        SSLFix.execute();
        Document document = Jsoup.connect("https://www.anorbank.uz/" + lang + "/business/settlement-service/").get();

        Elements image = document.getElementsByAttributeValue("class", "biznes-cards__img");
        String img = image.get(0).childNodes().get(1).attr("src");
        businessRates.add("https://www.anorbank.uz/" + img.substring(img.indexOf("/"), img.lastIndexOf(".")) + ".png"); //image

        Elements cardInfo = document.getElementsByAttributeValue("class", "biznes-cards__teaser");
        String p = cardInfo.get(0).childNodes().get(3).toString();
        businessRates.add(p.substring(3, p.lastIndexOf("<"))); //<p>


        Elements creditInfoButton = document.getElementsByAttributeValue("class", "biznes-cards__btns");
        String button = creditInfoButton.get(0).childNodes().get(1).toString();

        businessRates.add(button.substring(button.indexOf(">"), button.lastIndexOf("<")).substring(1));
        businessRates.add("https://www.anorbank.uz/" + (lang.equals("") ? "" : lang + "/") + "business/settlement-service/rates" + (lang.equals("") ? "" : "-" + lang) + "/");

        return businessRates;
    }

    public static List<String> getBusinessOverNightDeposit(String lang) throws IOException {
        List<String> businessOverNightDeposit = new ArrayList<>();
        SSLFix.execute();
        Document document = Jsoup.connect("https://www.anorbank.uz/" + lang + "/business/settlement-service/").get();

        Elements image = document.getElementsByAttributeValue("class", "biznes-cards__img");
        String img = image.get(1).childNodes().get(1).attr("src");
        businessOverNightDeposit.add("https://www.anorbank.uz/" + img.substring(img.indexOf("/"), img.lastIndexOf(".")) + ".png"); //image

        Elements cardInfo = document.getElementsByAttributeValue("class", "biznes-cards__teaser");
        String p = cardInfo.get(1).childNodes().get(3).toString();
        businessOverNightDeposit.add(p.substring(3, p.lastIndexOf("<"))); //<p>


        Elements creditInfoButton = document.getElementsByAttributeValue("class", "biznes-cards__btns");
        String button = creditInfoButton.get(1).childNodes().get(1).toString();

        businessOverNightDeposit.add(button.substring(button.indexOf(">"), button.lastIndexOf("<")).substring(1));
        businessOverNightDeposit.add("https://www.anorbank.uz/" + (lang.equals("") ? "" : lang + "/") + "business/deposits/overnight" + (lang.equals("") ? "" : "-" + lang) + "/");

        return businessOverNightDeposit;
    }

    public static List<String> getBusinessPayrollprogramme(String lang) throws IOException {
        List<String> businessOverNightDeposit = new ArrayList<>();
        SSLFix.execute();
        Document document = Jsoup.connect("https://www.anorbank.uz/" + lang + "/business/settlement-service/").get();

        Elements image = document.getElementsByAttributeValue("class", "biznes-cards__img");
        String img = image.get(2).childNodes().get(1).attr("src");
        businessOverNightDeposit.add("https://www.anorbank.uz/" + img.substring(img.indexOf("/"), img.lastIndexOf(".")) + ".png"); //image

        Elements cardInfo = document.getElementsByAttributeValue("class", "biznes-cards__teaser");
        String p = cardInfo.get(2).childNodes().get(3).toString();
        businessOverNightDeposit.add(p.substring(3, p.lastIndexOf("<"))); //<p>


        Elements creditInfoButton = document.getElementsByAttributeValue("class", "biznes-cards__btns");
        String button = creditInfoButton.get(2).childNodes().get(1).toString();

        businessOverNightDeposit.add(button.substring(button.indexOf(">"), button.lastIndexOf("<")).substring(1));
        businessOverNightDeposit.add("https://www.anorbank.uz/" + (lang.equals("") ? "" : lang + "/") + "business/settlement-service/salary-project" + (lang.equals("") ? "" : "-" + lang) + "/");

        return businessOverNightDeposit;
    }

    public static List<String> getBusinessCorporateCards(String lang) throws IOException {
            List<String> businessOverNightDeposit =new ArrayList<>();
            SSLFix.execute();
            Document document = Jsoup.connect("https://www.anorbank.uz/" + lang + "/business/settlement-service/").get();

            Elements image = document.getElementsByAttributeValue("class", "biznes-cards__img");
            String img = image.get(3).childNodes().get(1).attr("src");
            businessOverNightDeposit.add("https://www.anorbank.uz/" + img.substring(img.indexOf("/"), img.lastIndexOf(".")) + ".png"); //image

            Elements cardInfo = document.getElementsByAttributeValue("class", "biznes-cards__teaser");
            String p = cardInfo.get(3).childNodes().get(3).toString();
            businessOverNightDeposit.add(p.substring(3, p.lastIndexOf("<"))); //<p>


            Elements creditInfoButton = document.getElementsByAttributeValue("class", "biznes-cards__btns");
            String button = creditInfoButton.get(3).childNodes().get(1).toString();

            businessOverNightDeposit.add(button.substring(button.indexOf(">"), button.lastIndexOf("<")).substring(1));
            businessOverNightDeposit.add("https://www.anorbank.uz/" + (lang.equals("") ? "" : lang + "/") + "business/settlement-service/corporate-cards"+(lang.equals("") ? "" : "-" + lang)+"/");

            return businessOverNightDeposit;
        }
    }



