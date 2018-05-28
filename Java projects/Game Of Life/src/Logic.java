/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Daniil
 */
class Logic {
    public void play() {
        
        boolean update[][] = new boolean[50][50];
        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 50; j++) {
                update[i][j] = CellManager.getLife(i, j);
            }
        }
        
        
        
        //boolean update[][] = CellManager.getGrid().clone();
        
        for (int x = 1; x < Main.WIDTH_COUNT - 1; x++) {
            for (int y = 1; y < Main.HEIGH_COUNT - 1; y++) {

                int around = 0;

                for (int i = x - 1; i <= x + 1; i++) {
                    for (int j = y - 1; j <= y + 1; j++) {
                        if (CellManager.getLife(i, j) == true) {
                            around++;
                        }
                    }
                }

                if (CellManager.getLife(x, y)) {
                    around--;

                    update[x][y] = true;

                    System.out.println(around);

                    if (around <= 1) {
                        update[x][y] = false;
                        //System.out.println(x + " " + y + "nastave na false <= 1");
                    }

                    if (around >= 4) {
                        update[x][y] = false;
                        //System.out.println(x + " " + y + "nastave na false 4");
                    }
                } else if (around == 3) {
                    update[x][y] = true;
                }

            }
        }
        System.out.println("Finish");
        CellManager.setNewGrid(update);
    }

    public void changeCell(int x, int y) {
        int nx = (int) (x / Main.BOX_SIZE);
        int ny = (int) (y / Main.BOX_SIZE);

        CellManager.turnLife(nx, ny);
        //CellManager.setLife(nx, ny);
        System.out.println("Changed: " + nx + " " + ny);
        //CellManager.write();
    }
}
