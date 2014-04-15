package com.rams.stopthewatch.Entity;

import com.rams.stopthewatch.enumerations.GamePlayType;

public class GameEntity {

	// Properties start
	
	public int Score;
	
	public GamePlayType GameType; 
	
	public boolean IsGameStarted;
	
	public int Tally;
	
	public String ClockTime;
	
	public boolean IsGameOver;

	public StopWatchEntity StopWatchEntity;
	
	// Properties end
	
	//Constructor
	
	private GameEntity(){};
	
	public GameEntity(GamePlayType gamePlayType ){
		
		GameType = gamePlayType;
		
	}
	
}
