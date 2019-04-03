package main.java.com.vpd.bbva.control;

import java.util.HashMap;

import main.java.com.vpd.bbva.bean.BeanFF;

public class ValidaDatosCarta {

	public ValidaDatosCarta() {
		// TODO Auto-generated constructor stub
	}

	public HashMap<Integer, String> validaDatosCartaConsecutiva(BeanFF datosActuales, BeanFF datosAnteriores, int linea) {
		
		HashMap<Integer, String> validaCartaConse = new HashMap<Integer, String>();
		if(!datosActuales.getTp_carta().equals(datosAnteriores.getTp_carta())) {
			validaCartaConse.put(1, "ERROR CONSECUTIVO  " + datosActuales.getConsecArch() + "/" + datosActuales.getConsecNota() + " LINEA" + linea + " \"CONSECUTIVO ARCHIVO\"");
			return validaCartaConse;
		}
		
		if(!datosActuales.getTp_pago().equals(datosAnteriores.getTp_pago())) {
			validaCartaConse.put(1, "ERROR CONSECUTIVO  " + datosActuales.getConsecArch() + "/" + datosActuales.getConsecNota() + " LINEA" + linea +  "\"TIPO PAGO\"");
			return validaCartaConse;
		}
		
		if(datosActuales.getNu_proveedor() != datosAnteriores.getNu_proveedor()){
			validaCartaConse.put(1, "ERROR CONSECUTIVO  " + datosActuales.getConsecArch() + "/" + datosActuales.getConsecNota() + " LINEA" + linea + " \"NUMERO PROVEEDOR\"");
			return validaCartaConse;
		}
		
		if(!datosActuales.getSociedadRec().equals(datosAnteriores.getSociedadRec())) {
			validaCartaConse.put(1, "ERROR CONSECUTIVO  " + datosActuales.getConsecArch() + "/" + datosActuales.getConsecNota() + " LINEA" + linea + " \"SOCIEDAD RECEPTORA\"");
			return validaCartaConse;
		}
		
		if(!datosActuales.getGlg().equals(datosAnteriores.getGlg())){
			validaCartaConse.put(1, "ERROR CONSECUTIVO  " + datosActuales.getConsecArch() + "/" + datosActuales.getConsecNota() + " LINEA" + linea + " \"GLG\"");
			return validaCartaConse;
		}

		if(!datosActuales.getEmpGasBursa().equals(datosAnteriores.getEmpGasBursa())) {
			validaCartaConse.put(1, "ERROR CONSECUTIVO  " + datosActuales.getConsecArch() + "/" + datosActuales.getConsecNota() + " LINEA" + linea + " \"EMPRESA GASTOS BURSA\"");
			return validaCartaConse;
		}
		
		if(!datosActuales.getFideicomiso().equals(datosAnteriores.getFideicomiso())) {
			validaCartaConse.put(1, "ERROR CONSECUTIVO  " + datosActuales.getConsecArch() + "/" + datosActuales.getConsecNota() + " LINEA" + linea + " \"FIDEICOMISO\"");
			return validaCartaConse;
		}
		
		
		if(!datosActuales.getNu_acreditado().equals(datosAnteriores.getNu_acreditado())) {
			validaCartaConse.put(1, "ERROR CONSECUTIVO  " + datosActuales.getConsecArch() + "/" + datosActuales.getConsecNota() + " LINEA" + linea + " \"NUMERO DE ACREDITADO\"");
			return validaCartaConse;
		}
		
		if(!datosActuales.getNu_pep().equals(datosAnteriores.getNu_pep())) {
			validaCartaConse.put(1, "ERROR CONSECUTIVO  " + datosActuales.getConsecArch() + "/" + datosActuales.getConsecNota() + " LINEA" + linea + " \"NUMERO DE PEP\"");
			return validaCartaConse;
		}
		
		if(!datosActuales.getMondena().equals(datosAnteriores.getMondena())) {
			validaCartaConse.put(1, "ERROR CONSECUTIVO  " + datosActuales.getConsecArch() + "/" + datosActuales.getConsecNota() + " LINEA" + linea + " \"MONEDA\"");
			return validaCartaConse;
		}
		
		if(!datosActuales.getPeriodificacion().equals(datosAnteriores.getPeriodificacion())) {
			validaCartaConse.put(1, "ERROR CONSECUTIVO  " + datosActuales.getConsecArch() + "/" + datosActuales.getConsecNota() + " LINEA" + linea + " \"PERIODIFICACIÓN\"");
			return validaCartaConse;
		}
		
		if(!datosActuales.getProviEjerAnterior().equals(datosAnteriores.getProviEjerAnterior())) {
			validaCartaConse.put(1, "ERROR CONSECUTIVO  " + datosActuales.getConsecArch() + "/" + datosActuales.getConsecNota() + " LINEA" + linea + " \"PROVISIÓN EJERCICIO ANTERIOR\"");
			return validaCartaConse;
		}
		
		if(datosActuales.getContrato() != datosAnteriores.getContrato()) {
			validaCartaConse.put(1, "ERROR CONSECUTIVO  " + datosActuales.getConsecArch() + "/" + datosActuales.getConsecNota() + " LINEA" + linea + " \"CONTRATO\"");
			return validaCartaConse;
		}
		
		if(datosActuales.getRecAlternativo() != datosAnteriores.getRecAlternativo()) {
			validaCartaConse.put(1, "ERROR CONSECUTIVO  " + datosActuales.getConsecArch() + "/" + datosActuales.getConsecNota() + " LINEA" + linea + " \"RECEPTOR ALTERNATIVO\"");
			return validaCartaConse;
		}
		
		if(datosActuales.getCuentaGps() != datosAnteriores.getCuentaGps()) {
			validaCartaConse.put(1, "ERROR CONSECUTIVO  " + datosActuales.getConsecArch() + "/" + datosActuales.getConsecNota() + " LINEA" + linea + " \"CUENTA GPS(CUENTA DE GASTO)\"");
			return validaCartaConse;	
		}
		
		if(!datosActuales.getUsuarioCreador().equals(datosAnteriores.getUsuarioCreador())) {
			validaCartaConse.put(1, "ERROR CONSECUTIVO  " + datosActuales.getConsecArch() + "/" + datosActuales.getConsecNota() + " LINEA" + linea + " \"USUARIO CREADOR DE CARTA\"");
			return validaCartaConse;
		}
		
		return validaCartaConse;
	}
}
