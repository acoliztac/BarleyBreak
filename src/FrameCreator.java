import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public abstract class FrameCreator extends JFrame implements Runnable{
    int width, height;
    JButton startButton;
    JTextField puzzleHeight, puzzleWidth;
    eHandler handler = new eHandler();

    ArrayList<JButton> jButtonField = new ArrayList<JButton>();
    String[][] puzzle;
    String[][] result;

    int stepCounter = 0;

    boolean gameOver = false;
    boolean stopThread = false;

    public FrameCreator(String s) {
        super(s);
        setLayout(new FlowLayout());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
    }

    @Override
    public void run() {
        try {
            while(!stopThread){
                Thread.sleep(100);
            }
            setVisible(false);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public class eHandler implements ActionListener{

        @Override
        public void actionPerformed (ActionEvent e) {
            if (e.getSource() == startButton){
                int a, b;
                try {
                    a = Integer.parseInt(puzzleHeight.getText());
                    b = Integer.parseInt(puzzleWidth.getText());
                    if (b < 3 || b > 10 || a < 3 || a > 10)
                        throw new NumberFormatException();
                    height = a;
                    width = b;

                    stopThread = true;
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Беда. Говорят, что нужно вводить только целые числа! Да ещё и в" +
                            " диапазоне 3...10\nЯ приберу за тобой, а ты попробуй ещё раз!", "Ошибочка закралась", 2);
                    puzzleHeight.setText(null);
                    puzzleWidth.setText(null);
                }
            }

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