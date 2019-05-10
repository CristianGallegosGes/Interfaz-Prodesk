package main.java.com.vpd.bbva.control;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import main.java.com.vpd.bbva.bean.BeanConceptoFin;
import main.java.com.vpd.bbva.bean.BeanFF;
import main.java.com.vpd.bbva.bean.BeanFactura;
import main.java.com.vpd.bbva.bean.BeanFacturaNVA;
import main.java.com.vpd.bbva.bean.BeanNota;
import main.java.com.vpd.bbva.bean.BeanNotaCreFin;
import main.java.com.vpd.bbva.bean.BeanPosicionFin;
import main.java.com.vpd.bbva.bean.BeanRespuesta;
import main.java.com.vpd.bbva.modelo.InsertaDao;
import main.java.com.vpd.bbva.modelo.ValidaGeneralDatosDB;

public class LlenaObj {
	
	public ArrayList<HashMap<String, Object>> llenaPosicionF (List<BeanFF> listaBloque, int carta) throws Exception {
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		int posicion = 0;
		
		for (BeanFF beanFF : listaBloque) {
			ValidaGeneralDatosDB dao = new ValidaGeneralDatosDB();
			InsertaDao insert = new InsertaDao();
			String notaFactura = dao.parametro(8, beanFF.getTp_registro());
			if (beanFF.getTp_registro().equals(notaFactura)) { 
				BeanPosicionFin concep = new BeanPosicionFin();
				
					String status = dao.paramNC(5, beanFF.getEstatusF()); 			System.out.println(beanFF.getEstatusF() );
					BigDecimal iva = dao.cdIva(beanFF.getIva()); 					System.out.println(iva);
					/** nu_unidades * importe unitario * iva*/
					BigDecimal im_iva = ((beanFF.getNu_unidades().multiply(beanFF.getImporteUn())).multiply(iva)).divide(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP);
					System.out.println(beanFF.getNu_unidades());
					System.out.println(beanFF.getImporteUn());
					
				concep.setNu_carta(carta);
				concep.setStConcep(status);
				concep.setCuenta(beanFF.getCuentaGps());
				concep.setNb_servicio(beanFF.getDescripServicio());
				concep.setFh_Inicio(beanFF.getFechaInicio());
				concep.setFh_Fin(beanFF.getFechaFin());
				concep.setEntidad(beanFF.getEstado());
				concep.setCd_iva(beanFF.getDbiva()); System.out.println(beanFF.getDbiva());
				concep.setIm_iva(im_iva);
				concep.setNu_unidad(beanFF.getNu_unidades());  			/*	Numero de unidades */
				concep.setIm_sin_iva(beanFF.getImporteUn());			/*	Importe unitario */
				concep.setIm_subtotal(beanFF.getImporteUn().multiply(beanFF.getNu_unidades())); /* Numereo de unidades (*) Importe unitario*/
				concep.setCd_uso_gral_pos1("");
				concep.setCd_uso_gral_pos2("");
				concep.setCd_cr(beanFF.getCentroCostos());
				HashMap<String, Object>inserPos = insert.insertaPosicionFinanciera(concep);
				if(Integer.parseInt(inserPos.get("exito").toString()) > 0){
					inserPos.put("posicion", posicion);
					
					list.add(inserPos);
					break;
				}
				inserPos.put("posicion", posicion);
				inserPos.put("notaFactura", notaFactura);
				list.add(inserPos);
				
			}++posicion;
		}
		return list;
		}
	
	
	public BeanFactura llenaFactura (List<BeanFF> listaBloque, int carta, String nf) throws Exception {
		int nu_factura = 0 ;
		ValidaGeneralDatosDB dao = new ValidaGeneralDatosDB();
		BeanFactura factura = new BeanFactura();
		boolean completo = false;
		for (BeanFF beanFF : listaBloque) {
			if(completo) {
				break;
			}
			while(beanFF.getTp_registro().equals(nf)) {
			factura.setSt_factura(beanFF.getEstatusF());
			factura.setCd_usr_modifica(beanFF.getUsuarioCreador());
			factura.setNu_pedido(0);
			factura.setNu_carta(carta);
			factura.setIm_subtotal_nf(beanFF.getImporteUn().multiply(beanFF.getNu_unidades()));
				BigDecimal im_ivaDB = dao.cdIva(beanFF.getIva());
				/** nu_unidades * importe unitario * iva*/
				BigDecimal im_iva = ((beanFF.getNu_unidades().multiply(beanFF.getImporteUn())).multiply(im_ivaDB)).divide(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP);
				
			factura.setIm_iva_total_nf(im_iva);  /* nu_unidades * importe unitario * iva*/
				BigDecimal importeSubtotal = beanFF.getNu_unidades().multiply(beanFF.getImporteUn());   /* nu_unidades * importe unitario */
				BigDecimal subtotalIva_oi = importeSubtotal.add(im_iva).add(beanFF.getOtrosImpuestos()); /** importeSubtotal + im_iva + otros Impuesto */
			factura.setIm_sub_iva_oi_nf(subtotalIva_oi); 
			factura.setIm_isr_retenido_nf(beanFF.getIsrRetenido());
			factura.setIm_iva_retenido_nf(beanFF.getIvaRetenido());
			factura.setIm_descuento(beanFF.getDescuento());
			factura.setIm_impto_otros_nc(beanFF.getOtrosImpuestos());
			factura.setIm_total_nf(subtotalIva_oi.subtract(beanFF.getIsrRetenido().subtract(beanFF.getIvaRetenido().subtract(beanFF.getImpuestoCedular()))));  /** total =  */
			factura.setIm_subtotal_nc(new BigDecimal("0"));
			factura.setIm_iva_total_nc(new BigDecimal("0"));
			factura.setIm_sub_iva_oi_nc(new BigDecimal("0"));
			factura.setIm_isr_retenido_nc(new BigDecimal("0"));
			factura.setIm_iva_retenido_nc(new BigDecimal("0"));
			factura.setIm_impto_cedula_nc(new BigDecimal("0"));
			factura.setIm_impto_otros_nc(new BigDecimal("0"));
			factura.setIm_total_nc(new BigDecimal("0"));
			factura.setIm_bruto(new BigDecimal("0"));
			factura.setIm_neto_a_pagar(new BigDecimal("0"));
			factura.setNb_importe_letra("");
			factura.setNb_motivo("");
			factura.setCd_uso_gral_fac2("");
			factura.setCd_anticipo(beanFF.getNu_anticipo());
			factura.setFh_anticipo(beanFF.getFecha_anticipo());
			factura.setCd_folio(null);
			factura.setNu_serie(null);
			completo = true;
			break;
			}
			
		}
		return factura;
	}
	
