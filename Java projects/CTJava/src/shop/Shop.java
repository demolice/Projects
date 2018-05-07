/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shop;

import java.util.ArrayList;
import java.util.List;
import shop.builder.MasterBuilder;


/**
 *
 * @author JehoJaVista
 */
public class Shop {
    /*CahsDesk - pokladna
        Goods - zboží
        Trail - vozík
        Shelf - regál
    
    Customer - zákazník - Trail, Cash
    */
    
    List<Customer> customers = new ArrayList<>();

    public Shop() {
        MasterBuilder mb = new MasterBuilder();
        mb.loadAllCustomers();
        customers = mb.getCustomers();
    }
    
    
    public static void main(String[] args) {
        Shop s = new Shop();
        
        Goods mleko = new Goods(20, "mleko");
        Goods jablko = new Goods(6, "jablko");
        Goods pomeranc = new Goods(7, "pomeranč");
        GoodsPack pomerancPack1 = new GoodsPack(pomeranc,4);
        GoodsPack pomerancPack2 = new GoodsPack(pomeranc);
        pomerancPack2.setAmount(10);
        
        Shelf oragesShelf = new Shelf(pomeranc, 500);
        oragesShelf.addToShelf(10);
        System.out.println("V regalu: "+oragesShelf.getAmountStored());
        //oragesShelf.addToShelf(pomerancPack2);
        System.out.println("V regalu: "+oragesShelf.getAmountStored());
        System.out.println("Skladuje se: "+oragesShelf.getWhatToStore().getName());
        
        System.out.println("Jmeno: "+jablko.getName()+", cena: "+jablko.getPrice());
        
        ShoppingList seznamHonzy = new ShoppingList();
        seznamHonzy.addToList(new GoodsPack(mleko, 3));
        seznamHonzy.addToList(pomerancPack1);
        seznamHonzy.addToList(pomerancPack2);
        System.out.println("Obednano "+seznamHonzy.howManyOrdered(pomeranc)+" pomerancu.");
        seznamHonzy.removeFromList(pomeranc);
        System.out.println("Obednano "+seznamHonzy.howManyOrdered(pomeranc)+" pomerancu.");
        
        Trail t = new Trail();
        Customer honza = s.customers.get(0); //new Customer(100, "Honza",seznamHonzy);
        honza.setTrail(t);
        honza.getTrail().addGoods(pomerancPack1);
        honza.getTrail().addGoods(pomerancPack2);
        System.out.println(honza.getTrail().getStoredGoods());
        System.out.println("Odebráno z vozíku: "+honza.getTrail().getGoods().getAmount()+" pomerančů.");
        System.out.println(honza.getTrail().getStoredGoods());
        
        System.out.println("DDÚ:");
        honza.takeFromShelf(oragesShelf, pomeranc);
        
        
    }
}
