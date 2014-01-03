package com.tresd;

import java_cup.runtime.Symbol;
import java.io.File;
import java.util.ArrayList;

action code
{:
	private void error(){
		parser.syntax_error(parser.symbolActual());
	}

	private void error(String error, int linea){
		clase().error(error, parser.archivo, linea);
	}
	
	private void error(String symbol){
		parser.syntax_error(new Symbol(ParserSym.error, 
				parser.symbolActual().left, 0, symbol));
	}
	
	private Variable [] cases = new Variable [50];
	private int icases = -1;
	private String [] display = new String [50];
	private int idisplay = -1;
	private String [] pila = new String [50];		// para push
	private int ipila = -1;							// y pop
	private String tempPorGuardar = "";
	private String etSalidaMetodo;
	
	private void addtif(String val, String bool, String et){
		addt("if (" + val + " == " + bool + ") goto " + et);
	}
	
	private void push(String val){
		pila[++ ipila] = val;
	}
	
	private String pop(){
		return pila[ipila --];
	}
	
	private Clase clase(){ return parser.clase; }
	private String nclase(){ return parser.nclase; }
	private int pasada(){ return parser.pasada; }
	private Funcion funcion(){ return parser.funcion; }
	private void funcion(Funcion f) { parser.funcion = f; }
	private void add(String txt) { clase().add(txt); }
	private void addt(String txt) { add("\t" + txt + ";\n"); }
	private String genTemp(boolean guardar){
		String ret = Clase.genTemp();
		if(guardar)
			tempPorGuardar += tempPorGuardar.equals("") ? ret : ":" + ret;
		return ret;
	}
	private String genTemp(){ return genTemp(true); }
	private void resetTempPorGuardar(){ tempPorGuardar = ""; }
	private String genEtiq(){ return Clase.genEtiq(); }
	private void resetcTemp() { Clase.resetcTemp(); }
	private String apheap(){ return Clase.apHeap; }
	private String apstack(){ return Clase.apStack; }
	private String heap(){ return Clase.Heap; }
	private String stack(){ return Clase.Stack; }
	private String escape(){ return String.valueOf(Variable.escapeString); }

	/**
	 * Realiza el protocolo de llamada a función. Retorna null 
	 * si es un procedimiento sin retorno. Si no, genera variable  
	 * @param clase clase de la instancia a la que pertenece el método
	 * @param metodo método al que se llama
	 * @param pars lista de instancias de Variable puestos como parámetros
	 * @param posInstancia temporal que posee la posición de la instancia
	 * @param linea linea por posible error
	 * @return
	 */
	private Variable llamarFuncion(String clase, String metodo, 
			ArrayList<Variable> pars, String posInstancia, int linea){
		Variable ret = null;
		/**
		 * 1) verificar que metodo existe y que es accesible
		 * 2) guardar temps en stack
		 * 3) setear parametros por valor o referencia
		 * 4) actualizar punteros de stack y heap
		 * 5) llamar procedimiento
		 * 6) tomar valor de retorno
		 * 7) restablecer temporales
		 * 8) retornar valor 
		 */
		String def = clase().definicionDeClaseSiPuede(clase, metodo, pars, linea);
		if (def != null) { // ya se trató el error
							// def de la forma tipoRetorno-nombre[-[&| ]t1:[&| ]t2: ... :[&| ]tn]
			String [] vdef = def.split("-");
			int ind_ = vdef[0].indexOf('_');
			String clasep = vdef[0].substring(0, ind_);
			String retorno = vdef[0].substring(ind_ + 1);
			String [] vpars = vdef.length == 3 ? vdef[2].split(":") : null;
			String temp = genTemp(false);
			int tamAmbito = funcion().getTamaño();
			String [] vtempPorGuardar = tempPorGuardar.split(":");
			
			addt("// guardar temps en stack");
			// 2) guardar temps en stack
			if(!tempPorGuardar.equals("")){
				for(int i = 0; i < vtempPorGuardar.length; i++){
					addt(temp + " = " + apstack() + " + " + tamAmbito ++);
					addt(stack() + "[(int) " + temp + "] = " + vtempPorGuardar[i]);
				}
			}
			
			addt("// setear parametros por valor o referencia");
			// 3) setear parametros por valor o referencia
			int posStack = tamAmbito;
			if(!retorno.equals(Funcion.rconstructor) && !retorno.equals(Funcion.rvoid))
				posStack ++;
			String defin = "";
			for(int i = 0; i < pars.size(); i++){
				addt(temp + " = " + apstack() + " + " + ++ posStack);
				Variable par = pars.get(i);
				defin += "_" + par.tipo;
				if(vpars[i].charAt(0) == ' ' && Variable.esPrimitiva(par.tipo)){
					if(par.lugar == null){// valores float simples 
						addt(stack() + "[(int) " + temp + "] = " + par.temp);
					} else { // su 'temp' está apuntando a 'lugar'
						if(!par.referencia){ // sólo primitivos
							addt(par.temp + " = " + par.lugar + "[(int) " + par.temp + "]");
							addt(stack() + "[(int) " + temp + "] = " + par.temp);
						} else { // solo primitivos en stack
							addt(par.temp + " = " + stack() + "[(int) " + par.temp + "]");
							addt(par.temp + " = " + stack() + "[(int) " + par.temp + "]");
							addt(stack() + "[(int) " + temp + "] = " + par.temp);
						}
					}
				} else { // espera referencia de primitivo en stack o es instancia de clase
					if(par.lugar == null){
						error("Se espera variable", linea);
					} else {
						if(Variable.esPrimitiva(par.tipo)){
							if(!par.referencia) {
								addt(stack() + "[(int) " + temp + "] = " + par.temp);
							} else {
								addt(par.temp + " = " + stack() + "[(int) " + par.temp + "]");
								addt(stack() + "[(int) " + temp + "] = " + par.temp);
							}
						} else { // es instancia de clase o String
							if(par.lugar.equals(stack()))
								add(par.temp + " = " + par.lugar + "[(int) " + par.temp + "]");
							addt(stack() + "[(int) " + temp + "] = " + par.temp);
						}
					}
				} 
			}
			
			addt("// actualizar punteros en stack y heap");
			// 4) actualizar punteros de stack y heap
			addt(temp + " = " + apstack() + " + " + tamAmbito);
			addt(stack() + "[(int) " + temp + "] = " + posInstancia);
			addt(apstack() + " = " + apstack() + " + " + tamAmbito);
			
			addt("// llamar al procedimiento");
			// 5) llamar al procedimiento
			metodo = clasep + "_" + (retorno.equals(Funcion.rconstructor) ? 
					"void" : retorno) + "_" + metodo + defin;
			addt(metodo + "()");
			addt("// restablecer valor de apstack");
			// 5.1) restablecer valor de apstack
			addt(apstack() + " = " + apstack() + " - " + tamAmbito);
			
			addt("// tomar valor retorno");
			// 6) tomar valor retorno
			if(!retorno.equals(Funcion.rconstructor) && !retorno.equals(Funcion.rvoid)){
				String tret = genTemp();
				addt(tret + " = " + apstack() + " + " + (tamAmbito + 1));
				ret = new Variable(tret, Variable.esPrimitiva(retorno) ?
						stack() : heap(), retorno, !Variable.esPrimitiva(retorno), false);
			}
			
			addt("// restablecer temporales");
			// 7) restablecer temporales
			if(!tempPorGuardar.equals("")){
				for(int i = vtempPorGuardar.length - 1; i >= 0; i --){
					addt(temp + " = " + apstack() + " + " + -- tamAmbito);
					addt(vtempPorGuardar[i] + " = " + stack() + "[(int) " + temp + "]");
				}
			}
		}
		// 8) retornar valor
		return ret;
	}

	/**
	 * retorna temporal con el valor de la posición en heap de la nueva instancia
	 * Actualiza el aputador al heap
	 * @param sclase clase a instanciar
	 * @param nulo si la nueva clase está inicializada como null
	 * @param linea liea por posible error
	 * @return temporal generado o null si no existe clase
	 */
	private String tnuevaInstancia(String sclase, boolean nulo, int linea){
		int tamClase = sclase.equals(Variable.tString) ? 
				Variable.tamString : clase().tamClase(sclase);
		if(tamClase > 0){
			String temp = genTemp();
			addt(temp + " = " + apheap());
			if(!nulo)
				addt(heap() + "[(int) " + temp + "] = " + temp);
			else
				addt(heap() + "[(int) " + temp + "] = -1");
			addt(apheap() + " = " + apheap() + " + " + tamClase);
			return temp;
		}
		error("No se encuentra clase '" + sclase + "'", linea);
		return null;
	}
	
	/**
	 * retorna temporal con el valor de la posición en heap de la nueva instancia
	 * Actualiza el aputador al heap
	 * @param sclase clase a instanciar
	 * @param linea liea por posible error
	 * @return temporal generado o null si no existe clase
	 */
	private String tnuevaInstancia(String sclase, int linea){
		return tnuevaInstancia(sclase, false, linea);
	}
	
	/**
	 * Retorna un temporal con el valor de la posición en heap donde empieza 
	 * el string
	 * @param valor la cadena String
	 * @param linea la linea por posible error
	 * @return el temporal 
	 */
	private String tnuevaInstanciaString(String valor, int linea){
		String tstring = tnuevaInstancia(Variable.tString, linea);
		String temp = genTemp(false);
		int i = 0;
		for(char c : valor.toCharArray()){
			addt(temp + " = " + tstring + " + " + ++ i);
			// addt(heap() + "[(int) " + temp + "] = " + (int) c);
			addt(heap() + "[(int) " + temp + "] = " + (int)c);
		}
		addt(temp + " = " + tstring + " + " + ++ i);
		addt(heap() + "[(int) " + temp + "] = " + escape());
		return tstring;
	}

	/**
	 * Retorna Variable, el temporal tiene el valor correspondiente al resultado de la operación
	 * de tipo ++ o --
	 * Retorna null si la Variable es inválida
	 * @param antes Si el operador está antes de la variable
	 * @param op el operador, " + " o " - "
	 * @param v la Variable debe ser variable
	 * @param linea linea por posible error
	 * @return Variable cuyo temporal tiene el resultado de la operación
	 */
	private Variable cvariable(boolean antes, String op, Variable v, int linea){
		Variable ret = null;
		String temp1 = genTemp(!antes);
		String temp2 = genTemp(antes);
		if(!v.referencia){ // es un valor en 'lugar' apuntado por 'temp'
			addt(temp1 + " = " + v.lugar + "[(int) " + v.temp + "]");
			addt(temp2 + " = " + temp1 + op + 1);
			addt(v.lugar + "[(int) " + v.temp + "] = " + temp2);
			ret = new Variable(antes ? temp2 : temp1, null, v.tipo, false, false);
		} else { // es una referencia en stack apuntada por 'temp'
			if(v.lugar.equals(stack())){
				addt(temp1 + " = " + stack() + "[(int) " + v.temp + "]");
				addt(temp1 + " = " + stack() + "[(int) " + temp1 + "]");
				addt(temp2 + " = " + temp1 + op + 1);
				addt(stack() + "[(int) " + v.temp + "] = " + temp2);
				ret = new Variable(antes ? temp2 : temp1, null, v.tipo, false, false);
			} else
				error("Se esperaba variable numérica de ámbito", linea + 1);
		}
		return ret;
	}
	
	/**
	* Busca en los ámtibos actuales la variable etiquetada con 'id'
	* Si no se encuentra, devuelve null
	* @param id Etiqueta de la variable a buscar
	* @return Variable con los datos de la variable buscada o null si no se encuentra
	*/
	private Variable buscaEnAmbito(String id){
		Variable ret = null;
		Variable var = funcion().getPosicionVariable(id);
		if(var != null){
			String [] vatr = var.definición.split("-");
			String def = vatr.length > 1 ? "-" + vatr[1] : "";
			String temp = genTemp();
			addt(temp + " = " + apstack() + " + " + var.getPosición());
			ret = new Variable(temp + def, stack(), var.tipo, var.referencia, true);
		}
		return ret;
	}
	
	/**
	 * Busca el atributo de la instancia 'clasePoseedora' a partir de una instancia 
	 * 'claseDeOrigen'. Valida que se encuentre y visibilidad. Si es inválido, devuelve null
	 * @param id etiqueta del atributo 
	 * @param clasePoseedora clase que posee el atributo
	 * @param temp temporal que posee el valor de la posición del heap donde está la instancia 'clasePoseedora'
	 * @param linea linea por posible error
	 * @return Variable si se encuentra, o null si hay error
	 */
	private Variable buscaEnInstancia(String id, String clasePoseedora, String temp, int linea){
		Variable ret = null;
		Variable atr = clase().buscarEnClase(id, clasePoseedora, linea);
		if(atr != null){
			String [] vatr = atr.definición.split("-");
			String def = vatr.length > 1 ? "-" + vatr[1] : "";
			addt(temp + " = " + temp + " + " + atr.getPosición());
			ret = new Variable(temp + def, heap(), atr.tipo, !Variable.esPrimitiva(atr.tipo), true);
		}
		return ret;
	}

	/**
	 * Asigna al lugar apuntado por var.temp, en el lugar var.lugar, el valor o apuntador del lugar en
	 * val.lugar apuntado por  val.temp
	 * @param var Variable donde será asignado el nuevo valor
	 * @param val Variable que posee el valor a asignar
	 */
	private void asigna(Variable var, Variable val, String op, int linea){
		if(var.esPrimitiva()){
			if(val.temp != null && var.tipo.equals(val.tipo)){
				if(val.lugar == null){
				} else if(!val.referencia){
					addt(val.temp + " = " + val.lugar + "[(int) " + val.temp + "]");
				} else {
					addt(val.temp + " = " + stack() + "[(int) " + val.temp + "]");
					addt(val.temp + " = " + stack() + "[(int) " + val.temp + "]");
				}
				if(!var.referencia){
					if(!op.equals("")){
						String temp = genTemp();
						addt(temp + " = " + var.lugar + "[(int) " + var.temp + "]");
						addt(val.temp + " = " + temp + op + val.temp);
					}
					addt(var.lugar + "[(int) " + var.temp + "] = " + val.temp);
				} else {
					addt(var.temp + " = " + stack() + "[(int) " + var.temp + "]");
					if(!op.equals("")){
						String temp = genTemp();
						addt(temp + " = " + stack() + "[(int) " + var.temp + "]");
						addt(val.temp + " = " + temp + op + val.temp);
					}
					addt(stack() + "[(int) " + var.temp + "] = " + val.temp);					
				}
			} else 
				error("Se esperaba valor de tipo '" + var.tipo + "'", linea);
		} else {
			if(val.temp == null){
				String tinstancia = tnuevaInstancia(var.tipo, true, linea);
				addt(var.lugar + "[(int) " + var.temp + "] = " + tinstancia);
			} else if(var.tipo.equals(val.tipo)){
				if(val.variable)
					addt(val.temp + " = " + val.lugar + "[(int) " + val.temp + "]");
				addt(var.lugar + "[(int) " + var.temp + "] = " + val.temp);
			} else
				error("Se esperaba valor de tipo '" + var.tipo + "'", linea);
		}
	}
	
	/**
	* Realiza operación aritmética y retorna Variable con el temporal que tiene ese valor 
	* val1 valor 1
	* val2 valor 2
	* op operación aritmética a realizar
	* linea linea por posible error
	*/
	private Variable aritmética(Variable val1, Variable val2, String op, int linea) {
		Variable ret = null;
		if(val1.esNumerica() && val2.esNumerica()){
			if(val1.tipo.equals(val2.tipo)){
				String temp = val1.temp;
				if(val1.lugar == null){
					temp = genTemp();
				} else if(!val1.referencia){
					addt(val1.temp + " = " + val1.lugar + "[(int) " + val1.temp + "]");
				} else {
					addt(val1.temp + " = " + stack() + "[(int) " + val1.temp + "]");
					addt(val1.temp = " = " + stack() + "[(int) " + val1.temp + "]");
				}
				if(val2.lugar == null){
				} else if(!val2.referencia){
					addt(val2.temp + " = " + val2.lugar + "[(int) " + val2.temp + "]");
				} else {
					addt(val2.temp + " = " + stack() + "[(int) " + val2.temp + "]");
					addt(val2.temp = " = " + stack() + "[(int) " + val2.temp + "]");
				}
				addt(temp + " = " + val1.temp + op + val2.temp);
				ret = new Variable(temp, null, val1.tipo, false, false);
			} else
				error("Se debe realizar casteo", linea);
		} else 
			error("Variable no numérica, no se puede operar", linea);
		return ret;
	}
	
	/**
	* Realiza protocolo de retorno de variable. Por valor o referencia
	* var variable que tiene el temporal de valor o el temporal que apunta a lugar del valor 
	* linea por posible error
	*/
	private void retorna(Variable var, int linea){
		if((!Variable.esPrimitiva(funcion().getRetorno()) && var.tipo == null) ||
				funcion().getRetorno().equals(var.tipo)){
			String ltemp = genTemp(false);
			addt(ltemp + " = " + apstack() + " + 1");
			if(var.temp != null && var.esPrimitiva()){
				if(var.lugar == null){
				} else if(!var.referencia){
					addt(var.temp + " = " + var.lugar + "[(int) " + var.temp + "]");
				} else {
					addt(var.temp + " = " + stack() + "[(int) " + var.temp + "]");
					addt(var.temp + " = " + stack() + "[(int) " + var.temp + "]");
				}
				addt(stack() + "[(int) " + ltemp + "] = " + var.temp);
			} else {
				if(var.temp != null){
					addt(var.temp + " = " + var.lugar + "[(int) " + var.temp + "]");
					addt(stack() + "[(int) " + ltemp + "] = " + var.temp);					
				} else {
					String tinstancia = tnuevaInstancia(funcion().getRetorno(), true, linea);
					addt(stack() + "[(int) " + ltemp + "] = " + tinstancia);
				}
			}
		} else 
			error("La función debe retornar tipo '" + funcion().getRetorno() + "'", linea);
	}

	/**
	 * Genera una Variable con el resultado de la comparación booleana 
	 * de las variables.
	 * Realiza validaciones
	 * @param v1 primer valor 
	 * @param v2 segundo valor 
	 * @param op operador lógico
	 * @param linea linea por posible error
	 * @return Variable en forma de booleana resultado de aplicar la operación lógica
	 */
	private Variable lógica(Variable v1, Variable v2, String op, int linea) {
		Variable ret = null;
		if(v1.tipo.equals(v2.tipo) && v1.tipo.equals(Variable.tboolean)){
			if(op.equals(Variable.or)){
				if(v1.lugar == null){
				} else if (!v1.referencia){
					addt(v1.temp + " = " + v1.lugar + "[(int) " + v1.temp + "]");
				} else{
					addt(v1.temp + " = " + stack() + "[(int) " + v1.temp + "]");
					addt(v1.temp + " = " + stack() + "[(int) " + v1.temp + "]");
				}
				
				String temp = genTemp();
				addt(temp + " = 1");
				String ev = genEtiq();
				addtif(v1.temp, "1", ev);
				
				if(v2.lugar == null){
				} else if(!v2.referencia){
					addt(v2.temp + " = " + v2.lugar + "[(int) " + v2.temp + "]");
				} else {
					addt(v2.temp + " = " + stack() + "[(int) " + v2.temp + "]");
					addt(v2.temp + " = " + stack() + "[(int) " + v2.temp + "]");
				}
				
				addtif(v2.temp, "1", ev);
				addt(temp + " = 0");
				
				add(ev + ":\n");
				return new Variable(temp, null, Variable.tboolean, false, false);
			} else if(op.equals(Variable.and)){
				if(v1.lugar == null){
				} else if (!v1.referencia){
					addt(v1.temp + " = " + v1.lugar + "[(int) " + v1.temp + "]");
				} else {
					addt(v1.temp + " = " + stack() + "[(int) " + v1.temp + "]");
					addt(v1.temp + " = " + stack() + "[(int) " + v1.temp + "]");
				}
				
				String temp = genTemp();
				addt(temp + " = 0");
				String ev = genEtiq();
				addt("if (" + v1.temp + " == 0) goto " + ev);
				
				if(v2.lugar == null){
				} else if(!v2.referencia){
					addt(v2.temp + " = " + v2.lugar + "[(int) " + v2.temp + "]");
				} else {
					addt(v2.temp + " = " + stack() + "[(int) " + v2.temp + "]");
					addt(v2.temp + " = " + stack() + "[(int) " + v2.temp + "]");
				}
				
				addt("if (" + v2.temp + " == 0) goto " + ev);
				addt(temp + " = 1");
				
				add(ev + ":\n");
				return new Variable(temp, null, Variable.tboolean, false, false);
			} else {
				if(v1.lugar == null){
				} else if (!v1.referencia){
					addt(v1.temp + " = " + v1.lugar + "[(int) " + v1.temp + "]");
				} else {
					addt(v1.temp + " = " + stack() + "[(int) " + v1.temp + "]");
					addt(v1.temp + " = " + stack() + "[(int) " + v1.temp + "]");
				}
				
				String temp = genTemp();
				String et1 = genEtiq();
				String et2 = genEtiq();
				String ev = genEtiq();
				String ef = genEtiq();
				String es = genEtiq();
				addt("if (" + v1.temp + " == 1) goto " + et1);
				addt("goto " + et2);
				
				if(v2.lugar == null){
				} else if(!v2.referencia){
					addt(v2.temp + " = " + v2.lugar + "[(int) " + v2.temp + "]");
				} else {
					addt(v2.temp + " = " + stack() + "[(int) " + v2.temp + "]");
					addt(v2.temp + " = " + stack() + "[(int) " + v2.temp + "]");
				}
				
				add(et1 + ":\n");
				addt("if (" + v2.temp + " == 1) goto " + ef);
				addt("goto " + ev);
				add(et2 + ":\n");
				addt("if (" + v2.temp + " == 1) goto " + ev);
				addt("goto " + ef);

				add(ev + ":\n");
				addt(temp + " = 1");
				addt("goto " + es);
				add(ef + ":\n");
				addt(temp + " = 0");
				add(es + ":\n");
				
				return new Variable(temp, null, Variable.tboolean, false, false);
			}
		} else 
			error("Los operandos deben ser booleanos", linea);
		return ret;
	}

	/**
	 * Retorna una instancia tipo Variable con el temporal de la posición en 'lugar'
	 * de posición calculada de matriz
	 * @param mat variable con 'temp' definica de la forma tn-dim1[:dimm]* 
	 * @param dims string con temporales con las dimensiones separadas por ":"
	 * @param idleft linea por posible error
	 * @return Variable o null si detecta error
	 */
	private Variable variableMatriz(Variable mat, String dims, int idleft){
		Variable ret = null;
		if(mat.temp.contains(":")){
			String [] vdims = dims.split(":");
			String mattemp = mat.temp.split("-")[0];
			String [] vmax = mat.temp.split("-")[1].split(":");
			if(vmax.length == vdims.length){
				String ttemp = genTemp(false);
				addt(ttemp + " = " + vdims[0]);
				String taux = genTemp(false);
				for(int i = 1; i < vdims.length; i++){
					addt(taux + " = " + ttemp + " * " + vmax[i]);
					addt(ttemp + " = " + taux + " + " + vdims[i]);
				}
				addt(mattemp + " = " + ttemp + " + " + mattemp);
				ret = new Variable(mattemp, mat.lugar, mat.tipo, false, true);
			} else 
				error("Se requieren " + vmax.length + " dimensiones", idleft + 1);
		} else 
			error("El atributo no es matriz", idleft + 1);
		return ret;
	}

	/**
	* Realiza impresión en pantalla de dato primitivo
	* @param v2 Variable a imprimir
	*/
	private void printf(Variable v2){
		if(v2.lugar == null){
		} else if(!v2.referencia){
			addt(v2.temp + " = " + v2.lugar + "[(int) " + v2.temp + "]");
		} else {
			addt(v2.temp + " = " + stack() + "[(int) " + v2.temp + "]");
			addt(v2.temp + " = " + stack() + "[(int) " + v2.temp + "]");
		}		
		
		if(v2.tipo.equals(Variable.tchar)){
			addt("printf(\"%c\", (char)((int) " + v2.temp + "))");
		} else if(v2.tipo.equals(Variable.tint)){
			addt("printf(\"%d\", (int)" + v2.temp + ")");
		} else if(v2.tipo.equals(Variable.tfloat)){
			addt("printf(\"%lf\", " + v2.temp + ")");
		} else if(v2.tipo.equals(Variable.tboolean)){
			String lf = genEtiq();
			String ls = genEtiq();
			addtif(v2.temp, "0", lf);
			addt("printf(\"%c\", (char)((int) 116))");
			addt("printf(\"%c\", (char)((int) 114))");
			addt("printf(\"%c\", (char)((int) 117))");
			addt("printf(\"%c\", (char)((int) 101))");
			addt("goto " + ls);
			add(lf + ":\n");
			addt("printf(\"%c\", (char)((int) 102))");
			addt("printf(\"%c\", (char)((int) 97))");
			addt("printf(\"%c\", (char)((int) 108))");
			addt("printf(\"%c\", (char)((int) 115))");
			addt("printf(\"%c\", (char)((int) 101))");			
			add(ls + ":\n");
		}
		addt("printf(\"%c\", (char)((int) 10))");
	}
:};

