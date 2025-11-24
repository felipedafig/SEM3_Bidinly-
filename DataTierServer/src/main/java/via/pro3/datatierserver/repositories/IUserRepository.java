package via.pro3.datatierserver.repositories;

import via.pro3.datatierserver.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Integer> {
    // Spring Data JPA automatically provides:
    // - save(User) for add/update
    // - findById(Integer) for getSingle
    // - findAll() for getMany
    // - deleteById(Integer) for delete
    // - delete(User) for delete by entity
    
    default Optional<User> getSingle(Integer id) {
        return findById(id);
    }
    
    Optional<User> findByUsername(String username);
}