/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package karkulcin.byznys;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Daniil
 */
public class StartUp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        try {
            PrintWriter w = new PrintWriter("out.txt", "UTF-8");

            Scanner sc = new Scanner(new File("in.txt"));

            long houseNumber = sc.nextLong();
            long pos = sc.nextLong();
            long energySpend = 0;
            
            ArrayList<House> openHouses = new ArrayList();
            ArrayList<House> closedHouses = new ArrayList();
            
            for (int i = 0; i < houseNumber; i++) {
                House h = new House(sc.nextLong(), sc.nextLong());
                openHouses.add(h);
            }
            
            

        } catch (Exception e) {
            System.out.println(e);
        }
    }

}

class House {

    private final long x;
    private final long demand;
    private long energySpent;
    
    public House(long x, long demand) {
        this.x = x;
        this.demand = demand;
        this.energySpent = Long.MAX_VALUE;
    }

}
