package main.java.com.vpd.bbva.control;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import main.java.com.vpd.bbva.bean.BeanFF;
import main.java.com.vpd.bbva.bean.BeanRespuesta;
import main.java.com.vpd.bbva.modelo.InsertaDao;

public class CreaFacturaConcepto {
	
	public BeanRespuesta creaFactura(List<BeanFF> BloqueFac, int carta) throws SQLException {
		BeanRespuesta respEjec = new BeanRespuesta();
		InsertaDao dao = new InsertaDao();
		LlenaObj llenaObj = new LlenaObj();
		int exito = 0;
		
		/** Insertar posicion fin */
		
		HashMap<String, Object> inserPos = dao.insertaPosicionFinanciera(llenaObj.llenaPosicionF(BloqueFac, carta));
			exito = Integer.parseInt(inserPos.get("exito").toString());
			int nuPosFin = Integer.parseInt(inserPos.get("nuPosFin").toString());
				if(exito == 0) {
					
					/** Insertar factura */
					HashMap<String, Object> inserFac = dao.inserFactura(llenaObj.llenaFactura(BloqueFac, carta));
						exito = Integer.parseInt(inserFac.get("exito").toString());
						if(exito == 0) {
							int nuFactura = Integer.parseInt(inserFac.get("factura").toString());
							
							llenaObj.llenaNota(BloqueFac, nuFactura);
						}
					
					
				}else {
				nuPosFin = Integer.parseInt(inserPos.get("nuPosFin").toString()); /* tipo de error*/
				}
		
		
		
		return respEjec;
	}
	
}
