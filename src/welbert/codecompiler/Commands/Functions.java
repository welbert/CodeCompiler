package welbert.codecompiler.Commands;

import java.awt.Color;
import java.io.File;
import java.io.IOException;

import javax.swing.JTextPane;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import welbert.codecompiler.utils.Arquivo;



public class Functions {
	
	/**
	 * Abri o arquivo no programa padrão da extensão
	 * 
	 * @param afFile - File que se deseja abrir
	 * @throws Exception
	 * @author Welbert Serra
	 */
	public void openFile(File afFile) throws Exception{
		new FunctionsArquivo().openFile(afFile);
	}
	
	/**
	 * Obtem o nome do caminho com o arquivo baseado no compilador
	 * 
	 * @param asName - Nome do arquivo
	 * @param asLinguagem - Nome do Compilador usado (gcc,g++,java)
	 * @return Caminho do Arquivo
	 * @author Welbert Serra
	 */
	public String getPathFileCode(String asName,String asLinguagem){
		return new FunctionsArquivo().getPathFileCode(asName, asLinguagem);
	}
	
	/**
	 * 
	 * @param file - Caminho do arquivo
	 * @return true - se existe o arquivo ; false - se não existe o arquivo
	 * @author Welbert Serra
	 */
	public boolean existsFile(String file){
		return new FunctionsArquivo().existsFile(file);
	}
	
	/**
	 * Cria um novo arquivo baseado no compilador e usando o template de Config
	 * 
	 * @param name - Nome do arquivo
	 * @param extension - Compilador a ser usado (gcc,g++,java)
	 * @return Arquivo
	 * @throws IOException
	 * @author Welbert Serra
	 */
	public Arquivo createNewFile(String name,String extension) throws IOException{
		return new FunctionsArquivo().createNewFile(name, extension);
		
	}
	
	/**
	 * Função responsável pela execução e compilação do código
	 * 
	 * @param aaFile - Arquivo do código a ser compilado e executado
	 * @param asCompiler - Compilador a ser usado (gcc,g++,java)
	 * @param stdin - Texto de entrada
	 * @param timelimit - Timelimit do código
	 * @return Object[] - [0] Se houve sucesso na compilação/execução; [1] Mensagem retornada; [2] Duração do processo
	 * @throws Exception
	 * @author Welbert Serra
	 */
	public Object[] runCompileInCode(Arquivo aaFile, String asCompiler,String stdin,int timelimit) throws Exception {
		return new FunctionsProcess().runCompileInCode(aaFile, asCompiler,stdin, timelimit);
	}	
	
	/**
	 * Compara dois texto, printando o excesso do segundo texto e retornando se são iguais
	 * 
	 * @param in1 - Texto que será comparado com o primeiro
	 * @param in2 - Texto que será comparado com o segundo
	 * @param time - Tempo que levou o processo executando
	 * @param textArea - JtextPane que sofrerá a mudança
	 * @return true: Os textos são identicos; false: Os textos são diferentes
	 * @throws Exception
	 * @author Welbert Serra
	 */
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
	
	/**
	 * Altera o JTextPane inserindo o novo texto
	 * 
	 * @param text - Texto para inserir
	 * @param textArea - JTextPane que sofrerá a mudança
	 * @throws Exception
	 * @author Welbert Serra 
	 */
	public void alterTextPane(String text,JTextPane textArea) throws Exception{
		StyledDocument doc = textArea.getStyledDocument();
		Style styleDefault = textArea.addStyle("Line Default", null);
        StyleConstants.setForeground(styleDefault, Color.black);
        doc.insertString(doc.getLength(), "\n----------------\n",styleDefault);
        doc.insertString(doc.getLength(), text,styleDefault);
	}
	
	/**
	 * Retorna se na maquina foi encontrado o (gcc/g++/java)
	 * 
	 * @return Retorna se na maquina foi encontrado o (gcc/g++/java)
	 */
	public String[] getCompilers(){
		return new FunctionsProcess().getCompilers();
	}
}
