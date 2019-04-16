package main.java.com.vpd.bbva.modelo;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import main.java.com.vpd.bbva.bean.BeanPosicionFin;
import main.java.com.vpd.bbva.bean.BeanConceptoFin;
import main.java.com.vpd.bbva.bean.BeanFF;
import main.java.com.vpd.bbva.bean.BeanFactura;
import main.java.com.vpd.bbva.bean.BeanNota;
import main.java.com.vpd.bbva.bean.BeanRespuesta;
import main.java.com.vpd.bbva.conexion.Conexion;
import main.java.com.vpd.bbva.constantes.DBConstantes;
import oracle.jdbc.internal.OracleTypes;
import oracle.net.aso.b;

public class InsertaDao {
	static Logger LOG = Logger.getLogger(InsertaDao.class);
	
	Conexion obj ;
	Connection con;
	CallableStatement call;
	
	public BeanRespuesta CreaCarta(List<BeanFF> datosCarta) throws Exception{
		BeanRespuesta respuesta = new BeanRespuesta();
		con = obj.AbreConexion();
		Integer cerror;	
		try {
			call = con.prepareCall(DBConstantes.SICOFE_CALL_SP_INSERT_CARTA);
			for(BeanFF factura : datosCarta) {
				call.setString(1, factura.getTp_carta());
				call.setString(2, factura.getUsuarioCreador());
				call.setString(3, factura.getSociedadRec());
				call.setInt(4, factura.getNu_proveedor());
				call.setString(5, factura.getGlg());
				call.setInt(6, factura.getCuentaGps());
				call.setInt(7, factura.getTp_pago());
				call.setString(8, factura.getNu_anticipo());
				call.setDate(9,   (Date)factura.getFecha_anticipo()); /*	revisar el casteo */  
				call.setString(10, factura.getCentroCostos()); /*CR*/
				call.setString(11, factura.getMondena());
				call.setInt(12, factura.getContrato());
				call.setString(13, factura.getFideicomiso());
				call.setString(14, factura.getNu_acreditado());
				call.setInt(15, factura.getRecAlternativo());
				call.setString(16, factura.getEmpGasBursa());
				call.setString(17, factura.getNu_pep());
				call.setString(18, factura.getPeriodificacion());
				call.setString(19, factura.getProviEjerAnterior());
				call.registerOutParameter(20, OracleTypes.NUMBER);
				call.registerOutParameter(21, OracleTypes.DATE);
				call.registerOutParameter(22, OracleTypes.NUMBER);
				call.registerOutParameter(23, OracleTypes.VARCHAR);	
				call.execute();
				LOG.info("Fin de llamada al SP: "+ DBConstantes.SICOFE_CALL_SP_INSERT_CARTA);
			}
			cerror = new Integer(call.getObject(22).toString());
			if(cerror == 0) {
				respuesta.setBandera(true);
				respuesta.setMensaje(call.getObject(23).toString());
				respuesta.setCarta(Integer.parseInt(call.getObject(20).toString())); /*numero de carta devuelto*/
			}else {
				respuesta.setBandera(false);
				respuesta.setMensaje("No se creo la carta");
				LOG.warn(call.getObject(23).toString());
			}
		}catch (Exception e) {
			respuesta.setBandera(false);
			respuesta.setMensaje("No se creo la carta");
			LOG.warn(call.getObject(23).toString());
			LOG.warn("Error: " + e);
		}		
		return respuesta;
	}
	
	public HashMap<String, Object> insertaPosicionFinanciera(BeanPosicionFin posFac) throws SQLException {
		con = obj.AbreConexion();
		Integer cerror = null;
		String nuPosFin = null;
		HashMap<String, Object> resp = new HashMap();
		try {
			call = con.prepareCall(DBConstantes.SICOFE_NOM_SP_INSERT_POSICION_FINANCIERA);
				call.setInt(1, posFac.getNu_carta());
				call.setString(2, posFac.getStConcep());
				call.setInt(3, posFac.getCuenta());
				call.setString(4, posFac.getNb_servicio());
				call.setDate(5, (Date)posFac.getFh_Inicio());
				call.setDate(6, (Date)posFac.getFh_Fin());
				call.setInt(7, posFac.getEntidad());
				call.setInt(8, posFac.getCd_iva());
				call.setBigDecimal(9, posFac.getIm_iva());
				call.setBigDecimal(10, posFac.getNu_unidad());
				call.setBigDecimal(11, posFac.getIm_sin_iva());
				call.setBigDecimal(12, posFac.getIm_subtotal());
				call.setString(13, posFac.getCd_uso_gral_pos1());
				call.setString(14, posFac.getCd_uso_gral_pos2());
				call.setString(15, posFac.getCd_cr());
				call.registerOutParameter(16, OracleTypes.NUMBER);
				call.registerOutParameter(17, OracleTypes.VARCHAR);
				call.execute();
				
				cerror = new Integer(call.getObject(16).toString());
				nuPosFin = call.getObject(17).toString();
				resp.put("exito", cerror);
				resp.put("nu_posicion_F", nuPosFin);				
			
		}catch (Exception e) {
			resp.put("exito", cerror);
			LOG.warn("nuPosFin: "+nuPosFin);
		}
		
		
		return resp;
	}
	
