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
public class AboutVacanciesService {

    public static List<String> getAboutVacancies(String lang) throws IOException {
        List<String> aboutVacancies =new ArrayList<>();
        SSLFix.execute();
        Document document = Jsoup.connect("https://www.anorbank.uz/" + lang + "/about/vacancies/").get();
        aboutVacancies.add("https://www.anorbank.uz/upload/medialibrary/31b/31b62a6a646d88be68547dcafcec9a3b.jpg"); //image

        Elements anorBankVacancies = document.getElementsByAttributeValue("class", "col-12 col-md-9 pages__col pages__col--content");
        String p = anorBankVacancies.get(0).childNodes().get(1).toString();
        aboutVacancies.add(p.substring(4, p.lastIndexOf("<"))); //<h3>

        if (lang.equals("uz")){
            aboutVacancies.add("Egaligingizni tasdiqlang qo'shimcha imkoniyatlar uchun hr@anorbank.uz\n");
            aboutVacancies.add("Batafsil");
        }else if (lang.equals("")){
            aboutVacancies.add("Отправьте свое резюме в любом формате https://t.me/anorbank_hr в телеграмм или на почту hr@anorbank.uz\n");
            aboutVacancies.add("Подробнее");
        }else if (lang.equals("en")){
            aboutVacancies.add("Send your resume in any format @ankorbank_rk in a telegram or by mail hr@anorbank.uz\n" );//p1
            aboutVacancies.add("More");//buttonText
        }


        aboutVacancies.add("https://www.anorbank.uz/" + (lang.equals("") ? "" : lang + "/") + "about/vacancies/"+(lang.equals("") ? "" : "-" + lang)+"/");//buttonURL
        return aboutVacancies;
    }
    public static List<String> getCommercialProposal(String lang) throws IOException {
        List<String> aboutVacancies =new ArrayList<>();
        SSLFix.execute();
        aboutVacancies.add("https://www.anorbank.uz/upload/medialibrary/31b/31b62a6a646d88be68547dcafcec9a3b.jpg"); //image
        if (lang.equals("uz")){
            aboutVacancies.add("Tijorat taklifingizni har qanday shaklda elektron pochta manziliga yuboring info@anorbank.uz\n");

        }else if (lang.equals("en")){
            aboutVacancies.add("Send your commercial offer in any format to the email address info@anorbank.uz\n" );//p1

        }else
            aboutVacancies.add("Отправьте свое коммерческое предложение в любом формате на электронный адрес info@anorbank.uz\n");

        return aboutVacancies;
    }

}
