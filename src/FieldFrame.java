import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Никита on 05.05.2016.
 */
public class FieldFrame extends FrameCreator {
    private JLabel l3;

    public FieldFrame(String s, int a, int b){
        super(s);

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
}
