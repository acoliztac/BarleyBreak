import javax.swing.*;
import java.io.IOException;

public class Eighty {


    public static void main(String[] args) throws IOException, InterruptedException {

        String welcome ="Добро пожаловать в игру \"Восьмяшки\"!\n\n" +
                "Вам предстоит расставить все числа по возрастанию (слева-направо и сверху-вниз),\nа пустую ячейку " +
                "поместить в правый нижний угол.\n\nПриятной игры!";
        JOptionPane.showMessageDialog(null, welcome, "Восьмяшки", 3);

        MainFrame mainFrame = new MainFrame("Восьмяшки");
        mainFrame.setSize(200, 190);
        Thread tuning = new Thread(mainFrame);
        tuning.start();
        tuning.join();

        FieldFrame field = new FieldFrame("Восьмяшки", mainFrame.height, mainFrame.width);

//      int[] arrayR      = {0, 1, 2, 3,  4,  5,  6,  7,  8,  9,  10};
        int[] widthArray  = {0, 0, 0, 66, 57, 55, 55, 55, 55, 55, 54};
        int[] heightArray = {0, 0, 0, 71, 67, 64, 62, 61, 60, 59, 59};
        field.setSize(widthArray[mainFrame.width] * mainFrame.width, heightArray[mainFrame.height] * mainFrame.height);

        Thread game = new Thread(field);
        game.start();
        game.join();
    }
}
