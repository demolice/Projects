
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.JobAttributes;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Daniil
 */
class Center extends JPanel {

    Logic l;

    int boxSize;
    int borders;
    int w, h;

    Center(Logic l) {

        this.l = l;

        this.boxSize = Main.BOXSIZE;
        this.borders = Main.BORDERS;

        this.h = Main.HCOUNT;
        this.w = Main.WCOUNT;

        this.setSize(Main.SOX, Main.SOY);
        this.setPreferredSize(new Dimension(Main.SOX, Main.SOY));

        this.getInputMap().put(KeyStroke.getKeyStroke("UP"), "up");
        this.getActionMap().put("up", new buttonPressed("up"));

        this.getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "down");
        this.getActionMap().put("down", new buttonPressed("down"));

        this.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "left");
        this.getActionMap().put("left", new buttonPressed("left"));

        this.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "right");
        this.getActionMap().put("right", new buttonPressed("right"));
    }

    private class buttonPressed extends AbstractAction {

        String bt;

        public buttonPressed(String bt) {
            this.bt = bt;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            l.rotate(bt);
        }
    }
    
    @Override
    public void paintComponent(Graphics g) {
        g.setColor(Color.BLACK);

        for (int i = 0; i < (Main.SOX - (borders * 2)) / boxSize; i++) {
            for (int j = 0; j < (Main.SOY - (borders * 2)) / boxSize; j++) {
                g.drawRect(i * boxSize + borders,
                        j * boxSize + borders, boxSize, boxSize);
            }
        }

        boolean[][] grid = l.getGrid();
        Color[][] gridColor = l.getColorGrid();
        Particle p = l.getParticle();
        

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j]) {
                    g.setColor(gridColor[i][j]);

                    g.fillRect(i * boxSize + borders ,
                            j * boxSize + borders ,
                            boxSize+ 1 , boxSize + 1);
                } 
                
            }
        }
        
        g.setColor(p.getC());
        for (int i = 0; i < p.getT().getShape().length; i++) {
            for (int j = 0; j < p.getT().getShape()[i].length; j++) {
                if (p.getT().getShape()[i][j]) {
                  g.fillRect((p.getX() + i) * boxSize + borders + 1,
                          (p.getY() + j) * boxSize + borders + 1,
                           boxSize - 1, boxSize - 1);                 
                }
            }
        }
        
    }
}
