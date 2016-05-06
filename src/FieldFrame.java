import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Никита on 05.05.2016.
 */
public class FieldFrame extends FrameCreator {
    private JLabel motivationMSG;

    public FieldFrame(String frameName, int width, int height){
        super(frameName);

        puzzle = new String[width][height];
        result = new String[width][height];

        int buttonSide = 48;

        fillMatrix(puzzle, 0, width, height, true);
        fillMatrix(result, 1, width, height, false);

        motivationMSG = new JLabel("Запасись терпением и едой (;");
        for (int i = 0; i < width * height; i++){
            jButtonField.add(new JButton());
        }
        for (JButton jb : jButtonField){
            add(jb);
            jb.setPreferredSize(new Dimension(buttonSide, buttonSide));
            jb.addActionListener(handler);
        }
        add(motivationMSG);

        int k = 0;
        for (int i = 0; i < width; i++){
            for (int j = 0; j < height; j++){
                jButtonField.get(k).setText(String.valueOf(puzzle[i][j]));
                k++;
            }
        }
        solutionExists();
    }

    private static void fillMatrix(String[][] matrix, Integer k, Integer a, Integer b, boolean b1) {
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
                if (k >= a * b)
                    k = 0;
            }
        }
    }

    private void solutionExists() {
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (JButton ds : jButtonField){
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
//        findingEmpty : for (int i = 0; i < puzzle.length; i++){
//            String[] line = puzzle[i];
//            for (int j = 0; j < line.length; j++){
//                if (puzzle[i][j].equals(" ")) {
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
