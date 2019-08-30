package eu.hrgovic.feri.vrbarometer.ViewHolders;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import eu.hrgovic.feri.vrbarometer.Adapters.SessionFirebaseRecyclerAdapter;
import eu.hrgovic.feri.vrbarometer.Models.Session;
import eu.hrgovic.feri.vrbarometer.R;

// 3. Implement View.OnClickListener
public class SessionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView uidText;
    public TextView nameText;
    SessionFirebaseRecyclerAdapter.OnSessionListener onSessionListener;

    public SessionViewHolder(View itemView, SessionFirebaseRecyclerAdapter.OnSessionListener onSessionListener) {
        super(itemView);

        uidText = itemView.findViewById(R.id.text_session_id);
        nameText = itemView.findViewById(R.id.text_session_name);

        // 4. Attach the oncClickListener to the entire ViewHolder
        itemView.setOnClickListener(this);

        // 5. Set onSessionListener
        this.onSessionListener = onSessionListener;
    }

    public void bindToSession(Session session, View.OnClickListener startClickListener) {
        nameText.setText(session.getName());
        uidText.setText(session.getUid());
    }

    @Override
    public void onClick(View view) {
        // 6. Call onSessionClick method
        onSessionListener.onSessionClick(getAdapterPosition());
    }
}

