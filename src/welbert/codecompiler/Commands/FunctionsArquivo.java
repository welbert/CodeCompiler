package welbert.codecompiler.Commands;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import welbert.codecompiler.staticsvalues.Config;
import welbert.codecompiler.utils.Arquivo;

public class FunctionsArquivo {
	
	public boolean existsFile(String file){
		return new File(file).exists();
	}
	
	public void openFile(File afFile) throws Exception{
		if (Config.WINDOWS) {
		  String cmd = "rundll32 url.dll,FileProtocolHandler " + afFile.getCanonicalPath();
		  Runtime.getRuntime().exec(cmd);
		}else {						
			Desktop.getDesktop().open(afFile);						
		}
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
	
	public String getPathFileCode(String asName,String asLinguagem){
		String path;
		if(Config.WINDOWS)
			path = "Problems\\"+asLinguagem+"\\"+asName+"\\";
		else
			path = "Problems//"+asLinguagem+"//"+asName+"//";
		
		
		switch (asLinguagem) {
			case "gcc":
				return path+asName+".c";
			case "g++":
				return path+asName+".cpp";			
			case "java":
				return path+asName+".java";
			case "config":
				return path+asName+".wcd";
	
			default:
				return path+asName;
		}
	}

}
