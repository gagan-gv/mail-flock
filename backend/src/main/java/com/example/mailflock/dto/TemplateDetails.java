package com.example.mailflock.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_ABSENT)
public class TemplateDetails {
    private long id;

    private String name;

    private String subject;

    private String content;

    @JsonAlias(value = "html")
    private boolean isHTML;

    private String username;

    private String message;
}
