package via.pro3.datatierserver.repositories;

import via.pro3.datatierserver.model.User;
import java.util.List;
import java.util.Optional;

public interface IUserRepository {
    User add(User user);
    User update(User user);
    void delete(int id);
    Optional<User> getSingle(int id);
    List<User> getMany();
    Optional<User> getByUsername(String username);
}