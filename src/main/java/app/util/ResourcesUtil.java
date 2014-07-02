package app.util;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;

import javax.imageio.ImageIO;

public class ResourcesUtil {
	
	// Properties files.
	public static final String DATABASE_PROPERTIES = "database.properties";

	// Resource folders.
	private static final String ROOT_FOLDER = "app";
	private static final String PROPERTIES_FOLDER = "app/properties";
	private static final String IMAGES_FOLDER = "app/images";

	/**
	 * Method that returns the value of the key passed by argument.
	 * 
	 * @param fileName
	 * @param key
	 * @return {@link String}
	 */
	public static String getValue(final String fileName, final String key) {
		final Properties properties = loadProperties(fileName);
		return properties.getProperty(key);
	}

	/**
	 * Method that returns a loaded properties file.
	 * 
	 * @param fileName
	 * @return {@link Properties}
	 */
	public static Properties loadProperties(final String fileName) {
		final Properties properties = new Properties();
    	try {
    		final URL url = getClassLoader().getResource(PROPERTIES_FOLDER + File.separator + fileName);
    		if (url != null) {
	    		properties.load(url.openStream());
	    		return properties;
    		}
    	} catch (IOException e) {
    		e.printStackTrace();
        }
    	return null;
	}
	
	/**
	 * Method that returns a resource image.
	 * 
	 * @param imagePath
	 * @return {@link Image}
	 */
	public static Image getImage(final String imagePath) {
		try {
			final URL url = getClassLoader().getResource(IMAGES_FOLDER + File.separator + imagePath);
			if (url != null) {
				final Image image = ImageIO.read(url);
				return image;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * Method that returns a resource file.
	 * 
	 * @param imagePath
	 * @return {@link File}
	 */
	public static File getFile(final String filePath) {
		try {
			final URL url = getClassLoader().getResource(ROOT_FOLDER + File.separator + filePath);
			if (url != null) {
				final File file = new File(url.toURI());
				return file;
			}
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Method that returns the class loader.
	 * 
	 * @return {@link ClassLoader}
	 */
	private static ClassLoader getClassLoader() {
		ClassLoader loader = ResourcesUtil.class.getClassLoader();
		if (loader == null) {
			loader = ClassLoader.getSystemClassLoader();
		}
		return loader;
	}
	
}
