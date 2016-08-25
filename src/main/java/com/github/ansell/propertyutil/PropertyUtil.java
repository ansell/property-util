/**
 * 
 */
package com.github.ansell.propertyutil;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.ResourceBundle.Control;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A utility class for accessing properties from the following sources in order,
 * with the first instance that is found being used:
 * 
 * <ol>
 * <li>System properties : -Dproperty.name=... on command line</li>
 * <li>Properties file in current user directory: ./bundlename.properties</li>
 * <li>Properties file in user home directory: ~/bundlename.properties</li>
 * <li>Properties file in classpath: /bundlename.properties</li>
 * </ol>
 * 
 * @author Peter Ansell p_ansell@yahoo.com
 * 
 */
public final class PropertyUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(PropertyUtil.class);

	/**
	 * A constant to indicate the default preference for caching properties, or
	 * not caching properties.
	 */
	public static final boolean DEFAULT_USE_CACHE = true;

	/**
	 * The maximum cache size before it is cleared.
	 */
	private static final int MAX_CACHE_SIZE = 2048;

	/**
	 * The configured name for the resource bundle
	 */
	private final String bundleName;

	/**
	 * A {@link ResourceBundle} matching the given bundleName field or null if
	 * it was not available
	 */
	private final ResourceBundle bundle;

	/**
	 * Internal property cache, used if and when users indicate that they want
	 * to use the cache.
	 */
	private final ConcurrentMap<String, String> cache = new ConcurrentHashMap<String, String>();

	public PropertyUtil(final String bundleName) {
		this(bundleName, "");
	}

	public PropertyUtil(final String bundleName, final String userSubdirectory) {
		this.bundleName = bundleName;
		this.bundle = getBundle(bundleName, userSubdirectory);
	}

	/**
	 * Clears the internal property cache.
	 */
	public final void clearPropertyCache() {
		this.cache.clear();
	}

	/**
	 * 
	 * @return The property bundle name to be used for fetching properties.
	 */
	public String getPropertyBundleName() {
		return this.bundleName;
	}

	/**
	 * Checks for the key first in the system vm properties, then in the
	 * localisation properties file, then returns null if the key was not found.
	 * 
	 * @param key
	 *            The key to check for first in system vm properties and then in
	 *            the localisation properties file
	 * @return the string matching the key, or null if the key was not found.
	 */
	public String get(final String key) {
		return get(key, null);
	}

	/**
	 * Checks for the key first in the system vm properties, then in the
	 * localisation properties file, then uses the defaultValue if the location
	 * is still unknown.
	 * 
	 * @param key
	 *            The key to check for first in system vm properties and then in
	 *            the localisation properties file
	 * @param defaultValue
	 *            The value to return if the key does not match any configured
	 *            value. May be null.
	 * @return the string matching the key, or null if the default value was
	 *         null and the key was not found.
	 */
	public String get(final String key, final String defaultValue) {

		if (this.cache.size() > MAX_CACHE_SIZE) {
			this.cache.clear();
		}

		return this.cache.computeIfAbsent(key, k -> {
			// Try to get the property from the system configuration, for
			// example,
			// from, 'java
			// -Dkey=value'
			Optional<String> result = Optional.ofNullable(System.getProperty(k));

			if (!result.isPresent()) {
				// If we were unsuccessful in the cache and the system
				// properties,
				// try to fetch from
				// properties file on the class path
				if (this.bundle != null) {
					try {
						result = Optional.ofNullable(this.bundle.getString(k));
					} catch (final MissingResourceException e) {
						// Do nothing, will use default
						;
					}
				}
			}

			// if the property didn't exist, replace it with the default value
			if (!result.isPresent()) {
				result = Optional.ofNullable(defaultValue);
			}

			if (LOGGER.isTraceEnabled()) {
				LOGGER.trace("Returning property value: <{}>=<{}> (default=<{}>)", k, result.get(), defaultValue);
			}

			return result.orElse(null);
		});
	}

	private static ResourceBundle getBundle(String bundleName, String userSubdirectory) {
		Optional<ResourceBundle> result = Optional.empty();
		final String userDir = System.getProperty("user.dir");
		final String userHome = System.getProperty("user.home");
		if (userDir != null) {
			// Try to resolve bundle in the current user directory
			result = getBundleFromUserDir(bundleName, userSubdirectory, userDir);
		} else {
			LOGGER.debug("Could not find user.dir property");
		}
		if (!result.isPresent()) {
			if (userHome != null) {
				// Try to resolve bundle in the current user home directory
				result = getBundleFromUserDir(bundleName, userSubdirectory, userHome);
			} else {
				LOGGER.debug("Could not find user.home property");
			}
		}
		if (!result.isPresent()) {
			try {
				LOGGER.debug("Looking for property bundle on classpath");
				// Try to resolve bundle on classpath
				result = Optional.ofNullable(ResourceBundle.getBundle(bundleName));
				LOGGER.debug("Found property bundle in classpath: {}", bundleName);
			} catch (final MissingResourceException mre) {
				// Do nothing, will try other options
				;
			}
		}
		if (!result.isPresent()) {
			LOGGER.info("Could not find property bundle: {}", bundleName);
		}
		return result.orElse(null);
	}

	private static Optional<ResourceBundle> getBundleFromUserDir(String bundleName, String userSubdirectory,
			final String userDir) {
		ResourceBundle result = null;
		final String fullUserDir;
		if (userDir.endsWith("/")) {
			fullUserDir = userDir + userSubdirectory;
		} else {
			fullUserDir = userDir + "/" + userSubdirectory;
		}
		LOGGER.debug("Looking for property bundle in user.dir + subdirectory: {}", fullUserDir);
		final Path userDirPath = Paths.get(fullUserDir);
		if (Files.exists(userDirPath)) {
			try {
				final URL[] urls = { userDirPath.toUri().toURL() };
				final ClassLoader loader = new URLClassLoader(urls);
				result = ResourceBundle.getBundle(bundleName, Locale.getDefault(), loader,
						Control.getNoFallbackControl(Control.FORMAT_PROPERTIES));
				LOGGER.debug("Found property bundle in user.dir + subdirectory: {} {}", userDirPath.toUri().toURL(),
						bundleName);
			} catch (final MalformedURLException e) {
				LOGGER.debug("URL exception with user.dir:", e);
			} catch (final MissingResourceException e) {
				// Do nothing, will try other options
				LOGGER.debug("Missing resource exception with user.dir:", e);
			}
		}
		return Optional.ofNullable(result);
	}
}
