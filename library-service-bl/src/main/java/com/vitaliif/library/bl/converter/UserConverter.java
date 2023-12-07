package com.vitaliif.library.bl.converter;

import com.vitaliif.library.bl.dto.UserDto;
import com.vitaliif.library.db.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

    public UserDto covert(User user) {
        return new UserDto()
                .setId(user.getId())
                .setFirstName(user.getFirstName())
                .setLastName(user.getLastName())
                .setGender(user.getGender().getGenderSymbol())
                .setMembershipStartDate(user.getMembershipStartDate())
                .setMembershipEndDate(user.getMembershipUntil());
    }
}
