package ca.csf.calculatorgs;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * Created by Vicente on 4/8/2015.
 */
public class BigDecimalCalculator {

    private static MathContext getMathContext() {
        return new MathContext(15, RoundingMode.FLOOR);
    }

    public static String pow(String s1, String s2) {
        BigDecimal b1 = new BigDecimal(s1);
        BigDecimal b2 = new BigDecimal(s2);
        return b1.pow(b2.intValue(), getMathContext()).toString();
    }

    public static String divide(String s1, String s2) {
        BigDecimal b1 = new BigDecimal(s1);
        BigDecimal b2 = new BigDecimal(s2);
        return b1.divide(b2, getMathContext()).toString();
    }

    public static String modulo(String s1, String s2) {
        BigDecimal b1 = new BigDecimal(s1);
        BigDecimal b2 = new BigDecimal(s2);
        return b1.remainder(b2, getMathContext()).toString();
    }

    public static String multiply(String s1, String s2) {
        BigDecimal b1 = new BigDecimal(s1);
        BigDecimal b2 = new BigDecimal(s2);
        return b1.multiply(b2, getMathContext()).toString();
    }

    public static String subtract(String s1, String s2) {
        BigDecimal b1 = new BigDecimal(s1);
        BigDecimal b2 = new BigDecimal(s2);
        return b1.subtract(b2, getMathContext()).toString();
    }

    public static String add(String s1, String s2) {
        BigDecimal b1 = new BigDecimal(s1);
        BigDecimal b2 = new BigDecimal(s2);
        return b1.add(b2, getMathContext()).toString();
    }






}
