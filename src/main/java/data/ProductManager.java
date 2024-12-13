package data;

import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class ProductManager {


    public List<ClothingItem> getListOfShirts() {
        try {
            return Database.getClothingItems("shirts");
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<ClothingItem> getListOfHoodies() {
        try {
            return Database.getClothingItems("hoodies");
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<ClothingItem> getListOfLongSleeves() {
        try {
            return Database.getClothingItems("longsleeves");
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<ClothingItem> getListOfCaps() {
        try {
            return Database.getClothingItems("caps");
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }


    public List<ClothingItem> getProductsPage(List<ClothingItem> items, int ind) {
        if (items == null || items.isEmpty()) return new ArrayList<>();
        if (items.size() < 2) return items;
        return ind == 0 ? items.subList(0, 2) : items.subList(2, items.size());
    }

    public List<String> getListOfProductPaths(List<ClothingItem> items) {
        List<String> paths = new ArrayList<>();
        for (ClothingItem item : items) {
            paths.add(item.getImagePath());
        }
        return paths;
    }
}