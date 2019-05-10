package main.java.com.vpd.bbva.control;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import main.java.com.vpd.bbva.bean.BeanFF;
import main.java.com.vpd.bbva.bean.BeanRespuesta;

public class ValidacionDatosCtrol {

	private static final Logger log = Logger.getLogger(ValidacionDatosCtrol.class);
	public static final String UTF8_BOM = "\uFEFF";

	public ValidacionDatosCtrol() {
		// TODO Auto-generated constructor stub
	}

	public void procesarDatos(String archivo, String rutaArchivoSBKP, String rutaDirBkpE, String rutaDirS)
			throws FileNotFoundException, IOException {

		log.info("Leer archivo");
		FileInputStream file = new FileInputStream(archivo);
		BufferedReader br = new BufferedReader(new InputStreamReader(file, "UTF8"));
		FileReader fr = new FileReader(archivo);
		BufferedReader brTotalLineasS = new BufferedReader(fr);

		String cadena = null;
		boolean cartaGenerada = false;
		boolean firstLine = true;
		int noCartaGenerada = 0;
		int noFacturaGenerada = 0;
		int linea = 0;
		int totalRegistroCorrectos = 0;
		int totalRegistroError = 0;
		long lineasTotal = obtenerTotalLineas(fr, brTotalLineasS);

		int consecutivoArchivo = 0;

		List<String> cadenaControl = new ArrayList<String>();
		List<String> cadenasProcesar = new ArrayList<String>();
		List<Integer> cadenaLinea = new ArrayList<Integer>();
		List<String> lineasArchivo = new ArrayList<String>();
		List<String> validacionCadenas = new ArrayList<String>();

		HashMap<Integer, String> validarLongitudLineas = null;
		HashMap<Integer, String> validarDatosO = null;
		HashMap<Integer, String> validarDatosD = null;
		HashMap<Integer, String> validarTipoDato = null;
		HashMap<Integer, String> consecutivoMayor = null;

		String nombreArchivo = archivo.substring(archivo.length() - 24);
		// Renombrar el archivo para la salida
		String nuevoNombreArc = "LOG" + nombreArchivo;
		String aplicativoOrigen = nombreArchivo.substring(5, 8);

		while ((cadena = br.readLine()) != null) {

			String errorPosicionD = "";
			String errorDatosO = "";
			String errorDatosD = "";
			String errorTipoD = "";
			String errorConsecutivoM = "";

			if (firstLine) {
				cadena = removeUTF8BOM(cadena);
				firstLine = false;
			}

			linea++;
			// System.out.println("Linea" + linea.get(cont) + " " + cadena);

			if (linea == 1) {
				// Agregar cadena a lista control
				cadenaControl.add(cadena);
				// Agregar cadena a lista de procesamiento
				cadenasProcesar.add(cadena);
				// Agregar valor de linea actual posicion
				cadenaLinea.add(linea);
				// Agregar cadena a lista archivo
				lineasArchivo.add(cadena);
			} else {
				try {
				if (cadena.substring(0, 10).trim().equals(lineasArchivo.get(linea - 2).substring(0, 10).trim())
						&& cadena.substring(30, 34).trim()
								.equals(lineasArchivo.get(linea - 2).substring(30, 34).trim())) {

					// Agregar cadena a lista control
					cadenaControl.add(cadena);
					// Agregar cadena a lista de procesamiento
					cadenasProcesar.add(cadena);
					// Agregar valor de linea actual posicion
					cadenaLinea.add(linea);
					// Agregar cadena a lista archivo
					lineasArchivo.add(cadena);

				} else if (cadena.substring(0, 10).trim().equals(lineasArchivo.get(linea - 2).substring(0, 10).trim())
						&& !cadena.substring(30, 34).trim()
								.equals(lineasArchivo.get(linea - 2).substring(30, 34).trim())) {

					// Agregar cadena a lista control
					cadenaControl.add(cadena);
					// Agregar cadena a lista de procesamiento
					cadenasProcesar.add(cadena);
					// Agregar valor de linea actual posicion
					cadenaLinea.add(linea);
					// Agregar cadena a lista archivo
					lineasArchivo.add(cadena);

				} else if (cadena.substring(0, 10).trim() != lineasArchivo.get(linea - 2).substring(0, 10).trim()) {
					if (cadenaControl.size() > 0) {
						for (String cadenaP : cadenasProcesar) {
							validacionCadenas.add(cadenaP);
						}

						// Validar Longuitud de Cadena
						validarLongitudLineas = validarLongitudLinea(validacionCadenas, cadenaLinea);
						if (validarLongitudLineas.size() == 0) {
							// Validar Datos Obligatorios
							validarDatosO = validarDatosObligatorios(validacionCadenas, cadenaLinea);
							if (validarDatosO.size() == 0) {
								// Validar Datos Dependientes
								validarDatosD = validarDatosDependientes(validacionCadenas, cadenaLinea);
								if (validarDatosD.size() == 0) {
									// Validar Tipo de Dato
									validarTipoDato = validarTiposDato(validacionCadenas, cadenaLinea);
									if (validarTipoDato.size() == 0) {
										// Validar que el valor de consecutivo de archivo sea mayor al consecutvo de
										// archivo anterior
										consecutivoMayor = validaConsecutivoMayor(cadena, consecutivoArchivo,
												validacionCadenas, cadenaLinea);
										if (consecutivoMayor.size() == 0) {
											// Agregar a Objeto BeanFF las lineas que se enviaran al momento de inovcar
											// el memtodo de BD
											AgregarDatosBeanFF1 agregarBeanFF = new AgregarDatosBeanFF1();
											ArrayList<BeanFF> listaBeanFF = agregarBeanFF.setCadenas(cadenasProcesar);
											// Validar datos de carta de este bloque, que sean iguales
											ValidaDatosCartaCtrol validaDatosC = new ValidaDatosCartaCtrol();
											HashMap<Integer, String> validacionDatosCart = null;
											String errorDatosCart = "";
											validacionDatosCart = validaDatosC.validaDatosCartaConsecutiva(listaBeanFF,
													cadenaLinea, aplicativoOrigen);
											if (validacionDatosCart.size() == 0) {

												// Invocar el metodo de BD enviando la lista "listaBeanFF" como
												// parametro
												ControlBloqueaDB controlProcesoBD = new ControlBloqueaDB();
												try {

													if (Integer.parseInt(lineasArchivo.get(lineasArchivo.size() - 2)
															.substring(0, 10).trim()) == listaBeanFF.get(0)
																	.getConsecArch()) {

														int conta = 0;
														int contaa = 0;
														ArrayList<BeanFF> listaBeanN = new ArrayList<BeanFF>();
														ArrayList<BeanFF> listaBeanFFIAN = new ArrayList<BeanFF>();
														ArrayList<BeanFF> listaBeanFFDAN = new ArrayList<BeanFF>();
														ArrayList<String> cadenasProcesarBean = new ArrayList<String>();
														ArrayList<Integer> lineasProcesar = new ArrayList<Integer>();
														int totalListaBeanFF = listaBeanFF.size();
														for (int i = 1; i <= listaBeanFF.size(); i++) {
															contaa++;

															if (i == 1) {
																listaBeanN.add(listaBeanFF.get(conta));
																cadenasProcesarBean.add(cadenasProcesar.get(conta));
																lineasProcesar.add(cadenaLinea.get(conta));
															} else {
																if (listaBeanFF.get(conta)
																		.getConsecNota() == listaBeanFF.get(i - 2)
																				.getConsecNota()) {
																	if(conta == 1) {
																		listaBeanFFIAN.addAll(listaBeanN);
																	}
																	listaBeanFFIAN.add(listaBeanFF.get(conta));
																	cadenasProcesarBean.add(cadenasProcesar.get(conta));
																	lineasProcesar.add(cadenaLinea.get(conta));
																	if (i == totalListaBeanFF) {
																		listaBeanFFDAN.clear();
																	}
																	if (listaBeanFFDAN.size() > 0) {

																		ValidaDatosFacturaCtrol validaDatosF = new ValidaDatosFacturaCtrol();
																		HashMap<Integer, String> validacionDatosFac = null;
																		String errorDatosFac = "";
																		validacionDatosFac = validaDatosF
																				.validaDatosFacturaConsecutiva(
																						listaBeanFFDAN, lineasProcesar);
																		if (validacionDatosFac.size() == 0) {
																			// Validar el valor de la variable
																			// cartaGenerada
																			// que sea distinto a 0,
																			// ya que eso determina si ya se genero una
																			// carta con el mismo
																			// consecutivo
																			if (noCartaGenerada > 0) {
																				cartaGenerada = true;
																			}

																			List<BeanRespuesta> ctrolBD = controlProcesoBD
																					.BloqueaDB(listaBeanFFDAN, cartaGenerada);
																			boolean banderaCtrolBD = ctrolBD.get(0).GetBandera();

																			if (banderaCtrolBD) {

																				if (noCartaGenerada == 0) {
																					noCartaGenerada = ctrolBD.get(0).getCarta();
																				}
																				noFacturaGenerada = ctrolBD.get(0).getFactura();

																				for (String cadenaP : cadenasProcesarBean) {
																					escribirArchivoCorrecto(
																							rutaArchivoSBKP
																									+ nuevoNombreArc,
																							cadenaP, noCartaGenerada,
																							noFacturaGenerada);
																					totalRegistroCorrectos++;
																				}
																				
																				consecutivoArchivo = Integer
																						.parseInt(cadenasProcesarBean.get(0)
																								.substring(0, 10).trim());

																			} else {

																				// En caso de que la bandera sea false
																				// se da
																				// por
																				// hecho // que no
																				// se proeco correctamente los registros
																				// de
																				// la
																				// lista enviada y se
																				// debe de imprimir el
																				// error a todas las lineas enviadas.
																				String mensajeError = ctrolBD.get(0)
																						.getMensaje();
																				int lineaError = ctrolBD.get(0)
																						.getConsecutivoA();
																				for (String cadenaP : cadenasProcesarBean) {

																					escribirArchivoError(
																							rutaArchivoSBKP
																									+ nuevoNombreArc,
																							cadenaP,
																							"[ERROR EN LA LINEA"
																									+ lineasProcesar.get(
																											lineaError)
																									+ " CONSECUTIVO "
																									+ cadenasProcesarBean
																											.get(lineaError)
																											.substring(
																													0,
																													10)
																											.trim()
																									+ "/" +cadenasProcesarBean
																											.get(lineaError)
																											.substring(
																													30,
																													34)
																											.trim()
																									+ " \""
																									+ mensajeError
																									+ "\"]");
																					totalRegistroError++;
																				}
																				noCartaGenerada = 0;
																			}
																		} else {

																			for (Integer j : validacionDatosFac
																					.keySet()) {
																				errorDatosFac = validacionDatosFac
																						.values().toString();
																				log.error(
																						"Identificador de error(Datos Factura Consecutivo):"
																								+ j
																								+ " Descripcion de error: "
																								+ validacionDatosFac
																										.values()
																								+ " en la linea "
																								+ errorDatosFac.substring(15, 16));
																			}

																			for (String cadenaP : cadenasProcesarBean) {
																				escribirArchivoError(
																						rutaArchivoSBKP
																								+ nuevoNombreArc,
																						cadenaP, errorDatosFac);
																				totalRegistroError++;
																			}

																			/*if (Integer.parseInt((cadena
																					.substring(0, 10).trim().equals(""))
																							? "0"
																							: cadena.substring(0, 10)
																									.trim()) > consecutivoArchivo) {
																				// Agregar numero de archivo consecutivo
																				consecutivoArchivo = Integer
																						.parseInt(validacionCadenas
																								.get(0).substring(0, 10)
																								.trim());
																			}*/
																		}

																		listaBeanFFDAN.clear();
																		listaBeanN.clear();
																		cadenasProcesarBean.clear();
																		lineasProcesar.clear();
																	}
																} else if (listaBeanFF.get(conta)
																		.getConsecNota() != listaBeanFF.get(i - 2)
																				.getConsecNota()) {

																	if (listaBeanFFIAN.size() > 0) {

																		ValidaDatosFacturaCtrol validaDatosF = new ValidaDatosFacturaCtrol();
																		HashMap<Integer, String> validacionDatosFac = null;
																		String errorDatosFac = "";
																		validacionDatosFac = validaDatosF
																				.validaDatosFacturaConsecutiva(
																						listaBeanFFIAN, lineasProcesar);
																		if (validacionDatosFac.size() == 0) {

																			// Validar el valor de la variable
																			// cartaGenerada
																			// que sea distinto a 0,
																			// ya que eso determina si ya se genero una
																			// carta con el mismo
																			// consecutivo
																			if (noCartaGenerada > 0) {
																				cartaGenerada = true;
																			}

																			List<BeanRespuesta> ctrolBD = controlProcesoBD
																					.BloqueaDB(listaBeanFFIAN, cartaGenerada);
																			boolean banderaCtrolBD =  ctrolBD.get(0).GetBandera();

																			if (banderaCtrolBD) {

																				if (noCartaGenerada == 0) {
																					noCartaGenerada = ctrolBD.get(0).getCarta();
																				}
																				noFacturaGenerada = ctrolBD.get(0).getFactura();

																				for (String cadenaP : cadenasProcesarBean) {

																					escribirArchivoCorrecto(
																							rutaArchivoSBKP
																									+ nuevoNombreArc,
																							cadenaP, noCartaGenerada,
																							noFacturaGenerada);
																					totalRegistroCorrectos++;
																				}
																				
																				consecutivoArchivo = Integer
																						.parseInt(cadenasProcesarBean.get(0)
																								.substring(0, 10).trim());

																			} else {

																				// En caso de que la bandera sea false
																				// se da
																				// por
																				// hecho // que no
																				// se proeco correctamente los registros
																				// de
																				// la
																				// lista enviada y se
																				// debe de imprimir el
																				// error a todas las lineas enviadas.
																				String mensajeError = ctrolBD.get(0)
																						.getMensaje();
																				int lineaError = ctrolBD.get(0)
																						.getConsecutivoA();
																				for (String cadenaP : cadenasProcesarBean) {

																					escribirArchivoError(
																							rutaArchivoSBKP
																									+ nuevoNombreArc,
																							cadenaP,
																							"[ERROR EN LA LINEA"
																									+ lineasProcesar.get(
																											lineaError)
																									+ " CONSECUTIVO "
																									+ cadenasProcesarBean
																											.get(lineaError)
																											.substring(
																													0,
																													10)
																											.trim()
																									+ cadenasProcesarBean
																											.get(lineaError)
																											.substring(
																													30,
																													34)
																											.trim()
																									+ " \""
																									+ mensajeError
																									+ "\"]");
																					totalRegistroError++;
																				}

																				noCartaGenerada = 0;
																			}
																		} else {

																			for (Integer j : validacionDatosFac
																					.keySet()) {
																				errorDatosFac = validacionDatosFac
																						.values().toString();
																				log.error(
																						"Identificador de error(Datos Factura Consecutivo):"
																								+ j
																								+ " Descripcion de error: "
																								+ validacionDatosFac
																										.values()
																								+ " en la linea "
																								+ errorDatosFac.substring(15, 16));
																			}

																			for (String cadenaP : cadenasProcesarBean) {
																				escribirArchivoError(
																						rutaArchivoSBKP
																								+ nuevoNombreArc,
																						cadenaP, errorDatosFac);
																				totalRegistroError++;
																			}

																			/*if (Integer.parseInt((cadena
																					.substring(0, 10).trim().equals(""))
																							? "0"
																							: cadena.substring(0, 10)
																									.trim()) > consecutivoArchivo) {
																				// Agregar numero de archivo consecutivo
																				consecutivoArchivo = Integer
																						.parseInt(validacionCadenas
																								.get(0).substring(0, 10)
																								.trim());
																			}*/

																		}

																		listaBeanFFIAN.clear();
																		listaBeanN.clear();
																		cadenasProcesarBean.clear();
																		lineasProcesar.clear();
																	} else if (listaBeanFFDAN.size() > 0) {

																		ValidaDatosFacturaCtrol validaDatosF = new ValidaDatosFacturaCtrol();
																		HashMap<Integer, String> validacionDatosFac = null;
																		String errorDatosFac = "";
																		validacionDatosFac = validaDatosF
																				.validaDatosFacturaConsecutiva(
																						listaBeanFFDAN, lineasProcesar);
																		if (validacionDatosFac.size() == 0) {

																			// Validar el valor de la variable
																			// cartaGenerada
																			// que sea distinto a 0,
																			// ya que eso determina si ya se genero una
																			// carta con el mismo
																			// consecutivo
																			if (noCartaGenerada > 0) {
																				cartaGenerada = true;
																			}

																			List<BeanRespuesta> ctrolBD = controlProcesoBD
																					.BloqueaDB(listaBeanFFDAN, cartaGenerada);
																			boolean banderaCtrolBD = ctrolBD.get(0).GetBandera();

																			if (banderaCtrolBD) {

																				if (noCartaGenerada == 0) {
																					noCartaGenerada = ctrolBD.get(0).getCarta();
																				}
																				noFacturaGenerada = ctrolBD.get(0).getFactura();

																				for (String cadenaP : cadenasProcesarBean) {

																					escribirArchivoCorrecto(
																							rutaArchivoSBKP
																									+ nuevoNombreArc,
																							cadenaP, noCartaGenerada,
																							noFacturaGenerada);
																					totalRegistroCorrectos++;
																				}
																				
																				consecutivoArchivo = Integer
																						.parseInt(cadenasProcesarBean.get(0)
																								.substring(0, 10).trim());

																			} else {

																				// En caso de que la bandera sea false
																				// se da
																				// por
																				// hecho // que no
																				// se proeco correctamente los registros
																				// de
																				// la
																				// lista enviada y se
																				// debe de imprimir el
																				// error a todas las lineas enviadas.
																				String mensajeError = ctrolBD.get(0)
																						.getMensaje();
																				int lineaError = ctrolBD.get(0)
																						.getConsecutivoA();
																				for (String cadenaP : cadenasProcesarBean) {

																					escribirArchivoError(
																							rutaArchivoSBKP
																									+ nuevoNombreArc,
																							cadenaP,
																							"[ERROR EN LA LINEA"
																									+ lineasProcesar.get(
																											lineaError)
																									+ " CONSECUTIVO "
																									+ cadenasProcesarBean
																											.get(lineaError)
																											.substring(
																													0,
																													10)
																											.trim()
																									+ "/" +cadenasProcesarBean
																											.get(lineaError)
																											.substring(
																													30,
																													34)
																											.trim()
																									+ " \""
																									+ mensajeError
																									+ "\"]");
																					totalRegistroError++;
																				}

																				noCartaGenerada = 0;
																			}

																		} else {

																			for (Integer j : validacionDatosFac
																					.keySet()) {
																				errorDatosFac = validacionDatosFac
																						.values().toString();
																				log.error(
																						"Identificador de error(Datos Factura Consecutivo):"
																								+ j
																								+ " Descripcion de error: "
																								+ validacionDatosFac
																										.values()
																								+ " en la linea "
																								+ errorDatosFac.substring(15, 16));
																			}

																			for (String cadenaP : cadenasProcesarBean) {
																				escribirArchivoError(
																						rutaArchivoSBKP
																								+ nuevoNombreArc,
																						cadenaP, errorDatosFac);
																				totalRegistroError++;
																			}

																			/*if (Integer.parseInt((cadena
																					.substring(0, 10).trim().equals(""))
																							? "0"
																							: cadena.substring(0, 10)
																									.trim()) > consecutivoArchivo) {
																				// Agregar numero de archivo consecutivo
																				consecutivoArchivo = Integer
																						.parseInt(validacionCadenas
																								.get(0).substring(0, 10)
																								.trim());
																			}*/

																		}

																		listaBeanFFDAN.clear();
																		listaBeanN.clear();
																		cadenasProcesarBean.clear();
																		lineasProcesar.clear();
																	}
																	
																	if(conta == 1) {
																		listaBeanFFDAN.addAll(listaBeanN);
																	}
																	listaBeanFFDAN.add(listaBeanFF.get(conta));
																	cadenasProcesarBean.add(cadenasProcesar.get(conta));
																	lineasProcesar.add(cadenaLinea.get(conta));
																}
															}

															if (totalListaBeanFF == contaa) {
																if (listaBeanFFDAN.size() > 0) {

																	ValidaDatosFacturaCtrol validaDatosF = new ValidaDatosFacturaCtrol();
																	HashMap<Integer, String> validacionDatosFac = null;
																	String errorDatosFac = "";
																	validacionDatosFac = validaDatosF
																			.validaDatosFacturaConsecutiva(
																					listaBeanFFDAN, lineasProcesar);
																	if (validacionDatosFac.size() == 0) {

																		// Validar el valor de la variable cartaGenerada
																		// que
																		// sea distinto a 0,
																		// ya que eso determina si ya se genero una
																		// carta
																		// con el mismo
																		// consecutivo
																		if (noCartaGenerada > 0) {
																			cartaGenerada = true;
																		}

																		List<BeanRespuesta> ctrolBD = controlProcesoBD
																				.BloqueaDB(listaBeanFFDAN, cartaGenerada);
																		boolean banderaCtrolBD = ctrolBD.get(0).GetBandera();

																		if (banderaCtrolBD) {
																			if (noCartaGenerada == 0) {
																				noCartaGenerada = ctrolBD.get(0).getCarta();
																			}
																			noFacturaGenerada = ctrolBD.get(0).getFactura();

																			for (String cadenaP : cadenasProcesarBean) {

																				escribirArchivoCorrecto(
																						rutaArchivoSBKP
																								+ nuevoNombreArc,
																						cadenaP, noCartaGenerada,
																						noFacturaGenerada);
																				totalRegistroCorrectos++;
																			}
																			
																			consecutivoArchivo = Integer
																					.parseInt(cadenasProcesarBean.get(0)
																							.substring(0, 10).trim());

																		} else {

																			// En caso de que la bandera sea false se da
																			// por
																			// hecho // que no
																			// se proeco correctamente los registros de
																			// la
																			// lista enviada y se
																			// debe de imprimir el
																			// error a todas las lineas enviadas.
																			String mensajeError = ctrolBD.get(0)
																					.getMensaje();
																			int lineaError = ctrolBD.get(0)
																					.getConsecutivoA();
																			for (String cadenaP : cadenasProcesarBean) {

																				escribirArchivoError(
																						rutaArchivoSBKP
																								+ nuevoNombreArc,
																						cadenaP,
																						"[ERROR EN LA LINEA"
																								+ lineasProcesar
																										.get(lineaError)
																								+ " CONSECUTIVO "
																								+ cadenasProcesarBean
																										.get(lineaError)
																										.substring(0,
																												10)
																										.trim()
																								+ "/" +cadenasProcesarBean
																										.get(lineaError)
																										.substring(30,
																												34)
																										.trim()
																								+ " \"" + mensajeError
																								+ "\"]");
																				totalRegistroError++;
																			}
																			noCartaGenerada = 0;
																		}

																	} else {

																		for (Integer j : validacionDatosFac.keySet()) {
																			errorDatosFac = validacionDatosFac.values()
																					.toString();
																			log.error(
																					"Identificador de error(Datos Factura Consecutivo):"
																							+ j
																							+ " Descripcion de error: "
																							+ validacionDatosFac
																									.values()
																							+ " en la linea " + errorDatosFac.substring(15, 16));
																		}

																		for (String cadenaP : cadenasProcesarBean) {
																			escribirArchivoError(
																					rutaArchivoSBKP + nuevoNombreArc,
																					cadenaP, errorDatosFac);
																			totalRegistroError++;
																		}

																		/*if (Integer.parseInt((cadena.substring(0, 10)
																				.trim().equals("")) ? "0"
																						: cadena.substring(0, 10)
																								.trim()) > consecutivoArchivo) {
																			// Agregar numero de archivo consecutivo
																			consecutivoArchivo = Integer
																					.parseInt(validacionCadenas.get(0)
																							.substring(0, 10).trim());
																		}*/

																	}

																	listaBeanFFDAN.clear();
																	listaBeanN.clear();
																	cadenasProcesarBean.clear();
																	lineasProcesar.clear();

																} else if (listaBeanFFIAN.size() > 0) {

																	ValidaDatosFacturaCtrol validaDatosF = new ValidaDatosFacturaCtrol();
																	HashMap<Integer, String> validacionDatosFac = null;
																	String errorDatosFac = "";
																	validacionDatosFac = validaDatosF
																			.validaDatosFacturaConsecutiva(
																					listaBeanFFIAN, lineasProcesar);
																	if (validacionDatosFac.size() == 0) {

																		// Validar el valor de la variable cartaGenerada
																		// que
																		// sea distinto a 0,
																		// ya que eso determina si ya se genero una
																		// carta
																		// con el mismo
																		// consecutivo
																		if (noCartaGenerada > 0) {
																			cartaGenerada = true;
																		}

																		List<BeanRespuesta> ctrolBD = controlProcesoBD
																				.BloqueaDB(listaBeanFFIAN, cartaGenerada);
																		boolean banderaCtrolBD = ctrolBD.get(0).GetBandera();

																		if (banderaCtrolBD) {

																			if (noCartaGenerada == 0) {
																				noCartaGenerada = ctrolBD.get(0).getCarta();
																			}
																			noFacturaGenerada = ctrolBD.get(0).getFactura();

																			for (String cadenaP : cadenasProcesarBean) {

																				escribirArchivoCorrecto(
																						rutaArchivoSBKP
																								+ nuevoNombreArc,
																						cadenaP, noCartaGenerada,
																						noFacturaGenerada);
																				totalRegistroCorrectos++;
																			}
																			
																			consecutivoArchivo = Integer
																					.parseInt(cadenasProcesarBean.get(0)
																							.substring(0, 10).trim());
																		} else {

																			// En caso de que la bandera sea false se da
																			// por
																			// hecho // que no
																			// se proeco correctamente los registros de
																			// la
																			// lista enviada y se
																			// debe de imprimir el
																			// error a todas las lineas enviadas.
																			String mensajeError = ctrolBD.get(0)
																					.getMensaje();
																			int lineaError = ctrolBD.get(0)
																					.getConsecutivoA();
																			for (String cadenaP : cadenasProcesarBean) {

																				escribirArchivoError(
																						rutaArchivoSBKP
																								+ nuevoNombreArc,
																						cadenaP,
																						"[ERROR EN LA LINEA"
																								+ lineasProcesar
																										.get(lineaError)
																								+ " CONSECUTIVO "
																								+ cadenasProcesarBean
																										.get(lineaError)
																										.substring(0,
																												10)
																										.trim()
																								+ "/" +cadenasProcesarBean
																										.get(lineaError)
																										.substring(30,
																												34)
																										.trim()
																								+ " \"" + mensajeError
																								+ "\"]");
																				totalRegistroError++;
																			}
																			noCartaGenerada = 0;
																		}
																	} else {

																		for (Integer j : validacionDatosFac.keySet()) {
																			errorDatosFac = validacionDatosFac.values()
																					.toString();
																			log.error(
																					"Identificador de error(Datos Factura Consecutivo):"
																							+ j
																							+ " Descripcion de error: "
																							+ validacionDatosFac
																									.values()
																							+ " en la linea " + errorDatosFac.substring(15, 16));

																		}

																		for (String cadenaP : cadenasProcesarBean) {
																			escribirArchivoError(
																					rutaArchivoSBKP + nuevoNombreArc,
																					cadenaP, errorDatosFac);
																			totalRegistroError++;
																		}

																		/*if (Integer.parseInt((cadena.substring(0, 10)
																				.trim().equals("")) ? "0"
																						: cadena.substring(0, 10)
																								.trim()) > consecutivoArchivo) {
																			// Agregar numero de archivo consecutivo
																			consecutivoArchivo = Integer
																					.parseInt(validacionCadenas.get(0)
																							.substring(0, 10).trim());
																		}*/

																	}
																	listaBeanFFIAN.clear();
																	listaBeanN.clear();
																	cadenasProcesarBean.clear();
																	lineasProcesar.clear();
																}
															}
															conta++;
														}
													} else {
														
														// Inicializar la variable noCartaGenerada a 0 , ya que cambio el consetucivo de la carta
														noCartaGenerada = 0;
														ValidaDatosFacturaCtrol validaDatosF = new ValidaDatosFacturaCtrol();
														HashMap<Integer, String> validacionDatosFac = null;
														String errorDatosFac = "";
														validacionDatosFac = validaDatosF.validaDatosFacturaConsecutiva(
																listaBeanFF, cadenaLinea);
														if (validacionDatosFac.size() == 0) {
															List<BeanRespuesta> ctrolBD = controlProcesoBD
																	.BloqueaDB(listaBeanFF, cartaGenerada);
															boolean banderaCtrolBD = ctrolBD.get(0).GetBandera();

															if (banderaCtrolBD) {
																noCartaGenerada = ctrolBD.get(0).getCarta();
																noFacturaGenerada = ctrolBD.get(0).getFactura();

																for (String cadenaP : cadenasProcesar) {

																	escribirArchivoCorrecto(
																			rutaArchivoSBKP + nuevoNombreArc, cadenaP,
																			noCartaGenerada, noFacturaGenerada);
																	totalRegistroCorrectos++;
																}

																noCartaGenerada = 0;
																
																consecutivoArchivo = Integer.parseInt(cadenasProcesar.get(0).substring(0, 10).trim());

															} else {

																// En caso de que la bandera sea false se da por hecho
																// //
																// que no
																// se proeco correctamente los registros de la lista
																// enviada
																// y se
																// debe de imprimir el
																// error a todas las lineas enviadas.
																String mensajeError = ctrolBD.get(0).getMensaje();
																int lineaError = ctrolBD.get(0).getConsecutivoA();
																for (String cadenaP : cadenasProcesar) {

																	escribirArchivoError(
																			rutaArchivoSBKP + nuevoNombreArc, cadenaP,
																			"[ERROR EN LA LINEA"
																					+ cadenaLinea.get(lineaError)
																					+ " CONSECUTIVO "
																					+ cadenasProcesar.get(lineaError)
																							.substring(0, 10).trim()
																					+ "/" +cadenasProcesar.get(lineaError)
																							.substring(30, 34).trim()
																					+ " \"" + mensajeError + "\"]");
																	totalRegistroError++;
																}

																noCartaGenerada = 0;
															}
														} else {

															for (Integer j : validacionDatosFac.keySet()) {
																errorDatosFac = validacionDatosFac.values().toString();
																log.error(
																		"Identificador de error(Datos Factura Consecutivo):"
																				+ j + " Descripcion de error: "
																				+ validacionDatosFac.values()
																				+ " en la linea " + linea);
															}

															for (String cadenaP : cadenasProcesar) {
																escribirArchivoError(rutaArchivoSBKP + nuevoNombreArc,
																		cadenaP, errorDatosFac);
																totalRegistroError++;
															}

															/*if (Integer.parseInt(
																	(cadena.substring(0, 10).trim().equals("")) ? "0"
																			: cadena.substring(0, 10)
																					.trim()) > consecutivoArchivo) {
																// Agregar numero de archivo consecutivo
																consecutivoArchivo = Integer.parseInt(validacionCadenas
																		.get(0).substring(0, 10).trim());
															}*/
														}
													}

													/*if (Integer.parseInt((cadena.substring(0, 10).trim().equals(""))
															? "0"
															: cadena.substring(0, 10).trim()) > consecutivoArchivo) {
														// Agregar numero de archivo consecutivo
														consecutivoArchivo = Integer.parseInt(
																validacionCadenas.get(0).substring(0, 10).trim());
													}*/

												} catch (Exception e) {
													e.printStackTrace();
													log.error(e.getMessage());
												}

											} else {
												for (Integer j : validacionDatosCart.keySet()) {
													errorDatosCart = validacionDatosCart.values().toString();
													log.error("Identificador de error(Datos Carta Consecutivo):" + j
															+ " Descripcion de error: " + validacionDatosCart.values()
															+ " en la linea " + errorDatosCart.subSequence(15, 16));
													for (String cadenaP : cadenasProcesar) {
														escribirArchivoError(rutaArchivoSBKP + nuevoNombreArc, cadenaP,
																errorDatosCart);
														totalRegistroError++;
													}
												}

												/*if (Integer.parseInt((cadena.substring(0, 10).trim().equals("")) ? "0"
														: cadena.substring(0, 10).trim()) > consecutivoArchivo) {
													// Agregar numero de archivo consecutivo
													consecutivoArchivo = Integer
															.parseInt(validacionCadenas.get(0).substring(0, 10).trim());
												}*/
											}

											// Eliminar datos de la lista listaBeanFF
											listaBeanFF.clear();
										} else {
											for (Integer j : consecutivoMayor.keySet()) {
												errorConsecutivoM = consecutivoMayor.values().toString();
												for (String cadenaP : cadenasProcesar) {
													log.error("Identificador de error(Tipo Dato):" + j
															+ " Descripcion de error: " + consecutivoMayor.values()
															+ " en la linea " + linea);
													escribirArchivoError(rutaArchivoSBKP + nuevoNombreArc, cadenaP,
															errorConsecutivoM);
													totalRegistroError++;
												}
											}

											/*if (Integer.parseInt((cadena.substring(0, 10).trim().equals("")) ? "0"
													: cadena.substring(0, 10).trim()) > consecutivoArchivo) {
												// Agregar numero de archivo consecutivo
												consecutivoArchivo = Integer
														.parseInt(validacionCadenas.get(0).substring(0, 10).trim());
											}*/
										}
									} else {
										for (Integer j : validarTipoDato.keySet()) {
											errorTipoD = validarTipoDato.values().toString();
											for (String cadenaP : cadenasProcesar) {
												log.error("Identificador de error(Tipo Dato):" + j
														+ " Descripcion de error: " + validarTipoDato.values()
														+ " en la linea " + linea);
												escribirArchivoError(rutaArchivoSBKP + nuevoNombreArc, cadenaP,
														errorTipoD);
												totalRegistroError++;
											}
										}

										/*if (Integer.parseInt((cadena.substring(0, 10).trim().equals("")) ? "0"
												: cadena.substring(0, 10).trim()) > consecutivoArchivo) {
											// Agregar numero de archivo consecutivo
											consecutivoArchivo = Integer
													.parseInt(validacionCadenas.get(0).substring(0, 10).trim());
										}*/
									}
								} else {
									for (Integer j : validarDatosD.keySet()) {
										errorDatosD = validarDatosD.values().toString();
										for (String cadenaP : cadenasProcesar) {
											log.error("Identificador de error(Dependencia Campos):" + j
													+ " Descripcion de error: " + validarDatosD.values()
													+ " en la linea " + linea);
											escribirArchivoError(rutaArchivoSBKP + nuevoNombreArc, cadenaP,
													errorDatosD);
											totalRegistroError++;
										}
									}

									/*if (Integer.parseInt((cadena.substring(0, 10).trim().equals("")) ? "0"
											: cadena.substring(0, 10).trim()) > consecutivoArchivo) {
										// Agregar numero de archivo consecutivo
										consecutivoArchivo = Integer
												.parseInt(validacionCadenas.get(0).substring(0, 10).trim());
									}*/
								}
							} else {
								for (Integer j : validarDatosO.keySet()) {
									errorDatosO = validarDatosO.values().toString();
									for (String cadenaP : cadenasProcesar) {
										log.error("Identificador de error(Datos Obligatorios):" + j
												+ " Descripcion de error: " + validarDatosO.values() + " en la linea "
												+ linea);
										escribirArchivoError(rutaArchivoSBKP + nuevoNombreArc, cadenaP, errorDatosO);
										totalRegistroError++;
									}
								}

								/*if (Integer.parseInt(cadena.substring(0, 10).trim()) > consecutivoArchivo) {
									// Agregar numero de archivo consecutivo
									consecutivoArchivo = Integer
											.parseInt(validacionCadenas.get(0).substring(0, 10).trim());
								}*/
							}
						} else {
							for (Integer j : validarLongitudLineas.keySet()) {
								errorPosicionD = validarLongitudLineas.values().toString();
								for (String cadenaP : cadenasProcesar) {
									log.error("Identificador de error(Datos Obligatorios):" + j
											+ " Descripcion de error: " + validarLongitudLineas.values()
											+ " en la linea " + linea);
									escribirArchivoError(rutaArchivoSBKP + nuevoNombreArc, cadenaP, errorPosicionD);
									totalRegistroError++;
								}
							}
						}

						System.out.println("Se hace peticion de esta linea o lineas ^------");
					}

					// Eliminar datos de la lista control
					cadenaControl.clear();
					// Eliminar datos de la lista proceso
					cadenasProcesar.clear();
					// Agregar cadena a lista control
					cadenaControl.add(cadena);
					// Agregar cadena a lista de procesamiento
					cadenasProcesar.add(cadena);
					// Eliminar datos de lista lineas
					cadenaLinea.clear();
					// Agregar valor de linea actual posicion
					cadenaLinea.add(linea);
					// Agregar cadena a lista archivo
					lineasArchivo.add(cadena);
					// Eliminar datos de la lista validacionCadenas
					validacionCadenas.clear();
				}

				if (linea == lineasTotal) {
					if ((cadena.substring(0, 10).trim().equals(lineasArchivo.get(linea - 2).substring(0, 10).trim())
							&& cadena.substring(30, 34).trim()
									.equals(lineasArchivo.get(linea - 2).substring(30, 34).trim()))
							|| (cadena.substring(0, 10).trim()
									.equals(lineasArchivo.get(linea - 2).substring(0, 10).trim())
									&& !cadena.substring(30, 34).trim()
											.equals(lineasArchivo.get(linea - 2).substring(30, 34).trim()))) {
						if (cadenaControl.size() > 0) {
							for (String cadenaP : cadenasProcesar) {
								validacionCadenas.add(cadenaP);
							}

							// Validar Longuitud de Cadena
							validarLongitudLineas = validarLongitudLinea(validacionCadenas, cadenaLinea);
							if (validarLongitudLineas.size() == 0) {
								// Validar Datos Obligatorios
								validarDatosO = validarDatosObligatorios(validacionCadenas, cadenaLinea);
								if (validarDatosO.size() == 0) {
									// Agregar numero de archivo consetuvio
									// consecutivoArchivo = Integer
									// .parseInt(validacionCadenas.get(0).substring(0, 10).trim());
									// Validar Datos Dependientes
									validarDatosD = validarDatosDependientes(validacionCadenas, cadenaLinea);
									if (validarDatosD.size() == 0) {
										// Validar Tipo de Dato
										validarTipoDato = validarTiposDato(validacionCadenas, cadenaLinea);
										if (validarTipoDato.size() == 0) {
											// Validar que el valor de consecutivo de archivo sea mayor al consecutvo de
											// archivo anterior
											consecutivoMayor = validaConsecutivoMayor(cadena, consecutivoArchivo,
													validacionCadenas, cadenaLinea);
											if (consecutivoMayor.size() == 0) {
												// Agregar a Objeto BeanFF las lineas que se enviaran al momento de
												// inovcar el memtodo de BD
												AgregarDatosBeanFF1 agregarBeanFF = new AgregarDatosBeanFF1();
												ArrayList<BeanFF> listaBeanFF = agregarBeanFF
														.setCadenas(cadenasProcesar);
												// Validar datos de carta de este bloque, que sean iguales
												ValidaDatosCartaCtrol validaDatosC = new ValidaDatosCartaCtrol();
												HashMap<Integer, String> validacionDatosCart = null;
												String errorDatosCart = "";
												validacionDatosCart = validaDatosC.validaDatosCartaConsecutiva(
														listaBeanFF, cadenaLinea, aplicativoOrigen);
												if (validacionDatosCart.size() == 0) {

													// Invocar el metodo de BD enviando la lista "listaBeanFF" como
													// parametro
													ControlBloqueaDB controlProcesoBD = new ControlBloqueaDB();
													try {

														if (Integer.parseInt(lineasArchivo.get(lineasArchivo.size() - 2)
																.substring(0, 10).trim()) == listaBeanFF.get(0)
																		.getConsecArch()) {

															int conta = 0;
															int contaa = 0;
															ArrayList<BeanFF> listaBeanN = new ArrayList<BeanFF>();
															ArrayList<BeanFF> listaBeanFFIAN = new ArrayList<BeanFF>();
															ArrayList<BeanFF> listaBeanFFDAN = new ArrayList<BeanFF>();
															ArrayList<String> cadenasProcesarBean = new ArrayList<String>();
															ArrayList<Integer> lineasProcesar = new ArrayList<Integer>();
															int totalListaBeanFF = listaBeanFF.size();
															for (int i = 1; i <= listaBeanFF.size(); i++) {
																contaa++;

																if (i == 1) {
																	listaBeanN.add(listaBeanFF.get(conta));
																	cadenasProcesarBean.add(cadenasProcesar.get(conta));
																	lineasProcesar.add(cadenaLinea.get(conta));
																} else {
																	if (listaBeanFF.get(conta)
																			.getConsecNota() == listaBeanFF.get(i - 2)
																					.getConsecNota()) {
																		if(conta == 1) {
																			listaBeanFFIAN.addAll(listaBeanN);
																			listaBeanN.clear();
																		}
																		listaBeanFFIAN.add(listaBeanFF.get(conta));
																		cadenasProcesarBean.add(cadenasProcesar.get(conta));
																		lineasProcesar.add(cadenaLinea.get(conta));
																		if (i == totalListaBeanFF) {
																			listaBeanFFDAN.clear();
																		}
																		if (listaBeanFFDAN.size() > 0) {

																			ValidaDatosFacturaCtrol validaDatosF = new ValidaDatosFacturaCtrol();
																			HashMap<Integer, String> validacionDatosFac = null;
																			String errorDatosFac = "";
																			validacionDatosFac = validaDatosF
																					.validaDatosFacturaConsecutiva(
																							listaBeanFFDAN, lineasProcesar);
																			if (validacionDatosFac.size() == 0) {
																				// Validar el valor de la variable
																				// cartaGenerada
																				// que sea distinto a 0,
																				// ya que eso determina si ya se genero una
																				// carta con el mismo
																				// consecutivo
																				if (noCartaGenerada > 0) {
																					cartaGenerada = true;
																				}

																				List<BeanRespuesta> ctrolBD = controlProcesoBD
																						.BloqueaDB(listaBeanFFDAN, cartaGenerada);
																				boolean banderaCtrolBD = ctrolBD.get(0).GetBandera();

																				if (banderaCtrolBD) {

																					if (noCartaGenerada == 0) {
																						noCartaGenerada = ctrolBD.get(0).getCarta();
																					}
																					noFacturaGenerada = ctrolBD.get(0).getFactura();

																					for (String cadenaP : cadenasProcesarBean) {
																						escribirArchivoCorrecto(
																								rutaArchivoSBKP
																										+ nuevoNombreArc,
																								cadenaP, noCartaGenerada,
																								noFacturaGenerada);
																						totalRegistroCorrectos++;
																					}
																					
																					consecutivoArchivo = Integer
																							.parseInt(cadenasProcesarBean.get(0)
																									.substring(0, 10).trim());

																				} else {

																					// En caso de que la bandera sea false
																					// se da
																					// por
																					// hecho // que no
																					// se proeco correctamente los registros
																					// de
																					// la
																					// lista enviada y se
																					// debe de imprimir el
																					// error a todas las lineas enviadas.
																					String mensajeError = ctrolBD.get(0)
																							.getMensaje();
																					int lineaError = ctrolBD.get(0)
																							.getConsecutivoA();
																					for (String cadenaP : cadenasProcesarBean) {

																						escribirArchivoError(
																								rutaArchivoSBKP
																										+ nuevoNombreArc,
																								cadenaP,
																								"[ERROR EN LA LINEA"
																										+ lineasProcesar.get(
																												lineaError)
																										+ " CONSECUTIVO "
																										+ cadenasProcesarBean
																												.get(lineaError)
																												.substring(
																														0,
																														10)
																												.trim()
																										+ "/" +cadenasProcesarBean
																												.get(lineaError)
																												.substring(
																														30,
																														34)
																												.trim()
																										+ " \""
																										+ mensajeError
																										+ "\"]");
																						totalRegistroError++;
																					}
																					noCartaGenerada = 0;
																				}
																			} else {

																				for (Integer j : validacionDatosFac
																						.keySet()) {
																					errorDatosFac = validacionDatosFac
																							.values().toString();
																					log.error(
																							"Identificador de error(Datos Factura Consecutivo):"
																									+ j
																									+ " Descripcion de error: "
																									+ validacionDatosFac
																											.values()
																									+ " en la linea "
																									+ errorDatosFac.substring(15, 16));
																				}

																				for (String cadenaP : cadenasProcesarBean) {
																					escribirArchivoError(
																							rutaArchivoSBKP
																									+ nuevoNombreArc,
																							cadenaP, errorDatosFac);
																					totalRegistroError++;
																				}

																				/*if (Integer.parseInt((cadena
																						.substring(0, 10).trim().equals(""))
																								? "0"
																								: cadena.substring(0, 10)
																										.trim()) > consecutivoArchivo) {
																					// Agregar numero de archivo consecutivo
																					consecutivoArchivo = Integer
																							.parseInt(validacionCadenas
																									.get(0).substring(0, 10)
																									.trim());
																				}*/
																			}

																			listaBeanFFDAN.clear();
																			listaBeanN.clear();
																			cadenasProcesarBean.clear();
																			lineasProcesar.clear();
																		}
																	} else if (listaBeanFF.get(conta)
																			.getConsecNota() != listaBeanFF.get(i - 2)
																					.getConsecNota()) {

																		if (listaBeanFFIAN.size() > 0) {

																			ValidaDatosFacturaCtrol validaDatosF = new ValidaDatosFacturaCtrol();
																			HashMap<Integer, String> validacionDatosFac = null;
																			String errorDatosFac = "";
																			validacionDatosFac = validaDatosF
																					.validaDatosFacturaConsecutiva(
																							listaBeanFFIAN, lineasProcesar);
																			if (validacionDatosFac.size() == 0) {

																				// Validar el valor de la variable
																				// cartaGenerada
																				// que sea distinto a 0,
																				// ya que eso determina si ya se genero una
																				// carta con el mismo
																				// consecutivo
																				if (noCartaGenerada > 0) {
																					cartaGenerada = true;
																				}

																				List<BeanRespuesta> ctrolBD = controlProcesoBD
																						.BloqueaDB(listaBeanFFIAN, cartaGenerada);
																				boolean banderaCtrolBD =  ctrolBD.get(0).GetBandera();

																				if (banderaCtrolBD) {

																					if (noCartaGenerada == 0) {
																						noCartaGenerada = ctrolBD.get(0).getCarta();
																					}
																					noFacturaGenerada = ctrolBD.get(0).getFactura();

																					for (String cadenaP : cadenasProcesarBean) {

																						escribirArchivoCorrecto(
																								rutaArchivoSBKP
																										+ nuevoNombreArc,
																								cadenaP, noCartaGenerada,
																								noFacturaGenerada);
																						totalRegistroCorrectos++;
																					}
																					
																					consecutivoArchivo = Integer
																							.parseInt(cadenasProcesarBean.get(0)
																									.substring(0, 10).trim());

																				} else {

																					// En caso de que la bandera sea false
																					// se da
																					// por
																					// hecho // que no
																					// se proeco correctamente los registros
																					// de
																					// la
																					// lista enviada y se
																					// debe de imprimir el
																					// error a todas las lineas enviadas.
																					String mensajeError = ctrolBD.get(0)
																							.getMensaje();
																					int lineaError = ctrolBD.get(0)
																							.getConsecutivoA();
																					for (String cadenaP : cadenasProcesarBean) {

																						escribirArchivoError(
																								rutaArchivoSBKP
																										+ nuevoNombreArc,
																								cadenaP,
																								"[ERROR EN LA LINEA"
																										+ lineasProcesar.get(
																												lineaError)
																										+ " CONSECUTIVO "
																										+ cadenasProcesarBean
																												.get(lineaError)
																												.substring(
																														0,
																														10)
																												.trim()
																										+ cadenasProcesarBean
																												.get(lineaError)
																												.substring(
																														30,
																														34)
																												.trim()
																										+ " \""
																										+ mensajeError
																										+ "\"]");
																						totalRegistroError++;
																					}

																					noCartaGenerada = 0;
																				}
																			} else {

																				for (Integer j : validacionDatosFac
																						.keySet()) {
																					errorDatosFac = validacionDatosFac
																							.values().toString();
																					log.error(
																							"Identificador de error(Datos Factura Consecutivo):"
																									+ j
																									+ " Descripcion de error: "
																									+ validacionDatosFac
																											.values()
																									+ " en la linea "
																									+ errorDatosFac.substring(15, 16));
																				}

																				for (String cadenaP : cadenasProcesarBean) {
																					escribirArchivoError(
																							rutaArchivoSBKP
																									+ nuevoNombreArc,
																							cadenaP, errorDatosFac);
																					totalRegistroError++;
																				}

																				/*if (Integer.parseInt((cadena
																						.substring(0, 10).trim().equals(""))
																								? "0"
																								: cadena.substring(0, 10)
																										.trim()) > consecutivoArchivo) {
																					// Agregar numero de archivo consecutivo
																					consecutivoArchivo = Integer
																							.parseInt(validacionCadenas
																									.get(0).substring(0, 10)
																									.trim());
																				}*/

																			}

																			listaBeanFFIAN.clear();
																			listaBeanN.clear();
																			cadenasProcesarBean.clear();
																			lineasProcesar.clear();
																		} else if (listaBeanFFDAN.size() > 0) {

																			ValidaDatosFacturaCtrol validaDatosF = new ValidaDatosFacturaCtrol();
																			HashMap<Integer, String> validacionDatosFac = null;
																			String errorDatosFac = "";
																			validacionDatosFac = validaDatosF
																					.validaDatosFacturaConsecutiva(
																							listaBeanFFDAN, lineasProcesar);
																			if (validacionDatosFac.size() == 0) {

																				// Validar el valor de la variable
																				// cartaGenerada
																				// que sea distinto a 0,
																				// ya que eso determina si ya se genero una
																				// carta con el mismo
																				// consecutivo
																				if (noCartaGenerada > 0) {
																					cartaGenerada = true;
																				}

																				List<BeanRespuesta> ctrolBD = controlProcesoBD
																						.BloqueaDB(listaBeanFFDAN, cartaGenerada);
																				boolean banderaCtrolBD = ctrolBD.get(0).GetBandera();

																				if (banderaCtrolBD) {

																					if (noCartaGenerada == 0) {
																						noCartaGenerada = ctrolBD.get(0).getCarta();
																					}
																					noFacturaGenerada = ctrolBD.get(0).getFactura();

																					for (String cadenaP : cadenasProcesarBean) {

																						escribirArchivoCorrecto(
																								rutaArchivoSBKP
																										+ nuevoNombreArc,
																								cadenaP, noCartaGenerada,
																								noFacturaGenerada);
																						totalRegistroCorrectos++;
																					}
																					
																					consecutivoArchivo = Integer
																							.parseInt(cadenasProcesarBean.get(0)
																									.substring(0, 10).trim());

																				} else {

																					// En caso de que la bandera sea false
																					// se da
																					// por
																					// hecho // que no
																					// se proeco correctamente los registros
																					// de
																					// la
																					// lista enviada y se
																					// debe de imprimir el
																					// error a todas las lineas enviadas.
																					String mensajeError = ctrolBD.get(0)
																							.getMensaje();
																					int lineaError = ctrolBD.get(0)
																							.getConsecutivoA();
																					for (String cadenaP : cadenasProcesarBean) {

																						escribirArchivoError(
																								rutaArchivoSBKP
																										+ nuevoNombreArc,
																								cadenaP,
																								"[ERROR EN LA LINEA"
																										+ lineasProcesar.get(
																												lineaError)
																										+ " CONSECUTIVO "
																										+ cadenasProcesarBean
																												.get(lineaError)
																												.substring(
																														0,
																														10)
																												.trim()
																										+ "/" +cadenasProcesarBean
																												.get(lineaError)
																												.substring(
																														30,
																														34)
																												.trim()
																										+ " \""
																										+ mensajeError
																										+ "\"]");
																						totalRegistroError++;
																					}

																					noCartaGenerada = 0;
																				}

																			} else {

																				for (Integer j : validacionDatosFac
																						.keySet()) {
																					errorDatosFac = validacionDatosFac
																							.values().toString();
																					log.error(
																							"Identificador de error(Datos Factura Consecutivo):"
																									+ j
																									+ " Descripcion de error: "
																									+ validacionDatosFac
																											.values()
																									+ " en la linea "
																									+ errorDatosFac.substring(15, 16));
																				}

																				for (String cadenaP : cadenasProcesarBean) {
																					escribirArchivoError(
																							rutaArchivoSBKP
																									+ nuevoNombreArc,
																							cadenaP, errorDatosFac);
																					totalRegistroError++;
																				}

																				/*if (Integer.parseInt((cadena
																						.substring(0, 10).trim().equals(""))
																								? "0"
																								: cadena.substring(0, 10)
																										.trim()) > consecutivoArchivo) {
																					// Agregar numero de archivo consecutivo
																					consecutivoArchivo = Integer
																							.parseInt(validacionCadenas
																									.get(0).substring(0, 10)
																									.trim());
																				}*/

																			}

																			listaBeanFFDAN.clear();
																			listaBeanN.clear();
																			cadenasProcesarBean.clear();
																			lineasProcesar.clear();
																		}
																		
																		if(conta == 1) {
																			listaBeanFFDAN.addAll(listaBeanN);
																			listaBeanN.clear();
																		}
																		
																		listaBeanFFDAN.add(listaBeanFF.get(conta));
																		cadenasProcesarBean.add(cadenasProcesar.get(conta));
																		lineasProcesar.add(cadenaLinea.get(conta));
																	}
																}

																if (totalListaBeanFF == contaa) {
																	if (listaBeanFFDAN.size() > 0) {

																		ValidaDatosFacturaCtrol validaDatosF = new ValidaDatosFacturaCtrol();
																		HashMap<Integer, String> validacionDatosFac = null;
																		String errorDatosFac = "";
																		validacionDatosFac = validaDatosF
																				.validaDatosFacturaConsecutiva(
																						listaBeanFFDAN, lineasProcesar);
																		if (validacionDatosFac.size() == 0) {

																			// Validar el valor de la variable cartaGenerada
																			// que
																			// sea distinto a 0,
																			// ya que eso determina si ya se genero una
																			// carta
																			// con el mismo
																			// consecutivo
																			if (noCartaGenerada > 0) {
																				cartaGenerada = true;
																			}

																			List<BeanRespuesta> ctrolBD = controlProcesoBD
																					.BloqueaDB(listaBeanFFDAN, cartaGenerada);
																			boolean banderaCtrolBD = ctrolBD.get(0).GetBandera();

																			if (banderaCtrolBD) {
																				if (noCartaGenerada == 0) {
																					noCartaGenerada = ctrolBD.get(0).getCarta();
																				}
																				noFacturaGenerada = ctrolBD.get(0).getFactura();

																				for (String cadenaP : cadenasProcesarBean) {

																					escribirArchivoCorrecto(
																							rutaArchivoSBKP
																									+ nuevoNombreArc,
																							cadenaP, noCartaGenerada,
																							noFacturaGenerada);
																					totalRegistroCorrectos++;
																				}
																				
																				consecutivoArchivo = Integer
																						.parseInt(cadenasProcesarBean.get(0)
																								.substring(0, 10).trim());

																			} else {

																				// En caso de que la bandera sea false se da
																				// por
																				// hecho // que no
																				// se proeco correctamente los registros de
																				// la
																				// lista enviada y se
																				// debe de imprimir el
																				// error a todas las lineas enviadas.
																				String mensajeError = ctrolBD.get(0)
																						.getMensaje();
																				int lineaError = ctrolBD.get(0)
																						.getConsecutivoA();
																				for (String cadenaP : cadenasProcesarBean) {

																					escribirArchivoError(
																							rutaArchivoSBKP
																									+ nuevoNombreArc,
																							cadenaP,
																							"[ERROR EN LA LINEA"
																									+ lineasProcesar
																											.get(lineaError)
																									+ " CONSECUTIVO "
																									+ cadenasProcesarBean
																											.get(lineaError)
																											.substring(0,
																													10)
																											.trim()
																									+ "/" +cadenasProcesarBean
																											.get(lineaError)
																											.substring(30,
																													34)
																											.trim()
																									+ " \"" + mensajeError
																									+ "\"]");
																					totalRegistroError++;
																				}
																				noCartaGenerada = 0;
																			}

																		} else {

																			for (Integer j : validacionDatosFac.keySet()) {
																				errorDatosFac = validacionDatosFac.values()
																						.toString();
																				log.error(
																						"Identificador de error(Datos Factura Consecutivo):"
																								+ j
																								+ " Descripcion de error: "
																								+ validacionDatosFac
																										.values()
																								+ " en la linea " + errorDatosFac.substring(15, 16));
																			}

																			for (String cadenaP : cadenasProcesarBean) {
																				escribirArchivoError(
																						rutaArchivoSBKP + nuevoNombreArc,
																						cadenaP, errorDatosFac);
																				totalRegistroError++;
																			}

																			/*if (Integer.parseInt((cadena.substring(0, 10)
																					.trim().equals("")) ? "0"
																							: cadena.substring(0, 10)
																									.trim()) > consecutivoArchivo) {
																				// Agregar numero de archivo consecutivo
																				consecutivoArchivo = Integer
																						.parseInt(validacionCadenas.get(0)
																								.substring(0, 10).trim());
																			}*/

																		}

																		listaBeanFFDAN.clear();
																		listaBeanN.clear();
																		cadenasProcesarBean.clear();
																		lineasProcesar.clear();

																	} else if (listaBeanFFIAN.size() > 0) {

																		ValidaDatosFacturaCtrol validaDatosF = new ValidaDatosFacturaCtrol();
																		HashMap<Integer, String> validacionDatosFac = null;
																		String errorDatosFac = "";
																		validacionDatosFac = validaDatosF
																				.validaDatosFacturaConsecutiva(
																						listaBeanFFIAN, lineasProcesar);
																		if (validacionDatosFac.size() == 0) {

																			// Validar el valor de la variable cartaGenerada
																			// que
																			// sea distinto a 0,
																			// ya que eso determina si ya se genero una
																			// carta
																			// con el mismo
																			// consecutivo
																			if (noCartaGenerada > 0) {
																				cartaGenerada = true;
																			}

																			List<BeanRespuesta> ctrolBD = controlProcesoBD
																					.BloqueaDB(listaBeanFFIAN, cartaGenerada);
																			boolean banderaCtrolBD = ctrolBD.get(0).GetBandera();

																			if (banderaCtrolBD) {

																				if (noCartaGenerada == 0) {
																					noCartaGenerada = ctrolBD.get(0).getCarta();
																				}
																				noFacturaGenerada = ctrolBD.get(0).getFactura();

																				for (String cadenaP : cadenasProcesarBean) {

																					escribirArchivoCorrecto(
																							rutaArchivoSBKP
																									+ nuevoNombreArc,
																							cadenaP, noCartaGenerada,
																							noFacturaGenerada);
																					totalRegistroCorrectos++;
																				}
																				
																				consecutivoArchivo = Integer
																						.parseInt(cadenasProcesarBean.get(0)
																								.substring(0, 10).trim());
																				
																			} else {

																				// En caso de que la bandera sea false se da
																				// por
																				// hecho // que no
																				// se proeco correctamente los registros de
																				// la
																				// lista enviada y se
																				// debe de imprimir el
																				// error a todas las lineas enviadas.
																				String mensajeError = ctrolBD.get(0)
																						.getMensaje();
																				int lineaError = ctrolBD.get(0)
																						.getConsecutivoA();
																				for (String cadenaP : cadenasProcesarBean) {

																					escribirArchivoError(
																							rutaArchivoSBKP
																									+ nuevoNombreArc,
																							cadenaP,
																							"[ERROR EN LA LINEA"
																									+ lineasProcesar
																											.get(lineaError)
																									+ " CONSECUTIVO "
																									+ cadenasProcesarBean
																											.get(lineaError)
																											.substring(0,
																													10)
																											.trim()
																									+ "/" +cadenasProcesarBean
																											.get(lineaError)
																											.substring(30,
																													34)
																											.trim()
																									+ " \"" + mensajeError
																									+ "\"]");
																					totalRegistroError++;
																				}
																				noCartaGenerada = 0;
																			}
																		} else {

																			for (Integer j : validacionDatosFac.keySet()) {
																				errorDatosFac = validacionDatosFac.values()
																						.toString();
																				log.error(
																						"Identificador de error(Datos Factura Consecutivo):"
																								+ j
																								+ " Descripcion de error: "
																								+ validacionDatosFac
																										.values()
																								+ " en la linea " + errorDatosFac.substring(15, 16));

																			}

																			for (String cadenaP : cadenasProcesarBean) {
																				escribirArchivoError(
																						rutaArchivoSBKP + nuevoNombreArc,
																						cadenaP, errorDatosFac);
																				totalRegistroError++;
																			}

																			/*if (Integer.parseInt((cadena.substring(0, 10)
																					.trim().equals("")) ? "0"
																							: cadena.substring(0, 10)
																									.trim()) > consecutivoArchivo) {
																				// Agregar numero de archivo consecutivo
																				consecutivoArchivo = Integer
																						.parseInt(validacionCadenas.get(0)
																								.substring(0, 10).trim());
																			}*/

																		}
																		listaBeanFFIAN.clear();
																		listaBeanN.clear();
																		cadenasProcesarBean.clear();
																		lineasProcesar.clear();
																	}
																}
																conta++;
															}
														} else {
															
															// Inicializar la variable noCartaGenerada a 0 , ya que cambio el consetucivo de la carta
															noCartaGenerada = 0;
															ValidaDatosFacturaCtrol validaDatosF = new ValidaDatosFacturaCtrol();
															HashMap<Integer, String> validacionDatosFac = null;
															String errorDatosFac = "";
															validacionDatosFac = validaDatosF.validaDatosFacturaConsecutiva(
																	listaBeanFF, cadenaLinea);
															if (validacionDatosFac.size() == 0) {
																List<BeanRespuesta> ctrolBD = controlProcesoBD
																		.BloqueaDB(listaBeanFF, cartaGenerada);
																boolean banderaCtrolBD = ctrolBD.get(0).GetBandera();

																if (banderaCtrolBD) {
																	noCartaGenerada = ctrolBD.get(0).getCarta();
																	noFacturaGenerada = ctrolBD.get(0).getFactura();

																	for (String cadenaP : cadenasProcesar) {

																		escribirArchivoCorrecto(
																				rutaArchivoSBKP + nuevoNombreArc, cadenaP,
																				noCartaGenerada, noFacturaGenerada);
																		totalRegistroCorrectos++;
																	}

																	noCartaGenerada = 0;
																	
																	consecutivoArchivo = Integer
																			.parseInt(cadenasProcesar.get(0)
																					.substring(0, 10).trim());

																} else {

																	// En caso de que la bandera sea false se da por hecho
																	// //
																	// que no
																	// se proeco correctamente los registros de la lista
																	// enviada
																	// y se
																	// debe de imprimir el
																	// error a todas las lineas enviadas.
																	String mensajeError = ctrolBD.get(0).getMensaje();
																	int lineaError = ctrolBD.get(0).getConsecutivoA();
																	for (String cadenaP : cadenasProcesar) {

																		escribirArchivoError(
																				rutaArchivoSBKP + nuevoNombreArc, cadenaP,
																				"[ERROR EN LA LINEA"
																						+ cadenaLinea.get(lineaError)
																						+ " CONSECUTIVO "
																						+ cadenasProcesar.get(lineaError)
																								.substring(0, 10).trim()
																						+ "/" +cadenasProcesar.get(lineaError)
																								.substring(30, 34).trim()
																						+ " \"" + mensajeError + "\"]");
																		totalRegistroError++;
																	}

																	noCartaGenerada = 0;
																}
															} else {

																for (Integer j : validacionDatosFac.keySet()) {
																	errorDatosFac = validacionDatosFac.values().toString();
																	log.error(
																			"Identificador de error(Datos Factura Consecutivo):"
																					+ j + " Descripcion de error: "
																					+ validacionDatosFac.values()
																					+ " en la linea " + linea);
																}

																for (String cadenaP : cadenasProcesar) {
																	escribirArchivoError(rutaArchivoSBKP + nuevoNombreArc,
																			cadenaP, errorDatosFac);
																	totalRegistroError++;
																}

																/*if (Integer.parseInt(
																		(cadena.substring(0, 10).trim().equals("")) ? "0"
																				: cadena.substring(0, 10)
																						.trim()) > consecutivoArchivo) {
																	// Agregar numero de archivo consecutivo
																	consecutivoArchivo = Integer.parseInt(validacionCadenas
																			.get(0).substring(0, 10).trim());
																}*/
															}
														}

														/*if (Integer.parseInt((cadena.substring(0, 10).trim().equals(""))
																? "0"
																: cadena.substring(0, 10).trim()) > consecutivoArchivo) {
															// Agregar numero de archivo consecutivo
															consecutivoArchivo = Integer.parseInt(
																	validacionCadenas.get(0).substring(0, 10).trim());
														}*/

													} catch (Exception e) {
														e.printStackTrace();
														log.error(e.getMessage());
													}

												} else {
													for (Integer j : validacionDatosCart.keySet()) {
														errorDatosCart = validacionDatosCart.values().toString();
														log.error("Identificador de error(Datos Carta Consecutivo):" + j
																+ " Descripcion de error: "
																+ validacionDatosCart.values() + " en la linea "
																+ errorDatosCart.subSequence(15, 16));
														for (String cadenaP : cadenasProcesar) {
															escribirArchivoError(rutaArchivoSBKP + nuevoNombreArc,
																	cadenaP, errorDatosCart);
															totalRegistroError++;
														}
													}
												}

												// Eliminar datos de la lista listaBeanFF
												listaBeanFF.clear();
											} else {
												for (Integer j : consecutivoMayor.keySet()) {
													errorConsecutivoM = consecutivoMayor.values().toString();
													for (String cadenaP : cadenasProcesar) {
														log.error("Identificador de error(Tipo Dato):" + j
																+ " Descripcion de error: " + consecutivoMayor.values()
																+ " en la linea " + linea);
														escribirArchivoError(rutaArchivoSBKP + nuevoNombreArc, cadenaP,
																errorConsecutivoM);
														totalRegistroError++;
													}
												}
											}
										} else {
											for (Integer j : validarTipoDato.keySet()) {
												errorTipoD = validarTipoDato.values().toString();
												for (String cadenaP : cadenasProcesar) {
													log.error("Identificador de error(Tipo Dato):" + j
															+ " Descripcion de error: " + validarTipoDato.values()
															+ " en la linea " + linea);
													escribirArchivoError(rutaArchivoSBKP + nuevoNombreArc, cadenaP,
															errorTipoD);

													totalRegistroError++;
												}
											}
										}
									} else {
										for (Integer j : validarDatosD.keySet()) {
											errorDatosD = validarDatosD.values().toString();
											for (String cadenaP : cadenasProcesar) {
												log.error("Identificador de error(Dependencia Campos):" + j
														+ " Descripcion de error: " + validarDatosD.values()
														+ " en la linea " + linea);
												escribirArchivoError(rutaArchivoSBKP + nuevoNombreArc, cadenaP,
														errorDatosD);
												totalRegistroError++;
											}
										}
									}
								} else {
									for (Integer j : validarDatosO.keySet()) {
										errorDatosO = validarDatosO.values().toString();
										for (String cadenaP : cadenasProcesar) {
											log.error("Identificador de error(Datos Obligatorios):" + j
													+ " Descripcion de error: " + validarDatosO.values()
													+ " en la linea " + linea);
											escribirArchivoError(rutaArchivoSBKP + nuevoNombreArc, cadenaP,
													errorDatosO);
											totalRegistroError++;
										}
									}
								}
							} else {
								for (Integer j : validarLongitudLineas.keySet()) {
									errorPosicionD = validarLongitudLineas.values().toString();
									for (String cadenaP : cadenasProcesar) {
										log.error("Identificador de error(Datos Obligatorios):" + j
												+ "	Descripcion de error: " + validarLongitudLineas.values()
												+ " en la linea " + linea);
										escribirArchivoError(rutaArchivoSBKP + nuevoNombreArc, cadenaP, errorPosicionD);
										totalRegistroError++;
									}
								}
							}

							log.info("Se hace peticion de esta linea o lineas ^------");
						}

						// Eliminar datos de la lista control
						cadenaControl.clear();
						// Eliminar datos de la lista procesamiento
						cadenasProcesar.clear();
						// Eliminar datos de lista lineas
						cadenaLinea.clear();
						// Eliminar datos de la lista validacionCadenas
						validacionCadenas.clear();

					} else if (cadena.substring(0, 10).trim() != lineasArchivo.get(linea - 2).substring(0, 10).trim()) {
						if (cadenaControl.size() > 0) {
							for (String cadenaP : cadenasProcesar) {
								validacionCadenas.add(cadenaP);
							}

							// Inicializar la variable noCartaGenerada a 0 , ya que cambio el consetucivo de la carta
							noCartaGenerada = 0;

							// Validar Longuitud de Cadena
							validarLongitudLineas = validarLongitudLinea(validacionCadenas, cadenaLinea);
							if (validarLongitudLineas.size() == 0) {
								// Validar Datos Obligatorios
								validarDatosO = validarDatosObligatorios(validacionCadenas, cadenaLinea);
								if (validarDatosO.size() == 0) {
									// Validar Datos Dependientes
									validarDatosD = validarDatosDependientes(validacionCadenas, cadenaLinea);
									if (validarDatosD.size() == 0) {
										// Validar Tipo de Dato
										validarTipoDato = validarTiposDato(validacionCadenas, cadenaLinea);
										if (validarTipoDato.size() == 0) {
											// Validar que el valor de consecutivo de archivo sea mayor al consecutvo de
											// archivo anterior
											consecutivoMayor = validaConsecutivoMayor(cadena, consecutivoArchivo,
													validacionCadenas, cadenaLinea);
											if (consecutivoMayor.size() == 0) {
												// Agregar a Objeto BeanFF las lineas que se enviaran al momento de
												// inovcar el memtodo de BD
												AgregarDatosBeanFF1 agregarBeanFF = new AgregarDatosBeanFF1();
												ArrayList<BeanFF> listaBeanFF = agregarBeanFF
														.setCadenas(cadenasProcesar);
												// Validar datos de carta de este bloque, que sean iguales
												ValidaDatosCartaCtrol validaDatosC = new ValidaDatosCartaCtrol();
												HashMap<Integer, String> validacionDatosCart = null;
												String errorDatosCart = "";
												validacionDatosCart = validaDatosC.validaDatosCartaConsecutiva(
														listaBeanFF, cadenaLinea, aplicativoOrigen);
												if (validacionDatosCart.size() == 0) {

													ValidaDatosFacturaCtrol validaDatosF = new ValidaDatosFacturaCtrol();
													HashMap<Integer, String> validacionDatosFac = null;
													String errorDatosFac = "";
													validacionDatosFac = validaDatosF
															.validaDatosFacturaConsecutiva(listaBeanFF, cadenaLinea);
													if (validacionDatosFac.size() == 0) {

														// Invocar el metodo de BD enviando la lista "listaBeanFF" como
														// parametro
														ControlBloqueaDB controlProcesoBD = new ControlBloqueaDB();
														try {

															List<BeanRespuesta> ctrolBD = controlProcesoBD
																	.BloqueaDB(listaBeanFF, cartaGenerada);
															boolean banderaCtrolBD = ctrolBD.get(0).GetBandera();

															if (banderaCtrolBD) {
																noCartaGenerada = ctrolBD.get(0).getCarta();
																noFacturaGenerada = ctrolBD.get(0).getFactura();

																for (String cadenaP : cadenasProcesar) {

																	escribirArchivoCorrecto(
																			rutaArchivoSBKP + nuevoNombreArc, cadenaP,
																			noCartaGenerada, noFacturaGenerada);
																	totalRegistroCorrectos++;
																}

																noCartaGenerada = 0;

															} else {

																// En caso de que la bandera sea false se da por hecho
																// //
																// que no
																// se proeco correctamente los registros de la lista
																// enviada
																// y se
																// debe de imprimir el
																// error a todas las lineas enviadas.
																String mensajeError = ctrolBD.get(0).getMensaje();
																int lineaError = ctrolBD.get(0).getConsecutivoA();
																for (String cadenaP : cadenasProcesar) {

																	escribirArchivoError(
																			rutaArchivoSBKP + nuevoNombreArc, cadenaP,
																			"[ERROR EN LA LINEA"
																					+ cadenaLinea.get(lineaError)
																					+ " CONSECUTIVO "
																					+ cadenasProcesar.get(lineaError)
																							.substring(0, 10).trim()
																					+ "/" +cadenasProcesar.get(lineaError)
																							.substring(30, 34).trim()
																					+ " \"" + mensajeError + "\"]");
																	totalRegistroError++;
																}

																noCartaGenerada = 0;
															}

															if (Integer.parseInt(
																	(cadena.substring(0, 10).trim().equals("")) ? "0"
																			: cadena.substring(0, 10)
																					.trim()) > consecutivoArchivo) {
																// Agregar numero de archivo consecutivo
																consecutivoArchivo = Integer.parseInt(validacionCadenas
																		.get(0).substring(0, 10).trim());
															}

														} catch (Exception e) {
															e.printStackTrace();
															log.error(e.getMessage());
														}
													} else {

														for (Integer j : validacionDatosFac.keySet()) {
															errorDatosFac = validacionDatosFac.values().toString();
															log.error(
																	"Identificador de error(Datos Factura Consecutivo):"
																			+ j + " Descripcion de error: "
																			+ validacionDatosFac.values()
																			+ " en la linea " + linea);
														}

														for (String cadenaP : cadenasProcesar) {
															escribirArchivoError(rutaArchivoSBKP + nuevoNombreArc,
																	cadenaP, errorDatosFac);
															totalRegistroError++;
														}

													}
												} else {
													for (Integer j : validacionDatosCart.keySet()) {
														errorDatosCart = validacionDatosCart.values().toString();
														log.error("Identificador de error(Datos Carta Consecutivo):" + j
																+ " Descripcion de error: "
																+ validacionDatosCart.values() + " en la linea "
																+ errorDatosCart.subSequence(15, 16));
														for (String cadenaP : cadenasProcesar) {
															escribirArchivoError(rutaArchivoSBKP + nuevoNombreArc,
																	cadenaP, errorDatosCart);
															totalRegistroError++;
														}
													}

												}

												// Eliminar datos de la lista listaBeanFF
												listaBeanFF.clear();
											} else {
												for (Integer j : consecutivoMayor.keySet()) {
													errorConsecutivoM = consecutivoMayor.values().toString();
													for (String cadenaP : cadenasProcesar) {
														log.error("Identificador de error(Tipo Dato):" + j
																+ " Descripcion de error: " + consecutivoMayor.values()
																+ " en la linea " + linea);
														escribirArchivoError(rutaArchivoSBKP + nuevoNombreArc, cadenaP,
																errorConsecutivoM);
														totalRegistroError++;
													}
												}
											}
										} else {
											for (Integer j : validarTipoDato.keySet()) {
												errorTipoD = validarTipoDato.values().toString();
												for (String cadenaP : cadenasProcesar) {
													log.error("Identificador de error(Tipo Dato):" + j
															+ " Descripcion de error: " + validarTipoDato.values()
															+ " en la linea " + linea);
													escribirArchivoError(rutaArchivoSBKP + nuevoNombreArc, cadenaP,
															errorTipoD);
													totalRegistroError++;
												}
											}
										}
									} else {
										for (Integer j : validarDatosD.keySet()) {
											errorDatosD = validarDatosD.values().toString();
											for (String cadenaP : cadenasProcesar) {
												log.error("Identificador de error(Dependencia Campos):" + j
														+ " Descripcion de error: " + validarDatosD.values()
														+ " en la linea " + linea);
												escribirArchivoError(rutaArchivoSBKP + nuevoNombreArc, cadenaP,
														errorDatosD);
												totalRegistroError++;

											}
										}
									}
								} else {
									for (Integer j : validarDatosO.keySet()) {
										errorDatosO = validarDatosO.values().toString();
										for (String cadenaP : cadenasProcesar) {
											log.error("Identificador de error(Datos Obligatorios):" + j
													+ "	Descripcion de error: " + validarDatosO.values()
													+ " en la linea " + linea);
											escribirArchivoError(rutaArchivoSBKP + nuevoNombreArc, cadenaP,
													errorDatosO);
											totalRegistroError++;
										}
									}
								}
							} else {
								for (Integer j : validarLongitudLineas.keySet()) {
									errorPosicionD = validarLongitudLineas.values().toString();
									for (String cadenaP : cadenasProcesar) {
										log.error("Identifiacdor de error (Posicion Datos): " + j
												+ " Descripcion de error: " + validarLongitudLineas.values());
										escribirArchivoError(rutaArchivoSBKP + nuevoNombreArc, cadenaP, errorPosicionD);
										totalRegistroError++;
									}
								}
							}

							log.info("Se hace peticion de esta linea o lineas ^------");
						}

						// Eliminar datos de la lista control
						cadenaControl.clear();
						// Eliminar datos de la lista procesamiento
						cadenasProcesar.clear();
						// Eliminar datos de lista lineas
						cadenaLinea.clear();
						// Eliminar datos de la lista validacionCadenas
						validacionCadenas.clear();
					}
				}
				
			   } catch(Exception e) {
				   log.error("Error al recuperar datos de la linea " + linea);
				   log.error(e);
			   }
			}

		}

		copiarArchivo(archivo, rutaDirBkpE + nombreArchivo);
		moverArchivo(rutaArchivoSBKP + nuevoNombreArc, rutaDirS + nuevoNombreArc);
		br.close();
		eliminarArchivo(archivo);
		log.info("Total de registros procesados: " + linea);
		log.info("Total de registros correctos: " + totalRegistroCorrectos);
		log.info("Total de registros con error: " + totalRegistroError);
	}

