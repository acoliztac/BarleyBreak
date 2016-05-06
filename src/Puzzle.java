import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by Никита on 05.05.2016.
 */
public class Puzzle extends FrameCreator {

    private int stepCounter = 0;
    private ArrayList<JButton> jButtonField = new ArrayList<JButton>();
    private String[][] puzzle;
    private String[][] result;

    public Puzzle(int width, int height){
        super("Восьмяшки");
        eHandler handler = new eHandler();

        puzzle = new String[width][height];
        result = new String[width][height];

        int buttonSide = 48;

        fillMatrix(puzzle, 0, width, height, true);
        fillMatrix(result, 1, width, height, false);

        for (int i = 0; i < width * height; i++){
            jButtonField.add(new JButton());
        }
        for (JButton jb : jButtonField){
            add(jb);
            jb.setPreferredSize(new Dimension(buttonSide, buttonSide));
            jb.addActionListener(handler);
        }

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
        System.out.println(mark);

        if (mark % 2 != 0) {
            JOptionPane.showMessageDialog(null, "Хрен соберёшь, дружище. Позже пофиксим. Сейчас перезапускай игру.",
                    "Уп-с, плохо сгенерировалось поле", 2);
        }
    }

    public class eHandler implements ActionListener {
        private boolean gameOver = false;

        @Override
        public void actionPerformed (ActionEvent e) {

            for (JButton jb : jButtonField){
                int a = puzzle.length;
                int b = 0;
                if (e.getSource() == jb){
                    int x = 0;
                    int y = 0;
                    for (int i = 0; i < a; i++){
                        String[] part = puzzle[i];
                        b = part.length;
                        for (int j = 0; j < b; j++){
                            if (puzzle[i][j].equals(jb.getText())){
                                x = i;
                                y = j;
                                stepCounter++;
                                break;
                            }
                        }
                    }
                    int[] xs = {x, x, x + 1, x - 1};
                    int[] ys = {y - 1, y + 1, y, y};

                    for (int i = 0; i < 4; i++){
                        try {
                            if (puzzle[xs[i]][ys[i]].equals(" ")){
                                String buf = puzzle[x][y];
                                puzzle[x][y] = puzzle[xs[i]][ys[i]];
                                puzzle[xs[i]][ys[i]] = buf;

                                int l = 0;
                                for (int j = 0; j < a; j++){
                                    for (int k = 0; k < b; k++){
                                        jButtonField.get(l).setText(String.valueOf(puzzle[j][k]));
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
                    for (int i = 0; i < puzzle.length; i++){
                        String[] line = puzzle[i];
                        fieldA = i;
                        for (int j = 0; j < line.length; j++){
                            fieldB = j;
                            if (!puzzle[i][j].equals(result[i][j]))
                                gameOver = false;
                        }
                    }
                    if (gameOver){
                        String count;
                        if (stepCounter % 10 == 1)
                            count = " ход";
                        else if (stepCounter % 10 > 1 && stepCounter % 10 < 5)
                            count = " хода";
                        else count = " ходов";
                        String greetings = "Поздравляю, вы справились с задачей на поле " + (fieldA + 1) + " x " +
                                + (fieldB + 1) + " за " + stepCounter + count;
                        JOptionPane.showMessageDialog(null, greetings, "Восьмяшки", 1);
                        stopThread = true;
                    }
                }
            }
        }
    }
}
