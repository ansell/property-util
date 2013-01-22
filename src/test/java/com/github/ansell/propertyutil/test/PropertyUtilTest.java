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
public class PropertyUtilTest
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
    public synchronized final void testBasicGetExistsNoDefault()
    {
        final String result2 = PropertyUtil.get("test.clear.property.cache", null);
        
        Assert.assertNotNull("test.clear.property.cache property was not found", result2);
        
        Assert.assertEquals("Configured property for clearing property cache", result2);
    }
    
    @Test
    public synchronized final void testBasicGetExistsWithDefault()
    {
        final String result =
                PropertyUtil.getSystemOrPropertyString("test.clear.property.cache",
                        "default-clear-property-cache-test-property", false);
        
        Assert.assertNotNull("test.clear.property.cache property was not found", result);
        
        Assert.assertEquals("Configured property for clearing property cache", result);
    }
    
    @Test
    public synchronized final void testBasicGetNotExistsNoDefault()
    {
        final String result = PropertyUtil.getSystemOrPropertyString("test.false.property", null, false);
        
        Assert.assertNull(result);
    }
    
    @Test
    public synchronized final void testBasicGetNotExistsWithDefault()
    {
        final String result2 =
                PropertyUtil.getSystemOrPropertyString("test.false.property",
                        "default-clear-property-cache-test-property", true);
        
        Assert.assertNotNull("test.clear.property.cache property was not found", result2);
        
        Assert.assertEquals("default-clear-property-cache-test-property", result2);
    }
    
    @Test
    public synchronized final void testGetExistsNoDefaultNoCaching()
    {
        final String result = PropertyUtil.getSystemOrPropertyString("test.clear.property.cache", null, false);
        
        Assert.assertNotNull("test.clear.property.cache property was not found", result);
        
        Assert.assertEquals("Configured property for clearing property cache", result);
    }
    
    @Test
    public synchronized final void testGetExistsNoDefaultWithCaching()
    {
        final String result2 = PropertyUtil.getSystemOrPropertyString("test.clear.property.cache", null, true);
        
        Assert.assertNotNull("test.clear.property.cache property was not found", result2);
        
        Assert.assertEquals("Configured property for clearing property cache", result2);
    }
    
    @Test
    public synchronized final void testGetExistsWithDefaultNoCaching()
    {
        final String result =
                PropertyUtil.getSystemOrPropertyString("test.clear.property.cache",
                        "default-clear-property-cache-test-property", false);
        
        Assert.assertNotNull("test.clear.property.cache property was not found", result);
        
        Assert.assertEquals("Configured property for clearing property cache", result);
    }
    
    @Test
    public synchronized final void testGetExistsWithDefaultWithCaching()
    {
        final String result2 =
                PropertyUtil.getSystemOrPropertyString("test.clear.property.cache",
                        "default-clear-property-cache-test-property", true);
        
        Assert.assertNotNull("test.clear.property.cache property was not found", result2);
        
        Assert.assertEquals("Configured property for clearing property cache", result2);
    }
    
    @Test
    public synchronized final void testGetNotExistsNoDefaultNoCaching()
    {
        final String result3 = PropertyUtil.getSystemOrPropertyString("test.nonexistent.property.cache", null, false);
        
        Assert.assertNull("test.nonexistent.property.cache property was not found", result3);
    }
    
    @Test
    public synchronized final void testGetNotExistsNoDefaultWithCaching()
    {
        final String result3 = PropertyUtil.getSystemOrPropertyString("test.nonexistent.property.cache", null, true);
        
        Assert.assertNull("test.nonexistent.property.cache property was not found", result3);
    }
    
    @Test
    public synchronized final void testGetNotExistsWithDefaultNoCaching()
    {
        final String result =
                PropertyUtil.getSystemOrPropertyString("test.false.property",
                        "default-clear-property-cache-test-property", false);
        
        Assert.assertNotNull("test.clear.property.cache property was not found", result);
        
        Assert.assertEquals("default-clear-property-cache-test-property", result);
    }
    
    @Test
    public synchronized final void testGetNotExistsWithDefaultWithCaching()
    {
        final String result2 =
                PropertyUtil.getSystemOrPropertyString("test.false.property",
                        "default-clear-property-cache-test-property", true);
        
        Assert.assertNotNull("test.clear.property.cache property was not found", result2);
        
        Assert.assertEquals("default-clear-property-cache-test-property", result2);
    }
    
    @Test
    public synchronized final void testSetPropertyBundleName()
    {
        // make sure we are using our custom test bundle
        PropertyUtil.setPropertyBundleName("com.github.ansell.propertyutil.test.propertyutiltest");
        
        Assert.assertEquals("com.github.ansell.propertyutil.test.propertyutiltest",
                PropertyUtil.getPropertyBundleName());
        
        // make sure we are using our custom test bundle
        PropertyUtil.setPropertyBundleName(null);
        
        Assert.assertEquals(PropertyUtil.DEFAULT_PROPERTIES_BUNDLE_NAME, PropertyUtil.getPropertyBundleName());
        
    }
}
