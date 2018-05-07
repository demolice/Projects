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
public class Goods {
    private int price;
    private String name;

    /**
     * Vytvoří zboží s nějakou cenou a nějakým jménem
     * @param price cena
     * @param name název
     */
    public Goods(int price, String name) {
        this.price = price;
        this.name = name;
    }

    /**
     * Vrátí cenu jednoho kusu zboží
     * @return cena
     */
    public int getPrice() {
        return price;
    }

    /**
     * Vrátí název druhu zboží
     * @return název
     */
    public String getName() {
        return name;
    }
    
}
