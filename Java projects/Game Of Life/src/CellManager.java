/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Daniil
 */
public class CellManager {
    private static boolean[][] cellList = new boolean[Main.WIDTH_COUNT][Main.HEIGH_COUNT];
    
    /**
     * Returns value of the cell
     * @param x
     * @param y
     * @return true if it is alive
     */
    public static boolean getLife(int x, int y) {
        return cellList[x][y];
    }
    
    /**
     * Sets the cell to life
     * @param x possition 
     * @param y possition
     */
    public static void setLife(int x, int y) {
        cellList[x][y] = true;
    }
    
    /**
     * Deactivates the cell
     * @param x
     * @param y 
     */
    public static void setDead(int x, int y) {
        cellList[x][y] = false;
    }
    
    public static void turnLife(int x, int y) {
        cellList[x][y] = !cellList[x][y];
        System.out.println(x + " " + y + ": " + cellList[x][y]);
    }
    
    public static void setNewGrid(boolean[][] grid) {
        cellList = grid;
    }
    
    public static boolean[][] getGrid() {
        return cellList;
    }
    
    public static void write(){
        for (int i = 0; i < cellList.length; i++) {
            for (int j = 0; j < cellList.length; j++) {
                if (getLife(j, i)){
                    System.out.print("1 ");
                } else {
                    System.out.print("0 ");
                }
            }
            System.out.println("");
        }
    }
    
}
