package welbert.codecompiler.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import welbert.codecompiler.staticsvalues.Config;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;

@SuppressWarnings("serial")
public class About extends JFrame {

	private JPanel contentPane;
	private static About instance;

	public static About getInstance(){
		if(instance==null)
			instance = new About();
		
		return instance;
	}
	/**
	 * Create the frame.
	 */
	private About() {
		setTitle("About");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 313, 137);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblCodecompiler = new JLabel("CodeCompiler");
		lblCodecompiler.setBounds(96, 0, 108, 26);
		lblCodecompiler.setHorizontalAlignment(SwingConstants.LEFT);
		contentPane.add(lblCodecompiler);
		
		JLabel lblVersion = new JLabel("Version: "+Config.version);
		lblVersion.setBounds(32, 24, 98, 26);
		lblVersion.setHorizontalAlignment(SwingConstants.LEFT);
		contentPane.add(lblVersion);
		
		JLabel lblSite = new JLabel("Site: welbert.github.io/CodeCompiler");
		lblSite.setBounds(32, 49, 277, 26);
		lblSite.setHorizontalAlignment(SwingConstants.LEFT);
		contentPane.add(lblSite);
		
		JLabel lblByWelbertSerra = new JLabel("by Welbert Serra");
		lblByWelbertSerra.setBounds(198, 87, 113, 15);
		lblByWelbertSerra.setVerticalAlignment(SwingConstants.BOTTOM);
		lblByWelbertSerra.setFont(new Font("Dialog", Font.ITALIC, 12));
		lblByWelbertSerra.setHorizontalAlignment(SwingConstants.LEFT);
		contentPane.add(lblByWelbertSerra);
	}

}
