package ObjectsThreads;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.stream.Collectors;

import List.List;

public class RemoveProcessThread extends Thread {
	private Semaphore semaphore;

	public RemoveProcessThread(Semaphore semaphore) {
		this.semaphore = semaphore;
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(80000); //80000
				
				ArrayList<ProcessThread> listProcessWithoutCoordinator = (ArrayList<ProcessThread>) List.processes
						.stream().filter(process -> process.isCoordinator() == false).collect(Collectors.toList());
				
					if(listProcessWithoutCoordinator.size() > 0) {
						Random random = new Random();
						ProcessThread process = listProcessWithoutCoordinator.get(random.nextInt(listProcessWithoutCoordinator.size()));
						process.interrupt();
						List.processes.remove(process);
						System.out.println("O processo com o id(" + process.getIdProcess() + ") foi removido.");
					}
					else {
						semaphore.acquire();
					}
			} catch (InterruptedException ie) {
				ie.printStackTrace();
			} finally {
				semaphore.release();
			}
		}
	}
}
