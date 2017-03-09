import java.io.PrintStream;

/**
 * A "good" Philosopher implementation without CountDownLatch in its
 * {@link #eatAndThink() eatAndThink} method.
 * 
 * Should be used in tasks of normal execution.
 * 
 * @author ilya
 * 
 */
public class Philosopher implements Runnable {

	/**
	 * Empty Chopstick implementation. Created just for readability of the
	 * program.
	 * 
	 */
	static class Chopstick {
	}

	final long id;
	protected final Chopstick firstToTakechopstick;
	protected final Chopstick secondToTakeChopstick;
	protected final PrintStream outStream;

	public Philosopher(long id, Chopstick fiChopstick, Chopstick seChopstick, PrintStream outStream) {
		this.id = id;
		this.firstToTakechopstick = fiChopstick;
		this.secondToTakeChopstick = seChopstick;
		this.outStream = outStream;
	}

	public void eatAndThink() throws InterruptedException {

		synchronized (firstToTakechopstick) {
			synchronized (secondToTakeChopstick) {
				outStream.println(id + " Eating");
				Thread.sleep(1000);

			}
		}

		outStream.println(id + " Thinking");

	}

	@Override
	public void run() {
		while (true) {
			try {
				eatAndThink();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}
