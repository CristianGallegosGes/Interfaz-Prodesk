package main.java.com.vpd.bbva.vista;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import main.java.com.vpd.bbva.bean.BeanFF;
import main.java.com.vpd.bbva.bean.BeanRespuesta;
import main.java.com.vpd.bbva.modelo.ValidaDatosCarta;
import main.java.com.vpd.bbva.modelo.ValidaGeneralDatosDB;

public class ValidaArregloBO {
	public static final Logger LOG = Logger.getLogger(ValidaArregloBO.class);
	
public BeanRespuesta ValidaCarta (List<BeanFF> arregloArchivo) throws Exception{
	BeanFF DatosCarta = new BeanFF();
	BeanRespuesta respReturn = new BeanRespuesta();
	ValidaGeneralDatosDB validaDB = new ValidaGeneralDatosDB();
	ValidaDatosCarta vdatosc = new ValidaDatosCarta();
		
try {

	for (BeanFF beanFF : arregloArchivo) {
			while(beanFF.getTp_registro().equals("")) {	
			if(beanFF.getNu_carta() > 0) {				//Carta informada
				DatosCarta = validaDB.ValidaCarta(new Integer(beanFF.getNu_carta())); //VALIDACION DE CARTA
						if(DatosCarta.getContinua()==true) {										/*bandera de que existe la carta*/
							respReturn = vdatosc.ComparaArrevsDBCarta(arregloArchivo, DatosCarta);	//Valida si los datos de la carta en DB son iguales a los de la interface
							}
					}		
			else {
				respReturn =validaDB.DatosNvaCarta(arregloArchivo);  //Carta no informada, validar para crear una nueva
			}
			break;  /*se termina de leer el for porque solo es una carta por bloque, si vuelve a leer, leeria la misma carta*/
			}
	}
	
}catch (Exception e) {
		LOG.warn("Error "+e);	 
		}
		return respReturn;
	}
	
	
	
	
	
	
	public BeanRespuesta ValidaFactura(List<BeanFF> factura) throws SQLException{
		ValidaGeneralDatosDB validaDB = new ValidaGeneralDatosDB();
		BeanRespuesta BeanRes = new BeanRespuesta();
		BeanFF bean = new BeanFF();
		
		for(BeanFF leeF : factura) {
			if(leeF.getNu_factura()>0) {  						/** Existe la Factura */
				int facturaI = leeF.getNu_factura();
				int cartaI = leeF.getNu_carta();
				bean = validaDB.DatosFactura(facturaI, cartaI);				/** Validar que la factura exista en la DB */
				
					
	
			}else {													/** Validacion para crear Factura nueva */
				BeanRes = validaDB.DatosNvaFactura(factura);  		
			}		
			break;  // si se van a leer varias facturas por bloque se quita el break
		}

		
		return BeanRes;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*Metodo para validar si se continua*/
	
	
	

}
