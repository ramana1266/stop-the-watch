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

	@Override
	public boolean IsHintModeEnabled(Context context) {
		SharedPreferences prefs = context.getSharedPreferences("C20GamePlayKeys", Context.MODE_PRIVATE);
		boolean hintMode = prefs.getBoolean("HintMode",true);
		return hintMode;
	}

	@Override
	public void UpdateHintDisplayCount(Context context) {
		SharedPreferences prefs = context.getSharedPreferences("C20GamePlayKeys", Context.MODE_PRIVATE);
		int hintCount = prefs.getInt("HintDisplayCount",0);
		Editor editor = prefs.edit();
		editor.putInt("HintDisplayCount", ++hintCount);
		editor.commit();
		
		
	}

	@Override
	public void UpdateHintMode(Context context) {
		SharedPreferences prefs = context.getSharedPreferences("C20GamePlayKeys", Context.MODE_PRIVATE);
		int hintCount = GetHintDisplayCount(context);
		if(hintCount>=ApplicationConstants.HINTS_DISPLAY_TIMES){
		Editor editor = prefs.edit();
		editor.putBoolean("HintMode", false);
		editor.commit();
		}
	
		
	}

	@Override
	public int GetHintDisplayCount(Context context) {
		
		SharedPreferences prefs = context.getSharedPreferences("C20GamePlayKeys", Context.MODE_PRIVATE);
		int hintCount = prefs.getInt("HintDisplayCount",0);
		return hintCount;
	}

	@Override
	public int GetTimerSlowDownFactor(Context context) {
		SharedPreferences prefs = context.getSharedPreferences("C20GamePlayKeys", Context.MODE_PRIVATE);
		int slowdown_factor = prefs.getInt("TimerSlowDownFactor", 10);
		return slowdown_factor;
	}

	@Override
	public void UpdateTimerSlowDownFactor(Context context) {
		if(GetTimerSlowDownFactor(context)!=2){
		int slowdown_factor = 2;
		SharedPreferences prefs = context.getSharedPreferences("C20GamePlayKeys", Context.MODE_PRIVATE);
		Editor editor = prefs.edit();
		int stop_count = GetGameStopCount(context);
		if(stop_count<ApplicationConstants.LEVEL_ENDS_AFTER_STOPS){
			slowdown_factor = ApplicationConstants.LEVEL_ONE_SLOWDOWN_FACTOR;
		}
		else if(stop_count<ApplicationConstants.LEVEL_ENDS_AFTER_STOPS*2){
			slowdown_factor = ApplicationConstants.LEVEL_TWO_SLOWDOWN_FACTOR;
		}
		else if(stop_count<ApplicationConstants.LEVEL_ENDS_AFTER_STOPS*3){
			slowdown_factor = ApplicationConstants.LEVEL_THREE_SLOWDOWN_FACTOR;
		}
		else if(stop_count<ApplicationConstants.LEVEL_ENDS_AFTER_STOPS*4){
			slowdown_factor = ApplicationConstants.LEVEL_FOUR_SLOWDOWN_FACTOR;
		}
		else {
			slowdown_factor = ApplicationConstants.LEVEL_FIVE_SLOWDOWN_FACTOR;
		}
			
		editor.putInt("TimerSlowDownFactor", slowdown_factor);
		editor.commit();
		
	}
	}

	private int GetGameStopCount(Context context) {
		SharedPreferences prefs = context.getSharedPreferences("C20GamePlayKeys", Context.MODE_PRIVATE);
		int stopCount = prefs.getInt("GameStopCount",0);
		return stopCount;
	}
	
	@Override
	public void UpdateGameStopCount(Context context) {
		SharedPreferences prefs = context.getSharedPreferences("C20GamePlayKeys", Context.MODE_PRIVATE);
		int gameStopCount = GetGameStopCount(context);
		Editor editor = prefs.edit();
		editor.putInt("GameStopCount", ++gameStopCount);
		editor.commit();
				
	}
	@Override
	public void ResetGameStopCount(Context context) {
		SharedPreferences prefs = context.getSharedPreferences("C20GamePlayKeys", Context.MODE_PRIVATE);
		Editor editor = prefs.edit();
		editor.putInt("GameStopCount", 0);
		editor.commit();
				
	}
	@Override
	public void ResetTimerSlowDownFactor(Context context) {
		SharedPreferences prefs = context.getSharedPreferences("C20GamePlayKeys", Context.MODE_PRIVATE);
		Editor editor = prefs.edit();
		editor.putInt("TimerSlowDownFactor", 10);
		editor.commit();
				
	}

}
