package non_bot_package;

import org.glassfish.grizzly.utils.Pair;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CartManager {
    private final List<Pair<ClothingItem, String>> items;

    public CartManager() {
        this.items = new ArrayList<>();
    }

    public void addItem(ClothingItem item, String size) {
        items.add(new Pair<>(item, size));
    }

    public void clear() {
        items.clear();
    }

    public List<ClothingItem> getItems() {
        List<ClothingItem> listOfItems = new ArrayList<>();
        for (Pair<ClothingItem, String> item : items) {
            listOfItems.add(item.getFirst());
        }
        return listOfItems;
    }

    public List<Pair<ClothingItem, String>> getListOfItemsWithSize() {
        return Collections.unmodifiableList(items);
    }

}