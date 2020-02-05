package financialmanager.table;

import financialmanager.data.Counters;
import financialmanager.gui.OpenWindow;
import financialmanager.text.CounterType;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class CountersTable extends AbstractTableModel {
    private List<Counters> electricity = new ArrayList<>();
    private List<Counters> gas = new ArrayList<>();
    private List<Counters> water = new ArrayList<>();

    public CountersTable(List<Counters> counters) {
        setCounters(counters);
    }

    @Override
    public int getRowCount() {
        return electricity.size();
    }

    @Override
    public int getColumnCount() {
        return 23;
    }

    @Override
    public Object getValueAt(int r, int c) {
        financialmanager.businesslogic.Counters counter = new financialmanager.businesslogic.Counters();
        int rHistory = 0;
        if (r > 0) {
            rHistory = r - 1;
        }
        float gasMonthValue = counter.getMonthVolume(gas.get(r).getValue(), gas.get(rHistory).getValue());
        float electricityMonthValue = counter.getMonthVolume(electricity.get(r).getValue(), electricity.get(rHistory).getValue());
        float waterMonthValue = counter.getMonthVolume(water.get(r).getValue(), water.get(rHistory).getValue());
        switch (c) {
            case 0:
                return gas.get(r).getId();
            case 1:
                return gas.get(r).getDate();
            case 2:
                return OpenWindow.userLogin;
            case 3:
                return gas.get(r).getValue();
            case 4:
                return electricity.get(r).getValue();
            case 5:
                return water.get(r).getValue();
            case 6:
                return gas.get(r).getPrice();
            case 7:
                return electricity.get(r).getPrice();
            case 8:
                return water.get(r).getPrice();
            case 9:
                return counter.getSum(gasMonthValue, gas.get(r).getPrice());
            case 10:
                return counter.getSum(electricityMonthValue, electricity.get(r).getPrice());
            case 11:
                return counter.getSum(waterMonthValue, water.get(r).getPrice());
            case 12:
                return counter.getMonthVolume(gas.get(r).getValue(), gas.get(rHistory).getValue());
            case 13:
                return counter.getMonthVolume(electricity.get(r).getValue(), electricity.get(rHistory).getValue());
            case 14:
                return counter.getMonthVolume(water.get(r).getValue(), water.get(rHistory).getValue());
            case 15:
                return counter.getDayVolume(gas.get(r).getDate(), gas.get(rHistory).getDate(), gasMonthValue);
            case 16:
                return counter.getDayVolume(electricity.get(r).getDate(), electricity.get(rHistory).getDate(), electricityMonthValue);
            case 17:
                return counter.getDayVolume(water.get(r).getDate(), water.get(rHistory).getDate(), waterMonthValue);
            case 18:
                return gas.get(r).getPaid();
            case 19:
                return electricity.get(r).getPaid();
            case 20:
                return water.get(r).getPaid();
            case 21:
                return counter.getSumTotal(gasMonthValue, gas.get(r).getPrice(), electricityMonthValue, electricity.get(r).getPrice(), waterMonthValue, water.get(r).getPrice());
            case 22:
                return counter.getSumTotal(gas.get(r).getPaid(), gas.get(r).getPrice(), electricity.get(r).getPaid(), electricity.get(r).getPrice(), water.get(r).getPaid(), water.get(r).getPrice());
            default:
                return "";
        }
    }

    @Override
    public String getColumnName(int c) {
        String result = "";
        switch (c) {
            case 0:
                result = "Номер операции";
                break;
            case 1:
                result = "Дата";
                break;
            case 2:
                result = "Пользователь";
                break;
            case 3:
                result = "Газ показания";
                break;
            case 4:
                result = "Электричество показания";
                break;
            case 5:
                result = "Вода показания";
                break;
            case 6:
                result = "Газ цена";
                break;
            case 7:
                result = "Электричество цена";
                break;
            case 8:
                result = "Вода цена";
                break;
            case 9:
                result = "Газ сумма";
                break;
            case 10:
                result = "Электричество сумма";
                break;
            case 11:
                result = "Вода сумма";
                break;
            case 12:
                result = "Газ объем за месяц";
                break;
            case 13:
                result = "Электричество объем за месяц";
                break;
            case 14:
                result = "Вода объем за месяц";
                break;
            case 15:
                result = "Газ объем за день";
                break;
            case 16:
                result = "Электричество объем за день";
                break;
            case 17:
                result = "Вода объем за день";
                break;
            case 18:
                result = "Газ оплачено";
                break;
            case 19:
                result = "Электричество оплачено";
                break;
            case 20:
                result = "Вода оплачено";
                break;
            case 21:
                result = "Сумма потребления";
                break;
            case 22:
                result = "Сумма оплачено";
                break;
        }
        return result;
    }

    public void setCounters(List<Counters> counters) {
        electricity.removeAll(electricity);
        gas.removeAll(gas);
        water.removeAll(water);
        for (Counters counter : counters) {
            CounterType counterType = counter.getType();
            switch (counterType) {
                case GAS:
                    gas.add(counter);
                    break;
                case WATER:
                    water.add(counter);
                    break;
                case ELECTRICITY:
                    electricity.add(counter);
                    break;
            }
        }
    }

}