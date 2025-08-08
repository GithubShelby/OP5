package aufgaben.loesung_5v2;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadLocalRandom;

public class Producer implements Runnable
{
    private final BlockingQueue<Integer> queue;

    @Override
    public void run()
    {
        while (true)
        {
            produce();
        }
    }

    private void produce()
    {
        try
        {
            int daten = ThreadLocalRandom.current().nextInt(100) + 100;
            Thread.sleep(ThreadLocalRandom.current().nextInt(1000));
            System.out.println("Daten erzeugt: " + daten);
            //synchronized (Producer.class)
            // Wir müssen hier nicht selbst synchronisieren, da die 'put'-Methode in der Klasse 'LinkedBlockingQueue' bereits über 'ReentrantLock' synchronisiert ist.
            queue.put(daten);

        }
        catch (InterruptedException e)
        {
            throw new RuntimeException(e);
        }
    }

    public Producer(BlockingQueue<Integer> queue)
    {
        this.queue = queue;
    }
}
