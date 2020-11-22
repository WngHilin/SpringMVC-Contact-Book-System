package com.example.demo.controller;

import com.example.demo.model.Contact;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@Controller
public class MyController   {
    private List<Contact> contactList = new ArrayList<>();
    private int id = 0;

    @RequestMapping("/login")
    String loginView(Model model) {
        return "login";
    }

    @RequestMapping("/list")
    ModelAndView indexView(Model model) {
        model.addAttribute("user", contactList);
        ModelAndView modelAndView = new ModelAndView("addressbook", "userModel", model);
        return modelAndView;
    }

    @RequestMapping("/add")
    ModelAndView addView(Model model) {
        model.addAttribute("user", new Contact());
        ModelAndView modelAndView = new ModelAndView("add", "userModel", model);
        return modelAndView;
    }

    @RequestMapping("/update/{id}")
    String updateView(@ModelAttribute("user")Contact user, @PathVariable("id") int id, Model model) {
        return "update";
    }

    @PostMapping("/api/login")
    String Login(HttpServletRequest request, HttpServletResponse response,
                 @RequestParam("username") String username,
                 @RequestParam("password") String password)
    {
        if(username.equals("12345678") && password.equals("12345678")) {
            HttpSession session = request.getSession();
            session.setAttribute("User", "12345678");
            return "redirect:/list";
        }

        return "redirect:/login";
    }

    @PostMapping("/api/add")
    String add(Contact contact) {
        contactList.add(contact);
        System.out.println(contact.getId());
        return "redirect:/list";
    }

    @PostMapping("/api/update/{id}")
    String update(@ModelAttribute("user") Contact contact, @PathVariable("id") int id, Model model) {
        Iterator<Contact> iter = contactList.iterator();

        while(iter.hasNext()) {
            Contact tmp = iter.next();
            if(tmp.getId() == id) {
                Collections.replaceAll(contactList, tmp, contact);
            }
        }
        return "redirect:/list";
    }

    @PostMapping("/api/delete/{id}")
    String delete(@PathVariable("id") int id)
    {
        Iterator<Contact> iter = contactList.iterator();
        while(iter.hasNext()) {
            Contact tmp = iter.next();
            if(tmp.getId() == id)
            {
                iter.remove();
                System.out.println("删除成功！");
                break;
            }
        }
        return "redirect:/list";
    }
}
