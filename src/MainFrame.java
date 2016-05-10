import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Никита on 05.05.2016.
 */
public class MainFrame extends FrameCreator {
    private JLabel labelMessageSize, labelSideSize;
    private JButton startButton;
    private Handler handler = new Handler();
    private JTextField puzzleSideSize;

    public int width, height;

    public MainFrame(){
        super("Восьмяшки");
        setSize(215, 150);
        setLayout(new FlowLayout());

        startButton = new JButton("Приступить к решению");
        startButton.setPreferredSize(new Dimension(180, 30));

        labelMessageSize = new JLabel("Размеры поля");
        labelMessageSize.setPreferredSize(new Dimension(150, 30));
        labelSideSize = new JLabel("Сторона :");
        labelSideSize.setPreferredSize(new Dimension(70, 30));

        puzzleSideSize = new JTextField(5);

        add(labelMessageSize);
        add(labelSideSize);
        add(puzzleSideSize);
        add(startButton);
        startButton.addActionListener(handler);

        setVisible(true);
    }

    public class Handler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == startButton) {
                try {
                    height = Integer.parseInt(puzzleSideSize.getText());
                    width = height;
                    if (width < 3 || width > 10)
                        throw new IllegalArgumentException();

                    stopThread = true;
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Беда. Говорят, что нужно вводить только целые числа!\n" +
                            "Я приберу за тобой, а ты попробуй ещё раз!", "Ошибочка закралась", 2);
                    puzzleSideSize.setText(null);
                } catch (IllegalArgumentException ex){
                    JOptionPane.showMessageDialog(null, "Поле должно быть от 3 до 10 ячеек!\n" +
                            "Я приберу за тобой, а ты попробуй ещё раз!", "Ошибочка закралась", 2);
                    puzzleSideSize.setText(null);
                }
            }
        }
    }
}
