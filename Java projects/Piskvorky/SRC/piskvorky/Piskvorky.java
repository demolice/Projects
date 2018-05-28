package piskvorky;

import hrac.AI;
import hrac.BlokujiciHrac;
import hrac.Clovek;
import hrac.Hrac;
import hrac.NahodnyHrac;
import hrac.RohovyHrac;
import java.awt.Dimension;
import java.awt.Point;
import javax.swing.JFrame;
import vystup.PiskvorkyPanel;

public class Piskvorky {

    public static void main(String[] args) {  
        Hrac hrac2 = new Clovek();
        Hrac hrac1 = new BlokujiciHrac();
        Piskvorky piskvorky = new Piskvorky(hrac1, hrac2);
        piskvorky.souboj();
        piskvorky.jednaHra();
    }

    Hrac hrac1, hrac2;
    PiskvorkyPanel panel;
    JFrame frame;
    Pozice pozice;

    static final int ZPOZDENI_AI = 500; //kolik milisekund pockat pred tahem pocitace

    public Piskvorky(Hrac hrac1, Hrac hrac2) {
        this.hrac1 = hrac1;
        this.hrac2 = hrac2;
    }

    private void jednaHra() {
        vytvorOkno();
        int vitez = Pozice.VOLNE;
        while (vitez == Pozice.VOLNE && (pozice.getCisloTahu() < Pozice.VELIKOST_HRISTE * Pozice.VELIKOST_HRISTE)) {
            Hrac hraje = pozice.getHrajiKrizky() ? hrac1 : hrac2;
            vitez = dalsiTah(hraje);
            frame.repaint();
            if (hraje.getClass() != Hrac.class) {
                try {
                    Thread.sleep(ZPOZDENI_AI);
                } catch (Exception e) {
                }
            }
        }
    }

    final static int MARGIN = 5;

    private void souboj() {
        if (hrac1.getClass() == Clovek.class || hrac2.getClass() == Clovek.class)
            return;
        
        int[][] w = new int[3][3];
        for (int x = MARGIN; x < Pozice.VELIKOST_HRISTE - MARGIN; x++) {
            for (int y = MARGIN; y < Pozice.VELIKOST_HRISTE - MARGIN; y++) {
                for (int opacne = 0; opacne < 2; opacne++) {
                    pozice = new Pozice();
                    pozice.vykonejTah(x, y);
                    int vitez = Pozice.VOLNE;
                    while (vitez == Pozice.VOLNE && (pozice.getCisloTahu() < Pozice.VELIKOST_HRISTE * Pozice.VELIKOST_HRISTE)) {
                        Hrac hraje = (pozice.getHrajiKrizky() == (opacne == 0)) ? hrac1 : hrac2;
                        vitez = dalsiTah(hraje);
                    }
                    if (opacne == 1) vitez = 3 - vitez;
                    w[vitez][opacne]++;
                    w[vitez][2]++;
                }
            }
        }
        System.out.println("\t\tRemíza\thrac1\thrac2");
        String[] texts = {"Začíná hrac1", "Začíná hrac2", "Celkové skóre"};
        for (int i = 0; i < 3; i++) {
            System.out.print(texts[i] + ":\t");
            for (int j = 0; j < 3; j++) {
                System.out.print(w[j][i] + "\t");
            }
            System.out.println();
        }
        System.out.println();
        int res = w[1][2] - w[2][2];
        if (res == 0) {
            System.out.println("Remíza!");
        } else if (res > 0) {
            System.out.println("Vyhrává hráč 1 (" + hrac1.getClass() + ")!");
        } else {
            System.out.println("Vyhrává hráč 2 (" + hrac2.getClass() + ")!");
        }
    }

    private int dalsiTah(Hrac hraje) {
        Point tah = hraje.tah(pozice);
        Pozice p2 = new Pozice(pozice);
        if (!p2.vykonejTah(tah.x, tah.y)) {
            return pozice.getHrajiKrizky() ? Pozice.KOLECKO : Pozice.KRIZEK;
        } else {
            pozice.set(p2);
            return pozice.getVitez();
        }
    }

    private void vytvorOkno() {
        pozice = new Pozice();

        frame = new JFrame();
        frame.getContentPane().setPreferredSize(new Dimension(600, 600));
        frame.pack();

        panel = new PiskvorkyPanel(pozice);
        panel.setSize(600, 600);
        frame.add(panel);

        for (Hrac h : new Hrac[]{hrac1, hrac2}) {
            if (h.getClass() == Clovek.class) {
                Clovek c1 = (Clovek) h;
                frame.getContentPane().addMouseListener(c1);
                c1.setTileSize(panel.getTileSize());
            }
        }
        //Úpravy okna
        frame.setTitle("Piškvorky");
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
