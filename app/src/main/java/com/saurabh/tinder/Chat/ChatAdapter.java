package com.saurabh.tinder.Chat;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.saurabh.tinder.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ChatAdapter extends RecyclerView.Adapter<ChatViewHolders> {

    private List<ChatObject> chatList;
    private Context context;

    public ChatAdapter(List<ChatObject> chatList, Context context)
    {
        this.chatList=chatList;
        this.context=context;
    }

    @NonNull
    @Override
    public ChatViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat,null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);
        ChatViewHolders rcv = new ChatViewHolders(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolders holder, int position) {
        holder.mMessage.setText(chatList.get(position).getMessage());
        if(chatList.get(position).getCurrentUser())
        {
            holder.mMessage.setGravity(Gravity.END);
            holder.mMessage.setTextColor(Color.parseColor("#905F7FCC"));
            holder.mContainer.setBackgroundResource(R.drawable.sendbox);
        }
        else
        {
            holder.mMessage.setGravity(Gravity.START);
            holder.mMessage.setTextColor(Color.parseColor("#906A1144"));
            holder.mContainer.setBackgroundColor(Color.parseColor("#2DB4CB"));
            holder.mContainer.setBackgroundResource(R.drawable.recievebox);
        }

    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }
}
