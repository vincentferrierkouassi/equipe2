package ca.csf.calculatorgs;

/**
 * Created by Vicente on 3/17/2015.
 */
public class CalculatorTrigo {

    public static double calculer(String equation) throws Exception {

        String[] equations = equation.split(" ");

        if (equations.length == 2 ) {
            String trigo = equations[0];
            String valeur = equations[1];

            if (trigo.equals(AppConstants.SIN_TRIGO.trim())) {
                return CalculatorTrigo.sin(Double.valueOf(valeur));
            } else if (trigo.equals(AppConstants.COS_TRIGO.trim())) {
                return CalculatorTrigo.cos(Double.valueOf(valeur));
            } if (trigo.equals(AppConstants.TAN_TRIGO.trim())) {
                return CalculatorTrigo.tan(Double.valueOf(valeur));
            }
        } else {
            throw(new Exception("Calcul trigonométrique invalide"));
        }

        return 0;
    }

    public static double sin(double degree) {
        return Math.sin(CalculatorTrigo.enRadians(degree));
    }

    public static double cos(double degree) {
        return Math.cos(CalculatorTrigo.enRadians(degree));
    }

    public static double tan(double degree) {
        return Math.tan(CalculatorTrigo.enRadians(degree));
    }

    private static double enRadians(double degree) {
        return Math.toRadians(degree);
    }

    private static double enDegree(double radian) {
        return Math.toDegrees(radian);
    }

}
