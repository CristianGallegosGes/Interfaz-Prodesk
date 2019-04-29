package main.java.com.vpd.bbva.vista;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import main.java.com.vpd.bbva.bean.BeanFF;
import main.java.com.vpd.bbva.bean.BeanFacturaNVA;
import main.java.com.vpd.bbva.bean.BeanNotaCreFin;
import main.java.com.vpd.bbva.bean.BeanPosicionFin;
import main.java.com.vpd.bbva.bean.BeanRespuesta;
import main.java.com.vpd.bbva.control.LlenaObj;
import main.java.com.vpd.bbva.modelo.InsertaDao;
import main.java.com.vpd.bbva.modelo.ValidaDatos;
import main.java.com.vpd.bbva.modelo.ValidaGeneralDatosDB;

public class ValidaArregloBO {
	public static final Logger LOG = Logger.getLogger(ValidaArregloBO.class);
	
public BeanRespuesta ValidaCarta (List<BeanFF> arregloArchivo) throws Exception{
	BeanFF DatosCarta = new BeanFF();
	BeanRespuesta respReturn = new BeanRespuesta();
	ValidaGeneralDatosDB validaDB = new ValidaGeneralDatosDB();
	ValidaDatos vdatosc = new ValidaDatos();
		
try {
	int consecutivoA = 0;
	for (BeanFF beanFF : arregloArchivo) {
		++consecutivoA;
		String existe = validaDB.existe(beanFF.getTp_registro());
		
		if(existe != null) {
			String notaFactura = validaDB.parametro(8, beanFF.getTp_registro());
			respReturn.setTp_registro(notaFactura);
			
			while(beanFF.getTp_registro().equals(notaFactura)) { /** NF */	
			if(beanFF.getNu_carta() > 0) {				//Carta informada
				DatosCarta = validaDB.ValidaCarta(new Integer(beanFF.getNu_carta())); //VALIDACION DE CARTA
						if(DatosCarta.getContinua()==true) {										/*bandera de que existe la carta*/
							respReturn = vdatosc.ComparaArrevsDBCarta(arregloArchivo, DatosCarta ,notaFactura);	//Valida si los datos de la carta en DB son iguales a los de la interface
							
						}else {
							respReturn.setBandera(false);
							respReturn.setMensaje(DatosCarta.getDescError());
							respReturn.setConsecutivoA(consecutivoA);
							return respReturn;
						}
					}		
			else {
				respReturn.setConsecutivoA(consecutivoA);
				respReturn =validaDB.DatosNvaCarta(arregloArchivo, notaFactura);  //Carta no informada, validar para crear una nueva
				return respReturn;
			}
			break;  /*se termina de leer el for porque solo es una carta por bloque, si vuelve a leer, leeria la misma carta*/
			}
		}else {
			respReturn.setBandera(false);
			respReturn.setMensaje("TIPO REGISTRO " + beanFF.getTp_registro() + "NO EXISTE");
			respReturn.setConsecutivoA(consecutivoA);
		}
	}
	
}catch (Exception e) {
		LOG.warn("Error "+e);	 
		}
		return respReturn;
	}
	
	
	public BeanRespuesta ValidaFactura(List<BeanFF> factura) throws SQLException{
		ValidaGeneralDatosDB validaDB = new ValidaGeneralDatosDB();
		BeanRespuesta BeanRes = new BeanRespuesta();
		BeanFF beanDB = new BeanFF();
		InsertaDao dao = new InsertaDao();
		ValidaDatos validaFac = new ValidaDatos();
		LlenaObj objDB = new LlenaObj();
		int consecutivoA = 0;
		for(BeanFF leeF : factura) {
			++consecutivoA;
			String existe = validaDB.existe(leeF.getTp_registro());
			if(existe != null) {
				String notaCredito = validaDB.parametro(10, leeF.getTp_registro());
				
				if(leeF.getNu_factura()>0) {  /** Existe la Factura */
				while(leeF.getTp_registro().equals(notaCredito)) {   /**NC*/
					
											
						int facturaI = leeF.getNu_factura();
						int cartaI = leeF.getNu_carta();
						int proveedor = leeF.getNu_proveedor();
						String sociedad = leeF.getSociedadRec();
						String viaP = leeF.getViaP();
						
						BeanFacturaNVA beanFacNdb = validaDB.DatosFactura(facturaI, cartaI, proveedor, sociedad,viaP);				/** Validar que la factura exista en la DB */
							if(beanFacNdb.getExito() == 0 ) {  			/* OK */
									BeanFacturaNVA beanFacN = objDB.llenaFacNva(factura, notaCredito);
								BeanRes = validaFac.ComparaArrevsDBFactura(beanFacN, beanFacNdb); 
									if(BeanRes.GetBandera()==true) {
										BeanRes.setFactura(facturaI);
										BeanRes.setBandera(true);
										BeanRes.setConsecutivoA(consecutivoA);
									}else {
										BeanRes.setConsecutivoA(consecutivoA);
										
									}
							}else {
								BeanRes.setBandera(false);
								BeanRes.setMensaje(beanFacNdb.getMensaje());
								BeanRes.setConsecutivoA(consecutivoA);
								
							}
				}
				
					}else {													/** Validacion para crear Factura nueva */
						String notaFactura = validaDB.parametro(8, leeF.getTp_registro());
						while(leeF.getTp_registro().equals(notaFactura)) {   /**NF*/
							BeanRes = objDB.llenaFacConNva(factura,notaFactura); 		/** Validacion de Concepto **/
							break;
						}
						}
			
		}else {
				BeanRes.setBandera(false);
				BeanRes.setMensaje("TIPO REGISTRO " + leeF.getTp_registro() + "NO EXISTE");
				BeanRes.setConsecutivoA(consecutivoA);
				break; 
			}
		
	}return BeanRes;
	}
	
	
	public List<BeanRespuesta> validaInserNotaCredito (List<BeanFF> notaC, int carta, int factura) throws Exception {
		List<BeanRespuesta> notas = new ArrayList<BeanRespuesta>();
		BeanRespuesta respNota = new BeanRespuesta();
		int consecutivoA = 0;
		ValidaGeneralDatosDB dao = new ValidaGeneralDatosDB();
		ValidaGeneralDatosDB valida = new ValidaGeneralDatosDB(); 
		for(BeanFF leeNC: notaC) {
			++consecutivoA;
			String existe = dao.existe(leeNC.getTp_registro());
			if(existe != null) {
				String notaCredito = dao.parametro(10, leeNC.getTp_registro());
				int numFila = 0;
				while(leeNC.getTp_registro().equals(notaCredito)) {
					InsertaDao insert = new InsertaDao();
					++numFila;
					boolean exito = false;
					int estado = leeNC.getEstado();
					int iva    = leeNC.getIva();
					respNota = valida.ValidaDatosConcep(estado, iva);
					if(respNota.GetBandera()) {
						/** 1 Insertar posicion fin */
						
						LlenaObj llenaNotaC = new LlenaObj();
						BeanPosicionFin nota =  llenaNotaC.llenaPosicionFNotaC(notaC, notaCredito, numFila, carta, factura);
						/***VALIDACIONES DE NEGOCIO*/
						
						
						
						exito = insert.insertaConceptoNCFin(nota,factura);
							if(exito) {
								respNota.setBandera(true);
								respNota.setCarta(carta);
								respNota.setFactura(factura);
								respNota.setMensaje("NOTA DE CREDITO CREADA");
								respNota.setConsecutivoA(consecutivoA);
								notas.add(respNota);
							}else {
								String msjError = "ERROR AL CREAR NOTA CREDITO";
								respNota.setMensaje(msjError);
								respNota.setConsecutivoA(consecutivoA);
								notas.add(respNota);
							}
						
						
						
						respNota.setBandera(true);						
						
						/**consultar el total de la factura*/
						float totalFact;
						
						
					}else {
						respNota.setConsecutivoA(consecutivoA);
						notas.add(respNota);
					}
				}
			}else {
				respNota.setBandera(false);
				respNota.setMensaje("TIPO REGISTRO " + leeNC.getTp_registro() + "NO EXISTE");
				respNota.setConsecutivoA(consecutivoA);
				notas.add(respNota);
			}
		}
		return notas;
}
	
}
