package welbert.codecompiler.Commands;

import welbert.codecompiler.staticsvalues.Config;
import welbert.codecompiler.utils.Arquivo;
import welbert.codecompiler.utils.ThreadProcess;

public class FunctionsProcess {
	public boolean timeLimitExc;
	
	public Object[] runCompileInCode(Arquivo aaFile, String asCompiler,int timelimit) throws Exception {
		String out = "";
		boolean success = false, timeLimitExc = false;
		RunProcess process;
		long startTime=0, endTime=0, duration = 0;
		switch (asCompiler) {
		case "gcc":
			process = new RunProcess(new String[]{asCompiler,aaFile.getAbsolutePath()});
			out = process.getReturnProcessOut();
			if(out.trim().equals("")){
				startTime = System.currentTimeMillis();
				if(Config.WINDOWS)
					process = new RunProcess(new String[]{"a.exe"});
				else
					process = new RunProcess(new String[]{"./a.out"});
				
				timeLimitExc = timeRunProcess(process,timelimit);
				endTime = System.currentTimeMillis();
				
				if(timeLimitExc){
					out = "TLE - Tempo Limite Excedido.";
					success = false;
				}else{
					out = process.getReturnProcessOut();
					success = true;
				}
			}
			break;
		case "g++":
			process = new RunProcess(new String[]{asCompiler,aaFile.getAbsolutePath()});
			out = process.getReturnProcessOut();
			if(out.trim().equals("")){
				startTime = System.currentTimeMillis();
				if(Config.WINDOWS)
					process = new RunProcess(new String[]{"a.exe"});
				else
					process = new RunProcess(new String[]{"./a.out"});
				
				timeLimitExc = timeRunProcess(process,timelimit);
				endTime = System.currentTimeMillis();
				
				if(timeLimitExc){
					out = "TLE - Tempo Limite Excedido.";
					success = false;
				}else{
					out = process.getReturnProcessOut();
					success = true;
				}
			}
			
			break;		
		case "java":
			process = new RunProcess(new String[]{asCompiler+"c",aaFile.getAbsolutePath()});
			out = process.getReturnProcessOut();
			if(out.trim().equals("")){
				startTime = System.currentTimeMillis();
				if(Config.WINDOWS){
					Runtime.getRuntime().exec(new String[]{"cmd","/c","copy","/Y",
							aaFile.getPathName()+"\\CodeCompiler.class"});
					process = new RunProcess(new String[]{"java","CodeCompiler"});
				}else
					process = new RunProcess(new String[]{"java",aaFile.getFile().getCanonicalPath()+
							"/CodeCompiler"});				
				
				timeLimitExc = timeRunProcess(process,timelimit);
				endTime = System.currentTimeMillis();
				
				if(timeLimitExc){
					out = "TLE - Tempo Limite Excedido.";
					success = false;
				}else{
					out = process.getReturnProcessOut();
					success = true;
				}						
			}
			
			break;

		default:
			break;
		}
		
		if(success)
			duration = (endTime - startTime);
		
		return new Object[]{success,out,duration};
	}
	
	public boolean timeRunProcess(RunProcess process,int timelimit) throws InterruptedException{
		timeLimitExc=false;
		Thread threadProcess = null;
		
		if(timelimit<1)
			timelimit = Config.MAXTIMELIMIT;
		
		threadProcess = new Thread(new ThreadProcess(process,timelimit,this));
		threadProcess.start();
		
		process.waitProcessFinish();
		if(threadProcess!=null && threadProcess.isAlive())
			threadProcess.interrupt();
		
		return timeLimitExc;
	}
	
}
