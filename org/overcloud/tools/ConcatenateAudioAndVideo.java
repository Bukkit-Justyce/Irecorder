package org.overcloud.tools;

import com.xuggle.mediatool.IMediaReader;
import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.MediaToolAdapter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.mediatool.event.AudioSamplesEvent;
import com.xuggle.mediatool.event.IAddStreamEvent;
import com.xuggle.mediatool.event.IAudioSamplesEvent;
import com.xuggle.mediatool.event.ICloseCoderEvent;
import com.xuggle.mediatool.event.ICloseEvent;
import com.xuggle.mediatool.event.IOpenCoderEvent;
import com.xuggle.mediatool.event.IOpenEvent;
import com.xuggle.mediatool.event.IVideoPictureEvent;
import com.xuggle.mediatool.event.VideoPictureEvent;
import com.xuggle.xuggler.IAudioSamples;
import com.xuggle.xuggler.ICodec;
import com.xuggle.xuggler.IVideoPicture;


public class ConcatenateAudioAndVideo
{

	/**
	   * Concatenate two source files into one 
	   *
	   * @param sourceUrl1 the file which will appear first in the output
	   * @param sourceUrl2 the file which will appear second in the output
	   * @param destinationUrl the file which will be produced
	   */

	  public static void concatenate(String sourceUrl1, String sourceUrl2,
	    String destinationUrl,int c,int v)
	  {

	    // video parameters

	    final int videoStreamIndex = 0;
	    final int width = c;
	    final int height = v;

	    // audio parameters

	    final int audioStreamIndex = 1;
	    final int channelCount = 1;
	    final int sampleRate =8000; // Hz

	    // create the first media reader

	    IMediaReader reader1 = ToolFactory.makeReader(sourceUrl1);

	    // create the second media reader

	    IMediaReader reader2 = ToolFactory.makeReader(sourceUrl2);

	    // create the media concatenator

	    MediaConcatenator concatenator = new MediaConcatenator(audioStreamIndex,
	      videoStreamIndex);

	    // concatenator listens to both readers

	    reader1.addListener(concatenator);
	    reader2.addListener(concatenator);

	    // create the media writer which listens to the concatenator

	    IMediaWriter writer = ToolFactory.makeWriter(destinationUrl);
	    concatenator.addListener(writer);

	    // add the video stream and create the CODEC
	    ICodec FRAME_RATE = ICodec.guessEncodingCodec(null, null, destinationUrl, null, ICodec.Type.CODEC_TYPE_VIDEO); 

	    writer.addVideoStream(0, 0,FRAME_RATE.getID(), width, height);

	    // add the audio stream

	    writer.addAudioStream(1, 0, channelCount,
	      sampleRate);

	    // read packets from the first source file until done

	    while (reader1.readPacket() == null)
	      ;

	    // read packets from the second source file until done

	    while (reader2.readPacket() == null)
	      ;

	    // close the writer

	    writer.close();
	  }
	 
	  static class MediaConcatenator extends MediaToolAdapter
	  {
	    // the current offset
	   
	    private long mOffset = 0;
	   
	    // the next video timestamp
	   
	    private long mNextVideo = 0;
	   
	    // the next audio timestamp
	   
	    private long mNextAudio = 0;

	    // the index of the audio stream
	   
	    private final int mAudoStreamIndex;
	   
	    // the index of the video stream
	   
	    private final int mVideoStreamIndex;
	   
	    /**
	     * Create a concatenator.
	     *
	     * @param audioStreamIndex index of audio stream
	     * @param videoStreamIndex index of video stream
	     */
	   
	    public MediaConcatenator(int audioStreamIndex, int videoStreamIndex)
	    {
	      mAudoStreamIndex = audioStreamIndex;
	      mVideoStreamIndex = videoStreamIndex;
	    }
	    @Override
	    public void onAudioSamples(IAudioSamplesEvent event)
	    {
	      IAudioSamples samples = event.getAudioSamples();
	     
	      // set the new time stamp to the original plus the offset established
	      // for this media file

	      long newTimeStamp = samples.getTimeStamp() + mOffset;

	      // keep track of predicted time of the next audio samples, if the end
	      // of the media file is encountered, then the offset will be adjusted
	      // to this time.

	      mNextAudio = samples.getNextPts();

	      // set the new timestamp on audio samples

	      samples.setTimeStamp(newTimeStamp);

	      // create a new audio samples event with the one true audio stream
	      // index

	      super.onAudioSamples(new AudioSamplesEvent(this, samples,
	        mAudoStreamIndex));
	    }
	    @Override
	    public void onVideoPicture(IVideoPictureEvent event)
	    {
	      IVideoPicture picture = event.getMediaData();
	      long originalTimeStamp = picture.getTimeStamp();

	      // set the new time stamp to the original plus the offset established
	      // for this media file

	      long newTimeStamp = originalTimeStamp + mOffset;
	     
	      mNextVideo = originalTimeStamp + 1;

	      // set the new timestamp on video samples

	      picture.setTimeStamp(newTimeStamp);

	      // create a new video picture event with the one true video stream
	      // index

	      super.onVideoPicture(new VideoPictureEvent(this, picture,
	        mVideoStreamIndex));
	    }
	    @Override
	    public void onClose(ICloseEvent event)
	    {
	      // update the offset by the larger of the next expected audio or video
	      // frame time

	      mOffset = Math.max(mNextVideo, mNextAudio);
	    }
	    @Override
	    public void onAddStream(IAddStreamEvent event)
	    {
	      // overridden to ensure that add stream events are not passed down
	      // the tool chain to the writer, which could cause problems
	    }
	    @Override
	    public void onOpen(IOpenEvent event)
	    {
	      // overridden to ensure that open events are not passed down the tool
	      // chain to the writer, which could cause problems
	    }
	    @Override
	    public void onOpenCoder(IOpenCoderEvent event)
	    {
	      // overridden to ensure that open coder events are not passed down the
	      // tool chain to the writer, which could cause problems
	    }
	    @Override
	    public void onCloseCoder(ICloseCoderEvent event)
	    {
	      // overridden to ensure that close coder events are not passed down the
	      // tool chain to the writer, which could cause problems
	    }
	  }
	}

