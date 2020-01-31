package financialmanager.gui;

import financialmanager.database.DbCategories;
import financialmanager.table.CategoriesTable;
import financialmanager.text.Actions;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class WindowCategories extends JFrame implements ActionListener {
    static CategoriesTable modelCategories;
    private JTable jTabCategory;
    public static Actions action;
    private int[] selectedRows;
    //    private int[] selectedColumns;
    private int i;
    public static int selIndex;
    public static TableModel model;
    public static Object currentId;

    private WindowCategories() {

        super("Финансовый менеджер");
        this.setBounds(100, 100, 650, 400);
        //  this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        DbCategories dbCategories = new DbCategories();
        GridBagConstraints c = new GridBagConstraints();
        Container container = this.getContentPane();
        container.setLayout(new GridBagLayout());
        JFrame jfrm = new JFrame("JTableExample");
        modelCategories = new CategoriesTable(dbCategories.select());
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

        JButton buttonAddCategory = new JButton("Добавить категорию");
        buttonAddCategory.addActionListener(e -> {
            action = Actions.INSERT;
            Category.go();
        });
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(buttonAddCategory, c);

        JButton buttonDeleteCategory = new JButton("Удалить категорию");
        buttonDeleteCategory.addActionListener(e -> {
            dbCategories.delete(currentId.toString());
            modelCategories.setCategories(dbCategories.select());
            modelCategories.fireTableDataChanged();
        });
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(buttonDeleteCategory, c);

        JButton buttonUpdateCategory = new JButton("Редактировать категорию");
        buttonUpdateCategory.addActionListener(e -> {
            action = Actions.UPDATE;
            Category.go();
        });
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(buttonUpdateCategory, c);

        ListSelectionModel selModel = jTabCategory.getSelectionModel();
        selModel.addListSelectionListener(e -> {
            selectedRows = jTabCategory.getSelectedRows();
//                selectedColumns = jTabCategory.getSelectedColumns();
            for (i = 0; i < selectedRows.length; i++) {
                selIndex = selectedRows[i];
                model = jTabCategory.getModel();
                currentId = model.getValueAt(selIndex, 0);
//                    if (i != selectedRows.length - 1) {
//                    }
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public static void go() {
        WindowCategories app = new WindowCategories();
        app.setVisible(true);
    }
}
