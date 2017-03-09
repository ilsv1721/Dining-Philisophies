import java.io.PrintStream;
import java.util.concurrent.CountDownLatch;


/**
 * A "bad" Philosopher implementation with CountDownLatch in its
 * {@link #eatAndThink() eatAndThink} method.
 * 
 * Should be used in tasks of deadlocking the program.
 * 
 * @author ilya
 * 
 */
public class DeadlockedPhilosopher extends Philosopher {

	private final CountDownLatch latch;

	public DeadlockedPhilosopher(long id, Chopstick fiChopstick, Chopstick seChopstick, PrintStream outStream,
			CountDownLatch latch) {
		super(id, fiChopstick, seChopstick, outStream);
		this.latch = latch;
	}

	@Override
	public void eatAndThink() throws InterruptedException {

		synchronized (firstToTakechopstick) {
			latch.countDown();
			outStream.println("Dummy DeadlockPhilosopher " + id + " takes left fork.");
			latch.await();
			synchronized (secondToTakeChopstick) {
				outStream.println(id + " Eating");
				Thread.sleep(1000);

			}
		}

		outStream.println(id + " Thinking");

	}
}
