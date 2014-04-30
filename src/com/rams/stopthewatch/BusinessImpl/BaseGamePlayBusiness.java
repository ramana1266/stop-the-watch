/**
 * 
 */
package com.rams.stopthewatch.BusinessImpl;
import com.rams.stopthewatch.Business.*;
import com.rams.stopthewatch.enumerations.ApplicationConstants;

/**
 * @author RMalladi
 *
 */
public  abstract class BaseGamePlayBusiness implements IGamePlayBusiness{

	
	@Override
	public void PauseGame() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void StopGame() {
		// TODO Auto-generated method stub
		
	}
	
	public static int CalculateOffset(int lastTwo){
		int retVal = 0; 
		if(lastTwo == 0)
			lastTwo =100;
		if(lastTwo % 25 ==0){
			retVal = (lastTwo / 25)*ApplicationConstants.OFFSET_MULTIPLIER;
		}
		else{
			
		retVal = 0 - Math.min( Math.min( Math.min( Math.min(Math.abs(0-lastTwo), Math.abs(25-lastTwo)), Math.abs(50-lastTwo)),
				Math.abs(75-lastTwo)), Math.abs(100-lastTwo));
		retVal = retVal/ApplicationConstants.OFFSET_DIVIDER;
		}
		return retVal;
		
	}


	

}
