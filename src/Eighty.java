import javax.swing.*;
import java.io.IOException;

public class Eighty {


    public static void main(String[] args) throws IOException, InterruptedException {

        String welcomeMessage ="Добро пожаловать в игру \"Восьмяшки\"!\n\n" +
                "Вам предстоит расставить все числа по возрастанию (слева-направо и сверху-вниз),\nа пустую ячейку " +
                "поместить в правый нижний угол.\n\nПриятной игры!";
        JOptionPane.showMessageDialog(null, welcomeMessage, "Восьмяшки", 3);

        MainFrame mainFrame = new MainFrame();
        Thread tuning = new Thread(mainFrame);
        tuning.start();
        tuning.join();

        Puzzle field = new Puzzle(mainFrame.width, mainFrame.height);
        Thread game = new Thread(field);
        game.start();
        game.join();
    }
}
