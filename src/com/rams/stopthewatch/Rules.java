package com.rams.stopthewatch;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.rams.stopthewatch.enumerations.ApplicationConstants;
import com.stw.stopthewatch.R;

public class Rules extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);
        _SetFontStyle();
        Button backBtn = (Button) findViewById(R.id.backBtn);
		 backBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {

				Intent intent = new Intent(getApplicationContext(),Welcome.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			        
				startActivity(intent);
				
			}
		});
	}

	private void _SetFontStyle() {
		Typeface tf = Typeface.createFromAsset(getAssets(),
                ApplicationConstants.FONT_LOCATION);
        TextView tv = (TextView) findViewById(R.id.rule1);
        tv.setTypeface(tf);
        tv = (TextView) findViewById(R.id.rule2);
        tv.setTypeface(tf);
        tv = (TextView) findViewById(R.id.rule4);
        tv.setTypeface(tf);
        tv = (TextView) findViewById(R.id.rule5);
        tv.setTypeface(tf);
        tv = (TextView) findViewById(R.id.rule6);
        tv.setTypeface(tf);
        tv = (TextView) findViewById(R.id.new_game_rule);
        tv.setTypeface(tf);
        tv = (TextView) findViewById(R.id.start_rule);
        tv.setTypeface(tf);
        tv = (TextView) findViewById(R.id.scoring_rule);
        tv.setTypeface(tf);
        tv = (TextView) findViewById(R.id.rule6);
        tv.setTypeface(tf);
        tv = (TextView) findViewById(R.id.rule7);
        tv.setTypeface(tf);
        tv = (TextView) findViewById(R.id.rule6);
        tv.setTypeface(tf);
        tv = (TextView) findViewById(R.id.rules_table_header1);
        tv.setTypeface(tf);
        tv = (TextView) findViewById(R.id.rules_table_header2);
        tv.setTypeface(tf);
        tv = (TextView) findViewById(R.id.rules_offset1);
        tv.setTypeface(tf);
        tv = (TextView) findViewById(R.id.rules_offset2);
        tv.setTypeface(tf);
        tv = (TextView) findViewById(R.id.rules_offset3);
        tv.setTypeface(tf);
        tv = (TextView) findViewById(R.id.rules_offset4);
        tv.setTypeface(tf);
        tv = (TextView) findViewById(R.id.rules_offset5);
        tv.setTypeface(tf);
        tv = (TextView) findViewById(R.id.rules_offset6);
        tv.setTypeface(tf);
        tv = (TextView) findViewById(R.id.rules_offset7);
        tv.setTypeface(tf);
        tv = (TextView) findViewById(R.id.rules_offset8);
        tv.setTypeface(tf);
        tv = (TextView) findViewById(R.id.rules_offset9);
        tv.setTypeface(tf);
        tv = (TextView) findViewById(R.id.rules_offset10);
        tv.setTypeface(tf);
        tv = (TextView) findViewById(R.id.rules_offset11);
        tv.setTypeface(tf);
        tv = (TextView) findViewById(R.id.rules_offset12);
        tv.setTypeface(tf);
        tv = (TextView) findViewById(R.id.rules_offset13);
        tv.setTypeface(tf);
        tv = (TextView) findViewById(R.id.rules_offset14);
        tv.setTypeface(tf);
        tv = (TextView) findViewById(R.id.rules_offset15);
        tv.setTypeface(tf);
        tv = (TextView) findViewById(R.id.rules_offset16);
        tv.setTypeface(tf);
        tv.setTypeface(tf);
        Button bv = (Button) findViewById(R.id.backBtn);
        bv.setTypeface(tf);
        
		
	}
}
