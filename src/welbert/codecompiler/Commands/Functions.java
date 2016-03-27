package welbert.codecompiler.Commands;

import java.awt.Color;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.regex.Pattern;

import javax.swing.JTextPane;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import welbert.codecompiler.staticsvalues.Config;
import welbert.codecompiler.utils.Arquivo;
import welbert.codecompiler.utils.diff_match_patch;
import welbert.codecompiler.utils.diff_match_patch.Diff;



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
	
	/**
	 * @author Welbert Serra
	 * @param asName - Nome do Arquivo
	 * @param asLinguagem - Linguagem de programação para determinar a extensão
	 * @return O caminho do arquivo tratado
	 */
	public String getPathFileCode(String asName,String asLinguagem){
		String path;
		if(Windows)
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
	
	/**
	 * @author Welbert Serra
	 * @param asFile - Nome do Arquivo
	 * @param asCompiler - Compilador a ser usado
	 * @return O stdout do Processo
	 * @throws Exception - Se houver falha ao executar o processo 
	 */
	public Object[] runCompileInCode(String asFile, String asCompiler) throws Exception {
		String out = "";boolean success = false;
		RunProcess process;
		
		switch (asCompiler) {
		case "gcc":
			process = new RunProcess(new String[]{asCompiler,asFile});
			out = process.getReturnProcessOut();
			if(out.trim().equals("")){
				process = new RunProcess(new String[]{"./a.out"});
				out = process.getReturnProcessOut();
				success = true;
			}
			break;
		case "g++":
			process = new RunProcess(new String[]{asCompiler,asFile});
			out = process.getReturnProcessOut();
			if(out.trim().equals("")){
				process = new RunProcess(new String[]{"./a.out"});
				out = process.getReturnProcessOut();
				success = true;
			}
			
			break;		
		case "java":
			process = new RunProcess(new String[]{asCompiler+"c",asFile});
			out = process.getReturnProcessOut();
			if(out.trim().equals("")){
				process = new RunProcess(new String[]{"java","CodeCompiler"});
				out = process.getReturnProcessOut();
				success = true;
			}
			
			break;

		default:
			break;
		}
		return new Object[]{success,out};
	}
	
	public boolean diffStrings(String in1,String in2,JTextPane textArea) throws Exception{	
		StyledDocument doc = textArea.getStyledDocument();
		diff_match_patch dmp = new diff_match_patch();
		LinkedList<Diff> llDiff =  dmp.diff_main(in1, in2);
		boolean lbCorrect = true;
		
		Style styleCorrect = textArea.addStyle("Line Correct", null);
        StyleConstants.setForeground(styleCorrect, Color.green);
        Style styleWrong = textArea.addStyle("Line Wrong", null);
        StyleConstants.setForeground(styleWrong, Color.red);
        Style styleDefault = textArea.addStyle("Line Default", null);
        StyleConstants.setForeground(styleDefault, Color.black);
        
        doc.insertString(doc.getLength(), "\n-----------------\n",styleDefault);
		for(int i = 0; i<llDiff.size();i++){
			if(llDiff.get(i).operation == diff_match_patch.Operation.EQUAL){
				doc.insertString(doc.getLength(), llDiff.get(i).text,styleCorrect);
			}else{
				doc.insertString(doc.getLength(), llDiff.get(i).text,styleWrong);
				lbCorrect = false;
			}
		}
		return lbCorrect;
	}
	
	public void alterTextPane(String text,JTextPane textArea) throws Exception{
		StyledDocument doc = textArea.getStyledDocument();
		Style styleDefault = textArea.addStyle("Line Default", null);
        StyleConstants.setForeground(styleDefault, Color.black);
        doc.insertString(doc.getLength(), "\n-----------------\n",styleDefault);
        doc.insertString(doc.getLength(), text,styleDefault);
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
