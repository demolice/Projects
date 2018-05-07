/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris;

import cz.gyarab.util.Utils;
import cz.gyarab.util.event.EventListener;
import cz.gyarab.util.event.KeyEvent;
import cz.gyarab.util.light.LightColor;
import cz.gyarab.util.light.Matrix;
import java.util.Random;

/**
 *
 * @author JehoJaVista
 */
public class Main {

    /**
     * Testuje tvar jeho vykreslením. Pouze pro testovací účely
     * @param p tvar k vykreslení
     */
    public void testPattern(Pattern p) {
        boolean[][] test = new Piece(p).getPattern().getBlock();
        Matrix m = Matrix.createMatrix(test.length, test[0].length);
        for (int i = 0; i < test.length; i++) {
            for (int j = 0; j < test[0].length; j++) {
                if (test[i][j] == true) {
                    m.setBackground(i, j, LightColor.YELLOW);
                }
            }
        }
        m.showWindow();
    }

    private Matrix matrix;
    private Piece piece;

    /**
     * Vytvoří hrací plochu.
     * @param width šířka
     * @param height výška
     */
    public Main(int width, int height) {
        this.matrix = Matrix.createMatrix(width, height);
        manageKeyEvent();
    }

    /**
     * Spravuje uživatelské rozhraní
     */
    public void manageKeyEvent(){
        matrix.getInteractiveLightPanel().addKeyListener(new EventListener<KeyEvent>() {
            @Override
            public void event(KeyEvent event) {
                if(event.getEventType()==KeyEvent.Type.PRESSED){
                    System.out.println("pressed");   
                }else{
                    System.out.println("released");
                }
                         
            }
        });
    }
    
    /**
     * Spustí Tetris.
     */
    public void run() {
        matrix.showWindow();
        
        piece = getNewPiece();
        render(piece, 5,16);
        while(true){
            if(canRender(piece, 5, piece.getY()-1)){
                render(piece, 5, piece.getY()-1);
                Utils.sleep(100);
            }else{
                piece = getNewPiece();
                render(piece, 5,16);
            }
            
        }
    }
    
    /**
     * Ověří, zda se může kostka vykreslit o políčko níže. Pro každý pixel kostičky to otestuje, zda tam může být.
     * Pokud se alespoň jeden pixel nemůže vykreslit (buď tam už kostka je, nebo by se vykreslil mimo metrix), kostka vykreslit nelze. 
     * @param p kostka k otestování
     * @param x 
     * @param y
     * @return true, pokud se může vykresilt
     */
    private boolean canRender(Piece p, int x, int y){
        System.out.println("-----NEWTEST-----");
        for (int i = 0; i < p.getWidth(); i++) {
            for (int j = 0; j < p.getHeight(); j++) {
                if (p.getPattern().getBlock()[i][j] == true) {
                    try{
                        if(matrix.getBackground(i + x, j + y)!=LightColor.BLACK){
                            //return false;
                        }
                        System.out.println((i + p.getX())+" "+(j + p.getY()));
                    }catch (Exception e){
                        return false;
                    }
                    //throw new UnsupportedOperationException("Nutná oprava metody! DDÚ");
                }
            }
        }
        System.out.println("SUCCEES");
        return true;
    }
    
    private boolean isMyCoord(Piece p, int x, int y){
        throw new UnsupportedOperationException("NA tohle se dnes podivame :)");
    }

    /**
     * Vrátí novou, náhodnou kostku, náhodně otočenou.
     * @return Bová instance kostky.
     */
    private Piece getNewPiece() {
        Random r = new Random();
        Piece result = new Piece(Pattern.getPattern(r.nextInt(7)));//TODO
        result.setOrientation(Orientation.getOrientation(r.nextInt(4)));
        return result;
    }

    /**
     * Vykreslí kostku na zadaných souřadnicích
     * @param p kostka k vykreslení
     * @param x
     * @param y 
     */
    private void render(Piece p, int x, int y) {
        //TODO orientace
        if (p == null) {
            p = getNewPiece();
        } else {
            for (int i = 0; i < p.getWidth(); i++) {
                for (int j = 0; j < p.getHeight(); j++) {
                    if (p.getPattern().getBlock()[i][j] == true) {
                        matrix.setBackground(i + p.getX(), j + p.getY(), LightColor.BLACK);
                    }
                }
            }
        }
        p.setX(x);
        p.setY(y);
        for (int i = 0; i < p.getWidth(); i++) {
            for (int j = 0; j < p.getHeight(); j++) {
                if (p.getPattern().getBlock()[i][j] == true) {
                    matrix.setBackground(i + p.getX(), j + p.getY(), LightColor.YELLOW);
                }
            }
        }
    }

    public static void main(String[] args) {
        Main m = new Main(10, 20);
        //m.testPattern(m.getNewPiece().getPattern());
        m.run();
    }
}
