package Main;

import java.util.concurrent.Semaphore;

import ObjectsThreads.CreateProcessThread;
import ObjectsThreads.ProcessRequestThread;
import ObjectsThreads.RemoveCoordinatorThread;
import ObjectsThreads.RemoveProcessThread;

public class Main {

	public static void main(String[] args) {
		try {
			Semaphore semaphore = new Semaphore(1);
			
			ProcessRequestThread processRequest = new ProcessRequestThread(semaphore);
			CreateProcessThread createProcess = new CreateProcessThread(semaphore);
			RemoveProcessThread removeProcess = new RemoveProcessThread(semaphore);
			RemoveCoordinatorThread removeCoordinator = new RemoveCoordinatorThread(semaphore);
			
			processRequest.start();
			createProcess.start();
			removeProcess.start();
			removeCoordinator.start();
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
