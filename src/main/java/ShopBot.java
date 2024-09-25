import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;


import java.util.ArrayList;
import java.util.List;

public class ShopBot extends TelegramLongPollingBot {
    int ind=0;
    private final List<String> Allproducts = new ArrayList<>();
    private final List<String> products = new ArrayList<>();
    private final List<String> products2 = new ArrayList<>();
    private final List<List<String>> Products = new ArrayList<>();
    private final List<String> cart = new ArrayList<>();

    public ShopBot() {
        // Добавим несколько товаров в магазин
        Allproducts.add("1. Товар A - $10");
        Allproducts.add("2. Товар B - $20");
        Allproducts.add("3. Товар C - $30");
        Allproducts.add("4. Товар d - $40");
        Allproducts.add("5. Товар e - $5050500");
        Allproducts.add("6. Товар f - $99");
        products.add("1. Товар A - $10");
        products.add("2. Товар B - $20");
        products.add("3. Товар C - $30");
        products2.add("4. Товар d - $40");
        products2.add("5. Товар e - $5050500");
        products2.add("6. Товар f - $99");
        Products.add(products);
        Products.add(products2);
    }

    @Override
    public String getBotUsername() {
        return "SVmag"; // Укажите имя вашего бота
    }

    @Override
    public String getBotToken() {
        return ""; // Укажите токен вашего бота
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();


            if (messageText.equalsIgnoreCase("/start")) {
                MenuKeyboard(chatId);
            }
            else if (messageText.equalsIgnoreCase("вернуться в меню")) {
                ind=0;
                MenuKeyboard(chatId);
            }
            else if (messageText.equalsIgnoreCase("доступные товары")) {
                ProductKeyboard(chatId, Products, 0);
            }
            else if (messageText.equalsIgnoreCase("следующая страница")) {
                ProductKeyboard(chatId, Products, ++ind);
            }
            else if (messageText.equalsIgnoreCase("предыдущая страница")) {
                ProductKeyboard(chatId, Products, --ind);
            }
            else if (messageText.equalsIgnoreCase("корзина")) {
                CartKeyboard(chatId);
            }
            else if (messageText.equalsIgnoreCase("очистить корзину")) {
                cart.clear();
                sendMsg(chatId, "Корзина очищена!");
            }
            else{
                try {
                    int productIndex = Integer.parseInt(messageText.split("\\.")[0]) - 1;
                    if (productIndex >= 0 && productIndex < Allproducts.size()) {
                        cart.add(Allproducts.get(productIndex));
                        sendMsg(chatId, "Товар добавлен в корзину.");
                    } else {
                        sendMsg(chatId, "Некорректный номер товара. Попробуйте еще раз.");
                    }
                } catch (Exception e) {
                    sendMsg(chatId, "Не удалось распознать команду. Попробуйте еще раз.");
                }
            }

        }
    }

    private void ProductKeyboard (long chatId, List<List<String>> Products, int ind) {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setSelective(true);
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setOneTimeKeyboard(false);


        // Создаем строки клавиатуры
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();

        if(ind<Products.size()-1) {
            row.add(new KeyboardButton("следующая страница"));
            keyboard.add(row);
        }

        row = new KeyboardRow();

        if(ind!=0) {
            row.add(new KeyboardButton("предыдущая страница"));
            keyboard.add(row);
        }


        row = new KeyboardRow();

        row.add(new KeyboardButton("вернуться в меню"));
        row.add(new KeyboardButton("корзина"));
        keyboard.add(row);



        // Устанавливаем клавиатуру
        keyboardMarkup.setKeyboard(keyboard);

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Доступные товары:\n" + String.join("\n", Products.get(ind)) + "\nВведите номер товара, чтобы добавить его в корзину.");
        message.setReplyMarkup(keyboardMarkup);
        try {
            execute(message); // Отправка сообщения
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void MenuKeyboard (long chatId) {


        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setSelective(true);
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setOneTimeKeyboard(false);


        // Создаем строки клавиатуры
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();

        row.add(new KeyboardButton("доступные товары"));
        keyboard.add(row);

        row = new KeyboardRow();

        row.add(new KeyboardButton("корзина"));
        keyboard.add(row);

        // Устанавливаем клавиатуру
        keyboardMarkup.setKeyboard(keyboard);

        SendMessage message = new SendMessage();

        message.setChatId(chatId);
        message.setText("Добро пожаловать в наш магазин!");
        message.setReplyMarkup(keyboardMarkup);
        try {
            execute(message); // Отправка сообщения
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void CartKeyboard (long chatId) {


        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setSelective(true);
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setOneTimeKeyboard(false);


        // Создаем строки клавиатуры
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();

        row.add(new KeyboardButton("доступные товары"));
        keyboard.add(row);

        row = new KeyboardRow();

        row.add(new KeyboardButton("очистить корзину"));
        keyboard.add(row);


        // Устанавливаем клавиатуру
        keyboardMarkup.setKeyboard(keyboard);

        SendMessage message = new SendMessage();

        message.setChatId(chatId);
        message.setText("Ваша корзина:\n" + (cart.isEmpty() ? "Корзина пуста." : String.join("\n", cart)));
        message.setReplyMarkup(keyboardMarkup);
        try {
            execute(message); // Отправка сообщения
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendMsg(long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);




        try {
            execute(message); // Отправка сообщения
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
