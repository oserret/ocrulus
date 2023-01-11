package com.expert.ai.ocrulus.bean;

import com.expert.ai.ocrulus.utils.Globals;

public class Message {

    String message = Globals.VOID_STRING;
    String status = Globals.VOID_STRING;

    public Message(String message, String status){
        this.message = message;
        this.status = status;
    }

    public Message(String message){
        this.message = message;
    }
}
