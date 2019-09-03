package eu.hrgovic.feri.vrbarometer.ViewHolders;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import eu.hrgovic.feri.vrbarometer.Adapters.MeasurementFirebaseRecyclerAdapter;
import eu.hrgovic.feri.vrbarometer.Models.Measurement;
import eu.hrgovic.feri.vrbarometer.R;

// 3. Implement View.OnClickListener
public class MeasurementViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView pressureText;
    public TextView humidityText;


    MeasurementFirebaseRecyclerAdapter.OnMeasurementListener onMeasurementListener;

    public MeasurementViewHolder(View itemView, MeasurementFirebaseRecyclerAdapter.OnMeasurementListener onMeasurementListener) {
        super(itemView);

        pressureText = itemView.findViewById(R.id.text_measurement_pressure);
        humidityText = itemView.findViewById(R.id.text_measurement_humidity);

        // 4. Attach the oncClickListener to the entire ViewHolder
        itemView.setOnClickListener(this);

        // 5. Set onMeasurementListener
        this.onMeasurementListener = onMeasurementListener;
    }

    public void bindToMeasurement(Measurement measurement, View.OnClickListener startClickListener) {
        pressureText.setText(measurement.getPressure());
        humidityText.setText(measurement.getHumidity() + " %");
    }

    @Override
    public void onClick(View view) {
        // 6. Call onMeasurementClick method
        onMeasurementListener.onMeasurementClick(getAdapterPosition());
    }
}

