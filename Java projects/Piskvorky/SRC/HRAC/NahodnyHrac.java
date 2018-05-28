package hrac;

import java.awt.Point;
import java.util.Random;
import piskvorky.Pozice;

public class NahodnyHrac implements Hrac {

    @Override
    public Point tah(Pozice pozice) {
        Point vysledek;
        Random r = new Random();
        do {
            vysledek = new Point(r.nextInt(Pozice.VELIKOST_HRISTE), r.nextInt(Pozice.VELIKOST_HRISTE));
        } while(pozice.getPolicko(vysledek.x, vysledek.y) != Pozice.VOLNE);
        
        return vysledek;
    }
    
}
