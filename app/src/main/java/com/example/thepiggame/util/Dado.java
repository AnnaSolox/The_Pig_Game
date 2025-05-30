package com.example.thepiggame.util;

import com.example.thepiggame.R;

import java.util.HashMap;
import java.util.Map;

public class Dado {
    private static final HashMap<Integer, Integer> mapaDado = new HashMap<>();
    private static int ultimoValor;

    static {
        mapaDado.put(R.drawable.dice_one, 1);
        mapaDado.put(R.drawable.dice_two, 2);
        mapaDado.put(R.drawable.dice_three, 3);
        mapaDado.put(R.drawable.dice_four, 4);
        mapaDado.put(R.drawable.dice_five, 5);
        mapaDado.put(R.drawable.dice_six, 6);
    }

    public static int lanzarDado(){
        ultimoValor = (int)(Math.random() * 6) + 1;
        return ultimoValor;
    }

    public static int obtenerDado(int valor){
        for (Map.Entry<Integer, Integer> entry: mapaDado.entrySet()) {
            if(entry.getValue() == valor) {
                return entry.getKey();
            }
        }
        return R.drawable.dice_one;
    }

    public static int getUltimoValor(){
        return ultimoValor;
    }
}
