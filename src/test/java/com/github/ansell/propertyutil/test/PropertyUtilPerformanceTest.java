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
public class PropertyUtilPerformanceTest
{
    
    /**
     * @throws java.lang.Exception
     */
    @Before
    public synchronized void setUp() throws Exception
    {
        // verify that the default is to use caching
        Assert.assertTrue(PropertyUtil.DEFAULT_USE_CACHE);
        
        // make sure we are using our custom test bundle
        PropertyUtil.setPropertyBundleName("com.github.ansell.propertyutil.test.propertyutiltest");
        
        Assert.assertEquals("com.github.ansell.propertyutil.test.propertyutiltest",
                PropertyUtil.getPropertyBundleName());
        
        // clear property cache before and after tests
        PropertyUtil.clearPropertyCache();
    }
    
    /**
     * @throws java.lang.Exception
     */
    @After
    public synchronized void tearDown() throws Exception
    {
        // clear property cache before and after tests
        PropertyUtil.clearPropertyCache();
        
        PropertyUtil.setPropertyBundleName(PropertyUtil.DEFAULT_PROPERTIES_BUNDLE_NAME);
    }
    
    @Test
    public synchronized final void testPerformanceCachingExistingPropertiesNonThreadedNoDefault()
    {
        for(int i = 0; i < 1000000; i++)
        {
            final int value = i % 100;
            
            final String propertyString =
                    PropertyUtil.getSystemOrPropertyString("test.performance.property" + value + "randomvalue", null,
                            true);
            Assert.assertEquals(propertyString, "Test Performance Property " + value);
        }
    }
    
    @Test
    public synchronized final void testPerformanceCachingExistingPropertiesNonThreadedWithDefault()
    {
        for(int i = 0; i < 1000000; i++)
        {
            final int value = i % 100;
            
            final String defaultValue = Integer.toHexString(3127 + value);
            
            final String propertyString =
                    PropertyUtil.getSystemOrPropertyString("test.performance.property" + value + "randomvalue",
                            defaultValue, true);
            Assert.assertEquals(propertyString, "Test Performance Property " + value);
        }
    }
    
    @Test
    public synchronized final void testPerformanceCachingMissingPropertiesNonThreadedNoDefault()
    {
        for(int i = 0; i < 1000000; i++)
        {
            final int value = i % 100;
            
            final String propertyString =
                    PropertyUtil.getSystemOrPropertyString("test.missing.property" + value + "randomvalue", null, true);
            Assert.assertNull(propertyString);
        }
    }
    
    @Test
    public synchronized final void testPerformanceCachingMissingPropertiesNonThreadedWithDefault()
    {
        for(int i = 0; i < 1000000; i++)
        {
            final int value = i % 100;
            
            final String defaultValue = Integer.toHexString(3127 + value);
            
            final String propertyString =
                    PropertyUtil.getSystemOrPropertyString("test.missing.property" + value + "randomvalue",
                            defaultValue, true);
            Assert.assertEquals(propertyString, Integer.toHexString(3127 + value));
        }
    }
    
    @Test
    public synchronized final void testPerformanceNonCachingExistingPropertiesNonThreadedNoDefault()
    {
        for(int i = 0; i < 1000000; i++)
        {
            final int value = i % 100;
            
            final String propertyString =
                    PropertyUtil.getSystemOrPropertyString("test.performance.property" + value + "randomvalue", null,
                            false);
            Assert.assertEquals(propertyString, "Test Performance Property " + value);
        }
    }
    
    @Test
    public synchronized final void testPerformanceNonCachingExistingPropertiesNonThreadedWithDefault()
    {
        for(int i = 0; i < 1000000; i++)
        {
            final int value = i % 100;
            
            final String defaultValue = Integer.toHexString(3127 + value);
            
            final String propertyString =
                    PropertyUtil.getSystemOrPropertyString("test.performance.property" + value + "randomvalue",
                            defaultValue, false);
            Assert.assertEquals(propertyString, "Test Performance Property " + value);
        }
    }
    
    @Test
    public synchronized final void testPerformanceNonCachingMissingPropertiesNonThreadedNoDefault()
    {
        for(int i = 0; i < 1000000; i++)
        {
            final int value = i % 100;
            
            final String propertyString =
                    PropertyUtil
                            .getSystemOrPropertyString("test.missing.property" + value + "randomvalue", null, false);
            Assert.assertNull(propertyString);
        }
    }
    
    @Test
    public synchronized final void testPerformanceNonCachingMissingPropertiesNonThreadedWithDefault()
    {
        for(int i = 0; i < 1000000; i++)
        {
            final int value = i % 100;
            
            final String defaultValue = Integer.toHexString(3127 + value);
            
            final String propertyString =
                    PropertyUtil.getSystemOrPropertyString("test.missing.property" + value + "randomvalue",
                            defaultValue, false);
            Assert.assertEquals(propertyString, Integer.toHexString(3127 + value));
        }
    }
    
}