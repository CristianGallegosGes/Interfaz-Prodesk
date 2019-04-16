package main.java.com.vpd.bbva.control;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import main.java.com.vpd.bbva.bean.BeanConceptoFin;
import main.java.com.vpd.bbva.bean.BeanFF;
import main.java.com.vpd.bbva.bean.BeanFactura;
import main.java.com.vpd.bbva.bean.BeanNota;
import main.java.com.vpd.bbva.bean.BeanPosicionFin;
import main.java.com.vpd.bbva.bean.BeanRespuesta;
import main.java.com.vpd.bbva.modelo.InsertaDao;

public class CreaFacturaConcepto {
	
	public BeanRespuesta creaFactura(List<BeanFF> BloqueFac, int carta) throws SQLException {
		BeanRespuesta respEjec = new BeanRespuesta();
		InsertaDao dao = new InsertaDao();
		LlenaObj llenaObj = new LlenaObj();
		int exito = 0;
		String descOpera = null;
		
		/** Insertar posicion fin */
		BeanPosicionFin posicFin = llenaObj.llenaPosicionF(BloqueFac, carta);
		HashMap<String, Object> inserPos = dao.insertaPosicionFinanciera(posicFin);
			exito = Integer.parseInt(inserPos.get("exito").toString());
			int nuPosFin = Integer.parseInt(inserPos.get("nuPosFin").toString());
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
								
								/** Inserta Concepto */
								BeanConceptoFin beanConF = llenaObj.llenaConceptoF(BloqueFac, nuFactura, carta, nuPosFin, nu_nota);
								Integer inserConcFin = dao.insertConceptoFinan(beanConF);
								
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
									respEjec.setFactua(nuFactura);
								}else {
									respEjec.setBandera(false);
									respEjec.setMensaje("No se pudo insertar la via de pago" ); // para prueba
									respEjec.setFactua(nuFactura);
								}
							}
							
						}else {
							respEjec.setBandera(false);
							respEjec.setMensaje("Error al insertar la factura" + descOpera); // para prueba
						}
				}else {
					respEjec.setBandera(false);
					nuPosFin = Integer.parseInt(inserPos.get("nuPosFin").toString()); /* tipo de error*/
					respEjec.setMensaje("Error al insertar la factura" + nuPosFin); // para prueba
				}
		return respEjec;
	}
	
}
