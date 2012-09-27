package org.overcloud.tools;

import java.awt.image.BufferedImage;

import com.xuggle.mediatool.IMediaReader;
import com.xuggle.mediatool.IMediaTool;
import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.MediaToolAdapter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.mediatool.event.IAudioSamplesEvent;
import com.xuggle.mediatool.event.IVideoPictureEvent;
import com.xuggle.xuggler.ICodec;

public class Separator {

    public static void extractAudio(String inputFilename,String outputFilenameA) {

        // create a media reader
        IMediaReader mediaReader = ToolFactory.makeReader(inputFilename);
        IMediaTool audioVolumeMediaTool = new VolumeAdjustMediaTool(outputFilenameA);
        
        // create a tool chain:
        mediaReader.addListener(audioVolumeMediaTool);
        
        while (mediaReader.readPacket() == null) ;

    }
    public static void extractVideo(String inputFilename,String outputFilename) {

        // create a media reader
        IMediaReader mediaReader = ToolFactory.makeReader(inputFilename);
        IMediaTool audioVolumeMediaTool = new StaticImageMediaTool(outputFilename);
        mediaReader.setBufferedImageTypeToGenerate(BufferedImage.TYPE_3BYTE_BGR);
        // create a tool chain:
        mediaReader.addListener(audioVolumeMediaTool);
        
        while (mediaReader.readPacket() == null) ;

    }
    
    private static class StaticImageMediaTool extends MediaToolAdapter {
        
    	private IMediaWriter writer;
    	private boolean first =true;
    	ICodec FRAME_RATE = null; 
        
        
        public StaticImageMediaTool(String volume) {
            writer = ToolFactory.makeWriter(volume);
            FRAME_RATE=ICodec.guessEncodingCodec(null, null, volume, null, ICodec.Type.CODEC_TYPE_VIDEO);
        }

        @Override
        public void onVideoPicture(IVideoPictureEvent event) {
            if(first){
            	writer.addVideoStream(0, 0, FRAME_RATE.getID(), event.getPicture().getWidth(), event.getPicture().getHeight());
            	first=false;
            }
            writer.onVideoPicture(event);
            
            // call parent which will pass the video onto next tool in chain
            super.onVideoPicture(event);
            
        }
        
    }

    private static class VolumeAdjustMediaTool extends MediaToolAdapter {
        
    	private IMediaWriter writer;
    	private boolean first =true;
        
        
        public VolumeAdjustMediaTool(String volume) {
            writer = 
                    ToolFactory.makeWriter(volume);
        }

        @Override
        public void onAudioSamples(IAudioSamplesEvent event) {
        	if(first){
        		writer.addAudioStream(1, 0, event.getMediaData().getChannels(),  event.getMediaData().getSampleRate());
        		first=false;
        	}
        	writer.onAudioSamples(event);
            // call parent which will pass the audio onto next tool in chain
            super.onAudioSamples(event);
            
        }
        
    }

}
