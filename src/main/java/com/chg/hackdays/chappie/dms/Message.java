package com.chg.hackdays.chappie.dms;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class Message {
    private String id;
    private String conversation;
    private String replyTo;
    private String source;
    private String target;
    private String topic;
    private String type;
    private String text;
    private String timestamp;
    private Map<String, String> attributes;
}
