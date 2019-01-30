package financialManager.data;

public class Expenses {
    Integer nId;
    String dtDate;
    String nUserSurname;
    String nCategoryName;
    String nPlaceName;
    String nPaymentTypeName;
    Integer dSum;
    String nAccount;
    String nTransactionType;

    public Expenses(Integer nId, String dtDate, String nUserSurname, String nCategoryName, String nPlaceName, String nPaymentTypeName, Integer dSum, String nAccount, String nTransactionType) {
        this.nId = nId;
        this.dtDate = dtDate;
        this.nUserSurname = nUserSurname;
        this.nCategoryName = nCategoryName;
        this.nPlaceName = nPlaceName;
        this.nPaymentTypeName = nPaymentTypeName;
        this.dSum = dSum;
        this.nAccount = nAccount;
        this.nTransactionType = nTransactionType;
            }

    public Integer getnId() {
        return nId;
    }

    public String getdtDate() {
        return dtDate;
    }

    public String getnUserSurname() {
        return nUserSurname;
    }

    public String getnCategoryName() {
        return nCategoryName;
    }

    public String getnPlaceName() {
        return nPlaceName;
    }

    public String getnPaymentTypeName() {
        return nPaymentTypeName;
    }

    public Integer getdSum() {
        return dSum;
    }
    public String getnAccount() {
        return nAccount;
    }
    public String getnTransactionType() {
        return nTransactionType;
    }
}