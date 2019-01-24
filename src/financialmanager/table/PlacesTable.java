package financialmanager.table;

import financialmanager.data.Places;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class PlacesTable extends AbstractTableModel{
    ArrayList<Places> places;
    public PlacesTable(ArrayList<Places> places) {
        super();
        this.places = places;
    }

    @Override
    public int getRowCount() {
        return places.size();
    }
    @Override
    public int getColumnCount() {
        return 3;
    }
    @Override
    public Object getValueAt(int r, int c) {
        switch (c) {
            case 0:
                return places.get(r).getnId();
            case 1:
                return places.get(r).getsName();
            case 2:
                return places.get(r).getsAdress();
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
                result = "Adress";
                break;
        }
        return result;
    }

}