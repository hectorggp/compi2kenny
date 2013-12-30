package com.java;

import java_cup.runtime.Symbol;

action code
{:
	private void error(){
		parser.syntax_error(parser.symbolActual());
	}
	
	private void error(String symbol){
		parser.syntax_error(new Symbol(ParserSym.error, 
				parser.symbolActual().left, 0, symbol));
	}
:};

parser code
{:
    public Symbol symbolActual() { 
        return this.cur_token;
    }

	@Override
	public void syntax_error(Symbol s) { 
		report_error("ERR Sintaxis. Archivo: " + archivo + " Linea: " + (s.left + 1) + 
				". Texto: \"" + s.value + "\"", null);
	}

	@Override
	public void unrecovered_syntax_error(Symbol s) throws java.lang.Exception{
		System.err.println("La Cadena: \"" + s.value + "\" en la linea: " + 
				(s.left+1) + ", columna: " + s.right + " esta fuera de contexto!!!!!" );
	}

	public ParserCup(String texto, String archivo) throws Exception {
		super(new LexicoLex(new java.io.CharArrayReader(texto.toCharArray()), archivo));
		this.archivo = archivo;
	}
	
	private String archivo;	
:};

terminal String PAREN, TESIS, LLA, VES, PUNTOCOMA, COR, CHETE, PUNTO, COMILLAS, 
		MAS, MENOS, MUL, DIV, MOD, ASIGNA, COMA, DOSPUNTOS, MASIGUAL, MENOSIGUAL, 
		MULIGUAL, MODIGUAL, AND, XOR, OR, COMILLA, VCHAR, VSTRING, INT, STRING, 
		IF, ELSE, WHILE, DO, FOR, SWITCH, CASE, BREAK, CLASS, NEW, IMPORT, 
		PUBLIC, PROTECTED, PRIVATE, RETURN, PRINT, VFLOAT, VINT, ID, EXTENDS, VOID,
		APUNTADOR, CHAR, BOOLEAN, FLOAT, VBOOLEAN, COMPARA, PLUSPLUS, MINUSMINUS, 
		DEFAULT, NULL ;
		
terminal NEGATIVO ; 

non terminal S0, L, imports, _import, defclase, acceso, _extends, sentsc, sentc,
		atributo, tipo, atrasigna, funcion, decparams, _decparams, decparam, apunt, 
		bloque, valor, sents, sent, lclase, exp, lmatriz, _lmatriz, tupla, lfuncion,
		val, asigna, sentencias, ifelse, else, while, dowhile, for, _asigna, matriz,
		matriz_, switchcase, cases, case, default, id, func ;

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
	| defclase ;

imports::= imports _import
	| _import ;

_import::= IMPORT ID PUNTOCOMA ;

defclase::= acceso CLASS ID _extends LLA sentsc VES ;

acceso::= PUBLIC
	| PRIVATE
	| PROTECTED
	| ;
	
_extends::= EXTENDS ID
	| ;

sentsc::= sentsc sentc
	| ;
	
sentc::= atributo
	| funcion ;
	
atributo::= acceso tipo ID atrasigna PUNTOCOMA 
	| acceso tipo lmatriz CHETE atrasigna PUNTOCOMA ;
	
lmatriz::= lmatriz COMA valor
	| ID COR valor ;

tipo::= INT
	| STRING
	| CHAR
	| BOOLEAN
	| FLOAT
	| ID ;

atrasigna::= ASIGNA valor 
	| ;
	
funcion::= acceso tipo ID PAREN _decparams TESIS bloque 
	| acceso VOID ID PAREN _decparams TESIS bloque 
	| acceso ID PAREN _decparams TESIS bloque ;

_decparams::= decparams
	| ;

decparams::= decparams COMA decparam 
	| decparam ;
	
decparam::= tipo apunt ID ;

apunt::= APUNTADOR
	| ;

bloque::= LLA sents VES ;

sentencias::= bloque 
	| sent ;
	
sents::= sents sent 
	| ;

sent::= tipo ID atrasigna PUNTOCOMA
	| tipo matriz CHETE atrasigna PUNTOCOMA
	| asigna PUNTOCOMA 
	| id PAREN TESIS PUNTOCOMA
	| func TESIS PUNTOCOMA
	| ifelse 
	| while 
	| dowhile PUNTOCOMA 
	| for 
	| switchcase 
	| BREAK PUNTOCOMA ; 

func::= func COMA valor 
	| id PAREN valor ;

matriz::= matriz COMA valor 
	| ID COR valor ;

id::= ID
	| ID PUNTO ID ;
	
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

matriz_::= matriz_ COMA valor
	| id COR valor ;

valor::= val
	| LLA tupla VES ;

val::= val MAS val 
	| val MENOS val 
	| val MUL val 
	| val DIV val 
	| val MOD val
	| MENOS val %prec NEGATIVO
	| exp
	| val OR val 
	| val AND val 
	| val XOR val ;
	
tupla::= tupla COMA valor 
	| valor ;

exp::= id
	| VINT
	| VFLOAT
	| VBOOLEAN
	| COMILLA VCHAR COMILLA
	| COMILLAS VSTRING COMILLAS
	| COMILLAS COMILLAS
	| PAREN tipo TESIS valor
	| id PLUSPLUS
	| PLUSPLUS id
	| id MINUSMINUS
	| MINUSMINUS id
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
	| val COMPARA val 
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






















