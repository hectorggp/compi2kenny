import A;

class B extends A{
	int a;
	int z;	
	int y;
	int mat [4, 5, 5];
	float matf [3, 6];
	
	public B(){
		z = 6;
		mat[2, 3, 4] = 24;
		matf[1, 4] = add(2.2, 3.3) + 3.43 * 3.3;
	}
	
	public float add(float a, float b){
		print(z);
		return a + b;
	}
}
