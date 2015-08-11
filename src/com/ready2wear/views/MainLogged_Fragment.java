package com.ready2wear.views;


import java.io.InputStream;

import com.example.hellofacebook.R;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


public class MainLogged_Fragment extends Fragment {
	
	private ImageView mImage;

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_main_logged__fragment, container, false);
    /*    mImage = (ImageView) rootView.findViewById(R.id.itemsImage);
        
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy); 
        
        InputStream in = null;
		try {
			in = new java.net.URL("http://2.bp.blogspot.com/-7Blix1Qejug/VFjKmcUwyjI/AAAAAAAACWo/qy3ICiSkNEM/s1600/IMG_1660.PNG").openStream();
			Bitmap mIcon11 = BitmapFactory.decodeStream(in);
			
			mImage.setImageBitmap(mIcon11);
		} catch (Exception e) {
			Log.d(getTag(), e.getMessage());
		} */

        return rootView;
    }

}
