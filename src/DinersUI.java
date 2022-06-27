import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class DinersUI implements UI {
    private DinersActions command;
    private DinersManager dinersManager = new DinersManager();
    private String tableID = new String();

    public DinersUI() {
        if (tableID.isBlank()) {
            int n = -1;
            do {
                System.out.print("Nhập số bàn bạn đang ngồi từ 1 đến 30: ");
                n = sc.nextInt();
            } while (n < 1 || n > 30);
            tableID = "T" + (n);
            sc.nextLine();
        }
    }

    @Override
    public void displayOptions() { // Hiển thị lựa chọn cho thực khách
        System.out.println("[CM]: Chọn món!");
        System.out.println("[XHD]: Xem hoá đơn!");
        System.out.println("[DKTV]: Đăng ký thành viên");
        System.out.println("[QT] Quẹt thẻ cộng điểm thành viên(1000 điểm/1 lần)");
    }

    @Override
    public String handleCommands(String respond) { // Xử lý lệnh người dùng nhập
        String cmd = respond.toUpperCase();
        command = DinersActions.valueOf(cmd);
        if (command.equals(DinersActions.CM))
            return "Vui lòng chọn món: ";
        else if (command.equals(DinersActions.DKTV))
            return "Nhập tên và ID thẻ";
        else if (command.equals(DinersActions.XHD))
            return "Hoá đơn của bạn: ";
        else if (command.equals(DinersActions.QT))
            return "Vui lòng quẹt thẻ ! ";
        else
            return "Sai lệnh vui lòng nhập lại";
    }

    @Override
    public void handleInputs() { // Xử lý input và thực thi câu lệnh
        double tt;
        if (command.equals(DinersActions.CM)) {
            List<Object> list = dinersManager.xemMenu();
            for (int i = 0; i < list.size(); i += 3) {
                tt = (double) list.get(i + 2);
                System.out.printf("%10s%10s%10s\n", list.get(i), list.get(i + 1), chuyenTienTe(tt));
            }
            String nhapTiep;
            do {
                System.out.print("Nhập id món ăn bạn muốn: ");
                String idMonAn = sc.nextLine();

                int soLanGoiMon = -1;
                do {
                    System.out.print("Nhập số lượng: ");
                    soLanGoiMon = sc.nextInt();
                    if (soLanGoiMon < 1) {
                        System.out.println("[ERROR] Số lượng gọi món phải lớn hơn 0");
                    }
                } while (soLanGoiMon < 1);

                sc.nextLine();
                Boolean check = dinersManager.chonMon(tableID, idMonAn, soLanGoiMon);
                if (check) {
                    System.out.println("[DONE] Đã lưu món ăn được chọn!");
                } else {
                    System.out.println("[ERROR] Mã món ăn vừa nhập không đúng");
                }
                System.out.print("Tiếp tục chọn ? ( Hint: Nhập N để thoát hoặc nhấn enter để tiếp tục): ");
                nhapTiep = sc.nextLine();
            } while (!nhapTiep.equalsIgnoreCase("N"));

        } else if (command.equals(DinersActions.XHD)) {

            List<String> listDone = dinersManager.hienThiMonAnDaXong(tableID);
            for (String string : listDone) {
                System.out.println(string);
            }
            System.out.println("Thành tiền: " + chuyenTienTe(dinersManager.xemThanhTien(tableID)));
        } else if (command.equals(DinersActions.DKTV)) {
            if (dinersManager.dangKyThanhVien(newCreditCard()))
                System.out.println("Đăng ký thành công!");
            else
                System.out.println("Đăng ký thất bại, id thẻ bị trùng");
        } else if (command.equals(DinersActions.QT)) {
            System.out.print("Nhập ID Credit Card của bạn: ");
            String iDCreditCard = sc.nextLine();
            System.out.println("Điểm thưởng của bạn là: " + dinersManager.congDiemTV(iDCreditCard));
        } else
            System.out.println("[ERROR COMMAND] Sai lựa chọn vui lòng nhập lại");
    }

    CreditCard newCreditCard() {
        System.out.print("Nhập tên khách hàng: ");
        String name = sc.nextLine();
        System.out.print("Nhập id thẻ: ");
        String idCreditCard = sc.nextLine();
        return new CreditCard(name, idCreditCard, 0);
    }

    String chuyenTienTe(double tien) {
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat dg = NumberFormat.getIntegerInstance(localeVN);
        String xuat = dg.format(tien);
        return xuat;
    }

}
