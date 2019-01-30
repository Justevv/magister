package financialManager.table;

import financialManager.data.Accounts;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class AccountsTable extends AbstractTableModel {
    ArrayList<Accounts> accounts;

    public AccountsTable(ArrayList<Accounts> accounts) {
        super();
        this.accounts = accounts;
    }

    @Override
    public int getRowCount() {
        return accounts.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public Object getValueAt(int r, int c) {
        switch (c) {
            case 0:
                return accounts.get(r).getnId();
            case 1:
                return accounts.get(r).getsName();
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
                result = "Name";
                break;
        }
        return result;
    }

}