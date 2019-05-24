package main.java.com.vpd.bbva.bean;

import java.math.BigDecimal;
import java.sql.Date;

public class BeanFactura {
	private String st_factura;
	private String cd_usr_modifica;
	private int nu_pedido;
	private int nu_carta;
	private BigDecimal im_subtotal_nf= new BigDecimal("0");
	private BigDecimal im_iva_total_nf= new BigDecimal("0");
	private BigDecimal im_sub_iva_oi_nf= new BigDecimal("0");
	private BigDecimal im_isr_retenido_nf= new BigDecimal("0");
	private BigDecimal im_iva_retenido_nf= new BigDecimal("0");
	private BigDecimal im_descuento= new BigDecimal("0");
	private BigDecimal im_impo_otros_nf = new BigDecimal("0");
	private BigDecimal im_total_nf= new BigDecimal("0");
	private BigDecimal im_subtotal_nc= new BigDecimal("0");
	private BigDecimal im_iva_total_nc= new BigDecimal("0");
	private BigDecimal im_sub_iva_oi_nc= new BigDecimal("0");
	private BigDecimal im_isr_retenido_nc= new BigDecimal("0");
	private BigDecimal im_iva_retenido_nc= new BigDecimal("0");
	private BigDecimal im_impto_cedula_nc= new BigDecimal("0");
	private BigDecimal im_impto_otros_nc= new BigDecimal("0");
	private BigDecimal im_total_nc= new BigDecimal("0");
	private BigDecimal im_bruto= new BigDecimal("0");
	private BigDecimal im_neto_a_pagar= new BigDecimal("0");
	private String 	   nb_importe_letra;
	private String 	   nb_motivo;
	private String     cd_uso_gral_fac2;
	private String     cd_anticipo;
	private Date	   fh_anticipo;
	private Date	   fh_factura;
	private String     cd_folio;
	private String	   nu_serie;
	private String 	   aplicativo;
	
	
	
