package com.vitaliif.library.db.loader.dto;

import com.opencsv.bean.CsvBindByName;

public class BorrowedCsvDto {

    @CsvBindByName(column = "Borrower")
    private String borrower;

    @CsvBindByName(column = "book")
    private String book;

    @CsvBindByName(column = "borrowed from")
    private String borrowedFrom;

    @CsvBindByName(column = "borrowed to")
    private String borrowedTo;

    public String getBorrower() {
        return borrower;
    }

    public void setBorrower(String borrower) {
        this.borrower = borrower;
    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public String getBorrowedFrom() {
        return borrowedFrom;
    }

    public void setBorrowedFrom(String borrowedFrom) {
        this.borrowedFrom = borrowedFrom;
    }

    public String getBorrowedTo() {
        return borrowedTo;
    }

    public void setBorrowedTo(String borrowedTo) {
        this.borrowedTo = borrowedTo;
    }
}
