package webeng.contactlist.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import webeng.contactlist.service.ContactService;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Controller
public class ContactsController {

    private final ContactService service;
    private String testSearchName;

    public ContactsController(ContactService service) {
        this.service = service;
    }

    @GetMapping("/contacts")
    public String contacts(String searchName, Model model) {
        model.addAttribute("contactList", service.getContactList(searchName));
        testSearchName = searchName;
        return "contacts";
    }

    @GetMapping(value = "/contacts", params = "ClearName")
    public String contactsClear(String searchName, Model model) {
        model.addAttribute("contactList", service.getContactList(searchName));
        testSearchName = null;
        return "contacts";
    }


    @GetMapping("/contacts/{id}")
    public String showContact(@PathVariable int id, String searchName, Model model) { //warum searchName Null??
        var contact = service.findContact(id).orElseThrow(ContactNotFound::new);
        searchName = testSearchName;

        model.addAttribute("contactList", service.getContactList(searchName));
        model.addAttribute("contact", contact);
        return "contacts";
    }

    @ExceptionHandler(ContactNotFound.class)
    @ResponseStatus(NOT_FOUND)
    public String notFound(Model model) {
        model.addAttribute("contactList", service.getContactList(null));
        return "contacts";
    }

    private static class ContactNotFound extends RuntimeException {}
}
