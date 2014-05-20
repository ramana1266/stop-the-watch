/**
 * 
 */
package com.rams.stopthewatch.BusinessImpl;
import java.util.ArrayList;
import java.util.Collection;

import com.rams.stopthewatch.Business.*;
import com.rams.stopthewatch.enumerations.ApplicationConstants;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;

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
		
		if(ApplicationConstants.SpecialNumbers.contains(lastTwo)){
			retVal = ApplicationConstants.SPECIAL_NUMBER_POINTS * ApplicationConstants.OFFSET_MULTIPLIER;
		}
		else if(lastTwo % 25 ==0){
			retVal = (lastTwo / 25)*ApplicationConstants.OFFSET_MULTIPLIER;
		}
		else{
			
			ArrayList<Integer> specialNumbers = new ArrayList<Integer>();
			specialNumbers.addAll(ApplicationConstants.SpecialNumbers);
			specialNumbers.addAll(ApplicationConstants.MilestoneNumbers);
			
			ArrayList<Integer> positiveOffsets = new ArrayList<Integer>();
			for (Integer specialNum : specialNumbers) {
				positiveOffsets.add((Math.abs(lastTwo-specialNum))); 
			}
			
			int minOffset =50;
			for (Integer offset : positiveOffsets) {
				if(offset<minOffset)
					minOffset=offset;
			}
			
			retVal = 0 - minOffset;
			retVal = retVal/ApplicationConstants.OFFSET_DIVIDER;
		}
		return retVal;
		
	}
	


	

}
