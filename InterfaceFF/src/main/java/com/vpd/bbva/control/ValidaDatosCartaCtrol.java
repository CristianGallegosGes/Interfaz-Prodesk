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
				if (!(listaCadenas.get(contLinea).getConsecArch() == listaCadenas.get(contLinea).getConsecArch())) {
					validaCartaConse.put(1, "ERROR EST EN LINEA" + linea.get(contLinea) + " CONSECUTIVO  "
							+ listaCadenas.get(contLinea).getConsecArch() + "/"
							+ listaCadenas.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS  EN EL CAMPO -> \"CONSECUTIVO CARTA\"");
					return validaCartaConse;
				}
				
				if(listaCadenas.get(contLinea).getNu_carta() > 0) {
					if(listaCadenas.get(contLinea).getNu_carta() != listaCadenas.get(contLinea).getNu_carta()) {
						validaCartaConse.put(2, "ERROR EST EN LINEA" + linea.get(contLinea) + " CONSECUTIVO  "
								+ listaCadenas.get(contLinea).getConsecArch() + "/"
								+ listaCadenas.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS  EN EL CAMPO -> \"NUMERO DE CARTA\"");
						return validaCartaConse;
					}
				}
				
				if(!(listaCadenas.get(contLinea).getTp_carta() == null && listaCadenas.get(contLinea).getTp_carta() == null)) {
					if(!(listaCadenas.get(contLinea).getTp_carta().equals(listaCadenas.get(contLinea).getTp_carta()))) {
							validaCartaConse.put(3, "ERROR EST EN LINEA" + linea.get(contLinea) + " CONSECUTIVO  "
									+ listaCadenas.get(contLinea).getConsecArch() + "/"
									+ listaCadenas.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS  EN EL CAMPO -> \"TIPO CARTA\"");
							return validaCartaConse;
					}
				}
				
				if (!(listaCadenas.get(contLinea).getTp_pago() == (listaCadenas.get(contLinea).getTp_pago()))) {
					validaCartaConse.put(4,
							"ERROR EST EN LINEA" + linea.get(contLinea) + " CONSECUTIVO  "
									+ listaCadenas.get(contLinea).getConsecArch() + "/"
									+ listaCadenas.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS  EN EL CAMPO -> \"TIPO PAGO\"");
					return validaCartaConse;
				}

				if (listaCadenas.get(contLinea).getNu_proveedor() != listaCadenas.get(contLinea).getNu_proveedor()) {
					validaCartaConse.put(5,
							"ERROR EST EN LINEA" + linea.get(contLinea) + " CONSECUTIVO  "
									+ listaCadenas.get(contLinea).getConsecArch() + "/"
									+ listaCadenas.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS  EN EL CAMPO -> \"NUMERO PROVEEDOR\"");
					return validaCartaConse;
				}
				
				if(!(listaCadenas.get(contLinea).getSociedadRec() == null && listaCadenas.get(contLinea).getSociedadRec() == null)) {
					if (!listaCadenas.get(contLinea).getSociedadRec().equals(listaCadenas.get(contLinea).getSociedadRec())) {
						validaCartaConse.put(6,
								"ERROR EST EN LINEA" + linea.get(i) + " CONSECUTIVO  "
										+ listaCadenas.get(contLinea).getConsecArch() + "/"
										+ listaCadenas.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS  EN EL CAMPO -> \"SOCIEDAD RECEPTORA\"");
						return validaCartaConse;
					}
				}
				
				if(!(listaCadenas.get(contLinea).getGlg() == null && listaCadenas.get(contLinea).getGlg() == null)) {
					if (!listaCadenas.get(contLinea).getGlg().equals(listaCadenas.get(contLinea).getGlg())) {
						validaCartaConse.put(7,
								"ERROR EST EN LINEA" + linea.get(contLinea) + " CONSECUTIVO  "
										+ listaCadenas.get(contLinea).getConsecArch() + "/"
										+ listaCadenas.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS  EN EL CAMPO -> \"GLG\"");
						return validaCartaConse;
					}
				}
				
				if(!(listaCadenas.get(contLinea).getEmpGasBursa() == null  && listaCadenas.get(contLinea).getEmpGasBursa() == null)) {
					if (!listaCadenas.get(contLinea).getEmpGasBursa().equals(listaCadenas.get(contLinea).getEmpGasBursa())) {
						validaCartaConse.put(8, "ERROR EST EN LINEA" + linea.get(contLinea) + " CONSECUTIVO  "
								+ listaCadenas.get(contLinea).getConsecArch() + "/"
								+ listaCadenas.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS  EN EL CAMPO -> \"EMPRESA GASTOS BURSA\"");
						return validaCartaConse;
					}
				}
				
				if(!(listaCadenas.get(contLinea).getFideicomiso() == null && listaCadenas.get(contLinea).getFideicomiso() == null)) {
					if (!listaCadenas.get(contLinea).getFideicomiso().equals(listaCadenas.get(contLinea).getFideicomiso())) {
						validaCartaConse.put(9,
								"ERROR EST EN LINEA" + linea.get(contLinea) + " CONSECUTIVO  "
										+ listaCadenas.get(contLinea).getConsecArch() + "/"
										+ listaCadenas.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS  EN EL CAMPO -> \"FIDEICOMISO\"");
						return validaCartaConse;
					}
				}
				
				if(!(listaCadenas.get(contLinea).getNu_acreditado() == null && listaCadenas.get(contLinea).getNu_acreditado() == null)) {
					if (!listaCadenas.get(contLinea).getNu_acreditado().equals(listaCadenas.get(contLinea).getNu_acreditado())) {
						validaCartaConse.put(10, "ERROR EST EN LINEA" + linea.get(contLinea) + " CONSECUTIVO  "
								+ listaCadenas.get(contLinea).getConsecArch() + "/"
								+ listaCadenas.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS  EN EL CAMPO -> \"NUMERO DE ACREDITADO\"");
						return validaCartaConse;
					}
				}
				
				if(!(listaCadenas.get(contLinea).getNu_pep() == null && listaCadenas.get(contLinea).getNu_pep() == null )) {
					if (!listaCadenas.get(contLinea).getNu_pep().equals(listaCadenas.get(contLinea).getNu_pep())) {
						validaCartaConse.put(11,
								"ERROR EST EN LINEA" + linea.get(contLinea) + " CONSECUTIVO  "
										+ listaCadenas.get(contLinea).getConsecArch() + "/"
										+ listaCadenas.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS  EN EL CAMPO -> \"NUMERO DE PEP\"");
						return validaCartaConse;
					}
				}
				
				if(!(listaCadenas.get(contLinea).getMondena() == null &&  listaCadenas.get(contLinea).getMondena() == null)) {
					if (!listaCadenas.get(contLinea).getMondena().equals(listaCadenas.get(contLinea).getMondena())) {
						validaCartaConse.put(12,
								"ERROR EST EN LINEA" + linea.get(contLinea) + " CONSECUTIVO  "
										+ listaCadenas.get(contLinea).getConsecArch() + "/"
										+ listaCadenas.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS  EN EL CAMPO -> \"MONEDA\"");
						return validaCartaConse;
					}
				}
				
				if(!(listaCadenas.get(contLinea).getPeriodificacion() == null && listaCadenas.get(contLinea).getPeriodificacion() == null)) {
					if (!listaCadenas.get(contLinea).getPeriodificacion()
							.equals(listaCadenas.get(contLinea).getPeriodificacion())) {
						validaCartaConse.put(13,
								"ERROR EST EN LINEA" + linea.get(contLinea) + " CONSECUTIVO  "
										+ listaCadenas.get(contLinea).getConsecArch() + "/"
										+ listaCadenas.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS  EN EL CAMPO -> \"PERIODIFICACIÓN\"");
						return validaCartaConse;
					}
				}
				
				if(!(listaCadenas.get(contLinea).getProviEjerAnterior() == null && listaCadenas.get(contLinea).getProviEjerAnterior() == null)) {
					if (!listaCadenas.get(contLinea).getProviEjerAnterior()
							.equals(listaCadenas.get(contLinea).getProviEjerAnterior())) {
						validaCartaConse.put(14,
								"ERROR EST EN LINEA" + linea.get(contLinea) + " CONSECUTIVO  "
										+ listaCadenas.get(contLinea).getConsecArch() + "/"
										+ listaCadenas.get(contLinea).getConsecNota()
										+ " SE ENCONTRARON DIFERENCIAS DE DATOS  EN EL CAMPO -> \"PROVISIÓN EJERCICIO ANTERIOR\"");
						return validaCartaConse;
					}
				}
				if (listaCadenas.get(contLinea).getContrato() != listaCadenas.get(contLinea).getContrato()) {
					validaCartaConse.put(15,
							"ERROR EST EN LINEA" + linea.get(contLinea) + " CONSECUTIVO  "
									+ listaCadenas.get(contLinea).getConsecArch() + "/"
									+ listaCadenas.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS  EN EL CAMPO -> \"CONTRATO\"");
					return validaCartaConse;
				}
				
				if(listaCadenas.get(contLinea).getRecAlternativo() != listaCadenas.get(contLinea).getRecAlternativo()) {
					validaCartaConse.put(16, "ERROR EST EN LINEA" + linea.get(contLinea) + " CONSECUTIVO  "
							+ listaCadenas.get(contLinea).getConsecArch() + "/"
							+ listaCadenas.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS  EN EL CAMPO -> \"RECEPTOR ALTERNATIVO\"");
					return validaCartaConse;
				}
				
				if (listaCadenas.get(contLinea).getCuentaGps() != listaCadenas.get(contLinea).getCuentaGps()) {
					validaCartaConse.put(17,
							"ERROR EST EN LINEA" + linea.get(contLinea) + " CONSECUTIVO  "
									+ listaCadenas.get(contLinea).getConsecArch() + "/"
									+ listaCadenas.get(contLinea).getConsecNota()
									+ " SE ENCONTRARON DIFERENCIAS DE DATOS  EN EL CAMPO -> \"CUENTA GPS(CUENTA DE GASTO)\"");
					return validaCartaConse;
				}
				
				if(!(listaCadenas.get(contLinea).getUsuarioCreador() == null && listaCadenas.get(contLinea).getUsuarioCreador() == null)) {
					if (!listaCadenas.get(contLinea).getUsuarioCreador().equals(listaCadenas.get(contLinea).getUsuarioCreador())) {
						validaCartaConse.put(18, "ERROR EST EN LINEA" + linea.get(contLinea) + " CONSECUTIVO  "
								+ listaCadenas.get(contLinea).getConsecArch() + "/"
								+ listaCadenas.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS  EN EL CAMPO -> \"USUARIO CREADOR DE CARTA\"");
						return validaCartaConse;
					}
				}
				
				if(listaCadenas.get(contLinea).getNu_activo() != listaCadenas.get(contLinea).getNu_activo()) {
					validaCartaConse.put(19, "ERROR EST EN LINEA" + linea.get(contLinea) + " CONSECUTIVO  "
							+ listaCadenas.get(contLinea).getConsecArch() + "/"
							+ listaCadenas.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS  EN EL CAMPO -> \"NUMERO DE ACTIVO\"");
					return validaCartaConse;
				}
				
				if (!listaCadenas.get(contLinea).getAplicativoOrg().equals(aplicativoOrigen)) {
					validaCartaConse.put(20, "ERROR EST EN LINEA" + linea.get(contLinea) + " CONSECUTIVO  "
							+ listaCadenas.get(contLinea).getConsecArch() + "/"
							+ listaCadenas.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS  EN EL CAMPO -> \"APLICATIVO ORIGEN\"");
					return validaCartaConse;
				}

			} 
			
			if(!(i == listaCadenas.size())){
				if (!(listaCadenas.get(contLinea).getConsecArch() == listaCadenas.get(i).getConsecArch())) {
						validaCartaConse.put(1, "ERROR EST EN LINEA" + linea.get(i) + " CONSECUTIVO  "
							+ listaCadenas.get(contLinea).getConsecArch() + "/"
							+ listaCadenas.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS  EN EL CAMPO -> \"CONSECUTIVO CARTA\"");
						return validaCartaConse;
					
				}
				
				if(listaCadenas.get(contLinea).getNu_carta() > 0) {
					if(listaCadenas.get(contLinea).getNu_carta() != listaCadenas.get(i).getNu_carta()) {
						validaCartaConse.put(2, "ERROR EST EN LINEA" + linea.get(i) + " CONSECUTIVO  "
								+ listaCadenas.get(contLinea).getConsecArch() + "/"
								+ listaCadenas.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS  EN EL CAMPO -> \"NUMERO DE CARTA\"");
						return validaCartaConse;
					}
				}
				
				if(!(listaCadenas.get(contLinea).getTp_carta() == null && listaCadenas.get(i).getTp_carta() == null)) {
					if(!(listaCadenas.get(contLinea).getTp_carta().equals(listaCadenas.get(i).getTp_carta()))) {
							validaCartaConse.put(3, "ERROR EST EN LINEA" + linea.get(i) + " CONSECUTIVO  "
									+ listaCadenas.get(contLinea).getConsecArch() + "/"
									+ listaCadenas.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS  EN EL CAMPO -> \"TIPO CARTA\"");
							return validaCartaConse;
						
					}
				}
				
				if (!(listaCadenas.get(contLinea).getTp_pago() == (listaCadenas.get(i).getTp_pago()))) {
					validaCartaConse.put(4,
							"ERROR EST EN LINEA" + linea.get(i) + " CONSECUTIVO  "
									+ listaCadenas.get(contLinea).getConsecArch() + "/"
									+ listaCadenas.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS  EN EL CAMPO -> \"TIPO PAGO\"");
					return validaCartaConse;
				}

				if (listaCadenas.get(contLinea).getNu_proveedor() != listaCadenas.get(i).getNu_proveedor()) {
					validaCartaConse.put(5,
							"ERROR EST EN LINEA" + linea.get(i) + " CONSECUTIVO  "
									+ listaCadenas.get(contLinea).getConsecArch() + "/"
									+ listaCadenas.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS  EN EL CAMPO -> \"NUMERO PROVEEDOR\"");
					return validaCartaConse;
				}
				
				if(!(listaCadenas.get(contLinea).getSociedadRec() == null && listaCadenas.get(i).getSociedadRec() == null)) {
					if (!listaCadenas.get(contLinea).getSociedadRec().equals(listaCadenas.get(i).getSociedadRec())) {
						validaCartaConse.put(6,
								"ERROR EST EN LINEA" + linea.get(i) + " CONSECUTIVO  "
										+ listaCadenas.get(contLinea).getConsecArch() + "/"
										+ listaCadenas.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS EN EL SE ENCONTRARON DIFERENCIAS DE DATOS  EN EL CAMPO -> \"SOCIEDAD RECEPTORA\"");
						return validaCartaConse;
					}
				}

				if(!(listaCadenas.get(contLinea).getGlg() == null && listaCadenas.get(i).getGlg() == null)) {
					if (!listaCadenas.get(contLinea).getGlg().equals(listaCadenas.get(i).getGlg())) {
						validaCartaConse.put(7,
								"ERROR EST EN LINEA" + linea.get(i) + " CONSECUTIVO  "
										+ listaCadenas.get(contLinea).getConsecArch() + "/"
										+ listaCadenas.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS  EN EL CAMPO -> \"GLG\"");
						return validaCartaConse;
					}
				}
				
				if(!(listaCadenas.get(contLinea).getEmpGasBursa() == null  && listaCadenas.get(i).getEmpGasBursa() == null)) {
					if (!listaCadenas.get(contLinea).getEmpGasBursa().equals(listaCadenas.get(i).getEmpGasBursa())) {
						validaCartaConse.put(8, "ERROR EST EN LINEA" + linea.get(i) + " CONSECUTIVO  "
								+ listaCadenas.get(contLinea).getConsecArch() + "/"
								+ listaCadenas.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS  EN EL CAMPO -> \"EMPRESA GASTOS BURSA\"");
						return validaCartaConse;
					}
				}
				
				if(!(listaCadenas.get(contLinea).getFideicomiso() == null && listaCadenas.get(i).getFideicomiso() == null)) {
					if (!listaCadenas.get(contLinea).getFideicomiso().equals(listaCadenas.get(i).getFideicomiso())) {
						validaCartaConse.put(9,
								"ERROR EST EN LINEA" + linea.get(i) + " CONSECUTIVO  "
										+ listaCadenas.get(contLinea).getConsecArch() + "/"
										+ listaCadenas.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS  EN EL CAMPO -> \"FIDEICOMISO\"");
						return validaCartaConse;
					}
				}
				
				if(!(listaCadenas.get(contLinea).getNu_acreditado() == null && listaCadenas.get(i).getNu_acreditado() == null)) {
					if (!listaCadenas.get(contLinea).getNu_acreditado().equals(listaCadenas.get(i).getNu_acreditado())) {
						validaCartaConse.put(10, "ERROR EST EN LINEA" + linea.get(i) + " CONSECUTIVO  "
								+ listaCadenas.get(contLinea).getConsecArch() + "/"
								+ listaCadenas.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS  EN EL CAMPO -> \"NUMERO DE ACREDITADO\"");
						return validaCartaConse;
					}
				}
				
				if(!(listaCadenas.get(contLinea).getNu_pep() == null && listaCadenas.get(i).getNu_pep() == null )) {
					if (!listaCadenas.get(contLinea).getNu_pep().equals(listaCadenas.get(i).getNu_pep())) {
						validaCartaConse.put(11,
								"ERROR EST EN LINEA" + linea.get(i) + " CONSECUTIVO  "
										+ listaCadenas.get(contLinea).getConsecArch() + "/"
										+ listaCadenas.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS  EN EL CAMPO -> \"NUMERO DE PEP\"");
						return validaCartaConse;
					}
				}
				
				if(!(listaCadenas.get(contLinea).getMondena() == null &&  listaCadenas.get(i).getMondena() == null)) {
					if (!listaCadenas.get(contLinea).getMondena().equals(listaCadenas.get(i).getMondena())) {
						validaCartaConse.put(12,
								"ERROR EST EN LINEA" + linea.get(i) + " CONSECUTIVO  "
										+ listaCadenas.get(contLinea).getConsecArch() + "/"
										+ listaCadenas.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS  EN EL CAMPO -> \"MONEDA\"");
						return validaCartaConse;
					}
				}
				
				if(!(listaCadenas.get(contLinea).getPeriodificacion() == null && listaCadenas.get(i).getPeriodificacion() == null)) {
					if (!listaCadenas.get(contLinea).getPeriodificacion()
							.equals(listaCadenas.get(i).getPeriodificacion())) {
						validaCartaConse.put(13,
								"ERROR EST EN LINEA" + linea.get(i) + " CONSECUTIVO  "
										+ listaCadenas.get(contLinea).getConsecArch() + "/"
										+ listaCadenas.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS  EN EL CAMPO -> \"PERIODIFICACIÓN\"");
						return validaCartaConse;
					}
				}
				
				if(!(listaCadenas.get(contLinea).getProviEjerAnterior() == null && listaCadenas.get(i).getProviEjerAnterior() == null)) {
					if (!listaCadenas.get(contLinea).getProviEjerAnterior()
							.equals(listaCadenas.get(i).getProviEjerAnterior())) {
						validaCartaConse.put(14,
								"ERROR EST EN LINEA" + linea.get(i) + " CONSECUTIVO  "
										+ listaCadenas.get(contLinea).getConsecArch() + "/"
										+ listaCadenas.get(contLinea).getConsecNota()
										+ " SE ENCONTRARON DIFERENCIAS DE DATOS  EN EL CAMPO -> \"PROVISIÓN EJERCICIO ANTERIOR\"");
						return validaCartaConse;
					}
				}

				if (listaCadenas.get(contLinea).getContrato() != listaCadenas.get(i).getContrato()) {
					validaCartaConse.put(15,
							"ERROR EST EN LINEA" + linea.get(i) + " CONSECUTIVO  "
									+ listaCadenas.get(contLinea).getConsecArch() + "/"
									+ listaCadenas.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS  EN EL CAMPO -> \"CONTRATO\"");
					return validaCartaConse;
				}
				
				if(listaCadenas.get(contLinea).getRecAlternativo() != listaCadenas.get(i).getRecAlternativo()) {
					validaCartaConse.put(16, "ERROR EST EN LINEA" + linea.get(i) + " CONSECUTIVO  "
							+ listaCadenas.get(contLinea).getConsecArch() + "/"
							+ listaCadenas.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS  EN EL CAMPO -> \"RECEPTOR ALTERNATIVO\"");
					return validaCartaConse;
				}
				
				if (listaCadenas.get(contLinea).getCuentaGps() != listaCadenas.get(i).getCuentaGps()) {
					validaCartaConse.put(17,
							"ERROR EST EN LINEA" + linea.get(i) + " CONSECUTIVO  "
									+ listaCadenas.get(contLinea).getConsecArch() + "/"
									+ listaCadenas.get(contLinea).getConsecNota()
									+ " SE ENCONTRARON DIFERENCIAS DE DATOS  EN EL CAMPO -> \"CUENTA GPS(CUENTA DE GASTO)\"");
					return validaCartaConse;
				}
				
				if(!(listaCadenas.get(contLinea).getUsuarioCreador() == null && listaCadenas.get(i).getUsuarioCreador() == null)) {
					if (!listaCadenas.get(contLinea).getUsuarioCreador().equals(listaCadenas.get(i).getUsuarioCreador())) {
						validaCartaConse.put(18, "ERROR EST EN LINEA" + linea.get(i) + " CONSECUTIVO  "
								+ listaCadenas.get(contLinea).getConsecArch() + "/"
								+ listaCadenas.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS  EN EL CAMPO -> \"USUARIO CREADOR DE CARTA\"");
						return validaCartaConse;
					}
				}
				
				if(listaCadenas.get(contLinea).getNu_activo() != listaCadenas.get(i).getNu_activo()) {
					validaCartaConse.put(19, "ERROR EST EN LINEA" + linea.get(i) + " CONSECUTIVO  "
							+ listaCadenas.get(contLinea).getConsecArch() + "/"
							+ listaCadenas.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS  EN EL CAMPO -> \"NUMERO DE ACTIVO\"");
					return validaCartaConse;
				}
				
				if (!listaCadenas.get(contLinea).getAplicativoOrg().equals(aplicativoOrigen)) {
					validaCartaConse.put(20, "ERROR EST EN LINEA" + linea.get(i) + " CONSECUTIVO  "
							+ listaCadenas.get(contLinea).getConsecArch() + "/"
							+ listaCadenas.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS  EN EL CAMPO -> \"APLICATIVO ORIGEN\"");
					return validaCartaConse;
				}

			} else {
				if (!listaCadenas.get(contLinea).getAplicativoOrg().equals(aplicativoOrigen)) {
					validaCartaConse.put(20, "ERROR EST EN LINEA" + linea.get(i) + " CONSECUTIVO  "
							+ listaCadenas.get(contLinea).getConsecArch() + "/"
							+ listaCadenas.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS  EN EL CAMPO -> \"APLICATIVO ORIGEN\"");
					return validaCartaConse;
				}
			}
			contLinea++;
		}

		return validaCartaConse;
	}
}
