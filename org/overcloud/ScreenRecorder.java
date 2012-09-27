package org.overcloud;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.xuggler.ICodec;

public class ScreenRecorder implements Runnable{
	
	private File f;
	private int x,y,width,height,fps,time;
	private static Thread t;
	private boolean mouse= true;
	private static IMediaWriter writer;
	private static boolean stop =false;
	private static boolean wait =false;
	private static ArrayList<Long> times = new ArrayList<Long>();
	private static Image i =null;
	private static ICodec FRAME_RATE =null; 
	private static Robot robot =null;
	private static long sleeper = 1000;
	
	public ScreenRecorder(){
		t = new Thread(this,"IRecorder-Video");
     	t.setPriority(6);
     	try {
			i=ImageIO.read(getClass().getResource("/org/res/cursor.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
 		try {
 			robot = new Robot();
 		} catch (Exception e1) {
 			e1.printStackTrace();
 		}
	}
	
    public void record(File f,int x,int y,int width,int height,int fps,int time,boolean m){
    	stop=false;
    	wait=false;
    	this.f=f;  
    	t = new Thread(this,"IRecorder-Video");
     	t.setPriority(6);
    	FRAME_RATE = ICodec.guessEncodingCodec(null, null, f.getAbsolutePath(), null, ICodec.Type.CODEC_TYPE_VIDEO);
    	this.x=x;
    	this.y=y;
    	this.height=height;    
    	this.width=width;
    	this.setFps(fps);
    	sleeper =(long) (1000 / fps);
     	this.time=time;
     	this.mouse=m;
     	t.start();
    }
   /** 
    * Convert a {@link BufferedImage} of any type, to {@link
    * BufferedImage} of a specified type.  If the source image is the
    * same type as the target type, then original image is returned,
    * otherwise new image of the correct type is created and the content
    * of the source image is copied into the new image.
    *
    * @param sourceImage the image to be converted
    * @param targetType the desired BufferedImage type 
    *
    * @return a BufferedImage of the specifed target type.
    *
    * @see BufferedImage
    */

   public  BufferedImage convertToType(BufferedImage sourceImage, 
     int targetType)
   {
     BufferedImage image;

     // if the source image is already the target type, return the source image

     if (sourceImage.getType() == targetType)
       image = sourceImage;

     // otherwise create a new image of the target type and draw the new
     // image 

     else
     {
       image = new BufferedImage(sourceImage.getWidth(), sourceImage.getHeight(),
         targetType);
       if (mouse) {
           int x = MouseInfo.getPointerInfo().getLocation().x+5;
           int y = MouseInfo.getPointerInfo().getLocation().y+5;

           Graphics2D graphics2D = sourceImage.createGraphics();
           try {
        	   graphics2D.drawImage(i, x, y,20,20, null);
           } catch (Exception e) {
        	   e.printStackTrace();
           }
       }

       image.getGraphics().drawImage(sourceImage, 0, 0, null);
     }
      
     return image;
   }
   @SuppressWarnings("deprecation")
public void run() {
       
       writer = ToolFactory.makeWriter(f.getAbsolutePath());
       
       writer.addVideoStream(0, 0, FRAME_RATE.getID(), width, height);
       
       long startTime = System.nanoTime();
       int orTime = time;
       if(time==0){
    	   time+=1;
       }
       Rectangle rec = new Rectangle(x,y,width,height);
       for (int index = 0; index < time*getFps(); index++)
       {
    	   if(orTime==0){
          		time+=1;
          }
         // take the screen shot
         BufferedImage screen = robot.createScreenCapture(rec);
         
         // convert to the right image type
         BufferedImage bgrScreen = convertToType(screen,
             BufferedImage.TYPE_3BYTE_BGR);
         times.add(System.nanoTime());
         
         
         if(stop){
        	 break;
         }
         if(!wait){
        	// encode the image to stream #0
             writer.encodeVideo(0,bgrScreen, times.get(0)-startTime, TimeUnit.NANOSECONDS);
             times.remove(0);
         }
         
         // sleep for framerate milliseconds
         try {
        	Thread.sleep(sleeper);
         } catch (Exception e) {
         	e.printStackTrace();
		}
       }
       // Finally we tell the writer to close and write the trailer if
       // needed
       writer.close();
	   t.stop();
	
   }
   public void stop(){
       stop=true;
   }
   public void pause(){
	   wait=true;
   }
   public void reprend(){
	   wait=false;
   }
   public int getTime(){
	   return time;
   }

   public int getFps() {
	   return fps;
   }

   public void setFps(int fps) {
	   this.fps = fps;
   }
}
