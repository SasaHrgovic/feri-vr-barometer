package eu.hrgovic.feri.vrbarometer.fragments;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import eu.hrgovic.feri.vrbarometer.R;
import eu.hrgovic.feri.vrbarometer.adapters.LocationFirebaseRecyclerAdapter;
import eu.hrgovic.feri.vrbarometer.models.Location;
import eu.hrgovic.feri.vrbarometer.view_holders.LocationViewHolder;
import eu.hrgovic.feri.vrbarometer.fragments.LocationCreateFragment;
//import eu.hrgovic.feri.vrbarometer.fragments.LocationDetailFragment;

// 2. Implement interface
public class LocationsFragment extends Fragment implements LocationFirebaseRecyclerAdapter.OnLocationListener {

    private DatabaseReference mDatabase;

    private RecyclerView mRecyclerView;
    private FirebaseRecyclerAdapter<Location, LocationViewHolder> mAdapter;
    private LinearLayoutManager mLayoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        MaterialToolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.getMenu().clear();
        toolbar.inflateMenu(R.menu.toolbar_menu_create);
        toolbar.setNavigationIcon(null);
        toolbar.setTitle("Locations");

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.action_create) {
                    Fragment fragment = new LocationCreateFragment();
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();

                    return true;
                }

                return false;
            }
        });

        View view =  inflater.inflate(R.layout.fragment_locations, container, false);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mRecyclerView = (RecyclerView)view.findViewById(R.id.locations_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<Location>()
                .setQuery(mDatabase.child("locations"), Location.class)
                .build();

        // 7. Setting the onLocationListener in the ViewHolder - pass listener to adapter
        mAdapter = new LocationFirebaseRecyclerAdapter(options, this);

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
    public void onLocationClick(int position) {
        Log.d("ewfwefew", "onLocationClick: " + mAdapter.getRef(position).getKey());

        //Fragment fragment = new LocationDetailFragment();
        //getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
    }
}
