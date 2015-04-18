package ca.csf.calculatorgs;

/**
 * Created by Vicente on 3/17/2015.
 */
public class CalculatorTrigo {

    public static boolean isRadianTrigo = false;

    public static double calculate(String equation) throws Exception {

        String[] equations = equation.split(" ");

        if (equations.length == 2 ) {
            String trigonometricOperation = equations[0];
            String value = equations[1];

            if (trigonometricOperation.equals(AppConstants.SIN_TRIGO.trim())) {
                return CalculatorTrigo.sin(Double.valueOf(value));
            } else if (trigonometricOperation.equals(AppConstants.COS_TRIGO.trim())) {
                return CalculatorTrigo.cos(Double.valueOf(value));
            } if (trigonometricOperation.equals(AppConstants.TAN_TRIGO.trim())) {
                return CalculatorTrigo.tan(Double.valueOf(value));
            }
        } else {
            throw(new Exception("Calcul trigonométrique invalide"));
        }

        return 0;
    }

    public static double sin(double value) {
        double val = (CalculatorTrigo.isRadianTrigo)?value:CalculatorTrigo.toRadiansValue(value);
        return Math.sin(val);
    }

    public static double cos(double value) {
        double val = (CalculatorTrigo.isRadianTrigo)?value:CalculatorTrigo.toRadiansValue(value);
        return Math.cos(val);
    }

    public static double tan(double value) {
        double val = (CalculatorTrigo.isRadianTrigo)?value:CalculatorTrigo.toRadiansValue(value);
        return Math.tan(val);
    }

    private static double toRadiansValue(double degree) {
        return Math.toRadians(degree);
    }

    private static double toDegreeValue(double radian) {
        return Math.toDegrees(radian);
    }

}
