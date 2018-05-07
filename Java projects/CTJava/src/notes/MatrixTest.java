/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notes;

import cz.gyarab.util.Utils;
import cz.gyarab.util.light.LightColor;
import cz.gyarab.util.light.Matrix;

/**
 *
 * @author JehoJaVista
 */
public class MatrixTest {

    public static void main(String[] args) {
        Matrix m = Matrix.createMatrix(20, 20);
        m.showWindow();
        while (true) {
            for (int o = 0; o < 6; o++) {
                for (int i = 0; i < 20; i++) {
                    for (int j = 0; j < 20; j++) {
                        if (is(i, j, o)) {
                            m.setBackground(i, j, LightColor.RED);
                        } else {
                            m.setBackground(i, j, LightColor.GREEN);    
                        }
                    }
                    Utils.sleep(200);
                }
            }

        }
    }

    private static boolean is(int i, int j, int iteration) {
        switch (iteration) {
            case 0:
                return (i + j) % 3 == 0;
            case 2:
                return ((i * j) % 3 + i + j) % 2 == 0;
            case 4:
                return (i * j) % 2 + (i * j) % 3 == 0;
            default:
                return false;
        }

    }
}
