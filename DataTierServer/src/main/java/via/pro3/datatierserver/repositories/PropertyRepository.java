package via.pro3.datatierserver.repositories;

import via.pro3.datatierserver.model.Property;
import via.pro3.datatierserver.repositories.IPropertyRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class PropertyRepository implements IPropertyRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Property add(Property property) {
        entityManager.persist(property);
        entityManager.flush();
        return property;
    }

    @Override
    public Property update(Property property) {
        Property merged = entityManager.merge(property);
        entityManager.flush();
        return merged;
    }

    @Override
    public void delete(int id) {
        Property property = entityManager.find(Property.class, id);
        if (property != null) {
            entityManager.remove(property);
            entityManager.flush();
        }
    }

    @Override
    public Optional<Property> getSingle(int id) {
        return Optional.ofNullable(entityManager.find(Property.class, id));
    }

    @Override
    public List<Property> getMany() {
        return entityManager.createQuery("SELECT p FROM Property p", Property.class)
                .getResultList();
    }
}