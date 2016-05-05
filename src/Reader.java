import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Reader extends JFrame implements Runnable{
    int width, height;
    JButton b1;
    JLabel l0, l1, l2, l3;
    JTextField t1, t2;
    eHandler handler = new eHandler();

    ArrayList<JButton> buttons = new ArrayList<JButton>();
    String[][] matrix;
    String[][] result;

    int counter = 0;

    private boolean gameOver = false;

    private boolean stopThread = false;

    public Reader (String s){
        super(s);
        setLayout(new FlowLayout());

        b1 = new JButton("Приступить к решению");
        b1.setPreferredSize(new Dimension(180, 30));

        l0 = new JLabel("Размеры поля");
        l0.setPreferredSize(new Dimension(150, 30));
        l1 = new JLabel("Высота :");
        l1.setPreferredSize(new Dimension(70, 30));
        l2 = new JLabel("Ширина :");
        l2.setPreferredSize(new Dimension(70, 30));

        t1 = new JTextField(5);
        t2 = new JTextField(5);

        add(l0);
        add(l1);
        add(t1);
        add(l2);
        add(t2);
        add(b1);
        b1.addActionListener(handler);
    }

    public Reader (String s, int a, int b){
        super(s);
        setLayout(new FlowLayout());

        matrix = new String[a][b];
        result = new String[a][b];

        int d = 48;

        fillingMatrix(matrix, 0, a, b, true);
        fillingMatrix(result, 1, a, b, false);

        l3 = new JLabel("Запасись терпением и едой (;");
        for (int i = 0; i < a * b; i++){
            buttons.add(new JButton());
        }
        for (JButton jb : buttons){
            add(jb);
            jb.setPreferredSize(new Dimension(d, d));
            jb.addActionListener(handler);
        }
        add(l3);

        int k = 0;
        for (int i = 0; i < a; i++){
            for (int j = 0; j < b; j++){
                buttons.get(k).setText(String.valueOf(matrix[i][j]));
                k++;
            }
        }

        solutionable();

    }
//Проверка доступности решения (работает с полем 3х3)
    private void solutionable() {
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (JButton ds : buttons){
            if (ds.getText().equals(" "))
                continue;
            list.add(Integer.valueOf(ds.getText()));
        }

        int mark = 0;
        for (int i = 0; i < list.size(); i++){
            for (int j = i + 1; j < list.size(); j++){
                if (list.get(i) < list.get(j))
                    mark++;
            }
        }
//        findingEmpty : for (int i = 0; i < matrix.length; i++){
//            String[] line = matrix[i];
//            for (int j = 0; j < line.length; j++){
//                if (matrix[i][j].equals(" ")) {
//                    mark += i + 1;
//                    break findingEmpty;
//                }
//            }
//        }
        System.out.println(mark);

        if (mark % 2 != 0) {
            JOptionPane.showMessageDialog(null, "Хрен соберёшь, дружище. Позже пофиксим. Сейчас перезапускай игру.",
                    "Уп-с, плохо сгенерировалось поле", 2);
        }
    }

    private static void fillingMatrix(String[][] matrix, Integer k, Integer a, Integer b, boolean b1) {
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        if (!b1) {
            for (int i = 0; i < a * b; i++){
                arrayList.add(i);
            }

        } else {
            while (arrayList.size() < a * b){
                int i = (int) (Math.random()*(a * b));
                if (arrayList.contains(i))
                    continue;
                arrayList.add(i);
            }
        }

        for (int i = 0; i < a; i++){
            for (int j = 0; j < b; j++){
                int buf = arrayList.get(k++);
                if (buf == 0)
                    matrix[i][j] = " ";
                else matrix[i][j] = String.valueOf(buf);
//                matrix[i][j] = String.valueOf(arrayList.get(k++));
                if (k >= a * b)
                    k = 0;
            }
        }
    }

    @Override
    public void run() {
        try {
            while(!stopThread){
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public class eHandler implements ActionListener{

        @Override
        public void actionPerformed (ActionEvent e) {
            if (e.getSource() == b1){
                int a = 0;
                int b = 0;
                try {
                    a = Integer.parseInt(t1.getText());
                    b = Integer.parseInt(t2.getText());
                    if (b < 3 || b > 10 || a < 3 || a > 10)
                        throw new NumberFormatException();
                    height = a;
                    width = b;
                    stopThread = true;
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Беда. Говорят, что нужно вводить только целые числа! Да ещё и в диапазоне 3...10\n" +
                            "Я приберу за тобой, а ты попробуй ещё раз!", "Ошибочка закралась", 2);
                    t1.setText(null);
                    t2.setText(null);
                }

            }

            for (JButton jb : buttons){
                int a = matrix.length;
                int b = 0;
                if (e.getSource() == jb){
                    int x = 0;
                    int y = 0;
                    for (int i = 0; i < a; i++){
                        String[] part = matrix[i];
                        b = part.length;
                        for (int j = 0; j < b; j++){
                            if (matrix[i][j].equals(jb.getText())){
                                x = i;
                                y = j;
                                counter++;
                                break;
                            }
                        }
                    }
                    int[] xs = {x, x, x + 1, x - 1};
                    int[] ys = {y - 1, y + 1, y, y};

                    for (int i = 0; i < 4; i++){
                        try {
                            if (matrix[xs[i]][ys[i]].equals(" ")){
                                String buf = matrix[x][y];
                                matrix[x][y] = matrix[xs[i]][ys[i]];
                                matrix[xs[i]][ys[i]] = buf;

                                int l = 0;
                                for (int j = 0; j < a; j++){
                                    for (int k = 0; k < b; k++){
                                        buttons.get(l).setText(String.valueOf(matrix[j][k]));
                                        l++;
                                    }
                                }

                                break;
                            }
                        } catch (Exception e1) {
                        }
                    }
                    gameOver = true;
                    int fieldA = 0;
                    int fieldB = 0;
                    for (int i = 0; i < matrix.length; i++){
                        String[] line = matrix[i];
                        fieldA = i;
                        for (int j = 0; j < line.length; j++){
                            fieldB = j;
                            if (!matrix[i][j].equals(result[i][j]))
                                gameOver = false;
                        }
                    }
                    if (gameOver){
                        String count;
                        if (counter % 10 == 1)
                            count = " ход";
                        else if (counter % 10 > 1 && counter % 10 < 5)
                            count = " хода";
                        else count = " ходов";
                        String greetings = "Поздравляю, вы справились с задачей на поле " + (fieldA + 1) + " x " +
                                + (fieldB + 1) + " за " + counter + count;
                        JOptionPane.showMessageDialog(null, greetings, "Восьмяшки", 1);
                    }
                }
            }
        }


    }
}