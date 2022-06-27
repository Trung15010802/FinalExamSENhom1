import java.util.List;

public class ChefUI implements UI {
    private ChefActions command;
    ChefManager chefManager = new ChefManager();

    @Override
    public void displayOptions() {
        System.out.println("[TMA] Thêm món ăn");
        System.out.println("[BMA] Bớt món ăn");
        System.out.println("[TTMA] Hiển thị thông tin món ăn cần chuẩn bị");
        System.out.println("[TBDX] Thông báo món ăn đã chuẩn bị xong");
    }

    @Override
    public String handleCommands(String respond) { // Xử lý lệnh người dùng nhập
        String cmd = respond.toUpperCase();
        command = ChefActions.valueOf(cmd);
        if (command.equals(ChefActions.TMA))
            return "Nhập thông tin món ăn cần thêm";
        else if (command.equals(ChefActions.BMA))
            return "Nhập ID món ăn cần bớt:";
        else if (command.equals(ChefActions.TBDX))
            return "Thông báo cho khách biết món ăn đã được chuẩn bị xong";
        else if (command.equals(ChefActions.TTMA))
            return "Hiển thị thông tin món ăn được order";
        else
            return " [ERROR COMMAND] Sai lựa chọn vui lòng nhập lại";
    }

    @Override
    @SuppressWarnings("unchecked")
    public void handleInputs() { // Xử lý lệnh người dụng nhập
        if (command.equals(ChefActions.TMA)) {
            Boolean check = chefManager.themMonAn(addNewFood());
            if (check) {
                System.out.println("Đã thêm");
            } else {
                System.out.println("Trùng ID");
            }
        } else if (command.equals(ChefActions.BMA)) {
            String idMonAn = sc.nextLine();
            Boolean check = chefManager.botMonAn(idMonAn);
            if (check) {
                System.out.println("Đã bớt thành công");
            } else {
                System.out.println("Không tìm thấy món");
            }
        } else if (command.equals(ChefActions.TTMA)) {
            List<Object> list = chefManager.hienThiMonOdered();
            System.out.println(list.get(1));
            List<String> lists = (List<String>) list.get(2);
            for (Object object : lists) {
                System.out.println(object);
            }

        } else if (command.equals(ChefActions.TBDX)) {
            System.out.print("Nhập ID món đã chuẩn bị xong: ");
            String IDFood = sc.nextLine();
            System.out.print("Nhập bàn đã gọi món: ");

            String IDTable = sc.nextLine();
            List<Object> list = chefManager.monAnDaxong(IDTable, IDFood);
            for (Object object : list) {
                System.out.println("- " + object);
            }
        } else
            System.out.println("[ERROR COMMAND] Sai lựa chọn vui lòng nhập lại");

    }

    Food addNewFood() {
        System.out.println();
        System.out.print("Nhập mã món ăn cần thêm: ");
        String idMonAn = sc.nextLine();
        System.out.print("Nhập tên món ăn cần thêm: ");
        String name = sc.nextLine();
        System.out.print("Nhập đơn giá món ăn cần thêm:");
        double donGia = sc.nextDouble();
        sc.nextLine();
        return new Food(name, idMonAn, donGia);
    }

}
