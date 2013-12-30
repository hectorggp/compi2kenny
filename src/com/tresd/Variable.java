package com.tresd;

public class Variable {

	protected static final char vprivate = 'i';
	protected static final char vprotected = 'o';
	protected static final char vpublic = 'b';
	protected static final int escapeString = 0;
	protected static final int tamString = 256;
	protected static final String tString = "String";
	protected static final String tint = "int";
	protected static final String tfloat = "float";
	protected static final String tboolean = "boolean";
	protected static final String tchar = "char";
	protected static final String or = "or";
	protected static final String and = "and";
	protected static final String xor = "xor";

	protected String definición; // nombre[-dim1:dim2: ... :dimn]
	private int posición = -1;
	public char acceso = '*';
	private int tamaño = 0;
	
	protected Variable(String temp, String lugar, 
			String tipo, boolean referencia, boolean variable){
		this.temp = temp;
		this.lugar = lugar;
		this.tipo = tipo;
		this.referencia = referencia;
		this.variable = variable;
	}
	
	// para segunda pasada:
	protected String temp;
	protected String lugar;
	protected String tipo;
	protected boolean referencia;
	protected boolean variable;
	
	/**
	 * Si el atributo es privado
	 * @return si el atributo es privado
	 */
	protected boolean esPrivada(){
		return acceso == vprivate;
	}
	
	// retorna true si, a partir de la definición, tiene el mismo nombre
	@Override
	public boolean equals(Object o){
		if(o == null || o.getClass() != Variable.class)
			return false;
		Variable tmp = (Variable) o;
		return 	tmp.definición.split("-")[0].equals(definición.split("-")[0]);
	}
	
	@Override
	public String toString(){
		return acc(acceso) + "|" + definición + "|" + posición;
	}
	/**
	 * Encuentra el string correspondiente al tipo tipo char
	 * @param c tipo en char
	 * @return tipo en String
	 */
	protected static String acc(char c) {
		switch (c) {
		case vprivate:
			return "private";
		case vprotected:
			return "protected";
		default:
			return "public";
		}
	}
	/**
	 * Inicializa el atributo
	 * @param acceso
	 * @param tipo
	 * @param definicion
	 */
	public Variable(char acceso, String tipo, String definicion) {
		this.acceso = acceso;
		this.tipo = tipo;
		this.definición = definicion;
		tamaño = calcTamaño();
	}
	/**
	 * Inicializa variable local
	 * @param tipo tipo de variable
	 * @param definición definición de la variable
	 */
	public Variable(String tipo, String definición) {
		this.tipo = tipo;
		this.definición = definición;
		tamaño = calcTamaño();
	}
	
	/**
	 * Retorna si el tipo de dato es primitivo 
	 * int, char, boolean o float
	 * @return Si es tipo de dato primitivo
	 */
	protected boolean esPrimitiva(){
		return esPrimitiva(tipo);
	}
	/**
	 * Calcula el tamaño de la variable
	 * @return tamaño calculado
	 */
	private int calcTamaño() {
		String def[] = definición.split("-");
		int tam = 1;
		if (def.length == 2) {
			for (String num : def[1].split(":")) {
				int dim = Integer.valueOf(num);
				tam *= dim;
			}
		}
		return tam;
	}
	/**
	 * Devuelve el tamaño de la variable
	 * @return tamaño que varia dependiendo si se trata de un arreglo o un String
	 */
	public int getTamaño(){
		return tamaño;
	}
	
	/**
	 * establece la posición relativa de donde se encuentra la variable
	 * @param pos posición relativa
	 */
	protected void setPos(int pos){
		posición = pos;
	}
	/**
	 * Devuelve la posición relativa de la variable
	 * @return posición
	 */
	protected int getPosición(){ return posición; }
	
	/**
	 * Retorna true si el tipo de la variable es numérico 
	 * @return si el tipo es int o float
	 */
	protected boolean esNumerica(){
		return tipo != null && (tipo.equals(tfloat) || tipo.equals(tint));
	}

	/**
	 * Verifica que el tipo enviado sea primitivo
	 * @param tipo tipo en String
	 * @return retorna si es primitivo
	 */
	protected static boolean esPrimitiva(String tipo) {
		return tipo != null
				&& (tipo.equals(tboolean) || tipo.equals(tchar)
						|| tipo.equals(tfloat) || tipo.equals(tint));
	}
}
