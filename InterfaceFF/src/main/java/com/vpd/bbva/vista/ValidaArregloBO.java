package main.java.com.vpd.bbva.vista;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import main.java.com.vpd.bbva.bean.BeanConceptoFin;
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
		
		String existe = validaDB.existe(beanFF.getTp_registro());
		
		if(existe != null) { 
			String notaFactura = validaDB.parametro(8, beanFF.getTp_registro());
			respReturn.setTp_registro(notaFactura);
			
			while(beanFF.getTp_registro().equals(notaFactura)) { /** NF */	
			if(beanFF.getNu_carta() > 0) {				//Carta informada
				DatosCarta = validaDB.ValidaCarta(new Integer(beanFF.getNu_carta())); //VALIDACION DE CARTA
						if(DatosCarta.getContinua()==true) {										/*bandera de que existe la carta*/
							respReturn = vdatosc.ComparaArrevsDBCarta(arregloArchivo, DatosCarta ,notaFactura);	//Valida si los datos de la carta en DB son iguales a los de la interface
							return respReturn;
						}else {
							respReturn.setBandera(false);
							respReturn.setMensaje(DatosCarta.getDescError());
							respReturn.setConsecutivoA(consecutivoA);
							return respReturn;
							  }
			}else {
				/*DATOS PARA CREAR UNA NUEVA CARTA*/
				respReturn =validaDB.DatosNvaCarta(arregloArchivo, notaFactura);  //Carta no informada, validar para crear una nueva
				respReturn.setConsecutivoA(consecutivoA);
				respReturn.setTp_registro(notaFactura);
				return respReturn;
				}

			}
		}else {
			respReturn.setBandera(false);
			respReturn.setMensaje("TIPO REGISTRO " + beanFF.getTp_registro() + "NO EXISTE");
			respReturn.setConsecutivoA(consecutivoA);
			return respReturn;
		}++consecutivoA;
	}
	
}catch (Exception e) {
		LOG.warn("Error "+e);	 
		}
		
		return respReturn;
	}
	
	
	public BeanRespuesta ValidaFactura(List<BeanFF> factura) throws Exception{
		ValidaGeneralDatosDB validaDB = new ValidaGeneralDatosDB();
		BeanRespuesta BeanRes = new BeanRespuesta();
		BeanFF beanDB = new BeanFF();
		InsertaDao dao = new InsertaDao();
		ValidaDatos validaFac = new ValidaDatos();
		LlenaObj objDB = new LlenaObj();
		int consecutivoA = 0;
		boolean validacion = false;
		for(BeanFF leeF : factura) {
			if(validacion) {
				break;
			}
			BeanRes.setCarta(leeF.getNu_carta());
			
			String existe = validaDB.existe(leeF.getTp_registro());
			if(existe != null) {
				String notaFactura = validaDB.parametro(8, leeF.getTp_registro());
				
				if(leeF.getNu_factura()>0) {  /** Existe la Factura */
				while(leeF.getTp_registro().equals(notaFactura)) {   /**NC*/
					
											
						int facturaI = leeF.getNu_factura();
						int cartaI = leeF.getNu_carta();
						int proveedor = leeF.getNu_proveedor();
						String sociedad = leeF.getSociedadRec();
						String viaP = leeF.getViaP();
						 /* VALIDAR DATOS DE LA FACTURA QUE VIENEN EN EL LAY OUT */
						BeanFacturaNVA beanFacNdb = validaDB.DatosFactura(facturaI, cartaI, proveedor, sociedad,viaP);				/** Validar que la factura exista en la DB */
						/* OK */	
						if(beanFacNdb.getExito() == 0 ) {  	
									/* LLENAR OBJETO DE DE FACTURA NUEVA*/
									BeanFacturaNVA beanFacN = objDB.llenaFacNva(factura, notaFactura);
									/* COMPARAR DATOS DE LA FACTURA QUE VIENEN EN EL LAY OUT VS BASE DE DATOS*/
								BeanRes = validaFac.ComparaArrevsDBFactura(beanFacN, beanFacNdb); 
									/* SI ES OK */
									if(BeanRes.GetBandera()==true) {
										BeanRes.setFactura(facturaI);
										BeanRes.setBandera(true);
										BeanRes.setConsecutivoA(consecutivoA);
										validacion = true;
									}else {
										BeanRes.setConsecutivoA(consecutivoA);
										validacion = true;
									}
							}else {
								BeanRes.setBandera(false);
								BeanRes.setMensaje(beanFacNdb.getMensaje());
								BeanRes.setConsecutivoA(consecutivoA);
								validacion = true;
							}
						break;
				}
				
					}else {													/** Validacion para crear Factura nueva */
						/* VALIDACION DE DATOS PARA CREAR UNA NUEVA FACTURA */
						while(leeF.getTp_registro().equals(notaFactura)) {   /**NF*/
							BeanRes = objDB.llenaFacConNva(factura,notaFactura); 		/** Validacion de Concepto **/
							validacion = true;
							break;
						}
						}
			
		}else {
				BeanRes.setBandera(false);
				BeanRes.setMensaje("TIPO REGISTRO " + leeF.getTp_registro() + "NO EXISTE");
				BeanRes.setConsecutivoA(consecutivoA);
				validacion = true;
				break; 
			}
			++consecutivoA;
	}return BeanRes;
	}
	
	
	public List<BeanRespuesta> validaInserNotaCredito (List<BeanFF> notaC, int carta, int factura) throws Exception {
		List<BeanRespuesta> notas = new ArrayList<BeanRespuesta>();
		BeanRespuesta respNota = new BeanRespuesta();
		int consecutivoA = 0;
		ValidaGeneralDatosDB dao = new ValidaGeneralDatosDB();
		ValidaGeneralDatosDB valida = new ValidaGeneralDatosDB(); 
		for(BeanFF leeNC: notaC) {
			try{
			
			String existe = dao.existe(leeNC.getTp_registro());
			if(existe != null) {
				String notaCredito = dao.parametro(10, leeNC.getTp_registro());
				int numFila = 0;
				while(leeNC.getTp_registro().equals(notaCredito)) {
					InsertaDao insert = new InsertaDao();
					
					/*CREAR NOTA DE CREDITO*/
					
					boolean exito = false;
					/*EN NOTA DE CREDITO SOLO SE VALIDA EL IVA EN LA DB ANTES DE CREARLA*/
					int estado = 0;      
					int iva    = leeNC.getIva();
					respNota = valida.ValidaDatosConcep(estado, iva);
					if(respNota.GetBandera()) {
						/* 1 Insertar posicion fin */
						
						LlenaObj llenaNotaC = new LlenaObj();
						BeanPosicionFin nota;
						
						if(consecutivoA==0) {
						/*VALIDACIONES DE NEGOCIO*/
							int param = 6;
							nota =  llenaNotaC.llenaPosicionFNotaC(notaC, notaCredito, numFila, carta, factura,param);
						boolean consulta = false;
						
							/*CONSULTAR EL TOTAL DE LA FACTURA*/
							ValidaGeneralDatosDB consulTotal = new ValidaGeneralDatosDB();
							BigDecimal totalFactura = null;
							BigDecimal bdIsrRetenidoNF = null;
							BigDecimal bdIvaRetenidoNF = null;
							BigDecimal bdImpuestoCedularNF = null;
							BigDecimal bdOtrosImpuestosNF = null;
							ArrayList<BigDecimal> totalFac = consulTotal.totalNF(factura); /*TOTAL DE LA FACTURA*/
							
							for (int i=0; i<totalFac.size(); i++) {
								totalFactura = totalFac.get(0);
								bdIsrRetenidoNF = totalFac.get(1);
								bdIvaRetenidoNF = totalFac.get(2);
								bdImpuestoCedularNF = totalFac.get(3);
								bdOtrosImpuestosNF = totalFac.get(4);
							}
						
							
						
						BigDecimal totalCredito = null ;   /*SUMA TOTAL DE TODAS LAS NOTAS DE CREDITO*/
						BigDecimal impIVA = nota.getIm_iva();			/*IMPORTE DEL IVA DE LA NOTA DE CREDITO*/
						BigDecimal unidades = nota.getNu_unidad();		/* No. UDIDADES DE LA NOTA DE CREDITO*/
						BigDecimal importSinIVA = nota.getIm_sin_iva();	/* IMPORTE SIN I.V.A.*/
						
						totalCredito = totalCredito.add(unidades).multiply(importSinIVA).add(impIVA);
						
						/*Total Credito < = Total Factura*/
						if(totalCredito.compareTo(totalFactura) == 1 || totalCredito.compareTo(totalFactura) == -1) {
						
							
							/* CALCULOS DE NEGOCIO */
							/* RETENCIONES = ISR RETENIDO + IVA RETENIDO + IMPUESTO CEDULAR */
							/* TOTAL DE CREDITO = (UNIDADES * IMPORTE SIN IVA) + IVA */
							
							BigDecimal retencion = leeNC.getIsrRetenido().add(leeNC.getIvaRetenido().add(leeNC.getImpuestoCedular()));
							
							/* RETENCIONES <= TOTAL CREDITO */
							if (retencion.compareTo(totalCredito) == -1 || retencion.compareTo(totalCredito) == 1) {
								
							
							
						/*CREAR NOTA DE CREDITO*/
						exito = insert.insertaConceptoNCFin(nota,factura);
							if(exito) {
								
							
								/*CALCULAR IMPORTES DE FACTURA*/
								insert.calculaImportesFac(factura, bdIsrRetenidoNF, bdIvaRetenidoNF, bdImpuestoCedularNF, bdOtrosImpuestosNF, 
								leeNC.getIsrRetenido(),	leeNC.getIvaRetenido(), leeNC.getImpuestoCedular(), leeNC.getOtrosImpuestos());
							
								respNota.setBandera(true);
								respNota.setCarta(carta);
								respNota.setFactura(factura);
								respNota.setMensaje("NOTA DE CREDITO CREADA");
								respNota.setConsecutivoA(consecutivoA);
								notas.add(respNota);
							}else {
								String msjError = "ERROR AL CREAR NOTA CREDITO";
								respNota.setBandera(false);
								respNota.setMensaje(msjError);
								respNota.setConsecutivoA(consecutivoA);
								notas.add(respNota);
								
							}
					}else {
						String msjError = "LOS MONTOS GENERAN UN TOTAL DE CREDITO MENOR A CERO";
						respNota.setBandera(false);
						respNota.setMensaje(msjError);
						respNota.setConsecutivoA(consecutivoA);
						notas.add(respNota);
					}
					}else {
						String msjError = "LOS CALCULOS GENERAN UN TOTAL DE CREDITO MAYOR AL TOTAL DE FACTURA";
						respNota.setBandera(false);
						respNota.setMensaje(msjError);
						respNota.setConsecutivoA(consecutivoA);
						notas.add(respNota);
					}
						}else {
							/*CREAR CONCEPTO DE NOTA CREDITO*/
							int param = 14;
							nota =  llenaNotaC.llenaPosicionFNotaC(notaC, notaCredito, numFila, carta, factura,param);
							HashMap<String, Object> posicion = insert.insertaPosicionFinanciera(nota);
							
							String nuPos= posicion.get("nu_posicion_F").toString();
							Integer ok  = new Integer(posicion.get("exito").toString());
							if(ok == 0) {
								
							
								BeanConceptoFin concFin = llenaNotaC.llenaConceptoF(leeNC.getUsuarioCreador(), factura, carta, new Integer(nuPos), nota.getNu_nota()-1) ;
								Integer proceso = insert.insertConceptoFinan(concFin);
								if(proceso > 1) {
									/*CALCULAR IMPORTES DE FACTURA*/
									respNota.setBandera(true);
									respNota.setCarta(carta);
									respNota.setFactura(factura);
									respNota.setMensaje("CONCEPTO DE NOTA DE CREDITO CREADO");
									respNota.setConsecutivoA(consecutivoA);
									notas.add(respNota);
								}else {
									respNota.setBandera(false);
									respNota.setMensaje("ERROR AL GUARDAR NUEVO CONCEPTO DE NC");
									respNota.setConsecutivoA(consecutivoA);
									notas.add(respNota);
								}
							
							}else {
								respNota.setBandera(false);
								respNota.setMensaje("ERROR AL GUARDAR CONCEPTO DE NC" + nuPos);
								respNota.setConsecutivoA(consecutivoA);
								notas.add(respNota);
							}
							
							
						}
					}else {
						respNota.setConsecutivoA(consecutivoA);
						notas.add(respNota);
					}
					
					++numFila;	
				
				}
			}else {
				respNota.setBandera(false);
				respNota.setMensaje("TIPO REGISTRO " + leeNC.getTp_registro() + "NO EXISTE");
				respNota.setConsecutivoA(consecutivoA);
				notas.add(respNota);
			}
				
			
			++consecutivoA;
			
		}catch (Exception e) {
			respNota.setBandera(false);
			respNota.setMensaje("ERROR AL CREAR LA NOTA DE CREDITO");
		}
		
		}return notas;
	}
	
	
}
	

