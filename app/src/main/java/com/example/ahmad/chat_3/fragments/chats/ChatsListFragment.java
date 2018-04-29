package com.example.ahmad.chat_3.fragments.chats;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ahmad.chat_3.MyApplication;
import com.example.ahmad.chat_3.R;
import com.example.ahmad.chat_3.db.Db;
import com.example.ahmad.chat_3.fragments.users.UsersRecyclerViewAdapter;
import com.example.ahmad.chat_3.models.db.Chat;
import com.example.ahmad.chat_3.mvp.chatsList.ChatListPresenter;
import com.example.ahmad.chat_3.mvp.chatsList.ChatsListContract;

import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * interface.
 */
public class ChatsListFragment extends Fragment implements ChatsListContract.View, SearchView.OnQueryTextListener {

    private RecyclerView recyclerView;
    private ChatsListContract.Presenter presenter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ChatsListFragment() {
    }


    public static ChatsListFragment newInstance() {
        return new ChatsListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Db db = ((MyApplication) getContext().getApplicationContext()).getDb();
        presenter = new ChatListPresenter(db.chatDao());
        presenter.attachView(this);
        presenter.getChats();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void displayChats(List<Chat> chats) {
        recyclerView.setAdapter(new ChatsListRecyclerViewAdapter(chats));
    }

    @Override
    public void displayError(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        ((ChatsListRecyclerViewAdapter) recyclerView.getAdapter()).filter(newText);
        return true;
    }
}
