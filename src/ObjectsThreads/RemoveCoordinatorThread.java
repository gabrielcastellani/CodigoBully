package ObjectsThreads;
import java.util.Optional;
import java.util.concurrent.Semaphore;

import List.List;

public class RemoveCoordinatorThread extends Thread {
	private Semaphore semaphore;

	public RemoveCoordinatorThread(Semaphore semaphore) {
		this.semaphore = semaphore;
	}

	@Override
	public void run() {
		while(true) {
			try {
				Thread.sleep(100000); // 100000

				Optional<ProcessThread> processCoordinator = List.processes.stream().filter(ProcessThread::isCoordinator)
						.findAny();

				if (!processCoordinator.isPresent()) {
					semaphore.acquire();
				} else {
					processCoordinator.get().interrupt();
					List.processes.remove(processCoordinator.get());
					System.out
							.println("O coordenador com o id(" + processCoordinator.get().getIdProcess() + ") foi removido.");
				}
			} catch (InterruptedException ie) {
				ie.printStackTrace();
				break;
			} finally {
				semaphore.release();
			}
		}
	}
}
