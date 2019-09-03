package eu.hrgovic.feri.vrbarometer.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import eu.hrgovic.feri.vrbarometer.LoginActivity;
import eu.hrgovic.feri.vrbarometer.MainActivity;
import eu.hrgovic.feri.vrbarometer.Models.Location;
import eu.hrgovic.feri.vrbarometer.R;

public class LocationCreateFragment extends Fragment {

    private TextInputEditText mNameField;
    private TextInputEditText mAddressField;
    private TextInputEditText mCityField;

    private DatabaseReference mDatabase;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location_create, container, false);
        setToolbar();

        mNameField = (TextInputEditText)view.findViewById(R.id.input_location_name);
        mAddressField = (TextInputEditText)view.findViewById(R.id.input_location_address);
        mCityField = (TextInputEditText)view.findViewById(R.id.input_location_city);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        return view;
    }

    public void setToolbar() {
        MaterialToolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.getMenu().clear();
        toolbar.setTitle("Add location");
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
        Location location = new Location(mNameField.getText().toString(), mAddressField.getText().toString(), mCityField.getText().toString());

        mDatabase.child("locations").push().setValue(location);

        getActivity().onBackPressed();
    }
}
