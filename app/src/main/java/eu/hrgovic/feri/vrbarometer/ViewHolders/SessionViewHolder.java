package eu.hrgovic.feri.vrbarometer.ViewHolders;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import eu.hrgovic.feri.vrbarometer.Adapters.SessionFirebaseRecyclerAdapter;
import eu.hrgovic.feri.vrbarometer.Models.Session;
import eu.hrgovic.feri.vrbarometer.R;

// 3. Implement View.OnClickListener
public class SessionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView nameText;
    public TextView detailsText;


    SessionFirebaseRecyclerAdapter.OnSessionListener onSessionListener;

    public SessionViewHolder(View itemView, SessionFirebaseRecyclerAdapter.OnSessionListener onSessionListener) {
        super(itemView);

        nameText = itemView.findViewById(R.id.text_session_name);
        detailsText = itemView.findViewById(R.id.text_session_details);

        // 4. Attach the oncClickListener to the entire ViewHolder
        itemView.setOnClickListener(this);

        // 5. Set onSessionListener
        this.onSessionListener = onSessionListener;
    }

    public void bindToSession(Session session, View.OnClickListener startClickListener) {
        nameText.setText(session.getName());
        detailsText.setText("Every + " + session.getInterval() + " seconds");
    }

    @Override
    public void onClick(View view) {
        // 6. Call onSessionClick method
        onSessionListener.onSessionClick(getAdapterPosition());
    }
}