	public String statusPosicionFF (String estatus) {
		String status = null;
		ResultSet resultSet;
		try {
			con = obj.AbreConexion();
			call = con.prepareCall("SELECT CD_PARAM FROM GORAPR.TXWV180_PARAM_ALTA_FF WHERE TP_PARAM = 5 AND CD_PARAM = " + estatus);
			resultSet = call.executeQuery();
			while(resultSet.next()) {
				status = resultSet.getString("CD_PARAM");
			}
		}catch (Exception e) {
			LOG.warn("Error en al consultar el estatus " + e);
		}
		
		return status;		
	}
	
	public int cdIva(int cd_iva) {
		int iva = 0;
		ResultSet resultSet;
		try {
			con = obj.AbreConexion();
			call = con.prepareCall("SELECT CD_VALOR_IVA FROM TXWV123_IVA WHERE NB_IVA = " + cd_iva);
			resultSet = call.executeQuery();
				iva = Integer.parseInt(resultSet.getString("CD_VALOR_IVA"));
			
		}catch (Exception e) {
			LOG.warn("Error en al consultar cd_iva " + e);
		}
		
		return iva;
	}
	
	public String parametro(int tp_param, String estatus) {
		String parametro = null;
		ResultSet resultSet;
		try {
			con = obj.AbreConexion();
			call = con.prepareCall("SELECT CD_PARAM FROM GORAPR.TXWV180_PARAM_ALTA_FF WHERE TP_PARAM = " + tp_param +"AND CD_PARAM = " + estatus);
			resultSet = call.executeQuery();
			parametro = resultSet.getString("CD_PARAM");
			
		}catch (Exception e) {
			// TODO: handle exception
		}
		return parametro;
	}
	
	
	public HashMap<String, Object> inserFactura(BeanFactura beanFac) throws SQLException{
		HashMap<String, Object> factura = new HashMap<String, Object>();
		int exito = 0;
		try {
			con = obj.AbreConexion();
			call = con.prepareCall(DBConstantes.SICOFE_CALL_SP_INSERT_FACTURA);
			call.setString(1, beanFac.getSt_factura());
			call.setString(2, beanFac.getCd_usr_modifica());
			call.setInt(3, beanFac.getNu_pedido());
			call.setInt(4, beanFac.getNu_carta());
			call.setBigDecimal(5, beanFac.getIm_subtotal_nf());
			call.setBigDecimal(6, beanFac.getIm_iva_total_nf());
			call.setBigDecimal(7, beanFac.getIm_sub_iva_oi_nf());
			call.setBigDecimal(8, beanFac.getIm_isr_retenido_nf());
			call.setBigDecimal(9, beanFac.getIm_iva_retenido_nf());
			call.setBigDecimal(10, beanFac.getIm_impto_cedula_nf());
			call.setBigDecimal(11, beanFac.getIm_impo_otros_nf());
			call.setBigDecimal(12, beanFac.getIm_total_nf());
			call.setBigDecimal(13, beanFac.getIm_subtotal_nc());
			call.setBigDecimal(14, beanFac.getIm_iva_total_nc());
			call.setBigDecimal(15, beanFac.getIm_sub_iva_oi_nc());
			call.setBigDecimal(16, beanFac.getIm_isr_retenido_nc());
			call.setBigDecimal(17, beanFac.getIm_iva_retenido_nc());
			call.setBigDecimal(18, beanFac.getIm_impto_cedula_nc());
			call.setBigDecimal(19, beanFac.getIm_impto_otros_nc());
			call.setBigDecimal(20, beanFac.getIm_total_nc());
			call.setBigDecimal(21, beanFac.getIm_bruto());
			call.setBigDecimal(22, beanFac.getIm_neto_a_pagar());
			call.setString(23, beanFac.getNb_importe_letra());
			call.setString(24, beanFac.getNb_motivo());
			call.setString(25, beanFac.getCd_uso_gral_fac2());
			call.setString(26, beanFac.getCd_anticipo());
			call.setDate(27, (Date)beanFac.getFh_anticipo());
			call.setDate(28, (Date)beanFac.getFh_factura());
			call.setString(29, beanFac.getCd_folio());
			call.setString(30, beanFac.getNu_serie());
			call.registerOutParameter(31, OracleTypes.NUMBER);
			call.registerOutParameter(32, OracleTypes.NUMBER);
			call.registerOutParameter(33, OracleTypes.VARCHAR);
			call.execute();
			
				factura.put("factura", call.getObject(31).toString());
				factura.put("exito", Integer.parseInt(call.getObject(32).toString()));
				factura.put("error", call.getObject(33).toString());
				
		}catch (Exception e) {
			LOG.warn("Error: " + e);
			LOG.warn(call.getObject(33).toString().toString());
		}
		return factura;
	}
	
