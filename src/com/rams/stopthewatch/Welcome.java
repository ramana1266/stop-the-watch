package com.rams.stopthewatch;

import java.io.Serializable;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.app.AlertDialog;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.common.*;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.BaseGameActivity;


import com.rams.stopthewatch.Business.IGamePlayBusiness;
import com.rams.stopthewatch.Entity.GameEntity;
import com.rams.stopthewatch.Entity.StopWatchEntity;
import com.rams.stopthewatch.Factory.GamePlayFactory;
import com.rams.stopthewatch.enumerations.ApplicationConstants;
import com.rams.stopthewatch.enumerations.GamePlayType;
import com.stw.stopthewatch.R;

public class Welcome extends BaseGameActivity {
	public Toast toast;
	public Toast hint_toast;
	public Toast hint_toast_dup_for_more_time;
	public boolean dontStartNewGame = false;
	public static GameEntity gamePlay; 
	private AdView adView;
	//GoogleApiClient mGoogleClient;

    private static final String AD_UNIT_ID = "ca-app-pub-1028881757084159/2077087225";

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        new STWEula(this).show();
        //Ads Management Section Start
        
        _ShowAds(this);

        //Ads Management Section End

        //Setting custom fonts on all text views
        _SetCustomFonts();
      //Setting custom fonts on all text views
        
