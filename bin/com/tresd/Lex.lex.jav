package com.tresd;
import java_cup.runtime.Symbol;


class LexicoLex implements java_cup.runtime.Scanner {
	private final int YY_BUFFER_SIZE = 512;
	private final int YY_F = -1;
	private final int YY_NO_STATE = -1;
	private final int YY_NOT_ACCEPT = 0;
	private final int YY_START = 1;
	private final int YY_END = 2;
	private final int YY_NO_ANCHOR = 4;
	private final int YY_BOL = 65536;
	private final int YY_EOF = 65537;

	String file;
	Clase clase;
	LexicoLex(java.io.Reader reader, String archivo) {
		this(reader);
		this.file = archivo;
	}
	LexicoLex(java.io.File file, Clase clase) throws java.io.FileNotFoundException {
		this(new java.io.InputStreamReader(new java.io.FileInputStream(file)));
		this.clase = clase;
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
		clase.error("Error, símbolo '" + yytext() + "' inválido", file, yyline + 1);
	}
	private java.io.BufferedReader yy_reader;
	private int yy_buffer_index;
	private int yy_buffer_read;
	private int yy_buffer_start;
	private int yy_buffer_end;
	private char yy_buffer[];
	private int yychar;
	private int yyline;
	private boolean yy_at_bol;
	private int yy_lexical_state;

	LexicoLex (java.io.Reader reader) {
		this ();
		if (null == reader) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(reader);
	}

	LexicoLex (java.io.InputStream instream) {
		this ();
		if (null == instream) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(new java.io.InputStreamReader(instream));
	}

	private LexicoLex () {
		yy_buffer = new char[YY_BUFFER_SIZE];
		yy_buffer_read = 0;
		yy_buffer_index = 0;
		yy_buffer_start = 0;
		yy_buffer_end = 0;
		yychar = 0;
		yyline = 0;
		yy_at_bol = true;
		yy_lexical_state = YYINITIAL;

	}

