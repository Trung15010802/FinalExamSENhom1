import java.util.Scanner;

public interface UI {
    public Scanner sc = new Scanner(System.in);

    abstract String handleCommands(String respond);

    abstract void displayOptions();

    abstract void handleInputs();
}
