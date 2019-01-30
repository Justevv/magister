package financialManager.data;

public class Accounts {
    Integer nId;
    String sName;

    public Accounts(Integer nId, String sName) {
        this.nId = nId;
        this.sName = sName;
    }

    public Integer getnId() {
        return nId;
    }

    public String getsName() {
        return sName;
    }
}