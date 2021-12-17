package uz.anorbank.anorbankchatbot.service.base;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Sahobiddin Abbosaliyev
 * 12/7/2021
 */
public interface BaseBotService {
    default Long getUserChatId(Update update) {
        if (update.hasMessage()) return update.getMessage().getChatId();
        return update.getCallbackQuery().getMessage().getChatId();
    }

    default String getUserResponse(Update update) {
        if (update.hasMessage()) if (update.getMessage().hasText()) return update.getMessage().getText();
        else return "Nothing not found!";
        return update.getCallbackQuery().getData();
    }


    default ReplyKeyboardMarkup chooseYourLanguage() {
        return createMarkupButtons(
                "\uD83C\uDDFA\uD83C\uDDFF O'zbekcha",
                "\uD83C\uDDF7\uD83C\uDDFA Русский",
                "\uD83C\uDDEC\uD83C\uDDE7 English"
        );
    }


    default ReplyKeyboardMarkup createMarkupButtons(String firstButton, String secondButton) {
        ReplyKeyboardMarkup replyKeyboardMarkup = makeReplyMarkup();
        KeyboardRow keyboardRow = new KeyboardRow();
        List<KeyboardRow> rowList = new ArrayList<>();

        keyboardRow.add(firstButton);
        keyboardRow.add(secondButton);
        rowList.add(keyboardRow);
        keyboardRow = new KeyboardRow();
        rowList.add(keyboardRow);
        replyKeyboardMarkup.setKeyboard(rowList);
        return replyKeyboardMarkup;
    }



    default ReplyKeyboardMarkup createMarkupButton(String firstButton, String secondButton) {
        ReplyKeyboardMarkup replyKeyboardMarkup = makeReplyMarkup();
        KeyboardRow keyboardRow = new KeyboardRow();
        List<KeyboardRow> rowList = new ArrayList<>();

        keyboardRow.add(firstButton);
        keyboardRow.add(secondButton);
        rowList.add(keyboardRow);
        keyboardRow = new KeyboardRow();
        rowList.add(keyboardRow);
        replyKeyboardMarkup.setKeyboard(rowList);
        return replyKeyboardMarkup;
    }

    default ReplyKeyboardMarkup createMarkupButtons(String firstButton) {
        ReplyKeyboardMarkup replyKeyboardMarkup = makeReplyMarkup();
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add(firstButton);
        replyKeyboardMarkup.setKeyboard(Collections.singletonList(keyboardRow));
        return replyKeyboardMarkup;
    }

    default ReplyKeyboardMarkup makeReplyMarkup() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        replyKeyboardMarkup.setSelective(true);

