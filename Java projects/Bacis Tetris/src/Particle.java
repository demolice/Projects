
import java.awt.Color;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Daniil
 */
class Particle {

    private int x, y;
    
    private Color c;
    
    
    
    particleType t;
    
    Particle(particleType t) {
        this.t = t;
        
        this.x = 4;
        this.y = 0;
        
        this.c = Color.RED;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Color getC() {
        return c;
    }

    

    public particleType getT() {
        return t;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setC(Color c) {
        this.c = c;
    }

   
    
    
}
