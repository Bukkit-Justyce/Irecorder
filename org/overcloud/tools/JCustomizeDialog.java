package org.overcloud.tools;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JColorChooser;
import javax.swing.JLabel;

import org.overcloud.JRecordPanel;
import org.overcloud.Options;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class JCustomizeDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JButton back,buttonCol,fontC,choose;
	private Options p;
	private JRecordPanel rp;
	private JDialog it;

	/**
	 * Create the dialog.
	 */
	public JCustomizeDialog(Options ps,JRecordPanel rps) {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.p=ps;
		this.rp=rps;
		it=this;
		setTitle("Customize !");
		setResizable(false);
		setVisible(true);
		setBounds(100, 100, 265, 228);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblBackground = new JLabel("Background :");
		lblBackground.setBounds(10, 11, 119, 25);
		contentPanel.add(lblBackground);
		
		JLabel lblButtonsBackground = new JLabel("Buttons Background :");
		lblButtonsBackground.setBounds(10, 47, 119, 25);
		contentPanel.add(lblButtonsBackground);
		
		JLabel lblFontColor = new JLabel("Font color :");
		lblFontColor.setBounds(10, 83, 119, 25);
		contentPanel.add(lblFontColor);
		
		JLabel lblFontType = new JLabel("Font type :");
		lblFontType.setBounds(10, 119, 119, 25);
		contentPanel.add(lblFontType);
		
		back = new JButton("Select");
		back.setBackground(p.getBackColor());
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Color couleur = JColorChooser.showDialog(it, "Customize Background color", p.getBackColor());
				if(couleur==null){
					return;
				}
				p.setBackColor(couleur);
				back.setBackground(couleur);
			}
		});
		back.setBounds(130, 12, 89, 23);
		contentPanel.add(back);
		
		buttonCol = new JButton("Select");
		buttonCol.setBackground(p.getButColor());
		buttonCol.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Color couleur = JColorChooser.showDialog(it, "Customize Buttons Background color", p.getButColor());
				if(couleur==null){
					return;
				}
				p.setButColor(couleur);
				buttonCol.setBackground(couleur);
			}
		});
		buttonCol.setBounds(130, 48, 89, 23);
		contentPanel.add(buttonCol);
		
		fontC = new JButton("Select");
		fontC.setBackground(p.getFontColor());
		fontC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Color couleur = JColorChooser.showDialog(it, "Customize Font color", p.getFontColor());
				if(couleur==null){
					return;
				}
				p.setFontColor(couleur);
				fontC.setBackground(couleur);
			}
		});
		fontC.setBounds(130, 83, 89, 23);
		contentPanel.add(fontC);
		
		choose = new JButton("Choose");
		choose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ChoixDePolice font = new ChoixDePolice(p.getFont());
				if(font.fontGetter()==null){
					return;
				}
				p.setFont(font.fontGetter());
				choose.setFont(p.getFont());
			}
		});
		choose.setBounds(130, 119, 89, 23);
		contentPanel.add(choose);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						rp.setOptions(p);
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
