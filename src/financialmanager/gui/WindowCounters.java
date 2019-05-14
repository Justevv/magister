package financialmanager.gui;

import financialmanager.database.DbCounters;
import financialmanager.table.CountersTable;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class WindowCounters extends JFrame implements ActionListener {
    private JButton buttonAddCounter = new JButton("Добавить показания");
    private JButton buttonDeleteCounter = new JButton("Удалить показания");
    private JButton buttonUpdateCounter = new JButton("Редактировать показания");
    public static CountersTable modelCounters;
    private JTable jTabCounters;
    public static int[] selectedRows;
    public static int[] selectedColumns;
    public static int i;
    public static int selIndex;
    public static TableModel model;
    public static Object idCounters;
    public static String action;

    public WindowCounters() {

        super("Финансовый менеджер");
        this.setBounds(0, 100, 1650, 400);
        //  this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        DbCounters dbCounters = new DbCounters();
        dbCounters.counters.removeAll(dbCounters.counters);
        dbCounters.view(OpenWindow.userLogin);

        GridBagConstraints c = new GridBagConstraints();
        Container container = this.getContentPane();
        container.setLayout(new GridBagLayout());

        JFrame jfrm = new JFrame("JTableExample");
        modelCounters = new CountersTable(dbCounters.counters);
        //На основе модели, создадим новую JTable
        jTabCounters = new JTable(modelCounters);
        //Создаем панель прокрутки и включаем в ее состав нашу таблицу
        JScrollPane jscrlp = new JScrollPane(jTabCounters);
        //Устаналиваем размеры прокручиваемой области
        jTabCounters.setPreferredScrollableViewportSize(new Dimension(550, 200));
        //Добавляем в контейнер нашу панель прокрути и таблицу вместе с ней
        jfrm.getContentPane().add(jscrlp);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridwidth = 5;
        c.gridheight = 2;
        c.gridx = 0;
        c.gridy = 0;
        container.add(jscrlp, c);

        buttonAddCounter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                action = "add";
                Counter.go();
            }
        });
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(buttonAddCounter, c);

        buttonDeleteCounter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dbCounters.delete(idCounters, selectedRows[i - 1]);
                dbCounters.counters.remove(selectedRows[i - 1]);
                modelCounters.fireTableDataChanged();
            }
        });
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(buttonDeleteCounter, c);

        buttonUpdateCounter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                action = "update";
                Counter.go();
            }
        });
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(buttonUpdateCounter, c);

        ListSelectionModel selModel = jTabCounters.getSelectionModel();
        selModel.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                selectedRows = jTabCounters.getSelectedRows();
                selectedColumns = jTabCounters.getSelectedColumns();
                for (i = 0; i < selectedRows.length; i++) {
                    selIndex = selectedRows[i];
                    model = jTabCounters.getModel();
                    idCounters = model.getValueAt(selIndex, 0);
                }
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public static void main(String[] args) {
        WindowCounters app = new WindowCounters();
        app.setVisible(true);
    }

    public static void go() {
        WindowCounters app = new WindowCounters();
        app.setVisible(true);
    }
}
