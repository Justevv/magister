package financialmanager.data;

public class Expenses {
    Integer Id;
    String Date;
    String UserSurname;
    String CategoryName;
    String PlaceName;
    String PaymentTypeName;
    Integer Sum;
    String Account;
    String TransactionType;

    public Expenses(Integer Id, String Date, String UserSurname, String CategoryName, String PlaceName, String PaymentTypeName, Integer Sum, String Account, String TransactionType) {
        this.Id = Id;
        this.Date = Date;
        this.UserSurname = UserSurname;
        this.CategoryName = CategoryName;
        this.PlaceName = PlaceName;
        this.PaymentTypeName = PaymentTypeName;
        this.Sum = Sum;
        this.Account = Account;
        this.TransactionType = TransactionType;
    }

    public Integer getId() {
        return Id;
    }

    public String getdtDate() {
        return Date;
    }

    public String getUserSurname() {
        return UserSurname;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public String getPlaceName() {
        return PlaceName;
    }

    public String getPaymentTypeName() {
        return PaymentTypeName;
    }

    public Integer getSum() {
        return Sum;
    }

    public String getAccount() {
        return Account;
    }

    public String getTransactionType() {
        return TransactionType;
    }
}