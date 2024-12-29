package com.practice.jpa_and_criteriaqueries.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeSearchRequest {

    private String firstname;
    private String lastname;
    private String email;
}
