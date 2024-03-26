package com.match.matching.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMsg {
    enum MessageType{
        ENTER, MESSAGE, EXIT;
    }
    private String roomId;
    private String writer;
    private String message;
    private MessageType messageType;
}
