package eu.hrgovic.feri.vrbarometer.ViewHolders;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import eu.hrgovic.feri.vrbarometer.Adapters.LocationFirebaseRecyclerAdapter;
import eu.hrgovic.feri.vrbarometer.Models.Location;
import eu.hrgovic.feri.vrbarometer.R;

// 3. Implement View.OnClickListener
public class LocationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView nameText;
    public TextView addressText;

    LocationFirebaseRecyclerAdapter.OnLocationListener onLocationListener;

    public LocationViewHolder(View itemView, LocationFirebaseRecyclerAdapter.OnLocationListener onLocationListener) {
        super(itemView);

        nameText = itemView.findViewById(R.id.text_location_name);
        addressText = itemView.findViewById(R.id.text_location_address);

        // 4. Attach the oncClickListener to the entire ViewHolder
        itemView.setOnClickListener(this);

        // 5. Set onLocationListener
        this.onLocationListener = onLocationListener;
    }

    public void bindToLocation(Location location, View.OnClickListener startClickListener) {
        nameText.setText(location.getName());
        addressText.setText(location.getAddress() + ", " + location.getCity());
    }

    @Override
    public void onClick(View view) {
        // 6. Call onLocationClick method
        onLocationListener.onLocationClick(getAdapterPosition());
    }
}

