package sv.edu.udb.repository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import sv.edu.udb.UserSubscripcionAPI.entity.User;
import sv.edu.udb.UserSubscripcionAPI.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void saveUser_ShouldPersistUser() {
        User user = new User();
        user.setFirstName("Repo");
        user.setLastName("Test");
        user.setEmail("repo.test@udb.edu.sv");

        User savedUser = userRepository.save(user);

        assertNotNull(savedUser.getId());
        assertEquals("Repo", savedUser.getFirstName());
        assertEquals("repo.test@udb.edu.sv", savedUser.getEmail());
        assertNotNull(savedUser.getCreatedAt());
    }

    @Test
    void findByEmail_WhenUserExists_ShouldReturnUser() {
        User user = new User();
        user.setFirstName("Email");
        user.setLastName("Find");
        user.setEmail("email.find@udb.edu.sv");
        userRepository.save(user);

        Optional<User> foundUser = userRepository.findByEmail("email.find@udb.edu.sv");

        assertTrue(foundUser.isPresent());
        assertEquals("email.find@udb.edu.sv", foundUser.get().getEmail());
    }

    @Test
    void findByEmail_WhenUserNotExists_ShouldReturnEmpty() {
        Optional<User> foundUser = userRepository.findByEmail("nonexistent@udb.edu.sv");

        assertTrue(foundUser.isEmpty());
    }

    @Test
    void existsByEmail_WhenEmailExists_ShouldReturnTrue() {
        User user = new User();
        user.setFirstName("Exists");
        user.setLastName("Test");
        user.setEmail("exists.test@udb.edu.sv");
        userRepository.save(user);

        boolean exists = userRepository.existsByEmail("exists.test@udb.edu.sv");

        assertTrue(exists);
    }

    @Test
    void existsByEmail_WhenEmailNotExists_ShouldReturnFalse() {
        boolean exists = userRepository.existsByEmail("nonexistent@udb.edu.sv");

        assertFalse(exists);
    }
}