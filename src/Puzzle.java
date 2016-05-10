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
    private String[][] alternateResult;
    private Handler handler = new Handler();
    public static final String EMPTY_STRING = " ";
    public boolean alternateSolution = false;
    private int zeroLine = 0;

    private int buttonSize = 66;

    public Puzzle(int width, int height) {
        super("Восьмяшки");
        final JPanel content = new JPanel(new GridLayout(height,2,5,5));
        setSize(width * buttonSize, height * buttonSize);

        puzzle = fillMatrix(0, width, height, true, false);
        result = fillMatrix(1, width, height, false, false);
        alternateResult = fillMatrix(1, width, height, false, true);

        for (int i = 0; i < height; i++){
            for (int j = 0; j < width; j++){
                JButton button = new JButton(String.valueOf(puzzle[i][j]));
                button.setPreferredSize(new Dimension(48, 48));
                button.addActionListener(handler);
                jButtonField.add(button);
                content.add(button);
            }
        }

        getContentPane().add(content);
        setVisible(true);

        for (int i = 0; i < height; i++){
            for (int j = 0; j < width; j++){
                if (puzzle[i][j].equals(EMPTY_STRING)) {
                    zeroLine = i;
                }
            }
        }

        alternateSolution = solutionExists(zeroLine, width);
    }

    private static String[][] fillMatrix(Integer countNumber, Integer width, Integer height, boolean mess, boolean alternateSolution) {
        String[][] multiArray = new String[height][width];
        ArrayList<Integer> bufferArray = new ArrayList<Integer>();
        if (!mess) {
            for (int i = 0; i < height * width; i++){
                bufferArray.add(i);
            }

        } else {
            while (bufferArray.size() < height * width){
                int i = (int) (Math.random()*(height * width));
                if (bufferArray.contains(i))
                    continue;
                bufferArray.add(i);
            }
        }

        if (!alternateSolution) {
            for (int i = 0; i < height; i++) {
                countNumber = generateSolutionLine(countNumber, height, width, multiArray, bufferArray, i);
            }
        } else{
            for (int i = 0; i < height; i++){
                if (i % 2 == 1) {
                    countNumber = generateAlternateSolutionLine(countNumber, height, width, multiArray, bufferArray, i);
                } else {
                    countNumber = generateSolutionLine(countNumber, height, width, multiArray, bufferArray, i);
                }
            }
        }

        return multiArray;
    }

    private static Integer generateAlternateSolutionLine(Integer countNumber, Integer width, Integer height, String[][] multiArray, ArrayList<Integer> bufferArray, int i) {
        for (int j = height - 1; j >= 0; j--) {
            int buf = bufferArray.get(countNumber++);
            if (buf == 0) {
                multiArray[i][j] = EMPTY_STRING;
            } else {
                multiArray[i][j] = String.valueOf(buf);
            }
            if (countNumber >= width * height)
                countNumber = 0;
        }
        return countNumber;
    }

    private static Integer generateSolutionLine(Integer countNumber, Integer width, Integer height, String[][] multiArray, ArrayList<Integer> bufferArray, int i) {
        for (int j = 0; j < height; j++) {
            int buf = bufferArray.get(countNumber++);
            if (buf == 0) {
                multiArray[i][j] = EMPTY_STRING;
            } else {
                multiArray[i][j] = String.valueOf(buf);
            }
            if (countNumber >= width * height)
                countNumber = 0;
        }
        return countNumber;
    }

    private boolean solutionExists(int zeroLine, int puzzleSize) {
        boolean alternate;
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (JButton ds : jButtonField){
            if (ds.getText().equals(EMPTY_STRING))
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

        if (puzzleSize == 4){
            mark += zeroLine + 1;
        }
        if (mark % 2 != 0) {
            alternate = true;
            JOptionPane.showMessageDialog(null, "Альтернативное решение. Нечётные строки слева-направо, чётные - " +
                            "справа-налево", "Альтернативная задача", 2);
        } else{
            alternate = false;
            JOptionPane.showMessageDialog(null, "Классическое решение. Числа по возрастанию слева-направо и сверху-" +
                    "вниз", "Классическая задача", 2);
        }
        return alternate;
    }

    public class Handler implements ActionListener {
        private boolean gameOver = false;

        @Override
        public void actionPerformed (ActionEvent e) {
            int puzzleHeight = puzzle.length;
            int puzzleWidth = puzzle[0].length;
            String[][] solvedPuzzle;

            if (alternateSolution){
                solvedPuzzle = alternateResult;
            } else {
                solvedPuzzle = result;
            }


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
                            if (!puzzle[i][j].equals(solvedPuzzle[i][j]))
                                gameOver = false;
                        }
                    }
                    if (gameOver){
                        String count;
                        if (stepCounter >= 111 && stepCounter <= 120){
                            count = " ходов";
                        } else if (stepCounter % 10 == 1) {
                            count = " ход";
                        } else if (stepCounter % 10 > 1 && stepCounter % 10 < 5) {
                            count = " хода";
                        } else {
                            count = " ходов";
                        }
                        String greetings = "Поздравляю, вы справились с задачей на поле " + (fieldA + 1) + " x " +
                                + (fieldB + 1) + " за " + stepCounter + count;
                        JOptionPane.showMessageDialog(null, greetings, "Восьмяшки", 1);
                        setInterrupted(true);
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
                    if (puzzle[xs[i]][ys[i]].equals(EMPTY_STRING)){
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
