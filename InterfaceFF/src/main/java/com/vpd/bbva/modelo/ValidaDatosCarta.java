package main.java.com.vpd.bbva.modelo;

import java.util.ArrayList;
import java.util.List;

import main.java.com.vpd.bbva.bean.BeanFF;
import main.java.com.vpd.bbva.bean.BeanRespuesta;
import main.java.com.vpd.bbva.constantes.Constantes;

public class ValidaDatosCarta {
	
	Constantes cons = new Constantes();
	
	public BeanRespuesta ComparaArrevsDBFactura(){
		BeanRespuesta bean = new BeanRespuesta();
		
		return bean;
		
	}
	
	public BeanRespuesta ComparaArrevsDBCarta(List<BeanFF>arregloArchivo, BeanFF listaDB){
		BeanRespuesta resp = new BeanRespuesta();
		
		for (BeanFF ListArc: arregloArchivo) {
			
			if(ListArc.getTp_carta().equals(listaDB.getTp_carta())) {
			if(ListArc.getTp_pago() == listaDB.getTp_pago()) {
			if(ListArc.getNu_proveedor() == listaDB.getNu_proveedor()) {
			if(ListArc.getSociedadRec().equals(listaDB.getSociedadRec())) {
			if(ListArc.getGlg().equals(listaDB.getGlg())) {
			if(ListArc.getEmpGasBursa().equals(listaDB.getEmpGasBursa())) {
			if(ListArc.getNu_acreditado().equals(listaDB.getNu_acreditado())) {
			if(ListArc.getNu_pep().equals(listaDB.getNu_pep())) {
			if(ListArc.getMondena().equals(listaDB.getMondena())) {
			if(ListArc.getPeriodificacion().equals(listaDB.getPeriodificacion())) {
			if(ListArc.getProviEjerAnterior().equals(listaDB.getProviEjerAnterior())) {
			if(ListArc.getContrato() == listaDB.getContrato()) {
			if(ListArc.getRecAlternativo() == listaDB.getRecAlternativo()) {
			if(ListArc.getCuentaGps() == listaDB.getCuentaGps()) {
			if(ListArc.getUsuarioCreador().equals(listaDB.getUsuarioCreador())) {
				resp.setBandera(true);
				resp.setMensaje("DATOS CORRECTOS");
				
			}
			}else {	
				resp.setBandera(false);
				resp.setMensaje("CUENTA GPS, "+ ListArc.getCuentaGps()+ " no existe en la carta");
				}	
			}else {	
				resp.setBandera(false);
				resp.setMensaje("RECEPTOR ALTERNATIVO, "+ ListArc.getRecAlternativo()+ " no existe en la carta");
				}
			}else {	
				resp.setBandera(false);
				resp.setMensaje("CONTRATO, "+ ListArc.getContrato()+ " no existe en la carta");
				}
			}else {	
				resp.setBandera(false);
				resp.setMensaje("PROVISION, "+ ListArc.getProviEjerAnterior() + " no existe en la carta");
				}
			}else {	
				resp.setBandera(false);
				resp.setMensaje("PERIODIFICACION, "+ ListArc.getPeriodificacion() + " no existe en la carta");
				}
			}else {	
				resp.setBandera(false);
				resp.setMensaje("MONEDA, "+ ListArc.getMondena()+ " no existe en la carta");
				}
			}else {	
				resp.setBandera(false);
				resp.setMensaje("PEP, "+ ListArc.getNu_pep() + " no existe en la carta");
				}
			}else {	
				resp.setBandera(false);
				resp.setMensaje("NUM. ACREDITADO, "+ ListArc.getNu_acreditado() + " no existe en la carta");
				}
			}else {	
				resp.setBandera(false);
				resp.setMensaje("EMPRESA GASTOS B., "+ ListArc.getEmpGasBursa() + " no existe en la carta");
				}
			}else {	
				resp.setBandera(false);
				resp.setMensaje("GLG, "+ ListArc.getGlg() + " no existe en la carta");
				}
				
			}else {	
				resp.setBandera(false);
				resp.setMensaje("SOC. RECEPTORA, "+ ListArc.getSociedadRec() + " no existe en la carta");
				}
				
			}else {	
				resp.setBandera(false);
				resp.setMensaje("NUM. PROVEEDOR, "+ ListArc.getNu_proveedor() + "no existe en la carta");
				}
			}else { 
				resp.setBandera(false);
				resp.setMensaje("TIPO DE PAGO, "+ ListArc.getTp_pago() + "no existe en la carta");
				}
				
			}else {	
				resp.setBandera(false);
				resp.setMensaje("TIPO DE CARTA, "+ ListArc.getTp_carta()  + " no existe para la carta");
				}
			break;
			}	
		return resp;
	}
	
	

}
