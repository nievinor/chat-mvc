package ru.vostrikov.chat.dao;

import org.springframework.stereotype.Component;
import ru.vostrikov.chat.model.Message;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class MessageDAO {

    private static final List<Message> messages = new CopyOnWriteArrayList<>();

    public void addMessage(Message message) {
        messages.add(message);
    }

    public List<Message> getMessages() {
        return messages;
    }

}
