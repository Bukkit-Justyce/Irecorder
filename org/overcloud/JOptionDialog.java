package org.overcloud;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JLabel;

import org.overcloud.tools.JCustomizeDialog;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class JOptionDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField minX;
	private JTextField maxX;
	private JTextField minY;
	private JTextField maxY;
	private JLabel lblfps;
	private JTextField FPS;
	private JCheckBox chckbxRecordAudio,chckbxRecordVideo,chckbxFullScreen;
	private JCheckBox chckbxTimer;
	private JRecordPanel rp;
	private JCheckBox chckbxHide;
	private JCheckBox chckbxShowMouse;
	private JButton btnCustomize;

	/**
	 * Create the dialog.
	 */
	public JOptionDialog(final Options p,JRecordPanel l) {
		this.rp=l;
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
		setBackground(p.getBackColor());
		setResizable(false);
		setTitle("Options");
		setSize(371, 192);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		
		contentPanel.setBounds(0, 0, 365, 172);
		contentPanel.setBackground(p.getBackColor());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(null);
		
		chckbxRecordVideo = new JCheckBox("Record Video");
		chckbxRecordVideo.setForeground(p.getFontColor());
		chckbxRecordVideo.setFont(p.getFont());
		chckbxRecordVideo.setBackground(p.getBackColor());
		chckbxRecordVideo.setSelected(p.isVideo());
		chckbxRecordVideo.setBounds(132, 7, 140, 23);
		contentPanel.add(chckbxRecordVideo);
		
		chckbxFullScreen = new JCheckBox("Full Screen");
		chckbxFullScreen.setFont(p.getFont());
		chckbxFullScreen.setForeground(p.getFontColor());
		chckbxFullScreen.setBackground(p.getBackColor());
		chckbxFullScreen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(minX.isEnabled()){
					minX.setEnabled(false);
					minY.setEnabled(false);
					maxX.setEnabled(false);
					maxY.setEnabled(false);
				}
				else{
					minX.setEnabled(true);
					minY.setEnabled(true);
					maxX.setEnabled(true);
					maxY.setEnabled(true);
				}
			}
		});
		chckbxFullScreen.setSelected(p.isFullscreen());
		chckbxFullScreen.setBounds(6, 66, 119, 23);
		contentPanel.add(chckbxFullScreen);
		
		minX = new JTextField();
		minX.setBackground(p.getBackColor());
		minX.setForeground(p.getFontColor());
		minX.setFont(p.getFont());
		minX.setEnabled(!p.isFullscreen());
		minX.setHorizontalAlignment(SwingConstants.CENTER);
		minX.setText(Integer.toString(p.getMinX()));
		minX.setBounds(211, 67, 42, 22);
		contentPanel.add(minX);
		minX.setColumns(10);
		
		JLabel lblFromX = new JLabel("From X :");
		lblFromX.setBackground(p.getBackColor());
		lblFromX.setForeground(p.getFontColor());
		lblFromX.setFont(p.getFont());
		lblFromX.setHorizontalAlignment(SwingConstants.CENTER);
		lblFromX.setBounds(131, 70, 70, 14);
		contentPanel.add(lblFromX);
		
		JLabel lblToX = new JLabel("to X :");
		lblToX.setFont(p.getFont());
		lblToX.setForeground(p.getFontColor());
		lblToX.setBackground(p.getBackColor());
		lblToX.setBounds(263, 68, 46, 18);
		contentPanel.add(lblToX);
		
		maxX = new JTextField();
		maxX.setBackground(p.getBackColor());
		maxX.setForeground(p.getFontColor());
		maxX.setFont(p.getFont());
		maxX.setEnabled(!p.isFullscreen());
		maxX.setText(Integer.toString(p.getMaxX()));
		maxX.setHorizontalAlignment(SwingConstants.CENTER);
		maxX.setBounds(313, 67, 42, 22);
		contentPanel.add(maxX);
		maxX.setColumns(10);
		
		JLabel lblFromY = new JLabel("From Y :");
		lblFromY.setFont(p.getFont());
		lblFromY.setForeground(p.getFontColor());
		lblFromY.setBackground(p.getBackColor());
		lblFromY.setHorizontalAlignment(SwingConstants.CENTER);
		lblFromY.setBounds(132, 101, 70, 14);
		contentPanel.add(lblFromY);
		
		minY = new JTextField();
		minY.setForeground(p.getFontColor());
		minY.setFont(p.getFont());
		minY.setBackground(p.getBackColor());
		minY.setText(Integer.toString(p.getMinY()));
		minY.setHorizontalAlignment(SwingConstants.CENTER);
		minY.setEnabled(!p.isFullscreen());
		minY.setColumns(10);
		minY.setBounds(211, 98, 42, 22);
		contentPanel.add(minY);
		
		maxY = new JTextField();
		maxY.setFont(p.getFont());
		maxY.setForeground(p.getFontColor());
		maxY.setBackground(p.getBackColor());
		maxY.setText(Integer.toString(p.getMaxY()));
		maxY.setHorizontalAlignment(SwingConstants.CENTER);
		maxY.setEnabled(!p.isFullscreen());
		maxY.setColumns(10);
		maxY.setBounds(313, 98, 42, 22);
		contentPanel.add(maxY);
		
		JLabel lblToY = new JLabel("to Y :");
		lblToY.setFont(p.getFont());
		lblToY.setForeground(p.getFontColor());
		lblToY.setBackground(p.getBackColor());
		lblToY.setBounds(263, 101, 46, 14);
		contentPanel.add(lblToY);
		
		lblfps = new JLabel("FPS :");
		lblfps.setForeground(p.getFontColor());
		lblfps.setFont(p.getFont());
		lblfps.setBackground(p.getBackColor());
		lblfps.setBounds(16, 37, 42, 25);
		contentPanel.add(lblfps);
		
		FPS = new JTextField();
		FPS.setForeground(p.getFontColor());
		FPS.setBackground(p.getBackColor());
		FPS.setFont(p.getFont());
		FPS.setText(Integer.toString(p.getFps()));
		FPS.setHorizontalAlignment(SwingConstants.CENTER);
		FPS.setBounds(68, 39, 57, 22);
		contentPanel.add(FPS);
		FPS.setColumns(10);
		
		chckbxRecordAudio = new JCheckBox("Record Audio");
		chckbxRecordAudio.setSelected(p.isAudio());
		chckbxRecordAudio.setFont(p.getFont());
		chckbxRecordAudio.setForeground(p.getFontColor());
		chckbxRecordAudio.setBackground(p.getBackColor());
		chckbxRecordAudio.setBounds(6, 7, 124, 23);
		contentPanel.add(chckbxRecordAudio);
		
		chckbxTimer = new JCheckBox("Timer");
		chckbxTimer.setSelected(p.isTimer());
		chckbxTimer.setFont(p.getFont());
		chckbxTimer.setForeground(p.getFontColor());
		chckbxTimer.setBackground(p.getBackColor());
		chckbxTimer.setBounds(132, 37, 90, 23);
		contentPanel.add(chckbxTimer);
		
		chckbxHide = new JCheckBox("Hide");
		chckbxHide.setBackground(p.getBackColor());
		chckbxHide.setFont(p.getFont());
		chckbxHide.setForeground(p.getFontColor());
		chckbxHide.setSelected(p.isSysTray());
		chckbxHide.setBounds(226, 38, 97, 23);
		contentPanel.add(chckbxHide);
		
		chckbxShowMouse = new JCheckBox("Show Mouse");
		chckbxShowMouse.setForeground(p.getFontColor());
		chckbxShowMouse.setFont(p.getFont());
		chckbxShowMouse.setSelected(p.isMouse());
		chckbxShowMouse.setBackground(p.getBackColor());
		chckbxShowMouse.setBounds(6, 97, 119, 23);
		contentPanel.add(chckbxShowMouse);
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(205, 121, 150, 40);
			contentPanel.add(buttonPane);
			buttonPane.setBackground(p.getBackColor());
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			{
				JButton okButton = new JButton("OK");
				okButton.setForeground(p.getFontColor());
				okButton.setFont(p.getFont());
				okButton.setBackground(p.getButColor());
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
						rp.setOptions(getOptions());
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setForeground(p.getFontColor());
				cancelButton.setFont(p.getFont());
				cancelButton.setBackground(p.getButColor());
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		
		btnCustomize = new JButton("Customize ");
		btnCustomize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new JCustomizeDialog(p, rp);
			}
		});
		btnCustomize.setForeground(p.getFontColor());
		btnCustomize.setBackground(p.getButColor());
		btnCustomize.setBounds(14, 127, 150, 23);
		btnCustomize.setFont(p.getFont());
		contentPanel.add(btnCustomize);
	}
	public Options getOptions(){
		Options s= new Options(true);
		s.setAudio(chckbxRecordAudio.isSelected());
		s.setVideo(chckbxRecordVideo.isSelected());
		s.setFullscreen(chckbxFullScreen.isSelected());
		s.setMinX(Integer.parseInt(minX.getText()));
		s.setMaxX(Integer.parseInt(maxX.getText()));
		s.setMinY(Integer.parseInt(minY.getText()));
		s.setMaxY(Integer.parseInt(maxY.getText()));
		s.setFps(Integer.parseInt(FPS.getText()));
		s.setTimer(chckbxTimer.isSelected());
		s.setSysTray(chckbxHide.isSelected());
		s.setMouse(chckbxShowMouse.isSelected());
		return s;
	}
}
