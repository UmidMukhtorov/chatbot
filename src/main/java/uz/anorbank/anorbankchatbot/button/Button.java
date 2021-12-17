package uz.anorbank.anorbankchatbot.button;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sahobiddin Abbosaliyev
 * 12/8/2021
 */
public class Button {

    private static InlineKeyboardMarkup main(String secondButton, String url) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        inlineKeyboardButton1.setUrl(url);
        inlineKeyboardButton1.setText(secondButton);

        return getInlineKeyboardMarkup(inlineKeyboardMarkup, inlineKeyboardButton1);
    }

    private static InlineKeyboardMarkup getInlineKeyboardMarkup(
            InlineKeyboardMarkup inlineKeyboardMarkup,
            InlineKeyboardButton inlineKeyboardButton2
    ) {
        inlineKeyboardButton2.setCallbackData("test");

        List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();

        rowList.add(keyboardButtonsRow);
        keyboardButtonsRow = new ArrayList<>();
        keyboardButtonsRow.add(inlineKeyboardButton2);
        rowList.add(keyboardButtonsRow);

        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }

//    private static ReplyKeyboard test(String text,String url){
//
//    }
}
