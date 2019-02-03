package financialmanager.data;

public class Places {
    Integer Id;
    String Name;
    String Adress;

    public Places(Integer Id, String Name, String Adress) {
        this.Id = Id;
        this.Name = Name;
        this.Adress = Adress;
    }

    public Integer getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public String getAdress() {
        return Adress;
    }
}