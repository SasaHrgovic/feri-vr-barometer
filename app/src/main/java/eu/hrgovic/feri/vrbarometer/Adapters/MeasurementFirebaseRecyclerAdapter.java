package eu.hrgovic.feri.vrbarometer.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import eu.hrgovic.feri.vrbarometer.Models.Measurement;
import eu.hrgovic.feri.vrbarometer.R;
import eu.hrgovic.feri.vrbarometer.ViewHolders.MeasurementViewHolder;

public class MeasurementFirebaseRecyclerAdapter extends FirebaseRecyclerAdapter<Measurement, MeasurementViewHolder> {

    // 7. Setting the onMeasurementListener in the ViewHolder
    private OnMeasurementListener mOnMeasurementListener;

    // 7. Setting the onMeasurementListener in the ViewHolder - add second constructor parameter
    public MeasurementFirebaseRecyclerAdapter(FirebaseRecyclerOptions options, OnMeasurementListener onMeasurementListener) {
        super(options);

        this.mOnMeasurementListener = onMeasurementListener;
    }

    @Override
    protected void onBindViewHolder(@NonNull MeasurementViewHolder measurementViewHolder, int i, @NonNull Measurement measurement) {
        measurementViewHolder.pressureText.setText(measurement.getPressure() + " Pa");
        measurementViewHolder.humidityText.setText(measurement.getHumidity() + "%");
    }

    @NonNull
    @Override
    public MeasurementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.measurement_item, parent, false);

        // 7. Setting the onMeasurementListener in the ViewHolder - pass listener to ViewHolder
        return new MeasurementViewHolder(view, mOnMeasurementListener);
    }

    // Watch this: https://www.youtube.com/watch?v=69C1ljfDvl0
    // 1. Define listener interface
    public interface OnMeasurementListener {
        void onMeasurementClick(int position);
    }
}
