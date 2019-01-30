package financialmanager.table;

import financialmanager.data.Expenses;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class ExpensesTable extends AbstractTableModel {
    ArrayList<Expenses> expenses;

    public ExpensesTable(ArrayList<Expenses> expenses) {
        super();
        this.expenses = expenses;
    }

    @Override
    public int getRowCount() {
        return expenses.size();
    }

    @Override
    public int getColumnCount() {
        return 9;
    }

    @Override
    public Object getValueAt(int r, int c) {
        switch (c) {
            case 0:
                return expenses.get(r).getnId();
            case 1:
                return expenses.get(r).getdtDate();
            case 2:
                return expenses.get(r).getnUserSurname();
            case 3:
                return expenses.get(r).getnCategoryName();
            case 4:
                return expenses.get(r).getnPlaceName();
            case 5:
                return expenses.get(r).getnPaymentTypeName();
            case 6:
                return expenses.get(r).getdSum();
            case 7:
                return expenses.get(r).getnAccount();
            case 8:
                return expenses.get(r).getnTransactionType();
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
                result = "Date";
                break;
            case 2:
                result = "Surname";
                break;
            case 3:
                result = "Category";
                break;
            case 4:
                result = "Place";
                break;
            case 5:
                result = "PaymentType";
                break;
            case 6:
                result = "Sum";
                break;
            case 7:
                result = "Account";
                break;
            case 8:
                result = "TransactionType";
                break;
        }
        return result;
    }

}