package com.saurabh.tinder.Chat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.saurabh.tinder.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ChatViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {

    public LinearLayout mContainer;
    public TextView mMessage;

    public ChatViewHolders(@NonNull View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

        mContainer = itemView.findViewById(R.id.container);
        mMessage = itemView.findViewById(R.id.message);

    }

    @Override
    public void onClick(View view) {
    }
}
