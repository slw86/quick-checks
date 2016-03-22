package sb.quickchecks.locks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by slwk on 20.03.16.
 */
public class ReentrantLockCheck {

    public static final int SLEEP_TIME = 2000;
    private ReentrantLock lock = new ReentrantLock();
    private static final Logger LOGGER = LoggerFactory.getLogger(ReentrantLockCheck.class);
    private static final Pattern PATTERN = Pattern.compile("(.*)(joke\":)(\\s*\")(.*)(\",.*)");
    private static final Random RANDOM = new Random();
//    private static final Map<String, AtomicInteger> STATISTICS = new ConcurrentHashMap<>();
private static final Map<String, Integer> STATISTICS = new ConcurrentHashMap<>();
    private static final int NUM_OF_THREADS = 10;
    private static final CountDownLatch LATCH = new CountDownLatch(NUM_OF_THREADS);

    public void doSomethingOnSharedResourceUsingReentrantLock() {
        lock.lock();
        try {
            LOGGER.debug("Number of threads waiting: {}",lock.getQueueLength());
            doSomethingImportant();
        } catch (InterruptedException e) {
            LOGGER.error("{}",e);
        } finally {
            LOGGER.debug("Lock unlocked");
            lock.unlock();
        }
    }

    public boolean tryToDoSomethingOnSharedResourceUsingReentrantLock(CountDownLatch countDownLatch) {
        if(lock.tryLock()) {
            try {
                LOGGER.debug("Number of threads waiting: {}", lock.getQueueLength());
                doSomethingImportant();
            } catch (InterruptedException e) {
                LOGGER.error("{}", e);
            } finally {
                lock.unlock();
                LOGGER.debug("Lock unlocked");
            }
            countDownLatch.countDown();
            return true;
        } else {
            LOGGER.debug("Thread {} will do something different because shared resource is being used by another thread", Thread.currentThread().getName());
            return false;
        }
    }

    public boolean tryToDoSomethingOnSharedResourceUsingTimedReentrantLock(CountDownLatch countDownLatch) throws InterruptedException {
        if(lock.tryLock(1L, TimeUnit.SECONDS)) {
            try {
                LOGGER.debug("Number of threads waiting: {}", lock.getQueueLength());
                doSomethingImportant();
            } catch (InterruptedException e) {
                LOGGER.error("{}", e);
            } finally {
                lock.unlock();
                LOGGER.debug("Lock unlocked");
            }
            countDownLatch.countDown();
            return true;
        } else {
            LOGGER.debug("Thread {} will do something different because shared resource is being used by another thread", Thread.currentThread().getName());
            return false;
        }
    }

    public void doSomethingOnSharedResourceUsingIntrinsicLock() {
        synchronized (this) {
            try {
                doSomethingImportant();
            } catch (InterruptedException e) {
                LOGGER.error("{}", e);
            }
        }
    }

    private void doSomethingImportant() throws InterruptedException {
        LOGGER.debug("<<<<<< Thread {} is working on shared resource.",Thread.currentThread().getName());
//        LOGGER.debug("sleeping...");
        Thread.sleep(SLEEP_TIME);
        LOGGER.debug(">>>>>> Thread {} completed its work", Thread.currentThread().getName());
    }

    private static Runnable getRunnable(Consumer<ReentrantLockCheck> lockCheckConsumer, ReentrantLockCheck reentrantLockCheck) {
        return () -> {
            LOGGER.debug("Thread {} attempting to do something on shared resource",Thread.currentThread().getName());
            lockCheckConsumer.accept(reentrantLockCheck);
            LOGGER.debug("Thread {} completed its action", Thread.currentThread().getName());
        };
    }



    public static void main(String[] args) {

        ReentrantLockCheck reentrantLockCheck = new ReentrantLockCheck();


        ExecutorService executorService = getExecutorServiceAndSubmitTasks(reentrantLockCheck);

        executorService.shutdown();


        printStatistics();

    }

    private static void printStatistics() {
        try {
            LATCH.await();
        } catch (InterruptedException e) {
            LOGGER.error("{}",e);
        }
        STATISTICS.forEach((k,v) -> LOGGER.debug("Thread: {} told joke: {} times",k,v));
    }

    private static ExecutorService getExecutorServiceAndSubmitTasks(ReentrantLockCheck reentrantLockCheck) {
        ExecutorService executorService = Executors.newFixedThreadPool(NUM_OF_THREADS);
        for(int i = 0; i <NUM_OF_THREADS; i++) {
            executorService.submit(getRunnable((r) ->
//                    {
//                        while (!r.tryToDoSomethingOnSharedResourceUsingReentrantLock()) {
////                            LOGGER.debug("Thread {} is waiting for resource. Let's do something useful...", Thread.currentThread().getName());
//                            printRandomQuote();
//                        }
//                    }
                    {
                        try {
                            while (!r.tryToDoSomethingOnSharedResourceUsingTimedReentrantLock(LATCH)) {
                                printRandomQuote();
                            }
                        } catch (InterruptedException e) {
                            LOGGER.error("{}",e);
                        }
                    }
//            {
//                r.doSomethingOnSharedResourceUsingReentrantLock();
//            }
//            {
//                r.doSomethingOnSharedResourceUsingIntrinsicLock();
//            }
                    , reentrantLockCheck));
        }
        return executorService;
    }

    private static void printRandomQuote() {

//        int count = STATISTICS.get(Thread.currentThread().getName());
//        STATISTICS.putIfAbsent(Thread.currentThread().getName(),count++);

//        STATISTICS.putIfAbsent(Thread.currentThread().getName(), new AtomicInteger(0));
        STATISTICS.putIfAbsent(Thread.currentThread().getName(), 0);
//        LOGGER.debug("Thread {} INTEGER = {}",Thread.currentThread().getName(),integer);

        int val = STATISTICS.get(Thread.currentThread().getName());
//        val.incrementAndGet();

        STATISTICS.put(Thread.currentThread().getName(),++val);
        LOGGER.debug("{} = {}",Thread.currentThread().getName(),val);

//        int x = STATISTICS.computeIfPresent(Thread.currentThread().getName(), (k,v) -> v+1);
//        LOGGER.debug("Thread {} x = {}",Thread.currentThread().getName(),x);
//        LOGGER.debug("Number of jokes for thread {} is {}",Thread.currentThread().getName(),STATISTICS.get(Thread.currentThread().getName()));

        try {
            URL url = new URL("http://api.icndb.com/jokes/random");
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
                LOGGER.debug("Thread {}, tells joke: {}",Thread.currentThread().getName(),extractJokeString(output));
                Thread.sleep(500+RANDOM.nextInt(1000));
            }

            conn.disconnect();
        } catch (IOException e) {
            LOGGER.error("{}",e);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static String extractJokeString(String input) {
        Matcher matcher = PATTERN.matcher(input);
        LOGGER.trace("mathces {}",matcher.matches());
        return matcher.group(4);
    }
}
