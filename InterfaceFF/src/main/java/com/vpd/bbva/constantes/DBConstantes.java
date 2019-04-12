package main.java.com.vpd.bbva.constantes;

public final class DBConstantes {
	public static final String DBPACKAGE = "PG_SICOFE";

	public static final String SICOFE_SPN_S_VALIDA_CARTA				= DBPACKAGE + ".SP_VALIDA_CARTA";
	public static final String SICOFE_SPC_S_VALIDA_CARTA				= "{ call " + SICOFE_SPN_S_VALIDA_CARTA + "(?,?,?,?,?)}";
	
	public static final String SICOFE_NOM_VALIDA_FACTURA 				= DBPACKAGE + ".SP_VALIDA_FACTURA";
	public static final String SICOFE_CALL_VALIDA_FACTURA				= "{ call " + SICOFE_NOM_VALIDA_FACTURA + "(?,?,?,?,?,?,?,?,?,?,?)}";
	
	public static final String SICOFE_NOM_SP_INSERT_CARTA				= DBPACKAGE + ".SP_INSERT_CARTA";
	public static final String SICOFE_CALL_SP_INSERT_CARTA				= "{ call " + SICOFE_NOM_SP_INSERT_CARTA + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	
	public static final String SICOFE_SP_NOM_INSERT_FACTURA				= DBPACKAGE + ".SP_INSERT_FACTURA";
	public static final String SICOFE_CALL_SP_INSERT_FACTURA			= "{ call " + SICOFE_SP_NOM_INSERT_FACTURA+"(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	
	public static final String SICOFE_NOM_SP_INSERT_POSICION_FINANCIERA = DBPACKAGE + ".SP_INSERT_POSICION_FINANCIERA";
	public static final String SICOFE_CALL_SP_INSERT_POSICION_FINANCIERA= "{ call " + SICOFE_NOM_SP_INSERT_POSICION_FINANCIERA + "(?,?,?,?,?,?,?,?,?,?,?)}";
	

	
}
