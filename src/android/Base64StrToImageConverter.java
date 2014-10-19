package com.example.plugin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

import android.os.Environment;

public class SaveImage extends CordovaPlugin{

	@Override
	public boolean execute(String action, JSONArray args,CallbackContext callbackContext) throws JSONException {
		
		return saveImage(args.getString(0), args.getString(3), args.getString(2), callbackContext);
		
    }
	
	private boolean saveImage(String imageString, String path, String fileName, CallbackContext context){
		
		File pathToSave;
		
		File dirPath = new File(Environment.getExternalStorageDirectory()+"/"+path);
		try {
			if(!dirPath.exists()){
				
				dirPath.mkdir();
				
			}
			
			pathToSave = new File(dirPath+"/"+fileName);
			
			if(!pathToSave.exists()){
				pathToSave.createNewFile();
			}
		} catch (IOException e) {
			
			context.error(e.getMessage());
			return false;
		}
		
		FileOutputStream fos = null;
	    try {

		    if (imageString != null) {
		        fos = new FileOutputStream(pathToSave);
		        byte[] decodedString = android.util.Base64.decode(imageString, android.util.Base64.DEFAULT);
		        fos.write(decodedString);
	
		        fos.flush();
		        fos.close();
		        context.success(pathToSave.getAbsolutePath());
		        return true;
		    }
	
		} catch (Exception e) {
		    	context.error(e.getMessage());
		    	return false;
		} finally {
	        if (fos != null) {
	            fos = null;
	        }
	        
		}
	    return true;
	}
	
}
