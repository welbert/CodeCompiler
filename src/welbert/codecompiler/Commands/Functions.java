package welbert.codecompiler.Commands;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.swing.JTextPane;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import welbert.codecompiler.utils.Arquivo;



public class Functions {
	
	public void openFile(File afFile) throws Exception{
		new FunctionsArquivo().openFile(afFile);
	}
	
	/**
	 * @author Welbert Serra
	 * @param asName - Nome do Arquivo
	 * @param asLinguagem - Linguagem de programação para determinar a extensão
	 * @return O caminho do arquivo tratado
	 */
	public String getPathFileCode(String asName,String asLinguagem){
		return new FunctionsArquivo().getPathFileCode(asName, asLinguagem);
	}
	
	public boolean existsFile(String file){
		return new FunctionsArquivo().existsFile(file);
	}
	
	public Arquivo createNewFile(String name,String extension) throws IOException{
		return new FunctionsArquivo().createNewFile(name, extension);
		
	}
	
	/**
	 * @author Welbert Serra
	 * @param asFile - Nome do Arquivo
	 * @param asCompiler - Compilador a ser usado
	 * @return O stdout do Processo
	 * @throws Exception - Se houver falha ao executar o processo 
	 */
	public Object[] runCompileInCode(Arquivo aaFile, String asCompiler,int timelimit) throws Exception {
		return new FunctionsProcess().runCompileInCode(aaFile, asCompiler, timelimit);
	}
	
	public boolean timeRunProcess(RunProcess process,int timelimit) throws InterruptedException{
		return new FunctionsProcess().timeRunProcess(process, timelimit);
	}
	
	public boolean diffStrings(String in1,String in2,int time,JTextPane textArea) throws Exception{	
		StyledDocument doc = textArea.getStyledDocument();
		String[] text1 = in1.split("\n");
		String[] text2 = in2.split("\n");
		int lenIn1 = text1.length,lenIn2 = text2.length;
		boolean lbCorrect = true;
		
		Style styleCorrect = textArea.addStyle("Line Correct", null);
        StyleConstants.setForeground(styleCorrect, Color.green);
        Style styleWrong = textArea.addStyle("Line Wrong", null);
        StyleConstants.setForeground(styleWrong, Color.red);
        Style styleDefault = textArea.addStyle("Line Default", null);
        StyleConstants.setForeground(styleDefault, Color.black);
        
        doc.insertString(doc.getLength(), "\n--------"+time+"sec's --------\n",styleDefault);
        int i;
        for(i = 0;i<lenIn1 && i<lenIn2;i++){
        	if(text1[i].equals(text2[i]))
        		doc.insertString(doc.getLength(), text1[i]+"\n",styleCorrect);
        	else{
        		doc.insertString(doc.getLength(), text1[i]+"\n",styleWrong);
        		lbCorrect = false;
        	}
        }
        
        if(lenIn1!=lenIn2)
        	lbCorrect = false;
        
        for(;i<lenIn2;i++){
        	doc.insertString(doc.getLength(), text1[i],styleWrong);
        	lbCorrect = false;
        }
		
		return lbCorrect;
	}
	
	public void alterTextPane(String text,JTextPane textArea) throws Exception{
		StyledDocument doc = textArea.getStyledDocument();
		Style styleDefault = textArea.addStyle("Line Default", null);
        StyleConstants.setForeground(styleDefault, Color.black);
        doc.insertString(doc.getLength(), "\n----------------\n",styleDefault);
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
