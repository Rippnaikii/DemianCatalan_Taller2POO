package paquete;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;



public class App {
	public static ArrayList<Pokemon> pokedex = new ArrayList<>();
	public static ArrayList<Habitat> habitats = new ArrayList<>();
	
	public static void main(String[] args) throws FileNotFoundException {
		leerHabitats();
		
		leerPokedex();
		
		System.out.println("Habitats cargados: "+ habitats.size()); // solo para probar si printea bien...
		Scanner entrada = new Scanner(System.in); 
		
		boolean aux = true;
		do {
			System.out.println("1) Continuar. \n"
					+ "2) Nueva Partida. \n"
					+ "3) Salir. \n ");
			
			System.out.print("Ingrese Opcion: ");
			
			int opcion = Integer.parseInt(entrada.nextLine());
			switch (opcion) {
			
			case 1: 
				//Opcion continuar
				Jugador jugadorRegistros = leerRegistros(); 
				//menuJuego(entrada,jugadorRegistros);
				
				if (!(jugadorRegistros == null)) {
					System.out.println("\n\nBienvenido " + jugadorRegistros.getNombre());
					jugar(jugadorRegistros, entrada);
				}else {
					System.out.println("No hay partida cargada.. Por favor crea nueva partida");
					System.out.println(" ");
				}
				
					
					
				break;
			case 2:
				//Opcion nueva partida
				System.out.print("Ingrese su apodo de jugador: ");
				String apodo = entrada.nextLine();
				
				Jugador nuevoJugador = new Jugador(apodo,0); 
				
				jugar(nuevoJugador, entrada);
				
				break;
			case 3:
				aux = false;
				break;
			default:
				System.out.println("Opcion no valida...");
			}
		} while (aux);
		
		entrada.close();
		
	}
	
	public static void jugar(Jugador jugador, Scanner entrada) {
		
		boolean continuar = true;
		do {
			System.out.println(" ");
			System.out.println("\n¿Qué deseas hacer?"
					+ "\n1) Revisar equipo"
					+ "\n2) Salir a capturar"
					+ "\n3) Acceso al pc (cambiar pokemon del equipo) "
					+ "\n4) Retar un Gimnasio  "
					+ "\n5) Desafio de alto mando "
					+ "\n6) Curar Pokemon "
					+ "\n7) Guardar "
					+ "\n8) Guardar y salir \n"); 
			
			System.out.print("Ingrese Opcion: ");
			
			int actividad = Integer.parseInt(entrada.nextLine()); 
			switch (actividad) {
			
			case 1: //revisar equipo
				System.out.println(" ");
				jugador.revisarEquipo(); //lo mismo pero otro jugador nomas
				break;
			case 2: //capturar
				capturar(jugador, entrada);
				break;
			case 3: // entrar al PC
				
				break;
			case 4: // Gimnasio
				
				break;
			case 5: //alto mando

				break;
			case 6: // curar pokemon
				jugador.curarEquipo();
				break;
			case 7: //guardar partida
				guardarPartida(jugador);
				break;
			case 8: //guardar partida y salir
				guardarPartida(jugador);
				System.out.println();
				continuar = false;
				break;
			default:
				break;
			}
		} while (continuar);
			
		
		
	}
	
