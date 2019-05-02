package main.java.com.vpd.bbva.modelo;

import java.util.List;

import main.java.com.vpd.bbva.bean.BeanFF;
import main.java.com.vpd.bbva.bean.BeanFacturaNVA;
import main.java.com.vpd.bbva.bean.BeanRespuesta;
import main.java.com.vpd.bbva.constantes.Constantes;
import main.java.com.vpd.bbva.control.ValidacionDatosCtrol;

public class ValidaDatos {
	
	Constantes cons = new Constantes();
	
	public BeanRespuesta ComparaArrevsDBFactura(BeanFacturaNVA datosFac, BeanFacturaNVA beanDB){
		BeanRespuesta beanr = new BeanRespuesta();
			ValidaGeneralDatosDB dao = new ValidaGeneralDatosDB();
			int llave = 10;
			String status = dao.parametro(llave, datosFac.getEstatusF());
				if (status != null) {
					if(datosFac.getEstatusF().equals(beanDB.getEstatusF())) {
						if(datosFac.getViaP().equals(beanDB.getViaP())) {
							if(datosFac.getCuentaBanc() == beanDB.getCuentaBanc()) {
								beanr.setBandera(true);
								beanr.setMensaje("DATOS CORRECTOS");
							}else {
								beanr.setBandera(false);
								beanr.setMensaje("CUENTA BANCARIA "+datosFac.getCuentaBanc()+ " NO CORRESPONDE A LA FACTURA");
							}
						}else {
							beanr.setBandera(false);
							beanr.setMensaje("VIA DE PAGO "+datosFac.getViaP()+ " NO CORRESPONDE A LA FACTURA");
						}
						
					}else {
						beanr.setBandera(false);
						beanr.setMensaje("LA FACTURA EN VPD TIENE OTRO ESTATUS");
					}
					
				}else {
					beanr.setBandera(false);
					beanr.setMensaje("ESTATUS NO DISPONIBLE PARA AGREGAR NC EN FACTURA EXISTENTE");
				}
			
		return beanr;
		
	}
	
	
	
	public BeanRespuesta ComparaArrevsDBCarta(List<BeanFF>arregloArchivo, BeanFF listaDB, String nf){
		BeanRespuesta resp = new BeanRespuesta();
		
		for (BeanFF ListArc: arregloArchivo) {
			while(ListArc.getTp_registro().equals(nf)) {
			if(ListArc.getTp_carta().equals(listaDB.getTp_carta())) {
			if(ListArc.getTp_pago() == listaDB.getTp_pago()) {
			if(ListArc.getNu_proveedor() == listaDB.getNu_proveedor()) {
			if(ListArc.getSociedadRec().equals(listaDB.getSociedadRec())) {
			if(ListArc.getGlg().equals(listaDB.getGlg())) {
			if(ListArc.getNu_acreditado().equals(listaDB.getNu_acreditado())) {
			if(ListArc.getNu_pep().equals(listaDB.getNu_pep())) {
			if(ListArc.getMondena().equals(listaDB.getMondena())) {
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
		}
		return resp;
	}
	
	

}
