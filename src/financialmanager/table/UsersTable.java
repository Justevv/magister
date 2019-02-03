package financialmanager.table;

import financialmanager.data.Users;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class UsersTable extends AbstractTableModel{
    ArrayList<Users> users;
    public UsersTable(ArrayList<Users> users) {
        super();
        this.users = users;
    }

    @Override
    public int getRowCount() {
        return users.size();
    }
    @Override
    public int getColumnCount() {
        return 7;
    }
    @Override
    public Object getValueAt(int r, int c) {
        switch (c) {
            case 0:
                return users.get(r).getId();
            case 1:
                return users.get(r).getSurname();
            case 2:
                return users.get(r).getName();
            case 3:
                return users.get(r).getBirthday();
            case 4:
                return users.get(r).getSex();
            case 5:
                return users.get(r).getPhone();
            case 6:
                return users.get(r).getEmail();
            default:
                return "";
        }
    }

    @Override
    public String getColumnName(int c) {
        String result = "";
        switch (c) {
            case 0:
                result = "nId";
                break;
            case 1:
                result = "Surname";
                break;
            case 2:
                result = "Name";
                break;
            case 3:
                result = "Birthday";
                break;
            case 4:
                result = "Sex";
                break;
            case 5:
                result = "Phone";
                break;
            case 6:
                result = "Email";
                break;
        }
        return result;
    }

}