package com.match.matching.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMsg {
    public enum MessageType{
        ENTER, TALK
    }

    private MessageType type;
    private String roomId;
    private String sender;
    private String message;
}
