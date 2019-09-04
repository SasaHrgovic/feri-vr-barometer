package eu.hrgovic.feri.vrbarometer.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import eu.hrgovic.feri.vrbarometer.R;
import eu.hrgovic.feri.vrbarometer.adapters.DeviceFirebaseRecyclerAdapter;
import eu.hrgovic.feri.vrbarometer.models.Device;
import eu.hrgovic.feri.vrbarometer.view_holders.DeviceViewHolder;
// import eu.hrgovic.feri.vrbarometer.fragments.DeviceDetailFragment;

// 2. Implement interface
public class DevicesFragment extends Fragment implements DeviceFirebaseRecyclerAdapter.OnDeviceListener {

    private DatabaseReference mDatabase;

    private RecyclerView mRecyclerView;
    private FirebaseRecyclerAdapter<Device, DeviceViewHolder> mAdapter;
    private LinearLayoutManager mLayoutManager;

    private FloatingActionButton mNewDeviceButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        MaterialToolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.getMenu().clear();
        toolbar.setNavigationIcon(null);
        toolbar.setTitle("Devices");

        View view =  inflater.inflate(R.layout.fragment_devices, container, false);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mRecyclerView = (RecyclerView)view.findViewById(R.id.devices_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<Device>()
                .setQuery(mDatabase.child("devices"), Device.class)
                .build();

        // 7. Setting the onDeviceListener in the ViewHolder - pass listener to adapter
        mAdapter = new DeviceFirebaseRecyclerAdapter(options, this);

        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onStart() {
        super.onStart();

        if (mAdapter != null) {
            mAdapter.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        if (mAdapter != null) {
            mAdapter.stopListening();
        }
    }

    // 2. Implement interface method - empty
    @Override
    public void onDeviceClick(int position) {
        Log.d("ewfwefew", "onDeviceClick: " + mAdapter.getRef(position).getKey());

        //Fragment fragment = new DeviceDetailFragment();
        //getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
    }
}
