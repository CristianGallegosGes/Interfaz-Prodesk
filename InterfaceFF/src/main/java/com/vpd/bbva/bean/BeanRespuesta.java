package main.java.com.vpd.bbva.bean;

public class BeanRespuesta {
	private String mensaje;
	private int numeroFila;
	private boolean bandera;   /*Si es false termina*/
	private int factura;
	private int carta;
	
	
	
	
	
	public int getFactua() {
		return factura;
	}
	public void setFactua(int factura) {
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
	public int getNumeroFila() {
		return numeroFila;
	}
	public void setNumeroFila(int numeroFila) {
		this.numeroFila = numeroFila;
	}
	public boolean GetBandera() {
		return bandera;
	}
	public void setBandera(boolean bandera) {
		this.bandera = bandera;
	}
	
	
	
}
