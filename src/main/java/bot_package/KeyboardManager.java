package bot_package;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import java.util.ArrayList;
import java.util.List;
import non_bot_package.*;


public class KeyboardManager {

    public SendMessage createMenuKeyboard(long chatId, String textOfMessage) {
        return createKeyboard(chatId, List.of("доступные товары\uD83D\uDECD️", "корзина\uD83D\uDED2"), textOfMessage);
    }

    public SendMessage createMenuOfProductKeyboard(long chatId, String textOfMessage) {
        return createKeyboard(chatId, List.of("футболки\uD83D\uDC55", "худи\uD83E\uDD77", "кепки\uD83E\uDDE2","лонгсливы\uD83D\uDC7B", "вернуться в меню⏪", "корзина\uD83D\uDED2"), textOfMessage);
    }

    public SendMessage createProductKeyboard(long chatId, int indPage, List<ClothingItem> ClothingItems, String textOfMessage) {
        List<String> options = new ArrayList<>();
        if (indPage != 1 && ClothingItems.size() > 2) {
            options.add("следующая страница➡️");
        }
        if (indPage != 0) {
            options.add("предыдущая страница⬅️");
        }
        options.add("вернуться назад⏪");
        options.add("корзина\uD83D\uDED2");

        return createKeyboard(chatId, options, textOfMessage);
    }

    public SendMessage createCartKeyboard(long chatId, String textOfMessage) {
        return createKeyboard(chatId, List.of("доступные товары\uD83D\uDECD️", "очистить корзину\uD83D\uDDD1️", "вернуться в меню⏪", "перейти к оплате\uD83D\uDCB5"), textOfMessage);
    }

    private SendMessage createKeyboard(long chatId, List<String> buttons,String textOfMessage) {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setSelective(true);
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();

        int countOfRows=0;
        for (String button : buttons) {
            row.add(new KeyboardButton(button));
            countOfRows++;
            if (countOfRows == 3) {
                keyboard.add(row);
                row=new KeyboardRow();
                countOfRows=0;
            }
        }
        keyboard.add(row);
        keyboardMarkup.setKeyboard(keyboard);

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(textOfMessage);
        message.setReplyMarkup(keyboardMarkup);

        return message;
    }
}