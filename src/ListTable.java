import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ListTable {
    private static JsonArray listTableMemory = new JsonArray();
    private JsonArray listFoodOrdered = new JsonArray();
    private static ListFood listFood = new ListFood();

    public JsonArray getListFoodOrdered() {
        return listFoodOrdered;
    }

    public ListTable() {
        listTableMemory = read();
    }

    JsonArray updateOrdered(String tableID, String idMonAn, int soLanGoiMon) { // <<== update
        JsonObject food = listFood.search(idMonAn);
        if (food == null) {
            return null;
        }
        listFoodOrdered = searchTable(tableID).get("FoodOrdered").getAsJsonArray();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("Id", idMonAn);
        jsonObject.addProperty("Name", food.get("Name").getAsString());
        jsonObject.addProperty("SoLuong", soLanGoiMon);
        jsonObject.addProperty("DonGia", food.get("DonGia").getAsDouble());
        jsonObject.addProperty("CheckDone", false);
        listFoodOrdered.add(jsonObject);
        return listFoodOrdered;
    }

    void setFoodOrdered(String iDTable, JsonArray foodOrdered) { // Lưu đồ ăn được thực khách ordered
        JsonObject jsonObject = searchTable(iDTable);
        jsonObject.add("FoodOrdered", foodOrdered);
        int index = searchTableIndex(jsonObject);
        listTableMemory.set(index, jsonObject);

        writeToFile();
    }

    JsonObject searchTable(String idTable) { // tìm bàn
        for (int i = 0; i < listTableMemory.size(); i++) {
            JsonObject jsonObject = listTableMemory.get(i).getAsJsonObject();
            if (jsonObject.get("ID").getAsString().equalsIgnoreCase(idTable))
                return jsonObject;
        }
        return null;
    }

    int searchTableIndex(JsonObject table) { // tìm bàn, trả về số thứ tự <<== new
        for (int i = 0; i < listTableMemory.size(); i++) {
            JsonObject jsonObject = listTableMemory.get(i).getAsJsonObject();
            if (jsonObject == table)
                return i;
        }
        return -1;
    }

    double tinhThanhTien(String idTable) { // <<== updated
        JsonObject jsonObject = searchTable(idTable);
        if (jsonObject == null) {
            return -1;
        }
        JsonArray jsonArray = jsonObject.get("FoodOrdered").getAsJsonArray();
        double thanhTien = 0;
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject jsonObject2 = jsonArray.get(i).getAsJsonObject();
            if (jsonObject2.get("CheckDone").getAsBoolean()) {
                thanhTien += jsonObject2.get("DonGia").getAsDouble() * jsonObject2.get("SoLuong").getAsInt(); // <<==
            }
        }
        return thanhTien;
    }

    public JsonArray hienThiMonAnServed(String idTable) { // trả về các món ăn đã được chuẩn bị
        JsonArray listDone = new JsonArray();
        JsonObject table = searchTable(idTable);
        if (table == null) {
            return null;
        }
        JsonArray jsonArray = table.get("FoodOrdered").getAsJsonArray();

        for (int j = 0; j < jsonArray.size(); j++) {
            JsonObject jsonObject = jsonArray.get(j).getAsJsonObject();
            boolean check = jsonObject.get("CheckDone").getAsBoolean();
            if (check == true) {
                JsonObject foodServed = new JsonObject();
                foodServed.addProperty("Name", jsonObject.get("Name").getAsString());
                foodServed.addProperty("SoLuong", jsonObject.get("SoLuong").getAsInt());
                foodServed.addProperty("DonGia", jsonObject.get("DonGia").getAsDouble());
                listDone.add(foodServed);
            }
        }
        return listDone;
    }

    public JsonArray hienThiMonOder() { // trả về các món ăn đã gọi <<== new
        JsonArray listUnDone = new JsonArray();
        for (int i = 0; i < listTableMemory.size(); i++) {
            JsonObject table = listTableMemory.get(i).getAsJsonObject();
            JsonArray jsonArray = table.get("FoodOrdered").getAsJsonArray();
            for (int j = 0; j < jsonArray.size(); j++) {
                JsonObject jsonObject = jsonArray.get(j).getAsJsonObject();
                boolean check = jsonObject.get("CheckDone").getAsBoolean();
                if (check == false) {
                    JsonObject foodOrdered = new JsonObject();
                    foodOrdered.addProperty("Id", jsonObject.get("Id").getAsString());
                    foodOrdered.addProperty("Name", jsonObject.get("Name").getAsString());
                    foodOrdered.addProperty("SoLuong", jsonObject.get("SoLuong").getAsInt());
                    foodOrdered.addProperty("ID", table.get("ID").getAsString());
                    listUnDone.add(foodOrdered);
                }
            }

        }
        return listUnDone;
    }

    public Boolean setFoodIntoTrue(String iDTable, String idMonAn) { // làm cho món ăn nó true <<== new
        boolean check = false;
        JsonObject table = searchTable(iDTable);
        if (table == null) {
            return check;
        }
        JsonArray orderedFood = table.get("FoodOrdered").getAsJsonArray();
        for (int i = 0; i < orderedFood.size(); i++) {
            JsonObject food = orderedFood.get(i).getAsJsonObject();
            if (idMonAn.equalsIgnoreCase(food.get("Id").getAsString())) {
                if (!food.get("CheckDone").getAsBoolean()) {
                    check = true;
                }
                food.addProperty("CheckDone", true);
                orderedFood.set(i, food);
                break;
            }
        }
        setFoodOrdered(iDTable, orderedFood);
        return check;

    }

    void renewAndUpdateListFood(String tableID) { // làm rỗng OrderedFood và update reserved trong FoodList <<== new
        int tableIndex = searchTableIndex(searchTable(tableID));
        JsonObject jsonObject = listTableMemory.get(tableIndex).getAsJsonObject();
        JsonArray jsonArray = jsonObject.get("FoodOrdered").getAsJsonArray();
        for (int j = 0; j < jsonArray.size(); j++) {
            JsonObject tempFood = jsonArray.get(j).getAsJsonObject();
            if (tempFood.get("CheckDone").getAsBoolean()) {
                listFood.updateFoodServerd(tempFood);
            }
        }
        jsonObject.add("FoodOrdered", new JsonArray());
        listTableMemory.set(tableIndex, jsonObject);
        writeToFile();
    }

    static void update(String idTable, double thanhTien, JsonArray monAnOrder) {// Thêm món ăn ra file
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("ID", idTable);
        jsonObject.addProperty("ThanhTien", thanhTien);
        jsonObject.add("FoodOrdered", monAnOrder);
        listTableMemory.add(jsonObject);
        writeToFile();
    }

    static void writeToFile() { // ghi danh sách món ăn ra file
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter fileWriter = new FileWriter("ListTable.json")) {
            gson.toJson(listTableMemory, fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JsonArray read() { // Đọc dữ liệu từ file
        JsonArray jsonArray = null;
        try (FileReader reader = new FileReader("ListTable.json")) {
            jsonArray = (JsonArray) JsonParser.parseReader(reader);
        } catch (Exception e) {
            writeToFile();
        }
        return jsonArray;
    }

    static void createListTable() {
        for (int i = 1; i <= 30; i++) {
            String idTable = "T" + i;
            JsonArray monAnOrdered = new JsonArray();
            update(idTable, 0, monAnOrdered);
        }
    }

}
