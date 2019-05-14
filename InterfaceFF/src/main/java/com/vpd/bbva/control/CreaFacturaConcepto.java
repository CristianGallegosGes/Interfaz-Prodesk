package main.java.com.vpd.bbva.control;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import main.java.com.vpd.bbva.bean.BeanConceptoFin;
import main.java.com.vpd.bbva.bean.BeanFF;
import main.java.com.vpd.bbva.bean.BeanFactura;
import main.java.com.vpd.bbva.bean.BeanNota;
import main.java.com.vpd.bbva.bean.BeanRespuesta;
import main.java.com.vpd.bbva.modelo.InsertaDao;

public class CreaFacturaConcepto {
	
	public BeanRespuesta creaFactura(List<BeanFF> BloqueFac, int carta) throws Exception {
		BeanRespuesta respEjec = new BeanRespuesta();
		InsertaDao dao = new InsertaDao();
		LlenaObj llenaObj = new LlenaObj();
		int exito = 0;
		String descOpera = null;
		int nuPosFin ;
		String nf = null;
		String error = null;
		String posicion = null;
		/** Insertar posicion fin */
		try {
		ArrayList<HashMap<String, Object>> posicFin = llenaObj.llenaPosicionF(BloqueFac, carta);
			for (HashMap<String, Object> control : posicFin) {
				exito = Integer.parseInt(control.get("exito").toString());
				nf = (String) control.get("notaFactura");
				error = control.get("nu_posicion_F").toString();
				posicion = (control.get("posicion").toString());
				if(exito > 0) {
					respEjec.setBandera(false);
					respEjec.setMensaje(error);
					respEjec.setConsecutivoA(Integer.parseInt(posicion));
					return respEjec;
				}
				
			}
				if(exito == 0) {
					/** Inserta factura */
					BeanFactura beanFactura = llenaObj.llenaFactura(BloqueFac, carta, nf);
						String usuario = beanFactura.getCd_usr_modifica();
					HashMap<String, Object> inserFac = dao.inserFactura(beanFactura);
						exito = Integer.parseInt(inserFac.get("exito").toString());
						descOpera = inserFac.get("error").toString();
						
						if(exito == 0) {
							int nuFactura = Integer.parseInt(inserFac.get("factura").toString());
							
							/** Inserta Nota de factura*/
							BeanNota beaNota = llenaObj.llenaNota(BloqueFac, nuFactura, nf);
							Integer inserNotaF = dao.InsertNota(beaNota);
							int nu_nota = beaNota.getNu_nota();
							
							if (inserNotaF == 0) {
								
								for(HashMap<String, Object> listPosF: posicFin) {
									int conArchivo = 0;
									
									nuPosFin = Integer.parseInt(listPosF.get("nu_posicion_F").toString());
									 
									/** Inserta Concepto */
										BeanConceptoFin beanConF = llenaObj.llenaConceptoF(usuario, nuFactura, carta, nuPosFin, nu_nota);
										Integer inserConcFin = dao.insertConceptoFinan(beanConF);
										
										/*CALCULAR IMPORTES, SE INVOCA AL METODO CADA VEZ QUE SE INSERTA UN COCEPTO*/
										InsertaDao insert = new InsertaDao();
										insert.calculaImportesFac(nuFactura,new BigDecimal("0"), new BigDecimal("0"), new BigDecimal("0"), 
												new BigDecimal("0"), new BigDecimal("0"), new BigDecimal("0"), new BigDecimal("0"), 
												new BigDecimal("0"));  
										
								}
								String tpBanco = null;
								String viaPa = null;
								boolean run = false;
								for(BeanFF bean : BloqueFac) {
									if (run) {
										break;
									}
									while(bean.getTp_registro().equals(nf)) {
										viaPa = bean.getViaP();	
										tpBanco = bean.getTpBanco();
										run = true;
									break;
									}
								}
								
								if(viaPa != null || viaPa.isEmpty()) {
									
										Integer viaPagoInser = dao.insertViaP(nuFactura,viaPa, tpBanco );
										
										if(viaPagoInser == 0) {
											/**      OK    */
											respEjec.setBandera(true);
											respEjec.setFactura(nuFactura);
											respEjec.setCarta(carta);
										}else {
											respEjec.setBandera(true);
											respEjec.setMensaje("No se inserto via de pago" ); // para prueba
											respEjec.setFactura(nuFactura);
											respEjec.setCarta(carta);
										}
										
								}else {
									/**      OK    */
									respEjec.setBandera(true);
									respEjec.setFactura(nuFactura);
									respEjec.setCarta(carta);
								}
							}else {
								respEjec.setBandera(false);
								respEjec.setMensaje("Error al insertar la factura" + descOpera); // para prueba
							}
							
						}else {
							respEjec.setBandera(false);
							respEjec.setMensaje("Error al insertar la factura" + descOpera); // para prueba
						}
				}else {
					respEjec.setBandera(false);
					respEjec.setMensaje("Error al insertar posicion de la factura" + error); // para prueba
					respEjec.setConsecutivoA(Integer.parseInt(posicion));
				}
	}catch (Exception e) {
			// TODO: handle exception
		}
		return respEjec;
	}
	
}
