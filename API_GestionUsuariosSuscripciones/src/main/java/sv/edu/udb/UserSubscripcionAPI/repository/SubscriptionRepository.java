package sv.edu.udb.UserSubscripcionAPI.repository;

import sv.edu.udb.UserSubscripcionAPI.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    List<Subscription> findByUserId(Long userId);
    List<Subscription> findByIsActive(Boolean isActive);
    List<Subscription> findByUserIdAndIsActive(Long userId, Boolean isActive);
    long countByUserId(Long userId);
}