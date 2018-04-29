package com.example.ahmad.chat_3.fragments.users;

import android.content.Context;
import android.content.Intent;
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
import com.example.ahmad.chat_3.activities.ChatActivity;
import com.example.ahmad.chat_3.models.requests.Account;
import com.example.ahmad.chat_3.mvp.ApiConstructor;
import com.example.ahmad.chat_3.mvp.users.UserContract;
import com.example.ahmad.chat_3.mvp.users.UserPresenter;

import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * interface.
 */
public class UsersFragment extends Fragment implements SearchView.OnQueryTextListener, UserContract.View, ListListener {

    private RecyclerView recyclerView;
    private UserContract.Presenter presenter;

    public UsersFragment() {}


    public static UsersFragment newInstance() {
        return new UsersFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account_list, container, false);

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
        presenter = new UserPresenter(new ApiConstructor(context),
                ((MyApplication) getContext().getApplicationContext()).getDb().chatDao());
        presenter.attachView(this);
        presenter.getUsers();
    }

    @Override
    public void itemClicked(Account account) {
        presenter.createChat(account);
        Intent intent = new Intent(getContext(), ChatActivity.class);
        intent.putExtra("senderId", account.userID);
        getContext().startActivity(intent);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        ((UsersRecyclerViewAdapter) recyclerView.getAdapter()).filter(newText);
        return true;
    }

    @Override
    public void displayUsers(List<Account> users) {
        recyclerView.setAdapter(new UsersRecyclerViewAdapter(users, this));
    }

    @Override
    public void showError(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }
}