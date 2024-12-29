package com.practice.jpa_and_criteriaqueries.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Getter
@Setter
public class Employee {
    //Table column
    @Id
    private int empId;
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private Date birthdate;


}
