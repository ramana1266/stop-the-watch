package com.rams.stopthewatch.Factory;

import java.security.NoSuchProviderException;
import com.rams.stopthewatch.Business.IGamePlayBusiness;
import com.rams.stopthewatch.BusinessImpl.ChancesToZeroGamePlayBusiness;
import com.rams.stopthewatch.BusinessImpl.MaxTallyGamePlayBusiness;
import com.rams.stopthewatch.enumerations.GamePlayType;

public class GamePlayFactory {
	
	public static IGamePlayBusiness GetGamePlayBusiness(GamePlayType gamePlayType) throws Exception{
		
		IGamePlayBusiness retVal;
		switch (gamePlayType) {
		case ChancesToZero:
			retVal = new ChancesToZeroGamePlayBusiness();
			break;
		case MaxTally:
			retVal = new MaxTallyGamePlayBusiness();
		default:
			throw new NoSuchProviderException() ;
					
		}
		return retVal;
	}

}
