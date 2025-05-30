package com.example.thepiggame.logic;

import com.example.thepiggame.model.Jugador;

public class ControladorJuego {
    private final Jugador jugador1 = new Jugador();
    private final Jugador jugador2 = new Jugador();
    private Jugador jugadorActual = jugador1;

    public Jugador getJugador1(){return jugador1;}
    public Jugador getJugador2(){return jugador2;}
    public Jugador getJugadorActual(){return jugadorActual;}
    public void cambiarJugador(){
        jugadorActual = (jugadorActual == jugador1) ? jugador2 : jugador1;
    }

    public void sumarPuntos(int valorDado){
        jugadorActual.sumarPuntos(valorDado);
    }

    public boolean hayGanador(){
        return jugadorActual.getPuntuacion() >= 100;
    }

    public void reiniciarJuego(){
        jugador1.reiniciarPuntuacion();
        jugador2.reiniciarPuntuacion();
        jugadorActual = jugador1;
    }
}
