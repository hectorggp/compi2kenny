package com.formato;

import java_cup.runtime.Symbol;
import java.io.File;

action code
{:
	private int num = 0;
	
	private String tabs(){
		String ret = "";
		for(int i = 0; i < num; i++){
			ret += "\t";
		}
		return ret;
	}
	
	private void error(){
		parser.syntax_error(parser.symbolActual());
	}

	private void error(String error, int linea){
		java().error(error, parser.archivo, linea);
	}
	
	private void error(String symbol){
		parser.syntax_error(new Symbol(ParserSym.error, 
				parser.symbolActual().left, 0, symbol));
	}
	
	protected void add(String txt){
		java().add(txt);
	}
	
	protected Formador java (){ return parser.java; }

:};

parser code
{:
    public Symbol symbolActual() { 
        return this.cur_token;
    }

	@Override
	public void syntax_error(Symbol s) { 
		java.error("Símbolo '" + s.value + "' fuera de contexto", archivo, s.left + 1);
//		report_error("ERR Sintaxis. Archivo: " + archivo + " Linea: " + (s.left + 1) + 
//				". Texto: \"" + s.value + "\"", null);
	}

	@Override
	public void unrecovered_syntax_error(Symbol s) throws java.lang.Exception{
		System.err.println("La Cadena: \"" + s.value + "\" en la linea: " + 
				(s.left+1) + ", columna: " + s.right + " esta fuera de contexto!!!!!" );
	}

	public ParserCup(File file, Formador java) throws Exception {
		super(new LexicoLex(file, java));
		this.archivo = file.getName();
		this.java = java;
	}
	
	protected String archivo;	
	protected Formador java;
:};

terminal String PAREN, TESIS, LLA, VES, PUNTOCOMA, COR, CHETE, PUNTO, COMILLAS, 
		MAS, MENOS, MUL, DIV, MOD, ASIGNA, COMA, DOSPUNTOS, MASIGUAL, MENOSIGUAL, 
		MULIGUAL, MODIGUAL, AND, XOR, OR, COMILLA, VCHAR, VSTRING, INT, STRING, 
		IF, ELSE, WHILE, DO, FOR, SWITCH, CASE, BREAK, CLASS, NEW, IMPORT, 
		PUBLIC, PROTECTED, PRIVATE, RETURN, PRINT, VFLOAT, VINT, ID, EXTENDS, VOID,
		APUNTADOR, CHAR, BOOLEAN, FLOAT, VBOOLEAN, COMPARA, PLUSPLUS, MINUSMINUS, 
		DEFAULT, NULL, THIS ;
		
terminal NEGATIVO ; 

non terminal String S0, L, imports, _import, defclase, acceso, _extends, sentsc, sentc,
		atributo, tipo, atrasigna, funcion, decparams, _decparams, decparam, apunt, 
		bloque, valor, sents, sent, lclase, exp, lmatriz, _lmatriz, tupla, lfuncion,
		val, asigna, sentencias, ifelse, else, while, dowhile, for, _asigna, matriz,
		matriz_, switchcase, cases, case, default, id, func, tipoc, ves, puntocoma ;

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

_import::= IMPORT:i ID:j PUNTOCOMA:k {: add(i + " " + j + " " + k + "\n"); :}
	| error PUNTOCOMA {: error(); :} ;

defclase::= {: add(tabs()); :} acceso CLASS:c ID:i {: add(c + " " + i + " "); :}
		 _extends LLA {: add(" {\n"); num++; :} sentsc ves:v {: 
			num -- ;
			add("\n}");
	 		if(v != null) error("Se esperaba '}'", vleft + 1); 
		 :} ;

ves::= VES | {: RESULT = "ola q ase"; :} ;

acceso::= PUBLIC {: add("public "); :}
	| PRIVATE {: add("private "); :}
	| PROTECTED {: add("protected "); :}
	| ;
	
_extends::= EXTENDS ID:i {: add("extends " + i + " "); :}
	| ;

sentsc::= sentsc {: add(tabs()); :} sentc {: add("\n"); :}
	| ;
	
sentc::= atributo
	| funcion ;
	
atributo::= acceso tipo ID:i {: add(i); :} atrasigna puntocoma:p {: if(p != null) error("Se esperaba ';'", pleft + 1); :} 
	| acceso tipo lmatriz CHETE {: add("]"); :} atrasigna puntocoma:p {: if(p != null) error("Se esperaba ';'", pleft + 1); :}
	| error PUNTOCOMA {: error(); :};

puntocoma::= PUNTOCOMA {: add(";\n"); :} | {: add(";\n"); RESULT = "ola q ase"; :} ;
	
lmatriz::= lmatriz COMA {: add(", "); :} valor
	| ID:i COR {: add(i + " ["); :} valor ;

tipo::= INT {: add("int "); :}
	| STRING {: add("String "); :}
	| CHAR {: add("char "); :}
	| BOOLEAN {: add("boolean "); :}
	| FLOAT {: add("float "); :}
	| ID:id {: add(id + " "); :} ;

tipoc::= INT {: add("int "); :}
	| STRING {: add("String "); :}
	| CHAR {: add("char "); :}
	| BOOLEAN {: add("boolean "); :}
	| FLOAT {: add("float "); :} ;

atrasigna::= ASIGNA {: add(" = "); :} valor
	| ;
	
funcion::= acceso tipo ID:i PAREN {: add(i + " ("); :} _decparams TESIS {: add(") "); :} bloque 
	| acceso VOID ID:i PAREN {: add(i + " ("); :} _decparams TESIS {: add(") "); :} bloque 
	| acceso ID:i PAREN {: add(i + " ("); :} _decparams TESIS {: add(") "); :} bloque 
	| error {: error(); :} ;

