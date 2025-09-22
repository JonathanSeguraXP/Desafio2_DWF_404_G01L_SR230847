package sv.edu.udb.UserSubscripcionAPI.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class SubscriptionRequestDto {
    @NotBlank(message = "Subscription name is mandatory")
    private String name;

    @NotNull(message = "Start date is mandatory")
    @FutureOrPresent(message = "Start date must be today or in the future")
    private LocalDate startDate;

    @NotNull(message = "End date is mandatory")
    @FutureOrPresent(message = "End date must be today or in the future")
    private LocalDate endDate;

    @NotNull(message = "User ID is mandatory")
    private Long userId;

    // Constructors
    public SubscriptionRequestDto() {}

    public SubscriptionRequestDto(String name, LocalDate startDate, LocalDate endDate, Long userId) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.userId = userId;
    }

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
}
