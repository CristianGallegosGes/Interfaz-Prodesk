package main.java.com.vpd.bbva.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import main.java.com.vpd.bbva.bean.BeanFF;

public class ValidaDatosNotaCreditoCtrol {
	
	public ValidaDatosNotaCreditoCtrol() {
		// TODO Auto-generated constructor stub
	}
	
	public HashMap<Integer, String> validaDatosNotasConsecutiva(ArrayList<BeanFF> listaCadenas, List<Integer> linea) {
		
		ArrayList<BeanFF> listaCadena = new ArrayList<BeanFF>();
		List<Integer> lin = new ArrayList<Integer>();
		int con = 0;
		for(BeanFF listaCa : listaCadenas) {
			if(listaCa.getTp_registro().equals("NC")) {
				listaCadena.add(listaCa);
				lin.add(linea.get(con));
			}
			con++;
		}
		
		HashMap<Integer, String> validaNotaCreditoConse = new HashMap<Integer, String>();
		int contLinea = 0;
		for (int i = 1; i <= listaCadena.size(); i++) {
			
			if (listaCadena.size() == 1) {
				if(listaCadena.get(contLinea).getTp_registro().equals("NF")) {
					if(listaCadena.get(contLinea).getConsecNota() != listaCadena.get(contLinea).getConsecNota()) {
						validaNotaCreditoConse.put(1, "ERROR EN LINEA" + lin.get(contLinea) + " CONSECUTIVO  "
								+ listaCadena.get(contLinea).getConsecArch() + "/"
								+ listaCadena.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS EN EL CAMPO -> \"CONSECUTIVO FACTURA\"");
						return validaNotaCreditoConse;
					}
				}
				
				if(listaCadena.get(contLinea).getIsrRetenido() != listaCadena.get(contLinea).getIsrRetenido()) {
					validaNotaCreditoConse.put(2, "ERROR EN LINEA" + lin.get(contLinea) + " CONSECUTIVO  "
							+ listaCadena.get(contLinea).getConsecArch() + "/"
							+ listaCadena.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS EN EL CAMPO -> \"ISR RETENIDO\"");
					return validaNotaCreditoConse;
				}
				
				if(listaCadena.get(contLinea).getIvaRetenido() != listaCadena.get(contLinea).getIvaRetenido()) {
					validaNotaCreditoConse.put(3, "ERROR EN LINEA" + lin.get(contLinea) + " CONSECUTIVO  "
							+ listaCadena.get(contLinea).getConsecArch() + "/"
							+ listaCadena.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS EN EL CAMPO -> \"IVA RETENIDO\"");
					return validaNotaCreditoConse;
				}
				
				if(listaCadena.get(contLinea).getImpuestoCedular() != listaCadena.get(contLinea).getImpuestoCedular()) {
					validaNotaCreditoConse.put(4, "ERROR EN LINEA" + lin.get(contLinea) + " CONSECUTIVO  "
							+ listaCadena.get(contLinea).getConsecArch() + "/"
							+ listaCadena.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS EN EL CAMPO -> \"IMPUESTO CEDULAR\"");
					return validaNotaCreditoConse;
				}
				
				if(listaCadena.get(contLinea).getOtrosImpuestos() != listaCadena.get(contLinea).getOtrosImpuestos()) {
					validaNotaCreditoConse.put(5, "ERROR EN LINEA" + lin.get(contLinea) + " CONSECUTIVO  "
							+ listaCadena.get(contLinea).getConsecArch() + "/"
							+ listaCadena.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS EN EL CAMPO -> \"OTROS IMPUESTOS\"");
					return validaNotaCreditoConse;
				}
				
				if(listaCadena.get(contLinea).getDescuento() != listaCadena.get(contLinea).getDescuento()) {
					validaNotaCreditoConse.put(6, "ERROR EN LINEA" + lin.get(contLinea) + " CONSECUTIVO  "
							+ listaCadena.get(contLinea).getConsecArch() + "/"
							+ listaCadena.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS EN EL CAMPO -> \"DESCUENTO\"");
					return validaNotaCreditoConse;
				}
				
				if(listaCadena.get(contLinea).getTp_registro().equals("NF")) {
					if(!(listaCadena.get(contLinea).getComprobacion() == null && listaCadena.get(contLinea).getComprobacion() == null)) {
						if(!(listaCadena.get(contLinea).getComprobacion().equals(listaCadena.get(contLinea).getComprobacion()))) {
							validaNotaCreditoConse.put(7, "ERROR EN LINEA" + lin.get(contLinea) + " CONSECUTIVO  "
									+ listaCadena.get(contLinea).getConsecArch() + "/"
									+ listaCadena.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS EN EL CAMPO -> \"COMPROBACIÓN\"");
							return validaNotaCreditoConse;
						}
					}
				}
				
				if(listaCadena.get(contLinea).getTp_registro().equals("NF")) {
					if(!(listaCadena.get(contLinea).getNu_anticipo() == null && listaCadena.get(contLinea).getNu_anticipo() == null)) {
						if(!(listaCadena.get(contLinea).getNu_anticipo().equals(listaCadena.get(contLinea).getNu_anticipo()))) {
							validaNotaCreditoConse.put(8, "ERROR EN LINEA" + lin.get(contLinea) + " CONSECUTIVO  "
									+ listaCadena.get(contLinea).getConsecArch() + "/"
									+ listaCadena.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS EN EL CAMPO -> \"NUMERO DE ANTICIPO\"");
							return validaNotaCreditoConse;
						}
					}
				}
				
				if(listaCadena.get(contLinea).getTp_registro().equals("NF")) {
					if(!(listaCadena.get(contLinea).getFecha_anticipo() == null && listaCadena.get(contLinea).getFecha_anticipo() == null)) {
						if(!(listaCadena.get(contLinea).getFecha_anticipo().equals(listaCadena.get(contLinea).getFecha_anticipo()))) {
							validaNotaCreditoConse.put(9, "ERROR EN LINEA" + lin.get(contLinea) + " CONSECUTIVO  "
									+ listaCadena.get(contLinea).getConsecArch() + "/"
									+ listaCadena.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS EN EL CAMPO -> \"FECHA DE ANTICIPO\"");
							return validaNotaCreditoConse;
						}
					}
				}
				
				if(listaCadena.get(contLinea).getTp_registro().equals("NF")) {
					if(!(listaCadena.get(contLinea).getViaP() == null && listaCadena.get(contLinea).getViaP() ==  null)){
						if(!(listaCadena.get(contLinea).getViaP().equals(listaCadena.get(contLinea).getViaP()))) {
							validaNotaCreditoConse.put(10, "ERROR EN LINEA" + lin.get(contLinea) + " CONSECUTIVO  "
									+ listaCadena.get(contLinea).getConsecArch() + "/"
									+ listaCadena.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS EN EL CAMPO -> \"VIA DE PAGO\"");
							return validaNotaCreditoConse;
						}
					}
				}
				
				if(listaCadena.get(contLinea).getTp_registro().equals("NF")) {
					if(listaCadena.get(contLinea).getCuentaBanc() != listaCadena.get(contLinea).getCuentaBanc()) {
						validaNotaCreditoConse.put(11, "ERROR EN LINEA" + lin.get(contLinea) + " CONSECUTIVO  "
								+ listaCadena.get(contLinea).getConsecArch() + "/"
								+ listaCadena.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS EN EL CAMPO -> \"CUENTA BANCARIA\"");
						return validaNotaCreditoConse;
					}
				}
				
				if(listaCadena.get(contLinea).getTp_registro().equals("NF")) {
					if(!(listaCadena.get(contLinea).getTpBanco() == null && listaCadena.get(contLinea).getTpBanco() == null )) {
						if(!(listaCadena.get(contLinea).getTpBanco().equals(listaCadena.get(contLinea).getTpBanco()))) {
							validaNotaCreditoConse.put(12, "ERROR EN LINEA" + lin.get(contLinea) + " CONSECUTIVO  "
									+ listaCadena.get(contLinea).getConsecArch() + "/"
									+ listaCadena.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS EN EL CAMPO -> \"TIPO BANCO\"");
							return validaNotaCreditoConse;
						}
					}
				}
				
				if(!(listaCadena.get(contLinea).getEstatusF() == null && listaCadena.get(contLinea).getEstatusF() == null )) {
					if(!(listaCadena.get(contLinea).getEstatusF().equals(listaCadena.get(contLinea).getEstatusF()))) {
						validaNotaCreditoConse.put(13, "ERROR EN LINEA" + lin.get(contLinea) + " CONSECUTIVO  "
								+ listaCadena.get(contLinea).getConsecArch() + "/"
								+ listaCadena.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS EN EL CAMPO -> \"ESTATUS FACTURA\"");
						return validaNotaCreditoConse;
					}
				}
			}
			
			if(!(listaCadena.size() == 1)) {
				if(!(i == listaCadena.size())){
					if(listaCadena.get(contLinea).getTp_registro().equals("NF")) {
						if(listaCadena.get(contLinea).getConsecNota() != listaCadena.get(i).getConsecNota()) {
							validaNotaCreditoConse.put(1, "ERROR EN LINEA" + lin.get(i) + " CONSECUTIVO  "
									+ listaCadena.get(contLinea).getConsecArch() + "/"
									+ listaCadena.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS EN EL CAMPO -> \"CONSECUTIVO FACTURA\"");
							return validaNotaCreditoConse;
						}
					}
					
					if(!(listaCadena.get(contLinea).getIsrRetenido().compareTo(listaCadena.get(i).getIsrRetenido()) == 0)) {
						validaNotaCreditoConse.put(2, "ERROR EN LINEA" + lin.get(i) + " CONSECUTIVO  "
								+ listaCadena.get(contLinea).getConsecArch() + "/"
								+ listaCadena.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS EN EL CAMPO -> \"ISR RETENIDO\"");
						return validaNotaCreditoConse;
					}
					
					if(!(listaCadena.get(contLinea).getIvaRetenido().compareTo(listaCadena.get(i).getIvaRetenido()) == 0)) {
						validaNotaCreditoConse.put(3, "ERROR EN LINEA" + lin.get(i) + " CONSECUTIVO  "
								+ listaCadena.get(contLinea).getConsecArch() + "/"
								+ listaCadena.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS EN EL CAMPO -> \"IVA RETENIDO\"");
						return validaNotaCreditoConse;
					}
					
					if(!(listaCadena.get(contLinea).getImpuestoCedular().compareTo(listaCadena.get(i).getImpuestoCedular()) == 0)) {
						validaNotaCreditoConse.put(4, "ERROR EN LINEA" + lin.get(i) + " CONSECUTIVO  "
								+ listaCadena.get(contLinea).getConsecArch() + "/"
								+ listaCadena.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS EN EL CAMPO -> \"IMPUESTO CEDULAR\"");
						return validaNotaCreditoConse;
					}
					
					if(!(listaCadena.get(contLinea).getOtrosImpuestos().compareTo(listaCadena.get(i).getOtrosImpuestos()) == 0)) {
						validaNotaCreditoConse.put(5, "ERROR EN LINEA" + lin.get(i) + " CONSECUTIVO  "
								+ listaCadena.get(contLinea).getConsecArch() + "/"
								+ listaCadena.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS EN EL CAMPO -> \"OTROS IMPUESTOS\"");
						return validaNotaCreditoConse;
					}
					
					if(!(listaCadena.get(contLinea).getDescuento().compareTo(listaCadena.get(i).getDescuento()) == 0)) {
						validaNotaCreditoConse.put(6, "ERROR EN LINEA" + lin.get(i) + " CONSECUTIVO  "
								+ listaCadena.get(contLinea).getConsecArch() + "/"
								+ listaCadena.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS EN EL CAMPO -> \"DESCUENTO\"");
						return validaNotaCreditoConse;
					}
					
					if(listaCadena.get(contLinea).getTp_registro().equals("NF") && listaCadena.get(i).getTp_registro().equals("NF")) {
						if(!(listaCadena.get(contLinea).getComprobacion() == null && listaCadena.get(i).getComprobacion() == null )) {
							if(!(listaCadena.get(contLinea).getComprobacion().equals(listaCadena.get(i).getComprobacion()))) {
								validaNotaCreditoConse.put(7, "ERROR EN LINEA" + lin.get(i) + " CONSECUTIVO  "
										+ listaCadena.get(contLinea).getConsecArch() + "/"
										+ listaCadena.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS EN EL CAMPO -> \"COMPROBACIÓN\"");
								return validaNotaCreditoConse;
							}
						}
					}
					
					if(listaCadena.get(contLinea).getTp_registro().equals("NF") && listaCadena.get(i).getTp_registro().equals("NF")) {
						if(!(listaCadena.get(contLinea).getNu_anticipo() == null && listaCadena.get(i).getNu_anticipo() == null)) {
							if(!(listaCadena.get(contLinea).getNu_anticipo().equals(listaCadena.get(i).getNu_anticipo()))) {
								validaNotaCreditoConse.put(8, "ERROR EN LINEA" + lin.get(i) + " CONSECUTIVO  "
										+ listaCadena.get(contLinea).getConsecArch() + "/"
										+ listaCadena.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS EN EL CAMPO -> \"NUMERO DE ANTICIPO\"");
								return validaNotaCreditoConse;
							}
						}
					}
					
					if(listaCadena.get(contLinea).getTp_registro().equals("NF") && listaCadena.get(i).getTp_registro().equals("NF")) {
						if(!(listaCadena.get(contLinea).getFecha_anticipo() == null && listaCadena.get(i).getFecha_anticipo() == null)) {
							if(!(listaCadena.get(contLinea).getFecha_anticipo().equals(listaCadena.get(i).getFecha_anticipo()))) {
								validaNotaCreditoConse.put(9, "ERROR EN LINEA" + lin.get(i) + " CONSECUTIVO  "
										+ listaCadena.get(contLinea).getConsecArch() + "/"
										+ listaCadena.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS EN EL CAMPO -> \"FECHA DE ANTICIPO\"");
								return validaNotaCreditoConse;
							}
						}
					}
					
					if(listaCadena.get(contLinea).getTp_registro().equals("NF") && listaCadena.get(i).getTp_registro().equals("NF")) {
						if(!(listaCadena.get(contLinea).getViaP() == null && listaCadena.get(i).getViaP() == null)) {
							if(!(listaCadena.get(contLinea).getViaP().equals(listaCadena.get(i).getViaP()))) {
								validaNotaCreditoConse.put(10, "ERROR EN LINEA" + lin.get(i) + " CONSECUTIVO  "
										+ listaCadena.get(contLinea).getConsecArch() + "/"
										+ listaCadena.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS EN EL CAMPO -> \"VIA DE PAGO\"");
								return validaNotaCreditoConse;
							}
						}
					}
					
					if(listaCadena.get(contLinea).getTp_registro().equals("NF") && listaCadena.get(i).getTp_registro().equals("NF")) {
						if(listaCadena.get(contLinea).getCuentaBanc() != listaCadena.get(i).getCuentaBanc()) {
							validaNotaCreditoConse.put(11, "ERROR EN LINEA" + lin.get(i) + " CONSECUTIVO  "
									+ listaCadena.get(contLinea).getConsecArch() + "/"
									+ listaCadena.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS EN EL CAMPO -> \"CUENTA BANCARIA\"");
							return validaNotaCreditoConse;
						}
					}
					
					if(listaCadena.get(contLinea).getTp_registro().equals("NF") && listaCadena.get(i).getTp_registro().equals("NF")) {
						if(!(listaCadena.get(contLinea).getTpBanco() == null && listaCadena.get(i).getTpBanco() == null)) {
							if(!(listaCadena.get(contLinea).getTpBanco().equals(listaCadena.get(i).getTpBanco()))) {
								validaNotaCreditoConse.put(12, "ERROR EN LINEA" + lin.get(i) + " CONSECUTIVO  "
										+ listaCadena.get(contLinea).getConsecArch() + "/"
										+ listaCadena.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS EN EL CAMPO -> \"TIPO BANCO\"");
								return validaNotaCreditoConse;
							}
						}
					}
					
					if(!(listaCadena.get(contLinea).getEstatusF() == null && listaCadena.get(i).getEstatusF() == null)) {
						if(!(listaCadena.get(contLinea).getEstatusF().equals(listaCadena.get(i).getEstatusF()))) {
							validaNotaCreditoConse.put(13, "ERROR EN LINEA" + lin.get(i) + " CONSECUTIVO  "
									+ listaCadena.get(contLinea).getConsecArch() + "/"
									+ listaCadena.get(contLinea).getConsecNota() + " SE ENCONTRARON DIFERENCIAS DE DATOS EN EL CAMPO -> \"ESTATUS FACTURA\"");
							return validaNotaCreditoConse;
						}
					}
				}
			}
			
			contLinea++;
		}
		return validaNotaCreditoConse;
	}
	
}
