package com.example.demo.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String phone;
    private String email;
    private String qq;
    private String address;
    private static Long num = Long.valueOf(0);

    public Contact(Long id, String name, String phone, String email, String qq, String address) {
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
    public Contact(Long id)
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
