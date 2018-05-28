
import java.awt.Color;
import java.awt.Dimension;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Daniil
 */
public class Main {
    public static final int BOX_SIZE = 10;
    
    public static final int WIDTH_COUNT = 50;
    public static final int HEIGH_COUNT = 50;
    
    public static final int SOX = WIDTH_COUNT * BOX_SIZE;
    public static final int SOY = HEIGH_COUNT * BOX_SIZE;
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Logic l = new Logic();
        Center c = new Center(l);
        
        c.setSize(Main.SOX, Main.SOY);
        c.setPreferredSize(new Dimension(Main.SOX, Main.SOY));
        
        JFrame frame = new JFrame("Game Of Life");
        frame.setSize(SOX, SOY);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        frame.add(c);
        c.setBackground(Color.getHSBColor(0, 0, 0.13f));
        frame.pack();
        frame.setVisible(true);   
        
        
        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                frame.repaint();
            }
        }, 10, 10);
    }
    
}
