import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ListFood {
    JsonArray listFoodMemory = new JsonArray();

    public ListFood() {
        listFoodMemory = read();
    }

    void update(String idMonAn, String name, double donGia) {// Thêm món ăn ra file <<== updated
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("ID", idMonAn);
        jsonObject.addProperty("Name", name);
        jsonObject.addProperty("DonGia", donGia);
        jsonObject.addProperty("Served", 0); // <<== new
        listFoodMemory.add(jsonObject);
        writeToFile();
    }

    void renewOreder() { // Làm mới số lượng phục vụ món ăn sau 1 ngày trong ListFood.json
        JsonArray jsonArray = listFoodMemory;
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
            jsonObject.addProperty("Served", 0);
        }
        writeToFile();
    }

    void updateFoodServerd(JsonObject tempFood) { // cập nhập Served <<== new
        String idMonAn = tempFood.get("Id").getAsString();
        JsonObject food = search(idMonAn);
        food.addProperty("Served", tempFood.get("SoLuong").getAsInt() + food.get("Served").getAsInt());
        listFoodMemory.set(searchFoodIndex(idMonAn), food);
        writeToFile();
    }

    int searchFoodIndex(String foodID) { // trả về thứ tự của food <<== new

        for (int i = 0; i < listFoodMemory.size(); i++) {
            JsonObject food = listFoodMemory.get(i).getAsJsonObject();
            if (foodID.equalsIgnoreCase(food.get("ID").getAsString())) {
                return i;
            }
        }
        return -1;

    }

    JsonObject search(String idMonAn) {
        for (int i = 0; i < listFoodMemory.size(); i++) {
            JsonObject jsonObject = listFoodMemory.get(i).getAsJsonObject();
            String tempID = jsonObject.get("ID").getAsString();
            if (tempID.equals(idMonAn)) {
                return jsonObject;
            }
        }
        return null;
    }

    boolean remove(String idMonAn) { // xoá món ăn trong listfood
        JsonObject food = search(idMonAn);
        if (food == null) {
            return false;
        }
        listFoodMemory.remove(food);
        writeToFile();
        return true;
    }

    void writeToFile() { // ghi danh sách món ăn ra file <<== sửa lại chính tả, cũ: void wirteToFile()
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter fileWriter = new FileWriter("ListFood.json")) {
            gson.toJson(listFoodMemory, fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JsonArray read() { // Đọc dữ liệu từ file
        JsonArray jsonArray = null;
        try (FileReader reader = new FileReader("ListFood.json")) {
            jsonArray = (JsonArray) JsonParser.parseReader(reader);
        } catch (Exception e) {
            writeToFile();
        }
        return jsonArray;
    }

    List<Object> hienThiMenu() { //  Menu món ăn
        List<Object> menu = new ArrayList<>();
        JsonObject jsonObject = new JsonObject();
        Object object = new Object();
        for (int i = 0; i < listFoodMemory.size(); i++) {
            jsonObject = listFoodMemory.get(i).getAsJsonObject();
            object = jsonObject.get("Name").getAsString();
            menu.add(object);
            object = jsonObject.get("ID").getAsString();
            menu.add(object);
            object = jsonObject.get("DonGia").getAsDouble();
            menu.add(object);

        }
        return menu;
    }

    List<Object> doanhThuVaMonAn() { // Doanh thu và món ăn trong ngày
        List<Object> list = new ArrayList<>();
        double doanhthu = 0;
        for (int i = 0; i < listFoodMemory.size(); i++) {
            JsonObject jsonObject = listFoodMemory.get(i).getAsJsonObject();
            String tempName = jsonObject.get("Name").getAsString();
            int tempSL = jsonObject.get("Served").getAsInt();
            double tempDonGia = jsonObject.get("DonGia").getAsDouble();
            if (tempSL > 0) {
                doanhthu += tempDonGia * tempSL;
                list.add(tempName);
                list.add(tempSL);
            }
        }
        list.add(doanhthu);
        return list;
    }

}
