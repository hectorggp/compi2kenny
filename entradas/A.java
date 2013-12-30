import B;

class A extends B {
	private B be;
	private int a;

	public void main(){
		be = new B();
		be.add(2.2,3.3);
		A insA = new A(3);
		print(insA.mat[2, 3, 2]);
		int i = 0;
		while(true){
			print(i);
			i += 8;
			if(i > 32)
				break;
		}
		print("termina while");
		i = 1;
		for(int j = 0; j < 4; j++)
			for(i = 0; i < 5; ++i)
				switch(i+j){
					case 2: print("es dos"); break;
					case 5: print("es cinco"); break;
					case 7: print("es siete"); break;
					default: print("es otro valor");
				}
		print("termina for");
		print();
		i = 1;
		do{
			print(i);
			if (i-- == -2)
				break;
		} while (true);
		print("termina dowhile");
		print();
		A asd = new A(3);
		print(new B().mat[2, 3, 4]);
		print("eso era una matriz");
		print();
		new B().add(2.2,3.3);
		print("alamadre");
		mat[3, 4, 4] = 3;
		print(mat[3, 4, 4]++);
		print(mat[3, 4, 4]);
		print("esto nada q ver va");
		print(be.matf[1, 4]);
		print(mat[4,5,5]);
	}
	
	public A(int v){
		mat[2, 3, 2] = v;
		// asdf();
	}
	
	private void exception(){
	
		print("¡Error en tiempo de ejecución! Dimensiones fuera de rango.");
	}
}

