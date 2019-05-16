package financialmanager.data;

public class Transfers {
    private int id;
    private String accountSenderId;
    private String accountRecipientId;
    private int sum;

    public Transfers(int id, String accountSenderId, String accountRecipientId, int sum) {
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