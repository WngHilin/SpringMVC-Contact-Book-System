package com.example.demo.model;

import lombok.Data;

@Data
public class Contact {
    private static int num = 0;
    private int id;
    private String name;
    private String phone;
    private String email;
    private String qq;
    private String address;

    public Contact(int id, String name, String phone, String email, String qq, String address) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.qq = qq;
        this.address = address;
        num++;
    }
    public Contact()
    {
        this.id = num;
        num++;
    }
    public Contact(int id)
    {
        this.id = id;
        num = id+1;
    }

    public Contact(String name, String phone, String email, String qq, String address) {
        this.id = num;
        num++;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.qq = qq;
        this.address = address;
    }
}
