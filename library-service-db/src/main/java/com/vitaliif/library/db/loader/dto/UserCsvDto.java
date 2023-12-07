package com.vitaliif.library.db.loader.dto;

import com.opencsv.bean.CsvBindByName;

public class UserCsvDto {

    @CsvBindByName(column = "Name")
    private String name;
    @CsvBindByName(column = "First name")
    private String firstName;
    @CsvBindByName(column = "Member since")
    private String memberSince;
    @CsvBindByName(column = "Member till")
    private String memberTill;
    @CsvBindByName(column = "Gender")
    private String gender;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMemberSince() {
        return memberSince;
    }

    public void setMemberSince(String memberSince) {
        this.memberSince = memberSince;
    }

    public String getMemberTill() {
        return memberTill;
    }

    public void setMemberTill(String memberTill) {
        this.memberTill = memberTill;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
