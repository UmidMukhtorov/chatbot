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

/**
 * @author Sahobiddin Abbosaliyev
 * 12/7/2021
 */
@Slf4j
@Service
public class CardService {



    /**
     * bo'sh berilsa ruscha qaytaradi
     * en va uz
     *
     * @return List ichida nemelar
     */
    public static List<String> getCardName(String lang) throws IOException {
        List<String> names = new ArrayList<>();
        SSLFix.execute();
        Document document = Jsoup.connect("https://www.anorbank.uz/" + lang + "/cards/").get();
        Elements cards = document.getElementsByAttributeValue("class", "cards__teaser");
        for (Element card : cards) {
            String name= card.childNodes().get(1).toString();
            names.add(name.substring(name.indexOf(">"), name.lastIndexOf("<")).substring(1));
        }
        return names;
    }

    public static List<String> getInstallmentCard(String lang) throws IOException {   //ANOR muddatli to’lov kartasi
        List<String> installmentCard = new ArrayList<>();
        SSLFix.execute();
        Document document = Jsoup.connect("https://www.anorbank.uz/" + lang + "/cards/").get();
        Elements image = document.getElementsByAttributeValue("class", "cards__image card3d");
        String img = image.get(0).childNodes().get(0).toString();
        installmentCard.add("https://www.anorbank.uz/" + img.substring(img.indexOf("/"), img.lastIndexOf(".")) + ".png"); //image
        Elements cardInfo = document.getElementsByAttributeValue("class", "cards__teaser");
        String p = cardInfo.get(0).childNodes().get(2).toString();
        installmentCard.add(p.substring(3, p.lastIndexOf("<"))); //<p>
        Elements ul = document.getElementsByAttributeValue("class", "cards__teaser");
        String li1 = ul.get(0).childNodes().get(4).childNodes().get(1).childNodes().get(2).toString();
        String li2 = ul.get(0).childNodes().get(4).childNodes().get(3).childNodes().get(2).toString();
        String li3 = ul.get(0).childNodes().get(4).childNodes().get(5).childNodes().get(2).toString();
        installmentCard.add(li1.substring(3, li1.lastIndexOf("<")) + "\n" +
                li2.substring(3, li2.lastIndexOf("<")) + "\n" +
                li3.substring(3, li3.lastIndexOf("<")) + "\n"
        ); //cardInfo
        Elements cardInfoButton = document.getElementsByAttributeValue("class", "cards__btns");
        String button = cardInfoButton.get(1).childNodes().toString();

        installmentCard.add(button.substring(button.indexOf(">"), button.lastIndexOf("<")).substring(1));
        installmentCard.add("https://www.anorbank.uz/" + (lang.equals("") ? "" : lang + "/") + "cards/card-installment" + (lang.equals("") ? "" : ("-"+ lang))+ "/" );


        return installmentCard;
    }
        public static List<String> getTriaCard(String lang) throws IOException {   //ANOR muddatli to’lov kartasi
        List<String> installmentCard = new ArrayList<>();
        SSLFix.execute();
        Document document = Jsoup.connect("https://www.anorbank.uz/" + lang + "/cards/").get();
        Elements image = document.getElementsByAttributeValue("class", "cards__image card3d");
        String img = image.get(1).childNodes().get(0).toString();
        installmentCard.add("https://www.anorbank.uz/" + img.substring(img.indexOf("/"), img.lastIndexOf(".")) + ".png"); //image
        Elements cardInfo = document.getElementsByAttributeValue("class", "cards__teaser");
        String p = cardInfo.get(1).childNodes().get(2).toString();
        installmentCard.add(p.substring(3, p.lastIndexOf("<"))); //<p>
        Elements ul = document.getElementsByAttributeValue("class", "cards__teaser");
        String li1 = ul.get(1).childNodes().get(4).childNodes().get(1).childNodes().get(2).toString();
        String li2 = ul.get(1).childNodes().get(4).childNodes().get(3).childNodes().get(2).toString();
        String li3 = ul.get(1).childNodes().get(4).childNodes().get(5).childNodes().get(2).toString();
        installmentCard.add(li1.substring(3, li1.lastIndexOf("<")) + "\n" +
                li2.substring(3, li2.lastIndexOf("<")) + "\n" +
                li3.substring(3, li3.lastIndexOf("<")) + "\n"
        ); //cardInfo
        Elements cardInfoButton = document.getElementsByAttributeValue("class", "cards__btns");
        String button = cardInfoButton.get(1).childNodes().toString();
//        installmentCard.add(button.substring(32,button.lastIndexOf("<")));
//        installmentCard.add(cardInfoButton.get(1).childNodes().toString());
        installmentCard.add(button.substring(button.indexOf(">"), button.lastIndexOf("<")).substring(1));
        installmentCard.add("https://www.anorbank.uz/" + (lang.equals("") ? "" : lang + "/") + "cards/card-tria" + (lang.equals("") ? "" : ("-"+ lang))+ "/" );



        return installmentCard;
    }




//    public static List<String> getMasterCard(String lang) throws IOException {
//        List<String> masterCard = new ArrayList<>();
//        SSLFix.execute();
//        Document document = Jsoup.connect("https://www.anorbank.uz/" + lang + "/cards/").get();
//
//        Elements image = document.getElementsByAttributeValue("class", "cards__image card3d");
//        masterCard.add(image.get(2).toString()); //image
//
//        Elements cardInfo = document.getElementsByAttributeValue("class", "cards__teaser");
//        masterCard.add(cardInfo.get(2).childNodes().get(2).toString()); //<p>
//
//        Elements cardInfoTag = document.getElementsByAttributeValue("class", "cards__teaser");
//
//        String tags = cardInfoTag.get(2).childNodes().get(4).childNodes().get(1).childNodes().get(2).toString() +
//                cardInfoTag.get(2).childNodes().get(4).childNodes().get(3).childNodes().get(2).toString() +
//                cardInfoTag.get(2).childNodes().get(4).childNodes().get(5).childNodes().get(2).toString();
//
//        masterCard.add(tags); //<tag>
//
//        Elements cardInfoButton = document.getElementsByAttributeValue("class", "cards__btns");
//        masterCard.add(cardInfoButton.get(2).childNode(1).toString()); //buttondagi yozuv
//
//        return masterCard;
//    }