parser code
{:
    public Symbol symbolActual() { 
        return this.cur_token;
    }

	@Override
	public void syntax_error(Symbol s) { 
		clase.error("Símbolo '" + s.value + "' fuera de contexto", archivo, s.left + 1);
//		report_error("ERR Sintaxis. Archivo: " + archivo + " Linea: " + (s.left + 1) + 
//				". Texto: \"" + s.value + "\"", null);
	}

	@Override
	public void unrecovered_syntax_error(Symbol s) throws java.lang.Exception{
		System.err.println("La Cadena: \"" + s.value + "\" en la linea: " + 
				(s.left+1) + ", columna: " + s.right + " esta fuera de contexto!!!!!" );
	}

	public ParserCup(File file, Clase clase, int pasada) throws Exception {
		super(new LexicoLex(file, clase));
		this.archivo = file.getName();
		this.clase = clase;
		this.nclase = this.archivo.split("\\.")[0];
		this.pasada = pasada;
	}
	
	protected String archivo;	
	protected Clase clase;
	protected String nclase;
	protected int pasada;
	protected Funcion funcion;
:};

terminal String PAREN, TESIS, LLA, VES, PUNTOCOMA, COR, CHETE, PUNTO, COMILLAS, 
		MAS, MENOS, MUL, DIV, MOD, ASIGNA, COMA, DOSPUNTOS, MASIGUAL, MENOSIGUAL, 
		MULIGUAL, MODIGUAL, AND, XOR, OR, COMILLA, VCHAR, VSTRING, INT, STRING, 
		IF, ELSE, ELSEIF, WHILE, DO, FOR, SWITCH, CASE, BREAK, CLASS, NEW, IMPORT, 
		PUBLIC, PROTECTED, PRIVATE, RETURN, PRINT, VFLOAT, VINT, ID, EXTENDS, VOID,
		APUNTADOR, CHAR, BOOLEAN, FLOAT, VBOOLEAN, COMPARA, PLUSPLUS, MINUSMINUS, 
		DEFAULT, NULL, THIS, NOT, NEGADO ;
		
