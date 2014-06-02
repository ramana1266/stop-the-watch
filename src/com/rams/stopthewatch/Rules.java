package com.rams.stopthewatch;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.rams.stopthewatch.enumerations.ApplicationConstants;
import com.stw.stopthewatch.R;

public class Rules extends Activity {
	
	private AdView adView;

    private static final String AD_UNIT_ID = "ca-app-pub-1028881757084159/2077087225";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //Ads Management Section Start
        
        _ShowAds(this);

        //Ads Management Section End
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

	private void _ShowAds(Rules rules) {
		// Create an ad.
        adView = new AdView(rules);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId(AD_UNIT_ID);

        // Add the AdView to the view hierarchy. The view will have no size
        // until the ad is loaded.
        LinearLayout layout = (LinearLayout) findViewById(R.id.ads_layout_in_rules);
        layout.addView(adView);

        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device.
        AdRequest adRequest = new AdRequest.Builder()
       //     .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
         //   .addTestDevice("31427A98644C46A99506BA2AC7DD0031")
            .build();
        
        // Start loading the ad in the background.
        adView.loadAd(new AdRequest.Builder().build());
        
        adView.loadAd(adRequest);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
	}

	private void _SetFontStyle() {
		Typeface tf = Typeface.createFromAsset(getAssets(),
                ApplicationConstants.FONT_LOCATION);
        TextView tv = (TextView) findViewById(R.id.rule1);
        tv.setTypeface(tf);
        tv = (TextView) findViewById(R.id.rule2);
        tv.setTypeface(tf);
        tv = (TextView) findViewById(R.id.rule3);
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
