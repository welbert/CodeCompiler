package welbert.codecompiler.Commands;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class RunProcess {

	private BufferedReader brOut;
	private BufferedReader brErr;
	private BufferedWriter wrIn;
	private Process process;
	
	public RunProcess(String commandName) throws IOException{
		process = new ProcessBuilder(commandName.split(" ")).redirectErrorStream(true).start();
		InputStream isErr = process.getErrorStream();
		InputStreamReader isrErr = new InputStreamReader(isErr);
		brErr = new BufferedReader(isrErr);
		
		InputStream stdOut = process.getInputStream();
		InputStreamReader isrOut = new InputStreamReader(stdOut);
		brOut = new BufferedReader(isrOut);
		
		OutputStream stdin = process.getOutputStream ();
		wrIn = new BufferedWriter(new OutputStreamWriter(stdin));			
	}
	
	public RunProcess(String[] commandsName) throws IOException{
 		process = new ProcessBuilder(commandsName).redirectErrorStream(true).start();
		InputStream isErr = process.getErrorStream();
		InputStreamReader isrErr = new InputStreamReader(isErr);
		brErr = new BufferedReader(isrErr);
		
		InputStream stdOut = process.getInputStream();
		InputStreamReader isrOut = new InputStreamReader(stdOut);
		brOut = new BufferedReader(isrOut);
		
		OutputStream stdin = process.getOutputStream ();
		wrIn = new BufferedWriter(new OutputStreamWriter(stdin));			
	}
	
	public int waitProcessFinish() throws InterruptedException{
		return process.waitFor();
	}
	
	public void writeStdIn(String asMessage) throws IOException{
		//wrIn.write(asMessage);
		wrIn.write(asMessage + " && echo --EOF--) || echo --EOF--\n");
		wrIn.flush();
	}
	
	public String getReturnProcessOut() throws IOException{
		String line;
		String result="";
		while ((line = brOut.readLine()) != null) {
			if(!result.equals(""))
				result += "\n"+line;
			else
				result = line;
		}
		
		return result;
	}
	
	
	public String getReturnProcessErr() throws IOException{
		String line;
		String result="";
		while ((line = brErr.readLine()) != null) {
			if(!result.equals(""))
				result += "\n"+line;
			else
				result = line;
		}
		
		return result;
	}
	
	
	public void destroy(){
		try{
			wrIn.close();
			brErr.close();
			brOut.close();
		}catch(Exception e){}
		
		process.destroy();
	}
}
