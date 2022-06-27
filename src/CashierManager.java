import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class CashierManager {
    private ListTable listTable = new ListTable();
    private ListFood listFood = new ListFood();

    double xemHoaDon(String idTable) {
        return listTable.tinhThanhTien(idTable);
    }

    List<Object> hienThiMonAnServed(String idTable) {
        List<Object> objects = new ArrayList<>();
        List<String> list = new ArrayList<>();
        JsonArray jsonArray = listTable.hienThiMonAnServed(idTable);
        if (jsonArray == null) {
            objects.add(false);
            objects.add("Không tìm thấy bàn");
            objects.add(null);
        } else {
            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
                list.add(" Món: " + jsonObject.get("Name").getAsString()
                        + " || Số lượng: "
                        + jsonObject.get("SoLuong").getAsInt());
            }
            Boolean check = !list.isEmpty();
            objects.add(check);
            if (check) {
                objects.add("Danh sách món ăn đã được chuẩn bị");
            } else {
                objects.add("Không có món ăn được chuẩn bị");
            }
            objects.add(list);
        }

        return objects;// 0=boolean, 1=String message, 2=List<String> listOrderedFood
    }

    List<Object> xuatDoanhThuVaMonAn() {
        return listFood.doanhThuVaMonAn();
    }

    void renewAndUpdateListFood(String tableID) {
        listTable.renewAndUpdateListFood(tableID);
    }

    double nhapVaTinhTienThua(double money, String idTable) {
        double thanhTien = listTable.tinhThanhTien(idTable);
        if (thanhTien < 0) {
            return -1;
        }
        return money - thanhTien;
    }

    void renewOreder() {
        listFood.renewOreder();
    }
}
