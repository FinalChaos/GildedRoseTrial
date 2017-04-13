package com.gr.inv.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class SurgePriceTests {

	@Test
	public void testSurge() {
		
		{
			SurgePricer pricer = new SurgePricerDeterministic(
					100, // surgeTriggerWindowMilliseconds
					4, //numberOfPreviousViews
					10, //surgePriceIncrementPercent 
					1000, 1050, 1075, 1200, 1225, 1250, 1300, 1301, 1302, 1303, 1304, 1400, 1500, 1600, 1700, 1800, 1900, 1901, 1902, 1903//simulatedViewTimes
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
