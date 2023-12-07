package com.vitaliif.library.rest;

import com.vitaliif.library.bl.dto.UserDto;
import com.vitaliif.library.bl.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testGetUsersWhoBorrowedBook() throws Exception {
        List<UserDto> mockUsers = Arrays.asList(new UserDto(), new UserDto());
        when(userService.getAllUsersWhoBorrowedBook()).thenReturn(mockUsers);


        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/borrowed")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(mockUsers.size()));
    }

    @Test
    public void testGetUsersWhoBorrowedBookForSpecificDate() throws Exception {
        LocalDate date = LocalDate.now();
        List<UserDto> mockUsers = Arrays.asList(new UserDto(), new UserDto());
        when(userService.getAllUsersWhoBorrowedBook(date)).thenReturn(mockUsers);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/borrowed/{date}", date)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(mockUsers.size()));
    }

    @Test
    public void testGetUsersWhoNotBorrowedBook() throws Exception {
        List<UserDto> mockUsers = Arrays.asList(new UserDto(/*...*/), new UserDto(/*...*/));
        when(userService.getAllUsersWhoNotBorrowedBook()).thenReturn(mockUsers);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/not-borrowed")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(mockUsers.size()));
    }
}
