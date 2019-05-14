package financialmanager.data;

public class Transfers {
    Integer id;
    String accountSenderId;
    String accountRecipientId;
    Integer sum;

    public Transfers(Integer id, String accountSenderId, String accountRecipientId, Integer sum) {
        this.id = id;
        this.accountSenderId = accountSenderId;
        this.accountRecipientId = accountRecipientId;
        this.sum = sum;
    }

    public Integer getId() {
        return id;
    }
    public String getAccountSenderId() {
        return accountSenderId;
    }
    public String getAccountRecipientId() {
        return accountRecipientId;
    }
    public Integer getSum() {
        return sum;
    }
}