    public static List<String> getMasterCard(String lang) throws IOException {   //ANOR muddatli to’lov kartasi
        List<String> installmentCard = new ArrayList<>();
        SSLFix.execute();
        Document document = Jsoup.connect("https://www.anorbank.uz/" + lang + "/cards/").get();
        Elements image = document.getElementsByAttributeValue("class", "cards__image card3d");
        String img = image.get(2).childNodes().get(0).toString();
        installmentCard.add("https://www.anorbank.uz/" + img.substring(img.indexOf("/"), img.lastIndexOf(".")) + ".png"); //image
        Elements cardInfo = document.getElementsByAttributeValue("class", "cards__teaser");
        String p = cardInfo.get(2).childNodes().get(2).toString();
        installmentCard.add(p.substring(3, p.lastIndexOf("<"))); //<p>
        Elements ul = document.getElementsByAttributeValue("class", "cards__teaser");
        String li1 = ul.get(2).childNodes().get(4).childNodes().get(1).childNodes().get(2).toString();
        String li2 = ul.get(2).childNodes().get(4).childNodes().get(3).childNodes().get(2).toString();
        String li3 = ul.get(2).childNodes().get(4).childNodes().get(5).childNodes().get(2).toString();
        installmentCard.add(li1.substring(3, li1.lastIndexOf("<")) + "\n" +
                            li2.substring(3, li2.lastIndexOf("<")) + "\n" +
                            li3.substring(3, li3.lastIndexOf("<")) + "\n"
        ); //cardInfo
        Elements cardInfoButton = document.getElementsByAttributeValue("class", "cards__btns");
        String button = cardInfoButton.get(1).childNodes().toString();
        installmentCard.add(button.substring(button.indexOf(">"), button.lastIndexOf("<")).substring(1));
        installmentCard.add("https://www.anorbank.uz/" + (lang.equals("") ? "" : lang + "/") + "cards/mastercard-humo" + (lang.equals("") ? "" : ("-"+ lang))+ "/" );


        return installmentCard;
    }

//    public List<String> (String lang) throws IOException {
//        List<String> debetCard = new ArrayList<>();
//        SSLFix.execute();
//        Document document = Jsoup.connect("https://www.anorbank.uz/" + lang + "/cards/").get();
//
//        Elements image = document.getElementsByAttributeValue("class", "cards__image card3d");
//        debetCard.add(image.get(3).toString()); //image
//
//        Elements cardInfo = document.getElementsByAttributeValue("class", "cards__teaser");
//        debetCard.add(cardInfo.get(3).childNodes().get(2).toString()); //<p>
//
//        Elements cardInfoTag = document.getElementsByAttributeValue("class", "cards__teaser");
//
//        String tags = cardInfoTag.get(3).childNodes().get(4).childNodes().get(1).childNodes().get(2).toString() +
//                cardInfoTag.get(3).childNodes().get(4).childNodes().get(3).childNodes().get(2).toString() +
//                cardInfoTag.get(3).childNodes().get(4).childNodes().get(5).childNodes().get(2).toString();
//
//        debetCard.add(tags); //<tag>
//
//        Elements cardInfoButton = document.getElementsByAttributeValue("class", "cards__btns");
//        debetCard.add(cardInfoButton.get(3).childNode(1).toString()); //buttondagi yozuv
//
//
//        return debetCard;
//    }

