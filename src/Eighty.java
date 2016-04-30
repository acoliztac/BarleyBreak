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

        Reader field = new Reader("Сыграем-с в восьмяшки!!", r.height, r.width);
        field.setVisible(true);
        field.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        field.setLocationRelativeTo(null);
        field.setSize(55 * r.width, 64 * r.height);
        field.setResizable(false);

        Thread game = new Thread(field);
        game.start();
        game.join();
        field.setVisible(false);
    }
}
