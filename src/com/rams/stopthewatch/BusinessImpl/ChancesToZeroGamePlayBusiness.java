package com.rams.stopthewatch.BusinessImpl;

import android.os.Handler;
import android.os.SystemClock;
import android.widget.Button;
import android.widget.TextView;
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
import com.rams.stopthewatch.R;
import com.rams.stopthewatch.Entity.GameEntity;
import com.rams.stopthewatch.Entity.StopWatchEntity;
import com.rams.stopthewatch.enumerations.ApplicationConstants;
import com.rams.stopthewatch.enumerations.GamePlayType;

public class ChancesToZeroGamePlayBusiness extends BaseGamePlayBusiness {

	private Handler myHandler = new Handler();
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
	public int GetScore() {
		// TODO Auto-generated method stub
		return 0;
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

}
