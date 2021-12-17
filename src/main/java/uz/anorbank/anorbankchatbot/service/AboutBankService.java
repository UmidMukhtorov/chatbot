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
public class AboutBankService {
    public static List<String> getAddressOfficeBank(String lang) throws IOException {

        List<String> aboutAnorBank = new ArrayList<>();
        SSLFix.execute();
        Document document = Jsoup.connect("https://www.anorbank.uz/" + lang + "/about/contacts/").get();
        aboutAnorBank.add("https://www.anorbank.uz/upload/medialibrary/31b/31b62a6a646d88be68547dcafcec9a3b.jpg"); //image

        Elements bankInfo1 = document.getElementsByAttributeValue("class", "col-12 col-md-5 contacts__col contacts__col--teaser");
        String p = bankInfo1.get(0).childNodes().get(5).childNodes().get(3).childNodes().get(0).childNodes().get(0).toString();
        aboutAnorBank.add(p); //<p>

        Elements addressInfoButton = document.getElementsByAttributeValue("class", "col-12 main-title__col");
        String button = addressInfoButton.get(0).childNodes().toString();
        aboutAnorBank.add(button.substring(button.indexOf(">"), button.lastIndexOf("<")).substring(1));
        aboutAnorBank.add("https://www.anorbank.uz/" + (lang.equals("") ? "" : lang + "/") + "about/contacts/");


        return aboutAnorBank;
    }

