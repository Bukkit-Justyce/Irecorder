/**
 * 
 */
package org.overcloud.tools;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.overcloud.Files;
import org.overcloud.Options;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.io.File;


public class JConcatenateDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField audiFile;
	private JTextField Video;
	private JTextField widther;
	private JTextField heighter;

	/**
	 * Create the dialog.
	 */
	public JConcatenateDialog(Options o) {
		setResizable(false);
		setTitle("Concatenate audio and video");
		setVisible(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setSize(448, 206);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(o.getBackColor());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblAudio = new JLabel("Audio :");
		lblAudio.setBackground(o.getBackColor());
		lblAudio.setForeground(o.getFontColor());
		lblAudio.setFont(o.getFont());
		lblAudio.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAudio.setBounds(10, 11, 48, 21);
		contentPanel.add(lblAudio);
		
		audiFile = new JTextField();
		audiFile.setForeground(o.getFontColor());
		audiFile.setFont(o.getFont());
		audiFile.setBounds(68, 11, 267, 25);
		audiFile.setBackground(o.getBackColor());
		contentPanel.add(audiFile);
		audiFile.setColumns(10);
		
		JButton btnLoad = new JButton("Load");
		btnLoad.setForeground(o.getFontColor());
		btnLoad.setBackground(o.getButColor());
		btnLoad.setFont(o.getFont());
		btnLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String path =Files.LoadFromFile(null, "Select the audio file");
				if(path!=null){
					audiFile.setText(path);
				}
			}
		});
		btnLoad.setBounds(345, 10, 89, 23);
		contentPanel.add(btnLoad);
		
		Video = new JTextField();
		Video.setForeground(o.getFontColor());
		Video.setFont(o.getFont());
		Video.setBackground(o.getBackColor());
		Video.setColumns(10);
		Video.setBounds(68, 44, 267, 25);
		contentPanel.add(Video);
		
		JLabel vid = new JLabel("Video :");
		vid.setHorizontalAlignment(SwingConstants.RIGHT);
		vid.setForeground(o.getFontColor());
		vid.setFont(o.getFont());
		vid.setBackground(o.getBackColor());
		vid.setBounds(10, 44, 48, 21);
		contentPanel.add(vid);
		
		JButton button = new JButton("Load");
		button.setForeground(o.getFontColor());
		button.setFont(o.getFont());
		button.setBackground(o.getButColor());
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String path =Files.LoadFromFile(null, "Select the audio file");
				if(path!=null){
					Video.setText(path);
				}
			}
		});
		button.setBounds(345, 45, 89, 23);
		contentPanel.add(button);
		
		JLabel lblWidth = new JLabel("Width :");
		lblWidth.setHorizontalAlignment(SwingConstants.RIGHT);
		lblWidth.setBounds(10, 80, 229, 25);
		lblWidth.setFont(o.getFont());
		lblWidth.setForeground(o.getFontColor());
		lblWidth.setBackground(o.getBackColor());
		contentPanel.add(lblWidth);
		
		JLabel lblHeight = new JLabel("Height :");
		lblHeight.setHorizontalAlignment(SwingConstants.RIGHT);
		lblHeight.setBounds(18, 111, 221, 25);
		lblHeight.setFont(o.getFont());
		lblHeight.setForeground(o.getFontColor());
		lblHeight.setBackground(o.getBackColor());
		contentPanel.add(lblHeight);
		
		widther = new JTextField();
		widther.setForeground(o.getFontColor());
		widther.setFont(o.getFont());
		widther.setColumns(10);
		widther.setBackground(o.getBackColor());
		widther.setBounds(249, 80, 86, 25);
		contentPanel.add(widther);
		
		heighter = new JTextField();
		heighter.setForeground(o.getFontColor());
		heighter.setFont(o.getFont());
		heighter.setColumns(10);
		heighter.setBackground(o.getBackColor());
		heighter.setBounds(249, 111, 86, 25);
		contentPanel.add(heighter);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(0, 147, 444, 33);
			contentPanel.add(buttonPane);
			buttonPane.setBackground(o.getBackColor());
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			{
				JButton okButton = new JButton("OK");
				okButton.setFont(o.getFont());
				okButton.setForeground(o.getFontColor());
				okButton.setBackground(o.getButColor());
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						int width = Integer.parseInt(widther.getText());
						int height = Integer.parseInt(heighter.getText());
						if(width<1||height<1){
							JOptionPane.showMessageDialog(null, "Error, width or height is incorrect ! \nOnly numbers are allowed !");
							return;
						}
						String audio =audiFile.getText();
						String video = Video.getText();
						if(audio==null||audio==""||video==null||video==""){
							JOptionPane.showMessageDialog(null, "Error, audio or video file is incorrect !");
							return;
						}
						String path =Files.SaveFromFile(null, null, null);
						if(path==null){
							return;
						}
						ConcatenateAudioAndVideo.concatenate(audio, video, path, width, height);
						int x =JOptionPane.showConfirmDialog(null, "Would you like to open the directory of the record", "Open it ?", JOptionPane.YES_NO_OPTION);
						if(x==0){
							try {
								Desktop.getDesktop().open(new File(path));
							} catch (Exception e1) {
								e1.printStackTrace();
							}
						}
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setFont(o.getFont());
				cancelButton.setForeground(o.getFontColor());
				cancelButton.setBackground(o.getButColor());
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
