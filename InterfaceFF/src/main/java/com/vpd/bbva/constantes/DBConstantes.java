package main.java.com.vpd.bbva.constantes;

public final class DBConstantes {
	public static final String DBPACKAGE = "PG_SICOFE";

	public static final String SICOFE_SPN_S_VALIDA_CARTA = DBPACKAGE + ".SP_VALIDA_CARTA";
	public static final String SICOFE_SPC_S_VALIDA_CARTA = "{ call " + SICOFE_SPN_S_VALIDA_CARTA + "(?,?,?,?,?)}";
}
