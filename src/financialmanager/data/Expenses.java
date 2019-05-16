package financialmanager.data;

public class Expenses {
    private int id;
    private String date;
    private String userSurname;
    private String categoryName;
    private String placeName;
    private String paymentTypeName;
    private int sum;
    private String account;
    private String transactionType;

    public Expenses(int id, String date, String userSurname, String categoryName, String placeName, String paymentTypeName, int sum, String account, String transactionType) {
        this.id = id;
        this.date = date;
        this.userSurname = userSurname;
        this.categoryName = categoryName;
        this.placeName = placeName;
        this.paymentTypeName = paymentTypeName;
        this.sum = sum;
        this.account = account;
        this.transactionType = transactionType;
    }

    public Integer getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getUserSurname() {
        return userSurname;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getPlaceName() {
        return placeName;
    }

    public String getPaymentTypeName() {
        return paymentTypeName;
    }

    public Integer getSum() {
        return sum;
    }

    public String getAccount() {
        return account;
    }

    public String getTransactionType() {
        return transactionType;
    }
}