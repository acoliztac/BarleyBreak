import javax.swing.*;
import java.io.IOException;

public class Eighty {


    public static void main(String[] args) throws IOException, InterruptedException {

//        String welcome ="Добро пожаловать в игру \"Восьмяшки\"!\n\n" +
//                "Вам предстоит расставить все числа по возрастанию (слева-направо и сверху-вниз),\nа пустую ячейку " +
//                "поместить в правый нижний угол.\n\nПриятной игры!";
//        JOptionPane.showMessageDialog(null, welcome, "Восьмяшки", 3);

        MainFrame mainFrame = new MainFrame("Восьмяшки");
        Thread tuning = new Thread(mainFrame);
        tuning.start();
        tuning.join();
        mainFrame.setVisible(false);

        FieldFrame field = new FieldFrame("Восьмяшки", mainFrame.height, mainFrame.width);
        Thread game = new Thread(field);
        game.start();
        game.join();
        field.setVisible(false);
    }
}
