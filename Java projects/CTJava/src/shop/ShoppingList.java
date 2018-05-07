/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shop;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author JehoJaVista
 */
public class ShoppingList {
    private List<GoodsPack> shoppingList = new ArrayList<>();
    private List<Goods> goodsTypes = new ArrayList<>();
    
    /**
     * Přidá do nákupního seznamu novou položku. Pokud už tam tem samý druh zboží je, přičte se akorát množství zboží
     * @param gp nová obědnaná položka. počet kusů musí být >0
     */
    public void addToList(GoodsPack gp){
        if(gp.getAmount()==0){
            System.out.println("Nemuzes objednat 0 kusů!");
        }
        else if(goodsTypes.contains(gp.getGoodsType())){
            //TODO
            for(int i = 0;i<shoppingList.size();i++){
                if(shoppingList.get(i).getGoodsType().equals(gp.getGoodsType())){
                    shoppingList.get(i).setAmount(shoppingList.get(i).getAmount()+gp.getAmount());
                }
            }
        } else {
            shoppingList.add(gp);
            goodsTypes.add(gp.getGoodsType());
        }
    }
    
    /**
     * Vrátí náhodnou obědnanou položku ze seznamu.
     * @return náhodná položka. Pokud je seznam prázdný, vrátí null
     */
    public GoodsPack getAnyOrderedGoods(){
        //TODO
        if(shoppingList.isEmpty()) return null;
        return shoppingList.get(0);
    }
    
    /**
     * Kolik je obědnáno tohoto druhu zboží
     * @param g druh dotazovaného zboží
     * @return obědnané množství. Pokud není obědnáno, vrátí 0
     */
    public int howManyOrdered(Goods g){
        if(!goodsTypes.contains(g))return 0;
        //TODO
        for (int i = 0; i < shoppingList.size(); i++) {
            if(shoppingList.get(i).getGoodsType().equals(g)){
                return shoppingList.get(i).getAmount();
            }
        }
        //Prostě to vyhodí chybu :) A měl bys vědet proč, pokud nevíš, klidně se ptej ;)
        throw new IllegalStateException("Neplatný stav třídy! Asynchronizované listy shopingList a goodsTypes!");
    }
    
    /**
     * Odebere zboží ze seznamu. Pokud v seznamu není, nic se nestane.
     * @param gp co k odebrání
     */
    public void removeFromList(GoodsPack gp){
        shoppingList.remove(gp);
        goodsTypes.remove(gp.getGoodsType());
    }
    
    /**
     * Odebere zboží ze seznamu. Pokud v seznamu není, nic se nestane.
     * @param g co k odebrání
     */
    public void removeFromList(Goods g){
        if(goodsTypes.contains(g)){
            for(int i = 0;i<shoppingList.size();i++){
                if(shoppingList.get(i).getGoodsType().equals(g)){
                    shoppingList.remove(i);
                    goodsTypes.remove(g);
                    return;
                }
            }
        }
    }
}
