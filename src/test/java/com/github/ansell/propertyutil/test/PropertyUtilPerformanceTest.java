/**
 * 
 */
package com.github.ansell.propertyutil.test;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.github.ansell.propertyutil.PropertyUtil;

/**
 * Test for PropertyUtil class
 * 
 * @author Peter Ansell p_ansell@yahoo.com
 */
public class PropertyUtilPerformanceTest {

	private static final int LOOP_COUNT = 100000;

	private PropertyUtil propertyUtil;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public synchronized void setUp() throws Exception {
		this.propertyUtil = new PropertyUtil("com.github.ansell.propertyutil.test.propertyutiltestbundle");
		// verify that the default is to use caching
		Assert.assertTrue(PropertyUtil.DEFAULT_USE_CACHE);

		// make sure we are using our custom test bundle
		Assert.assertEquals("com.github.ansell.propertyutil.test.propertyutiltestbundle",
				this.propertyUtil.getPropertyBundleName());

		// clear property cache before and after tests
		this.propertyUtil.clearPropertyCache();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public synchronized void tearDown() throws Exception {
		// clear property cache before and after tests
		this.propertyUtil.clearPropertyCache();
		this.propertyUtil = null;
	}

	@Test
	public synchronized final void testPerformanceCachingExistingPropertiesNonThreadedNoDefault() {
		for (int i = 0; i < LOOP_COUNT; i++) {
			final int value = i % 100;

			final String propertyString = this.propertyUtil.get("test.performance.property" + value + "randomvalue",
					null);
			Assert.assertEquals(propertyString, "Test Performance Property " + value);
		}
	}

	@Test
	public synchronized final void testPerformanceCachingExistingPropertiesNonThreadedWithDefault() {
		for (int i = 0; i < LOOP_COUNT; i++) {
			final int value = i % 100;

			final String defaultValue = Integer.toHexString(3127 + value);

			final String propertyString = this.propertyUtil.get("test.performance.property" + value + "randomvalue",
					defaultValue);
			Assert.assertEquals(propertyString, "Test Performance Property " + value);
		}
	}

	@Test
	public synchronized final void testPerformanceCachingMissingPropertiesNonThreadedNoDefault() {
		for (int i = 0; i < LOOP_COUNT; i++) {
			final int value = i % 100;

			final String propertyString = this.propertyUtil.get("test.missing.property" + value + "randomvalue", null);
			Assert.assertNull(propertyString);
		}
	}

	@Test
	public synchronized final void testPerformanceCachingMissingPropertiesNonThreadedWithDefault() {
		for (int i = 0; i < LOOP_COUNT; i++) {
			final int value = i % 100;

			final String defaultValue = Integer.toHexString(3127 + value);

			final String propertyString = this.propertyUtil.get("test.missing.property" + value + "randomvalue",
					defaultValue);
			Assert.assertEquals(propertyString, Integer.toHexString(3127 + value));
		}
	}

	@Test
	public synchronized final void testPerformanceNonCachingExistingPropertiesNonThreadedNoDefault() {
		for (int i = 0; i < LOOP_COUNT; i++) {
			final int value = i % 100;

			final String propertyString = this.propertyUtil.get("test.performance.property" + value + "randomvalue",
					null);
			Assert.assertEquals(propertyString, "Test Performance Property " + value);
		}
	}

	@Test
	public synchronized final void testPerformanceNonCachingExistingPropertiesNonThreadedWithDefault() {
		for (int i = 0; i < LOOP_COUNT; i++) {
			final int value = i % 100;

			final String defaultValue = Integer.toHexString(3127 + value);

			final String propertyString = this.propertyUtil.get("test.performance.property" + value + "randomvalue",
					defaultValue);
			Assert.assertEquals(propertyString, "Test Performance Property " + value);
		}
	}

	@Test
	public synchronized final void testPerformanceNonCachingMissingPropertiesNonThreadedNoDefault() {
		for (int i = 0; i < LOOP_COUNT; i++) {
			final int value = i % 100;

			final String propertyString = this.propertyUtil.get("test.missing.property" + value + "randomvalue", null);
			Assert.assertNull(propertyString);
		}
	}

	@Test
	public synchronized final void testPerformanceNonCachingMissingPropertiesNonThreadedWithDefault() {
		for (int i = 0; i < LOOP_COUNT; i++) {
			final int value = i % 100;

			final String defaultValue = Integer.toHexString(3127 + value);

			final String propertyString = this.propertyUtil.get("test.missing.property" + value + "randomvalue",
					defaultValue);
			Assert.assertEquals(propertyString, Integer.toHexString(3127 + value));
		}
	}

}
