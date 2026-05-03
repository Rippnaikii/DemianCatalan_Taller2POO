package paquete;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;



public class App {
	public static ArrayList<Pokemon> pokedex = new ArrayList<>();
	public static ArrayList<Habitat> habitats = new ArrayList<>();
	
	public static void main(String[] args) throws FileNotFoundException {
		leerHabitats();
		
		leerPokemones();
		
		System.out.println("Habitats cargados: "+ habitats.size()); // solo para probar si printea bien...
		
		//pendiente el menu
		boolean aux = true;
		do {
			System.out.println("1) Continuar. \n2) Nueva Partida. \n3) Salir. ");
			Scanner entrada = new Scanner(System.in); 
			System.out.println(" ");
			System.out.print("Ingrese Opcion: ");
			int opcion = Integer.parseInt(entrada.nextLine());
			switch (opcion) {
			case 1: 
				//Opcion continuar
				Jugador jugadorRegistros = leerRegistros(); // esto da un error de momento ya que no hay nada en el txt
				//menuJuego(entrada,jugadorRegistros);
				boolean aux2 = true;
				do {
					System.out.println(" ");
					System.out.println("\n¿Qué deseas hacer?\n1) Revisar equipo\n2) Salir a capturar\n3) Acceso al pc (cambiar pokemon del equipo) \n4) Retar un Gimnasio  \n5) Desafio de alto mando \n6) Curar Pokemon \n7) Guardar \n8) Guardar y salir "); 
					System.out.print("Ingrese Opcion: ");
					int opcion2 = Integer.parseInt(entrada.nextLine()); 
					switch (opcion2) {
					case 1:
						
						break;
					case 2:
						
						break;
					case 3:
						
						break;
					case 4:
						
						break;
					case 5:
	
						break;
					case 6:
						
						break;
					case 7:
						
						break;
					case 8:
						aux2 = false;
						break;
					default:
						break;
					}
				} while (aux2);
					
					
				break;
			case 2:
				//Opcion nueva partida
				System.out.print("Ingrese su apodo de jugador: ");
				String apodo = entrada.nextLine();
				// El mismo codigo exacto copiado y pegado de arriba, esto pudo ser llamar a un metodo sin mas...
				
				boolean aux3 = true;
				do {
					System.out.println(" ");
					System.out.println("\n¿Qué deseas hacer?\n1) Revisar equipo\n2) Salir a capturar\n3) Acceso al pc (cambiar pokemon del equipo) \n4) Retar un Gimnasio  \n5) Desafio de alto mando \n6) Curar Pokemon \n7) Guardar \n8) Guardar y salir "); 
					System.out.print("Ingrese Opcion: ");
					int opcion3 = Integer.parseInt(entrada.nextLine()); 
					switch (opcion3) {
					case 1:
						
						break;
					case 2:
						
						break;
					case 3:
						
						break;
					case 4:
						
						break;
					case 5:
	
						break;
					case 6:
						
						break;
					case 7:
						
						break;
					case 8:
						aux3 = false;
						break;
					default:
						break;
					}
				} while (aux3);
					
				break;
			case 3:
				aux = false;
				break;
			default:
				System.out.println("Opcion no valida...");
			}
		} while (aux);
		
		
	}
	public static Jugador leerRegistros() throws FileNotFoundException {
		File f = new File("Registros.txt");
		Scanner sc = new Scanner(f);
		if (!sc.hasNextLine()) {
			System.out.println("No hay ninguna partida guardada.");
			sc.close();
			return null; 
		}
		
		String linea = sc.nextLine();
		String[] partes = linea.split(";");
		String nombreJugador = partes[0];
		int cantMedallas = Integer.parseInt(partes[1]);
		while(sc.hasNextLine()) {
			linea = sc.nextLine();
			partes = linea.split(";");
			String nomPokemon = partes[0];
			String estado = partes[1];
			// Agregar estado a atrubutos de pokemon..
			// falta guardar pokemon actualizado
			// guardarPokemonJugador();
			
		}
		return null;
	}
	public static void leerPokemones() {
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
				
	}
	public static void leerHabitats() {
		//leemos e instanciamos los habitats
		try {
			File archivo = new File("Habitats.txt");
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

