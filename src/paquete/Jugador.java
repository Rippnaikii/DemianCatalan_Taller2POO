package paquete;

import java.util.ArrayList;

public class Jugador extends Entrenador {
	
	private int medallas;
	private ArrayList<Pokemon> pc;
	

	public Jugador(String nombre, int medallas) {
		super(nombre);
		this.medallas = medallas;
		this.pc = new ArrayList<>();
		
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

	
	@Override
	public void agregarPokemon(Pokemon pokemon) {
		ArrayList<Pokemon> equipo = this.getEquipo();
		if (equipo.size() < 6) {
			super.agregarPokemon(pokemon);;
		} else {
			pc.add(pokemon);
		}
	}

	public int getMedallas() {
		return medallas;
	}


	public void setMedallas(int medallas) {
		this.medallas = medallas;
	}
	
	
}