terminal NEGATIVO ; 

non terminal S0, L, imports, _import, defclase, _extends, sentsc, sentc,
		atributo, funcion, declasig, 
		bloque, sents, sent, tupla, puntocoma, vatributo, __asigna,
		asigna, sentencias, ifelse, while, dowhile, for, _asigna,
		switchcase, tipoc, ves, bloquec ; 
non terminal Integer valorn ;
non terminal String lmatriz, if, decparam, decparams, apunt, _decparams, tipo, 
		as, case, default, cases, decl, dims ;
non terminal Character acceso ;
non terminal Variable declmatriz, variable, cvariable, valor, exp, primitivo, 
		declmatrizasigna ;
non terminal ArrayList<Variable> atrs, vatrs ;

precedence left ELSE ;
precedence left OR ;
precedence left XOR ;
precedence left AND ;
precedence left NEGADO ;
precedence left COMPARA ;
precedence left MAS, MENOS ;
precedence left MUL, DIV, MOD ;
precedence left NEGATIVO ;

start with S0 ;

S0::= L ;

L::= imports defclase 
	| defclase 
	| error {: error(); :} ;

imports::= imports _import
	| _import ;

_import::= IMPORT ID:id {:
		if(pasada() == 1) 
			clase().addClase(id, idleft + 1); 
	:} PUNTOCOMA 
	| error PUNTOCOMA {: error(); :} ;

