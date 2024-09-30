import java.util.ArrayList;
import java.util.List;

public class ProductManager {
    private final List<ClothingItem> tshirts;
    private final List<ClothingItem> pants;
    private final List<ClothingItem> shoes;

    public ProductManager() {
        this.tshirts = new ArrayList<>();
        this.pants = new ArrayList<>();
        this.shoes = new ArrayList<>();
        initializeProducts();
    }



    private void initializeProducts() {
        tshirts.add(new ClothingItem("A","S,M,L","BLACK"));
        tshirts.add(new ClothingItem("B","M,L","WHITE"));
        tshirts.add(new ClothingItem("C","M,XL","ORANGE"));
        tshirts.add(new ClothingItem("D","S,XXL","RED"));
        pants.add(new ClothingItem("E","38,40","BLACK"));
        pants.add(new ClothingItem("F","40,42","GREY"));
        shoes.add(new ClothingItem("H","40,41,42,43","BLACK"));
        shoes.add(new ClothingItem("I","41,42","GREEN"));
        shoes.add(new ClothingItem("J","42,44","WHITE"));
    }

    public List<ClothingItem> getListOftshirts() {
        return tshirts;
    }
    public List<ClothingItem> getListOfpants() {
        return pants;
    }
    public List<ClothingItem> getListOfshoes() {
        return shoes;
    }



    public List<ClothingItem> getProductsPage(List<ClothingItem> items, int ind) {
        if(ind==0) {
            return items.subList(0,2);
        }
        else{
            return items.subList(2, items.size());
        }
    }

}
