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
        assertNotNull(subscriptionService, "SubscriptionService debería estar inyectado");
        assertNotNull(userService, "UserService debería estar inyectado");
        System.out.println("✅ Servicios inyectados correctamente");
    }

    // TEST 2: Verifica que se puede crear un usuario
    @Test
    void testCanCreateUser() {
        try {
            var userRequest = new UserRequestDto();
            userRequest.setFirstName("Diagnostic");
            userRequest.setLastName("Test");
            userRequest.setEmail("diagnostic." + System.currentTimeMillis() + "@udb.edu.sv");

            var user = userService.createUser(userRequest);
            assertNotNull(user.getId(), "Usuario debería tener ID");
            System.out.println("✅ Usuario creado correctamente. ID: " + user.getId());
        } catch (Exception e) {
            fail("❌ Error creando usuario: " + e.getMessage());
        }
    }

    // TEST 3: Verifica que se puede obtener la lista de usuarios
    @Test
    void testCanGetAllUsers() {
        try {
            var users = userService.getAllUsers();
            assertNotNull(users, "Lista de usuarios no debería ser null");
            System.out.println("✅ Lista de usuarios obtenida. Total: " + users.size());
        } catch (Exception e) {
            fail("❌ Error obteniendo usuarios: " + e.getMessage());
        }
    }
}
