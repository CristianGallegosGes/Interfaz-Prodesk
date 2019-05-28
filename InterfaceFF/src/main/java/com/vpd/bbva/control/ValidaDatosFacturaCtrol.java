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
		
		ArrayList<BeanFF> listaCadena = new ArrayList<BeanFF>();
		List<Integer> lin = new ArrayList<Integer>();
		int con = 0;
		for(BeanFF listaCa : listaCadenas) {
			if(listaCa.getTp_registro().equals("NF")) {
				listaCadena.add(listaCa);
				lin.add(linea.get(con));
			}
			con++;			
		}
		
		HashMap<Integer, String> validaFacturaConse = new HashMap<Integer, String>();
		int contLinea = 0;
		for (int i = 1; i <= listaCadena.size(); i++) {
			
			if (listaCadena.size() == 1) {
				if(listaCadena.get(contLinea).getTp_registro().equals("NF")) {
					if(listaCadena.get(contLinea).getConsecNota() != listaCadena.get(contLinea).getConsecNota()) {
						validaFacturaConse.put(1, "ERROR EST EN LINEA" + lin.get(contLinea) + " CONSECUTIVO  "
								+ listaCadena.get(contLinea).getConsecArch() + "/"
								+ listaCadena.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS EN EL CAMPO -> \"CONSECUTIVO FACTURA\"");
						return validaFacturaConse;
					}
				}
				
				if(listaCadena.get(contLinea).getIsrRetenido() != listaCadena.get(contLinea).getIsrRetenido()) {
					validaFacturaConse.put(2, "ERROR EST EN LINEA" + lin.get(contLinea) + " CONSECUTIVO  "
							+ listaCadena.get(contLinea).getConsecArch() + "/"
							+ listaCadena.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS EN EL CAMPO -> \"ISR RETENIDO\"");
					return validaFacturaConse;
				}
				
				if(listaCadena.get(contLinea).getIvaRetenido() != listaCadena.get(contLinea).getIvaRetenido()) {
					validaFacturaConse.put(3, "ERROR EST EN LINEA" + lin.get(contLinea) + " CONSECUTIVO  "
							+ listaCadena.get(contLinea).getConsecArch() + "/"
							+ listaCadena.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS EN EL CAMPO -> \"IVA RETENIDO\"");
					return validaFacturaConse;
				}
				
				if(listaCadena.get(contLinea).getImpuestoCedular() != listaCadena.get(contLinea).getImpuestoCedular()) {
					validaFacturaConse.put(4, "ERROR EST EN LINEA" + lin.get(contLinea) + " CONSECUTIVO  "
							+ listaCadena.get(contLinea).getConsecArch() + "/"
							+ listaCadena.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS EN EL CAMPO -> \"IMPUESTO CEDULAR\"");
					return validaFacturaConse;
				}
				
				if(listaCadena.get(contLinea).getOtrosImpuestos() != listaCadena.get(contLinea).getOtrosImpuestos()) {
					validaFacturaConse.put(5, "ERROR EST EN LINEA" + lin.get(contLinea) + " CONSECUTIVO  "
							+ listaCadena.get(contLinea).getConsecArch() + "/"
							+ listaCadena.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS EN EL CAMPO -> \"OTROS IMPUESTOS\"");
					return validaFacturaConse;
				}
				
				if(listaCadena.get(contLinea).getDescuento() != listaCadena.get(contLinea).getDescuento()) {
					validaFacturaConse.put(6, "ERROR EST EN LINEA" + lin.get(contLinea) + " CONSECUTIVO  "
							+ listaCadena.get(contLinea).getConsecArch() + "/"
							+ listaCadena.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS EN EL CAMPO -> \"DESCUENTO\"");
					return validaFacturaConse;
				}
				
				if(listaCadena.get(contLinea).getTp_registro().equals("NF")) {
					if(!(listaCadena.get(contLinea).getComprobacion() == null && listaCadena.get(contLinea).getComprobacion() == null)) {
						if(!(listaCadena.get(contLinea).getComprobacion().equals(listaCadena.get(contLinea).getComprobacion()))) {
							validaFacturaConse.put(7, "ERROR EST EN LINEA" + lin.get(contLinea) + " CONSECUTIVO  "
									+ listaCadena.get(contLinea).getConsecArch() + "/"
									+ listaCadena.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS EN EL CAMPO -> \"COMPROBACIÓN\"");
							return validaFacturaConse;
						}
					}
				}
				
				if(listaCadena.get(contLinea).getTp_registro().equals("NF")) {
					if(!(listaCadena.get(contLinea).getNu_anticipo() == null && listaCadena.get(contLinea).getNu_anticipo() == null)) {
						if(!(listaCadena.get(contLinea).getNu_anticipo().equals(listaCadena.get(contLinea).getNu_anticipo()))) {
							validaFacturaConse.put(8, "ERROR EST EN LINEA" + lin.get(contLinea) + " CONSECUTIVO  "
									+ listaCadena.get(contLinea).getConsecArch() + "/"
									+ listaCadena.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS EN EL CAMPO -> \"NUMERO DE ANTICIPO\"");
							return validaFacturaConse;
						}
					}
				}
				
				if(listaCadena.get(contLinea).getTp_registro().equals("NF")) {
					if(!(listaCadena.get(contLinea).getFecha_anticipo() == null && listaCadena.get(contLinea).getFecha_anticipo() == null)) {
						if(!(listaCadena.get(contLinea).getFecha_anticipo().equals(listaCadena.get(contLinea).getFecha_anticipo()))) {
							validaFacturaConse.put(9, "ERROR EST EN LINEA" + lin.get(contLinea) + " CONSECUTIVO  "
									+ listaCadena.get(contLinea).getConsecArch() + "/"
									+ listaCadena.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS EN EL CAMPO -> \"FECHA DE ANTICIPO\"");
							return validaFacturaConse;
						}
					}
				}
				
				if(listaCadena.get(contLinea).getTp_registro().equals("NF")) {
					if(!(listaCadena.get(contLinea).getViaP() == null && listaCadena.get(contLinea).getViaP() ==  null)){
						if(!(listaCadena.get(contLinea).getViaP().equals(listaCadena.get(contLinea).getViaP()))) {
							validaFacturaConse.put(10, "ERROR EST EN LINEA" + lin.get(contLinea) + " CONSECUTIVO  "
									+ listaCadena.get(contLinea).getConsecArch() + "/"
									+ listaCadena.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS EN EL CAMPO -> \"VIA DE PAGO\"");
							return validaFacturaConse;
						}
					}
				}
				
				if(listaCadena.get(contLinea).getTp_registro().equals("NF")) {
					if(listaCadena.get(contLinea).getCuentaBanc() != listaCadena.get(contLinea).getCuentaBanc()) {
						validaFacturaConse.put(11, "ERROR EST EN LINEA" + lin.get(contLinea) + " CONSECUTIVO  "
								+ listaCadena.get(contLinea).getConsecArch() + "/"
								+ listaCadena.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS EN EL CAMPO -> \"CUENTA BANCARIA\"");
						return validaFacturaConse;
					}
				}
				
				if(listaCadena.get(contLinea).getTp_registro().equals("NF")) {
					if(!(listaCadena.get(contLinea).getTpBanco() == null && listaCadena.get(contLinea).getTpBanco() == null )) {
						if(!(listaCadena.get(contLinea).getTpBanco().equals(listaCadena.get(contLinea).getTpBanco()))) {
							validaFacturaConse.put(12, "ERROR EST EN LINEA" + lin.get(contLinea) + " CONSECUTIVO  "
									+ listaCadena.get(contLinea).getConsecArch() + "/"
									+ listaCadena.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS EN EL CAMPO -> \"TIPO BANCO\"");
							return validaFacturaConse;
						}
					}
				}
				
				if(!(listaCadena.get(contLinea).getEstatusF() == null && listaCadena.get(contLinea).getEstatusF() == null )) {
					if(!(listaCadena.get(contLinea).getEstatusF().equals(listaCadena.get(contLinea).getEstatusF()))) {
						validaFacturaConse.put(13, "ERROR EST EN LINEA" + lin.get(contLinea) + " CONSECUTIVO  "
								+ listaCadena.get(contLinea).getConsecArch() + "/"
								+ listaCadena.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS EN EL CAMPO -> \"ESTATUS FACTURA\"");
						return validaFacturaConse;
					}
				}
			}
			
			if(!(listaCadena.size() == 1)) {
				if(!(i == listaCadena.size())){
					if(listaCadena.get(contLinea).getTp_registro().equals("NF")) {
						if(listaCadena.get(contLinea).getConsecNota() != listaCadena.get(i).getConsecNota()) {
							validaFacturaConse.put(1, "ERROR EST EN LINEA" + lin.get(i) + " CONSECUTIVO  "
									+ listaCadena.get(contLinea).getConsecArch() + "/"
									+ listaCadena.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS EN EL CAMPO -> \"CONSECUTIVO FACTURA\"");
							return validaFacturaConse;
						}
					}
					
					if(!(listaCadena.get(contLinea).getIsrRetenido().compareTo(listaCadena.get(i).getIsrRetenido()) == 0)) {
						validaFacturaConse.put(2, "ERROR EST EN LINEA" + lin.get(i) + " CONSECUTIVO  "
								+ listaCadena.get(contLinea).getConsecArch() + "/"
								+ listaCadena.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS EN EL CAMPO -> \"ISR RETENIDO\"");
						return validaFacturaConse;
					}
					
					if(!(listaCadena.get(contLinea).getIvaRetenido().compareTo(listaCadena.get(i).getIvaRetenido()) == 0)) {
						validaFacturaConse.put(3, "ERROR EST EN LINEA" + lin.get(i) + " CONSECUTIVO  "
								+ listaCadena.get(contLinea).getConsecArch() + "/"
								+ listaCadena.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS EN EL CAMPO -> \"IVA RETENIDO\"");
						return validaFacturaConse;
					}
					
					if(!(listaCadena.get(contLinea).getImpuestoCedular().compareTo(listaCadena.get(i).getImpuestoCedular()) == 0)) {
						validaFacturaConse.put(4, "ERROR EST EN LINEA" + lin.get(i) + " CONSECUTIVO  "
								+ listaCadena.get(contLinea).getConsecArch() + "/"
								+ listaCadena.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS EN EL CAMPO -> \"IMPUESTO CEDULAR\"");
						return validaFacturaConse;
					}
					
					if(!(listaCadena.get(contLinea).getOtrosImpuestos().compareTo(listaCadena.get(i).getOtrosImpuestos()) == 0)) {
						validaFacturaConse.put(5, "ERROR EST EN LINEA" + lin.get(i) + " CONSECUTIVO  "
								+ listaCadena.get(contLinea).getConsecArch() + "/"
								+ listaCadena.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS EN EL CAMPO -> \"OTROS IMPUESTOS\"");
						return validaFacturaConse;
					}
					
					if(!(listaCadena.get(contLinea).getDescuento().compareTo(listaCadena.get(i).getDescuento()) == 0)) {
						validaFacturaConse.put(6, "ERROR EST EN LINEA" + lin.get(i) + " CONSECUTIVO  "
								+ listaCadena.get(contLinea).getConsecArch() + "/"
								+ listaCadena.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS EN EL CAMPO -> \"DESCUENTO\"");
						return validaFacturaConse;
					}
					
					if(listaCadena.get(contLinea).getTp_registro().equals("NF") && listaCadena.get(i).getTp_registro().equals("NF")) {
						if(!(listaCadena.get(contLinea).getComprobacion() == null && listaCadena.get(i).getComprobacion() == null )) {
							if(!(listaCadena.get(contLinea).getComprobacion().equals(listaCadena.get(i).getComprobacion()))) {
								validaFacturaConse.put(7, "ERROR EST EN LINEA" + lin.get(i) + " CONSECUTIVO  "
										+ listaCadena.get(contLinea).getConsecArch() + "/"
										+ listaCadena.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS EN EL CAMPO -> \"COMPROBACIÓN\"");
								return validaFacturaConse;
							}
						}
					}
					
					if(listaCadena.get(contLinea).getTp_registro().equals("NF") && listaCadena.get(i).getTp_registro().equals("NF")) {
						if(!(listaCadena.get(contLinea).getNu_anticipo() == null && listaCadena.get(i).getNu_anticipo() == null)) {
							if(!(listaCadena.get(contLinea).getNu_anticipo().equals(listaCadena.get(i).getNu_anticipo()))) {
								validaFacturaConse.put(8, "ERROR EST EN LINEA" + lin.get(i) + " CONSECUTIVO  "
										+ listaCadena.get(contLinea).getConsecArch() + "/"
										+ listaCadena.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS EN EL CAMPO -> \"NUMERO DE ANTICIPO\"");
								return validaFacturaConse;
							}
						}
					}
					
					if(listaCadena.get(contLinea).getTp_registro().equals("NF") && listaCadena.get(i).getTp_registro().equals("NF")) {
						if(!(listaCadena.get(contLinea).getFecha_anticipo() == null && listaCadena.get(i).getFecha_anticipo() == null)) {
							if(!(listaCadena.get(contLinea).getFecha_anticipo().equals(listaCadena.get(i).getFecha_anticipo()))) {
								validaFacturaConse.put(9, "ERROR EST EN LINEA" + lin.get(i) + " CONSECUTIVO  "
										+ listaCadena.get(contLinea).getConsecArch() + "/"
										+ listaCadena.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS EN EL CAMPO -> \"FECHA DE ANTICIPO\"");
								return validaFacturaConse;
							}
						}
					}
					
					if(listaCadena.get(contLinea).getTp_registro().equals("NF") && listaCadena.get(i).getTp_registro().equals("NF")) {
						if(!(listaCadena.get(contLinea).getViaP() == null && listaCadena.get(i).getViaP() == null)) {
							if(!(listaCadena.get(contLinea).getViaP().equals(listaCadena.get(i).getViaP()))) {
								validaFacturaConse.put(10, "ERROR EST EN LINEA" + lin.get(i) + " CONSECUTIVO  "
										+ listaCadena.get(contLinea).getConsecArch() + "/"
										+ listaCadena.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS EN EL CAMPO -> \"VIA DE PAGO\"");
								return validaFacturaConse;
							}
						}
					}
					
					if(listaCadena.get(contLinea).getTp_registro().equals("NF") && listaCadena.get(i).getTp_registro().equals("NF")) {
						if(listaCadena.get(contLinea).getCuentaBanc() != listaCadena.get(i).getCuentaBanc()) {
							validaFacturaConse.put(11, "ERROR EST EN LINEA" + lin.get(i) + " CONSECUTIVO  "
									+ listaCadena.get(contLinea).getConsecArch() + "/"
									+ listaCadena.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS EN EL CAMPO -> \"CUENTA BANCARIA\"");
							return validaFacturaConse;
						}
					}
					
					if(listaCadena.get(contLinea).getTp_registro().equals("NF") && listaCadena.get(i).getTp_registro().equals("NF")) {
						if(!(listaCadena.get(contLinea).getTpBanco() == null && listaCadena.get(i).getTpBanco() == null)) {
							if(!(listaCadena.get(contLinea).getTpBanco().equals(listaCadena.get(i).getTpBanco()))) {
								validaFacturaConse.put(12, "ERROR EST EN LINEA" + lin.get(i) + " CONSECUTIVO  "
										+ listaCadena.get(contLinea).getConsecArch() + "/"
										+ listaCadena.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS EN EL CAMPO -> \"TIPO BANCO\"");
								return validaFacturaConse;
							}
						}
					}
					
					if(!(listaCadena.get(contLinea).getEstatusF() == null && listaCadena.get(i).getEstatusF() == null)) {
						if(!(listaCadena.get(contLinea).getEstatusF().equals(listaCadena.get(i).getEstatusF()))) {
							validaFacturaConse.put(13, "ERROR EST EN LINEA" + lin.get(i) + " CONSECUTIVO  "
									+ listaCadena.get(contLinea).getConsecArch() + "/"
									+ listaCadena.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS EN EL CAMPO -> \"ESTATUS FACTURA\"");
							return validaFacturaConse;
						}
					}
				}
			}
			
			contLinea++;
		}
		return validaFacturaConse;
	}
}
