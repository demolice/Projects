/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package piskvorky;

import java.awt.Point;

public class Pozice {

    public final static int VELIKOST_HRISTE = 15;
    public final static int VELIKOST_RADY = 5; //kolik znaků za sebou je potřeba k vítězství
    public final static int VOLNE = 0;
    public final static int KRIZEK = 1;
    public final static int KOLECKO = 2;

    private int cisloTahu = 0; // rovna se poctu umistenych kamenu
    private int[][] stav = new int[15][15];
    private boolean hrajiKrizky = true;
    private Point posledniTah = new Point(-1, -1);

    /**
     * Vytvoří kopii existující pozice.
     */
    public Pozice(Pozice p) {
        for (int i = 0; i < VELIKOST_HRISTE; i++) {
            for (int j = 0; j < VELIKOST_HRISTE; j++) {
                stav[i][j] = p.stav[i][j];
            }
        }
        hrajiKrizky = p.hrajiKrizky;
        cisloTahu = p.cisloTahu;
        posledniTah = p.posledniTah;
    }

    public Pozice() {
        for (int i = 0; i < VELIKOST_HRISTE; i++) {
            for (int i2 = 0; i2 < VELIKOST_HRISTE; i2++) {
                stav[i][i2] = VOLNE;
            }
        }
    }

    public int getCisloTahu() {
        return cisloTahu;
    }

    /**
     *
     * @return Zda je daná pozice na hřišti.
     */
    public boolean jeNaHristi(int x, int y) {
        if (x < 0 || y < 0 || x >= VELIKOST_HRISTE || y >= VELIKOST_HRISTE) {
            return false;
        }
        return true;
    }

    public int getPolicko(int x, int y) {
        if (!jeNaHristi(x, y)) return VOLNE;
        return stav[x][y];
    }

    public Point getPosledniTah() {
        return posledniTah;
    }

    /**
     * Pokusí se vykonat tah daný pozicí. Upraví pozici, pokud je tah validní.
     *
     * @param x x-ová souřadnice tahu
     * @param y y-ová souřadnice tahu
     * @return Vrátí true, pokud byl zadaný tah validní (na volné políčko).
     */
    public boolean vykonejTah(int x, int y) {
        if (!jeNaHristi(x, y)) return false;
        if (stav[x][y] == VOLNE) {
            cisloTahu++;
            posledniTah = new Point(x, y);

            if (hrajiKrizky) {
                stav[x][y] = KRIZEK;
            } else {
                stav[x][y] = KOLECKO;
            }
            hrajiKrizky = !hrajiKrizky;
            return true;
        }
        return false;
    }

    public boolean getHrajiKrizky() {
        return hrajiKrizky;
    }

    /**
     * Vrátí číslo toho znaku, který je na tahu.
     *
     * @return
     */
    public int znakNaTahu() {
        if (hrajiKrizky) {
            return KRIZEK;
        } else {
            return KOLECKO;
        }
    }

    /**
     * Zkontroluje, zda nějaký hráč nevyhrál, a vrátí číslo jeho znaku.
     *
     * @return VOLNE, pokud nikdo nevyhrál. Jinak KRIZEK nebo KOLECKO podle
     * toho, ktery hrac vyhral.
     */
    public int getVitez() {
        int[] dx = {1, 1, 0, -1};
        int[] dy = {0, 1, 1, 1};
        for (int smer = 0; smer < 4; smer++) {
            for (int x = 0; x < VELIKOST_HRISTE; x++) {
                for (int y = 0; y < VELIKOST_HRISTE; y++) {
                    int delka = delkaRady(x, y, dx[smer], dy[smer]);
                    if (delka >= 5) {
                        return getPolicko(x, y);
                    }
                }
            }
        }
        return 0;
    }

    /**
     * Vrátí počet souvislých znaků z dané pozice a daného směru.
     *
     * @param x x počáteční pozice
     * @param y y počáteční pozice
     * @param zmenaX o kolik se má změnit x v každém kroku
     * @param zmenaY o kolik se má změnit y v každém kroku
     * @return
     */
    public int delkaRady(int x, int y, int zmenaX, int zmenaY) {
        int znak = getPolicko(x, y); //znak, který bude tvořit řadu
        if (znak == VOLNE) return 0;

        int vysledek = 0;
        while (jeNaHristi(x, y)) {
            if (getPolicko(x, y) != znak) return vysledek; //řada končí
            x += zmenaX;
            y += zmenaY;
            vysledek++;
        }
        return vysledek;
    }
    
    
    

    /**
     * Vytvoří ze sebe kopii existující pozice.
     *
     * @param p2
     */
    public void set(Pozice p) {
        for (int i = 0; i < VELIKOST_HRISTE; i++) {
            for (int j = 0; j < VELIKOST_HRISTE; j++) {
                stav[i][j] = p.stav[i][j];
            }
        }
        hrajiKrizky = p.hrajiKrizky;
        cisloTahu = p.cisloTahu;
        posledniTah = p.posledniTah;
    }
}
