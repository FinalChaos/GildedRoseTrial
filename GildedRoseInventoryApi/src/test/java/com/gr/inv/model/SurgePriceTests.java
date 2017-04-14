package com.gr.inv.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class SurgePriceTests {

	@Test
	public void testSurge() {
		
		{
			
			/* 
			 * The long list of simulated views makes sure the following scenarios verified.
			 * a)The internal array (in this case size 3) gets wrapped.
			 * b) Surge happens on the nth view. In this case the fourth. See long 1907
			 * c) Surge happens on some view after the nth view. see long 1903
			 * d)  Reset of class after surge happens. All test cases immediately after the multiplier goes up
			 * e) The class maintains the surge price state for the duration of the application. So give it a good workout by 
			 * supplying a bunch of numbers.
			 * 
			 */
			SurgePricer pricer = new SurgePricerDeterministic(
					100, // surgeTriggerWindowMilliseconds
					4, // number of views in window to trigger surge
					10, //surgePriceIncrementPercent 
					1000, 1050, 1075, 1200, 1225, 1250, 1300, 1301, 1302, 1303, 1304, 1400, 1500, 1600, 1700, 1800, 1900, 1901, 1902, 1903, 1904, 1905, 1906, 1907//simulatedViewTimes
					);
			
			assertEquals(100,pricer.calculateSurgePriceMultiplierPercent()); // 1000
			assertEquals(100,pricer.calculateSurgePriceMultiplierPercent()); // 1050
			assertEquals(100,pricer.calculateSurgePriceMultiplierPercent()); // 1075
			assertEquals(100,pricer.calculateSurgePriceMultiplierPercent()); // 1200
			assertEquals(100,pricer.calculateSurgePriceMultiplierPercent()); // 1225
			assertEquals(100,pricer.calculateSurgePriceMultiplierPercent()); // 1250
			assertEquals(110,pricer.calculateSurgePriceMultiplierPercent()); // 1300
			assertEquals(110,pricer.calculateSurgePriceMultiplierPercent()); // 1301
			assertEquals(110,pricer.calculateSurgePriceMultiplierPercent()); // 1302
			assertEquals(110,pricer.calculateSurgePriceMultiplierPercent()); // 1303
			assertEquals(120,pricer.calculateSurgePriceMultiplierPercent()); // 1304
			
			assertEquals(120,pricer.calculateSurgePriceMultiplierPercent()); // 1400
			assertEquals(120,pricer.calculateSurgePriceMultiplierPercent()); // 1500
			assertEquals(120,pricer.calculateSurgePriceMultiplierPercent()); // 1600
			assertEquals(120,pricer.calculateSurgePriceMultiplierPercent()); // 1700
			assertEquals(120,pricer.calculateSurgePriceMultiplierPercent()); // 1800
			assertEquals(120,pricer.calculateSurgePriceMultiplierPercent()); // 1900
			assertEquals(120,pricer.calculateSurgePriceMultiplierPercent()); // 1901
			assertEquals(120,pricer.calculateSurgePriceMultiplierPercent()); // 1902
			assertEquals(130,pricer.calculateSurgePriceMultiplierPercent()); // 1903
			
			assertEquals(130,pricer.calculateSurgePriceMultiplierPercent()); // 1904
			assertEquals(130,pricer.calculateSurgePriceMultiplierPercent()); // 1905
			assertEquals(130,pricer.calculateSurgePriceMultiplierPercent()); // 1906
			assertEquals(140,pricer.calculateSurgePriceMultiplierPercent()); // 1907
		}
		
		{
			SurgePricer pricer = new SurgePricerDeterministic(
					100, // surgeTriggerWindowMilliseconds
					2, //numberOfPreviousViews
					10, //surgePriceIncrementPercent 
					1000, 1101, 1102//simulatedViewTimes
					);
			
			assertEquals(100,pricer.calculateSurgePriceMultiplierPercent()); // 1000
			assertEquals(100,pricer.calculateSurgePriceMultiplierPercent()); // 1101
			assertEquals(110,pricer.calculateSurgePriceMultiplierPercent()); // 1102
		}

		{
			SurgePricer pricer = new SurgePricerDeterministic(
					100, // surgeTriggerWindowMilliseconds
					4, //numberOfPreviousViews
					10, //surgePriceIncrementPercent 
					1000, 1000, 1000, 1000//simulatedViewTimes
					);
			
			assertEquals(100,pricer.calculateSurgePriceMultiplierPercent()); // 1000
			assertEquals(100,pricer.calculateSurgePriceMultiplierPercent()); // 1000
			assertEquals(100,pricer.calculateSurgePriceMultiplierPercent()); // 1000
			assertEquals(110,pricer.calculateSurgePriceMultiplierPercent()); // 1000
		}

		
	}
	
	@Test
	public void testApplyingSurgePricer() {
		assertEquals(2,SurgePricer.applySurgeMultiplier(1, 150));
		assertEquals(1,SurgePricer.applySurgeMultiplier(1, 149));
	}
	
	

}
