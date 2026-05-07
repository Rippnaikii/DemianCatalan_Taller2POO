package paquete;

public class Gimnasio {
	private int numGym;
	private Entrenador lider;
	//private String estado;
	private boolean derrotado;
	
	public Gimnasio(int numGym, Entrenador lider, String estado) {
		this.numGym = numGym;
		this.lider = lider;
		if (estado.equalsIgnoreCase("Sin derrotar")) {
			this.derrotado = false;
		} else { 
			this.derrotado = true;
		}
		
		
	}

	public int getNumGym() {
		return numGym;
	}
	public Entrenador getLider() {
		return lider;
	}
	public String getMedalla() {
		return lider.getNombre();
	}

	public boolean isDerrotado() {
		return derrotado;
	}

	public void setDerrotado(boolean derrotado) {
		this.derrotado = derrotado;
	}

	@Override
	public String toString() {
		String estado;
		if (derrotado == true) {
			estado = "Derrotado";
		} else { 
			estado = "Sin derrotar";
			}
		return  numGym + ") " + lider + " " + estado  ;
	}

	
	
}
