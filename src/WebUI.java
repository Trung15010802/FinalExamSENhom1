import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class WebUI implements UI {
    private WebActions command;
    private WebManager webManager = new WebManager();

    @Override
    public void displayOptions() {
        System.out.println("[MN]: Hiển thị menu món ăn được phục vụ hôm nay");
    }

    @Override
    public String handleCommands(String respond) {
        String cmd = respond.toUpperCase();
        command = WebActions.valueOf(cmd);
        if (command.equals(WebActions.MN))
            return "Menu các món ăn được phục vụ hôm nay: ";
        else
            return "Sai lệnh vui lòng nhập lại";
    }

    @Override
    public void handleInputs() {
        if (command.equals(WebActions.MN)) {
            List<Object> list = webManager.menu();
            for (int i = 0; i < list.size(); i += 3) {
                Locale localeVN = new Locale("vi", "VN");
                NumberFormat dg = NumberFormat.getIntegerInstance(localeVN);
                System.out.printf("%10s%10s%10s\n", list.get(i), list.get(i + 1), dg.format(list.get(i + 2)));
            }
        } else
            System.out.println("[ERROR COMMAND] Sai lựa chọn vui lòng nhập lại");
    }

}
