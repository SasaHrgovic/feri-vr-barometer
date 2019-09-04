package eu.hrgovic.feri.vrbarometer.view_holders;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import eu.hrgovic.feri.vrbarometer.adapters.DeviceFirebaseRecyclerAdapter;
import eu.hrgovic.feri.vrbarometer.models.Device;
import eu.hrgovic.feri.vrbarometer.R;

// 3. Implement View.OnClickListener
public class DeviceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView nameText;
    public TextView macText;

    DeviceFirebaseRecyclerAdapter.OnDeviceListener onDeviceListener;

    public DeviceViewHolder(View itemView, DeviceFirebaseRecyclerAdapter.OnDeviceListener onDeviceListener) {
        super(itemView);

        //uidText = itemView.findViewById(R.id.text_device_id);
        nameText = itemView.findViewById(R.id.text_device_name);
        macText = itemView.findViewById(R.id.text_device_mac);

        // 4. Attach the oncClickListener to the entire ViewHolder
        itemView.setOnClickListener(this);

        // 5. Set onDeviceListener
        this.onDeviceListener = onDeviceListener;
    }

    public void bindToDevice(Device device, View.OnClickListener startClickListener) {
        nameText.setText(device.getName());
        macText.setText(device.getMac());
        //uidText.setText(device.getUid());
    }

    @Override
    public void onClick(View view) {
        // 6. Call onDeviceClick method
        onDeviceListener.onDeviceClick(getAdapterPosition());
    }
}

