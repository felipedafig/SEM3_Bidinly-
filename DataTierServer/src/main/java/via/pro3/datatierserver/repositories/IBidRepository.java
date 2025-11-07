package via.pro3.datatierserver.repositories;

import via.pro3.datatierserver.model.Bid;
import java.util.List;
import java.util.Optional;

public interface IBidRepository {
    Bid add(Bid bid);
    Bid update(Bid bid);
    void delete(int id);
    Optional<Bid> getSingle(int id);
    List<Bid> getMany();
}