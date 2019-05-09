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
	private String sociedad;
	private int proveedor;
	private String moneda;
	
	
	
	public String getSociedad() {
		return sociedad;
	}
	public void setSociedad(String sociedad) {
		this.sociedad = sociedad;
	}
	public int getProveedor() {
		return proveedor;
	}
	public void setProveedor(int proveedor) {
		this.proveedor = proveedor;
	}
	public String getMoneda() {
		return moneda;
	}
	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}
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
