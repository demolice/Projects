/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shop;

import java.util.List;
import java.util.Stack;

/**
 *
 * @author JehoJaVista
 */
public class Trail {
    private List<GoodsPack> storedGoods = new Stack<>();
    
    /**
     * Vrátí všechny druhy a kusy zboží ve vozíku.
     * @return String všech zboží ve vozíku 
     */
    public String getStoredGoods(){
        String s="";
        for(int i=0; i<storedGoods.size();i++){
            s+=storedGoods.get(i).getGoodsType().getName()+" "
                    +storedGoods.get(i).getAmount()+"x, ";
        }
        s = s.substring(0,s.length()-2);
        return s;
    }
    
    /**
     * Přidá do vozíku zboží. 
     * @param gp zboží. Nesmí být null.
     */
    public void addGoods(GoodsPack gp){
        if(gp!=null){
            storedGoods.add(gp);
        }else{
            System.out.println("Nemůžu do vozíku vložit null!");
        }
        
    }
    
    /**
     * Odebírá zboží z vozíku. Může vždy odebrat pouze z vrchu.
     * @return zboží
     */
    public GoodsPack getGoods(){
        if(storedGoods.isEmpty())return null;
        return storedGoods.remove(storedGoods.size()-1);
    }
}
