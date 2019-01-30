package financialManager.table;

import financialManager.data.Categories;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class CategoriesTable extends AbstractTableModel{
    ArrayList<Categories> categories;
    public CategoriesTable(ArrayList<Categories> categories) {
        super();
        this.categories = categories;
    }

    @Override
    public int getRowCount() {
        return categories.size();
    }
    @Override
    public int getColumnCount() {
        return 3;
    }
    @Override
    public Object getValueAt(int r, int c) {
        switch (c) {
            case 0:
                return categories.get(r).getnId();
            case 1:
                return categories.get(r).getsName();
            case 2:
                return categories.get(r).getnParentId();
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
            case 2:
                result = "ParentId";
                break;
        }
        return result;
    }

}