package com.fahamutech.af.database;


import javax.persistence.*;

@Entity
@Table(name = "sent_message")
public class SentMessage {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;
    @Column(name = "number")
    private String number;
    @Column(name = "cost")
    private String cost;
    @Column(name = "messageId")
    private String messageId;
    @Column(name = "status")
    private String status;

    public  SentMessage(){

    }

    public SentMessage(String number,String cost,String messageId,String status){
        this.number=number;
        this.cost=cost;
        this.messageId=messageId;
        this.status=status;
    }

    public int getId() {
        return id;
    }

    public String getCost() {
        return cost;
    }

    public String getMessageId() {
        return messageId;
    }

    public String getNumber() {
        return number;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getStatus() {
        return status;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
