/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sb.quickchecks.threads;

/**
 *
 * @author slwk
 */
public class ThreadLocalCheck {

    private interface StateSharable {

        public void doSmthImportant();

        public String getGlobalState();
    }

    private static class SharedState implements StateSharable {

        private static ThreadLocal<String> globalState = new ThreadLocal<>();

        public void doSmthImportant() {
            globalState.set(Thread.currentThread().getName() + " state");
        }

        public String getGlobalState() {
            return globalState.get();
        }
    }

    private static class SharedState2 implements StateSharable {

        private static String globalState;

        public void doSmthImportant() {
            globalState = Thread.currentThread().getName() + " state";
        }

        public String getGlobalState() {
            return globalState;
        }
    }

    private static class MyRunnable implements Runnable {

        private StateSharable sharedState;

        public MyRunnable(StateSharable sharedState) {
            this.sharedState = sharedState;
        }

        @Override
        public void run() {
            sharedState.doSmthImportant();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

            System.out.println(Thread.currentThread().getName() + "  :  " + sharedState.getGlobalState());
        }
    }

    public static void main(String[] args) {

        StateSharable sharedState = new SharedState2();

        Thread thread1 = new Thread(new MyRunnable(sharedState), "Thread_1");
        Thread thread2 = new Thread(new MyRunnable(sharedState), "Thread_2");
        Thread thread3 = new Thread(new MyRunnable(sharedState), "Thread_3");

        thread1.start();
        thread2.start();
        thread3.start();

    }
}