defclase::= CLASS ID:id {: 
		if(pasada() == 1)
			clase().revisaNombre(id, idleft + 1); 
	:} _extends LLA sentsc ves:v {: if(v != null) error("Se esperaba '}'", vleft + 1); :} ;

ves::= VES | {: RESULT = "ola q ase :D"; :} ;

acceso::= PUBLIC {: if(pasada() == 1 || pasada() == 2) RESULT = Variable.vpublic; :}
	| PRIVATE {: if(pasada() == 1 || pasada() == 2) RESULT = Variable.vprivate; :}
	| PROTECTED {: if(pasada() == 1 || pasada() == 2) RESULT = Variable.vprotected; :}
	| {: if(pasada() == 1 || pasada() == 2) RESULT = Variable.vpublic; :} ;
	
_extends::= EXTENDS ID:id {: 
		if(pasada() == 1)
			clase().claseExtiende(id, idleft + 1);
	:}
	| ;

sentsc::= sentsc sentc
	| ;
	
sentc::= vatributo
	| funcion ;
	
vatributo::= atributo ASIGNA valor puntocoma:p {: if(p != null) error("Se esperaba ';'", pleft + 1); :} 
	| atributo puntocoma:p {: if(p != null) error("Se esperaba ';'", pleft + 1); :} 
	| error PUNTOCOMA {: error(); :} ;
	
