

public class ClothingItem {
    private final String name;
    private final String sizes;
    private final String color;
    private final String imagePath;

    public ClothingItem(String name, String sizes, String color, String imagePath) {
        this.name = name;
        this.sizes = sizes;
        this.color = color;
        this.imagePath = imagePath;
    }

    public String getDescription() {
        return name +";\n"+ "Размеры: " + sizes+";\n" + "Цвет: " + color+";\n";
    }
    public String getName() {
        return name;
    }
    public String getColor() {
        return color;
    }
    public String getSizes() {
        return sizes;
    }
    public String getImagePath() {
        return imagePath;
    }
}