package minimal.sort;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Daniil
 */
public class StartUp {

    static ArrayList<Integer> array;

    public static void main(String[] args) {
        // TODO code application logic here
        fillArray(10);

        System.out.println("Původní array: " + array);

        Sort s = new Sort();

        array = s.sortArray(array);

        System.out.println("Seřazený array: " + array);

        System.out.println("Bylo potřeba " + s.getSteps() + " přesunutí");

    }

    public static void fillArray(int n) {
        array = new ArrayList();

        for (int i = 0; i < n; i++) {
            array.add(i);
        }

        Collections.shuffle(array);        
    }
}

class Sort {

    private int steps = 0;        // Počet kroků potřebných k seřazení řetězce
    ArrayList<Integer> array;     // Řetězec k seřazení
    ArrayList<Integer> lis;       // Pomocný řetězec

    public ArrayList sortArray(ArrayList<Integer> array) {
        this.array = array;

        calculateLongestSubsequence();

        howToSortArray();

        return this.array;
    }

    public int getSteps() {
        return steps;
    }

    private void howToSortArray() {
        if (steps > 0) {

            for (int i = 0; i < array.size(); i++) {
                int fromArray = array.get(i);
                
                if (lis.contains(fromArray)) {
                    continue;
                }

                array.remove(i);
                
                for (int j = array.size() - 1; j >= 0; j--) {

                    if (!lis.contains(array.get(j))) {
                        continue;
                        
                    } else if (fromArray > array.get(j)) {

                        if (j <= array.size() - 1) {
                            array.add(j + 1, fromArray);
                            System.out.println("Prvek " + fromArray + 
                                    " byl posunut za  " + array.get(j));
                        
                        } else {
                            array.add(fromArray);
                            System.out.println(fromArray + " přidnán na konec");
                        }
                        
                        lis.add(fromArray);
                     
                        break;
                    } else if (j == 0) {
                        array.add(0, fromArray);
                        lis.add(fromArray);
                        System.out.println(fromArray + " přidán na začátek.");
                        
                    }                    
                }
                i--;
               
            }
        } else {
            System.out.println("Array je již seřazen.");
        }
    }
    
    // Najde délku nejdelšího seřazeného řetězce jeho poslední prvek
    private void calculateLongestSubsequence() {
        ArrayList<Integer> tail = new ArrayList();

        for (int i = 0; i < array.size(); i++) {
            tail.add(0);
        }

        int lenght = 1;

        tail.set(0, array.get(0));

        for (int i = 1; i < array.size(); i++) {
            if (array.get(i) < tail.get(0)) {
                tail.set(0, array.get(i));

            } else if (array.get(i) > tail.get(lenght - 1)) {
                tail.set(lenght++, array.get(i));

            } else {
                tail.set(ceilIndex(tail, -1, lenght - 1, array.get(i)), array.get(i));

            }
        }

        makeLis(tail, lenght);

        System.out.println("Nejdelší LIS: " + lenght);

        System.out.println("LIS: " + lis);

        steps = array.size() - lenght;
    }

    private int ceilIndex(ArrayList<Integer> list, int l, int r, int key) {
        while (r - l > 1) {
            int m = l + (r - l) / 2;

            if (list.get(m) >= key) {
                r = m;

            } else {
                l = m;

            }
        }

        return r;
    }
    
    // Vytvoří array pouze s seřazenými prvky
    private void makeLis(ArrayList<Integer> a, int s) {
        lis = new ArrayList();
        lis.add(a.get(s - 1));

        for (int i = array.indexOf(a.get(s - 1)); i >= 0; i--) {

            if (array.get(i) < lis.get(lis.size() - 1)) {
                lis.add(array.get(i));

            } else if (lis.size() >= 2) {
                if (array.get(i) < lis.get(lis.size() - 2)) {
                    lis.set(lis.size() - 1, array.get(i));

                }
            }
        }

        for (int i = 0; i < lis.size() / 2; i++) {
            int temp = lis.get(i);
            lis.set(i, lis.get(lis.size() - i - 1));
            lis.set(lis.size() - i - 1, temp);
        }
    }
}
