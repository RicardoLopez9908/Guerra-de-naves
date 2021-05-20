package juego;

import java.awt.Image;

import entorno.Entorno;

public class Obstaculo extends Rectangulo{

	private double puntoY;
	private double puntoX;
	private int altura;
	private int ancho;
	
	public Obstaculo(double puntoX, double puntoY) {
		this.puntoY=puntoY;
		this.puntoX=puntoX;
		this.altura=50;
		this.ancho=50;
	}
	
	public void dibujar(Entorno entorno, Image imagen) {
		entorno.dibujarImagen(imagen, puntoX, puntoY, 0);
	}
	
	
	@Override
	public double getPuntoY() {
		return puntoY;
	}

	@Override
	public double getPuntoX() {
		return puntoX;	
	}

	@Override
	public int getAltura() {
		return altura;
	}

	@Override
	public int getAncho() {
		return ancho;
	}
	
	

}
