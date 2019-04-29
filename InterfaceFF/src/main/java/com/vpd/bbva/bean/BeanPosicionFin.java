package main.java.com.vpd.bbva.bean;

import java.math.BigDecimal;
import java.util.Date;

public class BeanPosicionFin {
	private int 	nu_carta;
	private String 	stConcep;
	private int 	cuenta;
	private String	nb_servicio;
	private Date	fh_Inicio;
	private Date	fh_Fin;
	private int		entidad;
	private int 	cd_iva;
	private BigDecimal 	im_iva;
	private BigDecimal 	nu_unidad;
	private BigDecimal	im_sin_iva;
	private BigDecimal 	im_subtotal;
	private String	cd_uso_gral_pos1;
	private String 	cd_uso_gral_pos2;
	private String 	cd_cr;
	
	
	
	private int nu_nota;   				/* variables para nota de credito*/
	private String tp_nota;
	private String cd_usr_modifica;
	
	
	
	
	public String getCd_usr_modifica() {
		return cd_usr_modifica;
	}
	public void setCd_usr_modifica(String cd_usr_modifica) {
		this.cd_usr_modifica = cd_usr_modifica;
	}
	public String getTp_nota() {
		return tp_nota;
	}
	public void setTp_nota(String tp_nota) {
		this.tp_nota = tp_nota;
	}
	public int getNu_nota() {
		return nu_nota;
	}
	public void setNu_nota(int nu_nota) {
		this.nu_nota = nu_nota;
	}
	public int getNu_carta() {
		return nu_carta;
	}
	public void setNu_carta(int nu_carta) {
		this.nu_carta = nu_carta;
	}
	public String getStConcep() {
		return stConcep;
	}
	public void setStConcep(String estConcep) {
		this.stConcep = estConcep;
	}
	public int getCuenta() {
		return cuenta;
	}
	public void setCuenta(int cuenta) {
		this.cuenta = cuenta;
	}
	public String getNb_servicio() {
		return nb_servicio;
	}
	public void setNb_servicio(String nb_servicio) {
		this.nb_servicio = nb_servicio;
	}
	public Date getFh_Inicio() {
		return fh_Inicio;
	}
	public void setFh_Inicio(Date fh_Inicio) {
		this.fh_Inicio = fh_Inicio;
	}
	public Date getFh_Fin() {
		return fh_Fin;
	}
	public void setFh_Fin(Date fh_Fin) {
		this.fh_Fin = fh_Fin;
	}
	public int getEntidad() {
		return entidad;
	}
	public void setEntidad(int entidad) {
		this.entidad = entidad;
	}
	public int getCd_iva() {
		return cd_iva;
	}
	public void setCd_iva(int cd_iva) {
		this.cd_iva = cd_iva;
	}
	public BigDecimal getIm_iva() {
		return im_iva;
	}
	public void setIm_iva(BigDecimal im_iva) {
		this.im_iva = im_iva;
	}
	public BigDecimal getNu_unidad() {
		return nu_unidad;
	}
	public void setNu_unidad(BigDecimal nu_unidad) {
		this.nu_unidad = nu_unidad;
	}
	public BigDecimal getIm_sin_iva() {
		return im_sin_iva;
	}
	public void setIm_sin_iva(BigDecimal im_sin_iva) {
		this.im_sin_iva = im_sin_iva;
	}
	public BigDecimal getIm_subtotal() {
		return im_subtotal;
	}
	public void setIm_subtotal(BigDecimal im_subtotal) {
		this.im_subtotal = im_subtotal;
	}
	public String getCd_uso_gral_pos1() {
		return cd_uso_gral_pos1;
	}
	public void setCd_uso_gral_pos1(String cd_uso_gral_pos1) {
		this.cd_uso_gral_pos1 = cd_uso_gral_pos1;
	}
	public String getCd_uso_gral_pos2() {
		return cd_uso_gral_pos2;
	}
	public void setCd_uso_gral_pos2(String cd_uso_gral_pos2) {
		this.cd_uso_gral_pos2 = cd_uso_gral_pos2;
	}
	public String getCd_cr() {
		return cd_cr;
	}
	public void setCd_cr(String cd_cr) {
		this.cd_cr = cd_cr;
	}
}
