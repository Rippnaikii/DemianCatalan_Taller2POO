package paquete;

import java.util.ArrayList;

public class Habitat {

	private String nombre;
	private ArrayList<Pokemon> pokemonSalvajes;
	
	public Habitat(String nombre) {
		this.nombre = nombre;
		pokemonSalvajes = new ArrayList<>();
	}
	
	public void agregarSalvaje(Pokemon pokemon) {
		pokemonSalvajes.add(pokemon);
	}

	public String getNombre() {
		return nombre;
	}
	
}
