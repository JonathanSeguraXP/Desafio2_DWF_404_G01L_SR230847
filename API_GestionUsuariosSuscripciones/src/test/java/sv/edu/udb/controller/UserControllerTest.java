package sv.edu.udb.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import sv.edu.udb.UserSubscripcionAPI.dto.UserRequestDto;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createUser_WithValidData_ShouldReturnCreated() throws Exception {
        UserRequestDto userRequest = new UserRequestDto();
        userRequest.setFirstName("Test");
        userRequest.setLastName("User");
        userRequest.setEmail("test.user@udb.edu.sv");

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("Test"))
                .andExpect(jsonPath("$.lastName").value("User"))
                .andExpect(jsonPath("$.email").value("test.user@udb.edu.sv"))
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    void getAllUsers_ShouldReturnOk() throws Exception {
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void getUserById_WhenUserNotExists_ShouldReturnNotFound() throws Exception {
        mockMvc.perform(get("/api/users/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createUser_WithInvalidEmail_ShouldReturnBadRequest() throws Exception {
        UserRequestDto userRequest = new UserRequestDto();
        userRequest.setFirstName("Test");
        userRequest.setLastName("User");
        userRequest.setEmail("invalid-email"); // Email inválido

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createUser_WithDuplicateEmail_ShouldReturnBadRequest() throws Exception {
        // Primer usuario
        UserRequestDto userRequest1 = new UserRequestDto();
        userRequest1.setFirstName("First");
        userRequest1.setLastName("User");
        userRequest1.setEmail("duplicate@udb.edu.sv");

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest1)))
                .andExpect(status().isCreated());

        // Segundo usuario con mismo email
        UserRequestDto userRequest2 = new UserRequestDto();
        userRequest2.setFirstName("Second");
        userRequest2.setLastName("User");
        userRequest2.setEmail("duplicate@udb.edu.sv");

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest2)))
                .andExpect(status().isBadRequest());
    }
}