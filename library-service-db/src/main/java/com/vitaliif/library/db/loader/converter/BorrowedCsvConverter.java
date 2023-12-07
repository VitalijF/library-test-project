package com.vitaliif.library.db.loader.converter;

import com.vitaliif.library.db.entity.Book;
import com.vitaliif.library.db.entity.Borrowed;
import com.vitaliif.library.db.entity.User;
import com.vitaliif.library.db.loader.dto.BorrowedCsvDto;
import com.vitaliif.library.db.utils.DateUtils;
import org.springframework.stereotype.Component;

@Component
public class BorrowedCsvConverter {

    public Borrowed convert(BorrowedCsvDto borrowedCsvDto) {
        Borrowed borrowed = new Borrowed();
        borrowed.setStartAt(DateUtils.parseDate(borrowedCsvDto.getBorrowedFrom()));
        borrowed.setEndAt(DateUtils.parseDate(borrowedCsvDto.getBorrowedTo()));

        User user = new User();
        user.setFirstName(borrowedCsvDto.getBorrower().split(",")[1]);
        user.setLastName(borrowedCsvDto.getBorrower().split(",")[0]);
        borrowed.setUser(user);


        Book book = new Book();
        book.setName(borrowedCsvDto.getBook());
        borrowed.setBook(book);

        return borrowed;
    }
}
