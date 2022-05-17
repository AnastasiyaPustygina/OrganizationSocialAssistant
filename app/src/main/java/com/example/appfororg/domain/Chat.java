package com.example.appfororg.domain;

import java.util.Objects;

public class Chat {
    private int id, chat_personId, chat_orgId;

    public Chat(int chat_personId, int chat_orgId) {
        this.chat_personId = chat_personId;
        this.chat_orgId = chat_orgId;
    }

    public Chat(int id, int chat_personId, int chat_orgId) {
        this.id = id;
        this.chat_personId = chat_personId;
        this.chat_orgId = chat_orgId;
    }

    public int getId() {
        return id;
    }

    public int getChat_personId() {
        return chat_personId;
    }

    public int getChat_orgId() {
        return chat_orgId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chat chat = (Chat) o;
        return chat_personId == chat.chat_personId && chat_orgId == chat.chat_orgId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(chat_personId, chat_orgId);
    }

    @Override
    public String toString() {
        return "Chat{" +
                "id=" + id +
                ", chat_personId=" + chat_personId +
                ", chat_orgId=" + chat_orgId +
                '}';
    }
}
