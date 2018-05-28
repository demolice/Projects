package hrac;

import java.awt.Point;
import java.util.Random;
import piskvorky.Pozice;


public class RohovyHrac implements Hrac {

    @Override
    public Point tah(Pozice pozice) {
        int x = 0, y = 0;
        while(pozice.getPolicko(x, y) != Pozice.VOLNE){
            x++;
            if(x >= Pozice.VELIKOST_HRISTE){
                x = 0;
                y++;
            }
        }
        return new Point(x, y);
    }
}
