package main.java.com.vpd.bbva.modelo;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import main.java.com.vpd.bbva.bean.BeanFF;
import main.java.com.vpd.bbva.bean.BeanFacturaNVA;
import main.java.com.vpd.bbva.bean.BeanRespuesta;
import main.java.com.vpd.bbva.conexion.Conexion;
import main.java.com.vpd.bbva.constantes.DBConstantes;
import oracle.jdbc.internal.OracleTypes;

public class ValidaGeneralDatosDB {

	private static final Logger LOG = Logger.getLogger(ValidaGeneralDatosDB.class);
	
	
	public BeanFacturaNVA DatosFactura(int facturaI, int cartaI,int proveedor,String sociedad, String viaP ) {
		BeanFacturaNVA beanFacN = new BeanFacturaNVA();
			
		Integer nuerror = null;
		ResultSet result = null;
		try {
			Conexion obConexion   = new Conexion();
			Connection con =  obConexion.AbreConexion();
			CallableStatement call;
			call = con.prepareCall(DBConstantes.SICOFE_CALL_VALIDA_FACTURA);
			call.registerOutParameter(1, OracleTypes.NUMBER);
			call.registerOutParameter(2, OracleTypes.VARCHAR);
			call.registerOutParameter(3, OracleTypes.CURSOR);
			call.registerOutParameter(4, 1);  		/** Accion en SP = 1 (Valida si exite la factura)*/
			call.setInt(5, facturaI);				/** Obligatiorio*/ 
			call.setInt(6, cartaI);					/** Obligatiorio*/	
			call.setInt(7, 0);
			call.setString(8, "");
			call.setInt(9, 0);
			call.setInt(10, 0);
			call.setInt(11, 0);
			call.setString(12, viaP);
			call.setInt(13, 0);
			call.setString(14, "");
			call.setString(15, "");
			call.setInt(16, proveedor);
			call.setString(17, sociedad);
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
					beanFacN.setTpBanco(result.getString(++i));
				}
			}else {
				beanFacN.setExito(nuerror);
				beanFacN.setMensaje(call.getObject(2).toString());
			}
			
		} catch (Exception e) {
			LOG.warn("Error: "+e);
		}
		
		
		return beanFacN;
		
	}
	
	public BeanRespuesta DatosNvaFactura(BeanFacturaNVA factura) {
		
		Integer nuerror = null;
		ResultSet result = null;
		
		BeanRespuesta listaRet = new BeanRespuesta();
		
		try {
			Conexion obConexion   = new Conexion();
			Connection con =  obConexion.AbreConexion();
			CallableStatement call;
			call = con.prepareCall(DBConstantes.SICOFE_CALL_VALIDA_FACTURA);
			call.registerOutParameter(1, OracleTypes.NUMBER);
			call.registerOutParameter(2, OracleTypes.VARCHAR);
			call.registerOutParameter(3, OracleTypes.CURSOR);
			call.registerOutParameter(4, 2);		
			call.setInt(5, factura.getNu_factura());
			call.setInt(6, factura.getNu_carta());
			call.setInt(7, factura.getEstado());
			call.setInt(8, factura.getIva());
			call.setBigDecimal(9, (factura.getIsrRetenido()));
			call.setBigDecimal(10, factura.getIvaRetenido());
			call.setBigDecimal(11, factura.getImpuestoCedular());
			call.setString(12, factura.getViaP());
			call.setInt(13, factura.getCuentaBanc());
			call.setString(14, factura.getTpBanco());
			call.setString(15, factura.getEstatusF());
			call.execute();
	
			nuerror = new Integer(call.getObject(1).toString());
			if(nuerror == 0) {
				listaRet.setBandera(true);
			}else {
				listaRet.setBandera(false);
			}
			listaRet.setMensaje(call.getObject(2).toString());
			
			
		}catch(Exception e){
			LOG.warn("Error: "+e);
		}
		
		return listaRet;
	}
	
	
	
	public BeanRespuesta ValidaDatosConcep (int estado, int iva ) {
		BeanRespuesta respCon = new BeanRespuesta();
		Integer nuerror = null;
		ResultSet result = null;
		try {
			Conexion obConexion   = new Conexion();
			Connection con =  obConexion.AbreConexion();
			CallableStatement call;
			call = con.prepareCall(DBConstantes.SICOFE_CALL_VALIDA_FACTURA);
			call.registerOutParameter(1, OracleTypes.NUMBER);
			call.registerOutParameter(2, OracleTypes.VARCHAR);
			call.registerOutParameter(3, OracleTypes.CURSOR);
			call.registerOutParameter(4, 3);	//P_ACCION	
			call.setInt(5, 0);
			call.setInt(6, 0);
			call.setInt(7, estado);
			call.setInt(8, iva);
			call.setBigDecimal(9, new BigDecimal("0.0"));
			call.setBigDecimal(10, new BigDecimal("0.0"));
			call.setBigDecimal(11, new BigDecimal("0.0"));
			call.setString(12, "");
			call.setInt(13, 0);
			call.setString(14, "");
			call.setString(15, "");
			call.execute();
	
			nuerror = new Integer(call.getObject(1).toString());
			if(nuerror == 0) {
				respCon.setBandera(true);
			}else {
				respCon.setBandera(false);
			}
			respCon.setMensaje(call.getObject(2).toString());
			
			
		}catch(Exception e){
			LOG.warn("Error: "+e);
		}
		
		return respCon;
	}
	
	
	public BeanRespuesta DatosNvaCarta (List<BeanFF> dtArregloC, String tpRegistroNF) throws Exception{
		Conexion obConexion   = new Conexion();
		Connection con =  obConexion.AbreConexion();
		CallableStatement call;
		boolean ejecucion = false;
		Integer nuerror = null;
		ResultSet result = null;
		BeanRespuesta resp = new BeanRespuesta();
		try {
			call = con.prepareCall(DBConstantes.SICOFE_SPC_S_VALIDA_CARTA);
			call.registerOutParameter(1, OracleTypes.NUMBER);
			call.registerOutParameter(2, OracleTypes.VARCHAR);
			call.registerOutParameter(3, OracleTypes.CURSOR);
			call.registerOutParameter(4, OracleTypes.VARCHAR);
			call.registerOutParameter(5, OracleTypes.VARCHAR);
			call.registerOutParameter(6, OracleTypes.VARCHAR);
			call.setInt(7, 2);  /* Accion en SP*/
			call.setInt(8, 0);
			for(BeanFF bean : dtArregloC) {
				if(!ejecucion) {
					while(bean.getTp_registro().equals(tpRegistroNF)) { /**NF*/
					call.setString(9, bean.getTp_carta());
					call.setInt(10, bean.getTp_pago());/*Revisar si cambia a int*/
					call.setInt(11, bean.getNu_proveedor());
					call.setString(12, bean.getSociedadRec());
					call.setString(13, bean.getGlg().replaceAll("\\s", ""));System.out.println(bean.getGlg().replaceAll("\\s", "")+".");
					call.setString(14, bean.getEmpGasBursa());
					call.setString(15, bean.getMondena());
					call.setInt(16, bean.getRecAlternativo());
					call.setInt(17, bean.getCuentaGps());
					call.setString(18, bean.getUsuarioCreador());
					call.setString(19, bean.getPeriodificacion());
					call.setString(20, bean.getProviEjerAnterior());
					call.execute();
					ejecucion = true;
					break;
					}
				}else {
					break;
				}
			}
			nuerror = new Integer(call.getObject(1).toString());
			if(nuerror == 0) {
				resp.setBandera(true);
				resp.setMensaje(call.getObject(2).toString());
				
			}else {
				resp.setBandera(false);
				resp.setMensaje(call.getObject(2).toString());
				LOG.info(call.getObject(2).toString());
			}
		}catch (Exception e) {
			LOG.warn("Error " + e);
		}
		return resp;
		
		
	}
	
	public BeanFF  ValidaCarta(int carta) throws Exception {
		Conexion obConexion   = new Conexion();
		Connection con =  obConexion.AbreConexion();
		CallableStatement call;
		Integer cderror 	= null;
		ResultSet result 	= null;
		BeanFF beanCarta = new BeanFF();
		
		try {
			call = con.prepareCall(DBConstantes.SICOFE_SPC_S_VALIDA_CARTA);
			call.registerOutParameter(1, OracleTypes.NUMBER);
			call.registerOutParameter(2, OracleTypes.VARCHAR);
			call.registerOutParameter(3, OracleTypes.CURSOR);
			call.setInt(4, 1); 	 	/*Accion 1 valida carta*/
			call.setInt(5, carta);		/*factura*/
			call.setString(6, "");	/*carta*/
			call.setString(7, "");
			call.setInt(8, 0);
			call.setString(9, "P_SOCIEDAD_REC");
			call.setString(10, "P_GLG");
			call.setString(11, "P_EMPRESA_GB");
			call.setString(12, "P_MONEDA");
			call.setInt(13, 0);
			call.setInt(14, 0);
			call.setString(15, "P_USUARIO_C");
			call.execute();
			
			LOG.info("Se llama al SP ");
			call.execute();
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
			
		}
		return beanCarta;
	}
	public int nuNota(int factura) throws Exception {
		Conexion obConexion   = new Conexion();
		Connection con =  obConexion.AbreConexion();
		CallableStatement call;
		
		ResultSet rs ;
		int numN = 0;
		try {
			call = con.prepareCall("SELECT COUNT(*) FROM TXWV106_NOTA WHERE NU_FACTURA = " + factura);
			rs = call.executeQuery();
				int conta = 0;
			while(rs.next()) {
				numN = Integer.parseInt(rs.getString(++conta));
			}
		}catch (Exception e) {
			
		}
		
		return numN;
	}
	
	
	
	public int cdIva(int cd_iva) {
		int iva = 0;
		ResultSet resultSet;
		try {
			Conexion obConexion   = new Conexion();
			Connection con =  obConexion.AbreConexion();
			CallableStatement call;
			
			call = con.prepareCall("SELECT CD_VALOR_IVA FROM TXWV123_IVA WHERE NB_IVA = " + cd_iva);
			resultSet = call.executeQuery();
			while(resultSet.next()) {
				iva = Integer.parseInt(resultSet.getString("CD_VALOR_IVA"));
			}
		}catch (Exception e) {
			LOG.warn("Error en al consultar cd_iva " + e);
		}
		
		return iva;
	}
	
	public String parametro(int tp_param, String estatus) {
		String parametro = null;
		ResultSet resultSet;
		try {
			Conexion obConexion   = new Conexion();
			Connection con =  obConexion.AbreConexion();
			CallableStatement call;
			
			call = con.prepareCall("SELECT CD_PARAM FROM GORAPR.TXWV180_PARAM_ALTA_FF WHERE TP_PARAM = " + tp_param +"AND CD_PARAM = '" + estatus+"'");
			resultSet = call.executeQuery();
			while(resultSet.next()) {
				parametro = resultSet.getString("CD_PARAM");
			}
			
			
		}catch (Exception e) {
			// TODO: handle exception
		}
		return parametro;
	}
	
	
	public String existe(String tp_param) {
		String parametro = null;
		Conexion obConexion ;
		Connection con;
		CallableStatement call;

		try {
			ResultSet resultSet;
			obConexion = new Conexion();
			con = obConexion.AbreConexion();
			call = con.prepareCall("SELECT CD_PARAM FROM GORAPR.TXWV180_PARAM_ALTA_FF WHERE CD_PARAM = '" + tp_param+"'" );
			resultSet = call.executeQuery();
			while(resultSet.next()) {
				parametro = resultSet.getString("CD_PARAM");
			}
			
			
		}catch (Exception e) {
			LOG.warn("Error: " +e);
		}
		return parametro;
	}
	
	public BigDecimal totalNF (int factura) {
		
		Conexion obConexion ;
		Connection con;
		CallableStatement call;
		
		BigDecimal total = null;
		ResultSet rs ;
		try {
			obConexion = new Conexion();
			con = obConexion.AbreConexion();
			call = con.prepareCall(""+factura);
			rs = call.executeQuery();
			while(rs.next()) {
			total = new BigDecimal(rs.getString("SELECT IM_TOTAL_NF FROM TXWV103_FACTURA WHERE NU_FACTURA ="+factura));
			}
		}catch (Exception e) {
			LOG.warn("Error: " +e);
		}
		
		return total;
	}
}
