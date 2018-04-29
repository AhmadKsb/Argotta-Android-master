package com.example.ahmad.chat_3.fragments.users;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ahmad.chat_3.R;
import com.example.ahmad.chat_3.models.requests.Account;

import java.util.ArrayList;
import java.util.List;

public class UsersRecyclerViewAdapter extends RecyclerView.Adapter<UsersRecyclerViewAdapter.ViewHolder> {

    private final List<Account> mValues;
    private List<Account> selected;
    private final ListListener listListener;

    public UsersRecyclerViewAdapter(List<Account> items, ListListener listListener) {
        mValues = items;
        selected = new ArrayList<>(mValues);
        this.listListener = listListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_account, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.nameView.setText(selected.get(position).username);
        holder.mView.setOnClickListener(v -> listListener.itemClicked(selected.get(position)));
    }

    @Override
    public int getItemCount() {
        return selected.size();
    }

    public void filter(String s) {
        selected = new ArrayList<>();
        for (Account a : mValues) {
            if (a.username.contains(s)) {
                selected.add(a);
            }
        }
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView nameView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            nameView = view.findViewById(R.id.user_name);
        }

    }
}