	@SuppressWarnings("unused")
	private long obtenerTotalLineas(FileReader fr, BufferedReader brTotalLineasS) {
		long lineasTotal = 0;
		String sCadena;
		try {
			while ((sCadena = brTotalLineasS.readLine()) != null) {
				lineasTotal++;
			}
			brTotalLineasS.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return lineasTotal;
	}

	public static String removeUTF8BOM(String s) {
		if (s.startsWith(UTF8_BOM)) {
			s = s.substring(1);
		}
		return s;
	}

	public static HashMap<Integer, String> validarLongitudLinea(List<String> cadenas, List<Integer> linea) {

		HashMap<Integer, String> validaPosicionDato = new HashMap<Integer, String>();
		int cont = 0;
		for (String cadena : cadenas) {
			if (validaPosicionDato.size() == 0) {
				char[] arrayChar = cadena.toCharArray();
				int caracteres = arrayChar.length;
				if (caracteres == 524) {
					log.info("Longitud correcta de caracteres");

				} else {
					log.info("Longitud incorrecta de caracteres");
					validaPosicionDato.put(1, "ERROR EN LA LINEA" + linea.get(cont)
							+ " LA LONGUITUD DE CARACTERES NO CORRESPONDE A LA ESTABLECIDA");
					return validaPosicionDato;
				}
				cont++;
			}
		}

		return validaPosicionDato;
	}

	public static HashMap<Integer, String> validarDatosObligatorios(List<String> cadenas, List<Integer> linea) {

		HashMap<Integer, String> validaCamposObli = new HashMap<Integer, String>();
		int cont = 0;
		try {
			for (String cadena : cadenas) {
				// log.info("Validar Datos Obligatorios de linea " + linea.get(cont));
				if (validaCamposObli.size() == 0) {
					if (cadena.substring(0, 10).trim().equals("")) {
						validaCamposObli.put(1, "ERROR EN LA LINEA" + linea.get(cont)
								+ ", EL CAMPO \"CONSECUTIVO ARCHIVO\" ES OBLIGATORIO");
						return validaCamposObli;
					}

					if (cadena.substring(28, 30).trim().equals("")) {
						validaCamposObli.put(2,
								"ERROR EN LA LIENA" + linea.get(cont) + ", EL CAMPO \"TIPO REGISTRO\" ES OBLIGATORIO");
						return validaCamposObli;
					}

					if (cadena.substring(30, 34).trim().equals("")) {
						validaCamposObli.put(3, "ERROR EN LA LINEA" + linea.get(cont)
								+ ", EL CAMPO \"CONSECUTIVO NOTA/CONCEPTO\" ES OBLIGATORIO");
						return validaCamposObli;
					}

					if (cadena.substring(34, 36).trim().equals("")) {
						validaCamposObli.put(4,
								"ERROR EN LA LINEA" + linea.get(cont) + " CONSECUTIVO " + cadena.substring(0, 10) + "/"
										+ cadena.substring(30, 34) + " EL CAMPO \"TIPO CARTA\" ES OBLIGATORIO");
						return validaCamposObli;
					}

					if (cadena.substring(36, 38).trim().equals("")) {
						validaCamposObli.put(5,
								"ERROR EN LA LINEA" + linea.get(cont) + " CONSECUTIVO " + cadena.substring(0, 10) + "/"
										+ cadena.substring(30, 34) + " EL CAMPO \"TIPO PAGO\" ES OBLIGATORIO");
						return validaCamposObli;
					}

					if (cadena.substring(38, 48).trim().equals("")) {
						validaCamposObli.put(6,
								"ERROR EN LA LINEA" + linea.get(cont) + " CONSECUTIVO " + cadena.substring(0, 10) + "/"
										+ cadena.substring(30, 34)
										+ " EL CAMPO \"NUMERO DE PROVEEDOR\" ES OBLIGATORIO");
						return validaCamposObli;
					}

					if (cadena.substring(48, 52).trim().equals("")) {
						validaCamposObli.put(7,
								"ERROR EN LA LINEA" + linea.get(cont) + " CONSECUTIVO " + cadena.substring(0, 10) + "/"
										+ cadena.substring(30, 34) + " EL CAMPO \"SOCIEDAD RECEPTORA\" ES OBLIGATORIO");
						return validaCamposObli;
					}

					if (cadena.substring(52, 102).trim().equals("")) {
						validaCamposObli.put(8,
								"ERROR EN LA LINEA" + linea.get(cont) + " CONSECUTIVO " + cadena.substring(0, 10) + "/"
										+ cadena.substring(30, 34) + " EL CAMPO \"GLG\" ES OBLIGATORIO");
						return validaCamposObli;
					}

					if (cadena.substring(157, 160).trim().equals("")) {
						validaCamposObli.put(9,
								"ERROR EN LA LINEA" + linea.get(cont) + " CONSECUTIVO " + cadena.substring(0, 10) + "/"
										+ cadena.substring(30, 34) + " EL CAMPO \"MONEDA\" ES OBLIGATORIO");
						return validaCamposObli;
					}

					if (cadena.substring(182, 192).trim().equals("")) {
						validaCamposObli.put(10,
								"ERROR EN LA LINEA" + linea.get(cont) + " CONSECUTIVO " + cadena.substring(0, 10) + "/"
										+ cadena.substring(30, 34)
										+ " EL CAMPO \"CUENTA GPS(CUENTA DE GASTO)\" ES OBLIGATORIO");
						return validaCamposObli;
					}

					if (cadena.substring(192, 202).trim().equals("")) {
						validaCamposObli.put(11,
								"ERROR EN LA LINEA" + linea.get(cont) + " CONSECUTIVO " + cadena.substring(0, 10) + "/"
										+ cadena.substring(30, 34)
										+ " EL CAMPO \"USUARIO CREADOR DE CARTA\" ES OBLIGATORIO");
						return validaCamposObli;
					}

					if (cadena.substring(202, 206).trim().equals("") || cadena.substring(202, 206).trim().equals("0")) {
						validaCamposObli.put(12,
								"ERROR EN LA LINEA" + linea.get(cont) + " CONSECUTIVO " + cadena.substring(0, 10) + "/"
										+ cadena.substring(30, 34) + " EL CAMPO \"CENTRO DE COSTOS\" ES OBLIGATORIO");
						return validaCamposObli;
					}

					if (cadena.substring(206, 306).trim().equals("")) {
						validaCamposObli.put(13,
								"ERROR EN LA LINEA" + linea.get(cont) + " CONSECUTIVO " + cadena.substring(0, 10) + "/"
										+ cadena.substring(30, 34)
										+ " EL CAMPO \"DESCRIPCIÓN DEL SERVICIO\" ES OBLIGATORIO");
						return validaCamposObli;
					}

					if (cadena.substring(306, 316).trim().equals("")) {
						validaCamposObli.put(14,
								"ERROR EN LA LINEA" + linea.get(cont) + " CONSECUTIVO " + cadena.substring(0, 10) + "/"
										+ cadena.substring(30, 34)
										+ " EL CAMPO \"FECHA INICIO SERVICIO\" ES OBLIGATORIO");
						return validaCamposObli;
					}

					if (cadena.substring(316, 326).trim().equals("")) {
						validaCamposObli.put(15,
								"ERROR EN LA LINEA" + linea.get(cont) + " CONSECUTIVO " + cadena.substring(0, 10) + "/"
										+ cadena.substring(30, 34) + " EL CAMPO \"FECHA FIN SERVICIO\" ES OBLIGATORIO");
						return validaCamposObli;
					}

					if (cadena.substring(326, 328).trim().equals("")) {
						validaCamposObli.put(16,
								"ERROR EN LA LINEA" + linea.get(cont) + " CONSECUTIVO " + cadena.substring(0, 10) + "/"
										+ cadena.substring(30, 34) + " EL CAMPO \"ESTADO\" ES OBLIGATORIO");
						return validaCamposObli;
					}

					if (cadena.substring(328, 340).trim().equals("")) {
						validaCamposObli.put(17,
								"ERROR EN LA LINEA" + linea.get(cont) + " CONSECUTIVO " + cadena.substring(0, 10) + "/"
										+ cadena.substring(30, 34) + " EL CAMPO \"IMPORTE UNITARIO\" ES OBLIGATORIO");
						return validaCamposObli;
					}

					if (cadena.substring(340, 353).trim().equals("")) {
						validaCamposObli.put(18,
								"ERROR EN LA LINEA" + linea.get(cont) + " CONSECUTIVO " + cadena.substring(0, 10) + "/"
										+ cadena.substring(30, 34) + " EL CAMPO \"NÚMERO DE UNIDADES\" ES OBLIGATORIO");
						return validaCamposObli;
					}

					if (cadena.substring(353, 365).trim().equals("")) {
						validaCamposObli.put(19,
								"ERROR EN LA LINEA" + linea.get(cont) + " CONSECUTIVO " + cadena.substring(0, 10) + "/"
										+ cadena.substring(30, 34) + " EL CAMPO \"IVA\" ES OBLIGATORIO");
						return validaCamposObli;
					}

					if (cadena.substring(425, 427).trim().equals("SI")
							&& cadena.substring(427, 457).trim().equals("")) {
						validaCamposObli.put(20, "ERROR EN LA LINEA" + linea.get(cont) + " CONSECUTIVO "
								+ cadena.substring(0, 10) + "/" + cadena.substring(30, 34)
								+ " EL CAMPO \"NUMERO DE ANTICIPO\" es obligatorio, ya que se informo con el valor SI el campo \"Comprobacion\" en la linea: "
								+ linea);
						return validaCamposObli;
					}

					if (cadena.substring(425, 427).trim().equals("SI")
							&& cadena.substring(457, 467).trim().equals("")) {
						validaCamposObli.put(21, "ERROR EN LA LINEA" + linea.get(cont) + " CONSECUTIVO "
								+ cadena.substring(0, 10) + "/" + cadena.substring(30, 34)
								+ " EL CAMPO \"FECHA DE ANTICIPO\" es obligatorio, ya que se informo con el valor SI el campo \"Comprobacion\" en la linea: "
								+ linea);
						return validaCamposObli;
					}

					/*if (cadena.substring(467, 468).trim().equals("")) {
						validaCamposObli.put(22,
								"ERROR EN LA LINEA" + linea.get(cont) + " CONSECUTIVO " + cadena.substring(0, 10) + "/"
										+ cadena.substring(30, 34) + " EL CAMPO \"VIA DE PAGO\" ES OBLIGATORIO");
						return validaCamposObli;
					}

					if (cadena.substring(468, 488).trim().equals("")) {
						validaCamposObli.put(23,
								"ERROR EN LA LINEA" + linea.get(cont) + " CONSECUTIVO " + cadena.substring(0, 10) + "/"
										+ cadena.substring(30, 34) + " EL CAMPO \"CUENTA BANCARIA\" ES OBLIGATORIO");
						return validaCamposObli;
					}*/

					if (cadena.substring(518, 521).trim().equals("")) {
						validaCamposObli.put(24,
								"ERROR EN LA LINEA" + linea.get(cont) + " CONSECUTIVO " + cadena.substring(0, 10) + "/"
										+ cadena.substring(30, 34)
										+ " EL CAMPO \"ESTATUS FACTURA\" Factura ES OBLIGATORIO");
						return validaCamposObli;
					}

					if (cadena.substring(521, 524).trim().equals("")) {
						validaCamposObli.put(25,
								"ERROR EN LA LINEA" + linea.get(cont) + " CONSECUTIVO " + cadena.substring(0, 10) + "/"
										+ cadena.substring(30, 34) + " EL CAMPO \"APLICATIVO ORIGEN\" ES OBLIGATORIO");
						return validaCamposObli;
					}
					cont++;
				}
			}
		} catch (StringIndexOutOfBoundsException e) {
			log.info("Error al obtner los datos de la posicion: " + e.getLocalizedMessage());
			validaCamposObli = null;
			return validaCamposObli;
		}
		return validaCamposObli;
	}

	public static HashMap<Integer, String> validarDatosDependientes(List<String> cadenas, List<Integer> linea) {

		HashMap<Integer, String> validaCamposDepen = new HashMap<Integer, String>();
		int cont = 0;
		for (String cadena : cadenas) {
			// log.info("Validar Datos Dependientes de linea " + linea.get(cont));
			if (validaCamposDepen.size() == 0) {
				if(!(cadena.substring(145, 157).trim().equals(""))) {
				boolean validarNuPep = validaNupep(cadena.substring(145, 157).trim());
					if (validarNuPep) {
						validaCamposDepen.put(1, "ERROR EN LA LINEA" + linea.get(cont) + " CONSECUTIVO "
								+ cadena.substring(0, 10).trim() + "/" + cadena.substring(30, 34).trim()
								+ " EL VALOR DEL CAMPO \"NUMERO DE PEP\" ES INCORRECTO, YA QUE NO CUMPLE CON EL FORMATO \"00000000-000\"");
						return validaCamposDepen;
					}
				}
				if ((!cadena.substring(174, 182).trim().equals(""))
						&& cadena.substring(174, 182).trim().equals(cadena.substring(38, 48).trim())) {
					validaCamposDepen.put(2, "ERROR EN LA LINEA" + linea.get(cont) + " CONSECUTIVO "
							+ cadena.substring(0, 10).trim() + "/" + cadena.substring(30, 34).trim()
							+ " EL VALOR DEL CAMPO \"RECEPTOR ALTERNATIVO\" DEBE DE SER DIFERENTE AL VALOR DE \"NUMERO DE PROVEEDOR\"");
					return validaCamposDepen;
				}

				boolean validarFechaFin = validaFecha(cadena.substring(306, 316).trim(),
						cadena.substring(316, 326).trim());
				if (validarFechaFin) {
					validaCamposDepen.put(3, "ERROR EN LA LINEA" + linea.get(cont) + " CONSECUTIVO "
							+ cadena.substring(0, 10).trim() + "/" + cadena.substring(30, 34).trim()
							+ " EL VALOR DEL CAMPO \"FECHA FIN SERVICIO\" DEBE DE SER MAYOR AL CAMPO DE \"FECHA INICIO SERVICIO\"");
					return validaCamposDepen;
				}

				if (cadena.substring(28, 30).trim().equals("NC") && cadena.substring(425, 427).trim().equals("SI")) {
					validaCamposDepen.put(4, "ERROR EN LA LINEA" + linea.get(cont) + " CONSECUTIVO "
							+ cadena.substring(0, 10).trim() + "/" + cadena.substring(30, 34).trim()
							+ " EL VALOR: \"SI\", DEL CAMPO CAMPO \"COMPROBACION\" SOLO APLICA PARA \"TIPO REGISTRO\": \"NF\"");
					return validaCamposDepen;
				}

				if ((cadena.substring(467, 468).trim().equals("") || cadena.substring(467, 468).trim() == null)
						&& !(cadena.substring(468, 488).trim().equals(""))) {
					validaCamposDepen.put(5, "ERROR EN LA LINEA" + linea.get(cont) + " CONSECUTIVO "
							+ cadena.substring(0, 10).trim() + "/" + cadena.substring(30, 34).trim()
							+ " EL VALOR DEL CAMPO CAMPO \"CUENTA BANCARIA\" NO PUEDE VENIR INFORMADO, YA QUE SOLO APLICA CUANDO EL CAMPO \"VÍA DE PAGO\": ESTA INFORMADO");
					return validaCamposDepen;
				}

				if ((cadena.substring(467, 468).trim().equals("") || cadena.substring(467, 468).trim() == null)
						&& !(cadena.substring(4488, 498).trim().equals(""))) {
					validaCamposDepen.put(5, "ERROR EN LA LINEA" + linea.get(cont) + " CONSECUTIVO "
							+ cadena.substring(0, 10).trim() + "/" + cadena.substring(30, 34).trim()
							+ " EL VALOR DEL CAMPO CAMPO \"TIPO BANCO\" NO PUEDE VENIR INFORMADO, YA QUE SOLO APLICA CUANDO EL CAMPO \"VÍA DE PAGO\": ESTA INFORMADO");
					return validaCamposDepen;
				}

				cont++;
			}
		}

		return validaCamposDepen;
	}

	public static Boolean validaNupep(String nuPep) {
		boolean validaP = false;
		String regexPep = "^([\\d]{8})([-])([\\d]{3})$";
		String valorPep = nuPep;

		Pattern pattern = Pattern.compile(regexPep);
		Matcher matcher = pattern.matcher(valorPep);

		if (!matcher.find()) {
			validaP = true;
		}
		return validaP;
	}

	public static Boolean validaFecha(String fechaInicio, String fechaFin) {
		boolean fechaValida = true;
		Date fechaIniParse = null;
		Date fechaFinalParse = null;
		SimpleDateFormat parseador = new SimpleDateFormat("dd/MM/yyyy");

		try {

			fechaIniParse = parseador.parse(fechaInicio);
			fechaFinalParse = parseador.parse(fechaFin);

			if (fechaIniParse.before(fechaFinalParse)) {
				fechaValida = false;
			} else {
				if (fechaFinalParse.before(fechaIniParse)) {
					fechaValida = true;
				} else {
					fechaValida = false;
				}
			}
		} catch (ParseException e) {
			// log.error("Error al validar la Fecha Inicio y Final.");
			e.printStackTrace();
			return fechaValida;
		}
		return fechaValida;
	}

	public static HashMap<Integer, String> validarTiposDato(List<String> cadenas, List<Integer> linea) {

		HashMap<Integer, String> validaTipoDato = new HashMap<Integer, String>();
		int cont = 0;
		try {
			for (String cadena : cadenas) {
				// log.info("Validar Tipo de Dato de linea " + linea.get(cont));
				if (validaTipoDato.size() == 0) {
					if (!(isNumeric(cadena.substring(0, 10).trim()))) {
						validaTipoDato.put(1,
								"ERROR EN LA LINEA" + linea.get(cont) + " CONSECUTIVO " + cadena.substring(0, 10) + "/"
										+ cadena.substring(30, 34)
										+ " EL CAMPO \"CONSECUTIVO ARCHIVO\" NO ES NUMERICO");
						return validaTipoDato;
					}

					if (!cadena.substring(10, 19).trim().equals("") && !(isNumeric(cadena.substring(10, 19).trim()))) {
						validaTipoDato.put(2,
								"ERROR EN LA LINEA" + linea.get(cont) + " CONSECUTIVO " + cadena.substring(0, 10) + "/"
										+ cadena.substring(30, 34) + " EL CAMPO \"NUMERO DE CARTA\" NO ES NUMERICO");
						return validaTipoDato;
					}

					if (!cadena.substring(19, 28).trim().equals("") && !(isNumeric(cadena.substring(19, 28).trim()))) {
						validaTipoDato.put(3,
								"ERROR EN LA LINEA" + linea.get(cont) + " CONSECUTIVO " + cadena.substring(0, 10) + "/"
										+ cadena.substring(30, 34) + " EL CAMPO \"NUMERO DE FACTURA\" NO ES NUMERICO");
						return validaTipoDato;
					}

					if (!(isNumeric(cadena.substring(30, 34).trim()))) {
						validaTipoDato.put(4,
								"ERROR EN LA LINEA" + linea.get(cont) + " CONSECUTIVO " + cadena.substring(0, 10) + "/"
										+ cadena.substring(30, 34)
										+ " EL CAMPO \"CONSECUTIVO NOTA\\CONCEPTO\" NO ES NUMERICO");
						return validaTipoDato;
					}

					if (!(isNumeric(cadena.substring(38, 48).trim()))) {
						validaTipoDato.put(5,
								"ERROR EN LA LINEA" + linea.get(cont) + " CONSECUTIVO " + cadena.substring(0, 10) + "/"
										+ cadena.substring(30, 34)
										+ " EL CAMPO \"NUMERO DE PROVEEDOR\" NO ES NUMERICO");
						return validaTipoDato;
					}

					if (!cadena.substring(164, 174).trim().equals("")
							&& !(isNumeric(cadena.substring(164, 174).trim()))) {
						validaTipoDato.put(6,
								"ERROR EN LA LINEA" + linea.get(cont) + " CONSECUTIVO " + cadena.substring(0, 10) + "/"
										+ cadena.substring(30, 34) + " EL CAMPO \"CONTRATO\" NO ES NUMERICO");
						return validaTipoDato;
					}

					if (!cadena.substring(174, 182).trim().equals("")
							&& !(isNumeric(cadena.substring(174, 182).trim()))) {
						validaTipoDato.put(7,
								"ERROR EN LA LINEA" + linea.get(cont) + " CONSECUTIVO " + cadena.substring(0, 10) + "/"
										+ cadena.substring(30, 34)
										+ " EL CAMPO \"RECEPTOR ALTERNATIVO\" NO ES NUMERICO");
						return validaTipoDato;
					}

					if (!(isNumeric(cadena.substring(182, 192).trim()))) {
						validaTipoDato.put(8,
								"ERROR EN LA LINEA" + linea.get(cont) + " CONSECUTIVO " + cadena.substring(0, 10) + "/"
										+ cadena.substring(30, 34) + " EL CAMPO \"CUENTA GPS\" NO ES NUMERICO");
						return validaTipoDato;
					}

					if (!(isNumeric(cadena.substring(326, 328).trim()))) {
						validaTipoDato.put(9,
								"ERROR EN LA LINEA" + linea.get(cont) + " CONSECUTIVO " + cadena.substring(0, 10) + "/"
										+ cadena.substring(30, 34) + " EL CAMPO \"ESTADO\" NO ES NUMERICO");
						return validaTipoDato;
					}

					if (!(isDecimal(cadena.substring(328, 340).trim()))) {
						validaTipoDato.put(10,
								"ERROR EN LA LINEA" + linea.get(cont) + " CONSECUTIVO " + cadena.substring(0, 10) + "/"
										+ cadena.substring(30, 34) + " EL CAMPO \"IMPORTE UNITARIO\" NO ES NUMERICO");
						return validaTipoDato;
					}

					if (!(isDecimal(cadena.substring(340, 353).trim()))) {
						validaTipoDato.put(11,
								"ERROR EN LA LINEA" + linea.get(cont) + " CONSECUTIVO " + cadena.substring(0, 10) + "/"
										+ cadena.substring(30, 34) + " EL CAMPO \"NUMERO DE UNIDADES\" NO ES NUMERICO");
						return validaTipoDato;
					}

					if (!cadena.substring(365, 377).trim().equals("")
							&& !(isDecimal(cadena.substring(365, 377).trim()))) {
						validaTipoDato.put(12,
								"ERROR EN LA LINEA" + linea.get(cont) + " CONSECUTIVO " + cadena.substring(0, 10) + "/"
										+ cadena.substring(30, 34) + " EL CAMPO \"ISR RETENIDO\" NO ES NUMERICO");
						return validaTipoDato;
					}

					if (!cadena.substring(377, 389).trim().equals("")
							&& !(isDecimal(cadena.substring(377, 389).trim()))) {
						validaTipoDato.put(13,
								"ERROR EN LA LINEA" + linea.get(cont) + " CONSECUTIVO " + cadena.substring(0, 10) + "/"
										+ cadena.substring(30, 34) + " EL CAMPO \"IVA RETENIDO\" NO ES NUMERICO");
						return validaTipoDato;
					}

					if (!cadena.substring(389, 401).trim().equals("")
							&& !(isDecimal(cadena.substring(389, 401).trim()))) {
						validaTipoDato.put(14,
								"ERROR EN LA LINEA" + linea.get(cont) + " CONSECUTIVO " + cadena.substring(0, 10) + "/"
										+ cadena.substring(30, 34) + " EL CAMPO \"IMPUESTO CEDULAR\" NO ES NUMERICO");
						return validaTipoDato;
					}

					if (!cadena.substring(401, 413).trim().contentEquals("")
							&& !(isDecimal(cadena.substring(401, 413).trim()))) {
						validaTipoDato.put(15,
								"ERROR EN LA LINEA" + linea.get(cont) + " CONSECUTIVO " + cadena.substring(0, 10) + "/"
										+ cadena.substring(30, 34) + " EL CAMPO \"OTROS IMPUESTOS\" NO ES NUMERICO");
						return validaTipoDato;
					}

					if (!cadena.substring(413, 425).trim().equals("")
							&& !(isDecimal(cadena.substring(413, 425).trim()))) {
						validaTipoDato.put(16,
								"ERROR EN LA LINEA" + linea.get(cont) + " CONSECUTIVO " + cadena.substring(0, 10) + "/"
										+ cadena.substring(30, 34) + " EL CAMPO \"DESCUENTO\" NO ES NUMERICO");
						return validaTipoDato;
					}

					if (!(isNumeric(cadena.substring(468, 488).trim()))) {
						validaTipoDato.put(17,
								"ERROR EN LA LINEA" + linea.get(cont) + " CONSECUTIVO " + cadena.substring(0, 10) + "/"
										+ cadena.substring(30, 34) + " EL CAMPO \"CUENTA BANCARIA\" NO ES NUMERICO");
						return validaTipoDato;
					}

					if (!cadena.substring(498, 518).trim().equals("")
							&& !(isNumeric(cadena.substring(498, 518).trim()))) {
						validaTipoDato.put(18,
								"ERROR EN LA LINEA" + linea.get(cont) + " CONSECUTIVO " + cadena.substring(0, 10) + "/"
										+ cadena.substring(30, 34) + " EL CAMPO \"NUMERO DE ACTIVO\" NO ES NUMERICO");
						return validaTipoDato;
					}
					cont++;
				}
			}
		} catch (StringIndexOutOfBoundsException e) {
			log.info("Error al obtner los datos de la posicion: " + e.getLocalizedMessage());
		}
		return validaTipoDato;
	}

	public static HashMap<Integer, String> validaConsecutivoMayor(String cadenaAtual, int consecutivoArchivo,
			List<String> cadenasProcesar, List<Integer> linea) {

		HashMap<Integer, String> validaConsecutivoMayor = new HashMap<Integer, String>();
		int cont = 0;
		for (String cadenaProcesar : cadenasProcesar) {
			log.info("Validar consecutivo actual, sea mayor al consecutivo de la linea anterior, " + "de la linea"
					+ linea);
			if (!cadenaAtual.substring(0, 10).trim().equals("")) {
				if (!(Integer.parseInt((cadenasProcesar.get(0).substring(0, 10).trim().equals("")) ? "0"
						: cadenasProcesar.get(cont).substring(0, 10).trim()) > consecutivoArchivo)) {
					validaConsecutivoMayor.put(1, "ERROR EN LA LINEA" + linea.get(cont) + " CONSECUTIVO "
							+ cadenaProcesar.substring(0, 10).trim() + "/" + cadenaProcesar.substring(30, 34).trim()
							+ " EL CAMPO \"CONSECUTIVO ARCHIVO\" ES MENOR AL CONSECUTIVO ARCHIVO DE LA LINEA ANTERIOR ANTERIOR");
					return validaConsecutivoMayor;
				}
			}
			cont++;
		}

		return validaConsecutivoMayor;
	}

	public static boolean isNumeric(String valor) {

		try {
			Integer.parseInt(valor);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static boolean isDecimal(String valor) {

		try {
			new BigDecimal(valor);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static void escribirArchivoError(String archivo, String cadena, String error) {
		try {
			File file = new File(archivo);

			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file, true);
			fw.write(cadena + " " + error + "\r\n");
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void escribirArchivoCorrecto(String archivo, String cadena, int numCarta, int numFactura) {
		try {
			File file = new File(archivo);

			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file, true);
			fw.write(cadena + " [Numero de carta: " + numCarta + ", Numero de factura: " + numFactura + "]" + "\r\n");
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void moverArchivo(String rutaDirSBKP, String rutaDirS) {

		File from = new File(rutaDirSBKP);
		File to = new File(rutaDirS);

		// CopyOption[] options = new CopyOption[] {
		// StandardCopyOption.REPLACE_EXISTING};
		try {
			InputStream in = new FileInputStream(from);
			OutputStream out = new FileOutputStream(to);

			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			in.close();
			out.close();
			// Files.copy(FROM, TO, options);
			from.delete();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void copiarArchivo(String rutaDir, String rutaDirS) {
		File from = new File(rutaDir);
		File to = new File(rutaDirS);

		try {
			InputStream in = new FileInputStream(from);
			OutputStream out = new FileOutputStream(to);

			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			in.close();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void eliminarArchivo(String archivoEntrada) {
		File from = new File(archivoEntrada);
		if (from.exists()) {
			if (from.delete()) {
				System.out.println("Se elimino el achivo: " + archivoEntrada);
			} else {
				System.out.println("No se elimino el achivo: " + archivoEntrada);
			}
		}
	}
}
