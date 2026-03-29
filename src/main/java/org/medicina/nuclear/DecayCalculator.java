package org.medicina.nuclear;

/**
 * Handles the mathematical operations for radioactive decay and volume calculations.
 */
public class DecayCalculator {

    /**
     * Calculates the exact volume required at time zero (V0) to achieve the target dose.
     */
    public static double calculateInitialVolume(double targetDose, double initialActivity, double totalVolume) {
        double concentration = initialActivity / totalVolume;
        return targetDose / concentration;
    }

    /**
     * Calculates the decay time required to reach a specific target volume for the target dose.
     */
    public static double calculateDecayMinutes(Isotope isotope, double initialActivity, double targetDose, double totalVolume, double targetVolume) {
        
        // 1. Calculate the required total activity remaining in the syringe for the target volume
        double requiredTotalActivity = (targetDose * totalVolume) / targetVolume;
        
        // Safety check: If the required activity is greater than the initial activity,
        // the required volume has already been surpassed. We return 0.
        if (requiredTotalActivity >= initialActivity) {
            return 0.0;
        }

        // 2. Calculate the decay constant (lambda)
        double decayConstant = Math.log(2) / isotope.getHalfLifeMinutes();
        
        // 3. Isolate time (t) from the general radioactive decay equation
        double timeInMinutes = (1 / decayConstant) * Math.log(initialActivity / requiredTotalActivity);
        
        return timeInMinutes;
    }
}