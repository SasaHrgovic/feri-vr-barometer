package eu.hrgovic.feri.vrbarometer.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import eu.hrgovic.feri.vrbarometer.models.Device;
import eu.hrgovic.feri.vrbarometer.models.Location;
import eu.hrgovic.feri.vrbarometer.models.Session;
import eu.hrgovic.feri.vrbarometer.R;

public class SessionCreateFragment extends Fragment {

    private DatabaseReference mDatabase;

    private TextInputEditText mNameField;
    private TextInputEditText mIntervalField;
    private AutoCompleteTextView dropdownDevices;
    private AutoCompleteTextView dropdownLocations;

    private String mLocationUid;
    private String mDeviceUid;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_session_create, container, false);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        setToolbar();

        loadDevices();
        loadLocations();

        mNameField = (TextInputEditText)view.findViewById(R.id.input_session_name);
        mIntervalField = (TextInputEditText)view.findViewById(R.id.input_measuring_interval);
        dropdownDevices = view.findViewById(R.id.dropdown_session_device);
        dropdownLocations = view.findViewById(R.id.dropdown_session_location);

        dropdownDevices.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Device device = (Device)adapterView.getAdapter().getItem(i);
                mDeviceUid = device.getUid();
            }
        });

        dropdownLocations.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Location location = (Location) adapterView.getAdapter().getItem(i);
                mLocationUid = location.getUid();
                Log.d("oi", "onItemSelected: feopfwepofme" + mLocationUid);
            }
        });

        return view;
    }

    private void loadDevices() {
        mDatabase.child("devices").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                ArrayList<Device> devices = new ArrayList<>();

                for (DataSnapshot deviceSnapshot: dataSnapshot.getChildren()) {
                    Device device = new Device();
                    device.setUid(deviceSnapshot.getKey());
                    device.setName(deviceSnapshot.child("name").getValue(String.class));

                    devices.add(device);
                }

                ArrayAdapter<Device> devicesArrayAdapter = new ArrayAdapter<>(getActivity(), R.layout.dropdown_locations_item, devices);

                dropdownDevices.setAdapter(devicesArrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    private void loadLocations() {
        mDatabase.child("locations").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                ArrayList<Location> locations = new ArrayList<>();

                for (DataSnapshot locationSnapshot: dataSnapshot.getChildren()) {
                    Location location = new Location();
                    location.setUid(locationSnapshot.getKey());
                    location.setName(locationSnapshot.child("name").getValue(String.class));

                    locations.add(location);
                }

                ArrayAdapter<Location> locationsArrayAdapter = new ArrayAdapter<>(getActivity(), R.layout.dropdown_locations_item, locations);

                dropdownLocations.setAdapter(locationsArrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    private void setToolbar() {
        MaterialToolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.getMenu().clear();
        toolbar.setTitle("Create session");
        toolbar.inflateMenu(R.menu.toolbar_menu_save);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.action_save) {
                    saveToFirebase();
                    return true;
                }

                return false;
            }
        });
    }

    public void saveToFirebase() {
        int interval = Integer.parseInt(mIntervalField.getText().toString()) * 1000;

        Session session = new Session();
        session.setName(mNameField.getText().toString());
        session.setInterval(interval);
        session.setDeviceUid(mDeviceUid);
        session.setLocationUid(mLocationUid);

        String sessionKey = mDatabase.child("sessions").push().getKey();
        mDatabase.child("sessions").child(sessionKey).setValue(session);

        mDatabase.child("devices").child(mDeviceUid).child("lastSessionUid").setValue(sessionKey);
        mDatabase.child("devices").child(mDeviceUid).child("isMeasuring").setValue(true);
        mDatabase.child("devices").child(mDeviceUid).child("interval").setValue(interval);

        getActivity().onBackPressed();
    }
}
