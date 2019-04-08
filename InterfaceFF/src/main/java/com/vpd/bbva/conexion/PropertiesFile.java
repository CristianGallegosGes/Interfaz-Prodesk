package main.java.com.vpd.bbva.conexion;

import java.io.FileInputStream;
import java.util.Properties;


public class PropertiesFile {
	private static PropertiesFile INSTANCE = null;
	private Properties props			=	null;
	private static String fileP		= 	"sicofe.properties";
	private FileInputStream input	= 	null;
	
	private static String paths[]	=	new String[1]; 
	//private static String ruta 	=	"/rhsicofi/SICOFE/prop_jars/" ;  	/*PRODUCCION*/
	private static String ruta 		=	"C:/" 	;						/*DESARROLLO*/
	
	private PropertiesFile () {
		if(ruta.isEmpty()) {
			paths[0] = "C:/" + getFileP();
		}
		else {
			paths[0] = ruta + getFileP();
		}
		
		boolean isFileOK = false;
		for(int i=0; i < paths.length; i++) {
			try {
				props = new Properties();
				input = new FileInputStream(paths[i]);
				props.load(input);
				isFileOK = true;
				break;
			}catch (Exception e) {
				
			}
		}
	}
	
	
	
	public static PropertiesFile getInstance() throws Exception {
        if (INSTANCE == null) {
            createInstace();
        }
        return INSTANCE;
    }
	
		
	private synchronized static void createInstace() throws Exception{
        if (INSTANCE == null) {
            INSTANCE = new PropertiesFile();
        }
    }
	
	
    public String getParameter(String parameter) {
        String proper = props.getProperty(parameter);
        return proper;
    }
    
    
    public static String getFileP() {
        return fileP;
    }

    public static void setFileP(String aFileP) {
        fileP = aFileP;
    }
	
}
