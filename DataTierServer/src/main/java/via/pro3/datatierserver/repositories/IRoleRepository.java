package via.pro3.datatierserver.repositories;

import via.pro3.datatierserver.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Integer> {
    // Spring Data JPA automatically provides:
    // - save(Role) for add/update
    // - findById(Integer) for getSingle
    // - findAll() for getMany
    // - deleteById(Integer) for delete
    // - delete(Role) for delete by entity
    // - count(), existsById(), etc.
}