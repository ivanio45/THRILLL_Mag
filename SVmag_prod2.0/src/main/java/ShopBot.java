import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.util.ArrayList;
import java.util.List;

public class ShopBot extends TelegramLongPollingBot {
    private final ProductManager productManager;
    private final Cart cart;
    private final KeyboardManager keyboardManager;
    private int indPage = 0;
    private List<ClothingItem> clothingItems = new ArrayList<>();

    public ShopBot() {
        this.productManager = new ProductManager();
        this.cart = new Cart();
        this.keyboardManager = new KeyboardManager();
    }

    @Override
    public String getBotUsername() {
        return "SVmag"; // Укажите имя вашего бота
    }

    @Override
    public String getBotToken() {
        return ""; // Укажите токен вашего бота
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

    private void sendMessege(SendMessage msg) {
        try {
            execute(msg); // Отправка сообщения
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            if (messageText.equalsIgnoreCase("/start")) {
                sendMessege(keyboardManager.createMenuKeyboard(chatId));
            } else if (messageText.equalsIgnoreCase("вернуться в меню")) {
                indPage = 0;
                sendMessege(keyboardManager.createMenuKeyboard(chatId));
            } else if (messageText.equalsIgnoreCase("вернуться назад")) {
                indPage = 0;
                sendMessege(keyboardManager.createMenuOfProductKeyboard(chatId));
            }else if (messageText.equalsIgnoreCase("доступные товары")) {
                sendMessege(keyboardManager.createMenuOfProductKeyboard(chatId));
            } else if (messageText.equalsIgnoreCase("футболки")) {
                clothingItems=productManager.getListOftshirts();
                sendMessege(keyboardManager.createProductKeyboard(chatId, productManager.getProductsPage(clothingItems,indPage), indPage));
            } else if (messageText.equalsIgnoreCase("штаны")) {
                clothingItems=productManager.getListOfpants();
                sendMessege(keyboardManager.createProductKeyboard(chatId, productManager.getProductsPage(clothingItems,indPage), indPage));
            } else if (messageText.equalsIgnoreCase("обувь")) {
                clothingItems=productManager.getListOfshoes();
                sendMessege(keyboardManager.createProductKeyboard(chatId, productManager.getProductsPage(clothingItems,indPage), indPage));
            } else if (messageText.equalsIgnoreCase("следующая страница")) {
                sendMessege(keyboardManager.createProductKeyboard(chatId, productManager.getProductsPage(clothingItems,++indPage), indPage));
            } else if (messageText.equalsIgnoreCase("предыдущая страница")) {
                sendMessege(keyboardManager.createProductKeyboard(chatId, productManager.getProductsPage(clothingItems,--indPage), indPage));
            } else if (messageText.equalsIgnoreCase("корзина")) {
                sendMessege(keyboardManager.createCartKeyboard(chatId, cart.getItems()));
                indPage =0;
            } else if (messageText.equalsIgnoreCase("очистить корзину")) {
                cart.clear();
                sendMsg(chatId, "Корзина очищена!");
            } else {
                try {
                    int productIndex = Integer.parseInt(messageText.split("\\.")[0]) - 1;
                    if (productIndex >= 0 && productIndex < productManager.getProductsPage(clothingItems,indPage).size()) {
                        cart.addItem(productManager.getProductsPage(clothingItems,indPage).get(productIndex));
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
}