	public BeanNota llenaNota (List<BeanFF> listaBloque, int factura, String nf){
		BeanNota nota = new BeanNota();
		ValidaGeneralDatosDB dao = new ValidaGeneralDatosDB();
		boolean completo = false;
		try {
		for (BeanFF beanFF : listaBloque) {
			if(completo) {
				break;
			}
			while(beanFF.getTp_registro().equals(nf))   // NF
			{
			nota.setNu_factura(factura);
				int nu_nota =  Integer.parseInt(dao.paramNC(7, beanFF.getEstatusF())); 	System.out.println(nu_nota);
			nota.setNu_nota(nu_nota); /**1*/
			nota.setTp_nota(beanFF.getTp_registro());  /**NF*/
				String estatusN = dao.paramNC(6, beanFF.getEstatusF());
			nota.setSt_nota(estatusN); /**PEN*/
			nota.setCd_folio_sat(null);
			nota.setNb_servicio(beanFF.getDescripServicio());
			nota.setFh_ini_servicio(beanFF.getFechaInicio());
			nota.setFg_fin_servicio(beanFF.getFechaFin());
			nota.setCd_entidad(0);       
			nota.setCd_iva(0);
			BigDecimal im_ivaDB = dao.cdIva(beanFF.getIva());
			/** nu_unidades * importe unitario * iva*/
			BigDecimal im_iva = ((beanFF.getNu_unidades().multiply(beanFF.getImporteUn())).multiply(im_ivaDB)).divide(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP);
			
			nota.setIm_iva(im_iva);
			nota.setNu_unidad(beanFF.getNu_unidades());
				BigDecimal importeSubtotal = beanFF.getNu_unidades().multiply(beanFF.getImporteUn());   /** nu_unidades * importe unitario */
			nota.setIm_sin_iva(importeSubtotal);
			nota.setIm_subtotal(importeSubtotal);
			nota.setNb_motivo(null);
			nota.setCd_usr_modifica(beanFF.getUsuarioCreador());
			nota.setNb_nombre_xml(null);
			nota.setNb_nombre_pdf(null);
			nota.setTp_carga(null);
			nota.setCd_uso_gral_not2(null);
			completo = true;
			break;
			}
			
		}
	}catch (Exception e) {
		
	}
	return nota;
}
	public BeanConceptoFin llenaConceptoF (String usuario, int factura, int carta, int nu_posFin, int nota) {
		BeanConceptoFin conFin = new BeanConceptoFin();
			conFin.setNu_factura(factura);
			conFin.setSt_concepto("NVO");
			conFin.setNu_carta(carta);
			conFin.setNu_posicion_fin(nu_posFin); 
			conFin.setCd_usr_modifica(usuario);
			conFin.setCd_usr_gral_con1("");
			conFin.setCd_usr_gral_con2("");
			conFin.setNu_nota(nota);
		return conFin;
	}	
	
	
	public BeanRespuesta llenaFacConNva(List<BeanFF> factura, String nf) throws Exception {
		BeanFacturaNVA facNva = new BeanFacturaNVA();
			ValidaGeneralDatosDB validaDB = new ValidaGeneralDatosDB();
			int consecBean = 0;
			BeanRespuesta respFacNva = new BeanRespuesta();
			boolean error = false;
			for (BeanFF beanFF : factura) {
				System.out.println("hola " + consecBean);
				while(beanFF.getTp_registro().equals(nf)) {
				
					facNva.setNu_factura(beanFF.getNu_carta());
					facNva.setNu_carta(beanFF.getNu_carta());
					facNva.setEstado(beanFF.getEstado());System.out.println(beanFF.getEstado());
					facNva.setIva(beanFF.getIva());System.out.println(beanFF.getIva());
					facNva.setIsrRetenido(beanFF.getIsrRetenido());
					facNva.setIvaRetenido(beanFF.getIvaRetenido());
					facNva.setImpuestoCedular(beanFF.getImpuestoCedular());
					facNva.setViaP(beanFF.getViaP());
					facNva.setCuentaBanc(beanFF.getCuentaBanc());
					facNva.setTpBanco(beanFF.getTpBanco());
					facNva.setEstatusF(beanFF.getEstatusF());
					facNva.setProveedor(beanFF.getNu_proveedor());
					facNva.setSociedad(beanFF.getSociedadRec());
					facNva.setMoneda(beanFF.getMondena());
					if(consecBean == 0) {
					respFacNva = validaDB.DatosNvaFactura(facNva);
					beanFF.setDbiva(respFacNva.getIvaout());System.out.println(respFacNva.getIvaout());
						if(respFacNva.GetBandera()==false) {
							respFacNva.setConsecutivoA(consecBean);
							error = true;
						}break;
					
					}else {				/** VALIDAR ESTADO Y TIPO DE IVA  DE CADA CONCEPTO**/
						respFacNva = validaDB.ValidaDatosConcep(beanFF.getEstado(), beanFF.getIva());
						beanFF.setDbiva(respFacNva.getIvaout());System.out.println("iva con" + respFacNva.getIvaout());
						if(respFacNva.GetBandera()==false) {
							respFacNva.setConsecutivoA(consecBean);
							error = true;
							break;
						}break;
					}
					
				}++consecBean;
				
				if(error) {
					break;
				}
			}
		
			return respFacNva;
	}
	
	
	public BeanFacturaNVA llenaFacNva(List<BeanFF> factura, String nf) throws SQLException {
		BeanFacturaNVA facNva = new BeanFacturaNVA();
			int consecutivoA = 0;
			for (BeanFF beanFF : factura) {
				
				while(beanFF.getTp_registro().equals(nf)) {
					++consecutivoA;
					facNva.setNu_factura(beanFF.getNu_factura());
					facNva.setNu_carta(beanFF.getNu_carta());
					facNva.setIsrRetenido(beanFF.getIsrRetenido());
					facNva.setIvaRetenido(beanFF.getIvaRetenido());
					facNva.setImpuestoCedular(beanFF.getImpuestoCedular());
					
					facNva.setViaP(beanFF.getViaP());
					facNva.setCuentaBanc(beanFF.getCuentaBanc());
					facNva.setTpBanco(beanFF.getTpBanco());
					facNva.setEstatusF(beanFF.getEstatusF());
					if(consecutivoA == 1) {
							break;
						}
				}
			}
			return facNva;
	}
	
