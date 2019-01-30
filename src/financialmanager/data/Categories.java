package financialmanager.data;

public class Categories {
    Integer nId;
    String sName;
    Integer nParentId;

    public Categories(Integer nId, String sName, Integer nParentId) {
        this.nId = nId;
        this.sName = sName;
        this.nParentId = nParentId;
    }

    public Integer getnId() {
        return nId;
    }

    public String getsName() {
        return sName;
    }

    public Integer getnParentId() {
        return nParentId;
    }
}