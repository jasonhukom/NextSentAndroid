package com.nextsent.android;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    public interface OnChatClickListener {
        void onChatClick(Contact contact);
    }

    private List<Contact> contacts;
    private OnChatClickListener listener;

    public ChatAdapter(List<Contact> contacts, OnChatClickListener listener) {
        this.contacts = contacts;
        this.listener = listener;
    }

    public void updateData(List<Contact> newContacts) {
        this.contacts = newContacts;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_row, parent, false);
        return new ChatViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        Contact contact = contacts.get(position);
        holder.bind(contact, listener);
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    static class ChatViewHolder extends RecyclerView.ViewHolder {
        TextView itemAvatar, itemName, itemTime, itemPreview;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            itemAvatar = itemView.findViewById(R.id.itemAvatar);
            itemName = itemView.findViewById(R.id.itemName);
            itemTime = itemView.findViewById(R.id.itemTime);
            itemPreview = itemView.findViewById(R.id.itemPreview);
        }

        public void bind(Contact contact, OnChatClickListener listener) {
            String initials = getInitials(contact.getName());
            itemAvatar.setText(initials);
            try {
                itemAvatar.setBackgroundColor(Color.parseColor(contact.getColor()));
            } catch (Exception e) {
                itemAvatar.setBackgroundColor(Color.parseColor("#718B83"));
            }

            String displayName = contact.getName() + (contact.isGroup() ? " [GROUP]" : "");
            itemName.setText(displayName);
            itemTime.setText(contact.getTime());
            itemPreview.setText(contact.getLastMessage());

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onChatClick(contact);
                }
            });
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
}
