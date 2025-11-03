using MainServer.Model;

namespace RepositoryInterfaces;

public interface ISaleRepository
{
    Task<Sale> AddAsync(Sale sale);
    Task<Sale> UpdateAsync(Sale sale);
    Task DeleteAsync(int id);
    Task<Sale> GetSingleAsync(int id);
    IQueryable<Sale> GetMany();
}

