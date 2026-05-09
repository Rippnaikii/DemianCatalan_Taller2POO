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
	public static ArrayList<Gimnasio> gimnasios = new ArrayList<>();
	//public static TablaTipos tabla = new TablaTipos();

	public static void main(String[] args) throws FileNotFoundException {
		leerHabitats();

		leerPokedex();
		
		leerGimnasios();
		
		leerAltoMando();
		/*System.out.println("Habitats cargados: " + habitats.size()); // solo para probar si printea bien...*/
		Scanner entrada = new Scanner(System.in);

		boolean repetirMenu;
		do {
			repetirMenu = false;
			System.out.println("1) Continuar. \n" + "2) Nueva Partida. \n" + "3) Salir. \n ");

			System.out.print("Ingrese Opcion: ");

			int opcion = leerOpcionSegura(entrada);
			switch (opcion) {

			case 1:
				// Opcion continuar
				Jugador jugadorRegistros = leerRegistros();

				if (!(jugadorRegistros == null)) {
					System.out.println("\n\nBienvenido " + jugadorRegistros.getNombre());
					sincronizarGimnasios(jugadorRegistros.getMedallas());
					jugar(jugadorRegistros, entrada);
				} else {
					System.out.println("No hay partida cargada... Por favor crea nueva partida\n");
					repetirMenu = true;
				}

				break;
			case 2:
				// Opcion nueva partida
				System.out.print("Ingrese su apodo de jugador: ");
				String apodo = entrada.nextLine();

				Jugador nuevoJugador = new Jugador(apodo, "none");
				sincronizarGimnasios("none");
				jugar(nuevoJugador, entrada);

				break;
			case 3:
				break;
			default:
				System.out.println("\nOpcion no valida...\n");
				repetirMenu = true;
			}
		} while (repetirMenu);
		entrada.close();

	}
	public static int leerOpcionSegura(Scanner entrada) {
		try {
			return Integer.parseInt(entrada.nextLine().strip()); 
		} catch (NumberFormatException e) { //me di cuenta del error por un enter vacio en el menu..
			return -1; 
		}
	}
	public static void jugar(Jugador jugador, Scanner entrada) {
		
		
		boolean continuar = true;
		do {
			System.out.println("\n\n¿Qué deseas hacer?" 
					+ "\n1) Revisar equipo" 
					+ "\n2) Salir a capturar"
					+ "\n3) Acceso al pc (cambiar pokemon del equipo) " 
					+ "\n4) Retar un Gimnasio  "
					+ "\n5) Desafio de alto mando " + "\n6) Curar Pokemon " 
					+ "\n7) Guardar "
					+ "\n8) Guardar y salir \n");

			System.out.print("Ingrese Opcion: ");
			
			int actividad = leerOpcionSegura(entrada); //descubri un pequeño error aca mejor lo ponemos en un try catch
			
			switch (actividad) {

			case 1: // revisar equipo
				System.out.println(" ");
				jugador.revisarEquipo(); // lo mismo pero otro jugador nomas
				break;
			case 2: // capturar
				capturar(jugador, entrada);
				break;
			case 3: // entrar al PC
				jugador.revisarPc();// MOSTRAMOS LA LISTA DE POKEMONES
				menuCambioPokemon(jugador, entrada); // LE PREGUNTAMOS QUE QUIERE HACER CON ESTA LISTA

				break;
			case 4: // Gimnasio
				retarALider(jugador,entrada); //NECESITA MEJORA, ESTA INCOMPLETO.. ME DIO FLOJERA SEGUIR..
				break;
			case 5: // alto mando
				retarAltosMandos(jugador, entrada);
				break;
			case 6: // curar pokemon
				jugador.curarEquipo();// mejorar
				System.out.println("\n¡La salud de tu equipo ha sido restaurada!\n");
				break;
			case 7: // guardar partida
				guardarPartida(jugador);
				break;
			case 8: // guardar partida y salir
				guardarPartida(jugador);
				System.out.println();
				continuar = false;
				System.out.println("\n\n¡Nos vemos, " + jugador.getNombre() + "!.");
				break;
			default:
				System.out.println("Opcion no valida..");
				break;
			}
		} while (continuar);

	}
	public static int menuCombate(Scanner entrada) {
		System.out.print("\nQue deseas hacer?\n"
				+ "1) Atacar\n"
				+ "2) Cambiar de pokemon\n"
				+ "3) Rendirse\n"
				+ "Ingrese Opcion: ");
		int opcion = leerOpcionSegura(entrada);
		return opcion;
	}
	public static Pokemon cambiarPokemon(Jugador jugador, Scanner entrada) {
		while (true) {
			System.out.println("A cuál pokemon deseas cambiar?\n");
			jugador.mostrarEquipo();
			int indiceCambio;
			do {
				System.out.print("\nseleccione un pokemon: ");
				indiceCambio = leerOpcionSegura(entrada) - 1;
			} while(indiceCambio < 0 || indiceCambio >= jugador.getEquipo().size());
			
			Pokemon cambio = jugador.elegirPokemon(indiceCambio);
			if (cambio != null) {
				return cambio;
			} else {System.out.println("El pokemon que desea sacar está debilitado.\n");}
		}
	}
	public static boolean combate(Jugador jugador, Entrenador rival, Scanner entrada) {
		if (jugador.haPerdido() == true) {
			System.out.println("\n\nNo puedes desafiar a otro entrenador con tu equipo debilitado...");
			return false;
		}
		boolean jugadorGana = false;
		boolean finCombate = false;
		
		Pokemon pokemonRival = rival.sacarPokemon();
		Pokemon pokemonActual = jugador.sacarPokemon(); 
		
		
		do {
			//int combate = menuCombate(entrada);
			int combate = menuCombate(entrada); 
			
			switch(combate) {
			
			case 1://atacar
				double puntos = pokemonActual.sumaStats();
				double puntosRival = pokemonRival.sumaStats();
				
				System.out.println("\n"+ pokemonActual.getNombre() + " --> " + puntos
						+ "\n" + pokemonRival.getNombre() + " --> " + puntosRival);
				
				double efectividad = TablaTipos.efectividad(pokemonActual.getTipo(), pokemonRival.getTipo());
				puntos = puntos * efectividad;
				
				if (efectividad > 1.0 ) {
					System.out.println("\n¡" + pokemonActual.getNombre() + " es efectivo contra " + pokemonRival.getNombre() + "!");
				} else if(efectividad < 1.0) {
					System.out.println("\n" + pokemonActual.getNombre() + " no es efectivo contra " + pokemonRival.getNombre() + "...");
				}
				
				//esto se repite, no lo puse como metodo porque habría que entregarle los puntajes igual:
				System.out.println("\n"+ pokemonActual.getNombre() + " --> " + puntos
						+ "\n" + pokemonRival.getNombre() + " --> " + puntosRival);
				
				if (puntos > puntosRival) {
					pokemonRival.setEstado(false);
					pokemonRival = rival.sacarPokemon();
				} else if(puntos < puntosRival) {
					pokemonActual.setEstado(false);
					if (jugador.haPerdido() != true) {
						pokemonActual = cambiarPokemon(jugador, entrada);
					} 
				}
				
				break;
				
				
			case 2://cambiar pokemon
				pokemonActual = cambiarPokemon(jugador, entrada);
				break;
				
				
			case 3: //rendirse
				finCombate = true;
				System.out.println("\nTe has rendido...\n");
				break;
			}
			
			
			if (rival.haPerdido() == true) {
				System.out.println("¡Has derrotado a " + rival.getNombre() + "!");
				jugadorGana = true;
				finCombate = true;
			} else if (jugador.haPerdido() == true) {
				System.out.println("Te has quedado sin pokemon en tu equipo...");
				finCombate = true;
			}
		}while (finCombate == false);
		
		return jugadorGana;
	}
	
	private static void retarALider(Jugador jugador, Scanner entrada) {
		System.out.println("A cual lider deseas retar?? \n");
		
		int indiceGym = -1;
		
		// conseguimos el gimnasio a retar
		
		do {
			for (int i = 0; i < gimnasios.size(); i++) {
				System.out.println(gimnasios.get(i).toString());
			}
			System.out.print(gimnasios.size() + 1 + ") Volver al menú"
					+ "\n\nIngrese gimnasio: ");
			
			indiceGym = leerOpcionSegura(entrada) - 1;
		} while (indiceGym > gimnasios.size() || indiceGym < 0);
		
		if (indiceGym == gimnasios.size()) { //verificamos si quiere volver
			System.out.println("\nVolviendo...");
			return;
		} 
		Gimnasio gym;
		gym = gimnasios.get(indiceGym);
		
		if (indiceGym > 0 && gimnasios.get(indiceGym-1).isDerrotado() == false) {
			System.out.println("Calmado Entrenador!!! No puedes retar a " + gym.getMedalla() + " sin haber derrotado a los lideres anteriores!!");
			return;
		}
		
		System.out.println("\nDesafiando a " + gym.getLider().getNombre() + "!");
		
		//tenemos el gymnasio y empezamos el combate
		boolean jugadorGana = combate(jugador, gym.getLider(), entrada);
		String estaMedalla = gym.getMedalla();
		
		
		//le damos la medalla si gano y no la tiene
		if (jugadorGana == true && gym.isDerrotado() == false) {
			jugador.agregarMedalla(estaMedalla); // Actualiza su medallaMaxima
			gym.setDerrotado(true); 
			System.out.println("¡Has derrotado al Líder de gimnasio " + estaMedalla + "!");
		} else if (jugadorGana == false) {
			gym.getLider().curarEquipo();
		}
		
		System.out.println("Volviendo al menú principal...\n");
		
	}
	
	private static void retarAltosMandos(Jugador jugador, Scanner entrada) {
		if (!jugador.getMedallas().equals(gimnasios.get(gimnasios.size()-1).getMedalla())) {
			System.out.println("No puedes desafiar al Alto mando sin derrotar a " + gimnasios.get(gimnasios.size()-1).getMedalla());
			return;
		}
		
		
		for (int i = 0; i < AltoMando.cantidadAltosMandos(); i++) {
			System.out.println("\nEstas desafiando a " + AltoMando.getAltoMando(i).getNombre());
			boolean gana = combate(jugador, AltoMando.getAltoMando(i), entrada);
			
			if (gana == false) {
				AltoMando.reiniciarAltoMando();
				System.out.println("\n\n\nHas fallado tu desafío al alto mando..."
						+ "\nVolviendo al menú principal...\n");
				return;
			}
			
			System.out.println("\nTe quedan " + (AltoMando.cantidadAltosMandos()-(i+1)) + " altos mandos por derrotar");
		}
		
		System.out.println("===================================="
				+ "\n\n¡¡¡¡Felicidades!!!!"
				+ "\n\nHas derrotado al alto mando y te has coronado como el mejor entrenador de la region."
				+ "\n\nGracias por jugar. Ahora volverás al menú principal.");
	}
	
	public static void menuCambioPokemon(Jugador jugador, Scanner entrada) {
		System.out.println("¿Qué deseas hacer?");
		boolean continuar = true;
		do {
			System.out.println(" ");
			System.out.println("1) Cambiar Pokémon. \n2) Salir.");
			System.out.print("Ingrese Opcion: ");

			int actividad = leerOpcionSegura(entrada);
			switch (actividad) {
			case 1:
				System.out.println(" ");
				System.out.print("Ingrese el primer numero de la lista a intercambiar: ");
				int indicePokemon1 = leerOpcionSegura(entrada) - 1; // PARA QUE OCUPEMOS LOS INDICES COMO LO
																				// VEMOS EN LOS ARRAYS
				System.out.print("Ingrese el segundo numero de la lista a intercambiar: ");
				int indicePokemon2 = leerOpcionSegura(entrada) - 1;
				jugador.intercambiarPokemon(indicePokemon1, indicePokemon2);
				break;
			case 2:
				System.out.println("Has salido con exito..");
				continuar = false;
				break;
			default:
				System.out.println("Opcion no valida ");
				break;
			}
		} while (continuar);
	}

	public static void capturar(Jugador jugador, Scanner entrada) {
		int indiceZona = -1;
		// preguntamos por la Zona
		do {
			System.out.println("¿Dónde deseas ir a explorar?" + "\n\nZonas Disponibles: \n\n");
			for (int i = 0; i < habitats.size(); i++) {
				System.out.println(i + 1 + ") " + habitats.get(i).getNombre());
			}

			System.out.println(habitats.size() + 1 + ") Volver al menú");
			System.out.print("\nIngrese zona: ");
			indiceZona = leerOpcionSegura(entrada);
			indiceZona += -1; 
			
			/*if (indiceZona > habitats.size() || indiceZona < 0) {
				indiceZona = habitats.size() + 10;
			}*/
		} while (indiceZona > habitats.size() || indiceZona < 0) ;

		Habitat Zona;
		if (indiceZona == habitats.size()) {
			System.out.println("Volviendo...");
			return;
		} else {
			Zona = habitats.get(indiceZona);
		}

		
		Pokemon pokemonSalvaje = Zona.explorar(); // MEJORADO
		System.out.println("¡¡Un " + pokemonSalvaje.getNombre() + " salvaje ha aparecido!!" + "\n\n¿Qué deseas hacer?"
				+ "\n\n1) Capturar" + "\n2) Huir");
		int menuSalvaje = -1;
		System.out.print("Ingrese opción: ");

		do {
			menuSalvaje = leerOpcionSegura(entrada);
			switch (menuSalvaje) {
			case 1:
				if (!jugador.tieneElPokemon(pokemonSalvaje)) {
					jugador.agregarPokemon(pokemonSalvaje); 
					System.out.println(pokemonSalvaje.getNombre() + " capturado con exito!!");
				} else {
					System.out.println(" ");
					System.out.println("Ya tienes un " + pokemonSalvaje.getNombre() + " en tu equipo o pc..");
				}

				break;
			case 2:
				System.out.println("Has huido con éxito.\n");
				break;
			default:
				System.out.println("\nOpción inválida, ingrese un valor correcto: ");
				break;
			}

		} while (menuSalvaje != 1 && menuSalvaje != 2);

	}

	public static void guardarPartida(Jugador jugador) {
		try {
			BufferedWriter escritor = new BufferedWriter(new FileWriter("Registros.txt"));

			escritor.write(jugador.getNombre() + ";" + jugador.getMedallas());

			ArrayList<Pokemon> equipo = jugador.getEquipo(); // FALTO GUARDAR LA LISTA PC...
			ArrayList<Pokemon> pc = jugador.getPc(); // LISTO
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
			if (pc.size() > 0) {
				for (int i = 0; i < pc.size(); i++) {
					escritor.newLine();
					escritor.write(pc.get(i).getNombre() + ";");

					if (pc.get(i).getEstado() == true) {
						escritor.write("Vivo");
					} else {
						escritor.write("Debilitado");
					}
				}
			}

			escritor.close();
		} catch (Exception e) {
			System.out.println("Hubo un error al guardar la partida.");
		}
	}

	public static Jugador leerRegistros() throws FileNotFoundException {
		File f = new File("Registros.txt");
		Scanner sc = new Scanner(f);
		if (!sc.hasNextLine()) {
			System.out.println("\nError al cargar partida..");
			sc.close();
			return null;
		}

		String linea = sc.nextLine();
		String[] partes = linea.split(";");
		String nombreJugador = partes[0];

		String medallas = "none";
		if (!partes[1].equalsIgnoreCase("none")) {
			medallas = partes[1];
		}
		Jugador jugadorRegistrado = new Jugador(nombreJugador, medallas);
		while (sc.hasNextLine()) {
			linea = sc.nextLine();
			partes = linea.split(";");
			String nomPokemon = partes[0];

			// ponemos el estado base vivo y si no es el caso entonces lo debilitamos
			boolean estadoPokemon = true;
			if (partes[1].equalsIgnoreCase("Debilitado")) {
				estadoPokemon = false;
			}

			int idx = buscarPokemon(nomPokemon);
			if (idx != -1) {
				Pokemon baseClon = pokedex.get(idx); 
				Pokemon pokemonClonado = baseClon.clonarPokemon();

				pokemonClonado.setEstado(estadoPokemon);
				jugadorRegistrado.agregarPokemon(pokemonClonado);
			}

		}
		sc.close();
		return jugadorRegistrado;
	}
	public static void sincronizarGimnasios(String ultimaMedalla) {
		for (int i = 0; i < gimnasios.size(); i++) {
			gimnasios.get(i).setDerrotado(false);
		}
		
		if (ultimaMedalla.equalsIgnoreCase("none")) {
			return; // Si no tiene medallas se quedan todos en false....
		}
		

		for (int i = 0; i < gimnasios.size(); i++) {
			// Vamos marcando true hasta que encontramos al lider..
			gimnasios.get(i).setDerrotado(true);

			if (gimnasios.get(i).getLider().getNombre().equalsIgnoreCase(ultimaMedalla)) {
				break; 
			}
		}
	}
	public static void leerPokedex() {
		// leemos e instanciamos los pokemon
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

				Pokemon pokemon = new Pokemon(nombre, habitat, porcentajeAparicion, vida, ataque, defensa, ataqueEsp,
						defensaEsp, velocidad, tipo);
				pokedex.add(pokemon);

				// agregamos el pokemon a su habitat
				for (int i = 0; i < habitats.size(); i++) {
					if (habitat.equals(habitats.get(i).getNombre())) {
						habitats.get(i).agregarSalvaje(pokemon);
						break;
					}
				}
			}

			lector.close();
		} catch (FileNotFoundException e) {
			System.out.println("No se encontraron los datos de la Pokédex");
		}
		//System.out.println("se introdujeron: " + pokedex.size() + " pokémon en la pokédex.");

	}

	public static void leerHabitats() {
		// leemos e instanciamos los habitats
		try {
			File archivo = new File("Habitats.txt");
			Scanner lector = new Scanner(archivo);

			while (lector.hasNextLine()) {
				String linea = lector.nextLine().strip();
				Habitat habitat = new Habitat(linea);
				habitats.add(habitat);
			}

			lector.close();
		} catch (FileNotFoundException e) {
			System.out.println("No se encontraron los datos de los Hábitats");
		}
	}
	
	public static void leerAltoMando() {
		try {
			File f = new File("Alto Mando.txt");
			Scanner sc = new Scanner(f);
			while (sc.hasNextLine()) {
				String[] partes = sc.nextLine().split(";");
				
				//partes 0 lo ignoramos..
				String nombreElite = partes[1];
				Entrenador elite = new Entrenador(nombreElite);

				// ocupamos el tamaño de partes partiendo desde el 2
				for (int i = 2; i < partes.length; i++) {
					String nomPokemon = partes[i]; 
					int idx = buscarPokemon(nomPokemon);
					
					if (idx != -1) {
						Pokemon pokemonElite = pokedex.get(idx).clonarPokemon();
						elite.agregarPokemon(pokemonElite);
					} else {
						System.out.println("El pokemon " + nomPokemon + " no está en la Pokedex..");
					}
				}
				AltoMando.agregarAltoMando(elite);
			}
			sc.close();
		} catch (FileNotFoundException e) {
			System.out.println("No se encontraron los datos de AltoMando.txt");
		}
	}
	public static void leerGimnasios() {

		try {
			File f = new File("Gimnasios.txt");
			Scanner sc = new Scanner(f);
			while (sc.hasNextLine()) {
				String[] partes = sc.nextLine().split(";");
				int numGym = Integer.parseInt(partes[0]);
				String nomLider = partes[1];
				Entrenador lider = new Entrenador(nomLider);
				String estadoLider = partes[2];
				int cantPokemons = Integer.parseInt(partes[3]);

				for (int i = 0; i < cantPokemons; i++) {
					String nomPokemon = partes[i + 4];
					int idx = buscarPokemon(nomPokemon);
					if (idx != -1) {
						Pokemon pokemonLider = pokedex.get(idx).clonarPokemon();
						// añadimos el clon a el array del lider
						lider.agregarPokemon(pokemonLider);
					} else {
						System.out.println("el pokemon no existe en la pokedex..");
					}
				}
				Gimnasio newGym = new Gimnasio(numGym, lider,estadoLider);
				if (estadoLider.equalsIgnoreCase("Derrotado")) { // en caso de que ya este derrotado..
					newGym.setDerrotado(true);
				} else {lider.curarEquipo();}
				gimnasios.add(newGym);

			}
			sc.close();

		} catch (FileNotFoundException e) {
			System.out.println("No se encontraron los datos de los Gimnasios..");
		}
	}
	
	public static int buscarPokemon(String nombre) {
		for (int i = 0; i < pokedex.size(); i++) {
			if (nombre.equals(pokedex.get(i).getNombre())) {
				return i;
			}
		}
		return -1;
	}
	
}
