package main.java.com.vpd.bbva.conf;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesFile {
	private static PropertiesFile INSTANCE = null;
	private Properties props = null;
	private static String archivoP = "interfaz.properties";
	private FileInputStream input = null;
	private static String paths[] = { "C:\\" + getArchivoP() }; // Local
	// private static String paths[]={"/rhsicofi/SICOFE/prop_jars/" + getFileP()}; // Desarrollo y Produccion
	
	private synchronized static void createInstace() {
		if (INSTANCE == null)
			INSTANCE = new PropertiesFile();
	}

	public static PropertiesFile getInstance() {
		if (INSTANCE == null)
			createInstace();
		return INSTANCE;

	}

	@SuppressWarnings("unused")
	public String getPropertiesFile(String parameter) throws IOException {

		String propertie = "";
		for (int i = 0; i < paths.length; i++) {
			props = new Properties();
			input = new FileInputStream(paths[i]);
			props.load(input);
			propertie = props.getProperty(parameter);
			return propertie;
		}
		return null;
	}

	public PropertiesFile() {
		// TODO Auto-generated constructor stub
	}

	public static String getArchivoP() {
		return archivoP;
	}

	public static void setArchivoP(String archivoP) {
		PropertiesFile.archivoP = archivoP;
	}

}
