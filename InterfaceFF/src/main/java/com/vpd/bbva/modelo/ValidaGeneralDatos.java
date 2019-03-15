package main.java.com.vpd.bbva.modelo;

import java.sql.CallableStatement;
import java.sql.Connection;

import org.apache.log4j.Logger;

import main.java.com.vpd.bbva.conexion.Conexion;
import main.java.com.vpd.bbva.constantes.DBConstantes;
import oracle.jdbc.internal.OracleTypes;

public class ValidaGeneralDatos {
	static Conexion obConexion;
	static Connection con;
	static CallableStatement call;
	
	private static final Logger LOG = Logger.getLogger(ValidaGeneralDatos.class);
	
	public boolean  ValidaCarta(int carta) throws Exception {
		obConexion = new Conexion();
		con = obConexion.AbreConexion();
		Integer cderror = null;
		boolean status = false;
		try {
			call = con.prepareCall(DBConstantes.SICOFE_SPC_S_VALIDA_CARTA);
			call.registerOutParameter(1, OracleTypes.NUMBER);
			call.registerOutParameter(2, OracleTypes.VARCHAR);
			call.setInt(3, 0);  /*Accion 0 valida carta*/
			call.setInt(4, 0);
			call.setInt(5, carta);
			LOG.info("Se llama al SP ");
			call.execute();
			LOG.info("Regeso del llamado ");
			
			cderror = new Integer(call.getObject(1).toString());
			System.out.println(cderror);
			if(cderror==0) {
				status = true;
				
			}else {
				status = false;
				
				
			}
			
			
		}catch (Exception e) {
			LOG.warn("Error en SP " +DBConstantes.SICOFE_SPC_S_VALIDA_CARTA );
			
		}
		return status;
		
	}

}
