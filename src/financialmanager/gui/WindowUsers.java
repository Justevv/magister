package financialmanager.gui;

import financialmanager.database.DbExpenses;
import financialmanager.database.DbUsers;
import financialmanager.table.UsersTable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WindowUsers extends JFrame implements ActionListener {
    private JButton buttonAddUser = new JButton("Добавить пользователя");
    public static UsersTable modelUsers;
    private JTable jTabPeople;
    private JTextField sSurname = new JTextField("", 5);

    public WindowUsers() {

        super("Финансовый менеджер");
        this.setBounds(100, 100, 750, 500);
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
        jTabPeople.setPreferredScrollableViewportSize(new Dimension(350, 200));
        //Добавляем в контейнер нашу панель прокрути и таблицу вместе с ней
        jfrm.getContentPane().add(jscrlp);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridwidth=1 ;
        c.gridheight=GridBagConstraints.RELATIVE+1 ;
        c.gridx = 0;
        c.gridy = 0;
        container.add(jscrlp, c);

        buttonAddUser.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                AddUser.go();
            }
        });
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(buttonAddUser, c);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public static void main(String[] args) {
        WindowUsers app = new WindowUsers();
        app.setVisible(true);
    }
    public static void go(){
        WindowUsers app = new WindowUsers();
        app.setVisible(true);
    };
}
