package main.java.com.vpd.bbva.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import main.java.com.vpd.bbva.bean.BeanFF;

public class ValidaDatosCartaCtrol {

	public ValidaDatosCartaCtrol() {
		// TODO Auto-generated constructor stub
	}

	public HashMap<Integer, String> validaDatosCartaConsecutiva(ArrayList<BeanFF> listaCadenas, List<Integer> linea, String aplicativoOrigen) {

		HashMap<Integer, String> validaCartaConse = new HashMap<Integer, String>();
		int contLinea = 0;
		for (int i = 1; i <= listaCadenas.size(); i++) {
			if (listaCadenas.size() == 1) {
				if (!listaCadenas.get(contLinea).getTp_carta().equals(listaCadenas.get(i).getTp_carta())) {
					validaCartaConse.put(1, "ERROR EN LINEA" + linea.get(contLinea) + " CONSECUTIVO  "
							+ listaCadenas.get(contLinea).getConsecArch() + "/"
							+ listaCadenas.get(contLinea).getConsecNota() + " CAMPO -> \"CONSECUTIVO CARTA\"");
					return validaCartaConse;
				}
				
				if(listaCadenas.get(contLinea).getNu_carta() > 0) {
					if(listaCadenas.get(contLinea).getNu_carta() != listaCadenas.get(i).getNu_carta()) {
						validaCartaConse.put(2, "ERROR EN LINEA" + linea.get(contLinea) + " CONSECUTIVO  "
								+ listaCadenas.get(contLinea).getConsecArch() + "/"
								+ listaCadenas.get(contLinea).getConsecNota() + " CAMPO -> \"NUMERO DE CARTA\"");
						return validaCartaConse;
					}
				}
				
				if(!(listaCadenas.get(contLinea).getTp_carta().equals(listaCadenas.get(i).getTp_carta()))) {
					if(listaCadenas.get(contLinea).getNu_carta() != listaCadenas.get(i).getNu_carta()) {
						validaCartaConse.put(3, "ERROR EN LINEA" + linea.get(contLinea) + " CONSECUTIVO  "
								+ listaCadenas.get(contLinea).getConsecArch() + "/"
								+ listaCadenas.get(contLinea).getConsecNota() + " CAMPO -> \"TIPO CARTA\"");
						return validaCartaConse;
					}
				}
				
				if (!(listaCadenas.get(contLinea).getTp_pago() == (listaCadenas.get(i).getTp_pago()))) {
					validaCartaConse.put(4,
							"ERROR EN LINEA" + linea.get(contLinea) + " CONSECUTIVO  "
									+ listaCadenas.get(contLinea).getConsecArch() + "/"
									+ listaCadenas.get(contLinea).getConsecNota() + " CAMPO -> \"TIPO PAGO\"");
					return validaCartaConse;
				}

				if (listaCadenas.get(contLinea).getNu_proveedor() != listaCadenas.get(i).getNu_proveedor()) {
					validaCartaConse.put(5,
							"ERROR EN LINEA" + linea.get(contLinea) + " CONSECUTIVO  "
									+ listaCadenas.get(contLinea).getConsecArch() + "/"
									+ listaCadenas.get(contLinea).getConsecNota() + " CAMPO -> \"NUMERO PROVEEDOR\"");
					return validaCartaConse;
				}

				if (!listaCadenas.get(contLinea).getSociedadRec().equals(listaCadenas.get(i).getSociedadRec())) {
					validaCartaConse.put(6,
							"ERROR EN LINEA" + linea.get(i) + " CONSECUTIVO  "
									+ listaCadenas.get(contLinea).getConsecArch() + "/"
									+ listaCadenas.get(contLinea).getConsecNota() + " CAMPO -> \"SOCIEDAD RECEPTORA\"");
					return validaCartaConse;
				}

				if (!listaCadenas.get(contLinea).getGlg().equals(listaCadenas.get(i).getGlg())) {
					validaCartaConse.put(7,
							"ERROR EN LINEA" + linea.get(contLinea) + " CONSECUTIVO  "
									+ listaCadenas.get(contLinea).getConsecArch() + "/"
									+ listaCadenas.get(contLinea).getConsecNota() + " CAMPO -> \"GLG\"");
					return validaCartaConse;
				}

				if (!listaCadenas.get(contLinea).getEmpGasBursa().equals(listaCadenas.get(i).getEmpGasBursa())) {
					validaCartaConse.put(8, "ERROR EN LINEA" + linea.get(contLinea) + " CONSECUTIVO  "
							+ listaCadenas.get(contLinea).getConsecArch() + "/"
							+ listaCadenas.get(contLinea).getConsecNota() + " CAMPO -> \"EMPRESA GASTOS BURSA\"");
					return validaCartaConse;
				}

				if (!listaCadenas.get(contLinea).getFideicomiso().equals(listaCadenas.get(i).getFideicomiso())) {
					validaCartaConse.put(9,
							"ERROR EN LINEA" + linea.get(contLinea) + " CONSECUTIVO  "
									+ listaCadenas.get(contLinea).getConsecArch() + "/"
									+ listaCadenas.get(contLinea).getConsecNota() + " CAMPO -> \"FIDEICOMISO\"");
					return validaCartaConse;
				}

				if (!listaCadenas.get(contLinea).getNu_acreditado().equals(listaCadenas.get(i).getNu_acreditado())) {
					validaCartaConse.put(10, "ERROR EN LINEA" + linea.get(contLinea) + " CONSECUTIVO  "
							+ listaCadenas.get(contLinea).getConsecArch() + "/"
							+ listaCadenas.get(contLinea).getConsecNota() + " CAMPO -> \"NUMERO DE ACREDITADO\"");
					return validaCartaConse;
				}

				if (!listaCadenas.get(contLinea).getNu_pep().equals(listaCadenas.get(i).getNu_pep())) {
					validaCartaConse.put(11,
							"ERROR EN LINEA" + linea.get(contLinea) + " CONSECUTIVO  "
									+ listaCadenas.get(contLinea).getConsecArch() + "/"
									+ listaCadenas.get(contLinea).getConsecNota() + " CAMPO -> \"NUMERO DE PEP\"");
					return validaCartaConse;
				}

				if (!listaCadenas.get(contLinea).getMondena().equals(listaCadenas.get(i).getMondena())) {
					validaCartaConse.put(12,
							"ERROR EN LINEA" + linea.get(contLinea) + " CONSECUTIVO  "
									+ listaCadenas.get(contLinea).getConsecArch() + "/"
									+ listaCadenas.get(contLinea).getConsecNota() + " CAMPO -> \"MONEDA\"");
					return validaCartaConse;
				}

				if (!listaCadenas.get(contLinea).getPeriodificacion()
						.equals(listaCadenas.get(i).getPeriodificacion())) {
					validaCartaConse.put(13,
							"ERROR EN LINEA" + linea.get(contLinea) + " CONSECUTIVO  "
									+ listaCadenas.get(contLinea).getConsecArch() + "/"
									+ listaCadenas.get(contLinea).getConsecNota() + " CAMPO -> \"PERIODIFICACIÓN\"");
					return validaCartaConse;
				}

				if (!listaCadenas.get(contLinea).getProviEjerAnterior()
						.equals(listaCadenas.get(i).getProviEjerAnterior())) {
					validaCartaConse.put(14,
							"ERROR EN LINEA" + linea.get(contLinea) + " CONSECUTIVO  "
									+ listaCadenas.get(contLinea).getConsecArch() + "/"
									+ listaCadenas.get(contLinea).getConsecNota()
									+ " CAMPO -> \"PROVISIÓN EJERCICIO ANTERIOR\"");
					return validaCartaConse;
				}

				if (listaCadenas.get(contLinea).getContrato() != listaCadenas.get(i).getContrato()) {
					validaCartaConse.put(15,
							"ERROR EN LINEA" + linea.get(contLinea) + " CONSECUTIVO  "
									+ listaCadenas.get(contLinea).getConsecArch() + "/"
									+ listaCadenas.get(contLinea).getConsecNota() + " CAMPO -> \"CONTRATO\"");
					return validaCartaConse;
				}
				
				if (listaCadenas.get(contLinea).getCuentaGps() != listaCadenas.get(i).getCuentaGps()) {
					validaCartaConse.put(16,
							"ERROR EN LINEA" + linea.get(contLinea) + " CONSECUTIVO  "
									+ listaCadenas.get(contLinea).getConsecArch() + "/"
									+ listaCadenas.get(contLinea).getConsecNota()
									+ " CAMPO -> \"CUENTA GPS(CUENTA DE GASTO)\"");
					return validaCartaConse;
				}

				if (!listaCadenas.get(contLinea).getUsuarioCreador().equals(listaCadenas.get(i).getUsuarioCreador())) {
					validaCartaConse.put(17, "ERROR EN LINEA" + linea.get(contLinea) + " CONSECUTIVO  "
							+ listaCadenas.get(contLinea).getConsecArch() + "/"
							+ listaCadenas.get(contLinea).getConsecNota() + " CAMPO -> \"USUARIO CREADOR DE CARTA\"");
					return validaCartaConse;
				}
				
				if(listaCadenas.get(contLinea).getNu_activo() != listaCadenas.get(i).getNu_activo()) {
					validaCartaConse.put(18, "ERROR EN LINEA" + linea.get(contLinea) + " CONSECUTIVO  "
							+ listaCadenas.get(contLinea).getConsecArch() + "/"
							+ listaCadenas.get(contLinea).getConsecNota() + " CAMPO -> \"NUMERO DE ACTIVO\"");
					return validaCartaConse;
				}
				
				if (!listaCadenas.get(contLinea).getAplicativoOrg().equals(aplicativoOrigen)) {
					validaCartaConse.put(19, "ERROR EN LINEA" + linea.get(contLinea) + " CONSECUTIVO  "
							+ listaCadenas.get(contLinea).getConsecArch() + "/"
							+ listaCadenas.get(contLinea).getConsecNota() + " CAMPO -> \"APLICATIVO ORIGEN\"");
					return validaCartaConse;
				}

			} 
			
			if(!(i == listaCadenas.size())){
				if (!listaCadenas.get(contLinea).getTp_carta().equals(listaCadenas.get(i).getTp_carta())) {
					validaCartaConse.put(1, "ERROR EN LINEA" + linea.get(contLinea) + " CONSECUTIVO  "
							+ listaCadenas.get(contLinea).getConsecArch() + "/"
							+ listaCadenas.get(contLinea).getConsecNota() + " CAMPO -> \"CONSECUTIVO CARTA\"");
					return validaCartaConse;
				}
				
				if(listaCadenas.get(contLinea).getNu_carta() > 0) {
					if(listaCadenas.get(contLinea).getNu_carta() != listaCadenas.get(i).getNu_carta()) {
						validaCartaConse.put(2, "ERROR EN LINEA" + linea.get(contLinea) + " CONSECUTIVO  "
								+ listaCadenas.get(contLinea).getConsecArch() + "/"
								+ listaCadenas.get(contLinea).getConsecNota() + " CAMPO -> \"NUMERO DE CARTA\"");
						return validaCartaConse;
					}
				}
				
				if(!(listaCadenas.get(contLinea).getTp_carta().equals(listaCadenas.get(i).getTp_carta()))) {
					if(listaCadenas.get(contLinea).getNu_carta() != listaCadenas.get(i).getNu_carta()) {
						validaCartaConse.put(3, "ERROR EN LINEA" + linea.get(contLinea) + " CONSECUTIVO  "
								+ listaCadenas.get(contLinea).getConsecArch() + "/"
								+ listaCadenas.get(contLinea).getConsecNota() + " CAMPO -> \"TIPO CARTA\"");
						return validaCartaConse;
					}
				}
				
				if (!(listaCadenas.get(contLinea).getTp_pago() == (listaCadenas.get(i).getTp_pago()))) {
					validaCartaConse.put(4,
							"ERROR EN LINEA" + linea.get(contLinea) + " CONSECUTIVO  "
									+ listaCadenas.get(contLinea).getConsecArch() + "/"
									+ listaCadenas.get(contLinea).getConsecNota() + " CAMPO -> \"TIPO PAGO\"");
					return validaCartaConse;
				}

				if (listaCadenas.get(contLinea).getNu_proveedor() != listaCadenas.get(i).getNu_proveedor()) {
					validaCartaConse.put(5,
							"ERROR EN LINEA" + linea.get(contLinea) + " CONSECUTIVO  "
									+ listaCadenas.get(contLinea).getConsecArch() + "/"
									+ listaCadenas.get(contLinea).getConsecNota() + " CAMPO -> \"NUMERO PROVEEDOR\"");
					return validaCartaConse;
				}

				if (!listaCadenas.get(contLinea).getSociedadRec().equals(listaCadenas.get(i).getSociedadRec())) {
					validaCartaConse.put(6,
							"ERROR EN LINEA" + linea.get(i) + " CONSECUTIVO  "
									+ listaCadenas.get(contLinea).getConsecArch() + "/"
									+ listaCadenas.get(contLinea).getConsecNota() + " CAMPO -> \"SOCIEDAD RECEPTORA\"");
					return validaCartaConse;
				}

				if (!listaCadenas.get(contLinea).getGlg().equals(listaCadenas.get(i).getGlg())) {
					validaCartaConse.put(7,
							"ERROR EN LINEA" + linea.get(contLinea) + " CONSECUTIVO  "
									+ listaCadenas.get(contLinea).getConsecArch() + "/"
									+ listaCadenas.get(contLinea).getConsecNota() + " CAMPO -> \"GLG\"");
					return validaCartaConse;
				}

				if (!listaCadenas.get(contLinea).getEmpGasBursa().equals(listaCadenas.get(i).getEmpGasBursa())) {
					validaCartaConse.put(8, "ERROR EN LINEA" + linea.get(contLinea) + " CONSECUTIVO  "
							+ listaCadenas.get(contLinea).getConsecArch() + "/"
							+ listaCadenas.get(contLinea).getConsecNota() + " CAMPO -> \"EMPRESA GASTOS BURSA\"");
					return validaCartaConse;
				}

				if (!listaCadenas.get(contLinea).getFideicomiso().equals(listaCadenas.get(i).getFideicomiso())) {
					validaCartaConse.put(9,
							"ERROR EN LINEA" + linea.get(contLinea) + " CONSECUTIVO  "
									+ listaCadenas.get(contLinea).getConsecArch() + "/"
									+ listaCadenas.get(contLinea).getConsecNota() + " CAMPO -> \"FIDEICOMISO\"");
					return validaCartaConse;
				}

				if (!listaCadenas.get(contLinea).getNu_acreditado().equals(listaCadenas.get(i).getNu_acreditado())) {
					validaCartaConse.put(10, "ERROR EN LINEA" + linea.get(contLinea) + " CONSECUTIVO  "
							+ listaCadenas.get(contLinea).getConsecArch() + "/"
							+ listaCadenas.get(contLinea).getConsecNota() + " CAMPO -> \"NUMERO DE ACREDITADO\"");
					return validaCartaConse;
				}

				if (!listaCadenas.get(contLinea).getNu_pep().equals(listaCadenas.get(i).getNu_pep())) {
					validaCartaConse.put(11,
							"ERROR EN LINEA" + linea.get(contLinea) + " CONSECUTIVO  "
									+ listaCadenas.get(contLinea).getConsecArch() + "/"
									+ listaCadenas.get(contLinea).getConsecNota() + " CAMPO -> \"NUMERO DE PEP\"");
					return validaCartaConse;
				}

				if (!listaCadenas.get(contLinea).getMondena().equals(listaCadenas.get(i).getMondena())) {
					validaCartaConse.put(12,
							"ERROR EN LINEA" + linea.get(contLinea) + " CONSECUTIVO  "
									+ listaCadenas.get(contLinea).getConsecArch() + "/"
									+ listaCadenas.get(contLinea).getConsecNota() + " CAMPO -> \"MONEDA\"");
					return validaCartaConse;
				}

				if (!listaCadenas.get(contLinea).getPeriodificacion()
						.equals(listaCadenas.get(i).getPeriodificacion())) {
					validaCartaConse.put(13,
							"ERROR EN LINEA" + linea.get(contLinea) + " CONSECUTIVO  "
									+ listaCadenas.get(contLinea).getConsecArch() + "/"
									+ listaCadenas.get(contLinea).getConsecNota() + " CAMPO -> \"PERIODIFICACIÓN\"");
					return validaCartaConse;
				}

				if (!listaCadenas.get(contLinea).getProviEjerAnterior()
						.equals(listaCadenas.get(i).getProviEjerAnterior())) {
					validaCartaConse.put(14,
							"ERROR EN LINEA" + linea.get(contLinea) + " CONSECUTIVO  "
									+ listaCadenas.get(contLinea).getConsecArch() + "/"
									+ listaCadenas.get(contLinea).getConsecNota()
									+ " CAMPO -> \"PROVISIÓN EJERCICIO ANTERIOR\"");
					return validaCartaConse;
				}

				if (listaCadenas.get(contLinea).getContrato() != listaCadenas.get(i).getContrato()) {
					validaCartaConse.put(15,
							"ERROR EN LINEA" + linea.get(contLinea) + " CONSECUTIVO  "
									+ listaCadenas.get(contLinea).getConsecArch() + "/"
									+ listaCadenas.get(contLinea).getConsecNota() + " CAMPO -> \"CONTRATO\"");
					return validaCartaConse;
				}
				
				if (listaCadenas.get(contLinea).getCuentaGps() != listaCadenas.get(i).getCuentaGps()) {
					validaCartaConse.put(16,
							"ERROR EN LINEA" + linea.get(contLinea) + " CONSECUTIVO  "
									+ listaCadenas.get(contLinea).getConsecArch() + "/"
									+ listaCadenas.get(contLinea).getConsecNota()
									+ " CAMPO -> \"CUENTA GPS(CUENTA DE GASTO)\"");
					return validaCartaConse;
				}

				if (!listaCadenas.get(contLinea).getUsuarioCreador().equals(listaCadenas.get(i).getUsuarioCreador())) {
					validaCartaConse.put(17, "ERROR EN LINEA" + linea.get(contLinea) + " CONSECUTIVO  "
							+ listaCadenas.get(contLinea).getConsecArch() + "/"
							+ listaCadenas.get(contLinea).getConsecNota() + " CAMPO -> \"USUARIO CREADOR DE CARTA\"");
					return validaCartaConse;
				}
				
				if(listaCadenas.get(contLinea).getNu_activo() != listaCadenas.get(i).getNu_activo()) {
					validaCartaConse.put(18, "ERROR EN LINEA" + linea.get(contLinea) + " CONSECUTIVO  "
							+ listaCadenas.get(contLinea).getConsecArch() + "/"
							+ listaCadenas.get(contLinea).getConsecNota() + " CAMPO -> \"NUMERO DE ACTIVO\"");
					return validaCartaConse;
				}
				
				if (!listaCadenas.get(contLinea).getAplicativoOrg().equals(aplicativoOrigen)) {
					validaCartaConse.put(19, "ERROR EN LINEA" + linea.get(contLinea) + " CONSECUTIVO  "
							+ listaCadenas.get(contLinea).getConsecArch() + "/"
							+ listaCadenas.get(contLinea).getConsecNota() + " CAMPO -> \"APLICATIVO ORIGEN\"");
					return validaCartaConse;
				}

			}
			contLinea++;
		}

		return validaCartaConse;
	}
}
