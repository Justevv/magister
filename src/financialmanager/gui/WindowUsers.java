package financialmanager.gui;

import financialmanager.database.DbUsers;
import financialmanager.table.UsersTable;
import financialmanager.text.Actions;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WindowUsers extends JFrame implements ActionListener {
    static UsersTable modelUsers;
    private JTable jTabPeople;
    private int[] selectedRows;
//    private int[] selectedColumns;
    private int i;
    static int selIndex;
    static TableModel model;
    static Object currentId;
    static Actions action;
    static Object currentEmail;

    private WindowUsers() {

        super("Финансовый менеджер");
        this.setBounds(100, 100, 650, 400);
        //  this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GridBagConstraints c = new GridBagConstraints();
        Container container = this.getContentPane();
        container.setLayout(new GridBagLayout());

        DbUsers dbUsers = new DbUsers();
        JFrame jfrm = new JFrame("JTableExample");
        modelUsers = new UsersTable(dbUsers.select());
        //На основе модели, создадим новую JTable
        jTabPeople = new JTable(modelUsers);
        //Создаем панель прокрутки и включаем в ее состав нашу таблицу
        JScrollPane jscrlp = new JScrollPane(jTabPeople);
        //Устаналиваем размеры прокручиваемой области
        jTabPeople.setPreferredScrollableViewportSize(new Dimension(550, 200));
        //Добавляем в контейнер нашу панель прокрути и таблицу вместе с ней
        jfrm.getContentPane().add(jscrlp);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridwidth = 5;
        c.gridheight = 2;
        c.gridx = 0;
        c.gridy = 0;
        container.add(jscrlp, c);

        JButton buttonAddUser = new JButton("Добавить пользователя");
        buttonAddUser.addActionListener(e -> {
            action = Actions.INSERT;
            User.go();
        });
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(buttonAddUser, c);

        JButton buttonDeleteUser = new JButton("Удалить пользователя");
        buttonDeleteUser.addActionListener(e -> {
            dbUsers.delete(currentId.toString());
            modelUsers.setUsers(dbUsers.select());
            modelUsers.fireTableDataChanged();
        });
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(buttonDeleteUser, c);

        JButton buttonUpdateUser = new JButton("Редактировать пользователя");
        buttonUpdateUser.addActionListener(e -> {
            action = Actions.UPDATE;
            User.go();
        });
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(buttonUpdateUser, c);

        ListSelectionModel selModel = jTabPeople.getSelectionModel();
        selModel.addListSelectionListener(e -> {
            selectedRows = jTabPeople.getSelectedRows();
//                selectedColumns = jTabPeople.getSelectedColumns();
            for (i = 0; i < selectedRows.length; i++) {
                selIndex = selectedRows[i];
                model = jTabPeople.getModel();
                currentId = model.getValueAt(selIndex, 0);
                currentEmail = model.getValueAt(selIndex, 6);
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public static void go() {
        WindowUsers app = new WindowUsers();
        app.setVisible(true);
    }
}
