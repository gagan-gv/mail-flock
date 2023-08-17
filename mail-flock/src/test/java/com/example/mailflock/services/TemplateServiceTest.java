package com.example.mailflock.services;

import com.example.mailflock.dto.MessageResponse;
import com.example.mailflock.dto.TemplateDetails;
import com.example.mailflock.models.Template;
import com.example.mailflock.models.User;
import com.example.mailflock.repositories.TemplateRepository;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class TemplateServiceTest {

    @Mock
    private TemplateRepository templateRepository;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private TemplateService templateService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    void createTemplate_Success() {
        // Arrange
        TemplateDetails details = TemplateDetails.builder()
                .name("Test")
                .subject("Test")
                .content("Test")
                .isHTML(false)
                .build();
        Template template = Template.builder()
                .name("test")
                .subject("test")
                .content("test")
                .isHTML(false)
                .build();

        // Act
        when(authentication.getPrincipal()).thenReturn(new User());
        when(templateRepository.save(any())).thenReturn(template);

        ResponseEntity<MessageResponse> response = templateService.createTemplate(details);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(
                "Template has been created successfully!",
                Objects.requireNonNull(response.getBody()).getMessage()
        );
    }

    @Test
    void createTemplate_UserNotFound() {
        // Arrange
        TemplateDetails details = TemplateDetails.builder()
                .name("Test")
                .subject("Test")
                .content("Test")
                .isHTML(false)
                .build();

        // Act
        when(authentication.getPrincipal()).thenReturn(null);

        ResponseEntity<MessageResponse> response = templateService.createTemplate(details);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals(
                "User not found",
                Objects.requireNonNull(response.getBody()).getMessage()
        );
    }

    @Test
    void createTemplate_InternalServerError() {
        // Arrange
        TemplateDetails details = TemplateDetails.builder()
                .name("Test")
                .subject("Test")
                .content("Test")
                .isHTML(false)
                .build();

        // Act
        when(authentication.getPrincipal()).thenReturn(new User());
        when(templateRepository.save(any())).thenThrow(new RuntimeException("Internal Server Error"));

        ResponseEntity<MessageResponse> response = templateService.createTemplate(details);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(
                "Internal Server Error",
                Objects.requireNonNull(response.getBody()).getMessage()
        );
    }

    @Test
    void testUpdateTemplate_Success() {
        // Arrange
        Long id = 1L;
        TemplateDetails details = TemplateDetails.builder()
                .name("Updated Test")
                .subject("Updated Test")
                .content("Updated test")
                .isHTML(true)
                .build();
        Template template = Template.builder()
                .name("test")
                .subject("test")
                .content("test")
                .isHTML(false)
                .build();

        // Act
        when(authentication.getPrincipal()).thenReturn(new User());
        when(templateRepository.findById(id)).thenReturn(Optional.of(template));
        when(templateRepository.save(any())).thenReturn(template);

        ResponseEntity<MessageResponse> response = templateService.updateTemplate(details, id);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(
                "Template has been updated",
                Objects.requireNonNull(response.getBody()).getMessage()
        );
    }

    @Test
    void testUpdateTemplate_Success_NoUpdate() {
        // Arrange
        Long id = 1L;
        TemplateDetails details = TemplateDetails.builder()
                .name("test")
                .subject("test")
                .content("test")
                .isHTML(false)
                .build();
        Template template = Template.builder()
                .name("test")
                .subject("test")
                .content("test")
                .isHTML(false)
                .build();

        // Act
        when(authentication.getPrincipal()).thenReturn(new User());
        when(templateRepository.findById(id)).thenReturn(Optional.of(template));
        when(templateRepository.save(any())).thenReturn(template);

        ResponseEntity<MessageResponse> response = templateService.updateTemplate(details, id);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(
                "Template has been updated",
                Objects.requireNonNull(response.getBody()).getMessage()
        );
    }

    @Test
    void testUpdateTemplate_TemplateNotFound() {
        // Arrange
        Long id = 1L;
        TemplateDetails details = TemplateDetails.builder()
                .name("Updated Test")
                .subject("Updated Test")
                .content("Updated test")
                .isHTML(true)
                .build();

        // Act
        when(authentication.getPrincipal()).thenReturn(new User());
        when(templateRepository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<MessageResponse> response = templateService.updateTemplate(details, id);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(
                "Template with the given id: " + id + " not found",
                Objects.requireNonNull(response.getBody()).getMessage()
        );
    }

    @Test
    void testUpdateTemplate_UserNotFound() {
        // Arrange
        Long id = 1L;
        TemplateDetails details = TemplateDetails.builder()
                .name("Updated Test")
                .subject("Updated Test")
                .content("Updated test")
                .isHTML(true)
                .build();

        // Act
        when(authentication.getPrincipal()).thenReturn(null);

        ResponseEntity<MessageResponse> response = templateService.updateTemplate(details, id);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals(
                "User not found",
                Objects.requireNonNull(response.getBody()).getMessage()
        );
    }

    @Test
    void testUpdateTemplate_InternalServerError() {
        // Arrange
        Long id = 1L;
        TemplateDetails details = TemplateDetails.builder()
                .name("Updated Test")
                .subject("Updated Test")
                .content("Updated test")
                .isHTML(true)
                .build();

        // Act
        when(authentication.getPrincipal()).thenReturn(new User());
        when(templateRepository.findById(id)).thenThrow(new RuntimeException("Internal Server Error"));

        ResponseEntity<MessageResponse> response = templateService.updateTemplate(details, id);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(
                "Internal Server Error",
                Objects.requireNonNull(response.getBody()).getMessage()
        );
    }

    @Test
    void testListAllTemplates_Success() {
        // Arrange
        List<Template> mockTemplates = new ArrayList<>();
        Template template1 = new Template();
        template1.setId(1L);
        template1.setName("Template 1");
        template1.setSubject("Subject 1");
        template1.setContent("Content 1");
        template1.setHTML(true);
        template1.setUser(new User());

        Template template2 = new Template();
        template2.setId(2L);
        template2.setName("Template 2");
        template2.setSubject("Subject 2");
        template2.setContent("Content 2");
        template2.setHTML(false);
        template2.setUser(new User());

        mockTemplates.add(template1);
        mockTemplates.add(template2);

        // Act
        when(templateRepository.findAll()).thenReturn(mockTemplates);

        ResponseEntity<List<TemplateDetails>> response = templateService.listAllTemplates();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, Objects.requireNonNull(response.getBody()).size());

        TemplateDetails template1Details = response.getBody().get(0);
        assertEquals(1L, template1Details.getId());
        assertEquals("Template 1", template1Details.getName());
        assertEquals("Subject 1", template1Details.getSubject());
        assertEquals("Content 1", template1Details.getContent());
        assertTrue(template1Details.isHTML());

        TemplateDetails template2Details = response.getBody().get(1);
        assertEquals(2L, template2Details.getId());
        assertEquals("Template 2", template2Details.getName());
        assertEquals("Subject 2", template2Details.getSubject());
        assertEquals("Content 2", template2Details.getContent());
        assertFalse(template2Details.isHTML());
    }

    @Test
    void testViewTemplate_Success() {
        Long id = 1L;
        Template template = new Template();
        template.setId(id);
        template.setUser(new User());
        template.setContent("Test Content");
        template.setSubject("Test Subject");
        template.setName("Test Template");
        template.setHTML(true);

        when(templateRepository.findById(id)).thenReturn(Optional.of(template));

        ResponseEntity<TemplateDetails> response = templateService.viewTemplate(id);

        assertEquals(HttpStatus.FOUND, response.getStatusCode());
        assertEquals(
                "Test Content",
                Objects.requireNonNull(response.getBody()).getContent()
        );
        assertEquals(
                "Test Subject",
                Objects.requireNonNull(response.getBody()).getSubject()
        );
        assertEquals(
                "Test Template",
                Objects.requireNonNull(response.getBody()).getName()
        );
        assertTrue(Objects.requireNonNull(response.getBody()).isHTML());
    }

    @Test
    void testViewTemplate_TemplateNotFound() {
        Long id = 1L;

        when(templateRepository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<TemplateDetails> response = templateService.viewTemplate(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(
                "Template with the given id: " + id + " not found",
                Objects.requireNonNull(response.getBody()).getMessage()
        );
    }

    @Test
    void testViewTemplate_InternalServerError() {
        Long id = 1L;

        when(templateRepository.findById(id)).thenThrow(new RuntimeException("Internal Server Error"));

        ResponseEntity<TemplateDetails> response = templateService.viewTemplate(id);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(
                "Internal Server Error",
                Objects.requireNonNull(response.getBody()).getMessage()
        );
    }
}
