package com.vitaliif.library.db.loader.converter;

import com.vitaliif.library.db.entity.Gender;
import com.vitaliif.library.db.entity.User;
import com.vitaliif.library.db.loader.dto.UserCsvDto;
import com.vitaliif.library.db.utils.DateUtils;
import org.springframework.stereotype.Component;

@Component
public class UserCsvConverter {


    public User convert(UserCsvDto userCsvDto) {
        final User user = new User();

        user.setFirstName(userCsvDto.getFirstName());
        user.setLastName(userCsvDto.getName());
        user.setMembershipStartDate(DateUtils.parseDate(userCsvDto.getMemberSince()));
        user.setMembershipUntil(DateUtils.parseDate(userCsvDto.getMemberTill()));
        user.setGender(Gender.getFromSymbol(userCsvDto.getGender()));

        return user;
    }

}
