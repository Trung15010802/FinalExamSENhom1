import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class CashierUI implements UI {
    private CashierActions command;
    private CashierManager cashierManager = new CashierManager();

    @Override
    public void displayOptions() {
        System.out.println("[XHD]: Xem hoá đơn");
        System.out.println("[DTVMA]: Xuất doanh thu và món ăn");
        System.out.println("[TT]: Nhập và tính tiền thừa");
    }

    @Override
    public String handleCommands(String respond) {
        String cmd = respond.toUpperCase();
        command = CashierActions.valueOf(cmd);
        if (command.equals(CashierActions.XHD))
            return "Xem hoá đơn của thực khách";
        else if (command.equals(CashierActions.TT))
            return "Thanh toán hóa đơn của khách";
        else if (command.equals(CashierActions.DTVMA))
            return "Tổng doanh thu trong ngày và danh sách món ăn đã gọi trong ngày";
        else
            return "[ERROR COMMAND] Sai lựa chọn vui lòng nhập lại";
    }

    @Override
    @SuppressWarnings("unchecked")
    public void handleInputs() {
        if (command.equals(CashierActions.XHD)) {
            System.out.print("Nhập bàn cần xem hoá đơn: ");
            String idTable = sc.nextLine();
            List<Object> listServed = cashierManager.hienThiMonAnServed(idTable);
            Boolean check = (Boolean) listServed.get(0);
            if (check == false) {
                System.out.println(listServed.get(1));
            } else {
                System.out.println(listServed.get(1));
                List<String> listFoodServed = (List<String>) listServed.get(2);
                for (String string : listFoodServed) {
                    System.out.println(string);
                }
                System.out.println("- Thành tiền: " + chuyenTienTe(cashierManager.xemHoaDon(idTable)));
            }
        } else if (command.equals(CashierActions.TT)) {
            System.out.print("- Nhập số tiền khách đưa: ");
            double money = sc.nextDouble();
            sc.nextLine();
            System.out.print("Nhập bàn cần tính tiền: ");
            String idTable = sc.nextLine();
            double thanhTien = cashierManager.nhapVaTinhTienThua(money, idTable);
            if (thanhTien < 0) {
                System.out.println("Không tìm thấy bàn");
            } else {
                System.out.println("- Số tiền thừa là: " + chuyenTienTe(thanhTien));
                cashierManager.renewAndUpdateListFood(idTable);
            }

        } else if (command.equals(CashierActions.DTVMA)) {
            double dt = 0;
            List<Object> list = cashierManager.xuatDoanhThuVaMonAn();
            System.out.printf("%10s %10s\n", "Tên món", "Số lượng");
            for (int i = 0; i < list.size() - 1;) {
                System.out.printf("%10s %7d\n", list.get(i), list.get(i + 1));
                i += 2;
                if (i == list.size() - 1) {
                    dt = (double) list.get(i);
                    System.out.printf("DOANH THU: %10s\n", chuyenTienTe(dt));
                }
            }
            cashierManager.renewOreder();
        } else
            System.out.println("[ERROR COMMAND] Sai lựa chọn vui lòng nhập lại");
    }

    String chuyenTienTe(double tien) {
        Locale localeVn = new Locale("vi", "VN");
        NumberFormat vn = NumberFormat.getCurrencyInstance(localeVn);
        String tienvn = vn.format(tien);
        return tienvn;
    }
}
