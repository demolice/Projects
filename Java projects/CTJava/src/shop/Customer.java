/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shop;

/**
 *
 * @author JehoJaVista
 */
public class Customer {
    private Trail trail = null;
    private ShoppingList shoppingList;
    private int cash;
    private String name;

    /**
     * Vytvoří zákazníka s nějakými penězi, názvem a nákupním seznamem.
     * @param cash peníze. Musí být >0
     * @param name jméno zákazníka
     * @param sl nákupní seznam. Nesmí být null
     */
    public Customer(int cash, String name, ShoppingList sl) {
        if(sl==null){
            //Závažná chyba, kdy nedovolíme pokračovat ve zpracovávání kódu - vysvětlíme si později
            throw new NullPointerException("Kritická chyba: nemůže být "+name+" bez nákupního seznamu!");
        }
        if(cash<=0){
            System.out.println("Chyba - zákazník je bez peněz!");
            cash = 0;
        }
        this.cash = cash;
        this.name = name;
        this.shoppingList=sl;
    }

    /**
     * Nastavuje vozík. Pokud už zákazník vozík má, nic se nestane.
     * @param t vozík pro zákazníka
     */
    public void setTrail(Trail t) {
        if(this.trail!=null){
            System.out.println("Chyba, "+name+" už má vozík!");
        }else{
            this.trail = t;
        }
    }

    /**
     * Vrátí vozík. Pokud zákazník žádný vozík nemá, vrátí null.
     * @return vozík
     */
    public Trail getTrail() {
        return trail;
    }
    
    /**
     * Odebere vozík zákazníkovi
     */
    public void removeTrail(){
        trail = null;
    }
    
    /**
     * Odebere zboží z regálu a dá si ho do košíku. Musí mít ale nějaký vozík a musí mít tento druh zboží
     * v nákupním seznamu. Pokud podmínka splněna, nakonec si položku z nákupního seznamu škrtne.
     * @param shelf
     * @param goods 
     */
    public void takeFromShelf(Shelf shelf, Goods goods){
        if(trail==null){
            System.out.println("Nemám vozík!");
            return;
        }
        int amount = shoppingList.howManyOrdered(goods);
        if(amount == 0){
            System.out.println("Toto zboží nemám objednané!");
            return;
        }
        int removed = shelf.removeFromShelf(amount);
        if(removed!=amount){
            System.out.println("V regalu nebylo dost zboží.");
        }
        GoodsPack newPack = new GoodsPack(goods, removed);
        trail.addGoods(newPack);
        shoppingList.removeFromList(goods);
    }

    /**
     * Vrátí hotovost zákazníka
     * @return hotovost
     */
    public int getCash() {
        return cash;
    }

    /**
     * Vrátí jméno zákazníka
     * @return jméno
     */
    public String getName() {
        return name;
    }
    
    
    
}
