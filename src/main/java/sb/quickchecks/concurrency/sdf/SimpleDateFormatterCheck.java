package sb.quickchecks.concurrency.sdf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;


public class SimpleDateFormatterCheck {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleDateFormatterCheck.class);

    public static final int THREADS_NUMBER = 10;
    public static final int RANGE = 1000;
    public static final String PATTERN = "dd-MM-YYYY hh:mm:ss";


    public static void main(String[] args) {


        Map<String, Date> stringDateMap = getMapFilledWithRandomDates(RANGE);

        runConcurrentFormatting(stringDateMap, THREADS_NUMBER);


    }

    static void runConcurrentFormatting(Map<String, Date> stringDateMap, int threadsNumber) {

        ExecutorService executorService = Executors.newFixedThreadPool(threadsNumber);

        stringDateMap.forEach(
                (s, date) -> executorService.submit(
                        () -> {

                            String formatted = LegacyUtilClassWithManyResponsibilities.validateDateAndDoSomethingElse(date);

                            if (!formatted.equals(s)) {
                                LOGGER.error("java.util.Date instance: {}\n" +
                                                "Date formatted in single thread: {}\n" +
                                                "Date formatted concurrently: {}",
                                        date, s, formatted);
                            }
                        })
        );

        executorService.shutdown();
    }


    static Map<String, Date> getMapFilledWithRandomDates(int range) {

        Random rand = new Random();

        Map<String, Date> dates = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat(PATTERN); //this sdf is only accessible by single thread

        IntStream.range(0, range).forEach(
                v -> {
                    Date random = getRandomDate(rand);
                    dates.put(sdf.format(random), random);
                }
        );

        return dates;
    }

    static Date getRandomDate(Random rand) {
        return new Date(rand.nextInt(199), rand.nextInt(12), rand.nextInt(31), rand.nextInt(12), rand.nextInt(60), rand.nextInt(60));
    }
}

class LegacyUtilClassWithManyResponsibilities {

    /*
     * a lot of methods
     */

    private static SimpleDateFormat dateFormat;

    static {
        dateFormat = new SimpleDateFormat("dd-MM-YYYY hh:mm:ss");
    }

    /*
     * methods...
     */

    public static String validateDateAndDoSomethingElse(Date dateOfSomething) {

        // validate date

        // do some additional magic

        // finally - format date... maybe for GUI?
        return dateFormat.format(dateOfSomething);
    }

    /*
     * methods again...
     */
}