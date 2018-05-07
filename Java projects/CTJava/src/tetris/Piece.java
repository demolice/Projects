/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris;

/**
 *
 * @author JehoJaVista
 */

import static tetris.Orientation.*;

public class Piece {
    private final Pattern pattern;
    private Orientation orientation = UP;
    private int x = 0;
    private int y = 0;
    

    public Piece(Pattern pattern) {
        this.pattern = pattern;
    }

    public void setOrientation(Orientation orientation) {
        if(orientation==null)throw new NullPointerException("Orientation nemůže být null!");
        this.orientation = orientation;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public Orientation getOrientation() {
        return orientation;
    }
    
    public int getWidth(){
        return pattern.getBlock().length;
    }
    
    public int getHeight(){
        return pattern.getBlock()[0].length;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    
    
    
}
