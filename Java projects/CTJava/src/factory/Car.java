/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factory;

/**
 *
 * @author JehoJaVista
 */
public class Car {

    double maxSpeed;
    double acceleration;
    CarBrand brand;
    CarType type;

    /**
     * Vytvoří auto
     *
     * @param maxSpeed maximální rychlost v metrech za sekundu
     * @param acceleration zrychlení v metrech za sekundu na druhou
     * @param brand značka auta
     * @param type typ auta
     */
    public Car(double maxSpeed, double acceleration, CarBrand brand, CarType type) {
        this.maxSpeed = maxSpeed;
        this.acceleration = acceleration;
        this.brand = brand;
        this.type = type;
    }

    /**
     * Vrátí, jestli je auto v argumentu rychlejší. Porovnávají se pouze třídy
     * aut.
     *
     * @param c Auto k porovnání
     * @return true, pokud je rychlejší.
     */
    public boolean isHeFaster(Car c) {
        return brand.ordinal() < c.brand.ordinal();
    }

    /**
     * Vrátí rychlost, v čase t. Auto se rozjíždí a po t sekundách jede nějakou
     * rychlostí.
     *
     * @param t čas v sekundách
     * @return rychlost v metrech za sekundu
     */
    public double getSpeed(double t) {
        double speed = acceleration * t;
        if (speed > maxSpeed) {
            speed = maxSpeed;
        }
        return speed;
    }

    /**
     * Vrátí, za jak dlouho dosáhne maximální rychlosti.
     *
     * @return
     */
    public double getMaxSpeedTime() {
        return maxSpeed / acceleration;
    }

    public boolean isHeReallyFaster(Car c, double distance) {
        double s1 = 0.5 * acceleration * getMaxSpeedTime() * getMaxSpeedTime();
        double t1 = 0;
        if (s1 > distance) {
            t1 = Math.sqrt(2 * distance / acceleration); //s = 1/2 * a * t * t
        } else {
            t1 = getMaxSpeedTime() + (distance - s1) / maxSpeed;// t = S / v
        }
        System.out.println("čas prvního auta: " + t1);
        //////////////
        double s2 = 0.5 * c.acceleration * c.getMaxSpeedTime() * c.getMaxSpeedTime();
        double t2 = 0;
        if (s2 > distance) {
            t2 = Math.sqrt(2 * distance / c.acceleration);
        } else {
            t2 = c.getMaxSpeedTime() + (distance - s2) / c.maxSpeed;
        }
        System.out.println("čas druhého auta: " + t2);
        return t2 < t1;
    }

    public static void main(String[] args) {
        Car c1 = new Car(140, 5000, CarBrand.DODGE, CarType.COMFORT);
        Car c2 = new Car(100, 3, CarBrand.JAGUAR, CarType.COMFORT);

        System.out.println(c1.isHeReallyFaster(c2, 4));
    }

}
