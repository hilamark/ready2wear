package com.ready2wear.views;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

import com.example.hellofacebook.R;
import com.ready2wear.domain.Item;
import com.ready2wear.domain.SessionData;
import com.ready2wear.utils.SeekBarPreference;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;

public class AddItemFragment extends Fragment {

	private Button mDone;
	private TableLayout mTableLayout;
	private SeekBar mSeekBar;
	private TextView mSeekVal;
	private RadioGroup mSize;
	private RadioGroup mColor;
	private CheckBox mDuration3Days;
	
	@Override
	  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	    View view = inflater.inflate(R.layout.activity_add_item_fragment, container, false);
	    
	    mTableLayout = (TableLayout) view.findViewById(R.id.itemDataTable);
	    
	    mSize = (RadioGroup) view.findViewById(R.id.sizeRadioGroup);
	    mColor = (RadioGroup) view.findViewById(R.id.colorRadioGroup);
	    mDuration3Days = (CheckBox) view.findViewById(R.id.threedCheckBox);
	    mSeekVal = (TextView) view.findViewById(R.id.priceVal);
	    mSeekBar = (SeekBar) view.findViewById(R.id.priceSeekbar);
	    mSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar,
                    int progress, boolean fromUser) {
            			mSeekVal.setText(Integer.toString(progress + 15));
            			mSeekVal.invalidate();
                            }

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
	    });	    
	    
	    mDone = (Button) view.findViewById(R.id.itemDoneButton);
		mDone.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onDoneButtonClicked(v);
            }
        });
		
	    return view;
	}
	
	private void onDoneButtonClicked(View v) {
		// Create item
		Item item = new Item();
		int index = getCheckedRadioData(mSize);
		item.setSize((getResources().getStringArray(R.array.size_array))[index]);
		item.setPricePerDay(Integer.parseInt((String) mSeekVal.getText()));
		index = getCheckedRadioData(mColor);
		item.setColor((getResources().getStringArray(R.array.color_array))[index]);
		item.setDuration(mDuration3Days.isChecked() ? "3Days" : "1Week");
		item.setOwnerID(SessionData.getInstance().getCurrentUser().getParseID());
		
		// Pass to owner activity to add data and save
		AddItems activity = (AddItems) getActivity();
		activity.saveItem(item);
	}

	public void updateVisibility(){
		mTableLayout.setVisibility(View.VISIBLE);
	}
	
	private int getCheckedRadioData(RadioGroup radioButtonGroup){
		int radioButtonID = radioButtonGroup.getCheckedRadioButtonId();
		View radioButton = radioButtonGroup.findViewById(radioButtonID);
		return radioButtonGroup.indexOfChild(radioButton);
	}
}
