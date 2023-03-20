# Esempi Produttore-Consumatore con Lock esplicito

Utilizzo della classe [java.util.concurrent.locks.Lock.java](https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/locks/Lock.html)

## Esempio implementazione di un MessageBox
![BLOCKING_QUEUE_page-0003.jpg](./BLOCKING_QUEUE_page-0003.jpg)

```java
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

```
Applicazione 
[messagebox.TestProduttoreConsumatoreMulti](./src/messagebox/TestProduttoreConsumatoreMulti.java)

## Esempio implementazione di una coda bloccante
![BLOCKING_QUEUE_page-0001.jpg](./BLOCKING_QUEUE_page-0001.jpg)

```java
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

```

Applicazione 
[blockingqueue.TestProduttoreConsumatoreMulti](./src/blockingqueue/TestProduttoreConsumatoreMulti.java)

### Vedi

[Esempi Produttore-Consumatore](https://github.com/checksound/EsempiProduttoreConsumatore)
