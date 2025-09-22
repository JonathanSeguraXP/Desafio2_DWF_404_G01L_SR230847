package sv.edu.udb.UserSubscripcionAPI.service.impl;

import sv.edu.udb.UserSubscripcionAPI.dto.SubscriptionRequestDto;
import sv.edu.udb.UserSubscripcionAPI.dto.SubscriptionResponseDto;
import sv.edu.udb.UserSubscripcionAPI.entity.Subscription;
import sv.edu.udb.UserSubscripcionAPI.entity.User;
import sv.edu.udb.UserSubscripcionAPI.exception.ResourceNotFoundException;
import sv.edu.udb.UserSubscripcionAPI.mapper.SubscriptionMapper;
import sv.edu.udb.UserSubscripcionAPI.repository.SubscriptionRepository;
import sv.edu.udb.UserSubscripcionAPI.repository.UserRepository;
import sv.edu.udb.UserSubscripcionAPI.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;
    private final SubscriptionMapper subscriptionMapper;

    @Autowired
    public SubscriptionServiceImpl(SubscriptionRepository subscriptionRepository,
                                   UserRepository userRepository,
                                   SubscriptionMapper subscriptionMapper) {
        this.subscriptionRepository = subscriptionRepository;
        this.userRepository = userRepository;
        this.subscriptionMapper = subscriptionMapper;
    }

    @Override
    public List<SubscriptionResponseDto> getAllSubscriptions() {
        return subscriptionRepository.findAll()
                .stream()
                .map(subscriptionMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public SubscriptionResponseDto getSubscriptionById(Long id) {
        Subscription subscription = subscriptionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subscription not found with id: " + id));
        return subscriptionMapper.toDto(subscription);
    }

    @Override
    public List<SubscriptionResponseDto> getSubscriptionsByUserId(Long userId) {
        List<Subscription> subscriptions = subscriptionRepository.findByUserId(userId);
        return subscriptions.stream()
                .map(subscriptionMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<SubscriptionResponseDto> getActiveSubscriptions(Boolean isActive) {
        List<Subscription> subscriptions = subscriptionRepository.findByIsActive(isActive);
        return subscriptions.stream()
                .map(subscriptionMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public SubscriptionResponseDto createSubscription(SubscriptionRequestDto subscriptionRequestDto) {
        // Verify user exists
        User user = userRepository.findById(subscriptionRequestDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + subscriptionRequestDto.getUserId()));

        // Validate dates
        if (subscriptionRequestDto.getEndDate().isBefore(subscriptionRequestDto.getStartDate())) {
            throw new IllegalArgumentException("End date cannot be before start date");
        }

        Subscription subscription = subscriptionMapper.toEntity(subscriptionRequestDto);
        subscription.setUser(user);

        Subscription savedSubscription = subscriptionRepository.save(subscription);
        return subscriptionMapper.toDto(savedSubscription);
    }

    @Override
    public SubscriptionResponseDto updateSubscription(Long id, SubscriptionRequestDto subscriptionRequestDto) {
        Subscription existingSubscription = subscriptionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subscription not found with id: " + id));

        // Verify user exists if userId is being updated
        if (!existingSubscription.getUser().getId().equals(subscriptionRequestDto.getUserId())) {
            User user = userRepository.findById(subscriptionRequestDto.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + subscriptionRequestDto.getUserId()));
            existingSubscription.setUser(user);
        }

        // Validate dates
        if (subscriptionRequestDto.getEndDate().isBefore(subscriptionRequestDto.getStartDate())) {
            throw new IllegalArgumentException("End date cannot be before start date");
        }

        subscriptionMapper.updateEntityFromDto(subscriptionRequestDto, existingSubscription);
        Subscription updatedSubscription = subscriptionRepository.save(existingSubscription);
        return subscriptionMapper.toDto(updatedSubscription);
    }

    @Override
    public void deleteSubscription(Long id) {
        if (!subscriptionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Subscription not found with id: " + id);
        }
        subscriptionRepository.deleteById(id);
    }

    @Override
    public SubscriptionResponseDto activateSubscription(Long id) {
        Subscription subscription = subscriptionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subscription not found with id: " + id));
        subscription.setIsActive(true);
        Subscription updatedSubscription = subscriptionRepository.save(subscription);
        return subscriptionMapper.toDto(updatedSubscription);
    }

    @Override
    public SubscriptionResponseDto deactivateSubscription(Long id) {
        Subscription subscription = subscriptionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subscription not found with id: " + id));
        subscription.setIsActive(false);
        Subscription updatedSubscription = subscriptionRepository.save(subscription);
        return subscriptionMapper.toDto(updatedSubscription);
    }

    @Override
    public Subscription getEntityById(Long id) {
        return subscriptionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subscription not found with id: " + id));
    }
}