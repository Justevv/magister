package financialmanager.data;

public class Places {
    Integer id;
    String name;
    String adress;

    public Places(Integer id, String name, String adress) {
        this.id = id;
        this.name = name;
        this.adress = adress;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAdress() {
        return adress;
    }
}