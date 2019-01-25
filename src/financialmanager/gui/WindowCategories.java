package financialmanager.gui;

import financialmanager.database.DbCategories;
import financialmanager.table.CategoriesTable;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WindowCategories extends JFrame implements ActionListener {
    private JButton buttonAddCategory = new JButton("Добавить категорию");
    private JButton buttonDeleteCategory = new JButton("Удалить категорию");
    private JButton buttonUpdateCategory = new JButton("Редактировать категорию");
    public static CategoriesTable modelCategories;
    private JTable jTabCategory;
    public static String action;
    public static int[] selectedRows;
    public static int[] selectedColumns;
    public static int i;
    public static int selIndex;
    public static TableModel model;
    public static Object value;

    public WindowCategories() {

        super("Финансовый менеджер");
        this.setBounds(100, 100, 650, 400);
        //  this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        DbCategories.view();

        GridBagConstraints c = new GridBagConstraints();
        Container container = this.getContentPane();
        container.setLayout(new GridBagLayout());

        JFrame jfrm = new JFrame("JTableExample");
        modelCategories = new CategoriesTable(DbCategories.categories);
        //На основе модели, создадим новую JTable
        jTabCategory = new JTable(modelCategories);
        //Создаем панель прокрутки и включаем в ее состав нашу таблицу
        JScrollPane jscrlp = new JScrollPane(jTabCategory);
        //Устаналиваем размеры прокручиваемой области
        jTabCategory.setPreferredScrollableViewportSize(new Dimension(550, 200));
        //Добавляем в контейнер нашу панель прокрути и таблицу вместе с ней
        jfrm.getContentPane().add(jscrlp);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridwidth = 5;
        c.gridheight = 2;
        c.gridx = 0;
        c.gridy = 0;
        container.add(jscrlp, c);

        buttonAddCategory.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                action="add";
                Category.go();
            }
        });
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(buttonAddCategory, c);

        buttonDeleteCategory.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DbCategories.delete();
            }
        });
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(buttonDeleteCategory, c);

        buttonUpdateCategory.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                action="update";
                Category.go();
            }
        });
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(buttonUpdateCategory, c);

        ListSelectionModel selModel = jTabCategory.getSelectionModel();
        selModel.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                selectedRows = jTabCategory.getSelectedRows();
                selectedColumns = jTabCategory.getSelectedColumns();
                for (i = 0; i < selectedRows.length; i++) {
                    selIndex = selectedRows[i];
                    model = jTabCategory.getModel();
                    value = model.getValueAt(selIndex, 0);
                    if (i != selectedRows.length - 1) {
                    }
                }
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public static void main(String[] args) {
        WindowCategories app = new WindowCategories();
        app.setVisible(true);
    }

    public static void go() {
        WindowCategories app = new WindowCategories();
        app.setVisible(true);
    }
}
