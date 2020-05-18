package com.saurabh.tinder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class arrayAdapter extends ArrayAdapter<cards>
{

    Context context;

    TextView name;
    ImageView image;

    cards card_item;

    public arrayAdapter(Context context, int resourceId, List<cards> items)
    {
        super(context,resourceId,items);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        card_item=getItem(position);

        if (convertView==null)
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.item,parent,false);

        name = (TextView)convertView.findViewById(R.id.name);
        image = (ImageView)convertView.findViewById(R.id.image);

        name.setText(card_item.getName());
        image.setImageResource(R.mipmap.ic_launcher);

        return convertView;
    }
}
