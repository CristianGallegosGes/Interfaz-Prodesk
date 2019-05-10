package main.java.com.vpd.bbva.modelo;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import main.java.com.vpd.bbva.bean.BeanConceptoFin;
import main.java.com.vpd.bbva.bean.BeanFF;
import main.java.com.vpd.bbva.bean.BeanFactura;
import main.java.com.vpd.bbva.bean.BeanNota;
import main.java.com.vpd.bbva.bean.BeanPosicionFin;
import main.java.com.vpd.bbva.bean.BeanRespuesta;
import main.java.com.vpd.bbva.conexion.Conexion;
import main.java.com.vpd.bbva.constantes.DBConstantes;
import oracle.jdbc.OracleTypes;

public class InsertaDao extends GeneralDao{
	static Logger LOG = Logger.getLogger(InsertaDao.class);

	
	public BeanRespuesta CreaCarta(List<BeanFF> datosCarta) throws Exception{
		BeanRespuesta respuesta = new BeanRespuesta();
		Conexion obConexion   = new Conexion();
		Connection con =  obConexion.AbreConexion();
		CallableStatement call = null;
		BeanRespuesta listaRet = new BeanRespuesta();
		
		boolean ejecucion = false;
		Integer cerror;	
		try {
			call = con.prepareCall(DBConstantes.SICOFE_CALL_SP_INSERT_CARTA);
			for(BeanFF factura : datosCarta) {
				ValidaGeneralDatosDB validaDatos = new ValidaGeneralDatosDB();
				String nf = validaDatos.parametro(8, factura.getTp_registro());
				while(factura.getTp_registro().equals(nf)) {
			
				call.setString(1, factura.getTp_carta());  				
				call.setString(2, factura.getUsuarioCreador());			
				call.setString(3, factura.getSociedadRec());			
				call.setInt(4, factura.getNu_proveedor());				
				call.setString(5, factura.getGlg());					
				call.setInt(6, factura.getCuentaGps());					
				call.setInt(7, factura.getTp_pago());					
				call.setString(8, factura.getNu_anticipo());			
				call.setDate(9, factura.getFecha_anticipo());  			System.out.println(factura.getFecha_anticipo());
				call.setString(10, null); /*CR*/ 									
				call.setString(11, factura.getMondena());				
				call.setInt(12, factura.getContrato());					
				call.setString(13, factura.getDbfideicomiso());			System.out.println(factura.getDbfideicomiso());
				call.setString(14, factura.getNu_acreditado());			
				call.setInt(15, factura.getRecAlternativo());			
				call.setString(16, factura.getEmpGasBursa());			
				call.setString(17, factura.getNu_pep());				
				call.setString(18, factura.getDbperiodificacion());		System.out.println(factura.getDbperiodificacion());
				call.setString(19, factura.getDbproviEjerAnterior());	System.out.println(factura.getDbproviEjerAnterior());
				call.registerOutParameter(20, OracleTypes.NUMBER);
				call.registerOutParameter(21, OracleTypes.DATE);
				call.registerOutParameter(22, OracleTypes.NUMBER);
				call.registerOutParameter(23, OracleTypes.VARCHAR);	
				call.execute();
				LOG.info("Fin de llamada al SP: "+ DBConstantes.SICOFE_CALL_SP_INSERT_CARTA);
				ejecucion = true;
				break;
				}
				if (ejecucion) {
					break;
				}
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
			respuesta.setMensaje("NO SE CREO LA CARTA");
			LOG.warn(call.getObject(23).toString());
			LOG.warn("Error: " + e);
		}finally {
			closeAll(null, null, null, call, con, obConexion);
		}
		return respuesta;
	}
	
	public HashMap<String, Object> insertaPosicionFinanciera(BeanPosicionFin posFac) throws Exception  {

		Integer exito = null;
		String nuPosFin = null;
		HashMap<String, Object> resp = new HashMap();
		
		Conexion obConexion   = new Conexion();
		Connection con =  obConexion.AbreConexion();
		CallableStatement call = null;
		
		try {
			
			call = con.prepareCall(DBConstantes.SICOFE_CALL_SP_INSERT_POSICION_FINANCIERA);
				call.setInt(1, posFac.getNu_carta());		System.out.println(posFac.getNu_carta());
				call.setString(2, posFac.getStConcep());	System.out.println(posFac.getStConcep());
				call.setInt(3, posFac.getCuenta());			System.out.println(posFac.getCuenta());
				call.setString(4, posFac.getNb_servicio());	System.out.println(posFac.getNb_servicio());
				call.setDate(5, posFac.getFh_Inicio());		System.out.println(posFac.getFh_Inicio());
				call.setDate(6, posFac.getFh_Fin());		System.out.println(posFac.getFh_Fin());
				call.setInt(7, posFac.getEntidad());		System.out.println(posFac.getEntidad());
				call.setInt(8, posFac.getCd_iva());			System.out.println(posFac.getCd_iva());
				call.setBigDecimal(9, posFac.getIm_iva());	System.out.println(posFac.getIm_iva());
				call.setBigDecimal(10, posFac.getNu_unidad());	System.out.println(posFac.getNu_unidad());
				call.setBigDecimal(11, posFac.getIm_sin_iva());	System.out.println(posFac.getIm_sin_iva());
				call.setBigDecimal(12, posFac.getIm_subtotal());System.out.println(posFac.getIm_subtotal());
				call.setString(13, posFac.getCd_uso_gral_pos1());System.out.println(posFac.getCd_uso_gral_pos1());
				call.setString(14, posFac.getCd_uso_gral_pos2());System.out.println(posFac.getCd_uso_gral_pos2());
				call.setString(15, posFac.getCd_cr());			System.out.println(posFac.getCd_cr());
				call.registerOutParameter(16, OracleTypes.NUMBER);
				call.registerOutParameter(17, OracleTypes.VARCHAR);
				call.execute();
				
				exito = new Integer(call.getObject(16).toString());
				nuPosFin = call.getObject(17).toString();
				resp.put("exito", exito);
				resp.put("nu_posicion_F", nuPosFin);				
			
		}catch (Exception e) {
			resp.put("exito", exito);
			LOG.warn("nuPosFin: "+nuPosFin);
		}finally {
			closeAll(null, null, null, call, con, obConexion);
		}
		
		
		return resp;
	}
	
	
	
	public HashMap<String, Object> inserFactura(BeanFactura beanFac) throws Exception {
		HashMap<String, Object> factura = new HashMap<String, Object>();
		int exito = 0;
		
		Conexion obConexion   = new Conexion();
		Connection con =  obConexion.AbreConexion();
		CallableStatement call = null;
		
		try {			
			con = obConexion.AbreConexion();
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
			call.setDate(27, beanFac.getFh_anticipo());
			call.setDate(28, beanFac.getFh_factura());
			call.setString(29, beanFac.getCd_folio());
			call.setString(30, beanFac.getNu_serie());
			call.registerOutParameter(31, OracleTypes.NUMBER);
			call.registerOutParameter(32, OracleTypes.NUMBER);
			call.registerOutParameter(33, OracleTypes.VARCHAR);
			call.execute();
			
				factura.put("factura", call.getObject(31).toString()); System.out.println(call.getObject(31).toString());
				factura.put("exito", Integer.parseInt(call.getObject(32).toString())); System.out.println(call.getObject(32).toString());
				factura.put("error", call.getObject(33).toString());
				
		}catch (Exception e) {
			LOG.warn("Error: " + e);
			LOG.warn(call.getObject(33).toString().toString());
		}finally {
			closeAll(null, null, null, call, con, obConexion);
		}
		return factura;
	}
	
	public Integer InsertNota (BeanNota nota) throws Exception{
		Integer exito = 0;
		
		Conexion obConexion   = new Conexion();
		Connection con =  obConexion.AbreConexion();
		CallableStatement call = null;
		
		try {			
			con = obConexion.AbreConexion();
			call= con.prepareCall(DBConstantes.SICOFE_CALL_SP_INSERT_NOTA);
			call.setInt(1, nota.getNu_factura());
			call.setInt(2, nota.getNu_nota());
			call.setString(3, nota.getTp_nota());
			call.setString(4, nota.getSt_nota());
			call.setString(5, nota.getCd_folio_sat());
			call.setString(6, nota.getNb_servicio());
			call.setDate(7,  nota.getFh_ini_servicio());
			call.setDate(8, nota.getFg_fin_servicio());
			call.setInt(9, nota.getCd_entidad());
			call.setInt(10, nota.getCd_iva());
			call.setBigDecimal(11, nota.getIm_iva());
			call.setBigDecimal(12, nota.getNu_unidad());
			call.setBigDecimal(13, nota.getIm_sin_iva());
			call.setBigDecimal(14, nota.getIm_subtotal());
			call.setString(15, nota.getNb_motivo());
			call.setString(16, nota.getCd_usr_modifica());
			call.setString(17, nota.getNb_nombre_xml());
			call.setString(18, nota.getNb_nombre_pdf());
			call.setString(19, nota.getTp_carga());
			call.setString(20, nota.getCd_uso_gral_not2());
			call.registerOutParameter(21, OracleTypes.NUMBER);
			call.registerOutParameter(22, OracleTypes.VARCHAR);
			call.execute();
			
			exito = Integer.parseInt(call.getObject(22).toString());
			
		} catch (Exception e) {
			LOG.warn("Error: " +e );
			LOG.warn( call.getObject(22).toString());
		}finally {
			closeAll(null, null, null, call, con, obConexion);
		}
		
		return exito;
	}
	
	public Integer insertConceptoFinan(BeanConceptoFin conceptoF) throws Exception{
		Integer exito = null;
		
		Conexion obConexion   = new Conexion();
		Connection con =  obConexion.AbreConexion();
		CallableStatement call = null;
		
		try {			
		con = obConexion.AbreConexion();
		call = con.prepareCall(DBConstantes.SICOFE_CALL_SP_INSERT_CONCEPTO_FINANCIERA);
		
		call.setInt(1, conceptoF.getNu_factura());				System.out.println(conceptoF.getNu_factura()+ ".");
		call.setString(2, conceptoF.getSt_concepto());			System.out.println(conceptoF.getSt_concepto() + ".");
		call.setInt(3, conceptoF.getNu_carta());				System.out.println(conceptoF.getNu_carta()+ ".");
		call.setInt(4, conceptoF.getNu_posicion_fin());			System.out.println(conceptoF.getNu_posicion_fin()+".");
		call.setString(5, conceptoF.getCd_usr_modifica());		System.out.println(conceptoF.getCd_usr_modifica());
		call.setString(6, conceptoF.getCd_usr_gral_con1());		System.out.println(conceptoF.getCd_usr_gral_con1()+".");
		call.setString(7, conceptoF.getCd_usr_gral_con2());		System.out.println(conceptoF.getCd_usr_gral_con2());
		call.setInt(8, conceptoF.getNu_nota());					System.out.println(conceptoF.getNu_nota()+ ".");  
		call.registerOutParameter(9, OracleTypes.NUMBER);
		call.registerOutParameter(10, OracleTypes.VARCHAR);
		call.execute();
		System.out.println("exito: "+call.getObject(9).toString());	
		exito = Integer.parseInt(call.getObject(9).toString());
			
		}catch (Exception e) {
			LOG.warn("Error:" + e);
			LOG.warn(call.getObject(10).toString());
		}finally {
			closeAll(null, null, null, call, con, obConexion);
		}
		return exito;
	}
	
	public Integer insertViaP (int nuFactura, String viaPa,  String tpBanco) throws Exception {
		Integer exito = 0;
		
		Conexion obConexion   = new Conexion();
		Connection con =  obConexion.AbreConexion();
		CallableStatement call = null;
		
		try {			
			con = obConexion.AbreConexion();
			call = con.prepareCall(DBConstantes.SICOFE_CALL_SP_INSERTA_VPAGO);
			call.setInt(1, nuFactura);
			call.setString(2, viaPa);
			call.setString(3, tpBanco);
			call.registerOutParameter(4, OracleTypes.NUMBER);
			call.registerOutParameter(5, OracleTypes.VARCHAR);
			call.execute();
			exito = Integer.parseInt(call.getObject(5).toString());
			
		}catch (Exception e) {
			LOG.warn("Error: " + e);
			LOG.warn("Error: " + call.getObject(5).toString());
		}finally {
			closeAll(null, null, null, call, con, obConexion);
		}
		
		return exito;
	}
	
	
	public boolean insertaConceptoNCFin(BeanPosicionFin pNotaCredito, int factura ) throws Exception {
		 Integer cveError 		= null;
		 boolean exito			= false;
		 
		 	Conexion obConexion   = new Conexion();
			Connection con =  obConexion.AbreConexion();
			CallableStatement call = null;
			
		 try {
				
			 con = obConexion.AbreConexion();
			 call = con.prepareCall(DBConstantes.SICOFE_CALL_SP_INSERT_NOTA_FIN);
			 call.setInt(1, pNotaCredito.getNu_carta());
			 call.setInt(2, factura);
			 call.setInt(3, pNotaCredito.getNu_nota());
			 call.setString(4, pNotaCredito.getTp_nota());
			 call.setString(5, pNotaCredito.getStConcep());
			 call.setInt(6, pNotaCredito.getCuenta());
			 call.setString(7, pNotaCredito.getNb_servicio());
			 call.setDate(8, pNotaCredito.getFh_Inicio());
			 call.setDate(9, pNotaCredito.getFh_Fin());
			 call.setInt(10, pNotaCredito.getEntidad());
			 call.setInt(11, pNotaCredito.getCd_iva());
			 call.setBigDecimal(12, pNotaCredito.getIm_iva());
			 call.setBigDecimal(13, pNotaCredito.getNu_unidad());
			 call.setBigDecimal(14, pNotaCredito.getIm_sin_iva());
			 call.setBigDecimal(15, pNotaCredito.getIm_subtotal());
			 call.setString(16, pNotaCredito.getCd_cr());
			 call.setString(17, pNotaCredito.getCd_usr_modifica());
			 call.registerOutParameter(18, OracleTypes.NUMBER);
			 call.registerOutParameter(19, OracleTypes.VARCHAR);
			 call.execute();
			 cveError = new Integer(call.getObject(18).toString());
			 if(cveError == 0) {
				 exito = true;
			 }			 
		 }catch(Exception e){
			 LOG.warn("Error: "+e);
			 LOG.warn("Error: "+call.getObject(19).toString());
		 }finally {
				closeAll(null, null, null, call, con, obConexion);
			}
		 
		 return exito;
	}
	
	
	public boolean calculaImportesFac(int nu_factura,  BigDecimal bdIsrRetenidoNF, BigDecimal bdIvaRetenidoNF, BigDecimal bdImpuestoCedularNF, BigDecimal bdOtrosImpuestosNF,
			  BigDecimal bdIsrRetenidoNC, BigDecimal bdIvaRetenidoNC, BigDecimal bdImpuestoCedularNC, BigDecimal bdOtrosImpuestosNC ) throws Exception {
		boolean exito = false;
		
		Conexion obConexion   = new Conexion();
		Connection con =  obConexion.AbreConexion();
		CallableStatement call = null;
		
		try {			
			con = obConexion.AbreConexion();
			call = con.prepareCall(DBConstantes.SICOFE_CALL_SP_UPDATE_IMP_FACTURA);
			call.setInt(1, nu_factura);
			call.setBigDecimal(2, bdIsrRetenidoNF);
			call.setBigDecimal(3, bdIvaRetenidoNF);
			call.setBigDecimal(4, bdImpuestoCedularNF);
			call.setBigDecimal(5, bdOtrosImpuestosNF);
			call.setBigDecimal(6, bdIsrRetenidoNC);
			call.setBigDecimal(7, bdIvaRetenidoNC);
			call.setBigDecimal(8, bdImpuestoCedularNC);
			call.setBigDecimal(9, bdOtrosImpuestosNC);
			call.registerOutParameter(10, OracleTypes.NUMBER);
			call.registerOutParameter(11, OracleTypes.VARCHAR);
			call.execute();
			
			if(new Integer(call.getObject(10).toString()) == 0) {
				exito = true;
			}
		}catch (Exception e) {
			// TODO: handle exception
		}finally {
				closeAll(null, null, null, call, con, obConexion);
		}
		return exito;
	}
	
}

