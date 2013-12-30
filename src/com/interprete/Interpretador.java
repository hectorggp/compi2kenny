package com.interprete;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import com.genobjeto.Objetador;
import com.ide.Ventana;
import com.tresd.Clase;

public class Interpretador {
	private static final int tMOV = 1;
	private static final int tCMP = 2;
	private static final int tJMP = 3;
	private static final int tARITM = 4;
	private static final int tRET = 5;
	private static final int tPRINT = 6;
	private static final int tCALL = 7;
	private static final String heap = Clase.Heap;
	private static final String stack = Clase.Stack;
	private static final String sptr_val = Objetador.ptr_val;
	private static final String spila_op = Objetador.pilaop;
	public static int pausa = 10;

	private int indPila = 0;
	private float ptr_val;
	private float cmp_r;
	private float [] pila_op = new float[7];
	private Salto procActual;
	private ArrayList<Salto> saltos = new ArrayList<Salto>();
	private ArrayList<Salto> procs = new ArrayList<Salto>();
	private int linea;
	private TableItem [] Heap;
	private TableItem [] Stack;
	private Table vars;
	private TableItem [] vvars;
	private TableItem [] Pila;
	private TableItem [] Depuracion;
	private StyledText consola;
	private StyledText text;

	public Interpretador(StyledText text, StyledText consola, Table table_variables,
			TableItem [] items_PilaDeEjecucion, TableItem [] items_LineasDeDepuracion, 
			TableItem [] items_Stack, TableItem [] items_Heap){
		this.text = text;
		this.consola = consola;
		this.vars = table_variables;
		this.Pila = items_PilaDeEjecucion;
		this.Depuracion = items_LineasDeDepuracion;
		this.Stack = items_Stack;
		this.Heap = items_Heap;
		
		primera();
		vvars = vars.getItems();
		segunda();
	}
	
	private void primera(){
		for(int i = 0; i < text.getLineCount(); i++){
			String linea = text.getLine(i);
			if(linea.length() > 0){
				String vlinea [] = linea.split(" ");
				if(vlinea[0].charAt(0) != '\t'){
					if(vlinea[0].equals("dec") && !linea.contains(".")){
						for(int j = 1; j < vlinea.length; j++){
							vlinea[j] = vlinea[j].replace(",", "");
							TableItem var = new TableItem(vars, SWT.None);
							var.setText(0, vlinea[j]);
							var.setText(1, "0");
						}
					} else {
						if(!vlinea[0].equals("dec")){
							if(vlinea.length == 2 && !vlinea[0].equals("end"))
								procs.add(new Salto(vlinea[1], i));
							else
								saltos.add(new Salto(linea.replace(":", ""), i));
						}
					}
				}
			}
			
		}
	}	
	
	private Salto searchProc(String name){
		ItemTablesetText(Pila[indPila ++], 0, name);
		return procs.get(procs.indexOf(new Salto(name, 0)));
	}
	
	private Salto searchSalto(String et){
		return saltos.get(saltos.indexOf(new Salto(et, 0)));
	}
	
	private int lineCount;
	private int textgetLineCount(){
		Ventana.ventana.syncExec(new Runnable(){
			public void run() {
				lineCount = text.getLineCount();
			}
		});
		return lineCount;
	}
	
	private String sline;
	private String textgetLine(final int linea){
		Ventana.ventana.syncExec(new Runnable(){
			public void run() {
				sline = text.getLine(linea);
			}
		});
		return sline;
	}
	
	private int offset;
	private int textgetOffsetAtLine(final int linea){
		Ventana.ventana.syncExec(new Runnable(){
			public void run() {
				offset = text.getOffsetAtLine(linea);
			}
		});
		return offset;
	}
	
