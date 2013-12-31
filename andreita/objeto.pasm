
proc main
	mov apstack, 0
	mov apheap, 123
	call A_void_main
end proc

proc A_void_main
	mov t1, stack.apstack
	mov pila_op.0, t1
	mov pila_op.1, 122
	Add
	mov t1, pila_op.0
	mov t2, apheap
	mov heap.t2, t2
	mov pila_op.0, apheap
	mov pila_op.1, 122
	Add
	mov apheap, pila_op.0
	mov pila_op.0, apstack
	mov pila_op.1, 1
	Add
	mov t3, pila_op.0
	mov stack.t3, t1
	mov pila_op.0, apstack
	mov pila_op.1, 2
	Add
	mov t3, pila_op.0
	mov stack.t3, t2
	mov pila_op.0, apstack
	mov pila_op.1, 3
	Add
	mov t3, pila_op.0
	mov stack.t3, t2
	mov pila_op.0, apstack
	mov pila_op.1, 3
	Add
	mov apstack, pila_op.0
	call B_void_B
	mov pila_op.0, apstack
	mov pila_op.1, 3
	Sub
	mov apstack, pila_op.0
	mov pila_op.0, apstack
	mov pila_op.1, 2
	Add
	mov t3, pila_op.0
	mov t2, stack.t3
	mov pila_op.0, apstack
	mov pila_op.1, 1
	Add
	mov t3, pila_op.0
	mov t1, stack.t3
	mov heap.t1, t2
	mov t4, stack.apstack
	mov pila_op.0, t4
	mov pila_op.1, 122
	Add
	mov t4, pila_op.0
	mov t4, heap.t4
	mov pila_op.0, apstack
	mov pila_op.1, 1
	Add
	mov t5, pila_op.0
	mov stack.t5, t4
	mov pila_op.0, apstack
	mov pila_op.1, 4
	Add
	mov t5, pila_op.0
	mov stack.t5, 2.2
	mov pila_op.0, apstack
	mov pila_op.1, 5
	Add
	mov t5, pila_op.0
	mov stack.t5, 3.3
	mov pila_op.0, apstack
	mov pila_op.1, 2
	Add
	mov t5, pila_op.0
	mov stack.t5, t4
	mov pila_op.0, apstack
	mov pila_op.1, 2
	Add
	mov apstack, pila_op.0
	call B_float_add_float_float
	mov pila_op.0, apstack
	mov pila_op.1, 2
	Sub
	mov apstack, pila_op.0
	mov pila_op.0, apstack
	mov pila_op.1, 3
	Add
	mov t6, pila_op.0
	mov pila_op.0, apstack
	mov pila_op.1, 1
	Add
	mov t5, pila_op.0
	mov t4, stack.t5
	mov t7, apheap
	mov heap.t7, t7
	mov pila_op.0, apheap
	mov pila_op.1, 123
	Add
	mov apheap, pila_op.0
	mov pila_op.0, apstack
	mov pila_op.1, 2
	Add
	mov t8, pila_op.0
	mov stack.t8, t7
	mov pila_op.0, apstack
	mov pila_op.1, 4
	Add
	mov t8, pila_op.0
	mov stack.t8, 3
	mov pila_op.0, apstack
	mov pila_op.1, 3
	Add
	mov t8, pila_op.0
	mov stack.t8, t7
	mov pila_op.0, apstack
	mov pila_op.1, 3
	Add
	mov apstack, pila_op.0
	call A_void_A_int
	mov pila_op.0, apstack
	mov pila_op.1, 3
	Sub
	mov apstack, pila_op.0
	mov pila_op.0, apstack
	mov pila_op.1, 2
	Add
	mov t8, pila_op.0
	mov t7, stack.t8
	mov pila_op.0, apstack
	mov pila_op.1, 1
	Add
	mov t9, pila_op.0
	mov stack.t9, t7
	mov pila_op.0, apstack
	mov pila_op.1, 1
	Add
	mov t10, pila_op.0
	mov t10, stack.t10
	mov pila_op.0, t10
	mov pila_op.1, 4
	Add
	mov t10, pila_op.0
	cmp 2, 4
	jumpBelow l2
	call exception
l2:
	mov t11, 2
	cmp 3, 5
	jumpBelow l3
	call exception
l3:
	mov pila_op.0, t11
	mov pila_op.1, 5
	Mul
	mov t12, pila_op.0
	mov pila_op.0, t12
	mov pila_op.1, 3
	Add
	mov t11, pila_op.0
	cmp 2, 5
	jumpBelow l4
	call exception
l4:
	mov pila_op.0, t11
	mov pila_op.1, 5
	Mul
	mov t12, pila_op.0
	mov pila_op.0, t12
	mov pila_op.1, 2
	Add
	mov t11, pila_op.0
	mov pila_op.0, t11
	mov pila_op.1, t10
	Add
	mov t10, pila_op.0
	mov t10, heap.t10
	print(0, t10)
	print(2, 10)
	mov pila_op.0, apstack
	mov pila_op.1, 2
	Add
	mov t13, pila_op.0
	mov stack.t13, 0
l5:
	cmp 1, 0
	jumpEqual l6
	mov pila_op.0, apstack
	mov pila_op.1, 2
	Add
	mov t14, pila_op.0
	mov t14, stack.t14
	print(0, t14)
	print(2, 10)
	mov pila_op.0, apstack
	mov pila_op.1, 2
	Add
	mov t15, pila_op.0
	mov t17, stack.t15
	mov pila_op.0, t17
	mov pila_op.1, 8
	Add
	mov t16, pila_op.0
	mov stack.t15, t16
	mov pila_op.0, apstack
	mov pila_op.1, 2
	Add
	mov t18, pila_op.0
	mov t18, stack.t18
	mov t19, 1
	cmp t18, 32
	jumpAbove l7
	mov t19, 0
l7:
	cmp t19, 0
	jumpEqual l8
	jump l6
l8:
	jump l5
l6:
	mov t20, apheap
	mov heap.t20, t20
	mov pila_op.0, apheap
	mov pila_op.1, 256
	Add
	mov apheap, pila_op.0
	mov pila_op.0, t20
	mov pila_op.1, 1
	Add
	mov t21, pila_op.0
	mov heap.t21, 116
	mov pila_op.0, t20
	mov pila_op.1, 2
	Add
	mov t21, pila_op.0
	mov heap.t21, 101
	mov pila_op.0, t20
	mov pila_op.1, 3
	Add
	mov t21, pila_op.0
	mov heap.t21, 114
	mov pila_op.0, t20
	mov pila_op.1, 4
	Add
	mov t21, pila_op.0
	mov heap.t21, 109
	mov pila_op.0, t20
	mov pila_op.1, 5
	Add
	mov t21, pila_op.0
	mov heap.t21, 105
	mov pila_op.0, t20
	mov pila_op.1, 6
	Add
	mov t21, pila_op.0
	mov heap.t21, 110
	mov pila_op.0, t20
	mov pila_op.1, 7
	Add
	mov t21, pila_op.0
	mov heap.t21, 97
	mov pila_op.0, t20
	mov pila_op.1, 8
	Add
	mov t21, pila_op.0
	mov heap.t21, 32
	mov pila_op.0, t20
	mov pila_op.1, 9
	Add
	mov t21, pila_op.0
	mov heap.t21, 119
	mov pila_op.0, t20
	mov pila_op.1, 10
	Add
	mov t21, pila_op.0
	mov heap.t21, 104
	mov pila_op.0, t20
	mov pila_op.1, 11
	Add
	mov t21, pila_op.0
	mov heap.t21, 105
	mov pila_op.0, t20
	mov pila_op.1, 12
	Add
	mov t21, pila_op.0
	mov heap.t21, 108
	mov pila_op.0, t20
	mov pila_op.1, 13
	Add
	mov t21, pila_op.0
	mov heap.t21, 101
	mov pila_op.0, t20
	mov pila_op.1, 14
	Add
	mov t21, pila_op.0
	mov heap.t21, 0
	mov t22, heap.t20