    public static List<String> getAndijan(String lang) throws IOException {

        List<String> getAndijan = new ArrayList<>();
        SSLFix.execute();
        getAndijan.add("https://www.anorbank.uz/upload/img/punkt/g_%D0%90ndijan.jpg");
        if (lang.equals("uz")) {
            getAndijan.add("\uD83C\uDFE0Andijon shahri, Alisher Navoiy shoh ko'chasi, 34-uy\n" +
                    "Or-r: Andijon shahar soliq inspeksiyasi.");
            getAndijan.add("Joylashuv:");
        } else if (lang.equals("en")) {

           getAndijan.add("Andijan (Andijan city)\n" +
                   "\n" +
                   "\uD83C\uDFE0Point address:\n" +
                   "Andijan city, Alisher Navoi avenue, building 34\n" +
                   "Orr: Tax Inspectorate of Andijan. (City State Tax Inspectorate)");
            getAndijan.add("Location:");
        } else if (lang.equals("")) {
            getAndijan.add("\uD83C\uDFE0Адрес опорного пункта:\n" +
                    "Город Андижан, проспект Алишера Навои, дом – 34\n" +
                    "Ор-р: Налоговая инспекция г.Андижан. (Городская ГНИ)");
            getAndijan.add("Геолокация:");
        }
        getAndijan.add("https://yandex.uz/maps/-/CCUmfJXm~C");
        return getAndijan;
    }
  public static List<String> getAngren(String lang) throws IOException {
        List<String> getAngren = new ArrayList<>();
        SSLFix.execute();
        getAngren.add("https://www.anorbank.uz/upload/img/punkt/g_%D0%90ngren.jpg");
      if (lang.equals("uz")) {
          getAngren.add("\uD83C\uDFE0Angren, may kimegar, 5/3 daha, Ohangaron ko'chasi \n" +
                  "\"Mo'ljal: \"Shohdier\"Restorani");
          getAngren.add("Joylashuv:");
      } else if (lang.equals("en")) {
          getAngren.add("\uD83C\uDFE0Point address:\n" +
                  "Angren, MFY Kimyogar, 5/3 dakha, Akhangaran str.\n" +
                  "Object orient: Restaurant \"Shokhdiyor\"");
          getAngren.add("Location:");
      } else if (lang.equals("")){
        getAngren.add("\uD83C\uDFE0Адрес опорного пункта:\n" +
                "Ангрен, МФЙ Кимёгар, 5/3 даха, ул.Ахангаранская \n" +
                "Ориентир: Ресторан \"Шохдиёр\"");
        getAngren.add("Геолокация:");
      }
        getAngren.add("https://yandex.uz/maps/-/CCUqMXG5-A");
        return getAngren;
    }
    public static List<String> getBuxara(String lang) throws IOException {
        List<String> getBuxara = new ArrayList<>();
        SSLFix.execute();
        getBuxara.add("https://www.anorbank.uz/upload/img/punkt/g_Buxara.jpg");
        if (lang.equals("uz")) {
            getBuxara.add("\uD83C\uDFE0Buxoro sh. " +
                    "Fayziobod ko'chasi, 4/3-uy, 3-sonli savdo binosi." +
                    "Mo'ljal:EuroSharga qarama-qarshi,Sulton PLOV");
            getBuxara.add("Joylashuv:");
        } else if (lang.equals("en")) {
            getBuxara.add("\uD83C\uDFE0Point address:\n" +
                    "Bukhara, st. Fayzobod, house 4/3, commercial premises No. 3.\n" +
                    "Object orient: Opposite Euro Shar, Sultan PLOV.");
            getBuxara.add("Location:");
        } else if (lang.equals("")) {
            getBuxara.add("\uD83C\uDFE0Адрес опорного пункта:\n" +
                    "г.Бухара, ул. Файзобод, дом 4/3, торговое помещение № 3.\n" +
                    "Ориентир: Напротив Euro Shar, Sultan PLOV.");
            getBuxara.add("Геолокация:");
        }
        getBuxara.add("https://yandex.uz/maps/-/CCUmbDArGB");
        return getBuxara;
    }
    public static List<String> getJizzax(String lang) throws IOException {
        List<String> getJizzax = new ArrayList<>();
        SSLFix.execute();
        getJizzax.add("https://www.anorbank.uz/upload/img/punkt/g_Djizzak.jpg");
        if (lang.equals("uz")) {
            getJizzax.add("\uD83C\uDFE0Jizzax viloyati, Jizzax shahri, Qutarma may, A. Navoiy ko'chasi, 3 A uy");
            getJizzax.add("Joylashuv:");
        } else if (lang.equals("en")) {
            getJizzax.add("\uD83C\uDFE0Point address:\n" +
                    "Jizzakh region, city of Jizzakh, MFY Kutarma, street A. Navoiy building 3A.");
            getJizzax.add("Location:");
        } else if (lang.equals("")) {
            getJizzax.add("\uD83C\uDFE0Адрес опорного пункта:\n" +
                    "Джизакская область, город Джиззак, МФЙ Кутарма, улица А.Навоий дом 3А.");
            getJizzax.add("Геолокация:");
        }
        getJizzax.add("https://yandex.uz/maps/-/CCUmfNg8HC");
        return getJizzax;
    }
    public static List<String> getKarshi(String lang) throws IOException {
        List<String> getKarshi = new ArrayList<>();
        SSLFix.execute();
        getKarshi.add("https://www.anorbank.uz/upload/img/punkt/g_Karshi.jpg");
        if (lang.equals("uz")) {
            getKarshi.add("\uD83C\uDFE0Qarshi sh. Boxodir Sherkulov ko'chasi, 226\n" +
                    "Mo'ljal: \"Sanam\" zavodi va Abdulla Oripov nomidagi maktab");
            getKarshi.add("Joylashuv:");

        } else if (lang.equals("en")) {
            getKarshi.add("\uD83C\uDFE0Point address:\n" +
                    "Karshi, st. Boxodir Sherqulov, 226\n" +
                    "Object orient: Factory \"Sanam\" and School named after Abdulla Aripov");
            getKarshi.add("Location:");
        } else if (lang.equals("")) {
            getKarshi.add("\uD83C\uDFE0Адрес опорного пункта:\n" +
                    "г.Карши, ул. Boxodir Sherqulov, 226\n" +
                    "Ориентир: Фабрика \"Sanam\" и Школа имени Абдуллы Арипова");
            getKarshi.add("Геолокация:");
        }
        getKarshi.add("https://yandex.uz/maps/-/CCUqJCAr~B");
        return getKarshi;
    }
    public static List<String> getNamangan(String lang) throws IOException {
        List<String> getNamangan = new ArrayList<>();
        SSLFix.execute();
        getNamangan.add("https://www.anorbank.uz/upload/img/punkt/g_namangan2.jpg");
        if (lang.equals("uz")) {
            getNamangan.add("\uD83C\uDFE0Namangan Shahri. Xalqlar do'stligi k. 4-mikrorayon, uy-28A, 1 - qavat\n" +
                    "\"Or-r: Medusa kafe.");
            getNamangan.add("Joylashuv:");
        } else if (lang.equals("en")) {
            getNamangan.add("\uD83C\uDFE0Point address:\n" +
                    "Namangan city. Khalklar Dustligi MFY street 4- microdistrict, building - 28A, 1st floor\n" +
                    "Orr: Cafe Meduza.");
            getNamangan.add("Location:");
        } else if (lang.equals("")) {
            getNamangan.add("\uD83C\uDFE0Адрес опорного пункта:\n" +
                    "Город Наманган. Ул.Халклар Дустлиги МФЙ 4- микрорайон, дом - 28А, 1 этаж\n" +
                    "Ор-р: кафе Медуза.");
            getNamangan.add("Геолокация:");
        }
        getNamangan.add("https://yandex.uz/maps/-/CCUmfJWpSC");
        return getNamangan;
    }
    public static List<String> getNavai(String lang) throws IOException {

        List<String> getNavai = new ArrayList<>();
        SSLFix.execute();
        getNavai.add("https://www.anorbank.uz/upload/img/punkt/g_Navoi.jpg");
        if (lang.equals("uz")) {
            getNavai.add("\uD83C\uDFE0Navoiy sh. 10 - mikrorayon,  A. Navoiy ko'chasi, 38A uy.\n" +
                    "\"Mo'ljal:\" Shirin \" Madaniyat uyi, avtosalonning yonida.");
            getNavai.add("Joylashuv:");

        } else if (lang.equals("en")) {
            getNavai.add("\uD83C\uDFE0Point address:\n" +
                    "Navoi, 10 microdistrict, MFY Vatan, st. A.Navoi, building 38A. (14 Office)\n" +
                    "Landmark: House of Culture \"Shirin\", Next to the Autosalon");
            getNavai.add("Location:");
        } else if (lang.equals("")) {
            getNavai.add("\uD83C\uDFE0Адрес опорного пункта:\n" +
                    "г.Навои, 10 микрорайон, МФЙ Ватан, ул. А.Навои, дом 38A. (14 Офис)\n" +
                    "Ориентир: Дом Культуры \"Ширин\", Рядом с Автосалоном.");
            getNavai.add("Геолокация:");
        }
        getNavai.add("https://yandex.uz/maps/-/CCUqESf00D");
        return getNavai;
    }
    public static List<String> getKarakalpak(String lang) throws IOException {

        List<String> getKarakalpak = new ArrayList<>();
        SSLFix.execute();
        getKarakalpak.add("https://www.anorbank.uz/upload/img/punkt/g_Nukus.jpg");
        if (lang.equals("uz")) {
            getKarakalpak.add("\uD83C\uDFE0Nukus shahri, To'lepbergen Kaipbergenov ko'chasi, 59-uy. \n" +
                    "\"Mo'ljal: Bojxona ma'muriyatining qarshisidagi uy");
            getKarakalpak.add("Joylashuv:");

        } else if (lang.equals("en")) {
            getKarakalpak.add("\uD83C\uDFE0Point address:\n" +
                    "Nukus, st. Tolepbergen Kaipbergenov, 59. (Corner office)\n" +
                    "Object orient: House opposite the customs office.");
            getKarakalpak.add("Location:");
        } else if (lang.equals("")) {
            getKarakalpak.add("\uD83C\uDFE0Адрес опорного пункта:\n" +
                    "г.Нукус, ул. Толепберген Каипбергенов, дом 59. (Угловой офис)\n" +
                    "Ориентир: Дом напротив таможенного управления.");
            getKarakalpak.add("Геолокация:");
        }
        getKarakalpak.add("https://yandex.uz/maps/-/CCUqMPrvGA");
        return getKarakalpak;
    }
    public static List<String> getSamarkand(String lang) throws IOException {

        List<String> getSamarkand = new ArrayList<>();
        SSLFix.execute();
        getSamarkand.add("https://www.anorbank.uz/upload/img/punkt/g_Samarkand.jpg");
        if (lang.equals("uz")) {
            getSamarkand.add("\uD83C\uDFE0Samarqand viloyati, Samarqand shahri, Buyuk Ipak yo'li ko'chasi, 90-uy\n" +
                    "Mo'ljal: Atlas savdo markazidan yo'l bo'ylab");
            getSamarkand.add("Joylashuv:");

        } else if (lang.equals("en")) {
            getSamarkand.add("\uD83C\uDFE0Point address:\n" +
                    "Samarkand region, Samarkand, st. Buyuk Ipak Yuli, house 90\n" +
                    "(Reference point: Across the street from the Shopping Center - Atlas)");
            getSamarkand.add("Location:");
        } else if (lang.equals("")) {
            getSamarkand.add("\uD83C\uDFE0Адрес опорного пункта:\n" +
                    "Самаркандская область, г.Самарканд, ул. Буюк Ипак Йули, дом 90\n" +
                    "(Ориентир: Через дорогу от Tоргового центра - Атлас)");
            getSamarkand.add("Геолокация:");
        }
        getSamarkand.add("https://yandex.uz/maps/-/CCUqEAq0oC");
        return getSamarkand;
    }
    public static List<String> getSirdarya(String lang) throws IOException {

        List<String> getSirdarya = new ArrayList<>();
        SSLFix.execute();
        getSirdarya.add("https://www.anorbank.uz/upload/img/punkt/g_Gulistan.jpg");
        if (lang.equals("uz")) {
            getSirdarya.add("\uD83C\uDFE0Sirdaryo viloyati, Guliston shahri, shifokorlar ko'chasi, 2-uy\n" +
                    "Mo'ljal: 5 maktab, Sirdaryo teleradiokompaniyasi");
            getSirdarya.add("Joylashuv:");
        } else if (lang.equals("en")) {
            getSirdarya.add("\uD83C\uDFE0Point address:\n" +
                    "Syrdarya region, Gulistan city, Shifokorlar street, building 2\n" +
                    "Landmark: 5 school, President of the Halk Kabulhonashi, Sirdaryo TV and radio company");
            getSirdarya.add("Location:");
        } else if (lang.equals("")) {
            getSirdarya.add("Сырдарья (г.Гулистан)\n" +
                    "\uD83C\uDFE0Адрес опорного пункта:\n" +
                    "Сырдарьинская область, г.Гулистан, улица Шифокорлар, дом 2\n" +
                    "Ориентир: 5 школа, Президент Халк кабулхонаси, Сирдарё теле-радиокомпания");
            getSirdarya.add("Геолокация:");
        }
        getSirdarya.add("https://yandex.uz/maps/-/CCUqEOcQ-A");
        return getSirdarya;
    }
    public static List<String> getTermiz(String lang) throws IOException {

        List<String> getTermiz = new ArrayList<>();
        SSLFix.execute();
        getTermiz.add("https://www.anorbank.uz/upload/img/punkt/g_Termez.jpg");
        if (lang.equals("uz")) {
            getTermiz.add("\uD83C\uDFE0Surxondaryo viloyati, Termiz shahri, shukron ko'chasi, 2 - uy, (1-ofis) (eski nomi-Abdulla Qahhor ko'chasi)\n" +
                    "Mo'ljal: \"Rohat\" kafe bar, Yubiley filiali yonida");
            getTermiz.add("Joylashuv:");
        } else if (lang.equals("en")) {
            getTermiz.add("\uD83C\uDFE0Point address: \n" +
                    "Surkhandarya region, Termez, st. Shukrona, building 2, (1 Office) (Old name of the street - Abdullah Kakhhor)\n" +
                    "Landmark: \"Rohat\" cafe bar, Next to the Jubilee branch.");
            getTermiz.add("Location:");
        } else if (lang.equals("")) {
            getTermiz.add("\uD83C\uDFE0Адрес опорного пункта:  Сурхандарьинская область, г. Термез, ул. Шукрона, дом 2, (1 Офис) (Старое название улица - Абдулла Каххор)\n" +
                    "Ориентир: «Рохат» кафе бар, Рядом с Юбилейный филиалом.");
            getTermiz.add("Геолокация:");
        }
        getTermiz.add("https://yandex.uz/maps/-/CCUqUUAcSC");
        return getTermiz;
    }
    public static List<String> getUrgench(String lang) throws IOException {

        List<String> getUrgench = new ArrayList<>();
        SSLFix.execute();
        getUrgench.add("https://www.anorbank.uz/upload/img/punkt/g_Urgench.jpg");
        if (lang.equals("uz")) {
            getUrgench.add("\uD83C\uDFE0Xorazm viloyati, Urganch shahri, Tinchlik ko'chasi, 31/2 uy.\n" +
                    "Mo'ljal: shahar hokimligining qarshisida \"GUMBAZ\"kafesi");
            getUrgench.add("Joylashuv:");

        } else if (lang.equals("en")) {
            getUrgench.add("\uD83C\uDFE0Point address:\n" +
                    "Khorezm region, Urgench city, Tinchlik street, house 31/2.\n" +
                    "Object orient: Opposite the City Khokimiyat, Cafe \"GUMBAZ\"");
            getUrgench.add("Location:");
        } else if (lang.equals("")) {
            getUrgench.add("\uD83C\uDFE0Адрес опорного пункта:\n" +
                    "Хорезмская область, город Ургенч, улица Тинчлик, дом 31/2.\n" +
                    "Ориентир: Напротив Городского хокимията, Кафе \"GUMBAZ\"");
            getUrgench.add("Геолокация:");
        }
        getUrgench.add("https://yandex.uz/maps/-/CCUmjQuCSA");
        return getUrgench;
    }
    public static List<String> getFergana(String lang) throws IOException {

        List<String> getFergana = new ArrayList<>();
        SSLFix.execute();
        getFergana.add("https://www.anorbank.uz/upload/img/punkt/g_Fergana.jpg");
        if (lang.equals("uz")) {
            getFergana.add("\uD83C\uDFE0Farg'ona shahri. ko'ch. Marifat, uy-12\n" +
                    "\"Or-r: Trast Bank");
            getFergana.add("Joylashuv:");

        } else if (lang.equals("en")) {
            getFergana.add("\uD83C\uDFE0Point address:\n" +
                    " Fergana city. st. Marifat, house - 12\n" +
                    "near to Trust Bank.");
            getFergana.add("Location:");
        } else if (lang.equals("")) {
            getFergana.add("\uD83C\uDFE0Адрес опорного пункта: Город Фергана. ул. Маърифат, дом – 12\n" +
                    "Ор-р: Траст банк.");
            getFergana.add("Геолокация:");
        }
        getFergana.add("https://yandex.uz/maps/-/CCUqA2t7OB");
        return getFergana;
    }
    public static List<String> getChirchik(String lang) throws IOException {

        List<String> getChirchik = new ArrayList<>();
        SSLFix.execute();
        getChirchik.add("https://www.anorbank.uz/upload/img/punkt/g_Chirchik.jpg");
        if (lang.equals("uz")) {
            getChirchik.add("Chirchiq shahri,  A. Temur ko'chasi, 108-uy");
            getChirchik.add("Joylashuv:");

        } else if (lang.equals("en")) {
            getChirchik.add("\uD83C\uDFE0Point address:\n" +
                    "Chirchik, MFY Shodlik, A. Temur str., house number 108");
            getChirchik.add("Location:");
        } else if (lang.equals("")) {
            getChirchik.add("\uD83C\uDFE0Адрес опорного пункта:\n" +
                    "г.Чирчик, МФЙ шодлик, ул.А.Темур, дом № 108");
            getChirchik.add("Геолокация:");
        }
        getChirchik.add("https://yandex.uz/maps/-/CCUqUUFoPD");
        return getChirchik;
    }


}