	public BeanNotaCreFin llenaNotaCredito (List<BeanFF> nota, String nc, int numFila, int carta, int factura) throws Exception {
		BeanNotaCreFin notaCre = new BeanNotaCreFin();
		ValidaGeneralDatosDB datosdb = new ValidaGeneralDatosDB();
		int fila = 0;
		for(BeanFF notaC : nota) {
			++fila;
			while(notaC.getTp_registro().equals(nc) && fila == numFila) {
				notaCre.setNu_carta(carta);
				notaCre.setNu_factura(factura);
					datosdb.nuNota(factura);
				notaCre.setNu_nota(datosdb.nuNota(factura));
				notaCre.setTp_nota(nc);
					String estatusN = datosdb.parametro(6, notaC.getEstatusF());
				notaCre.setSt_nota(estatusN);
				notaCre.setCd_cuenta(notaC.getCuentaGps());
				notaCre.setNb_servicio(notaC.getDescripServicio());
				notaCre.setFh_ini_servicio(notaC.getFechaInicio());
				notaCre.setFh_fin_servicio(notaC.getFechaFin());
				notaCre.setEntidad(notaC.getEstado());
				notaCre.setCd_iva(notaC.getDbiva());

			}
			
		}
		return notaCre;
	}


public BeanPosicionFin llenaPosicionFNotaC (List<BeanFF> listaBloque, String nc, int numFila, int carta, int factura, int param) throws Exception {
	int posicion = 0;
	BeanPosicionFin concep = new BeanPosicionFin();
	for (BeanFF beanFF : listaBloque) {
		while(beanFF.getTp_registro().equals(nc) && posicion == numFila) {
		ValidaGeneralDatosDB dao = new ValidaGeneralDatosDB();
				String status = dao.paramNC(param,beanFF.getEstatusF());
				BigDecimal im_ivaDB =(dao.cdIva(beanFF.getIva())).divide(new BigDecimal("100"));
				BigDecimal im_iva = (beanFF.getNu_unidades().multiply(beanFF.getImporteUn())).multiply(im_ivaDB);
			concep.setNu_carta(carta);
			concep.setStConcep(status);
				int num_nota = dao.nuNota(factura);
			concep.setNu_nota(num_nota+1);
			concep.setTp_nota(nc);
			concep.setCuenta(beanFF.getCuentaGps());
			concep.setNb_servicio(beanFF.getDescripServicio());
			concep.setFh_Inicio(new Date(beanFF.getFechaInicio().getTime()));
			concep.setFh_Fin(new Date(beanFF.getFechaFin().getTime()));
			concep.setEntidad(beanFF.getEstado());
			concep.setCd_iva(beanFF.getDbiva());
			concep.setIm_iva(im_iva);
			concep.setNu_unidad(beanFF.getNu_unidades());  			/*	Numero de unidades */
			concep.setIm_sin_iva(beanFF.getImporteUn());			/*	Importe unitario */
			BigDecimal bdSubtotal = beanFF.getImporteUn().multiply(beanFF.getNu_unidades()); /* Numereo de unidades (*) Importe unitario*/
			concep.setIm_subtotal(bdSubtotal);
			concep.setCd_usr_modifica(beanFF.getUsuarioCreador());
			concep.setCd_uso_gral_pos1("");
			concep.setCd_uso_gral_pos2("");
			concep.setCd_cr(beanFF.getCentroCostos());
		}++posicion;
	}
	return concep;
	}

}
