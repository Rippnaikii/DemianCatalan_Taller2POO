package paquete;

public class Gimnasio {
	private int numGym;
	private Entrenador lider;
	private String estado;
	private boolean derrotado;
	
	public Gimnasio(int numGym, Entrenador lider, String estado) {
		this.numGym = numGym;
		this.lider = lider;
		this.estado = estado;
		this.derrotado = false;
		
	}

	public int getNumGym() {
		return numGym;
	}
	public Entrenador getLider() {
		return lider;
	}

	public boolean isDerrotado() {
		return derrotado;
	}

	public void setDerrotado(boolean derrotado) {
		this.derrotado = derrotado;
	}

	@Override
	public String toString() {
		return  numGym + ") " + lider + " " + estado  ;
	}

	
	
}