atributo::= acceso:a tipo:t ID:id {:
		if(pasada() == 1)
			clase().agregaAtributo(a, t, id, idleft + 1);
	:}
	| acceso:a tipo:t lmatriz:l CHETE {:
		if(pasada() == 1 && l != null) 
			clase().agregaAtributo(a, t, l, lleft + 1);
	:} ;

puntocoma::= PUNTOCOMA | {: RESULT = "durmiendo o q ase"; :} ;
	
lmatriz::= lmatriz:l CHETE COR valorn:v {: 
		if(pasada() == 1) {
			if(v > 0){
				if (l != null)
					RESULT = l + ":" + v; 
			} else 
				error("Dimensión no válida. Se esperaba un valor positivo diferente de cero", vleft + 1);
		}
	:}
	| ID:id COR valorn:v {: 
		if(pasada() == 1) {
			if(v > 0)
				RESULT = id + "-" + v; 
			else 
				error("Dimensión no válida. Se esperaba un valor positivo diferente de cero", vleft + 1);
		}
	:} ;

valorn::= VINT:i {: if(pasada() == 1 || pasada() == 2) RESULT = Integer.valueOf(i); :} 
	| valorn:v1 MAS valorn:v2 {: if(pasada() == 1 || pasada() == 2) RESULT = v1 + v2; :}
	| valorn:v1 MENOS valorn:v2 {: if(pasada() == 1 || pasada() == 2) RESULT = v1 - v2; :}
	| valorn:v1 MUL valorn:v2 {: if(pasada() == 1 || pasada() == 2) RESULT = v1 * v2; :}
	| valorn:v1 DIV valorn:v2 {: if(pasada() == 1 || pasada() == 2) RESULT = v1 / v2; :}
	| valorn:v1 MOD valorn:v2 {: if(pasada() == 1 || pasada() == 2) RESULT = v1 % v2; :}
	| PAREN valorn:v TESIS {: if(pasada() == 1 || pasada() == 2) RESULT = v; :}
	| MENOS valorn:v {: if(pasada() == 1 || pasada() == 2) RESULT = - v; :} %prec NEGATIVO 
	| error {: RESULT = 0; :} ;

tipo::= INT {: if(pasada() == 1 || pasada() == 2) RESULT = Variable.tint; :}
	| STRING {: if(pasada() == 1 || pasada() == 2) RESULT = Variable.tString; :}
	| CHAR {: if(pasada() == 1 || pasada() == 2) RESULT = Variable.tchar; :}
	| BOOLEAN {: if(pasada() == 1 || pasada() == 2) RESULT = Variable.tboolean; :}
	| FLOAT {: if(pasada() == 1 || pasada() == 2) RESULT = Variable.tfloat; :}
	| ID:id {: if(pasada() == 1 || pasada() == 2) RESULT = id; :} ;

tipoc::= INT {: if(pasada() == 1 || pasada() == 2) RESULT = Variable.tint; :}
	| STRING {: if(pasada() == 1 || pasada() == 2) RESULT = Variable.tString; :}
	| CHAR {: if(pasada() == 1 || pasada() == 2) RESULT = Variable.tchar; :}
	| BOOLEAN {: if(pasada() == 1 || pasada() == 2) RESULT = Variable.tboolean; :}
	| FLOAT {: if(pasada() == 1 || pasada() == 2) RESULT = Variable.tfloat; :} ;
	
funcion::= acceso:a tipo:t ID:i PAREN {:
		if(pasada() == 2) {
			funcion(new Funcion(clase(), t));
			add("\nvoid " + nclase() + "_" + t + "_" + i);
		}
	:} _decparams:p {:
		if(pasada() == 1)
			if(Variable.esPrimitiva(t))
				clase().addFuncion(a, t, i, p, ileft + 1);
			else
				error("Tipo de retorno inválido para la función", tleft + 1);
	:} TESIS bloquec {: if(pasada() == 2) { add("}\n"); resetcTemp(); } :}
	| acceso:a VOID:t ID:i PAREN {:
		if(pasada() == 2) {
			funcion(new Funcion(clase(), t));
			add("\nvoid " + nclase() + "_" + t + "_" + i);
		}
	:} _decparams:p {:
		if(pasada() == 1)
			clase().addFuncion(a, t, i, p, ileft + 1);
	:} TESIS bloquec {: if(pasada() == 2) { add("}\n"); resetcTemp(); } :} 
	| acceso:a ID:i PAREN {:
		if(pasada() == 2) {
			funcion(new Funcion(clase(), Funcion.rconstructor));
			add("\nvoid " + nclase() + "_void_" + i);
		}
	:} _decparams:p {:
		if(pasada() == 1)
			clase().addFuncion(a, "%constructor%", i, p, ileft + 1);
	:} TESIS bloquec {: if(pasada() == 2) { add("}\n"); resetcTemp(); } :} 
	| error {: error(); :} ;

_decparams::= decparams:d {: 
		if (pasada() == 1) 
			RESULT = d; 
		else if (pasada() == 2) 
			add("() {\n");
	:}
	| {: 
		if(pasada() == 1) 
			RESULT = ""; 
		else if (pasada() == 2)
			add("() {\n");
	:} ;

decparams::= decparams:ds COMA decparam:d {: RESULT = ds + ":" + d; :}
	| decparam:d {: RESULT = d; :} ;
	
decparam::= tipo:t apunt:a ID:id {: 
		if(pasada() == 1) 
			RESULT = a + t; 
		else if(pasada() == 2) {
			funcion().addVariable(t, id, idleft + 1);
			add("_" + t);
		}
	:} ;

apunt::= APUNTADOR {: if(pasada() == 1) RESULT = "&"; :}
	| {: if(pasada() == 1) RESULT = " "; :} ;

bloquec::= LLA {:
		if(pasada() == 2)
			etSalidaMetodo = genEtiq();
	:} sents VES {:
		if(pasada() == 2){
			add(etSalidaMetodo + ":\n");
			addt("return");
		}
	:} ;

bloque::= LLA {:
		if(pasada() == 2)
			funcion(funcion().agregarAmbito());
	:} sents VES {:
		if(pasada() == 2)
			funcion(funcion().ambitoSuperior());
	:} ;

sentencias::= sent:s {:
		if(pasada() == 2 && s != null) 
			error("No se pueden hacer declaraciones", sleft + 1);
	:}
	| bloque ;
	
sents::= sents sent {: if(pasada() == 2) resetTempPorGuardar(); :}
	| ;

declasig::= decl:svar ASIGNA valor:val {:
		if(pasada() == 2) 
			if(svar != null && val != null)
				asigna(buscaEnAmbito(svar), val, "", svarleft + 1);
	:}
	| decl 
	| declmatrizasigna VES
	| declmatriz:d CHETE {:
		if(pasada() == 2)
			if(d != null)
				funcion().addVariable(d.tipo, d.temp, dleft + 1);
	:} ;
	
declmatrizasigna::= declmatrizasigna COMA valor 
	| declmatriz:dec CHETE ASIGNA LLA valor:val {:
		if(pasada() == 2){
			if(dec != null){
				funcion().addVariable(dec.tipo, dec.temp, decleft + 1);
				
				if(val != null){ 
					
				}
			}
		}
	:} ;

decl::= tipo:t ID:id {:
		if(pasada() == 2){
			if(funcion().addVariable(t, id, idleft + 1)){
				RESULT = id;
			}
		}
	:} ;

declmatriz::= declmatriz:d CHETE COR valorn:v {:
		if(pasada() == 2)
			if(d != null){
				d.temp += ":" + v;
				RESULT = d;
			}
	:}
	| tipo:t ID:id COR valorn:v {:
		if(pasada() == 2)
			if(v > 0){
				RESULT = new Variable(id + "-" + v, null, t, 
						false, false);
			} else
				error("Se esperaba dimensión de matriz positiva", idleft + 1);
			
	:} ;