	public static void capturar(Jugador jugador, Scanner entrada) {
		int indiceZona = -1;
		//preguntamos por la Zona
		do {
			System.out.println("¿Dónde deseas ir a explorar?"
					+ "\n\nZonas Disponibles: \n\n");
			for (int i = 0; i < habitats.size(); i++) {
				System.out.println( i+1 + ") " + habitats.get(i).getNombre());
			}
			
			System.out.println(habitats.size()+1 + ") Volver al menú");
			System.out.print("\nIngrese zona: ");
			indiceZona = Integer.parseInt(entrada.nextLine());
			indiceZona += -1; //para manejarlo como se debe
			
			//este control de errores se puede mejorar pero no quiero pensar cómo
			if (indiceZona > habitats.size() || indiceZona < 0) {
				indiceZona = habitats.size() + 10;
			}
		} while (indiceZona > habitats.size());
		
		Habitat Zona;
		if (indiceZona == habitats.size()) {
			System.out.println("Volviendo...");
			return;
		} else {
			Zona = habitats.get(indiceZona);
		}
		
		
		
		//Buscamos e interactuamos con el pokemon
		Pokemon pokemonSalvaje = Zona.explorar();
		System.out.println("¡¡Un " + pokemonSalvaje.getNombre() + " salvaje ha aparecido!!"
				+ "\n\n¿Qué deseas hacer?"
				+ "\n\n1) Capturar"
				+ "\n2) Huir");
		int menuSalvaje = -1; 
		System.out.print("Ingrese opción: ");
		
		
		do {
			menuSalvaje = Integer.parseInt(entrada.nextLine());
		switch (menuSalvaje) {
		case 1:
			jugador.agregarPokemon(pokemonSalvaje);
			break;
		case 2:
			System.out.println("Has huido con éxito.\n");
			break;
		default:
			System.out.println("\nOpción inválida, ingrese un valor correcto: ");
			break;
		}
			
		}while(menuSalvaje != 1 && menuSalvaje != 2);
		
	}
	public static void guardarPartida(Jugador jugador) {
		try { BufferedWriter escritor = new BufferedWriter(new FileWriter("Registros.txt"));
		
		escritor.write(jugador.getNombre() + ";" + jugador.getMedallas());
		
		ArrayList<Pokemon> equipo = jugador.getEquipo();
		
		if (equipo.size() > 0) {
			for (int i = 0; i < equipo.size(); i++) {
				escritor.newLine();
				escritor.write(equipo.get(i).getNombre() + ";");
				
				if (equipo.get(i).getEstado() == true) {
					escritor.write("Vivo");
				} else {
					escritor.write("Debilitado");
				}
			}
		}
		
		escritor.close();
		} catch (Exception e) {
			System.out.println("Hubo un error al cargar la partida.");
		}
	}
	public static Jugador leerRegistros() throws FileNotFoundException {
		File f = new File("Registros.txt");
		Scanner sc = new Scanner(f);
		if (!sc.hasNextLine()) {
			System.out.println(" ");
			System.out.println("Error al cargar partida..");
			sc.close();
			return null; 
		}
		
		String linea = sc.nextLine();
		String[] partes = linea.split(";");
		String nombreJugador = partes[0];
		
		
		int cantMedallas = 0; 
		if (!partes[1].equalsIgnoreCase("none")) {
			//  las medallas dependiendo de cómo lo guardan
			try { cantMedallas = Integer.parseInt(partes[1]); 
			}catch(Exception e) {
				System.out.println("Error al cargar las medallas..");
			}
		}
		Jugador jugadorRegistrado = new Jugador(nombreJugador,cantMedallas);
		while(sc.hasNextLine()) {
			linea = sc.nextLine();
			partes = linea.split(";");
			String nomPokemon = partes[0];
			
			//ponemos el estado base vivo y si no es el caso entonces lo debilitamos
			boolean estadoPokemon = true;
			if (partes[1].equalsIgnoreCase("Debilitado")) {
				estadoPokemon = false;
			}
			
			int idx = buscarPokemon(nomPokemon,pokedex);
			if (idx != -1) {
				Pokemon baseClon = pokedex.get(idx); //no podemos referenciar al pokemon de la pokedex sino afectaria a todos los pokemon que tengan el mismo nombre pero distinto dueño..
				Pokemon pokemonClonado = new Pokemon(baseClon.getNombre(), baseClon.getHabitat(), baseClon.getPorcentajeAparicion(), baseClon.getVida(), baseClon.getAtaque(), baseClon.getDefensa(), baseClon.getAtaqueEspecial(), baseClon.getDefensaEspecial(), baseClon.getVelocidad(), baseClon.getTipo());
				
				pokemonClonado.setEstado(estadoPokemon);
				jugadorRegistrado.agregarPokemon(pokemonClonado);
			}	
			
			
		}
		sc.close();
		return jugadorRegistrado;
	}
	public static void leerPokedex() {
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
	public static int buscarPokemon(String nombre, ArrayList<Pokemon> lista) { 
		for ( int i = 0; i< lista.size(); i++) {
			if ( nombre.equals(lista.get(i).getNombre())) {
				return i;
			}
		}
		return -1;
	}
}

