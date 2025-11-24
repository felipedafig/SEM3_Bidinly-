package via.pro3.datatierserver.repositories;

import via.pro3.datatierserver.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IPropertyRepository extends JpaRepository<Property, Integer> {
    // Spring Data JPA automatically provides:
    // - save(Property) for add/update
    // - findById(Integer) for getSingle
    // - findAll() for getMany
    // - deleteById(Integer) for delete
    // - delete(Property) for delete by entity
    // - count(), existsById(), etc.
}