as::= ASIGNA {: if(pasada() == 2) RESULT = ""; :}
	| MASIGUAL {: if(pasada() == 2) RESULT = " + "; :}
	| MENOSIGUAL {: if(pasada() == 2) RESULT = " - "; :}
	| MULIGUAL {: if(pasada() == 2) RESULT = " * "; :}
	| MODIGUAL {: if(pasada() == 2) RESULT = " % "; :} ;

sent::= declasig PUNTOCOMA {: RESULT = "42 >:)"; :}
	| asigna PUNTOCOMA
	| variable PUNTOCOMA
	| RETURN valor:v PUNTOCOMA {:
		if(pasada() == 2){
			if(v != null)
				retorna(v, vleft + 1);
		}
	:}
	| ifelse
	| while
	| dowhile PUNTOCOMA
	| switchcase 
	| BREAK:v PUNTOCOMA {:
		if(pasada() == 2)
			if(idisplay >= 0){
				addt("goto " + display[idisplay]);
			} else 
				error("Sentencia BREAK en ámbito incorrecto", vleft + 1);
	:}
	| for 
	| PRINT PAREN valor:v TESIS PUNTOCOMA {:
		if(pasada() == 2)
			if(v != null)
				if(v.temp != null){
					if(v.esPrimitiva()){
						printf(v);
					} else if(v.tipo.equals(Variable.tString)){
						String temp = genTemp(false);
						addt(temp + " = " + v.lugar + "[(int) " + v.temp + "]");
						String li = genEtiq();
						String ls = genEtiq();
						add(li + ":\n");
						addt(temp + " = " + temp + " + 1");
						addt(v.temp + " = " + heap() + "[(int) " + temp + "]");
						addtif(v.temp, "0", ls);
						addt("printf(\"%c\", (char)((int) " + v.temp + "))");
						addt("goto " + li);
						add(ls + ":\n");
						addt("printf(\"%c\", (char)((int) 10))");
					}
				} else
					error("Se variable no null", vleft + 1);
	:}
	| error PUNTOCOMA {: error(); :} ; 

asigna::= variable:var as:as valor:val {:
		if(pasada() == 2)
			if(var != null && val != null)
				if(var.variable)
					asigna(var, val, as, varleft + 1);
				else 
					error("Se esperaba variable para hacer asignación", varleft + 1);
	:}
	| cvariable ;
	
variable::= NEW ID:id PAREN vatrs:v TESIS {:
		if(pasada() == 2){
			String temp = tnuevaInstancia(id, idleft + 1);
			if(temp != null){
				RESULT = new Variable(temp, heap(), id, true, false);
				llamarFuncion(id, id, v, temp, idleft + 1);
			}
		}
	:}
	| THIS PUNTO ID:id COR dims:dims CHETE {:
		if(pasada() == 2 && dims != null){
			String temp = genTemp();
			addt(temp + " = " + stack() + "[(int) " + apstack() + "]");
			Variable mat = buscaEnInstancia(id, nclase(), temp, idleft + 1);
			if(mat != null)
				RESULT = variableMatriz(mat, dims, idleft + 1);
		}
	:}
	| ID:id COR dims:dims CHETE {:
		if(pasada() == 2 && dims != null){
			Variable mat = buscaEnAmbito(id);
			if(mat == null){
				String temp = genTemp();
				addt(temp + " = " + stack() + "[(int) " + apstack() + "]");
				mat = buscaEnInstancia(id, nclase(), temp, idleft + 1);
				if(mat != null)
					RESULT = variableMatriz(mat, dims, idleft + 1);				
			} else
				RESULT = variableMatriz(mat, dims, idleft + 1);
		}
	:}
	| variable:var PUNTO ID:id COR dims:dims CHETE {:
		if(pasada() == 2 && dims != null && var != null){
			
		}
	:}
	| THIS PUNTO ID:id {:
		if(pasada() == 2){
			String temp = genTemp();
			addt(temp + " = " + stack() + "[(int) " + apstack() + "]");
			RESULT = buscaEnInstancia(id, nclase(), temp, idleft + 1);
			if(RESULT != null && RESULT.temp.contains(":")){
				error("Acceso ilegal a la matriz '" + id + "'", idleft + 1);
				RESULT = null;
			}
		}
	:}
	| ID:id {:
		if(pasada() == 2){
			RESULT = buscaEnAmbito(id);
			if(RESULT == null){
				String temp = genTemp();
				addt(temp + " = " + stack() + "[(int) " + apstack() + "]");
				RESULT = buscaEnInstancia(id, nclase(), temp, idleft + 1);
			}
			if(RESULT != null && RESULT.temp.contains(":")){
				error("Acceso ilegal a la matriz '" + id + "'", idleft + 1);
				RESULT = null;
			}
		}
	:}
	| variable:v PUNTO ID:id {:
		if(pasada() == 2){
			if(v != null){
				addt(v.temp + " = " + heap() + "[(int) " + v.temp + "]");
				RESULT = buscaEnInstancia(id, v.tipo, v.temp, idleft + 1);
				if(RESULT != null && RESULT.temp.contains(":")){
					error("Acceso ilegal a la matriz '" + id + "'", idleft + 1);
					RESULT = null;
				}
			}
		}
	:}
	| THIS PUNTO ID:id PAREN vatrs:v TESIS {:
		if(pasada() == 2){
			String temp = genTemp();
			addt(temp + " = " + stack() + "[(int) " + apstack() + "]");
			RESULT = llamarFuncion(nclase(), id, v, temp, idleft + 1);
		}
	:}
	| ID:id PAREN vatrs:v TESIS {:
		if(pasada() == 2){
			String temp = genTemp();
			addt(temp + " = " + stack() + "[(int) " + apstack() + "]");
			RESULT = llamarFuncion(nclase(), id, v, temp, idleft + 1);
		}
	:}
	| variable:var PUNTO ID:id PAREN vatrs:v TESIS {:
		if(pasada() == 2){
			if(v != null && var != null){
				RESULT = llamarFuncion(var.tipo, id, v, var.temp, idleft + 1);
			}
		}
	:} ;

dims::= dims:d CHETE COR valor:v {:
		if(pasada() == 2)
			if(v != null && d != null)
				if(v.temp != null && v.tipo.equals(Variable.tint)){
					if(v.lugar == null){
					} else if(!v.referencia){
						addt(v.temp + " = " + v.lugar + "[(int) " + v.temp + "]");
					} else {
						addt(v.temp + " = " + stack() + "[(int) " + v.temp + "]");
						addt(v.temp + " = " + stack() + "[(int) " + v.temp + "]");
					}
					RESULT = d + ":" + v.temp;
				} else 
					error("Se espera expresión tipo INT", vleft + 1);
	:} 
	| valor:v {:
		if(pasada() == 2)
			if(v != null)
				if(v.temp != null && v.tipo.equals(Variable.tint)){
					if(v.lugar == null){
					} else if(!v.referencia){
						addt(v.temp + " = " + v.lugar + "[(int) " + v.temp + "]");
					} else {
						addt(v.temp + " = " + stack() + "[(int) " + v.temp + "]");
						addt(v.temp + " = " + stack() + "[(int) " + v.temp + "]");
					}
					RESULT = v.temp;
				} else 
					error("Se espera expresión tipo INT", vleft + 1);
	:} ;

