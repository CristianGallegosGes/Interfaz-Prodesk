package main.java.com.vpd.bbva.control;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import main.java.com.vpd.bbva.bean.BeanConceptoFin;
import main.java.com.vpd.bbva.bean.BeanFF;
import main.java.com.vpd.bbva.bean.BeanFactura;
import main.java.com.vpd.bbva.bean.BeanNota;
import main.java.com.vpd.bbva.bean.BeanPosicionFin;
import main.java.com.vpd.bbva.modelo.InsertaDao;

public class LlenaObj {
	
	public ArrayList<HashMap<String, Object>> llenaPosicionF (List<BeanFF> listaBloque, int carta) throws SQLException {
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		
		for (BeanFF beanFF : listaBloque) {
			
			if (beanFF.getTp_registro().equals("NF")) { /***********	REVISAR		***********/
				BeanPosicionFin concep = new BeanPosicionFin();
				InsertaDao dao = new InsertaDao();
					String status = dao.statusPosicionFF(beanFF.getEstatusF());
					BigDecimal im_ivaDB = new BigDecimal( (dao.cdIva(beanFF.getIva()) / 100 ));
					BigDecimal im_iva = (beanFF.getNu_unidades().multiply(beanFF.getImporteUn())).multiply(im_ivaDB);
				concep.setNu_carta(carta);
				concep.setStConcep(status);
				concep.setCuenta(beanFF.getCuentaGps());
				concep.setNb_servicio(beanFF.getDescripServicio());
				concep.setFh_Inicio(beanFF.getFechaInicio());
				concep.setFh_Fin(beanFF.getFechaFin());
				concep.setEntidad(beanFF.getEstado());
				concep.setCd_iva(beanFF.getIva());
				concep.setIm_iva(im_iva);
				concep.setNu_unidad(beanFF.getNu_unidades());  			/*	Numereo de unidades */
				concep.setIm_sin_iva(beanFF.getImporteUn());			/*	Importe unitario */
				concep.setIm_subtotal(beanFF.getImporteUn().multiply(beanFF.getNu_unidades())); /* Numereo de unidades (*) Importe unitario*/
				concep.setCd_uso_gral_pos1("");
				concep.setCd_uso_gral_pos2("");
				concep.setCd_cr(beanFF.getCentroCostos());
				HashMap<String, Object>inserPos = dao.insertaPosicionFinanciera(concep);
				list.add(inserPos);
			}
			
			for (HashMap<String, Object> hashMap : list) {  /** PRUEBA */
				hashMap.get("exito");
				hashMap.get("nu_posicion_F");
			}
		}
		return list;
		}
	
	
	public BeanFactura llenaFactura (List<BeanFF> listaBloque, int carta) {
		int nu_factura = 0 ;
		InsertaDao dao = new InsertaDao();
		BeanFactura factura = new BeanFactura();
		
		for (BeanFF beanFF : listaBloque) {
			while(beanFF.getTp_registro().equals("NF")) {
			factura.setSt_factura(beanFF.getEstatusF());
			factura.setCd_usr_modifica(beanFF.getUsuarioCreador());
			factura.setNu_pedido(0);
			factura.setNu_carta(carta);
			factura.setIm_subtotal_nf(beanFF.getImporteUn().multiply(beanFF.getNu_unidades()));
				BigDecimal im_ivaDB = new BigDecimal( (dao.cdIva(beanFF.getIva()) / 100 ));
				BigDecimal im_iva = (beanFF.getNu_unidades().multiply(beanFF.getImporteUn())).multiply(im_ivaDB);/** nu_unidades * importe unitario * iva*/
			factura.setIm_iva_total_nf(im_iva);  /* nu_unidades * importe unitario * iva*/
				BigDecimal importeSubtotal = beanFF.getNu_unidades().multiply(beanFF.getImporteUn());   /* nu_unidades * importe unitario */
				BigDecimal subtotalIva_oi = importeSubtotal.add(im_iva).add(beanFF.getOtrosImpuestos()); /** importeSubtotal + im_iva + otros Impuesto */
			factura.setIm_sub_iva_oi_nf(subtotalIva_oi); 
			factura.setIm_isr_retenido_nf(beanFF.getIsrRetenido());
			factura.setIm_iva_retenido_nf(beanFF.getIvaRetenido());
			factura.setIm_impto_cedula_nf(beanFF.getImpuestoCedular());
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
			break;
			}
			
		}
		return factura;
	}
	
	public BeanNota llenaNota (List<BeanFF> listaBloque, int factura) {
		BeanNota nota = new BeanNota();
		InsertaDao dao = new InsertaDao();
		
		for (BeanFF beanFF : listaBloque) {
			while(beanFF.getTp_carta().equals("NF"))   // Revisar
			{
			nota.setNu_factura(factura);
				int nu_nota =  Integer.parseInt(dao.parametro(7, beanFF.getEstatusF()));
			nota.setNu_nota(nu_nota); /**1*/
			nota.setTp_nota(beanFF.getTp_registro());  /**NF*/
				String estatusN = dao.parametro(6, beanFF.getEstatusF());
			nota.setSt_nota(estatusN); 
			nota.setCd_folio_sat(null);
			nota.setNb_servicio(beanFF.getDescripServicio());
			nota.setFh_ini_servicio(beanFF.getFechaInicio());
			nota.setFg_fin_servicio(beanFF.getFechaFin());
			nota.setCd_entidad(0);       
			nota.setCd_iva(0);
				BigDecimal im_ivaDB = new BigDecimal( (dao.cdIva(beanFF.getIva()) / 100 ));
				BigDecimal im_iva = (beanFF.getNu_unidades().multiply(beanFF.getImporteUn())).multiply(im_ivaDB);/** nu_unidades * importe unitario * iva*/
			nota.setIm_iva(im_iva);
			nota.setNu_unidad(im_iva);
				BigDecimal importeSubtotal = beanFF.getNu_unidades().multiply(beanFF.getImporteUn());   /** nu_unidades * importe unitario */
			nota.setIm_sin_iva(importeSubtotal);
			nota.setIm_subtotal(importeSubtotal);
			nota.setNb_motivo(null);
			nota.setCd_usr_modifica(beanFF.getUsuarioCreador());
			nota.setNb_nombre_xml(null);
			nota.setNb_nombre_pdf(null);
			nota.setTp_carga(null);
			nota.setCd_uso_gral_not2(null);
			}
			
		}
		return nota;
	}
	
	public BeanConceptoFin llenaConceptoF (List<BeanFF> listaBloque, int factura, int carta, int nu_posFin, int nota) {
		BeanConceptoFin conFin = new BeanConceptoFin();
		for (BeanFF beanFF : listaBloque) {
			conFin.setNu_factura(factura);
			conFin.setNu_carta(carta);
			conFin.setNu_posicion_fin(nu_posFin); 
			conFin.setCd_usr_modifica(beanFF.getUsuarioCreador());
			conFin.setCd_usr_gral_con1(null);
			conFin.setCd_usr_gral_con2(null);
			conFin.setNu_nota(nota);
		}
		return conFin;
		
	}	
}
