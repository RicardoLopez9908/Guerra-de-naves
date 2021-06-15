# Guerra-de-naves

__Se trata de un juego con tematica de naves, en la cual debemos eliminar la mayor cantidad de naves posible que nos aparezcan. Nos encontramos al comienzo con una pantalla inicial, en la cual debemos indicar nuestras preferencias de juego y una pantalla final, en la que el juego nos indicara nuestros resultados__

[img](https://github.com/RicardoLopez9908/Guerra-de-naves/blob/main/Presentacion%20guerra%20de%20naves.jpg)

## Funciones del juego:

- Tres posibilidades de dificultad dentro del codigo(facil, medio, dificil). :pencil2:

    `private Dificultad dificultad = Dificultad.FACIL;`

    `private Dificultad dificultad = Dificultad.MEDIO;`

    `private Dificultad dificultad = Dificultad.DIFICIL;`

- Sonido incorporado. (STAR WARS) :sound:
- Opcion multijugador a eleccion del usuario. (1 o 2 usuarios al mismo tiempo) :beers:
- Contador de puntos individual por partida. :floppy_disk:
- Volver a jugar la partida sin necesidad de cerrar la ventana y volver a abrir. :arrows_clockwise:
- Capacidad de incorporar mas disparos simultaneos (dentro del codigo). :pencil2:

    `private int minimaCantidadDisparos = 1;`
    
    `private int minimaCantidadDisparos = 2; // etc...`
    
- Aparicion de naves y obstaculos infinitos (gracias al manejo de listas). :boom:
- Guardar record actual automaticamente dentro de un archivo de texto (el cual se encuentra en la carpeta de recursos). :cd:
