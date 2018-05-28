
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
class Center extends JPanel implements MouseListener {

    Logic l;

    Center(Logic l) {
        this.l = l;

        this.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "space");
        this.getActionMap().put("space", new buttonPressed("space"));

        addMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        l.changeCell(e.getX(), e.getY());
        System.out.println(e.getX() + " " + e.getY());
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    private class buttonPressed extends AbstractAction {

        String bt;

        public buttonPressed(String bt) {
            this.bt = bt;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            l.play();
        }
    }

    @Override
    public void paint(Graphics g) {

        g.setColor(Color.black);
        for (int i = 0; i < Main.WIDTH_COUNT; i++) {
            for (int j = 0; j < Main.HEIGH_COUNT; j++) {
                g.setColor(Color.black);
                g.drawRect(i * Main.BOX_SIZE, j * Main.BOX_SIZE,
                        Main.BOX_SIZE, Main.BOX_SIZE);
            }
        }
       
        g.setColor(Color.YELLOW);
        for (int x = 0; x < Main.WIDTH_COUNT; x++) {
            for (int y = 0; y < Main.HEIGH_COUNT; y++) {
                if (CellManager.getLife(x, y)) {
                    g.fillRect(x * Main.BOX_SIZE, y * Main.BOX_SIZE,
                            Main.BOX_SIZE, Main.BOX_SIZE);
                }
            }
        }
    }
}
