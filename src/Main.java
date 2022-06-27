import java.util.Scanner;

public class Main {
    private static Scanner sc = new Scanner(System.in);
    private static Actions command;

    public static void main(String[] args) {
        ListTable.createListTable();
        try {
            while (true) {
                UI ui = display();
                option(ui);
            }
        } catch (Exception e) {
            System.out.println("[ERROR] Có lỗi xảy ra vui lòng mở lại chương trình!");
        }
    }

    static UI display() {
        System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-");
        System.out.println("WELCOME TO THE SELF RESTAURANT!");
        System.out.println("Who are you ?");
        System.out.println("[DI] Thực khách.");
        System.out.println("[CH] Đầu bếp");
        System.out.println("[CA] Thu ngân");
        System.out.println("[WE] Người duyệt web");
        System.out.println("[CP] Thoát chương trình!");
        System.out.print("Mời nhập lệnh cần thực hiện: ");
        String person = sc.nextLine().toUpperCase();

        command = Actions.valueOf(person);

        if (command.equals(Actions.DI)) {
            return new DinersUI();
        } else if (command.equals(Actions.CH)) {
            return new ChefUI();
        } else if (command.equals(Actions.CA))
            return new CashierUI();
        else if (command.equals(Actions.WE))
            return new WebUI();
        else if (command.equals(Actions.CP)) {
            System.out.println("GOODBYE! (●'◡'●)");
            System.exit(0);
        } else
            System.out.println("[ERROR] Lệnh không khả dụng!");
        System.out.println();
        return null;
    }

    static void option(UI ui) {
        do {
            String key = null;
            System.out.println("==============================");
            System.out.println();
            ui.displayOptions();
            System.out.print("\nNhập lệnh muốn thực thi: ");
            String respond = sc.nextLine();
            System.out.println(ui.handleCommands(respond));
            ui.handleInputs();
            System.out.print("[EX] Thoát ra màn hình chính(hoặc enter để quay lại menu chức năng trước đó): ");
            key = sc.nextLine();
            if (key.equalsIgnoreCase("EX"))
                break;
        } while (true);

    }
}
