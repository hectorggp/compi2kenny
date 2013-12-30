package com.tresd;

public class Valor { // muy importante para comprobación de tipos
	public String texto;
	public String temp = "";
	public String origen = "";
	public String tipo;
	public boolean primitivo = true;

	public Valor() {
	}

	public Valor(String texto) {
		setear(texto, "");
	}

	public Valor(String texto, String tipo) {
		setear(texto, tipo);
	}

	public void setear(String texto, String tipo) {
		this.texto = texto;
		this.tipo = tipo;
		this.primitivo = Variable.esPrimitiva(tipo);
		try{
			this.temp = texto.substring(texto.indexOf('[') + 1, texto.indexOf(']'));
		} catch(Exception e) { }
		try{
			this.origen = texto.substring(0, texto.indexOf('['));
		} catch(Exception e) { }
	}

	@Override
	public String toString() {
		return texto;
	}
}
