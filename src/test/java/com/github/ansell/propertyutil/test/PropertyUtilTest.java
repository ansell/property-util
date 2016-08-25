/**
 * 
 */
package com.github.ansell.propertyutil.test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ResourceBundle;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.github.ansell.propertyutil.PropertyUtil;

/**
 * Test for PropertyUtil class
 * 
 * @author Peter Ansell p_ansell@yahoo.com
 */
public class PropertyUtilTest {
	@Rule
	public TemporaryFolder tempDir = new TemporaryFolder();

	private Path testDir;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		this.testDir = this.tempDir.newFolder("propertyutiltest").toPath();

		// verify that the default is to use caching
		Assert.assertTrue(PropertyUtil.DEFAULT_USE_CACHE);
	}

	private PropertyUtil getTestUtil() {
		PropertyUtil result = new PropertyUtil("com.github.ansell.propertyutil.test.propertyutiltestbundle");

		// make sure we are using our custom test bundle
		Assert.assertEquals("com.github.ansell.propertyutil.test.propertyutiltestbundle",
				result.getPropertyBundleName());

		Assert.assertNotNull("Test Resource was not found",
				ResourceBundle.getBundle("com.github.ansell.propertyutil.test.propertyutiltestbundle"));

		return result;
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		this.testDir = null;
	}

	@Test
	public final void testBasicGetExistsNoDefault() {
		PropertyUtil testPropertyUtil = this.getTestUtil();
		final String result2 = testPropertyUtil.get("test.clear.property.cache");

		Assert.assertNotNull("test.clear.property.cache property was not found", result2);

		Assert.assertEquals("Configured property for clearing property cache", result2);
	}

	@Test
	public final void testBasicGetExistsWithDefault() {
		PropertyUtil testPropertyUtil = this.getTestUtil();
		final String result = testPropertyUtil.get("test.clear.property.cache",
				"default-clear-property-cache-test-property-1");

		Assert.assertNotNull("test.clear.property.cache property was not found", result);

		Assert.assertEquals("Configured property for clearing property cache", result);
	}

	@Test
	public final void testBasicGetNotExistsNoDefault() {
		PropertyUtil testPropertyUtil = this.getTestUtil();
		final String result = testPropertyUtil.get("test.false.property", null);

		Assert.assertNull(result);
	}

	@Test
	public final void testBasicGetNotExistsWithDefault() {
		PropertyUtil testPropertyUtil = this.getTestUtil();
		final String result2 = testPropertyUtil.get("test.false.property",
				"default-clear-property-cache-test-property-2");

		Assert.assertNotNull("test.clear.property.cache property was not found", result2);

		Assert.assertEquals("default-clear-property-cache-test-property-2", result2);
	}

	@Test
	public final void testGetExistsNoDefault() {
		PropertyUtil testPropertyUtil = this.getTestUtil();
		final String result2 = testPropertyUtil.get("test.clear.property.cache", null);

		Assert.assertNotNull("test.clear.property.cache property was not found", result2);

		Assert.assertEquals("Configured property for clearing property cache", result2);
	}

	@Test
	public final void testGetExistsWithDefault() {
		PropertyUtil testPropertyUtil = this.getTestUtil();
		final String result2 = testPropertyUtil.get("test.clear.property.cache",
				"default-clear-property-cache-test-property-4");

		Assert.assertNotNull("test.clear.property.cache property was not found", result2);

		Assert.assertEquals("Configured property for clearing property cache", result2);
	}

	@Test
	public final void testGetNotExistsNoDefault() {
		PropertyUtil testPropertyUtil = this.getTestUtil();
		final String result3 = testPropertyUtil.get("test.nonexistent.property.cache", null);

		Assert.assertNull("test.nonexistent.property.cache property was not found", result3);
	}

	@Test
	public final void testGetNotExistsWithDefault() {
		PropertyUtil testPropertyUtil = this.getTestUtil();
		final String result2 = testPropertyUtil.get("test.false.property",
				"default-clear-property-cache-test-property-6");

		Assert.assertNotNull("test.clear.property.cache property was not found", result2);

		Assert.assertEquals("default-clear-property-cache-test-property-6", result2);
	}

	@Test
	public final void testUserDir() throws Exception {
		final String originalUserDir = System.setProperty("user.dir", this.testDir.toString());

		try {
			Files.copy(
					this.getClass().getResourceAsStream(
							"/com/github/ansell/propertyutil/test/propertyutiltestbundle.properties"),
					this.testDir.resolve("poddclienttest.properties"));
			Assert.assertTrue(Files.exists(this.testDir.resolve("poddclienttest.properties")));
			Assert.assertTrue(Files.size(this.testDir.resolve("poddclienttest.properties")) > 0);

			PropertyUtil result = new PropertyUtil("poddclienttest");

			Assert.assertEquals("Configured property for clearing property cache",
					result.get("test.clear.property.cache", "not-a-property"));
		} finally {
			System.setProperty("user.dir", originalUserDir);
		}

	}

	@Test
	public final void testUserDirSubDirectory() throws Exception {
		final String originalUserHome = System.setProperty("user.dir", this.testDir.toString());

		try {
			Files.createDirectories(this.testDir.resolve("test"));

			Files.copy(
					this.getClass().getResourceAsStream(
							"/com/github/ansell/propertyutil/test/propertyutiltestbundle.properties"),
					this.testDir.resolve("test/poddclienttest.properties"));
			Assert.assertTrue(Files.exists(this.testDir.resolve("test/poddclienttest.properties")));
			Assert.assertTrue(Files.size(this.testDir.resolve("test/poddclienttest.properties")) > 0);

			PropertyUtil result = new PropertyUtil("test.poddclienttest");

			Assert.assertEquals("Configured property for clearing property cache",
					result.get("test.clear.property.cache", "not-a-property"));
		} finally {
			System.setProperty("user.dir", originalUserHome);
		}
	}

	@Test
	public final void testUserDirSubDirectoryOther() throws Exception {
		final String originalUserHome = System.setProperty("user.dir", this.testDir.toString());

		try {
			Files.createDirectories(this.testDir.resolve("test"));

			Files.copy(
					this.getClass().getResourceAsStream(
							"/com/github/ansell/propertyutil/test/propertyutiltestbundle.properties"),
					this.testDir.resolve("test/poddclienttest.properties"));
			Assert.assertTrue(Files.exists(this.testDir.resolve("test/poddclienttest.properties")));
			Assert.assertTrue(Files.size(this.testDir.resolve("test/poddclienttest.properties")) > 0);

			PropertyUtil result = new PropertyUtil("poddclienttest", "test");

			Assert.assertEquals("Configured property for clearing property cache",
					result.get("test.clear.property.cache", "not-a-property"));
		} finally {
			System.setProperty("user.dir", originalUserHome);
		}
	}

	@Test
	public final void testUserHome() throws Exception {
		final String originalUserHome = System.setProperty("user.home", this.testDir.toString());

		try {
			Files.copy(
					this.getClass().getResourceAsStream(
							"/com/github/ansell/propertyutil/test/propertyutiltestbundle.properties"),
					this.testDir.resolve("poddclienttest.properties"));
			Assert.assertTrue(Files.exists(this.testDir.resolve("poddclienttest.properties")));
			Assert.assertTrue(Files.size(this.testDir.resolve("poddclienttest.properties")) > 0);

			PropertyUtil result = new PropertyUtil("poddclienttest");

			Assert.assertEquals("Configured property for clearing property cache",
					result.get("test.clear.property.cache", "not-a-property"));
		} finally {
			System.setProperty("user.home", originalUserHome);
		}
	}

	@Test
	public final void testUserHomeSubDirectory() throws Exception {
		final String originalUserHome = System.setProperty("user.home", this.testDir.toString());

		try {
			Files.createDirectories(this.testDir.resolve("test"));

			Files.copy(
					this.getClass().getResourceAsStream(
							"/com/github/ansell/propertyutil/test/propertyutiltestbundle.properties"),
					this.testDir.resolve("test/poddclienttest.properties"));
			Assert.assertTrue(Files.exists(this.testDir.resolve("test/poddclienttest.properties")));
			Assert.assertTrue(Files.size(this.testDir.resolve("test/poddclienttest.properties")) > 0);

			PropertyUtil result = new PropertyUtil("test.poddclienttest");

			Assert.assertEquals("Configured property for clearing property cache",
					result.get("test.clear.property.cache", "not-a-property"));
		} finally {
			System.setProperty("user.home", originalUserHome);
		}
	}

	@Test
	public final void testUserHomeSubDirectoryOther() throws Exception {
		final String originalUserHome = System.setProperty("user.home", this.testDir.toString());

		try {
			Files.createDirectories(this.testDir.resolve("test"));

			Files.copy(
					this.getClass().getResourceAsStream(
							"/com/github/ansell/propertyutil/test/propertyutiltestbundle.properties"),
					this.testDir.resolve("test/poddclienttest.properties"));
			Assert.assertTrue(Files.exists(this.testDir.resolve("test/poddclienttest.properties")));
			Assert.assertTrue(Files.size(this.testDir.resolve("test/poddclienttest.properties")) > 0);

			PropertyUtil result = new PropertyUtil("poddclienttest", "test");

			Assert.assertEquals("Configured property for clearing property cache",
					result.get("test.clear.property.cache", "not-a-property"));
		} finally {
			System.setProperty("user.home", originalUserHome);
		}
	}
}
