package welbert.codecompiler.gui;

import java.awt.BorderLayout;
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
		setBounds(100, 100, 232, 182);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JLabel lblCodecompiler = new JLabel("CodeCompiler");
		lblCodecompiler.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblCodecompiler, BorderLayout.NORTH);
		
		JLabel lblByWelbertSerra = new JLabel("by Welbert Serra");
		lblByWelbertSerra.setFont(new Font("Dialog", Font.ITALIC, 12));
		lblByWelbertSerra.setHorizontalAlignment(SwingConstants.RIGHT);
		contentPane.add(lblByWelbertSerra, BorderLayout.SOUTH);
		
		JLabel lblVersion = new JLabel("Version:");
		lblVersion.setHorizontalAlignment(SwingConstants.LEFT);
		contentPane.add(lblVersion, BorderLayout.WEST);
		
		JLabel lblNversion = new JLabel(Config.version);
		contentPane.add(lblNversion, BorderLayout.CENTER);
	}

}
