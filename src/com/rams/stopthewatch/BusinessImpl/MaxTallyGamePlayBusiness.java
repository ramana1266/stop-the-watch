package com.rams.stopthewatch.BusinessImpl;

import com.rams.stopthewatch.Entity.GameEntity;
import com.rams.stopthewatch.enumerations.GamePlayType;

public class MaxTallyGamePlayBusiness extends BaseGamePlayBusiness{

	@Override
	public int GetTally() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int GetScore() {
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

}
