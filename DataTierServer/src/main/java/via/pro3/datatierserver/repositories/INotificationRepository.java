package via.pro3.datatierserver.repositories;

import via.pro3.datatierserver.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface INotificationRepository extends JpaRepository<Notification, Integer> {
    // Spring Data JPA automatically provides:
    // - save(Notification) for add/update
    // - findById(Integer) for getSingle
    // - findAll() for getMany
    // - deleteById(Integer) for delete
    // - delete(Notification) for delete by entity
    
    default List<Notification> getMany() {
        return findAll();
    }
    
    default Optional<Notification> getSingle(Integer id) {
        return findById(id);
    }
    
    List<Notification> findByBuyerId(Integer buyerId);
    
    List<Notification> findByBuyerIdAndIsRead(Integer buyerId, Boolean isRead);
    
    List<Notification> findByRecipientTypeAndBuyerId(String recipientType, Integer buyerId);
    
    List<Notification> findByRecipientTypeAndBuyerIdAndIsRead(String recipientType, Integer buyerId, Boolean isRead);
    
    List<Notification> findByRecipientTypeAndAgentId(String recipientType, Integer agentId);
    
    List<Notification> findByRecipientTypeAndAgentIdAndIsRead(String recipientType, Integer agentId, Boolean isRead);
}

