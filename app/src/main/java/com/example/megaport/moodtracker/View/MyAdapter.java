package com.example.megaport.moodtracker.View;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.example.megaport.moodtracker.Model.MoodData;
import com.example.megaport.moodtracker.R;
import java.util.List;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.HistoryViewHolder> {

    private List<MoodData> mdatas;

    // Constructor
    public MyAdapter(List<MoodData> datas) {
        this.mdatas = datas;
    }


    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        return new HistoryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        final MoodData item = mdatas.get(position);


        // Date
        holder.textViewTime.setText(item.getDate(mdatas.size() - position));

        // cardView color
        int colorId = mdatas.get(position).colorCard;
        int color = holder.cardView.getContext().getResources().getColor(colorId);
        holder.cardView.setCardBackgroundColor(color);

        // width cardView
        holder.cardView.getLayoutParams().width = (int) item.sizeCard;
        holder.cardView.requestLayout();

        // icons are disabled if there is no message
        if (item.message.isEmpty()) {
            holder.messageIcons.setVisibility(View.GONE);
        }


    }


    @Override
    public int getItemCount() {
        if (mdatas == null) {
            return 0;
        } else if (mdatas.size() == 8) {
            mdatas.remove(0);
        }
        return mdatas.size();
    }

    class HistoryViewHolder extends RecyclerView.ViewHolder {

        //Variable
        CardView cardView;
        TextView textViewTime;
        ImageButton messageIcons;


        HistoryViewHolder(final View itemView) {
            super(itemView);
            // findViewById ------------------------------------------------------------
            cardView = itemView.findViewById(R.id.cardview);
            textViewTime = itemView.findViewById(R.id.textview_time);
            messageIcons = itemView.findViewById(R.id.message_icons);
            // findViewById ------------------------------------------------------------


            messageIcons.setClickable(true);
            messageIcons.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int position = getAdapterPosition();
                    Toast.makeText(itemView.getContext(), mdatas.get(position).message, Toast.LENGTH_SHORT).show();

                }
            });

        }
    }
}