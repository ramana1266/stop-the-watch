package com.rams.stopthewatch.BusinessImpl;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Handler;

import com.rams.stopthewatch.Entity.GameEntity;
import com.rams.stopthewatch.Entity.StopWatchEntity;
import com.rams.stopthewatch.enumerations.ApplicationConstants;
import com.rams.stopthewatch.enumerations.GamePlayType;

public class ChancesToZeroGamePlayBusiness extends BaseGamePlayBusiness {

	private Handler myHandler = new Handler();
	@Override
	public GameEntity StartGame(){
		GameEntity newGameEntity = new GameEntity(GamePlayType.ChancesToZero);
		newGameEntity.ClockTime = ApplicationConstants.START_CLOCK_TIME;
		newGameEntity.Score = ApplicationConstants.START_SCORE_CTZ;
		newGameEntity.Tally = ApplicationConstants.START_TALLY;
		newGameEntity.IsGameStarted=false;
		newGameEntity.StopWatchEntity = new StopWatchEntity();
		newGameEntity.IsGameOver = false;
		
		return newGameEntity;
		
		
	}
	
	@Override
	public int GetHighScore(Context context) {
		
		SharedPreferences prefs = context.getSharedPreferences("C20GamePlayKeys", Context.MODE_PRIVATE);
		int highScore = prefs.getInt("HighScore", 0);
		return highScore;
	}

	@Override
	public GameEntity UpdateScores(GameEntity gameEntity) {

		// Instead of doing this, we can store the last 2 in a property directly. 
		// But could be too expensive because we continuously update the string 
		int millis = Integer.valueOf(gameEntity.ClockTime.substring(gameEntity.ClockTime.length() - 2));
		
		int offSet = BaseGamePlayBusiness.CalculateOffset(millis);
		gameEntity.Tally += offSet;
		if(gameEntity.Tally>0)
			gameEntity.Score+=1;
		else{
			gameEntity.Tally = 0;
			gameEntity.IsGameOver = true; 
		}
		return gameEntity;
	}

	@Override
	public int GetTally() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void SetHighScore(Context context, int highScore) {
		
		SharedPreferences prefs = context.getSharedPreferences("C20GamePlayKeys", Context.MODE_PRIVATE);
		Editor editor = prefs.edit();
		editor.putInt("HighScore", highScore);
		editor.commit();
		
	}

}
