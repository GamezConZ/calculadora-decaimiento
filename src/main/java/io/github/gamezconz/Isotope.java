package io.github.gamezconz; 

/**
 * Represents a radioactive isotope with its physical properties.
 */
public class Isotope {
    private String name;
    private double halfLifeMinutes;

    public Isotope(String name, double halfLifeMinutes) {
        this.name = name;
        this.halfLifeMinutes = halfLifeMinutes;
    }

    public String getName() {
        return name;
    }

    public double getHalfLifeMinutes() {
        return halfLifeMinutes;
    }
}