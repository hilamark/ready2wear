package com.ready2wear.views;

import java.util.ArrayList;
import java.util.List;

import com.example.hellofacebook.R;
import com.ready2wear.utils.DownloadImageTask;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class AddItems extends FragmentActivity {
	
	private GridView mGridView;
	private List<String> myItemsUrl = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_items);
		
		mGridView = (GridView) findViewById(R.id.addItems);
		
		myItemsUrl.add("https://dov5cor25da49.cloudfront.net/products/2604/195x300shirt_guys_01.jpg");
		myItemsUrl.add("http://www.vectors4all.net/preview/plus-sign-clip-art.jpg");
		mGridView.setAdapter(new CustomList(AddItems.this, (ArrayList<String>) myItemsUrl));
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
		      // If yet no items, show add item
		      if (myItemsUrl.size() == 1) 
		    	  imageView.setImageResource(R.drawable.add);
		      else
		    	  new DownloadImageTask(imageView).execute(myItemsUrl.get(position));
		      return imageView;
		   }
    	}
    
	/*
	public class ImageAdapter extends BaseAdapter {
		   private Context mContext;
		   
		   // Constructor
		   public ImageAdapter(Context c) {
			   super();
		      mContext = c;
		   }
		   
		   public int getCount() {
		      return myItemsUrl.size() + 1;
		   }

		   public Object getItem(int position) {
		      return null;
		   }

		   public long getItemId(int position) {
		      return 0;
		   }
		   
		   // create a new ImageView for each item referenced by the Adapter
		   public View getView(int position, View convertView, ViewGroup parent) {
		      ImageView imageView;
		      
		      if (convertView == null) {
		         imageView = new ImageView(mContext);
		         imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
		         imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
		         imageView.setPadding(8, 8, 8, 8);
		      } 
		      else 
		      {
		         imageView = (ImageView) convertView;
		      }
		      // If yet no items, show add item
		      if (myItemsUrl.size() == 1)
		    	  imageView.setImageResource(R.drawable.icon_old);
		      else
		    	  new DownloadImageTask(imageView).execute(myItemsUrl.get(position));
		      return imageView;
		   }
		} */
}
