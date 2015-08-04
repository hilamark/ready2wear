package com.ready2wear.views;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import com.example.hellofacebook.R;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.SaveCallback;
import com.ready2wear.utils.BitmapWorkerTask;
import com.ready2wear.utils.DownloadImageTask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class AddItems extends FragmentActivity {
	
	private static int PICK_IMAGE_REQUEST = 1;
	private GridView mGridView;
	private List<String> myItemsUrl = new ArrayList<String>();
	private ParseFile mfile;
	private ImageView fragmentImage;
	private CustomList mAdapter;
	private AddItemFragment mFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_items);
		
		mGridView = (GridView) findViewById(R.id.addItems);
		
		LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout l=(LinearLayout)inflater.inflate(R.layout.activity_add_item_fragment, null, false);
		fragmentImage = (ImageView) l.findViewById(R.id.itemImg);
		
		//myItemsUrl.add("https://dov5cor25da49.cloudfront.net/products/2604/195x300shirt_guys_01.jpg");
		myItemsUrl.add("http://www.vectors4all.net/preview/plus-sign-clip-art.jpg");
		mAdapter = new CustomList(AddItems.this, (ArrayList<String>) myItemsUrl);
		mGridView.setAdapter(mAdapter);
		selectImage();
	}
	
	private void selectImage() {
    	Intent intent = new Intent();
    	
    	// Show only images, no videos or anything else
    	intent.setType("image/*");
    	intent.setAction(Intent.ACTION_GET_CONTENT);
    	
    	// Always show the chooser (if there are multiple options available)
    	startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
	
	public void refreshView(){
		mAdapter.notifyDataSetChanged();
		mGridView.setAdapter(mAdapter);
    	InputStream in = null;
		try {
			in = new java.net.URL(myItemsUrl.get(myItemsUrl.size() - 1)).openStream();
			Bitmap mIcon11 = BitmapFactory.decodeStream(in);
			//fragmentImage.setImageBitmap(mIcon11);
			
			mFragment = (AddItemFragment)getSupportFragmentManager().findFragmentById(R.id.addItem_edit);
			mFragment.updateView(mIcon11);
		} catch (Exception e) {

		}
    }
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
     
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
        	Uri itemPicUri = data.getData();
        	
        	try {
        		// Translate to bitmap
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), itemPicUri);

                BitmapWorkerTask task = new BitmapWorkerTask(myItemsUrl, bitmap, AddItems.this);
                task.execute();
/*
                
                // Convert it to byte
				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				
				// Compress image to lower quality scale 1 - 100
				bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
				byte[] image = stream.toByteArray();
 
				// Create the ParseFile
				mfile = new ParseFile("itemImage.jpg", image);
				
				// Upload the image into Parse Cloud
				mfile.saveInBackground(new SaveCallback() {
	                @Override
	                public void done(ParseException e) {
	                	if (e == null){
	                		myItemsUrl.add(mfile.getUrl());
	                		mGridView.invalidate();
	                	}
	                }
	            });
 
				// Create a New Class called "ImageUpload" in Parse
				ParseObject imgupload = new ParseObject("ImageUpload");
 
				// Create a column named "ImageFile" and insert the image
				imgupload.put("ImageFile", mfile);
 
				// Create the class and the columns
				imgupload.saveInBackground(); */
        	} catch (IOException e) {
                e.printStackTrace();
            }
        }
        
    }
	
	public class CustomList extends ArrayAdapter<String>{
		
		private Activity mContext;

    	public CustomList(Activity context, ArrayList<String> imagesURL) {
    		super(context, 0, imagesURL);	
    		mContext = context;
    	}
    	
    	@Override
    	public View getView(int position, View view, ViewGroup parent) {
    		 ImageView imageView;
		      
		      if (view == null) {
		         imageView = new ImageView(mContext);
		         imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
		         imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
		         imageView.setPadding(8, 8, 8, 8);
		      } 
		      else
		      {
		         imageView = (ImageView) view;
		      }

		    	  new DownloadImageTask(imageView).execute(myItemsUrl.get(position));
		      return imageView;
		   }
    	}
}
