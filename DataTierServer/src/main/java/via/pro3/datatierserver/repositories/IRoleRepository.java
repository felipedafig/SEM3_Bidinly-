package via.pro3.datatierserver.repositories;

import via.pro3.datatierserver.model.Role;
import java.util.List;
import java.util.Optional;

public interface IRoleRepository {
    Role add(Role role);
    Role update(Role role);
    void delete(int id);
    Optional<Role> getSingle(int id);
    List<Role> getMany();
}