package com.rams.stopthewatch;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;
import android.app.AlertDialog;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;


import com.rams.stopthewatch.Business.IGamePlayBusiness;
import com.rams.stopthewatch.Entity.GameEntity;
import com.rams.stopthewatch.Entity.StopWatchEntity;
import com.rams.stopthewatch.Factory.GamePlayFactory;
import com.rams.stopthewatch.enumerations.ApplicationConstants;
import com.rams.stopthewatch.enumerations.GamePlayType;

public class Welcome extends Activity {
	public Toast toast;
	public boolean dontStartNewGame = false;
	public static GameEntity gamePlay; 
	private AdView adView;

    private static final String AD_UNIT_ID = "ca-app-pub-1028881757084159/2077087225";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        
        //Ads Management Section Start
        
        // Create an ad.
        adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId(AD_UNIT_ID);

        // Add the AdView to the view hierarchy. The view will have no size
        // until the ad is loaded.
        LinearLayout layout = (LinearLayout) findViewById(R.id.ads_layout);
        layout.addView(adView);

        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device.
        AdRequest adRequest = new AdRequest.Builder()
            .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
            .addTestDevice("31427A98644C46A99506BA2AC7DD0031")
            .build();
        
        // Start loading the ad in the background.
        //adView.loadAd(new AdRequest.Builder().build());
        
        adView.loadAd(adRequest);

        //Ads Management Section End
        
