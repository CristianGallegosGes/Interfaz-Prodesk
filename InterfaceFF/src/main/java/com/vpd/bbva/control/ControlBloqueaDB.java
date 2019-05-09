package main.java.com.vpd.bbva.control;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import main.java.com.vpd.bbva.bean.BeanFF;
import main.java.com.vpd.bbva.bean.BeanRespuesta;
import main.java.com.vpd.bbva.modelo.InsertaDao;
import main.java.com.vpd.bbva.vista.ValidaArregloBO;

public class ControlBloqueaDB {
	private static final Logger LOG = Logger.getLogger(ControlBloqueaDB.class);
	
	public List<BeanRespuesta> BloqueaDB(List<BeanFF> listaBloque, boolean cartaValidada) throws Exception{	
		List<BeanRespuesta> ListAControlF = new ArrayList<BeanRespuesta>();
		ValidaArregloBO validaBO = new ValidaArregloBO();
		BeanRespuesta statusF = new BeanRespuesta();
		InsertaDao insert = new InsertaDao();
		CreaFacturaConcepto creaFac = new CreaFacturaConcepto();
		try {
		int carta = 0 ;
		if(cartaValidada == false) {
			BeanRespuesta lstatusC = validaBO.ValidaCarta(listaBloque); /** Metodo para validar carta*/
			if(lstatusC.GetBandera() == true) {   /** Validacion de Carta correcta*/
				
				statusF	= validaBO.ValidaFactura(listaBloque);
				int existeFac = statusF.getFactura();
				
				if(statusF.GetBandera() == true) {  	/** Validacion de Factura correcta*/
					if(existeFac == 0) {				/** Si la factura no existe, crear carta, factura, concepto, nota de credito */
						/* CREA CARTA */
						BeanRespuesta creaCt =  insert.CreaCarta(listaBloque);	
						ListAControlF.add(creaCt);
							if(creaCt.GetBandera() == true) {	
								carta = creaCt.getCarta();
								/*	CREA FACTURA */
								BeanRespuesta beanfacConp = creaFac.creaFactura(listaBloque, carta);	
								ListAControlF.add(beanfacConp);
								
								
								/* CREAR NOTAS DE CREDITO */
								int factura = beanfacConp.getFactura();
								ValidaArregloBO valida = new ValidaArregloBO();
								List<BeanRespuesta> notaCredito = valida.validaInserNotaCredito(listaBloque, carta,factura );
								if(!notaCredito.isEmpty()) {
									for(BeanRespuesta informeNC : notaCredito) {
										ListAControlF.add(informeNC);
									}
								}
							}
					}else {							
						/* SI LA FACTURA EXISTE, SOLO CREAR NOTAS DE CREDITO*/
						/* CREAR NOTAS DE CREDITO */
						int factura = statusF.getFactura();
						
						ValidaArregloBO valida = new ValidaArregloBO();
						List<BeanRespuesta> notaCredito = valida.validaInserNotaCredito(listaBloque,carta,factura );
						if(!notaCredito.isEmpty()) {
							for(BeanRespuesta informeNC : notaCredito) {
								ListAControlF.add(informeNC);
							}
						}
						
					}
				}else {
					ListAControlF.add(statusF);
				}
				
				
			}else {
				ListAControlF.add(lstatusC);
			}
		}else {
			/* CARTA VALIDADA*/
			/* VALIDAR FACTURA */
			statusF	= validaBO.ValidaFactura(listaBloque);
			if(statusF.GetBandera() == true) { 
				int cartav = statusF.getCarta();
				/*	CREA FACTURA */
				BeanRespuesta beanfac = creaFac.creaFactura(listaBloque, cartav);	
				/* CREAR NOTAS DE CREDITO */
				int factura = beanfac.getFactura();
				ValidaArregloBO valida = new ValidaArregloBO();
				List<BeanRespuesta> notaCredito = valida.validaInserNotaCredito(listaBloque, cartav,factura );
				if(!notaCredito.isEmpty()) {
					for(BeanRespuesta informeNC : notaCredito) {
						ListAControlF.add(informeNC);
					}
				}
				
				}else {
					ListAControlF.add(statusF);
				}
		}
		
	}catch (Exception e) {
			LOG.warn("Error: " + e);
		}
		for (BeanRespuesta beanRespuesta : ListAControlF) {
			System.out.println(beanRespuesta.GetBandera());
			System.out.println(beanRespuesta.getMensaje());
			System.out.println(beanRespuesta.getConsecutivoA());
			System.out.println(beanRespuesta.getCarta());
			System.out.println(beanRespuesta.getFactura());
			System.out.println(beanRespuesta.getTp_registro());
		}
		return ListAControlF;
	}
	

}
