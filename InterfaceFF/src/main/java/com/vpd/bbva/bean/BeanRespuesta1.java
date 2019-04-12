package main.java.com.vpd.bbva.bean;

public class BeanRespuesta1 {
	private String mensaje;
	private int numeroFila;
	private boolean bandera;   /*Si es false termina*/
	private String campo;
	
	
	
	
	public String getCampo() {
		return campo;
	}
	public void setCampo(String campo) {
		this.campo = campo;
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
