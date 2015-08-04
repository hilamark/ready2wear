package com.ready2wear.views;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

import com.example.hellofacebook.R;
import com.ready2wear.domain.ItemSize;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

public class AddItemFragment extends Fragment {

	private Spinner mItemSizeSpinner;
	private ArrayList<ItemSize> mItemSizeTypes;
	private ImageView mImageView;
	
	@Override
	  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	    View view = inflater.inflate(R.layout.activity_add_item_fragment, container, false);
	    
	    mImageView = (ImageView) view.findViewById(R.id.itemImg);

        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL("http://moxiereviews.com/wp-content/uploads/2011/08/ready-to-wear.jpg").openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
	    
        mImageView.setImageBitmap(mIcon11);
	    
	    mItemSizeTypes = new ArrayList<ItemSize>();
        mItemSizeTypes.add(ItemSize.SMALL);
        mItemSizeTypes.add(ItemSize.MEDUIM);
        mItemSizeTypes.add(ItemSize.LARGE);
	    
	    mItemSizeSpinner = (Spinner) view.findViewById(R.id.itemSizeSpinner);
	    ArrayAdapter<String> adapter;

	    adapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Iterator<ItemSize> iterSize;
        iterSize = mItemSizeTypes.iterator();
        while (iterSize.hasNext()) {
            adapter.add(iterSize.next().getName());
        }
        mItemSizeSpinner.setAdapter(adapter);
        mItemSizeSpinner.setPrompt(getString(R.string.selectLabel));
	    
	    return view;
	}
	
	public void updateView(Bitmap mIcon11){
		mImageView.setImageBitmap(mIcon11);
		mImageView.invalidate();
	}
}
