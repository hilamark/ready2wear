package com.ready2wear.utils;

import java.io.ByteArrayOutputStream;
import java.util.List;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.SaveCallback;
import com.ready2wear.views.AddItems;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;

public class BitmapWorkerTask extends AsyncTask<Integer, List<String>, byte[]> {
    private final Bitmap imageViewReference;
    private List<String> urls;
    private ParseFile mfile;
    private AddItems activity;
    
    /** progress dialog to show user that the backup is processing. */
    private ProgressDialog dialog;

    public BitmapWorkerTask(List<String> urls, Bitmap bitmap, FragmentActivity activity) {
        // Use a WeakReference to ensure the ImageView can be garbage collected
        imageViewReference = bitmap;
        dialog = new ProgressDialog(activity);
        dialog.setCanceledOnTouchOutside(false);
        this.urls = urls;
        this.activity = (AddItems) activity;
    }
    
    @Override
    protected void onPreExecute() {
        this.dialog.setMessage("Loading image");
        this.dialog.show();
    }

    // Decode image in background.
    @Override
    protected byte[] doInBackground(Integer... params) {
    	// Convert it to byte
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		
		// Compress image to lower quality scale 1 - 100
		imageViewReference.compress(Bitmap.CompressFormat.PNG, 100, stream);
		return stream.toByteArray();
    }

    // Once complete, see if ImageView is still around and set bitmap.
    @Override
    protected void onPostExecute(byte[] bitmap) {
        if (bitmap != null) {
        	// Create the ParseFile
        	mfile = new ParseFile("itemImage.jpg", bitmap);
			
			// Upload the image into Parse Cloud
			mfile.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                	if (e == null){
                		urls.add(mfile.getUrl());
                		
                		dialog.dismiss();
                		activity.refreshView();
                	}
                }
            });

			// Create a New Class called "ImageUpload" in Parse
			ParseObject imgupload = new ParseObject("ImageUpload");

			// Create a column named "ImageFile" and insert the image
			imgupload.put("ImageFile", mfile);

			// Create the class and the columns
			imgupload.saveInBackground();
        }
    }
}