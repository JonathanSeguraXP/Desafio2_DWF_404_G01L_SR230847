package sv.edu.udb.UserSubscripcionAPI.controller;

import sv.edu.udb.UserSubscripcionAPI.dto.SubscriptionRequestDto;
import sv.edu.udb.UserSubscripcionAPI.dto.SubscriptionResponseDto;
import sv.edu.udb.UserSubscripcionAPI.service.SubscriptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/subscriptions")
@Tag(name = "Subscription Management", description = "APIs for managing subscriptions")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @Autowired
    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @GetMapping
    @Operation(summary = "Get all subscriptions")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved all subscriptions")
    public ResponseEntity<List<SubscriptionResponseDto>> getAllSubscriptions() {
        List<SubscriptionResponseDto> subscriptions = subscriptionService.getAllSubscriptions();
        return new ResponseEntity<>(subscriptions, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get subscription by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved subscription"),
            @ApiResponse(responseCode = "404", description = "Subscription not found")
    })
    public ResponseEntity<SubscriptionResponseDto> getSubscriptionById(@PathVariable Long id) {
        SubscriptionResponseDto subscription = subscriptionService.getSubscriptionById(id);
        return new ResponseEntity<>(subscription, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get subscriptions by user ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved user subscriptions"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<List<SubscriptionResponseDto>> getSubscriptionsByUserId(@PathVariable Long userId) {
        List<SubscriptionResponseDto> subscriptions = subscriptionService.getSubscriptionsByUserId(userId);
        return new ResponseEntity<>(subscriptions, HttpStatus.OK);
    }

    @GetMapping("/status/{isActive}")
    @Operation(summary = "Get subscriptions by status")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved subscriptions by status")
    public ResponseEntity<List<SubscriptionResponseDto>> getSubscriptionsByStatus(@PathVariable Boolean isActive) {
        List<SubscriptionResponseDto> subscriptions = subscriptionService.getActiveSubscriptions(isActive);
        return new ResponseEntity<>(subscriptions, HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Create a new subscription")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Subscription created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<SubscriptionResponseDto> createSubscription(@Valid @RequestBody SubscriptionRequestDto subscriptionRequestDto) {
        SubscriptionResponseDto createdSubscription = subscriptionService.createSubscription(subscriptionRequestDto);
        return new ResponseEntity<>(createdSubscription, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing subscription")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Subscription updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Subscription or user not found")
    })
    public ResponseEntity<SubscriptionResponseDto> updateSubscription(@PathVariable Long id, @Valid @RequestBody SubscriptionRequestDto subscriptionRequestDto) {
        SubscriptionResponseDto updatedSubscription = subscriptionService.updateSubscription(id, subscriptionRequestDto);
        return new ResponseEntity<>(updatedSubscription, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a subscription")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Subscription deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Subscription not found")
    })
    public ResponseEntity<Void> deleteSubscription(@PathVariable Long id) {
        subscriptionService.deleteSubscription(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{id}/activate")
    @Operation(summary = "Activate a subscription")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Subscription activated successfully"),
            @ApiResponse(responseCode = "404", description = "Subscription not found")
    })
    public ResponseEntity<SubscriptionResponseDto> activateSubscription(@PathVariable Long id) {
        SubscriptionResponseDto activatedSubscription = subscriptionService.activateSubscription(id);
        return new ResponseEntity<>(activatedSubscription, HttpStatus.OK);
    }

    @PatchMapping("/{id}/deactivate")
    @Operation(summary = "Deactivate a subscription")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Subscription deactivated successfully"),
            @ApiResponse(responseCode = "404", description = "Subscription not found")
    })
    public ResponseEntity<SubscriptionResponseDto> deactivateSubscription(@PathVariable Long id) {
        SubscriptionResponseDto deactivatedSubscription = subscriptionService.deactivateSubscription(id);
        return new ResponseEntity<>(deactivatedSubscription, HttpStatus.OK);
    }
}