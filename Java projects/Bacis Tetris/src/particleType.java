/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Daniil
 */
public enum particleType {
    S(new boolean[][]{{true, true, false},
    {false, true, true},
    {false, false, false}}),
    L(new boolean[][]{{false, true, false},
    {false, true, false},
    {false, true, true}}),
    O(new boolean[][]{{true, true},
    {true, true}}),
    T(new boolean[][]{{false, true, false},
    {true, true, true},
    {false, false, false}}),
    I(new boolean[][]{{false, true, false, false},
    {false, true, false, false},
    {false, true, false, false},
    {false, true, false, false}}),
    Z(new boolean[][]{{false, true, true},
    {true, true, false},
    {false, false, false}});

    private boolean[][] shape;

    particleType(boolean[][] i) {
        this.shape = i;
    }
    
    public boolean[][] getShape() {
        return shape;
    }
    
    public void setShape(boolean[][] shape) {
        this.shape = shape;
    }
}
