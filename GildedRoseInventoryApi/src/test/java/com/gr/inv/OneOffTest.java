package com.gr.inv;

import org.junit.Test;

public class OneOffTest {

	@Test
	public void test() {
		System.out.println( 1 % 1);
		final float ONE_HUNDERD_PERCENT = 100.0F;
		System.out.println(Math.round( ((1 * 149) / ONE_HUNDERD_PERCENT )));
	}

}
