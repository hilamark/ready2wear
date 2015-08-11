package com.ready2wear.views;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.example.hellofacebook.R;
import com.facebook.CallbackManager;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginBehavior;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;
import com.ready2wear.domain.Item;
import com.ready2wear.domain.SessionData;
import com.ready2wear.main.LoginScreen;
import com.ready2wear.management.UsersManagment;
import com.ready2wear.views.AddItems.CustomBitmapList;

import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class MyWardrove extends Fragment {
	
	private static final String TAG = "LoginScreen";

	private static CharSequence[] options = { "Edit item", "Remove item", "Cancel" };
	private View mView;
    private ProfilePictureView profilePictureView;
    private TextView greeting;
    private ProfileTracker profileTracker;
    
    private Button mAddItem;
    private TextView mMyAddress;
    private TextView mMyPhone;
    
    private CustomBitmapList mAdapter;
    private GridView mItemsGrid;
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		// Inflate the layout for this fragment
		mView = inflater.inflate(R.layout.fragment_my_wardrove, container, false);
		
		profilePictureView = (ProfilePictureView) mView.findViewById(R.id.profilePicture);
        greeting = (TextView) mView.findViewById(R.id.greeting);
        
        mMyAddress = (TextView) mView.findViewById(R.id.addressTextView);
        mMyAddress.setText(SessionData.getInstance().getCurrentUser().getAdress() + 
        		", Tel Aviv");
        mMyPhone = (TextView) mView.findViewById(R.id.phoneTextView);
        mMyPhone.setText(SessionData.getInstance().getCurrentUser().getPhoneNumber());
		
		// Enable more data on view
        profilePictureView.setProfileId(SessionData.getInstance().getCurrentProfile().getId());
        greeting.setText(SessionData.getInstance().getCurrentProfile().getFirstName() +
        		" " + SessionData.getInstance().getCurrentProfile().getLastName());
        
        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
            	// Check if user has just logged out
            	if (currentProfile == null){
            		// Go back to main screen
                    getActivity().finish(); 
            	}
            }
        };
        
        mAddItem = (Button) mView.findViewById(R.id.listAnItem_button);
        mAddItem.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	openListItem();
            }
        });
        
        mItemsGrid = (GridView) mView.findViewById(R.id.myItemsGrid);
        mAdapter = new CustomBitmapList(getActivity(), (ArrayList<Item>) 
        			SessionData.getInstance().getCurrentUser().getItems());
        mItemsGrid.setAdapter(mAdapter);
        
		return mView;
	}
	
	private void openListItem(int position){
		// Get item
		Item item = SessionData.getInstance().getCurrentUser().getItem(position);
		
		// Get view
		Intent itemsPage= new Intent(getActivity(), AddItems.class);
		
		// Set view with relevant item
		
		// Open view
        getActivity().startActivityForResult(itemsPage, 1);
	}
	
	private void openListItem(){
		Intent itemsPage= new Intent(getActivity(),AddItems.class);
        getActivity().startActivityForResult(itemsPage, 1);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        updateGrid();
    }
	
	private void updateGrid(){
		 mAdapter = new CustomBitmapList(getActivity(), (ArrayList<Item>) 
    			SessionData.getInstance().getCurrentUser().getItems());
        mAdapter.notifyDataSetChanged();
		mItemsGrid.setAdapter(mAdapter);
		MainLogged activity = (MainLogged) getActivity();
		activity.refreshWardrove();
	}
	
	public class CustomBitmapList extends ArrayAdapter<Item>{
		
		private Activity mContext;
		private List<Item> mItems;

    	public CustomBitmapList(Activity context, ArrayList<Item> itemList) {
    		super(context, 0, itemList);	
    		mContext = context;
    		mItems = itemList;
    	}
    	
    	@Override
    	public View getView(int position, View view, ViewGroup parent) {
    		 ImageView imageView;
    		 final int currPos = position;
		      
		      if (view == null) {
		         imageView = new ImageView(mContext);
		         imageView.setLayoutParams(new GridView.LayoutParams(250, 350));
		         imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
		        // imageView.setPadding(8, 8, 8, 8);
		      } 
		      else
		      {
		         imageView = (ImageView) view;
		      }

		    imageView.setImageBitmap(mItems.get(position).getImages().get(0));
		    
		    // on click, open menu
        	imageView.setOnClickListener(new View.OnClickListener() {
        		public void onClick(View view) {
        			// open dialog
        			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        			builder.setTitle(null);
        			builder.setItems(options, new DialogInterface.OnClickListener() {
        				
	        			@Override
	        			public void onClick(DialogInterface dialog, int item) {
	        				if (options[item].equals("Edit item")) {
	        					openListItem();
	        				} 
	        				else if (options[item].equals("Remove item")) {
	        					SessionData.getInstance().getCurrentUser().removeItem(currPos);
	        					updateGrid();
	        				} 
	        				else if (options[item].equals("Cancel")) {
	        					dialog.dismiss();
	        				}
	        			}
        			});
    			builder.show();
        		}
        	});
		    return imageView;
		   }
    	}
}
