package main.java.com.vpd.bbva.bean;

import java.math.BigDecimal;

public class BeanFacturaNVA {
	private int nu_factura = 0;
	private int nu_carta = 0;
	private int estado;
	private int iva;
	private BigDecimal IsrRetenido ;
	private BigDecimal IvaRetenido;
	private BigDecimal ImpuestoCedular;
	private String viaP;
	private int cuentaBanc;
	private String tpBanco;
	private String estatusF;
	private int exito;
	private String mensaje;
	
	
	
	
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public int getExito() {
		return exito;
	}
	public void setExito(int exito) {
		this.exito = exito;
	}
	public int getNu_factura() {
		return nu_factura;
	}
	public void setNu_factura(int nu_factura) {
		this.nu_factura = nu_factura;
	}
	public int getNu_carta() {
		return nu_carta;
	}
	public void setNu_carta(int nu_carta) {
		this.nu_carta = nu_carta;
	}
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}
	public int getIva() {
		return iva;
	}
	public void setIva(int iva) {
		this.iva = iva;
	}
	public BigDecimal getIsrRetenido() {
		return IsrRetenido;
	}
	public void setIsrRetenido(BigDecimal isrRetenido) {
		IsrRetenido = isrRetenido;
	}
	public BigDecimal getIvaRetenido() {
		return IvaRetenido;
	}
	public void setIvaRetenido(BigDecimal ivaRetenido) {
		IvaRetenido = ivaRetenido;
	}
	public BigDecimal getImpuestoCedular() {
		return ImpuestoCedular;
	}
	public void setImpuestoCedular(BigDecimal impuestoCedular) {
		ImpuestoCedular = impuestoCedular;
	}
	public String getViaP() {
		return viaP;
	}
	public void setViaP(String viaP) {
		this.viaP = viaP;
	}
	public int getCuentaBanc() {
		return cuentaBanc;
	}
	public void setCuentaBanc(int cuentaBanc) {
		this.cuentaBanc = cuentaBanc;
	}
	public String getTpBanco() {
		return tpBanco;
	}
	public void setTpBanco(String tpBanco) {
		this.tpBanco = tpBanco;
	}
	public String getEstatusF() {
		return estatusF;
	}
	public void setEstatusF(String estatusF) {
		this.estatusF = estatusF;
	}
	
	
}
