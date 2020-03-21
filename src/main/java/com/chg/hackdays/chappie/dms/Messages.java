package com.chg.hackdays.chappie.dms;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Messages {
    private List<Message> items = new ArrayList<>();
}
