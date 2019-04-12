package main.java.com.vpd.bbva.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import main.java.com.vpd.bbva.bean.BeanPosicionFin;
import main.java.com.vpd.bbva.bean.BeanFF;
import main.java.com.vpd.bbva.bean.BeanRespuesta;
import main.java.com.vpd.bbva.modelo.InsertaDao;
import main.java.com.vpd.bbva.vista.ValidaArregloBO;

public class ControlBloqueaDB {
	
	
	
	public List<BeanRespuesta> BloqueaDB(List<BeanFF> listaBloque, boolean cartaValidada) throws Exception{
		List<BeanRespuesta> ListAControlF = new ArrayList();
		ValidaArregloBO validaBO = new ValidaArregloBO();
		BeanRespuesta beanfacConp  = new BeanRespuesta();
		InsertaDao insert = new InsertaDao();
		CreaFacturaConcepto creaFac = new CreaFacturaConcepto();
		
		if(cartaValidada == false) {
			BeanRespuesta lstatusC = validaBO.ValidaCarta(listaBloque); /** Metodo para validar carta*/
			if(lstatusC.GetBandera() == true) {
				//Metodo para validar concepto
				BeanRespuesta statusF	= validaBO.ValidaFactura(listaBloque);
				
				if(statusF.GetBandera() == true) {
					
					BeanRespuesta creaCt = insert.CreaCarta(listaBloque);	/* CREA CARTA */
					ListAControlF.add(creaCt);
					if(creaCt.GetBandera() == true) {
						
					int carta = Integer.parseInt(creaCt.getCampo());
					 
					beanfacConp = creaFac.creaFactura(listaBloque, carta);	/*	CREA FACTURA */
					
					}
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
