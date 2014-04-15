/**
 * 
 */
package com.rams.stopthewatch.Business;

import com.rams.stopthewatch.Entity.GameEntity;
import com.rams.stopthewatch.enumerations.GamePlayType;

/**
 * @author RMalladi
 * Interface for Game play
 * 
 *  current gameplay types are
 *  1. chances to zero game play
 *	2. tally game play
 */
public interface IGamePlayBusiness {
	
	//gets the Tally
	public int GetTally();
	
	public GameEntity StartGame();
	
	public void PauseGame();
	
	public void StopGame();
	
	public int GetScore();
	
	public GameEntity UpdateScores(GameEntity gamePlay);

}