cvariable::= variable:v PLUSPLUS {:
		if(pasada() == 2)
			if(v != null)
				if(v.variable && (v.tipo.equals(Variable.tfloat) || v.tipo.equals(Variable.tint))){
					RESULT = cvariable(false, " + ", v, vleft + 1);
				} else
					error("Se esperaba variable numérica", vleft + 1);
	:}
	| variable:v MINUSMINUS {:
		if(pasada() == 2)
			if(v != null)
				if(v.variable && (v.tipo.equals(Variable.tfloat) || v.tipo.equals(Variable.tint)))
					RESULT = cvariable(false, " - ", v, vleft + 1);
				else
					error("Se esperaba variable numérica", vleft + 1);
	:}
	| PLUSPLUS variable:v {:
		if(pasada() == 2)
			if(v != null)
				if(v.variable && (v.tipo.equals(Variable.tfloat) || v.tipo.equals(Variable.tint)))
					RESULT = cvariable(true, " + ", v, vleft + 1);
				else
					error("Se esperaba variable numérica", vleft + 1);
	:}
	| MINUSMINUS variable:v {:
		if(pasada() == 2)
			if(v != null)
				if(v.variable && (v.tipo.equals(Variable.tfloat) || v.tipo.equals(Variable.tint)))
					RESULT = cvariable(true, " - ", v, vleft + 1);
				else
					error("Se esperaba variable numérica", vleft + 1);
	:} ;
	
vatrs::= atrs:l {: if(pasada() == 2) RESULT = l; :}
	| {: if(pasada() == 2) RESULT = new ArrayList<Variable>(); :} ;

atrs::= atrs:l COMA valor:v {:
		if(pasada() == 2){
			RESULT = l;
			if(v != null)
				RESULT.add(v);
		}
	:}
	| valor:v {: 
		if(pasada() == 2) {
			RESULT = new ArrayList<Variable>();
			if(v != null)
				RESULT.add(v);
		}
	:} ;

valor::= valor:v1 MAS valor:v2 {:
		if(pasada() == 2)
			if(v1 != null && v2 != null)
				RESULT = aritmética(v1, v2, " + ", v1left + 1);
	:}
	| valor:v1 MENOS valor:v2 {:
		if(pasada() == 2)
			if(v1 != null && v2 != null)
				RESULT = aritmética(v1, v2, " - ", v1left + 1);
	:}
	| valor:v1 MUL valor:v2 {:
		if(pasada() == 2)
			if(v1 != null && v2 != null)
				RESULT = aritmética(v1, v2, " * ", v1left + 1);
	:}
	| valor:v1 DIV valor:v2{:
		if(pasada() == 2)
			if(v1 != null && v2 != null)
				RESULT = aritmética(v1, v2, " / ", v1left + 1);
	:}
	| valor:v1 MOD valor:v2 {:
		if(pasada() == 2)
			if(v1 != null && v2 != null)
				RESULT = aritmética(v1, v2, " % ", v1left + 1);
	:}
	| MENOS valor:v {:
		if(pasada() == 2)
			if(v != null)
				RESULT = aritmética(new Variable("0", null, v.tipo, false, false), 
						v, " - ", vleft + 1);
	:} %prec NEGATIVO 
	| valor:v1 COMPARA:com valor:v2 {:
		if(pasada() == 2)
			if(v1 != null && v2 != null)
				if(v1.esNumerica() && v2.esNumerica()){
					if(v1.lugar == null){
					} else if(!v1.referencia){
						addt(v1.temp + " = " + v1.lugar + "[(int) " + v1.temp + "]");
					} else {
						addt(v1.temp + " = " + stack() + "[(int) " + v1.temp + "]");
						addt(v1.temp + " = " + stack() + "[(int) " + v1.temp + "]");
					}
					
					if(v2.lugar == null){
					} else if(!v2.referencia){
						addt(v2.temp + " = " + v2.lugar + "[(int) " + v2.temp + "]");
					} else {
						addt(v2.temp + " = " + stack() + "[(int) " + v2.temp + "]");
						addt(v2.temp + " = " + stack() + "[(int) " + v2.temp + "]");
					}
					
					String temp = genTemp();
					String et = genEtiq();
					addt(temp + " = 1");
					com = com.equals("#") ? "==" : com;
					addt("if (" + v1.temp + " " + com + " " + v2.temp +") goto " + et);
					addt(temp + " = 0");
					add(et + ":\n");
					
					RESULT = new Variable(temp, null, Variable.tboolean, false, false);
				} else
					error("Los valores deben ser numéricos", v1left + 1);
	:}
	| valor:v1 OR valor:v2 {:
		if(pasada() == 2)
			if(v1 != null && v2 != null)
				if(v1.temp != null && v2.temp != null)
					RESULT = lógica(v1, v2, Variable.or, v1left + 1);
				else
					error("No se permiten valores nulos", v1left + 1);
	:}
	| valor:v1 AND valor:v2 {:
		if(pasada() == 2)
			if(v1 != null && v2 != null)
				if(v1.temp != null && v2.temp != null)
					RESULT = lógica(v1, v2, Variable.and, v1left + 1);
				else
					error("No se permiten valores nulos", v1left + 1);	
	:}
	| valor:v1 XOR valor:v2 {:
		if(pasada() == 2)
			if(v1 != null && v2 != null)
				if(v1.temp != null && v2.temp != null)
					RESULT = lógica(v1, v2, Variable.xor, v1left + 1);
				else
					error("No se permiten valores nulos", v1left + 1);
	:}
	| NOT valor:v {:
		if(pasada() == 2)
			if(v != null)
				if(v.temp != null && v.tipo.equals(Variable.tboolean)){
					if(v.lugar == null){
					} else if (!v.referencia){
						addt(v.temp + " = " + v.lugar + "[(int) " + v.temp + "]");
					} else{
						addt(v.temp + " = " + stack() + "[(int) " + v.temp + "]");
						addt(v.temp + " = " + stack() + "[(int) " + v.temp + "]");
					}
					String temp = genTemp();
					String et = genEtiq();
					addt(temp + " = 0");
					addtif(v.temp, "1", et);
					addt(temp + " = 1");
					add(et + ":\n");
					RESULT = new Variable(temp, null, Variable.tboolean, false, false);
				} else
					error("Variable debe ser booleana", vleft + 1);
	:} %prec NEGADO 
	| PAREN valor:v TESIS {: if(pasada() == 2) RESULT = v; :}
	| PAREN tipoc:t TESIS valor:v {:
		if(pasada() == 2)
			if(v != null)
				if(v.temp != null && v.esPrimitiva()){
					if(t.equals(Variable.tboolean)){
						String lf = genEtiq();
						addt(v.temp + " = 0");
						addtif(v.temp, "0", lf);
						addt(v.temp + " = 1");
						add(lf + ":\n");
						v.tipo = Variable.tboolean;
						RESULT = v;
					} else if(t.equals(Variable.tint)){
						addt(v.temp + " = (int) " + v.temp);
						v.tipo = Variable.tint;
						RESULT = v;
					} else if(t.equals(Variable.tfloat)){
						v.tipo = Variable.tfloat;
						RESULT = v;
					} else if(t.equals(Variable.tchar)){
						addt(v.temp + " = (int) " + v.temp);
						RESULT = v;
					}
				} else
					error("Se esperaba un valor de tipo primitivo", vleft + 1);
	:}
	| exp:e {: if(pasada() == 2) RESULT = e; :}
	| error {: error(); :} ;
	
exp::= primitivo:v {: if(pasada() == 2) RESULT = v; :}
	| NULL {: if(pasada() == 2) RESULT = new Variable(null, null, null, false, false); :}
	| variable:v {: if(pasada() == 2) RESULT = v; :}
	| cvariable:v {: if(pasada() == 2) RESULT = v; :} ;
	
