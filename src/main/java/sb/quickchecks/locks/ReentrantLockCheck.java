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

    private static final Logger LOGGER = LoggerFactory.getLogger(ReentrantLockCheck.class);

    public final long SLEEP_TIME;
    private final int NUMBER_OF_THREADS;

    private ReentrantLock lock;

    public ReentrantLockCheck(int numOfThreads, long sleepTime, boolean fair) {
        NUMBER_OF_THREADS = numOfThreads;
        SLEEP_TIME = sleepTime;
        lock = new ReentrantLock(fair);
    }

    public void doSomethingOnSharedResourceUsingReentrantLock() {
        lock.lock();
        try {
            //LOGGER.debug("Number of threads waiting: {}",lock.getQueueLength());
            doSomethingImportant();
        } catch (InterruptedException e) {
            LOGGER.error("Exception: ",e);
        } finally {
            LOGGER.debug("Lock unlocked");
            lock.unlock();
        }
    }

    public void doSomethingOnSharedResourceUsingReentrantLockAndUnlockElsewhere() {
        lock.lock();
        try {
            //LOGGER.debug("Number of threads waiting: {}",lock.getQueueLength());
            doSomethingImportantAndUnlock();
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
            if(rand.nextInt(NUMBER_OF_THREADS) == 3) {
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
                LOGGER.error("Exception: ", e);
            }
        }
    }

    private void doSomethingImportant() throws InterruptedException {
            LOGGER.debug("<<<<<< Thread {} is working on shared resource.", Thread.currentThread().getName());
            Thread.sleep(SLEEP_TIME);
            LOGGER.debug(">>>>>> Thread {} completed its work", Thread.currentThread().getName());
    }

    private void doSomethingImportantAndUnlock() throws InterruptedException {
        try {
           doSomethingImportant();
        }
        finally {
            LOGGER.debug("Lock unlocked");
            lock.unlock();
        }
    }






}


