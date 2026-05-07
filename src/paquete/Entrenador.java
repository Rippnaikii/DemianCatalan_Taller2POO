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

	@Override
	public String toString() {
		return  nombre ;
	}

	public String getNombre() {
		return nombre;
	}

	public ArrayList<Pokemon> getEquipo() {
		return equipo;
	}
	
	public void curarEquipo() {
		for (int i = 0; i< equipo.size(); i++) {
			equipo.get(i).setEstado(true);
		}
	}
	
	public boolean verificarDerrota() {
		for (int i = 0; i < equipo.size(); i++) {
			if (equipo.get(i).getEstado() == true) {
				return false;
			}
		}
		return true;
	}
	
	public Pokemon sacarPokemon() {
		for (int i = 0; i < equipo.size(); i++) {
			if (equipo.get(i).getEstado() == true) {
				System.out.println("\n¡" + this.getNombre() + " saca a " + equipo.get(i).getNombre() + "!\n");
				return equipo.get(i);
			}
		}
		return null;
	}
	
}
