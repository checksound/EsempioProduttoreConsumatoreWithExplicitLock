package blockingqueue;

public class Produttore extends Thread {

    private String name;
    private BlockingQueue buffer;

    private Incrementer incrementer;

    public Produttore(String name, BlockingQueue buffer, Incrementer incrementer) {
        super(name);
        this.buffer = buffer;
        this.incrementer = incrementer;
    }

    public void run() {
        try {
            for(int i = 0; i < 10; i++) {
                int value = incrementer.getNextNumber();
                buffer.addContenuto(value);
                sleep(10);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
