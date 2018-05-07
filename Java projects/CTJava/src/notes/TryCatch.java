/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and opeún the template in the editor.
 */
package notes;

import shop.Customer;
import shop.ShoppingList;
import shop.Trail;

/**
 *
 * @author JehoJaVista
 */
public class TryCatch {
    
    public static void main(String[] args) {
        Customer c = new Customer(100, "Pepa", new ShoppingList());
        try {
            /*int[] novePole = new int[5];
            System.out.println(6/0);
            
            System.out.println(novePole[7]);
            System.out.println("Ahoj");
             */
            Trail t = c.getTrail();
            System.out.println("Trail: " + t);
            System.out.println(t.getStoredGoods());
            System.out.println("main doběhla");
        } catch (NullPointerException e) {
            System.out.println("chyba null");
        }

    }
}
