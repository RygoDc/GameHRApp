package com.rygodc.historicalrecycler;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<HistoricEventModel> historicEvents = new ArrayList<>();

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

        RecyclerView historyRecyclerView = findViewById(R.id.recyclerViewHistoricEvents);
        setHitoricEvents();
        HistoricEventRVAdapter adapter = new HistoricEventRVAdapter(this, historicEvents);
        historyRecyclerView.setAdapter(adapter);
        historyRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setHitoricEvents(){
        String [] eventNames = getResources().getStringArray(R.array.historic_event_names);
        String [] eventDates = getResources().getStringArray(R.array.historic_event_dates);
        String [] eventLocations = getResources().getStringArray(R.array.historic_event_locations);

        for (int i = 0; i < eventNames.length; i++) {
            historicEvents.add(new HistoricEventModel(eventNames[i], eventDates[i], eventLocations[i]));
        }
    }

}