import org.glassfish.grizzly.utils.Pair;
import java.util.List;


public class MassageManager {

    public static String createMenuMessage() {
        return "Мы магазин - \"TRILLL\"✨\n" +
                "Здесь вы найдете эксклюзивную одежду, созданную с любовью и вниманием к деталям!\n" +
                "Наши стильные модели идеально подчеркнут вашу индивидуальность, а качественные ткани обеспечат комфорт\uD83C\uDF96\n" +
                "\n" +
                "Присоединяйтесь к нашему сообществу и создайте уникальный гардероб, который вдохновляет!\uD83C\uDF0C";
    }


    public static String createMenuOfProductMessage() {
        return "Выберете Раздел";
    }


    public static String createProductMessage(List<ClothingItem> items, int indPage) {

        String str = new String();
        str+="Доступные товары:\n\n";
        int indexOfItem = (indPage*2)+1;
        for (ClothingItem item : items) {
            str+=String.valueOf(indexOfItem++)+(".");
            str+=(item.getDescription());
            str+=("\n");
        }
        str+=("Чтобы добавить товар в корзину отправьте его номер и размер.\nНапример: 3 M");
        return str;
    }

    public static String createCartMessage(List<Pair<ClothingItem, String>> cartItems) {
        String str = new String();
        int indexOfItem=1;
        for (Pair<ClothingItem, String> item : cartItems) {
            str+=String.valueOf(indexOfItem++) + (".");
            str+=(item.getFirst().getName()) + ("\n");
            str+=(item.getFirst().getColor()) + ("\n");
            str+= ("Size:") + (item.getSecond()) + ("\n");
            str+=("\n");
        }
        str = "Ваша корзина:\n\n" + (cartItems.isEmpty() ? "Корзина пуста." : str);
        return str.toString();
    }

}