        return replyKeyboardMarkup;
    }

    default ReplyKeyboardMarkup shareYourContact(String shareYourContact) {
        ReplyKeyboardMarkup replyKeyboardMarkup = makeReplyMarkup();
        KeyboardRow keyboardRow = new KeyboardRow();
        KeyboardButton keyboardButton = new KeyboardButton(shareYourContact);
        keyboardButton.setRequestContact(true);
        keyboardRow.add(keyboardButton);
        replyKeyboardMarkup.setKeyboard(Collections.singletonList(keyboardRow));
        return replyKeyboardMarkup;
    }

    default ReplyKeyboardMarkup createMarkupButtons(String firstButton, String secondButton, String thirdButton) {
        ReplyKeyboardMarkup replyKeyboardMarkup = makeReplyMarkup();
        KeyboardRow keyboardRow = new KeyboardRow();
        List<KeyboardRow> rowList = new ArrayList<>();

        keyboardRow.add(firstButton);
        keyboardRow.add(secondButton);
        rowList.add(keyboardRow);
        keyboardRow = new KeyboardRow();
        keyboardRow.add(thirdButton);
        rowList.add(keyboardRow);
        replyKeyboardMarkup.setKeyboard(rowList);
        return replyKeyboardMarkup;
    }

    default ReplyKeyboardMarkup createMarkupButtons(String firstButton, String secondButton, String thirdButton, String fourthCard) {
        ReplyKeyboardMarkup replyKeyboardMarkup = makeReplyMarkup();
        KeyboardRow keyboardRow = new KeyboardRow();
        List<KeyboardRow> rowList = new ArrayList<>();

        keyboardRow.add(firstButton);
        keyboardRow.add(secondButton);
        rowList.add(keyboardRow);
        keyboardRow = new KeyboardRow();
        keyboardRow.add(thirdButton);
        keyboardRow.add(fourthCard);
        rowList.add(keyboardRow);
        replyKeyboardMarkup.setKeyboard(rowList);
        return replyKeyboardMarkup;
    }

    default ReplyKeyboardMarkup createMarkupButtons(String firstButton, String secondButton, String thirdButton, String fourthCard, String fifth, String sixth, String seventh) {
        ReplyKeyboardMarkup replyKeyboardMarkup = makeReplyMarkup();
        List<KeyboardRow> rowList = new ArrayList<>();
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add(firstButton);
        keyboardRow.add(secondButton);
        rowList.add(keyboardRow);
        keyboardRow = new KeyboardRow();
        keyboardRow.add(thirdButton);
        keyboardRow.add(fourthCard);
        rowList.add(keyboardRow);
        keyboardRow = new KeyboardRow();
        keyboardRow.add(fifth);
        keyboardRow.add(sixth);
        rowList.add(keyboardRow);
        keyboardRow = new KeyboardRow();
        keyboardRow.add(seventh);
        rowList.add(keyboardRow);
        replyKeyboardMarkup.setKeyboard(rowList);
        return replyKeyboardMarkup;
    }

    default ReplyKeyboardMarkup createMarkupButtons(String firstButton, String secondButton, String thirdButton, String fourthCard, String fifth, String sixth) {
        ReplyKeyboardMarkup replyKeyboardMarkup = makeReplyMarkup();
        List<KeyboardRow> rowList = new ArrayList<>();
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add(firstButton);
        keyboardRow.add(secondButton);
        rowList.add(keyboardRow);
        keyboardRow = new KeyboardRow();
        keyboardRow.add(thirdButton);
        keyboardRow.add(fourthCard);
        rowList.add(keyboardRow);
        keyboardRow = new KeyboardRow();
        keyboardRow.add(fifth);
        keyboardRow.add(sixth);
        rowList.add(keyboardRow);
        replyKeyboardMarkup.setKeyboard(rowList);
        return replyKeyboardMarkup;
    }

    default ReplyKeyboardMarkup createMarkupButtons(String a, String b, String c, String d, String e, String f, String g,
                                                    String h, String i, String j, String k, String l, String m, String n,
                                                    String q) {
        ReplyKeyboardMarkup replyKeyboardMarkup = makeReplyMarkup();
        List<KeyboardRow> rowList = new ArrayList<>();
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add(a);
        keyboardRow.add(b);
        rowList.add(keyboardRow);
        keyboardRow = new KeyboardRow();
        keyboardRow.add(c);
        keyboardRow.add(d);
        rowList.add(keyboardRow);
        keyboardRow = new KeyboardRow();
        keyboardRow.add(e);
        keyboardRow.add(f);
        rowList.add(keyboardRow);
        keyboardRow = new KeyboardRow();
        keyboardRow.add(g);
        keyboardRow.add(h);
        rowList.add(keyboardRow);
        keyboardRow = new KeyboardRow();
        keyboardRow.add(i);
        keyboardRow.add(j);
        rowList.add(keyboardRow);
        keyboardRow = new KeyboardRow();
        keyboardRow.add(k);
        keyboardRow.add(l);
        rowList.add(keyboardRow);
        keyboardRow = new KeyboardRow();
        keyboardRow.add(m);
        keyboardRow.add(n);
        rowList.add(keyboardRow);
        keyboardRow = new KeyboardRow();
        keyboardRow.add(q);
        rowList.add(keyboardRow);
        replyKeyboardMarkup.setKeyboard(rowList);
        return replyKeyboardMarkup;
    }

    default ReplyKeyboardMarkup createMarkupButtons(String firstButton, String secondButton, String thirdButton, String fourthCard, String fifth, String sixth, String seventh, String eighth) {
        ReplyKeyboardMarkup replyKeyboardMarkup = makeReplyMarkup();
        KeyboardRow keyboardRow = new KeyboardRow();
        List<KeyboardRow> rowList = new ArrayList<>();
        keyboardRow.add(firstButton);
        keyboardRow.add(secondButton);
        rowList.add(keyboardRow);
        keyboardRow = new KeyboardRow();
        keyboardRow.add(thirdButton);
        keyboardRow.add(fourthCard);
        rowList.add(keyboardRow);
        keyboardRow = new KeyboardRow();
        keyboardRow.add(fifth);
        keyboardRow.add(sixth);
        rowList.add(keyboardRow);
        keyboardRow = new KeyboardRow();
        keyboardRow.add(seventh);
        keyboardRow.add(eighth);
        rowList.add(keyboardRow);
        keyboardRow = new KeyboardRow();
        rowList.add(keyboardRow);
        replyKeyboardMarkup.setKeyboard(rowList);

        return replyKeyboardMarkup;
    }

    default ReplyKeyboardMarkup createMarkupButtons(String firstButton, String secondButton, String thirdButton, String fourthCard, String fifth) {
        ReplyKeyboardMarkup replyKeyboardMarkup = makeReplyMarkup();
        KeyboardRow keyboardRow = new KeyboardRow();
        List<KeyboardRow> rowList = new ArrayList<>();

        keyboardRow.add(firstButton);
        keyboardRow.add(secondButton);
        rowList.add(keyboardRow);
        keyboardRow = new KeyboardRow();
        keyboardRow.add(thirdButton);
        keyboardRow.add(fourthCard);
        rowList.add(keyboardRow);
        keyboardRow = new KeyboardRow();
        keyboardRow.add(fifth);
        rowList.add(keyboardRow);
        replyKeyboardMarkup.setKeyboard(rowList);
        return replyKeyboardMarkup;
    }


}
