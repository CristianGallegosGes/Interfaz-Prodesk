package main.java.com.vpd.bbva.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import main.java.com.vpd.bbva.bean.BeanFF;

public class ValidaDatosCartaCtrol {

	public ValidaDatosCartaCtrol() {
		// TODO Auto-generated constructor stub
	}

	public HashMap<Integer, String> validaDatosCartaConsecutiva(ArrayList<BeanFF> listaCadenas, List<Integer> linea) {

		HashMap<Integer, String> validaCartaConse = new HashMap<Integer, String>();
		int contLinea = 0;
		for (int i = 1; i <= listaCadenas.size(); i++) {
			if (listaCadenas.size() == 1) {
				if (!listaCadenas.get(contLinea).getTp_carta().equals(listaCadenas.get(contLinea).getTp_carta())) {
					validaCartaConse.put(1, "ERROR EN LINEA" + linea.get(contLinea) + " CONSECUTIVO  "
							+ listaCadenas.get(contLinea).getConsecArch() + "/"
							+ listaCadenas.get(contLinea).getConsecNota() + " CAMPO -> \"CONSECUTIVO ARCHIVO\"");
					return validaCartaConse;
				}

				if (!(listaCadenas.get(contLinea).getTp_pago() == (listaCadenas.get(contLinea).getTp_pago()))) {
					validaCartaConse.put(2,
							"ERROR EN LINEA" + linea.get(contLinea) + " CONSECUTIVO  " 
									+ listaCadenas.get(contLinea).getConsecArch() + "/"
									+ listaCadenas.get(contLinea).getConsecNota() + " CAMPO -> \"TIPO PAGO\"");
					return validaCartaConse;
				}

				if (listaCadenas.get(contLinea).getNu_proveedor() != listaCadenas.get(contLinea).getNu_proveedor()) {
					validaCartaConse.put(3,
							"ERROR EN LINEA" + linea.get(contLinea) + " CONSECUTIVO  "
									+ listaCadenas.get(contLinea).getConsecArch() + "/"
									+ listaCadenas.get(contLinea).getConsecNota() + " CAMPO -> \"NUMERO PROVEEDOR\"");
					return validaCartaConse;
				}

				if (!listaCadenas.get(contLinea).getSociedadRec()
						.equals(listaCadenas.get(contLinea).getSociedadRec())) {
					validaCartaConse.put(4,
							"ERROR EN LINEA" + linea.get(contLinea) + " CONSECUTIVO  "
									+ listaCadenas.get(contLinea).getConsecArch() + "/"
									+ listaCadenas.get(contLinea).getConsecNota() + " CAMPO -> \"SOCIEDAD RECEPTORA\"");
					return validaCartaConse;
				}

				if (!listaCadenas.get(contLinea).getGlg().equals(listaCadenas.get(contLinea).getGlg())) {
					validaCartaConse.put(5,
							"ERROR EN LINEA" + linea.get(contLinea) + " CONSECUTIVO  "
									+ listaCadenas.get(contLinea).getConsecArch() + "/"
									+ listaCadenas.get(contLinea).getConsecNota() + " CAMPO -> \"GLG\"");
					return validaCartaConse;
				}

				if (!listaCadenas.get(contLinea).getEmpGasBursa()
						.equals(listaCadenas.get(contLinea).getEmpGasBursa())) {
					validaCartaConse.put(6, "ERROR EN LINEA" + linea.get(contLinea) + " CONSECUTIVO  "
							+ listaCadenas.get(contLinea).getConsecArch() + "/"
							+ listaCadenas.get(contLinea).getConsecNota() + " CAMPO -> \"EMPRESA GASTOS BURSA\"");
					return validaCartaConse;
				}

				if (!listaCadenas.get(contLinea).getFideicomiso()
						.equals(listaCadenas.get(contLinea).getFideicomiso())) {
					validaCartaConse.put(7,
							"ERROR EN LINEA" + linea.get(contLinea) + " CONSECUTIVO  "
									+ listaCadenas.get(contLinea).getConsecArch() + "/"
									+ listaCadenas.get(contLinea).getConsecNota() + " CAMPO -> \"FIDEICOMISO\"");
					return validaCartaConse;
				}

				if (!listaCadenas.get(contLinea).getNu_acreditado()
						.equals(listaCadenas.get(contLinea).getNu_acreditado())) {
					validaCartaConse.put(8, "ERROR EN LINEA" + linea.get(contLinea) + " CONSECUTIVO  "
							+ listaCadenas.get(contLinea).getConsecArch() + "/"
							+ listaCadenas.get(contLinea).getConsecNota() + " CAMPO -> \"NUMERO DE ACREDITADO\"");
					return validaCartaConse;
				}

				if (!listaCadenas.get(contLinea).getNu_pep().equals(listaCadenas.get(contLinea).getNu_pep())) {
					validaCartaConse.put(9,
							"ERROR EN LINEA" + linea.get(contLinea) + " CONSECUTIVO  "
									+ listaCadenas.get(contLinea).getConsecArch() + "/"
									+ listaCadenas.get(contLinea).getConsecNota() + " CAMPO -> \"NUMERO DE PEP\"");
					return validaCartaConse;
				}

				if (!listaCadenas.get(contLinea).getMondena().equals(listaCadenas.get(contLinea).getMondena())) {
					validaCartaConse.put(10,
							"ERROR EN LINEA" + linea.get(contLinea) + " CONSECUTIVO  "
									+ listaCadenas.get(contLinea).getConsecArch() + "/"
									+ listaCadenas.get(contLinea).getConsecNota() + " CAMPO -> \"MONEDA\"");
					return validaCartaConse;
				}

				if (!listaCadenas.get(contLinea).getPeriodificacion()
						.equals(listaCadenas.get(contLinea).getPeriodificacion())) {
					validaCartaConse.put(11,
							"ERROR EN LINEA" + linea.get(contLinea) + " CONSECUTIVO  "
									+ listaCadenas.get(contLinea).getConsecArch() + "/"
									+ listaCadenas.get(contLinea).getConsecNota() + " CAMPO -> \"PERIODIFICACIÓN\"");
					return validaCartaConse;
				}

				if (!listaCadenas.get(contLinea).getProviEjerAnterior()
						.equals(listaCadenas.get(contLinea).getProviEjerAnterior())) {
					validaCartaConse.put(12,
							"ERROR EN LINEA" + linea.get(contLinea) + " CONSECUTIVO  "
									+ listaCadenas.get(contLinea).getConsecArch() + "/"
									+ listaCadenas.get(contLinea).getConsecNota()
									+ " CAMPO -> \"PROVISIÓN EJERCICIO ANTERIOR\"");
					return validaCartaConse;
				}

				if (listaCadenas.get(contLinea).getContrato() != listaCadenas.get(contLinea).getContrato()) {
					validaCartaConse.put(13,
							"ERROR EN LINEA" + linea.get(contLinea) + " CONSECUTIVO  "
									+ listaCadenas.get(contLinea).getConsecArch() + "/"
									+ listaCadenas.get(contLinea).getConsecNota() + " CAMPO -> \"CONTRATO\"");
					return validaCartaConse;
				}

				if (listaCadenas.get(contLinea).getRecAlternativo() != listaCadenas.get(contLinea)
						.getRecAlternativo()) {
					validaCartaConse.put(14, "ERROR EN LINEA" + linea.get(contLinea) + " CONSECUTIVO  "
							+ listaCadenas.get(contLinea).getConsecArch() + "/"
							+ listaCadenas.get(contLinea).getConsecNota() + " CAMPO -> \"RECEPTOR ALTERNATIVO\"");
					return validaCartaConse;
				}

				if (listaCadenas.get(contLinea).getCuentaGps() != listaCadenas.get(contLinea).getCuentaGps()) {
					validaCartaConse.put(15,
							"ERROR EN LINEA" + linea.get(contLinea) + " CONSECUTIVO  "
									+ listaCadenas.get(contLinea).getConsecArch() + "/"
									+ listaCadenas.get(contLinea).getConsecNota()
									+ " CAMPO -> \"CUENTA GPS(CUENTA DE GASTO)\"");
					return validaCartaConse;
				}

				if (!listaCadenas.get(contLinea).getUsuarioCreador()
						.equals(listaCadenas.get(contLinea).getUsuarioCreador())) {
					validaCartaConse.put(16, "ERROR EN LINEA" + linea.get(contLinea) + " CONSECUTIVO  "
							+ listaCadenas.get(contLinea).getConsecArch() + "/"
							+ listaCadenas.get(contLinea).getConsecNota() + " CAMPO -> \"USUARIO CREADOR DE CARTA\"");
					return validaCartaConse;
				}
			} 
			
			if(!(i == listaCadenas.size())){
				if (!listaCadenas.get(contLinea).getTp_carta().equals(listaCadenas.get(i).getTp_carta())) {
					validaCartaConse.put(17, "ERROR EN LINEA" + linea.get(contLinea) + " CONSECUTIVO  "
							+ listaCadenas.get(contLinea).getConsecArch() + "/"
							+ listaCadenas.get(contLinea).getConsecNota() + " CAMPO -> \"CONSECUTIVO ARCHIVO\"");
					return validaCartaConse;
				}

				if (!(listaCadenas.get(contLinea).getTp_pago() == (listaCadenas.get(i).getTp_pago()))) {
					validaCartaConse.put(18,
							"ERROR EN LINEA" + linea.get(contLinea) + " CONSECUTIVO  "
									+ listaCadenas.get(contLinea).getConsecArch() + "/"
									+ listaCadenas.get(contLinea).getConsecNota() + " CAMPO -> \"TIPO PAGO\"");
					return validaCartaConse;
				}

				if (listaCadenas.get(contLinea).getNu_proveedor() != listaCadenas.get(i).getNu_proveedor()) {
					validaCartaConse.put(19,
							"ERROR EN LINEA" + linea.get(contLinea) + " CONSECUTIVO  "
									+ listaCadenas.get(contLinea).getConsecArch() + "/"
									+ listaCadenas.get(contLinea).getConsecNota() + " CAMPO -> \"NUMERO PROVEEDOR\"");
					return validaCartaConse;
				}

				if (!listaCadenas.get(contLinea).getSociedadRec().equals(listaCadenas.get(i).getSociedadRec())) {
					validaCartaConse.put(20,
							"ERROR EN LINEA" + linea.get(i) + " CONSECUTIVO  "
									+ listaCadenas.get(contLinea).getConsecArch() + "/"
									+ listaCadenas.get(contLinea).getConsecNota() + " CAMPO -> \"SOCIEDAD RECEPTORA\"");
					return validaCartaConse;
				}

				if (!listaCadenas.get(contLinea).getGlg().equals(listaCadenas.get(i).getGlg())) {
					validaCartaConse.put(21,
							"ERROR EN LINEA" + linea.get(contLinea) + " CONSECUTIVO  "
									+ listaCadenas.get(contLinea).getConsecArch() + "/"
									+ listaCadenas.get(contLinea).getConsecNota() + " CAMPO -> \"GLG\"");
					return validaCartaConse;
				}

				if (!listaCadenas.get(contLinea).getEmpGasBursa().equals(listaCadenas.get(i).getEmpGasBursa())) {
					validaCartaConse.put(22, "ERROR EN LINEA" + linea.get(contLinea) + " CONSECUTIVO  "
							+ listaCadenas.get(contLinea).getConsecArch() + "/"
							+ listaCadenas.get(contLinea).getConsecNota() + " CAMPO -> \"EMPRESA GASTOS BURSA\"");
					return validaCartaConse;
				}

				if (!listaCadenas.get(contLinea).getFideicomiso().equals(listaCadenas.get(i).getFideicomiso())) {
					validaCartaConse.put(23,
							"ERROR EN LINEA" + linea.get(contLinea) + " CONSECUTIVO  "
									+ listaCadenas.get(contLinea).getConsecArch() + "/"
									+ listaCadenas.get(contLinea).getConsecNota() + " CAMPO -> \"FIDEICOMISO\"");
					return validaCartaConse;
				}

				if (!listaCadenas.get(contLinea).getNu_acreditado().equals(listaCadenas.get(i).getNu_acreditado())) {
					validaCartaConse.put(24, "ERROR EN LINEA" + linea.get(contLinea) + " CONSECUTIVO  "
							+ listaCadenas.get(contLinea).getConsecArch() + "/"
							+ listaCadenas.get(contLinea).getConsecNota() + " CAMPO -> \"NUMERO DE ACREDITADO\"");
					return validaCartaConse;
				}

				if (!listaCadenas.get(contLinea).getNu_pep().equals(listaCadenas.get(i).getNu_pep())) {
					validaCartaConse.put(25,
							"ERROR EN LINEA" + linea.get(contLinea) + " CONSECUTIVO  "
									+ listaCadenas.get(contLinea).getConsecArch() + "/"
									+ listaCadenas.get(contLinea).getConsecNota() + " CAMPO -> \"NUMERO DE PEP\"");
					return validaCartaConse;
				}

				if (!listaCadenas.get(contLinea).getMondena().equals(listaCadenas.get(i).getMondena())) {
					validaCartaConse.put(26,
							"ERROR EN LINEA" + linea.get(contLinea) + " CONSECUTIVO  "
									+ listaCadenas.get(contLinea).getConsecArch() + "/"
									+ listaCadenas.get(contLinea).getConsecNota() + " CAMPO -> \"MONEDA\"");
					return validaCartaConse;
				}

				if (!listaCadenas.get(contLinea).getPeriodificacion()
						.equals(listaCadenas.get(i).getPeriodificacion())) {
					validaCartaConse.put(27,
							"ERROR EN LINEA" + linea.get(contLinea) + " CONSECUTIVO  "
									+ listaCadenas.get(contLinea).getConsecArch() + "/"
									+ listaCadenas.get(contLinea).getConsecNota() + " CAMPO -> \"PERIODIFICACIÓN\"");
					return validaCartaConse;
				}

				if (!listaCadenas.get(contLinea).getProviEjerAnterior()
						.equals(listaCadenas.get(i).getProviEjerAnterior())) {
					validaCartaConse.put(28,
							"ERROR EN LINEA" + linea.get(contLinea) + " CONSECUTIVO  "
									+ listaCadenas.get(contLinea).getConsecArch() + "/"
									+ listaCadenas.get(contLinea).getConsecNota()
									+ " CAMPO -> \"PROVISIÓN EJERCICIO ANTERIOR\"");
					return validaCartaConse;
				}

				if (listaCadenas.get(contLinea).getContrato() != listaCadenas.get(i).getContrato()) {
					validaCartaConse.put(29,
							"ERROR EN LINEA" + linea.get(contLinea) + " CONSECUTIVO  "
									+ listaCadenas.get(contLinea).getConsecArch() + "/"
									+ listaCadenas.get(contLinea).getConsecNota() + " CAMPO -> \"CONTRATO\"");
					return validaCartaConse;
				}

				if (listaCadenas.get(contLinea).getRecAlternativo() != listaCadenas.get(i).getRecAlternativo()) {
					validaCartaConse.put(30, "ERROR EN LINEA" + linea.get(contLinea) + " CONSECUTIVO  "
							+ listaCadenas.get(contLinea).getConsecArch() + "/"
							+ listaCadenas.get(contLinea).getConsecNota() + " CAMPO -> \"RECEPTOR ALTERNATIVO\"");
					return validaCartaConse;
				}

				if (listaCadenas.get(contLinea).getCuentaGps() != listaCadenas.get(i).getCuentaGps()) {
					validaCartaConse.put(31,
							"ERROR EN LINEA" + linea.get(contLinea) + " CONSECUTIVO  "
									+ listaCadenas.get(contLinea).getConsecArch() + "/"
									+ listaCadenas.get(contLinea).getConsecNota()
									+ " CAMPO -> \"CUENTA GPS(CUENTA DE GASTO)\"");
					return validaCartaConse;
				}

				if (!listaCadenas.get(contLinea).getUsuarioCreador().equals(listaCadenas.get(i).getUsuarioCreador())) {
					validaCartaConse.put(32, "ERROR EN LINEA" + linea.get(contLinea) + " CONSECUTIVO  "
							+ listaCadenas.get(contLinea).getConsecArch() + "/"
							+ listaCadenas.get(contLinea).getConsecNota() + " CAMPO -> \"USUARIO CREADOR DE CARTA\"");
					return validaCartaConse;
				}

			}
			contLinea++;
		}

		return validaCartaConse;
	}
}
