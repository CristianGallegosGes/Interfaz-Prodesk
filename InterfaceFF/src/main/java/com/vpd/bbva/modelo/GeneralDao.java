package main.java.com.vpd.bbva.modelo;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import main.java.com.vpd.bbva.conexion.Conexion;


public class GeneralDao {
	
	 public void closeAll(PreparedStatement pStatement, ResultSet resultSet, Statement statement, 
             CallableStatement callableStatement, Connection conn, Conexion objConexion) {
if (pStatement != null) {
try {
    pStatement.close();
    System.out.println("pStatement.close");
} catch (Exception exception) {
	System.out.println("Error al cerrar PreparedStatement "+ exception);
}
}

if (resultSet != null) {
try {
    resultSet.close();
    System.out.println("resultSet.close");
} catch (Exception exception) {
	System.out.println("Error al cerrar ResultSet "+ exception);
}
}

if (statement != null) {
try {
    statement.close();
    System.out.println("statement.close");
} catch (Exception exception) {
	System.out.println("Error al cerrar Statement " + exception);
}
}

if (callableStatement != null) {
try {
    callableStatement.close();
    System.out.println("callableStatement.close");
} catch (Exception exception) {
	System.out.println("Error al cerrar CallableStatement " + exception);
}
}

if (conn != null){
try{
    conn.close();
    System.out.println("conn.close");
} catch (Exception exception) {
	System.out.println("Error al cerrar la Connection" +exception);
}
}

if (objConexion != null){
try{
	objConexion = null;
	System.out.println("objConexion cerrado con exito");
} catch (Exception exception) {
	System.out.println("Error al hacer null Conexion " +exception);
}
}
}

}
