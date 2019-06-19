package com.iitbbs.qchatbot.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.github.library.bubbleview.BubbleTextView;
import com.iitbbs.qchatbot.R;
import com.iitbbs.qchatbot.model.ChatModel;

import java.util.List;

public class CustomAdapter extends BaseAdapter {
    private List<ChatModel> chatModelList;
    private Context context;
    private LayoutInflater layoutInflater;

    public CustomAdapter(List<ChatModel> chatModelList, Context context) {
        this.chatModelList = chatModelList;
        this.context = context;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return chatModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return chatModelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null) {
            if(chatModelList.get(position).isSend()) {
                view = layoutInflater.inflate(R.layout.list_item_message_send, null);
            } else {
                view  = layoutInflater.inflate(R.layout.list_item_message_recv, null);
            }

            BubbleTextView bubbleTextView = (BubbleTextView)view.findViewById(R.id.text_message);
            bubbleTextView.setText(chatModelList.get(position).getMessage());
        }
        return view;
    }
}
