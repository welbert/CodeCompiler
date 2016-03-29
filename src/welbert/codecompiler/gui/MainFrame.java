package welbert.codecompiler.gui;

import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JMenuBar;
import javax.swing.JFrame;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;

import welbert.codecompiler.Commands.Functions;
import welbert.codecompiler.gui.Project;
import welbert.codecompiler.staticsvalues.Config;
import welbert.codecompiler.utils.Arquivo;

import java.awt.event.*;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.*;
import javax.swing.JLabel;
 

@SuppressWarnings("serial")
public class MainFrame extends JFrame
                               implements ActionListener {
    JDesktopPane desktop;
    private Functions myFunctions = new Functions();
    private String[] compilers;
    private static MainFrame instance;
    private Arquivo log;
    
    public static MainFrame getInstance(){
    	if(instance==null)
    		instance = new MainFrame();
    	
    	return instance;
    }
 
    private MainFrame() {
        super(".:CodeCompiler:.");
        
        try{
        	log = new Arquivo("log.err");
        }catch(Exception e){}
        
        compilers = myFunctions.getCompilers();
        //Make the big window be indented 50 pixels from each edge
        //of the screen.
        int inset = 50;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset,
                  screenSize.width  - inset*2,
                  screenSize.height - inset*2);
 
        //Set up the GUI.
        desktop = new JDesktopPane();
        setContentPane(desktop);
        setJMenuBar(createMenuBarFile());
        
        createFrame();
 
        desktop.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);
        
        JLabel lblAuthor = new JLabel("Developed by Welbert Serra");
        lblAuthor.setForeground(Color.LIGHT_GRAY);
        lblAuthor.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 10));
        lblAuthor.setBounds(0, 0, 169, 15);
        desktop.add(lblAuthor);
    }
 
    protected JMenuBar createMenuBarFile() {
        JMenuBar menuBar = new JMenuBar();
 
        //Set up the lone menu.
        JMenu menu = new JMenu("File");
        menu.setMnemonic(KeyEvent.VK_D);
        menuBar.add(menu);
 
        //Set up the first menu item.
        JMenuItem menuItem = new JMenuItem("New...");
        menuItem.setMnemonic(KeyEvent.VK_N);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        menuItem.setActionCommand("NEW");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        //Set up the second menu item.
        menuItem = new JMenuItem("Load...");
        menuItem.setMnemonic(KeyEvent.VK_L);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_L, ActionEvent.CTRL_MASK));
        menuItem.setActionCommand("LOAD");
        menuItem.addActionListener(this);
        menu.add(menuItem);
 
        //Set up the third menu item.
        menuItem = new JMenuItem("About...");
        menuItem.setMnemonic(KeyEvent.VK_H);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_H, ActionEvent.CTRL_MASK));
        menuItem.setActionCommand("ABOUT");
        menuItem.addActionListener(this);
        menu.add(menuItem);
        
        //Set up the fourth menu item.
        menuItem = new JMenuItem("Exit...");
        menuItem.setMnemonic(KeyEvent.VK_Q);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
        menuItem.setActionCommand("EXIT");
        menuItem.addActionListener(this);
        menu.add(menuItem);
 
        return menuBar;
    }
 
    /**
     * Ações do menu
     */
    public void actionPerformed(ActionEvent e) {
    	switch (e.getActionCommand()) {
		case "NEW":
			createFrame();
		break;
		
		case "LOAD":
			try {
				JFileChooser fc = new JFileChooser(); 
				FileNameExtensionFilter filter = new FileNameExtensionFilter("WCodeCompiler (.wcd)", "wcd","text");
				fc.setFileFilter(filter);
				if(Config.WINDOWS)
					fc.setCurrentDirectory(new File("Problems\\."));
				else
					fc.setCurrentDirectory(new File("Problems//."));
				
	            int res = fc.showOpenDialog(null); //Abre o dialogo para selecionar os arquivos
	            
	            if(res == JFileChooser.APPROVE_OPTION){
	            	Arquivo file = new Arquivo(fc.getSelectedFile().getCanonicalPath());
	            	createFrame(file.carregar(),file.getAbsolutePath());					
	            }
			}catch(Exception ex){
				showMessage("Erro 001 - Falha ao Carregar o arquivo.");
				log("Erro 001 - Falha ao Carregar o arquivo."+ex.getMessage());
			}
		break;
		
		case "ABOUT":
			createFrameAbout();
			break;
			
		case "EXIT":
			quit();
			break;
		default:
			break;
		}
    }
 
    //Create a new internal frame.
    protected void createFrame() {
    	if(compilers.length < 1){
    		if(Config.WINDOWS)
    			showMessage("N�o h� compiladores instalados (gcc,g++,javac)");
    		else
    			showMessage("Não há compiladores instalados (gcc,g++,javac)");
    		
    		return;
    	}
        Project frame = new Project("New Project", compilers,"");
        frame.setVisible(true);
        desktop.add(frame);
        try {
            frame.setSelected(true);
        } catch (java.beans.PropertyVetoException e) {}
    }
    
    protected void createFrame(String project, String configDir) {
    	if(compilers.length < 1){
    		if(Config.WINDOWS)
    			showMessage("N�o h� compiladores instalados (gcc,g++,javac)");
    		else
    			showMessage("Não há compiladores instalados (gcc,g++,javac)");
    		
    		return;
    	}
        Project frame = new Project(project, compilers,configDir);
        frame.setVisible(true);
        desktop.add(frame);
        try {
            frame.setSelected(true);
        } catch (java.beans.PropertyVetoException e) {}
    }
    
    protected void createFrameAbout() {
        About frame = About.getInstance();
        frame.setVisible(true);
    }
 
    protected void quit() {
        System.exit(0);
    }
 

    private static void createAndShowGUI() {
 
        MainFrame frame = MainFrame.getInstance();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        frame.setVisible(true);
    }
 
    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
    
    public void showMessage(String message){
		JOptionPane.showMessageDialog(null, message);
	}
    
    public void log(String message){
    	if(Config.log)
	    	try{
	    		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	    		Date date = new Date();
	    		log.salvar(dateFormat.format(date) +" -> " +message);
	    	}catch(Exception e){}
    }
}