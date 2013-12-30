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
		84,
		86,
		88,
		90
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
		/* 79 */ YY_NO_ANCHOR,
		/* 80 */ YY_NO_ANCHOR,
		/* 81 */ YY_NO_ANCHOR,
		/* 82 */ YY_NOT_ACCEPT,
		/* 83 */ YY_NO_ANCHOR,
		/* 84 */ YY_NOT_ACCEPT,
		/* 85 */ YY_NO_ANCHOR,
		/* 86 */ YY_NOT_ACCEPT,
		/* 87 */ YY_NO_ANCHOR,
		/* 88 */ YY_NOT_ACCEPT,
		/* 89 */ YY_NO_ANCHOR,
		/* 90 */ YY_NOT_ACCEPT,
		/* 91 */ YY_NO_ANCHOR,
		/* 92 */ YY_NO_ANCHOR,
		/* 93 */ YY_NO_ANCHOR,
		/* 94 */ YY_NO_ANCHOR,
		/* 95 */ YY_NO_ANCHOR,
		/* 96 */ YY_NO_ANCHOR,
		/* 97 */ YY_NO_ANCHOR,
		/* 98 */ YY_NO_ANCHOR,
		/* 99 */ YY_NO_ANCHOR,
		/* 100 */ YY_NO_ANCHOR,
		/* 101 */ YY_NO_ANCHOR,
		/* 102 */ YY_NO_ANCHOR,
		/* 103 */ YY_NO_ANCHOR,
		/* 104 */ YY_NO_ANCHOR,
		/* 105 */ YY_NO_ANCHOR,
		/* 106 */ YY_NO_ANCHOR,
		/* 107 */ YY_NO_ANCHOR,
		/* 108 */ YY_NO_ANCHOR,
		/* 109 */ YY_NO_ANCHOR,
		/* 110 */ YY_NO_ANCHOR,
		/* 111 */ YY_NO_ANCHOR,
		/* 112 */ YY_NO_ANCHOR,
		/* 113 */ YY_NO_ANCHOR,
		/* 114 */ YY_NO_ANCHOR,
		/* 115 */ YY_NO_ANCHOR,
		/* 116 */ YY_NO_ANCHOR,
		/* 117 */ YY_NO_ANCHOR,
		/* 118 */ YY_NO_ANCHOR,
		/* 119 */ YY_NO_ANCHOR,
		/* 120 */ YY_NO_ANCHOR,
		/* 121 */ YY_NO_ANCHOR,
		/* 122 */ YY_NO_ANCHOR,
		/* 123 */ YY_NO_ANCHOR,
		/* 124 */ YY_NO_ANCHOR,
		/* 125 */ YY_NO_ANCHOR,
		/* 126 */ YY_NO_ANCHOR,
		/* 127 */ YY_NO_ANCHOR,
		/* 128 */ YY_NO_ANCHOR,
		/* 129 */ YY_NO_ANCHOR,
		/* 130 */ YY_NO_ANCHOR,
		/* 131 */ YY_NO_ANCHOR,
		/* 132 */ YY_NO_ANCHOR,
		/* 133 */ YY_NO_ANCHOR,
		/* 134 */ YY_NO_ANCHOR,
		/* 135 */ YY_NO_ANCHOR,
		/* 136 */ YY_NO_ANCHOR,
		/* 137 */ YY_NO_ANCHOR,
		/* 138 */ YY_NO_ANCHOR,
		/* 139 */ YY_NO_ANCHOR,
		/* 140 */ YY_NO_ANCHOR,
		/* 141 */ YY_NO_ANCHOR,
		/* 142 */ YY_NO_ANCHOR,
		/* 143 */ YY_NO_ANCHOR,
		/* 144 */ YY_NO_ANCHOR,
		/* 145 */ YY_NO_ANCHOR,
		/* 146 */ YY_NO_ANCHOR,
		/* 147 */ YY_NO_ANCHOR,
		/* 148 */ YY_NO_ANCHOR,
		/* 149 */ YY_NO_ANCHOR,
		/* 150 */ YY_NO_ANCHOR,
		/* 151 */ YY_NO_ANCHOR,
		/* 152 */ YY_NO_ANCHOR,
		/* 153 */ YY_NO_ANCHOR,
		/* 154 */ YY_NO_ANCHOR,
		/* 155 */ YY_NO_ANCHOR,
		/* 156 */ YY_NO_ANCHOR,
		/* 157 */ YY_NO_ANCHOR,
		/* 158 */ YY_NO_ANCHOR,
		/* 159 */ YY_NO_ANCHOR,
		/* 160 */ YY_NO_ANCHOR,
		/* 161 */ YY_NO_ANCHOR,
		/* 162 */ YY_NO_ANCHOR,
		/* 163 */ YY_NO_ANCHOR,
		/* 164 */ YY_NO_ANCHOR,
		/* 165 */ YY_NO_ANCHOR,
		/* 166 */ YY_NO_ANCHOR,
		/* 167 */ YY_NO_ANCHOR,
		/* 168 */ YY_NO_ANCHOR,
		/* 169 */ YY_NO_ANCHOR,
		/* 170 */ YY_NO_ANCHOR,
		/* 171 */ YY_NO_ANCHOR,
		/* 172 */ YY_NO_ANCHOR,
		/* 173 */ YY_NO_ANCHOR,
		/* 174 */ YY_NO_ANCHOR,
		/* 175 */ YY_NO_ANCHOR,
		/* 176 */ YY_NO_ANCHOR,
		/* 177 */ YY_NO_ANCHOR,
		/* 178 */ YY_NO_ANCHOR,
		/* 179 */ YY_NO_ANCHOR,
		/* 180 */ YY_NO_ANCHOR,
		/* 181 */ YY_NO_ANCHOR,
		/* 182 */ YY_NO_ANCHOR,
		/* 183 */ YY_NO_ANCHOR,
		/* 184 */ YY_NO_ANCHOR,
		/* 185 */ YY_NO_ANCHOR,
		/* 186 */ YY_NO_ANCHOR,
		/* 187 */ YY_NO_ANCHOR,
		/* 188 */ YY_NO_ANCHOR,
		/* 189 */ YY_NO_ANCHOR,
		/* 190 */ YY_NO_ANCHOR,
		/* 191 */ YY_NO_ANCHOR,
		/* 192 */ YY_NO_ANCHOR,
		/* 193 */ YY_NO_ANCHOR,
		/* 194 */ YY_NO_ANCHOR,
		/* 195 */ YY_NO_ANCHOR,
		/* 196 */ YY_NO_ANCHOR,
		/* 197 */ YY_NO_ANCHOR,
		/* 198 */ YY_NO_ANCHOR,
		/* 199 */ YY_NO_ANCHOR,
		/* 200 */ YY_NO_ANCHOR,
		/* 201 */ YY_NO_ANCHOR,
		/* 202 */ YY_NO_ANCHOR,
		/* 203 */ YY_NO_ANCHOR,
		/* 204 */ YY_NO_ANCHOR,
		/* 205 */ YY_NO_ANCHOR
	};
	private int yy_cmap[] = unpackFromString(1,65538,
"29:9,53,28,29,53,28,29:18,53,29:2,22,29,14,30,29,1,2,12,11,15,9,8,13,51:10," +
"16,5,23,17,10,7,29,52:13,21,20,52:9,18,52,27,26,25,19,24,29,38,40,37,39,32," +
"45,50,49,42,52:2,41,43,33,36,47,52,35,46,34,44,48,52:4,3,31,4,29:65,6,29:65" +
"344,0:2")[0];

	private int yy_rmap[] = unpackFromString(1,206,
"0,1:9,2,3,4,5,1,6,1:2,7,8,1,8,9,1,10,1:4,11,1,12,1:10,8,1,8:2,13,14,8:26,15" +
",1:4,16,1:2,14,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36," +
"37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61," +
"62,63,64,65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86," +
"87,88,89,90,91,92,93,94,95,96,97,98,99,100,101,102,103,104,105,106,107,108," +
"109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127" +
",128,129,130,131,132,133,134,8,135,136,137,138")[0];

	private int yy_nxt[][] = unpackFromString(139,54,
"1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28," +
"29,26,30,83,85,87,143,197,201,144,201,89,202,201,203,204,201,165,91,145,146" +
",205,201,31,201,32,-1:63,33,34,-1:6,35,-1:53,36,-1:47,37,-1:5,38,-1:53,39,-" +
"1:53,40,-1:53,41,-1:54,201,-1,201:2,-1:9,201:22,-1:19,201,-1,42,201,-1:9,20" +
"1:22,-1:18,43,-1:64,29,-1:2,29,-1:30,82,-1:42,31,-1:20,201,-1,201:2,-1:9,20" +
"1:2,96,201:19,-1:52,47,-1:3,74:25,-1,74:27,-1:28,79,-1:2,79,-1:40,201,-1,20" +
"1:2,-1:6,29,-1:2,83,201:21,-1,1,74:25,75,74:27,-1:18,201,-1,201:2,-1:9,201:" +
"2,166,201:19,-1,1,76:23,77,76:29,-1:18,201,-1,201:2,-1:9,201:5,44,201:5,167" +
",201,118,201:8,-1,1,78:27,79,78:2,79,78:22,-1:18,201,-1,201:2,-1:9,201,169," +
"201:3,45,201:16,-1,1,80:24,81,80:28,-1:18,201,-1,201:2,-1:9,201,173,201:5,1" +
"49,201:3,46,201:10,-1:19,201,-1,201:2,-1:9,201:5,48,201:16,-1:19,201,-1,201" +
":2,-1:9,201,49,201:20,-1:19,201,-1,201:2,-1:9,201:15,50,201:6,-1:19,201,-1," +
"201:2,-1:9,201:5,51,201:16,-1:19,201,-1,201:2,-1:9,201:5,52,201:16,-1:19,20" +
"1,-1,201:2,-1:9,201:7,53,201:14,-1:19,201,-1,201:2,-1:9,201:8,54,201:13,-1:" +
"19,201,-1,201:2,-1:9,201:5,55,201:16,-1:19,201,-1,201:2,-1:9,201,56,201:20," +
"-1:19,201,-1,201:2,-1:9,201,57,201:20,-1:19,201,-1,201:2,-1:9,201:4,58,201:" +
"17,-1:19,201,-1,201:2,-1:9,201:5,59,201:16,-1:19,201,-1,201:2,-1:9,201:7,60" +
",201:14,-1:19,201,-1,201:2,-1:9,201:7,61,201:14,-1:19,201,-1,201:2,-1:9,201" +
":5,62,201:16,-1:19,201,-1,201:2,-1:9,201:7,63,201:14,-1:19,201,-1,201:2,-1:" +
"9,201:10,64,201:11,-1:19,201,-1,201:2,-1:9,201:4,65,201:17,-1:19,201,-1,201" +
":2,-1:9,201:7,66,201:14,-1:19,201,-1,201:2,-1:9,201:7,67,201:14,-1:19,201,-" +
"1,201:2,-1:9,201:4,68,201:17,-1:19,201,-1,201:2,-1:9,201:5,69,201:16,-1:19," +
"201,-1,201:2,-1:9,201:4,70,201:17,-1:19,201,-1,201:2,-1:9,201:15,71,201:6,-" +
"1:19,201,-1,201:2,-1:9,201:8,72,201:13,-1:19,201,-1,201:2,-1:9,201:4,73,201" +
":17,-1:19,201,-1,201:2,-1:9,201,124,201:8,92,201:11,-1:19,201,-1,201:2,-1:9" +
",201:13,93,201:8,-1:19,201,-1,201:2,-1:9,201:11,94,201:10,-1:19,201,-1,201:" +
"2,-1:9,201:4,179,201:3,151,201:6,95,201:6,-1:19,201,-1,201:2,-1:9,201:4,97," +
"201:17,-1:19,201,-1,201:2,-1:9,201:11,98,201:10,-1:19,201,-1,201:2,-1:9,201" +
":17,99,201:4,-1:19,201,-1,201:2,-1:9,201:15,100,201:6,-1:19,201,-1,201:2,-1" +
":9,201:15,101,201:6,-1:19,201,-1,201:2,-1:9,201:11,102,201:10,-1:19,201,-1," +
"201:2,-1:9,201:4,103,201:17,-1:19,201,-1,201:2,-1:9,201:2,104,201:19,-1:19," +
"201,-1,201:2,-1:9,201:8,105,201:13,-1:19,201,-1,201:2,-1:9,201:2,106,201:19" +
",-1:19,201,-1,201:2,-1:9,201:2,107,201:19,-1:19,201,-1,201:2,-1:9,201:7,108" +
",201:14,-1:19,201,-1,201:2,-1:9,201:11,109,201:10,-1:19,201,-1,201:2,-1:9,2" +
"01:8,110,201:13,-1:19,201,-1,201:2,-1:9,201:6,111,201:15,-1:19,201,-1,201:2" +
",-1:9,201,112,201:20,-1:19,201,-1,201:2,-1:9,201:2,113,201:19,-1:19,201,-1," +
"201:2,-1:9,201:11,114,201:10,-1:19,201,-1,201:2,-1:9,201:7,115,201:14,-1:19" +
",201,-1,201:2,-1:9,201,116,201:20,-1:19,201,-1,201:2,-1:9,201:7,117,201:14," +
"-1:19,201,-1,201:2,-1:9,201:4,119,201:13,120,201:3,-1:19,201,-1,201:2,-1:9," +
"201:7,121,201:2,147,201:11,-1:19,201,-1,201:2,-1:9,201:4,174,201:2,122,201:" +
"5,175,201:8,-1:19,201,-1,201:2,-1:9,201:5,123,201:16,-1:19,201,-1,201:2,-1:" +
"9,201:7,125,201:14,-1:19,201,-1,201:2,-1:9,201:10,126,201:11,-1:19,201,-1,2" +
"01:2,-1:9,201:10,127,201:11,-1:19,201,-1,201:2,-1:9,201,128,201:20,-1:19,20" +
"1,-1,201:2,-1:9,201,129,201:20,-1:19,201,-1,201:2,-1:9,201,130,201:20,-1:19" +
",201,-1,201:2,-1:9,201:13,131,201:8,-1:19,201,-1,201:2,-1:9,201:4,132,201:1" +
"7,-1:19,201,-1,201:2,-1:9,201:12,133,201:9,-1:19,201,-1,201:2,-1:9,201:13,1" +
"34,201:8,-1:19,201,-1,201:2,-1:9,201:7,135,201:14,-1:19,201,-1,201:2,-1:9,2" +
"01:11,136,201:10,-1:19,201,-1,201:2,-1:9,201:3,137,201:18,-1:19,201,-1,201:" +
"2,-1:9,201:7,138,201:14,-1:19,201,-1,201:2,-1:9,201:12,139,201:9,-1:19,201," +
"-1,201:2,-1:9,201:4,140,201:17,-1:19,201,-1,201:2,-1:9,201:3,141,201:18,-1:" +
"19,201,-1,201:2,-1:9,201:2,142,201:19,-1:19,201,-1,201:2,-1:9,201:7,148,201" +
":14,-1:19,201,-1,201:2,-1:9,201:3,150,201:18,-1:19,201,-1,201:2,-1:9,201:2," +
"177,201:19,-1:19,201,-1,201:2,-1:9,201:3,178,201:18,-1:19,201,-1,201:2,-1:9" +
",201:6,180,201:15,-1:19,201,-1,201:2,-1:9,201:5,199,201:16,-1:19,201,-1,201" +
":2,-1:9,201:16,182,201:5,-1:19,201,-1,201:2,-1:9,201,183,201:20,-1:19,201,-" +
"1,201:2,-1:9,201:10,184,201:11,-1:19,201,-1,201:2,-1:9,201:5,185,201:5,186," +
"201:10,-1:19,201,-1,201:2,-1:9,201:9,187,201:12,-1:19,201,-1,201:2,-1:9,201" +
":4,152,201:17,-1:19,201,-1,201:2,-1:9,201:19,153,201:2,-1:19,201,-1,201:2,-" +
"1:9,201:5,154,201:16,-1:19,201,-1,201:2,-1:9,201:7,188,201:14,-1:19,201,-1," +
"201:2,-1:9,201:11,155,201:10,-1:19,201,-1,201:2,-1:9,201:10,156,201:11,-1:1" +
"9,201,-1,201:2,-1:9,201:4,190,201:17,-1:19,201,-1,201:2,-1:9,201:2,191,201:" +
"19,-1:19,201,-1,201:2,-1:9,201,192,201:20,-1:19,201,-1,201:2,-1:9,201:3,193" +
",201:18,-1:19,201,-1,201:2,-1:9,201:17,157,201:4,-1:19,201,-1,201:2,-1:9,20" +
"1:10,158,201:11,-1:19,201,-1,201:2,-1:9,201:6,159,201:15,-1:19,201,-1,201:2" +
",-1:9,201,160,201:20,-1:19,201,-1,201:2,-1:9,201:11,161,201:10,-1:19,201,-1" +
",201:2,-1:9,201:3,162,201:18,-1:19,201,-1,201:2,-1:9,201:6,200,201:15,-1:19" +
",201,-1,201:2,-1:9,201,194,201:20,-1:19,201,-1,201:2,-1:9,201:6,163,201:15," +
"-1:19,201,-1,201:2,-1:9,201:11,196,201:10,-1:19,201,-1,201:2,-1:9,201:5,164" +
",201:16,-1:19,201,-1,201:2,-1:9,201,168,201:20,-1:19,201,-1,201:2,-1:9,201:" +
"6,181,201:15,-1:19,201,-1,201:2,-1:9,201:10,189,201:11,-1:19,201,-1,201:2,-" +
"1:9,201:6,195,201:15,-1:19,201,-1,201:2,-1:9,201:5,170,201:16,-1:19,201,-1," +
"201:2,-1:9,201:2,198,201:9,171,201:9,-1:19,201,-1,201:2,-1:9,201:11,172,201" +
":10,-1:19,201,-1,201:2,-1:9,201,176,201:20,-1");

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
						{ return sym(ParserSym.MENOS); }
					case -11:
						break;
					case 11:
						{ return sym(ParserSym.COMPARA); }
					case -12:
						break;
					case 12:
						{ return sym(ParserSym.MAS); }
					case -13:
						break;
					case 13:
						{ return sym(ParserSym.MUL); }
					case -14:
						break;
					case 14:
						{ return sym(ParserSym.DIV); }
					case -15:
						break;
					case 15:
						{ return sym(ParserSym.MOD); }
					case -16:
						break;
					case 16:
						{ return sym(ParserSym.COMA); }
					case -17:
						break;
					case 17:
						{ return sym(ParserSym.DOSPUNTOS); }
					case -18:
						break;
					case 18:
						{ return sym(ParserSym.ASIGNA); }
					case -19:
						break;
					case 19:
						{ return sym(ParserSym.AND); }
					case -20:
						break;
					case 20:
						{ return sym(ParserSym.XOR); }
					case -21:
						break;
					case 21:
						{ return sym(ParserSym.OR); }
					case -22:
						break;
					case 22:
						{ return sym(ParserSym.ID); }
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
						{ yybegin(CHAR); return sym(ParserSym.COMILLA); }
					case -26:
						break;
					case 26:
						{ error(); }
					case -27:
						break;
					case 27:
						{ yybegin(STRING); return sym(ParserSym.COMILLAS); }
					case -28:
						break;
					case 28:
						{ yybegin(COMENTM); }
					case -29:
						break;
					case 29:
						{ /* ignorar */ }
					case -30:
						break;
					case 30:
						{ yybegin(COMENT); }
					case -31:
						break;
					case 31:
						{ return sym(ParserSym.VINT); }
					case -32:
						break;
					case 32:
						{ /* ignorar */ }
					case -33:
						break;
					case 33:
						{ return sym(ParserSym.MINUSMINUS); }
					case -34:
						break;
					case 34:
						{ return sym(ParserSym.PUNTO); }
					case -35:
						break;
					case 35:
						{ return sym(ParserSym.MENOSIGUAL); }
					case -36:
						break;
					case 36:
						{ return sym(ParserSym.COMPARA); }
					case -37:
						break;
					case 37:
						{ return sym(ParserSym.PLUSPLUS); }
					case -38:
						break;
					case 38:
						{ return sym(ParserSym.MASIGUAL); }
					case -39:
						break;
					case 39:
						{ return sym(ParserSym.MULIGUAL); }
					case -40:
						break;
					case 40:
						{ return sym(ParserSym.MODIGUAL); }
					case -41:
						break;
					case 41:
						{ return sym(ParserSym.COMPARA); }
					case -42:
						break;
					case 42:
						{ return sym(ParserSym.NOT); }
					case -43:
						break;
					case 43:
						{ return sym(ParserSym.COMPARA); }
					case -44:
						break;
					case 44:
						{ return sym(ParserSym.ELSE); }
					case -45:
						break;
					case 45:
						{ return sym(ParserSym.DO); }
					case -46:
						break;
					case 46:
						{ return sym(ParserSym.IF); }
					case -47:
						break;
					case 47:
						{ return sym(ParserSym.VFLOAT); }
					case -48:
						break;
					case 48:
						{ return sym(ParserSym.NULL); }
					case -49:
						break;
					case 49:
						{ return sym(ParserSym.VBOOLEAN); }
					case -50:
						break;
					case 50:
						{ return sym(ParserSym.THIS); }
					case -51:
						break;
					case 51:
						{ return sym(ParserSym.CASE); }
					case -52:
						break;
					case 52:
						{ return sym(ParserSym.ELSEIF); }
					case -53:
						break;
					case 53:
						{ return sym(ParserSym.FOR); }
					case -54:
						break;
					case 54:
						{ return sym(ParserSym.VOID); }
					case -55:
						break;
					case 55:
						{ return sym(ParserSym.NEW); }
					case -56:
						break;
					case 56:
						{ return sym(ParserSym.CLASS); }
					case -57:
						break;
					case 57:
						{ return sym(ParserSym.VBOOLEAN); }
					case -58:
						break;
					case 58:
						{ return sym(ParserSym.BREAK); }
					case -59:
						break;
					case 59:
						{ return sym(ParserSym.INT); }
					case -60:
						break;
					case 60:
						{ return sym(ParserSym.STRING); }
					case -61:
						break;
					case 61:
						{ return sym(ParserSym.EXTENDS); }
					case -62:
						break;
					case 62:
						{ return sym(ParserSym.DEFAULT); }
					case -63:
						break;
					case 63:
						{ return sym(ParserSym.RETURN); }
					case -64:
						break;
					case 64:
						{ return sym(ParserSym.FLOAT); }
					case -65:
						break;
					case 65:
						{ return sym(ParserSym.IMPORT); }
					case -66:
						break;
					case 66:
						{ return sym(ParserSym.PRIVATE); }
					case -67:
						break;
					case 67:
						{ return sym(ParserSym.PUBLIC); }
					case -68:
						break;
					case 68:
						{ return sym(ParserSym.CHAR); }
					case -69:
						break;
					case 69:
						{ return sym(ParserSym.BOOLEAN); }
					case -70:
						break;
					case 70:
						{ return sym(ParserSym.PRINT); }
					case -71:
						break;
					case 71:
						{ return sym(ParserSym.WHILE); }
					case -72:
						break;
					case 72:
						{ return sym(ParserSym.PROTECTED); }
					case -73:
						break;
					case 73:
						{ return sym(ParserSym.SWITCH); }
					case -74:
						break;
					case 74:
						{ return sym(ParserSym.VSTRING); }
					case -75:
						break;
					case 75:
						{ yybegin(YYINITIAL); return sym(ParserSym.COMILLAS); }
					case -76:
						break;
					case 76:
						{ return sym(ParserSym.VCHAR); }
					case -77:
						break;
					case 77:
						{ yybegin(YYINITIAL); return sym(ParserSym.COMILLA); }
					case -78:
						break;
					case 78:
						{ /* ignorar */ }
					case -79:
						break;
					case 79:
						{ yybegin(YYINITIAL); }
					case -80:
						break;
					case 80:
						{ /* ignorar */ }
					case -81:
						break;
					case 81:
						{ yybegin(YYINITIAL); }
					case -82:
						break;
					case 83:
						{ return sym(ParserSym.ID); }
					case -83:
						break;
					case 85:
						{ return sym(ParserSym.ID); }
					case -84:
						break;
					case 87:
						{ return sym(ParserSym.ID); }
					case -85:
						break;
					case 89:
						{ return sym(ParserSym.ID); }
					case -86:
						break;
					case 91:
						{ return sym(ParserSym.ID); }
					case -87:
						break;
					case 92:
						{ return sym(ParserSym.ID); }
					case -88:
						break;
					case 93:
						{ return sym(ParserSym.ID); }
					case -89:
						break;
					case 94:
						{ return sym(ParserSym.ID); }
					case -90:
						break;
					case 95:
						{ return sym(ParserSym.ID); }
					case -91:
						break;
					case 96:
						{ return sym(ParserSym.ID); }
					case -92:
						break;
					case 97:
						{ return sym(ParserSym.ID); }
					case -93:
						break;
					case 98:
						{ return sym(ParserSym.ID); }
					case -94:
						break;
					case 99:
						{ return sym(ParserSym.ID); }
					case -95:
						break;
					case 100:
						{ return sym(ParserSym.ID); }
					case -96:
						break;
					case 101:
						{ return sym(ParserSym.ID); }
					case -97:
						break;
					case 102:
						{ return sym(ParserSym.ID); }
					case -98:
						break;
					case 103:
						{ return sym(ParserSym.ID); }
					case -99:
						break;
					case 104:
						{ return sym(ParserSym.ID); }
					case -100:
						break;
					case 105:
						{ return sym(ParserSym.ID); }
					case -101:
						break;
					case 106:
						{ return sym(ParserSym.ID); }
					case -102:
						break;
					case 107:
						{ return sym(ParserSym.ID); }
					case -103:
						break;
					case 108:
						{ return sym(ParserSym.ID); }
					case -104:
						break;
					case 109:
						{ return sym(ParserSym.ID); }
					case -105:
						break;
					case 110:
						{ return sym(ParserSym.ID); }
					case -106:
						break;
					case 111:
						{ return sym(ParserSym.ID); }
					case -107:
						break;
					case 112:
						{ return sym(ParserSym.ID); }
					case -108:
						break;
					case 113:
						{ return sym(ParserSym.ID); }
					case -109:
						break;
					case 114:
						{ return sym(ParserSym.ID); }
					case -110:
						break;
					case 115:
						{ return sym(ParserSym.ID); }
					case -111:
						break;
					case 116:
						{ return sym(ParserSym.ID); }
					case -112:
						break;
					case 117:
						{ return sym(ParserSym.ID); }
					case -113:
						break;
					case 118:
						{ return sym(ParserSym.ID); }
					case -114:
						break;
					case 119:
						{ return sym(ParserSym.ID); }
					case -115:
						break;
					case 120:
						{ return sym(ParserSym.ID); }
					case -116:
						break;
					case 121:
						{ return sym(ParserSym.ID); }
					case -117:
						break;
					case 122:
						{ return sym(ParserSym.ID); }
					case -118:
						break;
					case 123:
						{ return sym(ParserSym.ID); }
					case -119:
						break;
					case 124:
						{ return sym(ParserSym.ID); }
					case -120:
						break;
					case 125:
						{ return sym(ParserSym.ID); }
					case -121:
						break;
					case 126:
						{ return sym(ParserSym.ID); }
					case -122:
						break;
					case 127:
						{ return sym(ParserSym.ID); }
					case -123:
						break;
					case 128:
						{ return sym(ParserSym.ID); }
					case -124:
						break;
					case 129:
						{ return sym(ParserSym.ID); }
					case -125:
						break;
					case 130:
						{ return sym(ParserSym.ID); }
					case -126:
						break;
					case 131:
						{ return sym(ParserSym.ID); }
					case -127:
						break;
					case 132:
						{ return sym(ParserSym.ID); }
					case -128:
						break;
					case 133:
						{ return sym(ParserSym.ID); }
					case -129:
						break;
					case 134:
						{ return sym(ParserSym.ID); }
					case -130:
						break;
					case 135:
						{ return sym(ParserSym.ID); }
					case -131:
						break;
					case 136:
						{ return sym(ParserSym.ID); }
					case -132:
						break;
					case 137:
						{ return sym(ParserSym.ID); }
					case -133:
						break;
					case 138:
						{ return sym(ParserSym.ID); }
					case -134:
						break;
					case 139:
						{ return sym(ParserSym.ID); }
					case -135:
						break;
					case 140:
						{ return sym(ParserSym.ID); }
					case -136:
						break;
					case 141:
						{ return sym(ParserSym.ID); }
					case -137:
						break;
					case 142:
						{ return sym(ParserSym.ID); }
					case -138:
						break;
					case 143:
						{ return sym(ParserSym.ID); }
					case -139:
						break;
					case 144:
						{ return sym(ParserSym.ID); }
					case -140:
						break;
					case 145:
						{ return sym(ParserSym.ID); }
					case -141:
						break;
					case 146:
						{ return sym(ParserSym.ID); }
					case -142:
						break;
					case 147:
						{ return sym(ParserSym.ID); }
					case -143:
						break;
					case 148:
						{ return sym(ParserSym.ID); }
					case -144:
						break;
					case 149:
						{ return sym(ParserSym.ID); }
					case -145:
						break;
					case 150:
						{ return sym(ParserSym.ID); }
					case -146:
						break;
					case 151:
						{ return sym(ParserSym.ID); }
					case -147:
						break;
					case 152:
						{ return sym(ParserSym.ID); }
					case -148:
						break;
					case 153:
						{ return sym(ParserSym.ID); }
					case -149:
						break;
					case 154:
						{ return sym(ParserSym.ID); }
					case -150:
						break;
					case 155:
						{ return sym(ParserSym.ID); }
					case -151:
						break;
					case 156:
						{ return sym(ParserSym.ID); }
					case -152:
						break;
					case 157:
						{ return sym(ParserSym.ID); }
					case -153:
						break;
					case 158:
						{ return sym(ParserSym.ID); }
					case -154:
						break;
					case 159:
						{ return sym(ParserSym.ID); }
					case -155:
						break;
					case 160:
						{ return sym(ParserSym.ID); }
					case -156:
						break;
					case 161:
						{ return sym(ParserSym.ID); }
					case -157:
						break;
					case 162:
						{ return sym(ParserSym.ID); }
					case -158:
						break;
					case 163:
						{ return sym(ParserSym.ID); }
					case -159:
						break;
					case 164:
						{ return sym(ParserSym.ID); }
					case -160:
						break;
					case 165:
						{ return sym(ParserSym.ID); }
					case -161:
						break;
					case 166:
						{ return sym(ParserSym.ID); }
					case -162:
						break;
					case 167:
						{ return sym(ParserSym.ID); }
					case -163:
						break;
					case 168:
						{ return sym(ParserSym.ID); }
					case -164:
						break;
					case 169:
						{ return sym(ParserSym.ID); }
					case -165:
						break;
					case 170:
						{ return sym(ParserSym.ID); }
					case -166:
						break;
					case 171:
						{ return sym(ParserSym.ID); }
					case -167:
						break;
					case 172:
						{ return sym(ParserSym.ID); }
					case -168:
						break;
					case 173:
						{ return sym(ParserSym.ID); }
					case -169:
						break;
					case 174:
						{ return sym(ParserSym.ID); }
					case -170:
						break;
					case 175:
						{ return sym(ParserSym.ID); }
					case -171:
						break;
					case 176:
						{ return sym(ParserSym.ID); }
					case -172:
						break;
					case 177:
						{ return sym(ParserSym.ID); }
					case -173:
						break;
					case 178:
						{ return sym(ParserSym.ID); }
					case -174:
						break;
					case 179:
						{ return sym(ParserSym.ID); }
					case -175:
						break;
					case 180:
						{ return sym(ParserSym.ID); }
					case -176:
						break;
					case 181:
						{ return sym(ParserSym.ID); }
					case -177:
						break;
					case 182:
						{ return sym(ParserSym.ID); }
					case -178:
						break;
					case 183:
						{ return sym(ParserSym.ID); }
					case -179:
						break;
					case 184:
						{ return sym(ParserSym.ID); }
					case -180:
						break;
					case 185:
						{ return sym(ParserSym.ID); }
					case -181:
						break;
					case 186:
						{ return sym(ParserSym.ID); }
					case -182:
						break;
					case 187:
						{ return sym(ParserSym.ID); }
					case -183:
						break;
					case 188:
						{ return sym(ParserSym.ID); }
					case -184:
						break;
					case 189:
						{ return sym(ParserSym.ID); }
					case -185:
						break;
					case 190:
						{ return sym(ParserSym.ID); }
					case -186:
						break;
					case 191:
						{ return sym(ParserSym.ID); }
					case -187:
						break;
					case 192:
						{ return sym(ParserSym.ID); }
					case -188:
						break;
					case 193:
						{ return sym(ParserSym.ID); }
					case -189:
						break;
					case 194:
						{ return sym(ParserSym.ID); }
					case -190:
						break;
					case 195:
						{ return sym(ParserSym.ID); }
					case -191:
						break;
					case 196:
						{ return sym(ParserSym.ID); }
					case -192:
						break;
					case 197:
						{ return sym(ParserSym.ID); }
					case -193:
						break;
					case 198:
						{ return sym(ParserSym.ID); }
					case -194:
						break;
					case 199:
						{ return sym(ParserSym.ID); }
					case -195:
						break;
					case 200:
						{ return sym(ParserSym.ID); }
					case -196:
						break;
					case 201:
						{ return sym(ParserSym.ID); }
					case -197:
						break;
					case 202:
						{ return sym(ParserSym.ID); }
					case -198:
						break;
					case 203:
						{ return sym(ParserSym.ID); }
					case -199:
						break;
					case 204:
						{ return sym(ParserSym.ID); }
					case -200:
						break;
					case 205:
						{ return sym(ParserSym.ID); }
					case -201:
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
