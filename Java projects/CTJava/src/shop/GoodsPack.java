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
public class GoodsPack {
    private Goods goodsType;
    private int amount;

    /**
     * Vytvoří zboží. Množství zboží je 0.
     * @param goodsType druh zboží
     */
    public GoodsPack(Goods goodsType) {
        this.goodsType = goodsType;
        amount = 0;
    }
    
    /**
     * Vytvoří zboží.
     * @param goodsType druh zboží
     * @param amount množství zboží. Musí být >=0
     */
    public GoodsPack(Goods goodsType, int amount) {
        this.goodsType = goodsType;
        if(amount<0){
            System.out.println("Nemůže existovat záporný počet kusů zboží!");
            amount = 0;
        }
        this.amount = amount;
    }

    /**
     * Vrátí druh zboží
     * @return Goods
     */
    public Goods getGoodsType() {
        return goodsType;
    }

    /**
     * Vrátí množství v tomto packu
     * @return množství
     */
    public int getAmount() {
        return amount;
    }
    
    /**
     * Nastaví množství. Musí být >=0
     * @param amount 
     */
    public void setAmount(int amount) {
        if(amount>=0){
            this.amount = amount;
        }else{
            System.out.println("Chyba - nelze nastavit množství zboží na záporné!");
        }
        
    }
    
    
}
