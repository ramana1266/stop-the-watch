package com.rams.stopthewatch.enumerations;

import java.util.ArrayList;

public class ApplicationConstants {
	
	private ApplicationConstants(){}
	
	public static final String START_CLOCK_TIME = "00:00:00";
	
	public static final int START_SCORE_CTZ = 0;

	public static final int START_TALLY = 20;

	public static final String BEST_SCORE = "0";
	
	//divisor for negative scores
	public static final int OFFSET_DIVIDER =1;
		
	//multiplier for positive scores
	public static final int OFFSET_MULTIPLIER =1;

	public static final String FONT_LOCATION = "fonts/euphoric.ttf";

	public static final int HINTS_DISPLAY_TIMES = 8;

	public static final String GOOD_JOB_STRING = "You are doing great. Keep going!!";

	public static final String BAD_JOB_STRING = " Try to Stop at 25, 50, 75 or 00 to get + points";
	
	public static String GetGoodJobString(int hintCount){
		String retVal;
		switch(hintCount){
		case 1:
		case 2:
			retVal = "Amazing Start!! Keep going. Keep aiming for 25, 50, 75, 00 to get + points";
			break;
		case 3:
		case 4:
			retVal = "Good Job!! keep going. Aim for 25, 50, 75, 00";
			break;
		case 5:
		case 6:
			retVal = "Looks like you are a quick learner";
			break;
		case 7:
		case 8:
			retVal= "You got it all under control. Keep up the good work.";
			break;
		default:
			retVal = GOOD_JOB_STRING;
			break;
		}
		return retVal;
	}
	
	public static String GetBadJobString(int hintCount){
		String retVal;
		switch(hintCount){
		case 1:
		case 2:
			retVal = "Keep trying. Aim to Stop at 25, 50, 75, 00 to get + points";
			break;
		case 3:
		case 4:
			retVal = "Speed up your reflexes. Get + points by Stopping at 25, 50, 75, 00";
			break;
		case 5:
		case 6:
			retVal = "Stop at 25, 50, 75, 00 to get + points";
			break;
		case 7:
		case 8:
			retVal= "Keep trying. Improve your reflexes a little and you'll be golden!!";
		default:
			retVal = BAD_JOB_STRING;
			break;
		}
		return retVal;
	}
	
	public static final ArrayList<Integer> MilestoneNumbers = new ArrayList<Integer>() {{
	    add(25);
	    add(50);
	    add(75);
	    add(100);
	}} ;
	
	public static final ArrayList<Integer> SpecialNumbers = new ArrayList<Integer>() {{
	    add(00);
	    add(11);
	    add(22);
	    add(33);
	    add(44);
	    add(55);
	    add(66);
	    add(77);
	    add(88);
	    add(99);
	    
	}} ;

	public static final int SPECIAL_NUMBER_POINTS = 5;
	
}
