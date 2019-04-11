package main.java.com.vpd.bbva.control;

import java.util.ArrayList;
import java.util.List;

import main.java.com.vpd.bbva.bean.BeanFF;
import main.java.com.vpd.bbva.bean.BeanRespuesta;
import main.java.com.vpd.bbva.modelo.InsertaDao;
import main.java.com.vpd.bbva.vista.ValidaArregloBO;

public class ControlBloqueaDB {
	
	
	
	public List<BeanRespuesta> BloqueaDB(List<BeanFF> listaBloque, boolean cartaValidada) throws Exception{
		List<BeanRespuesta> ListAControlF = new ArrayList<BeanRespuesta>();
		ValidaArregloBO validaBO = new ValidaArregloBO();
		BeanRespuesta lstatusC  = new BeanRespuesta();
		InsertaDao insert = new InsertaDao();
		
		if(cartaValidada == false) {
			lstatusC = validaBO.ValidaCarta(listaBloque); /** Metodo para validar carta*/
			if(lstatusC.GetBandera() == true) {
				//Metodo para validar concepto
				BeanRespuesta statusF	= validaBO.ValidaFactura(listaBloque);
			if(statusF.GetBandera() == true) {
				BeanRespuesta statusC = insert.CreaCarta(listaBloque);
				//BeanRespuesta statusF = 
				/** Metodo para insertar Factura */
				
				}
				}
		}else {
			BeanRespuesta statusF	= validaBO.ValidaFactura(listaBloque);
			if(statusF.GetBandera() == true) {
				/** Metodo para insertar Factura */
				
				}
		}
		
		
		
		return ListAControlF;
	}

}
