package sv.edu.udb.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import sv.edu.udb.UserSubscripcionAPI.dto.UserRequestDto;
import sv.edu.udb.UserSubscripcionAPI.service.SubscriptionService;
import sv.edu.udb.UserSubscripcionAPI.service.UserService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class SubscriptionServiceDiagnosticTest {

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private UserService userService;

    // TEST 1
    @Test
    void testServicesAreInjected() {
        assertNotNull(subscriptionService, "SubscriptionService should be injected");
        assertNotNull(userService, "UserService should be injected");
        System.out.println("✅ Services successfully injected");
    }

    // TEST 2
    @Test
    void testCanCreateUser() {
        try {
            var userRequest = new UserRequestDto();
            userRequest.setFirstName("Diagnostic");
            userRequest.setLastName("Test");
            userRequest.setEmail("diagnostic." + System.currentTimeMillis() + "@udb.edu.sv");

            var user = userService.createUser(userRequest);
            assertNotNull(user.getId(), "User should have an ID");
            System.out.println("✅ User successfully created. ID: " + user.getId());
        } catch (Exception e) {
            fail("❌ Error creating user: " + e.getMessage());
        }
    }

    // TEST 3: Verifies that the user list can be retrieved
    @Test
    void testCanGetAllUsers() {
        try {
            var users = userService.getAllUsers();
            assertNotNull(users, "User list should not be null");
            System.out.println("✅ User list retrieved. Total: " + users.size());
        } catch (Exception e) {
            fail("❌ Error retrieving users: " + e.getMessage());
        }
    }
}
