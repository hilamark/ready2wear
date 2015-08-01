/**
 * Copyright (c) 2014-present, Facebook, Inc. All rights reserved.
 *
 * You are hereby granted a non-exclusive, worldwide, royalty-free license to use,
 * copy, modify, and distribute this software in source code or binary form for use
 * in connection with the web services and APIs provided by Facebook.
 *
 * As with any software that integrates with the Facebook platform, your use of
 * this software is subject to the Facebook Developer Principles and Policies
 * [http://developers.facebook.com/policy/]. This copyright notice shall be
 * included in all copies or substantial portions of the software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.ready2wear.main;

import java.util.Arrays;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hellofacebook.R;
import com.facebook.*;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;
import com.parse.Parse;
import com.ready2wear.domain.SessionData;
import com.ready2wear.domain.User;
import com.ready2wear.management.UsersManagment;

public class LoginScreen extends FragmentActivity {

	private static final String TAG = "LoginScreen";
    private final String PENDING_ACTION_BUNDLE_KEY =
            "com.example.hellofacebook:PendingAction";

    private ProfilePictureView profilePictureView;
    private TextView greeting;
    private CallbackManager callbackManager;
    private ProfileTracker profileTracker;
    private Button mSave;
    private TextView mDetailsTitle;
    private EditText mUserAdress;
    private EditText mUserPhone;
    private TextView mUserAdressLabel;
    private TextView mUserPhoneLabel;
    private LoginButton mFBLogin;
    private Button mRemoveDB;
    
    private Profile currentProfile;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);       
        Parse.initialize(this, "fcadvUu586ZSR6Whu9Ah9NZ2ugR5PMbTzTMUnd4v", "Yv9iCTGvjEIOjIZopg2myTG0tqPy9etArssH3CH6");

        FacebookSdk.sdkInitialize(this.getApplicationContext());

        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                       // updateUser(loginResult);
                        updateUI();
                    }

                    @Override
                    public void onCancel() {
                        updateUI();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        updateUI();
                    }
                });

        if (savedInstanceState != null) {
            String name = savedInstanceState.getString(PENDING_ACTION_BUNDLE_KEY);
        }

        setContentView(R.layout.main);
        
        mFBLogin = (LoginButton) findViewById(R.id.fbLoginButon);
      //  mFBLogin.setReadPermissions(Arrays.asList("user_status",
      //  					"public_profile", "user_birthday", "user_friends")); 
		mFBLogin.setLoginBehavior(LoginBehavior.WEB_ONLY);
        
        // Hide details 
        mDetailsTitle = (TextView)findViewById(R.id.moreDetail);
        mDetailsTitle.setVisibility(View.INVISIBLE);
        mUserAdress =  (EditText) findViewById(R.id.userAdressEditText);
        mUserAdress.setVisibility(View.INVISIBLE);
        mUserPhone = (EditText) findViewById(R.id.userPhoneEditText);
        mUserPhone.setVisibility(View.INVISIBLE);
        
        mUserAdressLabel = (TextView) findViewById(R.id.userAdress);
        mUserAdressLabel.setVisibility(View.INVISIBLE);
        mUserPhoneLabel = (TextView) findViewById(R.id.userPhoneNumLabel);
        mUserPhoneLabel.setVisibility(View.INVISIBLE);
        
        mSave = (Button) findViewById(R.id.userDetailsSubmit);
        mSave.setVisibility(View.INVISIBLE);
        mSave.setBackgroundColor(Color.BLACK);
        mSave.setTextColor(Color.WHITE);
        
        mSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Log.d(TAG, "Submit button pressed");
            	User currUser = SessionData.getInstance().getCurrentUser();
            	currUser.setAdress(mUserAdress.getText().toString());
            	currUser.setPhoneNumber(mUserPhone.getText().toString());
            	
            	UsersManagment.saveUser(currUser);
            	
            	// Change view
            	updateUI();
            }
        });
        
        mRemoveDB = (Button) findViewById(R.id.removeUserButton);
        mRemoveDB.setVisibility(View.INVISIBLE);
        
        mRemoveDB.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Log.d(TAG, "Remove user button pressed");
            	
            	UsersManagment.removeUser(currentProfile.getId());
            	Toast.makeText(LoginScreen.this, "You have been deleted from DB", Toast.LENGTH_SHORT).show();
            }
        });
        		

        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
            	updateUser(null);
                updateUI();
            }
        };

        profilePictureView = (ProfilePictureView) findViewById(R.id.profilePicture);
        greeting = (TextView) findViewById(R.id.greeting);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Call the 'activateApp' method to log an app event for use in analytics and advertising
        // reporting.  Do so in the onResume methods of the primary Activities that an app may be
        // launched into.
        updateUser(null);
        updateUI();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onPause() {
        super.onPause();

        // Call the 'deactivateApp' method to log an app event for use in analytics and advertising
        // reporting.  Do so in the onPause methods of the primary Activities that an app may be
        // launched into.
        AppEventsLogger.deactivateApp(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        profileTracker.stopTracking();
    }
    
    private void updateUser(LoginResult loginResult) {
    	currentProfile = Profile.getCurrentProfile();
    	
    	// Sanity check
    	if (currentProfile == null)
    		return;
    	
    	// Init
    	User currentUser = null;
    	
    	// Check if user logged out
    	if (currentProfile.getId() == null || currentProfile.getId().isEmpty())
    		currentUser = new User();
    	else
    		// Try getting user from DB
    		currentUser = UsersManagment.getUser(currentProfile.getId());
    	
    	// Check if need to fetch data
    	if (currentUser == null){
	    	
	    	// Get more data
	  /*      GraphRequest request = GraphRequest.newMeRequest(
	                loginResult.getAccessToken(),
	                new GraphRequest.GraphJSONObjectCallback() {
	                    @Override
	                    public void onCompleted(
	                            JSONObject object,
	                            GraphResponse response) {
	                        // Application code
	                        Log.v(TAG, response.toString());
	                    }
	                });
	        Bundle parameters = new Bundle();
	        parameters.putString("fields", "email");
	        request.setParameters(parameters);
	        request.executeAsync();
	        */
	    	// Update current user
	    	currentUser = new User(currentProfile);
	    /*	try {
				currentUser.setEmail(request.getGraphObject().getString("email"));
			} catch (JSONException e) {
				Log.v(TAG, "Failed fetching email");
			} */
    	}
    	
    	// Set as current user
		SessionData.getInstance().setCurrentUser(currentUser);
    }

    private void updateUI() {
        boolean enableButtons = AccessToken.
        		getCurrentAccessToken() != null;

        currentProfile = Profile.getCurrentProfile();
        if (enableButtons && currentProfile != null) {
        	
        	// Enable more data on view
            profilePictureView.setProfileId(currentProfile.getId());
            greeting.setText(getString(R.string.hello_user, currentProfile.getFirstName()));
            
            // Check if need to update current user
          //  if (SessionData.getInstance().getCurrentUser() == null)
          //  	SessionData.getInstance().setCurrentUser(new User(currentProfile));
            
            // Check if need more info
            if (SessionData.getInstance().getCurrentUser() != null && (SessionData.getInstance().getCurrentUser().getAdress() == null ||
            		SessionData.getInstance().getCurrentUser().getAdress().isEmpty()))
            {
	            mDetailsTitle.setVisibility(View.VISIBLE);
	            mUserAdress.setVisibility(View.VISIBLE);
	            mUserAdressLabel.setVisibility(View.VISIBLE);
	            mUserPhone.setVisibility(View.VISIBLE);
	            mUserPhoneLabel.setVisibility(View.VISIBLE);
	            mSave.setVisibility(View.VISIBLE);
            }
            else if (SessionData.getInstance().getCurrentUser() != null && UsersManagment.userAlreadySignedup(currentProfile.getId())){
            	cleanDetailsEditView();
            	mRemoveDB.setVisibility(View.VISIBLE);
            }
        } else {
        	// Clean view
            profilePictureView.setProfileId(null);
            greeting.setText(null);
            cleanDetailsEditView();
            mRemoveDB.setVisibility(View.INVISIBLE);
        }
    }
    
    private void cleanDetailsEditView(){
    	mDetailsTitle.setVisibility(View.INVISIBLE);
        mUserAdress.setVisibility(View.INVISIBLE);
        mUserAdressLabel.setVisibility(View.INVISIBLE);
        mUserPhone.setVisibility(View.INVISIBLE);
        mUserPhoneLabel.setVisibility(View.INVISIBLE);
        mSave.setVisibility(View.INVISIBLE);
    }
}
