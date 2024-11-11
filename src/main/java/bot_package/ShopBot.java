package bot_package;

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
import non_bot_package.*;

public class ShopBot extends TelegramLongPollingBot {
    private final ProductManager productManager;
    private final KeyboardManager keyboardManager;
    private final MassageManager massageManager;
    private final Map<Long, User> users = new HashMap<>();// Хранение пользователей


    public ShopBot() {
        this.massageManager = new MassageManager();
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


    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            long chatId = update.getMessage().getChatId();
            String messageText = update.getMessage().getText();

            users.putIfAbsent(chatId, new User()); // Инициализация нового пользователя
            User user = users.get(chatId);
            ActionsManger action = new ActionsManger(user);

            switch (messageText.toLowerCase()) {
                case "/start":
                    sendPhotoWithCaption(chatId, "./photos/photo_2024-10-13_19-02-36.jpg",
                            "Добро пожаловать в TRILLL:)");
                    sendMessage(action.start(chatId, keyboardManager, massageManager));
                    break;
                case "вернуться в меню⏪":
                    sendMessage(action.backToMenu(chatId, keyboardManager));
                    break;
                case "вернуться назад⏪":
                    sendMessage(action.back(chatId, keyboardManager, massageManager));
                    break;
                case "доступные товары\uD83D\uDECD️":
                    sendMessage(action.showAvailableProducts(chatId, keyboardManager, massageManager));
                    break;
                case "футболки\uD83D\uDC55":
                    sendMessage(action.showTshits(chatId, keyboardManager, massageManager, productManager));
                    sendPhotos(chatId, action.getListOfProductPaths(productManager));
                    break;
                case "худи\uD83E\uDD77":
                    sendMessage(action.showHoodies(chatId, keyboardManager, massageManager, productManager));
                    sendPhoto(chatId, action.getProductPath());
                    break;
                case "лонгсливы\uD83D\uDC7B":
                    sendMessage(action.showLongSleeves(chatId, keyboardManager, massageManager, productManager));
                    sendPhoto(chatId, action.getProductPath());
                    break;
                case "кепки\uD83E\uDDE2":
                    sendMessage(action.showCaps(chatId, keyboardManager, massageManager, productManager));
                    sendPhotos(chatId, action.getListOfProductPaths(productManager));
                    break;
                case "следующая страница➡️":
                    sendMessage(action.showNextPage(chatId, keyboardManager, massageManager, productManager));
                    sendPhotos(chatId, action.getListOfProductPaths(productManager));
                    break;
                case "предыдущая страница⬅️":
                    sendMessage(action.showPastPage(chatId, keyboardManager, massageManager, productManager));
                    sendPhotos(chatId, action.getListOfProductPaths(productManager));
                    break;
                case "корзина\uD83D\uDED2":
                    sendMessage(action.showCart(chatId, keyboardManager, massageManager));
                    if (user.getCart().getItems().size() > 1) {
                        sendPhotos(chatId, action.getListOfProductPathsForCart(productManager));
                    }
                    else {
                        sendPhoto(chatId, action.getProductPath());
                    }
                    break;
                case "очистить корзину\uD83D\uDDD1️":
                    action.clearCart();
                    sendMessageWithText(chatId, "Корзина очищена!✅");
                    break;
                case "перейти к оплате\uD83D\uDCB5":
                    sendPhoto(chatId, "photos/chiki.jpg");
                    break;
                default:
                    try {
                        int productIndex = Integer.parseInt(messageText.split(" ")[0]) - 1;
                        String size = messageText.split(" ")[1];
                        List<ClothingItem> clothingItems = user.getClothingItems();
                        if (productIndex >= 0 && productIndex < clothingItems.size() &&
                                clothingItems.get(productIndex).getSizes().contains(size.toUpperCase())) {
                            action.addProductInCart(productIndex, size.toUpperCase());
                            sendMessageWithText(chatId, "Товар добавлен в корзину.✅");
                        }
                        else {
                            sendMessageWithText(chatId, "Некорректный номер товара или размер\uD83D\uDEAB Попробуйте еще раз. \nНапример: 2 M");
                        }
                    } catch (Exception e) {
                        sendMessageWithText(chatId, "Не удалось распознать команду. Попробуйте еще раз.\uD83D\uDEAB");
                    }
                    break;
            }
        }
    }

    private void sendMessageWithText(long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        message.enableMarkdown(true);
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
            photo.setMedia(new File(photoPath), photoPath);
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
        photo.setMedia(new File(photoPath), photoPath);
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
        photo.setMedia(new File(photoPath), photoPath);
        sendPhoto.setPhoto(photo);
        sendPhoto.setCaption(caption);
        try {
            execute(sendPhoto);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(SendMessage msg) {
        msg.enableMarkdown(true);
        try {
            execute(msg); // Отправка сообщения
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
