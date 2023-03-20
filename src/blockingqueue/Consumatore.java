package blockingqueue;

public class Consumatore extends Thread {

    private String name;
    private BlockingQueue buffer;

    public Consumatore(String name, BlockingQueue buffer){
        super(name);
        this.buffer = buffer;
    }

    public void run() {
        try {
            for(int i = 0; i < 10; i++) {
                int contenuto = buffer.getContenuto();
                sleep(80);

            }
        } catch(InterruptedException e) {
            e.printStackTrace();
        }

    }
}
