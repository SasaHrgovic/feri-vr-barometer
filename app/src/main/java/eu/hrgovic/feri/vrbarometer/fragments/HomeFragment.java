package eu.hrgovic.feri.vrbarometer.fragments;

import android.os.Bundle;
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
import eu.hrgovic.feri.vrbarometer.adapters.SessionFirebaseRecyclerAdapter;
import eu.hrgovic.feri.vrbarometer.models.Session;
import eu.hrgovic.feri.vrbarometer.view_holders.SessionViewHolder;
import eu.hrgovic.feri.vrbarometer.fragments.SessionCreateFragment;
import eu.hrgovic.feri.vrbarometer.fragments.SessionDetailFragment;

// 2. Implement interface
public class HomeFragment extends Fragment implements SessionFirebaseRecyclerAdapter.OnSessionListener {

    private DatabaseReference mDatabase;

    private RecyclerView mRecyclerView;
    private FirebaseRecyclerAdapter<Session, SessionViewHolder> mAdapter;
    private LinearLayoutManager mLayoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        MaterialToolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.getMenu().clear();
        toolbar.inflateMenu(R.menu.toolbar_menu_create);
        toolbar.setNavigationIcon(null);
        toolbar.setTitle("Sessions");

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.action_create) {
                    Fragment fragment = new SessionCreateFragment();
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();

                    return true;
                }

                return false;
            }
        });

        View view =  inflater.inflate(R.layout.fragment_home, container, false);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mRecyclerView = (RecyclerView)view.findViewById(R.id.sessions_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLayoutManager);

        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<Session>()
                .setQuery(mDatabase.child("sessions"), Session.class)
                .build();

        // 7. Setting the onSessionListener in the ViewHolder - pass listener to adapter
        mAdapter = new SessionFirebaseRecyclerAdapter(options, this);

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
    public void onSessionClick(int position) {
        String sessionId = mAdapter.getRef(position).getKey();

        Bundle args = new Bundle();
        args.putString("sessionId", sessionId);

        Fragment fragment = new SessionDetailFragment();
        fragment.setArguments(args);

        getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
    }
}
