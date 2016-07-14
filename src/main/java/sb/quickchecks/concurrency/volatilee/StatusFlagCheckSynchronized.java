package sb.quickchecks.concurrency.volatilee;

/**
 * Created by slwk on 13.07.16.
 */
public class StatusFlagCheckSynchronized {

    static boolean statusFlagCompleted = false;

    public static void main(String[] args) {

        new Thread(() -> {
            sleep(3000);
            System.out.println(Thread.currentThread().getName()+" has just completed");
            setStatusFlagCompleted(true);
        }).start();

        new Thread(() -> {

            do {
                sleep(1000);
                System.out.println("Waiting for changing status flag to completed "+statusFlagCompleted);
            } while (!isStatusFlagCompleted());

            System.out.println("Status flag changed");

        }).start();

    }

    static synchronized boolean isStatusFlagCompleted() {
        return statusFlagCompleted;
    }

    static synchronized void setStatusFlagCompleted(boolean statusFlagCompleted) {
        StatusFlagCheckSynchronized.statusFlagCompleted = statusFlagCompleted;
    }

    static void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
