package financialmanager.table;

import financialmanager.data.Accounts;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class AccountsTable extends AbstractTableModel {
    List<Accounts> accounts;

    public AccountsTable(List<Accounts> accounts) {
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
                return accounts.get(r).getId();
            case 1:
                return accounts.get(r).getName();
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