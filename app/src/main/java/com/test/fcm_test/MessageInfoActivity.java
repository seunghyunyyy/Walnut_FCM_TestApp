package com.test.fcm_test;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class MessageInfoActivity extends AppCompatActivity {
    MessagesListActivity messagesListActivity = (MessagesListActivity) MessagesListActivity.messagesListActivity;
    final String url = "http://192.168.50.190:8080/push/v1";
    MessageItem messageItems;
    MessageData messageData;
    String messagesData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_info);

        Intent intent = getIntent();

        Long ID = intent.getLongExtra("msgId", 0);
        Log.i("msgId info :", ID.toString());

        Thread thread = new Thread(){
            public void run(){
                messagesData = HttpUtility.getRest(url + "/messages/" + ID, "", "");
                System.out.println(messagesData);
            }
        };
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        MessageData(messagesData);

        TextView title = (TextView) findViewById(R.id.title);
        TextView body = (TextView) findViewById(R.id.body);
        TextView opcode = (TextView) findViewById(R.id.opcode);
        TextView tokenId = (TextView) findViewById(R.id.tokenId);
        TextView msgId = (TextView) findViewById(R.id.msgId);
        TextView etc = (TextView) findViewById(R.id.etc);

        title.setText(messageItems.getTitle());
        body.setText(messageItems.getBody());
        opcode.setText("opcode : " + messageData.getOpcode());
        tokenId.setText("token ID : " + messageData.getTokenId().toString());
        msgId.setText("msg ID : " + messageData.getMsgId().toString());
        etc.setText("특이사항 : ");

        Button agree = (Button) findViewById(R.id.agree);
        agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                messagesListActivity.finish();
                Intent intent = new Intent(getApplicationContext(), MessagesListActivity.class);
                Toast.makeText(getApplicationContext(), "승인되었습니다.", Toast.LENGTH_SHORT).show();
                intent.putExtra("Delete", true);
                intent.putExtra("msgId", ID);
                Log.i("intent", intent.toString());
                startActivity(intent);
                finish();
            }
        });
        Button disagree  = (Button) findViewById(R.id.disagree);
        disagree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                messagesListActivity.finish();
                Intent intent = new Intent(getApplicationContext(), MessagesListActivity.class);
                Toast.makeText(getApplicationContext(), "거부했습니다.", Toast.LENGTH_SHORT).show();
                intent.putExtra("Delete", true);
                intent.putExtra("msgId", ID);
                startActivity(intent);
                System.out.println("--------------------------------------------------------------------------"+intent.getLongExtra("msgId", 0)+"--------------------------------------"+intent.getBooleanExtra("Delete", false));
                finish();
            }
        });
    }

    public void MessageData(String json) {
        JsonParser jsonParser = new JsonParser();

        JsonObject jsonObject = (JsonObject) jsonParser.parse(json);
        JsonObject notification = (JsonObject) jsonObject.get("notification");
        JsonObject data = (JsonObject) jsonObject.get("data");

        String title = notification.get("title").getAsString();
        String body = notification.get("body").getAsString();

        String opcode = data.get("opcode").getAsString();
        Long tokenId = data.get("tokenId").getAsLong();
        Long msgId = data.get("msgId").getAsLong();
        Log.i("", msgId.toString());

        messageItems = new MessageItem("사진", title, body);
        messageData = new MessageData(opcode, tokenId, msgId);
    }
}