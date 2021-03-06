package financialmanager.guitest;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GridLayoutDemo {
    public static void main(String[] args) {
        JFrame jf = new JFrame();
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setSize(400, 300);
        jf.setVisible(true);

        // создаем  панель.
        JPanel p = new JPanel();
        jf.add(p);

        // к панели добавляем менеджер GridLayout и устанавливаем размеры таблицы 3*3.
        p.setLayout(new GridLayout(3,3));

        // к панели добавляем кнопку и устанавливаем для нее менеджер в верхнее расположение.

        p.add(new JButton("getBalance 2"));
        p.add(new JButton("getBalance 2"));
        p.add(new JButton("getBalance 3"));
        p.add(new JButton("getBalance 4"));
        p.add(new JButton("getBalance 5"));
        p.add(new JButton("getBalance 6"));
        p.add(new JButton("Okay"));
    }
}
