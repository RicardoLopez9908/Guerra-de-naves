package juego;

import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;

import entorno.Entorno;

//Triangulo(double x , double y, int altura, int base, double angulo, color color)

public class Nave extends Rectangulo{

	protected double puntoX;
	protected double puntoY;
	protected int altura;
	protected int ancho;
	protected double angulo;
	private Color color;
	
	//Crear una nave con las caracteristicas que yo quiera:
	public Nave(double puntoX, double puntoY, int altura, int ancho,double angulo) {
			this.puntoX = puntoX;
			this.puntoY = puntoY;
			this.altura = altura;
			this.ancho = ancho;
			this.angulo = angulo;
		}
		
	//Crear una nave principal para jugar por defecto:
	public Nave(double puntoX, double puntoY, Color color) {
		this.puntoX=puntoX;
		this.altura=40;
		this.puntoY=puntoY - this.altura/2;
		this.ancho=80;
		this.angulo=0;		
		this.color=color;
	}
	
	
	public double getPuntoX() {
		return puntoX;
	}


	public double getPuntoY() {
		return puntoY;
	}

	public int getAltura() {
		return altura;
	}

	public int getAncho() {
		return ancho;
	}

	
	public void dibujar(Entorno entorno,Image imagen){
		entorno.dibujarImagen(imagen, puntoX, puntoY, 0);
	}	

	
	public void moverArriba(ArrayList<Obstaculo> obstaculos, Nave nave) {
		ArrayList<Nave> naves= new ArrayList<>();
		naves.add(nave);
		if(chocaArriba(this,obstaculos) || chocaArriba(this,naves) ) {
		}
		else if(puntoY - altura/2> 0) {
			this.puntoY=puntoY-2;}
			
	}
	public void moverAbajo(ArrayList<Obstaculo> obstaculos,Nave nave) {
		ArrayList<Nave> naves= new ArrayList<>();
		naves.add(nave);
		if(chocaAbajo(this,obstaculos)|| chocaAbajo(this,naves) ) {
		}
		else if(puntoY + altura/2 <593) {
			this.puntoY=puntoY+2;}
			
	}
	
	public void moverDerecha(ArrayList<Obstaculo> obstaculos,Nave nave) {
		ArrayList<Nave> naves= new ArrayList<>();
		naves.add(nave);
		if(chocaDerecha(this,obstaculos)|| chocaDerecha(this,naves) ) {
		}
		else if(puntoX + ancho/2< 800) {
		this.puntoX=puntoX+2;}
	
		}
	
	public void moverIzquierda(ArrayList<Obstaculo> obstaculos,Nave nave) {
		ArrayList<Nave> naves= new ArrayList<>();
		naves.add(nave);
		if(chocaIzquierda(this,obstaculos)|| chocaIzquierda(this,naves) ) {
		}
		else if(puntoX - ancho/2> 0) {
			this.puntoX=puntoX-2;
			}
	}
	
	private boolean chocaArriba(Rectangulo rec1, ArrayList<? extends Rectangulo> rec2) {
		boolean ultimoChequeo=false;
		
		for(int i =0 ; i<rec2.size(); i++) {
		
		if(rec1!=null && rec2.get(i)!=null) {
		boolean chequeoY = (rec1.getPuntoY() - rec1.getAltura()/2 < rec2.get(i).getPuntoY() + rec2.get(i).getAltura()/2)
				&&         (rec1.getPuntoY() + rec1.getAltura()/2 > rec2.get(i).getPuntoY() + rec2.get(i).getAltura()/2); 
		
		boolean chequeoX = ((rec1.getPuntoX() - rec1.getAncho()/2)+1 < rec2.get(i).getPuntoX() + rec2.get(i).getAncho()/2) 
				&& 	   ((rec1.getPuntoX() + rec1.getAncho()/2)-1 > rec2.get(i).getPuntoX() - rec2.get(i).getAncho()/2) ; 
		
		ultimoChequeo= (chequeoY && chequeoX) || ultimoChequeo;}
		}
		return ultimoChequeo;
		
	}
	
private boolean chocaAbajo(Rectangulo rec1, ArrayList<? extends Rectangulo>rec2) {
	boolean ultimoChequeo=false;
	
	for(int i =0 ; i<rec2.size(); i++) {
	
	if(rec1!=null && rec2.get(i)!=null) {
	boolean chequeoY = (rec1.getPuntoY() + rec1.getAltura()/2 > rec2.get(i).getPuntoY() - rec2.get(i).getAltura()/2)
			&&         (rec1.getPuntoY() - rec1.getAltura()/2 < rec2.get(i).getPuntoY() - rec2.get(i).getAltura()/2);
	
	boolean chequeoX = ((rec1.getPuntoX() - rec1.getAncho()/2)+1 < rec2.get(i).getPuntoX() + rec2.get(i).getAncho()/2) 
			&& 	   ((rec1.getPuntoX() + rec1.getAncho()/2)-1 > rec2.get(i).getPuntoX() - rec2.get(i).getAncho()/2) ; 
	
	ultimoChequeo= (chequeoY && chequeoX) || ultimoChequeo;}
	}
	return ultimoChequeo;
}

private boolean chocaIzquierda(Rectangulo rec1, ArrayList<? extends Rectangulo>rec2) {
	boolean ultimoChequeo=false;
	
	for(int i =0 ; i<rec2.size(); i++) {
	
	if(rec1!=null && rec2.get(i)!=null) {
		boolean chequeoY = ((rec1.getPuntoY() - rec1.getAltura()/2)+1 < rec2.get(i).getPuntoY() + rec2.get(i).getAltura()/2) 
				&& 	   	((rec1.getPuntoY() + rec1.getAltura()/2)-1 > rec2.get(i).getPuntoY() - rec2.get(i).getAltura()/2) ; 
	
		boolean chequeoX = (rec1.getPuntoX() - rec1.getAncho()/2 < rec2.get(i).getPuntoX() + rec2.get(i).getAncho()/2)
				&&		(rec1.getPuntoX() + rec1.getAncho()/2 > rec2.get(i).getPuntoX() + rec2.get(i).getAncho()/2); 

	ultimoChequeo= (chequeoY && chequeoX) || ultimoChequeo;}
	}
	return ultimoChequeo;
}

private boolean chocaDerecha(Rectangulo rec1, ArrayList<? extends Rectangulo>rec2) {	
	boolean ultimoChequeo=false;
	
	for(int i =0 ; i<rec2.size(); i++) {
	
	
	if(rec1!=null && rec2.get(i)!=null) {
		boolean chequeoY = ((rec1.getPuntoY() - rec1.getAltura()/2)+1 < rec2.get(i).getPuntoY() + rec2.get(i).getAltura()/2) 
				&& 	   	((rec1.getPuntoY() + rec1.getAltura()/2)-1 > rec2.get(i).getPuntoY() - rec2.get(i).getAltura()/2) ; 
	
		boolean chequeoX = (rec1.getPuntoX() + rec1.getAncho()/2 > rec2.get(i).getPuntoX() - rec2.get(i).getAncho()/2)
				&&		(rec1.getPuntoX() - rec1.getAncho()/2 < rec2.get(i).getPuntoX() - rec2.get(i).getAncho()/2); 

	ultimoChequeo= (chequeoY && chequeoX) || ultimoChequeo;}
	
	}
	return ultimoChequeo;
	}



	public Disparo disparar(Entorno entorno) {
		Disparo nuevoDisparo= new Disparo(puntoX, puntoY-altura/2,color);
		nuevoDisparo.dibujar(entorno);
		return nuevoDisparo;
	}
	

}
