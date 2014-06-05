package com.rams.stopthewatch.BusinessImpl;

import android.content.Context;

import com.rams.stopthewatch.Entity.GameEntity;
import com.rams.stopthewatch.enumerations.GamePlayType;

public class MaxTallyGamePlayBusiness extends BaseGamePlayBusiness{

	@Override
	public int GetTally() {
		// TODO Auto-generated method stub
		return 0;
	}

	

	@Override
	public GameEntity StartGame() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameEntity UpdateScores(GameEntity gamePlay) {
		// TODO Auto-generated method stub
		return new GameEntity(GamePlayType.MaxTally);
		
	}



	@Override
	public int GetHighScore(Context context) {
		// TODO Auto-generated method stub
		return 0;
	}



	@Override
	public void SetHighScore(Context context, int highScore) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public boolean IsHintModeEnabled(Context context) {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public void UpdateHintDisplayCount(Context baseContext) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void UpdateHintMode(Context baseContext) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public int GetHintDisplayCount(Context context) {
		// TODO Auto-generated method stub
		return 0;
	}



	@Override
	public int GetTimerSlowDownFactor(Context baseContext) {
		// TODO Auto-generated method stub
		return 0;
	}



	@Override
	public void UpdateTimerSlowDownFactor(Context baseContext) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void UpdateGameStopCount(Context context) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void ResetGameStopCount(Context context) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void ResetTimerSlowDownFactor(Context context) {
		// TODO Auto-generated method stub
		
	}

}
