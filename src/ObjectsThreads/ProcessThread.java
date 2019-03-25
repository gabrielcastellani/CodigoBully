package ObjectsThreads;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

import List.List;

public class ProcessThread extends Thread {
	private Semaphore semaphore;
	private int id;
	private boolean coordinator;

	ProcessThread(int id, boolean coordinator, Semaphore semaphore) {
		this.id = id;
		this.coordinator = coordinator;
		this.semaphore = semaphore;
	}

	private boolean compareIdProcess(int otherProcessId) {
		return this.id > otherProcessId;
	}

	private void election() {
		if (List.processesElection.isEmpty()) {
			List.processesElection.addAll(List.processes);
		}

		List.processesElection.remove(this);
		ArrayList<String> resultCompareIdProcess = new ArrayList<>();
		for (ProcessThread process : List.processesElection) {
			System.out.println("O processo com o id(" + this.id
					+ "), mandou mensagem de eleição para o processo com o id(" + process.getIdProcess() + ").");
			if (List.processes.contains(process)) {
				resultCompareIdProcess.add(process.compareIdProcess(this.id) + "");
			} else {
				List.processesElection.remove(process);
			}
		}

		if (resultCompareIdProcess.contains("true")) {
			List.processesElection.get(0).election();
		} else {
			List.processesElection.clear();
			coordinator = true;

			if (List.processes.contains(this)) {
				System.out.println("O processo com o id(" + this.id + ") se tornou o novo coordenador.");
			}
		}
	}

	void request() {
		ProcessThread processCoordinator = List.processes.stream().filter(ProcessThread::isCoordinator).findAny()
				.orElse(null);

		if (processCoordinator == null) {
			try {
				election();				
			} catch (Exception e) {
				System.out.println(e.getMessage());
			} finally {
				semaphore.release();
			}
		} else {
			System.out.println("O processo com o id(" + id + ") fez uma requisi��o para o coordenador com o id("
					+ processCoordinator.id + ")");
		}
	}

	int getIdProcess() {
		return id;
	}

	boolean isCoordinator() {
		return coordinator;
	}
}
