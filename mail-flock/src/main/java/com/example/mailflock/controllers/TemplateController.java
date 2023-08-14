package com.example.mailflock.controllers;

import com.example.mailflock.dto.MessageResponse;
import com.example.mailflock.dto.TemplateDetails;
import com.example.mailflock.services.TemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
