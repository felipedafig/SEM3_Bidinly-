package via.pro3.datatierserver.repositories;

import via.pro3.datatierserver.model.Bid;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class BidRepository implements IBidRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Bid add(Bid bid) {
        entityManager.persist(bid);
        entityManager.flush();
        return bid;
    }

    @Override
    public Bid update(Bid bid) {
        Bid merged = entityManager.merge(bid);
        entityManager.flush();
        return merged;
    }

    @Override
    public void delete(int id) {
        Bid bid = entityManager.find(Bid.class, id);
        if (bid != null) {
            entityManager.remove(bid);
            entityManager.flush();
        }
    }

    @Override
    public Optional<Bid> getSingle(int id) {
        return Optional.ofNullable(entityManager.find(Bid.class, id));
    }

    @Override
    public List<Bid> getMany() {
        return entityManager.createQuery("SELECT b FROM Bid b", Bid.class)
                .getResultList();
    }
}