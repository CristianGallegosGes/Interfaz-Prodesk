package main.java.com.vpd.bbva.control;

import main.java.com.vpd.bbva.bean.BeanFF;

public class ValidaDatosCarta {

	public ValidaDatosCarta() {
		// TODO Auto-generated constructor stub
	}

	public boolean validaDatosCartaConsecutiva(BeanFF datosActuales, BeanFF datosAnteriores) {
		boolean datosIguales = true;
		
		if(!datosActuales.getTp_carta().equals(datosAnteriores.getTp_carta())) {
			datosIguales = false;
			return datosIguales;
		} 
		
		if(!datosActuales.getTp_pago().equals(datosAnteriores.getTp_pago())) {
			datosIguales = false;
			return datosIguales;
		}
		
		if(datosActuales.getNu_proveedor() == datosAnteriores.getNu_proveedor()){
			datosIguales = false;
			return datosIguales;
		}
		
		if(!datosActuales.getSociedadRec().equals(datosAnteriores.getSociedadRec())) {
			datosIguales = false;
			return datosIguales;
		}
		
		if(!datosActuales.getGlg().equals(datosAnteriores.getGlg())){
			datosIguales = false;
			return datosIguales;
		}

		if(!datosActuales.getEmpGasBursa().equals(datosAnteriores.getEmpGasBursa())) {
			datosIguales = false;
			return datosIguales;
		}
		
		if(!datosActuales.getFideicomiso().equals(datosAnteriores.getFideicomiso())) {
			datosIguales = false;
			return datosIguales;
		}
		
		
		if(!datosActuales.getNu_acreditado().equals(datosActuales.getNu_acreditado())) {
			datosIguales = false;
			return datosIguales;
		}
		
		return datosIguales;
	}

	public boolean validaDatosTipoNotaConsecutiva(BeanFF datosActuales, BeanFF datosAnteriores) {
		boolean datosIguales = true;

		return datosIguales;
	}
}
