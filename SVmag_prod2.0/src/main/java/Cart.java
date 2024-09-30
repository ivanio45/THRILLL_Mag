import java.util.ArrayList;
import java.util.List;

public class Cart {
    private final List<ClothingItem> items;

    public Cart() {
        this.items = new ArrayList<>();
    }

    public void addItem(ClothingItem item) {
        items.add(item);
    }

    public void clear() {
        items.clear();
    }

    public List<ClothingItem> getItems() {
        return items;
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }
}
