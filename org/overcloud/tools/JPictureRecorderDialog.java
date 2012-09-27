package org.overcloud.tools;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

import org.overcloud.Files;
import org.overcloud.Options;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.SwingConstants;

public class JPictureRecorderDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField fps;
	private JTextField width;
	private JTextField height;
	private JTextField name;
	private JComboBox<String> picF;



	/**
	 * Create the dialog.
	 */
	public JPictureRecorderDialog(final JToolDialog l,Options o) {
		setResizable(false);
		setTitle("Encode video from pictures");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
		setSize(227, 213);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setBackground(o.getBackColor());
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lbl1 = new JLabel("FPS");
			lbl1.setHorizontalAlignment(SwingConstants.RIGHT);
			lbl1.setBackground(o.getBackColor());
			lbl1.setForeground(o.getFontColor());
			lbl1.setFont(o.getFont());
			lbl1.setBounds(10, 6, 105, 25);
			contentPanel.add(lbl1);
		}
		{
			fps = new JTextField();
			fps.setBackground(o.getBackColor());
			fps.setForeground(o.getFontColor());
			fps.setFont(o.getFont());
			fps.setText("10");
			fps.setBounds(125, 6, 86, 25);
			contentPanel.add(fps);
			fps.setColumns(10);
		}
		{
			width = new JTextField();
			width.setBackground(o.getBackColor());
			width.setForeground(o.getFontColor());
			width.setFont(o.getFont());
			width.setText("480");
			width.setBounds(125, 36, 86, 25);
			contentPanel.add(width);
			width.setColumns(10);
		}
		{
			height = new JTextField();
			height.setBackground(o.getBackColor());
			height.setForeground(o.getFontColor());
			height.setFont(o.getFont());
			height.setText("360");
			height.setBounds(125, 65, 86, 25);
			contentPanel.add(height);
			height.setColumns(10);
		}
		{
			name = new JTextField();
			name.setText("Test");
			name.setBackground(o.getBackColor());
			name.setForeground(o.getFontColor());
			name.setFont(o.getFont());
			name.setBounds(125, 94, 86, 25);
			contentPanel.add(name);
			name.setColumns(10);
		}
		{
			picF = new JComboBox<String>();
			picF.setBackground(o.getButColor());
			picF.setForeground(o.getFontColor());
			picF.setFont(o.getFont());
			picF.setModel(new DefaultComboBoxModel<String>(new String[] {"png", "bmp", "jpg"}));
			picF.setBounds(125, 127, 86, 20);
			contentPanel.add(picF);
		}
		{
			JLabel lbl2 = new JLabel("Width");
			lbl2.setHorizontalAlignment(SwingConstants.RIGHT);
			lbl2.setBackground(o.getBackColor());
			lbl2.setForeground(o.getFontColor());
			lbl2.setFont(o.getFont());
			lbl2.setBounds(10, 36, 105, 25);
			contentPanel.add(lbl2);
		}
		{
			JLabel lbl3 = new JLabel("Height");
			lbl3.setHorizontalAlignment(SwingConstants.RIGHT);
			lbl3.setBackground(o.getBackColor());
			lbl3.setForeground(o.getFontColor());
			lbl3.setFont(o.getFont());
			lbl3.setBounds(10, 65, 108, 25);
			contentPanel.add(lbl3);
		}
		{
			JLabel lbl4 = new JLabel("Name ");
			lbl4.setHorizontalAlignment(SwingConstants.RIGHT);
			lbl4.setBackground(o.getBackColor());
			lbl4.setForeground(o.getFontColor());
			lbl4.setFont(o.getFont());
			lbl4.setToolTipText("The name of the video");
			lbl4.setBounds(10, 94, 105, 25);
			contentPanel.add(lbl4);
		}
		{
			JLabel lbl5 = new JLabel("Format ");
			lbl5.setHorizontalAlignment(SwingConstants.RIGHT);
			lbl5.setBackground(o.getBackColor());
			lbl5.setForeground(o.getFontColor());
			lbl5.setFont(o.getFont());
			lbl5.setBounds(10, 127, 105, 17);
			contentPanel.add(lbl5);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(o.getBackColor());
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						l.setTitle("Tools - Compiling...");
						String[] choix = {"MP4","AVI","MOV"};
						int form = JOptionPane.showOptionDialog(null, "Select the format",  "Format :", 
							       JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,choix,choix[2]);
						String format = choix[form];
						String path = Files.LoadFromFile(null, null, null);	
						if(path==null){
							return;
						}
						
						dispose();
						JOptionPane.showMessageDialog(null, "Compiling... Please wait until it asks you if you want to open the directory of the " +
								"record","Compiling", JOptionPane.INFORMATION_MESSAGE);
						new PicturesRecorder().record(new File(path), Integer.parseInt(width.getText()), Integer.parseInt(height.getText()),
								Integer.parseInt(fps.getText()),name.getText(),format, (String)picF.getSelectedItem());
						int x =JOptionPane.showConfirmDialog(null, "Would you like to open the directory of the record", "Open it ?", JOptionPane.YES_NO_OPTION);
						if(x==0){
							try {
								Desktop.getDesktop().open(new File(path));
							} catch (Exception e1) {
								e1.printStackTrace();
							}
						}
						l.setTitle("Tools");
						
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
