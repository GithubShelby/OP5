package aufgaben.loesung_5v2;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadLocalRandom;

public class Consumer implements Runnable
{
    private final BlockingQueue<Integer> queue;

    @Override
    public void run()
    {
        while (true)
        {
            consume();
        }
    }

    private void consume()
    {
        try
        {
            int daten;
            //synchronized (Consumer.class)
            // Wir müssen hier nicht selbst synchronisieren, da die 'take'-Methode in der Klasse 'LinkedBlockingQueue' bereits über 'ReentrantLock' synchronisiert ist.
            daten = queue.take();

            Thread.sleep(ThreadLocalRandom.current().nextInt(1000));
            System.out.println("Daten verarbeitet: " + daten + " - verbleibend: " + queue.size());
        }
        catch (InterruptedException e)
        {
            throw new RuntimeException(e);
        }
    }

    public Consumer(BlockingQueue<Integer> queue)
    {
        this.queue = queue;
    }
}
