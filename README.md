# Taller 2 de POO: Simulador de Combates Pokémon

## Integrantes
* Demian Catalan
* Daniel Moreno

## Descripción del Proyecto
Este proyecto es un simulador de combates Pokémon desarrollado en Java. Aplicamos conceptos clave de Programación Orientada a Objetos (POO) como herencia, polimorfismo y listas dinámicas (`ArrayList`). El juego permite gestionar un equipo de hasta 6 Pokémon, capturar especies en distintos hábitats, organizar los sobrantes en el PC, y progresar derrotando a los Líderes de Gimnasio y al Alto Mando.

## Instrucciones de Ejecución
1. Clonar el repositorio.
2. Asegurarse de que los archivos de texto (`Pokedex.txt`, `Habitats.txt`, `Gimnasios.txt`, `AltoMando.txt`, `Registros.txt`) estén en la misma carpeta principal para que el programa pueda leerlos.
3. Compilar y ejecutar la clase `App.java`.
4. Seguir el menú en la consola.

## Decisiones de Diseño (Arquitectura del Código)

Para hacer el código más seguro y ordenado, tomamos estas decisiones técnicas durante el desarrollo:

* **Control de errores en menús (`leerOpcionSegura`):** Creamos un método con un bloque `try-catch` para leer las opciones del usuario. Si alguien escribe una letra por error o presiona 'Enter' vacío, el programa ataja la excepción y no se cae, simplemente vuelve a pedir un número válido.

* **Guardado optimizado (`medallaMaxima`):** En `Registros.txt`, en lugar de guardar una lista larga con todas las medallas, el jugador solo guarda un `String` con el nombre del líder más fuerte que derrotó. Al cargar la partida, el programa sabe deducir y marcar como derrotados a todos los gimnasios anteriores. Esto hace que guardar partidas sea más limpio y dé menos errores.

* **Envío automático al PC (Uso de Polimorfismo):** Sobrescribimos el método `agregarPokemon()` en la clase `Jugador`. Así, si el equipo ya tiene 6 integrantes, cualquier Pokémon nuevo que se capture es enviado automáticamente a la lista del PC, manteniendo el menú principal limpio de estas validaciones.

* **Combate universal:** El método `combate()` está diseñado para recibir dos objetos genéricos tipo `Entrenador`. Como tanto el Jugador, los Líderes y el Alto Mando heredan de esta clase, pudimos reutilizar la misma lógica de pelea para todo el juego sin repetir código.

* **Tabla de Tipos eficiente:** La matriz de debilidades y sus métodos en `TablaTipos` se definieron como `static`. Esto permite calcular el daño al instante llamando a la clase directamente, sin tener que gastar memoria instanciando un objeto nuevo de la tabla en cada ataque.

## Requisitos del Taller Cumplidos
* Límite estricto de 6 Pokémon en el equipo activo.
* Los Pokémon en estado "Debilitado" no pueden entrar a pelear.
* Uso de `.clonarPokemon()` al capturar para que cada Pokémon en el juego sea independiente y no compartan la misma referencia en memoria.
* El desafío del Alto Mando es consecutivo: si pierdes, no puedes curarte ni ir al PC, debes empezar la liga desde cero.
