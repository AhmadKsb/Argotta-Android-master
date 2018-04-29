package com.example.ahmad.chat_3.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.ahmad.chat_3.R;
import com.example.ahmad.chat_3.models.db.Message;
import com.mzaart.aquery.AQ;

import java.util.List;

public class MessagesAdapter extends RecyclerView.Adapter {

    private static final int MESSAGE_RECEIVE = 0;
    private static final int MESSAGE_SENT = 1;
    
    private List<Message> messages;

    public MessagesAdapter(List<Message> messages) {
        this.messages = messages;
    }

    @Override
    public int getItemViewType(int position) {
        System.out.println("SenderID: " + messages.get(position).senderId);
        return messages.get(position).isFromUser() ? MESSAGE_SENT : MESSAGE_RECEIVE;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == MESSAGE_RECEIVE) {
             AQ inflated =  AQ.inflate(parent.getContext(), R.layout.message_received_item, parent);
            return new ReceiveViewHolder(inflated.raw());
        } else {
             AQ inflated =  AQ.inflate(parent.getContext(), R.layout.message_sent_item, parent);
            return new SentViewHolder(inflated.raw());
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (!messages.get(position).isFromUser())
            ((ReceiveViewHolder) holder).onBind(messages.get(position));
        else
            ((SentViewHolder) holder).onBind(messages.get(position));
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public void addItem(Message message) {
        messages.add(message);
        notifyItemInserted(messages.size() - 1);
    }

    public void appendToFront(List<Message> messages) {
        this.messages.addAll(0, messages);
        notifyDataSetChanged();
    }

    private class SentViewHolder extends RecyclerView.ViewHolder {

        private  AQ msg;

        public SentViewHolder(View itemView) {
            super(itemView);
            AQ v =  new AQ(itemView);

            msg = v.find(R.id.text_message_body);
        }

        public void onBind(Message message) {
            msg.text(message.getContent());
        }
    }

    private class ReceiveViewHolder extends RecyclerView.ViewHolder {

        private  AQ body;

        public ReceiveViewHolder(View itemView) {
            super(itemView);
             AQ v =  new AQ(itemView);
            body = v.find(R.id.text_message_body);
        }

        public void onBind(Message message) {
            body.text(message.getContent());
        }
    }
}
