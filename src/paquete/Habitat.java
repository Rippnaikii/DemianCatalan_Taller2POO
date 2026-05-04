package paquete;

import java.util.ArrayList;
import java.util.Random;

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

	public Pokemon explorar() {
		Random aleatorio = new Random();
		
		double numero = aleatorio.nextDouble();
		double suma = 0.0;
		
		for (int i = 0; i < pokemonSalvajes.size(); i++) {
			suma += pokemonSalvajes.get(i).getPorcentajeAparicion();
			if ( suma >= numero) {
				return pokemonSalvajes.get(i);
			}
				
		}
		return null;
		
	}
}
