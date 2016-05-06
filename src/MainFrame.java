import javax.swing.*;
import java.awt.*;

/**
 * Created by Никита on 05.05.2016.
 */
public class MainFrame extends FrameCreator {
    private JLabel l0, l1, l2;

    public MainFrame(){
        super("Восьмяшки");

        startButton = new JButton("Приступить к решению");
        startButton.setPreferredSize(new Dimension(180, 30));

        l0 = new JLabel("Размеры поля");
        l0.setPreferredSize(new Dimension(150, 30));
        l1 = new JLabel("Высота :");
        l1.setPreferredSize(new Dimension(70, 30));
        l2 = new JLabel("Ширина :");
        l2.setPreferredSize(new Dimension(70, 30));

        puzzleHeight = new JTextField(5);
        puzzleWidth = new JTextField(5);

        add(l0);
        add(l1);
        add(puzzleHeight);
        add(l2);
        add(puzzleWidth);
        add(startButton);
        startButton.addActionListener(handler);
    }
}
