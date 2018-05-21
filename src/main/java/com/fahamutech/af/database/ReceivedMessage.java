package com.fahamutech.af.database;

import javax.persistence.*;

@Entity
@Table(name = "received_sms")
public class ReceivedMessage {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;
    @Column(name = "from")
    private String fromNumber;
    @Column(name = "to")
    private String toNumber;
    @Column(name = "date")
    private String date;
    @Column(name = "message_id")
    private String messageId;
    @Column(name = "linkId")
    private String linkId;

    public ReceivedMessage(){

    }

    public ReceivedMessage(String fromNumber,String toNumber,String date,String messageId,String linkId){
        this.fromNumber=fromNumber;
        this.toNumber=toNumber;
        this.date=date;
        this.messageId=messageId;
        this.linkId=linkId;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getFromNumber() {
        return fromNumber;
    }

    public String getToNumber() {
        return toNumber;
    }

    public void setFromNumber(String fromNumber) {
        this.fromNumber = fromNumber;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setToNumber(String toNumber) {
        this.toNumber = toNumber;
    }

    public String getLinkId() {
        return linkId;
    }

    public void setLinkId(String linkId) {
        this.linkId = linkId;
    }

}
