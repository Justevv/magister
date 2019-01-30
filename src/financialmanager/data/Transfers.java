package financialmanager.data;

public class Transfers {
    Integer nId;
    String nAccountSenderId;
    String nAccountRecipientId;
    Integer dSum;

    public Transfers(Integer nId, String nAccountSenderId, String nAccountRecipientId, Integer dSum) {
        this.nId = nId;
        this.nAccountSenderId = nAccountSenderId;
        this.nAccountRecipientId = nAccountRecipientId;
        this.dSum = dSum;
    }

    public Integer getnId() {
        return nId;
    }
    public String getnAccountSenderId() {
        return nAccountSenderId;
    }
    public String getnAccountRecipientId() {
        return nAccountRecipientId;
    }
    public Integer getdSum() {
        return dSum;
    }
}