        // This should change later to get high score from the chosen game play
		// Right now, the only supported gameplay is ChancesToZeroGamePlay.
        try {
			((TextView)findViewById(R.id.best)).setText("Best: " +  GamePlayFactory.GetGamePlayBusiness(GamePlayType.ChancesToZero).GetHighScore(this));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
      //Google Game 
      //  mGoogleClient = new GoogleApiClient.Builder(this)
      // .addApi(Games.API)
      // .addScope(Games.SCOPE_GAMES)
      // .setGravityForPopups(Gravity.TOP | Gravity.CENTER_HORIZONTAL)
      // .build();
        
        Button rulesButton = (Button) findViewById(R.id.rulesBtn);
        
        rulesButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onPause();
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
						.setPositiveButton(R.string.yes_option, new DialogInterface.OnClickListener() {

						    public void onClick(DialogInterface dialog, int whichButton) {
						    	gamePlay.StopWatchEntity.MyHandler.removeCallbacks(UpdateTimerMethod);
						    	
						    	_StartNewGame(gamePlayType);

						    }
						    })
						    .setNegativeButton(R.string.no_option, new DialogInterface.OnClickListener() {
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
				((TextView)findViewById(R.id.stopwatch)).setTextSize(110);
				((TextView)findViewById(R.id.stopwatch)).setText(gamePlay.ClockTime);
				((Button)findViewById(R.id.start_stop_btn)).setVisibility(1);
				((TextView)findViewById(R.id.gameOvertxt)).setVisibility(-1);
				((Button)findViewById(R.id.leaderboard_button)).setVisibility(-1);
				GamePlayFactory.GetGamePlayBusiness(gamePlayType).ResetGameStopCount(getBaseContext());
				GamePlayFactory.GetGamePlayBusiness(gamePlayType).ResetTimerSlowDownFactor(getBaseContext());
				
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			
		});
        
        Button leaderboardButton = (Button) findViewById(R.id.leaderboard_button);
        
		  leaderboardButton.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					startActivityForResult(Games.Leaderboards.getLeaderboardIntent(getApiClient(), ApplicationConstants.GOOGLE_PLAY_LEADERBOARD_ID), 1);
				}
				 			
		  });
        
        Button starStopButton = (Button) findViewById(R.id.start_stop_btn);
        
        starStopButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				try {
				IGamePlayBusiness gamePlayBusiness = GamePlayFactory.GetGamePlayBusiness(gamePlay.GameType);
				if(gamePlay.IsGameStarted){
					//Stop is clicked
					
					//gamePlay.StopWatchEntity.TimeSwap += gamePlay.StopWatchEntity.TimeInMillies;
					gamePlayBusiness.UpdateTimerSlowDownFactor(getBaseContext());
					int timer_slowdown_factor = gamePlayBusiness.GetTimerSlowDownFactor(getBaseContext());
					//gamePlay.StopWatchEntity.TimeSwap /= timer_slowdown_factor;
					gamePlay.StopWatchEntity.TimeSwap = gamePlay.StopWatchEntity.FinalTime*timer_slowdown_factor;
					gamePlay.StopWatchEntity.MyHandler.removeCallbacks(UpdateTimerMethod);
					gamePlay.ClockTime = ((TextView)findViewById(R.id.stopwatch)).getText().toString(); 
					
					gamePlayBusiness.UpdateGameStopCount(getBaseContext());
					
					int oldTally = gamePlay.Tally;
					
					gamePlay = gamePlayBusiness.UpdateScores(gamePlay);
					if(gamePlayBusiness.GetHighScore(getBaseContext()) < gamePlay.Score)
						gamePlayBusiness.SetHighScore(getBaseContext(), gamePlay.Score);
					if(gamePlayBusiness.IsHintModeEnabled(getBaseContext())){
						gamePlayBusiness.UpdateHintDisplayCount(getBaseContext());
						ShowHintToast(oldTally, getApplicationContext());
						gamePlayBusiness.UpdateHintMode(getBaseContext());
					}
					else{
						ShowToast(oldTally,getApplicationContext());
					}
					
					SetScreenValues(false);
					gamePlay.IsGameStarted = false;
					
					
				}
				else
				{
					if(hint_toast!=null)
						hint_toast.cancel();
					if(toast!=null)
						toast.cancel();
					
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
			
			
			
			

			private void ShowHintToast(int oldTally, Context context) throws Exception {
				if(hint_toast!=null)
					hint_toast.cancel();
				LayoutInflater inflater = LayoutInflater.from(context);

	            View mainLayout = inflater.inflate(R.layout.hints, null);
	            View rootLayout = mainLayout.findViewById(R.id.hints_layout_root);
	            
	            mainLayout.setBackgroundColor(0);	            
	            TextView text = (TextView) mainLayout.findViewById(R.id.hint_text);
	            String toastVal;
	            String hintsVal ;
	            SpannableStringBuilder hintsValSb;
	            if(gamePlay.Tally-oldTally>0){
	            	toastVal = "+" + Integer.toString(gamePlay.Tally-oldTally);
	            	hintsVal = "WOO HOO!! You received " + toastVal + " points. "+ ApplicationConstants.GetGoodJobString(GamePlayFactory.GetGamePlayBusiness(gamePlay.GameType).GetHintDisplayCount(context));
	            	hintsValSb = new SpannableStringBuilder(hintsVal);
	            	ForegroundColorSpan fcs = new ForegroundColorSpan(Color.rgb(0, 0, 255));
	            	hintsValSb.setSpan(fcs, 23 , 25, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
	            	  
	            }
	            else{
	            	toastVal = Integer.toString(oldTally - gamePlay.Tally);
	            	hintsVal = "OOPS!! You lost " + toastVal + " points." + ApplicationConstants.GetBadJobString(GamePlayFactory.GetGamePlayBusiness(gamePlay.GameType).GetHintDisplayCount(context));
	            	hintsValSb = new SpannableStringBuilder(hintsVal);
	            	ForegroundColorSpan fcs = new ForegroundColorSpan(Color.rgb(255, 0, 0));
	            	hintsValSb.setSpan(fcs, 16 , 18, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
	            }
	            
	            text.setTextColor(Color.BLACK);
	            Typeface tf = Typeface.createFromAsset(getAssets(),
	                    ApplicationConstants.FONT_LOCATION);
	            text.setTypeface(tf);
	            text.setText(hintsValSb);
	            
	            hint_toast = new Toast(context);
	            hint_toast.setGravity(Gravity.TOP|Gravity.LEFT, -150, 560);
	            hint_toast.setDuration(Toast.LENGTH_LONG);
	            hint_toast.setView(rootLayout);
	            hint_toast.show();
	            
	            
	            
			}

			private void ShowToast(int oldTally,Context context) {
				
				if(toast!=null)
					toast.cancel();
				LayoutInflater inflater = LayoutInflater.from(context);

	            View mainLayout = inflater.inflate(R.layout.toast_layout, null);
	            View rootLayout = mainLayout.findViewById(R.id.toast_layout_root);
	            
	            mainLayout.setBackgroundColor(0);	            
	            TextView text = (TextView) mainLayout.findViewById(R.id.toast_val);
	            String toastVal ;
	            if(gamePlay.Tally-oldTally>0){
	            	toastVal = "+" + Integer.toString(gamePlay.Tally-oldTally);
	            	text.setTextColor(Color.BLUE);
	            }
	            else{
	            	toastVal = Integer.toString(gamePlay.Tally-oldTally);
	            	text.setTextColor(Color.RED);
	            }
	            Typeface tf = Typeface.createFromAsset(getAssets(),
	                    ApplicationConstants.FONT_LOCATION);
	            text.setTypeface(tf);
	            text.setText(toastVal);
	            

	            toast = new Toast(context);
	            toast.setGravity(Gravity.TOP|Gravity.LEFT, 495, 295);
	            toast.setDuration(Toast.LENGTH_SHORT);
	            toast.setView(rootLayout);
	            toast.show();
	            
	            
	            }
		});
        
        
        
    }

    private void _SetCustomFonts() {
    	
    	Typeface tf = Typeface.createFromAsset(getAssets(),
                ApplicationConstants.FONT_LOCATION);
        TextView tv = (TextView) findViewById(R.id.score);
        tv.setTypeface(tf);
        tv = (TextView) findViewById(R.id.best);
        tv.setTypeface(tf);
        tv = (TextView) findViewById(R.id.tallyValue);
        tv.setTypeface(tf);
        tv = (TextView) findViewById(R.id.stopwatch);
        tv.setTypeface(tf);
        tv = (TextView) findViewById(R.id.gameOvertxt);
        tv.setTypeface(tf);
        Button bv = (Button) findViewById(R.id.start_stop_btn);
        
        bv.setTypeface(tf);
        bv = (Button) findViewById(R.id.newGameBtn);
        bv.setTypeface(tf);
        bv = (Button) findViewById(R.id.rulesBtn);
        bv.setTypeface(tf);
        bv = (Button) findViewById(R.id.leaderboard_button);
        bv.setTypeface(tf);
		
	}

	private void _ShowAds(Welcome welcome) {
        // Create an ad.
        adView = new AdView(welcome);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId(AD_UNIT_ID);

        // Add the AdView to the view hierarchy. The view will have no size
        // until the ad is loaded.
        LinearLayout layout = (LinearLayout) findViewById(R.id.ads_layout);
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


	protected void UpdateScores() {
		// TODO Auto-generated method stub
		
	}

	protected void SetScreenValues(boolean newGame) {
		try {
    	if(gamePlay.IsGameOver){
    		
    		//((TextView)findViewById(R.id.stopwatch)).setTextSize(55);
    		//((TextView)findViewById(R.id.stopwatch)).setText("Game Over");`
    		
    		((Button)findViewById(R.id.start_stop_btn)).setVisibility(-1);
    		((TextView)findViewById(R.id.gameOvertxt)).setVisibility(1);
    		
    		Games.Leaderboards.submitScore(getApiClient(),  ApplicationConstants.GOOGLE_PLAY_LEADERBOARD_ID , GamePlayFactory.GetGamePlayBusiness(gamePlay.GameType).GetHighScore(this));
    		hHandler.sendEmptyMessage(0);

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
    	try {
    	IGamePlayBusiness gamePlayBusiness;
       	gamePlayBusiness = GamePlayFactory.GetGamePlayBusiness(gamePlay.GameType);
       	int timer_slowdown_factor = gamePlayBusiness.GetTimerSlowDownFactor(getBaseContext());
    	gamePlay.StopWatchEntity.TimeInMillies = SystemClock.uptimeMillis() - gamePlay.StopWatchEntity.StartTime;
    	gamePlay.StopWatchEntity.FinalTime = gamePlay.StopWatchEntity.TimeSwap + gamePlay.StopWatchEntity.TimeInMillies;
    	
		gamePlay.StopWatchEntity.FinalTime = gamePlay.StopWatchEntity.FinalTime /timer_slowdown_factor;
		
    	int seconds = (int) (gamePlay.StopWatchEntity.FinalTime / 1000);
    	int minutes = seconds / 60;
    	seconds = seconds % 60;
    			
    	int milliseconds = (int) (gamePlay.StopWatchEntity.FinalTime % 1000)/10;
    	
    	((TextView)findViewById(R.id.stopwatch)).setText("" + String.format("%02d", minutes) + ":"
    	    	+ String.format("%02d", seconds) + ":"
    	    	+ String.format("%02d", milliseconds));
    	if(gamePlay.StopWatchEntity.TimeInMillies >= 20000){
    		gamePlay.IsGameOver = true;
    		gamePlay.IsGameStarted =false;
    		SetScreenValues(false);
    	}
    	else{
    	gamePlay.StopWatchEntity.MyHandler.postDelayed(this, 0);
    	}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	}

		
    	};
    	
    	private Handler hHandler = new Handler()
    	  {
    	    @Override
    	    public void handleMessage(Message msg)
    	    {
    	        
    	            try
    	            {
    	                Thread.sleep(ApplicationConstants.SecondsToWaitForLeaderBoard*1000);
    	                ((TextView)findViewById(R.id.gameOvertxt)).setVisibility(-1);
        	            ((Button)findViewById(R.id.leaderboard_button)).setVisibility(1);
    	                
    	            } 
    	            catch (InterruptedException e)
    	            {
    	                // TODO Auto-generated catch block
    	                e.printStackTrace();
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
    public void onStart(){
        super.onStart();
        // Connect with Google Api
       //mGoogleClient.connect();
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
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.leaderboards:
            	startActivityForResult(Games.Leaderboards.getLeaderboardIntent(getApiClient(), ApplicationConstants.GOOGLE_PLAY_LEADERBOARD_ID), 1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

	@Override
	public void onSignInFailed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSignInSucceeded() {
		// TODO Auto-generated method stub
		
	}

    
}
