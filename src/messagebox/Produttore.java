package messagebox;

public class Produttore extends Thread {

    private String name;
    private MessageBox buffer;
    private Incrementer incrementer;

    public Produttore(String name, MessageBox buffer, Incrementer incrementer) {
        super(name);
        this.buffer = buffer;
        this.incrementer = incrementer;
    }

    public void run() {
        try {
            for(int i = 0; i < 5; i++) {
                int value = incrementer.getNextNumber();
                buffer.addContenuto(value);
                sleep(10);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
