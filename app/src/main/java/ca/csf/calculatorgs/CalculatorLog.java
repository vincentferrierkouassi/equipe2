package ca.csf.calculatorgs;

/**
 * Created by Vicente on 3/21/2015.
 */
public class CalculatorLog {

    public static double calculate(String equation) throws Exception {

        String[] equations = equation.split(" ");

        if (equations.length == 2 ) {
            String log = equations[0];
            String valeur = equations[1];

            if (log.equals(AppConstants.LOG.trim())) {
                return CalculatorLog.log(Double.valueOf(valeur));
            } else if (log.equals(AppConstants.LOG10.trim())) {
                return CalculatorLog.log10(Double.valueOf(valeur));
            }
        } else {
            throw(new Exception("Calcul logarithmique invalide"));
        }

        return 0;
    }


    public static double log(double value) {
        return Math.log(value);
    }

    public static double log10(double value) {
        return Math.log10(value);
    }


}
