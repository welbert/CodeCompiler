package welbert.codecompiler.Commands;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

import welbert.codecompiler.staticsvalues.Config;
import welbert.codecompiler.utils.Arquivo;



public class Functions {
	boolean Windows = Config.WINDOWS;
	
	public void openFile(File afFile) throws Exception{
		if (Windows) {
		  String cmd = "rundll32 url.dll,FileProtocolHandler " + afFile.getCanonicalPath();
		  Runtime.getRuntime().exec(cmd);
		}else {						
			Desktop.getDesktop().open(afFile);						
		}
	}
	
	public String getPathFileCode(String name,String extension){
		String path;
		if(Windows)
			path = "Problems\\"+extension+"\\"+name+"\\";
		else
			path = "Problems//"+extension+"//"+name+"//";
		
		
		switch (extension) {
		case "gcc":
			return path+name+".c";
		case "g++":
			return path+name+".cpp";			
		case "java":
			return path+name+".java";
		case "config":
			return path+name+".wcd";

		default:
			return path+name;
		}
	}
	
	public boolean existsFile(String file){
		return new File(file).exists();
	}
	
	public Arquivo createNewFile(String name,String extension) throws IOException{
		Arquivo file;
		
		file = new Arquivo(getPathFileCode(name,extension));
		file.deletarArquivo();
		switch (extension) {
		case "gcc":
			file.salvar(Config.templateC);
			break;
		case "g++":
			file.salvar(Config.templateCpp);
			break;
			
		case "java":
			file.salvar(Config.templateJava);
			break;

		default:
			break;
		}
		
		return file;
		
	}
	
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
			RunProcess runProcess = new RunProcess("java -version");
			tempResult=runProcess.getReturnProcessOut();
			compilers.add("java - "+tempResult.split(Pattern.quote("\""), 2)[1].split("\n",2)[0]);
			runProcess.destroy();
		} catch (Exception e) {}
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
