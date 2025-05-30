package com.example.thepiggame;

import static android.widget.Toast.LENGTH_SHORT;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.thepiggame.databinding.ActivityMainBinding;
import com.example.thepiggame.logic.ControladorJuego;
import com.example.thepiggame.model.Jugador;
import com.example.thepiggame.util.Dado;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    ControladorJuego controlador;


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

        controlador = new ControladorJuego();
        actualizarUi(null, false);

        binding.btnLanzar.setOnClickListener(view -> lanzarDado());

        binding.btnPasar.setOnClickListener(view -> {
            controlador.cambiarJugador();
            actualizarUi(null, false);
        });

        binding.reiniciar.setOnClickListener(view -> reiniciarJuego());

    }

    private void lanzarDado() {
        int resutado = Dado.lanzarDado();
        binding.dado.setImageResource(Dado.obtenerDado(resutado));
        Jugador lanzador = controlador.getJugadorActual();

        boolean seguir = resutado != 1;

        if (seguir) {
            controlador.sumarPuntos(resutado);
            actualizarUi(lanzador, false);
            if (controlador.hayGanador()) {
                mostrarGanador();
            }
        } else {
            animacionError();
            controlador.cambiarJugador();
            actualizarUi(lanzador, false);
        }
    }

    private void actualizarUi(Jugador jugadorQueLanzo, boolean reiniciar) {
        Jugador j1 = controlador.getJugador1();
        Jugador j2 = controlador.getJugador2();
        Jugador actual = controlador.getJugadorActual();

        int valorDado = Dado.getUltimoValor();

        binding.puntosJ1.setText(String.valueOf(j1.getPuntuacion()));
        binding.puntosJ2.setText(String.valueOf(j2.getPuntuacion()));

        if(reiniciar){
            valorDado = 0;
            binding.numDadoJ1.setText(String.valueOf(valorDado));
            binding.numDadoJ2.setText(String.valueOf(valorDado));
            binding.ganadorJ1.setVisibility(View.INVISIBLE);
            binding.ganadorJ2.setVisibility(View.INVISIBLE);
        } else if (jugadorQueLanzo != null) {
            if(jugadorQueLanzo == j1){binding.numDadoJ1.setText(String.valueOf(valorDado));}
            else{binding.numDadoJ2.setText(String.valueOf(valorDado));}
        }

        binding.noJuega1.setVisibility(actual ==j1 ?View.INVISIBLE :View.VISIBLE);
        binding.noJuega2.setVisibility(actual ==j2 ?View.INVISIBLE :View.VISIBLE);
}

private void reiniciarJuego() {
    controlador.reiniciarJuego();
    binding.btnLanzar.setEnabled(true);
    binding.btnPasar.setEnabled(true);
    binding.dado.setImageResource(R.drawable.dice_one);
    actualizarUi(null, true);
}

private void mostrarGanador() {
    binding.btnLanzar.setEnabled(false);
    binding.btnPasar.setEnabled(false);
    int numeroJugador = (controlador.getJugadorActual() == controlador.getJugador1()) ? 1 : 2;
    String mensaje = getString(R.string.mensaje_ganador, numeroJugador);

    switch (numeroJugador){
        case 1 : {
            binding.ganadorJ1.setVisibility(View.VISIBLE);
            break;
        }
        case 2 : {
            binding.ganadorJ2.setVisibility(View.VISIBLE);
            break;
        }
    }

    Toast.makeText(this, mensaje, LENGTH_SHORT).show();
}

/**
 * Animación dado error
 **/
public void animacionError() {
    binding.dado.setAlpha(0f);
    binding.dado.setImageResource(R.drawable.dice_one_error);

    binding.dado.animate()
            .alpha(1f)
            .setDuration(300)
            .withEndAction(() -> binding.dado.animate()
                    .alpha(0f)
                    .setDuration(300)
                    .withEndAction(() -> {
                        binding.dado.setImageResource(R.drawable.dice_one);
                        binding.dado.setAlpha(0f);

                        binding.dado.animate()
                                .alpha(1f)
                                .setDuration(100);
                    }));
}

}