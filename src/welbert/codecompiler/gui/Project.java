package welbert.codecompiler.gui;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import net.miginfocom.swing.MigLayout;
import welbert.codecompiler.Commands.Functions;

import javax.swing.JTextField;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextPane;

@SuppressWarnings("serial")
public class Project extends JInternalFrame 
					implements ActionListener {
	private JTextField txtProblem;
	private JTextField txtTimelimit;
	private Functions myFunctions = new Functions();

	/**
	 * Create the frame.
	 */
	public Project(String Project,String compilers){
		setClosable(true);
		setIconifiable(true);
		setMaximizable(true);
		setTitle(Project);
		setResizable(true);
		setBounds(100, 100, 536, 308);
		getContentPane().setLayout(new MigLayout("", "[grow][95.00,grow][][grow][][][grow][][grow][][][][][][][grow][][]", "[][][][][grow][][][][][grow][grow][][][][][][][][][][][][][]"));
		
		JLabel lblProblem = new JLabel("Problem: ");
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
		
		
		JTextPane txtpnIOut = new JTextPane();
		JScrollPane scrollOut = new JScrollPane();
		scrollOut.setViewportView(txtpnIOut);
		getContentPane().add(scrollOut, "cell 0 3 2 5,grow");
		
		JTextPane txtpnStdoutcode = new JTextPane();
		JScrollPane scrolloutcode = new JScrollPane();
		scrolloutcode.setViewportView(txtpnStdoutcode);
		getContentPane().add(scrolloutcode, "cell 3 4 15 16,grow");
		
		JLabel lblStdin = new JLabel("stdin");		
		getContentPane().add(lblStdin, "cell 0 8");
		
		JTextPane txtpnStdin = new JTextPane();
		JScrollPane scrollIn = new JScrollPane();
		scrollIn.setViewportView(txtpnStdin);
		getContentPane().add(scrollIn, "cell 0 9 2 15,grow");
		
		JComboBox<String> comboBox = new JComboBox<String>();
		getContentPane().add(comboBox, "cell 2 23 14 1,growx,aligny center");
		
		JButton btnSubmit = new JButton("Compile");
		btnSubmit.setActionCommand("SUBMIT");
		btnSubmit.addActionListener(this);
		getContentPane().add(btnSubmit, "cell 17 23");

	}

	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "SUBMIT":
			String[] result = myFunctions.getCompilers();
			JOptionPane.showMessageDialog(null, result);
		break;
		
		default:
			break;
		}
		
	}
}
