package com.rygodc.historicalrecycler;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<HistoricEventModel> historicEvents = new ArrayList<>();
    private int puntos = 0;
    private long tiempoInicio = 0L;
    private Handler manejadorTiempo = new Handler();
    private Runnable actualizarTemporizador;
    private TextView textoTiempo;
    private TextView textoPuntos;
    private boolean juegoTerminado = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        RecyclerView historyRecyclerView = findViewById(R.id.recyclerViewHistoricEvents);
        textoPuntos = findViewById(R.id.pointsTextView);
        textoTiempo = findViewById(R.id.timerTextView);

        setHitoricEvents();
        HistoricEventRVAdapter adapter = new HistoricEventRVAdapter(this, historicEvents, new RespuestaCorrectaListener() {
            @Override
            public void respuestaCorrecta() {
                puntos += 100;
                textoPuntos.setText("Puntos: " + puntos);

                if (historicEvents.size() == 0) {
                    finalizarJuego();
                }
            }

            @Override
            public void respuestaIncorrecta() {
                puntos -= 200;
                textoPuntos.setText("Puntos: " + puntos);
            }
        });

        historyRecyclerView.setAdapter(adapter);
        historyRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        iniciarTemporizador();
    }

    private void setHitoricEvents() {
        String[] eventNames = getResources().getStringArray(R.array.historic_event_names);
        String[] eventDates = getResources().getStringArray(R.array.historic_event_dates);
        String[] eventLocations = getResources().getStringArray(R.array.historic_event_locations);
        String[] eventQuestions = getResources().getStringArray(R.array.historic_event_questions);
        String[] eventExplains = getResources().getStringArray(R.array.historic_event_explain);
        int[] eventAnswers = getResources().getIntArray(R.array.historic_event_answers);


        for (int i = 0; i < eventNames.length; i++) {
            boolean esVerdadero = (eventAnswers[i] == 1);
            historicEvents.add(new HistoricEventModel(eventNames[i], eventDates[i], eventLocations[i], eventQuestions[i], eventExplains[i], esVerdadero));
        }
    }

    private void iniciarTemporizador() {
        tiempoInicio = SystemClock.uptimeMillis();
        textoTiempo.setText(String.format("Tiempo: %02d:%02d", 0, 0));
        actualizarTemporizador = new Runnable() {
            @Override
            public void run() {
                if (!juegoTerminado) {
                    long tiempoActual = SystemClock.uptimeMillis();
                    long tiempoTranscurrido = tiempoActual - tiempoInicio;

                    int segundos = (int) (tiempoTranscurrido / 1000);
                    int minutos = segundos / 60;
                    segundos = segundos % 60;

                    textoTiempo.setText(String.format("Tiempo: %02d:%02d", minutos, segundos));
                    manejadorTiempo.postDelayed(this, 1000);
                }
            }
        };
        manejadorTiempo.post(actualizarTemporizador);
    }

    private void detenerTemporizador() {
        juegoTerminado = true;
        manejadorTiempo.removeCallbacks(actualizarTemporizador);
    }

    private void finalizarJuego() {
        juegoTerminado = true;

        long tiempoTotalMillis = SystemClock.uptimeMillis() - tiempoInicio;
        int segundos = (int) (tiempoTotalMillis / 1000);
        int minutos = segundos / 60;
        segundos = segundos % 60;

        new MaterialAlertDialogBuilder(this)
                .setTitle("Juego Terminado")
                .setMessage("¡Felicidades!\nPuntaje: " + puntos + "\nTiempo: " + String.format("%02d:%02d", minutos, segundos))
                .setPositiveButton("Volver a jugar", (dialog, which) -> reiniciarJuego())
                .setNegativeButton("Salir", (dialog, which) -> finishAffinity())
                .setCancelable(false)
                .show();
    }

    private void reiniciarJuego() {
        puntos = 0;
        textoPuntos.setText("Puntos: 0");
        textoTiempo.setText("Tiempo: 00:00");

        detenerTemporizador();
        iniciarTemporizador();

        historicEvents.clear();
        setHitoricEvents();
        RecyclerView recyclerViewEventos = findViewById(R.id.recyclerViewHistoricEvents);

        HistoricEventRVAdapter adaptador = new HistoricEventRVAdapter(this, historicEvents, new RespuestaCorrectaListener() {
            @Override
            public void respuestaCorrecta() {
                puntos += 100;
                textoPuntos.setText("Puntos: " + puntos);

                if (historicEvents.size() == 0) {
                    finalizarJuego();
                }
            }

            @Override
            public void respuestaIncorrecta() {
                puntos -= 200;
                textoPuntos.setText("Puntos: " + puntos);
            }
        });
        recyclerViewEventos.setAdapter(adaptador);
    }
}
