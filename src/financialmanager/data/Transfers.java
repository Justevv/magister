package financialmanager.data;

public class Transfers {
    Integer Id;
    String AccountSenderId;
    String AccountRecipientId;
    Integer Sum;

    public Transfers(Integer Id, String AccountSenderId, String AccountRecipientId, Integer Sum) {
        this.Id = Id;
        this.AccountSenderId = AccountSenderId;
        this.AccountRecipientId = AccountRecipientId;
        this.Sum = Sum;
    }

    public Integer getId() {
        return Id;
    }
    public String getAccountSenderId() {
        return AccountSenderId;
    }
    public String getAccountRecipientId() {
        return AccountRecipientId;
    }
    public Integer getSum() {
        return Sum;
    }
}