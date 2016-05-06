package sb.quickchecks.concurrency.sdf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;


public class SimpleDateFormatterCheck {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleDateFormatterCheck.class);

    public static final int THREADS_NUMBER = 10;
    public static final int RANGE = 1000;
    public static final String PATTERN = "dd-MM-YYYY hh:mm:ss";



    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(THREADS_NUMBER);


        Map<String, Date> stringDateMap = getMapFilledWithRandomDates(RANGE);

        runMultithreadedFormatting(executorService, stringDateMap);


        executorService.shutdown();

    }

    private static void runMultithreadedFormatting(ExecutorService executorService, Map<String, Date> stringDateMap) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(PATTERN);

        stringDateMap.forEach(
                (s, date) -> executorService.submit(
                        () -> {

                            String formatted = simpleDateFormat.format(date);

                            if(!formatted.equals(s)) {
                                LOGGER.error("Preformatted date: {} Formatted: {}  Date: {}", s, formatted, date);
                            }
                        })
        );
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
