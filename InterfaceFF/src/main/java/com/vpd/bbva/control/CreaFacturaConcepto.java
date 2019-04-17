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
	
	public BeanRespuesta creaFactura(List<BeanFF> BloqueFac, int carta) throws SQLException {
		List<BeanRespuesta> listaBeanRes = new ArrayList<BeanRespuesta>();
		BeanRespuesta respEjec = new BeanRespuesta();
		InsertaDao dao = new InsertaDao();
		LlenaObj llenaObj = new LlenaObj();
		int exito = 0;
		String descOpera = null;
		int nuPosFin ;
		
		/** Insertar posicion fin */
		ArrayList<HashMap<String, Object>> posicFin = llenaObj.llenaPosicionF(BloqueFac, carta);
			
				if(exito == 0) {
					
					/** Inserta factura */
					BeanFactura beanFactura = llenaObj.llenaFactura(BloqueFac, carta);
					HashMap<String, Object> inserFac = dao.inserFactura(beanFactura);
						exito = Integer.parseInt(inserFac.get("exito").toString());
						descOpera = inserFac.get("error").toString();
						if(exito == 0) {
							int nuFactura = Integer.parseInt(inserFac.get("factura").toString());
							
							/** Inserta Nota de factura*/
							BeanNota beaNota = llenaObj.llenaNota(BloqueFac, nuFactura);
							Integer inserNotaF = dao.InsertNota(beaNota);
							int nu_nota = beaNota.getNu_nota();
							if (inserNotaF == 0) {
								
								for(HashMap<String, Object> listPosF: posicFin) {
									int conArchivo = 0;
									exito = Integer.parseInt(listPosF.get("exito").toString());
									if(exito == 0) {
									nuPosFin = Integer.parseInt(listPosF.get("nu_posicion_F").toString());
									    
									/** Inserta Concepto */
										BeanConceptoFin beanConF = llenaObj.llenaConceptoF(BloqueFac, nuFactura, carta, nuPosFin, nu_nota);
										Integer inserConcFin = dao.insertConceptoFinan(beanConF);
									}else {
										System.out.println("Error al insertar concepto en la factura");// para prueba
										respEjec.setBandera(false);
										respEjec.setMensaje("Error al insertar concepto en la factura" ); 
										respEjec.setFactura(nuFactura);
										respEjec.setConsecutivoA(conArchivo);
										listaBeanRes.add(respEjec);
										
									}
								}
								
								
								String moneda = null;
								String sociedad = null;
								int nu_proveedor = 0;
								
								for(BeanFF bean : BloqueFac) {
									moneda = bean.getMondena();
									sociedad = bean.getSociedadRec();
									nu_proveedor = bean.getNu_proveedor();
									break;
								}
								
								Integer viaP = dao.insertViaP(nuFactura, moneda, sociedad, nu_proveedor);
								
								if(viaP == 0) {
									/**      OK    */
									respEjec.setBandera(true);
									respEjec.setFactura(nuFactura);
								}else {
									respEjec.setBandera(false);
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
					respEjec.setMensaje("Error al insertar la factura" + exito); // para prueba
				}
		return respEjec;
	}
	
}
