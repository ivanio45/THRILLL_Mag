package non_bot_package;

public class ClothingItem {
    private final String name;
    private final String sizes;
    private final String color;
    private final int price;
    private final String imagePath;

    public ClothingItem(String name, String sizes, String color,int price, String imagePath) {
        this.name = name;
        this.sizes = sizes;
        this.color = color;
        this.imagePath = imagePath;
        this.price = price;
    }

    public String getDescription() {
        return name +";\n"+ "Размеры: " + sizes+";\n" + "Цвет: " + color+ ";\n" + "*Цена:* " + price+ "₽;\n";
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
    public int getPrice() {
        return price;
    }
    public String getImagePath() {
        return imagePath;
    }
}