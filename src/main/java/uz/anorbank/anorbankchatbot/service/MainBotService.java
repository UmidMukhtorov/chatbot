package uz.anorbank.anorbankchatbot.service;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.*;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import uz.anorbank.anorbankchatbot.bot.response.Uzb;
import uz.anorbank.anorbankchatbot.domain.User;
import uz.anorbank.anorbankchatbot.service.base.BaseBotService;

import java.util.*;

@Slf4j
@Service
public class MainBotService extends TelegramLongPollingBot implements BaseBotService, Uzb {
    @Value("${telegram.bot.username}")
    private String username;
    @Value("${telegram.bot.token}")
    private String token;
    @Value("${telegram.group.chatId}")
    private String groupChatId;
    private Long userChatId;
    private String userMessage;
    private String rank;
    private final static Map<Long, String> map = new HashMap<>();
    private final UserService userService;

    public MainBotService(UserService userService) {
        this.userService = userService;
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        userChatId = getUserChatId(update);
        String inputText = getUserResponse(update);
        if (update.hasMessage()) {
            if (update.getMessage().getContact() != null){
                String s = userService.editUserPhone(update, update.getMessage().getContact().getPhoneNumber());
                update.getMessage().setText(map.get(userChatId));
                update.getMessage().setContact(null);
                onUpdateReceived(update);
            }
            switch (inputText) {
                case "/start":
                    userMessage = chooseLanguage;
                    userService.saveUser(update);
                    execute(chooseYourLanguage(), null);
                    break;
                //********************************Русский********************************************//
                case "\uD83C\uDDF7\uD83C\uDDFA Русский":
                    userMessage = "Здравствуйте! Чем могу вам помочь?";
                    userService.saveUserLanguage(update, "");
                    execute(createMarkupButtons("Консультация", "Обратная связь", "Назад"), null);
                    map.put(userChatId, "/start");
                    break;
                case "Назад":
                    update.getMessage().setText(map.get(userChatId) != null && map.get(userChatId) != "" ? map.get(userChatId) : "/start");
                    onUpdateReceived(update);
                    break;
                    case "Скоро...":
                    update.getMessage().setText(map.get(userChatId) != null && map.get(userChatId) != "" ? map.get(userChatId) : "/start");
                    onUpdateReceived(update);
                    break;
                case "Консультация":
                    userMessage = "Ваш правовой статус?";
                    execute(createMarkupButtons("Физическое лицо", "Юридическое лицо", "Назад"), null);
                    map.put(userChatId, "\uD83C\uDDF7\uD83C\uDDFA Русский");
                    break;
                case "Физическое лицо":
                    userMessage = "Физическое лицо";
                    execute(createMarkupButtons("Карты ", "Кредиты", "Вклады", "Автокредит", "Адреса", "Не получил ответ на вопрос", "Назад"), null);
                    map.put(userChatId, "Консультация");
                    break;






                case "Не получил ответ на вопрос":
                    userMessage = "Для того, чтобы с вами связались специалисты контакт-центра отправьте свой телефонный номер как контакт!";
                    update.getMessage().setText("Позвоните мне");
                    onUpdateReceived(update);
                    break;
////
//                case "a":
//                    SendMessage sendMessage = new SendMessage();
//                    sendMessage.setChatId(String.valueOf(userChatId));
//                    sendMessage.setText("You send a");
//
//                    // create keyboard
//                    ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
//                    sendMessage.setReplyMarkup(replyKeyboardMarkup);
//                    replyKeyboardMarkup.setSelective(true);
//                    replyKeyboardMarkup.setResizeKeyboard(true);
//                    replyKeyboardMarkup.setOneTimeKeyboard(true);
//
//                    // new list
//                    List<KeyboardRow> keyboard = new ArrayList<>();
//
//                    // first keyboard line
//                    KeyboardRow keyboardFirstRow = new KeyboardRow();
//                    KeyboardButton keyboardButton = new KeyboardButton();
//                    keyboardButton.setText("Пожалуйста, отправьте свой контакт");
////                    userMessage="Kontakt yub";
//                    keyboardButton.setRequestContact(true);
//                    keyboardFirstRow.add(keyboardButton);
//
//                    // add array to list
//                    keyboard.add(keyboardFirstRow);
//
//                    // add list to our keyboard
//                    replyKeyboardMarkup.setKeyboard(keyboard);
//
//                    try {
//                        execute(sendMessage);
//                    }catch (Exception e){
//                        e.printStackTrace();
//                    }
//
//                    System.out.println("#############");
//                    System.out.println(update.getMessage().getContact());
//                    System.out.println("#############");
//                    break;
//
//
//
//
//
//
//
//
//
//
//
//






                case "Подтвердить":      //todo manashu case ni bossa contactni share qilishi kerak
                    userMessage = "В ближайшее с вами свяжается специалист контакт центра для далнейшей консультации";
                    execute(createMarkupButtons("Назад"), null);
                    map.put(userChatId, "/start");
                    break;
                case "Отменить заявку":
                    userMessage = "Отменить заявку";
                    update.getMessage().setText("Обратная связь");
                    onUpdateReceived(update);
                    break;
                case "Обратная связь":
                    userMessage = "Обратная связь";
                    execute(createMarkupButtons("Позвоните мне", "Оставить отзыв/предложение", "Коммерческое предложение", "Вакансии банка", "Назад"), null);
                    map.put(userChatId, "\uD83C\uDDF7\uD83C\uDDFA Русский");
                    break;
                case "Коммерческое предложение":
                    userMessage = "Коммерческое предложение";
                    execute(createMarkupButtons("Назад"), null);
                    List<String> commercialProposalRu = AboutVacanciesService.getCommercialProposal("");
                    execute1(commercialProposalRu.get(0), commercialProposalRu.get(1));
                    map.put(userChatId, "Обратная связь");
                    break;
                case "Вакансии банка":
                    userMessage = "Вакансии банка";
                    execute(createMarkupButtons("Назад"), null);
                    List<String> vacanciesRu = AboutVacanciesService.getAboutVacancies("");
                    execute(vacanciesRu.get(0), vacanciesRu.get(1), vacanciesRu.get(2), vacanciesRu.get(3), vacanciesRu.get(4));
                    map.put(userChatId, "Обратная связь");
                    break;

                case "Оставить отзыв/предложение":
                    userMessage = "Оставить отзыв/предложение";
                    execute(createMarkupButtons(  "Скоро..."), null);//todo davomi bor
                    map.put(userChatId, "Обратная связь");
                    break;
                case "Сервис банка":
                    userMessage = "Оставьте отзыв в виде текстого или аудиосообщения";
                    execute(createMarkupButtons("Отправить", "Назад"), null);//todo davomi bor
                    executeGroup(update, rank);


                    map.put(userChatId, "Хочу пожаловаться");
                    rank = "Сервис банка";
                    break;
                case "Продукты банка":
                    userMessage = "Оставьте отзыв в виде текстого или аудиосообщения";
                    execute(createMarkupButtons("Отправить", "Назад"), null);//todo davomi bor
                    map.put(userChatId, "Хочу пожаловаться");
                    rank = "Продукты банка";
                    break;
                case "Сотрудник банка":
                    userMessage = "Оставьте отзыв в виде текстого или аудиосообщения";
                    execute(createMarkupButtons("Отправить", "Назад"), null);//todo davomi bor
                    map.put(userChatId, "Хочу пожаловаться");
                    rank = "Сотрудник банка";
                    break;
                case "Все понравилось на 5":
                    userMessage = "Оставьте отзыв в виде текстого или аудиосообщения";
                    execute(createMarkupButtons("Отправить", "Назад"), null);//todo davomi bor
                    map.put(userChatId, "Оставить отзыв/предложение");
                    rank = "Все понравилось на 5";
                    break;
                case "Нормально на 4":
                    rank = "Нормально на 4";
                    userMessage = "Отменить заявку";
                    update.getMessage().setText("Хочу пожаловаться");
                    onUpdateReceived(update);
                    break;
                case "Удовлетворительно на 3":
                    rank = "Удовлетворительно на 3";
                    userMessage = "Отменить заявку";
                    update.getMessage().setText("Хочу пожаловаться");
                    onUpdateReceived(update);
                    break;
                case "Не понравилось на 2":
                    rank = "Нормально на 2";
                    userMessage = "Отменить заявку";
                    update.getMessage().setText("Хочу пожаловаться");
                    onUpdateReceived(update);
                    break;
                case "Хочу пожаловаться":
                    userMessage = "Что Вам не понравилось больше всего? ";
                    execute(createMarkupButtons("Сервис банка", "Продукты банка", "Сотрудник банка", "Назад"), null);//todo davomi bor
                    map.put(userChatId, "Оставить отзыв/предложение");
                    break;
                case "Отправить":
                    userMessage = "Отправлено! \n Спасибо за отзыв ";
                    execute(createMarkupButtons("Назад"), null);//todo davomi bor


                    executeGroup(update, rank);

                    map.put(userChatId, "/start");
                    break;
                case "Позвоните мне":
                    // todo telefon raqamga tekshirish
                    Optional<User> user = userService.findUserByChatId(update);
                    User user1 = new User();
                    if (user.isPresent()){
                        user1 = user.get();
                    }
                    if (user1.getPhoneNumber() == null || user1.getPhoneNumber().equals("") || user1.getPhoneNumber().trim().equals("")){
                        SendMessage sendMessage = new SendMessage();
                        sendMessage.setChatId(String.valueOf(userChatId));
                        sendMessage.setText("Пожалуйста, отправьте свой контакт");
                        shareContact(sendMessage, "");
                        map.put(userChatId, "Позвоните мне");
                    } else {
                        String message = user1.getUsername() + "\n" + user1.getPhoneNumber() + "\n\n" + update.getMessage().getText();
                        userMessage = "Операторы скоро вам перезвонят";
                        String groupChatId = this.groupChatId;
                        execute(groupChatId, message);
                        execute(new SendMessage(String.valueOf(this.userChatId), userMessage));
                        //backStepMessage = "\uD83C\uDDF7\uD83C\uDDFA Русский";
                        update.getMessage().setText("Обратная связь");
                        onUpdateReceived(update);
                    }
                    break;
                case "Адреса":
                    userMessage = "Адреса";
                    execute(createMarkupButtons("Адрес главного офиса", "Адреса опорных пунктов(Регионы)", "Адреса торговых точек", "Назад"), null);
                    map.put(userChatId, "Физическое лицо");
                    break;
                case "Автокредит":
                    userMessage = "Автокредит";
                    execute(createMarkupButtons("Назад"), null);
                    List<String> autoCreditRu = AutoCredit.getAutoCredit("");
                    execute1(autoCreditRu.get(0), autoCreditRu.get(1));
                    map.put(userChatId, "Физическое лицо");
                    break;
                case "Адрес главного офиса":
                    userMessage = "Адрес главного офиса";
                    execute(createMarkupButtons("Назад"), null);
                    List<String> addressOffice = AboutBankService.getAddressOfficeBank("");
                    execute(addressOffice.get(0), addressOffice.get(1), addressOffice.get(2), addressOffice.get(3));
                    map.put(userChatId, "Адреса");
                    break;
                case "Адреса опорных пунктов(Регионы)":
                    userMessage = "Адреса опорных пунктов(Регионы)";
                    execute(createMarkupButtons("Андижан", "Ангрен", "Бухара", "Джизак", "Карши", "Наманган", "Навои", "Республика Каракалпакстан", "Самарканд", "Сырдарья", "Термез",
                            "Ургенч", "Фергана", "Чирчик", "Назад"), null);
                    map.put(userChatId, "Адреса");
                    break;
                case "Андижан":
                    userMessage = "Андижан";
                    execute(createMarkupButtons("Назад"), null);
                    List<String> getandijan = AboutBankService.getAndijan("");
                    execute(getandijan.get(0), getandijan.get(1), getandijan.get(2), getandijan.get(3));
                    map.put(userChatId, "Адреса опорных пунктов(Регионы)");
                    break;
                case "Ангрен":
                    userMessage = "Ангрен";
                    List<String> getangren = AboutBankService.getAngren("");
                    execute(createMarkupButtons("Назад"), null);
                    execute(getangren.get(0), getangren.get(1), getangren.get(2), getangren.get(3));
                    map.put(userChatId, "Адреса опорных пунктов(Регионы)");
                    break;
                case "Бухара":
                    userMessage = "Бухара";
                    List<String> getbuxara = AboutBankService.getBuxara("");
                    execute(createMarkupButtons("Назад"), null);
                    execute(getbuxara.get(0), getbuxara.get(1), getbuxara.get(2), getbuxara.get(3));
                    map.put(userChatId, "Адреса опорных пунктов(Регионы)");
                    break;
                case "Джизак":
                    userMessage = "Джизак";
                    List<String> getjizzax = AboutBankService.getJizzax("");
                    execute(createMarkupButtons("Назад"), null);
                    execute(getjizzax.get(0), getjizzax.get(1), getjizzax.get(2), getjizzax.get(3));
                    map.put(userChatId, "Адреса опорных пунктов(Регионы)");
                    break;
                case "Карши":
                    userMessage = "Карши";
                    List<String> getkarshi = AboutBankService.getKarshi("");
                    execute(createMarkupButtons("Назад"), null);
                    execute(getkarshi.get(0), getkarshi.get(1), getkarshi.get(2), getkarshi.get(3));
                    map.put(userChatId, "Адреса опорных пунктов(Регионы)");
                    break;
                case "Наманган":
                    userMessage = "Наманган";
                    List<String> getnamangan = AboutBankService.getNamangan("");
                    execute(createMarkupButtons("Назад"), null);
                    execute(getnamangan.get(0), getnamangan.get(1), getnamangan.get(2), getnamangan.get(3));
                    map.put(userChatId, "Адреса опорных пунктов(Регионы)");
                    break;
                case "Навои":
                    userMessage = "Навои";
                    List<String> getnavai = AboutBankService.getNavai("");
                    execute(createMarkupButtons("Назад"), null);
                    execute(getnavai.get(0), getnavai.get(1), getnavai.get(2), getnavai.get(3));
                    map.put(userChatId, "Адреса опорных пунктов(Регионы)");
                    break;
                case "Республика Каракалпакстан":
                    userMessage = "Республика Каракалпакстан";
                    List<String> getnukus = AboutBankService.getKarakalpak("");
                    execute(createMarkupButtons("Назад"), null);
                    execute(getnukus.get(0), getnukus.get(1), getnukus.get(2), getnukus.get(3));
                    map.put(userChatId, "Адреса опорных пунктов(Регионы)");
                    break;
                case "Самарканд":
                    userMessage = "Самарканд";
                    List<String> getsam = AboutBankService.getSamarkand("");
                    execute(createMarkupButtons("Назад"), null);
                    execute(getsam.get(0), getsam.get(1), getsam.get(2), getsam.get(3));
                    map.put(userChatId, "Адреса опорных пунктов(Регионы)");
                    break;
                case "Сырдарья":
                    userMessage = "Сырдарья";
                    List<String> getsirdarya = AboutBankService.getSirdarya("");
                    execute(createMarkupButtons("Назад"), null);
                    execute(getsirdarya.get(0), getsirdarya.get(1), getsirdarya.get(2), getsirdarya.get(3));
                    map.put(userChatId, "Адреса опорных пунктов(Регионы)");
                    break;
                case "Термез":
                    userMessage = "Термез";
                    List<String> gettermiz = AboutBankService.getTermiz("");
                    execute(createMarkupButtons("Назад"), null);
                    execute(gettermiz.get(0), gettermiz.get(1), gettermiz.get(2), gettermiz.get(3));
                    map.put(userChatId, "Адреса опорных пунктов(Регионы)");
                    break;
                case "Ургенч":
                    userMessage = "Ургенч";
                    List<String> geturgench = AboutBankService.getUrgench("");
                    execute(createMarkupButtons("Назад"), null);
                    execute(geturgench.get(0), geturgench.get(1), geturgench.get(2), geturgench.get(3));
                    map.put(userChatId, "Адреса опорных пунктов(Регионы)");
                    break;
                case "Чирчик":
                    userMessage = "Чирчик";
                    List<String> getchirchik = AboutBankService.getChirchik("");
                    execute(createMarkupButtons("Назад"), null);
                    execute(getchirchik.get(0), getchirchik.get(1), getchirchik.get(2), getchirchik.get(3));
                    map.put(userChatId, "Адреса опорных пунктов(Регионы)");
                    break;
                case "Фергана":
                    userMessage = "Фергана";
                    List<String> getfergana = AboutBankService.getFergana("");
                    execute(createMarkupButtons("Назад"), null);
                    execute(getfergana.get(0), getfergana.get(1), getfergana.get(2), getfergana.get(3));
                    map.put(userChatId, "Адреса опорных пунктов(Регионы)");
                    break;
                case "Адреса торговых точек":
                    userMessage = "Адреса торговых точек";
                    execute(createMarkupButtons("Назад"), null);
                    List<String> addressOfficeg = ATMSServices.getATMS("");
                    execute(addressOfficeg.get(0), addressOfficeg.get(1), addressOfficeg.get(2), addressOfficeg.get(3));
                    map.put(userChatId, "Адреса");
                    break;
                /****************************************************Карты*********************************************/
                case "Карты":
                    userMessage = "Карты";
                    List<String> cardName = CardService.getCardName("");
                    execute(createMarkupButtons(cardName.get(0), cardName.get(1), cardName.get(2), "Назад"), null);
                    map.put(userChatId, "Физическое лицо");
                    break;
                case "Карта рассрочки ANOR":
                    userMessage = "Карта рассрочки ANOR”";
                    execute(createMarkupButtons("Назад"), null);
                    List<String> installmentCardUz = CardService.getInstallmentCard("");
                    execute(installmentCardUz.get(0), installmentCardUz.get(1), installmentCardUz.get(2), installmentCardUz.get(3), installmentCardUz.get(4));
                    map.put(userChatId, "Карты");
                    break;
                case "Карта TRIA":
                    userMessage = "Карта TRIA";
                    execute(createMarkupButtons("Назад"), null);
                    List<String> triaCard = CardService.getTriaCard("");
                    execute(triaCard.get(0), triaCard.get(1), triaCard.get(2), triaCard.get(3), triaCard.get(4));
                    map.put(userChatId, "Карты");
                    break;
                case "Карта 2 в 1 \"MASTERCARD - HUMO\"":
                    userMessage = "Карта 2 в 1 \"MASTERCARD - HUMO\"";
                    execute(createMarkupButtons("Назад"), null);
                    List<String> masterCard = CardService.getMasterCard("");
                    execute(masterCard.get(0), masterCard.get(1), masterCard.get(2), masterCard.get(3), masterCard.get(4));
                    map.put(userChatId, "Карты");
                    break;
                /*********************************************Кредиты**************************************************/
                case "Кредиты":
                    userMessage = "Кредиты";
                    List<String> creditName = CreditService.getCreditsName("");
                    execute(createMarkupButtons(creditName.get(0), creditName.get(1), creditName.get(2), creditName.get(3), "Назад"), null);
                    map.put(userChatId, "Физическое лицо");
                    break;
                case "Онлайн микрозайм":
                    userMessage = "Онлайн микрозайм";
                    execute(createMarkupButtons("Назад"), null);
                    List<String> microloanCredit = CreditService.getMicroloanCredit("");
                    execute(microloanCredit.get(0), microloanCredit.get(1), microloanCredit.get(2), microloanCredit.get(3), microloanCredit.get(4));
                    map.put(userChatId, "Кредиты");
                    break;
                case "Доступные деньги 2.0":
                    userMessage = "Доступные деньги";
                    execute(createMarkupButtons("Назад"), null);
                    List<String> availableCredit = CreditService.getAvailableCredit("");
                    execute(availableCredit.get(0), availableCredit.get(1), availableCredit.get(2), availableCredit.get(3), availableCredit.get(4));
                    map.put(userChatId, "Кредиты");
                    break;
                case "Рассрочка на покупки":
                    userMessage = "Рассрочка на покупки";
                    execute(createMarkupButtons("Назад"), null);
                    List<String> installmentsCredit = CreditService.getInstallmentsCredit("");
                    execute(installmentsCredit.get(0), installmentsCredit.get(1), installmentsCredit.get(2), installmentsCredit.get(3), installmentsCredit.get(4));
                    map.put(userChatId, "Кредиты");
                    break;
                case "Овердрафт на все случаи жизни":
                    userMessage = "Овердрафт на все случаи жизни";
                    List<String> overdraftCredit = CreditService.getOverdraftCredit("");
                    execute(createMarkupButtons("Назад"), null);
                    execute(overdraftCredit.get(0), overdraftCredit.get(1), overdraftCredit.get(2), overdraftCredit.get(3), overdraftCredit.get(4));
                    map.put(userChatId, "Кредиты");
                    break;
                /*********************************************Вклады***************************************************/
                case "Вклады":
                    userMessage = "Вклады";
                    List<String> depName = DepositService.getDepositName("");
                    execute(createMarkupButtons(depName.get(0), depName.get(1), depName.get(3), "Назад"), null);
                    map.put(userChatId, "Физическое лицо");
                    break;
                case "Вклад SMART 2.0":
                    userMessage = "Вклад SMART 2.0";
                    execute(createMarkupButtons("Назад"), null);
                    List<String> smartDeposit = DepositService.getSmartDeposit("");
                    execute(smartDeposit.get(0), smartDeposit.get(1), smartDeposit.get(2), smartDeposit.get(3), smartDeposit.get(4));
                    map.put(userChatId, "Вклады");
                    break;
                case "Вклад SHOSHILMASDAN 2.0":
                    userMessage = "Вклад SHOSHILMASDAN 2.0";
                    execute(createMarkupButtons("Назад"), null);
                    List<String> shoshilmasdanDeposit = DepositService.getShoshilmasdanDeposit("");
                    execute(shoshilmasdanDeposit.get(0), shoshilmasdanDeposit.get(1), shoshilmasdanDeposit.get(2), shoshilmasdanDeposit.get(3), shoshilmasdanDeposit.get(4));
                    map.put(userChatId, "Вклады");
                    break;
                case "Вклад «Go»":
                    userMessage = "Рассрочка на покупки";
                    execute(createMarkupButtons("Назад"), null);
                    List<String> goDeposit = DepositService.getGoDeposit("");
                    execute(goDeposit.get(0), goDeposit.get(1), goDeposit.get(2), goDeposit.get(3), goDeposit.get(4));
                    map.put(userChatId, "Вклады");
                    break;
                /******************************************Юридическое лицо****************************************/
                case "Юридическое лицо":
                    userMessage = "Юридическое лицо";
                    execute(createMarkupButtons("Расчетное обслуживание(сум)",
                            "Расчетное обслуживание(валюта)", "Депозиты", "Зарплатный проект",
                            "Хочу получить коммерческое предложение", "Партнерская программа “Anor Merchant”",
                            "Заказать корпоративную карту", "Назад"), null);
                    map.put(userChatId, "Консультация");
                    break;
                case "Расчетное обслуживание(сум)":
                    userMessage = "Расчетное обслуживание(сум)";
                    execute(createMarkupButtons("Юридическое лицо⠀", "Индивидуальный предприниматель", "Назад"), null);
                    map.put(userChatId, "Юридическое лицо");
                    break;
                case "Расчетное обслуживание(валюта)":
                    userMessage = "Расчетное обслуживание(валюта)";
                    execute(createMarkupButtons("Юридическое лицо⠀⠀", "Индивидуальный предприниматель⠀", "Назад"), null);
                    map.put(userChatId, "Юридическое лицо");
                    break;

                case "Юридическое лицо⠀":
                    userMessage = "Юридическое лицо";
                    List<String> getBusinessRate = SettlementServices.getBusinessRates("");
                    execute(createMarkupButtons("Назад"), null);
                    execute(getBusinessRate.get(0), getBusinessRate.get(1), getBusinessRate.get(2), getBusinessRate.get(3));
                    map.put(userChatId, "Расчетное обслуживание(сум)");
                    break;
                case "Юридическое лицо⠀⠀":
                    userMessage = "Юридическое лицо";
                    List<String> getBusinessRateValyut = SettlementServices.getBusinessRates("");
                    execute(createMarkupButtons("Назад"), null);
                    execute(getBusinessRateValyut.get(0), getBusinessRateValyut.get(1), getBusinessRateValyut.get(2), getBusinessRateValyut.get(3));
                    map.put(userChatId, "Расчетное обслуживание(валюта)");
                    break;
                case "Индивидуальный предприниматель":
                    userMessage = "Индивидуальный предприниматель";
                    List<String> getBusinessRates = SettlementServices.getBusinessRates("");
                    execute(createMarkupButtons("Назад"), null);
                    execute(getBusinessRates.get(0), getBusinessRates.get(1), getBusinessRates.get(2), getBusinessRates.get(3));
                    map.put(userChatId, "Расчетное обслуживание(сум)");
                    break;
                case "Индивидуальный предприниматель⠀":
                    userMessage = "Индивидуальный предприниматель";
                    List<String> getBusinessRatesValyut = SettlementServices.getBusinessRates("");
                    execute(createMarkupButtons("Назад"), null);
                    execute(getBusinessRatesValyut.get(0), getBusinessRatesValyut.get(1), getBusinessRatesValyut.get(2), getBusinessRatesValyut.get(3));
                    map.put(userChatId, "Расчетное обслуживание(валюта)");
                    break;
                case "Юридическое лицо⠀⠀⠀":
                    userMessage = "Юридическое лицо";
                    execute(createMarkupButtons("Депозит «Овернайт»", "Срочный депозит", "Назад"), null);
                    map.put(userChatId, "Депозиты");
                    break;
                case "Депозит «Овернайт»":
                    userMessage = "Депозит «Овернайт»";
                    List<String> depositName = BusinessDeposit.getOverNightDeposit("");
                    execute(createMarkupButtons("Назад"), null);
                    execute(depositName.get(0), depositName.get(1), depositName.get(2), depositName.get(3));
                    map.put(userChatId, "Юридическое лицо⠀⠀⠀");
                    break;
                case "Срочный депозит":
                    userMessage = "Срочный депозит";
                    List<String> deposittName = BusinessDeposit.getFixedTermDeposit("");
                    execute(createMarkupButtons("Назад"), null);
                    execute(deposittName.get(0), deposittName.get(1), deposittName.get(2), deposittName.get(3));
                    map.put(userChatId, "Юридическое лицо⠀⠀⠀");
                    break;
                case "Индивидуальный предприниматель⠀⠀":
                    userMessage = "Индивидуальный предприниматель";
                    execute(createMarkupButtons("Депозит «Овернайт»", "Срочный депозит", "Назад"), null);
                    map.put(userChatId, "Депозиты");
                    break;
                case "Депозит⠀«Овернайт»":
                    userMessage = "Депозит⠀«Овернайт»";
                    List<String> depositNamee = BusinessDeposit.getOverNightDeposit("");
                    execute(createMarkupButtons("Назад"), null);
                    execute(depositNamee.get(0), depositNamee.get(1), depositNamee.get(2), depositNamee.get(3));
                    map.put(userChatId, "Индивидуальный предприниматель⠀⠀");
                    break;
                case "Срочный⠀депозит":
                    userMessage = "Срочный депозит";
                    List<String> deposittNamee = BusinessDeposit.getFixedTermDeposit("");
                    execute(createMarkupButtons("Назад"), null);
                    execute(deposittNamee.get(0), deposittNamee.get(1), deposittNamee.get(2), deposittNamee.get(3));
                    map.put(userChatId, "Индивидуальный предприниматель⠀⠀");
                    break;
                case "Зарплатный проект":
                    userMessage = "Партнерская программа “Anor Merchant”";
                    List<String> payrollProgramme = SettlementServices.getBusinessPayrollprogramme("");
                    execute(createMarkupButtons("Назад"), null);
                    execute(payrollProgramme.get(0), payrollProgramme.get(1), payrollProgramme.get(2), payrollProgramme.get(3));
                    map.put(userChatId, "Юридическое лицо");
                    break;
                case "Хочу получить коммерческое предложение":
                    userMessage = "Хочу получить коммерческое предложение";
                    List<String> documents = BusinessDeposit.getDocuments("");
                    execute(createMarkupButtons("Назад"), null);
                    execute(documents.get(0), documents.get(1), documents.get(2), documents.get(3));
                    map.put(userChatId, "Юридическое лицо");
                    break;
                case "Партнерская программа “Anor Merchant”":
                    userMessage = "Партнерская программа “Anor Merchant”";
                    List<String> businesPartner = BusinessPartner.businessPartner("");
                    execute(createMarkupButtons("Назад"), null);
                    execute(businesPartner.get(0), businesPartner.get(1), businesPartner.get(2), businesPartner.get(3), businesPartner.get(4));
                    map.put(userChatId, "Юридическое лицо");
                    break;
                case "Заказать корпоративную карту":
                    userMessage = "Заказать корпоративную карту";
                    List<String> corporateCards = SettlementServices.getBusinessCorporateCards("");
                    execute(createMarkupButtons("Назад"), null);
                    execute(corporateCards.get(0), corporateCards.get(1), corporateCards.get(2), corporateCards.get(3));
                    map.put(userChatId, "Юридическое лицо");
                    break;
                case "Депозиты":
                    userMessage = "Депозиты";
                    execute(createMarkupButtons("Юридическое лицо⠀⠀⠀", "Индивидуальный предприниматель⠀⠀", "Назад"), null);
                    map.put(userChatId, "Юридическое лицо");
                    break;
                //********************************O'zbekcha********************************************//

                case "\uD83C\uDDFA\uD83C\uDDFF O'zbekcha":
                    userMessage = "O'zbek tili tanlandi";
                    userService.saveUserLanguage(update, "uz");
                    execute(createMarkupButtons("Maslahat", "Aloqa", "Orqaga"), null);
                    map.put(userChatId, "/start");
                    break;
                case "Orqaga":
                    update.getMessage().setText(map.get(userChatId) != null && map.get(userChatId) != "" ? map.get(userChatId) : "/start");
                    onUpdateReceived(update);
                    break;

                    case "Tez kunda...":
                    update.getMessage().setText(map.get(userChatId) != null && map.get(userChatId) != "" ? map.get(userChatId) : "/start");
                    onUpdateReceived(update);
                    break;
                case "Maslahat":
                    userMessage = "Sizning huquqiy maqomingiz qanday?";
                    execute(createMarkupButtons("Jismoniy shaxs", "Yuridik shaxs", "Orqaga"), null);
                    map.put(userChatId, "\uD83C\uDDFA\uD83C\uDDFF O'zbekcha");
                    break;
                case "Jismoniy shaxs":
                    userMessage = "Sizning huquqiy maqomingiz?";
                    execute(createMarkupButtons("Kartalar", "Kreditlar", "Omonatlar", "Avtokredit", "Manzillar", "Savolingizga javob olmadingizmi ?", "Orqaga"), null);
                    map.put(userChatId, "Maslahat");
                    break;
                case "Savolingizga javob olmadingizmi ?":
                    userMessage = "Savolingizga javob olmadingizmi ?";
                    update.getMessage().setText("Menga qo'ng'iroq qiling");
                    onUpdateReceived(update);
                    break;
                case "Manzillar":
                    userMessage = "Manzillar";
                    execute(createMarkupButtons("Bosh ofis manzili", "Tayanch nuqtalar", "Savdo nuqtalari manzillari", "Orqaga"), null);
                    map.put(userChatId,"Jismoniy shaxs");
                    break;
                case "Bosh ofis manzili":
                    userMessage = "Bosh ofis manzili";
                    execute(createMarkupButtons("Orqaga"), null);
                    List<String> addressOffice1 = AboutBankService.getAddressOfficeBank("uz");
                    execute(addressOffice1.get(0), addressOffice1.get(1), addressOffice1.get(2), addressOffice1.get(3));
                    map.put(userChatId,"Manzillar");
                    break;
                case "Savdo nuqtalari manzillari":
                    userMessage = "Savdo nuqtalari manzillari"; //todo https://www.anorbank.uz/atms/#sales
                    execute(createMarkupButtons("Orqaga"), null);
                    List<String> addressOfficeg1 = ATMSServices.getATMS("uz");
                    execute(addressOfficeg1.get(0), addressOfficeg1.get(1), addressOfficeg1.get(2), addressOfficeg1.get(3));
                    map.put(userChatId,"Manzillar");
                    break;
                case "Tasdiqlash":      //todo manashu case ni bossa contactni share qilishi kerak
                    userMessage = "Iloji boricha tezroq aloqa markazi mutaxassisi qo'shimcha maslahat olish uchun siz bilan bog'lanadi";
                    execute(createMarkupButtons("Orqaga"), null);
                    map.put(userChatId, "/start");
                    break;
                case "Arizani bekor qilish":
                    userMessage = "Arizani bekor qilish";
                    update.getMessage().setText("Aloqa");
                    onUpdateReceived(update);
                    break;
                case "Aloqa":
                    userMessage = "Aloqa";
                    execute(createMarkupButtons("Menga qo'ng'iroq qiling", "Fikr/taklif qoldiring", "Tijorat taklif", "Bank bo'sh ish o'rinlari", "Orqaga"), null);
                    map.put(userChatId,"\uD83C\uDDFA\uD83C\uDDFF O'zbekcha");
                    break;
                case "Tijorat taklif":
                    userMessage = "Tijorat taklif";
                    execute(createMarkupButtons("Orqaga"), null);
                    List<String> commercialProposalUz = AboutVacanciesService.getCommercialProposal("uz");
                    execute1(commercialProposalUz.get(0), commercialProposalUz.get(1));
                    map.put(userChatId, "Aloqa");
                    break;
                case "Bank bo'sh ish o'rinlari":
                    userMessage = "Bank bo'sh ish o'rinlari";
                    execute(createMarkupButtons("Orqaga"), null);
                    List<String> vacanciesUz = AboutVacanciesService.getAboutVacancies("uz");
                    execute(vacanciesUz.get(0), vacanciesUz.get(1), vacanciesUz.get(2), vacanciesUz.get(3), vacanciesUz.get(4));
                    map.put(userChatId,"Aloqa");
                    break;
                case "Fikr/taklif qoldiring":
                    userMessage = "Fikr/taklif qoldiring";
                    execute(createMarkupButtons( "Tez kunda..."), null);//todo davomi bor
                    map.put(userChatId,"Aloqa");
                    break;
                case "Bank xizmati":
                    userMessage = "Fikr-mulohazalarni matn yoki audio xabar sifatida qoldiring";
                    execute(createMarkupButtons("Yuborish", "Orqaga"), null);//todo davomi bor
                    map.put(userChatId, "Men shikoyat qilmoqchiman");
                    break;
                case "Bank mahsulotlari":
                    userMessage = "Fikr-mulohazalarni matn yoki audio xabar sifatida qoldiring";
                    execute(createMarkupButtons("Yuborish", "Orqaga"), null);//todo davomi bor
                    map.put(userChatId,"Men shikoyat qilmoqchiman");
                    break;
                case "Bank xodimi":
                    userMessage = "Fikr-mulohazalarni matn yoki audio xabar sifatida qoldiring";
                    execute(createMarkupButtons("Yuborish", "Orqaga"), null);//todo davomi bor
                    map.put(userChatId,"Men shikoyat qilmoqchiman");
                    break;
                case "Hamma narsa yoqdi 5":
                    userMessage = "Fikr-mulohazalarni matn yoki audio xabar sifatida qoldiring";
                    execute(createMarkupButtons("Yuborish", "Orqaga"), null);//todo davomi bor
                    map.put(userChatId, "Fikr/taklif qoldiring");
                    break;
                case "Yaxshi 4":
                    userMessage = "Arizani bekor qilish";
                    update.getMessage().setText("Men shikoyat qilmoqchiman");
                    onUpdateReceived(update);
                    break;
                case "Qoniqarli 3":
                    userMessage = "Arizani bekor qilish";
                    update.getMessage().setText("Men shikoyat qilmoqchiman");
                    onUpdateReceived(update);
                    break;
                case "Yoqmadi 2":
                    userMessage = "Arizani bekor qilish";
                    update.getMessage().setText("Men shikoyat qilmoqchiman");
                    onUpdateReceived(update);
                    break;
                case "Men shikoyat qilmoqchiman":
                    userMessage = "Eng ko'p nimani yoqtirmadingiz? ";
                    execute(createMarkupButtons("Bank xizmati", "Bank mahsulotlari", "Bank xodimi", "Orqaga"), null);//todo davomi bor
                    map.put(userChatId, "Fikr/taklif qoldiring");
                    break;
                case "Yuborish":
                    userMessage = "Yuborildi! \n Fikr-mulohaza uchun rahmat ";
                    execute(createMarkupButtons("Orqaga"), null);//todo davomi bor
                    map.put(userChatId,"/start");
                    break;
                case "Menga qo'ng'iroq qiling":
//                    // todo telefon raqamga tekshirish

                Optional<User> userUz = userService.findUserByChatId(update);
                User user2 = new User();
                if (userUz.isPresent()){
                    user2 = userUz.get();
                }
                if (user2.getPhoneNumber() == null || user2.getPhoneNumber().equals("") || user2.getPhoneNumber().trim().equals("")){
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setChatId(String.valueOf(userChatId));
                    sendMessage.setText("Telefon raqamingizni yuboring");
                    shareContact(sendMessage, "uz");
                    map.put(userChatId, "Menga qo'ng'iroq qiling");
                } else {
                    String message = user2.getUsername() + "\n" + user2.getPhoneNumber() + "\n\n" + update.getMessage().getText();
                    userMessage = "Operatorlar tez orada sizga aloqaga chiqishadi";
                    String groupChatId = this.groupChatId;
                    execute(groupChatId, message);
                    execute(new SendMessage(String.valueOf(this.userChatId), userMessage));
                    //backStepMessage = "\uD83C\uDDF7\uD83C\uDDFA Русский";
                    update.getMessage().setText("Aloqa");
                    onUpdateReceived(update);
                }
                break;













                /*************************************************KartalarUz*******************************************/



                case "Kartalar":
                    userMessage = "Kartalar ro`yxati:";
                    List<String> cardName1 = CardService.getCardName("uz");
                    execute(createMarkupButtons(cardName1.get(0), cardName1.get(1), cardName1.get(2), "Orqaga"), null);
                    map.put(userChatId,"Jismoniy shaxs");
                    break;
                case "ANOR muddatli to’lov kartasi":
                    userMessage = "ANOR muddatli to’lov kartasi”";
                    execute(createMarkupButtons("Orqaga"), null);
                    List<String> installmentCard = CardService.getInstallmentCard("uz");
                    execute(installmentCard.get(0), installmentCard.get(1), installmentCard.get(2), installmentCard.get(3), installmentCard.get(4));
                    map.put(userChatId,"Kartalar");
                    break;
                case "TRIA kartasi":
                    userMessage = "TRIA kartasi”";
                    execute(createMarkupButtons("Orqaga"), null);
                    List<String> triaCardUz = CardService.getTriaCard("uz");
                    execute(triaCardUz.get(0), triaCardUz.get(1), triaCardUz.get(2), triaCardUz.get(3), triaCardUz.get(4));
                    map.put(userChatId,"Kartalar");
                    break;
                case "\"MASTERCARD - HUMO\" 2-tasi 1-da":
                    userMessage = "\"MASTERCARD - HUMO\" 2-tasi 1-da";
                    execute(createMarkupButtons("Orqaga"), null);
                    List<String> masterCardUz = CardService.getMasterCard("uz");
                    execute(masterCardUz.get(0), masterCardUz.get(1), masterCardUz.get(2), masterCardUz.get(3), masterCardUz.get(4));
                    map.put(userChatId,"Kartalar");
                    break;
                /********************************************************KreditlarUz***********************************/
                case "Kreditlar":
                    userMessage = "Kreditlar ro`yxati";
                    List<String> creditsName = CreditService.getCreditsName("uz");
                    execute(createMarkupButtons(creditsName.get(0), creditsName.get(1), creditsName.get(2), creditsName.get(3), "Orqaga"), null);
                    map.put(userChatId,"Jismoniy shaxs");
                    break;
                case "Onlayn mikroqarz":
                    userMessage = "Onlayn mikroqarz";
                    execute(createMarkupButtons("Orqaga"), null);
                    List<String> microloanCreditUz = CreditService.getMicroloanCredit("uz");
                    execute(microloanCreditUz.get(0), microloanCreditUz.get(1), microloanCreditUz.get(2), microloanCreditUz.get(3), microloanCreditUz.get(4));
                    map.put(userChatId,"Kreditlar");
                    break;
                case "Oson pullar 2.0":
                    userMessage = "Oson pullar";
                    execute(createMarkupButtons("Orqaga"), null);
                    List<String> availableCreditUz = CreditService.getAvailableCredit("uz");
                    execute(availableCreditUz.get(0), availableCreditUz.get(1), availableCreditUz.get(2), availableCreditUz.get(3), availableCreditUz.get(4));
                    map.put(userChatId,"Kreditlar");
                    break;
                case "Xaridlar uchun muddatli to’lov":
                    userMessage = "Xaridlar uchun muddatli to’lov";
                    execute(createMarkupButtons("Orqaga"), null);
                    List<String> installmentsCreditUz = CreditService.getInstallmentsCredit("uz");
                    execute(installmentsCreditUz.get(0), installmentsCreditUz.get(1), installmentsCreditUz.get(2), installmentsCreditUz.get(3), installmentsCreditUz.get(4));
                    map.put(userChatId, "Kreditlar");
                    break;
                case "Barcha holatlar uchun overdraft":
                    userMessage = "Xaridlar uchun muddatli to’lov";
                    execute(createMarkupButtons("Orqaga"), null);
                    List<String> overdraftCreditUz = CreditService.getOverdraftCredit("uz");
                    execute(overdraftCreditUz.get(0), overdraftCreditUz.get(1), overdraftCreditUz.get(2), overdraftCreditUz.get(3), overdraftCreditUz.get(4));
                    map.put(userChatId, "Kreditlar");
                    break;
                /**********************************************************OmonatlarUz*********************************/
                case "Omonatlar":
                    userMessage = "Omonatlar ro`yxati";
                    List<String> depositsNameUz = DepositService.getDepositName("uz");
                    execute(createMarkupButtons(depositsNameUz.get(0), depositsNameUz.get(1), depositsNameUz.get(3), "Orqaga"), null);
                    map.put(userChatId, "Jismoniy shaxs");
                    break;
                case "\"SMART 2.0\" omonati":
                    userMessage = "\"SMART 2.0\" omonati";
                    execute(createMarkupButtons("Orqaga"), null);
                    List<String> smartDepositUz = DepositService.getSmartDeposit("uz");
                    execute(smartDepositUz.get(0), smartDepositUz.get(1), smartDepositUz.get(2), smartDepositUz.get(3), smartDepositUz.get(4));
                    map.put(userChatId,"Omonatlar");
                    break;
                case "\"SHOSHILMASDAN 2.0\" omonati":
                    userMessage = "\"SHOSHILMASDAN 2.0\" omonati";
                    execute(createMarkupButtons("Orqaga"), null);
                    List<String> shoshilmasdanDepositUz = DepositService.getShoshilmasdanDeposit("uz");
                    execute(shoshilmasdanDepositUz.get(0), shoshilmasdanDepositUz.get(1), shoshilmasdanDepositUz.get(2), shoshilmasdanDepositUz.get(3), shoshilmasdanDepositUz.get(4));
                    map.put(userChatId, "Omonatlar");
                    break;
                case "«Go» omonati":
                    userMessage = "«Go» omonati";
                    execute(createMarkupButtons("Orqaga"), null);
                    List<String> goDepositUz = DepositService.getGoDeposit("uz");
                    execute(goDepositUz.get(0), goDepositUz.get(1), goDepositUz.get(2), goDepositUz.get(3), goDepositUz.get(4));
                    map.put(userChatId,"Omonatlar");
                    break;
                case "Avtokredit":
                    userMessage = "Avtokredit";
                    execute(createMarkupButtons("Orqaga"), null);
                    List<String> autoCreditUz = AutoCredit.getAutoCredit("uz");
                    execute1(autoCreditUz.get(0), autoCreditUz.get(1));
                    map.put(userChatId,"Jismoniy shaxs");
                    break;
                case "Tayanch nuqtalar":
                    userMessage = "Tayanch nuqtalar"; //
                    execute(createMarkupButtons("Andijon", " Angren⠀", "Buxoro", "Jizzax", " Qarshi", "Namangan⠀", "Navoiy", " Qoraqalpog'iston Respublikasi", " Samarqand", "Sirdaryo",
                            "Termiz", "Urganch", "Chirchiq", "Farg'ona", "Orqaga"), null);
                    map.put(userChatId,"Manzillar");
                    break;
                case "Andijon":
                    userMessage = "Andijon";
                    execute(createMarkupButtons("Orqaga"), null);
                    List<String> getandijanUz = AboutBankService.getAndijan("uz");
                    execute(getandijanUz.get(0), getandijanUz.get(1), getandijanUz.get(2), getandijanUz.get(3));
                    map.put(userChatId,"Tayanch nuqtalar");
                    break;
                case "Angren⠀":
                    userMessage = "Angren";
                    List<String> getangrenUz = AboutBankService.getAngren("uz");
                    execute(createMarkupButtons("Orqaga"), null);
                    execute(getangrenUz.get(0), getangrenUz.get(1), getangrenUz.get(2), getangrenUz.get(3));
                    map.put(userChatId,"Tayanch nuqtalar");
                    break;
                case "Buxoro":
                    userMessage = "Buxoro";
                    List<String> getbuxaraUz = AboutBankService.getBuxara("uz");
                    execute(createMarkupButtons("Orqaga"), null);
                    execute(getbuxaraUz.get(0), getbuxaraUz.get(1), getbuxaraUz.get(2), getbuxaraUz.get(3));
                    map.put(userChatId, "Tayanch nuqtalar");
                    break;
                case "Jizzax":
                    userMessage = "Jizzax";
                    List<String> getjizzaxUz = AboutBankService.getJizzax("uz");
                    execute(createMarkupButtons("Orqaga"), null);
                    execute(getjizzaxUz.get(0), getjizzaxUz.get(1), getjizzaxUz.get(2), getjizzaxUz.get(3));
                    map.put(userChatId, "Tayanch nuqtalar");
                    break;
                case "Qarshi":
                    userMessage = "Qarshi";
                    List<String> getkarshiUz = AboutBankService.getKarshi("uz");
                    execute(createMarkupButtons("Orqaga"), null);
                    execute(getkarshiUz.get(0), getkarshiUz.get(1), getkarshiUz.get(2), getkarshiUz.get(3));
                    map.put(userChatId, "Tayanch nuqtalar");
                    break;
                case "Namangan⠀":
                    userMessage = "Namangan";
                    List<String> getnamanganUz = AboutBankService.getNamangan("uz");
                    execute(createMarkupButtons("Orqaga"), null);
                    execute(getnamanganUz.get(0), getnamanganUz.get(1), getnamanganUz.get(2), getnamanganUz.get(3));
                    map.put(userChatId,"Tayanch nuqtalar");
                    break;
                case "Navoiy":
                    userMessage = "Navoiy";
                    List<String> getnavaiUz = AboutBankService.getNavai("uz");
                    execute(createMarkupButtons("Orqaga"), null);
                    execute(getnavaiUz.get(0), getnavaiUz.get(1), getnavaiUz.get(2), getnavaiUz.get(3));
                    map.put(userChatId,"Tayanch nuqtalar");
                    break;
                case "Qoraqalpog'iston Respublikasi":
                    userMessage = "Qoraqalpog'iston Respublikasi";
                    List<String> getnukusUz = AboutBankService.getKarakalpak("uz");
                    execute(createMarkupButtons("Orqaga"), null);
                    execute(getnukusUz.get(0), getnukusUz.get(1), getnukusUz.get(2), getnukusUz.get(3));
                    map.put(userChatId, "Tayanch nuqtalar");
                    break;
                case "Samarqand":
                    userMessage = "Samarqand";
                    List<String> getsamUz = AboutBankService.getSamarkand("uz");
                    execute(createMarkupButtons("Orqaga"), null);
                    execute(getsamUz.get(0), getsamUz.get(1), getsamUz.get(2), getsamUz.get(3));
                    map.put(userChatId,"Tayanch nuqtalar");
                    break;
                case "Sirdaryo":
                    userMessage = "Sirdaryo";
                    List<String> getsirdaryaUz = AboutBankService.getSirdarya("uz");
                    execute(createMarkupButtons("Orqaga"), null);
                    execute(getsirdaryaUz.get(0), getsirdaryaUz.get(1), getsirdaryaUz.get(2), getsirdaryaUz.get(3));
                    map.put(userChatId,"Tayanch nuqtalar");
                    break;
                case "Termiz":
                    userMessage = "Termiz";
                    List<String> gettermizUz = AboutBankService.getTermiz("uz");
                    execute(createMarkupButtons("Orqaga"), null);
                    execute(gettermizUz.get(0), gettermizUz.get(1), gettermizUz.get(2), gettermizUz.get(3));
                    map.put(userChatId, "Tayanch nuqtalar");
                    break;
                case "Urganch":
                    userMessage = "Urganch";
                    List<String> geturgenchUz = AboutBankService.getUrgench("uz");
                    execute(createMarkupButtons("Orqaga"), null);
                    execute(geturgenchUz.get(0), geturgenchUz.get(1), geturgenchUz.get(2), geturgenchUz.get(3));
                    map.put(userChatId, "Tayanch nuqtalar");
                    break;
                case "Chirchiq":
                    userMessage = "Chirchiq";
                    List<String> getchirchikUz = AboutBankService.getChirchik("uz");
                    execute(createMarkupButtons("Orqaga"), null);
                    execute(getchirchikUz.get(0), getchirchikUz.get(1), getchirchikUz.get(2), getchirchikUz.get(3));
                    map.put(userChatId, "Tayanch nuqtalar");
                    break;
                case "Farg'ona":
                    userMessage = "Farg'ona";
                    List<String> getferganaUz = AboutBankService.getFergana("uz");
                    execute(createMarkupButtons("Orqaga"), null);
                    execute(getferganaUz.get(0), getferganaUz.get(1), getferganaUz.get(2), getferganaUz.get(3));
                    map.put(userChatId,"Tayanch nuqtalar");
                    break;
                /************************************************Yuridik shaxs****************************************************************************/
                case "Yuridik shaxs":
                    userMessage = "Yuridik shaxs";
                    execute(createMarkupButtons("Hisob-kitob xizmati (so'm)",
                            "Hisob-kitob xizmati (valyuta)", "Depozitlar", "Ish haqi loyihasi",
                            "Tijorat taklif olish istayman", "“Anor Merchant” Hamkorlik dasturi",
                            "Korporativ kartani buyurtma qiling", "Orqaga"), null);
                    map.put(userChatId,"Maslahat");
                    break;
                case "Hisob-kitob xizmati (so'm)":
                    userMessage = "Hisob-kitob xizmati (so'm)";
                    execute(createMarkupButtons("Yuridik shaxs⠀", "Yakka tartibdagi tadbirkor", "Orqaga"), null);
                    map.put(userChatId, "Yuridik shaxs");
                    break;
                case "Hisob-kitob xizmati (valyuta)":
                    userMessage = "Hisob-kitob xizmati (valyuta)";
                    execute(createMarkupButtons("Yuridik shaxs⠀⠀", "Yakka tartibdagi tadbirkor⠀", "Orqaga"), null);
                    map.put(userChatId,"Yuridik shaxs");
                    break;
                case "Yuridik shaxs⠀":
                    userMessage = "Yuridik shaxs";
                    List<String> getBusinessRate1 = SettlementServices.getBusinessRates("uz");
                    execute(createMarkupButtons("Orqaga"), null);
                    execute(getBusinessRate1.get(0), getBusinessRate1.get(1), getBusinessRate1.get(2), getBusinessRate1.get(3));
                    map.put(userChatId, "Hisob-kitob xizmati (so'm)");
                    break;
                case "Yuridik shaxs⠀⠀":
                    userMessage = "Yuridik shaxs";
                    List<String> getBusinessRateValyut1 = SettlementServices.getBusinessRates("uz");
                    execute(createMarkupButtons("Orqaga"), null);
                    execute(getBusinessRateValyut1.get(0), getBusinessRateValyut1.get(1), getBusinessRateValyut1.get(2), getBusinessRateValyut1.get(3));
                    map.put(userChatId, "Hisob-kitob xizmati (valyuta)");
                    break;
                case "Yakka tartibdagi tadbirkor":
                    userMessage = "Yakka tartibdagi tadbirkor";
                    List<String> getBusinessRates1 = SettlementServices.getBusinessRates("uz");
                    execute(createMarkupButtons("Orqaga"), null);
                    execute(getBusinessRates1.get(0), getBusinessRates1.get(1), getBusinessRates1.get(2), getBusinessRates1.get(3));
                    map.put(userChatId, "Hisob-kitob xizmati (so'm)");
                    break;
                case "Yakka tartibdagi tadbirkor⠀":
                    userMessage = "Yakka tartibdagi tadbirkor";
                    List<String> getBusinessRatesValyut1 = SettlementServices.getBusinessRates("uz");
                    execute(createMarkupButtons("Orqaga"), null);
                    execute(getBusinessRatesValyut1.get(0), getBusinessRatesValyut1.get(1), getBusinessRatesValyut1.get(2), getBusinessRatesValyut1.get(3));
                    map.put(userChatId, "Hisob-kitob xizmati (valyuta)");
                    break;
                case "Yuridik shaxs⠀⠀⠀":
                    userMessage = "Yuridik shaxs";
                    execute(createMarkupButtons("\"Overnayt\" omonati", "Muddatli omonati", "Orqaga"), null);
                    map.put(userChatId, "Depozitlar");
                    break;
                case "«Overnayt omonati»":
                    userMessage = "\"Overnayt\" omonati";
                    List<String> depositName1 = BusinessDeposit.getOverNightDeposit("");
                    execute(createMarkupButtons("Orqaga"), null);
                    execute(depositName1.get(0), depositName1.get(1), depositName1.get(2), depositName1.get(3));
                    map.put(userChatId,"Yuridik shaxs⠀⠀⠀");
                    break;
                case "Muddatli depozit":
                    userMessage = "Muddatli depozit";
                    List<String> deposittName1 = BusinessDeposit.getFixedTermDeposit("uz");
                    execute(createMarkupButtons("Orqaga"), null);
                    execute(deposittName1.get(0), deposittName1.get(1), deposittName1.get(2), deposittName1.get(3));
                    map.put(userChatId,"Yuridik shaxs⠀⠀⠀");
                    break;
                case "Yakka tartibdagi tadbirkor⠀⠀":
                    userMessage = "Индивидуальный предприниматель";
                    execute(createMarkupButtons("\"Overnayt\" omonati", "Muddatli depozit", "Orqaga"), null);
                    map.put(userChatId,"Depozitlar");
                    break;
                case "\"Overnayt\" omonati":
                    userMessage = "\"Overnayt\" omonati";
                    List<String> depositNamee1 = BusinessDeposit.getOverNightDeposit("uz");
                    execute(createMarkupButtons("Orqaga"), null);
                    execute(depositNamee1.get(0), depositNamee1.get(1), depositNamee1.get(2), depositNamee1.get(3));
                    map.put(userChatId,"Yakka tartibdagi tadbirkor⠀⠀");
                    break;
                case "Muddatli⠀depozit":
                    userMessage = "Muddatli⠀depozit";
                    List<String> deposittNamee1 = BusinessDeposit.getFixedTermDeposit("uz");
                    execute(createMarkupButtons("Orqaga"), null);
                    execute(deposittNamee1.get(0), deposittNamee1.get(1), deposittNamee1.get(2), deposittNamee1.get(3));
                    map.put(userChatId, "Yakka tartibdagi tadbirkor⠀⠀");
                    break;
                case "Ish haqi loyihasi":
                    userMessage = "“Anor Merchant” Hamkorlik dasturi";
                    List<String> payrollProgramme1 = SettlementServices.getBusinessPayrollprogramme("uz");
                    execute(createMarkupButtons("Orqaga"), null);
                    execute(payrollProgramme1.get(0), payrollProgramme1.get(1), payrollProgramme1.get(2), payrollProgramme1.get(3));
                    map.put(userChatId,"Yuridik shaxs");
                    break;
                case "Tijorat taklif olish istayman":
                    userMessage = "Tijorat taklif olish istayman";
                    List<String> documents1 = BusinessDeposit.getDocuments("uz");
                    execute(createMarkupButtons("Orqaga"), null);
                    execute(documents1.get(0), documents1.get(1), documents1.get(2), documents1.get(3));
                    map.put(userChatId,"Yuridik shaxs");
                    break;
                case "“Anor Merchant” Hamkorlik dasturi":
                    userMessage = "“Anor Merchant” Hamkorlik dasturi";
                    List<String> businesPartner1 = BusinessPartner.businessPartner("uz");
                    execute(createMarkupButtons("Orqaga"), null);
                    execute(businesPartner1.get(0), businesPartner1.get(1), businesPartner1.get(2), businesPartner1.get(3), businesPartner1.get(4));
                    map.put(userChatId,"Yuridik shaxs");
                    break;
                case "Korporativ kartani buyurtma qiling":
                    userMessage = "Korporativ kartani buyurtma qiling";
                    List<String> corporateCards1 = SettlementServices.getBusinessCorporateCards("uz");
                    execute(createMarkupButtons("Orqaga"), null);
                    execute(corporateCards1.get(0), corporateCards1.get(1), corporateCards1.get(2), corporateCards1.get(3));
                    map.put(userChatId, "Yuridik shaxs");
                    break;
                case "Depozitlar":
                    userMessage = "Depozitlar";
                    execute(createMarkupButtons("Yuridik shaxs⠀⠀⠀", "Yakka tartibdagi tadbirkor⠀⠀", "Orqaga"), null);
                    map.put(userChatId,"Yuridik shaxs");
                    break;
                //********************************English********************************************//
                case "\uD83C\uDDEC\uD83C\uDDE7 English":
                    userMessage = "English selected";
                    userService.saveUserLanguage(update, "en");
                    execute(createMarkupButtons("Consultation", "Feedback", "Back"), null);
                    map.put(userChatId, "/start");
                    break;
                case "Back":
                    update.getMessage().setText(map.get(userChatId) != null && map.get(userChatId) != "" ? map.get(userChatId) : "/start");
                    onUpdateReceived(update);
                    break;
                    case "Soon...":
                    update.getMessage().setText(map.get(userChatId) != null && map.get(userChatId) != "" ? map.get(userChatId) : "/start");
                    onUpdateReceived(update);
                    break;
                case "Consultation":
                    userMessage = "Your legal status?";
                    execute(createMarkupButtons("Individuals", "Legal entities", "Back"), null);
                    map.put(userChatId,"\uD83C\uDDEC\uD83C\uDDE7 English");
                    break;

                case "Individuals":
                    userMessage = "Opening and pressing the button An individual includes the following menu from:";
                    execute(createMarkupButtons("Cards ", "Credits", "Deposits", "Addresses", "Autocredit", "Did not receive an answer to the question ?", "Back"), null);
                    map.put(userChatId, "Consultation");
                    break;
                case "Did not receive an answer to the question ?":
                    userMessage = "Did not receive an answer to the question ?";
                    update.getMessage().setText("Call me");
                    onUpdateReceived(update);
                    break;
                case "Addresses":
                    userMessage = "Addresses";
                    execute(createMarkupButtons("Main office address", "Addresses of strongholds (Regions)", "Addresses of retail outlets", "Back"), null);
                    map.put(userChatId,"Individuals");
                    break;

                case "Main office address":
                    userMessage = "Main office address";
                    execute(createMarkupButtons("Back"), null);
                    List<String> addressOffice2 = AboutBankService.getAddressOfficeBank("en");
                    execute(addressOffice2.get(0), addressOffice2.get(1), addressOffice2.get(2), addressOffice2.get(3));
                    map.put(userChatId, "Addresses");
                    break;

                case "Addresses of retail outlets":
                    userMessage = "Addresses of retail outlets";
                    execute(createMarkupButtons("Back"), null);
                    List<String> addressOfficeg3 = ATMSServices.getATMS("en");
                    execute(addressOfficeg3.get(0), addressOfficeg3.get(1), addressOfficeg3.get(2), addressOfficeg3.get(3));
                    map.put(userChatId,"Addresses");
                    break;


                case "Feedback":
                    userMessage = "Feedback";
                    execute(createMarkupButtons("Call me", "Leave a review/suggestion", "Commercial proposal", "Bank vacancies", "Back"), null);
                    map.put(userChatId, "\uD83C\uDDEC\uD83C\uDDE7 English");
                    break;

                case"Leave a review/suggestion":
                    userMessage = "Leave a review/suggestion";
                    execute(createMarkupButtons( "Soon..."), null);//todo davomi bor
                    map.put(userChatId,"Feedback");

                    break;
                case "Call me":
                    // todo telefon raqamga tekshirish
                    Optional<User> userEn = userService.findUserByChatId(update);
                    User userEn1 = new User();
                    if (userEn.isPresent()){
                        userEn1 = userEn.get();
                    }
                    if (userEn1.getPhoneNumber() == null || userEn1.getPhoneNumber().equals("") || userEn1.getPhoneNumber().trim().equals("")){
                        SendMessage sendMessage = new SendMessage();
                        sendMessage.setChatId(String.valueOf(userChatId));
                        sendMessage.setText("Please send your contact");
                        shareContact(sendMessage, "en");
                        map.put(userChatId, "Call me");
                    } else {
                        String message = userEn1.getUsername() + "\n" + userEn1.getPhoneNumber() + "\n\n" + update.getMessage().getText();
                        userMessage = "The operators will call you back soon";
                        String groupChatId = this.groupChatId;
                        execute(groupChatId, message);
                        execute(new SendMessage(String.valueOf(this.userChatId), userMessage));
                        //backStepMessage = "\uD83C\uDDF7\uD83C\uDDFA Русский";
                        update.getMessage().setText("Feedback");
                        onUpdateReceived(update);
                    }
                    break;

                case "Commercial proposal":
                    userMessage = "Commercial proposal";
                    execute(createMarkupButtons("Back"), null);
                    List<String> commercialProposalEn = AboutVacanciesService.getCommercialProposal("en");
                    execute1(commercialProposalEn.get(0), commercialProposalEn.get(1));
                    map.put(userChatId,"Feedback");
                    break;

                case "Bank vacancies":
                    userMessage = "Bank vacancies";
                    execute(createMarkupButtons("Back"), null);
                    List<String> vacanciesEn = AboutVacanciesService.getAboutVacancies("en");
                    execute(vacanciesEn.get(0), vacanciesEn.get(1), vacanciesEn.get(2), vacanciesEn.get(3), vacanciesEn.get(4));
                    map.put(userChatId,"Feedback");
                    break;


                /**************************************************Cards***********************************************/
                case "Cards":
                    userMessage = "Cards:";
                    List<String> cardNameEn = CardService.getCardName("en");
                    execute(createMarkupButtons(cardNameEn.get(0), cardNameEn.get(1), cardNameEn.get(2), "Back"), null);
                    map.put(userChatId, "Individuals");
                    break;
                case "Installment card ANOR":
                    userMessage = "Installment card ANOR";
                    execute(createMarkupButtons("Back"), null);
                    List<String> installmentCardEn = CardService.getInstallmentCard("en");
                    execute(installmentCardEn.get(0), installmentCardEn.get(1), installmentCardEn.get(2), installmentCardEn.get(3), installmentCardEn.get(4));
                    map.put(userChatId, "Cards");
                    break;
                case "TRIA card":
                    userMessage = "TRIA card";
                    execute(createMarkupButtons("Back"), null);
                    List<String> availableCreditCardEn = CardService.getTriaCard("en");
                    execute(availableCreditCardEn.get(0), availableCreditCardEn.get(1), availableCreditCardEn.get(2), availableCreditCardEn.get(3), availableCreditCardEn.get(4));
                    map.put(userChatId,"Cards");
                    break;
                case "\"MASTERCARD - HUMO\" 2 in 1":
                    userMessage = "\"MASTERCARD - HUMO\" 2 in 1";
                    execute(createMarkupButtons("Back"), null);
                    List<String> masterCardEn = CardService.getMasterCard("en");
                    execute(masterCardEn.get(0), masterCardEn.get(1), masterCardEn.get(2), masterCardEn.get(3), masterCardEn.get(4));
                    map.put(userChatId, "Cards");
                    break;
                /**************************************************Credits***********************************************/
                case "Credits":
                    userMessage = "Credits:";
                    List<String> creditNameEn = CreditService.getCreditsName("en");
                    execute(createMarkupButtons(creditNameEn.get(0), creditNameEn.get(1), creditNameEn.get(2), creditNameEn.get(3), "Back"), null);
                    map.put(userChatId, "Individuals");
                    break;
                case "Online microloan":
                    userMessage = "Online microloan";
                    execute(createMarkupButtons("Back"), null);
                    List<String> microloanCreditEn = CreditService.getMicroloanCredit("en");
                    execute(microloanCreditEn.get(0), microloanCreditEn.get(1), microloanCreditEn.get(2), microloanCreditEn.get(3), microloanCreditEn.get(4));
                    map.put(userChatId, "Credits");
                    break;
                case "Available money 2.0":
                    userMessage = "Available money";
                    execute(createMarkupButtons("Back"), null);
                    List<String> availableCreditEn = CreditService.getAvailableCredit("en");
                    execute(availableCreditEn.get(0), availableCreditEn.get(1), availableCreditEn.get(2), availableCreditEn.get(3), availableCreditEn.get(4));
                    map.put(userChatId, "Credits");
                    break;
                case "Installments for purchases":
                    userMessage = "Installments for purchases";
                    execute(createMarkupButtons("Back"), null);
                    List<String> installmentCreditEn = CreditService.getInstallmentsCredit("en");
                    execute(installmentCreditEn.get(0), installmentCreditEn.get(1), installmentCreditEn.get(2), installmentCreditEn.get(3), installmentCreditEn.get(4));
                    map.put(userChatId, "Credits");
                    break;
                case "Overdraft for all occasions":
                    userMessage = "Overdraft for all occasions";
                    execute(createMarkupButtons("Back"), null);
                    List<String> overdraftCreditEn = CreditService.getOverdraftCredit("en");
                    execute(overdraftCreditEn.get(0), overdraftCreditEn.get(1), overdraftCreditEn.get(2), overdraftCreditEn.get(3), overdraftCreditEn.get(4));
                    map.put(userChatId, "Credits");
                    break;
                /**************************************************Deposits***********************************************/
                case "Deposits":
                    userMessage = "Deposit:";
                    List<String> depositsNameEn = DepositService.getDepositName("en");
                    execute(createMarkupButtons(depositsNameEn.get(0), depositsNameEn.get(1), depositsNameEn.get(3), "Back"), null);
                    map.put(userChatId, "Individuals");
                    break;
                case "\"SMART 2.0\" deposit":
                    userMessage = "\"SMART 2.0\" deposit";
                    execute(createMarkupButtons("Back"), null);
                    List<String> smartDepositEn = DepositService.getSmartDeposit("en");
                    execute(smartDepositEn.get(0), smartDepositEn.get(1), smartDepositEn.get(2), smartDepositEn.get(3), smartDepositEn.get(4));
                    map.put(userChatId,"Deposits");
                    break;
                case "\"SHOSHILMASDAN 2.0\" deposit":
                    userMessage = "\"SHOSHILMASDAN 2.0\" deposit";
                    execute(createMarkupButtons("Back"), null);
                    List<String> shoshilmasdanDepositEn = DepositService.getShoshilmasdanDeposit("en");
                    execute(shoshilmasdanDepositEn.get(0), shoshilmasdanDepositEn.get(1), shoshilmasdanDepositEn.get(2), shoshilmasdanDepositEn.get(3), shoshilmasdanDepositEn.get(4));
                    map.put(userChatId, "Deposits");
                    break;
                case "«Go» deposit":
                    userMessage = "«Go» deposit";
                    execute(createMarkupButtons("Back"), null);
                    List<String> goDepositEn = DepositService.getGoDeposit("en");
                    execute(goDepositEn.get(0), goDepositEn.get(1), goDepositEn.get(2), goDepositEn.get(3), goDepositEn.get(4));
                    map.put(userChatId, "Deposits");
                    break;
                case "Autocredit":
                    userMessage = "Autocredit";
                    execute(createMarkupButtons("Back"), null);
                    List<String> autoCreditEn = AutoCredit.getAutoCredit("en");
                    execute1(autoCreditEn.get(0), autoCreditEn.get(1));
                    map.put(userChatId,"Individuals");
                    break;
                case "Addresses of strongholds (Regions)":
                    userMessage = "Addresses of strongholds (Regions)";
                    execute(createMarkupButtons("Andijan", "Angren", "Bukhara", "Jizzakh", "Karshi", "Namangan", " Navoi", "The Republic Of Karakalpakstan", "Samarkand", "Sirdarya",
                            "Termez", "Urgench", "Chirchik", "Fergana", "Back"), null);
                    map.put(userChatId, "Addresses");
                    break;
                case "Andijan":
                    userMessage = "Andijan";
                    execute(createMarkupButtons("Back"), null);
                    List<String> getandijanEn = AboutBankService.getAndijan("en");
                    execute(getandijanEn.get(0), getandijanEn.get(1), getandijanEn.get(2), getandijanEn.get(3));
                    map.put(userChatId, "Addresses of strongholds (Regions)");
                    break;
                case "Angren":
                    userMessage = "Angren";
                    execute(createMarkupButtons("Back"), null);
                    List<String> getangrenEn = AboutBankService.getAngren("en");
                    execute(getangrenEn.get(0), getangrenEn.get(1), getangrenEn.get(2), getangrenEn.get(3));
                    map.put(userChatId, "Addresses of strongholds (Regions)");
                    break;
                case "Bukhara":
                    userMessage = "Bukhara";
                    execute(createMarkupButtons("Back"), null);
                    List<String> getbuxaraEn = AboutBankService.getBuxara("en");
                    execute(getbuxaraEn.get(0), getbuxaraEn.get(1), getbuxaraEn.get(2), getbuxaraEn.get(3));
                    map.put(userChatId, "Addresses of strongholds (Regions)");
                    break;
                case "Jizzakh":
                    userMessage = "Jizzakh";
                    execute(createMarkupButtons("Back"), null);
                    List<String> getjizzaxEn = AboutBankService.getJizzax("en");
                    execute(getjizzaxEn.get(0), getjizzaxEn.get(1), getjizzaxEn.get(2), getjizzaxEn.get(3));
                    map.put(userChatId, "Addresses of strongholds (Regions)");
                    break;
                case "Karshi":
                    userMessage = "Karshi";
                    execute(createMarkupButtons("Back"), null);
                    List<String> getkarshiEn = AboutBankService.getKarshi("en");
                    execute(getkarshiEn.get(0), getkarshiEn.get(1), getkarshiEn.get(2), getkarshiEn.get(3));
                    map.put(userChatId,"Addresses of strongholds (Regions)");
                    break;
                case "Namangan":
                    userMessage = "Namangan";
                    execute(createMarkupButtons("Back"), null);
                    List<String> getnamanganEn = AboutBankService.getNamangan("en");
                    execute(getnamanganEn.get(0), getnamanganEn.get(1), getnamanganEn.get(2), getnamanganEn.get(3));
                    map.put(userChatId, "Addresses of strongholds (Regions)");
                    break;
                case "Navoi":
                    userMessage = "Navoi";
                    execute(createMarkupButtons("Back"), null);
                    List<String> getnavaiEn = AboutBankService.getNavai("uz");
                    execute(getnavaiEn.get(0), getnavaiEn.get(1), getnavaiEn.get(2), getnavaiEn.get(3));
                    map.put(userChatId,"Addresses of strongholds (Regions)");
                    break;
                case "The Republic Of Karakalpakstan":
                    userMessage = " The Republic Of Karakalpakstan";
                    execute(createMarkupButtons("Back"), null);
                    List<String> getnukusEn = AboutBankService.getKarakalpak("en");
                    execute(getnukusEn.get(0), getnukusEn.get(1), getnukusEn.get(2), getnukusEn.get(3));
                    map.put(userChatId,"Addresses of strongholds (Regions)");
                    break;
                case "Samarkand":
                    userMessage = "Samarkand";
                    execute(createMarkupButtons("Back"), null);
                    List<String> getsamEn = AboutBankService.getSamarkand("en");
                    execute(getsamEn.get(0), getsamEn.get(1), getsamEn.get(2), getsamEn.get(3));
                    map.put(userChatId, "Addresses of strongholds (Regions)");
                    break;
                case "Sirdarya":
                    userMessage = "Sirdarya";
                    execute(createMarkupButtons("Back"), null);
                    List<String> getsirdaryaEn = AboutBankService.getSirdarya("en");
                    execute(getsirdaryaEn.get(0), getsirdaryaEn.get(1), getsirdaryaEn.get(2), getsirdaryaEn.get(3));
                    map.put(userChatId, "Addresses of strongholds (Regions)");
                    break;
                case "Termez":
                    userMessage = "Termez";
                    execute(createMarkupButtons("Back"), null);
                    List<String> gettermizEn = AboutBankService.getTermiz("en");
                    execute(gettermizEn.get(0), gettermizEn.get(1), gettermizEn.get(2), gettermizEn.get(3));
                    map.put(userChatId, "Addresses of strongholds (Regions)");
                    break;
                case "Urgench":
                    userMessage = "Urgench";
                    execute(createMarkupButtons("Back"), null);
                    List<String> geturgenchEn = AboutBankService.getUrgench("en");
                    execute(geturgenchEn.get(0), geturgenchEn.get(1), geturgenchEn.get(2), geturgenchEn.get(3));
                    map.put(userChatId, "Addresses of strongholds (Regions)");
                    break;
                case "Chirchik":
                    userMessage = "Chirchik";
                    execute(createMarkupButtons("Back"), null);
                    List<String> getchirchikEn = AboutBankService.getChirchik("en");
                    execute(getchirchikEn.get(0), getchirchikEn.get(1), getchirchikEn.get(2), getchirchikEn.get(3));
                    map.put(userChatId, "Addresses of strongholds (Regions)");
                    break;

                case "Fergana":
                    userMessage = "Fergana";
                    execute(createMarkupButtons("Back"), null);
                    List<String> getferganaEn = AboutBankService.getFergana("en");
                    execute(getferganaEn.get(0), getferganaEn.get(1), getferganaEn.get(2), getferganaEn.get(3));
                    map.put(userChatId, "Addresses of strongholds (Regions)");
                    break;
                /******************************************Legal entities****************************************/
                case "Legal entities":
                    userMessage = "Legal entities";
                    execute(createMarkupButtons("Settlement service(sum)",
                            "Settlement service(currency)", "Deposits⠀", "Salary project",
                            "I want to receive a commercial offer", "“Anor Merchant” Partner Program",
                            "Order a corporate card", "Back"), null);
                    map.put(userChatId, "Consultation");
                    break;
                case "Settlement service(sum)":
                    userMessage = "Settlement service(sum)";
                    execute(createMarkupButtons("Legal entities⠀", "Individual entrepreneur", "Back"), null);
                    map.put(userChatId, "Legal entities");
                    break;
                case "Settlement service(currency)":
                    userMessage = "Settlement service(currency)";
                    execute(createMarkupButtons("Legal entities⠀⠀", "Individual entrepreneur⠀", "Back"), null);
                    map.put(userChatId, "Legal entities");
                    break;
                case "Legal entities⠀":
                    userMessage = "Legal entities";
                    List<String> getBusinessRate2 = SettlementServices.getBusinessRates("en");
                    execute(createMarkupButtons("Back"), null);
                    execute(getBusinessRate2.get(0), getBusinessRate2.get(1), getBusinessRate2.get(2), getBusinessRate2.get(3));
                    map.put(userChatId, "Settlement service(sum)");
                    break;
                case "Legal entities⠀⠀":
                    userMessage = "Legal entities";
                    List<String> getBusinessRateValyut2 = SettlementServices.getBusinessRates("en");
                    execute(createMarkupButtons("Back"), null);
                    execute(getBusinessRateValyut2.get(0), getBusinessRateValyut2.get(1), getBusinessRateValyut2.get(2), getBusinessRateValyut2.get(3));
                    map.put(userChatId, "Settlement service(currency)");
                    break;
                case "Individual entrepreneur":
                    userMessage = "Individual entrepreneur";
                    List<String> getBusinessRates2 = SettlementServices.getBusinessRates("en");
                    execute(createMarkupButtons("Back"), null);
                    execute(getBusinessRates2.get(0), getBusinessRates2.get(1), getBusinessRates2.get(2), getBusinessRates2.get(3));
                    map.put(userChatId, "Settlement service(sum)");
                    break;
                case "Individual entrepreneur⠀":
                    userMessage = "Individual entrepreneur";
                    List<String> getBusinessRatesValyut2 = SettlementServices.getBusinessRates("en");
                    execute(createMarkupButtons("Back"), null);
                    execute(getBusinessRatesValyut2.get(0), getBusinessRatesValyut2.get(1), getBusinessRatesValyut2.get(2), getBusinessRatesValyut2.get(3));
                    map.put(userChatId, "Settlement service(currency)");
                    break;
                case "Legal entities⠀⠀⠀":
                    userMessage = "Legal entities";
                    execute(createMarkupButtons("«Overnight»⠀Deposit", "Term deposit", "Back"), null);
                    map.put(userChatId, "Deposits⠀");
                    break;
                case "«Overnight» Deposit":
                    userMessage = "«Overnight»Deposit";
                    List<String> depositName2 = BusinessDeposit.getOverNightDeposit("en");
                    execute(createMarkupButtons("Back"), null);
                    execute(depositName2.get(0), depositName2.get(1), depositName2.get(2), depositName2.get(3));
                    map.put(userChatId,"Legal entities⠀⠀⠀");
                    break;
                case "Term deposit":
                    userMessage = "Term deposit";
                    List<String> deposittName2 = BusinessDeposit.getFixedTermDeposit("en");
                    execute(createMarkupButtons("Back"), null);
                    execute(deposittName2.get(0), deposittName2.get(1), deposittName2.get(2), deposittName2.get(3));
                    map.put(userChatId, "Legal entities⠀⠀⠀");
                    break;
                case "Individual entrepreneur⠀⠀":
                    userMessage = "Individual entrepreneur";
                    execute(createMarkupButtons("«Overnight» Deposit", "Term deposit", "Back"), null);
                    map.put(userChatId, "Deposits⠀");
                    break;
                case "«Overnight»⠀Deposit":
                    userMessage = "«Overnight»Deposit";
                    List<String> depositNamee2 = BusinessDeposit.getOverNightDeposit("en");
                    execute(createMarkupButtons("Back"), null);
                    execute(depositNamee2.get(0), depositNamee2.get(1), depositNamee2.get(2), depositNamee2.get(3));
                    map.put(userChatId, "Individual entrepreneur⠀⠀");
                    break;
                case "Urgent⠀deposit":
                    userMessage = "Urgent⠀deposit";
                    List<String> deposittNamee2 = BusinessDeposit.getFixedTermDeposit("en");
                    execute(createMarkupButtons("Back"), null);
                    execute(deposittNamee2.get(0), deposittNamee2.get(1), deposittNamee2.get(2), deposittNamee2.get(3));
                    map.put(userChatId, "Individual entrepreneur⠀⠀");
                    break;
                case "Salary project":
                    userMessage = "“Anor Merchant” Affiliate Program";
                    List<String> payrollProgramme2 = SettlementServices.getBusinessPayrollprogramme("en");
                    execute(createMarkupButtons("Back"), null);
                    execute(payrollProgramme2.get(0), payrollProgramme2.get(1), payrollProgramme2.get(2), payrollProgramme2.get(3));
                    map.put(userChatId, "Legal entities");
                    break;
                case "I want to receive a commercial offer":
                    userMessage = "I want to receive a commercial offer";
                    List<String> documents2 = BusinessDeposit.getDocuments("en");
                    execute(createMarkupButtons("Back"), null);
                    execute(documents2.get(0), documents2.get(1), documents2.get(2), documents2.get(3));
                    map.put(userChatId,"Legal entities");
                    break;
                case "“Anor Merchant” Partner Program":
                    userMessage = "“Anor Merchant” Partner Program";
                    List<String> businesPartner2 = BusinessPartner.businessPartner("en");
                    execute(createMarkupButtons("Back"), null);
                    execute(businesPartner2.get(0), businesPartner2.get(1), businesPartner2.get(2), businesPartner2.get(3), businesPartner2.get(4));
                    map.put(userChatId, "Legal entities");
                    break;
                case "Order a corporate card":
                    userMessage = "Order a corporate card";
                    List<String> corporateCards2 = SettlementServices.getBusinessCorporateCards("en");
                    execute(createMarkupButtons("Back"), null);
                    execute(corporateCards2.get(0), corporateCards2.get(1), corporateCards2.get(2), corporateCards2.get(3));
                    map.put(userChatId, "Legal entities");
                    break;
                case "Deposits⠀":
                    userMessage = "Deposits";
                    execute(createMarkupButtons("Legal entities⠀⠀⠀", "Individual entrepreneur⠀⠀", "Back"), null);
                    map.put(userChatId, "Legal entities");
                    break;
            }
        }
        }