	private void segunda(){
		procActual = searchProc("main").clone();
		linea = procActual.linea;
		new Thread(){
			@Override
			public void run(){
				while(Ventana.ejecutando){
					if(!Ventana.pausado || Ventana.siguiente){
						if((linea = siguienteLinea()) >= 0 && 
								linea < textgetLineCount()){
							final int len = textgetLine(linea).length();
							final int off = textgetOffsetAtLine(linea);
							Ventana.ventana.syncExec(new Runnable(){
								public void run() {
									text.setSelection(off, off + len);
									text.redraw();
								}
							});
							Ventana.siguiente = false;
							try {
								Thread.sleep(pausa);
							} catch (InterruptedException e) { }
						} else 
							break;
						while(!Ventana.siguiente){
							if(esPuntoDeDepuracion(linea) && Ventana.puntos){
							}
							else 
								break;
						}
						Ventana.siguiente = false;
					} else
						System.out.println("parado");
				}
				Ventana.ejecutando = false;
			}
		}.start();
	}
	
	private int examinar(String [] vlinea){
		if(vlinea[0].equals("mov"))
			return tMOV;
		else if(vlinea[0].equals("cmp"))
			return tCMP;
		else if(vlinea[0].contains("jump"))
			return tJMP;
		else if(vlinea[0].equals("ret") || vlinea[0].equals("end"))
			return tRET;
		else if(vlinea.length == 1 && !vlinea[0].contains(":") && !vlinea[0].equals(""))
			return tARITM;
		else if(vlinea[0].equals("print"))
			return tPRINT;
		else if(vlinea[0].equals("call"))
			return tCALL;
		return -1;
	}
	
	private int siguienteLinea(){
		int linea = this.linea;
		String text = textgetLine(linea);
		//System.out.println(text);
		String [] vlinea = text.replace("\t", "").split(" ");
		switch(examinar(vlinea)){
		case tMOV:
			mov(vlinea[1].replace(",", ""), vlinea[2]);
			linea ++;
			break;
		case tCMP:
			cmp(vlinea[1].replace(",", ""), vlinea[2]);
			linea ++;
			break;
		case tJMP:
			linea = jmp(vlinea[0].substring(4), vlinea[1]);
			break;
		case tRET:
			procActual = procActual.sup;
			if(procActual == null)
				linea = -1;
			else
				linea = procActual.linea;
			ItemTablesetText(Pila[-- indPila], 0, "");
			break;
		case tARITM:
			if(vlinea[0].equals("Add"))
				pila_op[0] += pila_op[1];
			else if(vlinea[0].equals("Sub"))
				pila_op[0] -= pila_op[1];
			else if(vlinea[0].equals("Mul"))
				pila_op[0] *= pila_op[1];
			else if(vlinea[0].equals("Div"))
				pila_op[0] /= pila_op[1];
			else if(vlinea[0].equals("Mod"))
				pila_op[0] %= pila_op[1];
			else if(vlinea[0].equals("Exp"))
				pila_op[0] = (float) Math.pow(pila_op[0], pila_op[1]);
			else
				try {
					throw new Exception("Operacion aritmética no soportada");
				} catch (Exception e) {
					e.printStackTrace();
				}
			int i;
			for(i = 2; i < pila_op.length; i++)
				pila_op[i - 1] = pila_op[i];
			pila_op[i - 1] = 0;
			linea ++;
			break;
		case tPRINT:
			String print = "";
			switch(Integer.valueOf(vlinea[1])){
			case 1:
				print = ptr_val + "";
				break;
			case 0:
				print = ((int) ptr_val) + "";
				break;
			default:
				print = "" + (char)((int) ptr_val);
			}
			final String prints = print;
			Ventana.ventana.syncExec(new Runnable(){
				public void run() {
					consola.setText(consola.getText() + prints);
				}
			});
			linea ++;
			break;
		case tCALL:
			procActual.linea = linea + 1;
			Salto proc = searchProc(vlinea[1]).clone();
			proc.sup = procActual;
			procActual = proc;
			linea = procActual.linea;
			break;
		default:
			linea ++;
		}
		return linea;
	}
	
	private boolean isNumeric(String sc){
		try {
			Float.valueOf(sc);
			return true;
		} catch (Exception e){
			return false;
		}
	}
	
	private String itemText;
	private String ItemTablegetText(final TableItem item, final int column){
		Ventana.ventana.syncExec(new Runnable(){
			public void run() {
				itemText = item.getText(column);
			}
		});
		return itemText;
	}
	
