package via.pro3.datatierserver.repositories;

import via.pro3.datatierserver.model.Sale;
import via.pro3.datatierserver.repositories.ISaleRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class SaleRepository implements ISaleRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Sale add(Sale sale) {
        entityManager.persist(sale);
        entityManager.flush();
        return sale;
    }

    @Override
    public Sale update(Sale sale) {
        Sale merged = entityManager.merge(sale);
        entityManager.flush();
        return merged;
    }

    @Override
    public void delete(int id) {
        Sale sale = entityManager.find(Sale.class, id);
        if (sale != null) {
            entityManager.remove(sale);
            entityManager.flush();
        }
    }

    @Override
    public Optional<Sale> getSingle(int id) {
        return Optional.ofNullable(entityManager.find(Sale.class, id));
    }

    @Override
    public List<Sale> getMany() {
        return entityManager.createQuery("SELECT s FROM Sale s", Sale.class)
                .getResultList();
    }
}