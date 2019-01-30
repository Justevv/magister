package financialmanager.table;

import financialmanager.data.Transfers;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class TransfersTable extends AbstractTableModel{
    ArrayList<Transfers> transfers;
    public TransfersTable(ArrayList<Transfers> transfers) {
        super();
        this.transfers = transfers;
    }

    @Override
    public int getRowCount() {
        return transfers.size();
    }
    @Override
    public int getColumnCount() {
        return 4;
    }
    @Override
    public Object getValueAt(int r, int c) {
        switch (c) {
            case 0:
                return transfers.get(r).getnId();
            case 1:
                return transfers.get(r).getnAccountSenderId();
            case 2:
                return transfers.get(r).getnAccountRecipientId();
            case 3:
                return transfers.get(r).getdSum();
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
                result = "Отправитель";
                break;
            case 2:
                result = "Получатель";
                break;
            case 3:
                result = "Сумма";
                break;
        }
        return result;
    }

}