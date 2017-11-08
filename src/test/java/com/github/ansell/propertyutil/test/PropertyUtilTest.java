/**
 * 
 */
package com.github.ansell.propertyutil.test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ResourceBundle;

import org.junit.Rule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.rules.TemporaryFolder;
import org.junit.jupiter.migrationsupport.rules.ExternalResourceSupport;

import com.github.ansell.propertyutil.PropertyUtil;

/**
 * Test for PropertyUtil class
 * 
 * @author Peter Ansell p_ansell@yahoo.com
 */
@ExtendWith(ExternalResourceSupport.class)
public class PropertyUtilTest {
	@Rule
	public TemporaryFolder tempDir = new TemporaryFolder();

	private Path testDir;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	public void setUp() throws Exception {
		this.testDir = this.tempDir.newFolder("propertyutiltest").toPath();

		// verify that the default is to use caching
		assertTrue(PropertyUtil.DEFAULT_USE_CACHE);
	}

	private PropertyUtil getTestUtil() {
		PropertyUtil result = new PropertyUtil("com.github.ansell.propertyutil.test.propertyutiltestbundle");

		// make sure we are using our custom test bundle
		assertEquals("com.github.ansell.propertyutil.test.propertyutiltestbundle", result.getPropertyBundleName());

		assertNotNull(ResourceBundle.getBundle("com.github.ansell.propertyutil.test.propertyutiltestbundle"),
				"Test Resource was not found");

		return result;
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	public void tearDown() throws Exception {
		this.testDir = null;
	}

	@Test
	public final void testBasicGetExistsNoDefault() {
		PropertyUtil testPropertyUtil = this.getTestUtil();
		final String result2 = testPropertyUtil.get("test.clear.property.cache");

		assertNotNull(result2, "test.clear.property.cache property was not found");

		assertEquals("Configured property for clearing property cache", result2);
	}

	@Test
	public final void testBasicGetExistsWithDefault() {
		PropertyUtil testPropertyUtil = this.getTestUtil();
		final String result = testPropertyUtil.get("test.clear.property.cache",
				"default-clear-property-cache-test-property-1");

		assertNotNull(result, "test.clear.property.cache property was not found");

		assertEquals("Configured property for clearing property cache", result);
	}

	@Test
	public final void testBasicGetNotExistsNoDefault() {
		PropertyUtil testPropertyUtil = this.getTestUtil();
		final String result = testPropertyUtil.get("test.false.property", null);

		assertNull(result);
	}

	@Test
	public final void testBasicGetNotExistsWithDefault() {
		PropertyUtil testPropertyUtil = this.getTestUtil();
		final String result2 = testPropertyUtil.get("test.false.property",
				"default-clear-property-cache-test-property-2");

		assertNotNull(result2, "test.clear.property.cache property was not found");

		assertEquals("default-clear-property-cache-test-property-2", result2);
	}

	@Test
	public final void testGetExistsNoDefault() {
		PropertyUtil testPropertyUtil = this.getTestUtil();
		final String result2 = testPropertyUtil.get("test.clear.property.cache", null);

		assertNotNull(result2, "test.clear.property.cache property was not found");

		assertEquals("Configured property for clearing property cache", result2);
	}

	@Test
	public final void testGetExistsWithDefault() {
		PropertyUtil testPropertyUtil = this.getTestUtil();
		final String result2 = testPropertyUtil.get("test.clear.property.cache",
				"default-clear-property-cache-test-property-4");

		assertNotNull(result2, "test.clear.property.cache property was not found");

		assertEquals("Configured property for clearing property cache", result2);
	}

	@Test
	public final void testGetNotExistsNoDefault() {
		PropertyUtil testPropertyUtil = this.getTestUtil();
		final String result3 = testPropertyUtil.get("test.nonexistent.property.cache", null);

		assertNull(result3, "test.nonexistent.property.cache property was not found");
	}

	@Test
	public final void testGetNotExistsWithDefault() {
		PropertyUtil testPropertyUtil = this.getTestUtil();
		final String result2 = testPropertyUtil.get("test.false.property",
				"default-clear-property-cache-test-property-6");

		assertNotNull(result2, "test.clear.property.cache property was not found");

		assertEquals("default-clear-property-cache-test-property-6", result2);
	}

	@Test
	public final void testUserDir() throws Exception {
		final String originalUserDir = System.setProperty("user.dir", this.testDir.toString());

		try {
			Files.copy(
					this.getClass().getResourceAsStream(
							"/com/github/ansell/propertyutil/test/propertyutiltestbundle.properties"),
					this.testDir.resolve("poddclienttest.properties"));
			assertTrue(Files.exists(this.testDir.resolve("poddclienttest.properties")));
			assertTrue(Files.size(this.testDir.resolve("poddclienttest.properties")) > 0);

			PropertyUtil result = new PropertyUtil("poddclienttest");

			assertEquals(result.get("test.clear.property.cache", "not-a-property"),
					"Configured property for clearing property cache");
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
			assertTrue(Files.exists(this.testDir.resolve("test/poddclienttest.properties")));
			assertTrue(Files.size(this.testDir.resolve("test/poddclienttest.properties")) > 0);

			PropertyUtil result = new PropertyUtil("test.poddclienttest");

			assertEquals(result.get("test.clear.property.cache", "not-a-property"),
					"Configured property for clearing property cache");
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
			assertTrue(Files.exists(this.testDir.resolve("test/poddclienttest.properties")));
			assertTrue(Files.size(this.testDir.resolve("test/poddclienttest.properties")) > 0);

			PropertyUtil result = new PropertyUtil("poddclienttest", "test");

			assertEquals(result.get("test.clear.property.cache", "not-a-property"),
					"Configured property for clearing property cache");
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
			assertTrue(Files.exists(this.testDir.resolve("poddclienttest.properties")));
			assertTrue(Files.size(this.testDir.resolve("poddclienttest.properties")) > 0);

			PropertyUtil result = new PropertyUtil("poddclienttest");

			assertEquals(result.get("test.clear.property.cache", "not-a-property"),
					"Configured property for clearing property cache");
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
			assertTrue(Files.exists(this.testDir.resolve("test/poddclienttest.properties")));
			assertTrue(Files.size(this.testDir.resolve("test/poddclienttest.properties")) > 0);

			PropertyUtil result = new PropertyUtil("test.poddclienttest");

			assertEquals(result.get("test.clear.property.cache", "not-a-property"),
					"Configured property for clearing property cache");
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
			assertTrue(Files.exists(this.testDir.resolve("test/poddclienttest.properties")));
			assertTrue(Files.size(this.testDir.resolve("test/poddclienttest.properties")) > 0);

			PropertyUtil result = new PropertyUtil("poddclienttest", "test");

			assertEquals(result.get("test.clear.property.cache", "not-a-property"),
					"Configured property for clearing property cache");
		} finally {
			System.setProperty("user.home", originalUserHome);
		}
	}
}
