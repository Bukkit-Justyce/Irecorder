package org.overcloud;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.Timer;

public class TimerSplashScreen extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Image i ;
	final int x =(int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2)-25;
	final int y = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2-25;
	static int index = 5;
	static JFrame it;
	static Timer v;
	static JRecordPanel rp;
	static boolean audio;
	static boolean video;
	static String path;


	/**
	 * Create the frame.
	 */
	@SuppressWarnings("static-access")
	public TimerSplashScreen(JRecordPanel l,boolean a,boolean b,String f) {
		this.rp=l;
		this.audio=a;
		this.video=b;
		this.path=f;
		index=5;
		setUndecorated(true);
		setBackground(new Color(0.0f, 0.0f, 0.0f, 0.0f));
		setBounds(0, 0,x*2+25, y*2+25);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setAlwaysOnTop(true);
		try {
			i= ImageIO.read(getClass().getResource("/org/res/five.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		it=this;
		v= createUpdater();
		v.start();
	}
	@Override
	public void paint(Graphics g){
		switch(index){
		case 1:
			try {
				i= ImageIO.read(getClass().getResource("/org/res/one.png"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case 2:
			try {
				i= ImageIO.read(getClass().getResource("/org/res/two.png"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case 3:
			try {
				i= ImageIO.read(getClass().getResource("/org/res/three.png"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case 4:
			try {
				i= ImageIO.read(getClass().getResource("/org/res/four.png"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		}
		g.drawImage(i, x, y, null);
	}
	public static Timer createUpdater(){
		   ActionListener action = new ActionListener ()
		     {
		       public void actionPerformed (ActionEvent event){
		    	   index--;
		    	   it.repaint();
		    	   if(index==0){
		    		   if(audio&&video){
			   				rp.recordAudioVideo(path,path.substring(path.lastIndexOf(".")+1, path.length()));
			   			}
			   			else if(audio){
			   				rp.recordAudio(path);
			   			}
			   			else if (video){
			   				rp.recordVideo(path);
			   			}
		   				it.dispose();
		   				v.stop();
		    	   }
		       }
		     };
		   return new Timer (900, action);
	}

}
