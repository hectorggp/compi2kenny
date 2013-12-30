package com.formato;

import java.io.File;

public class Formador {
	private String errores = "";
	private String formato = "";
	private String comment = null;
	private String comments = null;
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
	/**
	 * Le arregla el formato al archivo abierto y coloca el texto en el editor
	 * @param file Archivo actual
	 * @return cadena formateada
	 */
	public String formato(File file){
		String ret = "";
		try {
			new ParserCup(file, this).parse();
		} catch (Exception e) { 
			e.printStackTrace();
		}
		return ret;
	}
	/**
	 * Agrega el texto en curso a la variable
	 * @param txt texto a agregar
	 */
	protected void add(String txt){
		add(txt, false);
	}
	protected void add(String txt, boolean s){
		formato += txt;
		if(txt.contains("\n")){
			últimoComment();
			if(!s) últimosComments();
		}		
	}
	/**
	 * @return retorna texto a formato
	 */
	public String getTexto(){
		return formato;
	}
	/**
	 * Agrega los comentarios encontrados en el léxico
	 * @param c comentario
	 */
	protected void addComment(String c){
		if(comment == null)
			comment = c;
		else 
			comment += c;
	}
	/**
	 * Agrega comentario
	 */
	private void últimoComment(){
		if(comment != null){
			add(CUP$ParserCup$actions.tabs() + "//" + comment);
			comment = null;
			add("\n");
		}
	}
	/**
	 * Acumula los comentarios
	 * @param c comentario
	 */
	protected void addComments(String c){
		if(comments == null)
			comments = c;
		else 
			comments += c;
	}
	/**
	 * Concatena los comentarios
	 */
	private void últimosComments(){
		if(comments != null){
			String cms [] = comments.split("\n");
			String tabs = CUP$ParserCup$actions.tabs();
			add(tabs + "/**\n", true);
			for(String cm : cms){
				add(tabs + " //* " + cm + "\n", true);
			}
			comments = null;
			add(tabs + "*/\n");
		}
	}
}








