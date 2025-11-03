
using MainServer.Model;

namespace RepositoryInterfaces;

public interface IBidRepository
{
    Task<Bid> AddAsync(Bid bid);
    Task<Bid> UpdateAsync(Bid bid);
    Task DeleteAsync(int id);
    Task<Bid> GetSingleAsync(int id);
    IQueryable<Bid> GetMany();
}