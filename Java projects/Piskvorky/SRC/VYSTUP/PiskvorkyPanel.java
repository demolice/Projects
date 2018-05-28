package vystup;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import static java.awt.SystemColor.text;
import javax.swing.JPanel;
import piskvorky.Pozice;
import static piskvorky.Pozice.VELIKOST_HRISTE;

public class PiskvorkyPanel extends JPanel {

    Pozice pozice;
    float margin = 1;

    public PiskvorkyPanel(Pozice pozice) {
        this.pozice = pozice;
    }

    public float getTileSize() {
        float tileSize = (float) getWidth() / Pozice.VELIKOST_HRISTE;
        if ((double) getHeight() / Pozice.VELIKOST_HRISTE < tileSize) {
            tileSize = (float) getHeight() / Pozice.VELIKOST_HRISTE;
        }
        return tileSize;
    }

    @Override
    public void paintComponent(Graphics g2) {
        Graphics2D g = (Graphics2D) g2;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.setColor(Color.black);
        g.fillRect(0, 0, getWidth(), getHeight());

        float tileSize = getTileSize();

        boolean[][] vitezne = getVitezne();

        for (int x = 0; x < Pozice.VELIKOST_HRISTE; x++) {
            for (int y = 0; y < Pozice.VELIKOST_HRISTE; y++) {
                g.setColor(vitezne[x][y] ? Color.yellow : Color.white);
                g.fillRect((int) (x * tileSize + margin), (int) (y * tileSize + margin),
                        (int) (tileSize - 2 * margin), (int) (tileSize - 2 * margin));
                String s = "";
                if (pozice.getPolicko(x, y) == Pozice.KRIZEK) {
                    g.setColor(Color.red);
                    s = "X";
                } else if (pozice.getPolicko(x, y) == Pozice.KOLECKO) {
                    g.setColor(Color.blue);
                    s = "O";
                }
                if (s.length() > 0)
                    drawStringCentered(g, s, x * tileSize + margin, y * tileSize + margin, tileSize - 2 * margin);
            }
        }
    }

    private void drawStringCentered(Graphics2D g, String s, float x0, float y0, float size) {
        FontMetrics metrics = g.getFontMetrics(g.getFont());
        float x = (size - metrics.stringWidth(s)) / 2;
        float y = ((size - metrics.getHeight()) / 2) + metrics.getAscent();
        g.drawString(s, x0 + x, y0 + y);
    }

    private boolean[][] getVitezne() {
        boolean[][] res = new boolean[VELIKOST_HRISTE][VELIKOST_HRISTE];
        int[] dx = {1, 1, 0, -1};
        int[] dy = {0, 1, 1, 1};
        for (int smer = 0; smer < 4; smer++) {
            for (int x = 0; x < VELIKOST_HRISTE; x++) {
                for (int y = 0; y < VELIKOST_HRISTE; y++) {
                    int delka = pozice.delkaRady(x, y, dx[smer], dy[smer]);
                    if (delka >= 5) {
                        for (int i = 0; i < delka; i++) {
                            res[x + dx[smer] * i][y + dy[smer] * i] = true;
                        }
                    }
                }
            }
        }
        return res;
    }
}
