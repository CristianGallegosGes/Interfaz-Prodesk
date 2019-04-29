package main.java.com.vpd.bbva.control;

import java.util.ArrayList;
import java.util.List;

import main.java.com.vpd.bbva.bean.BeanFF;
import main.java.com.vpd.bbva.bean.BeanRespuesta;
import main.java.com.vpd.bbva.modelo.InsertaDao;
import main.java.com.vpd.bbva.vista.ValidaArregloBO;

public class ControlBloqueaDB {
	
	
	
	public List<BeanRespuesta> BloqueaDB(List<BeanFF> listaBloque, boolean cartaValidada) throws Exception{
		List<BeanRespuesta> ListAControlF = new ArrayList();
		ValidaArregloBO validaBO = new ValidaArregloBO();
		BeanRespuesta statusF = new BeanRespuesta();
		InsertaDao insert = new InsertaDao();
		CreaFacturaConcepto creaFac = new CreaFacturaConcepto();
		
		if(cartaValidada == false) {
			BeanRespuesta lstatusC = validaBO.ValidaCarta(listaBloque); /** Metodo para validar carta*/
			if(lstatusC.GetBandera() == true) {   /** Validacion de Carta correcta*/
				
				statusF	= validaBO.ValidaFactura(listaBloque);
				int existeFac = statusF.getFactura();
				
				if(statusF.GetBandera() == true) {  /** Validacion de Factura correcta*/
					if(existeFac == 0) {				/** Si la factura no existe, crear carta, factura, concepto, nota de credito */
						
						BeanRespuesta creaCt = insert.CreaCarta(listaBloque);	/* CREA CARTA */
						ListAControlF.add(creaCt);
							if(creaCt.GetBandera() == true) {	
								int carta = creaCt.getCarta();
								BeanRespuesta beanfacConp = creaFac.creaFactura(listaBloque, carta);	/*	CREA FACTURA */
								ListAControlF.add(beanfacConp);
								
								
								/* CREAR NOTAS DE CREDITO */
								int factura = beanfacConp.getFactura();
								ValidaArregloBO valida = new ValidaArregloBO();
								List<BeanRespuesta> notaCredito = valida.validaInserNotaCredito(listaBloque, carta,factura );
								
								for(BeanRespuesta informeNC : notaCredito) {
									ListAControlF.add(informeNC);
								}
								
							}
					}else {							/** Si la factura existe, solo crear notas de credito*/
						/* CREAR NOTA DE CREDITO */
						
						
					}
				}else {
					ListAControlF.add(statusF);
				}
				
				
			}else {
				ListAControlF.add(lstatusC);
			}
		}else {
			statusF	= validaBO.ValidaFactura(listaBloque);
			if(statusF.GetBandera() == true) { 
				int carta = statusF.getCarta();
				BeanRespuesta beanfac = creaFac.creaFactura(listaBloque, carta);	/*	CREA FACTURA */
				/* CREAR NOTAS DE CREDITO */
				int factura = beanfac.getFactura();
				
				}
		}
		
		
		
		return ListAControlF;
	}
	

}
