package main.java.com.vpd.bbva.control;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import main.java.com.vpd.bbva.bean.BeanFF;

public class ValidacionDatos {

	private static final Logger log = Logger.getLogger(ValidacionDatos.class);
	public static final String UTF8_BOM = "\uFEFF";

	public ValidacionDatos() {
		// TODO Auto-generated constructor stub
	}

	public Boolean obtenerDatos(String archivo, String rutaArchivoSBKP, String rutaDirBkpE, String rutaDirS)
			throws FileNotFoundException, IOException {

		log.info("Leer archivo");
		FileInputStream file = new FileInputStream(archivo);
		BufferedReader br = new BufferedReader(new InputStreamReader(file, "UTF8"));
		String cadena = null;

		ArrayList<BeanFF> beanFF = new ArrayList<BeanFF>();
		BeanFF registroBeanFF = null;
		HashMap<Integer, String> validarLongitudLineas = null;
		HashMap<Integer, String> validarDatosO = null;
		HashMap<Integer, String> validarDatosD = null;
		HashMap<Integer, String> validarTipoDato = null;
		boolean firstLine = true;
		int linea = 0;
		int totalRegistroCorrectos = 0;
		int totalRegistroError = 0;
		
		String nombreArchivo = archivo.substring(archivo.length() - 22);
		// Renombrar el archivo para la salida
		String nuevoNombreArc = "LOG" + nombreArchivo;

		while ((cadena = br.readLine()) != null) {
			String errorPosicionD = "";
			String errorDatosO = "";
			String errorDatosD = "";
			String errorTipoD = "";
			
			if (firstLine) {
				cadena = removeUTF8BOM(cadena);
				firstLine = false;
			}

			linea++;
			log.info("Linea" + linea + ": " + cadena);

			validarLongitudLineas = validarLongitudLinea(cadena, linea);
			for (Integer j : validarLongitudLineas.keySet()) {
				errorPosicionD = validarLongitudLineas.values().toString();
				log.error("Identifiacdor de error (Posicion Datos): " + j + " Descripcion de error: "
						+ validarLongitudLineas.values());
				totalRegistroError++;
			}
			if (validarLongitudLineas.size() == 0) {
				validarDatosO = validarDatosObligatorios(cadena, linea);
				for (Integer j : validarDatosO.keySet()) {
					errorDatosO = validarDatosO.values().toString();
					log.error("Identificador de error(Datos Obligatorios):" + j + " Descripcion de error: "
							+ validarDatosO.values() + " en la linea " + linea);
					totalRegistroError++;
				}

				if (validarLongitudLineas.size() == 0 && validarDatosO.size() == 0) {
					validarDatosD = validarDatosDependientes(cadena, linea);
					for (Integer j : validarDatosD.keySet()) {
						errorDatosD = validarDatosD.values().toString();
						log.error("Identificador de error(Dependencia Campos):" + j + " Descripcion de error: "
								+ validarDatosD.values() + " en la linea " + linea);
						totalRegistroError++;
					}
				}

				if (validarLongitudLineas.size() == 0 && validarDatosO.size() == 0 && validarDatosD.size() == 0) {
					validarTipoDato = validarTiposDato(cadena, linea);
					for (Integer j : validarTipoDato.keySet()) {
						errorTipoD = validarTipoDato.values().toString();
						log.error("Identificador de error(Tipo Dato):" + j + " Descripcion de error: "
								+ validarTipoDato.values() + " en la linea " + linea);
						totalRegistroError++;
					}
				}

				try {
					if (validarDatosO.size() == 0) {
						if (validarDatosD.size() == 0) {
							if (validarTipoDato.size() == 0) {
								registroBeanFF = new BeanFF();

								registroBeanFF.setConsecArch(Integer.parseInt(cadena.substring(0, 10).trim()));

								if (!(cadena.substring(10, 19).trim().equals(""))) {
									registroBeanFF.setNu_carta(Integer.parseInt(cadena.substring(10, 19).trim()));
								}

								if (!(cadena.substring(19, 28).trim().equals(""))) {
									registroBeanFF.setNu_factura(Integer.parseInt(cadena.substring(19, 28).trim()));
								}

								registroBeanFF.setTp_registro(cadena.substring(28, 30).trim());
								registroBeanFF.setConsecNota(Integer.parseInt(cadena.substring(30, 34).trim()));
								registroBeanFF.setTp_carta(cadena.substring(34, 36).trim());
								registroBeanFF.setTp_pago(cadena.substring(36, 38).trim());
								registroBeanFF.setNu_proveedor(Integer.parseInt(cadena.substring(38, 48).trim()));
								registroBeanFF.setSociedadRec(cadena.substring(48, 52).trim());
								registroBeanFF.setGlg(cadena.substring(52, 102));

								if (!(cadena.substring(102, 104).trim().equals(""))) {
									registroBeanFF.setEmpGasBursa(cadena.substring(102, 104).trim());
								}

								if (!(cadena.substring(104, 125).trim().equals(""))) {
									registroBeanFF.setFideicomiso(cadena.substring(104, 125).trim());
								}

								if (!(cadena.substring(125, 145).trim().equals(""))) {
									registroBeanFF.setNu_acreditado(cadena.substring(125, 145).trim());
								}

								if (!(cadena.substring(145, 157).trim().equals(""))) {
									registroBeanFF.setNu_pep(cadena.substring(145, 157).trim());
								}

								registroBeanFF.setMondena(cadena.substring(157, 160).trim());

								if (!(cadena.substring(160, 162).trim().equals(""))) {
									registroBeanFF.setPeriodificacion(cadena.substring(160, 162).trim());
								}

								if (!(cadena.substring(162, 164).trim().equals(""))) {
									registroBeanFF.setProviEjerAnterior(cadena.substring(162, 164).trim());
								}

								if (!(cadena.substring(164, 174).trim().equals(""))) {
									registroBeanFF.setContrato(Integer.parseInt(cadena.substring(164, 174).trim()));
								}

								if (!(cadena.substring(174, 182).trim().equals(""))) {
									registroBeanFF
											.setRecAlternativo(Integer.parseInt(cadena.substring(174, 182).trim()));
								}

								registroBeanFF.setCuentaGps(Integer.parseInt(cadena.substring(182, 192).trim()));
								registroBeanFF.setUsuarioCreador(cadena.substring(192, 202).trim());
								registroBeanFF.setCentroCostos(cadena.substring(202, 206).trim());
								registroBeanFF.setDescripServicio(cadena.substring(206, 306).trim());
								registroBeanFF.setFechaInicio(cadena.substring(306, 316).trim());
								registroBeanFF.setFechaFin(cadena.substring(316, 326).trim());
								registroBeanFF.setEstado(Integer.parseInt(cadena.substring(326, 328).trim()));
								registroBeanFF.setImporteUn(new BigDecimal(cadena.substring(328, 340).trim()));
								registroBeanFF.setNu_unidades(new BigDecimal(cadena.substring(340, 353).trim()));
								registroBeanFF.setIva(cadena.substring(353, 365).trim());

								if (!(cadena.substring(365, 377).trim().equals(""))) {
									registroBeanFF.setIsrRetenido(new BigDecimal(cadena.substring(365, 377).trim()));
								}

								if (!(cadena.substring(377, 389).trim().equals(""))) {
									registroBeanFF.setIvaRetenido(new BigDecimal(cadena.substring(377, 389).trim()));
								}

								if (!(cadena.substring(389, 401).trim().equals(""))) {
									registroBeanFF
											.setImpuestoCedular(new BigDecimal(cadena.substring(389, 401).trim()));
								}

								if (!(cadena.substring(401, 413).trim().equals(""))) {
									registroBeanFF.setOtrosImpuestos(new BigDecimal(cadena.substring(401, 413).trim()));
								}

								if (!(cadena.substring(413, 425).trim().equals(""))) {
									registroBeanFF.setDescuento(new BigDecimal(cadena.substring(413, 425).trim()));
								}

								if (!(cadena.substring(425, 427).trim().equals(""))) {
									registroBeanFF.setComprobacion(cadena.substring(425, 427).trim());
								}

								if (!(cadena.substring(427, 457).trim().equals(""))) {
									registroBeanFF.setNu_anticipo(cadena.substring(427, 457).trim());
								}

								if (!(cadena.substring(457, 467).trim().equals(""))) {
									registroBeanFF.setFecha_anticipo(cadena.substring(457, 467).trim());
								}

								registroBeanFF.setViaP(cadena.substring(467, 468).trim());
								registroBeanFF.setCuentaBanc(Integer.parseInt(cadena.substring(468, 488).trim()));

								if (!(cadena.substring(488, 498).trim().equals(""))) {
									registroBeanFF.setTpBanco(cadena.substring(488, 498).trim());
								}

								if (!(cadena.substring(498, 518).trim().equals(""))) {
									registroBeanFF.setNu_activo(Integer.parseInt(cadena.substring(498, 518).trim()));
								}

								registroBeanFF.setEstatusF(cadena.substring(518, 521).trim());
								registroBeanFF.setAplicativoOrg(cadena.substring(521, 524).trim());
								beanFF.add(registroBeanFF);
								totalRegistroCorrectos++;
							} else {
								System.out.println(errorTipoD);
								// mandar a llamar el meto que escribira el error que contenga la fila.
								escribirArchivoError(rutaArchivoSBKP + nuevoNombreArc, cadena, errorTipoD);
							}
						} else {
							System.out.println(errorDatosD);
							// mandar a llamar el meto que escribira el error que contenga la fila.
							escribirArchivoError(rutaArchivoSBKP + nuevoNombreArc, cadena, errorDatosD);
						}
					} else {
						System.out.println(errorDatosO);
						// mandar a llamar el meto que escribira el error que contenga la fila.
						escribirArchivoError(rutaArchivoSBKP + nuevoNombreArc, cadena, errorDatosO);
					}
					
					System.out.println(beanFF.toString());

				} catch (Exception e) {
					e.printStackTrace();
					log.error("Error al al obtner informacíon de la linea" + linea + ": " + e.getMessage());
				}

			} else {

				escribirArchivoError(rutaArchivoSBKP + nuevoNombreArc, cadena, errorPosicionD);
				System.out.println(errorPosicionD);
			}

			if (errorPosicionD == "" && errorDatosO == "" && errorDatosD == "" && errorTipoD == "") {
				log.info("Procesar linea: " + linea
						+ " -> Metodo que validara si es el mismo consecutivo de carta y que los datos de carta y factura sean los mismos, para invocar el metodo de proceso a BD");
				//Validacion de carta consecutiva y facturas
				escribirArchivoCorrecto(rutaArchivoSBKP + nuevoNombreArc, cadena, 123456789, 22334455);
			}
		}

		copiarArchivo(archivo, rutaDirBkpE + nombreArchivo);
		moverArchivo(rutaArchivoSBKP + nuevoNombreArc, rutaDirS + nuevoNombreArc);

		log.info("Total de registros procesados: " + linea);
		log.info("Total de registros enviados a BD: " + totalRegistroCorrectos);
		log.info("Total de registros con error: " + totalRegistroError);
		br.close();

		log.info(
				"Nota -> Eliminar archivo de la carpeta Entrada y mover el archivo de la ruta BKPSalida a la carpta de Salida, ya que se creo un nuevo archivo para escribir los errres y los datos de lo que se obtenga del proceso a BD.");
		return null;
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
			fw.write(cadena.substring(0, 10) + numCarta + numFactura + cadena.substring(28, 524) + "\r\n");
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String removeUTF8BOM(String s) {
		if (s.startsWith(UTF8_BOM)) {
			s = s.substring(1);
		}
		return s;
	}

	public static HashMap<Integer, String> validarLongitudLinea(String cadena, int linea) {

		log.info("Validar Longitud de linea");
		HashMap<Integer, String> validaPosicionDato = new HashMap<Integer, String>();
		char[] arrayChar = cadena.toCharArray();
		int caracteres = arrayChar.length;
		if (caracteres == 524) {
			log.info("Longitud correcta de caracteres");
		} else {
			log.info("Longitud incorrecta de caracteres");
			validaPosicionDato.put(1, "La longuitud estalecida de caracteres no corresponde en la linea: " + linea);
		}

		return validaPosicionDato;
	}

	public static HashMap<Integer, String> validarDatosObligatorios(String cadena, int linea) {

		log.info("Validar Datos Obligatorios de linea");
		HashMap<Integer, String> validaCamposObli = new HashMap<Integer, String>();
		try {
			if (cadena.substring(0, 10).trim().equals("")) {
				validaCamposObli.put(1, "El campo \"CONSECUTIVO ARCHIVO\" es obligatorio en la linea: " + linea);
				return validaCamposObli;
			}

			if (cadena.substring(28, 30).trim().equals("")) {
				validaCamposObli.put(2, "El campo \"TIPO REGISTRO\" es obligatorio en la linea: " + linea);
				return validaCamposObli;
			}

			if (cadena.substring(30, 34).trim().equals("")) {
				validaCamposObli.put(3, "El campo \"CONSECUTIVO NOTA/CONCEPTO\" es obligatorio en la linea: " + linea);
				return validaCamposObli;
			}

			if (cadena.substring(34, 36).trim().equals("")) {
				validaCamposObli.put(4, "El campo \"TIPO CARTA\" es obligatorio en la linea: " + linea);
				return validaCamposObli;
			}

			if (cadena.substring(36, 38).trim().equals("")) {
				validaCamposObli.put(5, "El campo \"TIPO PAGO\" es obligatorio en la linea: " + linea);
				return validaCamposObli;
			}

			if (cadena.substring(38, 48).trim().equals("")) {
				validaCamposObli.put(6, "El campo \"NUMERO DE PROVEEDOR\" es obligatorio en la linea: " + linea);
				return validaCamposObli;
			}

			if (cadena.substring(48, 52).trim().equals("")) {
				validaCamposObli.put(7, "El campo \"SOCIEDAD RECEPTORA\" es obligatorio en la linea:" + linea);
				return validaCamposObli;
			}

			if (cadena.substring(52, 102).trim().equals("")) {
				validaCamposObli.put(8, "El campo \"GLG\" es obligatorio en la linea: " + linea);
				return validaCamposObli;
			}

			if (cadena.substring(157, 160).trim().equals("")) {
				validaCamposObli.put(9, "El campo \"MONEDA\" es obligatorio en la linea: " + linea);
				return validaCamposObli;
			}

			if (cadena.substring(182, 192).trim().equals("")) {
				validaCamposObli.put(10, "El campo \"CUENTA GPS(CUENTA DE GASTO)\" es obligatorio en la linea: " + linea);
				return validaCamposObli;
			}

			if (cadena.substring(192, 202).trim().equals("")) {
				validaCamposObli.put(11, "El campo \"USUARIO CREADOR DE CARTA\" es obligatorio en la linea: " + linea);
				return validaCamposObli;
			}

			if (cadena.substring(202, 206).trim().equals("") || cadena.substring(202, 206).trim().equals("0")) {
				validaCamposObli.put(12, "El campo \"CENTRO DE COSTOS\" es obligatorio en la linea: " + linea);
				return validaCamposObli;
			}

			if (cadena.substring(206, 306).trim().equals("")) {
				validaCamposObli.put(13, "El campo \"DESCRIPCIÓN DEL SERVICIO\" es obligatorio en la linea: " + linea);
				return validaCamposObli;
			}

			if (cadena.substring(306, 316).trim().equals("")) {
				validaCamposObli.put(14, "El campo \"FECHA INICIO SERVICIO\" es obligatorio en la linea: " + linea);
				return validaCamposObli;
			}

			if (cadena.substring(316, 326).trim().equals("")) {
				validaCamposObli.put(15, "El campo \"FECHA FIN SERVICIO\" es obligatorio en la linea: " + linea);
				return validaCamposObli;
			}

			if (cadena.substring(326, 328).trim().equals("")) {
				validaCamposObli.put(16, "El campo \"ESTADO\" es obligatorio en la linea: " + linea);
				return validaCamposObli;
			}

			if (cadena.substring(328, 340).trim().equals("")) {
				validaCamposObli.put(17, "El campo \"IMPORTE UNITARIO\" es obligatorio en la linea: " + linea);
				return validaCamposObli;
			}

			if (cadena.substring(340, 353).trim().equals("")) {
				validaCamposObli.put(18, "El campo \"NÚMERO DE UNIDADES\" es obligatorio en la linea: " + linea);
				return validaCamposObli;
			}

			if (cadena.substring(353, 365).trim().equals("")) {
				validaCamposObli.put(19, "El campo \"IVA\" es obligatorio en la linea: " + linea);
				return validaCamposObli;
			}

			if (cadena.substring(425, 427).trim().equals("SI") && cadena.substring(427, 457).trim().equals("")) {
				validaCamposObli.put(20,
						"El campo \"NUMERO DE ANTICIPO\" es obligatorio, ya que se informo con el valor SI el campo \"Comprobacion\" en la linea: " + linea);
				return validaCamposObli;
			}

			if (cadena.substring(425, 427).trim().equals("SI") && cadena.substring(457, 467).trim().equals("")) {
				validaCamposObli.put(21,
						"El campo \"FECHA DE ANTICIPO\" es obligatorio, ya que se informo con el valor SI el campo \"Comprobacion\" en la linea: " + linea);
				return validaCamposObli;
			}

			if (cadena.substring(467, 468).trim().equals("")) {
				validaCamposObli.put(22, "El campo \"VIA DE PAGO\" es obligatorio en la linea: " + linea);
				return validaCamposObli;
			}

			if (cadena.substring(468, 488).trim().equals("")) {
				validaCamposObli.put(23, "El campo \"CUENTA BANCARIA\" es obligatorio en la linea: " + linea);
				return validaCamposObli;
			}

			if (cadena.substring(518, 521).trim().equals("")) {
				validaCamposObli.put(24, "El campo \"ESTATUS FACTURA\" Factura es obligatorio en la linea: " + linea);
				return validaCamposObli;
			}

			if (cadena.substring(521, 524).trim().equals("")) {
				validaCamposObli.put(25, "El campo \"APLICATIVO ORIGEN\" es obligatorio en la linea: " + linea);
				return validaCamposObli;
			}
		} catch (StringIndexOutOfBoundsException e) {
			log.info("Error al obtner los datos de la posicion: " + e.getLocalizedMessage());
			validaCamposObli = null;
			return validaCamposObli;
		}
		return validaCamposObli;
	}

	public static HashMap<Integer, String> validarDatosDependientes(String cadena, int linea) {

		log.info("Validar Datos Dependientes de linea");
		HashMap<Integer, String> validaCamposDepen = new HashMap<Integer, String>();

		boolean validarNuPep = validaNupep(cadena.substring(145, 157).trim());
		if (validarNuPep) {
			validaCamposDepen.put(2,
					"El valor del campo \"NUMERO DE PEP\" es incorrecto, ya que no cuemple con el formato \"00000000-000\" en la linea: " + linea);
		}

		if ((!cadena.substring(174, 182).trim().equals(""))
				&& cadena.substring(174, 182).trim() == cadena.substring(38, 48).trim()) {
			validaCamposDepen.put(3,
					"El valor del campo \"RECEPTOR ALTERNATIVO\" debe de ser diferente al valor de \"NUMERO DE PROVEEDOR\" en la linea: " + linea);
		}

		boolean validarFechaFin = validaFecha(cadena.substring(306, 316).trim(), cadena.substring(316, 326).trim());
		if (validarFechaFin) {
			validaCamposDepen.put(4,
					"El valor del campo \"FECHA FIN SERVICIO\" debe ser mayor a la \"FECHA INICIO SERVICIO\" en la linea: " + linea);
		}

		if (cadena.substring(28, 30).trim().equals("NC") && cadena.substring(425, 427).trim().equals("SI")) {
			validaCamposDepen.put(5, "El valor: SI, del campo \"COMPROBACION\" solo aplica para \"TIPO REGISTRO: NF\" en la linea: " + linea);
		}

		return validaCamposDepen;
	}

	public static HashMap<Integer, String> validarTiposDato(String cadena, int linea) {

		log.info("Validar Tipo de Dato de linea");
		HashMap<Integer, String> validaTipoDato = new HashMap<Integer, String>();
		try {
			if (!(isNumeric(cadena.substring(0, 10).trim()))) {
				validaTipoDato.put(1, "El campo \"CONSECUTIVO ARCHIVO\" no es numerico en la linea: " + linea);
				return validaTipoDato;
			}

			if (!cadena.substring(10, 19).trim().equals("") && !(isNumeric(cadena.substring(10, 19).trim()))) {
				validaTipoDato.put(2, "El campo \"NUMERO DE CARTA\" no es numerico en la linea: " + linea);
				return validaTipoDato;
			}

			if (!cadena.substring(19, 28).trim().equals("") && !(isNumeric(cadena.substring(19, 28).trim()))) {
				validaTipoDato.put(3, "El campo \"NUMERO DE FACTURA\" no es numerico en la linea: " + linea);
				return validaTipoDato;
			}

			if (!(isNumeric(cadena.substring(30, 34).trim()))) {
				validaTipoDato.put(4, "El campo \"CONSECUTIVO NOTA\\CONCEPTO\" no es numerico en la linea: " + linea);
				return validaTipoDato;
			}

			if (!(isNumeric(cadena.substring(38, 48).trim()))) {
				validaTipoDato.put(5, "El campo \"NUMERO DE PROVEEDOR\" no es numerico en la linea: " + linea);
				return validaTipoDato;
			}

			if (!cadena.substring(164, 174).trim().equals("") && !(isNumeric(cadena.substring(164, 174).trim()))) {
				validaTipoDato.put(6, "El campo \"CONTRATO\" no es numerico en la linea: " + linea);
				return validaTipoDato;
			}

			if (!cadena.substring(174, 182).trim().equals("") && !(isNumeric(cadena.substring(174, 182).trim()))) {
				validaTipoDato.put(7, "El campo \"RECEPTOR ALTERNATIVO\" no es numerico en la linea: " + linea);
				return validaTipoDato;
			}

			if (!(isNumeric(cadena.substring(182, 192).trim()))) {
				validaTipoDato.put(8, "El campo \"CUENTA GPS\" no es numerico en la linea: " + linea);
				return validaTipoDato;
			}

			if (!(isNumeric(cadena.substring(326, 328).trim()))) {
				validaTipoDato.put(9, "El campo \"ESTADO\" no es numerico en la linea: " + linea);
				return validaTipoDato;
			}

			if (!(isDecimal(cadena.substring(328, 340).trim()))) {
				validaTipoDato.put(10, "El campo \"IMPORTE UNITARIO\" no es numerico en la linea: " + linea);
				return validaTipoDato;
			}

			if (!(isDecimal(cadena.substring(340, 353).trim()))) {
				validaTipoDato.put(11, "El campo \"NUMERO DE UNIDADES\" no es numerico en la linea: " + linea);
				return validaTipoDato;
			}

			if (!cadena.substring(365, 377).trim().equals("") && !(isDecimal(cadena.substring(365, 377).trim()))) {
				validaTipoDato.put(12, "El campo \"ISR RETENIDO\" no es numerico en la linea: " + linea);
				return validaTipoDato;
			}

			if (!cadena.substring(377, 389).trim().equals("") && !(isDecimal(cadena.substring(377, 389).trim()))) {
				validaTipoDato.put(13, "El campo \"IVA RETENIDO\" no es numerico en la linea: " + linea);
				return validaTipoDato;
			}

			if (!cadena.substring(389, 401).trim().equals("") && !(isDecimal(cadena.substring(389, 401).trim()))) {
				validaTipoDato.put(14, "El campo \"IMPUESTO CEDULAR\" no es numerico en la linea: " + linea);
				return validaTipoDato;
			}

			if (!cadena.substring(401, 413).trim().contentEquals("")
					&& !(isDecimal(cadena.substring(401, 413).trim()))) {
				validaTipoDato.put(15, "El campo \"OTROS IMPUESTOS\" no es numerico en la linea: " + linea);
				return validaTipoDato;
			}

			if (!cadena.substring(413, 425).trim().equals("") && !(isDecimal(cadena.substring(413, 425).trim()))) {
				validaTipoDato.put(16, "El campo \"DESCUENTO\" no es numerico en la linea: " + linea);
				return validaTipoDato;
			}

			if (!(isNumeric(cadena.substring(468, 488).trim()))) {
				validaTipoDato.put(17, "El campo \"CUENTA BANCARIA\" no es numerico en la linea: " + linea);
				return validaTipoDato;
			}

			if (!cadena.substring(498, 518).trim().equals("") && !(isNumeric(cadena.substring(498, 518).trim()))) {
				validaTipoDato.put(18, "El campo \"NUMERO DE ACTIVO\" no es numerico en la linea: " + linea);
				return validaTipoDato;
			}

		} catch (StringIndexOutOfBoundsException e) {
			log.info("Error al obtner los datos de la posicion: " + e.getLocalizedMessage());
		}
		return validaTipoDato;
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
			log.error("Error al validar la Fecha Inicio y Final.");
			e.printStackTrace();
			return fechaValida;
		}
		return fechaValida;
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

	public void moverArchivo(String archivoOrigen, String archvoDestino) {
		Path FROM = Paths.get(archivoOrigen);
		Path TO = Paths.get(archvoDestino);

		CopyOption[] options = new CopyOption[] { StandardCopyOption.REPLACE_EXISTING };
		try {
			Files.move(FROM, TO, options);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void copiarArchivo(String archivoOrigen, String archvoDestino) {
		Path FROM = Paths.get(archivoOrigen);
		Path TO = Paths.get(archvoDestino);

		CopyOption[] options = new CopyOption[] { StandardCopyOption.REPLACE_EXISTING };
		try {
			Files.copy(FROM, TO, options);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
