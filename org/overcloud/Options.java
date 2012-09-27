package org.overcloud;

import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Options {
	private boolean audio =true,video=true, fullscreen=true,timer = false,sysTray = true,mouse =true,key =true;
	private int minX =0,maxX=0,minY=0,maxY=0,fps =10;
	private Color backColor =Color.BLACK;
	private Color butColor =Color.DARK_GRAY;
	private Color fontColor = Color.WHITE;
	private Font font = new Font("Toledo", Font.BOLD, 12);
	static final File f = new File("./options.txt");
	
	public Options(){
	}
	
	/**
	 * 
	 * @param l used only to overload the method
	 */
	public Options(boolean l){
		if(!f.exists()){
			save();
			return;
		}
		try {
			BufferedReader r = new BufferedReader(new FileReader(f));
			int x=0;
			for(String s= r.readLine();s!=null;s=r.readLine()){
				switch (x){
				case 0:
					audio=Boolean.parseBoolean(s);
					break;
				case 1:
					video=Boolean.parseBoolean(s);
					break;
				case 2:
					fullscreen=Boolean.parseBoolean(s);
					break;
				case 3:
					minX=Integer.parseInt(s);
					break;
				case 4:
					maxX=Integer.parseInt(s);
					break;
				case 5:
					minY=Integer.parseInt(s);
					break;
				case 6:
					maxY=Integer.parseInt(s);
					break;
				case 7:
					fps=Integer.parseInt(s);
					break;
				case 8:
					timer=Boolean.parseBoolean(s);
					break;
				case 9:
					sysTray=Boolean.parseBoolean(s);
					break;
				case 10:
					mouse=Boolean.parseBoolean(s);
					break;
				case 11:
					key=Boolean.parseBoolean(s);
					break;
				case 12:
					backColor = readColor(s);
					break;
				case 13:
					butColor = readColor(s);
					break;
				case 14:
					fontColor = readColor(s);
					break;
				case 15:
					font = readFont(s);
					break;
				}
				x++;
			}
			r.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}
	
	public void save(){
		if(!f.exists()){
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
		}
		try {
			PrintWriter w = new PrintWriter(f);
			w.println(audio);
			w.println(video);
			w.println(fullscreen);
			w.println(minX);
			w.println(maxX);
			w.println(minY);
			w.println(maxY);
			w.println(fps);
			w.println(timer);
			w.println(sysTray);
			w.println(mouse);
			w.println(key);
			w.println(writeColor(backColor));
			w.println(writeColor(butColor));
			w.println(writeColor(fontColor));
			w.println(font.getFontName()+"+"+font.getStyle()+"-"+font.getSize());
			w.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}
	}

	public boolean isFullscreen() {
		return fullscreen;
	}

	public void setFullscreen(boolean fullscreen) {
		this.fullscreen = fullscreen;
	}

	public int getMinX() {
		return minX;
	}

	public void setMinX(int minX) {
		this.minX = minX;
	}

	public int getMaxX() {
		return maxX;
	}

	public void setMaxX(int maxX) {
		this.maxX = maxX;
	}

	public int getMinY() {
		return minY;
	}

	public void setMinY(int minY) {
		this.minY = minY;
	}

	public int getMaxY() {
		return maxY;
	}

	public void setMaxY(int maxY) {
		this.maxY = maxY;
	}

	public boolean isAudio() {
		return audio;
	}

	public void setAudio(boolean audio) {
		this.audio = audio;
	}

	public boolean isVideo() {
		return video;
	}

	public void setVideo(boolean video) {
		this.video = video;
	}

	public int getFps() {
		return fps;
	}

	public void setFps(int fps) {
		this.fps = fps;
	}

	public boolean isTimer() {
		return timer;
	}

	public void setTimer(boolean timer) {
		this.timer = timer;
	}

	public boolean isSysTray() {
		return sysTray;
	}

	public void setSysTray(boolean sysTray) {
		this.sysTray = sysTray;
	}

	public boolean isMouse() {
		return mouse;
	}

	public void setMouse(boolean mouse) {
		this.mouse = mouse;
	}

	public boolean isKey() {
		return key;
	}

	public void setKey(boolean key) {
		this.key = key;
	}

	public Color getBackColor() {
		return backColor;
	}

	public void setBackColor(Color backColor) {
		this.backColor = backColor;
	}

	public Color getButColor() {
		return butColor;
	}

	public void setButColor(Color butColor) {
		this.butColor = butColor;
	}

	public Color getFontColor() {
		return fontColor;
	}

	public void setFontColor(Color fontColor) {
		this.fontColor = fontColor;
	}
	public Color readColor(String s){
		Color c ;
		if(!s.contains("+")||!s.contains("-")){
			return Color.BLACK;
		}
		int r = Integer.parseInt(s.substring(0,s.lastIndexOf("+")));
		int g = Integer.parseInt(s.substring(s.lastIndexOf("+")+1,s.lastIndexOf("-")));
		int b =Integer.parseInt(s.substring(s.lastIndexOf("-")+1,s.length()));
		c = new Color(r,g,b);
		return c;
	}
	public String writeColor(Color c){
		return new String(c.getRed()+"+"+c.getGreen()+"-"+c.getBlue());
	}

	public Font getFont() {
		return font;
	}

	public void setFont(Font font) {
		this.font = font;
	}
	public Font readFont(String s){
		Font c ;
		if(!s.contains("+")||!s.contains("-")){
			return font;
		}
		String name = s.substring(0,s.lastIndexOf("+"));
		int style = Integer.parseInt(s.substring(s.lastIndexOf("+")+1,s.lastIndexOf("-")));
		int size =Integer.parseInt(s.substring(s.lastIndexOf("-")+1,s.length()));
		c = new Font(name,style,size);
		return c;
	}

}
