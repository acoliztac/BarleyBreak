import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public abstract class FrameCreator extends JFrame implements Runnable{

    boolean stopThread = false;

    public FrameCreator(String s) {
        super(s);
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


}