package eu.hrgovic.feri.vrbarometer.fragments;

import android.content.DialogInterface;
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
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import eu.hrgovic.feri.vrbarometer.Adapters.MeasurementFirebaseRecyclerAdapter;
import eu.hrgovic.feri.vrbarometer.Models.Device;
import eu.hrgovic.feri.vrbarometer.Models.Measurement;
import eu.hrgovic.feri.vrbarometer.Models.Session;
import eu.hrgovic.feri.vrbarometer.R;
import eu.hrgovic.feri.vrbarometer.ViewHolders.MeasurementViewHolder;
//import eu.hrgovic.feri.vrbarometer.fragments.MeasurementDetailFragment;

// 2. Implement interface
public class SessionDetailFragment extends Fragment implements MeasurementFirebaseRecyclerAdapter.OnMeasurementListener {

    private DatabaseReference mDatabase;

    private RecyclerView mRecyclerView;
    private FirebaseRecyclerAdapter<Measurement, MeasurementViewHolder> mAdapter;
    private LinearLayoutManager mLayoutManager;
    private MaterialToolbar mToolbar;

    private String mSessionId;
    private Session mSession;
    private Device mDevice;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view =  inflater.inflate(R.layout.fragment_session_details, container, false);
        setToolbar();

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mSessionId = getArguments().getString("sessionId");
        getSessionDetails();


        mRecyclerView = (RecyclerView)view.findViewById(R.id.measurements_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        return view;
    }

    private void getSessionDetails() {
        mDatabase.child("sessions").child(mSessionId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mSession = dataSnapshot.getValue(Session.class);

                if (mSession != null ) {
                    getDeviceDetails();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    private void getDeviceDetails() {
        mDatabase.child("devices").child(mSession.getDeviceUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mDevice = dataSnapshot.getValue(Device.class);

                if (mDevice != null && mDevice.getLastSessionUid().equals(mSessionId) && mDevice.getIsMeasuring()) {
                    mToolbar.getMenu().findItem(R.id.action_stop).setVisible(true);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    private void confirmStopMeasuring() {
        new MaterialAlertDialogBuilder(getActivity(), R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog_Centered)
                .setTitle("Stop measuring?")
                .setMessage("Are you sure you want to stop measuring?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mDatabase.child("devices").child(mSession.getDeviceUid()).child("isMeasuring").setValue(false);
                        mToolbar.getMenu().findItem(R.id.action_stop).setVisible(false);
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void confirmDeleteMeasurement() {
        new MaterialAlertDialogBuilder(getActivity(), R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog_Centered)
                .setTitle("Delete session?")
                .setMessage("Are you sure you want to delete the session?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mDatabase.child("sessions").child(mSessionId).removeValue();
                        // https://stackoverflow.com/questions/45566408/good-way-to-delete-all-data-according-to-criteria-childs-value-in-firebase-data
                        getActivity().onBackPressed();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void setToolbar() {
        mToolbar = getActivity().findViewById(R.id.toolbar);
        mToolbar.getMenu().clear();
        mToolbar.setTitle("Session details");
        mToolbar.inflateMenu(R.menu.toolbar_session_detail);

        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.action_stop) {
                    confirmStopMeasuring();
                    return true;
                } else if (id == R.id.action_delete) {
                    confirmDeleteMeasurement();
                }

                return false;
            }
        });
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLayoutManager);

        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<Measurement>()
                .setQuery(mDatabase.child("measurements").orderByChild("sessionUid").equalTo(mSessionId), Measurement.class)
                .build();

        // 7. Setting the onMeasurementListener in the ViewHolder - pass listener to adapter
        mAdapter = new MeasurementFirebaseRecyclerAdapter(options, this);

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
    public void onMeasurementClick(int position) {
        Log.d("ewfwefew", "onMeasurementClick: " + mAdapter.getRef(position).getKey());

        //Fragment fragment = new MeasurementDetailFragment();
        //getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
    }
}
