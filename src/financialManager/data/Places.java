package financialManager.data;

public class Places {
    Integer nId;
    String sName;
    String sAdress;

    public Places(Integer nId, String sName, String sAdress) {
        this.nId = nId;
        this.sName = sName;
        this.sAdress = sAdress;
    }

    public Integer getnId() {
        return nId;
    }

    public String getsName() {
        return sName;
    }

    public String getsAdress() {
        return sAdress;
    }
}