/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shop.builder;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import shop.Customer;
import shop.Goods;
import shop.GoodsPack;
import shop.ShoppingList;

/**
 *
 * @author JehoJaVista
 */
public class MasterBuilder {

    List<Customer> customers = new ArrayList<>();

    /**
     * Načte všechny zákazníky do listu customers. Uživatel musí zadat do
     * konzole počet zákazníků
     */
    public void loadAllCustomers() {
        Integer customersCount = null;
        Scanner sc = new Scanner(System.in);
        System.out.println("Načítám zákazníky. Zadej počet zákazníků");
        while (customersCount == null) {
            try {
                customersCount = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Chyba, zadej počet (int) znova!");
                sc = new Scanner(System.in);
            }
        }
        for (int i = 0; i < customersCount; i++) {
            System.out.println("Načítám " + (i + 1) + " zákazníka z " + customersCount);
            customers.add(createCustomer());
        }
    }

    /**
     * Načte zákazníka pomocí konzole. Uživatel musí zadat jméno a hotovost
     * zákazníka.
     *
     * @return nový zákazník
     */
    public Customer createCustomer() {
        String name = ShopScanner.load("Zadej jméno zákazníka", "");
        Integer cash = ShopScanner.load("Zadej cash zákazníka", 0);
        return new Customer(cash, name, new ShoppingList());
    }

    /**
     * Vytvoří nákupní seznam pomocí konzole. Uživatel musí zadat pro každý druh
     * zboží jeho množství. Příklad: metoda vypíše do konzole "Zadej množství
     * pro banán" a uživatel zadá např. 5. Při další iteraci listu metoda vypíše
     * "Zadej množství pro kaktus" a uživatel zadá např. 3463.
     *
     * @param goodsTypes list všech typů zboží, které budou v nákupním seznamu
     * @return nákupní seznam
     */
    public ShoppingList createShoppingList(List<Goods> goodsTypes) {
        /*for(int i = 0; i< goodsTypes.size();i++){
            goodsTypes.get(i).getName();
        }*/
        ShoppingList sl = new ShoppingList();
        for (Goods goodsFromList : goodsTypes) {
            sl.addToList(new GoodsPack(goodsFromList, ShopScanner.load("Zadej množství pro "+goodsFromList.getName(), 0)));
        }
        return sl;
    }
    
    /**
     * Načte z konzole zboží. Nutné nejdříve zadat počet vstupů.
     * @return List Všech zboží.
     */
    public List<Goods> getAllGoods(){
        List<Goods> l = new ArrayList<>();
        int size = ShopScanner.load("Zadej počet druhů zboží", 0);
        for(int i = 0;i < size;i++){
            String name = ShopScanner.load("Zadej název zboží", "");
            int price = ShopScanner.load("Zadej cenu zboží", 0);
            l.add(new Goods(price, name));
        }
        return l;
    }

    /**
     * Vrátí seznam všech načtených zákazníků
     *
     * @return list zákazníků
     */
    public List<Customer> getCustomers() {
        return customers;
    }

    public static void main(String[] args) {
        MasterBuilder builder = new MasterBuilder();
        //builder.loadAllCustomers();
        /*List<Goods> goodsTypes = new ArrayList<>();
        goodsTypes.add(new Goods(20, "Cudlik"));
        goodsTypes.add(new Goods(50, "Bible"));
        goodsTypes.add(new Goods(10, "CD"));
       */
        List<Goods> goodses = builder.getAllGoods();
        builder.createShoppingList(goodses);
        

    }
}
