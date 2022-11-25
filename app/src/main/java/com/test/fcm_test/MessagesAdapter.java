package com.test.fcm_test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MessagesAdapter extends BaseAdapter {

    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    ArrayList<MessageItem> messageItems;

    public MessagesAdapter(Context context, ArrayList<MessageItem> data) {
        mContext = context;
        messageItems = data;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return messageItems.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public MessageItem getItem(int position) {
        return messageItems.get(position);
    }
    @Override
    public View getView(int position, View converView, ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.messages_item, null);

        ImageView icon = (ImageView)view.findViewById(R.id.icon);
        TextView title = (TextView)view.findViewById(R.id.title);
        TextView body = (TextView)view.findViewById(R.id.body);

        title.setText(messageItems.get(position).getTitle());
        body.setText(messageItems.get(position).getBody());

        return view;
    }

}
