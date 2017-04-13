package com.gr.inv.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class SurgePriceTests {

	@Test
	public void test() {
		SurgePricer pricer = new SurgePricerDeterministic(
				100, // surgeTriggerWindowMilliseconds
				3, //numberOfPreviousViews
				10, //surgePriceIncrementPercent 
				1000, 1050, 1075, 1200, 1225, 1250, 1300, 1301, 1302, 1303, 1304, 1400, 1500, 1600, 1700, 1800, 1900, 1901, 1902, 1903//simulatedViewTimes
				);
		
		assertEquals(100,pricer.calculateAndRetainSurgePriceMultiplier()); // 1000
		assertEquals(100,pricer.calculateAndRetainSurgePriceMultiplier()); // 1050
		assertEquals(100,pricer.calculateAndRetainSurgePriceMultiplier()); // 1075
		assertEquals(100,pricer.calculateAndRetainSurgePriceMultiplier()); // 1200
		assertEquals(100,pricer.calculateAndRetainSurgePriceMultiplier()); // 1225
		assertEquals(100,pricer.calculateAndRetainSurgePriceMultiplier()); // 1250
		assertEquals(110,pricer.calculateAndRetainSurgePriceMultiplier()); // 1300
		assertEquals(110,pricer.calculateAndRetainSurgePriceMultiplier()); // 1301
		assertEquals(110,pricer.calculateAndRetainSurgePriceMultiplier()); // 1302
		assertEquals(110,pricer.calculateAndRetainSurgePriceMultiplier()); // 1303
		assertEquals(120,pricer.calculateAndRetainSurgePriceMultiplier()); // 1304
		
		assertEquals(120,pricer.calculateAndRetainSurgePriceMultiplier()); // 1400
		assertEquals(120,pricer.calculateAndRetainSurgePriceMultiplier()); // 1500
		assertEquals(120,pricer.calculateAndRetainSurgePriceMultiplier()); // 1600
		assertEquals(120,pricer.calculateAndRetainSurgePriceMultiplier()); // 1700
		assertEquals(120,pricer.calculateAndRetainSurgePriceMultiplier()); // 1800
		assertEquals(120,pricer.calculateAndRetainSurgePriceMultiplier()); // 1900
		assertEquals(120,pricer.calculateAndRetainSurgePriceMultiplier()); // 1901
		assertEquals(120,pricer.calculateAndRetainSurgePriceMultiplier()); // 1902
		assertEquals(130,pricer.calculateAndRetainSurgePriceMultiplier()); // 1903

		
	}
	
	

}