primitivo::= COMILLAS VSTRING:v COMILLAS {:
		if(pasada() == 2) {
			String temp = tnuevaInstanciaString(v, vleft + 1);
			RESULT = new Variable(temp, heap(), Variable.tString, true, false);
		}
	:}
	| COMILLA VCHAR:v COMILLA {:
		if(pasada() == 2)
			RESULT = new Variable(String.valueOf((int) v.charAt(0)), null, Variable.tchar, false, false);
	:}
	| VINT:v {:
		if(pasada() == 2)
			RESULT = new Variable(v, null, Variable.tint, false, false);
	:}
	| VFLOAT:v {:
		if(pasada() == 2)
			RESULT = new Variable(v, null, Variable.tfloat, false, false);
	:}
	| VBOOLEAN:v {:
		if(pasada() == 2)
			RESULT = new Variable(v.equals("true") ? "1" : "0", null, Variable.tboolean, false, false);
	:};
	
ifelse::= if:i ELSE {:
		if(pasada() == 2)
			if(i != null){
				String ls = genEtiq();
				addt("goto " + ls);
				push(ls);
				add(i + ":\n");
			}
	:} sentencias {:
		if(pasada() == 2)
			if(i != null){
				String ls = pop();
				add(ls + ":\n");
			}
	:}
	| if:i {:
		if(pasada() == 2)
			if(i != null)
				add(i + ":\n");
	:} ;

if::= IF PAREN valor:v TESIS {:
		if(pasada() == 2)
			if(v != null){
				if(v.temp != null && v.tipo.equals(Variable.tboolean)){
					if(v.lugar == null){
					} else if(!v.referencia){
						addt(v.temp + " = " + v.lugar + "[(int) " + v.temp + "]");
					} else {
						addt(v.temp + " = " + stack() + "[(int) " + v.temp + "]");
						addt(v.temp + " = " + stack() + "[(int) " + v.temp + "]");
					}
					String lf = genEtiq();
					addtif(v.temp, "0", lf);
					RESULT = lf;
				} else
					error("Se espera expresión booleana", vleft + 1);
			}
	:} sentencias ;
	
while::= WHILE PAREN {:
		if(pasada() == 2){
			idisplay ++;
			String li = genEtiq();
			push(li);
			add(li + ":\n");
		}
	:} valor:v {:
		if(pasada() == 2)
			if(v != null)
				if(v.temp != null && v.tipo.equals(Variable.tboolean)){
					if(v.lugar == null){
					} else if(!v.referencia){
						addt(v.temp + " = " + v.lugar + "[(int) " + v.temp + "]");
					} else {
						addt(v.temp + " = " + stack() + "[(int) " + v.temp + "]");
						addt(v.temp + " = " + stack() + "[(int) " + v.temp + "]");
					}
					String ls = genEtiq();
					display[idisplay] = ls;
					addtif(v.temp, "0", ls);
				} else
					error("Se espera expresión booleana", vleft + 1);
	:} TESIS sentencias {: 
		if(pasada() == 2){
			String li = pop();
			addt("goto " + li);
			add(display[idisplay] + ":\n");
			idisplay --; 
		}
	:} ;

dowhile::= DO {:
		if(pasada() == 2){
			String li = genEtiq();
			push(li);
			add(li + ":\n");
			String ls = genEtiq();
			display[++ idisplay] = ls;
		}
	:} sentencias WHILE PAREN valor:v {:
		if(pasada() == 2){
			if(v != null){
				if(v.temp != null & v.tipo.equals(Variable.tboolean)){
					if(v.lugar == null){
					} else if(!v.referencia){
						addt(v.temp + " = " + v.lugar + "[(int) " + v.temp + "]");
					} else {
						addt(v.temp + " = " + stack() + "[(int) " + v.temp + "]");
						addt(v.temp + " = " + stack() + "[(int) " + v.temp + "]");
					}
					String li = pop();
					String ls = display[idisplay --];
					addtif(v.temp, "1", li);
					add(ls + ":\n");
				} else
					error("Se espera expresión booleana", vleft + 1);
			}
		}
	:} TESIS ;

for::= FOR PAREN {:
		if(pasada() == 2)
			funcion(funcion().agregarAmbito());
	:} __asigna PUNTOCOMA {:
		if(pasada() == 2){
			String li = genEtiq();
			push(li);
			add(li + ":\n");
		}
	:} valor:v {:
		if(pasada() == 2)
			if(v != null)
				if(v.temp != null && v.tipo.equals(Variable.tboolean)){
					if(v.lugar == null){
					} else if (!v.referencia){
						addt(v.temp + " = " + v.lugar + "[(int) " + v.temp + "]");
					} else{
						addt(v.temp + " = " + stack() + "[(int) " + v.temp + "]");
						addt(v.temp + " = " + stack() + "[(int) " + v.temp + "]");
					}
					
					String ls = genEtiq();
					display[++ idisplay] = ls;
					addtif(v.temp, "0", ls);
				} else
					error("Se esperaba expresión booleana", vleft + 1);
	:} PUNTOCOMA _asigna TESIS sentencias {:
		if(pasada() == 2){
			String li = pop();
			addt("goto " + li);
			funcion(funcion().ambitoSuperior());
			add(display[idisplay --] + ":\n");
		}
	:};

__asigna::= _asigna
	| declasig ;

_asigna::= asigna 
	| ;

switchcase::= SWITCH PAREN valor:v {:
		if(pasada() == 2)
			if(v != null)
				if(v.variable && (v.tipo.equals(Variable.tboolean)
						|| v.tipo.equals(Variable.tfloat) || v.tipo.equals(Variable.tint))){
					if(v.lugar == null){
					} else if(!v.referencia){
						addt(v.temp + " = " + v.lugar + "[(int) " + v.temp + "]");
					} else {
						addt(v.temp + " = " + stack() + "[(int) " + v.temp + "]");
						addt(v.temp + " = " + stack() + "[(int) " + v.temp + "]");
					}
					String et = genEtiq();
					push(et);
					addt("goto " + et);
					String ls = genEtiq();
					display[++ idisplay] = ls;
					cases[++ icases] = v;
				} else
					error("Tipo inválido en SWITCH CASE");
	:} TESIS LLA cases:c default:d {: 
		if(pasada() == 2 && c != null && d != null){
			add(pop() + ":\n");
			add(c + d);
			add(display[idisplay --] + ":\n");
			icases --;
		}
	:} VES ;

cases::= cases:cs case:c {: if(pasada() == 2) if(cs != null && c != null) RESULT = cs + c; :}
	| case:c {: if(pasada() == 2) RESULT = c; :} ;
	
case::= CASE {:
		if(pasada() == 2)
			funcion(funcion().agregarAmbito());
	:} valor:v DOSPUNTOS {:
		if(v != null)
			if(v.lugar == null){
				Variable sw = cases[icases];
				if(sw.tipo.equals(v.tipo)){
					String et = genEtiq();
					RESULT = "\tif(" + sw.temp + " == " + v.temp + ") goto " + et + "\n";
					add(et + ":\n");
				} else
					error("Tipo colocado en caso no es válido", vleft + 1);
			} else 
				error("El valor debe ser constante", vleft + 1);
	:} sents {:
		if(pasada() == 2)
			funcion(funcion().ambitoSuperior());
	:};

default::= DEFAULT DOSPUNTOS {:
		if(pasada() == 2){
			String et = genEtiq();
			RESULT = "\tgoto " + et + "\n";
			add(et + ":\n");
		}
	:} sents 
	| {: if(pasada() == 2) RESULT = ""; :};





//// arreglar si manda null como atributo de clase 
///
