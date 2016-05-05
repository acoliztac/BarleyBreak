import javax.swing.*;
import java.awt.*;

/**
 * Created by Никита on 05.05.2016.
 */
public class MainFrame extends FrameCreator {
    private JLabel l0, l1, l2;

    public MainFrame(String s){
        super(s);

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
}
