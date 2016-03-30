package sb.quickchecks.locks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sb.quickchecks.locks.util.RandomQuoteRetriever;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;

/**
 * Created by slwk on 20.03.16.
 */
public class ReentrantLockCheck {

    public static final int SLEEP_TIME = 2000; //2000; //30000 for interruptibitility check
    //    private ReentrantLock lock = new ReentrantLock();
    private ReentrantLock lock = new ReentrantLock(true);
    private static final Logger LOGGER = LoggerFactory.getLogger(ReentrantLockCheck.class);
    private static final int NUM_OF_THREADS = 10; //2; //2 - for fainrness/non fairness
    private CountDownLatch latch = new CountDownLatch(NUM_OF_THREADS);
    private RandomQuoteRetriever randomQuoteRetriever = new RandomQuoteRetriever(latch);

    public void doSomethingOnSharedResourceUsingReentrantLock() {
        lock.lock();
        try {
            //LOGGER.debug("Number of threads waiting: {}",lock.getQueueLength());
            doSomethingImportant();
        } catch (InterruptedException e) {
            LOGGER.error("{}",e);
        } finally {
            LOGGER.debug("Lock unlocked");
            lock.unlock();
        }
    }

    public void doSomethingOnSharedResourceUsingReentrantLockAndUnlockSomwhere() {
        lock.lock();
        try {
            //LOGGER.debug("Number of threads waiting: {}",lock.getQueueLength());
            doSomethingImportant();
        } catch (InterruptedException e) {
            LOGGER.error("{}",e);
        }
    }

    public void doSomethingOnSharedResourceUsingReentrantInterruptibleLock() {
        try {
            lock.lockInterruptibly();
        } catch (InterruptedException e) {
            LOGGER.error("Lock interrupted in thread {}! returning...",Thread.currentThread().getName(),e);
            return;
        }
        try {
            //LOGGER.debug("Number of threads waiting: {}",lock.getQueueLength());
            doSomethingImportant();
        } catch (InterruptedException e) {
            LOGGER.error("{}",e);
            return;
        } finally {
            LOGGER.debug("Lock unlocked");
            lock.unlock();
        }
    }

    public void doSomethingOnSharedResourceUsingReentrantLockWithoutFinally() {
        lock.lock();
        try {
            //LOGGER.debug("Number of threads waiting: {}",lock.getQueueLength());
            doSomethingImportant();
            Random rand = new Random();
            if(rand.nextInt(NUM_OF_THREADS) == 3) {
                LOGGER.debug("Throwning exception");
                throw new RuntimeException("Something is broken... said "+Thread.currentThread().getName());
            }
        } catch (InterruptedException e) {
            LOGGER.error("{}",e);
        }
        LOGGER.debug("Lock unlocked");
        lock.unlock();
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
        try {
            LOGGER.debug("<<<<<< Thread {} is working on shared resource.", Thread.currentThread().getName());
            Thread.sleep(SLEEP_TIME);
            LOGGER.debug(">>>>>> Thread {} completed its work", Thread.currentThread().getName());
        }
        finally {
//            LOGGER.debug("Lock unlocked");
//            lock.unlock();
//            countDownLatch.countDown();
        }
    }


