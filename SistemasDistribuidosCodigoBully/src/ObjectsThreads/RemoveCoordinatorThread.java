package ObjectsThreads;
import java.util.concurrent.Semaphore;

import List.List;

public class RemoveCoordinatorThread extends Thread {
	private Semaphore semaphore;

	public RemoveCoordinatorThread(Semaphore semaphore) {
		this.semaphore = semaphore;
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(100000); // 100000

				ProcessThread processCoordinator = List.processes.stream().filter(process -> process.isCoordinator())
						.findAny().orElse(null);

				if (processCoordinator == null) {
					semaphore.acquire();
				} else {
					processCoordinator.interrupt();
					List.processes.remove(processCoordinator);
					System.out
							.println("O coordenador com o id(" + processCoordinator.getIdProcess() + ") foi removido.");
				}
			} catch (InterruptedException ie) {
				ie.printStackTrace();
			} finally {
				semaphore.release();
			}
		}
	}
}
