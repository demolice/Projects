package hrac;

import java.awt.Point;
import piskvorky.Pozice;


public class BlokujiciHrac implements Hrac {

    @Override
    public Point tah(Pozice pozice) {
        if (pozice.getCisloTahu() == 0)
            return new Point(Pozice.VELIKOST_HRISTE / 2, Pozice.VELIKOST_HRISTE / 2);
        int[] dx = {-1, 0, 1, -1, 1, -1, 0, 1};
        int[] dy = {-1, -1, -1, 0, 0, 1, 1, 1};
        
        Point vysledek = null;
        int nejlepsiHodnota = 0;
        
        for (int x = 0; x < Pozice.VELIKOST_HRISTE; x++) {
            for (int y = 0; y < Pozice.VELIKOST_HRISTE; y++) {
                
                
                if (pozice.getPolicko(x, y) != Pozice.VOLNE) continue;
                int hodnota = 0;
                for (int i = 0; i < 8; i++) {
                    hodnota = Math.max(hodnota, pozice.delkaRady(x+dx[i], y+dy[i], dx[i], dy[i]));
                }
                if(hodnota > nejlepsiHodnota){
                    nejlepsiHodnota = hodnota;
                    vysledek = new Point(x, y);
                }
            }
        }
        return vysledek;
    }
}
