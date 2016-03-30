package sb.quickchecks.locks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sb.quickchecks.locks.util.RandomQuoteRetriever;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by sbalcerek on 2016-03-30.
 */
public class ReentrantLockCheckRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReentrantLockCheckRunner.class);

    private static final int NUM_OF_THREADS = 2; //2 - for fairness/non fairness (ex 10/11)
    private static final long SLEEP_TIME = 2000; //20000 for interruption check (ex 7/8/9)
    private static final boolean IS_LOCK_FAIR = true; //true for fairness/non check
    private static final int ADDITIONAL_THREAD_POOL_SIZE = 100; //0 for ex. 1-9, 100 for ex. 10-11
    private static final int EXAMPLE_NO_TO_RUN = 11; //1-11;


    public static void main(String[] args) throws InterruptedException {

        CountDownLatch latch = new CountDownLatch(NUM_OF_THREADS);
        RandomQuoteRetriever randomQuoteRetriever = new RandomQuoteRetriever(latch);
        ReentrantLockCheck reentrantLockCheck = new ReentrantLockCheck(NUM_OF_THREADS, SLEEP_TIME, IS_LOCK_FAIR);

        Runnable unlockElsewhere = () -> reentrantLockCheck.doSomethingOnSharedResourceUsingReentrantLockAndUnlockElsewhere();
        Runnable unlockWithoutFinally = () -> reentrantLockCheck.doSomethingOnSharedResourceUsingReentrantLockWithoutFinally();
        Runnable useIntrinsicLock = () -> reentrantLockCheck.doSomethingOnSharedResourceUsingIntrinsicLock();
        Runnable useReentrantLock = () -> reentrantLockCheck.doSomethingOnSharedResourceUsingReentrantLock();
        Runnable useInterruptibleLock = () -> reentrantLockCheck.doSomethingOnSharedResourceUsingReentrantInterruptibleLock();
        Runnable tryAcquireReentrantLock = () -> {
            while (!reentrantLockCheck.tryToDoSomethingOnSharedResourceUsingReentrantLock(latch)) {
                randomQuoteRetriever.printRandomQuote();
            }
        };
        Runnable tryAcquireReentrantLockWaitingSomeTime = () -> {
            while (!reentrantLockCheck.tryToDoSomethingOnSharedResourceUsingReentrantLock(latch)) {
                randomQuoteRetriever.printRandomQuote();
            }
        };

        switch(EXAMPLE_NO_TO_RUN) {
            case 1: {
                //example 1. - using intrinsic lock:
                runExample(useIntrinsicLock);
                break;
            }
            case 2: {
                //example 2. - use reentrant lock:
                runExample(useReentrantLock);
                break;
            }
            case 3: {
                //example 3. - unlock reentrant lock in different method
                runExample(unlockElsewhere);
                break;
            }
            case 4: {
                //example 4. - unlock reentrant lock without finally - ticking bomb
                runExample(unlockWithoutFinally);
                break;
            }
            case 5: {
                //example 5. - try to acquire lock and if not success do something different
                runExample(tryAcquireReentrantLock);
                randomQuoteRetriever.printStatistics();
                break;
            }
            case 6: {
                //example 6. - try to acquire lock waiting at most some time and if not success do something different
                runExample(tryAcquireReentrantLockWaitingSomeTime);
                randomQuoteRetriever.printStatistics();
                break;
            }
            case 7: {
                //example 7. - interrupt thread blocked on intrinsic lock
                interruptThreadBlockedOnLock(useIntrinsicLock, Thread.State.BLOCKED);
                break;
            }
            case 8: {
                //example 8. - interrupt thread blocked on reentrant lock
                interruptThreadBlockedOnLock(useReentrantLock, Thread.State.WAITING);
                break;
            }
            case 9: {
                //example 9. - interrupt thread blocked on interruptible reentrant lock
                interruptThreadBlockedOnLock(useInterruptibleLock, Thread.State.WAITING);
                break;
            }
            case 10: {
                //example 10. - non fair lock
                runExampleForFairnessCheck(useReentrantLock);
                break;
            }
            case 11: {
                //example 11. - fair lock
                runExampleForFairnessCheck(useReentrantLock);
                break;
            }
        }

    }

    private static void runExample(Runnable runIt) {
        ExecutorService executorService = getExecutorServiceAndSubmitTasks(runIt);
        executorService.shutdown();
    }

    private static void runExampleForFairnessCheck(Runnable runIt) throws InterruptedException {
        ExecutorService executorService = getExecutorServiceAndSubmitTasks(runIt);

        Thread.sleep(SLEEP_TIME+5);

        for(int i = 0; i < ADDITIONAL_THREAD_POOL_SIZE; i++) {
            executorService.submit(decorateRunnable(runIt));
            Thread.sleep(1);
        }

        executorService.shutdown();
    }

    private static void interruptThreadBlockedOnLock(Runnable useIntrinsicLock, Thread.State stateWhenInterrupt) throws InterruptedException {
        List<Thread> threads = createThreads(useIntrinsicLock);
        threads.stream().forEach((t) -> t.start());

        Thread.sleep(2000);

        threads.stream().forEach((t) -> LOGGER.debug("{} state: {}", t.getName(), t.getState().name()));

        Thread blockedThread = threads.stream().filter((t) -> t.getState() == stateWhenInterrupt).findFirst().get();
        blockedThread.interrupt();
        LOGGER.debug("Interrupted {}",blockedThread.getName());
    }


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
        ExecutorService executorService = Executors.newFixedThreadPool(NUM_OF_THREADS+ADDITIONAL_THREAD_POOL_SIZE);
        for(int i = 0; i <NUM_OF_THREADS; i++) {

            executorService.submit(decorateRunnable(runnable));
        }
        return executorService;
    }

}
