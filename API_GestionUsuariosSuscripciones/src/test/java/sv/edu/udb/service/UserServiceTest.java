package sv.edu.udb.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import sv.edu.udb.UserSubscripcionAPI.dto.UserRequestDto;
import sv.edu.udb.UserSubscripcionAPI.exception.ResourceNotFoundException;
import sv.edu.udb.UserSubscripcionAPI.service.UserService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void createUser_WithValidData_ShouldCreateUser() {
        UserRequestDto userRequest = new UserRequestDto();
        userRequest.setFirstName("Service");
        userRequest.setLastName("Test");
        userRequest.setEmail("service.test@udb.edu.sv");

        var result = userService.createUser(userRequest);

        assertNotNull(result.getId());
        assertEquals("Service", result.getFirstName());
        assertEquals("service.test@udb.edu.sv", result.getEmail());
    }

    @Test
    void getUserById_WhenUserExists_ShouldReturnUser() {
        // Create user first
        UserRequestDto userRequest = new UserRequestDto();
        userRequest.setFirstName("Find");
        userRequest.setLastName("Service");
        userRequest.setEmail("find.service@udb.edu.sv");

        var createdUser = userService.createUser(userRequest);

        var foundUser = userService.getUserById(createdUser.getId());

        assertNotNull(foundUser);
        assertEquals(createdUser.getId(), foundUser.getId());
    }

    @Test
    void getUserById_WhenUserNotExists_ShouldThrowException() {
        assertThrows(ResourceNotFoundException.class, () -> {
            userService.getUserById(999L);
        });
    }
}
