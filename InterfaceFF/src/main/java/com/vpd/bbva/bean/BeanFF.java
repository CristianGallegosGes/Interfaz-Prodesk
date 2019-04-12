package main.java.com.vpd.bbva.bean;

import java.math.BigDecimal;
import java.util.Date;


public class BeanFF {
	/*banderas de validacion*/
	private boolean continua;
	private String descError;
	
	/*Datos de interface*/
	private int consecArch;
	private int nu_carta =0;
	private int nu_factura = 0;
	private String tp_registro;
	private int consecNota;
	private String tp_carta;
	private int tp_pago;  /*	cambiar por int **/
	private int nu_proveedor; 
	private String sociedadRec;
	private String glg;
	private String empGasBursa;
	private String fideicomiso;     /*Por definirse*/
	private String nu_acreditado;
	private String nu_pep;
	private String mondena;
	private String periodificacion; 
	private String proviEjerAnterior;
	private int contrato;		/*Por definirse*/
	private int recAlternativo;
	private int cuentaGps;
	private String usuarioCreador;
	private String centroCostos;
	private String descripServicio;
	private Date fechaInicio;
	private Date fechaFin;
	private int estado;
	private BigDecimal importeUn;   /*con dos decimales*/
	private BigDecimal nu_unidades; /*con dos decimales*/
	private int iva;
	private BigDecimal isrRetenido;
	private BigDecimal ivaRetenido;
	private BigDecimal impuestoCedular;
	private BigDecimal otrosImpuestos;
	private BigDecimal descuento;
	private String comprobacion;
	private String nu_anticipo;
	private Date fecha_anticipo;    /* en la base de datos es un Date, en el SP de crear carta, espera igual un dato de tipo Date*/
	private String viaP;
	private int cuentaBanc;
	private String tpBanco;
	private int nu_activo;      /*Por definirse */
	private String estatusF;
	private String aplicativoOrg;
	
	
	
