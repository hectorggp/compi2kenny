class C{
	void main(){
		print("ola q ase");
		m(3);
	}

	private void m(int par){
		if(par > 2)
			print("es mayor q 2");
		else
			print("no es mayor q 2");
		print(par);
		print(suma(par, ((float) par) * 2.2));
	}

	private int suma(int a, float b){
		return a + (int) b;
	}
}


























