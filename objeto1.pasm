dec apstack, apheap
dec stack.1000
dec heap.10000
dec t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12

proc main
	mov apstack, 8.24
	mov apheap, 124.4
	mov t2, 42.34
	mov stack.2, 424
	cmp 3, 4
et1:
	jumpEqual et
	mov apstack, 4
	cmp apstack, 4
	jump et1
et:
	mov apstack, 42.24