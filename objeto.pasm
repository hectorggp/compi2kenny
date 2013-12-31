dec apstack, apheap
dec stack.1000
dec heap.10000
dec t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12
dec t13, t14, t15, t16, t17

proc main
	mov apstack, 0
	mov apheap, 106
	call kenny_void_main
end proc

proc kenny_void_main
	mov t1, stack.apstack
	mov pila_op.0, t1
	mov pila_op.1, 1
	Add
	mov t1, pila_op.0
	mov heap.t1, 2
	mov t2, stack.apstack
	mov pila_op.0, t2
	mov pila_op.1, 2
	Add
	mov t2, pila_op.0
	mov t3, apheap
	mov heap.t3, t3
	mov pila_op.0, apheap
	mov pila_op.1, 256
	Add
	mov apheap, pila_op.0
	mov pila_op.0, t3
	mov pila_op.1, 1
	Add
	mov t4, pila_op.0
	mov heap.t4, 111
	mov pila_op.0, t3
	mov pila_op.1, 2
	Add
	mov t4, pila_op.0
	mov heap.t4, 108
	mov pila_op.0, t3
	mov pila_op.1, 3
	Add
	mov t4, pila_op.0
	mov heap.t4, 97
	mov pila_op.0, t3
	mov pila_op.1, 4
	Add
	mov t4, pila_op.0
	mov heap.t4, 0
	mov heap.t2, t3
	mov t5, stack.apstack
	mov pila_op.0, t5
	mov pila_op.1, 3
	Add
	mov t5, pila_op.0
	mov heap.t5, 2.32
	mov t6, stack.apstack
	mov pila_op.0, t6
	mov pila_op.1, 4
	Add
	mov t6, pila_op.0
	mov heap.t6, 0
	mov t7, stack.apstack
	mov pila_op.0, t7
	mov pila_op.1, 5
	Add
	mov t7, pila_op.0
	mov heap.t7, 99
	mov t8, stack.apstack
	mov pila_op.0, t8
	mov pila_op.1, 6
	Add
	mov t8, pila_op.0
	mov t9, 1
	mov pila_op.0, t9
	mov pila_op.1, 5
	Mul
	mov t10, pila_op.0
	mov pila_op.0, t10
	mov pila_op.1, 1
	Add
	mov t9, pila_op.0
	mov pila_op.0, t9
	mov pila_op.1, 5
	Mul
	mov t10, pila_op.0
	mov pila_op.0, t10
	mov pila_op.1, 1
	Add
	mov t9, pila_op.0
	mov pila_op.0, t9
	mov pila_op.1, t8
	Add
	mov t8, pila_op.0
	mov pila_op.0, 0
	mov pila_op.1, 32
	Sub
	mov t11, pila_op.0
	mov heap.t8, t11
	mov t12, stack.apstack
	mov pila_op.0, t12
	mov pila_op.1, 1
	Add
	mov t12, pila_op.0
	mov pila_op.0, 0
	mov pila_op.1, 2
	Sub
	mov t13, pila_op.0
	mov pila_op.0, 0
	mov pila_op.1, 143
	Sub
	mov t14, pila_op.0
	mov t15, stack.apstack
	mov pila_op.0, temps
	mov pila_op.1, stack
	NOOP: en
	mov //, pila_op.0
	mov pila_op.0, apstack
	mov pila_op.1, 1
	Add
	mov t16, pila_op.0
	mov stack.t16, t12
	mov pila_op.0, apstack
	mov pila_op.1, 2
	Add
	mov t16, pila_op.0
	mov stack.t16, t13
	mov pila_op.0, apstack
	mov pila_op.1, 3
	Add
	mov t16, pila_op.0
	mov stack.t16, t14
	mov pila_op.0, apstack
	mov pila_op.1, 4
	Add
	mov t16, pila_op.0
	mov stack.t16, t15
	// setear parametros por valor o referencia ; pero q mierda??
	mov pila_op.0, apstack
	mov pila_op.1, 7
	Add
	mov t16, pila_op.0
	mov stack.t16, t13
	mov pila_op.0, apstack
	mov pila_op.1, 8
	Add
	mov t16, pila_op.0
	mov stack.t16, t14
	// actualizar punteros en stack y heap ; pero q mierda??
	mov pila_op.0, apstack
	mov pila_op.1, 5
	Add
	mov t16, pila_op.0
	mov stack.t16, t15
	mov pila_op.0, apstack
	mov pila_op.1, 5
	Add
	mov apstack, pila_op.0
