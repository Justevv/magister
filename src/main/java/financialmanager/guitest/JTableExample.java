//package financialmanager.gui;
//
//import financialmanager.database.DbExpenses;
//import financialmanager.table.ExpensesTable;
//
//import java.awt.Dimension;
//import java.awt.FlowLayout;
//import javax.swing.JFrame;
//import javax.swing.JScrollPane;
//import javax.swing.JTable;
//import javax.swing.SwingUtilities;
//
//public class JTableExample {
//
//    //Объект таблицы
//    JTable jTabPeople;
//    ExpensesTable modelExpenses;
//    JTableExample() {
//        DbExpenses.select();
//        //Создаем новый контейнер JFrame
//        JFrame jfrm = new JFrame("JTableExample");
//        //Устанавливаем диспетчер компоновки
//        jfrm.getContentPane().setLayout(new FlowLayout());
//        //Устанавливаем размер окна
//        jfrm.setSize(500, 370);
//        //Устанавливаем завершение программы при закрытии окна
//        jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        //Создадим модель таблицы
//        modelExpenses = new ExpensesTable(DbExpenses.expenses);
////На основе модели, создадим новую JTable
//        jTabPeople = new JTable(modelExpenses);
//        //Создаем новую таблицу на основе двумерного массива данных и заголовков
//     //   jTabPeople = new JTable(new ExpensesTable(expenses));
//        //Создаем панель прокрутки и включаем в ее состав нашу таблицу
//        JScrollPane jscrlp = new JScrollPane(jTabPeople);
//        //Устаналиваем размеры прокручиваемой области
//       jTabPeople.setPreferredScrollableViewportSize(new Dimension(350, 100));
//        //Добавляем в контейнер нашу панель прокрути и таблицу вместе с ней
//        jfrm.getContentPane().insert(jscrlp);
//        //Отображаем контейнер
//        jfrm.setVisible(true);
//    }
//
//    //Функция select, запускающаяся при старте приложения
//    public static void select(String[] args) {
//        //Создаем фрейм в потоке обработки событий
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                new JTableExample();
//            }
//        });
//    }
//}