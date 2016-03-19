package welbert.codecompiler.Commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;


public class Functions {
	boolean Windows = System.getProperty("os.name").contains("Windows");
	
	public String[] getCompilers(){
		ArrayList<String> compilers = new ArrayList<String>();
		try {
			RunProcess runProcess = new RunProcess("gcc --version");
			compilers.add("gcc"+runProcess.getReturnProcessOut().split(Pattern.quote(")"), 2)[1].split("\n",2)[0]);
		} catch (IOException e) {}
		try {
			RunProcess runProcess = new RunProcess("g++ --version");
			compilers.add("g++"+runProcess.getReturnProcessOut().split(Pattern.quote(")"), 2)[1].split("\n",2)[0]);
		} catch (IOException e) {}
		try {
			RunProcess runProcess = new RunProcess("java -version");
			compilers.add("java"+runProcess.getReturnProcessOut().split(Pattern.quote(")"), 2)[1].split("\n",2)[0]);
		} catch (IOException e) {}
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
