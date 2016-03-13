package sb.quickchecks.bigdecimals;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
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

        if (difference.compareTo(BigDecimal.ZERO) != 0 && value.compareTo(BigDecimal.ZERO) != 0) {
            return OPTION.GOOD;
        } else {
            return OPTION.EVEN_BETTER;
        }
    }

    public enum OPTION {

        GOOD, EVEN_BETTER;
    }

    public static void main(String[] args) {

//        System.out.println(new BigDecimal(0.0).equals(BigDecimal.ZERO));
//        System.out.println(BigDecimal.valueOf(0.0).equals(BigDecimal.ZERO));
//        System.out.println(new BigDecimal(0.0).equals(BigDecimal.valueOf(0.0)));
//        System.out.println(new BigDecimal(0.0).equals(new BigDecimal("0.0")));
//        System.out.println(new BigDecimal(0.0).setScale(1).equals(new BigDecimal("0.0")));

        System.out.println(new BigDecimal("0.0").scale());
        System.out.println(new BigDecimal(0.1).scale());
//        System.out.println(new BigDecimal(0.0).setScale(-10).scale());
//        System.out.println(new BigDecimal(0.1).scale());
//        System.out.println(new BigDecimal(0.0).compareTo(new BigDecimal("0.0")) == 0);
        
//        System.out.println(new BigDecimal(0.0).equals(BigDecimal.ZERO));
//        System.out.println(BigDecimal.valueOf(0.0).equals(BigDecimal.ZERO));
//        System.out.println(new BigDecimal(0.0).compareTo(BigDecimal.ZERO) == 0);
//        System.out.println(BigDecimal.valueOf(0.0).compareTo(BigDecimal.ZERO) == 0);
//
//        
//        System.out.println(addElementsAndGetSize(new HashSet<>(), new BigDecimal(0.0), BigDecimal.valueOf(0.0)));
//        System.out.println(addElementsAndGetSize(new TreeSet<>(), new BigDecimal(0.0), BigDecimal.valueOf(0.0)));
    }
    

    private static void testHashSet() {
        Set<BigDecimal> bigDecimalSet = new HashSet<>();
        bigDecimalSet.add(new BigDecimal(0.0));
        bigDecimalSet.add(BigDecimal.valueOf(0.0));
        System.out.println(bigDecimalSet.size());
    }
    
    private static void testTreeSet() {
        Set<BigDecimal> bigDecimalSet = new TreeSet<>();
        bigDecimalSet.add(new BigDecimal(0.0));
        bigDecimalSet.add(BigDecimal.valueOf(0.0));
        System.out.println(bigDecimalSet.size());
    }
    
    private static int addElementsAndGetSize(Set<BigDecimal> set, BigDecimal... elements) {
        for(BigDecimal elem : elements) {
            set.add(elem);
        }
        return set.size();
    }
}
