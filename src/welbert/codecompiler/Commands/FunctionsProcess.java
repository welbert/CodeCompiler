package welbert.codecompiler.Commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

import welbert.codecompiler.staticsvalues.Config;
import welbert.codecompiler.utils.Arquivo;
import welbert.codecompiler.utils.ThreadProcess;

public class FunctionsProcess {
	public boolean timeLimitExc;
	
	/**
	 * Função responsável pela execução e compilação do código
	 * 
	 * @param aaFile - Arquivo do código a ser compilado e executado
	 * @param asCompiler - Compilador a ser usado (gcc,g++,java)
	 * @param timelimit - Timelimit do código
	 * @return Object[] - [0] Se houve sucesso na compilação/execução; [1] Mensagem retornada; [2] Duração do processo
	 * @throws Exception
	 * @author Welbert Serra
	 */
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
	
	/**
	 * Faz a lógica para parar o processo
	 * 
	 * @param process - Processo a ser controlado
	 * @param timelimit - Tempo limite para executar
	 * @return boolean - Se estourou o tempo
	 * @throws InterruptedException
	 * @author Welbert Serra
	 */
	private boolean timeRunProcess(RunProcess process,int timelimit) throws InterruptedException{
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
	
	/**
	 * Retorna se na maquina foi encontrado o (gcc/g++/java)
	 * 
	 * @return Retorna se na maquina foi encontrado o (gcc/g++/java)
	 */
 	public String[] getCompilers(){
		String tempResult;
		ArrayList<String> compilers = new ArrayList<String>();
		try {
			RunProcess runProcess = new RunProcess("gcc --version");
			tempResult=runProcess.getReturnProcessOut();
			compilers.add("gcc - "+tempResult.split(Pattern.quote(")"), 2)[1].split("\n",2)[0]);
			runProcess.destroy();
		} catch (IOException e) {}
		try {
			RunProcess runProcess = new RunProcess("g++ --version");
			tempResult=runProcess.getReturnProcessOut();
			compilers.add("g++ - "+tempResult.split(Pattern.quote(")"), 2)[1].split("\n",2)[0]);
			runProcess.destroy();
		} catch (IOException e) {}
		try {
			RunProcess runProcess = new RunProcess("javac -version");
			tempResult=runProcess.getReturnProcessOut();
			runProcess = new RunProcess("java -version");
			tempResult=runProcess.getReturnProcessOut();
			compilers.add("java - "+tempResult.split(Pattern.quote("\""), 2)[1].split("\n",2)[0]);
			runProcess.destroy();
		} catch (Exception e) {}
		if(compilers.isEmpty())
			return new String[]{};
		else{
			String[] result = new String[compilers.size()];
			for(int i = 0;i<compilers.size();i++)
				result[i] = compilers.get(i).toString();
			
			return result;
		}
	}
	
}
