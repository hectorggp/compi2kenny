package com.genobjeto;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

public class Objetador {
	
	private File archivo;
	private File archivoh;
	public static final String pilaop = "pila_op";
	public static final String ptr_val = "ptr_val";
	
	public Objetador(File archivo3D, File archivo3Dh){
		archivoh = archivo3Dh;
		archivo = archivo3D;
		add(false, "");
		generarDeclaraciones();
		generarCodigoObjeto();
		System.out.println("éxito generando código objeto");
	}
	
	private void generarDeclaraciones(){
		FileReader fr = null;
		BufferedReader br;
		try{
			fr = new FileReader(archivoh);
			br = new BufferedReader(fr);
			while(br.ready()){
				convertirLineaH(br.readLine());
			}
		} catch (Exception e){
			e.printStackTrace();
		} finally{
			try{
				if(null != fr)
					fr.close();
			} catch(Exception ex){
				ex.printStackTrace();
			}
		}
	}
	
	private void convertirLineaH(String linea){
		String [] vlinea = linea.split(" ");
		if((vlinea[0].equals("float") || vlinea[0].equals("int")) 
				&& !vlinea[1].contains("main(void);")){
			add(linea.replace(";", "").replace("]", "").replace("[", ".")
					.replace("int", "dec").replace("float", "dec") + "\n");
		}
	}
		
	private void generarCodigoObjeto(){
		FileReader fr = null;
		BufferedReader br;
		try{
			fr = new FileReader(archivo);
			br = new BufferedReader(fr);
			while(br.ready()){
				convertirLinea(br.readLine());
			}
		} catch (Exception e){
			e.printStackTrace();
		} finally{
			try{
				if(null != fr)
					fr.close();
			} catch(Exception ex){
				ex.printStackTrace();
			}
		}
	}
	
	private void addt(String salida){
		add("\t" + salida + "\n");
	}
	
	private void add(String salida){
		add(true, salida);
	}
	
	private void add(boolean truncar, String salida){
		FileWriter fichero = null;
		PrintWriter pw;
		try {
			fichero = new FileWriter(archivo.getAbsoluteFile().getParent() + "/objeto.pasm", truncar);
			pw = new PrintWriter(fichero);

			pw.print(salida);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null!= fichero)
					fichero.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
	
	public String nombreArchivoObj(){
		return archivo.getAbsoluteFile().getParent() + "/objeto.pasm";
	}
	
	private void convertirLinea(String linea){
		if(linea.length() == 0){
			add("\n");
		} else if(linea.charAt(0) == '#'){
		} else if(linea.charAt(0) != '\t'){
			String cmps [] = linea.split(" ");
			if(cmps.length >= 2){
				if(cmps[1].charAt(cmps[1].length() - 1) == ')')
					cmps[1] = cmps[1].substring(0, cmps[1].length() - 2);
				add("proc " + cmps[1].replace("(void){", "") + "\n");
			} else if(linea.charAt(0) == '}')
				add("end proc\n");
			else
				add(linea + "\n");
		} else{
			linea = quitarBasura(linea);
			String [] vlinea = linea.split(" ");
			if(vlinea.length == 3){ // instrucción mov sólamente
				addt("mov " + aArreglo(vlinea[0]) + ", " + aArreglo(vlinea[2]));
			} else if(vlinea.length == 4){ // no hay instrucción así
				add(linea + " ; CUATRO wtf????");
			} else if(vlinea.length == 5){
				addt("mov " + pilaop + ".0, " + vlinea[2]);
				addt("mov " + pilaop + ".1, " + vlinea[4]);
				addt(operacion(vlinea[3]));
				addt("mov " + vlinea[0] + ", " + pilaop + ".0");
			} else if(vlinea.length == 2 && vlinea[0].equals("goto")){
				addt("jump " + vlinea[1]);
			} else if(vlinea.length == 6){
				addt("cmp " + par(vlinea[1]) + ", " + par(vlinea[3]));
				addt(jump(vlinea[2]) + " " + vlinea[5]);
			} else if(vlinea.length == 1 && vlinea[0].equals("return")){
				addt("ret");
			} else if(vlinea[0].contains("printf")){
				addt(genPrint(linea.replace("printf", "").replace("(", "").replace(")", "")
						.replace("\"", "").replace("%", "")));
			} else if(vlinea.length == 1 && vlinea[0].contains("()")){
				addt("call " + linea.replace("()", ""));
			} else
				addt(linea + " ; pero q mierda??");
		}
	}
	
	private String quitarBasura(String linea){
		linea = linea.replace(";", "");
		linea = linea.replace("(int) ", "");
		linea = linea.replace(" (int)", "");
		linea = linea.replace("(int)", "");
		linea = linea.replace(" (char)", "");
		linea = linea.replace("\t", "");
		return linea;
	}
	
	private String aArreglo(String var){
		if(!var.contains("["))
			return var;
		var = var.replace("[", ":");
		String vvar [] = var.split(":");
		return vvar[0] + "." + vvar[1].substring(0, vvar[1].length() - 1);
	}
	
	private String operacion(String op){
		if(op.equals("+"))
			return "Add";
		else if(op.equals("-"))
			return "Sub";
		else if(op.equals("*"))
			return "Mul";
		else if(op.equals("/"))
			return "Div";
		else if(op.equals("%"))
			return "Mod";
		return  "NOOP: " + op;
	}
	
	private String par(String txt){
		return txt.replace("(", "").replace(")", "");
	}
	
	private String jump(String op){
		if(op.equals("=="))
			return "jumpEqual";
		else if(op.equals("!="))
			return "jumpNotEqual";
		else if(op.equals("<"))
			return "jumpBelow";
		else if(op.equals("<="))
			return "jumpBelowEqual";
		else if(op.equals(">"))
			return "jumpAbove";
		else if(op.equals(">="))
			return "jumpAboveEqual";
		return "NOJUMP";
	}
	
	private String genPrint(String txt){
		String [] vret = txt.split(",");
		String ret = "mov " + ptr_val + ", " + vret[1] + "\n\tprint ";
		if(vret[0].equals("d"))
			ret += "0";
		else if(vret[0].equals("c"))
			ret += "2";
		else if(vret[0].equals(("lf")))
			ret += "1";
		return ret;
	}
}
