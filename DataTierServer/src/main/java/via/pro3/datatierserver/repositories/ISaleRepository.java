package via.pro3.datatierserver.repositories;

import via.pro3.datatierserver.model.Sale;
import java.util.List;
import java.util.Optional;

public interface ISaleRepository {
    Sale add(Sale sale);
    Sale update(Sale sale);
    void delete(int id);
    Optional<Sale> getSingle(int id);
    List<Sale> getMany();
}