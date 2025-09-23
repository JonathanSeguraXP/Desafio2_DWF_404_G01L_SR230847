package sv.edu.udb.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import sv.edu.udb.UserSubscripcionAPI.dto.SubscriptionRequestDto;
import sv.edu.udb.UserSubscripcionAPI.dto.UserRequestDto;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test") // Usa el perfil limpio sin data.sql
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD) // Limpia la base entre tests
class SubscriptionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createSubscription_WithValidData_ShouldReturnCreated() throws Exception {
        Long userId = createTestUser();

        SubscriptionRequestDto subscriptionRequest = new SubscriptionRequestDto();
        subscriptionRequest.setName("Premium Plan");
        subscriptionRequest.setStartDate(LocalDate.now());
        subscriptionRequest.setEndDate(LocalDate.now().plusDays(30));
        subscriptionRequest.setUserId(userId);

        mockMvc.perform(post("/api/subscriptions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(subscriptionRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Premium Plan"))
                .andExpect(jsonPath("$.userId").value(userId));
    }

    @Test
    void getAllSubscriptions_ShouldReturnOk() throws Exception {
        Long userId = createTestUser();
        createTestSubscription(userId);

        mockMvc.perform(get("/api/subscriptions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void getSubscriptionsByUserId_ShouldReturnSubscriptions() throws Exception {
        Long userId = createTestUser();
        createTestSubscription(userId);

        mockMvc.perform(get("/api/subscriptions/user/" + userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].userId").value(userId));
    }

    @Test
    void getSubscriptionsByUserId_WhenUserHasNoSubscriptions_ShouldReturnEmptyArray() throws Exception {
        Long userId = createTestUser();

        mockMvc.perform(get("/api/subscriptions/user/" + userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void activateSubscription_ShouldReturnOk() throws Exception {
        Long userId = createTestUser();
        Long subscriptionId = createTestSubscription(userId);

        mockMvc.perform(patch("/api/subscriptions/" + subscriptionId + "/activate"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isActive").value(true));
    }

    @Test
    void activateSubscription_WhenSubscriptionNotExists_ShouldReturnNotFound() throws Exception {
        mockMvc.perform(patch("/api/subscriptions/999/activate"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createSubscription_WithInvalidDates_ShouldReturnBadRequest() throws Exception {
        Long userId = createTestUser();

        SubscriptionRequestDto subscriptionRequest = new SubscriptionRequestDto();
        subscriptionRequest.setName("Invalid Plan");
        subscriptionRequest.setStartDate(LocalDate.now().plusDays(10)); // Fecha futura
        subscriptionRequest.setEndDate(LocalDate.now()); // Fecha anterior
        subscriptionRequest.setUserId(userId);

        mockMvc.perform(post("/api/subscriptions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(subscriptionRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createSubscription_WhenUserNotExists_ShouldReturnNotFound() throws Exception {
        SubscriptionRequestDto subscriptionRequest = new SubscriptionRequestDto();
        subscriptionRequest.setName("Test Plan");
        subscriptionRequest.setStartDate(LocalDate.now());
        subscriptionRequest.setEndDate(LocalDate.now().plusDays(30));
        subscriptionRequest.setUserId(999L); // ID inexistente

        mockMvc.perform(post("/api/subscriptions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(subscriptionRequest)))
                .andExpect(status().isNotFound());
    }

    private Long createTestUser() throws Exception {
        UserRequestDto userRequest = new UserRequestDto();
        userRequest.setFirstName("Test");
        userRequest.setLastName("User");
        userRequest.setEmail("test." + System.nanoTime() + "@udb.edu.sv"); // Email único

        MvcResult result = mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isCreated())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        return objectMapper.readTree(responseContent).get("id").asLong();
    }

    private Long createTestSubscription(Long userId) throws Exception {
        SubscriptionRequestDto subscriptionRequest = new SubscriptionRequestDto();
        subscriptionRequest.setName("Test Subscription");
        subscriptionRequest.setStartDate(LocalDate.now());
        subscriptionRequest.setEndDate(LocalDate.now().plusDays(15));
        subscriptionRequest.setUserId(userId);

        MvcResult result = mockMvc.perform(post("/api/subscriptions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(subscriptionRequest)))
                .andExpect(status().isCreated())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        return objectMapper.readTree(responseContent).get("id").asLong();
    }
}
