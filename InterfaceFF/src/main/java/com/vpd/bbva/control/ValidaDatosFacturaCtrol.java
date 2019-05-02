package main.java.com.vpd.bbva.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import main.java.com.vpd.bbva.bean.BeanFF;

public class ValidaDatosFacturaCtrol {
	
	public ValidaDatosFacturaCtrol() {
		// TODO Auto-generated constructor stub
	}
	
	public HashMap<Integer, String> validaDatosFacturaConsecutiva(ArrayList<BeanFF> listaCadenas, List<Integer> linea) {
		
		
		HashMap<Integer, String> validaFacturaConse = new HashMap<Integer, String>();
		int contLinea = 0;
		for (int i = 1; i <= listaCadenas.size(); i++) {
			if (listaCadenas.size() == 1) {
				if(listaCadenas.get(contLinea).getTp_registro().equals("NF")) {
					if(listaCadenas.get(contLinea).getConsecNota() != listaCadenas.get(contLinea).getConsecNota()) {
						validaFacturaConse.put(1, "ERROR EN LINEA" + linea.get(contLinea) + " CONSECUTIVO  "
								+ listaCadenas.get(contLinea).getConsecArch() + "/"
								+ listaCadenas.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS EN EL CAMPO -> \"CONSECUTIVO FACTURA\"");
						return validaFacturaConse;
					}
				}
				
				if(listaCadenas.get(contLinea).getIsrRetenido() != listaCadenas.get(contLinea).getIsrRetenido()) {
					validaFacturaConse.put(2, "ERROR EN LINEA" + linea.get(contLinea) + " CONSECUTIVO  "
							+ listaCadenas.get(contLinea).getConsecArch() + "/"
							+ listaCadenas.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS EN EL CAMPO -> \"ISR RETENIDO\"");
					return validaFacturaConse;
				}
				
				if(listaCadenas.get(contLinea).getIvaRetenido() != listaCadenas.get(contLinea).getIvaRetenido()) {
					validaFacturaConse.put(3, "ERROR EN LINEA" + linea.get(contLinea) + " CONSECUTIVO  "
							+ listaCadenas.get(contLinea).getConsecArch() + "/"
							+ listaCadenas.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS EN EL CAMPO -> \"IVA RETENIDO\"");
					return validaFacturaConse;
				}
				
				if(listaCadenas.get(contLinea).getImpuestoCedular() != listaCadenas.get(contLinea).getImpuestoCedular()) {
					validaFacturaConse.put(4, "ERROR EN LINEA" + linea.get(contLinea) + " CONSECUTIVO  "
							+ listaCadenas.get(contLinea).getConsecArch() + "/"
							+ listaCadenas.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS EN EL CAMPO -> \"IMPUESTO CEDULAR\"");
					return validaFacturaConse;
				}
				
				if(listaCadenas.get(contLinea).getOtrosImpuestos() != listaCadenas.get(contLinea).getOtrosImpuestos()) {
					validaFacturaConse.put(5, "ERROR EN LINEA" + linea.get(contLinea) + " CONSECUTIVO  "
							+ listaCadenas.get(contLinea).getConsecArch() + "/"
							+ listaCadenas.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS EN EL CAMPO -> \"OTROS IMPUESTOS\"");
					return validaFacturaConse;
				}
				
				if(listaCadenas.get(contLinea).getDescuento() != listaCadenas.get(contLinea).getDescuento()) {
					validaFacturaConse.put(6, "ERROR EN LINEA" + linea.get(contLinea) + " CONSECUTIVO  "
							+ listaCadenas.get(contLinea).getConsecArch() + "/"
							+ listaCadenas.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS EN EL CAMPO -> \"DESCUENTO\"");
					return validaFacturaConse;
				}
				
				if(listaCadenas.get(contLinea).getTp_registro().equals("NF")) {
					if(!(listaCadenas.get(contLinea).getComprobacion().equals(listaCadenas.get(contLinea).getComprobacion()))) {
						validaFacturaConse.put(7, "ERROR EN LINEA" + linea.get(contLinea) + " CONSECUTIVO  "
								+ listaCadenas.get(contLinea).getConsecArch() + "/"
								+ listaCadenas.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS EN EL CAMPO -> \"COMPROBACIÓN\"");
						return validaFacturaConse;
					}
				}
				
				if(listaCadenas.get(contLinea).getTp_registro().equals("NF")) {
					if(!(listaCadenas.get(contLinea).getNu_anticipo().equals(listaCadenas.get(contLinea).getNu_anticipo()))) {
						validaFacturaConse.put(8, "ERROR EN LINEA" + linea.get(contLinea) + " CONSECUTIVO  "
								+ listaCadenas.get(contLinea).getConsecArch() + "/"
								+ listaCadenas.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS EN EL CAMPO -> \"NUMERO DE ANTICIPO\"");
						return validaFacturaConse;
					}
				}
				
				if(listaCadenas.get(contLinea).getTp_registro().equals("NF")) {
					if(listaCadenas.get(contLinea).getFecha_anticipo() != listaCadenas.get(contLinea).getFecha_anticipo()) {
						validaFacturaConse.put(9, "ERROR EN LINEA" + linea.get(contLinea) + " CONSECUTIVO  "
								+ listaCadenas.get(contLinea).getConsecArch() + "/"
								+ listaCadenas.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS EN EL CAMPO -> \"FECHA DE ANTICIPO\"");
						return validaFacturaConse;
					}
				}
				
				if(listaCadenas.get(contLinea).getTp_registro().equals("NF")) {
					if(!(listaCadenas.get(contLinea).getViaP().equals(listaCadenas.get(contLinea).getViaP()))) {
						validaFacturaConse.put(10, "ERROR EN LINEA" + linea.get(contLinea) + " CONSECUTIVO  "
								+ listaCadenas.get(contLinea).getConsecArch() + "/"
								+ listaCadenas.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS EN EL CAMPO -> \"VIA DE PAGO\"");
						return validaFacturaConse;
					}
				}
				
				if(listaCadenas.get(contLinea).getTp_registro().equals("NF")) {
					if(listaCadenas.get(contLinea).getCuentaBanc() != listaCadenas.get(contLinea).getCuentaBanc()) {
						validaFacturaConse.put(11, "ERROR EN LINEA" + linea.get(contLinea) + " CONSECUTIVO  "
								+ listaCadenas.get(contLinea).getConsecArch() + "/"
								+ listaCadenas.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS EN EL CAMPO -> \"CUENTA BANCARIA\"");
						return validaFacturaConse;
					}
				}
				
				if(listaCadenas.get(contLinea).getTp_registro().equals("NF")) {
					if(!(listaCadenas.get(contLinea).getTpBanco().equals(listaCadenas.get(contLinea).getTpBanco()))) {
						validaFacturaConse.put(12, "ERROR EN LINEA" + linea.get(contLinea) + " CONSECUTIVO  "
								+ listaCadenas.get(contLinea).getConsecArch() + "/"
								+ listaCadenas.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS EN EL CAMPO -> \"TIPO BANCO\"");
						return validaFacturaConse;
					}
				}
				
				if(!(listaCadenas.get(contLinea).getEstatusF().equals(listaCadenas.get(contLinea).getEstatusF()))) {
					validaFacturaConse.put(13, "ERROR EN LINEA" + linea.get(contLinea) + " CONSECUTIVO  "
							+ listaCadenas.get(contLinea).getConsecArch() + "/"
							+ listaCadenas.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS EN EL CAMPO -> \"ESTATUS FACTURA\"");
					return validaFacturaConse;
				}
			}
			
			if(!(i == listaCadenas.size())){
				if(listaCadenas.get(contLinea).getTp_registro().equals("NF")) {
					if(listaCadenas.get(contLinea).getConsecNota() != listaCadenas.get(i).getConsecNota()) {
						validaFacturaConse.put(1, "ERROR EN LINEA" + linea.get(i) + " CONSECUTIVO  "
								+ listaCadenas.get(contLinea).getConsecArch() + "/"
								+ listaCadenas.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS EN EL CAMPO -> \"CONSECUTIVO FACTURA\"");
						return validaFacturaConse;
					}
				}
				
				if(!(listaCadenas.get(contLinea).getIsrRetenido().compareTo(listaCadenas.get(i).getIsrRetenido()) == 0)) {
					validaFacturaConse.put(2, "ERROR EN LINEA" + linea.get(i) + " CONSECUTIVO  "
							+ listaCadenas.get(contLinea).getConsecArch() + "/"
							+ listaCadenas.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS EN EL CAMPO -> \"ISR RETENIDO\"");
					return validaFacturaConse;
				}
				
				if(!(listaCadenas.get(contLinea).getIvaRetenido().compareTo(listaCadenas.get(i).getIvaRetenido()) == 0)) {
					validaFacturaConse.put(3, "ERROR EN LINEA" + linea.get(i) + " CONSECUTIVO  "
							+ listaCadenas.get(contLinea).getConsecArch() + "/"
							+ listaCadenas.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS EN EL CAMPO -> \"IVA RETENIDO\"");
					return validaFacturaConse;
				}
				
				if(!(listaCadenas.get(contLinea).getImpuestoCedular().compareTo(listaCadenas.get(i).getImpuestoCedular()) == 0)) {
					validaFacturaConse.put(4, "ERROR EN LINEA" + linea.get(i) + " CONSECUTIVO  "
							+ listaCadenas.get(contLinea).getConsecArch() + "/"
							+ listaCadenas.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS EN EL CAMPO -> \"IMPUESTO CEDULAR\"");
					return validaFacturaConse;
				}
				
				if(!(listaCadenas.get(contLinea).getOtrosImpuestos().compareTo(listaCadenas.get(i).getOtrosImpuestos()) == 0)) {
					validaFacturaConse.put(5, "ERROR EN LINEA" + linea.get(i) + " CONSECUTIVO  "
							+ listaCadenas.get(contLinea).getConsecArch() + "/"
							+ listaCadenas.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS EN EL CAMPO -> \"OTROS IMPUESTOS\"");
					return validaFacturaConse;
				}
				
				if(!(listaCadenas.get(contLinea).getDescuento().compareTo(listaCadenas.get(i).getDescuento()) == 0)) {
					validaFacturaConse.put(6, "ERROR EN LINEA" + linea.get(i) + " CONSECUTIVO  "
							+ listaCadenas.get(contLinea).getConsecArch() + "/"
							+ listaCadenas.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS EN EL CAMPO -> \"DESCUENTO\"");
					return validaFacturaConse;
				}
				
				if(listaCadenas.get(contLinea).getTp_registro().equals("NF")) {
					if(!(listaCadenas.get(contLinea).getComprobacion().equals(listaCadenas.get(i).getComprobacion()))) {
						validaFacturaConse.put(7, "ERROR EN LINEA" + linea.get(i) + " CONSECUTIVO  "
								+ listaCadenas.get(contLinea).getConsecArch() + "/"
								+ listaCadenas.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS EN EL CAMPO -> \"COMPROBACIÓN\"");
						return validaFacturaConse;
					}
				}
				
				if(listaCadenas.get(contLinea).getTp_registro().equals("NF")) {
					if(!(listaCadenas.get(contLinea).getNu_anticipo().equals(listaCadenas.get(i).getNu_anticipo()))) {
						validaFacturaConse.put(8, "ERROR EN LINEA" + linea.get(i) + " CONSECUTIVO  "
								+ listaCadenas.get(contLinea).getConsecArch() + "/"
								+ listaCadenas.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS EN EL CAMPO -> \"NUMERO DE ANTICIPO\"");
						return validaFacturaConse;
					}
				}
				
				if(listaCadenas.get(contLinea).getTp_registro().equals("NF")) {
					if(!(listaCadenas.get(contLinea).getFecha_anticipo().equals(listaCadenas.get(i).getFecha_anticipo()))) {
						validaFacturaConse.put(9, "ERROR EN LINEA" + linea.get(i) + " CONSECUTIVO  "
								+ listaCadenas.get(contLinea).getConsecArch() + "/"
								+ listaCadenas.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS EN EL CAMPO -> \"FECHA DE ANTICIPO\"");
						return validaFacturaConse;
					}
				}
				
				if(listaCadenas.get(contLinea).getTp_registro().equals("NF")) {
					if(!(listaCadenas.get(contLinea).getViaP().equals(listaCadenas.get(i).getViaP()))) {
						validaFacturaConse.put(10, "ERROR EN LINEA" + linea.get(i) + " CONSECUTIVO  "
								+ listaCadenas.get(contLinea).getConsecArch() + "/"
								+ listaCadenas.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS EN EL CAMPO -> \"VIA DE PAGO\"");
						return validaFacturaConse;
					}
				}
				
				if(listaCadenas.get(contLinea).getTp_registro().equals("NF")) {
					if(listaCadenas.get(contLinea).getCuentaBanc() != listaCadenas.get(i).getCuentaBanc()) {
						validaFacturaConse.put(11, "ERROR EN LINEA" + linea.get(i) + " CONSECUTIVO  "
								+ listaCadenas.get(contLinea).getConsecArch() + "/"
								+ listaCadenas.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS EN EL CAMPO -> \"CUENTA BANCARIA\"");
						return validaFacturaConse;
					}
				}
				
				if(listaCadenas.get(contLinea).getTp_registro().equals("NF")) {
					if(!(listaCadenas.get(contLinea).getTpBanco().equals(listaCadenas.get(i).getTpBanco()))) {
						validaFacturaConse.put(12, "ERROR EN LINEA" + linea.get(i) + " CONSECUTIVO  "
								+ listaCadenas.get(contLinea).getConsecArch() + "/"
								+ listaCadenas.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS EN EL CAMPO -> \"TIPO BANCO\"");
						return validaFacturaConse;
					}
				}
				
				if(!(listaCadenas.get(contLinea).getEstatusF().equals(listaCadenas.get(i).getEstatusF()))) {
					validaFacturaConse.put(13, "ERROR EN LINEA" + linea.get(i) + " CONSECUTIVO  "
							+ listaCadenas.get(contLinea).getConsecArch() + "/"
							+ listaCadenas.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS EN EL CAMPO -> \"ESTATUS FACTURA\"");
					return validaFacturaConse;
				}
			}
			contLinea++;
		}
		return validaFacturaConse;
	}
}
