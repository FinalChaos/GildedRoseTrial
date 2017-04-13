package com.gr.inv.model;


/***
 * Test class designed to test surge pricer.
 * It takes an array of long values which we can use to simulate view times - that would have been
 * taken from system clock if we used the actual class.
 * @author Tomas
 *
 */
public class SurgePricerDeterministic extends SurgePricer {

	private int simulatedViewTimesPointer;
	private long[] simulatedViewTimes;
	
	public SurgePricerDeterministic(long surgeTriggerWindowMilliseconds, int numberOfPreviousViews,
			int surgePriceIncrementPercent, long... simulatedViewTimes) {
		super(surgeTriggerWindowMilliseconds, numberOfPreviousViews, surgePriceIncrementPercent);
		this.simulatedViewTimes = simulatedViewTimes;
		this.simulatedViewTimesPointer = -1;
	}

	@Override
	protected long getTimeOfViewInMilliseconds() {
		return this.simulatedViewTimes[++this.simulatedViewTimesPointer];
	}
	
	

}
