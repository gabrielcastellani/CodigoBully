package ObjectsThreads;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.stream.Collectors;

import List.List;

public class ProcessRequestThread extends Thread{
	private Semaphore semaphore;
	
	public ProcessRequestThread(Semaphore semaphore) {
		this.semaphore = semaphore;
	}
	
	@Override
	public void run() {
		while(true) {
			try {
				Thread.sleep(25000); //25000
				
				if(List.processes.isEmpty()) {
					semaphore.acquire();
				}else{
					ArrayList<ProcessThread> processes = (ArrayList<ProcessThread>) List.processes.stream()
							.filter(process -> process.isCoordinator() == false).collect(Collectors.toList());
					
					if(!processes.isEmpty()) {
						Random random = new Random();
						ProcessThread processRequest = processes.get(random.nextInt(processes.size()));
						processRequest.request();
					}
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
			finally {
				semaphore.release();
			}
		}
	}
}
