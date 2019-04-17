package main.java.com.vpd.bbva.bean;

public class BeanRespuesta {
	private String mensaje;
	private int consecutivoA;
	private boolean bandera;   /*Si es false termina*/
	private int factura;
	private int carta;
	
	
	
	
	
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
