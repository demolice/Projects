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
public class Shelf {
    private int amountStored = 0;
    private Goods whatToStore;
    private int amountLimit;
    
    /**
     * Vytvoří prázdný regál bez druhu zboží ke skladování.
     */
    public Shelf() {
        whatToStore = null;
        amountLimit = 20;
    }
    
    /**
     * Vytvoří regál s nějakým typem zboží a s jeho uloženým množstvím.
     * @param whatToStore
     * @param amountLimit 
     */
    public Shelf(Goods whatToStore, int amountLimit) {
        this.whatToStore=whatToStore;
        this.amountLimit=amountLimit;
    }
    
    /**
     * Přidá do regálu nějaké množství zboží. Musí umět ale něco skladovat.
     * Pokud by byl regál přeplněný, doplní se jen do maxima.
     * @param amount množství. Musí být >0
     */
    public void addToShelf(int amount){
        if(amount<1)return;
        if(whatToStore == null){
            System.out.println("Neumím nic skladovat!");
            return;
        }
        if(amountLimit>=amount+amountStored){
            amountStored+=amount;
        }else{
            amountStored=amountLimit;
        }
        
    }
    
    /**
     * Doplní regál do maxima. Musí umět něco skladovat.
     */
    public void addToShelf(){
        if(whatToStore == null){
            System.out.println("Neumím nic skladovat!");
            return;
        }
        amountStored=amountLimit;
    }
    
    /**
     * Odebere z regálu nějaké množství zboží. Pokud je ho v regálu málo, odebere všechno co může.
     * @param amount množství k odebrání. Musí být >0
     * @return kolik se toho odebralo
     */
    public int removeFromShelf(int amount){
        if(amount<1)return 0;
        if(amount>amountStored){
            int wasStored = amountStored;
            amountStored=0;
            return wasStored;
        }else{
            amountStored-=amount;
            return amount;
        }
    }

    public Goods getWhatToStore() {
        return whatToStore;
    }

    /**
     * Nastavuje, co se skladuje. Musí bý tale regál prázdný.
     * @param whatToStore druh zboží. Nesmí být null
     * @param newAmountLimit nový limit zboží.
     */
    public void setWhatToStore(Goods whatToStore, int newAmountLimit) {
        if(whatToStore==null)return;
        if(amountStored==0){
            this.whatToStore = whatToStore;
            this.amountLimit = newAmountLimit;
        }else{
            System.out.println("Chyba! Nemůžu změnit typ skladovbaného zboží, když není regál prázdný!");
        }
        
    }

    /**
     * Vrátí počet uloženého množství
     * @return uloženo
     */
    public int getAmountStored() {
        return amountStored;
    }

    /**
     * Vrátí limit množství k uložení
     * @return limit
     */
    public int getAmountLimit() {
        return amountLimit;
    }
    
    
}