// llamar al procedimiento ; CUATRO wtf????	call kenny_int_suma_int_int
	mov pila_op.0, valor
	mov pila_op.1, apstack
	NOOP: de
	mov //, pila_op.0
	mov pila_op.0, apstack
	mov pila_op.1, 5
	Sub
	mov apstack, pila_op.0
// tomar valor retorno ; CUATRO wtf????	mov pila_op.0, apstack
	mov pila_op.1, 6
	Add
	mov t17, pila_op.0
	mov //, temporales
	mov pila_op.0, apstack
	mov pila_op.1, 4
	Add
	mov t16, pila_op.0
	mov t15, stack.t16
	mov pila_op.0, apstack
	mov pila_op.1, 3
	Add
	mov t16, pila_op.0
	mov t14, stack.t16
	mov pila_op.0, apstack
	mov pila_op.1, 2
	Add
	mov t16, pila_op.0
	mov t13, stack.t16
	mov pila_op.0, apstack
	mov pila_op.1, 1
	Add
	mov t16, pila_op.0
	mov t12, stack.t16
	mov t17, stack.t17
	mov heap.t12, t17
l3:
	ret
end proc

proc kenny_int_suma_int_int
	mov pila_op.0, apstack
	mov pila_op.1, 2
	Add
	mov t1, pila_op.0
	mov pila_op.0, apstack
	mov pila_op.1, 3
	Add
	mov t2, pila_op.0
	mov t1, stack.t1
	mov t2, stack.t2
	mov pila_op.0, t1
	mov pila_op.1, t2
	Add
	mov t1, pila_op.0
	mov pila_op.0, apstack
	mov pila_op.1, 4
	Add
	mov t3, pila_op.0
	mov stack.t3, t1
	mov pila_op.0, apstack
	mov pila_op.1, 4
	Add
	mov t4, pila_op.0
	mov t4, stack.t4
	mov ptr_val, t4
	print 0
	mov ptr_val, 10
	print 2
	mov pila_op.0, apstack
	mov pila_op.1, 4
	Add
	mov t5, pila_op.0
	mov pila_op.0, apstack
	mov pila_op.1, 1
	Add
	mov t6, pila_op.0
	mov t5, stack.t5
	mov stack.t6, t5
l4:
	ret
end proc

proc exception
	mov ptr_val, 69
	print 2
	mov ptr_val, 114
	print 2
	mov ptr_val, 114
	print 2
	mov ptr_val, 111
	print 2
	mov ptr_val, 114
	print 2
	mov ptr_val, 32
	print 2
	mov ptr_val, 101
	print 2
	mov ptr_val, 110
	print 2
	mov ptr_val, 32
	print 2
	mov ptr_val, 116
	print 2
	mov ptr_val, 105
	print 2
	mov ptr_val, 101
	print 2
	mov ptr_val, 109
	print 2
	mov ptr_val, 112
	print 2
	mov ptr_val, 111
	print 2
	mov ptr_val, 32
	print 2
	mov ptr_val, 100
	print 2
	mov ptr_val, 101
	print 2
	mov ptr_val, 32
	print 2
	mov ptr_val, 101
	print 2
	mov ptr_val, 106
	print 2
	mov ptr_val, 101
	print 2
	mov ptr_val, 99
	print 2
	mov ptr_val, 117
	print 2
	mov ptr_val, 99
	print 2
	mov ptr_val, 105
	print 2
	mov ptr_val, 111
	print 2
	mov ptr_val, 110
	print 2
	mov ptr_val, 33
	print 2
	mov ptr_val, 32
	print 2
	mov ptr_val, 68
	print 2
	mov ptr_val, 105
	print 2
	mov ptr_val, 109
	print 2
	mov ptr_val, 101
	print 2
	mov ptr_val, 110
	print 2
	mov ptr_val, 115
	print 2
	mov ptr_val, 105
	print 2
	mov ptr_val, 111
	print 2
	mov ptr_val, 110
	print 2
	mov ptr_val, 101
	print 2
	mov ptr_val, 115
	print 2
	mov ptr_val, 32
	print 2
	mov ptr_val, 102
	print 2
	mov ptr_val, 117
	print 2
	mov ptr_val, 101
	print 2
	mov ptr_val, 114
	print 2
	mov ptr_val, 97
	print 2
	mov ptr_val, 32
	print 2
	mov ptr_val, 100
	print 2
	mov ptr_val, 101
	print 2
	mov ptr_val, 32
	print 2
	mov ptr_val, 114
	print 2
	mov ptr_val, 97
	print 2
	mov ptr_val, 110
	print 2
	mov ptr_val, 103
	print 2
	mov ptr_val, 111
	print 2
	mov ptr_val, 46
	print 2
	mov ptr_val, 10
	print 2
end proc
