package com.ide;

import java.io.BufferedReader;
import java.io.CharArrayReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.Bullet;
import org.eclipse.swt.custom.LineStyleEvent;
import org.eclipse.swt.custom.LineStyleListener;
import org.eclipse.swt.custom.ST;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GlyphMetrics;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import com.formato.Formador;
import com.genobjeto.Objetador;
import com.gs.Generador;
import com.interprete.Interpretador;
import com.optimizador.Optimus;
import com.tresd.Clase;

public class Ventana {

	public static boolean ejecutando = false;
	public static boolean siguiente = true;
	public static boolean pausado = false;
	public static boolean puntos = false;
	String titulo = "Compi 2 IDE";
	private Shell shell;
	public static Display ventana;
	private String [] extensiones = {"*.java;*.c;*.pasm", "*.java", "*.c", "*.pasm"};
	File archivo;
	File archivoh;
	private String errorArchivo;
	private final static String javaExt = "java";
	private final static String cExt = "c";
	private final static String asmExt = "pasm";
	private StyledText text;
	private StyledText consola;
	private Table table_variables;
	private final TableItem [] items_PilaDeEjecucion = new TableItem[20];
	private final TableItem [] items_LineasDeDepuracion = new TableItem[20];
	private final TableItem [] items_Stack = new TableItem[Clase.tamStack];
	private final TableItem [] items_Heap = new TableItem[Clase.tamHeap];
	/**
	 * Inicia la instancia controladora del panel de juego y niveles. 
	 */
	public Ventana(){
		crearVentana();
	}
	/**
	 * Inicia la ventana principal de la aplicación.
	 */
	private void crearVentana(){
		ventana = new Display ();
		shell = new Shell (ventana);
		shell.setText (titulo);

		// barra de menús
		Menu barra = new Menu (shell, SWT.BAR);
		shell.setMenuBar (barra);
		// menú archivo
		MenuItem menuArchivo = new MenuItem (barra, SWT.CASCADE);
		menuArchivo.setText ("&Archivo");
		Menu submenuArchivo = new Menu (shell, SWT.DROP_DOWN);
		menuArchivo.setMenu (submenuArchivo);
		
		// item archivo -> abrir
		MenuItem itemAbrir = new MenuItem (submenuArchivo, SWT.PUSH);
		itemAbrir.addListener (SWT.Selection, new Listener () {
			public void handleEvent (Event e) {
				abrir();
			}
		});
		itemAbrir.setText ("&Abrir\tCtrl+A");
		itemAbrir.setAccelerator (SWT.MOD1 + 'A');
		
		// item archivo -> guardar
		MenuItem itemGuardar = new MenuItem (submenuArchivo, SWT.PUSH);
		itemGuardar.addListener(SWT.Selection, new Listener () {
			public void handleEvent (Event e) {
				guardar();
			}
		});
		itemGuardar.setText("&Guardar\tCtrl+G");
		itemGuardar.setAccelerator(SWT.MOD1 + 'G');
		
		// item archivo -> guardar como
		MenuItem itemGuardarc = new MenuItem (submenuArchivo, SWT.PUSH);
		itemGuardarc.addListener(SWT.Selection, new Listener () {
			public void handleEvent (Event e) {
				guardarComo();
			}
		});
		itemGuardarc.setText("&Guardar como...\tCtrl+Shift+G");
		itemGuardarc.setAccelerator(SWT.MOD1 + SWT.MOD2 + 'G');

		// item archivo -> salir
		MenuItem itemSalir = new MenuItem (submenuArchivo, SWT.PUSH);
		itemSalir.addListener(SWT.Selection, new Listener(){
			public void handleEvent (Event e) {
				System.exit(0);
			}
		});
		itemSalir.setText("&Salir\tCtrl+Q");
		itemSalir.setAccelerator(SWT.MOD1 + 'Q');
		
		// menú Edición
		MenuItem menuEdicion = new MenuItem (barra, SWT.CASCADE);
		menuEdicion.setText ("&Edición");
		Menu submenuEdicion = new Menu (shell, SWT.DROP_DOWN);
		menuEdicion.setMenu (submenuEdicion);
		
		// item Edición -> Formatear
		MenuItem itemFormatear = new MenuItem (submenuEdicion, SWT.PUSH);
		itemFormatear.addListener(SWT.Selection, new Listener(){
			public void handleEvent (Event e) {
				formatear();
			}
		});
		itemFormatear.setText("&Formatear\tCtrl+Shift+F");
		itemFormatear.setAccelerator(SWT.MOD1 + SWT.MOD2 + 'F');		

		// item Edición -> Formatear
		MenuItem itemGenerar = new MenuItem (submenuEdicion, SWT.PUSH);
		itemGenerar.addListener(SWT.Selection, new Listener(){
			public void handleEvent (Event e) {
				generar();
			}
		});
		itemGenerar.setText("Ge&nerar getters y setters\tCtrl+N");
		itemGenerar.setAccelerator(SWT.MOD1 + 'N');		

		// menú Ejecutar
		MenuItem menuEjecutar = new MenuItem (barra, SWT.CASCADE);
		menuEjecutar.setText ("E&jecutar");
		Menu submenuEjecutar = new Menu (shell, SWT.DROP_DOWN);
		menuEjecutar.setMenu (submenuEjecutar);
		
		// item Ejecutar -> Ejecutar
		MenuItem itemEjecutar = new MenuItem (submenuEjecutar, SWT.PUSH);
		itemEjecutar.addListener(SWT.Selection, new Listener(){
			public void handleEvent (Event e) {
				ejecutar();
			}
		});
		itemEjecutar.setText("&Ejecutar\tCtrl+E");
		itemEjecutar.setAccelerator(SWT.MOD1 + 'E');	
		
		// item Ejecutar -> Continuar
		MenuItem itemContinuar = new MenuItem(submenuEjecutar, SWT.PUSH);
		itemContinuar.addListener(SWT.Selection, new Listener(){
			public void handleEvent(Event e){
				siguiente = true;
			}
		});
		itemContinuar.setText("Continuar\tCtrl+U");
		itemContinuar.setAccelerator(SWT.MOD1 + 'U');
		
		// item Ejecutar -> Pausado
		MenuItem itemPausado = new MenuItem(submenuEjecutar, SWT.CHECK);
		itemPausado.addListener(SWT.Selection, new Listener(){
			public void handleEvent(Event e){
				pausado = !pausado;
			}
		});
		itemPausado.setText("Pausa\tCtrl+P");
		itemPausado.setAccelerator(SWT.MOD1 + 'P');
		
		MenuItem itemParar = new MenuItem(submenuEjecutar, SWT.PUSH);
		itemParar.addListener(SWT.Selection, new Listener(){
			public void handleEvent(Event e){
				ejecutando = false;
			}
		});
		itemParar.setText("Parar\tCtrl+S");
		itemPausado.setAccelerator(SWT.MOD1 + 'S');
		
		MenuItem itemPuntos = new MenuItem(submenuEjecutar, SWT.CHECK);
		itemPuntos.addListener(SWT.Selection, new Listener(){
			public void handleEvent(Event e){
				puntos = !puntos;
			}
		});
		itemPuntos.setText("Puntos de Depuración\tCtrl+K");
		itemPuntos.setAccelerator(SWT.MOD1 + 'K');
		
		MenuItem itemTiempo = new MenuItem(submenuEjecutar, SWT.PUSH);
		itemTiempo.addListener(SWT.Selection, new Listener(){
			public void handleEvent(Event e){
				String res = JOptionPane.showInputDialog("Ingresar tiempo de pausa");
				if(res != null){
					try{
						Interpretador.pausa = Integer.valueOf(res);
					} catch (Exception dde){}
				}
			}
		});
		itemTiempo.setText("&Tiempo de Espera\tCtrl+T");
		itemTiempo.setAccelerator(SWT.MOD1 + 'T');

		// establecer layout
		GridLayout reja = new GridLayout(3,  true);
		reja.marginHeight = 3;
		reja.marginWidth = 3;
		reja.horizontalSpacing = 4;
		reja.verticalSpacing = 4;
		shell.setLayout(reja);
		
		// área de texto
		text = new StyledText(shell, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);	
		text.setTabs(4);
		text.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 5));

		// cambiar Fuente de área de texto
		Font initialFont = text.getFont();
		FontData[] fontData = initialFont.getFontData();
		for (int i = 0; i < fontData.length; i++)
			fontData[i].setName("Ubuntu mono");
		Font newFont = new Font(ventana, fontData);
		text.setFont(newFont);
		
		text.addLineStyleListener(new LineStyleListener() {

			@Override
			public void lineGetStyle(LineStyleEvent e) {
		        //Set the line number
		        e.bulletIndex = text.getLineAtOffset(e.lineOffset);

		        //Set the style, 12 pixles wide for each digit
		        StyleRange style = new StyleRange();
		        style.metrics = new GlyphMetrics(0, 0, 
		        		Integer.toString(text.getLineCount() + 1).length() * 12);
		        int col = e.bulletIndex % 2 == 0 ? 180 : 110 ;
		        style.foreground = new Color(ventana, col, col, col);

		        //Create and set the bullet
		        e.bullet = new Bullet(ST.BULLET_NUMBER, style);
		        
		        e.styles = styles(e.lineText, e.lineOffset);;
			}
		});
		//*/
		
		GridData grd;
		
		grd = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		grd.heightHint = 10;
		Table table_PilaDeEjecucion = new Table(shell, SWT.SINGLE | SWT.BORDER);
		table_PilaDeEjecucion.setLinesVisible(true);
		table_PilaDeEjecucion.setHeaderVisible(true);
		table_PilaDeEjecucion.setLayoutData(grd);
		TableColumn column_PilaDeEjecucion = new TableColumn(table_PilaDeEjecucion, SWT.None);
		column_PilaDeEjecucion.setText("Pila de ejecución");
		for(int i = 0; i < items_PilaDeEjecucion.length; i++)
			items_PilaDeEjecucion[i] = new TableItem(table_PilaDeEjecucion, SWT.None);
		column_PilaDeEjecucion.pack();
		
		grd = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		grd.heightHint = 10;
		final Table table_lineasDep = new Table(shell, SWT.SINGLE | SWT.BORDER);
		table_lineasDep.setLinesVisible(true);
		table_lineasDep.setHeaderVisible(true);
		table_lineasDep.setLayoutData(grd);
		TableColumn column_lineasDep = new TableColumn(table_lineasDep, SWT.None);
		column_lineasDep.setText("Líneas de depuración");
		for(int i = 0; i < items_LineasDeDepuracion.length; i++)
			items_LineasDeDepuracion[i] = new TableItem(table_lineasDep, SWT.None);
		column_lineasDep.pack();

		final TableEditor editor = new TableEditor(table_lineasDep);
		//The editor must have the same size as the cell and must
		//not be any smaller than 50 pixels.
		editor.horizontalAlignment = SWT.LEFT;
		editor.grabHorizontal = true;
		editor.minimumWidth = 50;
		// editing the second column
		final int EDITABLECOLUMN = 0;
		
		table_lineasDep.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				// Clean up any previous editor control
				Control oldEditor = editor.getEditor();
				if (oldEditor != null) oldEditor.dispose();
		
				// Identify the selected row
				if (e.item == null) return;
				TableItem item = (TableItem)e.item;
		
				// The control that will be the editor must be a child of the Table
				Text newEditor = new Text(table_lineasDep, SWT.NONE);
				newEditor.setText(item.getText(EDITABLECOLUMN));
				newEditor.addModifyListener(new ModifyListener() {
					@Override
					public void modifyText(ModifyEvent arg0) {
						Text text = (Text)editor.getEditor();
						editor.getItem().setText(EDITABLECOLUMN, text.getText());
					}
				});
				newEditor.selectAll();
				newEditor.setFocus();
				editor.setEditor(newEditor, item, EDITABLECOLUMN);
			}
		});

		
		grd = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		grd.heightHint = 10;
		Table table_stack = new Table(shell, SWT.SINGLE | SWT.BORDER);
		table_stack.setLinesVisible(true);
		table_stack.setHeaderVisible(true);
		table_stack.setLayoutData(grd);
		TableColumn column_stack = new TableColumn(table_stack, SWT.None);
		TableColumn column_stack1 = new TableColumn(table_stack, SWT.None);
		column_stack.setText("Posición Stack");
		column_stack1.setText("Valor Stack");
		for(int i = 0; i < items_Stack.length; i++){
			items_Stack[i] = new TableItem(table_stack, SWT.None);
			items_Stack[i].setText(0, i + "");
			items_Stack[i].setText(1, "0");
		}
		column_stack1.pack();
		column_stack.pack();
		
		grd = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		grd.heightHint = 10;
		Table table_heap = new Table(shell, SWT.SINGLE | SWT.BORDER);
		table_heap.setLinesVisible(true);
		table_heap.setHeaderVisible(true);
		table_heap.setLayoutData(grd);
		TableColumn column_heap = new TableColumn(table_heap, SWT.None);
		TableColumn column_heap1 = new TableColumn(table_heap, SWT.None);
		column_heap.setText("Posición Heap");
		column_heap1.setText("Valor Heap");
		for(int i = 0; i < items_Heap.length; i++){
			items_Heap[i] = new TableItem(table_heap, SWT.None);
			items_Heap[i].setText(0, i + "");
			items_Heap[i].setText(1, "0");
		}
		column_heap.pack();
		column_heap1.pack();
		
		grd = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		grd.heightHint = 10;
		table_variables = new Table(shell, SWT.SINGLE | SWT.BORDER);
		table_variables.setLinesVisible(true);
		table_variables.setHeaderVisible(true);
		table_variables.setLayoutData(grd);
		TableColumn column_variables = new TableColumn(table_variables, SWT.None);
		TableColumn column_variables1 = new TableColumn(table_variables, SWT.None);
		column_variables.setText("Variable");
		column_variables1.setText("Valor");
		column_variables.pack();
		column_variables1.pack();
		
		consola = new StyledText(shell, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		consola.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		consola.setEditable(false);
		consola.setText("Consola:\n");
		
		shell.setSize (700, 400);
		shell.open();
		while (!shell.isDisposed())
			if (!ventana.readAndDispatch())
				ventana.sleep();
		ventana.dispose ();
	}	
	/**
	 * Muestra un diálogo de selección de archivo, para seleccionar uno con las
	 * extensiones especificadas 
	 */
	private void abrir(){
		FileDialog dabrir = new FileDialog(shell, SWT.OPEN);
    	dabrir.setFileName("entradas");
		dabrir.setFilterExtensions(extensiones);
		String sarchivo = dabrir.open();
		if (sarchivo != null){
			archivo = new File (sarchivo);
			leerArchivo();
		}		
	}
	/**
	 * Abre el archivo actual. El contenido lo deja en el editor
	 * Se debe setear el archivo actual, nunca debe ser null y debe existir
	 */
	private void leerArchivo(){
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(archivo));
			text.setText("");
			while (br.ready())
				text.setText(text.getText() + br.readLine() + "\n");
		} catch (Exception e) {
			e.printStackTrace(); 
		}
		shell.setText(titulo + " - " + archivo.getName());
	}
	/**
	 * Guarda el archivo actual. Si no hay archivo actual
	 * pasa a Guardar como
	 */
	private void guardar(){
		if (archivo == null)
			guardarComo();
		else {
			PrintWriter pw;
			FileWriter fw;
			try {
				fw = new FileWriter(archivo);
				pw = new PrintWriter(fw);
				pw.print(text.getText());
				fw.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * Genera archivo para que el texto actual se guarde
	 */
	private void guardarComo(){
		FileDialog dguardar = new FileDialog(shell, SWT.SAVE);
    	dguardar.setFileName("entradas");
		dguardar.setFilterExtensions(extensiones);
		if (archivo != null)
			dguardar.setFileName(archivo.getName());
		String sarchivo = dguardar.open();
		if (sarchivo != null) {
			archivo =  new File(sarchivo);
			guardar();
			shell.setText(titulo + " - " + archivo.getName());
		}
	}
	/**
	 * Construye arreglo de estilos de resaltado
	 * @param lineOffset la posición que ocupa la línea
	 * @return arreglo de estilos
	 */
	private StyleRange [] styles(String txt, int lineOffset){
		ArrayList<StyleRange> styles = new ArrayList<StyleRange>();

		com.resaltado.LexicoLex l = new com.resaltado.LexicoLex(
				new CharArrayReader(txt.toCharArray()), styles, lineOffset, ventana);
		try {
			while(l.yylex() != null){ }
		} catch (IOException e) { }
		
		StyleRange [] ret = styles.size() > 0 ? new StyleRange [styles.size()] : null;
		int i = 0;
		for(StyleRange s : styles){
			ret[i] = s;
			i ++;
		}
		
		return ret;
	}
	/**
	 * Genera String con el codigo java arreglado
	 */
	private void formatear(){
		String err = archivoJavaValido();
		if(err == null){
			Formador form = new Formador();
			form.formato(archivo);
			text.setText(form.getTexto());
		} else {
			MessageBox msj = new MessageBox(shell, SWT.ICON_ERROR);
			msj.setMessage(errorArchivo);
			msj.open();
		}
	}
	/**
	 * Genera string con los getters y los setters del código java 
	 * ingresado en el text
	 */
	private void generar(){
		String err = archivoJavaValido();
		if(err == null){
			guardar();
			Generador gs = new Generador();
			gs.generarSG(archivo);
			int pos = gs.getPosGS();
			String gen = gs.getGS();
			if(pos != -1) {
				if(!gen.equals("}")){
					text.replaceTextRange(pos, 1, " ");
					text.setText(text.getText() + gen);
				}
			}
		} else {
			MessageBox msj = new MessageBox(shell, SWT.ICON_ERROR);
			msj.setMessage(errorArchivo);
			msj.open();
		}
	}
	/**
	 * Ejecuta las acciones correspondientes a los lenguajes
	 * Depende del archivo actualmente abierto
	 */
	private void ejecutar(){
		String msjError = archivoJavaValido();
		MessageBox msj;
		if(msjError == null){ // generar codigo 3D
			Clase clase = new Clase();
			clase.generarSalida(archivo);
			String archivoCreado = clase.nombreArchivo3D();
			if(archivoCreado != null){ // no hay errores, pregunta si desea abrir archivo 3D
				msj = new MessageBox(shell,  SWT.ICON_INFORMATION
			            | SWT.YES | SWT.NO);
				msj.setMessage("Éxito!\nCódigo intermedio generado en '" + archivoCreado + "'\n" +
						"Abrir el archivo?");
				int res = msj.open();
				if(res == SWT.YES){
					archivo = new File(archivoCreado);
					leerArchivo();
				}
				clase.errs(false);
			} else {
				msj = new MessageBox(shell, SWT.ICON_ERROR | 
						SWT.YES | SWT.NO);
				msj.setMessage("Existen errores en el código fuente\nMostrar los errores?");
				int res = msj.open();
				clase.errs(res == SWT.YES);
			}
		} else {
			msjError = archivo3DValido();
			if(msjError == null){ // generar código assm
				new Optimus(archivo);
				String arch = new Objetador(archivo, archivoh).nombreArchivoObj();
				msj = new MessageBox(shell, SWT.ICON_INFORMATION
						| SWT.YES | SWT.NO);
				msj.setMessage("Código objeto optimizado fue generado en '" + arch + "'\n" +
						"Abrir el archivo?");
				int res = msj.open();
				if(res == SWT.YES){
					archivo = new File(arch);
					leerArchivo();
				}
			} else {
				msjError = archivoASMValido();
				if(msjError == null){ // ejecutar intérprete
					if(!ejecutando){
						ejecutando = true;
						correrInterprete();
					}
				} else {
					msj = new MessageBox(shell, SWT.ICON_ERROR);
					msj.setText(msjError);
					msj.open();
				}
			}
		}
	}
	
	/**
	 * Valida si el archivo actual es archivo java válido
	 * Si el archivo no es válido devuelve el mensaje
	 * @return null o el mensaje si hubo error
	 */
	private String archivoJavaValido(){
		errorArchivo = null;
		if(archivo != null && archivo.exists()){
			String [] n = archivo.getName().split("\\.");
			if(n[n.length - 1].toLowerCase().equals(javaExt))
				return null;
			else
				errorArchivo = "Archivo abierto no es java";
		} else
			errorArchivo = "Por favor, abrir un archivo java válido";
		return errorArchivo;
	}
	/**
	 * Valida si el archivo actual es archivo 3D válido
	 * Si el archivo no es válido devuelve mensaje
	 * @return null o el mensaje si hubo error
	 */
	private String archivo3DValido(){
		errorArchivo = null;
		if(archivo != null && archivo.exists()){
			String [] n = archivo.getName().split("\\.");
			if(n[n.length - 1].toLowerCase().equals(cExt)){
				archivoh = new File(archivo.getAbsolutePath().substring(0, archivo.getAbsolutePath().length() - 1) + "h");
				if(archivoh.exists())
					return null;
				else {
					errorArchivo = "No existe archivo de cabecera de código objeto";
					MessageBox msj = new MessageBox(shell, SWT.ICON_ERROR);
					msj.setMessage(errorArchivo);
					msj.open();
				}
			} else
				errorArchivo = "Archivo abierto no es de código intermedio";
		} else
			errorArchivo = "Por favor, abrir un archivo de código 3D válido";
		return errorArchivo;
	}
	/**
	 * Valida si el archivo actual es archivo pseudo assembler válido
	 * Si el archivo no es válido devuelve mensaje
	 * @return null o el mensaje si hubo error
	 */
	private String archivoASMValido(){
		errorArchivo = null;
		if(archivo != null && archivo.exists()){
			String [] n = archivo.getName().split("\\.");
			if(n[n.length - 1].toLowerCase().equals(asmExt))
				return null;
			else
				errorArchivo = "Archivo abierto no es de código assembler";
		} else
			errorArchivo = "Por favor, abrir un archivo de código assembler válido";
		return errorArchivo;
	}
	/**
	 * Inicia la ejecución del intérprete
	 */
	private void correrInterprete(){
		text.setEditable(false);
		table_variables.removeAll();
		new Interpretador(text, consola, table_variables, items_PilaDeEjecucion,
				items_LineasDeDepuracion, items_Stack, items_Heap);
		text.setEditable(true);
	}
}
