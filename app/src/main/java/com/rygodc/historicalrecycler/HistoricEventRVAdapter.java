package com.rygodc.historicalrecycler;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class HistoricEventRVAdapter extends RecyclerView.Adapter<HistoricEventRVAdapter.MyViewHolder> {

    Context context;
    ArrayList<HistoricEventModel> historicEventsModel;
    RespuestaCorrectaListener respuestaCorrectaListener;

    public HistoricEventRVAdapter(Context context, ArrayList<HistoricEventModel> historicEventsModel, RespuestaCorrectaListener respuestaCorrectaListener) {
        this.context = context;
        this.historicEventsModel = historicEventsModel;
        this.respuestaCorrectaListener = respuestaCorrectaListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.cv_row, parent, false);
        return new HistoricEventRVAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoricEventRVAdapter.MyViewHolder holder, int position) {
        String eventNames = historicEventsModel.get(position).getEventName();
        String eventDates = historicEventsModel.get(position).getEventDate();
        String eventLocations = historicEventsModel.get(position).getEventLocation();
        String eventQuestions = historicEventsModel.get(position).getEventQuestion();
        String eventExplain = historicEventsModel.get(position).getEventExplain();
        boolean esVerdadero = historicEventsModel.get(position).esVerdadero();

        holder.tvEventName.setText(eventNames);
        holder.tvEventDate.setText(eventDates);
        holder.tvEventLocation.setText(eventLocations);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(context)
                        .setIcon(R.drawable.book_logo)
                        .setTitle(eventNames)
                        .setMessage(" " + eventQuestions + ". ¿Es correcto?")
                        .setPositiveButton("Verdadero", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (esVerdadero) {
                                    Snackbar snackbar = Snackbar.make(view, eventExplain, Snackbar.LENGTH_LONG);
                                    View snackbarView = snackbar.getView();
                                    TextView textView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
                                    textView.setMaxLines(Integer.MAX_VALUE);
                                    textView.setEllipsize(null);
                                    snackbar.show();
                                    historicEventsModel.remove(position);
                                    notifyItemRemoved(position);
                                    notifyItemRangeChanged(position, historicEventsModel.size());

                                    if (respuestaCorrectaListener != null) {
                                        respuestaCorrectaListener.respuestaCorrecta();
                                    }
                                } else {
                                    respuestaCorrectaListener.respuestaIncorrecta();
                                }
                            }
                        })
                        .setNegativeButton("Falso", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (!esVerdadero) {
                                    Snackbar snackbar = Snackbar.make(view, eventExplain, Snackbar.LENGTH_LONG);
                                    View snackbarView = snackbar.getView();
                                    TextView textView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
                                    textView.setMaxLines(Integer.MAX_VALUE);
                                    textView.setEllipsize(null);
                                    snackbar.show();
                                    historicEventsModel.remove(position);
                                    notifyItemRemoved(position);
                                    notifyItemRangeChanged(position, historicEventsModel.size());

                                    if (respuestaCorrectaListener != null) {
                                        respuestaCorrectaListener.respuestaCorrecta();
                                    }
                                } else {
                                    respuestaCorrectaListener.respuestaIncorrecta();
                                }
                            }
                        });
                materialAlertDialogBuilder.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return historicEventsModel.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvEventName, tvEventDate, tvEventLocation;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvEventName = itemView.findViewById(R.id.eventName);
            tvEventDate = itemView.findViewById(R.id.eventDate);
            tvEventLocation = itemView.findViewById(R.id.eventLocation);
        }
    }
}
