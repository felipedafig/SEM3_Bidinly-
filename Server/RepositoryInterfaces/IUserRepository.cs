using Server.Model;

namespace RepositoryInterfaces;

public interface IUserRepository
{
    Task<User> AddAsync(User user);
    Task<User> UpdateAsync(User user);
    Task DeleteAsync(int id);
    Task<User> GetSingleAsync(int id);
    IQueryable<User> GetMany();
    Task<User?> GetByUsernameAsync(string username);
}

