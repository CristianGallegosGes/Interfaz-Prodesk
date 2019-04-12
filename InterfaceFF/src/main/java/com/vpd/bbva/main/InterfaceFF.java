package main.java.com.vpd.bbva.main;

import java.text.ParseException;

import main.java.com.vpd.bbva.control.ControlFlujo;

/**
 * Clase Maestra para inciar el programa
 * 
 * @author XME0393
 *
 */
public class InterfaceFF {
	public static void main(String[] args) throws ParseException {
		ControlFlujo controlF = new ControlFlujo();
		controlF.Control();
	}
}
