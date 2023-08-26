package com.example.mailflock.controllers;


import java.util.List;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.mailflock.dto.MessageResponse;
import com.example.mailflock.dto.TemplateDetails;
import com.example.mailflock.services.TemplateService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/templates")
public class TemplateController {
    private final TemplateService templateService;

    @PostMapping
    public ResponseEntity<MessageResponse> createTemplate(@RequestBody TemplateDetails details) {
        return templateService.createTemplate(details);
    }

    @PatchMapping
    public ResponseEntity<MessageResponse> updateTemplate(
            @RequestBody TemplateDetails details,
            @RequestParam(value = "id") Long id
    ) {
        return templateService.updateTemplate(details, id);
    }

    @GetMapping("/view")
    public ResponseEntity<TemplateDetails> viewTemplate(@RequestParam Long id) {
        return templateService.viewTemplate(id);
    }

    @GetMapping
    public ResponseEntity<List<TemplateDetails>> listAllTemplates() {
        return templateService.listAllTemplates();
    }
}