l9:
	mov pila_op.0, t22
	mov pila_op.1, 1
	Add
	mov t22, pila_op.0
	mov t20, heap.t22
	cmp t20, 0
	jumpEqual l10
	print(2, t20)
	jump l9
l10:
	print(2, 10)
	mov pila_op.0, apstack
	mov pila_op.1, 2
	Add
	mov t23, pila_op.0
	mov stack.t23, 1
	mov pila_op.0, apstack
	mov pila_op.1, 3
	Add
	mov t24, pila_op.0
	mov stack.t24, 0
l11:
	mov pila_op.0, apstack
	mov pila_op.1, 3
	Add
	mov t25, pila_op.0
	mov t25, stack.t25
	mov t26, 1
	cmp t25, 4
	jumpBelow l12
	mov t26, 0
l12:
	cmp t26, 0
	jumpEqual l13
	jump l14
l15:
	mov pila_op.0, apstack
	mov pila_op.1, 3
	Add
	mov t27, pila_op.0
	mov t28, stack.t27
	mov pila_op.0, t28
	mov pila_op.1, 1
	Add
	mov t29, pila_op.0
	mov stack.t27, t29
	jump l11
l14:
	mov pila_op.0, apstack
	mov pila_op.1, 2
	Add
	mov t30, pila_op.0
	mov stack.t30, 0
l16:
	mov pila_op.0, apstack
	mov pila_op.1, 2
	Add
	mov t31, pila_op.0
	mov t31, stack.t31
	mov t32, 1
	cmp t31, 5
	jumpBelow l17
	mov t32, 0
l17:
	cmp t32, 0
	jumpEqual l18
	jump l19
l20:
	mov pila_op.0, apstack
	mov pila_op.1, 2
	Add
	mov t33, pila_op.0
	mov t34, stack.t33
	mov pila_op.0, t34
	mov pila_op.1, 1
	Add
	mov t35, pila_op.0
	mov stack.t33, t35
	jump l16
l19:
	mov pila_op.0, apstack
	mov pila_op.1, 2
	Add
	mov t36, pila_op.0
	mov pila_op.0, apstack
	mov pila_op.1, 3
	Add
	mov t37, pila_op.0
	mov t36, stack.t36
	mov t37, stack.t37
	mov pila_op.0, t36
	mov pila_op.1, t37
	Add
	mov t36, pila_op.0
	jump l21
l23:
	mov t38, apheap
	mov heap.t38, t38
	mov pila_op.0, apheap
	mov pila_op.1, 256
	Add
	mov apheap, pila_op.0
	mov pila_op.0, t38
	mov pila_op.1, 1
	Add
	mov t39, pila_op.0
	mov heap.t39, 101
	mov pila_op.0, t38
	mov pila_op.1, 2
	Add
	mov t39, pila_op.0
	mov heap.t39, 115
	mov pila_op.0, t38
	mov pila_op.1, 3
	Add
	mov t39, pila_op.0
	mov heap.t39, 32
	mov pila_op.0, t38
	mov pila_op.1, 4
	Add
	mov t39, pila_op.0
	mov heap.t39, 100
	mov pila_op.0, t38
	mov pila_op.1, 5
	Add
	mov t39, pila_op.0
	mov heap.t39, 111
	mov pila_op.0, t38
	mov pila_op.1, 6
	Add
	mov t39, pila_op.0
	mov heap.t39, 115
	mov pila_op.0, t38
	mov pila_op.1, 7
	Add
	mov t39, pila_op.0
	mov heap.t39, 0
	mov t40, heap.t38
l24:
	mov pila_op.0, t40
	mov pila_op.1, 1
	Add
	mov t40, pila_op.0
	mov t38, heap.t40
	cmp t38, 0
	jumpEqual l25
	print(2, t38)
	jump l24
l25:
	print(2, 10)
	jump l22
l26:
	mov t41, apheap
	mov heap.t41, t41
	mov pila_op.0, apheap
	mov pila_op.1, 256
	Add
	mov apheap, pila_op.0
	mov pila_op.0, t41
	mov pila_op.1, 1
	Add
	mov t42, pila_op.0
	mov heap.t42, 101
	mov pila_op.0, t41
	mov pila_op.1, 2
	Add
	mov t42, pila_op.0
	mov heap.t42, 115
	mov pila_op.0, t41
	mov pila_op.1, 3
	Add
	mov t42, pila_op.0
	mov heap.t42, 32
	mov pila_op.0, t41
	mov pila_op.1, 4
	Add
	mov t42, pila_op.0
	mov heap.t42, 99
	mov pila_op.0, t41
	mov pila_op.1, 5
	Add
	mov t42, pila_op.0
	mov heap.t42, 105
	mov pila_op.0, t41
	mov pila_op.1, 6
	Add
	mov t42, pila_op.0
	mov heap.t42, 110
	mov pila_op.0, t41
	mov pila_op.1, 7
	Add
	mov t42, pila_op.0
	mov heap.t42, 99
	mov pila_op.0, t41
	mov pila_op.1, 8
	Add
	mov t42, pila_op.0
	mov heap.t42, 111
	mov pila_op.0, t41
	mov pila_op.1, 9
	Add
	mov t42, pila_op.0
	mov heap.t42, 0
	mov t43, heap.t41
l27:
	mov pila_op.0, t43
	mov pila_op.1, 1
	Add
	mov t43, pila_op.0
	mov t41, heap.t43
	cmp t41, 0
	jumpEqual l28
	print(2, t41)
	jump l27
l28:
	print(2, 10)
	jump l22
