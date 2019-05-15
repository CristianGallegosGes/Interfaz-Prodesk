package main.java.com.vpd.bbva.bean;

import java.math.BigDecimal;

public class BeanRespuesta {
	private String mensaje;
	private int consecutivoA;
	private boolean bandera;   /*Si es false termina*/
	private int factura = 0;
	private int carta;
	private String tp_registro;
	private int ivaout;
	

	
	
	
	
	public boolean isBandera() {
		return bandera;
	}
	public int getIvaout() {
		return ivaout;
	}
	public void setIvaout(int ivaout) {
		this.ivaout = ivaout;
	}
	public String getTp_registro() {
		return tp_registro;
	}
	public void setTp_registro(String tp_registro) {
		this.tp_registro = tp_registro;
	}
	public int getConsecutivoA() {
		return consecutivoA;
	}
	public void setConsecutivoA(int consecutivoA) {
		this.consecutivoA = consecutivoA;
	}
	public int getFactura() {
		return factura;
	}
	public void setFactura(int factura) {
		this.factura = factura;
	}
	public int getCarta() {
		return carta;
	}
	public void setCarta(int carta) {
		this.carta = carta;
	}

	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public boolean GetBandera() {
		return bandera;
	}
	public void setBandera(boolean bandera) {
		this.bandera = bandera;
	}
	
	
	
}
