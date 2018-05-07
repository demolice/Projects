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
public enum Pattern {
    I(new boolean[][]{new boolean[]{true,true,true,true}}), 
    O(new boolean[][]{new boolean[]{true, true},new boolean[]{true,true}}), 
    T(new boolean[][]{new boolean[]{true, true,true},new boolean[]{false,true,false}}), 
    L(new boolean[][]{{true,true,true},{true,false,false}}), 
    J(new boolean[][]{{true,false,false},{true,true,true}}), 
    Z(new boolean[][]{{true,true,false},{false,true,true}}), 
    S(new boolean[][]{{false,true,true},{true,true,false}});
    
    private final boolean[][] block;

    private Pattern(boolean[][] block) {
        this.block = block;
    }

    public boolean[][] getBlock() {
        return block;
    }
    
    public static Pattern getPattern(int ordinal){
        switch(ordinal){
            case 0:
                return I;
            case 1:
                return O;
            case 2:
                return T;
            case 3:
                return L;
            case 4:
                return J;
            case 5:
                return Z;
            case 6:
                return S;
            default:
                return null;
        }
    }
    
}
