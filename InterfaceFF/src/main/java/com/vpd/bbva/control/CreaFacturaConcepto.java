package main.java.com.vpd.bbva.control;

import java.sql.SQLException;
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
		int posicion = 0;
		/** Insertar posicion fin */
		ArrayList<HashMap<String, Object>> posicFin = llenaObj.llenaPosicionF(BloqueFac, carta);
			for (HashMap<String, Object> control : posicFin) {
				exito = Integer.parseInt(control.get("exito").toString());
				nf = (String) control.get("notaFactura");
				error = control.get("nu_posicion_F").toString();
				posicion = new Integer(control.get("posicion").toString());
				if(exito > 0) {
					break;
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
								}
								String moneda = null;
								String sociedad = null;
								int nu_proveedor = 0;
								String tpBanco = null;
								
								for(BeanFF bean : BloqueFac) {
									while(bean.getTp_registro().equals(nf)) {
									moneda = bean.getMondena();
									sociedad = bean.getSociedadRec();
									nu_proveedor = bean.getNu_proveedor();
									tpBanco = bean.getTpBanco();
									break;
									}
								}
								
								Integer viaP = dao.insertViaP(nuFactura, moneda, sociedad, nu_proveedor);
								
								if(viaP == 0) {
									/**      OK    */
									respEjec.setBandera(true);
									respEjec.setFactura(nuFactura);
								}else {
									respEjec.setBandera(true);
									respEjec.setMensaje("No se pudo insertar la via de pago" ); // para prueba
									respEjec.setFactura(nuFactura);
								}
							}
							
						}else {
							respEjec.setBandera(false);
							respEjec.setMensaje("Error al insertar la factura" + descOpera); // para prueba
						}
				}else {
					respEjec.setBandera(false);
					respEjec.setMensaje("Error al insertar posicion de la factura" + error); // para prueba
					respEjec.setConsecutivoA(posicion);
				}
		return respEjec;
	}
	
}
