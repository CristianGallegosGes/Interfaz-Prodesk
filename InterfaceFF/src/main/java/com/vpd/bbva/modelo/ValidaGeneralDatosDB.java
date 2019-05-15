package main.java.com.vpd.bbva.modelo;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import main.java.com.vpd.bbva.bean.BeanConceptoFin;
import main.java.com.vpd.bbva.bean.BeanFF;
import main.java.com.vpd.bbva.bean.BeanFacturaNVA;
import main.java.com.vpd.bbva.bean.BeanRespuesta;
import main.java.com.vpd.bbva.conexion.Conexion;
import main.java.com.vpd.bbva.constantes.DBConstantes;
import oracle.jdbc.internal.OracleTypes;

public class ValidaGeneralDatosDB extends GeneralDao{

	private static final Logger LOG = Logger.getLogger(ValidaGeneralDatosDB.class);
	
	
	public BeanFacturaNVA DatosFactura(int facturaI, int cartaI,int proveedor,String sociedad, String viaP ) throws Exception {
		BeanFacturaNVA beanFacN = new BeanFacturaNVA();

		Conexion obConexion   = new Conexion();
		Connection con =  obConexion.AbreConexion();
		CallableStatement call = null;	
		Integer nuerror = null;
		ResultSet result = null;
		try {
			call = con.prepareCall(DBConstantes.SICOFE_CALL_VALIDA_FACTURA);
			call.registerOutParameter(1, OracleTypes.NUMBER);
			call.registerOutParameter(2, OracleTypes.VARCHAR);
			call.registerOutParameter(3, OracleTypes.CURSOR);
			call.setInt(4, 1); 		/** Accion en SP = 1 (Valida si exite la factura)*/
			call.setInt(5, facturaI);				/** Obligatiorio*/ 
			call.setInt(6, cartaI);					/** Obligatiorio*/	
			call.setInt(7, 0);
			call.setString(8, "");
			call.setInt(9, 0);
			call.setInt(10, 0);
			call.setInt(11, 0);
			call.setString(12, "VIA DE PAGO");
			call.setInt(13, 0);
			call.setString(14, "");
			call.setString(15, "");
			call.setInt(16, proveedor);
			call.setString(17, sociedad);
			call.setString(18, "MONEDA");
			call.execute();
			result = (ResultSet) call.getObject(3);
			nuerror = Integer.parseInt(call.getObject(1).toString());
			if(nuerror == 0) {
				beanFacN.setExito(nuerror);
				while(result.next()) {
					int i = 0;
					beanFacN.setEstatusF(result.getString(++i));
					beanFacN.setViaP(result.getString(++i));
					beanFacN.setCuentaBanc(result.getInt(++i));
					//beanFacN.setTpBanco(result.getString(++i));
				}
			}else {
				beanFacN.setExito(nuerror);
				beanFacN.setMensaje(call.getObject(2).toString());
			}
			
		} catch (Exception e) {
			LOG.warn("Error: "+e);
		}finally {
			closeAll(null, result, null, call, con, obConexion);
		}
		
		
		return beanFacN;
		
	}
	
