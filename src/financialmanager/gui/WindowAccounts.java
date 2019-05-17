package financialmanager.gui;

import financialmanager.database.DbAccounts;
import financialmanager.table.AccountsTable;
import financialmanager.text.Actions;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WindowAccounts extends JFrame implements ActionListener {
    private JButton buttonAddAccount = new JButton("Добавить счет");
    private JButton buttonDeleteAccount = new JButton("Удалить счет");
    private JButton buttonUpdateAccount = new JButton("Редактировать счет");
    static AccountsTable modelAccounts;
    private JTable jTabAccount;
    public static Actions action;
    private static int[] selectedRows;
    private static int[] selectedColumns;
    private static int i;
    static int selIndex;
    static TableModel model;
    static Object currentId;

    public WindowAccounts() {

        super("Финансовый менеджер");
        this.setBounds(100, 100, 650, 400);
        //  this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        DbAccounts dbAccounts = new DbAccounts();
        dbAccounts.select();

        GridBagConstraints c = new GridBagConstraints();
        Container container = this.getContentPane();
        container.setLayout(new GridBagLayout());

        JFrame jfrm = new JFrame("JTableExample");
        modelAccounts = new AccountsTable(dbAccounts.select());
        //На основе модели, создадим новую JTable
        jTabAccount = new JTable(modelAccounts);
        //Создаем панель прокрутки и включаем в ее состав нашу таблицу
        JScrollPane jscrlp = new JScrollPane(jTabAccount);
        //Устаналиваем размеры прокручиваемой области
        jTabAccount.setPreferredScrollableViewportSize(new Dimension(550, 200));
        //Добавляем в контейнер нашу панель прокрути и таблицу вместе с ней
        jfrm.getContentPane().add(jscrlp);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridwidth = 5;
        c.gridheight = 2;
        c.gridx = 0;
        c.gridy = 0;
        container.add(jscrlp, c);

        buttonAddAccount.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                action = Actions.INSERT;
                Account.go();
            }
        });
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(buttonAddAccount, c);

        buttonDeleteAccount.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dbAccounts.delete(currentId.toString());
                dbAccounts.select();
//                dbAccounts.accounts.remove(WindowAccounts.selectedRows[WindowAccounts.i - 1]);
                modelAccounts.fireTableDataChanged();
            }
        });
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(buttonDeleteAccount, c);

        buttonUpdateAccount.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                action = Actions.UPDATE;
                Account.go();
            }
        });
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(buttonUpdateAccount, c);

        ListSelectionModel selModel = jTabAccount.getSelectionModel();
        selModel.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                selectedRows = jTabAccount.getSelectedRows();
                selectedColumns = jTabAccount.getSelectedColumns();
                for (i = 0; i < selectedRows.length; i++) {
                    selIndex = selectedRows[i];
                    model = jTabAccount.getModel();
                    currentId = model.getValueAt(selIndex, 0);
                    if (i != selectedRows.length - 1) {
                    }
                }
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public static void go() {
        WindowAccounts app = new WindowAccounts();
        app.setVisible(true);
    }
}
