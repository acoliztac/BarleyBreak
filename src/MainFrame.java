import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Никита on 05.05.2016.
 */
public class MainFrame extends FrameCreator {
    private JLabel labelSize, labelHeight, labelWidth;
    private JButton startButton;
    private eHandler handler = new eHandler();
    private JTextField puzzleHeight, puzzleWidth;

    int width, height;

    public MainFrame(){
        super("Восьмяшки");

        startButton = new JButton("Приступить к решению");
        startButton.setPreferredSize(new Dimension(180, 30));

        labelSize = new JLabel("Размеры поля");
        labelSize.setPreferredSize(new Dimension(150, 30));
        labelHeight = new JLabel("Высота :");
        labelHeight.setPreferredSize(new Dimension(70, 30));
        labelWidth = new JLabel("Ширина :");
        labelWidth.setPreferredSize(new Dimension(70, 30));

        puzzleHeight = new JTextField(5);
        puzzleWidth = new JTextField(5);

        add(labelSize);
        add(labelHeight);
        add(puzzleHeight);
        add(labelWidth);
        add(puzzleWidth);
        add(startButton);
        startButton.addActionListener(handler);
    }

    public class eHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == startButton) {
                try {
                    height = Integer.parseInt(puzzleHeight.getText());
                    width = Integer.parseInt(puzzleWidth.getText());
                    if (width < 3 || width > 10 || height < 3 || height > 10)
                        throw new NumberFormatException();

                    stopThread = true;
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Беда. Говорят, что нужно вводить только целые числа! Да ещё и в" +
                            " диапазоне 3...10\nЯ приберу за тобой, а ты попробуй ещё раз!", "Ошибочка закралась", 2);
                    puzzleHeight.setText(null);
                    puzzleWidth.setText(null);
                }
            }
        }
    }
}
