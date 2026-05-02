package paquete;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class App {

	public static void main(String[] args) {
		ArrayList<Pokemon> pokedex = new ArrayList<>();
		ArrayList<Habitat> habitats = new ArrayList<>();
		
		//leemos e instanciamos los habitats
			try {
				File archivo = new File("Pokedex.txt");
				Scanner lector = new Scanner(archivo);
				
				while (lector.hasNextLine()) {
					String linea = lector.nextLine().strip();
					Habitat habitat = new Habitat(linea);
					habitats.add(habitat);
				}
				
				lector.close();
			}catch ( FileNotFoundException e) {
				System.out.println("No se encontraron los datos de los Hábitats");
			}
			
			
		//leemos e instanciamos los pokemon
		try {
			File archivo = new File("Pokedex.txt");
			Scanner lector = new Scanner(archivo);
			
			while (lector.hasNextLine()) {
				String[] linea = lector.nextLine().strip().split(";");
				
				String nombre = linea[0];
				String habitat = linea[1];
				Double porcentajeAparicion = Double.parseDouble(linea[2]);
				String tipo = linea[9];
				
				int vida = Integer.parseInt(linea[3]);
				int ataque = Integer.parseInt(linea[4]);
				int defensa = Integer.parseInt(linea[5]);
				int ataqueEsp = Integer.parseInt(linea[6]);
				int defensaEsp = Integer.parseInt(linea[7]);
				int velocidad = Integer.parseInt(linea[8]);
				
				Pokemon pokemon = new Pokemon(nombre, habitat, porcentajeAparicion, vida, ataque, defensa, ataqueEsp, defensaEsp, velocidad, tipo);
				pokedex.add(pokemon);
				
				//agregamos el pokemon a su habitat
				for (int i = 0; i< habitats.size() ;i++) {
					if (habitat.equals(habitats.get(i).getNombre())) {
						habitats.get(i).agregarSalvaje(pokemon);
						break;
					}
				}
			}
			
			lector.close();
		}catch ( FileNotFoundException e) {
			System.out.println("No se encontraron los datos de la Pokédex");
		}
		System.out.println("se introdujeron: " + pokedex.size() + " pokémon en la pokédex.");
		
		
		
		//pendiente el menu
		
		
		
	}

	//esto lo dejo listo para luego añadir a los pokemon a los equipos
	public static int buscarPokemon(String nombre, ArrayList<Pokemon> lista) { 
		for ( int i = 0; i< lista.size(); i++) {
			if ( nombre.equals(lista.get(i).getNombre())) {
				return i;
			}
		}
		return -1;
	}
}

