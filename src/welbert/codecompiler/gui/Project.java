package welbert.codecompiler.gui;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import net.miginfocom.swing.MigLayout;
import welbert.codecompiler.Commands.Functions;
import welbert.codecompiler.staticsvalues.Config;
import welbert.codecompiler.utils.Arquivo;

import javax.swing.JTextField;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextPane;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@SuppressWarnings("serial")
public class Project extends JInternalFrame 
					implements ActionListener {
	private JTextField txtProblem;
	private JTextField txtTimelimit;
	
	private JLabel lblProblem;
	
	private JTextPane txtpnStdin;
	private JTextPane txtpnStdOut;
	private JTextPane txtpnStdOutcode;
	
	private JButton btnSubmit;	
	private JButton btnEdit;
	private JButton btnClear;
	
	private JComboBox<String> comboBoxCompilers;
	
	private Functions myFunctions = new Functions();
	private Arquivo codeFile;
	private Arquivo configFile;
	private JButton btnSave;
	private JLabel label;

	/**
	 * Create the frame.
	 */
	public Project(String asProject,String[] aascompilers,String asconfigFile){
		
		setClosable(true);
		setIconifiable(true);
		setMaximizable(true);
		setTitle(asProject);
		setResizable(true);
		setBounds(100, 100, 650, 400);
		getContentPane().setLayout(new MigLayout("", "[grow][95.00,grow][][grow][][][grow][][][][][grow][][][][][][][][grow][][][]", "[][][][][grow][][][][][grow][grow][][][][][][][][][][][][][][]"));
		
		lblProblem = new JLabel("Problem: ");
		getContentPane().add(lblProblem, "cell 0 0,alignx trailing");
		
		txtProblem = new JTextField();
		txtProblem.setToolTipText("Nome do problema");
		getContentPane().add(txtProblem, "cell 1 0 6 1,growx,aligny center");
		txtProblem.setColumns(10);
		
		JLabel lblTimelimit = new JLabel("Time Limit: ");
		getContentPane().add(lblTimelimit, "cell 10 0 2 1,alignx right,aligny center");
		
		txtTimelimit = new JTextField();
		txtTimelimit.setToolTipText("Tempo limite em segundos de execução do código. 0 = "+Config.MAXTIMELIMIT+"s");
		txtTimelimit.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent ev) {
				if(ev.getKeyCode()<48 || ev.getKeyCode()>57){
					if(txtTimelimit.getText().length()>0)
						txtTimelimit.setText(txtTimelimit.getText().substring(0, txtTimelimit.getText().length()-1));
				}					
			}
		});
		getContentPane().add(txtTimelimit, "cell 12 0,alignx left,aligny center");
		txtTimelimit.setColumns(10);
		
		btnSave = new JButton("Save");
		btnSave.setToolTipText("Salva as novas configurações");
		btnSave.setActionCommand("SAVE");
		btnSave.setVisible(false);
		btnSave.addActionListener(this);
		getContentPane().add(btnSave, "cell 16 0");
		
		label = new JLabel("?");
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				showMessage("Matenha o mouse sobre os campos para mais informações");
			}
		});
		label.setToolTipText("Matenha o mouse sobre os campos para mais informações");
		
		getContentPane().add(label, "cell 22 0,alignx right");
		
		JLabel lblStdout = new JLabel("stdout");
		getContentPane().add(lblStdout, "cell 0 2");
		
		JLabel lblStdoutcode = new JLabel("stdoutCode");
		getContentPane().add(lblStdoutcode, "cell 12 2");
		
		
		txtpnStdOut = new JTextPane();
		txtpnStdOut.setToolTipText("Saída esperada do código");
		JScrollPane scrollOut = new JScrollPane();
		scrollOut.setViewportView(txtpnStdOut);
		getContentPane().add(scrollOut, "cell 0 3 2 5,grow");
		
		txtpnStdOutcode = new JTextPane();
		txtpnStdOutcode.setToolTipText("Saída gerada pela compilação ou execução do código");
		txtpnStdOutcode.setEditable(false);
		JScrollPane scrolloutcode = new JScrollPane();
		scrolloutcode.setViewportView(txtpnStdOutcode);
		getContentPane().add(scrolloutcode, "cell 3 4 20 16,grow");
		
		JLabel lblStdin = new JLabel("stdin");		
		getContentPane().add(lblStdin, "cell 0 8");
		
		txtpnStdin = new JTextPane();
		txtpnStdin.setToolTipText("Valores de entrada para o código");
		JScrollPane scrollIn = new JScrollPane();
		scrollIn.setViewportView(txtpnStdin);
		getContentPane().add(scrollIn, "cell 0 9 2 16,grow");
		
		btnEdit = new JButton("Edit");
		btnEdit.setToolTipText("Abri o editor padrão para o código selecionado");
		btnEdit.setActionCommand("EDIT");
		btnEdit.addActionListener(this);		
		
		
		btnClear = new JButton("Clear");
		btnClear.setToolTipText("Limpa o stdoutCode");
		getContentPane().add(btnClear, "cell 12 22,alignx center");
		btnClear.setActionCommand("CLEAR");
		btnClear.addActionListener(this);
		getContentPane().add(btnEdit, "cell 22 22");
		
		comboBoxCompilers = new JComboBox<String>(aascompilers);
		comboBoxCompilers.setToolTipText("Seleciona o compilador");
		getContentPane().add(comboBoxCompilers, "cell 2 24 18 1,growx,aligny center");
				
		btnSubmit = new JButton("New");		
		if(!asconfigFile.trim().equals("")){
			btnSubmit.setText("Compile");
			btnSubmit.setActionCommand("SUBMIT");
			btnEdit.setVisible(true);
			btnClear.setVisible(true);
			btnSubmit.setToolTipText("Compila e executa o código");
		}else{			
			btnSubmit.setText("New");
			btnSubmit.setActionCommand("NEW");
			btnEdit.setVisible(false);
			btnClear.setVisible(false);
			btnSubmit.setToolTipText("Cria um novo arquivo de configuração e Código");
		}
		btnSubmit.addActionListener(this);
		getContentPane().add(btnSubmit, "cell 22 24");
	
		//Load file
		if(!asconfigFile.trim().equals("")){
			try {
				loadConfigFile(asconfigFile);
			} catch (Exception e) {
				String message;
				if(Config.WINDOWS)
					message = "Erro 105 - Falha ao carregar o arquivo de configura��o ou arquivo corrompido.";
				else
					message = "Erro 105 - Falha ao carregar o arquivo de configuração ou arquivo corrompido.";
				
				log(message,e.toString());
				this.doDefaultCloseAction();
				return;
			}
			
		}

	}

	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		
		case "NEW":

			if(!validateFields())
				return;
			
			String problemName = txtProblem.getText().replace(" ", "");
			String extension = comboBoxCompilers.getSelectedItem().toString().split(" ",2)[0];
			String pathFileProblem = myFunctions.getPathFileCode(problemName, extension);
			if(myFunctions.existsFile(pathFileProblem)){
				String message;
				if(Config.WINDOWS)
					message = "O arquivo "+pathFileProblem+" j� existe. Deseja substituir?";
				else
					message = "O arquivo "+pathFileProblem+" já existe. Deseja substituir?";
				
				switch (showConfirmDialog(message)) {
				case JOptionPane.YES_OPTION:
					try{
						codeFile = myFunctions.createNewFile(problemName, extension);
					}catch (Exception ex) {
						this.log("Erro 102 - Falha ao criar o Arquivo " + codeFile.getAbsolutePath(),
									ex.toString());
						return;
					}
					try{
						myFunctions.openFile(codeFile.getFile());
					}catch (Exception ex) {
						this.log("Erro 103 - Falha ao abrir o Arquivo "+codeFile.getAbsolutePath(), 
									ex.toString());
					}
					try{
						newConfigFile(codeFile.getAbsolutePath(),problemName);
					}catch (Exception ex) {
						if(Config.WINDOWS){
							this.log("Erro 104 - Falha ao criar o Arquivo de configura��o", 
									ex.toString());
						}else{
							this.log("Erro 104 - Falha ao criar o Arquivo de configuração", 
									ex.toString());
						}
							
					}
					break;

				default:
					break;
				}			
			}else{
				try{
					codeFile = myFunctions.createNewFile(problemName, extension);
				}catch (Exception ex) {
					this.log("Erro 102 - Falha ao criar o Arquivo " + codeFile.getAbsolutePath(),
								ex.toString());
					return;
				}
				try{
					myFunctions.openFile(codeFile.getFile());
				}catch (Exception ex) {
					this.log("Erro 103 - Falha ao abrir o Arquivo "+codeFile.getAbsolutePath(), 
								ex.toString());
				}
				try{
					newConfigFile(codeFile.getAbsolutePath(),problemName);
				}catch (Exception ex) {
					if(Config.WINDOWS){
						this.log("Erro 104 - Falha ao criar o Arquivo de configura��o", 
								ex.toString());
					}else{
						this.log("Erro 104 - Falha ao criar o Arquivo de configuração", 
								ex.toString());
					}
				}
			}
		break;
		
		case "SUBMIT":
			Object[] loOut;
			String lsStdOutCode;
			try {			
				int timelimit;
				try{
					timelimit = Integer.parseInt(txtTimelimit.getText());
				}catch(Exception ex){
					timelimit = 0;
					txtTimelimit.setText("0");
				}
				loOut = myFunctions.runCompileInCode(codeFile, 
							comboBoxCompilers.getSelectedItem().toString().split(" ",2)[0],
							txtpnStdin.getText(),
							timelimit);
							
			} catch (Exception ex) {
				if(Config.WINDOWS){
					log("Erro 106 - Falha ao executar o compilador ou a executar o c�digo.\n"
							+ "Mensagem retornada: "+ex.toString(),ex.toString());
				}else{
					log("Erro 106 - Falha ao executar o compilador ou a executar o código.\n"
							+ "Mensagem retornada: "+ex.toString(),ex.toString());
				}
				return;
			}	
						
			lsStdOutCode=txtpnStdOutcode.getText();	
			if((boolean)loOut[0]){
				try{
					boolean isCorrect;
					isCorrect = myFunctions.diffStrings(txtpnStdOut.getText(),
							String.valueOf(loOut[1]),
							Integer.parseInt(loOut[2].toString())/1000,
							txtpnStdOutcode);
					if(isCorrect)
						if(Config.WINDOWS)
							showMessage("O stdOut e StdOutCode s�o id�nticos.");
						else
							showMessage("O stdOut e StdOutCode são idênticos.");
					else
						if(Config.WINDOWS)
							showMessage("O stdOut e StdOutCode est�o diferentes.");
						else
							showMessage("O stdOut e StdOutCode estão diferentes.");
					
				}catch (Exception ex) {
					txtpnStdOutcode.setText(lsStdOutCode+"\n--------"+Integer.parseInt(loOut[2].toString())/1000+
							"sec's --------\n"+loOut[1]);
					log(ex.toString());
				}
			}else{
				try{
					String result = String.valueOf(loOut[1]);
					if(!result.startsWith("TLE"))
						myFunctions.alterTextPane(result,txtpnStdOutcode);
					else
						showMessage(result);
					
				}catch (Exception ex) {
					txtpnStdOutcode.setText(lsStdOutCode+"\n----------------\n"+loOut[1]);
					log(ex.toString());
				}
			}
			
				
			
		break;
		
		case "EDIT":
			try {
				myFunctions.openFile(codeFile.getFile());
			} catch (Exception e1) {
				this.log("Erro 101 - Falha ao editar "+codeFile.getAbsolutePath(), e1.toString());
			}
		break;
		
		case "SAVE":
			String problemName1 = txtProblem.getText().replace(" ", "");
			try{
				newConfigFile(codeFile.getAbsolutePath(),problemName1);
			}catch (Exception ex) {
				if(Config.WINDOWS){
					this.log("Erro 104 - Falha ao criar o Arquivo de configura��o", 
							ex.toString());
				}else{
					this.log("Erro 104 - Falha ao criar o Arquivo de configuração", 
							ex.toString());
				}
			}
			break;
		
		case "CLEAR":
			txtpnStdOutcode.setText("");
			break;
		
		default:
			break;
		}		
	}
	
	private void loadConfigFile(String file) throws IOException{
		String aux="",aux2="";
		configFile = new Arquivo(file);
		txtProblem.setText(configFile.carregar());
		codeFile = new Arquivo(configFile.carregar());
		try{
			int timelimit = Integer.parseInt(configFile.carregar());
			txtTimelimit.setText(timelimit+"");
		}catch(Exception e){txtTimelimit.setText("0");}
		aux = configFile.carregar();
		while(!aux.equals("---")){
			aux2 = txtpnStdin.getText();
			if(!aux2.trim().equals(""))
				txtpnStdin.setText(aux2+"\n"+aux);
			else
				txtpnStdin.setText(aux);
			aux = configFile.carregar();
		}
		aux = configFile.carregar();
		while(!aux.equals("---")){
			aux2 = txtpnStdOut.getText();
			if(!aux2.trim().equals(""))
				txtpnStdOut.setText(aux2+"\n"+aux);
			else
				txtpnStdOut.setText(aux);
			aux = configFile.carregar();
		}
		try{
			comboBoxCompilers.setSelectedIndex(Integer.parseInt(configFile.carregar()));
		}catch(Exception e){}
		
		initInterfaceLoaded();
	}
	
	private void newConfigFile(String asConfigFileDir,String asProblemName) throws IOException{	
		String configFileDir = asConfigFileDir.substring(0, asConfigFileDir.lastIndexOf("."))+".wcd";
		configFile = new Arquivo(configFileDir);
		configFile.deletarArquivo();
		configFile.salvar(asProblemName);
		configFile.salvar(asConfigFileDir);
		configFile.salvar(txtTimelimit.getText());
		configFile.salvar(txtpnStdin.getText());
		configFile.salvar("---");
		configFile.salvar(txtpnStdOut.getText());
		configFile.salvar("---");
		configFile.salvar(comboBoxCompilers.getSelectedIndex()+"");
		initInterfaceLoaded();
	}
	
	private boolean validateFields(){
		if(txtProblem.getText().trim().equals("")){
			if(Config.WINDOWS)
				showMessage("Campo Problem � de preenchimento obrigat�rio.");
			else
				showMessage("Campo Problem é de preenchimento obrigatório.");
			return false;
		}
			
		return true;
	}
	
	private void initInterfaceLoaded(){
		btnEdit.setVisible(true);
		btnClear.setVisible(true);
		btnSave.setVisible(true);
		btnSubmit.setText("Compile");
		btnSubmit.setActionCommand("SUBMIT");
		txtProblem.setEnabled(false);
		comboBoxCompilers.setEnabled(false);
		this.setTitle(txtProblem.getText());
	}
	
	public void showMessage(String message){
		JOptionPane.showMessageDialog(null, message);
	}
	
	public int showConfirmDialog(String message){
		return JOptionPane.showConfirmDialog(null, message,"CodeCompiler",JOptionPane.YES_NO_OPTION);
	}
	
	private void log(String message,String exception){
		MainFrame.getInstance().log("Interface - "+message+" :::: Exception - "+exception);
		showMessage(message);
	}
	
	private void log(String exception){
		MainFrame.getInstance().log("Interface - Falha ao fazer o diff do texto :::: Exception - "+exception);
	}
}
