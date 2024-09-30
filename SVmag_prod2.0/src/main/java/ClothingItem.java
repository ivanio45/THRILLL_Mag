import java.util.ArrayList;
import java.util.List;

public class ClothingItem {
    private String name;
    private String sizes;
    private String color;

    public ClothingItem(String name, String sizes, String color) {
        this.name = name;
        this.sizes = sizes;
        this.color = color;
    }

    public String getDescription() {
        return name + " Размеры: " + sizes + "; Цвет: " + color;
    }
}