        // This should change later to get high score from the chosen game play
		// Right now, the only supported gameplay is ChancesToZeroGamePlay.
        try {
			((TextView)findViewById(R.id.best)).setText("Best: " +  GamePlayFactory.GetGamePlayBusiness(GamePlayType.ChancesToZero).GetHighScore(this));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        Button rulesButton = (Button) findViewById(R.id.rulesBtn);
        
        rulesButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent intent = new Intent(getApplicationContext(),Rules.class);
				startActivity(intent);
			}
			 			
		});
        
        
        
        Button newGameButton = (Button) findViewById(R.id.newGameBtn);
        newGameButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// This should change later to choose what game user wants to play
				// Right now, the only supported gameplay is ChancesToZeroGamePlay.
				final GamePlayType gamePlayType = GamePlayType.ChancesToZero;				
				
				try {
					
					if(gamePlay!=null && gamePlay.IsGameStarted){
						new AlertDialog.Builder(Welcome.this)
						.setTitle("New Game")
						.setMessage("You will lose your progress. Are you sure?")	
						//.setIcon(android.R.drawable.ic_dialog_alert)
						.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

						    public void onClick(DialogInterface dialog, int whichButton) {
						    	gamePlay.StopWatchEntity.MyHandler.removeCallbacks(UpdateTimerMethod);
						    	
						    	_StartNewGame(gamePlayType);

						    }
						    })
						    .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
				                   public void onClick(DialogInterface dialog, int id) {
				                       
				                   }
				               }).show();	

						//gamePlay.StopWatchEntity.MyHandler.removeCallbacks(UpdateTimerMethod);
					}
					else{
						_StartNewGame(gamePlayType);
						
					}
					
				} catch (Exception e) {
					Log.wtf("START_FAIL", "failed to start the game");
					e.printStackTrace();
				}
			}

			private void _StartNewGame(GamePlayType gamePlayType) {
				try {
					gamePlay = GamePlayFactory.GetGamePlayBusiness(gamePlayType).StartGame();
				
				SetScreenValues(true);
				
				((TextView)findViewById(R.id.stopwatch)).setTextSize(75);
				((TextView)findViewById(R.id.stopwatch)).setText(gamePlay.ClockTime);
				((Button)findViewById(R.id.start_stop_btn)).setVisibility(1);
				((TextView)findViewById(R.id.gameOvertxt)).setVisibility(-1);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			
		});
        
        
        Button starStopButton = (Button) findViewById(R.id.start_stop_btn);
        
        starStopButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				try {
				IGamePlayBusiness gamePlayBusiness = GamePlayFactory.GetGamePlayBusiness(gamePlay.GameType);
				if(gamePlay.IsGameStarted){
					
					gamePlay.StopWatchEntity.TimeSwap += gamePlay.StopWatchEntity.TimeInMillies;
					gamePlay.StopWatchEntity.MyHandler.removeCallbacks(UpdateTimerMethod);
					gamePlay.ClockTime = ((TextView)findViewById(R.id.stopwatch)).getText().toString(); 
					
					int oldTally = gamePlay.Tally;
					
					gamePlay = gamePlayBusiness.UpdateScores(gamePlay);
					if(gamePlayBusiness.GetHighScore(getBaseContext()) < gamePlay.Score)
						gamePlayBusiness.SetHighScore(getBaseContext(), gamePlay.Score);
					
					ShowToast(oldTally,getApplicationContext());
					SetScreenValues(false);
					gamePlay.IsGameStarted = false;
					
					
				}
				else
				{
					gamePlay.StopWatchEntity.StartTime = SystemClock.uptimeMillis();
					gamePlay.StopWatchEntity.MyHandler.postDelayed(UpdateTimerMethod, 0); 
					SetScreenValues(false);
					gamePlay.IsGameStarted = true;
				}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			private void ShowToast(int oldTally,Context context) {
				
				if(toast!=null)
					toast.cancel();
				LayoutInflater inflater = LayoutInflater.from(context);

	            View mainLayout = inflater.inflate(R.layout.toast_layout, null);
	            View rootLayout = mainLayout.findViewById(R.id.toast_layout_root);
	            
	            mainLayout.setBackgroundColor(0);	            
	            TextView text = (TextView) mainLayout.findViewById(R.id.text);
	            String toastVal ;
	            if(gamePlay.Tally-oldTally>0){
	            	toastVal = "+" + Integer.toString(gamePlay.Tally-oldTally);
	            	text.setTextColor(Color.BLUE);
	            }
	            else{
	            	toastVal = Integer.toString(gamePlay.Tally-oldTally);
	            	text.setTextColor(Color.RED);
	            }
	            text.setText(toastVal);

	            toast = new Toast(context);
	            toast.setGravity(Gravity.TOP|Gravity.LEFT, 485, 298);
	            toast.setDuration(Toast.LENGTH_SHORT);
	            toast.setView(rootLayout);
	            toast.show();
	            
				
				
				
				//Toast toast = Toast.makeText(getApplicationContext(), Integer.toString(gamePlay.Tally-oldTally), Toast.LENGTH_SHORT);
				
				//View view = toast.getView();
				//view.setBackgroundColor(0);
				//toast.
				//toast.show();
				
			}
		});
    }

    protected void UpdateScores() {
		// TODO Auto-generated method stub
		
	}

	protected void SetScreenValues(boolean newGame) {
		try {
    	if(gamePlay.IsGameOver){
    		
    		//((TextView)findViewById(R.id.stopwatch)).setTextSize(55);
    		//((TextView)findViewById(R.id.stopwatch)).setText("Game Over");
    		
    		((Button)findViewById(R.id.start_stop_btn)).setVisibility(-1);
    		((TextView)findViewById(R.id.gameOvertxt)).setVisibility(1);
    	}
    	
		if(gamePlay.IsGameStarted || newGame){
			((Button)findViewById(R.id.start_stop_btn)).setText("Start");
		}
		else{
    	((Button)findViewById(R.id.start_stop_btn)).setText("Stop");
    	}
		((TextView)findViewById(R.id.score)).setText("Score: " + gamePlay.Score);
		((TextView)findViewById(R.id.best)).setText("Best: " +  GamePlayFactory.GetGamePlayBusiness(gamePlay.GameType).GetHighScore(this));
		((TextView)findViewById(R.id.tallyValue)).setText("Tally: " + gamePlay.Tally);
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}



	private Runnable UpdateTimerMethod = new Runnable() {

    	@Override
		public void run() {
    		gamePlay.StopWatchEntity.TimeInMillies = SystemClock.uptimeMillis() - gamePlay.StopWatchEntity.StartTime;
    		gamePlay.StopWatchEntity.FinalTime = gamePlay.StopWatchEntity.TimeSwap + gamePlay.StopWatchEntity.TimeInMillies;

    	int seconds = (int) (gamePlay.StopWatchEntity.FinalTime / 1000);
    	int minutes = seconds / 60;
    	seconds = seconds % 60;
    	int milliseconds = (int) (gamePlay.StopWatchEntity.FinalTime % 1000)/10;
    	
    	((TextView)findViewById(R.id.stopwatch)).setText("" + String.format("%02d", minutes) + ":"
    	    	+ String.format("%02d", seconds) + ":"
    	    	+ String.format("%02d", milliseconds));
    	if(gamePlay.StopWatchEntity.TimeInMillies >= 10000){
    		gamePlay.IsGameOver = true;
    		gamePlay.IsGameStarted =false;
    		SetScreenValues(false);
    	}
    	else{
    	gamePlay.StopWatchEntity.MyHandler.postDelayed(this, 0);
    	}
    	}

		
    	};
    	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.welcome, menu);
        return true;
    }
    @Override
    public void onResume() {
      super.onResume();
      if (adView != null) {
        adView.resume();
      }
    }

    @Override
    public void onPause() {
      if (adView != null) {
        adView.pause();
      }
      super.onPause();
    }

    /** Called before the activity is destroyed. */
    @Override
    public void onDestroy() {
      // Destroy the AdView.
      if (adView != null) {
        adView.destroy();
      }
      super.onDestroy();
    }

    
}
