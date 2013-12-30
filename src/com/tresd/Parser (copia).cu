package com.tresd;

import java_cup.runtime.Symbol;
import java.io.File;

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
	
	private Clase clase (){ return parser.clase; }
	private String nclase(){ return parser.nclase; }
	private int pasada(){ return parser.pasada; }
	private Funcion funcion(){ return parser.funcion; }
	private void funcion(Funcion f) { parser.funcion = f; }
	private void add(String txt) { clase().add(txt); }
	private void addt(String txt) { add("\t" + txt + "\n"); }
	private String genTemp(){ return Clase.genTemp(); }
	private String genEtiq(){ return Clase.genEtiq(); }
	private void resetcTemp() { Clase.resetcTemp(); }
	private String apheap(){ return Clase.apHeap; }
	private String apstack(){ return Clase.apStack; }
	private String heap(){ return Clase.Heap; }
	private String stack(){ return Clase.Stack; }
	private String escape(){ return "-2.71"; }

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
		IF, ELSE, WHILE, DO, FOR, SWITCH, CASE, BREAK, CLASS, NEW, IMPORT, 
		PUBLIC, PROTECTED, PRIVATE, RETURN, PRINT, VFLOAT, VINT, ID, EXTENDS, VOID,
		APUNTADOR, CHAR, BOOLEAN, FLOAT, VBOOLEAN, COMPARA, PLUSPLUS, MINUSMINUS, 
		DEFAULT, NULL, THIS ;
		
terminal NEGATIVO ; 

non terminal S0, L, imports, _import, defclase, _extends, sentsc, sentc,
		atributo, atrasigna, funcion, declasig,
		bloque, sents, sent, lclase, _lmatriz, tupla, lfuncion, puntocoma,
		asigna, sentencias, ifelse, else, while, dowhile, for, _asigna, matriz,
		matriz_, switchcase, cases, case, default, id, idvalor, func, tipoc, ves ; 
non terminal Integer valorn ;
non terminal String lmatriz, decparam, decparams, apunt, _decparams, tipo ;
non terminal Character acceso ;
non terminal Valor val, valor, exp, decl ;

precedence left ELSE ;
precedence left OR ;
precedence left XOR ;
precedence left AND ;
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

ves::= VES | {: RESULT = "ola q ase"; :} ;

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
	
sentc::= atributo
	| funcion ;
	
atributo::= acceso:a tipo:t ID:id {:
		if(pasada() == 1)
			clase().agregaAtributo(a, t, id, idleft + 1);
	:} atrasigna puntocoma:p {: if(p != null) error("Se esperaba ';'", pleft + 1); :} 
	| acceso:a tipo:t lmatriz:l CHETE atrasigna {:
		if(pasada() == 1 && l != null) 
			clase().agregaAtributo(a, t, l, lleft + 1);
	:} puntocoma:p {: if(p != null) error("Se esperaba ';'", pleft + 1); :}
	| error PUNTOCOMA {: error(); :};

puntocoma::= PUNTOCOMA | {: RESULT = "ola q ase"; :} ;
	
