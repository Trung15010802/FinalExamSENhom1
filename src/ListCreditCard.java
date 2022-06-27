import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ListCreditCard {
    private JsonArray listCreditCardMemory = new JsonArray();

    public ListCreditCard() {
        listCreditCardMemory = read();
    }

    void updateCreditCard(String name, String iDCreditCard) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("Name", name);
        jsonObject.addProperty("ID", iDCreditCard);
        jsonObject.addProperty("BonusPoint", 0);
        listCreditCardMemory.add(jsonObject);
        writeToFile();
    }

    int searchIndex(String iDCreditCard) { // Tìm chỉ số của CreditCard
        for (int i = 0; i < listCreditCardMemory.size(); i++) {
            JsonObject jsonObject = listCreditCardMemory.get(i).getAsJsonObject();
            if (iDCreditCard.equals(jsonObject.get("ID").getAsString())) {
                return i;
            }
        }
        return -1;
    }

    JsonObject searchCreditCard(String iDCreditCard) { // Tìm json object của credit card
        for (int i = 0; i < listCreditCardMemory.size(); i++) {
            JsonObject jsonObject = listCreditCardMemory.get(i).getAsJsonObject();
            if (iDCreditCard.equalsIgnoreCase(jsonObject.get("ID").getAsString())) {
                return jsonObject;
            }
        }
        return null;
    }

    double addBonusPoint(String iDCreditCard) { // Công điểm thành viên và ghi vô file
        JsonObject jsonObject = searchCreditCard(iDCreditCard);
        jsonObject.addProperty("BonusPoint", jsonObject.get("BonusPoint").getAsInt() + 1000);
        writeToFile();
        return jsonObject.get("BonusPoint").getAsDouble();
    }

    void writeToFile() { // ghi danh sách khách hàng đăng ký thẻ thành viên ra file
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter fileWriter = new FileWriter("ListCreditCard.json")) {
            gson.toJson(listCreditCardMemory, fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JsonArray read() { // Đọc dữ liệu từ file
        JsonArray jsonArray = null;
        try (FileReader reader = new FileReader("ListCreditCard.json")) {
            jsonArray = (JsonArray) JsonParser.parseReader(reader);
        } catch (Exception e) {
            writeToFile();
        }
        return jsonArray;
    }
}
