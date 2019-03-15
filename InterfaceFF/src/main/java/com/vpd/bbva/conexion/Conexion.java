package main.java.com.vpd.bbva.conexion;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import main.java.com.vpd.bbva.conf.PropertiesFile;
import main.java.com.vpd.bbva.constantes.Constantes;
import oracle.jdbc.pool.OracleDataSource;

public class Conexion {
	static final Logger LOG = Logger.getLogger(Conexion.class);

	protected Conexion conn;
	protected String usr = "";
	protected String pwd = "";
	protected String dvr = "";
	protected String svr = "";
	protected String dbn = "";
	protected String prt = "";
	protected String serviceName = "";

	public Conexion() throws Exception {
		if (!ConexionIntra()) {
			throw new Exception();
		}
	}

	public boolean ConexionIntra() throws Exception {
		boolean exito = false;
		PropertiesFile props = null;
		props = PropertiesFile.getInstance();

		dvr = props.getPropertiesFile(Constantes.PN_DRV);
		svr = props.getPropertiesFile(Constantes.PN_SRV);
		prt = props.getPropertiesFile(Constantes.PN_PRT);
		usr = props.getPropertiesFile(Constantes.PN_USR);
		pwd = props.getPropertiesFile(Constantes.PN_PWD);
		serviceName = props.getPropertiesFile(Constantes.PN_SN);

		if (dvr == null || svr == null || prt == null || usr == null || pwd == null || serviceName == null) {
			exito = false;
		} else {
			exito = true;
		}

		LOG.info("Parametros de conexion suficientes: " + exito);
		conn = null;
		return exito;

	}

	public Connection AbreConexion() throws SQLException {
		Connection conn = null;
		Context ctx = null;
		DataSource dataSource = null;
		try {
			LOG.info("Connection AbreConexion");
			ctx = new InitialContext();
			dataSource = (DataSource) ctx.lookup("jdbc/xwvEXTMX");
			conn = dataSource.getConnection();
			LOG.info("Conexion CON Datasource");

		} catch (Exception e) {
			LOG.info(e.getMessage());

			try {
				OracleDataSource ods = new OracleDataSource();
				ods.setDriverType(dvr);
				ods.setServerName(svr);
				ods.setPortNumber(Integer.parseInt(prt));
				ods.setUser(usr);
				ods.setPassword(pwd);
				ods.setServiceName(serviceName);
				conn = ods.getConnection();
				LOG.info("Conexion estandar");

			} catch (SQLException ex) {
				LOG.info("Error en la Conexion " + ex);
			}
		}
		return conn;

	}
}
