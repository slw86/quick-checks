package sb.quickchecks.concurrency.sdf;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

/**
 * Created by sbalcerek on 2016-05-05.
 */
public class SimpleDateFormatterCheck {

    public static final int THREADS_NUMBER = 10;
    public static final int RANGE = 1000;

    public static final Random rand = new Random();


    public static final String PATTERN = "dd-MM-YYYY hh:mm:ss";
    static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(PATTERN);

    public static void main(String[] args) {


        ExecutorService executorService = Executors.newFixedThreadPool(THREADS_NUMBER);


        Map<String, Date> stringDateMap = initDates(RANGE);

        stringDateMap.forEach(
                (s, date) -> executorService.submit(
                        () -> {

                            String formatted = simpleDateFormat.format(date);

                            if(!formatted.equals(s)) {
                                System.out.println("---- Preformatted date: "+s+" Formatted: "+formatted+"  Date: "+date);
                            }
                        })
        );


        executorService.shutdown();

    }

    static Map<String, Date> initDates(int range) {


        Map<String, Date> dates = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat(PATTERN);

        IntStream.range(0, range).forEach(
                v -> {
                    Date random = getRandomDate();
                    dates.put(sdf.format(random), random);
                }
        );


        return dates;
    }

    static Date getRandomDate() {
        return new Date(rand.nextInt(199), rand.nextInt(12), rand.nextInt(31), rand.nextInt(12), rand.nextInt(60), rand.nextInt(60));
    }
}