	public Integer InsertNota (BeanNota nota) throws SQLException{
		Integer exito = 0;
		
		try {
			con = obj.AbreConexion();
			call= con.prepareCall(DBConstantes.SICOFE_CALL_SP_INSERT_NOTA);
			call.setInt(1, nota.getNu_factura());
			call.setInt(2, nota.getNu_nota());
			call.setString(3, nota.getTp_nota());
			call.setString(5, nota.getSt_nota());
			call.setString(6, nota.getCd_folio_sat());
			call.setString(7, nota.getNb_servicio());
			call.setDate(8, (Date) nota.getFh_ini_servicio());
			call.setDate(10, (Date)nota.getFh_ini_servicio());
			call.setInt(11, nota.getCd_entidad());
			call.setInt(12, nota.getCd_iva());
			call.setBigDecimal(13, nota.getNu_unidad());
			call.setBigDecimal(14, nota.getIm_sin_iva());
			call.setBigDecimal(15, nota.getIm_subtotal());
			call.setString(16, nota.getNb_motivo());
			call.setString(17, nota.getCd_usr_modifica());
			call.setString(18, nota.getNb_nombre_xml());
			call.setString(19, nota.getNb_nombre_pdf());
			call.setString(20, nota.getTp_carga());
			call.setString(21, nota.getCd_uso_gral_not2());
			call.registerOutParameter(22, OracleTypes.NUMBER);
			call.registerOutParameter(23, OracleTypes.VARCHAR);
			call.execute();
			
			exito = Integer.parseInt(call.getObject(22).toString());
			
		} catch (Exception e) {
			LOG.warn("Error: " +e );
			LOG.warn( call.getObject(22).toString());
		}
		
		return exito;
	}
	
	public Integer insertConceptoFinan(BeanConceptoFin conceptoF) throws SQLException{
		Integer exito = 0;
		try {
		con = obj.AbreConexion();
		call = con.prepareCall(DBConstantes.SICOFE_CALL_SP_INSERT_CONCEPTO_FINANCIERA);
		call.setInt(1, conceptoF.getNu_factura());
		call.setString(2, conceptoF.getSt_concepto());
		call.setInt(3, conceptoF.getNu_carta());
		call.setInt(4, conceptoF.getNu_posicion_fin());
		call.setString(5, conceptoF.getCd_usr_modifica());
		call.setString(6, conceptoF.getCd_usr_gral_con1());
		call.setString(7, conceptoF.getCd_usr_gral_con2());
		call.setInt(8, conceptoF.getNu_nota());
		call.registerOutParameter(9, OracleTypes.NUMBER);
		call.registerOutParameter(10, OracleTypes.VARCHAR);
		
		exito = Integer.parseInt(call.getObject(9).toString());
				
		}catch (Exception e) {
			LOG.warn("Error:" + e);
			LOG.warn(call.getObject(10).toString());
		}
		return exito;
	}
	
	public Integer insertViaP (int nuFactura, String moneda, String sociedad, int nu_proveedor) throws SQLException {
		Integer exito = 0;
		try {
			con = obj.AbreConexion();
			call = con.prepareCall(DBConstantes.SICOFE_CALL_SP_INSERTA_VPAGO);
			call.setInt(1, nuFactura);
			call.setString(2, moneda);
			call.setString(3, sociedad);
			call.setInt(4, nu_proveedor);
			call.registerOutParameter(5, OracleTypes.NUMBER);
			call.registerOutParameter(6, OracleTypes.VARCHAR);
			call.execute();
			exito = Integer.parseInt(call.getObject(5).toString());
			
		}catch (Exception e) {
			LOG.warn("Error: " + e);
			LOG.warn("Error: " + call.getObject(6).toString());
		}
		
		return exito;
	}
}
















