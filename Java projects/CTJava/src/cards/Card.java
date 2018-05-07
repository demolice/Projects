/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cards;

/**
 *
 * @author JehoJaVista
 */
public class Card {
    public CardColorType ct;
    public CardValueType cv;

    public Card(CardColorType ct, CardValueType cv) {
        this.ct = ct;
        this.cv = cv;
    }
    
    public static void main(String[] args) {
        Card card1 = new Card(CardColorType.KARA, CardValueType.Q);
        Card card2 = new Card(CardColorType.SRDCE, CardValueType.A);
        
        System.out.println("type: "+(card1.ct == card2.ct));
        System.out.println("value: "+(card1.cv == card2.cv));
        System.out.println("Compare: "+card1.isThisCardHigherThan(card2));
    }
    
    /**
     * Vrátí, jestli je karta v argumentu vyšší. Porovnává jejich indexy
     * ve třídě CardValueType (pomocí .ordinal()).
     * @param toCompare karta k porovnání
     * @return vrátí true, pokud je karta v argumentu vyšší.
     * Pokud jsou si karty rovny, vrátí false.
     */
    public boolean isThisCardHigherThan(Card toCompare){
        return cv.ordinal()>toCompare.cv.ordinal();
    }
}
