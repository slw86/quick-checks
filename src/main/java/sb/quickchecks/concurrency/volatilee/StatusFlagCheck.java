package sb.quickchecks.concurrency.volatilee;

import java.util.concurrent.TimeUnit;

/**
 * Created by slwk on 12.07.16.
 */
public class StatusFlagCheck {

    static volatile boolean statusFlagCompleted = false;

    public static void main(String[] args) throws InterruptedException {

        new Thread(() -> {
            sleep(3000);
            System.out.println(Thread.currentThread().getName()+" has just completed");
            statusFlagCompleted = true;
        }).start();

        new Thread(() -> {

            do {
                sleep(1000);
                System.out.println("Waiting for changing status flag to completed "+statusFlagCompleted);
            } while (!statusFlagCompleted);

            System.out.println("Status flag changed");

        }).start();

//        System.out.println("Starting ");
//        TimeUnit.SECONDS.sleep(1);
//
//        System.out.println("After sleep");
//
//        statusFlagCompleted = true;
//
//        System.out.println("Status flag: "+statusFlagCompleted);
    }

    static void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