	public HashMap<String, Object> DatosNvaFactura(BeanFacturaNVA factura) throws Exception {
		
		Integer nuerror = null;
		ResultSet result = null;
		Conexion obConexion   = new Conexion();
		Connection con =  obConexion.AbreConexion();
		CallableStatement call = null;
		HashMap<String, Object> beanres= new HashMap();
		
		try {
			
			call = con.prepareCall(DBConstantes.SICOFE_CALL_VALIDA_FACTURA);
			call.registerOutParameter(1, OracleTypes.NUMBER);
			call.registerOutParameter(2, OracleTypes.VARCHAR);
			call.registerOutParameter(3, OracleTypes.CURSOR);
			call.registerOutParameter(4, OracleTypes.NUMBER);
			call.registerOutParameter(5, OracleTypes.NUMBER);
			call.setInt(6, 2);		
			call.setInt(7, factura.getNu_factura());
			call.setInt(8, factura.getNu_carta());
			call.setInt(9, factura.getEstado());System.out.println(factura.getEstado());
			call.setString(10, factura.getIva());System.out.println(factura.getIva());
			call.setBigDecimal(11, (factura.getIsrRetenido()));System.out.println("isr RET"+factura.getIsrRetenido());
			call.setBigDecimal(12, factura.getIvaRetenido());System.out.println("UVA RET "+factura.getIvaRetenido());
			call.setBigDecimal(13, factura.getImpuestoCedular());System.out.println(factura.getImpuestoCedular());
			call.setString(14, factura.getViaP());System.out.println(factura.getViaP());
			call.setInt(15, factura.getCuentaBanc());System.out.println(factura.getCuentaBanc());
			call.setString(16, factura.getTpBanco());System.out.println(factura.getTpBanco());
			call.setString(17, factura.getEstatusF());System.out.println(factura.getEstatusF());
			call.setInt(18, factura.getProveedor());System.out.println(factura.getProveedor());
			call.setString(19, factura.getSociedad());System.out.println(factura.getSociedad());
			call.setString(20, factura.getMoneda());System.out.println(factura.getMoneda());
			call.execute();
			
			nuerror = new Integer(call.getObject(1).toString());
			
			System.out.println("object 1 "+call.getObject(4));
			System.out.println("Valor iva "+call.getObject(5));
			nuerror = Integer.parseInt(call.getObject(1).toString());
			if(nuerror == 0) {
				beanres.put("bandera", true);
				beanres.put("iva", call.getObject(4).toString());
				beanres.put("valorIva", call.getObject(5).toString());
				System.out.println(call.getObject(5).toString());
				System.out.println(call.getObject(4).toString());
			}else {
				beanres.put("bandera", false);
			}
			beanres.put("mensaje", call.getObject(2).toString());
			
			
		}catch(Exception e){
			LOG.warn("Error :  "+e);

		}finally {
			closeAll(null, result, null, call, con, obConexion);
		}
		
		return beanres;
	}
	
	
	
