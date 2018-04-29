package com.example.ahmad.chat_3.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.ahmad.chat_3.R;
import com.example.ahmad.chat_3.db.dao.ChatDao;
import com.example.ahmad.chat_3.fragments.chats.ChatsListFragment;
import com.example.ahmad.chat_3.fragments.users.UsersFragment;
import com.example.ahmad.chat_3.models.db.Chat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity {

    // constants
    public static final int USERS_FRAGMENT_POS = 0;
    public static final int CHATS_FRAGMENT_POS = 1;

    private Fragment[] fragments;
    private int activeFragmentPosition;
    private static final String TAG = "MainActivity";

    private SectionsPagerAdapter sectionPageAdapter;

    private ViewPager viewPager;
    List<Chat> userList = new ArrayList<>();

    private SearchView searchView;
    TabLayout tabLayout;
    private CompositeDisposable compositeDisposable;
    private com.example.ahmad.androidroomdatabase.Database.UserRepository userRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        // intent extras
        boolean refresh = getIntent().getBooleanExtra("refresh", false);

        // setup fragments
        fragments = new Fragment[]{
                UsersFragment.newInstance(),
                ChatsListFragment.newInstance()
        };

        sectionPageAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        viewPager = (ViewPager) findViewById(R.id.container);
        viewPager.setAdapter(sectionPageAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                invalidateOptionsMenu();
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setBackgroundColor(Color.parseColor("#3f51b5"));




        String users_text = getResources().getString(R.string.Users_textView);
        String chats_text = getResources().getString(R.string.Chats_textView);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_account_circle_white_36dp);
        tabLayout.getTabAt(0).setText(users_text);

        tabLayout.getTabAt(1).setIcon(R.drawable.ic_supervisor_account_white_36dp);
        tabLayout.getTabAt(1).setText(chats_text);


    }

//    @Override
//    public boolean onContextItemSelected(MenuItem item) {
//        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
//        final Chat user =
//        final Chat user = viewPager.get(info.position);
//        switch (item.getItemId()){
//            case 0: //Update
//            {
//                final EditText edtName = new EditText(MainActivity.this);
//                edtName.setText(user.getUserName());
//                edtName.setHint("Enter your name");
//                new AlertDialog.Builder(MainActivity.this)
//                        .setTitle("Edit")
//                        .setMessage("Edit user name")
//                        .setView(edtName)
//                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                if(TextUtils.isEmpty(edtName.getText().toString()))
//                                    return;
//                                else{
//                                    user.setUserName(edtName.getText().toString());
//                                }
//                            }
//                        }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();;
//                    }
//                }).create().show();
//            }
//            break;
//            case 1: //Delete
//            {
//                new AlertDialog.Builder(MainActivity.this)
//                        .setMessage("Do you want to delete " +user.toString())
//                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                deleteUser(userID);
//                            }
//                        }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();;
//                    }
//                }).create().show();
//            }
//            break;
//        }
//        return true;
//    }
//    private void deleteUser(final Chat user) {
//        Disposable disposable = io.reactivex.Observable.create(new ObservableOnSubscribe<Object>() {
//            @Override
//            public void subscribe(ObservableEmitter<Object> e) throws Exception {
//                userRepository.deleteFromSender(user);
//                e.onComplete();
//            }
//        })
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .subscribe(new Consumer() {
//                    @Override
//                    public void accept(Object o) throws Exception {
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable throwable) throws Exception {
//                        Toast.makeText(MainActivity.this, "" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                }, new Action() {
//                    @Override
//                    public void run() throws Exception {
//                    }
//                });
//        compositeDisposable.add(disposable);
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        SearchManager searchManager = (SearchManager)
                getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchMenuItem = menu.findItem(R.id.search);
        searchView = (SearchView) searchMenuItem.getActionView();

        searchView.setSearchableInfo(searchManager.
                getSearchableInfo(getComponentName()));

        searchView.setIconifiedByDefault(false);  //to make whole toolbar CLICKABLE for search

        searchView.setSubmitButtonEnabled(false);
        searchView.setOnQueryTextListener((SearchView.OnQueryTextListener) fragments[viewPager.getCurrentItem()]);
        searchView.setOnSearchClickListener((v) -> menu.findItem(R.id.add).setVisible(false));
        searchView.setOnCloseListener(() -> {
            menu.findItem(R.id.add).setVisible(true);
            invalidateOptionsMenu();
            return false;
        });

        switch (viewPager.getCurrentItem()) {
            case 0:
                //menu.findItem(R.id.add).setVisible(true);
                return true;
            default:
                menu.findItem(R.id.add).setVisible(true);
                return true;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            default:
                return false;
        }
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // update listener
            if (searchView != null)
                searchView.setOnQueryTextListener((SearchView.OnQueryTextListener) fragments[position]);

            return fragments[position];
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
//            switch (position) {
//                case USERS_FRAGMENT_POS:
//                    return "Users";
//
//                case CHATS_FRAGMENT_POS:
//                    return "Chats";
//            }
            return null;
        }

    }

        @Override
        public void onBackPressed() {
            // show home
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
}