	private void ItemTablesetText(final TableItem item, final int column, final String text){
		Ventana.ventana.syncExec(new Runnable(){
			public void run() {
				item.setText(column, text);
			}
		});
	}
	
	private float valorDe(String var){
		float ret = 0;
		if(isNumeric(var))
			return Float.valueOf(var);
		String [] vvar = var.split("\\.");
		if(vvar.length == 1){
			if(var.charAt(0) == 't')
				return Float.valueOf(ItemTablegetText(vvars[Integer.valueOf(var.substring(1)) + 1], 1));
			else if(ItemTablegetText(vvars[0], 0).equals(var))
				return Float.valueOf(ItemTablegetText(vvars[0], 1));
			else if(ItemTablegetText(vvars[1], 0).equals(var))
				return Float.valueOf(ItemTablegetText(vvars[1], 1));
			else
				try {
					throw new Exception("'" + var +  "' es una fuente inválida");
				} catch (Exception e) {
					e.printStackTrace();
				} 
		} else if(vvar.length == 2){
			int ind = (int) valorDe(vvar[1]);
			if(vvar[0].equals(stack))
				return Float.valueOf(ItemTablegetText(Stack[ind], 1));
			else if(vvar[0].equals(heap))
				return Float.valueOf(ItemTablegetText(Heap[ind], 1));
			else if(vvar[0].equals(spila_op))
				return pila_op[ind];
			else
				try {
					throw new Exception("'" + var +  "' es una fuente inválida");
				} catch (Exception e) {
					e.printStackTrace();
				} 
		}
		return ret;
	}
	
	private void mov(String ds, String sr){
		Float vsr = valorDe(sr);
		String [] vds = ds.split("\\.");
		if(vds.length == 1){
			if(ds.charAt(0) == 't')
				ItemTablesetText(vvars[Integer.valueOf(ds.substring(1)) + 1], 1, vsr.toString());
			else if(ds.equals(sptr_val))
				ptr_val = vsr;
			else if(ItemTablegetText(vvars[1], 0).equals(ds))
				ItemTablesetText(vvars[1], 1, vsr.toString());
			else if(ItemTablegetText(vvars[0], 0).equals(ds))
				ItemTablesetText(vvars[0], 1, vsr.toString());
			else
				try {
					throw new Exception("'" + ds + "' es un destino inválido");
				} catch (Exception e) {
					e.printStackTrace();
				}
		} else if(vds.length == 2){
			int ind = (int) valorDe(vds[1]);
			if(vds[0].equals(stack)){
				ItemTablesetText(Stack[ind], 1, vsr.toString());
			} else if(vds[0].equals(heap)){
				ItemTablesetText(Heap[ind], 1, vsr.toString());
			} else if(vds[0].equals(spila_op)){
				pila_op[ind] = vsr;
			} else
				try {
					throw new Exception("'" + ds + "' es un destino inválido");
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
	}
	
	private void cmp(String cm1, String cm2){
		cmp_r = valorDe(cm1) - valorDe(cm2);
	}
	
	private int jmp(String cond, String et){
		if(cond.equals("")
				|| cond.equals("Equal") && cmp_r == 0
				|| cond.equals("NotEqual") && cmp_r != 0
				|| cond.equals("Below") && cmp_r < 0
				|| cond.equals("BelowEqual") && cmp_r <= 0
				|| cond.equals("Above") && cmp_r > 0
				|| cond.equals("AboveEqual") && cmp_r >= 0)
			return searchSalto(et).linea;
		return linea + 1;
	}
	
	private boolean esPuntoDeDepuracion(int linea){
		boolean ret = false;
		for(TableItem item : Depuracion){
			String dep = ItemTablegetText(item, 0);
			if(dep.equals(""))
				break;
			if(isNumeric(dep)){
				float pdep = Float.valueOf(dep);
				int idep = (int) pdep;
				ret = linea + 1 == idep;
				if(ret)
					break;
			}
		}
		return ret;
	}
}





