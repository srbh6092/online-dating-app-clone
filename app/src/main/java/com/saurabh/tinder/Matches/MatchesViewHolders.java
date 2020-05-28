package com.saurabh.tinder.Matches;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.saurabh.tinder.Chat.ChatActivity;
import com.saurabh.tinder.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MatchesViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView mMatchID, mMatchName;
    public ImageView mMatchImage;
    public MatchesViewHolders(@NonNull View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

        mMatchID= (TextView) itemView.findViewById(R.id.matchID);
        mMatchName= (TextView) itemView.findViewById(R.id.matchName);
        mMatchImage= (ImageView) itemView.findViewById(R.id.matchImage);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(view.getContext(), ChatActivity.class);
        Bundle b = new Bundle();
        b.putString("matchID",mMatchID.getText().toString());
        intent.putExtras(b);
        view.getContext().startActivity(intent);
    }
}
