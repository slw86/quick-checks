package sb.quickchecks.bigdecimals;

import java.math.BigDecimal;
import java.util.function.Supplier;

public class BigDecimalIssue {

    public OPTION doSomethingExtremelyImportant(BigDecimal difference, BigDecimal value, Supplier<BigDecimal> bigDecimalZeroSupplier) {

        final BigDecimal zero = new BigDecimal(0.00);

        if (difference == null) {
            difference = bigDecimalZeroSupplier.get();
        }
        if (value == null) {
            value = bigDecimalZeroSupplier.get();
        }

        if (difference.compareTo(zero) != 0 && !value.equals(BigDecimal.ZERO)) {
            return OPTION.GOOD;
        } else {
            return OPTION.EVEN_BETTER;
        }
    }

    public enum OPTION {

        GOOD, EVEN_BETTER;
    }



    public static void main(String[] args) {
        
        System.out.println(new BigDecimal(0.0).equals(BigDecimal.ZERO));
        System.out.println(BigDecimal.valueOf(0.0).equals(BigDecimal.ZERO));
        System.out.println(new BigDecimal(0.0).equals(BigDecimal.valueOf(0.0)));
        System.out.println(new BigDecimal(0.0).equals(new BigDecimal("0.0")));
    }
}