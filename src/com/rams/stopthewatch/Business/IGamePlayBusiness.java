/**
 * 
 */
package com.rams.stopthewatch.Business;

import android.content.Context;

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
	
	public int GetHighScore(Context context);
	
	public GameEntity UpdateScores(GameEntity gamePlay);
	
	public void SetHighScore(Context context, int highScore);

	public boolean IsHintModeEnabled(Context context);

	public void UpdateHintDisplayCount(Context baseContext);

	public void UpdateHintMode(Context baseContext);
	
	public int GetHintDisplayCount(Context context);

}
