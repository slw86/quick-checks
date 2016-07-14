package sb.quickchecks.concurrency.volatilee;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by slwk on 13.07.16.
 */
public class VolatileCounterCheck {

    static volatile int counter = 0;

    static void increment() {
        counter++;
    }

    static Set<Integer> valuesOfCounter = Collections.synchronizedSet(new HashSet<>());

    public static void main(String[] args) {

        getThread().start();
        getThread().start();
        getThread().start();
    }

    static Thread getThread() {
        return new Thread(() -> {
            while (true) {
                increment();
                if (!valuesOfCounter.add(counter)) {
                    throw new RuntimeException("ELement alread exists... " + counter);
                }
            }
        });
    }

    static void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
