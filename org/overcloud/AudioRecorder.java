package org.overcloud;

import java.io.*;

import javax.sound.sampled.*;

public class AudioRecorder {

  private static final AudioFormat audioFormat =new AudioFormat(8000F,16,1,true,false);
  private TargetDataLine targetDataLine;
  private CaptureThread t;

  public AudioRecorder(){}

  //This method captures audio input from a
  // microphone and saves it in an audio file.
  	public void captureAudio(File l){
  		try{
  			//Get things set up for capture
  			DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class,audioFormat);
  			targetDataLine = (TargetDataLine)AudioSystem.getLine(dataLineInfo);
  			t= new CaptureThread(l);
 			t.setPriority(6);
 			t.setName("IRecorder-Audio");
  			t.start();
  		}catch (Exception e) {
  			e.printStackTrace();
  		}
  	}

class CaptureThread extends Thread{
	File r;
	boolean wait;
	
	public CaptureThread(File z){
		this.r=z;
	}
	
	public void run(){
		AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;
		try {
			targetDataLine.open(audioFormat);
		} catch (Exception e) {
			e.printStackTrace();
		}
		targetDataLine.start();
		try {
			AudioSystem.write(new AudioInputStream(targetDataLine),fileType, r);
		} catch (Exception e) {
			e.printStackTrace();
		}


	}
}
	@SuppressWarnings("deprecation")
	public void stop(){
		targetDataLine.stop();
        targetDataLine.close();
        t.stop();
	}
	public void pause(){
		 targetDataLine.stop();
	 }
	 public void reprend(){
		 targetDataLine.start();
	  }
}

