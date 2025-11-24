package via.pro3.datatierserver.repositories;

import via.pro3.datatierserver.model.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IBidRepository extends JpaRepository<Bid, Integer> {
    // Spring Data JPA automatically provides:
    // - save(Bid) for add/update
    // - findById(Integer) for getSingle
    // - findAll() for getMany
    // - deleteById(Integer) for delete
    // - delete(Bid) for delete by entity
    
    default List<Bid> getMany() {
        return findAll();
    }
    
    default Optional<Bid> getSingle(Integer id) {
        return findById(id);
    }
}