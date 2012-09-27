package org.overcloud.tools;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.xuggler.ICodec;

public class PicturesRecorder implements Runnable{
	
	private File f;
	private int width,height,fps;
	private static Thread t;
	private static IMediaWriter writer;
	private static ICodec FRAME_RATE =null; 
	private long sleeper = 1000;
	private String n,format,pic;
	
	public PicturesRecorder(){
	}
	
    public void record(File f,int width,int height,int fps,String name,String form,String p){
    	this.f=f;  
    	this.pic=p;
    	this.n=name;
    	this.format=form;
    	t = new Thread(this,"IRecorder-VideoPic");
     	t.setPriority(6);
    	FRAME_RATE = ICodec.guessEncodingCodec(null, null, f.getAbsolutePath()+"/"+n+"."+format, null, ICodec.Type.CODEC_TYPE_VIDEO);
    	this.height=height;    
    	this.width=width;
    	this.setFps(fps);
    	this.sleeper=1000/fps;
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
       image.getGraphics().drawImage(sourceImage, 0, 0, null);
     }
      
     return image;
   }
   @SuppressWarnings("deprecation")
public void run() {
       if(!f.isDirectory()){
    	   return;
       }
       // First, let's make a IMediaWriter to write the file.
       writer = ToolFactory.makeWriter(f.getAbsolutePath()+"/"+n+"."+format);
       
       File[] files = f.listFiles();
       writer.addVideoStream(0, 0, FRAME_RATE.getID(), width, height);
       File[] Pic = new File[100000]; ;
       int v =0;
       for(int x=0;x<files.length;x++){
    	   if(f.listFiles()[x].getName().endsWith(pic)){
    		   Pic[v]=files[x];
    		   v++;
    	   }
       }
       BufferedImage screen = null ;
       // Now, we're going to loop
       long startTime = System.nanoTime();
       for(int x= 0;x<v;x++){
    	   try {
    		   screen = ImageIO.read(Pic[x]);
    	   } catch (Exception e) {
				e.printStackTrace();
			}
    	   BufferedImage bgrScreen = convertToType(screen,
    	             BufferedImage.TYPE_3BYTE_BGR);
    	   writer.encodeVideo(0,bgrScreen, System.nanoTime()-startTime, TimeUnit.NANOSECONDS);
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

   public int getFps() {
	   return fps;
   }

   public void setFps(int fps) {
	   this.fps = fps;
   }
}
