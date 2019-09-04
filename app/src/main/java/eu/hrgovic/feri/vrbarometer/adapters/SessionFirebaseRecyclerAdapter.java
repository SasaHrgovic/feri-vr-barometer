package eu.hrgovic.feri.vrbarometer.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.Random;

import eu.hrgovic.feri.vrbarometer.models.Session;
import eu.hrgovic.feri.vrbarometer.R;
import eu.hrgovic.feri.vrbarometer.view_holders.SessionViewHolder;

public class SessionFirebaseRecyclerAdapter extends FirebaseRecyclerAdapter<Session, SessionViewHolder> {

    // 7. Setting the onSessionListener in the ViewHolder
    private OnSessionListener mOnSessionListener;

    // 7. Setting the onSessionListener in the ViewHolder - add second constructor parameter
    public SessionFirebaseRecyclerAdapter(FirebaseRecyclerOptions options, OnSessionListener onSessionListener) {
        super(options);

        this.mOnSessionListener = onSessionListener;
    }

    @Override
    protected void onBindViewHolder(@NonNull SessionViewHolder sessionViewHolder, int i, @NonNull Session session) {
        int[] emojis = { R.drawable.a1, R.drawable.a2, R.drawable.a3, R.drawable.a4, R.drawable.a5 };

        Random rand = new Random();
        int min = 0;
        int max = 4;

        sessionViewHolder.nameText.setText(session.getName());
        sessionViewHolder.detailsText.setText("Every " + session.getInterval()/1000 + " seconds");
        sessionViewHolder.emojiImage.setImageResource(emojis[rand.nextInt((max - min) + 1) + min]);
    }

    @NonNull
    @Override
    public SessionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.session_item, parent, false);

        // 7. Setting the onSessionListener in the ViewHolder - pass listener to ViewHolder
        return new SessionViewHolder(view, mOnSessionListener);
    }

    // Watch this: https://www.youtube.com/watch?v=69C1ljfDvl0
    // 1. Define listener interface
    public interface OnSessionListener {
        void onSessionClick(int position);
    }
}
