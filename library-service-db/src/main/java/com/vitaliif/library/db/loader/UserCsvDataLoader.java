package com.vitaliif.library.db.loader;

import com.vitaliif.library.db.entity.User;
import com.vitaliif.library.db.loader.converter.UserCsvConverter;
import com.vitaliif.library.db.loader.dto.UserCsvDto;
import com.vitaliif.library.db.parser.CsvParser;
import com.vitaliif.library.db.repo.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component("userDataLoader")
public class UserCsvDataLoader implements DataLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserCsvDataLoader.class);

    private final CsvParser csvParser;
    private final UserRepository userRepository;

    private final UserCsvConverter userCsvConverter;

    public UserCsvDataLoader(CsvParser csvParser,
                             UserRepository userRepository, UserCsvConverter userCsvConverter) {
        this.csvParser = csvParser;
        this.userRepository = userRepository;
        this.userCsvConverter = userCsvConverter;
    }


    @Override
    @Transactional
    public void loadDataIntoTable(String filePath) {
        final List<UserCsvDto> usersFromCsv = csvParser.parse(UserCsvDto.class, filePath);
        final List<User> usersToSave = usersFromCsv.stream().map(userCsvConverter::convert).toList();

        usersToSave.forEach(this::saveUser);
    }

    private void saveUser(User user) {
        Optional<User> existedUser = userRepository.findUserByFirstNameAndLastName(user.getFirstName(), user.getLastName());
        if (existedUser.isPresent()) {
            LOGGER.warn("User already exist. First name = {}, last name =  {}", user.getFirstName(), user.getLastName());
        } else {
            userRepository.save(user);
        }

    }
}
