package blockingqueue;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BlockingQueue {
    private int maxCapability;
    private Lock lock = new ReentrantLock();
    private Condition conditionIsFull = lock.newCondition();
    private Condition conditionIsEmpty = lock.newCondition();
    private List<Integer> list = new LinkedList<>();

    public BlockingQueue(int maxCapability) {
        this.maxCapability = maxCapability;
    }

    public void addContenuto(int contenuto) throws InterruptedException {
        try {
            lock.lock();
            while (list.size() == maxCapability) {
                conditionIsFull.await();
            }

            if (list.size() == 0)
                conditionIsEmpty.signal();

            System.out.println(Thread.currentThread().getName() + " - PUT >>>>: " + contenuto);
            list.add(contenuto);
        } finally {
            lock.unlock();
        }
    }

    public int getContenuto() throws InterruptedException {
        try {
            lock.lock();
            while (list.size() == 0)
                conditionIsEmpty.await();

            if (list.size() == maxCapability)
                conditionIsFull.signal();

            int contenuto = list.remove(0);
            System.out.println(Thread.currentThread().getName() + " - GET <<<<<: " + contenuto);
            return contenuto;
        } finally {
            lock.unlock();
        }
    }
}
