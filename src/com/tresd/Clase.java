package com.tresd;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Clase {

	private static final String cabhtml = "<!DOCTYPE html>\n" +
			"<META HTTP-EQUIV=\"Content-Type\" content=\"text/html; charset=utf-8\"/>\n" +
			"<html>\n" +
			"	<head>\n" +
			"		<title>Reporte de errores</title>\n" +
			"	</head>\n" +
			"	<body bgcolor=\"#EFF8FB\" LEFTMARGIN=90>\n" +
			"		<font face=\"ubuntu\">\n" +
			"			<h2>Errores en los archivos revisados</h2>\n" +
			"			<table border=\"1\" cellpadding = \"5\">\n"+
			"				<tr><td><b>Descripción de error</b></td><td><b>Archivo</b></td><td><b>Línea</b></td></tr>\n";
	private static final String foothtml = "			</table>\n" +
			"		</font>\n" +
			"	</body>\n" +
			"</html>";
	private static final String erroresFile = "Errores.html";
	protected static int cantTemps = 0;
	private static final int tempPorLinea = 12;
	protected static final char escape = '~';
	public static final String Stack = "stack";
	public static final String Heap = "heap";
	public static final String apStack = "apstack";
	public static final String apHeap = "apheap";
	protected static int cTemp = 0;
	protected static int cEtiq = 0;
	protected static String genTemp(){
		cTemp ++;
		if(cantTemps < cTemp)
			cantTemps = cTemp;
		return "t" + cTemp; 
	}
	protected static String genEtiq(){ return "l" + ++ cEtiq; }
	protected static void resetcTemp(){ cTemp = 0; }
	public static final int tamStack = 1000;
	public static final int tamHeap = 10000;
	private static final String apuntadores = "int " + apStack + ", " + apHeap + ";\n" + 
			"float " + Stack + "[" + tamStack + "];\n" +
			"float " + Heap + "[" + tamHeap + "];\n";

	// punto de vista clase individual
	private String nombre;
	private ArrayList<Clase> clasesDisponibles;
	private ArrayList<Variable> atributos;
	private ArrayList<Funcion> funciones;
	private Clase extiende;
	int tamaño = 1;
	int posición;
	
	// punto de vista de lista
	private ArrayList<Clase> clases;
	private String errores;
	private String directory;
	private String salida = "";
	private String archivo;
	private Clase clase;
	protected int linea;
	
	private final static String salida3D = "3D.c";
	private final static String salida3DH = "3D.h";
	private final static String extJava = ".java";
	
	/**
	 * Inicializa un elemento clase
	 * @param nombre nombre de la clase
	 */
	public Clase(String nombre){
		this.nombre = nombre;
	}
	
	public Clase(){
		clases = new ArrayList<Clase>();
	}
	// Sólo acepta instancias de Clase
	@Override
	public boolean equals(Object o){
		if(o == null || o.getClass() != Clase.class)
			return false;
		return nombre.equals(o.toString());
	}
	// regresa el nombre de la clase
	@Override
	public String toString(){
		return nombre;
	}
	/**
	 * Agrega errores de cualquier tipo al archivo analizandose
	 * @param err Descripción del error
	 * @param archivo Archivo donde se encuentra el error
	 * @param linea Linea donde está el error
	 */
	protected void error(String err, String archivo, int linea){
		if(errores == null)
			errores = "";
		errores += "				<tr><td>" + err + "</td><td>" + archivo + "</td><td>" + linea + "</td></tr>\n";
	}
	/**
	 * Escribe el error. Debe estar actualizado el lugar
	 * @param err
	 */
	protected void error(String err){
		error(err, archivo, linea);
	}
	/**
	 * Devuelde los errores encontrados
	 * @param mostrarErrs booleano que indica si se bebe 
	 * abrir el explorador con los errores
	 */
	public void errs(boolean mostrarErrs){
		// se supone q es la instancia principal
		for(Clase clase : clases){
			System.out.print(clase + ":\nImportadas: ");
			for(Clase imp : clase.clasesDisponibles)
				System.out.print(imp + ", ");
			System.out.print("\nextiende de " + clase.extiende + "\nAtributos: ");
			if(clase.atributos != null)
				for(Variable var : clase.atributos)
					System.out.print(var + ", ");
			System.out.print("\nFunciones: ");
			if(clase.funciones != null)
				for(Funcion fun : clase.funciones)
					System.out.print(fun + ", ");
			System.out.println("\n");
		}
		if(mostrarErrs){
			generarArchivoErrores();
			try {
				Runtime.getRuntime().exec("google-chrome " + directory + "/" + erroresFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * Inicia la generación de código intermedio
	 * @param file archivo de entrada a parsear
	 */
	public void generarSalida(File file){
		if(directory == null)
			directory = file.getParentFile().getAbsolutePath();
		escribir(false);
		generar3D(file);
		addCabecera();
		crearMain();
		for(Clase clase : clases){
			this.clase = clase;
			this.archivo = clase.nombre + extJava;
			file = new File(directory + "/" + clase.nombre + extJava);
			try {
				new ParserCup(file, this, 2).parse();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		declararTemporales();
		agregarException();
	}
	/**
	 * Retorna el nombre del archivo creado si no hay errores, si no, 
	 * retorna null
	 * @return retorna null o el nombre del archivo generado
	 */
	public String nombreArchivo3D(){
		if(errores == null)
			return directory + salida3D;
		else
			return null;
	}
	/**
	 * Genera el código de tres direcciones en la carpeta del archivo
	 * el archivo debe ser válido
	 * @param file
	 * @return la instancia de la clase generada
	 */
	private Clase generar3D(File file){
		try {
			Clase ctmp = clase;
			String tmp = archivo;
			clase = new Clase(file.getName().split("\\.")[0]);
			archivo = file.getName();
			clase.importarClase(clase);
			clases.add(clase);
			new ParserCup(file, this, 1).parse();
			Clase otmp = clase;
			clase = ctmp;
			archivo = tmp;
			return otmp;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * Agrega una clase si no está creada y agrega la clase usuaria
	 * de donde se genera la petición
	 * @param usuaria clase donde se genera la petición
	 * @param linea linea donde se hace el import 
	 */
	protected void addClase(String simportada, int linea){
		this.linea = linea;
		Clase importada = getClase(simportada);
		if(importada == null){
			File fclase = new File(directory + "/" + simportada + extJava);
			if(fclase.isFile() && fclase.exists())
				importada = generar3D(fclase);
			else
				error("Archivo de clase '" + simportada + "' no encontrado");
		}
		if(!clase.importarClase(importada))
			error("Ya se había importado la clase '" + simportada + "'");
	}
	/**
	 * Devuelve clase con nombre recibido
	 * @param nombre nombre de la clase q se busca
	 * @return Clase o null
	 */
	protected Clase getClase(String nombre){
		int index = clases.indexOf(new Clase(nombre));
		if(index > -1)
			return clases.get(index);
		else 
			return null;
	}	
	/**
	 * Agrega texto a la cadena de tres direcciones generada
	 * @param txt
	 */
	protected void add(String txt){
		salida = txt;
		escribir(true);
	}
	/**
	 * Revisa que la clase tenga el mismo nombre que el archivo
	 * @param nombre el nombre encontrado
	 * @param linea linea 
	 */
	protected void revisaNombre(String nombre, int linea){
		this.linea = linea;
		// aquí nunca debería dar null pointer
		if(!clase.toString().equals(nombre))
			error("El archivo no tiene el mismo nombre que la clase '" + nombre + "'");
	}
	/**
	 * Busca la clase para indicar su clase padre
	 * @param extiend la clase padre
	 * @param liena linea donde aparece la instrucción
	 */
	protected void claseExtiende(String extiend, int liena){
		this.linea = liena; 
		Clase padre = getClase(extiend);
		if(padre != null && clase.clasesDisponibles.contains(padre))
			clase.extiende(padre);
		else 
			error("Clase '" + extiend + "' no fue importada");
	}
	/**
	 * Agrega atributo a la clase indicada
	 * @param clase
	 * @param acceso
	 * @param t
	 * @param def
	 * @param linea
	 */
	protected void agregaAtributo(char acceso, String t, String def, int linea){
		this.linea = linea;
		if(Variable.esPrimitiva(t) || Variable.tString.equals(t) 
				|| clase.clasesDisponibles.contains(new Clase(t))){
			if(!clase.addAtributo(acceso, t, def))
				error("Atributo '" + def.split("-")[0] + "' repetido");
		} else
			error("Se desconoce tipo '" + t + "'");
	}
	/**
	 * Agrega función a la clase, sólo su definición
	 * @param acceso tipo de visibilidad
	 * @param tipo tipo de retorno. Para constructores, %constructor%
	 * @param id nombre de la funcion 
	 * @param params definición de parametros
	 * @param linea linea por posible error
	 */
	protected void addFuncion(char acceso, String tipo, String id, String params, int linea){
		this.linea = linea;
		Funcion nueva = new Funcion(id + (params.length() > 0 ? "-" : "") +  params, tipo, acceso);
		if(tipo.equals("%constructor%") && !id.equals(clase.nombre))
			error("La función '" + id + "' no es un constructor válido");
		else {
			if(clase.getFuncion(nueva) == null){
				boolean correcto = true;
				if(params.length() > 0)
					for(String var : params.split(":")){
						var = var.substring(1);
						if(!Variable.esPrimitiva(var) && !var.equals(Variable.tString) &&
								!clase.clasesDisponibles.contains(var)){
							error("La clase '" + var + "' no está disponible");
							correcto = false;
							break;
						}
					}
			if(correcto)
				clase.addFuncion(nueva);
			} else
				error("Función '" + id + "' ya definida");
		}
	}
	
	/**
	 * Genera la salida en tres direcciones al archivo
	 * en la misma carpeta
	 */
	private void escribir(boolean truncar){
		FileWriter fichero = null;
		PrintWriter pw;
		try {
			fichero = new FileWriter(directory + salida3D, truncar);
			pw = new PrintWriter(fichero);

		pw.print(salida);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null!= fichero)
					fichero.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	/**
	 * Genera la salida en tres direcciones al archivo
	 * en la misma carpeta
	 */
	private void escribirCab(String salida, boolean truncar){
		FileWriter fichero = null;
		PrintWriter pw;
		try {
			fichero = new FileWriter(directory + salida3DH, truncar);
			pw = new PrintWriter(fichero);

		pw.print(salida);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null!= fichero)
					fichero.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	/**
	 * Busca y retorna una Variable buscada en la clase tipo 'clasePoseedora', si no se encuentra, 
	 * retorna null. Si se encuentra, se revisa la visibilidad del atributo en clasePoseedora
	 * respecto claseDeOrigen 
	 * @param id Etiqueta nombre del atributo
	 * @param sclasePoseedora clase donde se supone debería estar el atributo
	 * @param linea linea por posible error
	 * @return Variable con lo requerido para hacer referencia al atributo de la clase o null
	 */
	public Variable buscarEnClase(String id, String sclasePoseedora, int linea) {
		this.linea = linea;
		String sclaseDeOrigen = clase.nombre;
		Clase clasePoseedora = getClase(sclasePoseedora);	// nunca debería ser null
		Variable atr = clasePoseedora.getAtributo(id);
		if(atr != null){
			if(sclaseDeOrigen.equals(sclasePoseedora) || !atr.esPrivada()){
				return atr;
			} else
				error("Atributo '" + id + "' de clase '" + sclasePoseedora + "' no es público");
		} else if(clasePoseedora.extiende != null && clasePoseedora.extiende.atributos != null){
			sclaseDeOrigen = clasePoseedora.nombre;
			clasePoseedora = clasePoseedora.extiende;
			sclasePoseedora = clasePoseedora.nombre;
			atr = clasePoseedora.getAtributo(id);
			if(atr != null){
				if(sclaseDeOrigen.equals(sclasePoseedora) || !atr.esPrivada()){
					return atr;
				} else
					error("Atributo '" + id + "' de clase '" + sclasePoseedora + "' no es público");
			} else
				error("Atributo '" + id + "' no es parte de la clase '" + sclasePoseedora + "'");
		} else
			error("Atributo '" + id + "' no es parte de la clase '" + sclasePoseedora + "'");
		return null;
	}
	
	/** acá empiezan los métodos para las unidades **/
	
	/**
	 * Retorna la posicón relativa de un atributo
	 * @param id nombre del atributo
	 * @return posición del atributo o -1 si no se encuentra
	 */
	private Variable getAtributo(String id){
		int index =  atributos == null ? -1 : atributos.indexOf(new Variable("saber", id));
		if(index >= 0){
			return atributos.get(index);
		}
		return null;
	}
	/**
	 * Debería obtener sus atributos
	 * Para saber de donde empiezan los atributos de la clase
	 * se suma a 0, el tamaño de la clase padre
	 * @param padre
	 */
	private void extiende(Clase padre){
		this.extiende = padre; 
		tamaño = padre.tamaño;
	}
	
	/**
	 * Agrega clase a la lista de clases que pueden acceder a la clase 
	 * @param cusuaria clase que puede acceder a atributos y funciones
	 * siempre y cuando sean publicos
	 */
	private boolean importarClase(Clase importada){
		if(clasesDisponibles == null)
			clasesDisponibles = new ArrayList<Clase>();
		if(!clasesDisponibles.contains(importada)){
			clasesDisponibles.add(importada);
			return true;
		} 
		return false;
	}
	
	/**
	 * Agrega atributo a la clase actual
	 * @param acceso acceso
	 * @param tipo tipo de acceso
	 * @param def definición de atributo
	 * @return si se pudo agregar
	 */
	private boolean addAtributo(char acceso, String tipo, String def){
		Variable var = new Variable(acceso, tipo, def);
		if(atributos != null && atributos.contains(var))
			return false;
		if(atributos == null)
			atributos = new ArrayList<Variable>();			
		var.setPos(tamaño);
		tamaño += var.getTamaño();
		atributos.add(var);
		return true;
	}
	/**
	 * Retorna la funcion con la misma definición.
	 * @param f funcion a buscar 
	 * @return la funcion dentro de la lista
	 */
	private Funcion getFuncion(Funcion f){
		if(funciones == null) 
			return null;
		int index = funciones.indexOf(f);
		if(index > -1)
			return funciones.get(index);
		else 
			return null;
	}
	/**
	 * Agrega la funcion. Crea la lista si no ha sido instanciada
	 * @param f la funcion a agregar
	 */
	private void addFuncion(Funcion f){
		if(funciones == null)
			funciones = new ArrayList<Funcion>();
		funciones.add(f);
	}
	/**
	 * Busca la definción de la clase en base a la clase actual parseada
	 * Retorna su definición si existe y es public o protected
	 * @param sclase clase del método
	 * @param metodo método
	 * @param pars lista de parámetros, debe coincidir
	 * @return string de definición de función o null
	 */
	protected String definicionDeClaseSiPuede(String sclase, String metodo,
			ArrayList<Variable> pars, int linea) {
		this.linea = linea;
		String def = metodo + (pars.size() > 0 ? "-" : "");
		String sdef = "(";
		for(int i = 0; i < pars.size(); i++){
			def += (i > 0 ? ":" : "") + " " + pars.get(i).tipo;
			if(i > 0)
				sdef += ", ";
			sdef += pars.get(i).tipo;						
		}
		sdef += ")";
		Clase clasep = getClase(sclase);
		if(clasep != null){
			if(clasep.funciones != null){
				int index = clasep.funciones.indexOf(new Funcion(def, "s", 's'));
				if(index >= 0){ // se encontró la función
					Funcion encontrada = clasep.funciones.get(index);
					if(sclase.equals(this.clase.nombre) || !encontrada.esPrivada())
						return clasep.nombre + "_" + encontrada.getRetorno() + "-" + encontrada.toString();
					else 
						error("No se permite el acceso a la función '" + metodo + "' de clase '" + sclase + "'");
				} else if(clasep.extiende != null && clasep.extiende.funciones != null){
					clasep = clasep.extiende;
					index = clasep.funciones.indexOf(new Funcion(def, "s", 's'));
					if(index >= 0){ // se encontró función en clase padre
						Funcion encontrada = clasep.funciones.get(index);
						if(sclase.equals(clasep.nombre) || !encontrada.esPrivada())
							return clasep.nombre + "_" + encontrada.getRetorno() + "-" + encontrada.toString();
						else
							error("No se permite el acceso a la función '" + metodo + "' de la clase padre '" + clasep.nombre + "'");
					} else
						error("No existe la función '" + metodo + sdef + "' en clase padre '" + clasep.nombre + "'");
				} else
					error("No existe la función '" + metodo + sdef + "' en la clase '" + sclase + "'");
			} else  
				error("No existe la función '" + metodo + sdef + "' en la clase '" + sclase + "'");
		} else 
			error("Tipo '" + sclase + "' no válido o no existe en el contexto actual");
		return null;
	}
	
	/**
	 * Retorna el tamaño de la clase si existe, si no, 
	 * retorna -1
	 * @param sclase clase a buscar
	 * @return tamañon de clase o 0
	 */
	protected int tamClase(String sclase) {
		Clase clase = getClase(sclase);
		if(clase == null)
			return -1;
		return clase.tamaño;
	}
	
	/**
	 * Declara los temporales a usar en C
	 */
	private void declararTemporales(){
		String vars = "";
		int mod = 0;
		for(int i = 1; i <= cantTemps; i++){
			mod = i % tempPorLinea;
			if(mod == 1)
				vars += "\nfloat t" + i;
			else if(mod == 0)
				vars += ", t" + i + ";";
			else
				vars += ", t" + i;
		}
		if(mod != 0)
			vars += ";";
		vars += "\n";
		
		String funcs = "";
		for(Clase clase : clases)
			if(clase.funciones != null)
				for(Funcion func : clase.funciones){
					String vfunc [] = func.toString().split("-");
					funcs += "void " + clase.nombre + "_" + (func.getRetorno().equals(Funcion.rconstructor) ?
							Funcion.rvoid : func.getRetorno()) + "_";
					funcs += vfunc[0];
					if(vfunc.length > 1){
						String [] vatrs = vfunc[1].split(":");
						for(String atr : vatrs)
							funcs += "_" + atr.substring(1);
					}
					funcs += "();\n";
				}
		funcs += "int main(void);\nvoid exception();\n";
		
		escribirCab("#ifndef entradas3D_H\n", false);
		escribirCab("#define entradas3D_H\n\n", true);
		escribirCab(apuntadores, true);
		escribirCab(vars, true);
		escribirCab("\n" + funcs + "\n", true);
		escribirCab("#endif", true);
	}

	private void crearMain(){
		String funPrincipal = "";
		int tamInstancia = 0;
		for(Clase clase : clases){
			if(clase.funciones != null &&
					clase.funciones.contains(new Funcion("main", Funcion.rvoid, Variable.vpublic))){
				funPrincipal += clase.toString() + "_void_main";
				tamInstancia = clase.tamaño;
			}
		}
		this.linea = 0;
		if(!funPrincipal.equals("")){
			add("\nint main(void){\n" +
					"\t" + apStack + " = 0;\n" +
					"\t" + apHeap + " = " + tamInstancia + ";\n" + 
					"\t" + funPrincipal + "();\n" + 
					"}\n");
		} else
			error("No existe función principal");
	}
	
	private void addCabecera(){
		String [] dir = directory.split("/");
		add("#include <stdio.h>\n");
		add("#include \"" + dir[dir.length - 1] + salida3DH + "\"\n");
	}
	
	private void agregarException(){
		add("\nvoid exception() { \n" + 
				"\tprintf(\"%c\", (char)((int) 69));\n" +
				"\tprintf(\"%c\", (char)((int) 114));\n" +
				"\tprintf(\"%c\", (char)((int) 114));\n" +
				"\tprintf(\"%c\", (char)((int) 111));\n" +
				"\tprintf(\"%c\", (char)((int) 114));\n" +
				"\tprintf(\"%c\", (char)((int) 32));\n" +
				"\tprintf(\"%c\", (char)((int) 101));\n" +
				"\tprintf(\"%c\", (char)((int) 110));\n" +
				"\tprintf(\"%c\", (char)((int) 32));\n" +
				"\tprintf(\"%c\", (char)((int) 116));\n" +
				"\tprintf(\"%c\", (char)((int) 105));\n" +
				"\tprintf(\"%c\", (char)((int) 101));\n" +
				"\tprintf(\"%c\", (char)((int) 109));\n" +
				"\tprintf(\"%c\", (char)((int) 112));\n" +
				"\tprintf(\"%c\", (char)((int) 111));\n" +
				"\tprintf(\"%c\", (char)((int) 32));\n" +
				"\tprintf(\"%c\", (char)((int) 100));\n" +
				"\tprintf(\"%c\", (char)((int) 101));\n" +
				"\tprintf(\"%c\", (char)((int) 32));\n" +
				"\tprintf(\"%c\", (char)((int) 101));\n" +
				"\tprintf(\"%c\", (char)((int) 106));\n" +
				"\tprintf(\"%c\", (char)((int) 101));\n" +
				"\tprintf(\"%c\", (char)((int) 99));\n" +
				"\tprintf(\"%c\", (char)((int) 117));\n" +
				"\tprintf(\"%c\", (char)((int) 99));\n" +
				"\tprintf(\"%c\", (char)((int) 105));\n" +
				"\tprintf(\"%c\", (char)((int) 111));\n" +
				"\tprintf(\"%c\", (char)((int) 110));\n" +
				"\tprintf(\"%c\", (char)((int) 33));\n" +
				"\tprintf(\"%c\", (char)((int) 32));\n" +
				"\tprintf(\"%c\", (char)((int) 68));\n" +
				"\tprintf(\"%c\", (char)((int) 105));\n" +
				"\tprintf(\"%c\", (char)((int) 109));\n" +
				"\tprintf(\"%c\", (char)((int) 101));\n" +
				"\tprintf(\"%c\", (char)((int) 110));\n" +
				"\tprintf(\"%c\", (char)((int) 115));\n" +
				"\tprintf(\"%c\", (char)((int) 105));\n" +
				"\tprintf(\"%c\", (char)((int) 111));\n" +
				"\tprintf(\"%c\", (char)((int) 110));\n" +
				"\tprintf(\"%c\", (char)((int) 101));\n" +
				"\tprintf(\"%c\", (char)((int) 115));\n" +
				"\tprintf(\"%c\", (char)((int) 32));\n" +
				"\tprintf(\"%c\", (char)((int) 102));\n" +
				"\tprintf(\"%c\", (char)((int) 117));\n" +
				"\tprintf(\"%c\", (char)((int) 101));\n" +
				"\tprintf(\"%c\", (char)((int) 114));\n" +
				"\tprintf(\"%c\", (char)((int) 97));\n" +
				"\tprintf(\"%c\", (char)((int) 32));\n" +
				"\tprintf(\"%c\", (char)((int) 100));\n" +
				"\tprintf(\"%c\", (char)((int) 101));\n" +
				"\tprintf(\"%c\", (char)((int) 32));\n" +
				"\tprintf(\"%c\", (char)((int) 114));\n" +
				"\tprintf(\"%c\", (char)((int) 97));\n" +
				"\tprintf(\"%c\", (char)((int) 110));\n" +
				"\tprintf(\"%c\", (char)((int) 103));\n" +
				"\tprintf(\"%c\", (char)((int) 111));\n" +
				"\tprintf(\"%c\", (char)((int) 46));\n" +
				"\tprintf(\"%c\", (char)((int) 10));\n" +
				"}");
	}
	/**
	 * Genera el archivo html con los errores encontrados en errores
	 */
	private void generarArchivoErrores(){
		FileWriter fichero = null;
		PrintWriter pw;
		try {
			fichero = new FileWriter(directory + "/" + erroresFile);
			pw = new PrintWriter(fichero);

		pw.print(cabhtml + errores + foothtml);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null!= fichero)
					fichero.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
}