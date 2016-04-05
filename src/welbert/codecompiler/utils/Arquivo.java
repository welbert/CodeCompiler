package welbert.codecompiler.utils;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


/**
 * Version: 1.4
 *		Se houver algum erro, enviar para welberts@gmail.com
 * @author Welbert Serra
 *
 */
public class Arquivo  {
	private File arquivo;
	
	public static boolean WINDOWS = System.getProperty("os.name").contains("Windows");
	
	private FileWriter fw ;
	private BufferedWriter bw;
	private FileReader fr;
	private BufferedReader br;

	/**
	 * 
	 * @param nome - Nome do arquivo ou caminho com nome do arquivo
	 * @throws IOException
	 * @author Welbert Serra
	 */
	public Arquivo(String nome)throws IOException{		
		arquivo = new File(nome);
		String sep;
		if(WINDOWS)
			sep = "\\";
		else
			sep = "/";
		
		int lastindex = nome.lastIndexOf(sep);
		if(lastindex!=-1){
			File dirMake = new File(nome.substring(0,lastindex));
			if(!dirMake.exists())
				dirMake.mkdirs();
		}
		
		fw = new FileWriter(arquivo, true);
		bw = new BufferedWriter(fw);
		fr = new FileReader(arquivo);
		br = new BufferedReader(fr);

		if(arquivo.exists()){
			arquivo.createNewFile();			
		}
		fw.close();
		bw.close();
	}

	/**
	 * Cria o arquivo caso não exista o arquivo.
	 * @throws IOException
	 * @author Welbert Serra
	 */
	public void criaArquivo() throws IOException{
		if(!arquivo.exists()){
			arquivo.createNewFile();
		}
	}
	
	/**
	 * Retorna o File utilizado no arquivo
	 * @return File
	 * @author Welbert Serra
	 */
	public File getFile(){
		return arquivo;
	}

	/**
	 * Salva uma Stringa e automaticamente quebra linha
	 * @param texto - String do que se deseja salvar
	 * @throws IOException
	 * @author Welbert Serra
	 */
	public void salvar(String texto)throws IOException{
		FileWriter fw = new FileWriter(arquivo, true);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(texto);
		bw.newLine();
		bw.close();
		fw.close();
	}
	
	/**
	 * Reinicia o ponteiro de leitor do arquivo
	 * @throws IOException
	 * @author Welbert Serra
	 */
	public void reset() throws IOException{
		br.close();
		fr.close();
		this.fr = new FileReader(arquivo);
		this.br = new BufferedReader(fr);

	}
	
	/**
	 * Deletar uma linha do arquivo
	 * @param indice - Linha que será deletada
	 * @return String - Mensagem do erro ocorrido, senão String vazia
	 * @throws IOException
	 * @author Welbert Serra
	 */
	public String deletarDado(int indice) throws IOException{ //Ineficiente
		reset();
		ArrayList<String> aux= new ArrayList<String>(); 
		while(NEOF()){
			aux.add(carregar());
		}
		if(indice<aux.size()){
			deletarArquivo();
			criaArquivo();
				for(int i=0;i<aux.size();i++){
					if(indice == i){
						i++;
					}if(i<aux.size()){	
					salvar(aux.get(i));
					}
				}
		}else{
			return "A posicao "+indice+" não foi encontrada";
		}
		reset();
		return "";
	}
	
	/**
	 * Modifica uma linha por uma nova String
	 * @param indice - Linha
	 * @param palavra - Nova String
	 * @throws IOException
	 * @author Welbert Serra
	 */
	public String modificar(int indice, String palavra) throws IOException{ //Ineficiente
		reset();
		ArrayList<String> aux= new ArrayList<String>(); 
		while(NEOF()){
			aux.add(carregar());
		}
		if(indice<aux.size()){
			aux.set(indice, palavra);
			deletarArquivo();
			criaArquivo();
				for(int i=0;i<aux.size();i++){
					salvar(aux.get(i));
				}
		}else{
			return "A posicao "+indice+" não foi encontrada";
		}
		reset();
		return "";
		
	}
	
	/**
	 * Carrega uma linha do arquivo e aponta pra próxima linha
	 * @return Linha do arquivo
	 * @throws IOException
	 * @author Welbert Serra
	 */
	public String carregar() throws IOException{
		if(NEOF()){
			return br.readLine();
		}else{
			reset();
		return 	br.readLine();
		}		
	}

	/**
	 * Não End Of File
	 * @return
	 * @throws IOException
	 * @author Welbert Serra
	 */
	public boolean NEOF() throws IOException{
		return br.ready();
	}
	
	/**
	 * Fecha o Arquivo
	 * @throws IOException
	 * @author Welbert Serra
	 */
	public void fecha() throws IOException{
		br.close();
		fr.close();
	}
	
	/**
	 * Limpa o Arquivo
	 * @throws IOException
	 * @author Welbert Serra
	 */
	public void deletarArquivo() throws IOException{
		FileWriter fw = new FileWriter(arquivo, false);
		BufferedWriter bw = new BufferedWriter(fw);
		fw.close();
		bw.close();
	}

	/**
	 * Retorna o caminho do Arquivo
	 * @return String
	 * @author Welbert Serra
	 */
	public String getPathName(){
		return arquivo.getParent();
	}
	
	/**
	 * Retorna o Caminho Absoluto
	 * @return String
	 * @author Welbert Serra
	 */
	public String getAbsolutePath(){
		return arquivo.getAbsolutePath();
	}
	
	/**
	 * Retorna o caminho Relativo
	 * @return String
	 * @author Welbert Serra
	 */
	public String getRelativePath(){
		String path = arquivo.getAbsolutePath();
		String base = new File(".").getAbsolutePath();
		String relative = new File(base).toURI().relativize(new File(path).toURI()).getPath();
		return relative;
	}
	
	/**
	 * Retorna o nome do arquivo
	 * @return String
	 * @author Welbert Serra
	 */
	public String getFileName(){
		return arquivo.getName();
	}

}
