package welbert.codecompiler.utils;

import welbert.codecompiler.Commands.FunctionsProcess;
import welbert.codecompiler.Commands.RunProcess;

public class ThreadProcess implements Runnable{
	private RunProcess process;
	private int timelimit;
	private FunctionsProcess sender;
	
	public ThreadProcess(RunProcess process,int timelimit,FunctionsProcess sender) {
		this.process = process;
		this.timelimit = timelimit;
		this.sender = sender;
	}
	
	@Override
	public void run() {

		try {
		    Thread.sleep(1000*timelimit);
		    sender.timeLimitExc=true;
		    process.destroy();		    
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
		
	}

}
