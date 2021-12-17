package uz.anorbank.anorbankchatbot.service;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import uz.anorbank.anorbankchatbot.config.SSLFix;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class CreditService{



    public static List<String> getCreditsName(String lang) throws IOException {
        List<String> names =new ArrayList<>();
        SSLFix.execute();
        Document document = Jsoup.connect("https://www.anorbank.uz/" + lang + "/credits/").get();
        Elements deposits = document.getElementsByAttributeValue("class", "cards__teaser");
        for (Element deposit : deposits) {
            String name= deposit.childNodes().get(1).toString();
            names.add(name.substring(name.indexOf(">"), name.lastIndexOf("<")).substring(1));
        }
        return names;
    }

    public static List<String> getMicroloanCredit(String lang) throws IOException {
        List<String> microloanCredit =new ArrayList<>();
        SSLFix.execute();
        Document document = Jsoup.connect("https://www.anorbank.uz/" + lang + "/credits/").get();

        Elements image = document.getElementsByAttributeValue("class", "cards__image");
        String img = image.get(0).childNodes().get(3).toString();
        microloanCredit.add("https://www.anorbank.uz/" + img.substring(img.indexOf("/"), img.lastIndexOf(".")) + ".png"); //image

        Elements cardInfo = document.getElementsByAttributeValue("class", "cards__teaser");
        String p = cardInfo.get(0).childNodes().get(3).toString();
        microloanCredit.add(p.substring(3, p.lastIndexOf("<"))); //<p>


        Elements ul = document.getElementsByAttributeValue("class", "cards__teaser");
        String li1 = ul.get(0).childNodes().get(5).childNodes().get(1).childNodes().get(3).toString();
        String li2 = ul.get(0).childNodes().get(5).childNodes().get(3).childNodes().get(3).toString();
        String li3 = ul.get(0).childNodes().get(5).childNodes().get(5).childNodes().get(3).toString();
        microloanCredit.add(li1.substring(3, li1.lastIndexOf("<")) + "\n" +
                li2.substring(3, li2.lastIndexOf("<")) + "\n" +
                li3.substring(3, li3.lastIndexOf("<")) + "\n"
        ); //creditInfo

        Elements creditInfoButton = document.getElementsByAttributeValue("class", "cards__btns");
        String button = creditInfoButton.get(0).childNodes().get(1).toString();

        microloanCredit.add(button.substring(button.indexOf(">"), button.lastIndexOf("<")).substring(1));
        microloanCredit.add("https://www.anorbank.uz/" + (lang.equals("") ? "" : lang + "/") + "credits/microloan"+(lang.equals("") ? "" : "-" + lang)+"/");

        return microloanCredit;
    }


    public static List<String> getAvailableCredit(String lang) throws IOException {
        List<String> availableCredit =new ArrayList<>();
        SSLFix.execute();
        Document document = Jsoup.connect("https://www.anorbank.uz/" + lang + "/credits/").get();

        Elements image = document.getElementsByAttributeValue("class", "cards__image");
        String img = image.get(1).childNodes().get(3).toString();
        availableCredit.add("https://www.anorbank.uz/" + img.substring(img.indexOf("/"), img.lastIndexOf(".")) + ".png"); //image

        Elements cardInfo = document.getElementsByAttributeValue("class", "cards__teaser");
        String p = cardInfo.get(1).childNodes().get(3).toString();
        availableCredit.add(p.substring(3, p.lastIndexOf("<"))); //<p>


        Elements ul = document.getElementsByAttributeValue("class", "cards__teaser");
        String li1 = ul.get(1).childNodes().get(5).childNodes().get(1).childNodes().get(3).toString();
        String li2 = ul.get(1).childNodes().get(5).childNodes().get(3).childNodes().get(3).toString();
        String li3 = ul.get(1).childNodes().get(5).childNodes().get(5).childNodes().get(3).toString();
        availableCredit.add(li1.substring(3, li1.lastIndexOf("<")) + "\n" +
                li2.substring(3, li2.lastIndexOf("<")) + "\n" +
                li3.substring(3, li3.lastIndexOf("<")) + "\n"
        ); //creditInfo

        Elements creditInfoButton = document.getElementsByAttributeValue("class", "cards__btns");
        String button = creditInfoButton.get(1).childNodes().get(1).toString();

        availableCredit.add(button.substring(button.indexOf(">"), button.lastIndexOf("<")).substring(1));
        availableCredit.add("https://www.anorbank.uz/" + (lang.equals("") ? "" : lang + "/") + "credits/available-money"+(lang.equals("") ? "" : "-" + lang)+"/");


        return availableCredit;
    }

    public static List<String> getInstallmentsCredit(String lang) throws IOException {
        List<String> installmentsCredit =new ArrayList<>();
        SSLFix.execute();
        Document document = Jsoup.connect("https://www.anorbank.uz/" + lang + "/credits/").get();

        Elements image = document.getElementsByAttributeValue("class", "cards__image");
        String img = image.get(2).childNodes().get(3).toString();
        installmentsCredit.add("https://www.anorbank.uz/" + img.substring(img.indexOf("/"), img.lastIndexOf(".")) + ".png"); //image

        Elements cardInfo = document.getElementsByAttributeValue("class", "cards__teaser");
        String p = cardInfo.get(2).childNodes().get(3).toString();
        installmentsCredit.add(p.substring(3, p.lastIndexOf("<"))); //<p>


        Elements ul = document.getElementsByAttributeValue("class", "cards__teaser");
        String li1 = ul.get(2).childNodes().get(5).childNodes().get(1).childNodes().get(3).toString();
        String li2 = ul.get(2).childNodes().get(5).childNodes().get(3).childNodes().get(3).toString();
        String li3 = ul.get(2).childNodes().get(5).childNodes().get(5).childNodes().get(3).toString();
        installmentsCredit.add(li1.substring(3, li1.lastIndexOf("<")) + "\n" +
                li2.substring(3, li2.lastIndexOf("<")) + "\n" +
                li3.substring(3, li3.lastIndexOf("<")) + "\n"
        ); //creditInfo

        Elements creditInfoButton = document.getElementsByAttributeValue("class", "cards__btns");
        String button = creditInfoButton.get(2).childNodes().get(1).toString();

        installmentsCredit.add(button.substring(button.indexOf(">"), button.lastIndexOf("<")).substring(1));
        installmentsCredit.add("https://www.anorbank.uz/" + (lang.equals("") ? "" : lang + "/") + "credits/card-installment"+(lang.equals("") ? "" : "-" + lang)+"/");



        return installmentsCredit;
    }

    public static List<String> getOverdraftCredit(String lang) throws IOException {
        List<String> overdraftCredit =new ArrayList<>();
        SSLFix.execute();
        Document document = Jsoup.connect("https://www.anorbank.uz/" + lang + "/credits/").get();

        Elements image = document.getElementsByAttributeValue("class", "cards__image");
        String img = image.get(3).childNodes().get(3).toString();
        overdraftCredit.add("https://www.anorbank.uz/" + img.substring(img.indexOf("/"), img.lastIndexOf(".")) + ".png"); //image

        Elements cardInfo = document.getElementsByAttributeValue("class", "cards__teaser");
        String p = cardInfo.get(3).childNodes().get(3).toString();
        overdraftCredit.add(p.substring(3, p.lastIndexOf("<"))); //<p>


        Elements ul = document.getElementsByAttributeValue("class", "cards__teaser");
        String li1 = ul.get(3).childNodes().get(5).childNodes().get(1).childNodes().get(3).toString();
        String li2 = ul.get(3).childNodes().get(5).childNodes().get(3).childNodes().get(3).toString();
        String li3 = ul.get(3).childNodes().get(5).childNodes().get(5).childNodes().get(3).toString();
        overdraftCredit.add(li1.substring(3, li1.lastIndexOf("<")) + "\n" +
                li2.substring(3, li2.lastIndexOf("<")) + "\n" +
                li3.substring(3, li3.lastIndexOf("<")) + "\n"
        ); //creditInfo

        Elements creditInfoButton = document.getElementsByAttributeValue("class", "cards__btns");
        String button = creditInfoButton.get(3).childNodes().get(1).toString();

        overdraftCredit.add(button.substring(button.indexOf(">"), button.lastIndexOf("<")).substring(1));
        overdraftCredit.add("https://www.anorbank.uz/" + (lang.equals("") ? "" : lang + "/") + "credits/card-tria"+(lang.equals("") ? "" : "-" + lang)+"/");


        return overdraftCredit;
    }
}