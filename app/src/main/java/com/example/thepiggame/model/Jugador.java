package com.example.thepiggame.model;

public class Jugador {
    private int puntuacion;

    public Jugador(){
        this.puntuacion = 0;
    }

    public int getPuntuacion(){return puntuacion;}

    public void reiniciarPuntuacion(){
        this.puntuacion = 0;
    }

    public void sumarPuntos(int puntuacion){
        this.puntuacion += puntuacion;
    }
}
