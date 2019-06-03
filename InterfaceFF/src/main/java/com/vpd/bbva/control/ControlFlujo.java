package main.java.com.vpd.bbva.control;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.apache.log4j.Logger;

import main.java.com.vpd.bbva.conf.PropertiesFile;
import main.java.com.vpd.bbva.vista.ValidarArchivo;

/**
 * Clase Control de Flujo Servira para controlar las acciones a ejecutar del
 * proceso de Interface
 * 
 * @author XME0393
 *
 */
public class ControlFlujo {

	private static final Logger log = Logger.getLogger(ControlFlujo.class);

	public ControlFlujo() {
		// TODO Auto-generated constructor stub
	}

	public void Control() throws ParseException {

		log.info("Inicia Proceso de Control");
		ValidarArchivo procesarA = new ValidarArchivo();
		List<String> validacionArchivo = procesarA.ValidaArchivo();

		if (validacionArchivo != null && validacionArchivo.size() > 0) {
			log.info("Comienza Procesamiento de Archivos");
			int contA = 0;
			for (String archivo : validacionArchivo) {
				log.info("Archivo a procesar: \n" + archivo);
				ValidacionDatosCtrol validaD = new ValidacionDatosCtrol();
				PropertiesFile properties = new PropertiesFile();
				try {
					String rutaDirSBKP = properties.getPropertiesFile("archivos_salidabkp");
					String rutaDirBkpE = properties.getPropertiesFile("archivos_bkpentrada");
					String rutaDirS = properties.getPropertiesFile("archivos_salida");
					validaD.procesarDatos(archivo, rutaDirSBKP, rutaDirBkpE, rutaDirS);
					contA++;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			log.info("Total de archivos procesados:" + contA);
		} else {
			log.error("No hay archivos por procesar");
		}
	}
}