	public HashMap<String, Object> ValidaDatosConcep (int estado, String iva, BigDecimal isrRetenido, BigDecimal ivaRet, BigDecimal impuestoC, int proveedor, String sociedad ) throws Exception {
		HashMap<String, Object> respCon = new HashMap<String, Object>();
		Integer nuerror = null;
		ResultSet result = null;
		Conexion obConexion   = new Conexion();
		Connection con =  obConexion.AbreConexion();
		CallableStatement call = null;
		try {
			call = con.prepareCall(DBConstantes.SICOFE_CALL_VALIDA_FACTURA);
			call.registerOutParameter(1, OracleTypes.NUMBER);
			call.registerOutParameter(2, OracleTypes.VARCHAR);
			call.registerOutParameter(3, OracleTypes.CURSOR);
			call.registerOutParameter(4, OracleTypes.NUMBER);
			call.registerOutParameter(5, OracleTypes.NUMBER);
			call.setInt(6, 3);	//P_ACCION 3	
			call.setInt(7, 0);
			call.setInt(8, 0);
			call.setInt(9, estado);
			call.setString(10, iva);
			call.setBigDecimal(11, isrRetenido);
			call.setBigDecimal(12, ivaRet);
			call.setBigDecimal(13, impuestoC);
			call.setString(14, "");
			call.setInt(15, 0);
			call.setString(16, "");
			call.setString(17, "");
			call.setInt(18, proveedor);
			call.setString(19, sociedad);
			call.setString(20, "MONEDA");
			call.execute();
	
			nuerror = new Integer(call.getObject(1).toString());
			if(nuerror == 0) {
				respCon.put("bandera", true);
				respCon.put("iva", call.getObject(4).toString());
				respCon.put("valorIva", call.getObject(5).toString());
				System.out.println(call.getObject(5).toString());
				System.out.println(call.getObject(4).toString());
			}else {
				respCon.put("bandera", false);
			}
			respCon.put("mensaje", call.getObject(2).toString());
			
		}catch(Exception e){
			LOG.warn("Error: "+e);
		}finally {
			closeAll(null, result, null, call, con, obConexion);
		}
		
		return respCon;
	}
	
	
	public BeanRespuesta DatosNvaCarta (List<BeanFF> dtArregloC, String tpRegistroNF) throws Exception{
		Conexion obConexion   = new Conexion();
		Connection con =  obConexion.AbreConexion();
		CallableStatement call = null;
		boolean ejecucion = false;
		Integer nuerror = null;
		ResultSet result = null;
			
		BeanRespuesta resp = new BeanRespuesta();
		try {
			call = con.prepareCall(DBConstantes.SICOFE_SPC_S_VALIDA_CARTA);
			call.registerOutParameter(1, OracleTypes.NUMBER);
			call.registerOutParameter(2, OracleTypes.VARCHAR);
			call.registerOutParameter(3, OracleTypes.CURSOR);
			call.registerOutParameter(4, OracleTypes.VARCHAR); /*FIDEICOMISO*/
			call.registerOutParameter(5, OracleTypes.VARCHAR); /*PROVISION*/
			call.registerOutParameter(6, OracleTypes.VARCHAR); /*PERIODIFICACION*/
			call.setInt(7, 2);  /* Accion en SP*/
			call.setInt(8, 0);
			for(BeanFF bean : dtArregloC) {
				if(!ejecucion) {
					while(bean.getTp_registro().equals(tpRegistroNF)) { /**NF*/ System.out.println(bean.getFideicomiso());
					call.setString(9, bean.getTp_carta());System.out.println(bean.getTp_carta() );
					call.setInt(10, bean.getTp_pago());System.out.println(bean.getTp_pago() );
					call.setInt(11, bean.getNu_proveedor());System.out.println(bean.getNu_proveedor() );
					call.setString(12, bean.getSociedadRec());System.out.println(bean.getSociedadRec() );
					call.setString(13, bean.getGlg().trim());System.out.println(bean.getGlg() );
					call.setString(14, bean.getEmpGasBursa());System.out.println(bean.getEmpGasBursa() );
					call.setString(15, bean.getMondena());System.out.println(bean.getMondena() );
					call.setInt(16, bean.getRecAlternativo());System.out.println(bean.getRecAlternativo());
					call.setInt(17, bean.getCuentaGps());System.out.println(bean.getCuentaGps());
					call.setString(18, bean.getUsuarioCreador());System.out.println(bean.getUsuarioCreador());
					call.setString(19, bean.getPeriodificacion());System.out.println(bean.getPeriodificacion());
					call.setString(20, bean.getProviEjerAnterior());
					call.execute();
					ejecucion = true;
					
					
					nuerror = new Integer(call.getObject(1).toString());
					if(nuerror == 0) {
						resp.setBandera(true);
						resp.setMensaje(call.getObject(2).toString());
						if(new Integer(bean.getEmpGasBursa()) > 0) {
							bean.setDbfideicomiso(call.getObject(4).toString());System.out.println(bean.getDbfideicomiso());
						}
						bean.setDbperiodificacion(call.getObject(5).toString());System.out.println(bean.getDbperiodificacion());
						bean.setDbproviEjerAnterior(call.getObject(6).toString());System.out.println(bean.getDbproviEjerAnterior());
						
					}else {
						resp.setBandera(false);
						resp.setMensaje(call.getObject(2).toString());
						LOG.info(call.getObject(2).toString());
					}
					
					break;
					}
				}else {
					break;
				}
			}
			
		}catch (Exception e) {
			LOG.warn("Error " + e);
		}
		finally {
			closeAll(null, result, null, call, con, obConexion);
		}
		return resp;
		
		
	}
	
