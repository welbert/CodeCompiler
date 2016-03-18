package welbert.codecompiler.Commands;

import java.io.IOException;
import java.util.ArrayList;

public class Functions {
	boolean Windows = System.getProperty("os.name").contains("Windows");
	
	public String[] getCompilers(){
		ArrayList<String> compilers = new ArrayList<String>();
		try {
			RunProcess runProcess = new RunProcess("gcc");
			return new String[]{runProcess.getReturnProcessOut()+":::\n"+runProcess.getReturnProcessErr()};
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
