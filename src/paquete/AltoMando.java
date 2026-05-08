package paquete;

import java.util.ArrayList;

public class AltoMando {
	
	public static ArrayList<Entrenador> altosMandos = new ArrayList<>();
	
	public static void agregarAltoMando(Entrenador altoMando) {
		altosMandos.add(altoMando);
	}
	
	public static Entrenador getAltoMando(int indice) {
		return altosMandos.get(indice);
	}
	
	public static int cantidadAltosMandos() {
		return altosMandos.size();
	}
	
	public static void reiniciarAltoMando() {
		for (Entrenador altoMando: altosMandos) {
			altoMando.curarEquipo();
		}
	}
}
