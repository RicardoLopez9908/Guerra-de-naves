package juego;

public class NaveEnemiga extends Nave {
	

	public NaveEnemiga(double puntoX, double puntoY) {
		super(puntoX, puntoY, 40, 80, 0);	
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

	public boolean avanzar() {
		if(puntoY-altura/2>600) {
			return false;
		}
		else {
		this.puntoY=puntoY+2;
		return true;
		}
	}

	
	
}
