package hrac;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import piskvorky.Pozice;

public class Clovek implements Hrac, MouseListener {

    volatile Point tah = null;

    @Override
    public Point tah(Pozice pozice) {
        while (tah == null) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        Point res = (Point) tah.clone();
        tah = null;
        return res;
    }

    float tileSize = 1;

    public void setTileSize(float ts) {
        tileSize = ts;
    }

    public void setTah(Point p) {
        tah = p;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        tah = new Point((int) (e.getX() / tileSize), (int) (e.getY() / tileSize));
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
}
