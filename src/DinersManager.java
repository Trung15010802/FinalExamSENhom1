import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class DinersManager {
    private ListTable listTable = new ListTable();
    private ListFood listFood = new ListFood();
    private ListCreditCard listCreditCard = new ListCreditCard();

    boolean chonMon(String idTable, String idMonAn, int soLanGoiMon) { // Hàm chọn món ăn
        JsonArray jsonArray = listTable.updateOrdered(idTable, idMonAn, soLanGoiMon);
        if (jsonArray == null) {
            return false;
        }
        listTable.setFoodOrdered(idTable, jsonArray);
        return true;

    }

    double xemThanhTien(String idTable) {
        return listTable.tinhThanhTien(idTable);
    }

    List<String> hienThiMonAnDaXong(String idTable) {
        List<String> messages = new ArrayList<>();
        JsonArray listDone = listTable.hienThiMonAnServed(idTable);
        for (int i = 0; i < listDone.size(); i++) {
            JsonObject food = listDone.get(i).getAsJsonObject();
            messages.add("Món: " + food.get("Name").getAsString() + " || Số lượng: " + food.get("SoLuong").getAsString()
                    + " || Đon giá: " + food.get("DonGia").getAsDouble());
        }
        return messages;
    }

    boolean dangKyThanhVien(CreditCard creditCard) { // Đăng ký thành viên và kiểm tra thêm id có bị trùng không
        if (listCreditCard.searchCreditCard(creditCard.getIdCreditCard()) == null) {
            listCreditCard.updateCreditCard(creditCard.getName(), creditCard.getIdCreditCard());
            return true;
        }
        else return false;

    }

    double congDiemTV(String iDCreditCard) { // cộng điểm thành viên
        return listCreditCard.addBonusPoint(iDCreditCard);
    }

    List<Object> xemMenu() { // xem menu
        return listFood.hienThiMenu();
    }
}
