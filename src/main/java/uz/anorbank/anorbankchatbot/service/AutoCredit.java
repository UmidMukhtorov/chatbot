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
public class AutoCredit {

    public static List<String> getAutoCredit(String lang) throws IOException {
        List<String> autoCredit = new ArrayList<>();
        SSLFix.execute();
        autoCredit.add("https://www.anorbank.uz/upload/img/punkt/avtokredit.png"); //image

        if (lang.equals("uz")) {
            autoCredit.add("Avtokredit xizmati ikkilamchi bozordan xarid uchun taqdim etiladi. Muddati 36 oy bo'ladi. Yillik 23%. Boshlang’ich to’lov 30%. Rasmiylashtirish uchun bosh ofisimizga (agar Toshkent shaxrida bo'lsangiz) yoki viloyatlarda joylashgan tayanch punktlarimizga murojaat qilishingiz mumkin. Savollar bo`yicha quyidagi raqamlarga murojaat qilishingiz mumkin: \n +998555030000 \n 1290");
        } else if (lang.equals("en")) {
            autoCredit.add("The car loan service is provided for purchase on the secondary market. The term will be 36 months. 23% per annum. The initial payment is 30%. For registration, you can contact our head office (if you are located in Tashkent) or our branches are located in the central cities of the regions.\n+998555030000 \n 1290");

        } else

        autoCredit.add("Услуга автокредита предоставляется для покупки на вторичном рынке. Срок составит 36 месяцев. 23% годовых. Первоначальный взнос 30%. Для оформления вы можете обратиться в наш головной офис (если вы находитесь в г. Ташкенте) или в наши опорные пункты, расположенные в регионах. Если у вас есть дополнительные вопросы, позвоните по номеру: \n +998555030000 \n 1290");//<p>
        return autoCredit;


    }
}
