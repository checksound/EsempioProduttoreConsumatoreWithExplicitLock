package blockingqueue;

public class TestProduttoreConsumatoreMulti {

    public static void main(String[] args) {
        BlockingQueue buffer = new BlockingQueue(6);
        Incrementer incrementer = new Incrementer();
        Thread produttore1 = new Produttore("produttore_1", buffer, incrementer);
        Thread produttore2 = new Produttore("produttore_2", buffer, incrementer);
        Thread produttore3 = new Produttore("produttore_3", buffer, incrementer);

        Thread consumatore1 = new Consumatore("consumatore_1", buffer);
        Thread consumatore2 = new Consumatore("consumatore_2", buffer);
        Thread consumatore3 = new Consumatore("consumatore_3", buffer);

        produttore1.start();
        produttore2.start();
        produttore3.start();

        consumatore1.start();
        consumatore2.start();
        consumatore3.start();

    }

}
