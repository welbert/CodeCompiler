package welbert.codecompiler.utils;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


/* Version: 1.2
 * Construtor:
 * 	Arquivo(String nome); Nome se refere ao nome do arquivo
 * 
 * Comandos:
 * 	salva(String texto);Ele salvara no arquivo a string e pular� a linha no arquivo
 *  
 *  reset(); Ele reiniciar� o carregar(), apontando para o inicio do arquivo
 *  
 *  deteletarDado(int indicie);Ele deleta a linha indicada no indice
 *  
 *  modificar(int indice, String palavra); Ele modifica a linha indicada no indice
 *pela String palavra passada por parametro
 *	
 *	carregar();Ele retornar� a String presente na linha e pular� a linha do arquivo,
 *caso eu tente ler uma linha null, ele dar� um reset() e ser� lido a primeira linha
 *
 *	NEOF(); Retornar� um boolean indicando se o arquivo est� apontando para depois
 *da ultima linha. True = N�o � fim de arquivo || False = Fim de arquivo
 *
 *	fecha();Ele encerra o arquivo, este comando deve ser SEMPRE executado ao fim do
 *programa
 *
 *	deletarArquivo();Ele cria um novo arquivo em branco com o mesmo nome
 *
 *	getPathName();Retorna o caminho em que est� localizado o arquivo mais o nome
 *do arquivo
 *
 *	getFileName();Retorna o nome do arquivo
 *
 *	Arquivo.java by Welbert Serra
 *		Se houver algum erro, enviar para welberts@gmail.com
*/
public class Arquivo  {
	private File arquivo;
	
	private FileWriter fw ;
	private BufferedWriter bw;
	private FileReader fr;
	private BufferedReader br;

	public Arquivo(String nome)throws IOException{		
		//nome = nome.concat(".txt");
		arquivo = new File(nome);
		
		String sep = "\\";
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
			
		}fw.close();
		bw.close();
	}

	public void criaArquivo() throws IOException{
		if(!arquivo.exists()){
			arquivo.createNewFile();
		}
	}

	public void salvar(String texto)throws IOException{
		FileWriter fw = new FileWriter(arquivo, true);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(texto);
		bw.newLine();
		bw.close();
		fw.close();
	}
	
	public void reset() throws IOException{
		br.close();
		fr.close();
		this.fr = new FileReader(arquivo);
		this.br = new BufferedReader(fr);

	}
	
	public void deletarDado(int indice) throws IOException{
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
			System.out.printf("\nA posicao "+indice+" n�o foi encontrada\n");
		}
		reset();
	}
	
	public void modificar(int indice, String palavra) throws IOException{
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
			System.out.printf("\nA posicao "+indice+" n�o foi encontrada\n");
		}
		reset();
		
	}
	
	public String carregar() throws IOException{
		if(NEOF()){
		return br.readLine();
		}else{
			reset();
		return 	br.readLine();
		}		
	}

	public boolean NEOF() throws IOException{
		return br.ready();
	}
	
	public void fecha() throws IOException{
		br.close();
		fr.close();
	}
	
	public void deletarArquivo() throws IOException{
		FileWriter fw = new FileWriter(arquivo, false);
		BufferedWriter bw = new BufferedWriter(fw);
		fw.close();
		bw.close();
	}

	public String getPathName(){
		String sep = "\\";
		int lastindex = arquivo.getAbsolutePath().lastIndexOf(sep);
		if(lastindex!=-1)			
			return arquivo.getAbsolutePath().substring(0,lastindex);
		else
			return arquivo.getAbsolutePath();
	}
	
	public String getFileName(){
		return arquivo.getName();
	}

}
