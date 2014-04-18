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

}
