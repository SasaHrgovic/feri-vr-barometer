package eu.hrgovic.feri.vrbarometer.ViewHolders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.Random;

import eu.hrgovic.feri.vrbarometer.Adapters.SessionFirebaseRecyclerAdapter;
import eu.hrgovic.feri.vrbarometer.Models.Session;
import eu.hrgovic.feri.vrbarometer.R;

// 3. Implement View.OnClickListener
public class SessionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView nameText;
    public TextView detailsText;
    public ImageView emojiImage;


    SessionFirebaseRecyclerAdapter.OnSessionListener onSessionListener;

    public SessionViewHolder(View itemView, SessionFirebaseRecyclerAdapter.OnSessionListener onSessionListener) {
        super(itemView);

        nameText = itemView.findViewById(R.id.text_session_name);
        detailsText = itemView.findViewById(R.id.text_session_details);
        emojiImage = itemView.findViewById(R.id.image_emoji);

        // 4. Attach the oncClickListener to the entire ViewHolder
        itemView.setOnClickListener(this);

        // 5. Set onSessionListener
        this.onSessionListener = onSessionListener;
    }

    public void bindToSession(Session session, View.OnClickListener startClickListener) {
        int[] emojis = { R.drawable.a1, R.drawable.a2, R.drawable.a3, R.drawable.a4, R.drawable.a5 };

        Random rand = new Random();
        int min = 0;
        int max = 4;

        nameText.setText(session.getName());
        detailsText.setText("Every + " + session.getInterval()/1000 + " seconds");
        emojiImage.setImageResource(emojis[rand.nextInt((max - min) + 1) + min]);
    }

    @Override
    public void onClick(View view) {
        // 6. Call onSessionClick method
        onSessionListener.onSessionClick(getAdapterPosition());
    }
}

