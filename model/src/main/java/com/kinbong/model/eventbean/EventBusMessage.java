package com.kinbong.model.eventbean;

/**
 * Created by Administrator on 2017/9/20.
 */

public class EventBusMessage {
    private String Message;
    private String Message2;
    private int postion;

    public EventBusMessage(String message) {
        Message = message;
    }
    public EventBusMessage(String message, String message2, int postion) {
        Message = message;
        Message2 = message2;
        postion = postion;
    }

    public String getMessage() {
        return Message;
    }
    public String getMessage2() {
        return Message2;
    }
    public int getPostion(){
        return  postion;
    }
}
