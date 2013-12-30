package com.java;

public class Java {

	private String errores = "";
	/**
	 * Agrega errores de cualquier tipo al archivo analizandose
	 * @param err Descripción del error
	 * @param archivo Archivo donde se encuentra el error
	 * @param linea Linea donde está el error
	 */
	protected void error(String err, String archivo, int linea){
		errores += err + ". Linea " + linea + ", archivo '" + archivo + "'\n";
	}
	
	public void errs(){
		System.out.println(errores.equals("") ? "No hay errores" : errores);
	}
	
}
