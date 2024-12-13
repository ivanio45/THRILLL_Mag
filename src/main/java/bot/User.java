package bot;

import data.CartManager;
import data.ClothingItem;

import java.util.ArrayList;
import java.util.List;

public class User {
    private int indPage;
    private final CartManager cart;
    private List<ClothingItem> clothingItems;

    User() {
        this.indPage = 0;
        this.cart = new CartManager();
        this.clothingItems = new ArrayList<>();
    }


    public int getIndPage() {
        return indPage;
    }

    public void setIndPage(int indPage) {
        this.indPage = indPage;
    }

    public CartManager getCart() {
        return cart;
    }

    public List<ClothingItem> getClothingItems() {
        return clothingItems;
    }

    public void setClothingItems(List<ClothingItem> clothingItems) {
        this.clothingItems = clothingItems;
    }
}
