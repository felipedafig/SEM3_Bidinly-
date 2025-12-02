package via.pro3.paymentserver.repositories;

import via.pro3.paymentserver.model.ValidCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IValidCardRepository extends JpaRepository<ValidCard, Integer> {
    // Spring Data JPA automatically provides:
    // - save(ValidCard) for add/update
    // - findById(Integer) for getSingle
    // - findAll() for getMany
    // - deleteById(Integer) for delete
    // - delete(ValidCard) for delete by entity
    
    default Optional<ValidCard> getSingle(Integer id) {
        return findById(id);
    }
    
    Optional<ValidCard> findByCardNumber(String cardNumber);
}

