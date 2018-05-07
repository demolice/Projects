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
public enum Orientation {
    UP, RIGHT, DOWN, LEFT; //static final Orientation UP = new Orientation(345);

        public static Orientation getOrientation(int ordinal){
            switch(ordinal){
                case 0:
                    return UP;
                case 1:
                    return RIGHT;
                case 2:
                    return DOWN;
                case 3:
                    return LEFT;
                default:
                    return null;
            }
        }
}
