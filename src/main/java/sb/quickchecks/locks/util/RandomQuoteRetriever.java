package sb.quickchecks.locks.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by slwk on 29.03.16.
 */
public class RandomQuoteRetriever {

    private static final Logger LOGGER = LoggerFactory.getLogger(RandomQuoteRetriever.class);
    private static final Pattern PATTERN = Pattern.compile("(.*)(joke\":)(\\s*\")(.*)(\",.*)");
    public static final String URL = "http://api.icndb.com/jokes/random";

    private final Random RANDOM = new Random();
    private final Map<String, Integer> STATISTICS = new ConcurrentHashMap<>();
    private final CountDownLatch LATCH;

    public RandomQuoteRetriever(CountDownLatch latch) {
        LATCH = latch;
    }

    public void printRandomQuote() {


        STATISTICS.putIfAbsent(Thread.currentThread().getName(), 0);

//        int val = STATISTICS.get(Thread.currentThread().getName());
//        STATISTICS.put(Thread.currentThread().getName(),++val);
//        LOGGER.debug("{} = {}",Thread.currentThread().getName(),val);

        int x = STATISTICS.computeIfPresent(Thread.currentThread().getName(), (k,v) -> ++v);

        try {
            URL url = new URL(URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;

            while ((output = br.readLine()) != null) {
                LOGGER.debug("Thread {}, tells a joke: {}",Thread.currentThread().getName(),extractJokeString(output));
                Thread.sleep(500+RANDOM.nextInt(1000));
            }

            conn.disconnect();
        } catch (IOException  | InterruptedException e) {
            LOGGER.error("{}",e);
        }
    }

    public void printStatistics() {
        try {
            LATCH.await();
        } catch (InterruptedException e) {
            LOGGER.error("{}",e);
        }
        STATISTICS.forEach((k,v) -> LOGGER.debug("Thread: {} told joke: {} times",k,v));
    }

    private String extractJokeString(String input) {
        Matcher matcher = PATTERN.matcher(input);
        LOGGER.trace("mathces {}",matcher.matches());
        return matcher.group(4);
    }


}
