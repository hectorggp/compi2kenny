package com.optimizador;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

public class Optimus {
	
	private File archivo;
	private String mirilla;
	private int tamMirilla = 20;
	private String [] lineas;
	private int ind = 0;
	private boolean exito = true;
	
	/**
	 * Constructor. Reliza la optimización del archivo inmediatamente
	 * @param archivo
	 */
	public Optimus(File archivo){
		this.archivo = archivo;
		int i = 0;
		while(i ++ < 20 && exito){
			System.out.println("vez " + i);
			exito = false;
			leerArchivo();
			optimizar();
			actualizarTexto();
		}
	}
	
	/**
	 * Realiza una lectura del archivo por la cantidad de líneas especificadas en 
	 * variable global
	 * @return Texto de mirilla
	 */
	private boolean hayMásTexto(){
		mirilla = "";
		for(int i = 0; i < tamMirilla; i++){
			if(ind < lineas.length){
				mirilla += lineas[ind++] + "\n";
			} else
				return false;
		}
		return true;
	}
	
	/**
	 * Realiza la lectura del archivo y coloca texto leido en variable
	 */
	private void leerArchivo(){
		FileReader fr = null;
		BufferedReader br;
		String texto = "";
		try{
			fr = new FileReader(archivo);
			br = new BufferedReader(fr);
			while(br.ready())
				texto += br.readLine() + "\n";
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
		lineas = texto.split("\\n");
	}
	
	/**
	 * Setea el texto en el archivo
	 */
	private void actualizarTexto(){
		FileWriter fichero = null;
		PrintWriter pw;
		try{
			fichero = new FileWriter(archivo);
			pw = new PrintWriter(fichero);
			for(String linea : lineas)
				if(linea != null)
					pw.println(linea);
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			try {
				if(null != fichero)
					fichero.close();
			} catch (Exception ex){
				ex.printStackTrace();
			}
		}
	}
	
	private void optimizar(){
		for(int i = 0; i < lineas.length; i ++){
			String linea = lineas[i];
			if(linea == null) continue;
			String cmps [] = linea.split(" ");
			if(cmps.length == 5) { // puede ser optimizado algrebraico
				if(cmps[3].equals("+") && cmps[4].equals("0;")
						|| cmps[3].equals("*") && cmps[4].equals("1;")){
					lineas[i] = cmps[0] + " " + cmps[1] + " " + cmps[2] + ";";
					exito = true;
				} else if(cmps[3].equals("*") && cmps[4].equals("0;")){
					lineas[i] = cmps[0] + " " + cmps[1] + " 0;";
					exito = true;
				}
			} else if(cmps.length == 2 && i < lineas.length - 1 && cmps[0].replace("\t", "").equals("goto")){
				String sigIns = lineas[i + 1].replace(':', ';');
				System.out.println(sigIns);
				if(sigIns.equals(cmps[1])){
					exito = true;
					lineas[i] = null;
				} else if(sigIns.charAt(0) == '\t'){
					lineas[i + 1] = null;
					exito = true;
				}
			} else if(cmps.length == 6){
				if((cmps[1].equals("(1") && cmps[3].equals("0)")
						|| cmps[1].equals("(0") && cmps[3].equals("1)"))
						&& cmps[2].equals("==")){
					exito = true;
					lineas[i] = null;
				}
				else if((cmps[1].equals("(1") && cmps[3].equals("0)")
						|| cmps[1].equals("(0") && cmps[3].equals("1)"))
						&& cmps[2].equals("==")){
					lineas[i] = "goto " + cmps[5];
					exito = true;
				}
			} 
		}		
	}
}
