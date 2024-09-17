import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class ShopBot extends TelegramLongPollingBot {

    private final List<String> products = new ArrayList<>();
    private final List<String> cart = new ArrayList<>();

    public ShopBot() {
        // Добавим несколько товаров в магазин
        products.add("1. Товар A - $10");
        products.add("2. Товар B - $20");
        products.add("3. Товар C - $30");
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

            switch (messageText) {
                case "/start":
                    sendMsg(chatId, "Добро пожаловать в наш магазин! Введите /products, чтобы увидеть товары.");
                    break;
                case "/products":
                    sendMsg(chatId, "Доступные товары:\n" + String.join("\n", products) + "\nВведите номер товара, чтобы добавить его в корзину.");
                    break;
                case "/cart":
                    sendMsg(chatId, "Ваша корзина:\n" + (cart.isEmpty() ? "Корзина пуста." : String.join("\n", cart)));
                    break;
                default:
                    try {
                        int productIndex = Integer.parseInt(messageText.split("\\.")[0]) - 1;
                        if (productIndex >= 0 && productIndex < products.size()) {
                            cart.add(products.get(productIndex));
                            sendMsg(chatId, "Товар добавлен в корзину.");
                        } else {
                            sendMsg(chatId, "Некорректный номер товара. Попробуйте еще раз.");
                        }
                    } catch (Exception e) {
                        sendMsg(chatId, "Не удалось распознать команду. Попробуйте еще раз.");
                    }
                    break;
            }
        }
    }

    private void sendMsg(long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);

        try {
            execute(message); // Отправка сообщения
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
