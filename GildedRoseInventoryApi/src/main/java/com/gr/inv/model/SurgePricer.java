package com.gr.inv.model;

import java.util.Date;

/**
 * This class implements the surge pricing.<p>
 * If there are a certain number of views within a given time window the price of all items goes up by a certain amount.
 * The number of views, the duration of the time window and the amount the price of items increases are all set in the constructor.
 * Calling  {@link #calculateSurgePriceMultiplierPercent} determines if the price should surge or not. It records the view time
 * using java.util.Date as the source of a long that records the surge time. It also increments the surge price multiplier. This is
 * an integer that contains the percentage impact of the surge pricing. Initially it is 100 for 100% which would mean no surge pricing.
 * The method is to be called every time the list of items is retrieved. If the surge increase is 10% than after the first surge the
 * multiplier would be 110. Every price is 10% higher.<p>
 * The  {@link #applySurgeMultiplier} for applying this to a price. It encapsulates rounding and the percentage.<p>
 * Usage: Call exactly calculateSurgePriceMultiplierPercent once before each action that counts towards surging. At the time of 
 * the only known action was writing views of the item list.<p>
 * Store the surge multiplier. Afterwards apply the multiplier to each price. Use the provided routine for calculating. Note there is no 
 * compounding. Two 10% surges lead to a multiplier of 120 not 121. Also once a surge occurs the class resets itself and a complete count of 
 * views must happen before the next surge. In other words after 10 views in the window you get a surge you need 10 more views in the window
 * to get the next surge. See class InventoryDaoImpl for example <p>
 * The method is synchronized so if two views happen concurrently internal state is protected.
 * <p> Implementation: Use an array of longs in a round robin style to record view times. Each new view replaces the oldest previous view. The array is always
 * initialized to longs corresponding to a very old time so even when first initialized (or reset after a surge) the array acts as if
 * populated with long ago views. 
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
