package com.rams.stopthewatch;

import com.rams.stopthewatch.Business.IGamePlayBusiness;
import com.rams.stopthewatch.Entity.GameEntity;
import com.rams.stopthewatch.Entity.StopWatchEntity;
import com.rams.stopthewatch.Factory.GamePlayFactory;
import com.rams.stopthewatch.enumerations.ApplicationConstants;
import com.rams.stopthewatch.enumerations.GamePlayType;

import android.R.bool;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog.Builder;

public class Welcome extends Activity {
	public Toast toast;
	public boolean dontStartNewGame = false;
	public static GameEntity gamePlay; 
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        
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
    
}
