package juego;

import java.awt.Color;
import java.util.ArrayList;

import javax.sound.sampled.Clip;

import entorno.Entorno;
import entorno.Herramientas;

public class Disparo extends Rectangulo{
	
	private double puntoX;
	private double puntoY;
	private int altura;
	private int ancho;
	private double angulo;
	private Color color;
	
	public Disparo(double puntoX, double puntoY, Color color) {
		this.puntoX=puntoX;
		this.puntoY=puntoY;
		this.altura= 15;
		this.ancho=15;
		this.color=color;
		}
	
	public void dibujar(Entorno entorno) {
		entorno.dibujarRectangulo(puntoX, puntoY, ancho, altura, angulo, color);
	}
	
	public boolean avanzar(ArrayList<Obstaculo> rec) {
		for(int i=0; i<rec.size() ;i++) {
			if(chocan(this,rec.get(i))){
				return false;
			}}
		if(puntoY+altura/2 < 0) return false;
		this.puntoY=puntoY-5;
		return true;
	}
	
	private boolean chocan(Rectangulo rec1, Rectangulo rec2) {
		
		if(rec1!=null && rec2!=null) {
		boolean chequeoY = (rec1.getPuntoY() - rec1.getAltura()/2 < rec2.getPuntoY() + rec2.getAltura()/2) 
					&& 	   (rec1.getPuntoY() + rec1.getAltura()/2 > rec2.getPuntoY() - rec2.getAltura()/2) ; 
		
		boolean chequeoX = (rec1.getPuntoX() - rec1.getAncho()/2 < rec2.getPuntoX() + rec2.getAncho()/2) 
				&& 	   (rec1.getPuntoX() + rec1.getAncho()/2 > rec2.getPuntoX() - rec2.getAncho()/2) ; 
		
		return chequeoY && chequeoX;}
		
		else return false;
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

	


}
