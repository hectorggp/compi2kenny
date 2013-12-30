
package com.gs;

import java_cup.runtime.Symbol;

%% 

%class LexicoLex

%unicode
%line
%column
%char

%cupsym com.enemigo.ParserSym
%cup

%init{

%init}

%eofval{
	System.out.println("Fin del archivo");
	return new Symbol(ParserSym.EOF, "Fin de archivo");
%eofval}

%{
	String file;
	Generador java;
	
	LexicoLex(java.io.Reader reader, String archivo) {
		this(reader);
		this.file = archivo;
	}
	
	LexicoLex(java.io.File file, Generador java) throws java.io.FileNotFoundException {
		this(new java.io.InputStreamReader(new java.io.FileInputStream(file)));
		this.java = java;
		this.file = file.getName();
	}
	
	private Symbol sym(int type) {
		return sym(type, yytext());
	}

	private Symbol sym(int type, Object value) {
		return new Symbol(type, yyline, yychar, value);
	}

	private void error() {
		// System.err.println("Símbolo ilegal: '" + yytext() + "' en archivo " + file + ". Linea " + (yyline + 1));
		java.error("Error, símbolo '" + yytext() + "' inválido", file, yyline + 1);
	}
%}

Espacio 	= [ \t\f]
FinDeLinea	= [\r|\n]+
ID		 	= [:jletter:] [:jletterdigit:]*
D			= [0-9]+

%state STRING
%state CHAR
%state COMENT
%state COMENTM

%%

