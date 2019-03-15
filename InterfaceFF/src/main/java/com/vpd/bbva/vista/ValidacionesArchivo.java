package main.java.com.vpd.bbva.vista;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.mozilla.universalchardet.UniversalDetector;

/**
 * Clase para validar extension del archivo
 * 
 * @author XME0393
 *
 */
public class ValidacionesArchivo implements FilenameFilter {

	private static final Logger log = Logger.getLogger(ValidacionesArchivo.class);
	private FileWriter fw = null;

	@Override
	public boolean accept(File archivo, String extension) {
		log.info("Valida Extension de archivo: " + archivo);
		return archivo.getName().endsWith(extension);
	}

	public Boolean validaNomenclatura(String nombreA) {

		log.info("Valida Nomencaltura de archivo: " + nombreA);
		boolean validacionN = false;
		String fecha = obtenerFecha();

		String[] parts = fecha.split("-");
		String anio = parts[0];
		String mes = parts[1];
		String dia = parts[2];
		String hora = parts[3];

		String proyecto = nombreA.substring(0, 3);
		String proceso = nombreA.substring(3, 6);
		String anioA = nombreA.substring(6, 10);
		String mesA = nombreA.substring(10, 12);
		String diaA = nombreA.substring(12, 14);
		String horaA = nombreA.substring(14, 16);
		String numeroA = nombreA.substring(16, 18);

		if (nombreA.length() == 18) {
			if (proyecto.equals("VPD")) {
				if (proceso.equals("PDK")) {
					if (anioA.equals(anio)) {
						if (mesA.equals(mes)) {
							if (diaA.equals(dia)) {
								if (horaA.equals(hora)) {
									if (Integer.parseInt(numeroA) >= 0 || Integer.parseInt(numeroA) <= 99) {
										validacionN = true;
									}
								}
							}
						}
					}
				}
			}
		}

		return validacionN;
	}

	public boolean validaCodificacion(String rutaDir, String archivo, String rutaDirS, String rutaDirBkpE) {

		log.info("Valida codificacion de archivo: " + archivo);
		boolean codif = false;
		String encoding;

		try {
			
			final FileInputStream fis = new FileInputStream(rutaDir + archivo);
			final UniversalDetector detector = new UniversalDetector(null);
			handleData(fis, detector);
			encoding = getEncoding(detector);
			if (!(encoding.equals("UTF-8") || encoding.equals("UTF-16BE") || encoding.equals("UTF-32BE") || encoding.equals("UTF-32LE") || encoding.equals("UTF-16LE"))) {
				codif = true;
				log.info("El archivo \"" + archivo + "\" tiene codificación: UTF-8 sin BOM");
				detector.reset();
				fis.close();
			} else {
				detector.reset();
				fis.close();
				log.error("El archivo \"" + archivo + "\" tiene codificacion: " + encoding);
				//Mover archivo en caso de que sea codificacion diferente a UTF-8
				if(encoding.equals("UTF-16BE") || encoding.equals("UTF-32BE") || encoding.equals("UTF-32LE") || encoding.equals("UTF-16LE")) {
					// Renombrar el archivo para la salida
					File archivoF = new File(rutaDir + archivo);
					String nuevoNombreArc = "LOG" + archivo;
					archivoF.renameTo(new File(rutaDir + nuevoNombreArc));
					moverArchivo(rutaDir + nuevoNombreArc, rutaDirS + nuevoNombreArc);
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			return codif;
		}

		return codif;
	}

	public void EscribeError(String rutaDir, String nombre, String rutaDirS, String rutaDirBkpE, String tipo) {
		File archivoF = new File(rutaDir + nombre);
		BufferedWriter bw = null;
		
		try {
			if (archivoF.exists()) {
				
				copiarArchivo(rutaDir + nombre, rutaDirBkpE + nombre);

				fw = new FileWriter(archivoF.getAbsoluteFile(), true);
				bw = new BufferedWriter(fw);
				bw.newLine();
				if (tipo == "Ext") {
					bw.write("\nError al procesar el archivo, por su extension.");
					bw.close();
				} else if (tipo == "Nom") {
					bw.write("\nError al procesar el archivo, por la nomenclatura.");
					bw.close();
				} else {
					bw.write("\nError al procesar el archivo, por la codificacion.");
					bw.close();
				}

				// Renombrar el archivo para la salida
				String nuevoNombreArc = "LOG" + nombre;
				archivoF.renameTo(new File(rutaDir + nuevoNombreArc));

				moverArchivo(rutaDir + nuevoNombreArc, rutaDirS + nuevoNombreArc);
			} else {
				//archivoF.createNewFile();
				log.info("No existe el archivo " + rutaDir + nombre + ".");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// Cierra instancias de FileWriter y BufferedWriter
				if (bw != null)
					bw.close();
				if (fw != null)
					fw.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	public String obtenerFecha() {
		String fecha = null;
		Date objDate = new Date();
		String strDateFormat = "YYYY-MM-dd-kk";
		SimpleDateFormat objSDF = new SimpleDateFormat(strDateFormat);
		fecha = objSDF.format(objDate);
		return fecha;
	}

	private void handleData(FileInputStream fis, UniversalDetector detector) throws IOException {
		int nread;
		final byte[] buf = new byte[4096];
		while ((nread = fis.read(buf)) > 0 && !detector.isDone()) {
			detector.handleData(buf, 0, nread);
		}
		detector.dataEnd();
	}

	private String getEncoding(UniversalDetector detector) {
		if (detector.isDone()) {
			return detector.getDetectedCharset();
		}
		return "";
	}

	public void moverArchivo(String rutaDir, String rutaDirS) {
		
		Path FROM = Paths.get(rutaDir);
		Path TO = Paths.get(rutaDirS);

		CopyOption[] options = new CopyOption[] { StandardCopyOption.REPLACE_EXISTING};
		try {
			Files.move(FROM, TO, options);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void copiarArchivo(String rutaDir, String rutaDirS) {
		Path FROM = Paths.get(rutaDir);
		Path TO = Paths.get(rutaDirS);

		CopyOption[] options = new CopyOption[] { StandardCopyOption.REPLACE_EXISTING};
		try {
			Files.copy(FROM, TO, options);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
