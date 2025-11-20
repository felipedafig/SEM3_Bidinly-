package via.pro3.datatierserver.repositories;

import via.pro3.datatierserver.model.Bid;
import via.pro3.datatierserver.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISaleRepository extends JpaRepository<Sale, Integer> {
    // Spring Data JPA automatically provides:
    // - save(Sale) for add/update
    // - findById(Integer) for getSingle
    // - findAll() for getMany
    // - deleteById(Integer) for delete
    // - delete(Sale) for delete by entity
    // - count(), existsById(), etc.

    default List<Sale> getMany() {
        return findAll();
    }
}