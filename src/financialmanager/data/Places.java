package financialmanager.data;

public class Places {
    private int id;
    private String name;
    private  String address;

    public Places(int id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }
}