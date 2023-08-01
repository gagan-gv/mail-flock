package com.example.mailflock.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MailRequest {
    @JsonAlias(value = "to_mail")
    private String[] toMail;
    private String[] cc;
    private String[] bcc;
    private String subject;
    private String content;
    private boolean isHTML;
}
