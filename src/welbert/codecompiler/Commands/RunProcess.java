package welbert.codecompiler.Commands;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class RunProcess {

	BufferedReader brOut;
	BufferedReader brErr;
	BufferedWriter wrIn;
	
	public RunProcess(String commandName) throws IOException{
			Process process = new ProcessBuilder(
					commandName).start();
					InputStream isErr = process.getErrorStream();
					InputStreamReader isrErr = new InputStreamReader(isErr);
					brErr = new BufferedReader(isrErr);
					InputStream stdOut = process.getInputStream();
					InputStreamReader isrOut = new InputStreamReader(stdOut);
					brOut = new BufferedReader(isrOut);
					OutputStream stdin = process.getOutputStream ();
					wrIn = new BufferedWriter(new OutputStreamWriter(stdin));			
	}
	
	public String getReturnProcess() throws IOException{
		String line;
		String result="";
		while ((line = brOut.readLine()) != null) {
		  result += line;
		}
		
		return result;
	}
}
