package com.example.ahmad.chat_3.fragments.chats;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ahmad.chat_3.R;
import com.example.ahmad.chat_3.activities.ChatActivity;
import com.example.ahmad.chat_3.fragments.users.UsersFragment;
import com.example.ahmad.chat_3.models.db.Chat;
import com.example.ahmad.chat_3.models.requests.Account;

import java.util.ArrayList;
import java.util.List;


public class ChatsListRecyclerViewAdapter extends RecyclerView.Adapter<ChatsListRecyclerViewAdapter.ViewHolder> {

    private final List<Chat> mValues;
    private List<Chat> selected;

    public ChatsListRecyclerViewAdapter(List<Chat> items) {
        mValues = items;
        selected = new ArrayList<>(items);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_chat, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.nameView.setText(mValues.get(position).getUserName());
        System.out.println("REC: " + mValues.get(position).getUserName());

        holder.mView.setOnClickListener(v -> {
            Intent intent = new Intent(holder.mView.getContext(), ChatActivity.class);
            intent.putExtra("senderId", mValues.get(position).getSenderId());
            holder.mView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void filter(String s) {
        selected = new ArrayList<>();
        for (Chat c : mValues) {
            if (c.getUserName().contains(s)) {
                selected.add(c);
            }
        }
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView nameView;
        public final TextView langView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            nameView = (TextView) view.findViewById(R.id.user_name);
            langView = (TextView) view.findViewById(R.id.lang);
        }
    }
}
