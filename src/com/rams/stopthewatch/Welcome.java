package com.rams.stopthewatch;

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
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Welcome extends Activity {
	
	public static GameEntity gamePlay; 
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        
        Button newGameButton = (Button) findViewById(R.id.newGameBtn);
        newGameButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// This should change later to choose what game user wants to play
				// Right now, the only supported gameplay is ChancesToZeroGamePlay.
				GamePlayType gamePlayType = GamePlayType.ChancesToZero;				
				
				try {
					
					gamePlay = GamePlayFactory.GetGamePlayBusiness(gamePlayType).StartGame();
					SetScreenValues(true);
					((TextView)findViewById(R.id.stopwatch)).setTextSize(75);
					((TextView)findViewById(R.id.stopwatch)).setText(gamePlay.ClockTime);
					((Button)findViewById(R.id.start_stop_btn)).setVisibility(1);
					
				} catch (Exception e) {
					Log.wtf("START_FAIL", "failed to start the game");
					e.printStackTrace();
				}
			}

			
		});
        
        
        Button starStopButton = (Button) findViewById(R.id.start_stop_btn);
        
        starStopButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				
				if(gamePlay.IsGameStarted){
					
					try {
					gamePlay.StopWatchEntity.TimeSwap += gamePlay.StopWatchEntity.TimeInMillies;
					gamePlay.StopWatchEntity.MyHandler.removeCallbacks(UpdateTimerMethod);
					gamePlay.ClockTime = ((TextView)findViewById(R.id.stopwatch)).getText().toString(); 
					gamePlay = GamePlayFactory.GetGamePlayBusiness(gamePlay.GameType).UpdateScores(gamePlay);
					SetScreenValues(false);
					gamePlay.IsGameStarted = false;
					
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else
				{
					gamePlay.StopWatchEntity.StartTime = SystemClock.uptimeMillis();
					gamePlay.StopWatchEntity.MyHandler.postDelayed(UpdateTimerMethod, 0); 
					SetScreenValues(false);
					gamePlay.IsGameStarted = true;
				}
			}
		});
    }

    protected void UpdateScores() {
		// TODO Auto-generated method stub
		
	}

	protected void SetScreenValues(boolean newGame) {
    	if(gamePlay.IsGameOver){
    		
    		//((TextView)findViewById(R.id.stopwatch)).setTextSize(55);
    		//((TextView)findViewById(R.id.stopwatch)).setText("Game Over");
    		
    		((Button)findViewById(R.id.start_stop_btn)).setVisibility(-1);
    	}
    	
		if(gamePlay.IsGameStarted || newGame){
			((Button)findViewById(R.id.start_stop_btn)).setText("Start");
		}
		else{
    	((Button)findViewById(R.id.start_stop_btn)).setText("Stop");
    	}
		((TextView)findViewById(R.id.score)).setText("Score :" + gamePlay.Score);
		((TextView)findViewById(R.id.best)).setText("Best :" + GetBestScore(gamePlay.GameType));
		((TextView)findViewById(R.id.tallyValue)).setText("Tally :" + gamePlay.Tally);
		
	}

	private String GetBestScore(GamePlayType gameType) {
		// TODO Auto-generated method stub
		return ApplicationConstants.BEST_SCORE;
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
    	gamePlay.StopWatchEntity.MyHandler.postDelayed(this, 0);
    	}

		
    	};
    	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.welcome, menu);
        return true;
    }
    
}
