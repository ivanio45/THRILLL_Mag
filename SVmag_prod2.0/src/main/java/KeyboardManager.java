import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class KeyboardManager {
    public SendMessage createMenuKeyboard(long chatId) {
        return createKeyboard(chatId, "Добро пожаловать в наш магазин!", List.of("доступные товары", "корзина"));
    }

    public SendMessage createMenuOfProductKeyboard(long chatId) {
        return createKeyboard(chatId, "Выберете Раздел", List.of("футболки","штаны","обувь","вернуться в меню", "корзина"));
        //return createKeyboard(chatId, "Доступные товары:\n" + String.join("\n", products) + "\nчтобы добавить товар в корзину отправьте его номер\n", options);
    }

    public SendMessage createProductKeyboard(long chatId, List<ClothingItem> items, int indPage) {
        List<String> options = new ArrayList<>();
        if (indPage != 1) {
            options.add("следующая страница");
        }
        if (indPage != 0) {
            options.add("предыдущая страница");
        }
        options.add("вернуться назад");
        options.add("корзина");
        String str=new String();
        int indexOfItem=1;
        for(ClothingItem item:items){
            str+=String.valueOf(indexOfItem++)+".";
            str+=item.getDescription();
            str+="\n";
        }
        return createKeyboard(chatId, "Доступные товары:\n" + str + "\nчтобы добавить товар в корзину отправьте его номер\n", options);
    }

    public SendMessage createCartKeyboard(long chatId, List<ClothingItem> cartItems) {
        String str=new String();
        for(ClothingItem item:cartItems){
            str+=item.getDescription();
            str+="\n";
        }
        String message = "Ваша корзина:\n" + (cartItems.isEmpty() ? "Корзина пуста." : str);
        return createKeyboard(chatId, message, List.of("доступные товары", "очистить корзину", "вернуться в меню"));
    }

    private SendMessage createKeyboard(long chatId, String text, List<String> buttons) {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setSelective(true);
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();

        int cntRows=0;
        for (String button : buttons) {
            cntRows++;
            row.add(new KeyboardButton(button));
            if(cntRows==3){
                keyboard.add(row);
                row = new KeyboardRow();
            }
        }
        keyboard.add(row);
        keyboardMarkup.setKeyboard(keyboard);

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        message.setReplyMarkup(keyboardMarkup);


        return message;
    }
}