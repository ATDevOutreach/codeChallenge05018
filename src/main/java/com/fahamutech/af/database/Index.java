package com.fahamutech.af.database;

import javax.persistence.*;

@Entity
@Table(name = "fetch_index")
public class Index {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "last_id")
    private int lastId;

    public Index() {

    }

    public Index(String name, int lastId) {
        this.name = name;
        this.lastId = lastId;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getLastId() {
        return lastId;
    }

    public void setLastId(int lastId) {
        this.lastId = lastId;
    }

}
