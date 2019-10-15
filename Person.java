/**
 * created by mustafa.tozluoglu on 4.10.2019
 */
public class Person {

    private String name;
    private String cardNumber;
    private int tenDays;
    private int threeMonths;
    private int oneYear;

    public Person(String name, String cardNumber) {
        this.name = name;
        this.cardNumber = cardNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public int getTenDays() {
        return tenDays;
    }

    public void setTenDays(int tenDays) {
        this.tenDays = tenDays;
    }

    public int getThreeMonths() {
        return threeMonths;
    }

    public void setThreeMonths(int threeMonths) {
        this.threeMonths = threeMonths;
    }

    public int getOneYear() {
        return oneYear;
    }

    public void setOneYear(int oneYear) {
        this.oneYear = oneYear;
    }

    public String toString() {
        return name + " || " + cardNumber + " || " + tenDays + " || " + threeMonths + " || " + oneYear + "\n";
    }
}
