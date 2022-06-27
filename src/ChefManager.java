import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class ChefManager {
    ListFood listFood = new ListFood();
    ListTable listTable = new ListTable();

    boolean themMonAn(Food food) {
        if (!(listFood.search(food.getIdMonAn()) == null)) {
            return false;
        }
        listFood.update(food.getIdMonAn(), food.getName(), food.getDonGia());
        return true;
    }

    boolean botMonAn(String idMonAn) {
        return listFood.remove(idMonAn);
    }

    List<Object> hienThiMonOdered() { // Danh sách món ăn đã được Odered
        List<Object> objects = new ArrayList<>();
        List<String> list = new ArrayList<>();
        JsonArray jsonArray = listTable.hienThiMonOder();
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
            list.add("Bàn: " + jsonObject.get("ID").getAsString() + " || Món: " + jsonObject.get("Name").getAsString()
                    + " || Mã: " + jsonObject.get("Id").getAsString() + " || Số lượng: "
                    + jsonObject.get("SoLuong").getAsInt());
        }
        Boolean check = !list.isEmpty();
        objects.add(check);
        if (check) {
            objects.add("Danh sách món ăn cần chuẩn bị");
        } else {
            objects.add("Không có món ăn cần chuẩn bị");
        }
        objects.add(list);
        return objects;// 0=boolean, 1=String message, 2=List<String> listOrderedFood
    }

    List<Object> monAnDaxong(String IDTable, String IDFood) { // Xác nhận món ăn đã xong
        List<Object> list = new ArrayList<>();
        boolean check = listTable.setFoodIntoTrue(IDTable, IDFood);
        list.add(check);
        if (check) {
            list.add("Món này đã được chuẩn bị xong");
        } else {
            list.add("Không tìm thấy món này hoặc bàn này");
        }
        return list;// 0=boolean, 1=String message
    }
}
