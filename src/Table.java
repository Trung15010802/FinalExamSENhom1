import java.util.List;

public class Table {
    private String idTable;
    private double thanhTien;
    private List<Food> monAnOrdered;
    private boolean checkDone;

    public String getIdTable() {
        return idTable;
    }

    public double getThanhTien() {
        return thanhTien;
    }

    public boolean isCheckDone() {
        return checkDone;
    }

    public Table() {
    }

    public Table(String idTable, double thanhTien, List<Food> monAnOrdered) {
        this.idTable = idTable;
        this.thanhTien = thanhTien;
        this.monAnOrdered = monAnOrdered;
    }

    Table createTable(String idTable, double thanhTien, List<Food> monAnOrder) {
        return new Table(idTable, thanhTien, monAnOrdered);
    }
}
