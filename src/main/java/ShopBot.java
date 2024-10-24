import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.media.InputMedia;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShopBot extends TelegramLongPollingBot {
    private final ProductManager productManager;
    private final KeyboardManager keyboardManager;
    private int indPage = 0;
    private List<ClothingItem> clothingItems = new ArrayList<>();
    private final Map<Long, CartManager> userCarts = new HashMap<>(); // Хранение корзин пользователей


    public ShopBot() {
        this.productManager = new ProductManager();
        this.keyboardManager = new KeyboardManager();
    }

    @Override
    public String getBotUsername() {
        return "TRILLL";
    }

    @Override
    public String getBotToken() {
        return "";
    }

    private void sendMsg(long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendPhotos(long chatId, List<String> photoPaths) {
        List<InputMedia> media = new ArrayList<>();

        for (String photoPath : photoPaths) {
            InputMediaPhoto photo = new InputMediaPhoto();
            photo.setMedia(new File(photoPath),photoPath);
            media.add(photo);
        }


        SendMediaGroup sendMediaGroup = new SendMediaGroup();
        sendMediaGroup.setChatId(chatId);
        sendMediaGroup.setMedias(media);

        try {
            execute(sendMediaGroup);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendPhoto(long chatId, String photoPath) {
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(chatId);
        InputFile photo = new InputFile();
        photo.setMedia(new File(photoPath),photoPath);
        sendPhoto.setPhoto(photo);
        try {
            execute(sendPhoto);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendPhotoWithCaption(long chatId, String photoPath, String caption) {
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(chatId);
        InputFile photo = new InputFile();
        photo.setMedia(new File(photoPath),photoPath);
        sendPhoto.setPhoto(photo);
        sendPhoto.setCaption(caption);
        try {
            execute(sendPhoto);
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

            userCarts.putIfAbsent(chatId, new CartManager()); // Инициализация корзины для нового пользователя
            CartManager cart = userCarts.get(chatId); // Получение корзины для пользователя

            if (messageText.equalsIgnoreCase("/start")) {
                sendPhotoWithCaption(chatId,"C:/Users/antip/IdeaProjects/SVmag3.0/photos/photo_2024-10-13_19-02-36.jpg", "Добро пожаловать в THRILLL:)");
                sendMessege(keyboardManager.createMenuKeyboard(chatId,MassageManager.createMenuMessage()));
            } else if (messageText.equalsIgnoreCase("вернуться в меню⏪")) {
                indPage = 0;
                sendMessege(keyboardManager.createMenuKeyboard(chatId, "Меню:"));
            } else if (messageText.equalsIgnoreCase("вернуться назад⏪")) {
                indPage = 0;
                sendMessege(keyboardManager.createMenuOfProductKeyboard(chatId, MassageManager.createMenuOfProductMessage()));
            } else if (messageText.equalsIgnoreCase("доступные товары\uD83D\uDECD️")) {
                sendMessege(keyboardManager.createMenuOfProductKeyboard(chatId, MassageManager.createMenuOfProductMessage()));
            } else if (messageText.equalsIgnoreCase("футболки\uD83D\uDC55")) {
                clothingItems = productManager.getListOfShirts();
                sendMessege(keyboardManager.createProductKeyboard(chatId, indPage, clothingItems, MassageManager.createProductMessage(productManager.getProductsPage(clothingItems, indPage), indPage)));
                sendPhotos(chatId, productManager.getListOfProductPaths(productManager.getProductsPage(clothingItems, indPage)));
            } else if (messageText.equalsIgnoreCase("худи\uD83E\uDD77")) {
                clothingItems = productManager.getListOfHoodies();
                sendMessege(keyboardManager.createProductKeyboard(chatId, indPage, clothingItems, MassageManager.createProductMessage(productManager.getProductsPage(clothingItems, indPage), indPage)));
                sendPhoto(chatId, clothingItems.getFirst().getImagePath());
            } else if (messageText.equalsIgnoreCase("лонгсливы\uD83D\uDC7B")) {
                clothingItems = productManager.getListOfLongSleeves();
                sendMessege(keyboardManager.createProductKeyboard(chatId, indPage, clothingItems, MassageManager.createProductMessage(productManager.getProductsPage(clothingItems, indPage), indPage)));
                sendPhoto(chatId, clothingItems.getFirst().getImagePath());
            } else if (messageText.equalsIgnoreCase("кепки\uD83E\uDDE2")) {
                clothingItems = productManager.getListOfCaps();
                sendMessege(keyboardManager.createProductKeyboard(chatId, indPage, clothingItems, MassageManager.createProductMessage(productManager.getProductsPage(clothingItems, indPage), indPage)));
                sendPhotos(chatId, productManager.getListOfProductPaths(productManager.getProductsPage(clothingItems, indPage)));
            } else if (messageText.equalsIgnoreCase("следующая страница➡️")) {
                ++indPage;
                sendMessege(keyboardManager.createProductKeyboard(chatId, indPage, clothingItems, MassageManager.createProductMessage(productManager.getProductsPage(clothingItems, indPage), indPage)));
                sendPhotos(chatId, productManager.getListOfProductPaths(productManager.getProductsPage(clothingItems, indPage)));
            } else if (messageText.equalsIgnoreCase("предыдущая страница⬅️")) {
                --indPage;
                sendMessege(keyboardManager.createProductKeyboard(chatId, indPage, clothingItems,MassageManager.createProductMessage(productManager.getProductsPage(clothingItems, indPage), indPage)));
                sendPhotos(chatId, productManager.getListOfProductPaths(productManager.getProductsPage(clothingItems, indPage)));
            } else if (messageText.equalsIgnoreCase("корзина\uD83D\uDED2")) {
                sendMessege(keyboardManager.createCartKeyboard(chatId,MassageManager.createCartMessage(cart.getListOfItemsWithSize())));
                if(cart.getItems().size()>1){
                    sendPhotos(chatId, productManager.getListOfProductPaths(cart.getItems()));
                }
                else {
                    sendPhoto(chatId, cart.getItems().getFirst().getImagePath());
                    indPage = 0;
                }
            } else if (messageText.equalsIgnoreCase("очистить корзину\uD83D\uDDD1️")) {
                cart.clear();
                sendMsg(chatId, "Корзина очищена!✅");
            } else {
                try {
                    int productIndex = Integer.parseInt(messageText.split(" ")[0]) - 1;
                    String size=messageText.split(" ")[1];
                    if (productIndex >= 0 && productIndex < clothingItems.size() && clothingItems.get(productIndex).getSizes().contains(size)) {
                        cart.addItem(clothingItems.get(productIndex), size);
                        sendMsg(chatId, "Товар добавлен в корзину.✅");
                    } else {
                        sendMsg(chatId, "Некорректный номер товара или размер\uD83D\uDEAB Попробуйте еще раз. \nНапример: 2 M");
                    }
                } catch (Exception e) {
                    sendMsg(chatId, "Не удалось распознать команду. Попробуйте еще раз.\uD83D\uDEAB");
                }
            }
        }
    }
}
