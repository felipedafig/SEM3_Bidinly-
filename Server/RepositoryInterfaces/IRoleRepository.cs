using Server.Model;

namespace RepositoryInterfaces;

public interface IRoleRepository
{
    Task<Role> AddAsync(Role role);
    Task<Role> UpdateAsync(Role role);
    Task DeleteAsync(int id);
    Task<Role> GetSingleAsync(int id);
    IQueryable<Role> GetMany();
}

