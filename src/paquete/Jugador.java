package paquete;

public class Jugador extends Entrenador {
	private int medallas;
	

	public Jugador(String nombre, int medallas) {
		super(nombre);
		this.medallas = medallas;
	}
	
	public void revisarEquipo() {
		System.out.println("Equipo Actual: ");
		if(super.getEquipo().isEmpty()) {
			System.out.println("Tu equipo esta vacio..");
			return;
		}
		for (int i = 0; i < super.getEquipo().size(); i++) {
			System.out.println((i+1)+") "+ super.getEquipo().get(i).toString());
		}
		
	}
	
}
