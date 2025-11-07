package via.pro3.datatierserver.repositories;

import via.pro3.datatierserver.model.Role;
import via.pro3.datatierserver.repositories.IRoleRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class RoleRepository implements IRoleRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Role add(Role role) {
        entityManager.persist(role);
        entityManager.flush();
        return role;
    }

    @Override
    public Role update(Role role) {
        Role merged = entityManager.merge(role);
        entityManager.flush();
        return merged;
    }

    @Override
    public void delete(int id) {
        Role role = entityManager.find(Role.class, id);
        if (role != null) {
            entityManager.remove(role);
            entityManager.flush();
        }
    }

    @Override
    public Optional<Role> getSingle(int id) {
        return Optional.ofNullable(entityManager.find(Role.class, id));
    }

    @Override
    public List<Role> getMany() {
        return entityManager.createQuery("SELECT r FROM Role r", Role.class)
                .getResultList();
    }
}