l29:
	mov t44, apheap
	mov heap.t44, t44
	mov pila_op.0, apheap
	mov pila_op.1, 256
	Add
	mov apheap, pila_op.0
	mov pila_op.0, t44
	mov pila_op.1, 1
	Add
	mov t45, pila_op.0
	mov heap.t45, 101
	mov pila_op.0, t44
	mov pila_op.1, 2
	Add
	mov t45, pila_op.0
	mov heap.t45, 115
	mov pila_op.0, t44
	mov pila_op.1, 3
	Add
	mov t45, pila_op.0
	mov heap.t45, 32
	mov pila_op.0, t44
	mov pila_op.1, 4
	Add
	mov t45, pila_op.0
	mov heap.t45, 115
	mov pila_op.0, t44
	mov pila_op.1, 5
	Add
	mov t45, pila_op.0
	mov heap.t45, 105
	mov pila_op.0, t44
	mov pila_op.1, 6
	Add
	mov t45, pila_op.0
	mov heap.t45, 101
	mov pila_op.0, t44
	mov pila_op.1, 7
	Add
	mov t45, pila_op.0
	mov heap.t45, 116
	mov pila_op.0, t44
	mov pila_op.1, 8
	Add
	mov t45, pila_op.0
	mov heap.t45, 101
	mov pila_op.0, t44
	mov pila_op.1, 9
	Add
	mov t45, pila_op.0
	mov heap.t45, 0
	mov t46, heap.t44
l30:
	mov pila_op.0, t46
	mov pila_op.1, 1
	Add
	mov t46, pila_op.0
	mov t44, heap.t46
	cmp t44, 0
	jumpEqual l31
	print(2, t44)
	jump l30
l31:
	print(2, 10)
	jump l22
l32:
	mov t47, apheap
	mov heap.t47, t47
	mov pila_op.0, apheap
	mov pila_op.1, 256
	Add
	mov apheap, pila_op.0
	mov pila_op.0, t47
	mov pila_op.1, 1
	Add
	mov t48, pila_op.0
	mov heap.t48, 101
	mov pila_op.0, t47
	mov pila_op.1, 2
	Add
	mov t48, pila_op.0
	mov heap.t48, 115
	mov pila_op.0, t47
	mov pila_op.1, 3
	Add
	mov t48, pila_op.0
	mov heap.t48, 32
	mov pila_op.0, t47
	mov pila_op.1, 4
	Add
	mov t48, pila_op.0
	mov heap.t48, 111
	mov pila_op.0, t47
	mov pila_op.1, 5
	Add
	mov t48, pila_op.0
	mov heap.t48, 116
	mov pila_op.0, t47
	mov pila_op.1, 6
	Add
	mov t48, pila_op.0
	mov heap.t48, 114
	mov pila_op.0, t47
	mov pila_op.1, 7
	Add
	mov t48, pila_op.0
	mov heap.t48, 111
	mov pila_op.0, t47
	mov pila_op.1, 8
	Add
	mov t48, pila_op.0
	mov heap.t48, 32
	mov pila_op.0, t47
	mov pila_op.1, 9
	Add
	mov t48, pila_op.0
	mov heap.t48, 118
	mov pila_op.0, t47
	mov pila_op.1, 10
	Add
	mov t48, pila_op.0
	mov heap.t48, 97
	mov pila_op.0, t47
	mov pila_op.1, 11
	Add
	mov t48, pila_op.0
	mov heap.t48, 108
	mov pila_op.0, t47
	mov pila_op.1, 12
	Add
	mov t48, pila_op.0
	mov heap.t48, 111
	mov pila_op.0, t47
	mov pila_op.1, 13
	Add
	mov t48, pila_op.0
	mov heap.t48, 114
	mov pila_op.0, t47
	mov pila_op.1, 14
	Add
	mov t48, pila_op.0
	mov heap.t48, 0
	mov t49, heap.t47
l33:
	mov pila_op.0, t49
	mov pila_op.1, 1
	Add
	mov t49, pila_op.0
	mov t47, heap.t49
	cmp t47, 0
	jumpEqual l34
	print(2, t47)
	jump l33
l34:
	print(2, 10)
	jump l22
