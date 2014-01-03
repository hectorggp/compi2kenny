#include <stdio.h>
#include "entradas3D.h"

int main(void){
	apstack = 0;
	apheap = 107;
	kenny_void_main();
}

void kenny_void_main() {
	t1 = stack[(int) apstack];
	t1 = t1 + 2;
	heap[(int) t1] = 2;
	t2 = stack[(int) apstack];
	t2 = t2 + 3;
	t3 = apheap;
	heap[(int) t3] = t3;
	apheap = apheap + 256;
	t4 = t3 + 1;
	heap[(int) t4] = 111;
	t4 = t3 + 2;
	heap[(int) t4] = 108;
	t4 = t3 + 3;
	heap[(int) t4] = 97;
	t4 = t3 + 4;
	heap[(int) t4] = 0;
	heap[(int) t2] = t3;
	t5 = stack[(int) apstack];
	t5 = t5 + 4;
	heap[(int) t5] = 2.32;
	t6 = stack[(int) apstack];
	t6 = t6 + 5;
	heap[(int) t6] = 0;
	t7 = stack[(int) apstack];
	t7 = t7 + 6;
	heap[(int) t7] = 99;
	t8 = stack[(int) apstack];
	t8 = t8 + 7;
	t9 = 1;
	t10 = t9 * 5;
	t9 = t10 + 1;
	t10 = t9 * 5;
	t9 = t10 + 1;
	t8 = t9 + t8;
	t11 = 0 - 32;
	heap[(int) t8] = t11;
	t12 = stack[(int) apstack];
	t12 = t12 + 2;
	t13 = 0 - 2;
	t14 = 0 - 143;
	t15 = stack[(int) apstack];
	// guardar temps en stack;
	t16 = apstack + 13;
	stack[(int) t16] = t12;
	t16 = apstack + 14;
	stack[(int) t16] = t13;
	t16 = apstack + 15;
	stack[(int) t16] = t14;
	t16 = apstack + 16;
	stack[(int) t16] = t15;
	// setear parametros por valor o referencia;
	t16 = apstack + 19;
	stack[(int) t16] = t13;
	t16 = apstack + 20;
	stack[(int) t16] = t14;
	// actualizar punteros en stack y heap;
	t16 = apstack + 17;
	stack[(int) t16] = t15;
	apstack = apstack + 17;
	// llamar al procedimiento;
	kenny_int_suma_int_int();
	// restablecer valor de apstack;
	apstack = apstack - 17;
	// tomar valor retorno;
	t17 = apstack + 18;
	// restablecer temporales;
	t16 = apstack + 16;
	t15 = stack[(int) t16];
	t16 = apstack + 15;
	t14 = stack[(int) t16];
	t16 = apstack + 14;
	t13 = stack[(int) t16];
	t16 = apstack + 13;
	t12 = stack[(int) t16];
	t17 = stack[(int) t17];
	heap[(int) t12] = t17;
	t18 = apheap;
	heap[(int) t18] = t18;
	apheap = apheap + 256;
	t19 = t18 + 1;
	heap[(int) t19] = 108;
	t19 = t18 + 2;
	heap[(int) t19] = 97;
	t19 = t18 + 3;
	heap[(int) t19] = 32;
	t19 = t18 + 4;
	heap[(int) t19] = 114;
	t19 = t18 + 5;
	heap[(int) t19] = 101;
	t19 = t18 + 6;
	heap[(int) t19] = 115;
	t19 = t18 + 7;
	heap[(int) t19] = 112;
	t19 = t18 + 8;
	heap[(int) t19] = 117;
	t19 = t18 + 9;
	heap[(int) t19] = 101;
	t19 = t18 + 10;
	heap[(int) t19] = 115;
	t19 = t18 + 11;
	heap[(int) t19] = 116;
	t19 = t18 + 12;
	heap[(int) t19] = 97;
	t19 = t18 + 13;
	heap[(int) t19] = 32;
	t19 = t18 + 14;
	heap[(int) t19] = 101;
	t19 = t18 + 15;
	heap[(int) t19] = 115;
	t19 = t18 + 16;
	heap[(int) t19] = 0;
	t20 = heap[(int) t18];
l282:
	t20 = t20 + 1;
	t18 = heap[(int) t20];
	if (t18 == 0) goto l283;
	printf("%c", (char)((int) t18));
	goto l282;
l283:
	printf("%c", (char)((int) 10));
	t21 = stack[(int) apstack];
	t21 = t21 + 2;
	t21 = heap[(int) t21];
	printf("%d", (int)t21);
	printf("%c", (char)((int) 10));
	t22 = apstack + 13;
	stack[(int) t22] = 20;
	t23 = apstack + 14;
	stack[(int) t23] = 4;
	t24 = apstack + 14;
	t25 = apstack + 13;
	t24 = stack[(int) t24];
	t25 = stack[(int) t25];
	t26 = 1;
	if (t24 == t25) goto l284;
	t26 = 0;
l284:
	if (t26 == 0) goto l285;
	t27 = apheap;
	heap[(int) t27] = t27;
	apheap = apheap + 256;
	t28 = t27 + 1;
	heap[(int) t28] = 105;
	t28 = t27 + 2;
	heap[(int) t28] = 103;
	t28 = t27 + 3;
	heap[(int) t28] = 117;
	t28 = t27 + 4;
	heap[(int) t28] = 97;
	t28 = t27 + 5;
	heap[(int) t28] = 108;
	t28 = t27 + 6;
	heap[(int) t28] = 0;
	t29 = heap[(int) t27];
l286:
	t29 = t29 + 1;
	t27 = heap[(int) t29];
	if (t27 == 0) goto l287;
	printf("%c", (char)((int) t27));
	goto l286;
l287:
	printf("%c", (char)((int) 10));
	goto l288;
l285:
	t30 = apstack + 14;
	t31 = apstack + 13;
	t30 = stack[(int) t30];
	t31 = stack[(int) t31];
	t32 = 1;
	if (t30 < t31) goto l289;
	t32 = 0;
l289:
	if (t32 == 0) goto l290;
	t33 = apheap;
	heap[(int) t33] = t33;
	apheap = apheap + 256;
	t34 = t33 + 1;
	heap[(int) t34] = 109;
	t34 = t33 + 2;
	heap[(int) t34] = 101;
	t34 = t33 + 3;
	heap[(int) t34] = 110;
	t34 = t33 + 4;
	heap[(int) t34] = 111;
	t34 = t33 + 5;
	heap[(int) t34] = 114;
	t34 = t33 + 6;
	heap[(int) t34] = 0;
	t35 = heap[(int) t33];
l291:
	t35 = t35 + 1;
	t33 = heap[(int) t35];
	if (t33 == 0) goto l292;
	printf("%c", (char)((int) t33));
	goto l291;
l292:
	printf("%c", (char)((int) 10));
	goto l293;
l290:
	t36 = apheap;
	heap[(int) t36] = t36;
	apheap = apheap + 256;
	t37 = t36 + 1;
	heap[(int) t37] = 109;
	t37 = t36 + 2;
	heap[(int) t37] = 97;
	t37 = t36 + 3;
	heap[(int) t37] = 121;
	t37 = t36 + 4;
	heap[(int) t37] = 111;
	t37 = t36 + 5;
	heap[(int) t37] = 114;
	t37 = t36 + 6;
	heap[(int) t37] = 32;
	t37 = t36 + 7;
	heap[(int) t37] = 111;
	t37 = t36 + 8;
	heap[(int) t37] = 32;
	t37 = t36 + 9;
	heap[(int) t37] = 105;
	t37 = t36 + 10;
	heap[(int) t37] = 103;
	t37 = t36 + 11;
	heap[(int) t37] = 117;
	t37 = t36 + 12;
	heap[(int) t37] = 97;
	t37 = t36 + 13;
	heap[(int) t37] = 108;
	t37 = t36 + 14;
	heap[(int) t37] = 0;
	t38 = heap[(int) t36];
l294:
	t38 = t38 + 1;
	t36 = heap[(int) t38];
	if (t36 == 0) goto l295;
	printf("%c", (char)((int) t36));
	goto l294;
l295:
	printf("%c", (char)((int) 10));
l293:
l288:
l296:
	t39 = apstack + 13;
	t40 = apstack + 14;
	t39 = stack[(int) t39];
	t40 = stack[(int) t40];
	t41 = 1;
	if (t39 > t40) goto l297;
	t41 = 0;
l297:
	if (t41 == 0) goto l298;
	t42 = apstack + 13;
	t42 = stack[(int) t42];
	printf("%d", (int)t42);
	printf("%c", (char)((int) 10));
	t43 = apstack + 13;
	t43 = stack[(int) t43];
	t44 = 1;
	if (t43 == 18) goto l299;
	t44 = 0;
l299:
	if (t44 == 0) goto l300;
	goto l298;
l300:
	t45 = apstack + 13;
	t46 = stack[(int) t45];
	t47 = t46 - 1;
	stack[(int) t45] = t47;
	goto l296;
l298:
	t48 = stack[(int) apstack];
	// guardar temps en stack;
	t49 = apstack + 15;
	stack[(int) t49] = t48;
	// setear parametros por valor o referencia;
	t49 = apstack + 18;
	stack[(int) t49] = 3;
	t49 = apstack + 19;
	stack[(int) t49] = 2;
	// actualizar punteros en stack y heap;
	t49 = apstack + 16;
	stack[(int) t49] = t48;
	apstack = apstack + 16;
	// llamar al procedimiento;
	kenny_int_resta_int_int();
	// restablecer valor de apstack;
	apstack = apstack - 16;
	// tomar valor retorno;
	t50 = apstack + 17;
	// restablecer temporales;
	t49 = apstack + 15;
	t48 = stack[(int) t49];
	t50 = stack[(int) t50];
	printf("%d", (int)t50);
	printf("%c", (char)((int) 10));
l281:
	return;
}

