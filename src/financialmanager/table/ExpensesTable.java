package financialmanager.table;

import financialmanager.data.Expenses;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class ExpensesTable extends AbstractTableModel {
    private List<Expenses> expenses;

    public ExpensesTable(List<Expenses> expenses) {
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
                return expenses.get(r).getId();
            case 1:
                return expenses.get(r).getDate();
            case 2:
                return expenses.get(r).getUserSurname();
            case 3:
                return expenses.get(r).getCategoryName();
            case 4:
                return expenses.get(r).getPlaceName();
            case 5:
                return expenses.get(r).getPaymentTypeName();
            case 6:
                return expenses.get(r).getSum();
            case 7:
                return expenses.get(r).getAccount();
            case 8:
                return expenses.get(r).getTransactionType();
            default:
                return "";
        }
    }

    @Override
    public String getColumnName(int c) {
        String result = "";
        switch (c) {
            case 0:
                result = "Id";
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

    public List<Expenses> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<Expenses> expenses) {
        this.expenses = expenses;
    }
}