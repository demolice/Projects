/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fiks.segment.tree;

/**
 *
 * @author Daniil
 */
import java.io.*;
import java.util.Scanner;

public class GFG {

    
    
    private static long nextInt() {
        xo = ((xo * ao + bo) % 1000000007);
        return xo;
    }
    
    public static long ao;
    public static long bo;
    public static long xo;
    
    public static void readFile() {
        File input = new File("input.txt");
        File output = new File("output.txt");

        Scanner sc;
        PrintWriter w;

        try {
            sc = new Scanner(input);
            w = new PrintWriter(output);

            int tests = sc.nextInt();

            for (int iteration = 0; iteration < tests; iteration++) {
                System.out.printf("Sada %s\n", iteration);

                int to = sc.nextInt(); // pocet operaci, ktery bude proveden
                int N = sc.nextInt(); // velikost pole cisle
                ao = sc.nextLong();
                bo = sc.nextLong();
                xo = sc.nextLong();
                
                long[] pole = new long[N];
                
                
                
                for (int index = 0; index < to; index++) {
                    long t = nextInt() % 3; // druh operace
                    long b = nextInt() % N; // zacatek intervalu
                    long e = nextInt() % N; // konec intervalu
                    if (b > e) {
                        long h = b;
                        b = e;
                        e = h;
                    }

                    long A = nextInt() % N; // nastavovane cislo
                    
                    if (t == 0) {
                        
                    } else if (t == 1) {
                        
                    } else if (t == 2) {
                    
                    } else {
                        System.out.printf("chyba, t = %s", t);
                        System.exit(0);
                    }
                }

            }

        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        }

    }
}