_decparams::= decparams
	| ;

decparams::= decparams COMA {: add(", "); :} decparam 
	| decparam ;
	
decparam::= tipo apunt ID:i {: add(i); :} ;

apunt::= APUNTADOR {: add("&"); :}
	| ;

bloque::= LLA {: add("{"); num++ :} sents VES {: num--; add("\n" + tabs() + "}"); :} ;

sentencias::= bloque 
	| {: n++; add(tabs()); :} sent {: n--; :} ;
	
sents::= sents {: add(tabs()); :} sent 
	| ;

sent::= tipo ID:i {: add(i); :} atrasigna PUNTOCOMA {: add(";\n"); :}
	| tipo matriz CHETE {: add("]"); :} atrasigna PUNTOCOMA {: add(";\n"); :}
	| asigna PUNTOCOMA {: add(";\n"); :}
	| id PAREN TESIS PUNTOCOMA {: add("();\n"); :}
	| func TESIS PUNTOCOMA {: add(");\n"); :}
	| ifelse 
	| while 
	| dowhile PUNTOCOMA {: add(";\n"); :}
	| for 
	| switchcase 
	| BREAK PUNTOCOMA {: add("break;\n"); :}
	| RETURN {: add("return "); :} valor PUNTOCOMA {: add(";\n"); :}
	| PRINT PAREN {: add("print ("); :} val TESIS PUNTOCOMA {: add(");\n"); :}
	| error PUNTOCOMA {: error(); :} ; 

func::= func COMA {: add(", "); :} valor 
	| id PAREN {: add("("); :} valor ;

matriz::= matriz COMA {: add(", "); :} valor 
	| ID:i COR {: add(i + " [" :} valor ;

id::= ID:i {: add(i); :}
	| ID:id1 PUNTO ID:id2 {: add(id1 + "." + id2); :} 
	| THIS PUNTO ID:i {: add("this." + i); :} ;
	
asigna::= id ASIGNA {: add(" = "); :} valor
	| id MASIGUAL {: add(" += "); :} valor 
	| id MENOSIGUAL {: add(" -= "); :} valor
	| id MULIGUAL {: add(" *= "); :} valor 
	| id MODIGUAL {: add(" %= "); :} valor 
	| id PLUSPLUS {: add(" ++"); :}
	| PLUSPLUS {: add("++ "); :} id
	| id MINUSMINUS {: add(" --"); :}
	| MINUSMINUS {: add("-- "); :} id
	| matriz_ CHETE ASIGNA {: add("] = "); :} valor
	| matriz_ CHETE MASIGUAL {: add("] += "); :} valor 
	| matriz_ CHETE MENOSIGUAL {: add("] -= "); :} valor
	| matriz_ CHETE MULIGUAL {: add("] *= "); :} valor 
	| matriz_ CHETE MODIGUAL {: add("] %= "); :} valor 
	| matriz_ CHETE PLUSPLUS {: add("] ++"); :}
	| PLUSPLUS {: add("++ "); :}  matriz_ CHETE {: add("]"); :}
	| matriz_ CHETE MINUSMINUS {: add("] --"); :}
	| MINUSMINUS {: add("-- "); :} matriz_ CHETE {: add("]"); :} ;

matriz_::= matriz_ COMA {: add(", "); :} valor
	| id:i COR {: add(i + " ["); :} valor ;

valor::= val
	| LLA {: add("{"); :} tupla VES {: add("}"); :} 
	| error {: error(); :} ;

val::= val MAS {: add(" + "); :} val 
	| val MENOS {: add(" - "); :} val 
	| val MUL {: add(" * "); :} val 
	| val DIV {: add(" / "); :} val 
	| val MOD {: add(" % "); :} val 
	| MENOS {: add(" - "); :} val %prec NEGATIVO 
	| PAREN {: add("("); :} val TESIS {: add(")"); :}
	| exp 
	| val COMPARA:c {: add(c + " "); :} val 
	| val OR {: add(" || "); :} val 
	| val AND {: add(" && "); :} val 
	| val XOR {: add(" ^ "); :} val ;
	
tupla::= tupla COMA {: add(", "); :} valor 
	| valor ;

exp::= id
	| VINT:v {: add(v); :}
	| VFLOAT:v {: add(v); :}
	| VBOOLEAN:v {: add(v); :}
	| COMILLA VCHAR:v COMILLA {: add("'" + v + "'"); :}
	| COMILLAS VSTRING:v COMILLAS {: add("\"" + v + "\""); :}
	| COMILLAS COMILLAS {: add("\"\""); :}
	| PAREN {: add("("); :} tipoc TESIS {: add(") "); :} valor 
	| id PLUSPLUS {: add(" ++"); :}
	| PLUSPLUS {: add("++ "); :} id
	| id MINUSMINUS {: add(" --"); :}
	| MINUSMINUS {: add("-- "); :} id
	| NEW {: add("new "); :} lclase TESIS {: add(")"); :}
	| NEW ID:i PAREN TESIS {: add("new " + i + "()"); :}
	| ID:i PAREN TESIS {: add(i + "()"); :}
	| ID:i1 PUNTO ID:i2 PAREN TESIS {: add(i1 + "." + i2 + "()"); :}
	| lfuncion TESIS {: add(")"); :}
	| _lmatriz CHETE 
	| _lmatriz CHETE PLUSPLUS
	| PLUSPLUS _lmatriz CHETE
	| _lmatriz CHETE MINUSMINUS
	| MINUSMINUS _lmatriz CHETE 
	| NULL ;
	
_lmatriz::= _lmatriz COMA valor 
	| id COR valor ;

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






















