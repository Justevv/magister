package financialmanager.gui;

import financialmanager.database.DbUsers;
import financialmanager.database.DeleteUser;
import financialmanager.table.UsersTable;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WindowUsers extends JFrame implements ActionListener {
    private JButton buttonAddUser = new JButton("Добавить пользователя");
    private JButton buttonDeleteUser = new JButton("Удалить пользователя");
    private JButton buttonUpdateUser = new JButton("Редактировать пользователя");
    public static UsersTable modelUsers;
    private JTable jTabPeople;
    private JTextField sSurname = new JTextField("", 5);
    public static String result;
    public static int[] selectedRows;
    public static int[] selectedColumns;
    public static int i;
    public static int selIndex;
    static TableModel model;
    public static Object value;

    public WindowUsers() {

        super("Финансовый менеджер");
        this.setBounds(100, 100, 650, 400);
        //  this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        DbUsers.main();

        GridBagConstraints c = new GridBagConstraints();
        Container container = this.getContentPane();
        container.setLayout(new GridBagLayout());

        JFrame jfrm = new JFrame("JTableExample");
        modelUsers = new UsersTable(DbUsers.users);
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

        buttonAddUser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                User.go();
            }
        });
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(buttonAddUser, c);

        buttonDeleteUser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DeleteUser.main();
            }
        });
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(buttonDeleteUser, c);

        buttonUpdateUser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                UpdateUser.go();
            }
        });
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(buttonUpdateUser, c);

        final JLabel currentSelectionLabel = new JLabel("");
        currentSelectionLabel.setAutoscrolls(true);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(currentSelectionLabel, c);

        ListSelectionModel selModel = jTabPeople.getSelectionModel();
        selModel.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                result = "";
                selectedRows = jTabPeople.getSelectedRows();
                selectedColumns = jTabPeople.getSelectedColumns();
                for ( i = 0; i < selectedRows.length; i++) {
                    selIndex = selectedRows[i];
                    model = jTabPeople.getModel();
                    value = model.getValueAt(selIndex, 0);
                    System.out.println(value);
                    result = result + value;
                  //  System.out.println(selectedColumns[i]);
                    if (i != selectedRows.length - 1) {
                        result += ", ";
                    }
                }
                currentSelectionLabel.setText(result);
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public static void main(String[] args) {
        WindowUsers app = new WindowUsers();
        app.setVisible(true);
    }

    public static void go() {
        WindowUsers app = new WindowUsers();
        app.setVisible(true);
    }

    ;
}
