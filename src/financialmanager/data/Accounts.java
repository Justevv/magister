package financialmanager.data;

public class Accounts {
    Integer Id;
    String Name;

    public Accounts(Integer Id, String Name) {
        this.Id = Id;
        this.Name = Name;
    }

    public Integer getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }
}