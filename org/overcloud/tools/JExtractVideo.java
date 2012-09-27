/**
 * 
 */
package org.overcloud.tools;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.overcloud.Files;
import org.overcloud.Options;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;

public class JExtractVideo extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JComboBox<String> audio;

	/**
	 * Create the dialog.
	 * @param o 
	 */
	public JExtractVideo(Options o) {
		setResizable(false);
		setTitle("Extract video");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setSize(295, 109);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setBackground(o.getBackColor());
		contentPanel.setLayout(null);
		{
			JLabel lbl1 = new JLabel("Video Format :");
			lbl1.setHorizontalAlignment(SwingConstants.RIGHT);
			lbl1.setBounds(10, 11, 100, 30);
			lbl1.setBackground(o.getBackColor());
			lbl1.setForeground(o.getFontColor());
			lbl1.setFont(o.getFont());
			contentPanel.add(lbl1);
		}
		
		String[] choixA = {"AVI","MOV","MP4"};
		audio = new JComboBox<String>();
		audio.setFont(o.getFont());
		audio.setModel(new DefaultComboBoxModel<String>(choixA));
		audio.setBackground(o.getButColor());
		audio.setForeground(o.getFontColor());
		audio.setBounds(120, 16, 150, 20);
		contentPanel.add(audio);
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(o.getBackColor());
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String aF = "."+(String) audio.getSelectedItem();
						String path = Files.LoadFromFile(null, "Select the source file");
						if(path==null){ return;}
						String name = new File(path).getName();
						name=name.substring(0, name.lastIndexOf("."))+"Video";
						String out = Files.LoadFromFile(null, null, null);
						if(out==null){ return;}
						Separator.extractVideo(path, out+name+aF);
						dispose();
					}
				});
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
		setVisible(true);
	}
}
