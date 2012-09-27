package org.overcloud;

import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JDesktopPane;
import javax.swing.UIManager;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import org.bzzz.Error;

public class JFrameContainer extends JFrame {

	private static final long serialVersionUID = 1L;
	PopupMenu pop;
	TrayIcon trayIcon = null;

	public JFrameContainer() {
		setTitle("IRecorder");
		pop=new PopupMenu("iRecorder");
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			Error.show(e);
		}
		setSize(450, 150);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setUndecorated(true);
		getContentPane().setLayout(null);
		
		
		JDesktopPane desktopPane = new JDesktopPane();
		desktopPane.setSize(450, 150);
		setContentPane(desktopPane);
		
		JMainGui mainGui = new JMainGui(this);
		mainGui.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentMoved(ComponentEvent arg0) {
				setLocationRelativeTo(arg0.getComponent());
				repaint();
			}
		});
		try {
			mainGui.setSelected(true);
		} catch (Exception e) {
			Error.show(e);
		}
		mainGui.setLocation(0, 0);
		mainGui.setSize(450, 150);
		desktopPane.add(mainGui);
		
		pop=SysTrayPopup.Sys(pop,this, mainGui.getRecordPanel());
		
		try {
			trayIcon = new TrayIcon(ImageIO.read(getClass().getResource("/org/res/icone.png")),"iRecorder",pop	);
		} catch (IOException e1) {
			Error.show(e1);
		}

	}
	@SuppressWarnings("deprecation")
	public void topetiticone(){
		if (SystemTray.isSupported()) {
			SystemTray systemTray = SystemTray.getSystemTray();
			
			if(!contains(systemTray.getTrayIcons(), trayIcon)){
				try {
					systemTray.add(trayIcon);
				} catch (Exception e) {
					Error.show(e);
				}
			}
			this.hide();
		}
	}
	@SuppressWarnings("deprecation")
	public void removepetiticone(){
			this.show();
			if (SystemTray.isSupported()) {
				SystemTray systemTray = SystemTray.getSystemTray();
				
				if(contains(systemTray.getTrayIcons(), trayIcon)){
					systemTray.remove(trayIcon);
				}
			}
	}

	/**
	 * 
	 * @return if the array contains this object
	 */
	public static boolean contains(Object[] obj,Object ob){
		for(int x =0;x<obj.length;x++){
			Object comp = obj[x];
			if(ob.equals(comp)){
				return true;
			}
		}
		return false;
	}
}
