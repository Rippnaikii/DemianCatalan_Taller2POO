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
	
	public void intercambiarPokemon(int idx1,int idx2) {
		int tamañoEquipo = super.getEquipo().size();
		int tamañoPc = this.pc.size();
		int tamañoTotal = tamañoEquipo + tamañoPc;
		
		if (idx1 < 0 || idx1 >= tamañoTotal || 
			    idx2 < 0 || idx2 >= tamañoTotal) {
				System.out.println("Indices invalidos..");
				return ; 
			}
		Pokemon pok1 ;
		if (idx1<tamañoEquipo) {//indice pertenece a lista equipo
			pok1 = super.getEquipo().get(idx1);
		}else {//pertenece a la lista pc
			pok1 = this.pc.get(idx1-tamañoEquipo);
		}
		
		Pokemon pok2; 
		if (idx2<tamañoEquipo) {
			pok2 = super.getEquipo().get(idx2);
		}else {
			pok2 = this.pc.get(idx2-tamañoEquipo);
		}
		
		if (idx1 < tamañoEquipo) {
			super.getEquipo().set(idx1, pok2);
		} else {
			this.pc.set(idx1 - tamañoEquipo, pok2);
		}
		if (idx2 < tamañoEquipo) {
			super.getEquipo().set(idx2, pok1);
		} else {
			this.pc.set(idx2 - tamañoEquipo, pok1);
		}
		
	}
	
	public void revisarEquipo() {
		System.out.println("Equipo Actual: ");
		if(super.getEquipo().isEmpty()) {
			System.out.println("Tu equipo esta vacio..");
			return;
		}else {
			for (int i = 0; i < super.getEquipo().size(); i++) {
				System.out.println((i+1)+") "+ super.getEquipo().get(i).toString());
			}
		}
	}
	public void revisarPc() {
		System.out.println("Pokemones capturados: ");
		if(super.getEquipo().isEmpty()) {
			System.out.println("Tu lista de pokemones está vacia..");
			return;
		}else {
			int numPantalla = 1;
			for (int i = 0; i < super.getEquipo().size(); i++) {
				System.out.println(numPantalla+") "+ super.getEquipo().get(i).toString());
				numPantalla++;
			}
			
			if (this.pc.isEmpty()) {
				System.out.println(" ");
				System.out.println("Tu pc está vacio..");
				  
			}
			for (int i = 0; i < this.pc.size();i++) {
				System.out.println(numPantalla+") "+ this.pc.get(i).toString());
				numPantalla++;
			}
		}
		
	}
	public boolean tieneElPokemon(Pokemon buscado) {
		String nomPokemonB = buscado.getNombre();
		for (Pokemon pokemon : this.pc) {
			if (pokemon.getNombre().equalsIgnoreCase(nomPokemonB)) {
				return true;
			}
		}
		for (Pokemon pokemon : super.getEquipo()) {
			if (pokemon.getNombre().equalsIgnoreCase(nomPokemonB)) {
				return true;
			}
		}
		return false;
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


	public ArrayList<Pokemon> getPc() {
		return pc;
	}


	
	
	
}
