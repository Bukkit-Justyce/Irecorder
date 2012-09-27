package org.overcloud.tools;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JButton;

import org.overcloud.Files;
import org.overcloud.Options;

import java.awt.Desktop;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;

public class JToolDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JToolDialog it;

	/**
	 * Create the dialog.
	 */
	public JToolDialog(final Options p) {
		it=this;
		getContentPane().setForeground(p.getFontColor());
		getContentPane().setFont(p.getFont());
		getContentPane().setBackground(p.getBackColor());
		getContentPane().setLayout(null);
		
		JButton btnEncodeVideoFrom = new JButton("Encode video from pictures");
		btnEncodeVideoFrom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new JPictureRecorderDialog(it,p);
			}
		});
		btnEncodeVideoFrom.setFont(p.getFont());
		btnEncodeVideoFrom.setForeground(p.getFontColor());
		btnEncodeVideoFrom.setBackground(p.getButColor());
		btnEncodeVideoFrom.setBounds(6, 6, 240, 38);
		getContentPane().add(btnEncodeVideoFrom);
		
		JButton btnGetPicturesFrom = new JButton("Get pictures from video");
		btnGetPicturesFrom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int z = JOptionPane.showConfirmDialog(null, "Are you sure it could last a very very very long moment \n e.g : video of 15 min with a fps of 150 extracts 135 000 pictures !","Are you sure ?",JOptionPane.YES_NO_OPTION);
				if(z==1){
					return;
				}
				String[] choix = {"PNG","BMP","JPG"};
				int form = JOptionPane.showOptionDialog(null, "Select the format",  "Format :", 
					       JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,choix,choix[2]);
				String format = choix[form];
				String path = Files.LoadFromFile(null, null, null);	
				String file = Files.SaveFromFile(null, null, null);
				if(file==null){
					return;
				}
				if(path==null){
					return;
				}
				JOptionPane.showMessageDialog(null, "Extracting... Please wait until it asks you if you want to open the directory of the " +
						"pictures","Extracting", JOptionPane.INFORMATION_MESSAGE);
				setTitle("Tools - Extracting...");
				DecodeAndCaptureFrames.start(file, path, format);
				int x =JOptionPane.showConfirmDialog(null, "Would you like to open the directory of the record", "Open it ?", JOptionPane.YES_NO_OPTION);
				if(x==0){
					try {
						Desktop.getDesktop().open(new File(path));
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
				setTitle("Tools");
			}
		});
		btnGetPicturesFrom.setForeground(p.getFontColor());
		btnGetPicturesFrom.setFont(p.getFont());
		btnGetPicturesFrom.setBackground(p.getButColor());
		btnGetPicturesFrom.setBounds(6, 56, 240, 38);
		getContentPane().add(btnGetPicturesFrom);
		
		JButton btnConcatenateAudioAnd = new JButton("Concatenate audio and video");
		btnConcatenateAudioAnd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new JConcatenateDialog(p);
			}
		});
		btnConcatenateAudioAnd.setForeground(p.getFontColor());
		btnConcatenateAudioAnd.setFont(p.getFont());
		btnConcatenateAudioAnd.setBackground(p.getButColor());
		btnConcatenateAudioAnd.setBounds(6, 106, 240, 38);
		getContentPane().add(btnConcatenateAudioAnd);
		
		JButton btnSeparateAudioAnd = new JButton("Extract audio");
		btnSeparateAudioAnd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new JExtractAudio(p);
			}
		});
		btnSeparateAudioAnd.setForeground(p.getFontColor());
		btnSeparateAudioAnd.setFont(p.getFont());
		btnSeparateAudioAnd.setBackground(p.getButColor());
		btnSeparateAudioAnd.setBounds(6, 156, 240, 38);
		getContentPane().add(btnSeparateAudioAnd);
		
		JButton btnExtractVideo = new JButton("Extract video");
		btnExtractVideo.setForeground(p.getFontColor());
		btnExtractVideo.setFont(p.getFont());
		btnExtractVideo.setBackground(p.getButColor());
		btnExtractVideo.setBounds(6, 206, 240, 38);
		btnExtractVideo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new JExtractVideo(p);
			}
		});
		getContentPane().add(btnExtractVideo);
		setBackground(p.getBackColor());
		setForeground(p.getFontColor());
		setFont(p.getFont());
		setTitle("Tools");
		setResizable(false);
		setSize(260, 281);
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	}
}
