import java.util.ArrayList;
import java.util.List;

public class ProductManager {
    private final List<ClothingItem> shirts;
    private final List<ClothingItem> hoodies;
    private final List<ClothingItem> caps;
    private final List<ClothingItem> longSleeves;

    public ProductManager() {
        this.shirts = new ArrayList<>();
        this.hoodies = new ArrayList<>();
        this.caps = new ArrayList<>();
        this.longSleeves = new ArrayList<>();
        initializeProducts();
    }



    private void initializeProducts() {
        shirts.add(new ClothingItem("T-shirt \"Trilll\"","S,M,L","ORANGE","C:/Users/antip/IdeaProjects/SVmag3.0/photos/Picsart_24-10-21_22-49-31-469.jpg"));
        shirts.add(new ClothingItem("T-shirt \"don't let me remember v.1\"","M,L","WHITE","C:/Users/antip/IdeaProjects/SVmag3.0/photos/Picsart_24-10-21_22-48-24-374.jpg"));
        shirts.add(new ClothingItem("T-shirt \"don't let me remember v.2\"","M,XL","WHITE","C:/Users/antip/IdeaProjects/SVmag3.0/photos/Picsart_24-10-21_22-46-54-609.jpg"));
        shirts.add(new ClothingItem("T-shirt \"Ever and Never\"","S,XXL","WHITE","C:/Users/antip/IdeaProjects/SVmag3.0/photos/Picsart_24-10-21_22-59-11-338.jpg"));
        hoodies.add(new ClothingItem("Hoodie \"don't let me remember\"","38,40","BLACK","C:/Users/antip/IdeaProjects/SVmag3.0/photos/Picsart_24-10-21_22-53-30-754.jpg"));
        longSleeves.add(new ClothingItem("LongSleeve \"Troll\"","38,40","WHITE","C:/Users/antip/IdeaProjects/SVmag3.0/photos/Picsart_24-10-21_23-32-36-817.jpg"));
        caps.add(new ClothingItem("Cap \"Trilll\"","54","BLUE","C:/Users/antip/IdeaProjects/SVmag3.0/photos/Picsart_24-10-21_23-06-39-475.jpg"));
        caps.add(new ClothingItem("Cap \"I love too...\"","54","BLACK","C:/Users/antip/IdeaProjects/SVmag3.0/photos/Picsart_24-10-21_23-10-46-213.jpg"));
        caps.add(new ClothingItem("Cap \"Infinity\"","54","BLACK","C:/Users/antip/IdeaProjects/SVmag3.0/photos/Picsart_24-10-21_23-15-17-064.jpg"));
        caps.add(new ClothingItem("Cap TRILL* \"TIME\"","54","ORANGE","C:/Users/antip/IdeaProjects/SVmag3.0/photos/Picsart_24-10-21_23-24-02-134.jpg"));

    }

    public List<ClothingItem> getListOfShirts() {
        return shirts;
    }
    public List<ClothingItem> getListOfHoodies() {
        return hoodies;
    }
    public List<ClothingItem> getListOfLongSleeves() {
        return longSleeves;
    }
    public List<ClothingItem> getListOfCaps() {
        return caps;
    }


    public List<ClothingItem> getProductsPage(List<ClothingItem> items, int ind) {
        if (items.size()<2){
            return items;
        }
        else if(ind==0) {
            return items.subList(0,2);
        }
        else{
            return items.subList(2, items.size());
        }
    }

    public List<String> getListOfProductPaths(List<ClothingItem> items) {
        List<String> paths = new ArrayList<>();
        for(ClothingItem item: items){
            paths.add(item.getImagePath());
        }
        return paths;
    }

}