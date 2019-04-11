package main.java.com.vpd.bbva.modelo;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import main.java.com.vpd.bbva.bean.BeanFF;
import main.java.com.vpd.bbva.bean.BeanRespuesta;
import main.java.com.vpd.bbva.conexion.Conexion;
import main.java.com.vpd.bbva.constantes.DBConstantes;
import oracle.jdbc.internal.OracleTypes;

public class ValidaGeneralDatosDB {
	static Conexion obConexion ;
	static Connection con;
	static CallableStatement call;
	
	private static final Logger LOG = Logger.getLogger(ValidaGeneralDatosDB.class);
	
	
	public BeanFF DatosFactura(int facturaI, int cartaI ) throws SQLException{
		BeanFF beanResp = new BeanFF();
		con = obConexion.AbreConexion();
		Integer nuerror = null;
		ResultSet result = null;
		try {
			call = con.prepareCall(DBConstantes.SICOFE_CALL_VALIDA_FACTURA);
			call.registerOutParameter(1, OracleTypes.NUMBER);
			call.registerOutParameter(2, OracleTypes.VARCHAR);
			call.registerOutParameter(3, OracleTypes.CURSOR);
			call.registerOutParameter(4, 1);  						/** Accion en SP = 1 (Valida si exite la factura)*/
			call.setInt(5, facturaI);				/** Obligatiorio*/ 
			call.setInt(6, cartaI);					/** Obligatiorio*/	
			call.setInt(7, 0);
			call.setString(8, "");
			call.setInt(9, 0);
			call.setInt(10, 0);
			call.setInt(11, 0);
			call.setString(12, "");
			call.setInt(13, 0);
			call.setString(14, "");
			call.setString(15, "");
			
			result = (ResultSet) call.getObject(3);
			while(result.next()) {
				int i = 0;
				beanResp.setEstado(result.getInt(++i));
				
			}
			
			
		} catch (Exception e) {
			LOG.warn("Error: "+e);
		}
		
		
		return beanResp;
		
	}
	
	public BeanRespuesta DatosNvaFactura(List<BeanFF> facturaB) throws SQLException{
		con = obConexion.AbreConexion();
		Integer nuerror = null;
		ResultSet result = null;
		
		BeanRespuesta listaRet = new BeanRespuesta();
		
		try {
			call = con.prepareCall(DBConstantes.SICOFE_CALL_VALIDA_FACTURA);
			call.registerOutParameter(1, OracleTypes.NUMBER);
			call.registerOutParameter(2, OracleTypes.VARCHAR);
			call.registerOutParameter(3, OracleTypes.CURSOR);
			call.registerOutParameter(4, 2);
			for(BeanFF factura : facturaB) {
			call.setInt(5, factura.getNu_factura());
			call.setInt(6, factura.getNu_carta());
			call.setInt(7, factura.getEstado());
			call.setString(8, factura.getIva());
			call.setInt(9, (factura.getIsrRetenido().intValueExact()));
			call.setInt(10, factura.getIvaRetenido().intValueExact());
			call.setInt(11, factura.getImpuestoCedular().intValueExact());
			call.setString(12, factura.getViaP());
			call.setInt(13, factura.getCuentaBanc());
			call.setString(14, factura.getTpBanco());
			call.setString(15, factura.getEstatusF());
			//break;
			}
			nuerror = new Integer((Integer) call.getObject(1));
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
	
	public BeanRespuesta DatosNvaCarta (List<BeanFF> dtArregloC) throws SQLException{
		con = obConexion.AbreConexion();
		Integer nuerror = null;
		ResultSet result = null;
		BeanRespuesta resp = new BeanRespuesta();
		try {
			call = con.prepareCall(DBConstantes.SICOFE_SPC_S_VALIDA_CARTA);
			call.registerOutParameter(1, OracleTypes.NUMBER);
			call.registerOutParameter(2, OracleTypes.VARCHAR);
			call.registerOutParameter(3, OracleTypes.CURSOR);
			call.setInt(4, 2);  /* Accion en SP*/
			call.setInt(5, 0);
			for(BeanFF bean : dtArregloC) {
				call.setString(6, bean.getTp_carta());
				call.setInt(7, bean.getTp_pago());/*Revisar si cambia a int*/
				call.setInt(8, bean.getNu_proveedor());
				call.setString(9, bean.getSociedadRec());
				call.setString(10, bean.getGlg());
				call.setString(11, bean.getEmpGasBursa());
				call.setString(12, bean.getMondena());
				call.setInt(13, bean.getRecAlternativo());
				call.setInt(14, bean.getCuentaGps());
				call.setString(15, bean.getUsuarioCreador());
				call.execute();
				break;
			}
			nuerror = new Integer(call.getObject(1).toString());
			if(nuerror == 0) {
				resp.setBandera(true);
				resp.setMensaje(call.getObject(2).toString());
				
			}else {
				resp.setBandera(false);
				resp.setMensaje(call.getObject(2).toString());
			}
		}catch (Exception e) {
			LOG.warn("Error " + e);
		}
		return resp;
		
		
	}
	
	public BeanFF  ValidaCarta(int carta) throws Exception {
		obConexion = new Conexion();
		con = obConexion.AbreConexion();
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
					beanCarta.setUsuarioCreador(result.getString(++i));
					beanCarta.setSociedadRec(result.getString(++i));
					beanCarta.setNu_proveedor(new Integer(result.getString(++i)));
					beanCarta.setGlg(result.getString(++i));
					beanCarta.setCuentaGps(result.getInt(++i));
					beanCarta.setMondena(result.getString(++i));
					beanCarta.setRecAlternativo(result.getInt(++i));
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

}
