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
public class DepositService {


    public static List<String> getDepositName(String lang) throws IOException {
        List<String> names =new ArrayList<>();
        SSLFix.execute();
        Document document = Jsoup.connect("https://www.anorbank.uz/" + lang + "/deposit/").get();
        Elements deposits = document.getElementsByAttributeValue("class", "cards__teaser");
        for (Element deposit : deposits) {
            String dep= deposit.childNodes().get(1).toString();
            names.add(dep.substring(dep.indexOf(">"), dep.lastIndexOf("<")).substring(1));
        }
        return names;
    }

    public static List<String> getSmartDeposit(String lang) throws IOException {
        List<String> smartDeposit =new ArrayList<>();
        SSLFix.execute();
        Document document = Jsoup.connect("https://www.anorbank.uz/" + lang + "/deposit/").get();

        Elements image = document.getElementsByAttributeValue("class", "cards__image");
        String img = image.get(0).childNodes().get(3).toString();
        smartDeposit.add("https://www.anorbank.uz/" + img.substring(img.indexOf("/"), img.lastIndexOf(".")) + ".png"); //image

        Elements depositInfo = document.getElementsByAttributeValue("class", "cards__teaser");
        String p = depositInfo.get(0).childNodes().get(3).toString();
        smartDeposit.add(p.substring(3, p.lastIndexOf("<"))); //<p>

        Elements ul = document.getElementsByAttributeValue("class", "cards__teaser");
        String li1 = ul.get(0).childNodes().get(5).childNodes().get(1).childNodes().get(3).toString();
        String li2 = ul.get(0).childNodes().get(5).childNodes().get(3).childNodes().get(3).toString();
        String li3 = ul.get(0).childNodes().get(5).childNodes().get(5).childNodes().get(3).toString();
        smartDeposit.add(li1.substring(3, li1.lastIndexOf("<")) + "\n" +
                li2.substring(3, li2.lastIndexOf("<")) + "\n" +
                li3.substring(3, li3.lastIndexOf("<")) + "\n"
        ); //DepositInfo

        Elements depositInfoButton = document.getElementsByAttributeValue("class", "cards__btns");
        String button = depositInfoButton.get(0).childNodes().get(1).toString();

        smartDeposit.add(button.substring(button.indexOf(">"), button.lastIndexOf("<")).substring(1));
        smartDeposit.add("https://www.anorbank.uz/" + (lang.equals("") ? "" : lang + "/") + "deposit/vklad-smart-2-0"+(lang.equals("") ? "" : "-" + lang)+"/");

        return smartDeposit;
    }

    public static List<String> getShoshilmasdanDeposit(String lang) throws IOException {
        List<String> shoshilmasdanDeposit =new ArrayList<>();
        SSLFix.execute();
        Document document = Jsoup.connect("https://www.anorbank.uz/" + lang + "/deposit/").get();

        Elements image = document.getElementsByAttributeValue("class", "cards__image");
        String img = image.get(1).childNodes().get(3).toString();
        shoshilmasdanDeposit.add("https://www.anorbank.uz/" + img.substring(img.indexOf("/"), img.lastIndexOf(".")) + ".png"); //image

        Elements depositInfo = document.getElementsByAttributeValue("class", "cards__teaser");
        String p = depositInfo.get(1).childNodes().get(3).toString();
        shoshilmasdanDeposit.add(p.substring(3, p.lastIndexOf("<"))); //<p>

        Elements ul = document.getElementsByAttributeValue("class", "cards__teaser");
        String li1 = ul.get(1).childNodes().get(5).childNodes().get(1).childNodes().get(3).toString();
        String li2 = ul.get(1).childNodes().get(5).childNodes().get(3).childNodes().get(3).toString();
        String li3 = ul.get(1).childNodes().get(5).childNodes().get(5).childNodes().get(3).toString();
        shoshilmasdanDeposit.add(li1.substring(3, li1.lastIndexOf("<")) + "\n" +
                li2.substring(3, li2.lastIndexOf("<")) + "\n" +
                li3.substring(3, li3.lastIndexOf("<")) + "\n"
        ); //DepositInfo

        Elements depositInfoButton = document.getElementsByAttributeValue("class", "cards__btns");
        String button = depositInfoButton.get(1).childNodes().get(1).toString();

        shoshilmasdanDeposit.add(button.substring(button.indexOf(">"), button.lastIndexOf("<")).substring(1));
        shoshilmasdanDeposit.add("https://www.anorbank.uz/" + (lang.equals("") ? "" : lang + "/") + "deposit/vklad-shoshilmasdan-2-0"+(lang.equals("") ? "" : "-" + lang)+"/");

        return shoshilmasdanDeposit;
    }


    public static List<String> getGoDeposit(String lang) throws IOException {
        List<String> goDeposit =new ArrayList<>();
        SSLFix.execute();
        Document document = Jsoup.connect("https://www.anorbank.uz/" + lang + "/deposit/").get();

        Elements image = document.getElementsByAttributeValue("class", "cards__image");
        String img = image.get(3).childNodes().get(3).toString();
        goDeposit.add("https://www.anorbank.uz/" + img.substring(img.indexOf("/"), img.lastIndexOf(".")) + ".png"); //image

        Elements depositInfo = document.getElementsByAttributeValue("class", "cards__teaser");
        String p = depositInfo.get(3).childNodes().get(3).toString();
        goDeposit.add(p.substring(3, p.lastIndexOf("<"))); //<p>

        Elements ul = document.getElementsByAttributeValue("class", "cards__teaser");
        String li1 = ul.get(3).childNodes().get(5).childNodes().get(1).childNodes().get(3).toString();
        String li2 = ul.get(3).childNodes().get(5).childNodes().get(3).childNodes().get(3).toString();
        String li3 = ul.get(3).childNodes().get(5).childNodes().get(5).childNodes().get(3).toString();
        goDeposit.add(li1.substring(3, li1.lastIndexOf("<")) + "\n" +
                li2.substring(3, li2.lastIndexOf("<")) + "\n" +
                li3.substring(3, li3.lastIndexOf("<")) + "\n"
        ); //DepositInfo

        Elements depositInfoButton = document.getElementsByAttributeValue("class", "cards__btns");
        String button = depositInfoButton.get(3).childNodes().get(1).toString();

        goDeposit.add(button.substring(button.indexOf(">"), button.lastIndexOf("<")).substring(1));
        goDeposit.add("https://www.anorbank.uz/" + (lang.equals("") ? "" : lang + "/") + "deposit/go-deposit"+(lang.equals("") ? "" : "-" + lang)+"/");

        return goDeposit;
    }



}

