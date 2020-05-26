package com.saurabh.tinder.Matches;

import android.view.View;
import android.widget.TextView;

import com.saurabh.tinder.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MatchesViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView mMatchID;
    public MatchesViewHolders(@NonNull View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

        mMatchID= (TextView) itemView.findViewById(R.id.matchID);
    }

    @Override
    public void onClick(View view) {

    }
}