<YYINITIAL>	"("				{ return sym(ParserSym.PAREN); }
<YYINITIAL>	")"				{ return sym(ParserSym.TESIS); }
<YYINITIAL>	"{"				{ return sym(ParserSym.LLA); }
<YYINITIAL>	"}"				{ return sym(ParserSym.VES); }
<YYINITIAL>	";"				{ return sym(ParserSym.PUNTOCOMA); }
<YYINITIAL>	"["				{ return sym(ParserSym.COR); }
<YYINITIAL>	"]"				{ return sym(ParserSym.CHETE); }
<YYINITIAL>	"."				{ return sym(ParserSym.PUNTO); }
<YYINITIAL>	"+"				{ return sym(ParserSym.MAS); }
<YYINITIAL>	"-"				{ return sym(ParserSym.MENOS); }
<YYINITIAL>	"*"				{ return sym(ParserSym.MUL); }
<YYINITIAL>	"/"				{ return sym(ParserSym.DIV); }
<YYINITIAL>	"%"				{ return sym(ParserSym.MOD); }
<YYINITIAL>	","				{ return sym(ParserSym.COMA); }
<YYINITIAL>	":"				{ return sym(ParserSym.DOSPUNTOS); }
<YYINITIAL>	"="				{ return sym(ParserSym.ASIGNA); }
<YYINITIAL>	"+="			{ return sym(ParserSym.MASIGUAL); }
<YYINITIAL>	"-="			{ return sym(ParserSym.MENOSIGUAL); }
<YYINITIAL>	"*="			{ return sym(ParserSym.MULIGUAL); }
<YYINITIAL>	"%="			{ return sym(ParserSym.MODIGUAL); }
<YYINITIAL>	"&&"			{ return sym(ParserSym.AND); }
<YYINITIAL>	"^"				{ return sym(ParserSym.XOR); }
<YYINITIAL>	"||"			{ return sym(ParserSym.OR); }
<YYINITIAL>	"++"			{ return sym(ParserSym.PLUSPLUS); }
<YYINITIAL>	"--"			{ return sym(ParserSym.MINUSMINUS); }
<YYINITIAL>	"=="			{ return sym(ParserSym.COMPARA); }
<YYINITIAL>	"!="			{ return sym(ParserSym.COMPARA); }
<YYINITIAL>	"<"				{ return sym(ParserSym.COMPARA); }
<YYINITIAL>	"<="			{ return sym(ParserSym.COMPARA); }
<YYINITIAL>	">"				{ return sym(ParserSym.COMPARA); }
<YYINITIAL>	">="			{ return sym(ParserSym.COMPARA); }
<YYINITIAL>	"&"				{ return sym(ParserSym.APUNTADOR); }
<YYINITIAL>	"'"				{ yybegin(CHAR); return sym(ParserSym.COMILLA); }
<CHAR>		[^"'"]			{ return sym(ParserSym.VCHAR); }
<CHAR>		"'"				{ yybegin(YYINITIAL); return sym(ParserSym.COMILLA); }
<YYINITIAL>	"\""			{ yybegin(STRING); return sym(ParserSym.COMILLAS); }
<STRING>	[^"\""]+		{ return sym(ParserSym.VSTRING); }
<STRING>	"\""			{ yybegin(YYINITIAL); return sym(ParserSym.COMILLAS); }
<YYINITIAL>	"/*"			{ yybegin(COMENTM); }
<COMENTM>	"*/"			{ yybegin(YYINITIAL); }
<COMENTM>	[^"*/"]			{ /* ignorar */ }
<COMENTM>	.				{ /* ignorar */ }
<YYINITIAL>	"//"			{ yybegin(COMENT); }
<COMENT>	{FinDeLinea}	{ yybegin(YYINITIAL); }
<COMENT>	.				{ /* ignorar */ }
<YYINITIAL>	int				{ return sym(ParserSym.INT); }
<YYINITIAL>	String			{ return sym(ParserSym.STRING); }
<YYINITIAL>	char			{ return sym(ParserSym.CHAR); }
<YYINITIAL>	boolean			{ return sym(ParserSym.BOOLEAN); }
<YYINITIAL>	float			{ return sym(ParserSym.FLOAT); }
<YYINITIAL>	true			{ return sym(ParserSym.VBOOLEAN); }
<YYINITIAL>	false			{ return sym(ParserSym.VBOOLEAN); }
<YYINITIAL>	if				{ return sym(ParserSym.IF); }
<YYINITIAL>	else			{ return sym(ParserSym.ELSE); }
<YYINITIAL>	while			{ return sym(ParserSym.WHILE); }
<YYINITIAL>	do				{ return sym(ParserSym.DO); }
<YYINITIAL>	for				{ return sym(ParserSym.FOR); }
<YYINITIAL>	switch			{ return sym(ParserSym.SWITCH); }
<YYINITIAL>	case			{ return sym(ParserSym.CASE); }
<YYINITIAL>	break			{ return sym(ParserSym.BREAK); }
<YYINITIAL>	class			{ return sym(ParserSym.CLASS); }
<YYINITIAL>	new				{ return sym(ParserSym.NEW); }
<YYINITIAL>	import			{ return sym(ParserSym.IMPORT); }
<YYINITIAL>	extends			{ return sym(ParserSym.EXTENDS); }
<YYINITIAL>	public			{ return sym(ParserSym.PUBLIC); }
<YYINITIAL>	protected		{ return sym(ParserSym.PROTECTED); }
<YYINITIAL>	private			{ return sym(ParserSym.PRIVATE); }
<YYINITIAL>	return			{ return sym(ParserSym.RETURN); }
<YYINITIAL>	default			{ return sym(ParserSym.DEFAULT); }
<YYINITIAL>	void			{ return sym(ParserSym.VOID); }
<YYINITIAL>	this			{ return sym(ParserSym.THIS); }
<YYINITIAL>	print			{ return sym(ParserSym.PRINT); }
<YYINITIAL>	null			{ return sym(ParserSym.NULL); }
<YYINITIAL>	{D}+"."{D}		{ return sym(ParserSym.VFLOAT); }
<YYINITIAL>	{D}+			{ return sym(ParserSym.VINT); }
<YYINITIAL>	{ID}			{ return sym(ParserSym.ID); }
<YYINITIAL>	{Espacio}		{ /* ignorar */ }
<YYINITIAL>	{FinDeLinea}	{ /* ignorar */ }
<YYINITIAL>	.				{ error(); }