	private boolean yy_eof_done = false;
	private final int COMENTM = 4;
	private final int STRING = 1;
	private final int COMENT = 3;
	private final int YYINITIAL = 0;
	private final int CHAR = 2;
	private final int yy_state_dtrans[] = {
		0,
		164,
		165,
		166,
		167
	};
	private void yybegin (int state) {
		yy_lexical_state = state;
	}
	private int yy_advance ()
		throws java.io.IOException {
		int next_read;
		int i;
		int j;

		if (yy_buffer_index < yy_buffer_read) {
			return yy_buffer[yy_buffer_index++];
		}

		if (0 != yy_buffer_start) {
			i = yy_buffer_start;
			j = 0;
			while (i < yy_buffer_read) {
				yy_buffer[j] = yy_buffer[i];
				++i;
				++j;
			}
			yy_buffer_end = yy_buffer_end - yy_buffer_start;
			yy_buffer_start = 0;
			yy_buffer_read = j;
			yy_buffer_index = j;
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}

		while (yy_buffer_index >= yy_buffer_read) {
			if (yy_buffer_index >= yy_buffer.length) {
				yy_buffer = yy_double(yy_buffer);
			}
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}
		return yy_buffer[yy_buffer_index++];
	}
	private void yy_move_end () {
		if (yy_buffer_end > yy_buffer_start &&
		    '\n' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
		if (yy_buffer_end > yy_buffer_start &&
		    '\r' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
	}
	private boolean yy_last_was_cr=false;
	private void yy_mark_start () {
		int i;
		for (i = yy_buffer_start; i < yy_buffer_index; ++i) {
			if ('\n' == yy_buffer[i] && !yy_last_was_cr) {
				++yyline;
			}
			if ('\r' == yy_buffer[i]) {
				++yyline;
				yy_last_was_cr=true;
			} else yy_last_was_cr=false;
		}
		yychar = yychar
			+ yy_buffer_index - yy_buffer_start;
		yy_buffer_start = yy_buffer_index;
	}
	private void yy_mark_end () {
		yy_buffer_end = yy_buffer_index;
	}
	private void yy_to_mark () {
		yy_buffer_index = yy_buffer_end;
		yy_at_bol = (yy_buffer_end > yy_buffer_start) &&
		            ('\r' == yy_buffer[yy_buffer_end-1] ||
		             '\n' == yy_buffer[yy_buffer_end-1] ||
		             2028/*LS*/ == yy_buffer[yy_buffer_end-1] ||
		             2029/*PS*/ == yy_buffer[yy_buffer_end-1]);
	}
	private java.lang.String yytext () {
		return (new java.lang.String(yy_buffer,
			yy_buffer_start,
			yy_buffer_end - yy_buffer_start));
	}
	private int yylength () {
		return yy_buffer_end - yy_buffer_start;
	}
	private char[] yy_double (char buf[]) {
		int i;
		char newbuf[];
		newbuf = new char[2*buf.length];
		for (i = 0; i < buf.length; ++i) {
			newbuf[i] = buf[i];
		}
		return newbuf;
	}
	private final int YY_E_INTERNAL = 0;
	private final int YY_E_MATCH = 1;
	private java.lang.String yy_error_string[] = {
		"Error: Internal error.\n",
		"Error: Unmatched input.\n"
	};
	private void yy_error (int code,boolean fatal) {
		java.lang.System.out.print(yy_error_string[code]);
		java.lang.System.out.flush();
		if (fatal) {
			throw new Error("Fatal Error.\n");
		}
	}
	private int[][] unpackFromString(int size1, int size2, String st) {
		int colonIndex = -1;
		String lengthString;
		int sequenceLength = 0;
		int sequenceInteger = 0;

		int commaIndex;
		String workString;

		int res[][] = new int[size1][size2];
		for (int i= 0; i < size1; i++) {
			for (int j= 0; j < size2; j++) {
				if (sequenceLength != 0) {
					res[i][j] = sequenceInteger;
					sequenceLength--;
					continue;
				}
				commaIndex = st.indexOf(',');
				workString = (commaIndex==-1) ? st :
					st.substring(0, commaIndex);
				st = st.substring(commaIndex+1);
				colonIndex = workString.indexOf(':');
				if (colonIndex == -1) {
					res[i][j]=Integer.parseInt(workString);
					continue;
				}
				lengthString =
					workString.substring(colonIndex+1);
				sequenceLength=Integer.parseInt(lengthString);
				workString=workString.substring(0,colonIndex);
				sequenceInteger=Integer.parseInt(workString);
				res[i][j] = sequenceInteger;
				sequenceLength--;
			}
		}
		return res;
	}
	private int yy_acpt[] = {
		/* 0 */ YY_NOT_ACCEPT,
		/* 1 */ YY_NO_ANCHOR,
		/* 2 */ YY_NO_ANCHOR,
		/* 3 */ YY_NO_ANCHOR,
		/* 4 */ YY_NO_ANCHOR,
		/* 5 */ YY_NO_ANCHOR,
		/* 6 */ YY_NO_ANCHOR,
		/* 7 */ YY_NO_ANCHOR,
		/* 8 */ YY_NO_ANCHOR,
		/* 9 */ YY_NO_ANCHOR,
		/* 10 */ YY_NO_ANCHOR,
		/* 11 */ YY_NO_ANCHOR,
		/* 12 */ YY_NO_ANCHOR,
		/* 13 */ YY_NO_ANCHOR,
		/* 14 */ YY_NO_ANCHOR,
		/* 15 */ YY_NO_ANCHOR,
		/* 16 */ YY_NO_ANCHOR,
		/* 17 */ YY_NO_ANCHOR,
		/* 18 */ YY_NO_ANCHOR,
		/* 19 */ YY_NO_ANCHOR,
		/* 20 */ YY_NO_ANCHOR,
		/* 21 */ YY_NO_ANCHOR,
		/* 22 */ YY_NO_ANCHOR,
		/* 23 */ YY_NO_ANCHOR,
		/* 24 */ YY_NO_ANCHOR,
		/* 25 */ YY_NO_ANCHOR,
		/* 26 */ YY_NO_ANCHOR,
		/* 27 */ YY_NO_ANCHOR,
		/* 28 */ YY_NO_ANCHOR,
		/* 29 */ YY_NO_ANCHOR,
		/* 30 */ YY_NO_ANCHOR,
		/* 31 */ YY_NO_ANCHOR,
		/* 32 */ YY_NO_ANCHOR,
		/* 33 */ YY_NO_ANCHOR,
		/* 34 */ YY_NO_ANCHOR,
		/* 35 */ YY_NO_ANCHOR,
		/* 36 */ YY_NO_ANCHOR,
		/* 37 */ YY_NO_ANCHOR,
		/* 38 */ YY_NO_ANCHOR,
		/* 39 */ YY_NO_ANCHOR,
		/* 40 */ YY_NO_ANCHOR,
		/* 41 */ YY_NO_ANCHOR,
		/* 42 */ YY_NO_ANCHOR,
		/* 43 */ YY_NO_ANCHOR,
		/* 44 */ YY_NO_ANCHOR,
		/* 45 */ YY_NO_ANCHOR,
		/* 46 */ YY_NO_ANCHOR,
		/* 47 */ YY_NO_ANCHOR,
		/* 48 */ YY_NO_ANCHOR,
		/* 49 */ YY_NO_ANCHOR,
		/* 50 */ YY_NO_ANCHOR,
		/* 51 */ YY_NO_ANCHOR,
		/* 52 */ YY_NO_ANCHOR,
		/* 53 */ YY_NO_ANCHOR,
		/* 54 */ YY_NO_ANCHOR,
		/* 55 */ YY_NO_ANCHOR,
		/* 56 */ YY_NO_ANCHOR,
		/* 57 */ YY_NO_ANCHOR,
		/* 58 */ YY_NO_ANCHOR,
		/* 59 */ YY_NO_ANCHOR,
		/* 60 */ YY_NO_ANCHOR,
		/* 61 */ YY_NO_ANCHOR,
		/* 62 */ YY_NO_ANCHOR,
		/* 63 */ YY_NO_ANCHOR,
		/* 64 */ YY_NO_ANCHOR,
		/* 65 */ YY_NO_ANCHOR,
		/* 66 */ YY_NO_ANCHOR,
		/* 67 */ YY_NO_ANCHOR,
		/* 68 */ YY_NO_ANCHOR,
		/* 69 */ YY_NO_ANCHOR,
		/* 70 */ YY_NO_ANCHOR,
		/* 71 */ YY_NO_ANCHOR,
		/* 72 */ YY_NO_ANCHOR,
		/* 73 */ YY_NO_ANCHOR,
		/* 74 */ YY_NO_ANCHOR,
		/* 75 */ YY_NO_ANCHOR,
		/* 76 */ YY_NO_ANCHOR,
		/* 77 */ YY_NO_ANCHOR,
		/* 78 */ YY_NO_ANCHOR,
		/* 79 */ YY_NOT_ACCEPT,
		/* 80 */ YY_NO_ANCHOR,
		/* 81 */ YY_NO_ANCHOR,
		/* 82 */ YY_NO_ANCHOR,
		/* 83 */ YY_NOT_ACCEPT,
		/* 84 */ YY_NO_ANCHOR,
		/* 85 */ YY_NO_ANCHOR,
		/* 86 */ YY_NOT_ACCEPT,
		/* 87 */ YY_NO_ANCHOR,
		/* 88 */ YY_NO_ANCHOR,
		/* 89 */ YY_NOT_ACCEPT,
		/* 90 */ YY_NO_ANCHOR,
		/* 91 */ YY_NOT_ACCEPT,
		/* 92 */ YY_NO_ANCHOR,
		/* 93 */ YY_NOT_ACCEPT,
		/* 94 */ YY_NO_ANCHOR,
		/* 95 */ YY_NOT_ACCEPT,
		/* 96 */ YY_NO_ANCHOR,
		/* 97 */ YY_NOT_ACCEPT,
		/* 98 */ YY_NO_ANCHOR,
		/* 99 */ YY_NOT_ACCEPT,
		/* 100 */ YY_NO_ANCHOR,
		/* 101 */ YY_NOT_ACCEPT,
		/* 102 */ YY_NO_ANCHOR,
		/* 103 */ YY_NOT_ACCEPT,
		/* 104 */ YY_NO_ANCHOR,
		/* 105 */ YY_NOT_ACCEPT,
		/* 106 */ YY_NOT_ACCEPT,
		/* 107 */ YY_NOT_ACCEPT,
		/* 108 */ YY_NOT_ACCEPT,
		/* 109 */ YY_NOT_ACCEPT,
		/* 110 */ YY_NOT_ACCEPT,
		/* 111 */ YY_NOT_ACCEPT,
		/* 112 */ YY_NOT_ACCEPT,
		/* 113 */ YY_NOT_ACCEPT,
		/* 114 */ YY_NOT_ACCEPT,
		/* 115 */ YY_NOT_ACCEPT,
		/* 116 */ YY_NOT_ACCEPT,
		/* 117 */ YY_NOT_ACCEPT,
		/* 118 */ YY_NOT_ACCEPT,
		/* 119 */ YY_NOT_ACCEPT,
		/* 120 */ YY_NOT_ACCEPT,
		/* 121 */ YY_NOT_ACCEPT,
		/* 122 */ YY_NOT_ACCEPT,
		/* 123 */ YY_NOT_ACCEPT,
		/* 124 */ YY_NOT_ACCEPT,
		/* 125 */ YY_NOT_ACCEPT,
		/* 126 */ YY_NOT_ACCEPT,
		/* 127 */ YY_NOT_ACCEPT,
		/* 128 */ YY_NOT_ACCEPT,
		/* 129 */ YY_NOT_ACCEPT,
		/* 130 */ YY_NOT_ACCEPT,
		/* 131 */ YY_NOT_ACCEPT,
		/* 132 */ YY_NOT_ACCEPT,
		/* 133 */ YY_NOT_ACCEPT,
		/* 134 */ YY_NOT_ACCEPT,
		/* 135 */ YY_NOT_ACCEPT,
		/* 136 */ YY_NOT_ACCEPT,
		/* 137 */ YY_NOT_ACCEPT,
		/* 138 */ YY_NOT_ACCEPT,
		/* 139 */ YY_NOT_ACCEPT,
		/* 140 */ YY_NOT_ACCEPT,
		/* 141 */ YY_NOT_ACCEPT,
		/* 142 */ YY_NOT_ACCEPT,
		/* 143 */ YY_NOT_ACCEPT,
		/* 144 */ YY_NOT_ACCEPT,
		/* 145 */ YY_NOT_ACCEPT,
		/* 146 */ YY_NOT_ACCEPT,
		/* 147 */ YY_NOT_ACCEPT,
		/* 148 */ YY_NOT_ACCEPT,
		/* 149 */ YY_NOT_ACCEPT,
		/* 150 */ YY_NOT_ACCEPT,
		/* 151 */ YY_NOT_ACCEPT,
		/* 152 */ YY_NOT_ACCEPT,
		/* 153 */ YY_NOT_ACCEPT,
		/* 154 */ YY_NOT_ACCEPT,
		/* 155 */ YY_NOT_ACCEPT,
		/* 156 */ YY_NOT_ACCEPT,
		/* 157 */ YY_NOT_ACCEPT,
		/* 158 */ YY_NOT_ACCEPT,
		/* 159 */ YY_NOT_ACCEPT,
		/* 160 */ YY_NOT_ACCEPT,
		/* 161 */ YY_NOT_ACCEPT,
		/* 162 */ YY_NOT_ACCEPT,
		/* 163 */ YY_NOT_ACCEPT,
		/* 164 */ YY_NOT_ACCEPT,
		/* 165 */ YY_NOT_ACCEPT,
		/* 166 */ YY_NOT_ACCEPT,
		/* 167 */ YY_NOT_ACCEPT,
		/* 168 */ YY_NO_ANCHOR,
		/* 169 */ YY_NOT_ACCEPT,
		/* 170 */ YY_NOT_ACCEPT,
		/* 171 */ YY_NOT_ACCEPT,
		/* 172 */ YY_NOT_ACCEPT,
		/* 173 */ YY_NOT_ACCEPT,
		/* 174 */ YY_NOT_ACCEPT,
		/* 175 */ YY_NOT_ACCEPT,
		/* 176 */ YY_NOT_ACCEPT,
		/* 177 */ YY_NOT_ACCEPT,
		/* 178 */ YY_NOT_ACCEPT,
		/* 179 */ YY_NOT_ACCEPT,
		/* 180 */ YY_NOT_ACCEPT,
		/* 181 */ YY_NOT_ACCEPT,
		/* 182 */ YY_NOT_ACCEPT,
		/* 183 */ YY_NOT_ACCEPT,
		/* 184 */ YY_NOT_ACCEPT,
		/* 185 */ YY_NOT_ACCEPT,
		/* 186 */ YY_NOT_ACCEPT,
		/* 187 */ YY_NOT_ACCEPT,
		/* 188 */ YY_NOT_ACCEPT,
		/* 189 */ YY_NOT_ACCEPT,
		/* 190 */ YY_NOT_ACCEPT,
		/* 191 */ YY_NO_ANCHOR,
		/* 192 */ YY_NOT_ACCEPT,
		/* 193 */ YY_NOT_ACCEPT,
		/* 194 */ YY_NO_ANCHOR,
		/* 195 */ YY_NO_ANCHOR,
		/* 196 */ YY_NO_ANCHOR,
		/* 197 */ YY_NO_ANCHOR,
		/* 198 */ YY_NO_ANCHOR
	};
	private int yy_cmap[] = unpackFromString(1,65538,
"35:9,52,26,35,52,34,35:18,52,35:2,21,35,13,24,35,1,2,11,9,14,10,8,12,50:10," +
"15,5,22,16,23,35:15,20,19,35:9,17,35,6,35,7,18,25,35,28,27,29,41,36,45,35,3" +
"3,42,51,30,32,43,37,40,47,35,39,31,38,44,49,46,48,35:2,3,34,4,35:65410,0:2")[0];

	private int yy_rmap[] = unpackFromString(1,199,
"0,1:9,2,3,4,1,5,1:2,6,1:3,7,1,8,9,1:2,10,1,11,1:15,12,1:25,13:2,1:3,14,1:2," +
"15,16,17,18,19,1,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,3" +
"9,40,41,42,43,44,12,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,6" +
"3,64,65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,8" +
"8,89,90,91,92,93,94,95,96,97,98,99,100,101,102,103,104,105,106,107,108,109," +
"110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128" +
",129,130,131,132")[0];

	private int yy_nxt[][] = unpackFromString(133,53,
"1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,80," +
"84,87,84,90,28,84,27,84,81,92,85,88,84,94,96,84:2,98,100,102,84,104,29,28,3" +
"0,-1:62,31,-1:6,32,-1:46,33,-1:5,34,-1:52,35,-1:52,36,-1:52,37,-1:55,38,-1:" +
"49,39,-1:52,40,-1:62,27,-1:7,27,-1:26,110,-1:41,29,-1:52,45,-1:3,71:25,-1,7" +
"1:26,-1:26,76,-1:7,76,-1:47,111,-1:51,79,-1:10,83,86,-1:44,95,-1:4,97,-1:10" +
",192,-1:5,71:25,-1,71:6,72,71:19,-1:36,169,-1:49,170,-1:5,103,-1:53,186,-1:" +
"40,89,-1:3,91,-1:56,172,-1:47,112,-1:7,183,-1,113,-1:57,93,-1:34,171,-1:60," +
"99,-1:7,101,-1:50,185,-1:46,105,-1:3,41,-1:43,114,-1:64,106,-1,42,-1:45,174" +
",-1:42,173,-1:11,107,-1:58,43,-1:39,184,-1:51,115,-1:59,108,-1:4,109,-1:52," +
"117,-1:48,190,-1:41,119,-1:15,120,-1:54,121,-1:44,44,-1:53,124,-1,125,-1:37" +
",176,-1:55,175,-1:58,46,-1:52,129,-1:52,47,-1:48,48,-1:51,49,-1:57,50,-1:60" +
",179,-1:50,132,-1:38,133,-1:64,188,-1:43,134,-1:53,135,-1:58,136,-1:51,137," +
"-1:11,138,-1:44,51,-1:41,52,-1:51,141,-1:60,142,-1:46,53,-1:60,144,-1:56,18" +
"9,-1:53,181,-1:44,54,-1:52,55,-1:52,148,-1:54,56,-1:42,182,-1:66,149,-1:42," +
"150,-1:58,152,-1:42,57,-1:57,58,-1:59,59,-1:53,153,-1:48,60,-1:53,61,-1:43," +
"156,-1:52,62,-1:51,158,-1:61,159,-1:51,160,-1:47,63,-1:53,64,-1:58,65,-1:52" +
",161,-1:50,66,-1:47,162,-1:61,67,-1:51,68,-1:49,163,-1:49,69,-1:60,70,-1:11" +
",1,71:25,-1,198,71:25,1,73:24,74,73:27,1,75:25,76,75:7,76,75:18,1,77:6,78,7" +
"7:45,-1,71:25,-1,71:4,82,71:21,-1:28,127,-1:66,116,-1:41,130,-1:59,118,-1:4" +
"6,122,-1:56,131,-1:47,140,-1:53,139,-1:49,143,-1:60,145,-1:54,146,-1:41,151" +
",-1:56,155,-1:58,157,-1:42,128,-1:66,123,-1:48,177,-1:46,187,-1:56,180,-1:5" +
"5,147,-1:41,154,-1:66,126,-1:11,71:25,-1,71,168,71:24,-1:38,193,-1:50,178,-" +
"1:17,71:25,-1,71:5,191,71:20,-1,71:25,-1,71:4,194,71:21,-1,71:25,-1,71:3,19" +
"5,71:22,-1,71:25,-1,71:2,196,71:23,-1,71:25,-1,71,197,71:24");

	public java_cup.runtime.Symbol next_token ()
		throws java.io.IOException {
		int yy_lookahead;
		int yy_anchor = YY_NO_ANCHOR;
		int yy_state = yy_state_dtrans[yy_lexical_state];
		int yy_next_state = YY_NO_STATE;
		int yy_last_accept_state = YY_NO_STATE;
		boolean yy_initial = true;
		int yy_this_accept;

		yy_mark_start();
		yy_this_accept = yy_acpt[yy_state];
		if (YY_NOT_ACCEPT != yy_this_accept) {
			yy_last_accept_state = yy_state;
			yy_mark_end();
		}
		while (true) {
			if (yy_initial && yy_at_bol) yy_lookahead = YY_BOL;
			else yy_lookahead = yy_advance();
			yy_next_state = YY_F;
			yy_next_state = yy_nxt[yy_rmap[yy_state]][yy_cmap[yy_lookahead]];
			if (YY_EOF == yy_lookahead && true == yy_initial) {

	System.out.println("Fin del archivo");
	return new Symbol(ParserSym.EOF, "Fin de archivo");
			}
			if (YY_F != yy_next_state) {
				yy_state = yy_next_state;
				yy_initial = false;
				yy_this_accept = yy_acpt[yy_state];
				if (YY_NOT_ACCEPT != yy_this_accept) {
					yy_last_accept_state = yy_state;
					yy_mark_end();
				}
			}
			else {
				if (YY_NO_STATE == yy_last_accept_state) {
					throw (new Error("Lexical Error: Unmatched Input."));
				}
				else {
					yy_anchor = yy_acpt[yy_last_accept_state];
					if (0 != (YY_END & yy_anchor)) {
						yy_move_end();
					}
					yy_to_mark();
					switch (yy_last_accept_state) {
					case 1:
						
					case -2:
						break;
					case 2:
						{ return sym(ParserSym.PAREN); }
					case -3:
						break;
					case 3:
						{ return sym(ParserSym.TESIS); }
					case -4:
						break;
					case 4:
						{ return sym(ParserSym.LLA); }
					case -5:
						break;
					case 5:
						{ return sym(ParserSym.VES); }
					case -6:
						break;
					case 6:
						{ return sym(ParserSym.PUNTOCOMA); }
					case -7:
						break;
					case 7:
						{ return sym(ParserSym.COR); }
					case -8:
						break;
					case 8:
						{ return sym(ParserSym.CHETE); }
					case -9:
						break;
					case 9:
						{ return sym(ParserSym.PUNTO); }
					case -10:
						break;
					case 10:
						{ return sym(ParserSym.MAS); }
					case -11:
						break;
					case 11:
						{ return sym(ParserSym.MENOS); }
					case -12:
						break;
					case 12:
						{ return sym(ParserSym.MUL); }
					case -13:
						break;
					case 13:
						{ return sym(ParserSym.DIV); }
					case -14:
						break;
					case 14:
						{ return sym(ParserSym.MOD); }
					case -15:
						break;
					case 15:
						{ return sym(ParserSym.COMA); }
					case -16:
						break;
					case 16:
						{ return sym(ParserSym.DOSPUNTOS); }
					case -17:
						break;
					case 17:
						{ return sym(ParserSym.ASIGNA); }
					case -18:
						break;
					case 18:
						{ return sym(ParserSym.AND); }
					case -19:
						break;
					case 19:
						{ return sym(ParserSym.XOR); }
					case -20:
						break;
					case 20:
						{ return sym(ParserSym.OR); }
					case -21:
						break;
					case 21:
						{ error(); }
					case -22:
						break;
					case 22:
						{ return sym(ParserSym.COMPARA); }
					case -23:
						break;
					case 23:
						{ return sym(ParserSym.COMPARA); }
					case -24:
						break;
					case 24:
						{ return sym(ParserSym.COMPARA); }
					case -25:
						break;
					case 25:
						{ return sym(ParserSym.APUNTADOR); }
					case -26:
						break;
					case 26:
						{ yybegin(CHAR); return sym(ParserSym.COMILLA); }
					case -27:
						break;
					case 27:
						{ /* ignorar */ }
					case -28:
						break;
					case 28:
						{ return sym(ParserSym.ID); }
					case -29:
						break;
					case 29:
						{ return sym(ParserSym.VINT); }
					case -30:
						break;
					case 30:
						{ /* ignorar */ }
					case -31:
						break;
					case 31:
						{ return sym(ParserSym.PLUSPLUS); }
					case -32:
						break;
					case 32:
						{ return sym(ParserSym.MASIGUAL); }
					case -33:
						break;
					case 33:
						{ return sym(ParserSym.MINUSMINUS); }
					case -34:
						break;
					case 34:
						{ return sym(ParserSym.MENOSIGUAL); }
					case -35:
						break;
					case 35:
						{ return sym(ParserSym.MULIGUAL); }
					case -36:
						break;
					case 36:
						{ return sym(ParserSym.MODIGUAL); }
					case -37:
						break;
					case 37:
						{ return sym(ParserSym.COMPARA); }
					case -38:
						break;
					case 38:
						{ return sym(ParserSym.NOT); }
					case -39:
						break;
					case 39:
						{ return sym(ParserSym.COMPARA); }
					case -40:
						break;
					case 40:
						{ return sym(ParserSym.COMPARA); }
					case -41:
						break;
					case 41:
						{ return sym(ParserSym.DO); }
					case -42:
						break;
					case 42:
						{ return sym(ParserSym.IF); }
					case -43:
						break;
					case 43:
						{ return sym(ParserSym.NEW); }
					case -44:
						break;
					case 44:
						{ return sym(ParserSym.FOR); }
					case -45:
						break;
					case 45:
						{ return sym(ParserSym.VFLOAT); }
					case -46:
						break;
					case 46:
						{ return sym(ParserSym.CASE); }
					case -47:
						break;
					case 47:
						{ return sym(ParserSym.ELSE); }
					case -48:
						break;
					case 48:
						{ return sym(ParserSym.NULL); }
					case -49:
						break;
					case 49:
						{ return sym(ParserSym.THIS); }
					case -50:
						break;
					case 50:
						{ return sym(ParserSym.VBOOLEAN); }
					case -51:
						break;
					case 51:
						{ return sym(ParserSym.VOID); }
					case -52:
						break;
					case 52:
						{ return sym(ParserSym.BREAK); }
					case -53:
						break;
					case 53:
						{ return sym(ParserSym.CLASS); }
					case -54:
						break;
					case 54:
						{ return sym(ParserSym.VBOOLEAN); }
					case -55:
						break;
					case 55:
						{ return sym(ParserSym.WHILE); }
					case -56:
						break;
					case 56:
						{ return sym(ParserSym.PRINT); }
					case -57:
						break;
					case 57:
						{ return sym(ParserSym.STRING); }
					case -58:
						break;
					case 58:
						{ return sym(ParserSym.SWITCH); }
					case -59:
						break;
					case 59:
						{ return sym(ParserSym.INT); }
					case -60:
						break;
					case 60:
						{ return sym(ParserSym.RETURN); }
					case -61:
						break;
					case 61:
						{ return sym(ParserSym.IMPORT); }
					case -62:
						break;
					case 62:
						{ return sym(ParserSym.PUBLIC); }
					case -63:
						break;
					case 63:
						{ return sym(ParserSym.EXTENDS); }
					case -64:
						break;
					case 64:
						{ return sym(ParserSym.FLOAT); }
					case -65:
						break;
					case 65:
						{ return sym(ParserSym.DEFAULT); }
					case -66:
						break;
					case 66:
						{ return sym(ParserSym.PRIVATE); }
					case -67:
						break;
					case 67:
						{ return sym(ParserSym.BOOLEAN); }
					case -68:
						break;
					case 68:
						{ return sym(ParserSym.CHAR); }
					case -69:
						break;
					case 69:
						{ yybegin(STRING); return sym(ParserSym.COMILLAS); }
					case -70:
						break;
					case 70:
						{ return sym(ParserSym.PROTECTED); }
					case -71:
						break;
					case 71:
						{ return sym(ParserSym.VSTRING); }
					case -72:
						break;
					case 72:
						{ yybegin(YYINITIAL); return sym(ParserSym.COMILLAS); }
					case -73:
						break;
					case 73:
						{ return sym(ParserSym.VCHAR); }
					case -74:
						break;
					case 74:
						{ yybegin(YYINITIAL); return sym(ParserSym.COMILLA); }
					case -75:
						break;
					case 75:
						{ /* ignorar */ }
					case -76:
						break;
					case 76:
						{ yybegin(YYINITIAL); }
					case -77:
						break;
					case 77:
						{ /* ignorar */ }
					case -78:
						break;
					case 78:
						{ yybegin(YYINITIAL); }
					case -79:
						break;
					case 80:
						{ error(); }
					case -80:
						break;
					case 81:
						{ return sym(ParserSym.ID); }
					case -81:
						break;
					case 82:
						{ return sym(ParserSym.VSTRING); }
					case -82:
						break;
					case 84:
						{ error(); }
					case -83:
						break;
					case 85:
						{ return sym(ParserSym.ID); }
					case -84:
						break;
					case 87:
						{ error(); }
					case -85:
						break;
					case 88:
						{ return sym(ParserSym.ID); }
					case -86:
						break;
					case 90:
						{ error(); }
					case -87:
						break;
					case 92:
						{ error(); }
					case -88:
						break;
					case 94:
						{ error(); }
					case -89:
						break;
					case 96:
						{ error(); }
					case -90:
						break;
					case 98:
						{ error(); }
					case -91:
						break;
					case 100:
						{ error(); }
					case -92:
						break;
					case 102:
						{ error(); }
					case -93:
						break;
					case 104:
						{ error(); }
					case -94:
						break;
					case 168:
						{ return sym(ParserSym.VSTRING); }
					case -95:
						break;
					case 191:
						{ return sym(ParserSym.VSTRING); }
					case -96:
						break;
					case 194:
						{ return sym(ParserSym.VSTRING); }
					case -97:
						break;
					case 195:
						{ return sym(ParserSym.VSTRING); }
					case -98:
						break;
					case 196:
						{ return sym(ParserSym.VSTRING); }
					case -99:
						break;
					case 197:
						{ return sym(ParserSym.VSTRING); }
					case -100:
						break;
					case 198:
						{ return sym(ParserSym.VSTRING); }
					case -101:
						break;
					default:
						yy_error(YY_E_INTERNAL,false);
					case -1:
					}
					yy_initial = true;
					yy_state = yy_state_dtrans[yy_lexical_state];
					yy_next_state = YY_NO_STATE;
					yy_last_accept_state = YY_NO_STATE;
					yy_mark_start();
					yy_this_accept = yy_acpt[yy_state];
					if (YY_NOT_ACCEPT != yy_this_accept) {
						yy_last_accept_state = yy_state;
						yy_mark_end();
					}
				}
			}
		}
	}
}