void kenny_int_resta_int_int() {
	t1 = apstack + 2;
	t2 = apstack + 3;
	t1 = stack[(int) t1];
	t2 = stack[(int) t2];
	t1 = t1 + t2;
	t3 = apstack + 1;
	stack[(int) t3] = t1;
l301:
	return;
}

void kenny_int_suma_int_int() {
	t1 = apstack + 2;
	t2 = apstack + 3;
	t1 = stack[(int) t1];
	t2 = stack[(int) t2];
	t1 = t1 + t2;
	t3 = apstack + 4;
	stack[(int) t3] = t1;
	t4 = apstack + 4;
	t5 = apstack + 1;
	t4 = stack[(int) t4];
	stack[(int) t5] = t4;
l302:
	return;
}

void miguel_int_resta_int_int() {
	t1 = apstack + 2;
	t2 = apstack + 3;
	t1 = stack[(int) t1];
	t2 = stack[(int) t2];
	t1 = t1 - t2;
	t3 = apstack + 1;
	stack[(int) t3] = t1;
l303:
	return;
}

void exception() { 
	printf("%c", (char)((int) 69));
	printf("%c", (char)((int) 114));
	printf("%c", (char)((int) 114));
	printf("%c", (char)((int) 111));
	printf("%c", (char)((int) 114));
	printf("%c", (char)((int) 32));
	printf("%c", (char)((int) 101));
	printf("%c", (char)((int) 110));
	printf("%c", (char)((int) 32));
	printf("%c", (char)((int) 116));
	printf("%c", (char)((int) 105));
	printf("%c", (char)((int) 101));
	printf("%c", (char)((int) 109));
	printf("%c", (char)((int) 112));
	printf("%c", (char)((int) 111));
	printf("%c", (char)((int) 32));
	printf("%c", (char)((int) 100));
	printf("%c", (char)((int) 101));
	printf("%c", (char)((int) 32));
	printf("%c", (char)((int) 101));
	printf("%c", (char)((int) 106));
	printf("%c", (char)((int) 101));
	printf("%c", (char)((int) 99));
	printf("%c", (char)((int) 117));
	printf("%c", (char)((int) 99));
	printf("%c", (char)((int) 105));
	printf("%c", (char)((int) 111));
	printf("%c", (char)((int) 110));
	printf("%c", (char)((int) 33));
	printf("%c", (char)((int) 32));
	printf("%c", (char)((int) 68));
	printf("%c", (char)((int) 105));
	printf("%c", (char)((int) 109));
	printf("%c", (char)((int) 101));
	printf("%c", (char)((int) 110));
	printf("%c", (char)((int) 115));
	printf("%c", (char)((int) 105));
	printf("%c", (char)((int) 111));
	printf("%c", (char)((int) 110));
	printf("%c", (char)((int) 101));
	printf("%c", (char)((int) 115));
	printf("%c", (char)((int) 32));
	printf("%c", (char)((int) 102));
	printf("%c", (char)((int) 117));
	printf("%c", (char)((int) 101));
	printf("%c", (char)((int) 114));
	printf("%c", (char)((int) 97));
	printf("%c", (char)((int) 32));
	printf("%c", (char)((int) 100));
	printf("%c", (char)((int) 101));
	printf("%c", (char)((int) 32));
	printf("%c", (char)((int) 114));
	printf("%c", (char)((int) 97));
	printf("%c", (char)((int) 110));
	printf("%c", (char)((int) 103));
	printf("%c", (char)((int) 111));
	printf("%c", (char)((int) 46));
	printf("%c", (char)((int) 10));
}