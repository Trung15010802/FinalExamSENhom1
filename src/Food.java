public class Food {
    private String name;
    private String idMonAn;
    
    private double donGia;
    private int soLanGoiMon;

    public int getSoLanGoiMon() {
        return soLanGoiMon;
    }

    public Food(String name, String idMonAn, double donGia) {
        this.name = name;
        this.idMonAn = idMonAn;
        this.donGia = donGia;
    }

    public Food(String name, String idMonAn, int soLanGoiMon) {
        this.name = name;
        this.idMonAn = idMonAn;
        this.soLanGoiMon = soLanGoiMon;
    }

    public String getName() {
        return name;
    }

    public String getIdMonAn() {
        return idMonAn;
    }

    public double getDonGia() {
        return donGia;
    }

}
