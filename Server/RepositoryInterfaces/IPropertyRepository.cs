using Server.Model;

namespace RepositoryInterfaces;

public interface IPropertyRepository
{
    Task<Property> AddAsync(Property property);
    Task<Property> UpdateAsync(Property property);
    Task DeleteAsync(int id);
    Task<Property> GetSingleAsync(int id);
    IQueryable<Property> GetMany();
}

