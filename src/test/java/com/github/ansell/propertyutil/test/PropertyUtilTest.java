/**
 * 
 */
package com.github.ansell.propertyutil.test;

import java.util.ResourceBundle;

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
    
    private PropertyUtil testPropertyUtil;
    
    /**
     * @throws java.lang.Exception
     */
    @Before
    public synchronized void setUp() throws Exception
    {
        this.testPropertyUtil = new PropertyUtil();
        // clear property cache before and after tests
        this.testPropertyUtil.clearPropertyCache();
        
        // verify that the default is to use caching
        Assert.assertTrue(testPropertyUtil.DEFAULT_USE_CACHE);
        
        // make sure we are using our custom test bundle
        testPropertyUtil.setPropertyBundleName("com.github.ansell.propertyutil.test.propertyutiltestbundle");
        
        Assert.assertEquals("com.github.ansell.propertyutil.test.propertyutiltestbundle",
                testPropertyUtil.getPropertyBundleName());
        
        Assert.assertNotNull("Test Resource was not found",
                ResourceBundle.getBundle("com.github.ansell.propertyutil.test.propertyutiltestbundle"));
    }
    
    /**
     * @throws java.lang.Exception
     */
    @After
    public synchronized void tearDown() throws Exception
    {
        // clear property cache before and after tests
        testPropertyUtil.clearPropertyCache();
        
        testPropertyUtil.setPropertyBundleName(testPropertyUtil.DEFAULT_PROPERTIES_BUNDLE_NAME);
    }
    
    @Test
    public synchronized final void testBasicGetExistsNoDefault()
    {
        final String result2 = testPropertyUtil.get("test.clear.property.cache", null);
        
        Assert.assertNotNull("test.clear.property.cache property was not found", result2);
        
        Assert.assertEquals("Configured property for clearing property cache", result2);
    }
    
    @Test
    public synchronized final void testBasicGetExistsWithDefault()
    {
        final String result =
                testPropertyUtil.getSystemOrPropertyString("test.clear.property.cache",
                        "default-clear-property-cache-test-property-1", false);
        
        Assert.assertNotNull("test.clear.property.cache property was not found", result);
        
        Assert.assertEquals("Configured property for clearing property cache", result);
    }
    
    @Test
    public synchronized final void testBasicGetNotExistsNoDefault()
    {
        final String result = testPropertyUtil.getSystemOrPropertyString("test.false.property", null, false);
        
        Assert.assertNull(result);
    }
    
    @Test
    public synchronized final void testBasicGetNotExistsWithDefault()
    {
        final String result2 =
                testPropertyUtil.getSystemOrPropertyString("test.false.property",
                        "default-clear-property-cache-test-property-2", true);
        
        Assert.assertNotNull("test.clear.property.cache property was not found", result2);
        
        Assert.assertEquals("default-clear-property-cache-test-property-2", result2);
    }
    
    @Test
    public synchronized final void testGetExistsNoDefaultNoCaching()
    {
        final String result = testPropertyUtil.getSystemOrPropertyString("test.clear.property.cache", null, false);
        
        Assert.assertNotNull("test.clear.property.cache property was not found", result);
        
        Assert.assertEquals("Configured property for clearing property cache", result);
    }
    
    @Test
    public synchronized final void testGetExistsNoDefaultWithCaching()
    {
        final String result2 = testPropertyUtil.getSystemOrPropertyString("test.clear.property.cache", null, true);
        
        Assert.assertNotNull("test.clear.property.cache property was not found", result2);
        
        Assert.assertEquals("Configured property for clearing property cache", result2);
    }
    
    @Test
    public synchronized final void testGetExistsWithDefaultNoCaching()
    {
        final String result =
                testPropertyUtil.getSystemOrPropertyString("test.clear.property.cache",
                        "default-clear-property-cache-test-property-3", false);
        
        Assert.assertNotNull("test.clear.property.cache property was not found", result);
        
        Assert.assertEquals("Configured property for clearing property cache", result);
    }
    
    @Test
    public synchronized final void testGetExistsWithDefaultWithCaching()
    {
        final String result2 =
                testPropertyUtil.getSystemOrPropertyString("test.clear.property.cache",
                        "default-clear-property-cache-test-property-4", true);
        
        Assert.assertNotNull("test.clear.property.cache property was not found", result2);
        
        Assert.assertEquals("Configured property for clearing property cache", result2);
    }
    
    @Test
    public synchronized final void testGetNotExistsNoDefaultNoCaching()
    {
        final String result3 =
                testPropertyUtil.getSystemOrPropertyString("test.nonexistent.property.cache", null, false);
        
        Assert.assertNull("test.nonexistent.property.cache property was not found", result3);
    }
    
    @Test
    public synchronized final void testGetNotExistsNoDefaultWithCaching()
    {
        final String result3 =
                testPropertyUtil.getSystemOrPropertyString("test.nonexistent.property.cache", null, true);
        
        Assert.assertNull("test.nonexistent.property.cache property was not found", result3);
    }
    
    @Test
    public synchronized final void testGetNotExistsWithDefaultNoCaching()
    {
        final String result =
                testPropertyUtil.getSystemOrPropertyString("test.false.property",
                        "default-clear-property-cache-test-property-5", false);
        
        Assert.assertNotNull("test.clear.property.cache property was not found", result);
        
        Assert.assertEquals("default-clear-property-cache-test-property-5", result);
    }
    
    @Test
    public synchronized final void testGetNotExistsWithDefaultWithCaching()
    {
        final String result2 =
                testPropertyUtil.getSystemOrPropertyString("test.false.property",
                        "default-clear-property-cache-test-property-6", true);
        
        Assert.assertNotNull("test.clear.property.cache property was not found", result2);
        
        Assert.assertEquals("default-clear-property-cache-test-property-6", result2);
    }
    
    @Test
    public synchronized final void testSetPropertyBundleName()
    {
        testPropertyUtil.setPropertyBundleName("com.github.ansell.propertyutil.test.propertyutiltestbundle");
        
        Assert.assertEquals("com.github.ansell.propertyutil.test.propertyutiltestbundle",
                testPropertyUtil.getPropertyBundleName());
        
        // make sure we are using our custom test bundle
        testPropertyUtil.setPropertyBundleName(null);
        
        Assert.assertEquals(testPropertyUtil.DEFAULT_PROPERTIES_BUNDLE_NAME, testPropertyUtil.getPropertyBundleName());
        
        testPropertyUtil.setPropertyBundleName("com.github.ansell.propertyutil.test.propertyutiltestbundle");
        
        Assert.assertEquals("com.github.ansell.propertyutil.test.propertyutiltestbundle",
                testPropertyUtil.getPropertyBundleName());
    }
}
