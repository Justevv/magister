package financialmanager.table;

import financialmanager.data.Places;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class PlacesTable extends AbstractTableModel {
    private List<Places> places;

    public PlacesTable(List<Places> places) {
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
                return places.get(r).getId();
            case 1:
                return places.get(r).getName();
            case 2:
                return places.get(r).getAddress();
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
                result = "Adress";
                break;
        }
        return result;
    }

    public List<Places> getPlaces() {
        return places;
    }

    public void setPlaces(List<Places> places) {
        this.places = places;
    }
}