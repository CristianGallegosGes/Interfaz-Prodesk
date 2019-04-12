package main.java.com.vpd.bbva.control;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import main.java.com.vpd.bbva.bean.BeanFF;

public class AgregarDatosBeanFF1 {

	public ArrayList<BeanFF> setCadenas(List<String> cadenas) throws ParseException {

		ArrayList<BeanFF> beanFFDatosCorrectos = new ArrayList<BeanFF>();
		
		System.out.println();
		for (String cadena : cadenas) {
			BeanFF registroBeanFF = new BeanFF();
			registroBeanFF.setConsecArch(Integer.parseInt(cadena.substring(0, 10).trim()));

			if (!(cadena.substring(10, 19).trim().equals(""))) {
				registroBeanFF.setNu_carta(Integer.parseInt(cadena.substring(10, 19).trim()));
			}

			if (!(cadena.substring(19, 28).trim().equals(""))) {
				registroBeanFF.setNu_factura(Integer.parseInt(cadena.substring(19, 28).trim()));
			}

			registroBeanFF.setTp_registro(cadena.substring(28, 30).trim());
			registroBeanFF.setConsecNota(Integer.parseInt(cadena.substring(30, 34).trim()));
			registroBeanFF.setTp_carta(cadena.substring(34, 36).trim());
			registroBeanFF.setTp_pago(Integer.parseInt(cadena.substring(36, 38).trim()));
			registroBeanFF.setNu_proveedor(Integer.parseInt(cadena.substring(38, 48).trim()));
			registroBeanFF.setSociedadRec(cadena.substring(48, 52).trim());
			registroBeanFF.setGlg(cadena.substring(52, 102));

			if (!(cadena.substring(102, 104).trim().equals(""))) {
				registroBeanFF.setEmpGasBursa(cadena.substring(102, 104).trim());
			}

			if (!(cadena.substring(104, 125).trim().equals(""))) {
				registroBeanFF.setFideicomiso(cadena.substring(104, 125).trim());
			}

			if (!(cadena.substring(125, 145).trim().equals(""))) {
				registroBeanFF.setNu_acreditado(cadena.substring(125, 145).trim());
			}

			if (!(cadena.substring(145, 157).trim().equals(""))) {
				registroBeanFF.setNu_pep(cadena.substring(145, 157).trim());
			}

			registroBeanFF.setMondena(cadena.substring(157, 160).trim());

			if (!(cadena.substring(160, 162).trim().equals(""))) {
				registroBeanFF.setPeriodificacion(cadena.substring(160, 162).trim());
			}

			if (!(cadena.substring(162, 164).trim().equals(""))) {
				registroBeanFF.setProviEjerAnterior(cadena.substring(162, 164).trim());
			}

			if (!(cadena.substring(164, 174).trim().equals(""))) {
				registroBeanFF.setContrato(Integer.parseInt(cadena.substring(164, 174).trim()));
			}

			if (!(cadena.substring(174, 182).trim().equals(""))) {
				registroBeanFF.setRecAlternativo(Integer.parseInt(cadena.substring(174, 182).trim()));
			}

			registroBeanFF.setCuentaGps(Integer.parseInt(cadena.substring(182, 192).trim()));
			registroBeanFF.setUsuarioCreador(cadena.substring(192, 202).trim());
			registroBeanFF.setCentroCostos(cadena.substring(202, 206).trim());
			registroBeanFF.setDescripServicio(cadena.substring(206, 306).trim());
				SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
			registroBeanFF.setFechaInicio(formato.parse(cadena.substring(306, 316).trim()));
			registroBeanFF.setFechaFin(formato.parse(cadena.substring(316, 326).trim()));
			registroBeanFF.setEstado(Integer.parseInt(cadena.substring(326, 328).trim()));
			registroBeanFF.setImporteUn(new BigDecimal(cadena.substring(328, 340).trim()));
			registroBeanFF.setNu_unidades(new BigDecimal(cadena.substring(340, 353).trim()));
			registroBeanFF.setIva(new Integer(cadena.substring(353, 365).trim()));

			if (!(cadena.substring(365, 377).trim().equals(""))) {
				registroBeanFF.setIsrRetenido(new BigDecimal(cadena.substring(365, 377).trim()));
			}

			if (!(cadena.substring(377, 389).trim().equals(""))) {
				registroBeanFF.setIvaRetenido(new BigDecimal(cadena.substring(377, 389).trim()));
			}

			if (!(cadena.substring(389, 401).trim().equals(""))) {
				registroBeanFF.setImpuestoCedular(new BigDecimal(cadena.substring(389, 401).trim()));
			}

			if (!(cadena.substring(401, 413).trim().equals(""))) {
				registroBeanFF.setOtrosImpuestos(new BigDecimal(cadena.substring(401, 413).trim()));
			}

			if (!(cadena.substring(413, 425).trim().equals(""))) {
				registroBeanFF.setDescuento(new BigDecimal(cadena.substring(413, 425).trim()));
			}

			if (!(cadena.substring(425, 427).trim().equals(""))) {
				registroBeanFF.setComprobacion(cadena.substring(425, 427).trim());
			}

			if (!(cadena.substring(427, 457).trim().equals(""))) {
				registroBeanFF.setNu_anticipo(cadena.substring(427, 457).trim());
			}

			if (!(cadena.substring(457, 467).trim().equals(""))) {
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				registroBeanFF.setFecha_anticipo(formatter.parse(cadena.substring(457, 467).trim()));
			}

			registroBeanFF.setViaP(cadena.substring(467, 468).trim());
			registroBeanFF.setCuentaBanc(Integer.parseInt(cadena.substring(468, 488).trim()));

			if (!(cadena.substring(488, 498).trim().equals(""))) {
				registroBeanFF.setTpBanco(cadena.substring(488, 498).trim());
			}

			if (!(cadena.substring(498, 518).trim().equals(""))) {
				registroBeanFF.setNu_activo(Integer.parseInt(cadena.substring(498, 518).trim()));
			}

			registroBeanFF.setEstatusF(cadena.substring(518, 521).trim());
			registroBeanFF.setAplicativoOrg(cadena.substring(521, 524).trim());
			beanFFDatosCorrectos.add(registroBeanFF);
		}
		System.out.println();
		return beanFFDatosCorrectos;
	}
}
