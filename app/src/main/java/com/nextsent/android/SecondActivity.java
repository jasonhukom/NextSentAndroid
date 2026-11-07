package com.nextsent.android;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class SecondActivity extends AppCompatActivity {

    public static final String EXTRA_CONTACT_NAME = "com.nextsent.android.CONTACT_NAME";

    private String contactName = "Contact";
    private TextView threadAvatar, threadName;
    private LinearLayout messagesContainer, attachMenu;
    private EditText messageInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_CONTACT_NAME)) {
            contactName = intent.getStringExtra(EXTRA_CONTACT_NAME);
        }

        threadAvatar = findViewById(R.id.threadAvatar);
        threadName = findViewById(R.id.threadName);
        messagesContainer = findViewById(R.id.messagesContainer);
        attachMenu = findViewById(R.id.attachMenu);
        messageInput = findViewById(R.id.messageInput);

        threadName.setText(contactName);
        threadAvatar.setText(getInitials(contactName));

        ImageButton buttonCloseThread = findViewById(R.id.buttonCloseThread);
        buttonCloseThread.setOnClickListener(v -> finish());

        ImageButton buttonStartCall = findViewById(R.id.buttonStartCall);
        buttonStartCall.setOnClickListener(v -> {
            Intent callIntent = new Intent(SecondActivity.this, CallActivity.class);
            callIntent.putExtra(CallActivity.EXTRA_CALL_NAME, contactName);
            startActivity(callIntent);
        });

        ImageButton buttonToggleAttach = findViewById(R.id.buttonToggleAttach);
        buttonToggleAttach.setOnClickListener(v -> {
            if (attachMenu.getVisibility() == View.VISIBLE) {
                attachMenu.setVisibility(View.GONE);
            } else {
                attachMenu.setVisibility(View.VISIBLE);
            }
        });

        findViewById(R.id.buttonAttachPhoto).setOnClickListener(v -> sendAttachment("Photo"));
        findViewById(R.id.buttonAttachAudio).setOnClickListener(v -> sendAttachment("Audio"));
        findViewById(R.id.buttonAttachLocation).setOnClickListener(v -> sendAttachment("Location"));
        findViewById(R.id.buttonAttachDocument).setOnClickListener(v -> sendAttachment("Document"));

        findViewById(R.id.buttonSendMessage).setOnClickListener(v -> {
            String text = messageInput.getText().toString().trim();
            if (!text.isEmpty()) {
                DataManager.getInstance().addMessage(contactName, text);
                messageInput.setText("");
                renderMessages();
            }
        });

        renderMessages();
    }

    private void sendAttachment(String type) {
        attachMenu.setVisibility(View.GONE);
        if ("Location".equals(type)) {
            DataManager.getInstance().addMessage(contactName, "📍 Location: Shared Location — https://maps.google.com");
        } else {
            DataManager.getInstance().addMessage(contactName, "📎 " + type + " attachment (demo)");
        }
        renderMessages();
    }

    private void renderMessages() {
        messagesContainer.removeAllViews();
        List<ChatMessage> list = DataManager.getInstance().getThread(contactName);
        String currentAcc = DataManager.getInstance().getCurrentAccount();

        for (ChatMessage msg : list) {
            boolean isOut = msg.getFrom().equals(currentAcc);

            View bubbleView = getLayoutInflater().inflate(R.layout.item_message_bubble, messagesContainer, false);
            LinearLayout msgBubble = bubbleView.findViewById(R.id.msgBubble);
            TextView msgText = bubbleView.findViewById(R.id.msgText);
            TextView msgStamp = bubbleView.findViewById(R.id.msgStamp);

            msgText.setText(msg.getText());
            msgStamp.setText(msg.getTime() + (isOut ? " ✓✓" : ""));

            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) msgBubble.getLayoutParams();
            if (isOut) {
                lp.gravity = Gravity.END;
                msgBubble.setBackgroundColor(Color.parseColor("#D9FDD3"));
            } else {
                lp.gravity = Gravity.START;
                msgBubble.setBackgroundColor(Color.parseColor("#FFFFFF"));
            }
            msgBubble.setLayoutParams(lp);

            messagesContainer.addView(bubbleView);
        }
    }

    private String getInitials(String name) {
        if (name == null || name.isEmpty()) return "?";
        String[] parts = name.split(" ");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < Math.min(parts.length, 2); i++) {
            if (!parts[i].isEmpty()) {
                sb.append(parts[i].charAt(0));
            }
        }
        return sb.toString().toUpperCase();
    }
}
