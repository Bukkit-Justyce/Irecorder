package org.overcloud;

import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import java.awt.Color;
import javax.swing.JFrame;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

public class JMainGui extends JInternalFrame {

	/**
	 * 
	 */
	private JFrame parent;
	private static final long serialVersionUID = 1L;
	private JRecordPanel recordPanel;
	public JRecordPanel getRecordPanel() {
		return recordPanel;
	}

	public void setRecordPanel(JRecordPanel recordPanel) {
		this.recordPanel = recordPanel;
	}

	/**
	 * Create the frame.
	 */
	public JMainGui(JFrame l) {
		setIconifiable(true);
		this.setFocusable(true);
		setClosable(true);
		parent = l;
		requestFocusInWindow();
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
		setVisible(true);
		getContentPane().setBackground(Color.DARK_GRAY);
		setForeground(Color.YELLOW);
		setFont(new Font("X-Files", Font.BOLD, 12));
		setSize(new Dimension(400, 120));
		setTitle("iRecorder");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBorder(null);
		setBackground(Color.DARK_GRAY);
		setVisible(true);
		setBounds(0,0, 400, 120);
		getContentPane().setLayout(null);
		
		recordPanel = new JRecordPanel(this);
		recordPanel.setBorder(null);
		recordPanel.setBounds(0, 0, 398, 118);
		setContentPane(recordPanel);
		addInternalFrameListener(new InternalFrameAdapter() {
			@Override
			public void internalFrameClosing(InternalFrameEvent e) {
				int x = 0;
				x = JOptionPane.showConfirmDialog(parent, "All unsaved records will be lost.","Are you sure ?", JOptionPane.YES_NO_OPTION);
				if(x==0){
					System.exit(0);
				}
			}
			@Override
			public void internalFrameIconified(InternalFrameEvent arg0) {
				parent.toBack();
			}
			@Override
			public void internalFrameDeiconified(InternalFrameEvent e) {
				parent.toBack();
			}
		});
	}
	public JFrame getParent() {
		return parent;
	}
}
