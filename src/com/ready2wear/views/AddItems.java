package com.ready2wear.views;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class AddItems extends FragmentActivity {
	
	public static final String ANDROID_RESOURCE = "android.resource://";
	public static final String FORESLASH = "/";
	private static int PICK_IMAGE_REQUEST = 1;
	private static final int CAMERA_REQUEST = 0;
	public static final String TAG = "AddItems";
	
	private Button mCancel;
	private Button mDone;
	private GridView mGridView;
	private List<String> myItemsUrl = new ArrayList<String>();
	private List<Bitmap> myItemsBitmap = new ArrayList<Bitmap>();
	//private CustomList mAdapter;
	private CustomBitmapList mAdapter;
	private AddItemFragment mFragment;
	private Bitmap mLastAddedBitmap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_items);
		
		mFragment = (AddItemFragment)getSupportFragmentManager().findFragmentById(R.id.addItem_edit);
		mCancel = (Button) findViewById(R.id.itemCancelButton);
		mCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onCancelButtonClicked();
            }
        });
		mDone = (Button) findViewById(R.id.itemDoneButton);
		mDone.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onCancelButtonClicked();
            }
        });
		mGridView = (GridView) findViewById(R.id.addItems);
		myItemsBitmap.add(BitmapFactory.decodeResource(getResources(), R.drawable.plus_sign));
		//myItemsUrl.add("https://dov5cor25da49.cloudfront.net/products/2604/195x300shirt_guys_01.jpg");
		//myItemsUrl.add("http://www.vectors4all.net/preview/plus-sign-clip-art.jpg");
		//mAdapter = new CustomList(AddItems.this, (ArrayList<String>) myItemsUrl);
		
		mAdapter = new CustomBitmapList(AddItems.this, (ArrayList<Bitmap>) myItemsBitmap);
		mGridView.setAdapter(mAdapter);
		selectImage();
	}
	
	private void onCancelButtonClicked() {
        Log.v(TAG, "Cancel button clicked");
        setResult(RESULT_OK, null);
        finish();
    }
	
	private void selectImagee() {
		final CharSequence[] items = { "Take Photo", "Choose from Library", "Cancel" };
		AlertDialog.Builder builder = new AlertDialog.Builder(AddItems.this);
		builder.setTitle("Add Photo!");
		builder.setItems(items, new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int item) {
		if (items[item].equals("Take Photo")) {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(intent, CAMERA_REQUEST);
		} else if (items[item].equals("Choose from Library")) {
		Intent intent = new Intent(
		Intent.ACTION_PICK,
		android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		intent.setType("image/*");
		startActivityForResult(
		Intent.createChooser(intent, "Select File"),
		PICK_IMAGE_REQUEST);
		} else if (items[item].equals("Cancel")) {
		dialog.dismiss();
		}
		}
		});
		builder.show();
		}
	
	private void selectImage() {
		selectImagee();
    /*	Intent intent = new Intent();
    	
    	// Show only images, no videos or anything else
    	intent.setType("image/*");
    	intent.setAction(Intent.ACTION_GET_CONTENT);
    	
    	
    	
    	// Always show the chooser (if there are multiple options available)
    	startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
   */
    }
	
	public void refreshView(){
	//	if (mLastAddedBitmap == null)
	//		return;
		
		Bitmap plus = myItemsBitmap.get(myItemsBitmap.size() - 1);
		myItemsBitmap.remove(myItemsBitmap.size() - 1);
		myItemsBitmap.add(mLastAddedBitmap);
		myItemsBitmap.add(plus);
		mAdapter.notifyDataSetChanged();
		mGridView.setAdapter(mAdapter);
    /*	InputStream in = null;
		try {
			in = new java.net.URL(myItemsUrl.get(myItemsUrl.size() - 1)).openStream();
			Bitmap mIcon11 = BitmapFactory.decodeStream(in);
			//fragmentImage.setImageBitmap(mIcon11);
			
			mFragment = (AddItemFragment)getSupportFragmentManager().findFragmentById(R.id.addItem_edit);
			mFragment.updateView(mIcon11);
		} catch (Exception e) {

		} */
    }
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        if (!(resultCode == RESULT_OK))
        	return;
        
        if (requestCode == CAMERA_REQUEST) {
        	mLastAddedBitmap = (Bitmap) data.getExtras().get("data");
        	ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        //	mLastAddedBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        	File destination = new File(Environment.getExternalStorageDirectory(),
        	System.currentTimeMillis() + ".jpg");
        	FileOutputStream fo;
        	try {
        		destination.createNewFile();
        		fo = new FileOutputStream(destination);
        		fo.write(bytes.toByteArray());
        		fo.close();
        	} catch (Exception e) {
        		e.printStackTrace();
        	}
        	
        	 // Update image in fragment
          //  mFragment = (AddItemFragment)getSupportFragmentManager().findFragmentById(R.id.addItem_edit);
		//	mFragment.updateView(mLastAddedBitmap);
		//	mFragment.updateVisibility();
        }
        else if (requestCode == PICK_IMAGE_REQUEST && data != null && data.getData() != null) {
        	Uri itemPicUri = data.getData();
        	
        	try {
        		//refreshView();
        		// Translate to bitmap
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), itemPicUri);
                mLastAddedBitmap = Bitmap.createScaledBitmap(bitmap, 120, 120, false);
                
                // Update image in fragment
              //  mFragment = (AddItemFragment)getSupportFragmentManager().findFragmentById(R.id.addItem_edit);
    		//	mFragment.updateView(bitmap);
    		//	mFragment.updateVisibility();
    			// Save to parse in the background
               // BitmapWorkerTask task = new BitmapWorkerTask(myItemsUrl, bitmap, AddItems.this);
               // task.execute();

        	} catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        refreshView();
        mFragment.updateVisibility();
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
	
	public class CustomBitmapList extends ArrayAdapter<Bitmap>{
		
		private Activity mContext;
		private List<Bitmap> mBitmaps;

    	public CustomBitmapList(Activity context, ArrayList<Bitmap> images) {
    		super(context, 0, images);	
    		mContext = context;
    		mBitmaps = images;
    	}
    	
    	@Override
    	public View getView(int position, View view, ViewGroup parent) {
    		 ImageView imageView;
		      
		      if (view == null) {
		         imageView = new ImageView(mContext);
		         imageView.setLayoutParams(new GridView.LayoutParams(150, 150));
		         imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
		         imageView.setPadding(8, 8, 8, 8);
		      } 
		      else
		      {
		         imageView = (ImageView) view;
		      }

		    imageView.setImageBitmap(mBitmaps.get(position));
		    
		    // Check if last item (add item image)
            if (position == mBitmaps.size() - 1){
            	imageView.setOnClickListener(new View.OnClickListener() {
            		public void onClick(View view) {
            			selectImage();
            		}
            	});
            }
		    return imageView;
		   }
    	}
}
