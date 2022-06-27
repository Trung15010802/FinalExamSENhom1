public class CreditCard {
    private String name;
    private String idCreditCard;
    private int bonusPoint;

    public String getName() {
        return name;
    }

    public String getIdCreditCard() {
        return idCreditCard;
    }

    public int getBonusPoint() {
        return bonusPoint;
    }

    public CreditCard(String name, String idCreditCard, int bonusPoint) {
        this.name = name;
        this.idCreditCard = idCreditCard;
        this.bonusPoint = bonusPoint;
    }

}
