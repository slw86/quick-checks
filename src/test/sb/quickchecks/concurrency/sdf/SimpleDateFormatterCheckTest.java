package sb.quickchecks.concurrency.sdf;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Created by slwk on 05.05.16.
 */
public class SimpleDateFormatterCheckTest {

    public static final String PATTERN = "dd-MM-YYYY hh:mm:ss";
    public static final int THREADS_NUMBER = 20;
    public static final int RANGE = 5000;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(PATTERN);

    @Rule
    public ErrorCollector errorCollector = new ErrorCollector();

    @Test
    public void testConcurrentAccessToSDF() {

        ExecutorService executorService = Executors.newFixedThreadPool(THREADS_NUMBER);

        Map<String, Date> stringDateMap = getMapFilledWithRandomDates(RANGE);



        stringDateMap.forEach(
                (formattedReferenceDate, date) -> executorService.submit(
                        () -> {

                           //assertThat(simpleDateFormat.format(date), is(equalTo("XXX")));
                            errorCollector.checkThat(simpleDateFormat.format(date), is(equalTo("XXX")));
                        })
        );


        executorService.shutdown();
    }


    private Map<String, Date> getMapFilledWithRandomDates(int size) {


        Map<String, Date> dates = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat(PATTERN);
        Random random = new Random();

        IntStream.range(0, size).forEach(
                v -> {
                    Date randomDate = getRandomDate(random);
                    dates.put(sdf.format(randomDate), randomDate);
                }
        );


        return dates;
    }

    private Date getRandomDate(Random rand) {
        return new Date(rand.nextInt(199), rand.nextInt(12), rand.nextInt(31), rand.nextInt(12), rand.nextInt(60), rand.nextInt(60));
    }
}