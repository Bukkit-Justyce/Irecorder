package org.overcloud;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.Desktop;
import java.awt.Graphics;
import java.awt.Toolkit;

import javax.swing.JButton;

import org.overcloud.tools.ConcatenateAudioAndVideo;
import org.overcloud.tools.JToolDialog;

import java.io.File;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class JRecordPanel extends JPanel implements Runnable{

	/**
	 * 
	 */
	private Options o =null;
	private boolean play = false;
	private static final long serialVersionUID = 1L;
	private JButton playwait,stop,options,tools =null;
	private JRecordPanel it =null;
	private ScreenRecorder video=null;
	private AudioRecorder audio =null;
	private boolean Raudio =false;
	private boolean Rvideo = false;
	private String file =null;
	private JMainGui parent =null;
	private boolean pause =false;
	private static Thread t ;
	private static final Desktop d =Desktop.getDesktop();
	
	/**
	 * Create the panel.
	 */
	public JRecordPanel(JMainGui l) {
		o = new Options(true);
		setFont(o.getFont());
		setForeground(o.getFontColor());
		this.parent=l;
		t = new Thread(this,"iRecorder");
		t.start();
	}

	public boolean isPlaying() {
		return play;
	}

	public void setPlaying(boolean play) {
		this.play = play;
		if(!play){
			parent.setTitle("iRecorder");
			try {
				playwait.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/org/res/play.png"))));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else{
			parent.setTitle("iRecorder - Recording");
			try {
				playwait.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/org/res/wait.png"))));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	public Options getOptions() {
		return o;
	}

	public void setOptions(Options o) {
		this.o = o;
		this.o.save();
		setBackground(o.getBackColor());
		playwait.setBackground(o.getButColor());
		stop.setBackground(o.getButColor());
		options.setBackground(o.getButColor());
		options.setFont(o.getFont());
		options.setForeground(o.getFontColor());
		tools.setFont(o.getFont());
		tools.setForeground(o.getFontColor());
		tools.setBackground(o.getButColor());
	}
	public void recordVideo(String path){
		if(o.isSysTray()){
			((JFrameContainer) parent.getParent()).topetiticone();
		}
		if(o.isFullscreen()){
			file=path;
			video.record(new File(path),0,0, (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth(),(int)Toolkit.getDefaultToolkit().getScreenSize().getHeight(),
					o.getFps(), 0, o.isMouse());
		}
		else{
			file=path;
			video.record(new File(path),o.getMinX(),o.getMinY(), o.getMaxX()-o.getMinX(),o.getMaxY()-o.getMinY(),
					o.getFps(), 0, o.isMouse());
		}
	}
	public void recordAudio(String path) {
		if(o.isSysTray()){
			((JFrameContainer) parent.getParent()).topetiticone();
		}
		audio.captureAudio(new File(path));
		file=path;
	}
	public void recordAudioVideo(String path, String format){
		if(o.isSysTray()){
			((JFrameContainer) parent.getParent()).topetiticone();
		}
		if(o.isFullscreen()){
			file=path;
			audio.captureAudio(new File(path.substring(0, path.lastIndexOf("."))+2+"."+format));
			video.record(new File(path.substring(0, path.lastIndexOf("."))+1+"."+format),0,0,
					(int)Toolkit.getDefaultToolkit().getScreenSize().getWidth(),
					(int)Toolkit.getDefaultToolkit().getScreenSize().getHeight(),o.getFps(), 0, o.isMouse());
		}
		else{
			file=path;
			audio.captureAudio(new File(path.substring(0, path.lastIndexOf("."))+2+"."+format));
			video.record(new File(path.substring(0, path.lastIndexOf("."))+1+"."+format),o.getMinX(),o.getMinY(),
					o.getMaxX()-o.getMinX(),o.getMaxY()-o.getMinY(),o.getFps(), 0, o.isMouse());
		}
	}
	public void stopRecord() throws Exception{
		if(isPlaying()){
			setPlaying(false);
			String path = file;
			File parentF = new File(path).getParentFile();
			int x ;
			String format = path.substring(path.lastIndexOf(".")+1, path.length());
			if(Raudio&&!Rvideo){
				Raudio=false;
				audio.stop();
				
				
				x =JOptionPane.showConfirmDialog(parent, "Would you like to open the directory of the record", "Open it ?", JOptionPane.YES_NO_OPTION);
				if(x==0){
					d.open(parentF);
				}
			}
			else if(Rvideo&&!Raudio){
				Rvideo=false;
				video.stop();
				
				x =JOptionPane.showConfirmDialog(parent, "Would you like to open the directory of the record", "Open it ?", JOptionPane.YES_NO_OPTION);
				if(x==0){
					d.open(parentF);
				}
			}
			else if(Raudio&&Rvideo){
				int timer = video.getFps()*video.getTime()/1000;
				this.parent.setTitle("iRecorder - Compiling audio and video ... (at least "+(timer/100+1)+"sec )");
				Raudio=false;
				audio.stop();
				
				Rvideo=false;
				video.stop();
				
				JOptionPane.showMessageDialog(null, "Compiling please wait until it asks you if you want to open the directory of the record ...",
						"Compiling...",JOptionPane.INFORMATION_MESSAGE);
				path = file;
				
				String audi = path.substring(0,path.lastIndexOf("."))+2+"."+format;
				String vid = path.substring(0,path.lastIndexOf("."))+1+"."+format;
				Thread.sleep(1000);
				Thread.sleep(timer);
				if(o.isFullscreen()){
					ConcatenateAudioAndVideo.concatenate(audi,vid, path,(int)Toolkit.getDefaultToolkit().getScreenSize().getWidth(),
									(int)Toolkit.getDefaultToolkit().getScreenSize().getHeight());
				}
				else{	
					ConcatenateAudioAndVideo.concatenate(audi,vid, path,o.getMaxX()-o.getMinX(),o.getMaxY()-o.getMinY());
				}
				File r = new File(audi);
				r.delete();
				r= new File(vid);
				r.delete();
				
				x =JOptionPane.showConfirmDialog(parent, "Would you like to open the directory of the record", "Open it ?",
						JOptionPane.YES_NO_OPTION);
				if(x==0){
					d.open(parentF);
				}
				parent.setTitle("iRecorder");
				video = new ScreenRecorder();
				audio = new AudioRecorder();
			}
		}
	}
	public void pause(){
		if(isPlaying()){
			setPlaying(false);
			if(Raudio&&!Rvideo){
				audio.pause();
			}
			else if(Rvideo&&!Raudio){
				video.pause();
			}
			else if(Raudio&&Rvideo){
				audio.pause();
				video.pause();
			}
		}
	}
	public void reprend(){
		if(!isPlaying()){
			setPlaying(true);
			if(Raudio&&!Rvideo){
				audio.reprend();
			}
			else if(Rvideo&&!Raudio){
				video.reprend();
			}
			else if(Raudio&&Rvideo){
				audio.reprend();
				video.reprend();
			}
		}
	}

	public void run() {
		init();
	}
	public void init(){
		video=new ScreenRecorder();
		audio=new AudioRecorder();
		it=this;
		setBackground(o.getBackColor());
		setLayout(null);
		
		playwait = new JButton();
		playwait.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {	
				record();
			}
		});
		try {
			playwait.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/org/res/play.png"))));
		} catch (Exception e) {
			e.printStackTrace();
		}
		playwait.setBackground(o.getButColor());
		playwait.setBounds(6, 6, 103, 103);
		add(playwait);
		
		stop = new JButton();
		stop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					stopRecord();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		try {
			stop.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/org/res/stop.png"))));
		} catch (Exception e) {
			e.printStackTrace();
		}
		stop.setBackground(o.getButColor());
		stop.setBounds(135, 6, 103, 103);
		add(stop);
		
		options = new JButton("Options");
		options.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new JOptionDialog(o,it);
			}
		});
		options.setFont(o.getFont());
		options.setForeground(o.getFontColor());
		options.setBackground(o.getButColor());
		options.setBounds(268, 18, 103, 48);
		add(options);
		
		tools = new JButton("Tools");
		tools.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new JToolDialog(o);
			}
		});
		tools.setFont(o.getFont());
		tools.setForeground(o.getFontColor());
		tools.setBackground(o.getButColor());
		tools.setBounds(268, 71, 103, 39);
		add(tools);

	}
	public void record(){
		if(play&&!pause){
			pause();
			pause=true;
			setPlaying(false);
			return;
		}
		if(!play&&pause){
			reprend();
			pause=false;
			setPlaying(true);
			return;
		}
		if(o.isAudio()&&!o.isVideo()&&!play){
			pause=false;
			setPlaying(true);
			
			String[] choix = {"MP3","WAV","MP4"};
			int form = JOptionPane.showOptionDialog(this, "Select the format",  "Format :", 
				       JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,choix,choix[2]);
			String format = choix[form];
			String path = Files.SaveFromFile(null, null, null);
			if(path==null){
				setPlaying(!play);
				return;
			}
			if(!path.endsWith("."+format)){
				path+="."+format;
			}
			Raudio=true;
			if(o.isTimer()){
				new TimerSplashScreen(it,true,false,path);
			}
			else{
				recordAudio(path);
			}
		}
		else if(o.isVideo()&&!o.isAudio()&&!play){
			pause=false;
			setPlaying(true);
			String[] choix = {"MP4","AVI","MOV"};
			int form = JOptionPane.showOptionDialog(this, "Select the format",  "Format :", 
				       JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,choix,choix[2]);
			String format = choix[form];
			String path = Files.SaveFromFile(null, null, null);
			
			if(path==null){
				setPlaying(!play);
				return;
			}
			if(!path.endsWith("."+format)){
				path+="."+format;
			}
			Rvideo=true;
			if(o.isTimer()){
				new TimerSplashScreen(it,false,true,path);
			}
			else{
				recordVideo(path);
			}
		}
		else if(o.isVideo()&&o.isAudio()&&!play){
			pause=false;
			setPlaying(true);
			String[] choix = {"MP4","MOV"};
			int form = JOptionPane.showOptionDialog(this, "Select the format",  "Format :", 
				       JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,choix,choix[1]);
			String format = choix[form];
			String path = Files.SaveFromFile(null, null, null);
			
			if(path==null){
				setPlaying(!play);
				return;
			}
			if(!path.endsWith("."+format)){
				path+="."+format;
			}
			file=path;
			Rvideo=true;
			Raudio=true;
			if(o.isTimer()){
				new TimerSplashScreen(it,true,true,path);
			}
			else{
				recordAudioVideo(path,format);
			}
		}
	}
	public void paint(Graphics g){
		super.paint(g);
	}
}
