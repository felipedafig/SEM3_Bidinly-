package via.pro3.datatierserver.repositories;

import via.pro3.datatierserver.model.Property;
import java.util.List;
import java.util.Optional;

public interface IPropertyRepository {
    Property add(Property property);
    Property update(Property property);
    void delete(int id);
    Optional<Property> getSingle(int id);
    List<Property> getMany();
}