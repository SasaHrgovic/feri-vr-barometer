package eu.hrgovic.feri.vrbarometer.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import eu.hrgovic.feri.vrbarometer.Models.Device;
import eu.hrgovic.feri.vrbarometer.R;
import eu.hrgovic.feri.vrbarometer.ViewHolders.DeviceViewHolder;

public class DeviceFirebaseRecyclerAdapter extends FirebaseRecyclerAdapter<Device, DeviceViewHolder> {

    // 7. Setting the onDeviceListener in the ViewHolder
    private OnDeviceListener mOnDeviceListener;

    // 7. Setting the onDeviceListener in the ViewHolder - add second constructor parameter
    public DeviceFirebaseRecyclerAdapter(FirebaseRecyclerOptions options, OnDeviceListener onDeviceListener) {
        super(options);

        this.mOnDeviceListener = onDeviceListener;
    }

    @Override
    protected void onBindViewHolder(@NonNull DeviceViewHolder deviceViewHolder, int i, @NonNull Device device) {
        deviceViewHolder.nameText.setText(device.getName());
        deviceViewHolder.macText.setText("MAC: " + device.getMac());
        //deviceViewHolder.uidText.setText(this.getRef(i).getKey());
    }

    @NonNull
    @Override
    public DeviceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.device_item, parent, false);

        // 7. Setting the onDeviceListener in the ViewHolder - pass listener to ViewHolder
        return new DeviceViewHolder(view, mOnDeviceListener);
    }

    // Watch this: https://www.youtube.com/watch?v=69C1ljfDvl0
    // 1. Define listener interface
    public interface OnDeviceListener {
        void onDeviceClick(int position);
    }
}