	public BeanFF  ValidaCarta(int carta) throws Exception {
		Conexion obConexion   = new Conexion();
		Connection con =  obConexion.AbreConexion();
		CallableStatement call = null;
		Integer cderror 	= null;
		ResultSet result 	= null;
		BeanFF beanCarta = new BeanFF();
		
		try {
			call = con.prepareCall(DBConstantes.SICOFE_SPC_S_VALIDA_CARTA);
			call.registerOutParameter(1, OracleTypes.NUMBER);
			call.registerOutParameter(2, OracleTypes.VARCHAR);
			call.registerOutParameter(3, OracleTypes.CURSOR);
			call.registerOutParameter(4, OracleTypes.VARCHAR); /*FIDEICOMISO*/
			call.registerOutParameter(5, OracleTypes.VARCHAR); /*PROVISION*/
			call.registerOutParameter(6, OracleTypes.VARCHAR); /*PERIODIFICACION*/
			call.setInt(7, 1); 	 	/*Accion 1 valida carta*/
			call.setInt(8, carta);		/*factura*/
			call.setString(9, "");	/*carta*/
			call.setString(10, "");
			call.setInt(11, 0);
			call.setString(12, "P_SOCIEDAD_REC");
			call.setString(13, "P_GLG");
			call.setString(14, "P_EMPRESA_GB");
			call.setString(15, "P_MONEDA");
			call.setInt(16, 0);
			call.setInt(17, 0);
			call.setString(18, "P_USUARIO_C");
			call.setString(19, "PERIODIFICACION");
			call.setString(20, "PROVISION");
			call.execute();
			
			LOG.info("Se llama al SP ");
			LOG.info("Regeso del llamado ");
			
			result = (ResultSet) call.getObject(3);
			
			
			
			cderror = new Integer(call.getObject(1).toString());
			System.out.println(cderror);
			if(cderror==0) {
				while(result.next()) {
					int i = 0;
					beanCarta.setNu_carta(new Integer(result.getString(++i)));
					beanCarta.setTp_carta(result.getString(++i));
					beanCarta.setTp_pago(result.getInt(++i));
					beanCarta.setUsuarioCreador(result.getString(++i));
					beanCarta.setSociedadRec(result.getString(++i));
					beanCarta.setNu_proveedor(new Integer(result.getString(++i)));
					beanCarta.setGlg(result.getString(++i));
					beanCarta.setCuentaGps(result.getInt(++i));
					beanCarta.setMondena(result.getString(++i));
					beanCarta.setNu_acreditado(result.getString(++i));
					beanCarta.setRecAlternativo(result.getInt(++i));
					beanCarta.setContrato(result.getInt(++i));  /*si es null hacer metodo para que guarde cero?*/
					beanCarta.setNu_pep(result.getString(++i));
					beanCarta.setContinua(true);
					beanCarta.setDescError(result.getObject(2).toString());
					
				}
				
			}else {
				beanCarta.setContinua(false);
				beanCarta.setDescError(result.getObject(2).toString());  //
				LOG.warn(result.getObject(2).toString() + carta );
				
			}
			
			
		}catch (Exception e) {
			LOG.warn("Error en SP " +DBConstantes.SICOFE_SPC_S_VALIDA_CARTA );
		}finally {
			closeAll(null, result, null, call, con, obConexion);
		}
		return beanCarta;
	}
	public int nuNota(int factura) throws Exception {
		Conexion obConexion   = new Conexion();
		Connection con =  obConexion.AbreConexion();
		CallableStatement call = null;
		
		ResultSet rs = null ;
		int numN = 0;
		try {
			call = con.prepareCall("SELECT COUNT(*) FROM TXWV106_NOTA WHERE NU_FACTURA = " + factura);
			rs = call.executeQuery();
				int conta = 0;
			while(rs.next()) {
				numN = Integer.parseInt(rs.getString(++conta));
			}
		}catch (Exception e) {
			LOG.warn("Error" + e);
		}finally {
			closeAll(null, rs, null, call, con, obConexion);
		}
		
		return numN;
	}
	
	
	
