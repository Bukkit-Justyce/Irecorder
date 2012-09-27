package org.overcloud;

import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

public class SysTrayPopup {

	static MenuItem pause;
	static MenuItem stop,quit,restaurer;
	public static PopupMenu Sys(PopupMenu pop,final JFrameContainer rp,final JRecordPanel f){
		pause=new MenuItem("Record/Pause");
		pause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				f.record();
			}
		});
		pop.add(pause);
		
		stop=new MenuItem("Stop");
		stop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					f.stopRecord();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		pop.add(stop);
		
		restaurer=new MenuItem("Show");
		restaurer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				rp.removepetiticone();
			}
		});
		pop.add(restaurer);
		
		quit=new MenuItem("Quit");
		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int x = 0;
				x = JOptionPane.showConfirmDialog(rp, "All unsaved records will be lost.","Are you sure ?", JOptionPane.YES_NO_OPTION);
				if(x==0){
					System.exit(0);
				}
			}
		});
		pop.add(quit);
		
		pop.setName("iRecorder");
		return pop;
	}
}

