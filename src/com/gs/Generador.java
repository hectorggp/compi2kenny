package com.gs;

import java.util.ArrayList;
import java.io.File;

public class Generador {

	private String errores = "";
	private ArrayList<String> atrs = new ArrayList<String>();
	private int pos = -1;
	private final static String [] tipos = {"int", "void", "String", "char", "float"};
	/**
	 * Agrega una construcción de función
	 * formato:
	 * [set|get][ID]_[TIPO]
	 * @param construcción string de nombre por construcción
	 */
	protected void addNombre(String construcción){
		if(!atrs.contains(construcción))
			atrs.add(construcción);
		else 
			atrs.remove(construcción);
	}
	/**
	 * Revisa si hay método identico, para no agregarlo
	 * @param construcción
	 */
	protected void checkNombre(String construcción){
		String subs = construcción.substring(construcción.indexOf("_"));
		for(String tipo : tipos){
			tipo += subs;
			if(atrs.contains(tipo))
				atrs.remove(tipo);
		}
	}
	/**
	 * Genera los getters y los setters
	 */
	public void generarSG(File file){
		try {
			new ParserCup(file, this).parse();
		} catch (Exception e) { }
	}
	/**
	 * Guarda el valor de la posición donde se insertarán los getters y los setters
	 * @param i posición
	 */
	protected void setPosGS(int i){
		this.pos = i;
	}
	/**
	 * Devuelve posición que ocuparán los getters y los setters
	 * @return posición
	 */
	public int getPosGS(){ return pos; }
	/**
	 * Agrega errores de cualquier tipo al archivo analizandose
	 * @param err Descripción del error
	 * @param archivo Archivo donde se encuentra el error
	 * @param linea Linea donde está el error
	 */
	public String getGS(){
		String ret = "";
		for(String v : atrs){
			String des [] = v.split("_");
			String pars = "";
			String cod = des[1].charAt(0) == 's' 
					? des[1].substring(3) + " = arg0" 
					: "return " + des[1].substring(3);
			if(des.length > 2)
				pars = des[2] + " arg0";
			ret += "\tpublic " + des[0] + " " + des[1] + "(" + pars + ") {\n\t\t" + cod + ";\n\t}\n\n";
		}
		return ret + "}";
	}
	
	protected void error(String err, String archivo, int linea){
		errores += err + ". Linea " + linea + ", archivo '" + archivo + "'\n";
	}

	public void errs(){
		System.out.println(errores.equals("") ? "No hay errores" : errores);
	}
}

