package com.example.thepiggame;

import android.animation.Animator;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.thepiggame.databinding.ActivityMainBinding;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    int numeroDado;
    Contador contadorJ1 = new Contador(0);
    Contador contadorJ2 = new Contador(0);
    Contador contadorSeleccionado;
    TextView jugador1;
    TextView jugador2;
    TextView jugadorSeleccionado;
    TextView dadoJ1;
    TextView dadoJ2;
    TextView dadoSeleccionado;
    TextView ganadorJ1;
    TextView ganadorJ2;
    TextView ganadorSeleccionado;
    Toast textoEmergente;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setContentView((binding = ActivityMainBinding.inflate(getLayoutInflater())).getRoot());
        jugador1 = findViewById(R.id.puntosJ1);
        jugador2 = findViewById(R.id.puntosJ2);

        dadoJ1 = findViewById(R.id.numDadoJ1);
        dadoJ2 = findViewById(R.id.numDadoJ2);

        ganadorJ1 = findViewById(R.id.ganadorJ1);
        ganadorJ2 = findViewById(R.id.ganadorJ2);

        seleccionarJ1();

        Toast.makeText(MainActivity.this, "Empieza el Jugador 1", Toast.LENGTH_SHORT).show();

        mapaDado();

        binding.btnLanzar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numeroDado = random();
                actualizarDatos(numeroDado);
            }
        });

        binding.btnPasar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "¡Buen turno!", Toast.LENGTH_SHORT).show();
                cambiarJugador();
            }
        });

        binding.reiniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reiniciar();
            }
        });

    }

    /**
     * Método para crear el HashMap con los dados y su valor asocialdo
     **/
    private static HashMap<Integer, Integer> mapaDado() {
        HashMap<Integer, Integer> mapaDado = new HashMap<>();
        mapaDado.put(R.drawable.dice_one, 1);
        mapaDado.put(R.drawable.dice_two, 2);
        mapaDado.put(R.drawable.dice_three, 3);
        mapaDado.put(R.drawable.dice_four, 4);
        mapaDado.put(R.drawable.dice_five, 5);
        mapaDado.put(R.drawable.dice_six, 6);
        return mapaDado;
    }

    /**
     * Generar el valor del dado al lanzar
     **/
    private int random() {
        return (int) (Math.random() * 6) + 1;
    }

    /**
     * Actualizar datos
     **/
    private void actualizarDatos(int numeroDado) {
        int dado = mapaDado().entrySet().stream()
                .filter(values -> values.getValue() == numeroDado)
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);

        binding.dado.setImageResource(dado);

        if (numeroDado == 1) {
            Toast.makeText(MainActivity.this, "Has sacado un 1. Pierdes el turno", Toast.LENGTH_SHORT).show();
            dadoSeleccionado.setText("1");
            jugadorSeleccionado.setText("0");
            contadorSeleccionado.valor=0;
            animacion();
            cambiarJugador();
        } else {
            contadorSeleccionado.valor += numeroDado;
            dadoSeleccionado.setText(String.valueOf(numeroDado));
            jugadorSeleccionado.setText(String.valueOf(contadorSeleccionado.valor));
            if (contadorSeleccionado.valor >= 100) {
                ganar();
                binding.btnLanzar.setClickable(false);
                binding.btnPasar.setClickable(false);
            }
        }
    }

    /**
     * Cambiar jugador
     **/
    private void cambiarJugador() {
        if (contadorSeleccionado == contadorJ1) {
            seleccionarJ2();
            Toast.makeText(MainActivity.this, "Turno del jugador 2", Toast.LENGTH_SHORT).show();
        } else {
            seleccionarJ1();
            Toast.makeText(MainActivity.this, "Turno del jugador 1", Toast.LENGTH_SHORT).show();
        }
    }

    private void seleccionarJ1() {
        contadorSeleccionado = contadorJ1;
        jugadorSeleccionado = jugador1;
        dadoSeleccionado = dadoJ1;
        ganadorSeleccionado = ganadorJ1;
        binding.noJuega1.setVisibility(View.INVISIBLE);
        binding.noJuega2.setVisibility(View.VISIBLE);
    }

    public void seleccionarJ2() {
        contadorSeleccionado = contadorJ2;
        jugadorSeleccionado = jugador2;
        dadoSeleccionado = dadoJ2;
        ganadorSeleccionado = ganadorJ2;
        binding.noJuega1.setVisibility(View.VISIBLE);
        binding.noJuega2.setVisibility(View.INVISIBLE);
    }

    /**
     * Reiniciar el juego
     **/
    private void reiniciar() {
        contadorJ1.valor = contadorJ2.valor = 0;
        jugador1.setText("0");
        jugador2.setText("0");
        dadoJ1.setText("0");
        dadoJ2.setText("0");
        ganadorSeleccionado.setVisibility(View.INVISIBLE);
        binding.btnLanzar.setClickable(true);
        binding.btnPasar.setClickable(true);

        seleccionarJ1();
    }

    /** Mostrar ganador **/
    private void ganar(){
        ganadorSeleccionado.setVisibility(View.VISIBLE);
        if (ganadorSeleccionado == ganadorJ1){
            Toast.makeText(MainActivity.this, "¡Ha ganado el Jugador 1!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, "¡Ha ganado el Jugador 2!", Toast.LENGTH_SHORT).show();
        }

        Toast.makeText(MainActivity.this, "Haz clic en el botón de reiniciar para empezar otra partida", Toast.LENGTH_SHORT).show();
    }

    /**
     * Animación dado error
     **/
    public void animacion() {
        binding.dado.setAlpha(0f);
        binding.dado.setImageResource(R.drawable.dice_one_error);

        binding.dado.animate()
                .alpha(1f)
                .setDuration(300)
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        binding.dado.animate()
                                .alpha(0f)
                                .setDuration(300)
                                .withEndAction(new Runnable() {
                                    @Override
                                    public void run() {
                                        binding.dado.setImageResource(R.drawable.dice_one);
                                        binding.dado.setAlpha(0f);

                                        binding.dado.animate()
                                                .alpha(1f)
                                                .setDuration(100);
                                    }
                                });
                    }
                });
    }

}