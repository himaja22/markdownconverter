package com.example.markdownconverter.controller;

import com.example.markdownconverter.service.MarkdownService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller for handling Markdown to HTML conversion requests.
 */
@Controller
public class MarkdownController {

    // Store the current Markdown and HTML content in memory
    private String markdown = "";
    private String html = "";

    // Inject the MarkdownService to handle the conversion logic
    @Autowired
    private MarkdownService markdownService;

    /**
     * Handles GET requests to the root URL ("/").
     * Displays the form for Markdown input and shows the resulting HTML.
     */
    @GetMapping("/")
    public String index(Model model) {
        // Add the current markdown and HTML content to the model
        model.addAttribute("markdown", markdown);
        model.addAttribute("html", html);

        // Return the name of the Thymeleaf template to render
        return "index";
    }

    /**
     * Handles POST requests to the root URL ("/").
     * Processes the input Markdown text and converts it to HTML.
     */
    @PostMapping("/")
    public String post(@RequestParam String input, Model model) {
        // Update the markdown content with the submitted input
        markdown = input;

        // Convert the Markdown text to HTML using the MarkdownService
        html = markdownService.convertMarkdownToHtml(markdown);

        // Add the updated markdown and HTML content to the model
        model.addAttribute("markdown", markdown);
        model.addAttribute("html", html);

        // Return the name of the Thymeleaf template to render
        return "index";
    }
}
