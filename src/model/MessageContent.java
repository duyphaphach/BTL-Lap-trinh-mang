/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;

/**
 *
 * @author Admin
 */
public class MessageContent implements Serializable{
    private String nickName;
    private String message;
    private String timeMessage;

    public MessageContent(){
        
    }

    public MessageContent(String nickName, String message) {
        this.nickName = nickName;
        this.message = message;
    }
    
    
    public MessageContent(String nickName, String message,String timeMessage) {
        this.nickName = nickName;
        this.message = message;
        this.timeMessage = timeMessage;
    }

    public String getTimeMessage() {
        return timeMessage;
    }

    public void setTimeMessage(String timeMessage) {
        this.timeMessage = timeMessage;
    }

    
    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
}
