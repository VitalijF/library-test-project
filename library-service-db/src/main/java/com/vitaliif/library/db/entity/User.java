package com.vitaliif.library.db.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "user", schema = "public")
public class User extends AbstractEntity {
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false)
    private LocalDate membershipStartDate;

    private LocalDate membershipUntil;
    @Column(nullable = false)
    private Gender gender;

    @OneToMany(mappedBy ="user", fetch = FetchType.LAZY)
    private List<Borrowed> borrowedList;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getMembershipStartDate() {
        return membershipStartDate;
    }

    public void setMembershipStartDate(LocalDate membershipStartDate) {
        this.membershipStartDate = membershipStartDate;
    }

    public LocalDate getMembershipUntil() {
        return membershipUntil;
    }

    public void setMembershipUntil(LocalDate membershipEndDate) {
        this.membershipUntil = membershipEndDate;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public List<Borrowed> getBorrowedList() {
        return borrowedList;
    }

    public User setBorrowedList(List<Borrowed> borrowedList) {
        this.borrowedList = borrowedList;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(membershipStartDate, user.membershipStartDate) && Objects.equals(membershipUntil, user.membershipUntil) && gender == user.gender;
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, membershipStartDate, membershipUntil, gender);
    }
}
