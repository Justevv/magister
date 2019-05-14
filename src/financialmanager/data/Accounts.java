package financialmanager.data;

import java.util.ArrayList;

public class Accounts {
    private Integer id;
    private String name;
    public static ArrayList<Accounts> accounts = new ArrayList<>();

    public Accounts() {
    }

    public Accounts(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void removeList() {
        accounts.removeAll(accounts);
    }


}