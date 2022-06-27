import java.util.List;

public class WebManager {
    private ListFood listFood = new ListFood();

    List<Object> menu() {
        return listFood.hienThiMenu();
    }

}
