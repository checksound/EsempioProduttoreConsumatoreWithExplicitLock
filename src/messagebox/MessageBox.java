package messagebox;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MessageBox {
    private Lock lock = new ReentrantLock();
    private Condition isFullCondition = lock.newCondition();
    private Condition isEmptyCondition = lock.newCondition();
    private boolean isFull = false;
    private int contenuto;

    public void addContenuto(int contenuto) throws InterruptedException {
        try {
            lock.lock();
            while (isFull) {
                isEmptyCondition.await();
            }

            isFull = true;
            System.out.println(Thread.currentThread().getName() + " - PUT >>>>: " + contenuto);
            this.contenuto = contenuto;
        } finally {
            isFullCondition.signal();
            lock.unlock();
        }
    }

    public int getContenuto() throws InterruptedException {
        try {
            lock.lock();
            while (!isFull)
                isFullCondition.await();

            isFull = false;
            System.out.println(Thread.currentThread().getName() + " - GET <<<<<: " + contenuto);
            return contenuto;
        } finally {
            isEmptyCondition.signal();
            lock.unlock();
        }
    }
}
