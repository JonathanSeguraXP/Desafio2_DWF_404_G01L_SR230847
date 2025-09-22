package sv.edu.udb.UserSubscripcionAPI.service;

import sv.edu.udb.UserSubscripcionAPI.dto.SubscriptionRequestDto;
import sv.edu.udb.UserSubscripcionAPI.dto.SubscriptionResponseDto;
import sv.edu.udb.UserSubscripcionAPI.entity.Subscription;

import java.util.List;

public interface SubscriptionService {
    List<SubscriptionResponseDto> getAllSubscriptions();
    SubscriptionResponseDto getSubscriptionById(Long id);
    List<SubscriptionResponseDto> getSubscriptionsByUserId(Long userId);
    List<SubscriptionResponseDto> getActiveSubscriptions(Boolean isActive);
    SubscriptionResponseDto createSubscription(SubscriptionRequestDto subscriptionRequestDto);
    SubscriptionResponseDto updateSubscription(Long id, SubscriptionRequestDto subscriptionRequestDto);
    void deleteSubscription(Long id);
    SubscriptionResponseDto activateSubscription(Long id);
    SubscriptionResponseDto deactivateSubscription(Long id);
    Subscription getEntityById(Long id);
}