package juego;

import java.awt.Color;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;

import entorno.Entorno;
import entorno.Herramientas;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego{
	
	// El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;
	
	
	//PROPIEDADES DE CADA JUGADOR:
	private Nave nave1;
	private Nave nave2;
	private int puntosPlayer1;
	private int puntosPlayer2;
	private ArrayList<Disparo> disparosPlayer1 = new ArrayList<>();
	private ArrayList<Disparo> disparosPlayer2 = new ArrayList<>();
	private int minimaCantidadDisparos=1;
	
	
	//INCREMENTO POR NIVEL:
	private ArrayList<Obstaculo> obstaculos  = new ArrayList<>();
	private ArrayList<NaveEnemiga> navesEnemigas = new ArrayList<>();
		
	//IMAGENES:
	private Image imagenPlayer1 = new ImageIcon("recursos/player1.jpg").getImage();
	private Image imagenPlayer2 = new ImageIcon("recursos/player2.jpg").getImage();
	private Image imagenNaveEnemiga = new ImageIcon("recursos/naveEnemiga.jpg").getImage();
	private Image imagenObstaculo = new ImageIcon("recursos/obstaculo.jpg").getImage();
	private Image imagenDeFondo = new ImageIcon("recursos/fondo.jpg").getImage();
	private Image imagenDePantalla = new ImageIcon("recursos/pantalla.jpg").getImage();
	
	
	//SONIDOS:
	private Clip sonidoDisparo = Herramientas.cargarSonido("disparo.wav");
	private Clip sonidoDeFondo = Herramientas.cargarSonido("sonidoDeFondo.wav");
	private Clip sonidoGameOver = Herramientas.cargarSonido("sonidoGameOver.wav");
	
	
	//INFORMACION PARA EL FUNCIONAMIENTO DEL JUEGO:
	private boolean multijugador;	
	private boolean pantallaDeInicio=true; 
	private boolean gameOver=false;
	private int nivel=0;
	private String resultado;
	private int contadorDisparoPlayer1;
	private int contadorDisparoPlayer2;
	private Dificultad dificultad=Dificultad.MEDIO;
	private int disparosDisponiblesPlayer1;
	private int disparosDisponiblesPlayer2;
	
	
	
	
	Juego()
	{
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Guerra de naves", 800, 600);
		
		// Inicializar lo que haga falta para el juego
		// ...
		
		//Nave enemiga:
		navesEnemigas.add(new NaveEnemiga(400,40));
		
		// Inicia el juego!
		this.entorno.iniciar();
		
		
	}

	/**
	 * Durante el juego, el método tick() será ejecutado en cada instante y 
	 * por lo tanto es el método más importante de esta clase. Aquí se debe 
	 * actualizar el estado interno del juego para simular el paso del tiempo 
	 * (ver el enunciado del TP para mayor detalle).
	 */
	public void tick(){

	
		
		//MOSTRAMOS LA PANTALLA DE INICIO

		if(pantallaDeInicio==true) {
			elegirCantidadDeJugadores(entorno); //Pedimos al usuario que indique la cantidad de jugadores
		}
		else {
		
			
		//EN CASO DE QUE HAYA TERMINADO EL JUEGO:	
		if(gameOver== true) {
			imprimirResultado(entorno,resultado,puntosPlayer1,puntosPlayer2);	//imprimimos el resultado en la pantalla final
		}
		else {
			
		entorno.dibujarImagen(imagenDeFondo, 405, 300, 0);	
		
		//SUMAMOS DIFICULTAD POR CADA NIVEL:	
				
		for(int i=1; i<= nivel;i++) {
			if(this.nivel>obstaculos.size()) {
				obstaculos.add(crearNuevoObstaculo());
			}
			obstaculos.get(i-1).dibujar(entorno,imagenObstaculo); 
			}
		
		//CREAMOS NAVES ENEMIGAS SI ES NECESARIO:
		
		for(int i=0; i<= nivel;i++) {

			if(this.nivel+1>navesEnemigas.size()) {
					navesEnemigas.add(crearNuevaNaveEnemiga());
			}
			if(navesEnemigas.get(i)!=null) {  			
				
				navesEnemigas.set(i,revisarMovimientosNaveEnemiga(navesEnemigas.get(i))); //Revisamos si la nave enemiga puede avanzar 
			}
			else navesEnemigas.set(i,crearNuevaNaveEnemiga()); //creamos nueva nave enemiga en caso de que haya desaparecido
			
			if(revisarSiDisparanANaveEnemiga(navesEnemigas.get(i))){
				navesEnemigas.set(i,crearNuevaNaveEnemiga());
			}
			
		}	
		
		
					
		//MOVIMIENTOS DEL DISPARO1:
		
		for(int i=0; i<disparosPlayer1.size();i++) {
		
			if(disparosPlayer1.get(i)!= null) {				//Cuando exista un disparo
				disparosPlayer1.get(i).dibujar(entorno);	//Dibujar el disparo
				if(disparosPlayer1.get(i).avanzar(obstaculos)==false) {		//Avanzar el disparo
					disparosPlayer1.remove(i);}					
			}
			else	disparosPlayer1.remove(i);		 
		}
		
		
	
		
		//MOVIMIENTOS DEL DISPARO2: (solo si el juego es multijugador)
		
		if(multijugador) {
		
			
		//MOVIMIENTOS DEL DISPARO2:
		
		for(int i=0; i<disparosPlayer2.size();i++) {
		
			if(disparosPlayer2.get(i)!= null) {				//Cuando exista un disparo
				disparosPlayer2.get(i).dibujar(entorno);	//Dibujar el disparo
				if(disparosPlayer2.get(i).avanzar(obstaculos)==false) {		//Avanzar el disparo
					disparosPlayer2.remove(i);}					
			}
			else	disparosPlayer2.remove(i);		 
		}
		
		}
		
		nave1.dibujar(this.entorno,this.imagenPlayer1);		//dibujar la nave principal
		revisarMovimientosNave1();  	//revisar si tocaron teclas
		
		
		if(this.multijugador==true) {
		nave2.dibujar(entorno,this.imagenPlayer2);		//dibujar la nave secundaria (solo si el juego es multijugador)	
		revisarMovimientosNave2();				//revisar si tocaron teclas (solo si el juego es multijugador	
		}
		
		//IMPRIMIMOS LA PUNTUACION DE LOS JUGADORES
		this.nivel= (puntosPlayer1+puntosPlayer2)/(gestionarDificultad(this.dificultad));
		imprimirPuntuacion();
		
		
		if(disparosPlayer1.size()==0) {
			contadorDisparoPlayer1=0;
		}
		else contadorDisparoPlayer1--;
	
		if(disparosPlayer2.size()==0) {
			contadorDisparoPlayer2=0;
		}
		else contadorDisparoPlayer2--;
	
		}
		}
	}
	
	
	//-------------------------------------METODOS ESTATICOS---------------------------------
	
	public int gestionarDificultad(Dificultad d) {
		if(d== Dificultad.FACIL) {
			return 10;
		}
		else if(d== Dificultad.MEDIO) {
			return 5;
		}
		else return 1;
	}	
	
	
	public void reiniciar() {
		sonidoGameOver.stop();
		sonidoGameOver.flush();
		sonidoDeFondo.setMicrosecondPosition(0);
		sonidoDeFondo.loop(Clip.LOOP_CONTINUOUSLY);
		sonidoGameOver.setMicrosecondPosition(0);
		
		
		this.pantallaDeInicio=true;
		this.gameOver=false;
		this.nave1=null;
		this.nave2=null;
		this.nivel=0;
		this.puntosPlayer1=0;
		this.puntosPlayer2=0;
		
		this.disparosPlayer1.clear();
		disparosPlayer1 = new ArrayList<>();
		
		this.disparosPlayer2.clear();
		disparosPlayer2 = new ArrayList<>();
		
		this.obstaculos.clear();
		obstaculos= new ArrayList<>();
		
		this.navesEnemigas.clear();
		navesEnemigas= new ArrayList<>();
		
	}
	
	
	public void elegirCantidadDeJugadores(Entorno entorno) {
		sonidoDeFondo.start();
		sonidoDeFondo.loop(Clip.LOOP_CONTINUOUSLY);
		
		
		entorno.dibujarImagen(imagenDePantalla, 405, 300, 0);	
		
		entorno.cambiarFont("Times New Roman", 33, Color.BLACK);
		entorno.escribirTexto("Para un jugador, presione la flecha hacia arriba.", 100, 305);
		entorno.escribirTexto("Para dos jugadores, presione la flecha hacia abajo.", 90, 365);
		if(entorno.estaPresionada(entorno.TECLA_ARRIBA)) {
			//creamos nuestra nave :
			this.nave1 = new Nave(400,593,Color.ORANGE);
			this.disparosDisponiblesPlayer1=minimaCantidadDisparos;
			this.multijugador=false;
			this.pantallaDeInicio=false;
			}
		if(entorno.estaPresionada(entorno.TECLA_ABAJO)) {
			//creamos nuestra nave :
			this.nave1 = new Nave(450,593,Color.ORANGE);
			this.disparosDisponiblesPlayer1=minimaCantidadDisparos;
			
			//creamos nuestra nave2:
			this.nave2= new Nave (350,593,Color.GREEN);
			this.disparosDisponiblesPlayer2=minimaCantidadDisparos;
			
			this.multijugador=true;
			this.pantallaDeInicio=false;}
		}
	
	
	
	public void imprimirPuntuacion() {
		
		entorno.cambiarFont("Arial", 23, Color.WHITE);
		entorno.escribirTexto("Player 1: "+ puntosPlayer1 + " puntos", 600, 20);
		entorno.escribirTexto("Nivel: "+ this.nivel, 363, 20);
		if(this.multijugador==true) {
		entorno.escribirTexto("Player 2: "+ puntosPlayer2 + " puntos", 5, 20);  //(solo si el juego es multijugador)	
		}
		
	}
		
	public Obstaculo crearNuevoObstaculo() {
		double randomX = Math.floor(Math.random()*(780-30+1)+30);  // Valor random entre 30 y 780, ambos incluidos
		double randomY = Math.floor(Math.random()*(560-340+1)+340);  // Valor random entre 30 y 780, ambos incluidos
		Obstaculo nuevo = new Obstaculo(randomX,randomY);
		while((chocan(nuevo,nave1)) || (chocan(nuevo,nave2))) {
			return crearNuevoObstaculo();
		}
		return nuevo;
	}
	
	public void guardarNuevoRecord(int informacion) {
		try {
			File records= new File("recursos/records.txt");
			records.delete();
			FileWriter escribir = new FileWriter(records,true);
			escribir.write(informacion);
			escribir.close();
			}
			catch(Exception e) {
				System.out.println("ERROR AL ESCRIBIR EL ARCHIVO!");
			}
	}
	
	
	public void obtenerMayorRecord(int posibleRecord) {
		int baseDeDatos=0;
		
		try
		{
		//Creamos un archivo FileReader que obtiene lo que tenga el archivo
		FileReader lector=new FileReader("recursos/records.txt");

		//El contenido de lector se guarda en un BufferedReader
		BufferedReader contenido=new BufferedReader(lector);

		//Con el siguiente ciclo extraemos todo el contenido del objeto "contenido" y lo mostramos
		baseDeDatos=contenido.read();
		}
		catch(Exception e) {
			System.out.println("ERROR AL LEER EL ARCHIVO!");
		}
		if(posibleRecord>baseDeDatos) {
			guardarNuevoRecord(posibleRecord);
		}
	}
	
	
	public int obtenerRecordActual() {
		int baseDeDatos=0;
		try
		{
		//Creamos un archivo FileReader que obtiene lo que tenga el archivo
		FileReader lector=new FileReader("recursos/records.txt");

		//El contenido de lector se guarda en un BufferedReader
		BufferedReader contenido=new BufferedReader(lector);

		//Con el siguiente ciclo extraemos todo el contenido del objeto "contenido" y lo mostramos
		baseDeDatos=contenido.read();
		}
		catch(Exception e) {
			System.out.println("ERROR AL LEER EL ARCHIVO!");
		}		
		return baseDeDatos;
		
	}
	
	//este metodo imprime el resultado en la pantalla final
	public void imprimirResultado(Entorno entorno,String resultado, int puntosPlayer1, int puntosPlayer2) {
		sonidoDeFondo.stop();
		sonidoDeFondo.flush();
		sonidoGameOver.start();
		
		entorno.dibujarImagen(imagenDePantalla, 405, 300, 0);	
		
		entorno.cambiarFont("Times New Roman", 60, Color.BLACK);
		entorno.escribirTexto(resultado, 300, 200);
		entorno.cambiarFont("Arial", 30, Color.ORANGE);
		entorno.escribirTexto("Player 1: "+ puntosPlayer1 + " puntos", 300, 310);
		entorno.cambiarFont("Arial", 30, Color.BLACK);
		entorno.escribirTexto("Record actual: "+ obtenerRecordActual() + " puntos.", 260, 450);
		if(this.multijugador==true) {
			entorno.cambiarFont("Arial", 30, Color.GREEN);
			entorno.escribirTexto("Player 2: "+ puntosPlayer2 + " puntos", 300, 350); //(solo si el juego es multijugador)	
		}
		entorno.cambiarFont("Times New Roman", 15, Color.WHITE);
		entorno.escribirTexto("PRESIONE ESPACIO PARA JUGAR OTRA VEZ", 260, 590);		
		entorno.cambiarFont("Times New Roman", 17, Color.WHITE);
		entorno.escribirTexto("Q = SALIR", 730, 588);
		
		if(entorno.estaPresionada(entorno.TECLA_ESPACIO)) {
			obtenerMayorRecord(puntosPlayer1>puntosPlayer2? puntosPlayer1 : puntosPlayer2);
			reiniciar();}
		if(entorno.estaPresionada('Q')) {
			obtenerMayorRecord(puntosPlayer1>puntosPlayer2? puntosPlayer1 : puntosPlayer2);
			entorno.dispose();
		}
		
	}
	
	
	//Este metodo revisa si hay un choque entre dos objetos del tipo rectangulo:
	public boolean chocan(Rectangulo rec1, Rectangulo rec2) {
		
		if(rec1!=null && rec2!=null) {
		boolean chequeoY = (rec1.getPuntoY() - rec1.getAltura()/2 < rec2.getPuntoY() + rec2.getAltura()/2) 
					&& 	   (rec1.getPuntoY() + rec1.getAltura()/2 > rec2.getPuntoY() - rec2.getAltura()/2) ; 
		
		boolean chequeoX = (rec1.getPuntoX() - rec1.getAncho()/2 < rec2.getPuntoX() + rec2.getAncho()/2) 
				&& 	   (rec1.getPuntoX() + rec1.getAncho()/2 > rec2.getPuntoX() - rec2.getAncho()/2) ; 
		
		return chequeoY && chequeoX;}
		
		else return false;
	}
	
	//Este metodo revisa si alguna tecla del player1 esta presionada y efectua lo solicitado
	public void revisarMovimientosNave1() {
		
		if(entorno.estaPresionada(entorno.TECLA_DERECHA)) { 
			nave1.moverDerecha(obstaculos,nave2);}
		
		if(entorno.estaPresionada(entorno.TECLA_IZQUIERDA)) {
			nave1.moverIzquierda(obstaculos,nave2);}
		
		if(entorno.estaPresionada(entorno.TECLA_ESPACIO)) {
			if((contadorDisparoPlayer1<=0)&&(disparosPlayer1.size()<disparosDisponiblesPlayer1)) {
				disparosPlayer1.add(nave1.disparar(entorno));
				contadorDisparoPlayer1=10;
					sonidoDisparo.stop();
					sonidoDisparo.flush();
					sonidoDisparo.setMicrosecondPosition(0);
					sonidoDisparo.start();
				}
			}
			
		if(entorno.estaPresionada(entorno.TECLA_ARRIBA)) {
			nave1.moverArriba(this.obstaculos,nave2);
			}
		if(entorno.estaPresionada(entorno.TECLA_ABAJO)) {
			nave1.moverAbajo(obstaculos,nave2);
			}		
		}
	
	//Este metodo revisa si alguna tecla del player2 esta presionada y efectua lo solicitado
	public void revisarMovimientosNave2() {
		if(entorno.estaPresionada('d')) { 
			nave2.moverDerecha(obstaculos,nave1);}
		
		if(entorno.estaPresionada('a')) {
			nave2.moverIzquierda(obstaculos,nave1);}
		
		if(entorno.estaPresionada('g')) {
			if((contadorDisparoPlayer2<=0)&&(disparosPlayer2.size()<disparosDisponiblesPlayer2)) {
				disparosPlayer2.add(nave2.disparar(entorno));
				sonidoDisparo.stop();
				sonidoDisparo.flush();
				sonidoDisparo.setMicrosecondPosition(0);
				sonidoDisparo.start();
				}
			}
			
		if(entorno.estaPresionada('w')) {
			nave2.moverArriba(obstaculos,nave1);
			}
		if(entorno.estaPresionada('s')) {
			nave2.moverAbajo(obstaculos,nave1);
			}
		}

	/**
	 * Este metodo revisa si la naveEnemiga que le pasemos como parametro choca con las dos naves principales del juego.
	 * 
	 * @param naveEnemiga Es la nave enemiga que queremos revisar
	 * @return retorana el nuevo valor de la nave enemiga luego de avanzar(o null en caso de que corresponda).
	 */
	public NaveEnemiga revisarMovimientosNaveEnemiga(NaveEnemiga naveEnemiga) {
		naveEnemiga.dibujar(entorno,this.imagenNaveEnemiga);
		
		if(naveEnemiga.avanzar() == false) {		//Metodo "avanzar" devuelve false si sobrepasa los bordes
			return null;							//Si la nave enemiga sobrepasa el borde queda en null la referencia
		}
		else if((chocan(naveEnemiga,nave1)) || (chocan(naveEnemiga,nave2))) {	//Revisar si choca la nave enemiga con la nave principal
			 resultado="¡Perdiste!";			
			 gameOver=true;
			 return null;
			}
		else return naveEnemiga;
	}
	
	
	/**
	 * Este metodo revisa si le disparan a la nave enemiga y devuelve el valor de la nave enemiga 
	 * @param naveEnemiga la nave que queremos verificar
	 * @param disparo el disparo que queremos verificar
	 * @return	retorna el nuevo valor de la nave enemiga
	 */
	public boolean revisarSiDisparanANaveEnemiga(NaveEnemiga naveEnemiga) {
		for(int i=0; i<disparosPlayer1.size();i++) {	
			if(chocan(naveEnemiga,disparosPlayer1.get(i))) {					//Revisar si la nave enemiga choca con el disparo
				this.puntosPlayer1++;
				disparosPlayer1.remove(i);
				return true;								//Eliminar la nave enemiga
			}}
		
		for(int i=0; i<disparosPlayer2.size();i++) {	
			if(chocan(naveEnemiga,disparosPlayer2.get(i))) {					//Revisar si la nave enemiga choca con el disparo
				this.puntosPlayer1++;
				disparosPlayer2.remove(i);
				return true;								//Eliminar la nave enemiga
			}}
		return false;
	}
	
	/**
	 * Este metodo crea una nueva nave enemiga con un valor en X: aleatorio (Entre los bordes) e Y: 20 
	 * @return
	 */
	
	public NaveEnemiga crearNuevaNaveEnemiga() {
		double random = Math.floor(Math.random()*(780-30+1)+30);  // Valor random entre 20 y 780, ambos incluidos
		return new NaveEnemiga(random,40);	
	}
	
	
	

	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Juego juego = new Juego();
	}
}
