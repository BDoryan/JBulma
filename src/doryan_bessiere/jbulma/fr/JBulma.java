package doryan_bessiere.jbulma.fr;

import java.io.File;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import doryan_bessiere.jbulma.fr.tools.logger.Logger;
import doryan_bessiere.jbulma.fr.tools.logger.LoggerFile;
import doryan_bessiere.jbulma.fr.tools.reader.FileReader;
import doryan_bessiere.jwebapi.fr.JWebAPI;

public class JBulma {

	public static final String NAME = "JBulma";
	public static final String VERSION = "BETA-0.0.1";
	public static final List<String> AUTHORS = Arrays.asList("Doryan Bessiere");
	public static final String WEB_SITE = "http://doryan-bessiere.000webhostapp.com/apps/";
	public static final String BULMA_RESOURCES = WEB_SITE+"jbulma/resources/";
	
	public static final FileReader BULMA_INFO = new FileReader("bulma.info", BULMA_RESOURCES+"bulma.info", false); 

	public static String BULMA_VERSION = null;
	
	public static Logger LOGGER = new Logger("JBulma", new LoggerFile(new File(localDirectory(), "logs")));
	public static boolean DEBUG = false;

	public static void initialize(boolean debug) {
		DEBUG = debug;
		BULMA_INFO.read(debug);
		
		BULMA_VERSION = BULMA_INFO.get("version");

		System.out.println("");
		System.out.println("BULMA INFORMATION:");
		System.out.println("	URL: "+WEB_SITE);
		System.out.println("	VERSION: "+BULMA_VERSION);
		System.out.println("jbulma-api is initialized.");
		JWebAPI.initialize(debug);
	}
	
	public static File localDirectory() {
		try {
			File file = new File(JBulma.class.getProtectionDomain().getCodeSource().getLocation().toURI());
			return file;
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return null;
	}
}
