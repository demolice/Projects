
import java.awt.Color;
import java.util.Random;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Logic sturcture of the game
 *
 * @author Daniil
 */
class Logic {

    private static boolean[][] grid; // Contains info about used blocks
    private static Color[][] gridColors; // Grid that contains info about color of used blocks

    private Particle p; //The current particle that is being used

    private static Random r;

    private final char[] types = {'S', 'L', 'O', 'T', 'I', 'Z'};

    private static double points = 0;

    private static boolean timer = false;

    Logic() {
        this.r = new Random();
        this.grid = new boolean[Main.WCOUNT][Main.HCOUNT];
        this.gridColors = new Color[Main.WCOUNT][Main.HCOUNT];
        this.p = generateParticle();
    }

    /**
     * Makes one step in the game
     */
    public void tick() {
        endGame();
        checkFullRow();

        if (!collision()) {
            p.setY(p.getY() + 1);
        } else {
            death();
            p = generateParticle();
            
            ;
            
            if (generatedOnTop()) {
                killGame();
            }
        }
    }
    
    private boolean generatedOnTop() {
        for (int x = 0; x < p.getT().getShape().length; x++) {
            for (int y = 0; y < p.getT().getShape()[x].length; y++) {
                if (grid[p.getX() + x][p.getY() + y] && p.getT().getShape()[x][y]){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Ends current particle and sets new
     */
    private void death() {

        boolean[][] shape = p.getT().getShape();

        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                if (shape[i][j]) {
                    grid[p.getX() + i][p.getY() + j] = true;
                    gridColors[p.getX() + i][p.getY() + j] = p.getC();
                }
            }
        }

    }

    /**
     * Checks the collision with lower layer
     *
     * @return true if there is a collision between particle and used boxes
     */
    private boolean collision() {
        for (int i = 0; i < p.getT().getShape().length; i++) {
            for (int j = 0; j < p.getT().getShape()[i].length; j++) {
                if (p.getT().getShape()[i][j]) {

                    if (p.getY() + j < Main.HCOUNT - 1) {
                        if (grid[p.getX() + i][p.getY() + j + 1] == true) {
                            return true;
                        }
                    } else {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * Check if the grid is full and if game shouldn't be ended
     */
    private void endGame() {
        for (int i = 0; i < Main.WCOUNT; i++) {
            if (grid[i][0]) {
                killGame();
            }
        }
    }

    private void killGame() {
        JOptionPane.showMessageDialog(null, "You lose", "End",
                JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }

    /**
     * Checks state of rows and if full row is found, deletes it
     */
    private void checkFullRow() {
        boolean disappear = true;
        for (int i = 0; i < Main.HCOUNT; i++) {
            for (int j = 0; j < Main.WCOUNT; j++) {
                if (!grid[j][i]) {
                    disappear = false;
                }
            }

            if (disappear) {
                for (int j = 0; j < Main.WCOUNT; j++) {
                    grid[j][i] = false;
                }
                moveGrid(i);
            } else {
                disappear = true;
            }
        }
    }

    /**
     * Moves grid down
     *
     * @param row coorinate of disappered row
     */
    private void moveGrid(int row) {
        for (int y = row; y >= 0; y--) {
            for (int x = Main.WCOUNT - 1; x >= 0; x--) {
                if (y < Main.HCOUNT - 1) {
                    //System.out.println(y + " " + x);
                    if (!grid[x][y + 1] && grid[x][y]) {

                        grid[x][y] = false;
                        grid[x][y + 1] = true;
                        gridColors[x][y + 1] = gridColors[x][y];

                    }
                }
            }
        }

    }

    /**
     * Generates new particle and its color
     *
     * @return new particle
     */
    private Particle generateParticle() {
        Particle temp = null;

        char ch = types[r.nextInt(types.length - 1)];

        float h = r.nextFloat();
        float s = 0.9f;
        float b = 1.0f;

        Color c = Color.getHSBColor(h, s, b);

        switch (ch) {
            case 'S':
                temp = new Particle(particleType.S);
                break;
            case 'L':
                temp = new Particle(particleType.L);
                break;
            case 'O':
                temp = new Particle(particleType.O);
                break;
            case 'T':
                temp = new Particle(particleType.T);
                break;
            case 'I':
                temp = new Particle(particleType.I);
                break;
            case 'Z':
                temp = new Particle(particleType.Z);
            default:
                break;
        }

        temp.setC(c);
        
        
        
        return temp;
    }

    /**
     * Moves the particle
     *
     * @param s name of an arrow that is being pressed
     */
    public void rotate(String s) {
        boolean collisionB = false;
        boolean[][] shape = p.getT().getShape();

        switch (s) {
            case "right":

                for (int i = shape.length - 1; i >= 0; i--) {
                    for (int j = shape[i].length - 1; j >= 0; j--) {
                        if (shape[i][j]) {
                            if (p.getX() + i + 1 > Main.WCOUNT - 1) {
                                collisionB = true;
                                break;
                            } else if (grid[p.getX() + i + 1][p.getY() + j]) {
                                collisionB = true;
                                break;
                            }
                        }
                    }
                }
                if (!collisionB) {
                    p.setX(p.getX() + 1);
                }
                break;
            case "left":

                for (int i = 0; i < shape.length; i++) {
                    for (int j = 0; j < shape[i].length; j++) {
                        if (shape[i][j]) {
                            if (p.getX() + i - 1 < 0) {
                                collisionB = true;
                                break;
                            } else if (grid[p.getX() + i - 1][p.getY() + j]) {
                                collisionB = true;
                                break;
                            }
                        }
                    }
                }
                if (!collisionB) {
                    p.setX(p.getX() - 1);
                }
                break;
            case "up":
                rotateParticle();
                break;
            case "down":
                if (!collision()) {
                    p.setY(p.getY() + 1);
                }
                break;

        }
    }

    /**
     * Rotates particle by own axis
     */
    private void rotateParticle() {
        int m = p.getT().getShape().length;
        int n = p.getT().getShape()[0].length;

        int x = p.getX();
        int y = p.getY();

        boolean isOut = false;

        boolean[][] ret = new boolean[n][m];

        for (int r = 0; r < m; r++) {
            for (int k = 0; k < n; k++) {
                ret[k][m - 1 - r] = p.getT().getShape()[r][k];

                if (x + r > Main.WCOUNT - 1 || x + r < 0) {
                    isOut = true;
                    break;
                } else if (y + k > Main.HCOUNT - 1 || y + k < 0) {
                    isOut = true;
                    break;
                } else if (grid[x + r][y + k]) {
                    isOut = true;
                    break;
                }
            }
        }

        if (!isOut) {
            p.getT().setShape(ret);
        }
    }

    /**
     * Returns state of the timer
     *
     * @return if game ended returns true
     */
    public boolean getTimer() {
        return timer;
    }

    public boolean[][] getGrid() {
        return grid;
    }

    public Color[][] getColorGrid() {
        return gridColors;
    }

    public Particle getParticle() {
        return p;
    }
}
