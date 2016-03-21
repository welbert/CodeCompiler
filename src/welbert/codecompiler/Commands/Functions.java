package welbert.codecompiler.Commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;


public class Functions {
	boolean Windows = System.getProperty("os.name").contains("Windows");
	
	public String[] getCompilers(){
		String tempResult;
		ArrayList<String> compilers = new ArrayList<String>();
		try {
			RunProcess runProcess = new RunProcess("gcc --version");
			tempResult=runProcess.getReturnProcessOut();
			compilers.add("gcc - "+tempResult.split(Pattern.quote(")"), 2)[1].split("\n",2)[0]);
		} catch (IOException e) {}
		try {
			RunProcess runProcess = new RunProcess("g++ --version");
			tempResult=runProcess.getReturnProcessOut();
			compilers.add("g++ - "+tempResult.split(Pattern.quote(")"), 2)[1].split("\n",2)[0]);
		} catch (IOException e) {}
		try {
			RunProcess runProcess = new RunProcess("java -version");
			tempResult=runProcess.getReturnProcessOut();
			compilers.add("java - "+tempResult.split(Pattern.quote("\""), 2)[1].split("\n",2)[0]);
		} catch (Exception e) {e.printStackTrace();}
		if(compilers.isEmpty())
			return null;
		else{
			String[] result = new String[compilers.size()];
			for(int i = 0;i<compilers.size();i++)
				result[i] = compilers.get(i).toString();
			
			return result;
		}
	}
}