	public String getAplicativo() {
		return aplicativo;
	}
	public void setAplicativo(String aplicativo) {
		this.aplicativo = aplicativo;
	}
	public String getSt_factura() {
		return st_factura;
	}
	public void setSt_factura(String st_factura) {
		this.st_factura = st_factura;
	}
	public String getCd_usr_modifica() {
		return cd_usr_modifica;
	}
	public void setCd_usr_modifica(String cd_usr_modifica) {
		this.cd_usr_modifica = cd_usr_modifica;
	}
	public int getNu_pedido() {
		return nu_pedido;
	}
	public void setNu_pedido(int nu_pedido) {
		this.nu_pedido = nu_pedido;
	}
	public int getNu_carta() {
		return nu_carta;
	}
	public void setNu_carta(int nu_carta) {
		this.nu_carta = nu_carta;
	}
	public BigDecimal getIm_subtotal_nf() {
		return im_subtotal_nf;
	}
	public void setIm_subtotal_nf(BigDecimal im_subtotal_nf) {
		this.im_subtotal_nf = im_subtotal_nf;
	}
	public BigDecimal getIm_iva_total_nf() {
		return im_iva_total_nf;
	}
	public void setIm_iva_total_nf(BigDecimal im_iva_total_nf) {
		this.im_iva_total_nf = im_iva_total_nf;
	}
	public BigDecimal getIm_sub_iva_oi_nc() {
		return im_sub_iva_oi_nc;
	}
	public void setIm_sub_iva_oi_nc(BigDecimal im_sub_iva_oi_nc) {
		this.im_sub_iva_oi_nc = im_sub_iva_oi_nc;
	}
	public BigDecimal getIm_isr_retenido_nf() {
		return im_isr_retenido_nf;
	}
	public void setIm_isr_retenido_nf(BigDecimal im_isr_retenido_nf) {
		this.im_isr_retenido_nf = im_isr_retenido_nf;
	}
	public BigDecimal getIm_iva_retenido_nf() {
		return im_iva_retenido_nf;
	}
	public void setIm_iva_retenido_nf(BigDecimal im_iva_retenido_nf) {
		this.im_iva_retenido_nf = im_iva_retenido_nf;
	}

	
	
	
	public BigDecimal getIm_descuento() {
		return im_descuento;
	}
	public void setIm_descuento(BigDecimal im_descuento) {
		this.im_descuento = im_descuento;
	}
	public BigDecimal getIm_impo_otros_nf() {
		return im_impo_otros_nf;
	}
	public void setIm_impo_otros_nf(BigDecimal im_impo_otros_nf) {
		this.im_impo_otros_nf = im_impo_otros_nf;
	}
	public BigDecimal getIm_total_nf() {
		return im_total_nf;
	}
	public void setIm_total_nf(BigDecimal im_total_nf) {
		this.im_total_nf = im_total_nf;
	}
	public BigDecimal getIm_subtotal_nc() {
		return im_subtotal_nc;
	}
	public void setIm_subtotal_nc(BigDecimal im_subtotal_nc) {
		this.im_subtotal_nc = im_subtotal_nc;
	}
	public BigDecimal getIm_iva_total_nc() {
		return im_iva_total_nc;
	}
	public void setIm_iva_total_nc(BigDecimal im_iva_total_nc) {
		this.im_iva_total_nc = im_iva_total_nc;
	}
	public BigDecimal getIm_sub_iva_oi_nf() {
		return im_sub_iva_oi_nf;
	}
	public void setIm_sub_iva_oi_nf(BigDecimal im_sub_iva_oi_nf) {
		this.im_sub_iva_oi_nf = im_sub_iva_oi_nf;
	}
	public BigDecimal getIm_isr_retenido_nc() {
		return im_isr_retenido_nf;
	}
	public void setIm_isr_retenido_nc(BigDecimal im_isr_retenido_nc) {
		this.im_isr_retenido_nc = im_isr_retenido_nc;
	}
	public BigDecimal getIm_iva_retenido_nc() {
		return im_iva_retenido_nc;
	}
	public void setIm_iva_retenido_nc(BigDecimal im_iva_retenido_nc) {
		this.im_iva_retenido_nc = im_iva_retenido_nc;
	}
	public BigDecimal getIm_impto_cedula_nc() {
		return im_impto_cedula_nc;
	}
	public void setIm_impto_cedula_nc(BigDecimal im_impto_cedula_nc) {
		this.im_impto_cedula_nc = im_impto_cedula_nc;
	}
	public BigDecimal getIm_impto_otros_nc() {
		return im_impto_otros_nc;
	}
	public void setIm_impto_otros_nc(BigDecimal im_impto_otros_nc) {
		this.im_impto_otros_nc = im_impto_otros_nc;
	}
	public BigDecimal getIm_total_nc() {
		return im_total_nc;
	}
	public void setIm_total_nc(BigDecimal im_total_nc) {
		this.im_total_nc = im_total_nc;
	}
	public BigDecimal getIm_bruto() {
		return im_bruto;
	}
	public void setIm_bruto(BigDecimal im_bruto) {
		this.im_bruto = im_bruto;
	}
	public BigDecimal getIm_neto_a_pagar() {
		return im_neto_a_pagar;
	}
	public void setIm_neto_a_pagar(BigDecimal im_neto_a_pagar) {
		this.im_neto_a_pagar = im_neto_a_pagar;
	}
	public String getNb_importe_letra() {
		return nb_importe_letra;
	}
	public void setNb_importe_letra(String nb_importe_letra) {
		this.nb_importe_letra = nb_importe_letra;
	}
	public String getNb_motivo() {
		return nb_motivo;
	}
	public void setNb_motivo(String nb_motivo) {
		this.nb_motivo = nb_motivo;
	}
	public String getCd_uso_gral_fac2() {
		return cd_uso_gral_fac2;
	}
	public void setCd_uso_gral_fac2(String cd_uso_gral_fac2) {
		this.cd_uso_gral_fac2 = cd_uso_gral_fac2;
	}
	public String getCd_anticipo() {
		return cd_anticipo;
	}
	public void setCd_anticipo(String cd_anticipo) {
		this.cd_anticipo = cd_anticipo;
	}
	public Date getFh_anticipo() {
		return fh_anticipo;
	}
	public void setFh_anticipo(Date fh_anticipo) {
		this.fh_anticipo = fh_anticipo;
	}
	public Date getFh_factura() {
		return fh_factura;
	}
	public void setFh_factura(Date fh_factura) {
		this.fh_factura = fh_factura;
	}
	public String getCd_folio() {
		return cd_folio;
	}
	public void setCd_folio(String cd_folio) {
		this.cd_folio = cd_folio;
	}
	public String getNu_serie() {
		return nu_serie;
	}
	public void setNu_serie(String nu_serie) {
		this.nu_serie = nu_serie;
	}
	
	
}
