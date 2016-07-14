package sb.quickchecks.concurrency.volatilee;

/**
 * Created by slwk on 14.07.16.
 */
public class ReferenceVolatileCheck {

    static class MyObject {
        boolean isReadyToFire;
    }

    static volatile MyObject myObject = new MyObject();

    public static void main(String[] args) {

        new Thread(() -> {
            sleep(1000);
            myObject.isReadyToFire = true;
            System.out.println("READY TO FIRE");
        }).start();

        new Thread(() -> {
            do {
                System.out.println("Waiting...");
            } while (!myObject.isReadyToFire);
            System.out.println("Task completed");
        }).start();

    }

    static void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
