
import java.awt.Color;
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
    public static final int BOXSIZE = 30; // Size of the square
    public static final int BORDERS = 40; // Space between playfield and border window
    
    public static final int WCOUNT = 10; // Number of boxes in a row
    public static final int HCOUNT = 20; // Number of boxes in a colom
    
    public static final int SOX = 2 * BORDERS + WCOUNT * BOXSIZE; // Size of window - x
    public static final int SOY = 2 * BORDERS + HCOUNT * BOXSIZE; // Size of window - y
    
    private static boolean timer = true;
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Logic l = new Logic();
        Center c = new Center(l);
        
        JFrame frame = new JFrame("Tetris");
        frame.setSize(SOX, SOY);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        frame.add(c);
        frame.setBackground(Color.getHSBColor(0, 0, 0.13f));
        frame.pack();
        
        
        //Setting timer to repaint frame
        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            int tickCount = 0;
            
            @Override
            public void run() {
                frame.repaint();
                
                if (l.getTimer()) {
                    
                    t.cancel();
                }          
                if (tickCount >= 16) {
                    l.tick();
                    tickCount = 0;
                } else {
                    tickCount += 1;
                } 
            }
        }, 1000, 30);
        
        frame.setVisible(true);
    }
    
}
