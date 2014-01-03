incluir miguel;

clase kenny hereda miguel {
	privada entero ent;
	privada cadena cad;
	privada decimal dec;
	privada booleano bool;
	privada caracter car;
	privada entero mat ¿4?¿ 5?¿ 5?;

	publica normal main(){
		ent mat2 ¿3?¿4?;
		ent = 2;
		cad = \ola\;
		dec = 2.32;
		bool = falso;
		car = _c_;
		mat ¿1?¿1?¿1? = -32;
		ent = suma (-2, -143);
		imprimir (\la respuesta es\);
		imprimir (ent);
		& a = 20;
		& error porque a no es pública
		entero a = 20;
		entero b = 4;
		si (b # a) {
			imprimir (\igual\);
		} no si(b < a)  {
			imprimir (\menor\);
		} no {
			imprimir(\mayor o igual\);
		}

		mientras (a > b) {
			imprimir(a);
			si (a==18)
				salir;
			a--;
		}
		imprimir (resta(3, 2));

		entero asdf;
		entero fdsa;
		asdf = 44;
		fdsa = 24;
		si(mayor(asdf, fdsa))
			imprimir(\es mayor!\);
		no 
			imprimir(\es menor!\);
		imprimir(asdf);
		imprimir(fdsa);
[
esto es un comentario
fasfkajs f
sdfkja 
]

&esto también
	}

	entero resta(entero c, entero b){
		retorna c + b;
	}

	privada entero suma (entero a, entero b) {
		entero ret = a + b;
&		imprimir (\la respuesta es\);
&		imprimir (ret);
		retorna ret;
	}

	booleano mayor(entero ref x, entero ref y) {
		x = x + 3;
		y = y + 3;
		retorna (x > y);
	}

}