    public static List<String> getDebetCard(String lang) throws IOException {   //ANOR muddatli to’lov kartasi
        List<String> installmentCard = new ArrayList<>();
        SSLFix.execute();
        Document document = Jsoup.connect("https://www.anorbank.uz/" + lang + "/cards/").get();
        Elements image = document.getElementsByAttributeValue("class", "cards__image card3d");
        String img = image.get(3).childNodes().get(0).toString();
        installmentCard.add("https://www.anorbank.uz/" + img.substring(img.indexOf("/"), img.lastIndexOf(".")) + ".png"); //image
        Elements cardInfo = document.getElementsByAttributeValue("class", "cards__teaser");
        String p = cardInfo.get(3).childNodes().get(2).toString();
        installmentCard.add(p.substring(3, p.lastIndexOf("<"))); //<p>
        Elements ul = document.getElementsByAttributeValue("class", "cards__teaser");
        String li1 = ul.get(3).childNodes().get(4).childNodes().get(1).childNodes().get(2).toString();
        String li2 = ul.get(3).childNodes().get(4).childNodes().get(3).childNodes().get(2).toString();
        String li3 = ul.get(3).childNodes().get(4).childNodes().get(5).childNodes().get(2).toString();
        installmentCard.add(li1.substring(3, li1.lastIndexOf("<")) + "\n" +
                li2.substring(3, li2.lastIndexOf("<")) + "\n" +
                li3.substring(3, li3.lastIndexOf("<")) + "\n"
        ); //cardInfo
        Elements cardInfoButton = document.getElementsByAttributeValue("class", "cards__btns");
        log.info("Uzcard:{}",cardInfoButton);
        String button = cardInfoButton.get(3).childNodes().toString();
        installmentCard.add(button.substring(button.indexOf(">"), button.lastIndexOf("<")).substring(1));
        installmentCard.add("https://www.anorbank.uz/" + (lang.equals("") ? "" : lang + "/") + "cards/");
        return installmentCard;
    }


}