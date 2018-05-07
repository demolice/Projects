/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameOfLife;

import cz.gyarab.util.Utils;
import cz.gyarab.util.light.LightColor;
import cz.gyarab.util.light.Matrix;

/**
 *
 * @author JehoJaVista
 */
public class Core {
    Matrix m;
    Cell[][] cells;

    /**
     * Vytvoří a spustí Game of Life. Ve třídě se pracuje s dvourozměrným polem cells.
     * Toto pole uchovává údaje o buňkách Matrixu. Má navíc ještě rámeček, ze všech stran dokola 
     * jeden sloupec a jednu řádku, která se v Matrixu nezobraazuje. Slouží pro správnou funkčnost Matrixu.
     * @param width šířka matrixu
     * @param height výška matrixu
     * @param sleepMilis - doba mezi iteracemi v milisekundách (1000 ms = 1 s)
     */
    public Core(int width, int height, int sleepMilis) {
        this.m = Matrix.createMatrix(width, height);
        cells = new Cell[width+2][height+2];
        //inicializace naší paměti
        for(int i = 0;i<width+2;i++){
            for(int j = 0;j<height+2;j++){
                if(i==0||j==0||i==width+1||j==height+1){
                    //rámečkové buňky, nezobrazují se v matrixu
                    cells[i][j] = new Cell(i, j,false);
                }else
                    //standardní buňky
                    cells[i][j] = new Cell(i, j,true);
            }
        }
        lightOn(9, 6);
        lightOn(10, 6);
        lightOn(11, 6);
        lightOn(9, 7);
        lightOn(11, 7);
        lightOn(9, 8);
        lightOn(10, 8);
        lightOn(11, 8);
        lightOn(9, 9);
        lightOn(10, 9);
        lightOn(11, 9);
        lightOn(9, 10);
        lightOn(10, 10);
        lightOn(11, 10);
        lightOn(9, 11);
        lightOn(10, 11);
        lightOn(11, 11);
        lightOn(9, 12);
        lightOn(11, 12);
        lightOn(9, 13);
        lightOn(10, 13);
        lightOn(11, 13);
        
        m.showWindow();
        while(true){
            prepareNextGeneration();
            renderNextGeneration();
            Utils.sleep(sleepMilis);
        }
    }
    
    /**
     * Připraví novou generaci. Nejprve si vytvoří pomocné pole, kam si postupně ukládá
     * údaje o nové generaci. Poté staré pole cells nahradí.
     */
    private void prepareNextGeneration(){
        Cell[][] newCells = new Cell[cells.length][cells[0].length];
        for(int i = 0;i<newCells.length;i++){
            for(int j = 0;j<newCells[0].length;j++){
                //rámečkové buňky nikdy nesvítí (ani nemohou):
                if(!cells[i][j].isRegular()){
                    newCells[i][j] = new Cell(i, j, false);
                    continue;//kód zde končí a pokračuje se další iterací cyklu
                }
                //obdobný kód
                /*if(i==0||j==0||i==newCells.length-1||j==newCells[0].length-1){
                    newCells[i][j] = new Cell(i, j, false);
                    continue;
                }*/
                if(willBeAlive(cells[i][j])){
                    newCells[i][j] = new Cell(i, j, true);
                    newCells[i][j].setIsAlive(true);
                }else{
                    newCells[i][j] = new Cell(i, j, true);
                }
            }
        }
        cells = newCells;
        
    }
    
    /**
     * Vyrenderuje uložené pole do Matrixu. Vyrenderuje se bez rámečku.
     */
    private void renderNextGeneration(){
        for(int i = 1;i<cells.length-1;i++){
            for(int j = 1;j<cells[0].length-1;j++){
                if(cells[i][j].isIsAlive()){
                    m.setBackground(i-1, j-1, LightColor.YELLOW);
                }else{
                    m.setBackground(i-1, j-1, LightColor.BLACK);
                }
            }
        }
    }
    
    /**
     * Zjistí, zda bude zadaná buňka v příští iteraci naživu.
     * @param c buňka k otestování
     * @return true, pokud ano
     */
    private boolean willBeAlive(Cell c){
        int myX = c.getX();
        int myY = c.getY();
        int aliveNeighbours = 0;
        if(cells[myX+1][myY].isIsAlive())aliveNeighbours++;
        if(cells[myX+1][myY+1].isIsAlive())aliveNeighbours++;
        if(cells[myX][myY+1].isIsAlive())aliveNeighbours++;
        if(cells[myX-1][myY+1].isIsAlive())aliveNeighbours++;
        if(cells[myX-1][myY].isIsAlive())aliveNeighbours++;
        if(cells[myX-1][myY-1].isIsAlive())aliveNeighbours++;
        if(cells[myX][myY-1].isIsAlive())aliveNeighbours++;
        if(cells[myX+1][myY-1].isIsAlive())aliveNeighbours++;
        if(c.isIsAlive()){
            return aliveNeighbours >=2 && aliveNeighbours <=3;
        }else{
            return aliveNeighbours ==3;
        }
    }
    
    /**
     * Rozsvítí buňku.
     * @param x x souřadnice (bezrámečková souřadnice)
     * @param y y souřadnice (bezrámečková souřadnice)
     */
    private void lightOn(int x, int y){
        try{
            m.setBackground(x,y,LightColor.YELLOW);
            cells[x+1][y+1].setIsAlive(true);
        }catch(IllegalArgumentException e){
            System.out.println("Nemohu rozsvítit světlo na souřadnicích "+x+" "+y);
        }
    }
    
    /**
     * Zhasne buňku.
     * @param x x souřadnice (bezrámečková souřadnice)
     * @param y y souřadnice (bezrámečková souřadnice)
     */
    private void lightOff(int x, int y){
        try{
            m.setBackground(x, y, LightColor.BLACK);
            cells[x+1][y+1].setIsAlive(false);
        }catch(IllegalArgumentException e){
            System.out.println("Nemohu zhasnout světlo na souřadnicích "+x+" "+y);
        }
    }
    
    public static void main(String[] args) {
        new Core(25, 20, 300);
    }
}
