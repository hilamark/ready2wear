package com.ready2wear.views;

import com.example.hellofacebook.R;
import com.parse.Parse;
import com.ready2wear.domain.SessionData;
import com.ready2wear.management.UsersManagment;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

public class DrawerList {

	private FragmentManager mFragmentManager;
	private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private ActionBar mActionBar;
    private Activity hostActivity;

    private String[] mTitles;
    
    public DrawerList(ActionBar actionBar){
    	mActionBar = actionBar;
    }
    
    public DrawerList(Resources res, View drawerLayout, View drawerList, Context context,
    		FragmentManager fManager, boolean selectFirst, Activity host, ActionBar actionBar){
    	
    	hostActivity = host;
    	mFragmentManager = fManager;
    	mActionBar = actionBar;
    	updateBaR();
    	
    	mTitles = res.getStringArray(R.array.menu_array);
        mDrawerLayout = (DrawerLayout) drawerLayout;
        mDrawerList = (ListView) drawerList;
        
     // set up the drawer's list view with items and click listener
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                R.layout.drawer_list_item, mTitles);
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
        		host,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.menu_circle_icon,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
                ) {

        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (selectFirst) {
            selectItem(0);
        }
    }
    
    public void updateBaR(){
    	mActionBar.setDisplayOptions(mActionBar.getDisplayOptions()
                | ActionBar.DISPLAY_SHOW_CUSTOM);
        ImageView imageView = new ImageView(mActionBar.getThemedContext());
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        imageView.setImageResource(R.drawable.readytowear);
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(
                400,
                55, Gravity.CENTER_HORIZONTAL
                        | Gravity.CENTER_VERTICAL);
        //layoutParams.rightMargin = 250;
        imageView.setLayoutParams(layoutParams);
        mActionBar.setCustomView(imageView);
    }
    
    /* The click listner for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }
    
    public void selectItem(int position) {
        // update the main content by replacing fragments
    	Fragment fragment = null;
    	switch(position){
    		case 0:{
    			fragment = new MainLogged_Fragment();
    			mFragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
    			break;
    		}
    		case 1:{
    			fragment = new MyWardrove();
    			mFragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
    			break;
    		}
    		case 3:{
    			UsersManagment.removeUser(SessionData.getInstance().getCurrentProfile().getId());
            	Toast.makeText(hostActivity, "You have been deleted from DB", Toast.LENGTH_SHORT).show();
    			break;
    		}
			default:
				break;
    	}

        // update selected item and title, then close the drawer
        mDrawerList.setItemChecked(position, true);
        mDrawerLayout.closeDrawer(mDrawerList);
    }
    
    public boolean onOptionsItemSelected(MenuItem item) {
         return mDrawerToggle.onOptionsItemSelected(item);
    }

    public boolean isDrawerOpen(){
    	return mDrawerLayout.isDrawerOpen(mDrawerList);
    }
    
    protected void onPostCreate(Bundle savedInstanceState) {
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    public void onConfigurationChanged(Configuration newConfig) {
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
}
