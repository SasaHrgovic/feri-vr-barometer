package eu.hrgovic.feri.vrbarometer.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import eu.hrgovic.feri.vrbarometer.Models.Location;
import eu.hrgovic.feri.vrbarometer.R;
import eu.hrgovic.feri.vrbarometer.ViewHolders.LocationViewHolder;

public class LocationFirebaseRecyclerAdapter extends FirebaseRecyclerAdapter<Location, LocationViewHolder> {

    // 7. Setting the onLocationListener in the ViewHolder
    private OnLocationListener mOnLocationListener;

    // 7. Setting the onLocationListener in the ViewHolder - add second constructor parameter
    public LocationFirebaseRecyclerAdapter(FirebaseRecyclerOptions options, OnLocationListener onLocationListener) {
        super(options);

        this.mOnLocationListener = onLocationListener;
    }

    @Override
    protected void onBindViewHolder(@NonNull LocationViewHolder locationViewHolder, int i, @NonNull Location location) {
        locationViewHolder.nameText.setText(location.getName());
        locationViewHolder.addressText.setText(location.getAddress() + ", " + location.getCity());
    }

    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.location_item, parent, false);

        // 7. Setting the onLocationListener in the ViewHolder - pass listener to ViewHolder
        return new LocationViewHolder(view, mOnLocationListener);
    }

    // Watch this: https://www.youtube.com/watch?v=69C1ljfDvl0
    // 1. Define listener interface
    public interface OnLocationListener {
        void onLocationClick(int position);
    }
}
