package com.tresd;

import java.util.ArrayList;

public class Funcion {
	
	protected static final String rvoid = "void";
	protected static final String rconstructor = "%constructor%";
	
	private String definición; // nombre[-[&| ]t1:[&| ]t2: ... :[&| ]tn]
	private String retorno; // tipoRetorno | %constructor% 
	private char acceso;
	private ArrayList<Variable> variables;
	private int tamaño = 1;
	private Funcion ambitoSuperior;
	private int linea;
	private Clase clase; // para los errores, es la instancia principal
	private String etiquetas = "";

	@Override
	public boolean equals(Object o){
		if (o == null || o.getClass() != Funcion.class)
			return false;
		return aDefinicionNormal(definición).equals(aDefinicionNormal(o.toString()));
	}
	
	@Override
	public String toString(){
		return definición;
	}
	/**
	 * Inicializa una función
	 * @param def definición de la función
	 * @param ret tipo de retorno
	 * @param ac tipo de acceso
	 */
	public Funcion(String def, String ret, char ac){
		this.definición = def;
		this.retorno = ret;
		this.acceso = ac;
	}
	/**
	 * Inicializa un ámbito
	 * @param clase es la instancia principal
	 * @param retorno es el tipo de retorno
	 */
	public Funcion(Clase clase, String retorno){
		this.clase = clase;
		this.retorno = retorno;
	}
	/**
	 * Agrega el error en la instancia principal
	 * @param err descripción del error
	 */
	private void error(String err){
		clase.linea = linea;
		clase.error(err);
	}
	/**
	 * Agraga la variable al ámbito.
	 * Setea su posición relativa del stack
	 * @param tipo tipo de dato
	 * @param ap apuntador
	 * @param id nombre
	 * @param linea linea por posible error
	 * @return indica si se pudo agregar
	 */
	protected boolean addVariable(String tipo, String id, int linea) {
		this.linea = linea;
		if (variables == null)
			variables = new ArrayList<Variable>();
		Funcion f = this;
		boolean agregar = true;
		Variable var = new Variable(tipo, id);
		do {
			if (f.variables != null && f.variables.contains(var)) {
				agregar = false;
				break;
			}
			f = f.ambitoSuperior;
		} while (f != null);
		if (agregar) {
			
			int tam = 0;
			if(this.ambitoSuperior != null)
				tam = this.ambitoSuperior.getTamaño();
			
			var.setPos(tamaño + tam + (Variable.esPrimitiva(retorno) ? 1 : 0));
			this.variables.add(var);
			tamaño += var.getTamaño();
		} else
			error("La variable '" + id + "' ya ha sido declarada");
		return agregar;
	}
	/**
	 * calcula el tamaño de el ámbito
	 * @return el tamaño del ámbito y sus ámbitos superiores
	 */
	protected int getTamaño(){
		int ret = 0;
		Funcion f = this;
		do {
			ret += f.tamaño;
			f = f.ambitoSuperior;
		} while(f != null);
		return ret;
	}
	/**
	 * Agrega la etiqueta a una lista de etiquetas qus serían usadas
	 * después de la llamada a otro procedimiento. Es para conservar su valor
	 * @param et la etiqueta
	 */
	protected void agregarEtiquetaParaGuardar(String et){
		etiquetas += (etiquetas.equals("") ? "" : "&" ) + "et";
	}	
	/**
	 * Guarda las etiquetas desde la posición actual del stack más el tamaño del ámbito
	 * @return la cantidad de etiquetas agregadas 
	 */
	protected int guardarEtiquetas(){
		if(etiquetas.equals(""))
			return 0;
		String [] ets = etiquetas.split("&");
		int ret = ets.length;
		int tam = getTamaño();
		for(int i = 0 ; i < ret; i++)
			clase.add(Clase.Stack + "[" + (i + tam) + "] = " + ets[i]);
		return ret;
	}
	/**
	 * Borra las etiquetas
	 */
	protected void vaciarEtiquetas(){ etiquetas = ""; }
	/**
	 * Retorna la posicón relativa de una variable con nombre
	 * No necesita linea porque no maneja errores
	 * @param id nombre de la variable
	 * @return la posicón relativa. -1 si no es encontrada
	 */
	protected Variable getPosicionVariable(String id) {
		Funcion f = this;
		do {
			int index = f.variables == null ? -1 : f.variables.indexOf(new Variable("tipo", id));
			if (index >= 0)
				return f.variables.get(index);
			f = f.ambitoSuperior;
		} while (f != null);
		return null;
	}
	
	/**
	 * Si la visibilidad es privada
	 * @return si el acceso es private
	 */
	protected boolean esPrivada(){
		return acceso == Variable.vprivate;
	}
	protected String getRetorno(){
		return retorno; 
	}
	/**
	 * Convierte una definición con indicadores de variable o valor
	 * a una definición sin estos indicadores, sólo con tipos
	 * @param def definición con indicadores
	 * @return definición sólo con tipos
	 */
	private static String aDefinicionNormal(String def){
		String [] vdef = def.split("-");
		String ret = vdef[0];
		if(vdef.length == 1)
			return def;
		for(String tipo : vdef[1].split((":"))){
			try{
			ret += tipo.substring(1);
			} catch(Exception e){
				System.out.println();
			}
		}
		return ret;
	}
	
	/**
	 * Agrega un ámbito donde serán agregadas variables locales
	 * para dar impresión que serán accedidas sólo en el ámbito y no en los hermanos
	 * @return nueva Funcion, se agrega referencia de ámbito superior
	 */
	protected Funcion agregarAmbito(){
		Funcion ret = new Funcion(clase, retorno);
		ret.tamaño = 0;
		ret.ambitoSuperior = this;
		return ret;
	}
	
	/**
	 * Quita la referencia al ámbito superior 
	 * @return
	 */
	protected Funcion ambitoSuperior(){
		return this.ambitoSuperior;
	}
}
