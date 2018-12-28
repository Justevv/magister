package financialmanager.data;

public class Expenses {
    Integer nId;
    String dtDate;
    String nUserSurname;
    String nCategoryName;
    String nPlaceName;
    String nPaymentTypeName;
    Integer dSum;

    public Expenses(Integer nId, String dtDate, String nUserSurname, String nCategoryName, String nPlaceName, String nPaymentTypeName, Integer dSum) {
        this.nId = nId;
        this.dtDate = dtDate;
        this.nUserSurname = nUserSurname;
        this.nCategoryName = nCategoryName;
        this.nPlaceName = nPlaceName;
        this.nPaymentTypeName = nPaymentTypeName;
        this.dSum = dSum;
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
}