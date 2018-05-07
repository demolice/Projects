/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameOfLife;

/**
 *
 * @author JehoJaVista
 */
public class Cell {
    private boolean isAlive = false;
    private final boolean regular;
    private final int x;
    private final int y;

    public Cell(int x, int y, boolean regular) {
        this.x = x;
        this.y = y;
        this.regular = regular;
    }

    public boolean isIsAlive() {
        return isAlive;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setIsAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

    /**
     * Ptáme se na to, jestli je to normální buňka, nebo buňka pomocná
     * (rámeček).
     * @return 
     */
    public boolean isRegular() {
        return regular;
    }
    
    
    
    
    
    
}
