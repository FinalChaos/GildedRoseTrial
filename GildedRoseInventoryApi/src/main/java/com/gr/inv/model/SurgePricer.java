package com.gr.inv.model;

import java.util.Date;

/**
 * Does not compound
 * @author Tomas
 *
 */
public class SurgePricer {

	private final static float ONE_HUNDERD_PERCENT = 100.0F;
	private final long previousViewTimes[];
	private final long surgeTriggerWindowMilliseconds;
	private final int numberOfPreviousViews;
	private int oldestViewPointer;
	private int surgePriceMultiplierPercent;
	private final int surgePriceIncrementPercent;
	
	/**
	 * This constant depends getTime on java.util.date returning the number of milliseconds 
	 * since January 1, 1970, 00:00:00 GMT.
	 * 
	 * The class can only be used safely when working with systems where new java.util.Date are  more than surgeTriggerWindowMilliseconds after
	 * January 1, 1970, 00:00:00 GMT. This because a time before that date is used to initialize the record of the previous view times.
	 * This should be sufficient for most business cases. In other words if your system clock is not back dated to January 1970 the class
	 * will work as expected.
	 */
	private static final long FAR_BACK_IN_PAST = -1;
	
	
	public SurgePricer(long surgeTriggerWindowMilliseconds, int viewsPerWindow, 
			int surgePriceIncrementPercent) {
		super();
		this.surgeTriggerWindowMilliseconds = surgeTriggerWindowMilliseconds;
		this.numberOfPreviousViews = viewsPerWindow - 1;
		this.surgePriceMultiplierPercent = 100;
		this.surgePriceIncrementPercent = surgePriceIncrementPercent;
		this.previousViewTimes = new long[viewsPerWindow];
		intializePreviousViews();
	}

	private void incrementOldestViewPointer(){
		this.oldestViewPointer = ((this.oldestViewPointer + 1) % this.numberOfPreviousViews);
	}
	private void intializePreviousViews(){		
		for (int i = 0; i < previousViewTimes.length; i++) {
			this.previousViewTimes[i] = FAR_BACK_IN_PAST;
		}
		this.oldestViewPointer = 0;
	}
	
	/**
	 * Make this protected so can override in test class
	 * @return
	 */
	protected long getTimeOfViewInMilliseconds(){
		return (new Date()).getTime();
	}
	
	private boolean inWindow(long viewTime){
		return viewTime <= (this.previousViewTimes[this.oldestViewPointer] + this.surgeTriggerWindowMilliseconds);
	}
	
	private void incrementSurgePriceMultiplier(){
		this.surgePriceMultiplierPercent = this.surgePriceMultiplierPercent + this.surgePriceIncrementPercent;
	}
	/***
	 * This method has side effects!
	 * Calling it may change the current surge price multiplier it will also change the record of previous view times
	 * @return the surge price as an percentage integer e.g. 100% (no price increase) this method returns 100.
	 * For a 10% price increase this method returns 110.
	 * <p> Use  {@link #applySurgeMultiplier} for applying this to a price. It encapsulates rounding and the percentage
	 */
	public final synchronized int calculateSurgePriceMultiplierPercent(){
		long viewTime = getTimeOfViewInMilliseconds();
		if (inWindow(viewTime)) {
			incrementSurgePriceMultiplier();
			// once we increment the price multipier we "throw away" the old views
			// it will take numberOfPreviousViews + 1 in the surge window to trigger another increment
			intializePreviousViews();
		} else {
			this.previousViewTimes[oldestViewPointer] = viewTime;
			incrementOldestViewPointer();
		}
		
		return this.surgePriceMultiplierPercent;
	}
	
	/***
	 * Convenience method that encapsulates the rounding needed to apply the surgePriceMultiplierPercent to an
	 * integer price. You need to divide by 100 not simply multiply the two integers.
	 * If you need to apply the surge multiplier to any integer price this method is recommended.
	 * @param price > 0
	 * @param surgeMultiplier >= 100
	 * @return rounded price as integer after surge multiplier applied
	 */
	public static  int applySurgeMultiplier(int price, int surgeMultiplier){
		return (Math.round( ((price * surgeMultiplier) / ONE_HUNDERD_PERCENT )));
	}
	
}
