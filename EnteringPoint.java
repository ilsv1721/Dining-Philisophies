import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

/*
 * Данная программа является решением второй задачи "Обедающие философы" для IT университета 
 * компании DigitalDesign.
 *
 * Для решения основной задачи использовался алгоритм с использованием иерархических ресурсов.
 * Для решения дополнительного задания философами был имплементирован алгоритм, в котором 
 * они всегда берут первой левую по отношении к ним вилку. Данный алгоритм в конечном итоге приведет 
 * к дедлоку. 
 * Для моделирования ситауции и для блокировки программы с момента ее запуска был использован
 * CountDownLatch.
 *  
 * Самойлов Илья
 * ilsv1721@gmail.com
 * 
 * This is the solution of 2nd task "Dining Philosophers" for DigitalDesign It-university.
 * 
 * In order to solve main task the classic resource hierarchy solution was used, which involves 
 * firstly picking  the hierarchically lowest available resource by working unit. 
 * (In this case, firstly picking lowest numbered chopstick by Philosopher)
 *
 * In order to solve additional task (causing program to deadlock) philosophers were implemented with
 * left-pick (always pick the left Chopstick) algorithm. 
 * 
 * This algorithm (and the algorithm which involves picking chopsticks randomly, which is ultimately the 
 * left-pick algorithm in a context of deadlock) will deadlock eventually.
 * 
 * In order to simulate "eventual"  deadlock and lock the program right after the start 
 * the JAVA's CountDownLatch was used.
 *  
 * Samoylov Ilya
 * ilsv1721@gmail.com
 * 
 */

public class EnteringPoint {

	/**
	 * Easy to invoke facade.
	 * 
	 */
	public static class DiningPhilFacade {

		/**
		 * Starts the execution of the task with resource hierarchy algorithm.
		 */
		static void startNormalHierarchySolution() {
			ArrayList<Philosopher.Chopstick> chopsticks = new ArrayList<>();
			ArrayList<Philosopher> philosophers = new ArrayList<>();
			for (int i = 0; i < 5; i++) {
				chopsticks.add(new Philosopher.Chopstick());
			}
			
			//picking the lowest numbered ones
			philosophers.add(new Philosopher(0, chopsticks.get(0), chopsticks.get(4), System.out));
			philosophers.add(new Philosopher(1, chopsticks.get(0), chopsticks.get(1), System.out));
			philosophers.add(new Philosopher(2, chopsticks.get(1), chopsticks.get(2), System.out));
			philosophers.add(new Philosopher(3, chopsticks.get(2), chopsticks.get(3), System.out));
			philosophers.add(new Philosopher(4, chopsticks.get(3), chopsticks.get(4), System.out));

			for (Philosopher ph : philosophers) {
				new Thread(ph).start();
			}

		};

		/**
		 * Starts the execution of the task with left-pick algorithm +
		 * CountDownLatch causing program to deadlock.
		 *
		 */
		static void startDeadlockedAlgorithm() {

			ArrayList<Philosopher.Chopstick> chopsticks = new ArrayList<>();
			ArrayList<Philosopher> philosophers = new ArrayList<>();
			for (int i = 0; i < 5; i++) {
				chopsticks.add(new Philosopher.Chopstick());
			}

			CountDownLatch latch = new CountDownLatch(chopsticks.size());

			//Always picking left chopsticks first
			philosophers.add(new DeadlockedPhilosopher(0, chopsticks.get(0), chopsticks.get(4), System.out, latch));
			philosophers.add(new DeadlockedPhilosopher(1, chopsticks.get(1), chopsticks.get(0), System.out, latch));
			philosophers.add(new DeadlockedPhilosopher(2, chopsticks.get(2), chopsticks.get(1), System.out, latch));
			philosophers.add(new DeadlockedPhilosopher(3, chopsticks.get(3), chopsticks.get(2), System.out, latch));
			philosophers.add(new DeadlockedPhilosopher(4, chopsticks.get(4), chopsticks.get(3), System.out, latch));

			for (Philosopher ph : philosophers) {
				new Thread(ph).start();
			}

		};

	}

	public static void main(String[] args) {
		 DiningPhilFacade.startNormalHierarchySolution();
		
		 // Uncomment in order to start deadlocked algorithm 
		 // DiningPhilFacade.startDeadlockedAlgorithm();

	}
}
