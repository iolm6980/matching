package com.match.matching.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMsg {
    private String roomId;
    private String writer;
    private String message;
}
