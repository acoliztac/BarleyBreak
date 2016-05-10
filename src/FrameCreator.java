import javax.swing.*;

public abstract class FrameCreator extends JFrame implements Runnable{

    volatile boolean isInterrupted = false;

    public FrameCreator(String name) {
        super(name);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }

    @Override
    public void run() {
        try {
            while(!isInterrupted){
                Thread.sleep(100);
            }
            setVisible(false);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}