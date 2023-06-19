package com.zanchenko.alexey.spring5webappguru.controllers;

import com.zanchenko.alexey.spring5webappguru.repositories.AuthorRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AuthorColntoller {
    private final AuthorRepository authorRepository;

    public AuthorColntoller(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @RequestMapping("/authors")
    public String getAuthorsList(Model model){
        model.addAttribute("authors", authorRepository.findAll());
        return "authors/list";
    }
}
