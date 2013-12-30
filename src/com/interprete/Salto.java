package com.interprete;

public class Salto implements Cloneable{

	public String et;
	public int linea;
	public Salto sup;
	
	public Salto(String et, int pos){
		this.et = et;
		this.linea = pos;
	}
	
	@Override
	public String toString(){
		return "posicion: " + linea + ". Et: " + et;
	}

	@Override
	public Salto clone(){
		return new Salto(et, linea);
	}
	
	@Override
	public boolean equals(Object o){
		if(o.getClass() != Salto.class)
			return false;
		return ((Salto) o).et.equals(et);
	}
}
