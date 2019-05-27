package financialmanager.gui;

import financialmanager.database.DbTransfers;
import financialmanager.table.TransfersTable;
import financialmanager.text.Actions;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WindowTransfers extends JFrame implements ActionListener {
    static TransfersTable modelTransfers;
    private JTable jTabTransfer;
    static Actions action;
    private int[] selectedRows;
//    private int[] selectedColumns;
    private int i;
    static int selIndex;
    static TableModel model;
    static Object currentId;

    private WindowTransfers() {

        super("Финансовый менеджер");
        this.setBounds(100, 100, 650, 400);
        //  this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GridBagConstraints c = new GridBagConstraints();
        Container container = this.getContentPane();
        container.setLayout(new GridBagLayout());

        DbTransfers dbTransfers = new DbTransfers();
        JFrame jfrm = new JFrame("JTableExample");
        modelTransfers = new TransfersTable(dbTransfers.select(OpenWindow.userLogin));
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

        JButton buttonAddTransfer = new JButton("Добавить перевод");
        buttonAddTransfer.addActionListener(e -> {
            action = Actions.INSERT;
            Transfer.go();
        });
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(buttonAddTransfer, c);

        JButton buttonDeleteTransfer = new JButton("Удалить перевод");
        buttonDeleteTransfer.addActionListener(e -> {
            dbTransfers.delete(currentId);
            modelTransfers.setTransfers(dbTransfers.select(OpenWindow.userLogin));
            modelTransfers.fireTableDataChanged();
        });
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(buttonDeleteTransfer, c);

        JButton buttonUpdateTransfer = new JButton("Редактировать перевод");
        buttonUpdateTransfer.addActionListener(e -> {
            action = Actions.UPDATE;
            Transfer.go();
        });
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(buttonUpdateTransfer, c);

        ListSelectionModel selModel = jTabTransfer.getSelectionModel();
        selModel.addListSelectionListener(e -> {
            selectedRows = jTabTransfer.getSelectedRows();
//            selectedColumns = jTabTransfer.getSelectedColumns();
            for (i = 0; i < selectedRows.length; i++) {
                selIndex = selectedRows[i];
                model = jTabTransfer.getModel();
                currentId = model.getValueAt(selIndex, 0);
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public static void go() {
        WindowTransfers app = new WindowTransfers();
        app.setVisible(true);
    }
}