l21:
	mov pila_op.0, 2)
	mov pila_op.1, l23
	NOOP
	mov if(t36, pila_op.0
	mov pila_op.0, 5)
	mov pila_op.1, l26
	NOOP
	mov if(t36, pila_op.0
	mov pila_op.0, 7)
	mov pila_op.1, l29
	NOOP
	mov if(t36, pila_op.0
	jump l32
l22:
	jump l20
l18:
	jump l15
l13:
	mov t50, apheap
	mov heap.t50, t50
	mov pila_op.0, apheap
	mov pila_op.1, 256
	Add
	mov apheap, pila_op.0
	mov pila_op.0, t50
	mov pila_op.1, 1
	Add
	mov t51, pila_op.0
	mov heap.t51, 116
	mov pila_op.0, t50
	mov pila_op.1, 2
	Add
	mov t51, pila_op.0
	mov heap.t51, 101
	mov pila_op.0, t50
	mov pila_op.1, 3
	Add
	mov t51, pila_op.0
	mov heap.t51, 114
	mov pila_op.0, t50
	mov pila_op.1, 4
	Add
	mov t51, pila_op.0
	mov heap.t51, 109
	mov pila_op.0, t50
	mov pila_op.1, 5
	Add
	mov t51, pila_op.0
	mov heap.t51, 105
	mov pila_op.0, t50
	mov pila_op.1, 6
	Add
	mov t51, pila_op.0
	mov heap.t51, 110
	mov pila_op.0, t50
	mov pila_op.1, 7
	Add
	mov t51, pila_op.0
	mov heap.t51, 97
	mov pila_op.0, t50
	mov pila_op.1, 8
	Add
	mov t51, pila_op.0
	mov heap.t51, 32
	mov pila_op.0, t50
	mov pila_op.1, 9
	Add
	mov t51, pila_op.0
	mov heap.t51, 102
	mov pila_op.0, t50
	mov pila_op.1, 10
	Add
	mov t51, pila_op.0
	mov heap.t51, 111
	mov pila_op.0, t50
	mov pila_op.1, 11
	Add
	mov t51, pila_op.0
	mov heap.t51, 114
	mov pila_op.0, t50
	mov pila_op.1, 12
	Add
	mov t51, pila_op.0
	mov heap.t51, 0
	mov t52, heap.t50
l35:
	mov pila_op.0, t52
	mov pila_op.1, 1
	Add
	mov t52, pila_op.0
	mov t50, heap.t52
	cmp t50, 0
	jumpEqual l36
	print(2, t50)
	jump l35
l36:
	print(2, 10)
	print(2, 10)
	mov pila_op.0, apstack
	mov pila_op.1, 2
	Add
	mov t53, pila_op.0
	mov stack.t53, 1
l37:
	mov pila_op.0, apstack
	mov pila_op.1, 2
	Add
	mov t54, pila_op.0
	mov t54, stack.t54
	print(0, t54)
	print(2, 10)
	mov pila_op.0, apstack
	mov pila_op.1, 2
	Add
	mov t55, pila_op.0
	mov t56, stack.t55
	mov pila_op.0, t56
	mov pila_op.1, 1
	Sub
	mov t57, pila_op.0
	mov stack.t55, t57
	mov pila_op.0, 0
	mov pila_op.1, 2
	Sub
	mov t58, pila_op.0
	mov t59, 1
	cmp t56, t58
	jumpEqual l39
	mov t59, 0
l39:
	cmp t59, 0
	jumpEqual l40
	jump l38
l40:
	cmp 1, 1
	jumpEqual l37
l38:
	mov t60, apheap
	mov heap.t60, t60
	mov pila_op.0, apheap
	mov pila_op.1, 256
	Add
	mov apheap, pila_op.0
	mov pila_op.0, t60
	mov pila_op.1, 1
	Add
	mov t61, pila_op.0
	mov heap.t61, 116
	mov pila_op.0, t60
	mov pila_op.1, 2
	Add
	mov t61, pila_op.0
	mov heap.t61, 101
	mov pila_op.0, t60
	mov pila_op.1, 3
	Add
	mov t61, pila_op.0
	mov heap.t61, 114
	mov pila_op.0, t60
	mov pila_op.1, 4
	Add
	mov t61, pila_op.0
	mov heap.t61, 109
	mov pila_op.0, t60
	mov pila_op.1, 5
	Add
	mov t61, pila_op.0
	mov heap.t61, 105
	mov pila_op.0, t60
	mov pila_op.1, 6
	Add
	mov t61, pila_op.0
	mov heap.t61, 110
	mov pila_op.0, t60
	mov pila_op.1, 7
	Add
	mov t61, pila_op.0
	mov heap.t61, 97
	mov pila_op.0, t60
	mov pila_op.1, 8
	Add
	mov t61, pila_op.0
	mov heap.t61, 32
	mov pila_op.0, t60
	mov pila_op.1, 9
	Add
	mov t61, pila_op.0
	mov heap.t61, 100
	mov pila_op.0, t60
	mov pila_op.1, 10
	Add
	mov t61, pila_op.0
	mov heap.t61, 111
	mov pila_op.0, t60
	mov pila_op.1, 11
	Add
	mov t61, pila_op.0
	mov heap.t61, 119
	mov pila_op.0, t60
	mov pila_op.1, 12
	Add
	mov t61, pila_op.0
	mov heap.t61, 104
	mov pila_op.0, t60
	mov pila_op.1, 13
	Add
	mov t61, pila_op.0
	mov heap.t61, 105
	mov pila_op.0, t60
	mov pila_op.1, 14
	Add
	mov t61, pila_op.0
	mov heap.t61, 108
	mov pila_op.0, t60
	mov pila_op.1, 15
	Add
	mov t61, pila_op.0
	mov heap.t61, 101
	mov pila_op.0, t60
	mov pila_op.1, 16
	Add
	mov t61, pila_op.0
	mov heap.t61, 0
	mov t62, heap.t60
l41:
	mov pila_op.0, t62
	mov pila_op.1, 1
	Add
	mov t62, pila_op.0
	mov t60, heap.t62
	cmp t60, 0
	jumpEqual l42
	print(2, t60)
	jump l41
l42:
	print(2, 10)
	print(2, 10)
	mov t63, apheap
	mov heap.t63, t63
	mov pila_op.0, apheap
	mov pila_op.1, 123
	Add
	mov apheap, pila_op.0
	mov pila_op.0, apstack
	mov pila_op.1, 4
	Add
	mov t64, pila_op.0
	mov stack.t64, t63
	mov pila_op.0, apstack
	mov pila_op.1, 6
	Add
	mov t64, pila_op.0
	mov stack.t64, 3
	mov pila_op.0, apstack
	mov pila_op.1, 5
	Add
	mov t64, pila_op.0
	mov stack.t64, t63
	mov pila_op.0, apstack
	mov pila_op.1, 5
	Add
	mov apstack, pila_op.0
	call A_void_A_int
	mov pila_op.0, apstack
	mov pila_op.1, 5
	Sub
	mov apstack, pila_op.0
	mov pila_op.0, apstack
	mov pila_op.1, 4
	Add
	mov t64, pila_op.0
	mov t63, stack.t64
	mov pila_op.0, apstack
	mov pila_op.1, 3
	Add
	mov t65, pila_op.0
	mov stack.t65, t63
	mov t66, apheap
	mov heap.t66, t66
	mov pila_op.0, apheap
	mov pila_op.1, 122
	Add
	mov apheap, pila_op.0
	mov pila_op.0, apstack
	mov pila_op.1, 4
	Add
	mov t67, pila_op.0
	mov stack.t67, t66
	mov pila_op.0, apstack
	mov pila_op.1, 5
	Add
	mov t67, pila_op.0
	mov stack.t67, t66
	mov pila_op.0, apstack
	mov pila_op.1, 5
	Add
	mov apstack, pila_op.0
	call B_void_B
	mov pila_op.0, apstack
	mov pila_op.1, 5
	Sub
	mov apstack, pila_op.0
	mov pila_op.0, apstack
	mov pila_op.1, 4
	Add
	mov t67, pila_op.0
	mov t66, stack.t67
	mov t66, heap.t66
	mov pila_op.0, t66
	mov pila_op.1, 4
	Add
	mov t66, pila_op.0
	cmp 2, 4
	jumpBelow l43
	call exception
l43:
	mov t68, 2
	cmp 3, 5
	jumpBelow l44
	call exception
l44:
	mov pila_op.0, t68
	mov pila_op.1, 5
	Mul
	mov t69, pila_op.0
	mov pila_op.0, t69
	mov pila_op.1, 3
	Add
	mov t68, pila_op.0
	cmp 4, 5
	jumpBelow l45
	call exception
l45:
	mov pila_op.0, t68
	mov pila_op.1, 5
	Mul
	mov t69, pila_op.0
	mov pila_op.0, t69
	mov pila_op.1, 4
	Add
	mov t68, pila_op.0
	mov pila_op.0, t68
	mov pila_op.1, t66
	Add
	mov t66, pila_op.0
	mov t66, heap.t66
	print(0, t66)
	print(2, 10)
	mov t70, apheap
	mov heap.t70, t70
	mov pila_op.0, apheap
	mov pila_op.1, 256
	Add
	mov apheap, pila_op.0
	mov pila_op.0, t70
	mov pila_op.1, 1
	Add
	mov t71, pila_op.0
	mov heap.t71, 101
	mov pila_op.0, t70
	mov pila_op.1, 2
	Add
	mov t71, pila_op.0
	mov heap.t71, 115
	mov pila_op.0, t70
	mov pila_op.1, 3
	Add
	mov t71, pila_op.0
	mov heap.t71, 111
	mov pila_op.0, t70
	mov pila_op.1, 4
	Add
	mov t71, pila_op.0
	mov heap.t71, 32
	mov pila_op.0, t70
	mov pila_op.1, 5
	Add
	mov t71, pila_op.0
	mov heap.t71, 101
	mov pila_op.0, t70
	mov pila_op.1, 6
	Add
	mov t71, pila_op.0
	mov heap.t71, 114
	mov pila_op.0, t70
	mov pila_op.1, 7
	Add
	mov t71, pila_op.0
	mov heap.t71, 97
	mov pila_op.0, t70
	mov pila_op.1, 8
	Add
	mov t71, pila_op.0
	mov heap.t71, 32
	mov pila_op.0, t70
	mov pila_op.1, 9
	Add
	mov t71, pila_op.0
	mov heap.t71, 117
	mov pila_op.0, t70
	mov pila_op.1, 10
	Add
	mov t71, pila_op.0
	mov heap.t71, 110
	mov pila_op.0, t70
	mov pila_op.1, 11
	Add
	mov t71, pila_op.0
	mov heap.t71, 97
	mov pila_op.0, t70
	mov pila_op.1, 12
	Add
	mov t71, pila_op.0
	mov heap.t71, 32
	mov pila_op.0, t70
	mov pila_op.1, 13
	Add
	mov t71, pila_op.0
	mov heap.t71, 109
	mov pila_op.0, t70
	mov pila_op.1, 14
	Add
	mov t71, pila_op.0
	mov heap.t71, 97
	mov pila_op.0, t70
	mov pila_op.1, 15
	Add
	mov t71, pila_op.0
	mov heap.t71, 116
	mov pila_op.0, t70
	mov pila_op.1, 16
	Add
	mov t71, pila_op.0
	mov heap.t71, 114
	mov pila_op.0, t70
	mov pila_op.1, 17
	Add
	mov t71, pila_op.0
	mov heap.t71, 105
	mov pila_op.0, t70
	mov pila_op.1, 18
	Add
	mov t71, pila_op.0
	mov heap.t71, 122
	mov pila_op.0, t70
	mov pila_op.1, 19
	Add
	mov t71, pila_op.0
	mov heap.t71, 0
	mov t72, heap.t70
l46:
	mov pila_op.0, t72
	mov pila_op.1, 1
	Add
	mov t72, pila_op.0
	mov t70, heap.t72
	cmp t70, 0
	jumpEqual l47
	print(2, t70)
	jump l46
l47:
	print(2, 10)
	print(2, 10)
	mov t73, apheap
	mov heap.t73, t73
	mov pila_op.0, apheap
	mov pila_op.1, 122
	Add
	mov apheap, pila_op.0
	mov pila_op.0, apstack
	mov pila_op.1, 4
	Add
	mov t74, pila_op.0
	mov stack.t74, t73
	mov pila_op.0, apstack
	mov pila_op.1, 5
	Add
	mov t74, pila_op.0
	mov stack.t74, t73
	mov pila_op.0, apstack
	mov pila_op.1, 5
	Add
	mov apstack, pila_op.0
	call B_void_B
	mov pila_op.0, apstack
	mov pila_op.1, 5
	Sub
	mov apstack, pila_op.0
	mov pila_op.0, apstack
	mov pila_op.1, 4
	Add
	mov t74, pila_op.0
	mov t73, stack.t74
	mov t73, heap.t73
	mov pila_op.0, apstack
	mov pila_op.1, 4
	Add
	mov t75, pila_op.0
	mov stack.t75, t73
	mov pila_op.0, apstack
	mov pila_op.1, 7
	Add
	mov t75, pila_op.0
	mov stack.t75, 2.2
	mov pila_op.0, apstack
	mov pila_op.1, 8
	Add
	mov t75, pila_op.0
	mov stack.t75, 3.3
	mov pila_op.0, apstack
	mov pila_op.1, 5
	Add
	mov t75, pila_op.0
	mov stack.t75, t73
	mov pila_op.0, apstack
	mov pila_op.1, 5
	Add
	mov apstack, pila_op.0
	call B_float_add_float_float
	mov pila_op.0, apstack
	mov pila_op.1, 5
	Sub
	mov apstack, pila_op.0
	mov pila_op.0, apstack
	mov pila_op.1, 6
	Add
	mov t76, pila_op.0
	mov pila_op.0, apstack
	mov pila_op.1, 4
	Add
	mov t75, pila_op.0
	mov t73, stack.t75
	mov t77, apheap
	mov heap.t77, t77
	mov pila_op.0, apheap
	mov pila_op.1, 256
	Add
	mov apheap, pila_op.0
	mov pila_op.0, t77
	mov pila_op.1, 1
	Add
	mov t78, pila_op.0
	mov heap.t78, 97
	mov pila_op.0, t77
	mov pila_op.1, 2
	Add
	mov t78, pila_op.0
	mov heap.t78, 108
	mov pila_op.0, t77
	mov pila_op.1, 3
	Add
	mov t78, pila_op.0
	mov heap.t78, 97
	mov pila_op.0, t77
	mov pila_op.1, 4
	Add
	mov t78, pila_op.0
	mov heap.t78, 109
	mov pila_op.0, t77
	mov pila_op.1, 5
	Add
	mov t78, pila_op.0
	mov heap.t78, 97
	mov pila_op.0, t77
	mov pila_op.1, 6
	Add
	mov t78, pila_op.0
	mov heap.t78, 100
	mov pila_op.0, t77
	mov pila_op.1, 7
	Add
	mov t78, pila_op.0
	mov heap.t78, 114
	mov pila_op.0, t77
	mov pila_op.1, 8
	Add
	mov t78, pila_op.0
	mov heap.t78, 101
	mov pila_op.0, t77
	mov pila_op.1, 9
	Add
	mov t78, pila_op.0
	mov heap.t78, 0
	mov t79, heap.t77
l48:
	mov pila_op.0, t79
	mov pila_op.1, 1
	Add
	mov t79, pila_op.0
	mov t77, heap.t79
	cmp t77, 0
	jumpEqual l49
	print(2, t77)
	jump l48
l49:
	print(2, 10)
	mov t80, stack.apstack
	mov pila_op.0, t80
	mov pila_op.1, 4
	Add
	mov t80, pila_op.0
	cmp 3, 4
	jumpBelow l50
	call exception
l50:
	mov t81, 3
	cmp 4, 5
	jumpBelow l51
	call exception
l51:
	mov pila_op.0, t81
	mov pila_op.1, 5
	Mul
	mov t82, pila_op.0
	mov pila_op.0, t82
	mov pila_op.1, 4
	Add
	mov t81, pila_op.0
	cmp 4, 5
	jumpBelow l52
	call exception
l52:
	mov pila_op.0, t81
	mov pila_op.1, 5
	Mul
	mov t82, pila_op.0
	mov pila_op.0, t82
	mov pila_op.1, 4
	Add
	mov t81, pila_op.0
	mov pila_op.0, t81
	mov pila_op.1, t80
	Add
	mov t80, pila_op.0
	mov heap.t80, 3
	mov t83, stack.apstack
	mov pila_op.0, t83
	mov pila_op.1, 4
	Add
	mov t83, pila_op.0
	cmp 3, 4
	jumpBelow l53
	call exception
l53:
	mov t84, 3
	cmp 4, 5
	jumpBelow l54
	call exception
l54:
	mov pila_op.0, t84
	mov pila_op.1, 5
	Mul
	mov t85, pila_op.0
	mov pila_op.0, t85
	mov pila_op.1, 4
	Add
	mov t84, pila_op.0
	cmp 4, 5
	jumpBelow l55
	call exception
l55:
	mov pila_op.0, t84
	mov pila_op.1, 5
	Mul
	mov t85, pila_op.0
	mov pila_op.0, t85
	mov pila_op.1, 4
	Add
	mov t84, pila_op.0
	mov pila_op.0, t84
	mov pila_op.1, t83
	Add
	mov t83, pila_op.0
	mov t86, heap.t83
	mov pila_op.0, t86
	mov pila_op.1, 1
	Add
	mov t87, pila_op.0
	mov heap.t83, t87
	print(0, t86)
	print(2, 10)
	mov t88, stack.apstack
	mov pila_op.0, t88
	mov pila_op.1, 4
	Add
	mov t88, pila_op.0
	cmp 3, 4
	jumpBelow l56
	call exception
l56:
	mov t89, 3
	cmp 4, 5
	jumpBelow l57
	call exception
l57:
	mov pila_op.0, t89
	mov pila_op.1, 5
	Mul
	mov t90, pila_op.0
	mov pila_op.0, t90
	mov pila_op.1, 4
	Add
	mov t89, pila_op.0
	cmp 4, 5
	jumpBelow l58
	call exception
l58:
	mov pila_op.0, t89
	mov pila_op.1, 5
	Mul
	mov t90, pila_op.0
	mov pila_op.0, t90
	mov pila_op.1, 4
	Add
	mov t89, pila_op.0
	mov pila_op.0, t89
	mov pila_op.1, t88
	Add
	mov t88, pila_op.0
	mov t88, heap.t88
	print(0, t88)
	print(2, 10)
	mov t91, apheap
	mov heap.t91, t91
	mov pila_op.0, apheap
	mov pila_op.1, 256
	Add
	mov apheap, pila_op.0
	mov pila_op.0, t91
	mov pila_op.1, 1
	Add
	mov t92, pila_op.0
	mov heap.t92, 101
	mov pila_op.0, t91
	mov pila_op.1, 2
	Add
	mov t92, pila_op.0
	mov heap.t92, 115
	mov pila_op.0, t91
	mov pila_op.1, 3
	Add
	mov t92, pila_op.0
	mov heap.t92, 116
	mov pila_op.0, t91
	mov pila_op.1, 4
	Add
	mov t92, pila_op.0
	mov heap.t92, 111
	mov pila_op.0, t91
	mov pila_op.1, 5
	Add
	mov t92, pila_op.0
	mov heap.t92, 32
	mov pila_op.0, t91
	mov pila_op.1, 6
	Add
	mov t92, pila_op.0
	mov heap.t92, 110
	mov pila_op.0, t91
	mov pila_op.1, 7
	Add
	mov t92, pila_op.0
	mov heap.t92, 97
	mov pila_op.0, t91
	mov pila_op.1, 8
	Add
	mov t92, pila_op.0
	mov heap.t92, 100
	mov pila_op.0, t91
	mov pila_op.1, 9
	Add
	mov t92, pila_op.0
	mov heap.t92, 97
	mov pila_op.0, t91
	mov pila_op.1, 10
	Add
	mov t92, pila_op.0
	mov heap.t92, 32
	mov pila_op.0, t91
	mov pila_op.1, 11
	Add
	mov t92, pila_op.0
	mov heap.t92, 113
	mov pila_op.0, t91
	mov pila_op.1, 12
	Add
	mov t92, pila_op.0
	mov heap.t92, 32
	mov pila_op.0, t91
	mov pila_op.1, 13
	Add
	mov t92, pila_op.0
	mov heap.t92, 118
	mov pila_op.0, t91
	mov pila_op.1, 14
	Add
	mov t92, pila_op.0
	mov heap.t92, 101
	mov pila_op.0, t91
	mov pila_op.1, 15
	Add
	mov t92, pila_op.0
	mov heap.t92, 114
	mov pila_op.0, t91
	mov pila_op.1, 16
	Add
	mov t92, pila_op.0
	mov heap.t92, 32
	mov pila_op.0, t91
	mov pila_op.1, 17
	Add
	mov t92, pila_op.0
	mov heap.t92, 118
	mov pila_op.0, t91
	mov pila_op.1, 18
	Add
	mov t92, pila_op.0
	mov heap.t92, 97
	mov pila_op.0, t91
	mov pila_op.1, 19
	Add
	mov t92, pila_op.0
	mov heap.t92, 0
	mov t93, heap.t91
l59:
	mov pila_op.0, t93
	mov pila_op.1, 1
	Add
	mov t93, pila_op.0
	mov t91, heap.t93
	cmp t91, 0
	jumpEqual l60
	print(2, t91)
	jump l59
l60:
	print(2, 10)
	mov t94, stack.apstack
	mov pila_op.0, t94
	mov pila_op.1, 122
	Add
	mov t94, pila_op.0
	mov t94, heap.t94
	mov pila_op.0, t94
	mov pila_op.1, 104
	Add
	mov t94, pila_op.0
	cmp 1, 3
	jumpBelow l61
	call exception
l61:
	mov t95, 1
	cmp 4, 6
	jumpBelow l62
	call exception
l62:
	mov pila_op.0, t95
	mov pila_op.1, 6
	Mul
	mov t96, pila_op.0
	mov pila_op.0, t96
	mov pila_op.1, 4
	Add
	mov t95, pila_op.0
	mov pila_op.0, t95
	mov pila_op.1, t94
	Add
	mov t94, pila_op.0
	mov t94, heap.t94
	print(1, t94)
	print(2, 10)
l1:
	ret
end proc

proc A_void_A_int
	mov t1, stack.apstack
	mov pila_op.0, t1
	mov pila_op.1, 4
	Add
	mov t1, pila_op.0
	cmp 2, 4
	jumpBelow l64
	call exception
l64:
	mov t2, 2
	cmp 3, 5
	jumpBelow l65
	call exception
l65:
	mov pila_op.0, t2
	mov pila_op.1, 5
	Mul
	mov t3, pila_op.0
	mov pila_op.0, t3
	mov pila_op.1, 3
	Add
	mov t2, pila_op.0
	cmp 2, 5
	jumpBelow l66
	call exception
l66:
	mov pila_op.0, t2
	mov pila_op.1, 5
	Mul
	mov t3, pila_op.0
	mov pila_op.0, t3
	mov pila_op.1, 2
	Add
	mov t2, pila_op.0
	mov pila_op.0, t2
	mov pila_op.1, t1
	Add
	mov t1, pila_op.0
	mov pila_op.0, apstack
	mov pila_op.1, 1
	Add
	mov t4, pila_op.0
	mov t4, stack.t4
	mov heap.t1, t4
l63:
	ret
end proc

proc A_void_exception
	mov t1, apheap
	mov heap.t1, t1
	mov pila_op.0, apheap
	mov pila_op.1, 256
	Add
	mov apheap, pila_op.0
	mov pila_op.0, t1
	mov pila_op.1, 1
	Add
	mov t2, pila_op.0
	mov heap.t2, 161
	mov pila_op.0, t1
	mov pila_op.1, 2
	Add
	mov t2, pila_op.0
	mov heap.t2, 69
	mov pila_op.0, t1
	mov pila_op.1, 3
	Add
	mov t2, pila_op.0
	mov heap.t2, 114
	mov pila_op.0, t1
	mov pila_op.1, 4
	Add
	mov t2, pila_op.0
	mov heap.t2, 114
	mov pila_op.0, t1
	mov pila_op.1, 5
	Add
	mov t2, pila_op.0
	mov heap.t2, 111
	mov pila_op.0, t1
	mov pila_op.1, 6
	Add
	mov t2, pila_op.0
	mov heap.t2, 114
	mov pila_op.0, t1
	mov pila_op.1, 7
	Add
	mov t2, pila_op.0
	mov heap.t2, 32
	mov pila_op.0, t1
	mov pila_op.1, 8
	Add
	mov t2, pila_op.0
	mov heap.t2, 101
	mov pila_op.0, t1
	mov pila_op.1, 9
	Add
	mov t2, pila_op.0
	mov heap.t2, 110
	mov pila_op.0, t1
	mov pila_op.1, 10
	Add
	mov t2, pila_op.0
	mov heap.t2, 32
	mov pila_op.0, t1
	mov pila_op.1, 11
	Add
	mov t2, pila_op.0
	mov heap.t2, 116
	mov pila_op.0, t1
	mov pila_op.1, 12
	Add
	mov t2, pila_op.0
	mov heap.t2, 105
	mov pila_op.0, t1
	mov pila_op.1, 13
	Add
	mov t2, pila_op.0
	mov heap.t2, 101
	mov pila_op.0, t1
	mov pila_op.1, 14
	Add
	mov t2, pila_op.0
	mov heap.t2, 109
	mov pila_op.0, t1
	mov pila_op.1, 15
	Add
	mov t2, pila_op.0
	mov heap.t2, 112
	mov pila_op.0, t1
	mov pila_op.1, 16
	Add
	mov t2, pila_op.0
	mov heap.t2, 111
	mov pila_op.0, t1
	mov pila_op.1, 17
	Add
	mov t2, pila_op.0
	mov heap.t2, 32
	mov pila_op.0, t1
	mov pila_op.1, 18
	Add
	mov t2, pila_op.0
	mov heap.t2, 100
	mov pila_op.0, t1
	mov pila_op.1, 19
	Add
	mov t2, pila_op.0
	mov heap.t2, 101
	mov pila_op.0, t1
	mov pila_op.1, 20
	Add
	mov t2, pila_op.0
	mov heap.t2, 32
	mov pila_op.0, t1
	mov pila_op.1, 21
	Add
	mov t2, pila_op.0
	mov heap.t2, 101
	mov pila_op.0, t1
	mov pila_op.1, 22
	Add
	mov t2, pila_op.0
	mov heap.t2, 106
	mov pila_op.0, t1
	mov pila_op.1, 23
	Add
	mov t2, pila_op.0
	mov heap.t2, 101
	mov pila_op.0, t1
	mov pila_op.1, 24
	Add
	mov t2, pila_op.0
	mov heap.t2, 99
	mov pila_op.0, t1
	mov pila_op.1, 25
	Add
	mov t2, pila_op.0
	mov heap.t2, 117
	mov pila_op.0, t1
	mov pila_op.1, 26
	Add
	mov t2, pila_op.0
	mov heap.t2, 99
	mov pila_op.0, t1
	mov pila_op.1, 27
	Add
	mov t2, pila_op.0
	mov heap.t2, 105
	mov pila_op.0, t1
	mov pila_op.1, 28
	Add
	mov t2, pila_op.0
	mov heap.t2, 243
	mov pila_op.0, t1
	mov pila_op.1, 29
	Add
	mov t2, pila_op.0
	mov heap.t2, 110
	mov pila_op.0, t1
	mov pila_op.1, 30
	Add
	mov t2, pila_op.0
	mov heap.t2, 33
	mov pila_op.0, t1
	mov pila_op.1, 31
	Add
	mov t2, pila_op.0
	mov heap.t2, 32
	mov pila_op.0, t1
	mov pila_op.1, 32
	Add
	mov t2, pila_op.0
	mov heap.t2, 68
	mov pila_op.0, t1
	mov pila_op.1, 33
	Add
	mov t2, pila_op.0
	mov heap.t2, 105
	mov pila_op.0, t1
	mov pila_op.1, 34
	Add
	mov t2, pila_op.0
	mov heap.t2, 109
	mov pila_op.0, t1
	mov pila_op.1, 35
	Add
	mov t2, pila_op.0
	mov heap.t2, 101
	mov pila_op.0, t1
	mov pila_op.1, 36
	Add
	mov t2, pila_op.0
	mov heap.t2, 110
	mov pila_op.0, t1
	mov pila_op.1, 37
	Add
	mov t2, pila_op.0
	mov heap.t2, 115
	mov pila_op.0, t1
	mov pila_op.1, 38
	Add
	mov t2, pila_op.0
	mov heap.t2, 105
	mov pila_op.0, t1
	mov pila_op.1, 39
	Add
	mov t2, pila_op.0
	mov heap.t2, 111
	mov pila_op.0, t1
	mov pila_op.1, 40
	Add
	mov t2, pila_op.0
	mov heap.t2, 110
	mov pila_op.0, t1
	mov pila_op.1, 41
	Add
	mov t2, pila_op.0
	mov heap.t2, 101
	mov pila_op.0, t1
	mov pila_op.1, 42
	Add
	mov t2, pila_op.0
	mov heap.t2, 115
	mov pila_op.0, t1
	mov pila_op.1, 43
	Add
	mov t2, pila_op.0
	mov heap.t2, 32
	mov pila_op.0, t1
	mov pila_op.1, 44
	Add
	mov t2, pila_op.0
	mov heap.t2, 102
	mov pila_op.0, t1
	mov pila_op.1, 45
	Add
	mov t2, pila_op.0
	mov heap.t2, 117
	mov pila_op.0, t1
	mov pila_op.1, 46
	Add
	mov t2, pila_op.0
	mov heap.t2, 101
	mov pila_op.0, t1
	mov pila_op.1, 47
	Add
	mov t2, pila_op.0
	mov heap.t2, 114
	mov pila_op.0, t1
	mov pila_op.1, 48
	Add
	mov t2, pila_op.0
	mov heap.t2, 97
	mov pila_op.0, t1
	mov pila_op.1, 49
	Add
	mov t2, pila_op.0
	mov heap.t2, 32
	mov pila_op.0, t1
	mov pila_op.1, 50
	Add
	mov t2, pila_op.0
	mov heap.t2, 100
	mov pila_op.0, t1
	mov pila_op.1, 51
	Add
	mov t2, pila_op.0
	mov heap.t2, 101
	mov pila_op.0, t1
	mov pila_op.1, 52
	Add
	mov t2, pila_op.0
	mov heap.t2, 32
	mov pila_op.0, t1
	mov pila_op.1, 53
	Add
	mov t2, pila_op.0
	mov heap.t2, 114
	mov pila_op.0, t1
	mov pila_op.1, 54
	Add
	mov t2, pila_op.0
	mov heap.t2, 97
	mov pila_op.0, t1
	mov pila_op.1, 55
	Add
	mov t2, pila_op.0
	mov heap.t2, 110
	mov pila_op.0, t1
	mov pila_op.1, 56
	Add
	mov t2, pila_op.0
	mov heap.t2, 103
	mov pila_op.0, t1
	mov pila_op.1, 57
	Add
	mov t2, pila_op.0
	mov heap.t2, 111
	mov pila_op.0, t1
	mov pila_op.1, 58
	Add
	mov t2, pila_op.0
	mov heap.t2, 46
	mov pila_op.0, t1
	mov pila_op.1, 59
	Add
	mov t2, pila_op.0
	mov heap.t2, 0
	mov t3, heap.t1
l68:
	mov pila_op.0, t3
	mov pila_op.1, 1
	Add
	mov t3, pila_op.0
	mov t1, heap.t3
	cmp t1, 0
	jumpEqual l69
	print(2, t1)
	jump l68
l69:
	print(2, 10)
l67:
	ret
end proc

proc B_void_B
	mov t1, stack.apstack
	mov pila_op.0, t1
	mov pila_op.1, 2
	Add
	mov t1, pila_op.0
	mov heap.t1, 6
	mov t2, stack.apstack
	mov pila_op.0, t2
	mov pila_op.1, 4
	Add
	mov t2, pila_op.0
	cmp 2, 4
	jumpBelow l71
	call exception
l71:
	mov t3, 2
	cmp 3, 5
	jumpBelow l72
	call exception
l72:
	mov pila_op.0, t3
	mov pila_op.1, 5
	Mul
	mov t4, pila_op.0
	mov pila_op.0, t4
	mov pila_op.1, 3
	Add
	mov t3, pila_op.0
	cmp 4, 5
	jumpBelow l73
	call exception
l73:
	mov pila_op.0, t3
	mov pila_op.1, 5
	Mul
	mov t4, pila_op.0
	mov pila_op.0, t4
	mov pila_op.1, 4
	Add
	mov t3, pila_op.0
	mov pila_op.0, t3
	mov pila_op.1, t2
	Add
	mov t2, pila_op.0
	mov heap.t2, 24
	mov t5, stack.apstack
	mov pila_op.0, t5
	mov pila_op.1, 104
	Add
	mov t5, pila_op.0
	cmp 1, 3
	jumpBelow l74
	call exception
l74:
	mov t6, 1
	cmp 4, 6
	jumpBelow l75
	call exception
l75:
	mov pila_op.0, t6
	mov pila_op.1, 6
	Mul
	mov t7, pila_op.0
	mov pila_op.0, t7
	mov pila_op.1, 4
	Add
	mov t6, pila_op.0
	mov pila_op.0, t6
	mov pila_op.1, t5
	Add
	mov t5, pila_op.0
	mov t8, stack.apstack
	mov pila_op.0, apstack
	mov pila_op.1, 1
	Add
	mov t9, pila_op.0
	mov stack.t9, t5
	mov pila_op.0, apstack
	mov pila_op.1, 2
	Add
	mov t9, pila_op.0
	mov stack.t9, t8
	mov pila_op.0, apstack
	mov pila_op.1, 5
	Add
	mov t9, pila_op.0
	mov stack.t9, 2.2
	mov pila_op.0, apstack
	mov pila_op.1, 6
	Add
	mov t9, pila_op.0
	mov stack.t9, 3.3
	mov pila_op.0, apstack
	mov pila_op.1, 3
	Add
	mov t9, pila_op.0
	mov stack.t9, t8
	mov pila_op.0, apstack
	mov pila_op.1, 3
	Add
	mov apstack, pila_op.0
	call B_float_add_float_float
	mov pila_op.0, apstack
	mov pila_op.1, 3
	Sub
	mov apstack, pila_op.0
	mov pila_op.0, apstack
	mov pila_op.1, 4
	Add
	mov t10, pila_op.0
	mov pila_op.0, apstack
	mov pila_op.1, 2
	Add
	mov t9, pila_op.0
	mov t8, stack.t9
	mov pila_op.0, apstack
	mov pila_op.1, 1
	Add
	mov t9, pila_op.0
	mov t5, stack.t9
	mov pila_op.0, 3.43
	mov pila_op.1, 3.3
	Mul
	mov t11, pila_op.0
	mov t10, stack.t10
	mov pila_op.0, t10
	mov pila_op.1, t11
	Add
	mov t10, pila_op.0
	mov heap.t5, t10
l70:
	ret
end proc

proc B_float_add_float_float
	mov t1, stack.apstack
	mov pila_op.0, t1
	mov pila_op.1, 2
	Add
	mov t1, pila_op.0
	mov t1, heap.t1
	print(0, t1)
	print(2, 10)
	mov pila_op.0, apstack
	mov pila_op.1, 2
	Add
	mov t2, pila_op.0
	mov pila_op.0, apstack
	mov pila_op.1, 3
	Add
	mov t3, pila_op.0
	mov t2, stack.t2
	mov t3, stack.t3
	mov pila_op.0, t2
	mov pila_op.1, t3
	Add
	mov t2, pila_op.0
	mov pila_op.0, apstack
	mov pila_op.1, 1
	Add
	mov t4, pila_op.0
	mov stack.t4, t2
l76:
	ret
end proc

proc exception(){
	print(2, 69)
	print(2, 114)
	print(2, 114)
	print(2, 111)
	print(2, 114)
	print(2, 32)
	print(2, 101)
	print(2, 110)
	print(2, 32)
	print(2, 116)
	print(2, 105)
	print(2, 101)
	print(2, 109)
	print(2, 112)
	print(2, 111)
	print(2, 32)
	print(2, 100)
	print(2, 101)
	print(2, 32)
	print(2, 101)
	print(2, 106)
	print(2, 101)
	print(2, 99)
	print(2, 117)
	print(2, 99)
	print(2, 105)
	print(2, 111)
	print(2, 110)
	print(2, 33)
	print(2, 32)
	print(2, 68)
	print(2, 105)
	print(2, 109)
	print(2, 101)
	print(2, 110)
	print(2, 115)
	print(2, 105)
	print(2, 111)
	print(2, 110)
	print(2, 101)
	print(2, 115)
	print(2, 32)
	print(2, 102)
	print(2, 117)
	print(2, 101)
	print(2, 114)
	print(2, 97)
	print(2, 32)
	print(2, 100)
	print(2, 101)
	print(2, 32)
	print(2, 114)
	print(2, 97)
	print(2, 110)
	print(2, 103)
	print(2, 111)
	print(2, 46)
	print(2, 10)
end proc
