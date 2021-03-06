package com.clearpay.demo.Document;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document(collection = "User")
public class User {

    @Id
    private String id = new ObjectId().toString();

    private String name;

    private double balance;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date fecha = new Date();

    private int wallets = 0;

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getWallets() {
        return wallets;
    }

    public void setWallets(int wallets) {
        this.wallets = wallets;
    }

    public double addBalance(double money){
        this.balance = this.balance + money;
        return this.balance;
    }

    public int addWallets(){
        return this.wallets = this.wallets + 1;
    }
}
