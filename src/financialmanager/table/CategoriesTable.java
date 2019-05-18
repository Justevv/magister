package financialmanager.table;

import financialmanager.data.Categories;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class CategoriesTable extends AbstractTableModel {
    private List<Categories> categories;

    public CategoriesTable(List<Categories> categories) {
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
                return categories.get(r).getId();
            case 1:
                return categories.get(r).getName();
            case 2:
                return categories.get(r).getParentId();
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
                result = "Name";
                break;
            case 2:
                result = "ParentId";
                break;
        }
        return result;
    }

    public List<Categories> getCategories() {
        return categories;
    }

    public void setCategories(List<Categories> categories) {
        this.categories = categories;
    }
}