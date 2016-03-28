package welbert.codecompiler.utils;

import welbert.codecompiler.Commands.RunProcess;

public class ThreadProcess implements Runnable{
	private RunProcess process;
	private int timelimit;
	
	public ThreadProcess(RunProcess process,int timelimit) {
		this.process = process;
		this.timelimit = timelimit;
	}
	
	@Override
	public void run() {

		try {
		    Thread.sleep(1000*timelimit);
		    process.destroy();
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
		
	}

}
