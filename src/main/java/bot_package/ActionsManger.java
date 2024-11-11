package bot_package;

import non_bot_package.CartManager;
import non_bot_package.ClothingItem;
import non_bot_package.MassageManager;
import non_bot_package.ProductManager;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

public class ActionsManger {
    private final User user;

    ActionsManger(User user) {
        this.user = user;
    }

    SendMessage ShowItems(long chatId, KeyboardManager keyboardManager,
              MassageManager massageManager, ProductManager productManager){
        List<ClothingItem> clothingItems = user.getClothingItems();
        List<ClothingItem> clothingItemsPage = productManager.getProductsPage(clothingItems, user.getIndPage());
        String text_of_message = massageManager.createProductMessage(clothingItemsPage, user.getIndPage());
        return keyboardManager.createProductKeyboard(chatId, user.getIndPage(), clothingItems, text_of_message);
    }

    public SendMessage backToMenu(long chatId, KeyboardManager keyboardManager) {
        user.setIndPage(0);
        return keyboardManager.createMenuKeyboard(chatId, "Меню:");
    }

    public SendMessage start(long chatId, KeyboardManager keyboardManager, MassageManager massageManager) {
        user.setIndPage(0);
        String text_of_message = massageManager.createMenuMessage();
        return keyboardManager.createMenuKeyboard(chatId, text_of_message);
    }

    public SendMessage back(long chatId, KeyboardManager keyboardManager, MassageManager massageManager) {
        user.setIndPage(0);
        String text_of_message = massageManager.createMenuOfProductMessage();
        return keyboardManager.createMenuOfProductKeyboard(chatId, text_of_message);
    }

    public SendMessage showAvailableProducts(long chatId, KeyboardManager keyboardManager,
                                             MassageManager massageManager) {
        String text_of_message = massageManager.createMenuOfProductMessage();
        return keyboardManager.createMenuOfProductKeyboard(chatId, text_of_message);
    }

    public SendMessage showTshits(long chatId, KeyboardManager keyboardManager,
                                  MassageManager massageManager, ProductManager productManager) {
        user.setClothingItems(productManager.getListOfShirts());
        return ShowItems(chatId, keyboardManager, massageManager, productManager);
    }

    public SendMessage showHoodies(long chatId, KeyboardManager keyboardManager,
                                   MassageManager massageManager, ProductManager productManager) {
        user.setClothingItems(productManager.getListOfHoodies());
        return ShowItems(chatId, keyboardManager, massageManager, productManager);
    }

    public SendMessage showLongSleeves(long chatId, KeyboardManager keyboardManager,
                                       MassageManager massageManager, ProductManager productManager) {
        user.setClothingItems(productManager.getListOfLongSleeves());
        return ShowItems(chatId, keyboardManager, massageManager, productManager);
    }

    public SendMessage showCaps(long chatId, KeyboardManager keyboardManager,
                                MassageManager massageManager, ProductManager productManager) {
        user.setClothingItems(productManager.getListOfCaps());
        return ShowItems(chatId, keyboardManager, massageManager, productManager);
    }

    public SendMessage showNextPage(long chatId, KeyboardManager keyboardManager,
                                    MassageManager massageManager, ProductManager productManager) {
        user.setIndPage(user.getIndPage() + 1);
        return ShowItems(chatId, keyboardManager, massageManager, productManager);
    }


    public SendMessage showPastPage(long chatId, KeyboardManager keyboardManager,
                                    MassageManager massageManager, ProductManager productManager) {
        user.setIndPage(user.getIndPage() - 1);
        return ShowItems(chatId, keyboardManager, massageManager, productManager);
    }

    public SendMessage showCart(long chatId, KeyboardManager keyboardManager,
                                MassageManager massageManager) {
        user.setIndPage(0);
        CartManager cart=user.getCart();
        user.setClothingItems(cart.getItems());
        String text_of_message = massageManager.createCartMessage(cart.getListOfItemsWithSize());
        return keyboardManager.createCartKeyboard(chatId, text_of_message);
    }

    public void clearCart() {
        user.getCart().clear();
    }

    public void addProductInCart(int productIndex, String size){
        List<ClothingItem> clothingItems = user.getClothingItems();
        user.getCart().addItem(clothingItems.get(productIndex), size);
    }

    public List<String> getListOfProductPaths(ProductManager productManager){
        List<ClothingItem> clothingItems=user.getClothingItems();
        List<ClothingItem> clothingItemsPage=productManager.getProductsPage(clothingItems, user.getIndPage());
        return productManager.getListOfProductPaths(clothingItemsPage);
    }

    public List<String> getListOfProductPathsForCart(ProductManager productManager){
        List<ClothingItem> clothingItems=user.getClothingItems();
        return productManager.getListOfProductPaths(clothingItems);
    }

    public String getProductPath(){
        List<ClothingItem> clothingItems=user.getClothingItems();
        return clothingItems.getFirst().getImagePath();
    }

}
