/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shop.builder;

import java.util.Scanner;

/**
 *
 * @author JehoJaVista
 */
public class ShopScanner {

    public static int load(String message, int pointer) {
        Integer requiedValue = null;
        Scanner sc = new Scanner(System.in);
        System.out.println(message);
        while (requiedValue == null) {
            try {
                requiedValue = sc.nextInt();
            } catch (Exception e) {
                System.out.println("Neznámá chyba, opakuj!");
                sc = new Scanner(System.in);
            }
        }
        return requiedValue;
    }

    public static String load(String message, String pointer) {
        String requiedValue = null;
        Scanner sc = new Scanner(System.in);
        System.out.println(message);
        while (requiedValue == null) {
            try {
                requiedValue = sc.next();
            } catch (Exception e) {
                System.out.println("Neznámá chyba, opakuj!");
                sc = new Scanner(System.in);
            }
        }
        return requiedValue;
    }
    
    public static double load(String message, double pointer) {
        Double requiedValue = null;
        Scanner sc = new Scanner(System.in);
        System.out.println(message);
        while (requiedValue == null) {
            try {
                requiedValue = sc.nextDouble();
            } catch (Exception e) {
                System.out.println("Neznámá chyba, opakuj!");
                sc = new Scanner(System.in);
            }
        }
        return requiedValue;
    }

    public static void main(String[] args) {
        ShopScanner.load("Nacitam String", "");

        new ShopScanner().load("Nacitam double", 7d);

        new ShopScanner().load("Nacitam int", 5);
    }
}
