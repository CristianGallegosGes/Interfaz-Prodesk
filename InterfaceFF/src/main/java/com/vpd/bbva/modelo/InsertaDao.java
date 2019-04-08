package main.java.com.vpd.bbva.modelo;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.List;

import main.java.com.vpd.bbva.bean.BeanFF;
import main.java.com.vpd.bbva.bean.BeanRespuesta;
import main.java.com.vpd.bbva.conexion.Conexion;
import main.java.com.vpd.bbva.constantes.DBConstantes;

public class InsertaDao {
	Conexion obj ;
	Connection con;
	CallableStatement call;
	
	public BeanRespuesta CreaCarta(List<BeanFF> datosCarta) throws Exception{
		con = obj.AbreConexion();
		Integer cerror;	
		try {
			call = con.prepareCall(DBConstantes.SICOFE_CALL_SP_INSERT_FACTURA);
			for(BeanFF factura : datosCarta) {
			call.setString(1, factura.getEstatusF());
			call.execute();
			}
		cerror = new Integer(call.getObject("P_EXITO").toString());
		}catch (Exception e) {
			
		}

		
		
		return null;
		
	}
}
