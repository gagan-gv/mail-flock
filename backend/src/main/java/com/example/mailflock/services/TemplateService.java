package com.example.mailflock.services;

import com.example.mailflock.dto.MessageResponse;
import com.example.mailflock.dto.TemplateDetails;
import com.example.mailflock.exceptions.TemplateNotFoundException;
import com.example.mailflock.models.Template;
import com.example.mailflock.models.User;
import com.example.mailflock.repositories.TemplateRepository;
import com.example.mailflock.services.interfaces.ITemplateService;

import io.jsonwebtoken.ExpiredJwtException;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TemplateService implements ITemplateService {

    private final TemplateRepository templateRepository;

    @Override
    public ResponseEntity<MessageResponse> createTemplate(TemplateDetails details) {
        MessageResponse response;
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            if(user == null) {
                throw new UsernameNotFoundException("User not found");
            }

            Template template = Template.builder()
                    .name(details.getName())
                    .subject(details.getSubject())
                    .content(details.getSubject())
                    .isHTML(details.isHTML())
                    .build();

            templateRepository.save(template);

            response = MessageResponse.builder()
                    .message("Template has been created successfully!")
                    .build();

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (ExpiredJwtException | UsernameNotFoundException e) {
            response = MessageResponse.builder()
                    .message(e.getMessage())
                    .build();

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        } catch (Exception e) {
            response = MessageResponse.builder()
                    .message(e.getMessage())
                    .build();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<MessageResponse> updateTemplate(TemplateDetails details, Long id) {
        MessageResponse response;

        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            if(user == null) {
                throw new UsernameNotFoundException("User not found");
            }

            Template template = templateRepository.findById(id)
                    .orElseThrow(() ->
                new TemplateNotFoundException("Template with the given id: " + id + " not found")
            );

            if(!template.getName().equals(details.getName())) {
                template.setName(details.getName());
            }
            if(!template.getSubject().equals(details.getSubject())) {
                template.setSubject(details.getSubject());
            }
            if(!template.getContent().equals(details.getContent())) {
                template.setContent(details.getContent());
            }
            if(template.isHTML() != details.isHTML()) {
                template.setHTML(details.isHTML());
            }

            templateRepository.save(template);

            response = MessageResponse.builder()
                    .message("Template has been updated")
                    .build();

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (TemplateNotFoundException e) {
            response = MessageResponse.builder()
                    .message(e.getMessage())
                    .build();

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (ExpiredJwtException | UsernameNotFoundException e) {
            response = MessageResponse.builder()
                    .message(e.getMessage())
                    .build();

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        } catch (Exception e) {
            response = MessageResponse.builder()
                    .message(e.getMessage())
                    .build();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<TemplateDetails> viewTemplate(Long id) {
        TemplateDetails details;

        try {
            Template template = templateRepository.findById(id)
                    .orElseThrow(() -> new TemplateNotFoundException(
                            "Template with the given id: " + id + " not found"
                    ));

            details = TemplateDetails.builder()
                    .id(template.getId())
                    .username(template.getUser().getUsername())
                    .content(template.getContent())
                    .subject(template.getSubject())
                    .name(template.getName())
                    .isHTML(template.isHTML())
                    .build();

            return ResponseEntity.status(HttpStatus.FOUND).body(details);
        } catch (TemplateNotFoundException e) {
            details = TemplateDetails.builder()
                    .message(e.getMessage())
                    .build();

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(details);
        } catch (Exception e) {
            details = TemplateDetails.builder()
                    .message(e.getMessage())
                    .build();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(details);
        }
    }

    @Override
    public ResponseEntity<List<TemplateDetails>> listAllTemplates() {
        List<TemplateDetails> templateDetails = new ArrayList<>();
        List<Template> templates = templateRepository.findAll();

        for(Template template : templates) {
            TemplateDetails details = TemplateDetails.builder()
                    .id(template.getId())
                    .name(template.getName())
                    .subject(template.getSubject())
                    .content(template.getContent())
                    .isHTML(template.isHTML())
                    .username(template.getUser().getUsername())
                    .build();

            templateDetails.add(details);
        }

        return ResponseEntity.status(HttpStatus.OK).body(templateDetails);
    }
}
