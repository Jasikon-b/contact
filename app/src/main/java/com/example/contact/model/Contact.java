package com.example.contact.model;

public class Contact {
    public int id;
    public String name, phone, email;

    public Contact() {}

    public Contact(int id, String name, String phone, String email) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }
}
