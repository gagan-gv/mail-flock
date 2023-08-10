package com.example.mailflock.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Time;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DemoRequest {
    @Pattern(regexp = "^[A-Za-z]+(?:\\s+[A-Za-z]+)*$")
    private String name;

    @Pattern(regexp = "^(?!\\.)[a-zA-Z0-9._%+-]+(?<!\\.)@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    private String emailId;

    private Date date;

    private Time time;
}