    private void executeGroup(Update update, String rank) {
        SendMessage sendMessage;
        SendPhoto sendPhoto;
//        SendVoice sendVoice;
        SendDocument sendDocument;
        SendAudio sendAudio;

        if (update.getMessage().hasText()) {
            sendMessage = new SendMessage(String.valueOf(groupChatId), update.getMessage().getText());
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        } else {
            sendMessage = new SendMessage(String.valueOf(userChatId), "Пока вы можете отправить только текст!");
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    private void execute(ReplyKeyboardMarkup replyKeyboardMarkup, InlineKeyboardMarkup inlineKeyboardMarkup) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(this.userChatId));
        sendMessage.setText(this.userMessage);
        sendMessage.enableHtml(true);
        if (replyKeyboardMarkup != null)
            sendMessage.setReplyMarkup(replyKeyboardMarkup);
        else if (inlineKeyboardMarkup != null)
            sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void execute(String groupChatId, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(groupChatId));
        sendMessage.setText(message);
        sendMessage.enableHtml(true);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void execute(String photoUrl, String text1, String text2, String buttonText, String buttonUrl) {
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setPhoto(new InputFile(photoUrl));
        sendPhoto.setChatId(String.valueOf(this.userChatId));
        sendPhoto.setCaption(text1 + "\n" + "\n" + text2);
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> button1 = new ArrayList<>();
        List<List<InlineKeyboardButton>> button2 = new ArrayList<>();
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(buttonText);
        button.setUrl(buttonUrl);
        button1.add(button);
        button2.add(button1);
        markup.setKeyboard(button2);
        sendPhoto.setReplyMarkup(markup);
        try {
            execute(sendPhoto);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void execute1(String photoUrl, String text1) {
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setPhoto(new InputFile(photoUrl));
        sendPhoto.setChatId(String.valueOf(this.userChatId));
        sendPhoto.setCaption(text1);
        try {
            execute(sendPhoto);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void execute(String photoUrl, String text, String buttonText, String buttonUrl) {
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setPhoto(new InputFile(photoUrl));
        sendPhoto.setChatId(String.valueOf(this.userChatId));
        sendPhoto.setCaption(text);
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> button1 = new ArrayList<>();
        List<List<InlineKeyboardButton>> button2 = new ArrayList<>();
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(buttonText);
        button.setUrl(buttonUrl);
        button1.add(button);
        button2.add(button1);
        markup.setKeyboard(button2);
        sendPhoto.setReplyMarkup(markup);
        try {
            execute(sendPhoto);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void execute2(String buttonText, String buttonUrl) {
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(String.valueOf(this.userChatId));
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> button1 = new ArrayList<>();
        List<List<InlineKeyboardButton>> button2 = new ArrayList<>();
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(buttonText);
        button.setUrl(buttonUrl);
        button1.add(button);
        button2.add(button1);
        markup.setKeyboard(button2);
        sendPhoto.setReplyMarkup(markup);
        try {
            execute(sendPhoto);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    @Override
    public String getBotUsername() {
        return this.username;
    }

    @Override
    public String getBotToken() {
        return this.token;
    }

    public void shareContact(SendMessage sendMessage, String lang){
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        // new list
        List<KeyboardRow> keyboard = new ArrayList<>();

        // first keyboard line
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        KeyboardButton keyboardButton = new KeyboardButton();
        String q = lang.equals("en") ? "Share your contact" : (lang.equals("uz") ? "Kontaktingizni yuboring" : "Пожалуйста, отправьте свой контакт");;
        keyboardButton.setText(q);
        userMessage="Kontakt yub";
        keyboardButton.setRequestContact(true);
        keyboardFirstRow.add(keyboardButton);

        // add array to list
        keyboard.add(keyboardFirstRow);

        // add list to our keyboard
        replyKeyboardMarkup.setKeyboard(keyboard);

        try {
            execute(sendMessage);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

