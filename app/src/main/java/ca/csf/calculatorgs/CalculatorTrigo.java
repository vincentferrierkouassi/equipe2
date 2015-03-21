package ca.csf.calculatorgs;

/**
 * Created by Vicente on 3/17/2015.
 */
public class CalculatorTrigo {

    public static boolean isRadianTrigo = false;

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

    public static double sin(double valeur) {
        double val = (CalculatorTrigo.isRadianTrigo)?valeur:CalculatorTrigo.enRadians(valeur);
        return Math.sin(val);
    }

    public static double cos(double valeur) {
        double val = (CalculatorTrigo.isRadianTrigo)?valeur:CalculatorTrigo.enRadians(valeur);
        return Math.cos(val);
    }

    public static double tan(double valeur) {
        double val = (CalculatorTrigo.isRadianTrigo)?valeur:CalculatorTrigo.enRadians(valeur);
        return Math.tan(val);
    }

    private static double enRadians(double degree) {
        return Math.toRadians(degree);
    }

    private static double enDegree(double radian) {
        return Math.toDegrees(radian);
    }

}
