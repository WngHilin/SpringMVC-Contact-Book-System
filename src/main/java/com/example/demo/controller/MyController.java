package com.example.demo.controller;

import com.example.demo.dao.ContactRepository;
import com.example.demo.entity.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@RestController
public class MyController   {
    //private List<Contact> contactList = new ArrayList<>();
    private ContactRepository contactRepository;

    @Autowired
    public void setContactRepository(ContactRepository contactRepository) {
        this.contactRepository = contactRepository ;
    }

    @RequestMapping("/login")
    ModelAndView loginView(Model model) {
        ModelAndView modelAndView = new ModelAndView("login", "userModel", model);
        return modelAndView;
    }

    @RequestMapping("/list")
    ModelAndView indexView(Model model) {
        ModelAndView modelAndView = new ModelAndView("addressbook", "userModel", model);
        return modelAndView;
    }

    @GetMapping("/api/list")
    List<Contact> list(HttpSession session) {
        List<Contact> contacts = (List<Contact>)contactRepository.findAll();
        if(contacts == null) {
            contacts = new ArrayList<Contact>();
        }
        return contacts;
    }

    @PostMapping("/api/login")
    void Login(HttpServletRequest request, HttpServletResponse response,
                 @RequestParam("username") String username,
                 @RequestParam("password") String password) throws IOException {
        if(username.equals("12345678") && password.equals("12345678")) {
            HttpSession session = request.getSession();
            session.setAttribute("User", "12345678");
            response.sendRedirect("/list");
            return;
        }
        response.sendRedirect("/login");
    }

    @PostMapping("/api/add")
    String add(@RequestParam String name, @RequestParam String phoneNumber, @RequestParam String email,
               @RequestParam String address, @RequestParam String QQNumber) {
        Contact contact = new Contact(name, phoneNumber, email, QQNumber, address);
        contactRepository.save(contact);
        return "success";
    }

    @PostMapping("/api/edit")
    String edit(@RequestParam String name, @RequestParam String phoneNumber, @RequestParam String email,
                @RequestParam String address, @RequestParam String QQNumber, @RequestParam Long contactId) {
        List<Contact> contacts = (List<Contact>)contactRepository.findAll();
        if(contacts != null) {
            Contact contact = contacts.get(contactId.intValue() - 1);
            contact.setName(name);
            contact.setPhone(phoneNumber);
            contact.setEmail(email);
            contact.setQq(QQNumber);
            contact.setAddress(address);

            contactRepository.save(contact);
            return "success";
        }
        else {
            return "failure";
        }
    }

    @DeleteMapping("/api/delete")
    String delete(@RequestParam Long contactId) {
        System.out.println("Delete: " + contactId);
        List<Contact> contacts = (List<Contact>)contactRepository.findAll();
        Contact contactToDelete = contacts.get(contactId.intValue() - 1);
        contactRepository.deleteById(contactToDelete.getId());

        return "success";
    }

    @PostMapping("/api/phone")
    String checkPhone(@RequestParam int id, @RequestParam String phoneNumber) {
        System.out.println("Phone Number: " + phoneNumber);

        List<Contact> contacts = (List<Contact>) contactRepository.findAll();
        boolean find = false;
        for(int i = 0; i < contacts.size(); i++) {
            Contact item = contacts.get(i);
            if(phoneNumber.equals(item.getPhone()) && i != (id - 1)) {
                find = true;
                break;
            }
        }

        if(find) {
            return "error";
        } else {
            return "success";
        }
    }
}
