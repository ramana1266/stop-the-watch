package com.rams.stopthewatch.Entity;

import android.os.Handler;

public class StopWatchEntity {
	
	public long StartTime;
	
	public Handler MyHandler;
	
	public long TimeInMillies;
	
	public long TimeSwap;
	
	public long FinalTime; 
	
	public StopWatchEntity(){
		StartTime = 0L;
		MyHandler = new Handler();
		TimeInMillies = 0L;
		TimeSwap = 0L;
		FinalTime = 0L; 
		
	}

}
