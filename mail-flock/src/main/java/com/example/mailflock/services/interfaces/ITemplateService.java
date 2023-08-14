package com.example.mailflock.services.interfaces;

import com.example.mailflock.dto.MessageResponse;
import com.example.mailflock.dto.TemplateDetails;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ITemplateService {

    /**
     * Creates a template by taking in TemplateDetails and defaults creator from
     * AuthTokens and times used to 0.
     * @param details Contains template contents
     * @return Returns a message stating if the template is created or if any error is thrown.
     */
    ResponseEntity<MessageResponse> createTemplate(TemplateDetails details);

    /**
     * Updates the given template without altering constant data like user who created it
     * and times the template is used.
     * @param details Contains updatable template contents
     * @param id Template ID
     * @return Returns a message stating if the template is updated or if any error is thrown.
     */
    ResponseEntity<MessageResponse> updateTemplate(TemplateDetails details, Long id);

    /**
     * Fetches the template from id
     * @param id Template ID
     * @return Returns a message stating if the template is fetched or if any error is thrown.
     */
    ResponseEntity<TemplateDetails> viewTemplate(Long id);

    /**
     * Lists all templates for users to use
     * @return A list of template details
     */
    ResponseEntity<List<TemplateDetails>> listAllTemplates();
}
