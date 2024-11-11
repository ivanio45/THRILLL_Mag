package non_bot_package;

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
        shirts.add(new ClothingItem("T-shirt \"Trilll\"","S,M,L","ORANGE",1499,"photos/trilll.jpg"));
        shirts.add(new ClothingItem("T-shirt \"don't let me remember v.1\"","M,L","WHITE",1499,"photos/dont let me remember v1.jpg"));
        shirts.add(new ClothingItem("T-shirt \"don't let me remember v.2\"","M,XL","WHITE",1499,"photos/dont let me remember v2.jpg"));
        shirts.add(new ClothingItem("T-shirt \"Ever and Never\"","S,XXL","WHITE",1599,"photos/ever and never.jpg"));
        hoodies.add(new ClothingItem("Hoodie \"don't let me remember\"","38,40","BLACK",3499,"photos/dont let me remember hoodie.jpg"));
        longSleeves.add(new ClothingItem("LongSleeve \"Troll\"","38,40","WHITE",1699,"photos/troll long.jpg"));
        caps.add(new ClothingItem("Cap \"Trilll\"","54","BLUE",1599,"photos/trilll cap.jpg"));
        caps.add(new ClothingItem("Cap \"I love too...\"","54","BLACK",1599,"photos/i love too cap.jpg"));
        caps.add(new ClothingItem("Cap \"Infinity\"","54","BLACK",1599,"photos/infinity cap.jpg"));
        caps.add(new ClothingItem("Cap TRILL* \"TIME\"","54","ORANGE",1599,"photos/trill time cap.jpg"));

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