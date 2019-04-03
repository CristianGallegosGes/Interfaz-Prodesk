package main.java.com.vpd.bbva.modelo;

import java.util.ArrayList;
import java.util.List;

import main.java.com.vpd.bbva.bean.BeanFF;

public class ValidaArregloDao {
	
	public ArrayList<String> ValidaCarta (List<BeanFF> arregloConsc) throws Exception{
		ArrayList<String> infoArregloC = new ArrayList<String>();
		boolean continua = true;
		ValidaGeneralDatos valida = new ValidaGeneralDatos();
		

		for (BeanFF beanFF : arregloConsc) {
			
			//VALIDACION DE CARTA
			if(beanFF.getNu_carta()>0 ) {  //nu_carta informada,
					/****  Validar que exista carta*/
				continua  = valida.ValidaCarta(beanFF.getNu_carta());
					
				
				if(continua==false) {
					infoArregloC.add("");
					break;
				}
				
				continua  = valida.ValidaCarta(beanFF.getNu_carta());

			
			}else {// Nu_carta no informada, se creara una nueva carta con su respectiva factura
				
				/****  Validar que exista carta*/
					
				}
			
		}
		
		return infoArregloC;
	}
	
	public boolean Continua(boolean control) {
		if(control == true){
			
			// continua proceso
			
		}else {
		
		}
		return true;
		
	}
	
	
	public ArrayList<String> ValidaFactura(List<BeanFF> arregloConsc){
		ArrayList<String> infoArregloF = new ArrayList<>();
		
		for (BeanFF beanFF : arregloConsc) {
			if(beanFF.getNu_factura()>0 ) {  //Factura informada 
				/*** Validar que la factura exista  ***/
				// entra a metodo de validaciones de datos
				System.out.println("Prueba de Git");
			}else {
				// entra a metodo de validaciones de datos
			}		
			
		}

		
		return infoArregloF;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*Metodo para validar si se continua*/
	
	
	

}
