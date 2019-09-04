package eu.hrgovic.feri.vrbarometer.ViewHolders;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;

import eu.hrgovic.feri.vrbarometer.Adapters.MeasurementFirebaseRecyclerAdapter;
import eu.hrgovic.feri.vrbarometer.Models.Measurement;
import eu.hrgovic.feri.vrbarometer.R;

// 3. Implement View.OnClickListener
public class MeasurementViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView pressureText;
    public TextView humidityText;
    public TextView temperatureText;
    public TextView timeText;


    MeasurementFirebaseRecyclerAdapter.OnMeasurementListener onMeasurementListener;

    public MeasurementViewHolder(View itemView, MeasurementFirebaseRecyclerAdapter.OnMeasurementListener onMeasurementListener) {
        super(itemView);

        pressureText = itemView.findViewById(R.id.text_measurement_pressure);
        humidityText = itemView.findViewById(R.id.text_measurement_humidity);
        temperatureText = itemView.findViewById(R.id.text_measurement_temperature);
        timeText = itemView.findViewById(R.id.text_measurement_time);

        // 4. Attach the oncClickListener to the entire ViewHolder
        itemView.setOnClickListener(this);

        // 5. Set onMeasurementListener
        this.onMeasurementListener = onMeasurementListener;
    }

    public void bindToMeasurement(Measurement measurement, View.OnClickListener startClickListener) {
        Date date = new Date(measurement.getTimestamp()*1000);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

        String timestamp = dateFormat.format(date) + " at " + timeFormat.format(date);
        pressureText.setText(measurement.getPressure());
        humidityText.setText(measurement.getHumidity() + " %");
        temperatureText.setText(measurement.getTemperature() + " ℃");
        timeText.setText(timestamp);
    }

    @Override
    public void onClick(View view) {
        // 6. Call onMeasurementClick method
        onMeasurementListener.onMeasurementClick(getAdapterPosition());
    }
}

