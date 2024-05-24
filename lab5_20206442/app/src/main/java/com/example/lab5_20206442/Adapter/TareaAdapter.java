package com.example.lab5_20206442.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab5_20206442.Entity.TareaData;
import com.example.lab5_20206442.R;

import java.util.List;

public class TareaAdapter extends RecyclerView.Adapter<TareaAdapter.ViewHolder> {

    private List<TareaData> tareaDataList;

    public TareaAdapter(List<TareaData> tareaDataList) {
        this.tareaDataList = tareaDataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TareaData tareaData = tareaDataList.get(position);
        holder.activityTitulo.setText(tareaData.getTitulo());
        holder.activityfechaVencimiento.setText(tareaData.getFechaVencimiento());
        holder.activityDescripcion.setText(tareaData.getDescripcion());
    }

    @Override
    public int getItemCount() {
        return tareaDataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView activityTitulo;
        TextView activityfechaVencimiento;
        TextView activityDescripcion;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            activityTitulo = itemView.findViewById(R.id.titulo);
            activityfechaVencimiento = itemView.findViewById(R.id.fechaVencimiento);
            activityDescripcion = itemView.findViewById(R.id.descripcion);
        }
    }

}
