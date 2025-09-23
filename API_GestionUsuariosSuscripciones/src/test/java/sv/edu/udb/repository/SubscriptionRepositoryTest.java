package sv.edu.udb.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import sv.edu.udb.UserSubscripcionAPI.entity.Subscription;
import sv.edu.udb.UserSubscripcionAPI.entity.User;
import sv.edu.udb.UserSubscripcionAPI.repository.SubscriptionRepository;
import sv.edu.udb.UserSubscripcionAPI.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test") // Usa el perfil limpio sin data.sql
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD) // Limpia la base entre tests
class SubscriptionRepositoryTest {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private UserRepository userRepository;

    private User createTestUser() {
        User user = new User();
        user.setFirstName("Test");
        user.setLastName("User");
        user.setEmail("test.user." + System.nanoTime() + "@udb.edu.sv"); // Email único por test
        return userRepository.save(user);
    }

    @Test
    void saveSubscription_ShouldPersistSubscription() {
        User user = createTestUser();

        Subscription subscription = new Subscription();
        subscription.setName("Test Plan");
        subscription.setStartDate(LocalDate.now());
        subscription.setEndDate(LocalDate.now().plusDays(30));
        subscription.setUser(user);

        Subscription savedSubscription = subscriptionRepository.save(subscription);

        assertNotNull(savedSubscription.getId());
        assertEquals("Test Plan", savedSubscription.getName());
        assertEquals(user.getId(), savedSubscription.getUser().getId());
    }

    @Test
    void findByUserId_ShouldReturnSubscriptions() {
        User user = createTestUser();

        Subscription subscription = new Subscription();
        subscription.setName("User Plan");
        subscription.setStartDate(LocalDate.now());
        subscription.setEndDate(LocalDate.now().plusDays(15));
        subscription.setUser(user);
        subscriptionRepository.save(subscription);

        List<Subscription> subscriptions = subscriptionRepository.findByUserId(user.getId());

        assertFalse(subscriptions.isEmpty());
        assertEquals("User Plan", subscriptions.get(0).getName());
    }

    @Test
    void findByIsActive_ShouldReturnActiveSubscriptions() {
        User user = createTestUser();

        Subscription activeSubscription = new Subscription();
        activeSubscription.setName("Active Plan");
        activeSubscription.setStartDate(LocalDate.now());
        activeSubscription.setEndDate(LocalDate.now().plusDays(30));
        activeSubscription.setIsActive(true);
        activeSubscription.setUser(user);
        subscriptionRepository.save(activeSubscription);

        Subscription inactiveSubscription = new Subscription();
        inactiveSubscription.setName("Inactive Plan");
        inactiveSubscription.setStartDate(LocalDate.now());
        inactiveSubscription.setEndDate(LocalDate.now().plusDays(30));
        inactiveSubscription.setIsActive(false);
        inactiveSubscription.setUser(user);
        subscriptionRepository.save(inactiveSubscription);

        List<Subscription> activeSubscriptions = subscriptionRepository.findByIsActive(true);

        assertFalse(activeSubscriptions.isEmpty());
        assertEquals("Active Plan", activeSubscriptions.get(0).getName());
    }
}
