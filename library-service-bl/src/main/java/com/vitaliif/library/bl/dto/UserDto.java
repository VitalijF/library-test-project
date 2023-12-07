package com.vitaliif.library.bl.dto;

import java.time.LocalDate;

public class UserDto {

    private Integer id;
    private String firstName;
    private String lastName;
    private String gender;
    private LocalDate membershipStartDate;
    private LocalDate membershipEndDate;

    public Integer getId() {
        return id;
    }

    public UserDto setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserDto setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserDto setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getGender() {
        return gender;
    }

    public UserDto setGender(String gender) {
        this.gender = gender;
        return this;
    }

    public LocalDate getMembershipStartDate() {
        return membershipStartDate;
    }

    public UserDto setMembershipStartDate(LocalDate membershipStartDate) {
        this.membershipStartDate = membershipStartDate;
        return this;
    }

    public LocalDate getMembershipEndDate() {
        return membershipEndDate;
    }

    public UserDto setMembershipEndDate(LocalDate membershipEndDate) {
        this.membershipEndDate = membershipEndDate;
        return this;
    }
}
