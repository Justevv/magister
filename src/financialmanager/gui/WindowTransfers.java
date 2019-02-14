package financialmanager.gui;

import financialmanager.database.DbTransfers;
import financialmanager.table.TransfersTable;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WindowTransfers extends JFrame implements ActionListener {
    private JButton buttonAddTransfer = new JButton("Добавить перевод");
    private JButton buttonDeleteTransfer = new JButton("Удалить перевод");
    private JButton buttonUpdateTransfer = new JButton("Редактировать перевод");
    public static TransfersTable modelTransfers;
    private JTable jTabTransfer;
    public static String result;
    public static String action;
    public static int[] selectedRows;
    public static int[] selectedColumns;
    public static int i;
    public static int selIndex;
    public static TableModel model;
    public static Object currentId;

    public WindowTransfers() {

        super("Финансовый менеджер");
        this.setBounds(100, 100, 650, 400);
        //  this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        DbTransfers.select(OpenWindow.userLogin);

        GridBagConstraints c = new GridBagConstraints();
        Container container = this.getContentPane();
        container.setLayout(new GridBagLayout());

        JFrame jfrm = new JFrame("JTableExample");
        modelTransfers = new TransfersTable(DbTransfers.Transfers);
        //На основе модели, создадим новую JTable
        jTabTransfer = new JTable(modelTransfers);
        //Создаем панель прокрутки и включаем в ее состав нашу таблицу
        JScrollPane jscrlp = new JScrollPane(jTabTransfer);
        //Устаналиваем размеры прокручиваемой области
        jTabTransfer.setPreferredScrollableViewportSize(new Dimension(550, 200));
        //Добавляем в контейнер нашу панель прокрути и таблицу вместе с ней
        jfrm.getContentPane().add(jscrlp);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridwidth = 5;
        c.gridheight = 2;
        c.gridx = 0;
        c.gridy = 0;
        container.add(jscrlp, c);

        buttonAddTransfer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                action = "insert";
                Transfer.go();
            }
        });
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(buttonAddTransfer, c);

        buttonDeleteTransfer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DbTransfers.delete(currentId,selectedRows[i-1]);
                modelTransfers.fireTableDataChanged();
            }
        });
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(buttonDeleteTransfer, c);

        buttonUpdateTransfer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                action = "update";
                Transfer.go();
            }
        });
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(buttonUpdateTransfer, c);

        ListSelectionModel selModel = jTabTransfer.getSelectionModel();
        selModel.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                result = "";
                selectedRows = jTabTransfer.getSelectedRows();
                selectedColumns = jTabTransfer.getSelectedColumns();
                for (i = 0; i < selectedRows.length; i++) {
                    selIndex = selectedRows[i];
                    model = jTabTransfer.getModel();
                    currentId = model.getValueAt(selIndex, 0);
                }
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public static void main(String[] args) {
        WindowTransfers app = new WindowTransfers();
        app.setVisible(true);
    }

    public static void go() {
        WindowTransfers app = new WindowTransfers();
        app.setVisible(true);
    }
}
