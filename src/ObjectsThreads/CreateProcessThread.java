package ObjectsThreads;
import java.util.Random;
import java.util.concurrent.Semaphore;

import List.List;

public class CreateProcessThread extends Thread {
	private Semaphore semaphore;
	private int processId;
	
	public CreateProcessThread(Semaphore semaphore) {
		this.semaphore = semaphore;
	}
	
	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(30000); // 30000

				Random random = new Random();
				processId = random.nextInt(Integer.MAX_VALUE);
				boolean firstProcessList = false;
				
				long countCoordinator = List.processes.stream().filter(ProcessThread::isCoordinator).count();
				if(countCoordinator == 0 && List.processes.isEmpty()) {
					firstProcessList = true;
				}
				
				if (List.processes.stream().anyMatch(process -> process.getIdProcess() == processId)) {
					while (true) {
						processId = random.nextInt(Integer.MAX_VALUE);

						if (List.processes.stream().noneMatch(process -> process.getIdProcess() == processId)) {
							break;
						}
					}
				}
				
				ProcessThread process = new ProcessThread(processId, firstProcessList, semaphore);
				process.start();
				List.processes.add(process);
				
				if (firstProcessList) {
					System.out.println("O processo(Coordenador) com o id(" + process.getIdProcess() + ") foi criado.");
				} else {
					System.out.println("O processo com o id(" + process.getIdProcess() + ") foi criado.");
				}
			
			} catch (InterruptedException ie) {
				ie.printStackTrace();
			}
		}
	}
}