lmatriz::= lmatriz:l COMA valorn:v {: 
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

valorn::= VINT:i {: if(pasada() == 1) RESULT = Integer.valueOf(i); :} 
	| valorn:v1 MAS valorn:v2 {: if(pasada() == 1) RESULT = v1 + v2; :}
	| valorn:v1 MENOS valorn:v2 {: if(pasada() == 1) RESULT = v1 - v2; :}
	| valorn:v1 MUL valorn:v2 {: if(pasada() == 1) RESULT = v1 * v2; :}
	| valorn:v1 DIV valorn:v2 {: if(pasada() == 1) RESULT = v1 / v2; :}
	| valorn:v1 MOD valorn:v2 {: if(pasada() == 1) RESULT = v1 % v2; :}
	| PAREN valorn:v TESIS {: if(pasada() == 1) RESULT = v; :}
	| MENOS valorn:v {: if(pasada() == 1) RESULT = - v; :} %prec NEGATIVO 
	| error {: RESULT = 0; :} ;

tipo::= INT {: if(pasada() == 1 || pasada() == 2) RESULT = Variable.tint; :}
	| STRING {: if(pasada() == 1 || pasada() == 2) RESULT = Variable.tString; :}
	| CHAR {: if(pasada() == 1 || pasada() == 2) RESULT = Variable.tchar; :}
	| BOOLEAN {: if(pasada() == 1 || pasada() == 2) RESULT = Variable.tboolean; :}
	| FLOAT {: if(pasada() == 1 || pasada() == 2) RESULT = Variable.tfloat; :}
	| ID:id {: if(pasada() == 1 || pasada() == 2) RESULT = id; :} ;

tipoc::= INT {: if(pasada() == 1) RESULT = Variable.tint; :}
	| STRING {: if(pasada() == 1) RESULT = Variable.tString; :}
	| CHAR {: if(pasada() == 1) RESULT = Variable.tchar; :}
	| BOOLEAN {: if(pasada() == 1) RESULT = Variable.tboolean; :}
	| FLOAT {: if(pasada() == 1) RESULT = Variable.tfloat; :} ;

atrasigna::= ASIGNA valor 
	| ;
	
funcion::= acceso:a tipo:t ID:i PAREN {:
		if(pasada() == 2) {
			funcion(new Funcion(clase()));
			add("\n" + t + "_" + i);
		}
	:} _decparams:p {:
		if(pasada() == 1)
			clase().addFuncion(a, t, i, p, ileft + 1);
	:} TESIS bloque {: if(pasada() == 2) { add("}\n"); resetcTemp(); } :}
	| acceso:a VOID:t ID:i PAREN {:
		if(pasada() == 2) {
			funcion(new Funcion(clase()));
			add("\n" + t + "_" + i);
		}
	:} _decparams:p {:
		if(pasada() == 1)
			clase().addFuncion(a, t, i, p, ileft + 1);
	:} TESIS bloque {: if(pasada() == 2) { add("}\n"); resetcTemp(); } :} 
	| acceso:a ID:i PAREN {:
		if(pasada() == 2) {
			funcion(new Funcion(clase()));
			add("\n" + "void_" + i);
		}
	:} _decparams:p {:
		if(pasada() == 1)
			clase().addFuncion(a, "%constructor%", i, p, ileft + 1);
	:} TESIS bloque {: if(pasada() == 2) { add("}\n"); resetcTemp(); } :} 
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

bloque::= LLA sents VES ;

sentencias::= sent | bloque ;
	
sents::= sents sent 
	| ;

declasig::= decl:id ASIGNA valor:v {: 
		if(pasada() == 2) {
			if(id != null && v != null) {
				if(id.tipo.equals(v.tipo)) {
					if(id.tipo.equals(Variable.tString)){
						if(v.primitivo){ // se copia cada posición del string
							int i = 0;
							String tem = id.temp;
							String t = genTemp();
							for(char c : v.texto.toCharArray()) {
								addt(t + " = " + tem + " + " + i);
								addt(stack() + "[" + t + "] = " + (int) c);
								if(i == 256) 
									break;
								i ++;
							}
							addt(t + " = " + tem + " + " + i);
							addt(stack() + "[" + t + "] = " + escape());
						}
					}
				} else
					error("Los operandos son tipos diferentes", idleft + 1);
			}
		}
	:}
	| decl ;

decl::= tipo:t ID:id {: // retorna texto de la forma stack[tn] con su tipo
		if(pasada() == 2) {
			if(funcion().addVariable(t, id, idleft + 1)) {
				int pos = funcion().getPosicionStackVariable(id);
				if(pos >= 0) {
					String t1 = genTemp();
					addt(t1 + " = " + apstack() + " + " + pos);
					RESULT = new Valor(stack() + "[" + t1 + "]", t);
				}
			}
		}
	:}
	| matriz CHETE ;

sent::= declasig PUNTOCOMA
	| asigna PUNTOCOMA 
	| id PAREN TESIS PUNTOCOMA
	| func TESIS PUNTOCOMA
	| ifelse 
	| while 
	| dowhile PUNTOCOMA 
	| for 
	| switchcase 
	| BREAK PUNTOCOMA 
	| RETURN valor PUNTOCOMA
	| PRINT PAREN val TESIS PUNTOCOMA 
	| error PUNTOCOMA {: error(); :} ; 

func::= func COMA valor 
	| id PAREN valor ;

matriz::= matriz COMA valor 
	| tipo ID COR valor ;

id::= ID // retorna un string tipo 'stack[tn]'
	| ID PUNTO ID 
	| THIS PUNTO ID ;
	
idvalor::= ID:id {: // retorna un string tipo tn
		if(pasada() == 2) {
			int pos = funcion().getPosicionStackVariable(id);
			if(pos >= 0){
				String t = genTemp();
			} else {
				pos = clase().getPosicionHeapAtributo(id, idleft + 1);
				if(pos >= 0){
				
				}
			}
		}
	:} 
	| THIS PUNTO ID PUNTO ID {::}
	| ID:ins PUNTO ID:id {::}
	| THIS PUNTO ID{::} ;
	
asigna::= id ASIGNA valor
	| id MASIGUAL valor 
	| id MENOSIGUAL valor
	| id MULIGUAL valor 
	| id MODIGUAL valor 
	| id PLUSPLUS
	| PLUSPLUS id
	| id MINUSMINUS
	| MINUSMINUS id
	| matriz_ CHETE ASIGNA valor
	| matriz_ CHETE MASIGUAL valor 
	| matriz_ CHETE MENOSIGUAL valor
	| matriz_ CHETE MULIGUAL valor 
	| matriz_ CHETE MODIGUAL valor 
	| matriz_ CHETE PLUSPLUS
	| PLUSPLUS matriz_ CHETE
	| matriz_ CHETE MINUSMINUS
	| MINUSMINUS matriz_ CHETE ;

matriz_::= matriz_ COMA valor // retorna un string tipo 'stack[tn]'
	| ID COR valor 
	| ID PUNTO ID COR valor 
	| THIS PUNTO ID COR valor ;

valor::= val:v {: if(pasada() == 2) RESULT = v; :} // retorna un Valor de la forma 'tn' con su tipo. Pueden venir valores primitivos
	| LLA tupla VES 
	| error {: error(); :} ;

val::= val MAS val 
	| val MENOS val 
	| val MUL val 
	| val DIV val 
	| val MOD val 
	| MENOS val %prec NEGATIVO 
	| PAREN val TESIS 
	| exp:e {: if(pasada() == 2) RESULT = e; :}
	| val COMPARA val 
	| val OR val 
	| val AND val 
	| val XOR val ;
	
tupla::= tupla COMA valor 
	| valor ;

exp::= idvalor
	| VINT
	| VFLOAT
	| VBOOLEAN
	| COMILLA VCHAR COMILLA
	| COMILLAS VSTRING:v {: if(pasada() == 2) RESULT = new Valor(v, Variable.tString); :} COMILLAS
	| COMILLAS COMILLAS
	| PAREN tipoc TESIS valor
	| idvalor PLUSPLUS
	| PLUSPLUS idvalor
	| idvalor MINUSMINUS
	| MINUSMINUS idvalor
	| NEW lclase TESIS
	| NEW ID PAREN TESIS 
	| ID PAREN TESIS
	| ID PUNTO ID PAREN TESIS 
	| lfuncion TESIS 
	| _lmatriz CHETE 
	| _lmatriz CHETE PLUSPLUS
	| PLUSPLUS _lmatriz CHETE
	| _lmatriz CHETE MINUSMINUS
	| MINUSMINUS _lmatriz CHETE 
	| NULL ;
	
_lmatriz::= _lmatriz COMA valor 
	| idvalor COR valor ;

lclase::= lclase COMA valor
	| ID PAREN valor ;

lfuncion::= lfuncion COMA valor
	| ID PAREN valor 
	| ID PUNTO ID PAREN valor ;

ifelse::= IF PAREN valor TESIS sentencias else ;

else::= ELSE sentencias 
	| ;
	
while::= WHILE PAREN valor TESIS sentencias ;

dowhile::= DO sentencias WHILE PAREN valor TESIS ;

for::= FOR PAREN _asigna PUNTOCOMA valor PUNTOCOMA _asigna TESIS sentencias ;

_asigna::= asigna 
	| ;

switchcase::= SWITCH PAREN valor TESIS LLA cases default VES ;

cases::= cases case 
	| case ;
	
case::= CASE valor DOSPUNTOS sents ;

default::= DEFAULT DOSPUNTOS sents 
	| ;

