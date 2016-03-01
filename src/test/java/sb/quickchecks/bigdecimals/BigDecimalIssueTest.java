package sb.quickchecks.bigdecimals;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Supplier;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;
import static org.junit.Assert.*;
import static sb.quickchecks.bigdecimals.BigDecimalIssue.OPTION;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runners.Parameterized.Parameter;

@RunWith(Parameterized.class)
public class BigDecimalIssueTest {

    private final BigDecimalIssue decimalIssue = new BigDecimalIssue();

    @Parameters(name = "Run: {index} with parameters: first={0}, second={1}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
            {null, null},
            {BigDecimal.TEN, null},
            {null, BigDecimal.TEN},
            {BigDecimal.TEN, BigDecimal.TEN}
        });
    }

    @Parameter
    public BigDecimal firstParameter;

    @Parameter(value = 1)
    public BigDecimal secondParameter;

    @Test
    public void testIfRefactoringWasDoneDiligently() {

        OPTION beforeRefactoring = getOption(() -> new BigDecimal(0.0));
        OPTION afterRefactoring = getOption(() -> BigDecimal.valueOf(0.0));

        assertThat(beforeRefactoring, is(equalTo(afterRefactoring)));

    }

    private OPTION getOption(Supplier<BigDecimal> zeroSupplier) {
        OPTION option = decimalIssue.doSomethingExtremelyImportant(firstParameter, secondParameter, zeroSupplier);

        return option;
    }
    

}