	public BigDecimal cdIva(String cd_iva) throws Exception {
		BigDecimal iva = null;
		ResultSet resultSet = null;
		Conexion obConexion   = new Conexion();
		Connection con =  obConexion.AbreConexion();
		CallableStatement call = null;
		
		try {
			String qry = "SELECT CD_VALOR_IVA FROM TXWV123_IVA WHERE NB_IVA = '" + cd_iva + "'";
			call = con.prepareCall(qry);
			resultSet = call.executeQuery();
			while(resultSet.next()) {
				iva = new BigDecimal(resultSet.getString("CD_VALOR_IVA"));
			}
		}catch (Exception e) {
			LOG.warn("Error en al consultar cd_iva "+ e);
		}finally {
			closeAll(null, resultSet, null, call, con, obConexion);
		}
		
		return iva;
	}
	
	
	public String parametro(int tp_param, String estatus){
		String parametro = null;

		try {
			ResultSet resultSet = null;
			Conexion obConexion   = new Conexion();
			Connection con =  obConexion.AbreConexion();
			CallableStatement call = null;
			
			String sql="SELECT CD_PARAM FROM GORAPR.TXWV180_PARAM_ALTA_FF WHERE TP_PARAM = " + tp_param +" AND CD_PARAM = '" + estatus+"'";
			call = con.prepareCall(sql);
			resultSet = call.executeQuery();
			while(resultSet.next()) {
				parametro = resultSet.getString("CD_PARAM");
			}
			
			closeAll(null, resultSet, null, call, con, obConexion);
			
		}catch (Exception e) {
			LOG.warn("Error" + e);
		}
		return parametro;
	}
	
	public String paramNC(int tp_param, String estatus) throws Exception {
		String parametro = null;
		ResultSet resultSet = null;
		Conexion obConexion   = new Conexion();
		Connection con =  obConexion.AbreConexion();
		CallableStatement call = null;
		try {
			call = con.prepareCall("SELECT NB_PARAM FROM GORAPR.TXWV180_PARAM_ALTA_FF WHERE TP_PARAM = " + tp_param +"AND CD_PARAM = '" + estatus+"'");
			resultSet = call.executeQuery();
			while(resultSet.next()) {
				parametro = resultSet.getString("NB_PARAM");
			}
			
			
		}catch (Exception e) {
			// TODO: handle exception
		}finally {
			closeAll(null, resultSet, null, call, con, obConexion);
		}
		return parametro;
	}
	
	public String existe(String tp_param) {
		String parametro = null;
		Conexion obConexion = null ;
		Connection con = null;
		CallableStatement call = null;
		ResultSet resultSet = null;

		try {
			obConexion = new Conexion();
			con = obConexion.AbreConexion();
			call = con.prepareCall("SELECT CD_PARAM FROM GORAPR.TXWV180_PARAM_ALTA_FF WHERE CD_PARAM = '" + tp_param+"'" );
			resultSet = call.executeQuery();
			while(resultSet.next()) {
				parametro = resultSet.getString("CD_PARAM");
			}
			
			
		}catch (Exception e) {
			LOG.warn("Error: " +e);
		}finally {
			closeAll(null, resultSet, null, call, con, obConexion);
		}
		return parametro;
	}
	
	public ArrayList<BigDecimal> totalNF (int factura) {
		ArrayList<BigDecimal> importesFac = new ArrayList<BigDecimal>();
		Conexion obConexion = null ;
		Connection con = null;
		CallableStatement call = null;
		
		BigDecimal total = null;
		ResultSet rs = null ;
		try {
			obConexion = new Conexion();
			con = obConexion.AbreConexion();
			String sql = "SELECT IM_TOTAL_NF, IM_ISR_RETENIDO_NF, IM_IVA_RETENIDO_NF, IM_IMPTO_CEDULA_NF, IM_IMPTO_OTROS_NF FROM TXWV103_FACTURA WHERE NU_FACTURA ="+factura;
			call = con.prepareCall(sql);
			rs = call.executeQuery();
			while(rs.next()) {
				importesFac.add(new BigDecimal(rs.getString("IM_TOTAL_NF")));
				importesFac.add(new BigDecimal(rs.getString("IM_ISR_RETENIDO_NF")));
				importesFac.add(new BigDecimal(rs.getString("IM_IVA_RETENIDO_NF")));
				importesFac.add(new BigDecimal(rs.getString("IM_IMPTO_CEDULA_NF")));
				importesFac.add(new BigDecimal(rs.getString("IM_IMPTO_OTROS_NF")));
				
			
			}
		}catch (Exception e) {
			LOG.warn("Error: " +e);
		}finally {
			closeAll(null, rs, null, call, con, obConexion);
		}
		
		return importesFac;
	}
}
