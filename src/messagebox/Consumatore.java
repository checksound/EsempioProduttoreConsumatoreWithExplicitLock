package messagebox;

public class Consumatore extends Thread {

    private String name;
    private MessageBox buffer;

    public Consumatore(String name, MessageBox buffer){
        super(name);
        this.buffer = buffer;
    }

    public void run() {
        try {
            for(int i = 0; i < 5; i++) {
                int contenuto = buffer.getContenuto();
                sleep(10);

            }
        } catch(InterruptedException e) {
            e.printStackTrace();
        }

    }
}
