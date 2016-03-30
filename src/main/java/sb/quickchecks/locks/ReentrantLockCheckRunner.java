package sb.quickchecks.locks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sb.quickchecks.locks.util.RandomQuoteRetriever;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Predicate;

/**
 * Created by sbalcerek on 2016-03-30.
 */
public class ReentrantLockCheckRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReentrantLockCheckRunner.class);

    private static final int NUM_OF_THREADS = 10; //2; //2 - for fainrness/non fairness




    public static void main(String[] args) throws InterruptedException {


        CountDownLatch latch = new CountDownLatch(NUM_OF_THREADS);
        RandomQuoteRetriever randomQuoteRetriever = new RandomQuoteRetriever(latch);
        ReentrantLockCheck reentrantLockCheck = new ReentrantLockCheck(NUM_OF_THREADS, false);

        Runnable unlockElswhere = () -> reentrantLockCheck.doSomethingOnSharedResourceUsingReentrantLockAndUnlockSomwhere();
        Runnable unlockWithoutFinally = () -> reentrantLockCheck.doSomethingOnSharedResourceUsingReentrantLockWithoutFinally();
        Runnable useIntrinsicLock = () -> reentrantLockCheck.doSomethingOnSharedResourceUsingIntrinsicLock();
        Runnable useReentrantLock = () -> reentrantLockCheck.doSomethingOnSharedResourceUsingReentrantLock();
        Runnable useInterruptibleLock = () -> reentrantLockCheck.doSomethingOnSharedResourceUsingReentrantInterruptibleLock();
        Runnable tryAcquireReentrantLock = () -> {
            while (!reentrantLockCheck.tryToDoSomethingOnSharedResourceUsingReentrantLock(latch)) {
                randomQuoteRetriever.printRandomQuote();
            }
        };




//        ExecutorService executorService = getExecutorServiceAndSubmitTasks(useReentrantLock);
//
////            feedByThreads(reentrantLockCheck, executorService);
//
//        executorService.shutdown();

//        randomQuoteRetriever.printStatistics();





        //interruptible:

//        Runnable runnable = decorateRunnable(useIntrinsicLock);
//        Runnable runnable = decorateRunnable(useReentrantLock);
//        Runnable runnable = decorateRunnable(useInterruptibleLock);

        List<Thread> threads = createThreads(useIntrinsicLock);
        threads.stream().forEach((t) -> t.start());

        Thread.sleep(2000);

        threads.stream().forEach((t) -> LOGGER.debug("{} state: {}", t.getName(), t.getState().name()));

        //for intrinsic
//        if(threads.get(0).getState() == Thread.State.BLOCKED) {
//            threads.get(0).interrupt();
//            LOGGER.debug("INterrupted t1");
//        } else if(threads.get(1).getState() == Thread.State.BLOCKED) {
//            threads.get(1).interrupt();
//            LOGGER.debug("INterrupted t2");
//        }

//        Thread blockedThread = threads.stream().filter((t) -> t.getState() == Thread.State.BLOCKED).findFirst().get();
//        blockedThread.interrupt();
//        LOGGER.debug("Interrupted {}",blockedThread.getName());

        //for reentrant
//        if(threads.get(0).getState() == Thread.State.WAITING) {
//            threads.get(0).interrupt();
//            LOGGER.debug("INterrupted t1");
//        } else if(threads.get(1).getState() == Thread.State.WAITING) {
//            threads.get(1).interrupt();
//            LOGGER.debug("INterrupted t1");
//        }

//        Thread waitingThread = threads.stream().filter((t) -> t.getState() == Thread.State.WAITING).findFirst().get();
//        waitingThread.interrupt();
//        LOGGER.debug("Interrupted {}",waitingThread.getName());


        //for reentrant
//        if(threads.get(0).getState() == Thread.State.TIMED_WAITING) {
//            threads.get(0).interrupt();
//            LOGGER.debug("INterrupted t1");
//        } else if(threads.get(1).getState() == Thread.State.TIMED_WAITING) {
//            threads.get(1).interrupt();
//            LOGGER.debug("INterrupted t1");
//        }

//        Thread timedWaitingThread = threads.stream().filter((t) -> t.getState() == Thread.State.TIMED_WAITING).findFirst().get();
//        timedWaitingThread.interrupt();
//        LOGGER.debug("Interrupted {}",timedWaitingThread.getName());
    }

//    private static void feedByThreads(ReentrantLockCheck reentrantLockCheck, ExecutorService executorService) throws InterruptedException {
//        Thread.sleep(SLEEP_TIME+10);
//
//        for(int i = 0; i < 20; i++) {
//            executorService.submit(decorateRunnable(() -> reentrantLockCheck.doSomethingOnSharedResourceUsingReentrantLock()));
//            Thread.sleep(2);
//        }
//    }


    private static List<Thread> createThreads(Runnable runnable) {

        return Arrays.asList(new Thread(decorateRunnable(runnable)), new Thread(decorateRunnable(runnable)), new Thread(decorateRunnable(runnable)));
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


    private static ExecutorService getExecutorServiceAndSubmitTasks(Runnable runnable) {
        ExecutorService executorService = Executors.newFixedThreadPool(NUM_OF_THREADS+20);
        for(int i = 0; i <NUM_OF_THREADS; i++) {

            executorService.submit(decorateRunnable(runnable));
        }
        return executorService;
    }
}
