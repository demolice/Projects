
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Daniil
 */
public class Startup {

    /**
     * @param args the command line arguments
     */
    static long xo;
    static long ao;
    static long bo;

    public static void main(String[] args) {
        // TODO code application logic here
        File input = new File("input.txt");
        File output = new File("output.txt");

        Scanner sc;

        try {
            sc = new Scanner(input);
            PrintWriter w = new PrintWriter(output);

            int tests = sc.nextInt();

            for (int index = 0; index < tests; index++) {
                System.out.printf("Sada %s\n", index);

                int to = sc.nextInt(); // pocet operaci, ktery bude proveden
                int N = sc.nextInt(); // velikost pole cisle
                ao = sc.nextInt();
                bo = sc.nextInt();
                xo = sc.nextInt();
                
                System.out.printf("ao: %s, bo: %s, xo: %s, N: %s\n", ao, bo, xo, N);

                long[] pole = new long[N];

                List<Long> minValues = new ArrayList();
                List<Long> maxValues = new ArrayList();
                List<Long> sumValues = new ArrayList();

                for (int i = 0; i < to; i++) {

                    long t = nextInt() % 3; // druh operace
                    long b = nextInt() % N; // zacatek intervalu
                    long e = nextInt() % N; // konec intervalu
                    if (b > e) {
                        long h = b;
                        b = e;
                        e = h;
                    }

                    long A = nextInt() % N; // nastavovane cislo

//                    Arrays.stream(pole).forEach(num -> System.out.print(num + ", "));
//                    System.out.println("");
                    if (t == 0) {
                        long MIN = Long.MAX_VALUE;
                        long MAX = Long.MIN_VALUE;
                        long SUM = 0;
                        for (int x = (int) b; x <= e; x++) {
                            if (pole[x] < MIN) {
                                MIN = pole[x];
                            }

                            if (pole[x] > MAX) {
                                MAX = pole[x];
                            }

                            SUM += pole[x];
                        }

                        minValues.add(MIN);
                        maxValues.add(MAX);
                        sumValues.add(SUM);

                    } else if (t == 1) {
                        for (int x = (int) b; x <= e; x++) {
                            pole[x] = pole[x] + A;
                        }
                    } else {
                        for (int x = (int) b; x <= e; x++) {
                            pole[x] = A;
                        }
                    }

                    System.out.printf("%s: (%s, %s) [%s]\n", t, b, e, A);
                    System.out.print("Arr: ");
                    Arrays.stream(pole).forEach(num -> System.out.print(num + ", "));
                    System.out.println("");
                }

                long maxXOR = 0;
                if (maxValues.size() > 2) {
                    maxXOR = maxValues.get(0);             
                    for (int i = 1; i < maxValues.size(); i++) {
                        maxXOR ^= maxValues.get(i);
                    }
                } else if (maxValues.size() == 1){
                    maxXOR = maxValues.get(0);
                }
                
                long minXOR = 0;
                if (minValues.size() > 2) {
                    minXOR = minValues.get(0);             
                    for (int i = 1; i < minValues.size(); i++) {
                        minXOR ^= minValues.get(i);
                    }
                } else if (minValues.size() == 1){
                    minXOR = minValues.get(0);
                }

                long sumXOR = 0;
                if (sumValues.size() > 2) {
                    sumXOR = sumValues.get(0);             
                    for (int i = 1; i < sumValues.size(); i++) {
                        sumXOR ^= sumValues.get(i);
                    }
                } else if (sumValues.size() == 1){
                    sumXOR = sumValues.get(0);
                }

                w.print(minXOR + "\n" + maxXOR + "\n" + sumXOR + "\n");

                System.out.printf("XOR: %s, %s, %s \n", minXOR, maxXOR, sumXOR);
                w.flush();

            }

            sc.close();
            w.close();
        } catch (FileNotFoundException e) {

            System.out.println(e);
        }

    }

    private static long nextInt() {
        xo = ((xo * ao + bo) % 1000000007);
        return xo;
    }

}
