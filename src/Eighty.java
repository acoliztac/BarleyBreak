import javax.swing.*;
import java.io.IOException;

public class Eighty {


    public static void main(String[] args) throws IOException, InterruptedException {

        String welcome ="Добро пожаловать в игру \"Восьмяшки\"!\n\n" +
                "Вам предстоит расставить все числа по возрастанию (слева-направо и сверху-вниз),\nа число \"0\" поместить в правый нижний угол.\n\n" +
                "Приятной игры!";
        JOptionPane.showMessageDialog(null, welcome, "Восьмяшки", 3);

        Reader r = new Reader("Восьмяшки");
        r.setVisible(true);
        r.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        r.setLocationRelativeTo(null);
        r.setSize(200, 190);
        r.setResizable(false);

        Thread tuning = new Thread(r);
        tuning.start();

        tuning.join();
        r.setVisible(false);

//        int[] arrayR     = {0, 1, 2, 3,  4,  5,  6,  7,  8,  9, 10};
        int[] widthArray  = {0, 0, 0, 61, 57, 55, 55, 55, 55, 55, 54};
        int[] heightArray = {0, 0, 0, 70, 67, 64, 62, 61, 60, 59, 59};

        Reader field = new Reader("Восьмяшки", r.height, r.width);
        field.setVisible(true);
        field.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        field.setLocationRelativeTo(null);
        field.setSize(widthArray[r.width] * r.width, heightArray[r.height] * r.height);
        field.setResizable(false);

        Thread game = new Thread(field);
        game.start();
        game.join();
        field.setVisible(false);
    }
}
