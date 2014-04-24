package com.rams.stopthewatch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Rules extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);
        
        Button backBtn = (Button) findViewById(R.id.backBtn);
		 backBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {

				Intent intent = new Intent(getApplicationContext(),Welcome.class);
				startActivity(intent);
				
			}
		});
	}
}
