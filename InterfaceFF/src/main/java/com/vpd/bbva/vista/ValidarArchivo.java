package main.java.com.vpd.bbva.vista;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import main.java.com.vpd.bbva.conf.PropertiesFile;

public class ValidarArchivo {

	private static final Logger log = Logger.getLogger(ValidarArchivo.class);
	private List<String> archivos;
	private List<String> archivosRecuperados;
	private List<String> archivosRecuperadosExt;
	private String rutaDirE;
	private String rutaDirS;
	private String rutaDirBkpE;
	private File ruta;

	public ValidarArchivo() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Metodo para validar los archivos existentes en la ruta
	 * 
	 * @return
	 */
	public List<String> ValidaArchivo() {

		log.info("Comienza Validacion de Archivos");

		try {
			PropertiesFile properties = new PropertiesFile();
			rutaDirE = properties.getPropertiesFile("archivos_entrada");
			rutaDirS = properties.getPropertiesFile("archivos_salida");
			rutaDirBkpE = properties.getPropertiesFile("archivos_bkpentrada");

			ruta = new File(rutaDirE);
			if (ruta.exists()) {
				archivosRecuperados = getArchivosR(rutaDirE);
				if (archivosRecuperados.size() >= 1) {
					archivosRecuperadosExt = new ArrayList<String>(archivosRecuperados.size());
					for (String archivo : archivosRecuperados) {
						File fi = new File(archivo);

						ValidacionesArchivo validacionAr = new ValidacionesArchivo();
						if (validacionAr.accept(fi, ".txt")) {
							String cadena = fi.toString();
							String nombreArchivo = cadena.substring(0, cadena.length() - 4);
							if (validacionAr.validaNomenclatura(nombreArchivo)) {
								log.info("Se valido correctamente la nomenclatura del archivo " + fi.getPath());
								if (validacionAr.validaCodificacion(rutaDirE, archivo, rutaDirS, rutaDirBkpE)) {
									archivosRecuperadosExt.add(rutaDirE + archivo);
								} else {
									validacionAr.EscribeError(rutaDirE, archivo, rutaDirS, rutaDirBkpE, "Cod");
									log.error("No se proceso el archivo: " + fi.getPath()
											+ " por su codificacion, ya que es incorrecta.");
								}
							} else {
								validacionAr.EscribeError(rutaDirE, archivo, rutaDirS, rutaDirBkpE, "Nom");
								log.error("No se proceso el archivo: " + fi.getPath()
										+ " por su nomenclatura, ya que es incorrecta.");
							}
						} else {
							validacionAr.EscribeError(rutaDirE, archivo, rutaDirS, rutaDirBkpE, "Ext");
							log.error("No se proceso el archivo: " + fi.getPath()
									+ " por su extension, ya que es incorrecta.");
						}
					}
				} else {
					log.error("No existen archivos para su valdiacion");
				}
			} else {
				log.error("No existe el directorio: " + rutaDirE + " para el proceso de los archivos.");
			}

		} catch (IOException e) {
			log.error("No se encontro la ruta definida para el archivo: " + "interfaz.properties");
		}

		log.info("Termina Validacion de Archivos");
		return archivosRecuperadosExt;

	}

	/**
	 * Metodo para recuperar los archivos de la ruta de entrada definida en el
	 * properties
	 * 
	 * @param ruta
	 * @return
	 */
	public List<String> getArchivosR(String ruta) {
		File dir = new File(ruta);
		String[] ficheros = dir.list();
		archivos = new ArrayList<String>(ficheros.length);

		for (String s : ficheros) {
			archivos.add(s);
		}
		return archivos;
	}

	public String validaCodificacion(String archivo) {
		File fi = new File(archivo);
		String entrada;
		String cadena = "";
		String valorR = "";

		BufferedReader br = null;

		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(fi), ("UTF-8")));
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			while ((entrada = br.readLine()) != null) {
				cadena = cadena + entrada;
				System.out.println(cadena);
			}
		} catch (IOException e) {
			e.printStackTrace();
			valorR = "Error";
		}
		return valorR;
	}

	public List<String> getArchivos() {
		return archivos;
	}

	public void setArchivos(List<String> archivos) {
		this.archivos = archivos;
	}

	public List<String> getArchivosRecuperados() {
		return archivosRecuperados;
	}

	public void setArchivosRecuperados(List<String> archivosRecuperados) {
		this.archivosRecuperados = archivosRecuperados;
	}

	public String getRutaDir() {
		return rutaDirE;
	}

	public void setRutaDir(String rutaDirE) {
		this.rutaDirE = rutaDirE;
	}

	public List<String> getArchivosRecuperadosExt() {
		return archivosRecuperadosExt;
	}

	public void setArchivosRecuperadosExt(List<String> archivosRecuperadosExt) {
		this.archivosRecuperadosExt = archivosRecuperadosExt;
	}
}
