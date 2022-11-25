package com.test.fcm_test;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MessagesListActivity extends AppCompatActivity {

    public static MessagesListActivity messagesListActivity;

    final String url = "http://192.168.50.190:8080/push/v1";
    ArrayList<MessageItem> messageItems;
    ArrayList<MessageData> messageData;
    String messages;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messages_activity);
        messagesListActivity = MessagesListActivity.this;
        Intent getIntent = getIntent();
        System.out.println("============================================================"+getIntent.getBooleanExtra("Delete", false)+ "============================================================"+getIntent.getLongExtra("msgId", 0));
        Thread thread = new Thread(){
            public void run(){
                messages = HttpUtility.getRest(url + "/messages", "token", SaveSharedPreference.getString(getApplicationContext(), "token"));
                System.out.println(messages);
            }
        };
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i("messages", messages);

        MessageDataList(messages);
/*
        if (getIntent != null && getIntent.getBooleanExtra("Delete", false)) {
            Long MSG = getIntent.getLongExtra("msgId", 0);
            messageItems.remove(messageData.indexOf(MSG));
        }
*/
        ListView listView = (ListView) findViewById(R.id.messages_listview);
        MessagesAdapter adapter = new MessagesAdapter(this, messageItems);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), MessageInfoActivity.class);
                intent.putExtra("msgId", messageData.get(position).getMsgId());
                startActivity(intent);
            }
        });
    }

    public void MessageDataList(String json) {
        messageItems = new ArrayList<MessageItem>();
        messageData = new ArrayList<MessageData>();

        JsonParser jsonParser = new JsonParser();
        JsonArray jsonArray = (JsonArray) jsonParser.parse(json);

        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject jsonObject = (JsonObject) jsonArray.get(i);
            JsonObject notification = (JsonObject) jsonObject.get("notification");
            JsonObject data = (JsonObject) jsonObject.get("data");

            String title = notification.get("title").getAsString();
            String body = notification.get("body").getAsString();

            String opcode = data.get("opcode").getAsString();
            Long tokenId = data.get("tokenId").getAsLong();
            Long msgId = data.get("msgId").getAsLong();
            Log.i("", msgId.toString());

            messageItems.add(new MessageItem("사진", title, body));
            messageData.add(new MessageData(opcode, tokenId, msgId));
        }
    }
}