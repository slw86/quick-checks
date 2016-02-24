package sb.quickchecks.bigdecimals;

import java.math.BigDecimal;

/**
 *
 * @author slwk
 */
public class BigDecimalCheck {

private static final BigDecimal bigdecZero = new BigDecimal(0.00);

    private static final BigDecimal[][] parameters = new BigDecimal[][] {
            {null, null},
            {BigDecimal.ONE, null},
            {null, BigDecimal.ONE},
            {BigDecimal.ONE, BigDecimal.ONE}
    };

    public static void main(String[] args) {

        for(BigDecimal[] param : parameters) {
            System.out.println("************************************");
            System.out.println("bigdecSettledDifference: "+param[0]+"  bigdecSettledAmount: "+param[1]);
            withNew(param[0], param[1]);
            withValueOf(param[0], param[1]);
        }

    }

     private static void doSomething(BigDecimal difference, BigDecimal value) {
        
        final BigDecimal zero = new BigDecimal(0.00);

        if (difference == null) {
            difference = new BigDecimal(0.0);
        }
        if (value == null) {
            value = new BigDecimal(0.0);
        }

        if (difference.compareTo(zero) != 0 && !value.equals(BigDecimal.ZERO)) {
            // do variant 1
        } else {
            // do variant 2
        }
    }

    private static void withNew(BigDecimal bigdecSettledDifference, BigDecimal bigdecSettledAmount) {
        System.out.println("---- WITH NEW:");

        if (bigdecSettledDifference == null) {
            bigdecSettledDifference = new BigDecimal(0.0);
        }
        if (bigdecSettledAmount == null) {
            bigdecSettledAmount = new BigDecimal(0.0);
        }

        System.out.println("bigdecSettledDifference.compareTo(bigdecZero): "+ bigdecSettledDifference.compareTo(bigdecZero));
        System.out.println("bigdecSettledAmount.equals(BigDecimal.ZERO): "+ bigdecSettledAmount.equals(BigDecimal.ZERO));

        if (bigdecSettledDifference.compareTo(bigdecZero) != 0 && !bigdecSettledAmount.equals(BigDecimal.ZERO)) {
            System.out.println("RED TAG");
        } else {
            System.out.println("GREEN TAG");
        }
    }

    private static void withValueOf(BigDecimal bigdecSettledDifference, BigDecimal bigdecSettledAmount) {
        System.out.println("---- WITH VALUE OF:");

        if (bigdecSettledDifference == null) {
            bigdecSettledDifference = BigDecimal.valueOf(0.0);
        }
        if (bigdecSettledAmount == null) {
            bigdecSettledAmount = BigDecimal.valueOf(0.0);
        }

        System.out.println("bigdecSettledDifference.compareTo(bigdecZero): "+ bigdecSettledDifference.compareTo(bigdecZero));
        System.out.println("bigdecSettledAmount.equals(BigDecimal.ZERO): "+ bigdecSettledAmount.equals(BigDecimal.ZERO));

        if (bigdecSettledDifference.compareTo(bigdecZero) != 0 && !bigdecSettledAmount.equals(BigDecimal.ZERO)) {
            System.out.println("RED TAG");
        } else {
            System.out.println("GREEN TAG");
        }
    }

    private static void doOption1() {
        System.out.println("Option 1");
    }

    private static void doOption2() {
        System.out.println("Option 2");
    }

}
