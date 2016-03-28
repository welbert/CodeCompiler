package welbert.codecompiler.gui;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import net.miginfocom.swing.MigLayout;
import welbert.codecompiler.Commands.Functions;
import welbert.codecompiler.utils.Arquivo;

import javax.swing.JTextField;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextPane;

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

	/**
	 * Create the frame.
	 */
	public Project(String asProject,String[] aascompilers,String asconfigFile){
		
		setClosable(true);
		setIconifiable(true);
		setMaximizable(true);
		setTitle(asProject);
		setResizable(true);
		setBounds(100, 100, 536, 308);
		getContentPane().setLayout(new MigLayout("", "[grow][95.00,grow][][grow][][][grow][][grow][][][][][][][grow][][]", "[][][][][grow][][][][][grow][grow][][][][][][][][][][][][][][]"));
		
		lblProblem = new JLabel("Problem: ");
		getContentPane().add(lblProblem, "cell 0 0,alignx trailing");
		
		txtProblem = new JTextField();
		getContentPane().add(txtProblem, "cell 1 0,growx,aligny center");
		txtProblem.setColumns(10);
		
		JLabel lblTimelimit = new JLabel("Time Limit: ");
		getContentPane().add(lblTimelimit, "cell 4 0");
		
		txtTimelimit = new JTextField();
		getContentPane().add(txtTimelimit, "cell 6 0 3 1,growx");
		txtTimelimit.setColumns(10);
		
		JLabel lblStdout = new JLabel("stdout");
		getContentPane().add(lblStdout, "cell 0 2");
		
		JLabel lblStdoutcode = new JLabel("stdoutCode");
		getContentPane().add(lblStdoutcode, "cell 9 2");
		
		
		txtpnStdOut = new JTextPane();
		JScrollPane scrollOut = new JScrollPane();
		scrollOut.setViewportView(txtpnStdOut);
		getContentPane().add(scrollOut, "cell 0 3 2 5,grow");
		
		txtpnStdOutcode = new JTextPane();
		txtpnStdOutcode.setEditable(false);
		JScrollPane scrolloutcode = new JScrollPane();
		scrolloutcode.setViewportView(txtpnStdOutcode);
		getContentPane().add(scrolloutcode, "cell 3 4 15 16,grow");
		
		JLabel lblStdin = new JLabel("stdin");		
		getContentPane().add(lblStdin, "cell 0 8");
		
		txtpnStdin = new JTextPane();
		JScrollPane scrollIn = new JScrollPane();
		scrollIn.setViewportView(txtpnStdin);
		getContentPane().add(scrollIn, "cell 0 9 2 16,grow");
		
		btnEdit = new JButton("Edit");
		btnEdit.setActionCommand("EDIT");
		btnEdit.addActionListener(this);		
		getContentPane().add(btnEdit, "cell 17 22");
		
		
		btnClear = new JButton("Clear");
		getContentPane().add(btnClear, "cell 9 22");
		btnClear.setActionCommand("CLEAR");
		btnClear.addActionListener(this);
		
		comboBoxCompilers = new JComboBox<String>(aascompilers);
		getContentPane().add(comboBoxCompilers, "cell 2 24 14 1,growx,aligny center");
		
			
		if(!asconfigFile.trim().equals("")){
			btnSubmit = new JButton("Compile");
			btnSubmit.setActionCommand("SUBMIT");
			btnEdit.setVisible(true);
			btnClear.setVisible(true);
		}else{
			btnSubmit = new JButton("New");
			btnSubmit.setActionCommand("NEW");
			btnEdit.setVisible(false);
			btnClear.setVisible(false);
		}
		btnSubmit.addActionListener(this);
		getContentPane().add(btnSubmit, "cell 17 24");
		
		//Load file
		if(!asconfigFile.trim().equals("")){
			try {
				loadConfigFile(asconfigFile);
			} catch (Exception e) {
				String message = "Erro 105 - Falha ao carregar o arquivo de configurações ou arquivo corrompido.";
				log(message,e.getMessage());
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
				switch (showConfirmDialog("O arquivo "+pathFileProblem+" já existe. Deseja substituir?")) {
				case JOptionPane.YES_OPTION:
					try{
						codeFile = myFunctions.createNewFile(problemName, extension);
					}catch (Exception ex) {
						this.log("Erro 102 - Falha ao criar o Arquivo " + codeFile.getAbsolutePath(),
									ex.getMessage());
						return;
					}
					try{
						myFunctions.openFile(codeFile.getFile());
					}catch (Exception ex) {
						this.log("Erro 103 - Falha ao abrir o Arquivo "+codeFile.getAbsolutePath(), 
									ex.getMessage());
					}
					try{
						newConfigFile(codeFile.getAbsolutePath(),problemName);
					}catch (Exception ex) {
						this.log("Erro 104 - Falha ao criar o Arquivo de configurações", 
								ex.getMessage());
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
								ex.getMessage());
					return;
				}
				try{
					myFunctions.openFile(codeFile.getFile());
				}catch (Exception ex) {
					this.log("Erro 103 - Falha ao abrir o Arquivo "+codeFile.getAbsolutePath(), 
								ex.getMessage());
				}
				try{
					newConfigFile(codeFile.getAbsolutePath(),problemName);
				}catch (Exception ex) {
					this.log("Erro 104 - Falha ao criar o Arquivo de configurações", 
							ex.getMessage());
				}
			}
		break;
		
		case "SUBMIT":
			Object[] loOut;
			String lsStdOutCode;
			try {				
				loOut = myFunctions.runCompileInCode(codeFile.getAbsolutePath(), 
							comboBoxCompilers.getSelectedItem().toString().split(" ",2)[0]);
							
			} catch (Exception ex) {
				log("Erro 106 - Falha ao executar o compilador ou a executar o código.\n"
						+ "Mensagem retornada: "+ex.getMessage(),ex.getMessage());
				return;
			}	
						
			lsStdOutCode=txtpnStdOutcode.getText();	
			if((boolean)loOut[0]){
				try{
					boolean isCorrect;
					isCorrect = myFunctions.diffStrings(txtpnStdOut.getText(),String.valueOf(loOut[1]),txtpnStdOutcode);
					if(isCorrect)
						showMessage("O stdOut e StdOutCode são idênticos.");
					
				}catch (Exception ex) {
					txtpnStdOutcode.setText(lsStdOutCode+"\n-----------------\n"+loOut[1]);
					log(ex.getMessage());
				}
			}else{
				try{
					myFunctions.alterTextPane(String.valueOf(loOut[1]), txtpnStdOutcode);
				}catch (Exception ex) {
					txtpnStdOutcode.setText(lsStdOutCode+"\n-----------------\n"+loOut[1]);
					log(ex.getMessage());
				}
			}
			
				
			
		break;
		
		case "EDIT":
			try {
				myFunctions.openFile(codeFile.getFile());
			} catch (Exception e1) {
				this.log("Erro 101 - Falha ao editar "+codeFile.getAbsolutePath(), e1.getMessage());
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
		configFile.salvar(txtpnStdOut.getText());
		configFile.salvar("---");
		configFile.salvar(txtpnStdin.getText());
		configFile.salvar("---");
		configFile.salvar(comboBoxCompilers.getSelectedIndex()+"");
		initInterfaceLoaded();
	}
	
	private boolean validateFields(){
		if(txtProblem.getText().trim().equals("")){
			showMessage("Campo Problem � de preenchimento obrigat�rio.");
			return false;
		}
			
		return true;
	}
	
	private void initInterfaceLoaded(){
		btnEdit.setVisible(true);
		btnClear.setVisible(true);
		btnSubmit.setText("Compile");
		btnSubmit.setActionCommand("SUBMIT");
		txtProblem.setEnabled(false);
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