    public static void main(String[] args) throws InterruptedException {



        ReentrantLockCheck reentrantLockCheck = new ReentrantLockCheck();

            ExecutorService executorService = getExecutorServiceAndSubmitTasks(reentrantLockCheck);

//            feedByThreads(reentrantLockCheck, executorService);

            executorService.shutdown();

            reentrantLockCheck.randomQuoteRetriever.printStatistics();





        //interruptible:
/*
//        Runnable runnable = getRunnable((r) -> r.doSomethingOnSharedResourceUsingIntrinsicLock(), reentrantLockCheck);
//        Runnable runnable = getRunnable((r) -> r.doSomethingOnSharedResourceUsingReentrantLock(), reentrantLockCheck);
        Runnable runnable = getRunnable((r) -> r.doSomethingOnSharedResourceUsingReentrantInterruptibleLock(), reentrantLockCheck);

        Thread t1 = new Thread(runnable);
        Thread t2 = new Thread(runnable);
        Thread t3 = new Thread(runnable);

        t1.start();
        t2.start();
        t3.start();

        Thread.sleep(2000);

        LOGGER.debug("T1 state: {}", t1.getState().name());
        LOGGER.debug("T2 state: {}", t2.getState().name());
        LOGGER.debug("T3 state: {}", t3.getState().name());

        //for intrinsic
//        if(t1.getState() == Thread.State.BLOCKED) {
//            t1.interrupt();
//            LOGGER.debug("INterrupted t1");
//        } else if(t2.getState() == Thread.State.BLOCKED) {
//            t2.interrupt();
//            LOGGER.debug("INterrupted t2");
//        }

        //for reentrant
        if(t1.getState() == Thread.State.WAITING) {
            t1.interrupt();
            LOGGER.debug("INterrupted t1");
        } else if(t2.getState() == Thread.State.WAITING) {
            t2.interrupt();
            LOGGER.debug("INterrupted t1");
        }

        //for reentrant
//        if(t1.getState() == Thread.State.TIMED_WAITING) {
//            t1.interrupt();
//            LOGGER.debug("INterrupted t1");
//        } else if(t2.getState() == Thread.State.TIMED_WAITING) {
//            t2.interrupt();
//            LOGGER.debug("INterrupted t1");
//        }
*/
    }

    private static void feedByThreads(ReentrantLockCheck reentrantLockCheck, ExecutorService executorService) throws InterruptedException {
        Thread.sleep(SLEEP_TIME+10);

        for(int i = 0; i < 20; i++) {
            executorService.submit(decorateRunnable(() -> reentrantLockCheck.doSomethingOnSharedResourceUsingReentrantLock()));
            Thread.sleep(2);
        }
    }

    private static Runnable decorateRunnable(Runnable runnable) {
        return () -> {
            LOGGER.debug("Thread {} attempting to do something on shared resource",Thread.currentThread().getName());
            try {
                runnable.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
//            LOGGER.debug("Thread {} completed its action", Thread.currentThread().getName());
        };
    }


    private static ExecutorService getExecutorServiceAndSubmitTasks(final ReentrantLockCheck reentrantLockCheck) {
        ExecutorService executorService = Executors.newFixedThreadPool(NUM_OF_THREADS+20);
        for(int i = 0; i <NUM_OF_THREADS; i++) {

            executorService.submit(decorateRunnable(() ->
                    {
                        while (!reentrantLockCheck.tryToDoSomethingOnSharedResourceUsingReentrantLock(reentrantLockCheck.latch)) {
//                            LOGGER.debug("Thread {} is waiting for resource. Let's do something useful...", Thread.currentThread().getName());
                           reentrantLockCheck.randomQuoteRetriever.printRandomQuote();
                        }
                    }
//                    {
//                        try {
//                            while (!reentrantLockCheck.tryToDoSomethingOnSharedResourceUsingTimedReentrantLock(reentrantLockCheck.latch)) {
//                                reentrantLockCheck.randomQuoteRetriever.printRandomQuote();
//                            }
//                        } catch (InterruptedException e) {
//                            LOGGER.error("{}",e);
//                        }
//                    }
//                    {
//                        reentrantLockCheck.doSomethingOnSharedResourceUsingReentrantLock();
//                    }
//            {
//                reentrantLockCheck.doSomethingOnSharedResourceUsingIntrinsicLock();
//            }
//            {
//                reentrantLockCheck.doSomethingOnSharedResourceUsingReentrantLockWithoutFinally();
//            }
//            {
//                reentrantLockCheck.doSomethingOnSharedResourceUsingReentrantLockAndUnlockSomwhere();
//            }
                    ));
        }
        return executorService;
    }



}


