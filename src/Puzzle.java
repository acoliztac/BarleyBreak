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
    private eHandler handler = new eHandler();

    private int buttonSize = 70;

    public Puzzle(int width, int height){
        super("Восьмяшки");
        final JPanel content = new JPanel(new GridLayout(height,2,5,5));
        setSize(width * buttonSize, height * buttonSize);



        puzzle = fillMatrix(0, width, height, true);
        result = fillMatrix(1, width, height, false);

        for (int i = 0; i < width * height; i++){
            JButton jb = new JButton();
            jb.setPreferredSize(new Dimension(48, 48));
            jb.addActionListener(handler);
            jButtonField.add(new JButton());
        }

        int k = 0;
        for (int i = 0; i < width; i++){
            for (int j = 0; j < height; j++){
                jButtonField.get(k).setText(String.valueOf(puzzle[i][j]));
                k++;
            }
        }

        for (JButton button : jButtonField){
            content.add(button);
        }

        getContentPane().add(content);

        solutionExists();
    }

    private static String[][] fillMatrix(Integer firstNumber, Integer a, Integer b, boolean b1) {
        String[][] multyArray = new String[a][b];
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
                int buf = arrayList.get(firstNumber++);
                if (buf == 0)
                    multyArray[i][j] = " ";
                else multyArray[i][j] = String.valueOf(buf);
                if (firstNumber >= a * b)
                    firstNumber = 0;
            }
        }

        return multyArray;
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
            int puzzleHeight = puzzle.length;
            int puzzleWidth = puzzle[0].length;


            for (JButton jb : jButtonField){
                if (e.getSource() == jb){

                    int[] coordinates = getCoordinates(puzzleHeight, puzzleWidth, jb);

                    shuffle(puzzleHeight, puzzleWidth, coordinates);

                    gameOver = true;
                    int fieldA = 0;
                    int fieldB = 0;
                    for (int i = 0; i < puzzleHeight; i++){
                        fieldA = i;
                        for (int j = 0; j < puzzleWidth; j++){
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

        private void shuffle(int puzzleHeight, int puzzleWidth, int[] coordinates) {
            int x = coordinates[0];
            int y = coordinates[1];
            int[] xs = {  x,      x,   x + 1,  x - 1};
            int[] ys = {y - 1,  y + 1,   y,      y};

            for (int i = 0; i < 4; i++){
                try {
                    if (puzzle[xs[i]][ys[i]].equals(" ")){
                        String buf = puzzle[x][y];
                        puzzle[x][y] = puzzle[xs[i]][ys[i]];
                        puzzle[xs[i]][ys[i]] = buf;

                        int l = 0;
                        for (int j = 0; j < puzzleHeight; j++){
                            for (int k = 0; k < puzzleWidth; k++){
                                jButtonField.get(l).setText(String.valueOf(puzzle[j][k]));
                                l++;
                            }
                        }
                        break;
                    }
                } catch (Exception e1) {
                }
            }
        }

        private int[] getCoordinates(int puzzleHeight, int puzzleWidth, JButton jb) {
            int[] array = new int[2];
            for (int i = 0; i < puzzleHeight; i++){
                for (int j = 0; j < puzzleWidth; j++){
                    if (puzzle[i][j].equals(jb.getText())){
                        array[0] = i;
                        array[1] = j;
                        stepCounter++;
                        break;
                    }
                }
            }
            return array;
        }
    }
}
