package financialmanager.gui;

import financialmanager.database.DbPlaces;
import financialmanager.table.PlacesTable;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WindowPlaces extends JFrame implements ActionListener {
    private JButton buttonAddPlace = new JButton("Добавить место");
    private JButton buttonDeletePlace = new JButton("Удалить место");
    private JButton buttonUpdatePlace = new JButton("Редактировать место");
    public static PlacesTable modelPlaces;
    private JTable jTabPlace;
    public static String result;
    public static int[] selectedRows;
    public static int[] selectedColumns;
    public static int i;
    public static int selIndex;
    public static TableModel model;
    public static Object value;

    public WindowPlaces() {

        super("Финансовый менеджер");
        this.setBounds(100, 100, 650, 400);
        //  this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        DbPlaces.view();

        GridBagConstraints c = new GridBagConstraints();
        Container container = this.getContentPane();
        container.setLayout(new GridBagLayout());

        JFrame jfrm = new JFrame("JTableExample");
        modelPlaces = new PlacesTable(DbPlaces.places);
        //На основе модели, создадим новую JTable
        jTabPlace = new JTable(modelPlaces);
        //Создаем панель прокрутки и включаем в ее состав нашу таблицу
        JScrollPane jscrlp = new JScrollPane(jTabPlace);
        //Устаналиваем размеры прокручиваемой области
        jTabPlace.setPreferredScrollableViewportSize(new Dimension(550, 200));
        //Добавляем в контейнер нашу панель прокрути и таблицу вместе с ней
        jfrm.getContentPane().add(jscrlp);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridwidth = 5;
        c.gridheight = 2;
        c.gridx = 0;
        c.gridy = 0;
        container.add(jscrlp, c);

        buttonAddPlace.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AddPlace.go();
            }
        });
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(buttonAddPlace, c);

        buttonDeletePlace.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DbPlaces.delete();
            }
        });
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(buttonDeletePlace, c);

        buttonUpdatePlace.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                UpdatePlace.go();
            }
        });
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(buttonUpdatePlace, c);

        final JLabel currentSelectionLabel = new JLabel("");
//        currentSelectionLabel.setAutoscrolls(true);
//        c.fill = GridBagConstraints.HORIZONTAL;
//        c.weightx = 0.5;
//        c.gridx = 0;
//        c.gridy = GridBagConstraints.RELATIVE;
//        container.add(currentSelectionLabel, c);

        ListSelectionModel selModel = jTabPlace.getSelectionModel();
        selModel.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                result = "";
                selectedRows = jTabPlace.getSelectedRows();
                selectedColumns = jTabPlace.getSelectedColumns();
                for (i = 0; i < selectedRows.length; i++) {
                    selIndex = selectedRows[i];
                    model = jTabPlace.getModel();
                    value = model.getValueAt(selIndex, 0);
                    // System.out.println(value);
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
        WindowPlaces app = new WindowPlaces();
        app.setVisible(true);
    }

    public static void go() {
        WindowPlaces app = new WindowPlaces();
        app.setVisible(true);
    }
}
