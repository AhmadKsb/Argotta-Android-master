package com.example.ahmad.chat_3.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ahmad.chat_3.MyApplication;
import com.example.ahmad.chat_3.R;
import com.example.ahmad.chat_3.adapters.MessagesAdapter;
import com.example.ahmad.chat_3.api.ChatApi;
import com.example.ahmad.chat_3.db.dao.MessageDao;
import com.example.ahmad.chat_3.generalUtils.NotificationUtils;
import com.example.ahmad.chat_3.models.db.Chat;
import com.example.ahmad.chat_3.models.db.Message;
import com.example.ahmad.chat_3.mvp.ApiConstructor;
import com.example.ahmad.chat_3.mvp.chat.ChatContract;
import com.example.ahmad.chat_3.mvp.chat.ChatPresenter;
import com.google.gson.Gson;
import com.mzaart.aquery.AQ;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ChatActivity extends BaseActivity implements ChatContract.View {

    private ChatContract.Presenter presenter;
    private RecyclerView recyclerView;
    private String senderId;


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.chat_message_context_menu,menu);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Toolbar toolbar = (Toolbar) findViewById(R.id.chattoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });
//        Button btnCamera = (Button)findViewById(R.id.btnCamera);
//        imageView = (ImageView)findViewById(R.id.imageVieww);
//
//        btnCamera.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(intent,0);
//            }
//        });
        MessageReceiver messageReceiver = new MessageReceiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(messageReceiver,
                new IntentFilter("messageReceived"));


        new AQ(this).ready(() -> {
            {
                recyclerView = findViewById(R.id.reyclerview_message_list);

                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                registerForContextMenu(recyclerView);
            }

            // message send
            new AQ(this, R.id.button_chatbox_send).click(v -> {
                String text = new AQ(this, R.id.edittext_chatbox).text();
                if (AQ.validator().present(text)) {
                    presenter.sendMessage(text);
                    new AQ(this, R.id.edittext_chatbox).text("");

                    if (recyclerView != null) {
                        Message m = new Message();
                        m.setContent(text);
                        m.setFromUser(true);
                        ((MessagesAdapter) recyclerView.getAdapter()).addItem(m);
                        int itemCount = recyclerView.getAdapter().getItemCount();
                        if (itemCount > 0)
                            recyclerView.smoothScrollToPosition(itemCount - 1);
                    }
                }

            });


            new AQ(this, R.id.edittext_chatbox).click(v -> {
                final Handler handler = new Handler();
                handler.postDelayed(() -> {
                    if (recyclerView.getAdapter().getItemCount() > 0) {
                        recyclerView.smoothScrollToPosition(recyclerView.getAdapter().getItemCount() - 1);
                    }
                }, 350);

            });

            senderId = getIntent().getStringExtra("senderId");
            MessageDao messageDao = ((MyApplication) getApplicationContext()).getDb().messageDao();
            ChatApi chatApi = new ApiConstructor(this).getChatApi();
            presenter = new ChatPresenter(senderId, messageDao, chatApi);
            presenter.attachView(this);
            presenter.getMessages();
        });

    }


//    TextView rrrr = (TextView)findViewById(R.id.text_message_time);
//    getCurrentTime(rrrr);
//
//}
//    public void getCurrentTime(View view) {
//        Calendar calendar = Calendar.getInstance();
//        SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm:ss");
//        String strDate = "Current Time : " + mdformat.format(calendar.getTime());
//        display(strDate);
//    }
//
//    private void display(String num) {
//        TextView textView = (TextView) findViewById(R.id.text_message_time);
//        textView.setText(num);
//    }

    @Override
    public void onResume() {
        super.onResume();
        ((MyApplication) getApplication()).setChatActivityVisibile(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        ((MyApplication) getApplication()).setChatActivityVisibile(false);
    }

    @Override
    public void displayMessages(List<Message> messages) {
        recyclerView.setAdapter(new MessagesAdapter(messages));
        int itemCount = recyclerView.getAdapter().getItemCount();
        if (itemCount > 0)
            recyclerView.smoothScrollToPosition(itemCount - 1);


    }

    @Override
    public void displayError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    private class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Message message = new Gson().fromJson(intent.getStringExtra("messageJson"), Message.class);
            if (message.getSenderId().equals(senderId) && recyclerView != null) {
                ((MessagesAdapter) recyclerView.getAdapter()).addItem(message);
            } else {
                Intent onClickIntent = new Intent(ChatActivity.this, MainActivity.class);
                NotificationUtils.sendNotification(ChatActivity.this,
                        "You've got a new message!", message.getContent(), onClickIntent);
            }
        }
    }
    @Override
    public void onBackPressed() {
        // show home

        Intent intent = new Intent(ChatActivity.this, MainActivity.class);
        startActivity(intent);
    }

}
