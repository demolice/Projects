package hrac;

import java.awt.Point;
import java.util.Random;
import piskvorky.Pozice;
import static piskvorky.Pozice.KOLECKO;
import static piskvorky.Pozice.KRIZEK;
import static piskvorky.Pozice.VOLNE;

public class AI implements Hrac {

    @Override
    public Point tah(Pozice pozice) {
        if (pozice.getCisloTahu() == 0) {
            return new Point(Pozice.VELIKOST_HRISTE / 2, Pozice.VELIKOST_HRISTE / 2);
        }

        int[] dx = {-1, 0, 1, -1, 1, -1, 0, 1};
        int[] dy = {-1, -1, -1, 0, 0, 1, 1, 1};

        Point vysledek = null;
        int nejlepsiHodnota = 0;

        for (int x = 0; x < Pozice.VELIKOST_HRISTE; x++) {
            for (int y = 0; y < Pozice.VELIKOST_HRISTE; y++) {

                if (pozice.getCisloTahu() == 1) {
                    Random r = new Random();
                    if (pozice.getPolicko(x, y) == Pozice.KRIZEK) {
                        vysledek = new Point(x + r.nextInt(3) - 1, y + r.nextInt(3) - 1);

                    }
                }

                if (pozice.znakNaTahu() == KRIZEK) {
                    if (pozice.getPolicko(x, y) == Pozice.KRIZEK) {
                        if (nejdelsiRada(x, y, 4, pozice, KRIZEK) != null) {
                            vysledek = new Point(x, y);
                            return vysledek;
                        }
                    }
                } else {
                    if (pozice.getPolicko(x, y) == Pozice.KOLECKO) {
                        if (nejdelsiRada(x, y, 4, pozice, KOLECKO) != null) {
                            vysledek = new Point(x, y);
                            return vysledek;
                        }
                    }
                }

                if (pozice.znakNaTahu() == KRIZEK) {
                    if (pozice.getCisloTahu() % 2 == 0) {
                        if (pozice.getPolicko(x, y) == KRIZEK) {
                            if (nejdelsiRada(x, y, 3, pozice, KRIZEK) != null) {
                                vysledek = new Point(x, y);
                                return vysledek;
                            }
                        }
                        if (pozice.getPolicko(x, y) == KOLECKO) {
                            if (nejdelsiRada(x, y, 3, pozice, KOLECKO) != null) {
                                vysledek = new Point(x, y);
                                return vysledek;
                            }
                        }

                    }
                } else {
                    if (pozice.getCisloTahu() % 2 == 0) {

                        if (pozice.getPolicko(x, y) == KOLECKO) {
                            if (nejdelsiRada(x, y, 3, pozice, KOLECKO) != null) {
                                vysledek = new Point(x, y);
                                return vysledek;
                            }
                        }
                        if (pozice.getPolicko(x, y) == KRIZEK) {
                            if (nejdelsiRada(x, y, 3, pozice, KRIZEK) != null) {
                                vysledek = new Point(x, y);
                                return vysledek;
                            }
                        }

                    } else {

                        if (pozice.getPolicko(x, y) == KOLECKO) {
                            if (nejdelsiRada(x, y, 3, pozice, KOLECKO) != null) {
                                vysledek = new Point(x, y);
                                return vysledek;
                            }
                        }

                        if (pozice.getPolicko(x, y) == KRIZEK) {
                            if (nejdelsiRada(x, y, 3, pozice, KRIZEK) != null) {
                                vysledek = new Point(x, y);
                                return vysledek;
                            }
                        }

                    }
                }

                if (pozice.getPolicko(x, y) != Pozice.VOLNE) {
                    continue;
                }
                int hodnota = 0;
                for (int i = 0; i < 8; i++) {
                    hodnota = Math.max(hodnota, pozice.delkaRady(x + dx[i], y + dy[i], dx[i], dy[i]));
                }
                if (pozice.getPolicko(x, y) == Pozice.VOLNE) {
                    vysledek = new Point(x, y);
                    return vysledek;
                }
            }
        }

        return vysledek;

    }

    public int delkaRadyZnaku(int x, int y, int zmenaX, int zmenaY, Pozice pozice, int znakZ) {
        int znak = pozice.getPolicko(x, y); //znak, který bude tvořit řadu
        if (znak == VOLNE) {
            return 0;
        }
        if (znak == znakZ) {
            return 0;
        }
        int vysledek = 0;
        while (pozice.jeNaHristi(x, y)) {
            if (pozice.getPolicko(x, y) != znak) {
                return vysledek; //řada končí
            }
            x += zmenaX;
            y += zmenaY;
            vysledek++;
        }
        return vysledek;

    }

    public Point nejdelsiRada(int x, int y, int delkaRady, Pozice pozice, int znak) {

        int[] dx = {-1, 0, 1, -1, 1, -1, 0, 1};
        int[] dy = {-1, -1, -1, 0, 0, 1, 1, 1};

        Point vysledek = null;

        int hodnota = 0;
        for (int i = 0; i < 8; i++) {
            hodnota = Math.max(hodnota, delkaRadyZnaku(x + dx[i], y + dy[i], dx[i], dy[i], pozice, znak));

            if (hodnota == delkaRady && pozice.getPolicko(x + dx[i] * hodnota, y + dy[i] * hodnota) == Pozice.VOLNE
                    && pozice.jeNaHristi(x + dx[i] * hodnota, y + dy[i] * hodnota)) {

                vysledek = new Point(x + dx[i] * hodnota, y + dy[i] * hodnota);
                return vysledek;

            } else if (hodnota == delkaRady && pozice.getPolicko(x - dx[i], y - dy[i]) == Pozice.VOLNE
                    && pozice.jeNaHristi(x - dx[i], y - dy[i])) {

                vysledek = new Point(x - dx[i], y - dy[i]);

                return vysledek;
            } else {
                hodnota = 0;
            }
        }

        return vysledek;

    }

}
