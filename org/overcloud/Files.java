package org.overcloud;

import java.awt.FileDialog;
import java.awt.Frame;

public class Files {

	   public static String LoadFromFile(Frame _frame, String _path,String ext) {
	       FileDialog fileDialog = new FileDialog(_frame, "Select a file from the directory you want to load ...", FileDialog.LOAD);
	       fileDialog.setFile(ext);
	       fileDialog.setDirectory(_path);
	       fileDialog.setLocation(50, 50);
	       fileDialog.setDirectory(_path);
	       fileDialog.setVisible(true);
	       if(fileDialog.getFile()==null){
	    	   return null;
	       }
	       return fileDialog.getDirectory();
	   }
	   
	   public static String LoadFromFile(Frame _frame, String title) {
	       FileDialog fileDialog = new FileDialog(_frame, title, FileDialog.LOAD);
	       fileDialog.setLocation(50, 50);
	       fileDialog.setVisible(true);
	       if(fileDialog.getFile()==null){
	    	   return null;
	       }
	       return fileDialog.getDirectory()+fileDialog.getFile();
	   }

	   public static String SaveFromFile(Frame _frame,  String _path,String ext) {
	       FileDialog fileDialog = new FileDialog(_frame, "Save...", FileDialog.SAVE);
	       fileDialog.setFile(ext);
	       fileDialog.setDirectory(_path);
	       fileDialog.setLocation(50, 50);
	       fileDialog.setDirectory(_path);
	       fileDialog.setVisible(true);
	       if(fileDialog.getFile()==null){
	    	   return null;
	       }
	       return fileDialog.getDirectory() + fileDialog.getFile();
	   }
	}

