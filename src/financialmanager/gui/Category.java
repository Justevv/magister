package financialmanager.gui;

import financialmanager.database.DbCategories;
import financialmanager.text.Actions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static financialmanager.gui.WindowCategories.currentId;
import static financialmanager.gui.WindowCategories.modelCategories;

public class Category extends JFrame {
    private JTextField textFieldName = new JTextField("Свет", 5);
    private JTextField textFieldParentId = new JTextField("1", 5);

    private Category() {

        super("Финансовый менеджер");
        this.setBounds(100, 100, 350, 200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        if (WindowCategories.action == Actions.UPDATE) {
            textFieldName = new JTextField(String.valueOf(WindowCategories.model.getValueAt(WindowCategories.selIndex, 1)), 5);
            textFieldParentId = new JTextField(String.valueOf(WindowCategories.model.getValueAt(WindowCategories.selIndex, 2)), 5);
        } else {
//            textFieldName = new JTextField("", 5);
//            textFieldParentId = new JTextField("", 5);
        }

        JButton buttonCancel = new JButton("Cancel");
        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        //call onCancel() on ESCAPE
        JPanel contentPane = new JPanel();
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        GridBagConstraints c = new GridBagConstraints();
        Container container = this.getContentPane();
        container.setLayout(new GridBagLayout());

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        JLabel labelName = new JLabel("Имя:");
        container.add(labelName, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        JLabel labelParentId = new JLabel("Родительская категория:");
        container.add(labelParentId, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(textFieldName, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(textFieldParentId, c);

        JButton buttonOK = new JButton("OK");
        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(buttonOK, c);


        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = GridBagConstraints.RELATIVE;
        container.add(buttonCancel, c);

    }

    public static void go() {
        Category app = new Category();
        // app.pack();
        app.setVisible(true);
    }

    private void onOK() {
        DbCategories dbCategories = new DbCategories();
        String name = textFieldName.getText();
        String parentId = textFieldParentId.getText();
        if (WindowCategories.action == Actions.UPDATE) {
            dbCategories.update(name, parentId, currentId.toString());

        }
        if (WindowCategories.action == Actions.INSERT) {
            dbCategories.insert(name, parentId);
        }
        modelCategories.setCategories(dbCategories.select());
        modelCategories.fireTableDataChanged();
        setVisible(false);
    }

    private void onCancel() {
        // insert your code here if necessary
        // dispose();
        setVisible(false);
        //System.exit(0);
    }
}


