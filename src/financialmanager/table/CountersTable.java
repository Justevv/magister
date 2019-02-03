package financialmanager.table;

import financialmanager.data.Counters;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class CountersTable extends AbstractTableModel {
    ArrayList<Counters> counters;

    public CountersTable(ArrayList<Counters> counters) {
        super();
        this.counters = counters;
    }

    @Override
    public int getRowCount() {
        return counters.size();
    }

    @Override
    public int getColumnCount() {
        return 23;
    }

    @Override
    public Object getValueAt(int r, int c) {
        switch (c) {
            case 0:
                return counters.get(r).getId();
            case 1:
                return counters.get(r).getDate();
            case 2:
                return counters.get(r).getUserSurname();
            case 3:
                return counters.get(r).getGasReadings();
            case 4:
                return counters.get(r).getElectricityReadings();
            case 5:
                return counters.get(r).getWaterReadings();
            case 6:
                return counters.get(r).getGasPrice();
            case 7:
                return counters.get(r).getElectricityPrice();
            case 8:
                return counters.get(r).getWaterPrice();
            case 9:
                return counters.get(r).getGasSum();
            case 10:
                return counters.get(r).getElectricitySum();
            case 11:
                return counters.get(r).getWaterSum();
            case 12:
                return counters.get(r).getGasMonthVolume();
            case 13:
                return counters.get(r).getElectricityMonthVolume();
            case 14:
                return counters.get(r).getWaterMonthVolume();
            case 15:
                return counters.get(r).getGasDayVolume();
            case 16:
                return counters.get(r).getElectricityDayVolume();
            case 17:
                return counters.get(r).getWaterDayVolume();
            case 18:
                return counters.get(r).getGasPaid();
            case 19:
                return counters.get(r).getElectricityPaid();
            case 20:
                return counters.get(r).getWaterPaid();
            case 21:
                return counters.get(r).getConsumptionAmount();
            case 22:
                return counters.get(r).getPaymentAmount();
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
                result = "Фамилия";
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

}