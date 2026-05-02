package paquete;

import java.util.ArrayList;

public class Entrenador {

	private String nombre;
	private ArrayList<Pokemon> equipo;
	
	public Entrenador(String nombre) {
		this.nombre = nombre;
		this.equipo = new ArrayList<>();
	}
	
	public void agregarPokemon(Pokemon pokemon) {
		this.equipo.add(pokemon);
	}
}