	public String getDescError() {
		return descError;
	}
	public void setDescError(String descError) {
		this.descError = descError;
	}
	public boolean getContinua() {
		return continua;
	}
	public void setContinua(boolean continua) {
		this.continua = continua;
	}
	public int getConsecArch() {
		return consecArch;
	}
	public void setConsecArch(int consecArch) {
		this.consecArch = consecArch;
	}
	public int getNu_carta() {
		return nu_carta;
	}
	public void setNu_carta(int nu_carta) {
		this.nu_carta = nu_carta;
	}
	public int getNu_factura() {
		return nu_factura;
	}
	public void setNu_factura(int nu_factura) {
		this.nu_factura = nu_factura;
	}
	public String getTp_registro() {
		return tp_registro;
	}
	public void setTp_registro(String tp_registro) {
		this.tp_registro = tp_registro;
	}
	public int getConsecNota() {
		return consecNota;
	}
	public void setConsecNota(int consecNota) {
		this.consecNota = consecNota;
	}
	public String getTp_carta() {
		return tp_carta;
	}
	public void setTp_carta(String tp_carta) {
		this.tp_carta = tp_carta;
	}
	public int getTp_pago() {
		return tp_pago;
	}
	public void setTp_pago(int tp_pago) {
		this.tp_pago = tp_pago;
	}
	public int getNu_proveedor() {
		return nu_proveedor;
	}
	public void setNu_proveedor(int nu_proveedor) {
		this.nu_proveedor = nu_proveedor;
	}
	public String getSociedadRec() {
		return sociedadRec;
	}
	public void setSociedadRec(String sociedadRec) {
		this.sociedadRec = sociedadRec;
	}
	public String getGlg() {
		return glg;
	}
	public void setGlg(String glg) {
		this.glg = glg;
	}
	public String getEmpGasBursa() {
		return empGasBursa;
	}
	public void setEmpGasBursa(String empGasBursa) {
		this.empGasBursa = empGasBursa;
	}
	public String getFideicomiso() {
		return fideicomiso;
	}
	public void setFideicomiso(String fideicomiso) {
		this.fideicomiso = fideicomiso;
	}
	public String getNu_acreditado() {
		return nu_acreditado;
	}
	public void setNu_acreditado(String nu_acreditado) {
		this.nu_acreditado = nu_acreditado;
	}
	public String getNu_pep() {
		return nu_pep;
	}
	public void setNu_pep(String nu_pep) {
		this.nu_pep = nu_pep;
	}
	public String getMondena() {
		return mondena;
	}
	public void setMondena(String mondena) {
		this.mondena = mondena;
	}
	public String getPeriodificacion() {
		return periodificacion;
	}
	public void setPeriodificacion(String periodificacion) {
		this.periodificacion = periodificacion;
	}
	public String getProviEjerAnterior() {
		return proviEjerAnterior;
	}
	public void setProviEjerAnterior(String proviEjerAnterior) {
		this.proviEjerAnterior = proviEjerAnterior;
	}
	public int getContrato() {
		return contrato;
	}
	public void setContrato(int contrato) {
		this.contrato = contrato;
	}
	public int getRecAlternativo() {
		return recAlternativo;
	}
	public void setRecAlternativo(int recAlternativo) {
		this.recAlternativo = recAlternativo;
	}
	public int getCuentaGps() {
		return cuentaGps;
	}
	public void setCuentaGps(int cuentaGps) {
		this.cuentaGps = cuentaGps;
	}
	public String getUsuarioCreador() {
		return usuarioCreador;
	}
	public void setUsuarioCreador(String usuarioCreador) {
		this.usuarioCreador = usuarioCreador;
	}
	public String getCentroCostos() {
		return centroCostos;
	}
	public void setCentroCostos(String centroCostos) {
		this.centroCostos = centroCostos;
	}
	public String getDescripServicio() {
		return descripServicio;
	}
	public void setDescripServicio(String descripServicio) {
		this.descripServicio = descripServicio;
	}
	public Date getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public Date getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}
	public BigDecimal getImporteUn() {
		return importeUn;
	}
	public void setImporteUn(BigDecimal importeUn) {
		this.importeUn = importeUn;
	}
	public BigDecimal getNu_unidades() {
		return nu_unidades;
	}
	public void setNu_unidades(BigDecimal nu_unidades) {
		this.nu_unidades = nu_unidades;
	}
	public int getIva() {
		return iva;
	}
	public void setIva(int iva) {
		this.iva = iva;
	}
	public BigDecimal getIsrRetenido() {
		return isrRetenido;
	}
	public void setIsrRetenido(BigDecimal isrRetenido) {
		this.isrRetenido = isrRetenido;
	}
	public BigDecimal getIvaRetenido() {
		return ivaRetenido;
	}
	public void setIvaRetenido(BigDecimal ivaRetenido) {
		this.ivaRetenido = ivaRetenido;
	}
	public BigDecimal getImpuestoCedular() {
		return impuestoCedular;
	}
	public void setImpuestoCedular(BigDecimal impuestoCedular) {
		this.impuestoCedular = impuestoCedular;
	}
	public BigDecimal getOtrosImpuestos() {
		return otrosImpuestos;
	}
	public void setOtrosImpuestos(BigDecimal otrosImpuestos) {
		this.otrosImpuestos = otrosImpuestos;
	}
	public BigDecimal getDescuento() {
		return descuento;
	}
	public void setDescuento(BigDecimal descuento) {
		this.descuento = descuento;
	}
	public String getComprobacion() {
		return comprobacion;
	}
	public void setComprobacion(String comprobacion) {
		this.comprobacion = comprobacion;
	}
	public String getNu_anticipo() {
		return nu_anticipo;
	}
	public void setNu_anticipo(String nu_anticipo) {
		this.nu_anticipo = nu_anticipo;
	}
	public Date getFecha_anticipo() {
		return fecha_anticipo;
	}
	public void setFecha_anticipo(Date fecha_anticipo) {
		this.fecha_anticipo = fecha_anticipo;
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
	public int getNu_activo() {
		return nu_activo;
	}
	public void setNu_activo(int nu_activo) {
		this.nu_activo = nu_activo;
	}
	public String getEstatusF() {
		return estatusF;
	}
	public void setEstatusF(String estatusF) {
		this.estatusF = estatusF;
	}
	public String getAplicativoOrg() {
		return aplicativoOrg;
	}
	public void setAplicativoOrg(String aplicativoOrg) {
		this.aplicativoOrg = aplicativoOrg;
	}
	
	
	
		
	
}
